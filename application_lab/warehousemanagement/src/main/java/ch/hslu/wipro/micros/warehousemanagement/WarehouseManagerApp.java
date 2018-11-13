package ch.hslu.wipro.micros.warehousemanagement;

import ch.hslu.wipro.micros.warehousemanagement.config.ConfigConsts;
import ch.hslu.wipro.micros.warehousemanagement.config.ConfigUtils;
import ch.hslu.wipro.micros.warehousemanagement.consumer.GetArticleCommandConsumer;
import ch.hslu.wipro.micros.warehousemanagement.rabbitmq.ChannelBuilder;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class WarehouseManagerApp {
    private static final Logger logger = LogManager.getLogger(WarehouseManagerApp.class);
    private static ConfigUtils configUtils = new ConfigUtils(ConfigConsts.CONFIG_FILE);
    private static Channel channel;

    public static void main(String[] args) {

        try {
            channel = new ChannelBuilder()
                    .withHost(configUtils.getHost())
                    .build();

            channel.exchangeDeclare(configUtils.getArticleExchange(), BuiltinExchangeType.TOPIC);
            channel.queueDeclare(configUtils.getInventoryCommandQueue(), false, false, false, null);
            channel.queueBind(configUtils.getInventoryCommandQueue(),
                    configUtils.getArticleExchange(),
                    configUtils.getInventoryCommandRoutingKey());

            handleIncomingInventoryCommands();
        } catch (IOException | TimeoutException e) {
            logger.error("can't connect to rabbitmq");
        }
    }

    private static void handleIncomingInventoryCommands() throws IOException {
        boolean autoAck = false;

        channel.basicConsume(
                configUtils.getInventoryCommandQueue(),
                autoAck,
                new GetArticleCommandConsumer(channel));
    }
}