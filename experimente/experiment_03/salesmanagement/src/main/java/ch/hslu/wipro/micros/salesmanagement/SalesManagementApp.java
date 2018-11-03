package ch.hslu.wipro.micros.salesmanagement;

import ch.hslu.wipro.micros.common.RabbitMqErrors;
import ch.hslu.wipro.micros.common.dto.OrderDto;
import ch.hslu.wipro.micros.common.dto.OrderState;
import ch.hslu.wipro.micros.salesmanagement.saga.OrderEligibilityState;
import ch.hslu.wipro.micros.salesmanagement.saga.OrderSagaContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class SalesManagementApp {
    private static final Logger logger = LogManager.getLogger(SalesManagementApp.class);

    public static void main(String[] args) {
        List<Integer> articles = Arrays.asList(0, 0, 1, 2);
        int customer = 0;

        try {
            OrderDto orderDto = new OrderDto(articles, customer, 1200, OrderState.OPEN);

            OrderEligibilityState orderEligibilityState = new OrderEligibilityState();
            OrderSagaContext orderSagaContext = new OrderSagaContext(orderDto);
            orderSagaContext.setState(orderEligibilityState);
            orderSagaContext.process();
        } catch (IOException | TimeoutException e) {
            logger.error(RabbitMqErrors.getIOExceptionMessage());
            logger.error(e.getMessage());
        }
    }
}
