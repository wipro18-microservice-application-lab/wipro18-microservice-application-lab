package ch.hslu.wipro.micros.customermanagement;

import ch.hslu.wipro.micros.customermanagement.repository.CustomerRepository;
import ch.hslu.wipro.micros.customermanagement.repository.FakeCustomerRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class CustomerManagerApp {
    private static final Logger logger = LogManager.getLogger(CustomerManagerApp.class);
    private static final CustomerRepository customerRepository = new FakeCustomerRepository();

    public static void main(String[] args) {
        try {
            RabbitMqManager rabbitMqManager = new RabbitMqManager(customerRepository);
            rabbitMqManager.listenForCustomerRequest();
        } catch (IOException e) {
            logger.warn("encountered an IOException while communicating with rabbitmq");
        } catch (TimeoutException e) {
            logger.error("a timeout has occurred!");
        }
    }
}
