package ch.hslu.wipro.micros.service;

import java.util.ArrayList;
import java.util.List;

public class MessageDomain {

    private List<String> commands;
    private List<String> events;

    public MessageDomain() {
        commands = new ArrayList<>();
        events = new ArrayList<>();
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
}
