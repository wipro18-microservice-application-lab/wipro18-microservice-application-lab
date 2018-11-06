package ch.hslu.wipro.micros.customermanagement;

import ch.hslu.wipro.micros.common.RabbitMqErrors;
import ch.hslu.wipro.micros.common.RabbitMqUtils;
import ch.hslu.wipro.micros.common.discovery.DiscoveryService;
import ch.hslu.wipro.micros.common.discovery.DomainType;
import ch.hslu.wipro.micros.common.discovery.QueueType;
import ch.hslu.wipro.micros.common.discovery.StrategyFactory;
import ch.hslu.wipro.micros.common.consumer.OrderCommandConsumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Optional;

public class CustomerManagerApp {
    private static final Logger logger = LogManager.getLogger(CustomerManagerApp.class);
    private static final String domain = DomainType.CUSTOMER;

    public static void main(String[] args) {
        DiscoveryService orderConnection = new DiscoveryService(StrategyFactory.discoverByUrl(), domain);
        Optional<String> optionalQueue = orderConnection.getQueueForSubject(QueueType.COMMAND);

        optionalQueue.ifPresent(queue -> RabbitMqUtils.createChannel().ifPresent(channel -> {
            try {
                channel.basicConsume(queue, false, new OrderCommandConsumer(channel));

            } catch (IOException e) {
                logger.error(RabbitMqErrors.getIOExceptionMessage());
            }
        }));
    }
}
