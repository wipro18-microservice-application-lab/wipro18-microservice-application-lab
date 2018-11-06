package ch.hslu.wipro.micros.salesmanagement.consumer;

import ch.hslu.wipro.micros.common.RabbitMqErrors;
import ch.hslu.wipro.micros.common.event.EventFactory;
import ch.hslu.wipro.micros.common.event.OrderConfirmedEvent;
import ch.hslu.wipro.micros.salesmanagement.OrderCallbackListener;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static com.rabbitmq.client.AMQP.BasicProperties;

public class OrderEventConsumer extends DefaultConsumer {
    private static final Logger logger = LogManager.getLogger(OrderEventConsumer.class);
    private OrderCallbackListener callbackListener;

    public OrderEventConsumer(Channel channel, OrderCallbackListener callbackListener) {
        super(channel);
        this.callbackListener = callbackListener;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope,
                               BasicProperties properties, byte[] body) throws IOException {

        EventFactory.getEventFromBytes(body).ifPresent(event -> {
            try {
                if (event instanceof OrderConfirmedEvent) {
                    logger.info("order has been confirmed by {}", ((OrderConfirmedEvent) event).getAttendant());

                    super.getChannel().basicAck(envelope.getDeliveryTag(), false);

                    callbackListener.onValidOrder();
                } else {
                    // Give the Event back to Queue
                    super.getChannel().basicNack(envelope.getDeliveryTag(), false, true);
                }
            } catch (IOException e) {
                logger.info(RabbitMqErrors.getIOExceptionMessage());
            }
        });
    }
}
