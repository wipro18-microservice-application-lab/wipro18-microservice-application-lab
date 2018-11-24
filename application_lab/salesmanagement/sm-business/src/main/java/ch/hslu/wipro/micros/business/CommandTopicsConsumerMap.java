package ch.hslu.wipro.micros.business;

import ch.hslu.wipro.micros.business.rabbitmq.consumer.OrderCreateCommandConsumer;
import ch.hslu.wipro.micros.business.rabbitmq.consumer.OrderGetAllByCustomerId;
import ch.hslu.wipro.micros.business.rabbitmq.consumer.OrderGetAllCommandConsumer;
import ch.hslu.wipro.micros.business.rabbitmq.topic.Topic;
import ch.hslu.wipro.micros.business.rabbitmq.topic.TopicBuilder;
import com.rabbitmq.client.DefaultConsumer;

import java.util.HashMap;
import java.util.Map;

public class CommandTopicsConsumerMap {
    private final Map<Topic, Class<? extends DefaultConsumer>> handledTopics = new HashMap<>();

    public CommandTopicsConsumerMap() {
        Topic orderCommandCreate = new TopicBuilder()
                .atRoute("order.command.create")
                .atQueue("ch.hslu.wipro.micros.OrderCreateCommand").build();
        handledTopics.put(orderCommandCreate, OrderCreateCommandConsumer.class);

        Topic orderCommandGetAll = new TopicBuilder()
                .atRoute("order.command.getAll")
                .atQueue("ch.hslu.wipro.micros.OrderGetAllCommand").build();
        handledTopics.put(orderCommandGetAll, OrderGetAllCommandConsumer.class);

        Topic orderCommandGetAllByCustomerId = new TopicBuilder()
                .atRoute("order.command.getAllByCustomerId")
                .atQueue("ch.hslu.wipro.micros.OrderGetAllByCustomerIdCommand").build();
        handledTopics.put(orderCommandGetAllByCustomerId, OrderGetAllByCustomerId.class);

        Topic orderCommandUpdateStatus = new TopicBuilder()
                .atRoute("order.command.updateStatus")
                .atQueue("ch.hslu.wipro.micros.OrderUpdateStatus").build();
        handledTopics.put(orderCommandUpdateStatus, OrderCreateCommandConsumer.class);
    }

    public Map<Topic, Class<? extends DefaultConsumer>> getAsMap() {
        return handledTopics;
    }
}