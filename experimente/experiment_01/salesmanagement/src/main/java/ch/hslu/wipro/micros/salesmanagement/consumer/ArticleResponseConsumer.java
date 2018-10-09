package ch.hslu.wipro.micros.salesmanagement.consumer;

import com.rabbitmq.client.*;

import java.nio.charset.StandardCharsets;

public class ArticleResponseConsumer extends DefaultConsumer {

    public ArticleResponseConsumer(Channel channel) {
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope,
                               AMQP.BasicProperties properties, byte[] body) {

        // ToDo: Convert from Json to ArticleDto
        System.out.println(new String(body, StandardCharsets.UTF_8));
    }
}
