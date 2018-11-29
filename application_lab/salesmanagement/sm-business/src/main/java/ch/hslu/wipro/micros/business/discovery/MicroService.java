package ch.hslu.wipro.micros.business.discovery;

import java.util.Map;

public class MicroService {
    private String exchange;
    private Map<String, String> commands;

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getCommands(String name) {
        return commands.get(name);
    }

    public void setCommands(Map<String, String> commands) {
        this.commands = commands;
    }
}