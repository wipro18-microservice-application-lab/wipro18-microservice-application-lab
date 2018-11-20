package ch.hslu.wipro.micros.service.sales;

import ch.hslu.wipro.micros.rabbit.Command;
import ch.hslu.wipro.micros.service.MessageManager;

public class SalesCommandFactory {

    private static MessageManager messageManager = SalesMessageDomainFactory.getMessageManager();

    private SalesCommandFactory() {

    }

    public static Command<OrderDTO> createOrderCreateCommand(OrderDTO dto) {
        Command<OrderDTO> command = new Command<>();
        command.setRoutingKey(messageManager.getCommandKey("create"));
        command.setPayload(dto);
        command.setToExchange(messageManager.getExchange());
        return command;
    }

    public static Command<String> createGetAllOrdersCommand() {
        Command<String> command = new Command<>();
        command.setRoutingKey(messageManager.getCommandKey("getAll"));
        command.setPayload("");
        command.setToExchange(messageManager.getExchange());
        return command;
    }

    public static Command<CustomerIdDTO> createGetAllByCustomerIdCommand(CustomerIdDTO dto) {
        Command<CustomerIdDTO> command = new Command<>();
        command.setRoutingKey(messageManager.getCommandKey("CustomerId"));
        command.setPayload(dto);
        command.setToExchange(messageManager.getExchange());
        return command;
    }

    public static Command<UpdateOrderDTO> createUpdateOrderStatusCommand(UpdateOrderDTO dto) {
        Command<UpdateOrderDTO> command = new Command<>();
        command.setRoutingKey(messageManager.getCommandKey("update"));
        command.setPayload(dto);
        command.setToExchange(messageManager.getExchange());
        return command;
    }
}
