package ch.hslu.wipro.micros.rabbit;

import ch.hslu.wipro.micros.util.GsonConverter;
import ch.hslu.wipro.micros.util.JsonConverter;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;

public class RabbitClient implements MessageBroker {
    private final static String HOST = "rabbitmq"; //todo from config

    private Connection connection;
    private Channel channel;
    private JsonConverter converter;

    public RabbitClient(JsonConverter converter) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST);
        connection = factory.newConnection();
        channel = connection.createChannel();
        this.converter = converter;
    }

    @Override
    public String call(Command command) throws IOException, InterruptedException {
        final String corrId = UUID.randomUUID().toString();
        String replyQueueName = channel.queueDeclare().getQueue();
        AMQP.BasicProperties props = new AMQP.BasicProperties
                .Builder()
                .correlationId(corrId)
                .replyTo(replyQueueName)
                .build();

        byte[] message = payloadToJson(command);
        channel.basicPublish(command.getToExchange(), command.getRoutingKey(), props, message);

        final BlockingQueue<String> response = new ArrayBlockingQueue<String>(1);

        String ctag = channel.basicConsume(replyQueueName, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                if (properties.getCorrelationId().equals(corrId)) {
                    response.offer(new String(body, "UTF-8"));
                }
            }
        });

        String result = response.take();
        channel.basicCancel(ctag);
        return result;
    }

    private byte[] payloadToJson(Command command) throws UnsupportedEncodingException {
        return converter.toJsonString(command.getPayload()).getBytes("utf-8");
    }
}
