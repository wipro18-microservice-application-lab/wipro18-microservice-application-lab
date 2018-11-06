package ch.hslu.wipro.micros.common.discovery;

public class ConnectionInfo {
    private String exchange;
    private String commandQueue;
    private String eventQueue;

    public String getExchange() {
        return exchange;
    }

    void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getCommandQueue() {
        return commandQueue;
    }

    void setCommandQueue(String commandQueue) {
        this.commandQueue = commandQueue;
    }

    public String getEventQueue() {
        return eventQueue;
    }

    void setEventQueue(String eventQueue) {
        this.eventQueue = eventQueue;
    }
}