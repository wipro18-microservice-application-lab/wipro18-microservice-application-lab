package ch.hslu.wipro.micros.rabbit;

public class Command<T> {

    private String routingKey;
    private T payload;
    private String toExchange;

    public Command() {
        routingKey = null;
        payload = null;
        toExchange = null;
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

    public String getToExchange() {
        return toExchange;
    }

    public void setToExchange(String toExchange) {
        this.toExchange = toExchange;
    }
}
