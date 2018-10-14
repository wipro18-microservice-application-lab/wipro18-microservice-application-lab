package ch.hslu.wipro.micros.warehousemanagement.eventsourcing;

public class EventBrokerFactory {
    private static final EventBroker eventBroker = new EventBroker();

    public static EventBroker getBroker() {
        return eventBroker;
    }
}
