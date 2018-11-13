package ch.hslu.wipro.micros.service;

import ch.hslu.wipro.micros.rabbit.Command;
import ch.hslu.wipro.micros.service.sales.OrderDTO;

/**
 * This class represents a factory to create message broker commands.
 */
public class CommandFactory {

    /**
     * Private constructor.
     */
    private CommandFactory() {
    }

    public static Command<OrderDTO> createOrderCreateCommand(OrderDTO orderDTO) {
        Command<OrderDTO> command = new Command<>();
        String routingKey = "order.command.create";
        command.setRoutingKey(routingKey);
        command.setPayload(orderDTO);
        return command;
    }

    public static Command<String> createGetAllArticleCommand() {
        Command<String> command = new Command<>();
        String routingKey = "article.command.get";
        command.setRoutingKey(routingKey);
        command.setPayload("all");
        return command;
    }
}
