package ch.hslu.wipro.micros.repository;

import ch.hslu.wipro.micros.model.order.OrderBuilder;
import ch.hslu.wipro.micros.model.order.OrderDto;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class OrderRepositoryVolatileTest {
    private OrderRepository orderRepository = new OrderRepositoryVolatile();
    private final String CORRELATION_ID = "order.repository.volatile.test";
    private final int CUSTOMER_ID = 42;

    @Before
    public void setUp() throws Exception {
        OrderDto order = new OrderBuilder()
                .atCustomer(CUSTOMER_ID)
                .build();

        orderRepository.set(CORRELATION_ID, order);
    }

    @Test
    public void get() {
        OrderDto retrievedOrder = orderRepository.get(CORRELATION_ID);
        assertEquals(CUSTOMER_ID, retrievedOrder.getCustomerId());
    }
}