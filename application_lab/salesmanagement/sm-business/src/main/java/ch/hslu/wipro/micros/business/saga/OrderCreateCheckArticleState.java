package ch.hslu.wipro.micros.business.saga;

import ch.hslu.wipro.micros.business.converter.JsonConverterFactory;
import ch.hslu.wipro.micros.business.discovery.DiscoveryService;
import ch.hslu.wipro.micros.business.discovery.DiscoveryServiceFactory;
import ch.hslu.wipro.micros.business.discovery.MicroService;
import ch.hslu.wipro.micros.business.rabbitmq.consumer.ArticleCheckQuantityReplyConsumer;
import ch.hslu.wipro.micros.model.article.ArticleCheckQuantityDto;
import ch.hslu.wipro.micros.model.order.OrderDto;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class OrderCreateCheckArticleState implements OrderSagaState {

    @Override
    public void process(OrderSaga saga) throws IOException {
        Channel channel = saga.getContext().getChannel();
        String correlationId = UUID.randomUUID().toString();
        String replyToQueue = channel.queueDeclare().getQueue();

        OrderDto orderDto = saga.getContext().getCommand().getPayload();
        ArticleCheckQuantityDto articleCheckQuantityDto = new ArticleCheckQuantityDto();
        articleCheckQuantityDto.setAmountToArticle(orderDto.getAmountToArticle());

        String amountToArticleJson = new JsonConverterFactory<ArticleCheckQuantityDto>().get()
                .toJson(articleCheckQuantityDto);

        AMQP.BasicProperties replyProperties = new AMQP.BasicProperties
                .Builder()
                .correlationId(correlationId)
                .replyTo(replyToQueue)
                .build();

        DiscoveryService discoveryService = new DiscoveryServiceFactory().get();
        MicroService warehouseService = discoveryService.getWarehouseManagement();

        channel.basicPublish(
                warehouseService.getExchange(),
                warehouseService.getCommands("checkQuantities"),
                replyProperties,
                amountToArticleJson.getBytes(StandardCharsets.UTF_8));

        boolean noAutoAck = true;
        channel.basicConsume(replyToQueue, noAutoAck, new ArticleCheckQuantityReplyConsumer(channel, saga));
    }
}
