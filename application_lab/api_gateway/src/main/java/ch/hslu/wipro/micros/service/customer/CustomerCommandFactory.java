package ch.hslu.wipro.micros.service.customer;

import ch.hslu.wipro.micros.rabbit.Command;
import ch.hslu.wipro.micros.service.MessageManager;
import ch.hslu.wipro.micros.service.customer.dto.CustomerCreateDTO;
import ch.hslu.wipro.micros.service.customer.dto.CustomerByIdDTO;

public class CustomerCommandFactory {

    private static MessageManager messageManager = CustomerMessageComainFactory.getMessageManager();

    private CustomerCommandFactory() {

    }

    public static Command<String> createGetAllCustomersCommand() {
        Command<String> command = new Command<>();
        command.setRoutingKey(messageManager.getCommandKey("getAll"));
        command.setPayload("");
        command.setToExchange(messageManager.getExchange());
        return command;
    }

    public static Command<CustomerByIdDTO> createGetByCustomerIdCommand(CustomerByIdDTO dto) {
        Command<CustomerByIdDTO> command = new Command<>();
        command.setRoutingKey(messageManager.getCommandKey("getById"));
        command.setPayload(dto);
        command.setToExchange(messageManager.getExchange());
        return command;
    }

    public static Command<CustomerCreateDTO> createCreateCustomerCommand(CustomerCreateDTO dto) {
        Command<CustomerCreateDTO> command = new Command<>();
        command.setRoutingKey(messageManager.getCommandKey("create"));
        command.setPayload(dto);
        command.setToExchange(messageManager.getExchange());
        return command;
    }

    public static Command<CustomerByIdDTO> createGetReminderByIdCommand(CustomerByIdDTO dto) {
        Command<CustomerByIdDTO> command = new Command<>();
        command.setRoutingKey(messageManager.getCommandKey("getReminder"));
        command.setPayload(dto);
        command.setToExchange(messageManager.getExchange());
        return command;
    }
}
