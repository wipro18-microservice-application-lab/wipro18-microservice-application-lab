package ch.hslu.wipro.micros.customermanagement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class CustomerManagerApp {

    private static final Logger logger = LogManager.getLogger(CustomerManagerApp.class);

    public static void main(String[] args) {
        try {
            RabbitMqManager rabbitMqManager = new RabbitMqManager();
        } catch (IOException e) {
            logger.error("cannot connect to rabbitmq!");
        } catch (TimeoutException e) {
            logger.error("a timeout has occurred!");
        }
    }
}
