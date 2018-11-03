package ch.hslu.wipro.micros.salesmanagement.consumer;

import ch.hslu.wipro.micros.salesmanagement.OrderCallbackListener;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static com.rabbitmq.client.AMQP.*;

public class OrderResponseConsumer extends DefaultConsumer {
    private static final Logger logger = LogManager.getLogger(OrderResponseConsumer.class);
    private OrderCallbackListener callbackListener;

    public OrderResponseConsumer(Channel channel, OrderCallbackListener callbackListener) {
        super(channel);
        this.callbackListener = callbackListener;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope,
                               BasicProperties properties, byte[] body) throws IOException {
        callbackListener.onResponseEvent();
    }
}
