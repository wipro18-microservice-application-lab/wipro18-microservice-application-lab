package ch.hslu.wipro.micros.business;

import ch.hslu.wipro.micros.business.manager.OrderManager;
import ch.hslu.wipro.micros.service.config.ErrorService;
import ch.hslu.wipro.micros.service.discovery.DiscoveryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class SalesManagementApp {
    private static final Logger logger = LogManager.getLogger(SalesManagementApp.class);
    private static final ErrorService errorService = new ErrorService();
    private static ScheduledExecutorService connectionService;

    public static void main(String[] args) {
        Runnable runnable = () -> {
            try {
                logger.info("registering sales manager on discovery service");
                DiscoveryService discoveryService = new DiscoveryService();
                discoveryService.register();

                startOderManager();
                connectionService.shutdown();
            } catch (IOException | TimeoutException e) {
                errorService.logConnectionError(SalesManagementApp.class);
            }
        };

        connectionService = Executors.newSingleThreadScheduledExecutor();
        connectionService.scheduleAtFixedRate(
                runnable,
                10,
                5,
                TimeUnit.SECONDS);
    }

    private static void startOderManager() {
        try {
            logger.info("order manager handling incoming orders");
            OrderManager orderManager = new OrderManager();
            orderManager.setup().handleIncomingOrders();
        } catch (IOException | TimeoutException e) {
            errorService.logConnectionError(SalesManagementApp.class);
        }
    }
}