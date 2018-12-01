package ch.hslu.wipro.micros.customermanagement.rabbitmq.manager;

import ch.hslu.wipro.micros.customermanagement.CommandTopicsConsumerMap;
import ch.hslu.wipro.micros.customermanagement.rabbitmq.config.RabbitMqConfig;
import ch.hslu.wipro.micros.customermanagement.rabbitmq.config.RabbitMqConsts;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommandManager {
    private static final Logger logger = LogManager.getLogger(CommandManager.class);
    private final RabbitMqConfig rabbitMqConfig = new RabbitMqConfig(RabbitMqConsts.CONFIG_FILE);
    private final CommandTopicsConsumerMap commandTopicsConsumerMap;


    public CommandManager(CommandTopicsConsumerMap commandTopicsConsumerMap) {
        this.commandTopicsConsumerMap = commandTopicsConsumerMap;
    }

    public void startWithChannel(Channel channel) {
        boolean noAutoAck = false;

        commandTopicsConsumerMap.getAsMap().forEach((topic, consumer) -> {
            try {
                channel.exchangeDeclare(rabbitMqConfig.getArticleExchange(), BuiltinExchangeType.TOPIC);
                channel.queueDeclare(topic.getQueueName(), false, false, false, null);
                channel.queueBind(topic.getQueueName(),
                        rabbitMqConfig.getArticleExchange(),
                        topic.getRoutingKey());

                channel.basicConsume(topic.getQueueName(), noAutoAck,
                        consumer.getConstructor(Channel.class).newInstance(channel));
            } catch (Exception e) {
                logger.error("can't connect to rabbitmq");
            }
        });
    }
}
