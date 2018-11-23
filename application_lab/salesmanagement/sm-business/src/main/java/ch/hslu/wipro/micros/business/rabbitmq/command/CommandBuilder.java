package ch.hslu.wipro.micros.business.rabbitmq.command;

public class CommandBuilder<T> {
    private String routingKey;
    private T payload;
    private String replyTo;
    private String correlationId;
    private long deliveryTag;

    public CommandBuilder() {
        this.routingKey = "";
    }

    public CommandBuilder withPayload(T payload) {
        this.payload = payload;
        return this;
    }

    public CommandBuilder atCorrelationId(String correlationId) {
        this.correlationId = correlationId;
        return this;
    }

    public CommandBuilder atReplyTo(String replyTo) {
        this.replyTo = replyTo;
        return this;
    }

    public CommandBuilder atDeliveryTag(long deliveryTag) {
        this.deliveryTag = deliveryTag;
        return this;
    }

    public Command<T> build() {
        Command<T> command = new Command<>();
        command.setRoutingKey(routingKey);
        command.setPayload(payload);
        command.setReplyTo(replyTo);
        command.setCorrelationId(correlationId);
        command.setDeliveryTag(deliveryTag);

        return command;
    }
}