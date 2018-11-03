package ch.hslu.wipro.micros.common.discovery;

import java.util.List;

public class ConnectionInfo {
    private String exchange;
    private List<String> queues;

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public List<String> getQueues() {
        return queues;
    }

    public void setQueues(List<String> queues) {
        this.queues = queues;
    }
}
