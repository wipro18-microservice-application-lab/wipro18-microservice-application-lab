package ch.hslu.wipro.micros.business.discovery;

public class DiscoveryServiceFactory {

    public DiscoveryService get() {
        return new DiscoveryServiceStatic();
    }
}
