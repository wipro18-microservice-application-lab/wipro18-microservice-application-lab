package ch.hslu.wipro.micros.warehousemanagement.eventsourcing;

import java.util.ArrayList;

public class EventHandler<T> {
    private ArrayList<Event<T>> eventDelegateArray = new ArrayList<>();

    public void subscribe(Event<T> methodReference) {
        eventDelegateArray.add(methodReference);
    }

    public void unSubscribe(Event<T> methodReference) {
        eventDelegateArray.remove(methodReference);
    }

    public void invoke(Object source, T eventArgs) {
        if (eventDelegateArray.size() > 0)
            eventDelegateArray.forEach(p -> p.invoke(source, eventArgs));
    }

    public void close() {
        if (eventDelegateArray.size() > 0)
            eventDelegateArray.clear();
    }
}
