package ch.hslu.wipro.micros.service.repository;

import ch.hslu.wipro.micros.repository.OrderRepository;
import ch.hslu.wipro.micros.repository.OrderRepositoryMongo;
import ch.hslu.wipro.micros.repository.OrderRepositoryVolatile;
import ch.hslu.wipro.micros.service.config.ConfigConsts;
import ch.hslu.wipro.micros.service.config.ConfigService;

public class RepositoryFactory {
    private static ConfigService configService = new ConfigService();

    public static RepositoryService getRepository() {
        OrderRepository orderRepository;

        switch (configService.getRepositoryStrategy()) {
            case "mongo_db":
                orderRepository = new OrderRepositoryMongo();
                break;
            default:
                orderRepository = new OrderRepositoryVolatile();
        }

        return new RepositoryService(orderRepository);
    }
}
