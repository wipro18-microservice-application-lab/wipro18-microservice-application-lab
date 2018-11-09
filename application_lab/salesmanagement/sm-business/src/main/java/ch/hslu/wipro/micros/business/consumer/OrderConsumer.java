package ch.hslu.wipro.micros.business.consumer;

import static com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class OrderConsumer extends DefaultConsumer {

    public OrderConsumer(Channel channel) {
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope,
                               BasicProperties properties, byte[] body) throws IOException {

        String route = "";
        BasicProperties basicProperties = new BasicProperties
                .Builder()
                .correlationId(properties.getCorrelationId())
                .build();

        super.getChannel().basicPublish(
                route,
                properties.getReplyTo(),
                basicProperties,
                "ORDER COMPLETED".getBytes(StandardCharsets.UTF_8));
    }
}