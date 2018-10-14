package ch.hslu.wipro.micros.salesmanagement.consumer;

import ch.hslu.wipro.micros.common.message.WarehouseCommandState;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;

public class ArticleResponseConsumer extends DefaultConsumer {
    private static final Logger logger = LogManager.getLogger(ArticleResponseConsumer.class);

    public ArticleResponseConsumer(Channel channel) {
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope,
                               AMQP.BasicProperties properties, byte[] body) {

        WarehouseCommandState warehouseCommandState = convertFromJson(body);
        printReceivedDelivery(warehouseCommandState, envelope);
    }

    private WarehouseCommandState convertFromJson(byte[] body) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        return gson.fromJson(
                new String(body, StandardCharsets.UTF_8),
                WarehouseCommandState.class);
    }

    private void printReceivedDelivery(WarehouseCommandState warehouseCommandState, Envelope envelope) {
        String deliveryInfo = String.format("exchange: %s, deliveryTag: %s, warehouse response: %s",
                envelope.getExchange(),
                envelope.getDeliveryTag(),
                warehouseCommandState);

        logger.info(deliveryInfo);
    }
}