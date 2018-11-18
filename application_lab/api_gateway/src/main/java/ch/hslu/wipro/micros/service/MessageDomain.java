package ch.hslu.wipro.micros.service;

import java.util.ArrayList;
import java.util.List;

public class MessageDomain {

    private List<String> commands;
    private List<String> events;
    private String exchange;

    public MessageDomain(String exchange) {
        commands = new ArrayList<>();
        events = new ArrayList<>();
        this.exchange = exchange;
    }

    public void addCommand(String command) {
        commands.add(command);
    }

    public void addEvent(String event) {
        events.add(event);
    }

    public String getCommand(String pattern) {
        return commands.stream()
                .filter(command -> command.contains(pattern))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No command for pattern: "+pattern+" found"));
    }

    public String getExchange() {
        return exchange;
    }
}
