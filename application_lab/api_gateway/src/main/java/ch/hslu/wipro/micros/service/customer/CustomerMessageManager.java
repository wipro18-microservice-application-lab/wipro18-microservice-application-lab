package ch.hslu.wipro.micros.service.customer;

import ch.hslu.wipro.micros.service.MessageManager;

public class CustomerMessageManager extends MessageManager {

    @Override
    public void prepareMessageDomain() {
        setExchange("ch.hslu.wipro.micros.Customer");
        addCommandKey("customer.command.create");
        addCommandKey("customer.command.getAll");
        addCommandKey("customer.command.getById"); //todo check command naming
        addCommandKey("customer.command.getReminderByCustomerId");
    }
}
