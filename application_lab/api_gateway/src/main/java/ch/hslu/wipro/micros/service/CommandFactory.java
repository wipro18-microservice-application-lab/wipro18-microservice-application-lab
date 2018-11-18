package ch.hslu.wipro.micros.service;

import ch.hslu.wipro.micros.rabbit.Command;
import ch.hslu.wipro.micros.service.sales.OrderDTO;

/**
 * This class represents a factory to create message broker commands.
 */
public class CommandFactory {
    private static MessageRepository messageRepository = StaticMessageRepository.getMessageRepository();

    /**
     * Private constructor.
     */
    private CommandFactory() {
    }

    public static Command<OrderDTO> createOrderCreateCommand(OrderDTO orderDTO) {
        Command<OrderDTO> command = new Command<>();
        String routingKey = messageRepository.getDomain("order").getCommand("create");
        command.setRoutingKey(routingKey);
        command.setPayload(orderDTO);
        return command;
    }

    public static Command<String> createGetAllArticleCommand() {
        Command<String> command = new Command<>();
        String routingKey = messageRepository.getDomain("article").getCommand("getAll");
        command.setRoutingKey(routingKey);
        command.setPayload("");
        return command;
    }
}
