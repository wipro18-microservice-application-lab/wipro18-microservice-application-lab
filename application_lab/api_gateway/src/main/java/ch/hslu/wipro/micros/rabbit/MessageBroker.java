package ch.hslu.wipro.micros.rabbit;

import java.io.IOException;

public interface MessageBroker {

    String call(Command command) throws IOException, InterruptedException;
}
