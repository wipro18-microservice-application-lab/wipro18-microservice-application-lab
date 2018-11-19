package ch.hslu.wipro.micros.business.manager;

import java.io.IOException;

public interface CommandManager {
    CommandManager setup() throws IOException;

    void handleIncomingCommands() throws IOException;
}
