package ch.hslu.wipro.micros.common.discovery;

public class ConnectionInfoBuilder {
    private String exchange;
    private String commandQueue;
    private String eventQueue;

    public ConnectionInfoBuilder withExchange(String exchange) {
        this.exchange = exchange;
        return this;
    }

    public ConnectionInfoBuilder atCommandQueue(String commandQueue) {
        this.commandQueue = commandQueue;
        return this;
    }

    public ConnectionInfoBuilder atEventQueue(String eventQueue) {
        this.eventQueue = eventQueue;
        return this;
    }

    public ConnectionInfo build() {
        ConnectionInfo connectionInfo = new ConnectionInfo();
        connectionInfo.setExchange(exchange);
        connectionInfo.setCommandQueue(commandQueue);
        connectionInfo.setEventQueue(eventQueue);

        return connectionInfo;
    }
}