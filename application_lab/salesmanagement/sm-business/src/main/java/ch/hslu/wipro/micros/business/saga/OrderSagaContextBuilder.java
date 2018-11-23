package ch.hslu.wipro.micros.business.saga;

import ch.hslu.wipro.micros.business.rabbitmq.command.Command;
import ch.hslu.wipro.micros.model.order.OrderDto;
import com.rabbitmq.client.Channel;

public class OrderSagaContextBuilder {
    private Command<OrderDto> command;
    private Channel channel;

    public OrderSagaContextBuilder atCommand(Command<OrderDto> command) {
        this.command = command;
        return this;
    }

    public OrderSagaContextBuilder overChannel(Channel channel) {
        this.channel = channel;
        return this;
    }

    public OrderSagaContext build() {
        OrderSagaContext orderSagaContext = new OrderSagaContext();
        orderSagaContext.setCommand(command);
        orderSagaContext.setChannel(channel);

        return orderSagaContext;
    }
}