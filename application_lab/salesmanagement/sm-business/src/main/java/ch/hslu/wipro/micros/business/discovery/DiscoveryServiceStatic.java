package ch.hslu.wipro.micros.business.discovery;

public class DiscoveryServiceStatic implements DiscoveryService {

    public MicroService getWarehouseManagement() {
        return new MicroServiceBuilder()
                .atExchange("ch.hslu.wipro.micros.Article")
                .addCommand("checkQuantities", "article.command.checkQuantities")
                .build();
    }
}
