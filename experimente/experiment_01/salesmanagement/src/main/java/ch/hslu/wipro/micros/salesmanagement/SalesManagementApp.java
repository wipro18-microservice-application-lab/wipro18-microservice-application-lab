package ch.hslu.wipro.micros.salesmanagement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class SalesManagementApp {
    private static final Logger logger = LogManager.getLogger(SalesManagementApp.class);

    public static void main(String[] args) {

        try (RabbitMqManager rabbitMqManager = new RabbitMqManager()) {
            rabbitMqManager.sendArticleRequest(0L);
            rabbitMqManager.sendCustomerRequest(0L);
            rabbitMqManager.listenForArticleResponse();
            rabbitMqManager.listenForCustomerResponse();

            while(true) {
                Scanner sc = new Scanner(System.in);
                long articleId = sc.nextLong();

                rabbitMqManager.sendArticleRequest(articleId);
            }
        } catch (IOException e) {
            logger.warn("encountered an IOException while communicating with rabbitmq");
        } catch (TimeoutException e) {
            logger.error("a timeout has occurred!");
        } catch (NumberFormatException e) {
            logger.error("input is not valid");
        }
    }
}
