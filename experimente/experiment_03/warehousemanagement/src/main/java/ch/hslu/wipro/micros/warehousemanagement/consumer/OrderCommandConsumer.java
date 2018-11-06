package ch.hslu.wipro.micros.common.consumer;

import ch.hslu.wipro.micros.common.RabbitMqConstants;
import ch.hslu.wipro.micros.common.RabbitMqErrors;
import ch.hslu.wipro.micros.common.command.CheckOrderCommand;
import ch.hslu.wipro.micros.common.command.CommandFactory;
import ch.hslu.wipro.micros.common.event.EventFactory;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static com.rabbitmq.client.AMQP.BasicProperties;

public class OrderCommandConsumer extends DefaultConsumer {
    private static final Logger logger = LogManager.getLogger(OrderCommandConsumer.class);

    public OrderCommandConsumer(Channel channel) {
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope,
                               BasicProperties properties, byte[] body) throws IOException {

        CommandFactory.getCommandFromBytes(body).ifPresent(command -> {
            logger.info("received {}", command.getClass().getName());
            BasicProperties replyProps = new BasicProperties
                    .Builder()
                    .correlationId(properties.getCorrelationId())
                    .build();

            try {
                if (command instanceof CheckOrderCommand) {
                    String jsonOrderConfirmedEvent = EventFactory.getOrderConfirmedEventAsJson(getClass().getName());
                    super.getChannel().basicPublish(
                            "",
                            properties.getReplyTo(),
                            replyProps,
                            jsonOrderConfirmedEvent.getBytes(RabbitMqConstants.DEFAULT_CHAR_SET));
                }
            } catch (IOException e) {
                logger.error(RabbitMqErrors.getIOExceptionMessage());
            }
        });
    }
}