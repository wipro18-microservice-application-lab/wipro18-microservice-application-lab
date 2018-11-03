package ch.hslu.wipro.micros.common.discovery;

public class StrategyFactory {

    public static DiscoverStrategy discoverByUrl() {
        return new DiscoverStrategyUrl();
    }
}
