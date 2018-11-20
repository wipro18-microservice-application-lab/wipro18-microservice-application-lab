package ch.hslu.wipro.micros.service.sales;

import ch.hslu.wipro.micros.service.MessageManager;

public class OrderMessageManager extends MessageManager {

    public OrderMessageManager() {
        super();
    }

    @Override
    public void prepareMessageDomain() {
        setExchange("ch.hslu.wipro.micros.Order");
        addCommandKey("order.command.create");
        addCommandKey("order.command.getAll");
        addCommandKey("order.command.getAllByCustomerId");
        addCommandKey("order.command.updateStatus");
    }
}
