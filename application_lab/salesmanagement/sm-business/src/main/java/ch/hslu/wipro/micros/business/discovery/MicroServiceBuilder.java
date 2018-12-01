package ch.hslu.wipro.micros.business.discovery;

import java.util.HashMap;
import java.util.Map;

public class MicroServiceBuilder {
    private String exchange;
    private Map<String, String> commands = new HashMap<>();

    public MicroServiceBuilder atExchange(String exchange) {
        this.exchange = exchange;
        return this;
    }

    public MicroServiceBuilder addCommand(String name, String routingKey) {
        this.commands.put(name, routingKey);
        return this;
    }

    public MicroService build() {
        MicroService microService = new MicroService();
        microService.setExchange(exchange);
        microService.setCommands(commands);

        return microService;
    }
}
