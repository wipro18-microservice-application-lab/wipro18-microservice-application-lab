package ch.hslu.wipro.micros.business;

import ch.hslu.wipro.micros.business.rabbitmq.ChannelBuilder;
import ch.hslu.wipro.micros.business.rabbitmq.RabbitMqConnector;
import ch.hslu.wipro.micros.business.rabbitmq.config.RabbitMqConfig;
import ch.hslu.wipro.micros.business.rabbitmq.manager.CommandManager;
import ch.hslu.wipro.micros.business.rabbitmq.topic.Topic;
import com.nurkiewicz.asyncretry.RetryExecutor;
import com.rabbitmq.client.DefaultConsumer;

import java.util.Map;

@SuppressWarnings("WeakerAccess")
public class SalesManagementApp {
    private static final RabbitMqConfig rabbitMqConfig = new RabbitMqConfig();
    private static final RetryExecutor rabbitMqExecutor = RabbitMqConnector.getExecutor();

    public static void main(String[] args) {
        Map<Topic, Class<? extends DefaultConsumer>> supportedCommandTopicMap = new CommandTopicsConsumerMap()
                .getAsMap();

        CommandManager commandManager = new CommandManager(supportedCommandTopicMap);

        rabbitMqExecutor
                .getWithRetry(() -> new ChannelBuilder()
                        .withHost(rabbitMqConfig.getHost())
                        .build())
                .thenAccept(commandManager::startWithChannel);
    }
}