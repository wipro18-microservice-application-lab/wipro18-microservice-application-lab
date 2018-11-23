package ch.hslu.wipro.micros.business.rabbitmq.topic;

public class Topic {
    private String routingKey;
    private String queueName;

    Topic() {}

    void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public String getQueueName() {
        return queueName;
    }
}