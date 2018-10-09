package ch.hslu.wipro.micros.warehousemanagement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class WarehouseManagerApp {
    private static final Logger logger = LogManager.getLogger(WarehouseManagerApp.class);

    public static void main(String[] args) {
        try {
            RabbitMqManager rabbitMqManager = new RabbitMqManager();
            rabbitMqManager.listenForArticleRequest();
        } catch (IOException e) {
            logger.error("cannot connect to rabbitmq!");
        } catch (TimeoutException e) {
            logger.error("a timeout has occurred!");
        }
    }
}
