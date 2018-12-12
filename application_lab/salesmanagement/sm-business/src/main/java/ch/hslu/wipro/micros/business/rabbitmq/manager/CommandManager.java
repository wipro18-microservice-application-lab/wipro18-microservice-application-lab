package ch.hslu.wipro.micros.business.rabbitmq.manager;

import ch.hslu.wipro.micros.business.rabbitmq.config.RabbitMqConfig;
import ch.hslu.wipro.micros.business.rabbitmq.topic.Topic;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {
    private static final Logger logger = LogManager.getLogger(CommandManager.class);
    private static final RabbitMqConfig rabbitMqConfig = new RabbitMqConfig();
    private final Map<Topic, Class<? extends DefaultConsumer>> handledTopics;

    public CommandManager(Map<Topic, Class<? extends DefaultConsumer>> handledTopics) {
        this.handledTopics = handledTopics;
    }

    public void startWithChannel(Channel channel) {
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", 5000);
        args.put("x-dead-letter-exchange", "ch.hslu.wipro.micros.DeadLetter");
        boolean noAutoAck = false;

        handledTopics.forEach((topic, consumer) -> {
            try {
                channel.exchangeDeclare(rabbitMqConfig.getDomainExchange(), BuiltinExchangeType.TOPIC);
                channel.queueDeclare(topic.getQueueName(), false, false, false, args);
                channel.queueBind(topic.getQueueName(),
                        rabbitMqConfig.getDomainExchange(),
                        topic.getRoutingKey());

                channel.basicConsume(topic.getQueueName(),
                        noAutoAck,
                        consumer.getConstructor(Channel.class).newInstance(channel));
            } catch (Exception e) {
                logger.error("can't connect to rabbitmq");
            }
        });
    }
}