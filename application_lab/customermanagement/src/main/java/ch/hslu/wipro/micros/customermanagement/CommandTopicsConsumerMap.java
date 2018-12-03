package ch.hslu.wipro.micros.customermanagement;

import ch.hslu.wipro.micros.customermanagement.rabbitmq.consumer.CustomerCreateCommandConsumer;
import ch.hslu.wipro.micros.customermanagement.rabbitmq.consumer.CustomerFlagCommandConsumer;
import ch.hslu.wipro.micros.customermanagement.rabbitmq.consumer.CustomerGetAllCommandConsumer;
import ch.hslu.wipro.micros.customermanagement.rabbitmq.consumer.CustomerGetByIdCommandConsumer;
import ch.hslu.wipro.micros.customermanagement.rabbitmq.topic.Topic;
import ch.hslu.wipro.micros.customermanagement.rabbitmq.topic.TopicBuilder;
import com.rabbitmq.client.DefaultConsumer;

import java.util.HashMap;
import java.util.Map;

public class CommandTopicsConsumerMap {
    private final Map<Topic, Class<? extends DefaultConsumer>> handledTopics = new HashMap<>();

    CommandTopicsConsumerMap() {
        Topic articleCommandCheckQuantity = new TopicBuilder()
                .atRoute("customer.command.create")
                .atQueue("ch.hslu.wipro.micros.CustomerCreateCommand").build();
        handledTopics.put(articleCommandCheckQuantity, CustomerCreateCommandConsumer.class);

        Topic articleCommandCheckQuantities = new TopicBuilder()
                .atRoute("customer.command.getAll")
                .atQueue("ch.hslu.wipro.micros.CustomerGetAllCommand").build();
        handledTopics.put(articleCommandCheckQuantities, CustomerGetAllCommandConsumer.class);

        Topic articleCommandGetAll = new TopicBuilder()
                .atRoute("customer.command.getById")
                .atQueue("ch.hslu.wipro.micros.CustomerGetByIdCommand").build();
        handledTopics.put(articleCommandGetAll, CustomerGetByIdCommandConsumer.class);

        Topic articleCommandFlag = new TopicBuilder()
                .atRoute("customer.command.flag")
                .atQueue("ch.hslu.wipro.micros.CustomerFlagCommand").build();
        handledTopics.put(articleCommandFlag, CustomerFlagCommandConsumer.class);
    }

    public Map<Topic, Class<? extends DefaultConsumer>> getAsMap() {
        return handledTopics;
    }
}