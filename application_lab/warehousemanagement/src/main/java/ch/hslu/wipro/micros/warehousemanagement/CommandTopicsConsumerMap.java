package ch.hslu.wipro.micros.warehousemanagement;

import ch.hslu.wipro.micros.warehousemanagement.rabbitmq.consumer.ArticleCheckQuantityCommandConsumer;
import ch.hslu.wipro.micros.warehousemanagement.rabbitmq.consumer.ArticleGetAllCommandConsumer;
import ch.hslu.wipro.micros.warehousemanagement.rabbitmq.topic.Topic;
import ch.hslu.wipro.micros.warehousemanagement.rabbitmq.topic.TopicBuilder;
import com.rabbitmq.client.DefaultConsumer;

import java.util.HashMap;
import java.util.Map;

public class CommandTopicsConsumerMap {
    private final Map<Topic, Class<? extends DefaultConsumer>> handledTopics = new HashMap<>();

    public CommandTopicsConsumerMap() {
        Topic orderCommandCreate = new TopicBuilder()
                .atRoute("article.command.checkQuantity")
                .atQueue("ch.hslu.wipro.micros.ArticleCheckQuantityCommand").build();
        handledTopics.put(orderCommandCreate, ArticleCheckQuantityCommandConsumer.class);

        Topic orderCommandGetAll = new TopicBuilder()
                .atRoute("article.command.getAll")
                .atQueue("ch.hslu.wipro.micros.ArticleGetAllCommand").build();
        handledTopics.put(orderCommandGetAll, ArticleGetAllCommandConsumer.class);

        Topic orderCommandGetAllByCustomerId = new TopicBuilder()
                .atRoute("article.command.getById")
                .atQueue("ch.hslu.wipro.micros.ArticleGetByIdCommand").build();
        handledTopics.put(orderCommandGetAllByCustomerId, ArticleCheckQuantityCommandConsumer.class);
    }

    public Map<Topic, Class<? extends DefaultConsumer>> getAsMap() {
        return handledTopics;
    }
}