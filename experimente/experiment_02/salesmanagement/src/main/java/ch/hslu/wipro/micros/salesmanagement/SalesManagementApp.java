package ch.hslu.wipro.micros.salesmanagement;

import ch.hslu.wipro.micros.common.command.ChangeArticleStockCommand;
import ch.hslu.wipro.micros.common.command.UndoLastCommand;
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
            rabbitMqManager.sendArticleRequest(new ChangeArticleStockCommand(0, 1));
            rabbitMqManager.sendCustomerRequest(3);

            rabbitMqManager.sendArticleRequest(new UndoLastCommand());

            rabbitMqManager.listenForArticleResponse();
            rabbitMqManager.listenForCustomerResponse();

            Scanner sc = new Scanner(System.in);
            while(true) {
                sc.next();
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
