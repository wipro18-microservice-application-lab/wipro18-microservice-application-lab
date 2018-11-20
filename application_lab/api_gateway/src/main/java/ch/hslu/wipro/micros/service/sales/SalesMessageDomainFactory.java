package ch.hslu.wipro.micros.service.sales;

import ch.hslu.wipro.micros.service.MessageManager;

public class SalesMessageDomainFactory {

    private SalesMessageDomainFactory() {

    }

    public static MessageManager getMessageManager() {
        MessageManager salesManager = new SalesMessageManager();
        salesManager.prepareMessageDomain();
        return salesManager;
    }
}
