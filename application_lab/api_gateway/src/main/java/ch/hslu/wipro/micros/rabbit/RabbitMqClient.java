package ch.hslu.wipro.micros.rabbit;

import ch.hslu.wipro.micros.service.sales.SalesCommand;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;

public class RabbitMqClient {
    private Connection connection;
    private Channel channel;

    public RabbitMqClient() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        connection = factory.newConnection();
        channel = connection.createChannel();
    }

    public String call(SalesCommand command, String requestExchange) throws IOException, InterruptedException {
        final String corrId = UUID.randomUUID().toString();
        String replyQueueName = channel.queueDeclare().getQueue();
        AMQP.BasicProperties props = new AMQP.BasicProperties
                .Builder()
                .correlationId(corrId)
                .replyTo(replyQueueName)
                .build();

        channel.basicPublish(requestExchange, command.getLabel(), props, command.toJson().getBytes("UTF-8"));

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
}
