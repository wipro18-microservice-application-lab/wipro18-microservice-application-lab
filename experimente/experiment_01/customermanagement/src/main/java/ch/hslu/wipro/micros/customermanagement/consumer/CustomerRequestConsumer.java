package ch.hslu.wipro.micros.customermanagement.consumer;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.nio.charset.StandardCharsets;

public class CustomerRequestConsumer extends DefaultConsumer {

    public CustomerRequestConsumer(Channel channel) {
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope,
                               AMQP.BasicProperties properties, byte[] body) {

        System.out.println(new String(body, StandardCharsets.UTF_8));
    }
}
