package ch.hslu.wipro.micros.business.rabbitmq.consumer;

import ch.hslu.wipro.micros.business.rabbitmq.command.Command;
import ch.hslu.wipro.micros.business.rabbitmq.command.CommandBuilder;
import ch.hslu.wipro.micros.business.saga.OrderSaga;
import ch.hslu.wipro.micros.business.saga.OrderSagaContext;
import ch.hslu.wipro.micros.business.saga.OrderSagaContextBuilder;
import ch.hslu.wipro.micros.business.saga.OrderUnknownRequestState;
import ch.hslu.wipro.micros.model.order.OrderDto;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;

public class ConsumerUtil {

    public static void unknownRequest(Channel channel, long deliveryTag, AMQP.BasicProperties properties) {
        OrderDto orderDto = new OrderDto();

        Command<OrderDto> orderCreateCommand = new CommandBuilder<OrderDto>()
                .atDeliveryTag(deliveryTag)
                .withPayload(orderDto)
                .atCorrelationId(properties.getCorrelationId())
                .atReplyTo(properties.getReplyTo())
                .build();

        OrderSagaContext orderSagaContext = new OrderSagaContextBuilder()
                .overChannel(channel)
                .atCommand(orderCreateCommand)
                .build();

        OrderSaga orderSaga = new OrderSaga(orderSagaContext, new OrderUnknownRequestState());
        orderSaga.process();
    }
}
