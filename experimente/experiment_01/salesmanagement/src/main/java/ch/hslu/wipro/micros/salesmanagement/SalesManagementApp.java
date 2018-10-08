package ch.hslu.wipro.micros.salesmanagement;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class SalesManagementApp {

    public static void main(String[] args) {
        try {
            RabbitMqManager rabbitMqManager = new RabbitMqManager();
            rabbitMqManager.requestArticle(0);
        } catch (IOException e) {
            System.err.println("cannot connect to rabbitmq!");
        } catch (TimeoutException e) {
            System.err.println("a timeout has occurred!");
        }
    }
}
