package ch.hslu.wipro.micros.warehousemanagement;

import ch.hslu.wipro.micros.warehousemanagement.rabbitmq.consumer.*;
import ch.hslu.wipro.micros.warehousemanagement.rabbitmq.topic.Topic;
import ch.hslu.wipro.micros.warehousemanagement.rabbitmq.topic.TopicBuilder;
import com.rabbitmq.client.DefaultConsumer;

import java.util.HashMap;
import java.util.Map;

public class CommandTopicsConsumerMap {
    private final Map<Topic, Class<? extends DefaultConsumer>> handledTopics = new HashMap<>();

    public CommandTopicsConsumerMap() {
        Topic articleCommandCheckQuantity = new TopicBuilder()
                .atRoute("article.command.checkQuantity")
                .atQueue("ch.hslu.wipro.micros.ArticleCheckQuantityCommand").build();
        handledTopics.put(articleCommandCheckQuantity, ArticleCheckQuantityCommandConsumer.class);

        Topic articleCommandCheckQuantities = new TopicBuilder()
                .atRoute("article.command.checkQuantities")
                .atQueue("ch.hslu.wipro.micros.ArticleCheckQuantitiesCommand").build();
        handledTopics.put(articleCommandCheckQuantities, ArticleCheckQuantitiesCommandConsumer.class);

        Topic articleCommandGetAll = new TopicBuilder()
                .atRoute("article.command.getAll")
                .atQueue("ch.hslu.wipro.micros.ArticleGetAllCommand").build();
        handledTopics.put(articleCommandGetAll, ArticleGetAllCommandConsumer.class);

        Topic articleCommandGetById = new TopicBuilder()
                .atRoute("article.command.getById")
                .atQueue("ch.hslu.wipro.micros.ArticleGetByIdCommand").build();
        handledTopics.put(articleCommandGetById, ArticleGetAllByIdCommandConsumer.class);

        Topic articleCommandReduce = new TopicBuilder()
                .atRoute("article.command.reduce")
                .atQueue("ch.hslu.wipro.micros.ArticleGetReduce").build();
        handledTopics.put(articleCommandReduce, ArticleReduceCommandConsumer.class);
    }

    public Map<Topic, Class<? extends DefaultConsumer>> getAsMap() {
        return handledTopics;
    }
}