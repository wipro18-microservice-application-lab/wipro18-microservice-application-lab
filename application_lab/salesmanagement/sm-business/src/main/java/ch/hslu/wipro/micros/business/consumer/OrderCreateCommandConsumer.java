package ch.hslu.wipro.micros.business.consumer;

import ch.hslu.wipro.micros.business.saga.OrderCreateState;
import ch.hslu.wipro.micros.business.saga.OrderSaga;
import ch.hslu.wipro.micros.business.saga.OrderSagaBuilder;
import ch.hslu.wipro.micros.service.convertion.GsonConverter;
import ch.hslu.wipro.micros.service.convertion.OrderCreateCommandConverter;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static com.rabbitmq.client.AMQP.BasicProperties;

public class OrderCreateCommandConsumer extends DefaultConsumer {
    private static final Logger logger = LogManager.getLogger(OrderCreateCommandConsumer.class);

    public OrderCreateCommandConsumer(Channel channel) {
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope,
                               BasicProperties properties, byte[] body) throws IOException {

        logger.info("handle incoming OrderCreateCommand with correlation id: {}", properties.getCorrelationId());

        OrderSaga orderSaga = new OrderSagaBuilder()
                .withJsonConverter(new GsonConverter())
                .withStateSequence(new OrderCreateState())
                .withProperties(properties)
                .atDeliveryTag(envelope.getDeliveryTag())
                .atOrder(OrderCreateCommandConverter.fromBytes(body))
                .overChannel(super.getChannel())
                .build();

        orderSaga.process();
    }
}