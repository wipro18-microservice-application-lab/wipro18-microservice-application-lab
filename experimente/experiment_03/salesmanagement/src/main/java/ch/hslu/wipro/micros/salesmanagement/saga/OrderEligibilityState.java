package ch.hslu.wipro.micros.salesmanagement.saga;

import ch.hslu.wipro.micros.common.RabbitMqConstants;
import ch.hslu.wipro.micros.common.RabbitMqUtils;
import ch.hslu.wipro.micros.common.RoutingKey;
import ch.hslu.wipro.micros.common.command.CommandFactory;
import ch.hslu.wipro.micros.common.discovery.ConnectionInfo;
import ch.hslu.wipro.micros.common.discovery.ConnectionInfoBuilder;
import ch.hslu.wipro.micros.common.discovery.DiscoveryService;
import ch.hslu.wipro.micros.common.discovery.StrategyFactory;
import ch.hslu.wipro.micros.salesmanagement.OrderCallbackListener;
import ch.hslu.wipro.micros.salesmanagement.config.ConfigConsts;
import ch.hslu.wipro.micros.salesmanagement.config.ConfigUtils;
import ch.hslu.wipro.micros.salesmanagement.consumer.OrderEventConsumer;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

public class OrderEligibilityState implements OrderSagaState, OrderCallbackListener {
    private static final Logger logger = LogManager.getLogger(OrderEligibilityState.class);
    private final ConfigUtils configUtils = new ConfigUtils(ConfigConsts.CONFIG_FILE);
    private OrderSagaContext context;
    private Channel channel;

    public OrderEligibilityState() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RabbitMqConstants.HOST_NAME);
        Connection connection = factory.newConnection();
        channel = connection.createChannel();
    }

    @Override
    public void process(OrderSagaContext context) throws IOException {
        this.context = context;

        ConnectionInfo connectionInfo = new ConnectionInfoBuilder()
                .withExchange(configUtils.getExchange())
                .atCommandQueue(configUtils.getCommandQueue())
                .atEventQueue(configUtils.getEventQueue())
                .build();

        DiscoveryService discoveryService = new DiscoveryService(StrategyFactory.discoverByUrl());
        discoveryService.register(configUtils.getDomain(), connectionInfo);

        publishCheckOrderCommand();
        channel.basicConsume(configUtils.getEventQueue(), false,
                new OrderEventConsumer(channel, this));
    }

    @Override
    public void onValidOrder() {
        context.setState(new OrderPaymentState());
        context.process();
    }

    private void publishCheckOrderCommand() throws IOException {
        String jsonCheckOrderCommand = CommandFactory.getCheckOrderCommandAsJson(context.getOrderDto());
        final String correlationId = UUID.randomUUID().toString();

        channel.basicPublish(
                configUtils.getExchange(),
                RoutingKey.COMMAND,
                RabbitMqUtils.getPropertiesForReplyChannel(correlationId, configUtils.getEventQueue()),
                jsonCheckOrderCommand.getBytes(RabbitMqConstants.DEFAULT_CHAR_SET));

        logger.info("published CheckOrderCommand on exchange");
    }
}