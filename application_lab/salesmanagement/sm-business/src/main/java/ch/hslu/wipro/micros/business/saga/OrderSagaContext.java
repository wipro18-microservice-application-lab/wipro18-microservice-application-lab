package ch.hslu.wipro.micros.business.saga;

import ch.hslu.wipro.micros.business.rabbitmq.command.Command;
import ch.hslu.wipro.micros.model.order.OrderDto;
import com.rabbitmq.client.Channel;

public class OrderSagaContext {
    private Command<OrderDto> command;
    private Channel channel;

    OrderSagaContext() {}

    void setCommand(Command<OrderDto> command) {
        this.command = command;
    }

    void setChannel(Channel channel) {
        this.channel = channel;
    }

    public Command<OrderDto> getCommand() {
        return command;
    }

    public Channel getChannel() {
        return channel;
    }
}