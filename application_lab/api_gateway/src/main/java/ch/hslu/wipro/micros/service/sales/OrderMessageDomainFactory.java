package ch.hslu.wipro.micros.service.sales;

import ch.hslu.wipro.micros.service.MessageManager;

public class OrderMessageDomainFactory {

    private OrderMessageDomainFactory() {

    }

    public static MessageManager getMessageManager() {
        MessageManager salesManager = new OrderMessageManager();
        salesManager.prepareMessageDomain();
        return salesManager;
    }
}
