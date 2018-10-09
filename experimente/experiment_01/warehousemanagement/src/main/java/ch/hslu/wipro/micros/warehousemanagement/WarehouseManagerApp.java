package ch.hslu.wipro.micros.warehousemanagement;

import ch.hslu.wipro.micros.warehousemanagement.repository.FakeWarehouseRepository;
import ch.hslu.wipro.micros.warehousemanagement.repository.WarehouseRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class WarehouseManagerApp {
    private static final Logger logger = LogManager.getLogger(WarehouseManagerApp.class);
    private static final WarehouseRepository warehouseRepository = new FakeWarehouseRepository();

    public static void main(String[] args) {
        try {
            RabbitMqManager rabbitMqManager = new RabbitMqManager(warehouseRepository);
            rabbitMqManager.listenForArticleRequest();
        } catch (IOException e) {
            logger.error("cannot connect to rabbitmq!");
        } catch (TimeoutException e) {
            logger.error("a timeout has occurred!");
        }
    }
}
