package ch.hslu.wipro.micros.business.rabbitmq.consumer;

import ch.hslu.wipro.micros.business.saga.OrderCreatePersistState;
import ch.hslu.wipro.micros.business.saga.OrderSaga;
import com.rabbitmq.client.*;

import java.io.IOException;

public class ArticleCheckQuantityReplyConsumer extends DefaultConsumer {
    private final OrderSaga saga;

    public ArticleCheckQuantityReplyConsumer(Channel channel, OrderSaga saga) {
        super(channel);
        this.saga = saga;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        /** ToDo: Implement look at result */

        saga.setState(new OrderCreatePersistState());
        saga.process();
    }
}
