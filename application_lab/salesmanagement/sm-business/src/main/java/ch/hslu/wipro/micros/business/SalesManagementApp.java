package ch.hslu.wipro.micros.business;

import ch.hslu.wipro.micros.business.manager.OrderManager;
import ch.hslu.wipro.micros.model.order.OrderBuilder;
import ch.hslu.wipro.micros.model.order.OrderDto;
import ch.hslu.wipro.micros.service.config.ErrorService;
import ch.hslu.wipro.micros.service.discovery.DiscoveryService;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

public class SalesManagementApp {
    private static final Logger logger = LogManager.getLogger(SalesManagementApp.class);
    private static final ErrorService errorService = new ErrorService();

    public static void main(String[] args) {
        final String correlationId = UUID.randomUUID().toString();
        final Integer customerId = 0;
        final BigDecimal totalPrice = BigDecimal.valueOf(2500);

        final OrderDto order = new OrderBuilder()
                .withCorrelationId(correlationId)
                .atCustomer(0)
                .atTotalPrice(totalPrice)
                .build();

        String jsonOrder = new Gson().toJson(order);

        try {
            logger.info("registering salesmanagement on discovery service");
            DiscoveryService discoveryService = new DiscoveryService();
            discoveryService.register();

            logger.info("odermanager handling incoming orders");
            OrderManager orderManager = new OrderManager();
            orderManager.setup().handleIncomingOrders();
        } catch (IOException | TimeoutException e) {
            errorService.logConnectionError(SalesManagementApp.class);
        }
    }
}