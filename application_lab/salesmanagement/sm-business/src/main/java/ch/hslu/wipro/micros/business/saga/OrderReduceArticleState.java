package ch.hslu.wipro.micros.business.saga;

import ch.hslu.wipro.micros.business.converter.JsonConverterFactory;
import ch.hslu.wipro.micros.business.discovery.DiscoveryService;
import ch.hslu.wipro.micros.business.discovery.DiscoveryServiceFactory;
import ch.hslu.wipro.micros.business.discovery.MicroService;
import ch.hslu.wipro.micros.business.rabbitmq.consumer.ArticleCheckQuantityReplyConsumer;
import ch.hslu.wipro.micros.business.rabbitmq.consumer.ArticleReduceReplyConsumer;
import ch.hslu.wipro.micros.model.article.ArticleCheckQuantityDto;
import ch.hslu.wipro.micros.model.article.ArticleReduceDto;
import ch.hslu.wipro.micros.model.order.OrderDto;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class OrderReduceArticleState implements OrderSagaState {

    @Override
    public void process(OrderSaga saga) throws IOException {
        Channel channel = saga.getContext().getChannel();
        String correlationId = UUID.randomUUID().toString();
        String replyToQueue = channel.queueDeclare().getQueue();

        OrderDto orderDto = saga.getContext().getCommand().getPayload();
        ArticleReduceDto articleReduceDto = new ArticleReduceDto();
        articleReduceDto.setAmountToArticle(orderDto.getAmountToArticle());

        String amountToArticleJson = new JsonConverterFactory<ArticleReduceDto>().get()
                .toJson(articleReduceDto);

        AMQP.BasicProperties replyProperties = new AMQP.BasicProperties
                .Builder()
                .correlationId(correlationId)
                .replyTo(replyToQueue)
                .build();

        DiscoveryService discoveryService = new DiscoveryServiceFactory().get();
        MicroService warehouseService = discoveryService.getWarehouseManagement();

        channel.basicPublish(
                warehouseService.getExchange(),
                warehouseService.getCommands("reduce"),
                replyProperties,
                amountToArticleJson.getBytes(StandardCharsets.UTF_8));

        boolean noAutoAck = false;
        channel.basicConsume(replyToQueue, noAutoAck, new ArticleReduceReplyConsumer(channel, saga));
    }
}
