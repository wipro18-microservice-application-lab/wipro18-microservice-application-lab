package ch.hslu.wipro.micros.service.customer;

import ch.hslu.wipro.micros.service.MessageManager;

public class CustomerMessageComainFactory {

    private CustomerMessageComainFactory() {

    }

    public static MessageManager getMessageManager() {
        MessageManager salesManager = new CustomerMessageManager();
        salesManager.prepareMessageDomain();
        return salesManager;
    }
}
