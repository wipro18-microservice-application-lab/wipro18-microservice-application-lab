package ch.hslu.wipro.micros.service;

import ch.hslu.wipro.micros.rabbit.Command;
import ch.hslu.wipro.micros.service.sales.CustomerIdDTO;
import ch.hslu.wipro.micros.service.sales.OrderDTO;
import ch.hslu.wipro.micros.service.sales.UpdateOrderDTO;

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
        MessageDomain domain = messageRepository.getDomain("order");
        Command<OrderDTO> command = new Command<>();
        command.setRoutingKey(domain.getCommand("create"));
        command.setPayload(orderDTO);
        command.setToExchange(domain.getExchange());
        return command;
    }

    public static Command<String> createGetAllOrdersCommand() {
        MessageDomain domain = messageRepository.getDomain("order");
        Command<String> command = new Command<>();
        command.setRoutingKey(domain.getCommand("getAll"));
        command.setPayload("");
        command.setToExchange(domain.getExchange());
        return command;
    }

    public static Command<CustomerIdDTO> createGetAllByCustomerIdCommand(CustomerIdDTO dto) {
        MessageDomain domain = messageRepository.getDomain("order");
        Command<CustomerIdDTO> command = new Command<>();
        command.setRoutingKey(domain.getCommand("CustomerId"));
        command.setPayload(dto);
        command.setToExchange(domain.getExchange());
        return command;
    }

    public static Command<UpdateOrderDTO> createUpdateOrderStatusCommand(UpdateOrderDTO dto) {
        MessageDomain domain = messageRepository.getDomain("order");
        Command<UpdateOrderDTO> command = new Command<>();
        command.setRoutingKey(domain.getCommand("update"));
        command.setPayload(dto);
        command.setToExchange(domain.getExchange());
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
