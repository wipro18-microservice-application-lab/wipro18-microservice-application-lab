package ch.hslu.wipro.micros.service;

import java.util.ArrayList;
import java.util.List;

public abstract class MessageManager implements MessageDomain {

    private List<String> commandKeys;
    private String exchange;

    public MessageManager() {
        commandKeys = new ArrayList<>();
        exchange = null;
    }

    @Override
    public void addCommandKey(String commandKey) {
        commandKeys.add(commandKey);
    }

    @Override
    public String getCommandKey(String pattern) throws IllegalArgumentException{
        return commandKeys.stream()
                .filter(command -> command.contains(pattern))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No command for pattern: "+pattern+" found"));
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    @Override
    public String getExchange() {
        return exchange;
    }
}
