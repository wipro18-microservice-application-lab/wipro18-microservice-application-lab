package ch.hslu.wipro.micros.salesmanagement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class SalesManagementApp {
    private static final Logger logger = LogManager.getLogger(SalesManagementApp.class);

    public static void main(String[] args) {


        try {
            RabbitMqManager rabbitMqManager = new RabbitMqManager();
            rabbitMqManager.requestArticle(0);
        } catch (IOException e) {
            logger.error("cannot connect to rabbitmq!");
        } catch (TimeoutException e) {
            logger.error("a timeout has occurred!");
        }
    }
}
