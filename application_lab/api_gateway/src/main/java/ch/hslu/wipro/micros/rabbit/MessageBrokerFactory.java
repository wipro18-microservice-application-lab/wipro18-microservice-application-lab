package ch.hslu.wipro.micros.rabbit;

import ch.hslu.wipro.micros.util.GsonConverter;
import ch.hslu.wipro.micros.util.JsonConverter;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * This class represents a factory for MessageBrokers.
 */
public class MessageBrokerFactory {

    private MessageBrokerFactory() {

    }

    public static MessageBroker createMessageBrokerClient() throws IOException, TimeoutException {
        JsonConverter converter = new GsonConverter();
        MessageBroker messageBroker = new RabbitClient(converter);
        return messageBroker;
    }
}
