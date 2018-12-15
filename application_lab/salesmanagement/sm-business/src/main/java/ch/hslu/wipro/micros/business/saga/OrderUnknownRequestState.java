package ch.hslu.wipro.micros.business.saga;

import ch.hslu.wipro.micros.business.converter.JsonConverterFactory;
import ch.hslu.wipro.micros.business.rabbitmq.consumer.OrderCreateCommandConsumer;
import ch.hslu.wipro.micros.business.result.OrderCreateCommandResult;
import ch.hslu.wipro.micros.business.result.OrderCreateCommandResultBuilder;
import ch.hslu.wipro.micros.business.result.OrderCreateResult;
import ch.hslu.wipro.micros.model.order.OrderDto;
import com.rabbitmq.client.Channel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.rabbitmq.client.AMQP.BasicProperties;

public class OrderUnknownRequestState implements OrderSagaState {
    private static final Logger logger = LogManager.getLogger(OrderUnknownRequestState.class);

    @Override
    public void process(OrderSaga saga) throws IOException {
        logger.warn("Unknown order request");

        Channel channel = saga.getContext().getChannel();
        String correlationId = saga.getContext().getCommand().getCorrelationId();
        String replyToQueue = saga.getContext().getCommand().getReplyTo();
        String replyRoutingKey = "";

        OrderCreateCommandResult orderCreateCommandResult = new OrderCreateCommandResultBuilder()
                .withOrder(saga.getContext().getCommand().getPayload())
                .withResult(OrderCreateResult.UNKNOWN_REQUEST)
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

        boolean acknowledgeAll = false;
        channel.basicNack(saga.getContext().getCommand().getDeliveryTag(), acknowledgeAll, false);
    }
}