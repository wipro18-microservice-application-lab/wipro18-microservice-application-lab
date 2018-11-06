package ch.hslu.wipro.micros.mailservice.consumer;

import ch.hslu.wipro.micros.common.event.EventFactory;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static com.rabbitmq.client.AMQP.BasicProperties;

public class OrderEventConsumer extends DefaultConsumer {
    private static final Logger logger = LogManager.getLogger(OrderEventConsumer.class);

    public OrderEventConsumer(Channel channel) {
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope,
                               BasicProperties properties, byte[] body) throws IOException {

        EventFactory.getEventFromBytes(body).ifPresent(event -> {
            logger.info("received {}", event.getClass().getName());
            logger.info("sending order confirmation email");
        });
    }
}