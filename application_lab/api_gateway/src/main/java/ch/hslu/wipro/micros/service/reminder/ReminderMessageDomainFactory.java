package ch.hslu.wipro.micros.service.reminder;

import ch.hslu.wipro.micros.service.MessageManager;

public class ReminderMessageDomainFactory {

    private ReminderMessageDomainFactory() {

    }

    public static MessageManager getMessageManager() {
        MessageManager salesManager = new ReminderMessageManager();
        salesManager.prepareMessageDomain();
        return salesManager;
    }
}
