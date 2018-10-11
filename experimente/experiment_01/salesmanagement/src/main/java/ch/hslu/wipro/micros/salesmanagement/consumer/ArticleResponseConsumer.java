package ch.hslu.wipro.micros.salesmanagement.consumer;

import ch.hslu.wipro.micros.common.dto.ArticleDto;
import ch.hslu.wipro.micros.common.message.RequestOperation;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.rabbitmq.client.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Set;

public class ArticleResponseConsumer extends DefaultConsumer {
    private static final Logger logger = LogManager.getLogger(ArticleResponseConsumer.class);
    public ArticleResponseConsumer(Channel channel) {
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope,
                               AMQP.BasicProperties properties, byte[] body) {

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        RequestOperation<ArticleDto> requestOperation =
                gson.fromJson(new String(body, StandardCharsets.UTF_8),
                new TypeToken<RequestOperation<ArticleDto>>(){}.getType());

        if (requestOperation.isSuccessful()) {
            printSuccessfulDelivery(requestOperation, envelope);
        } else {
            printFailedDelivery(requestOperation, envelope);
        }
    }

    private void printFailedDelivery(RequestOperation<ArticleDto> requestOperation, Envelope envelope) {
        String deliveryInfo = String.format("exchange: %s, deliveryTag: %s, no article with %d id found.",
                envelope.getExchange(),
                envelope.getDeliveryTag(),
                requestOperation.getRequestedId());

        logger.warn(deliveryInfo);
    }

    private void printSuccessfulDelivery(RequestOperation<ArticleDto> requestOperation, Envelope envelope) {
        String deliveryInfo = String.format("exchange: %s, deliveryTag: %s, article name: %s, article price: %d",
                envelope.getExchange(),
                envelope.getDeliveryTag(),
                requestOperation.getDto().getName(),
                requestOperation.getDto().getPrice());

        logger.info(deliveryInfo);
    }
}
