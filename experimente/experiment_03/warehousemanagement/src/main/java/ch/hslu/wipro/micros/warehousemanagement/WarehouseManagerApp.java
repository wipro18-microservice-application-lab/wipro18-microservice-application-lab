package ch.hslu.wipro.micros.warehousemanagement;


import ch.hslu.wipro.micros.common.RabbitMqChannelBuilder;
import ch.hslu.wipro.micros.common.RabbitMqConstants;
import ch.hslu.wipro.micros.common.RabbitMqErrors;
import ch.hslu.wipro.micros.common.discovery.ConnectionInfo;
import ch.hslu.wipro.micros.common.discovery.DiscoveryService;
import ch.hslu.wipro.micros.common.discovery.DomainType;
import ch.hslu.wipro.micros.common.discovery.StrategyFactory;
import ch.hslu.wipro.micros.warehousemanagement.config.ConfigConsts;
import ch.hslu.wipro.micros.warehousemanagement.config.ConfigUtils;
import ch.hslu.wipro.micros.warehousemanagement.consumer.OrderCommandConsumer;
import com.rabbitmq.client.Channel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class WarehouseManagerApp {
    private static final Logger logger = LogManager.getLogger(WarehouseManagerApp.class);
    private static ConfigUtils configUtils = new ConfigUtils(ConfigConsts.CONFIG_FILE);
    private static ScheduledExecutorService connectionService;
    private static ConnectionInfo connectionInfo;

    public static void main(String[] args) {

        Runnable runnable = () -> {
            logger.info("retrieving connection info for domain = {}", DomainType.ORDER);
            DiscoveryService discoveryService = new DiscoveryService(StrategyFactory.discoverByUrl());
            connectionInfo = discoveryService.getConnection(DomainType.ORDER);

            if (connectionInfo.getExchange() != null) {
                connectionService.shutdown();
                listenForOrderCommands();
            }
        };

        connectionService = Executors.newSingleThreadScheduledExecutor();
        connectionService.scheduleAtFixedRate(
                runnable,
                configUtils.getConnectionDelay(),
                configUtils.getConnectionPeriod(),
                TimeUnit.SECONDS);
    }

    private static void listenForOrderCommands() {
        try {
            Channel channel = new RabbitMqChannelBuilder()
                    .withHost(RabbitMqConstants.HOST_NAME)
                    .build();

            channel.basicConsume(connectionInfo.getCommandQueue(), false, new OrderCommandConsumer(channel));

        } catch (IOException | TimeoutException e) {
            logger.error(RabbitMqErrors.getIOExceptionMessage());
        }
    }
}