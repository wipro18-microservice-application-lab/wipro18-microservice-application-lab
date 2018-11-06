package ch.hslu.wipro.micros.common.discovery;

public interface DiscoverStrategy {
    ConnectionInfo get(String domain);

    void put(String domain, ConnectionInfo connectionInfo);
}