package ch.hslu.wipro.micros.salesmanagement.saga;


import ch.hslu.wipro.micros.common.dto.OrderDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class OrderSagaContext {
    private static final Logger logger = LogManager.getLogger(OrderSagaContext.class);
    private final OrderDto orderDto;
    private OrderSagaState orderSagaState;

    public OrderSagaContext(OrderDto orderDto) {
        this.orderDto = orderDto;
    }

    public void process() {
        try {
            orderSagaState.process(this);
        } catch (IOException e) {
            logger.error("connection to rabbitmq lost in state = {}", orderSagaState);
        }
    }

    public OrderSagaState getState() {
        return orderSagaState;
    }

    public void setState(OrderSagaState orderSagaState) {
        logger.info("State changed: {}", orderSagaState.getClass().getName());
        this.orderSagaState = orderSagaState;
    }

    OrderDto getOrderDto() {
        return orderDto;
    }
}