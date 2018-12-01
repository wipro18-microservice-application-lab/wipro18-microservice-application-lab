package ch.hslu.wipro.micros.service.reminder;

import ch.hslu.wipro.micros.rabbit.Command;
import ch.hslu.wipro.micros.service.MessageManager;

public class ReminderCommandFactory {

    private static MessageManager messageManager = ReminderMessageDomainFactory.getMessageManager();

    private ReminderCommandFactory() {

    }

    public static Command<String> createGetAllRemindersCommand() {
        Command<String> command = new Command<>();
        command.setRoutingKey(messageManager.getCommandKey("getAll"));
        command.setPayload("");
        command.setToExchange(messageManager.getExchange());
        return command;
    }
}
