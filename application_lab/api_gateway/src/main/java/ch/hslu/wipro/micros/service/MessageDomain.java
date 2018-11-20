package ch.hslu.wipro.micros.service;

public interface MessageDomain {

    void addCommandKey(String commandKey);

    String getCommandKey(String pattern);

    String getExchange();

    void prepareMessageDomain();
}
