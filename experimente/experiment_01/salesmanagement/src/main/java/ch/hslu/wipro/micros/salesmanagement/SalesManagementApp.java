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
            rabbitMqManager.sendArticleRequest(0);
            rabbitMqManager.listenForArticleResponse();

            while(true) {
                Scanner sc = new Scanner(System.in);
                System.out.print("article id: ");
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
