package ch.hslu.wipro.micros.warehousemanagement;

import ch.hslu.wipro.micros.warehousemanagement.eventsourcing.EventBroker;
import ch.hslu.wipro.micros.warehousemanagement.eventsourcing.EventBrokerFactory;
import ch.hslu.wipro.micros.warehousemanagement.repository.WarehouseRepository;
import ch.hslu.wipro.micros.warehousemanagement.repository.WarehouseRepositoryFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class WarehouseManagerApp {
    private static final Logger logger = LogManager.getLogger(WarehouseManagerApp.class);
    private static final WarehouseRepository warehouseRepository = WarehouseRepositoryFactory.getRepository();
    private static final EventBroker eventBroker = EventBrokerFactory.getBroker();

    public static void main(String[] args) {
        eventBroker.commands.subscribe(warehouseRepository::brokerOnCommands);

        try {
            RabbitMqManager rabbitMqManager = new RabbitMqManager();
            rabbitMqManager.listenForArticleRequest();
        } catch (IOException e) {
            logger.warn("encountered an IOException while communicating with rabbitmq");
        } catch (TimeoutException e) {
            logger.error("a timeout has occurred!");
        }
    }
}