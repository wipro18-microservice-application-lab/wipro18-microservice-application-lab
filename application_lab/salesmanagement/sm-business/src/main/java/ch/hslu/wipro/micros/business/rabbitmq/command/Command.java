package ch.hslu.wipro.micros.business.rabbitmq.command;

public class Command<T> {
    private String routingKey;
    private T payload;
    private String replyTo;
    private String correlationId;
    private long deliveryTag;

    void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    void setPayload(T payload) {
        this.payload = payload;
    }

    void setReplyTo(String replyTo) {
        this.replyTo = replyTo;
    }

    void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    void setDeliveryTag(long deliveryTag) {
        this.deliveryTag = deliveryTag;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public T getPayload() {
        return payload;
    }

    public String getReplyTo() {
        return replyTo;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public long getDeliveryTag() {
        return deliveryTag;
    }
}