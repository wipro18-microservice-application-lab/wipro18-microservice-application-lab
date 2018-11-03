package ch.hslu.wipro.micros.common.discovery;

import com.google.gson.Gson;

import java.util.Optional;

public class DiscoveryService {
    private ConnectionInfo connectionInfo;

    public DiscoveryService(DiscoverStrategy strategy, String domain) {
        String jsonAllConnectionInfo = strategy.retrieve(domain);
        connectionInfo = new Gson().fromJson(jsonAllConnectionInfo, ConnectionInfo.class);
    }

    public Optional<String> getQueueForSubject(final String subject) {
        return connectionInfo.getQueues()
                .stream()
                .filter(x -> x.toLowerCase().contains(subject))
                .findFirst();
    }

    public String getExchange() {
        return connectionInfo.getExchange();
    }
}
