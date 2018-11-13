package ch.hslu.wipro.micros.service;

import ch.hslu.wipro.micros.rabbit.Command;
import ch.hslu.wipro.micros.service.sales.OrderDTO;

public class CommandFactory {

    private CommandFactory() {

    }

    public static Command<OrderDTO> createOrderCreateCommand(OrderDTO orderDTO) {
        Command<OrderDTO> command = new Command<>();
        String routingKey = "order.command.create";
        command.setRoutingKey(routingKey);
        command.setPayload(orderDTO);
        return command;
    }
}
