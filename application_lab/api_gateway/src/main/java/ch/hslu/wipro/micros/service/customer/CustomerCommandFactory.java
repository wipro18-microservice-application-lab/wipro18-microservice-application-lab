package ch.hslu.wipro.micros.service.customer;

import ch.hslu.wipro.micros.rabbit.Command;
import ch.hslu.wipro.micros.service.MessageManager;

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
}
