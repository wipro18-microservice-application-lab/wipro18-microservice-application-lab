package ch.hslu.wipro.micros.common.discovery;

public class DiscoveryService {
    private DiscoverStrategy discoverStrategy;

    public DiscoveryService(DiscoverStrategy discoverStrategy) {
        this.discoverStrategy = discoverStrategy;
    }

    public void register(String domain, ConnectionInfo connectionInfo) {
        discoverStrategy.put(domain, connectionInfo);
    }

    public ConnectionInfo getConnection(String domain) {
        return discoverStrategy.get(domain);
    }
}
