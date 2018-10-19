package ch.hslu.wipro.micros.warehousemanagement.eventsourcing;

@FunctionalInterface
public interface Event<T extends Object> {
    void invoke(Object source, T eventArgs);
}
