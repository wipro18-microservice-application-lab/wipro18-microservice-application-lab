package ch.hslu.wipro.micros.service;

import java.util.HashMap;

public class MessageRepository {
    private HashMap<String, MessageDomain> repository;

    public MessageRepository() {
        repository = new HashMap<>();
    }

    public void addDomain(String key, MessageDomain domain) {
        repository.put(key, domain);
    }

    public MessageDomain getDomain(String key) {
        return repository.get(key);
    }
}
