package ch.hslu.wipro.micros.warehousemanagement.rabbitmq.topic;

public class TopicBuilder {
    private String routingKey;
    private String queueName;

    public TopicBuilder atRoute(String routingKey) {
        this.routingKey = routingKey;
        return this;
    }

    public TopicBuilder atQueue(String queueName) {
        this.queueName = queueName;
        return this;
    }

    public Topic build() {
        Topic topic = new Topic();
        topic.setRoutingKey(routingKey);
        topic.setQueueName(queueName);

        return topic;
    }
}