package ch.hslu.wipro.micros.salesmanagement.saga;

import ch.hslu.wipro.micros.common.RabbitMqConstants;
import ch.hslu.wipro.micros.common.RabbitMqErrors;
import ch.hslu.wipro.micros.common.RoutingKey;
import ch.hslu.wipro.micros.common.command.CommandFactory;
import ch.hslu.wipro.micros.common.discovery.DiscoveryService;
import ch.hslu.wipro.micros.common.discovery.DomainType;
import ch.hslu.wipro.micros.common.discovery.QueueType;
import ch.hslu.wipro.micros.common.discovery.StrategyFactory;
import ch.hslu.wipro.micros.salesmanagement.OrderCallbackListener;
import ch.hslu.wipro.micros.salesmanagement.consumer.OrderResponseConsumer;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class OrderEligibilityState implements OrderSagaState, OrderCallbackListener {
    private static final Logger logger = LogManager.getLogger(OrderEligibilityState.class);
    private OrderSagaContext context;
    private int eligibilityCheckCount = 0;
    private Channel channel;

    public OrderEligibilityState() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RabbitMqConstants.HOST_NAME);
        Connection connection = factory.newConnection();
        channel = connection.createChannel();
    }

    @Override
    public void process(OrderSagaContext context) {
        this.context = context;
        DiscoveryService orderConnection = new DiscoveryService(StrategyFactory.discoverByUrl(), DomainType.ORDER);
        sendCheckOrderCommand();

        orderConnection.getQueueForSubject(QueueType.REPLY).ifPresent(queue -> {
            try {
                channel.basicConsume(queue, true, new OrderResponseConsumer(channel, this));
            } catch (IOException e) {
                logger.error(RabbitMqErrors.getIOExceptionMessage());
                logger.error(e.getMessage());
            }
        });
    }

    @Override
    public void onResponseEvent() {
        eligibilityCheckCount++;
        logger.info("received acknowledgement");
        if (eligibilityCheckCount >= 2 && context.getState() instanceof OrderEligibilityState) {
            context.setState(new OrderPaymentState());
        }
    }

    private void sendCheckOrderCommand() {
        DiscoveryService articleConnection = new DiscoveryService(StrategyFactory.discoverByUrl(), DomainType.ARTICLE);
        DiscoveryService customerConnection = new DiscoveryService(StrategyFactory.discoverByUrl(), DomainType.CUSTOMER);

        String jsonCheckOrderCommand = CommandFactory.getCheckOrderCommandAsJson(context.getOrderDto());

        try {
            channel.basicPublish(articleConnection.getExchange(), RoutingKey.COMMAND,
                    RabbitMqConstants.jsonMimeType, jsonCheckOrderCommand.getBytes("UTF-8"));

            channel.basicPublish(customerConnection.getExchange(), RoutingKey.COMMAND,
                    RabbitMqConstants.jsonMimeType, jsonCheckOrderCommand.getBytes("UTF-8"));
        } catch (IOException e) {
            logger.error(RabbitMqErrors.getIOExceptionMessage());
            logger.error(e.getMessage());
        }
    }
}
