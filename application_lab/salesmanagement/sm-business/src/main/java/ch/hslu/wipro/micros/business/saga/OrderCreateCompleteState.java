package ch.hslu.wipro.micros.business.saga;

import ch.hslu.wipro.micros.business.converter.JsonConverterFactory;
import ch.hslu.wipro.micros.business.result.OrderCreateCommandResult;
import ch.hslu.wipro.micros.business.result.OrderCreateCommandResultBuilder;
import ch.hslu.wipro.micros.business.result.OrderCreateResult;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.rabbitmq.client.AMQP.BasicProperties;

public class OrderCreateCompleteState implements OrderSagaState {

    @Override
    public void process(OrderSaga saga) throws IOException {
        Channel channel = saga.getContext().getChannel();
        String correlationId = saga.getContext().getCommand().getCorrelationId();
        String replyToQueue = saga.getContext().getCommand().getReplyTo();
        String replyRoutingKey = "";

        OrderCreateCommandResult orderCreateCommandResult = new OrderCreateCommandResultBuilder()
                .withOrder(saga.getContext().getCommand().getPayload())
                .withResult(OrderCreateResult.SUCCESSFUL)
                .build();

        String operationResult = new JsonConverterFactory<OrderCreateCommandResult>()
                .get()
                .toJson(orderCreateCommandResult);

        BasicProperties replyProperties = new BasicProperties
                .Builder()
                .correlationId(correlationId)
                .build();

        channel.basicPublish(
                replyRoutingKey,
                replyToQueue,
                replyProperties,
                operationResult.getBytes(StandardCharsets.UTF_8));

        channel.basicPublish("ch.hslu.wipro.micros.Order",
                "order.event.complete",
                new BasicProperties(),
                "Hello World!".getBytes(StandardCharsets.UTF_8));

        boolean acknowledgeAll = false;
        channel.basicAck(saga.getContext().getCommand().getDeliveryTag(), acknowledgeAll);
    }
}