package ch.hslu.wipro.micros.rabbit;

public class Command<T> {

    private String routingKey;
    private T payload;

    public Command() {
        routingKey = null;
        payload = null;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }
}
