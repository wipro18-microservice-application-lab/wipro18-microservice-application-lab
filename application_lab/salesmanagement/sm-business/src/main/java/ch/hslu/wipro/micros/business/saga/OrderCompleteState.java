package ch.hslu.wipro.micros.business.saga;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import ch.hslu.wipro.micros.business.OrderCreateMessages;
import com.rabbitmq.client.Channel;

import static com.rabbitmq.client.AMQP.BasicProperties;

public class OrderCompleteState implements OrderSagaState {

    @Override
    public void process(OrderSaga saga) throws IOException {
        Channel channel = saga.getContext().getChannel();
        String correlationId = saga.getContext().getCorrelationId();
        String replyToQueue = saga.getContext().getReplyToQueue();
        String replyRoutingKey = "";

        BasicProperties replyProperties = new BasicProperties
                .Builder()
                .correlationId(correlationId)
                .build();

        channel.basicPublish(
                replyRoutingKey,
                replyToQueue,
                replyProperties,
                OrderCreateMessages.SUCCESSFUL.getBytes(StandardCharsets.UTF_8));

        boolean acknowledgeAll = false;
        channel.basicAck(saga.getContext().getDeliveryTag(), acknowledgeAll);
    }
}
