package ch.hslu.wipro.micros.business;

import ch.hslu.wipro.micros.business.manager.OrderManager;
import ch.hslu.wipro.micros.service.config.ErrorService;
import ch.hslu.wipro.micros.service.discovery.DiscoveryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class SalesManagementApp {
    private static final Logger logger = LogManager.getLogger(SalesManagementApp.class);
    private static final ErrorService errorService = new ErrorService();

    public static void main(String[] args) {

        try {
            logger.info("registering sales manager on discovery service");
            DiscoveryService discoveryService = new DiscoveryService();
            discoveryService.register();

            logger.info("order manager handling incoming orders");
            OrderManager orderManager = new OrderManager();
            orderManager.setup().handleIncomingOrders();
        } catch (IOException | TimeoutException e) {
            errorService.logConnectionError(SalesManagementApp.class);
        }
    }
}