package ch.hslu.wipro.micros.service.reminder;

import ch.hslu.wipro.micros.service.MessageManager;

public class ReminderMessageManager extends MessageManager {

    @Override
    public void prepareMessageDomain() {
       setExchange("ch.hslu.wipro.micros.Reminder");
       addCommandKey("reminder.command.getAll");
    }
}
