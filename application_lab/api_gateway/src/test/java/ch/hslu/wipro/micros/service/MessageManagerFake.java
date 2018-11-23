package ch.hslu.wipro.micros.service;

/**
 * This class represents a MessageManager fake implementation.
 */
public class MessageManagerFake extends MessageManager {

    @Override
    public void prepareMessageDomain() {
        setExchange("fake.exchange");
        addCommandKey("fake.create");
    }
}
