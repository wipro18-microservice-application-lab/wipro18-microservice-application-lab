package ch.hslu.wipro.micros.salesmanagement.consumer;

import ch.hslu.wipro.micros.common.ArticleDto;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rabbitmq.client.*;

import java.nio.charset.StandardCharsets;

public class ArticleResponseConsumer extends DefaultConsumer {

    public ArticleResponseConsumer(Channel channel) {
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope,
                               AMQP.BasicProperties properties, byte[] body) {

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        ArticleDto article = gson.fromJson(new String(body, StandardCharsets.UTF_8), ArticleDto.class);

        printReceivedDelivery(article, envelope);

        System.out.println(new String(body, StandardCharsets.UTF_8));
    }

    private void printReceivedDelivery(ArticleDto article, Envelope envelope) {
        String deliveryInfo = String.format("exchange: %s, deliveryTag: %s, article name: %s, article price: %d",
                envelope.getExchange(), envelope.getDeliveryTag(), article.getName(), article.getPrice());

        System.out.println(deliveryInfo);
    }
}
