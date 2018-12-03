package ch.hslu.wipro.micros.business.discovery;

public class DiscoveryServiceStatic implements DiscoveryService {

    public MicroService getWarehouseManagement() {
        return new MicroServiceBuilder()
                .atExchange("ch.hslu.wipro.micros.Article")
                .addCommand("checkQuantities", "article.command.checkQuantities")
                .addCommand("reduce", "article.command.reduce")
                .build();
    }

    public MicroService getCustomerManagement() {
        return new MicroServiceBuilder()
                .atExchange("ch.hslu.wipro.micros.Customer")
                .addCommand("getById", "customer.command.getById")
                .build();
    }
}
