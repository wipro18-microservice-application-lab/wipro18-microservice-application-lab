package ch.hslu.wipro.micros.common.consumer;

import ch.hslu.wipro.micros.common.RabbitMqConstants;
import ch.hslu.wipro.micros.common.RoutingKey;
import ch.hslu.wipro.micros.common.command.CheckOrderCommand;
import ch.hslu.wipro.micros.common.command.CommandFactory;
import ch.hslu.wipro.micros.common.discovery.DiscoveryService;
import ch.hslu.wipro.micros.common.discovery.DomainType;
import ch.hslu.wipro.micros.common.discovery.StrategyFactory;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.rabbitmq.client.AMQP.BasicProperties;

public class OrderCommandConsumer extends DefaultConsumer {
    private static final Logger logger = LogManager.getLogger(OrderCommandConsumer.class);

    public OrderCommandConsumer(Channel channel) {
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope,
                               BasicProperties properties, byte[] body) throws IOException {

        DiscoveryService discoveryService = new DiscoveryService(StrategyFactory.discoverByUrl(), DomainType.ORDER);


        CommandFactory.getCommandFromBytes(body).ifPresent(command -> {
            try {
                logger.info("Received a new command {}", command.getClass().getName());

                if (command instanceof CheckOrderCommand) {
                    /* this dummy just acknowledges anything */
                    super.getChannel().basicAck(envelope.getDeliveryTag(), false);
                    super.getChannel().basicPublish(discoveryService.getExchange(), RoutingKey.REPLY,
                            RabbitMqConstants.jsonMimeType, "".getBytes(RabbitMqConstants.defaultCharSet));
                }
            } catch (IOException e) {
                logger.error("There was a problem sending dummy acknowledge");
            }
        });
    }
}
