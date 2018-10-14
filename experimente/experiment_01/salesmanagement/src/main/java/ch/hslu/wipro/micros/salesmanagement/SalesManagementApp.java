package ch.hslu.wipro.micros.salesmanagement;

import ch.hslu.wipro.micros.common.command.ChangeArticleStockCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class SalesManagementApp {
    private static final Logger logger = LogManager.getLogger(SalesManagementApp.class);

    public static void main(String[] args) {

        try (RabbitMqManager rabbitMqManager = new RabbitMqManager()) {
            rabbitMqManager.listenForArticleResponse();
            rabbitMqManager.listenForCustomerResponse();

            while(true) {
                logger.info("Create a new order");
                logger.info("article id: ");
                Scanner sc = new Scanner(System.in);
                long articleId = sc.nextLong();

                logger.info("customer id: ");
                long customerId = sc.nextLong();

                rabbitMqManager.sendArticleRequest(new ChangeArticleStockCommand(articleId, 1));
                rabbitMqManager.sendCustomerRequest(customerId);
            }

        } catch (IOException e) {
            logger.warn("encountered an IOException while communicating with rabbitmq");
        } catch (TimeoutException e) {
            logger.error("a timeout has occurred!");
        } catch (NumberFormatException e) {
            logger.error("input is not valid");
        } catch (NoSuchElementException e) {
            logger.error("pressed ctrl + c");
        }
    }
}
