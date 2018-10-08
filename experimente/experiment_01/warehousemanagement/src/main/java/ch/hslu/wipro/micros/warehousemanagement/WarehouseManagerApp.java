package ch.hslu.wipro.micros.warehousemanagement;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class WarehouseManagerApp {

    public static void main(String[] args) {
        try {
            RabbitMqManager rabbitMqManager = new RabbitMqManager();
            rabbitMqManager.listenForArticleRequest();
        } catch (IOException e) {
            System.err.println("cannot connect to rabbitmq!");
        } catch (TimeoutException e) {
            System.err.println("a timeout has occurred!");
        }
    }
}
