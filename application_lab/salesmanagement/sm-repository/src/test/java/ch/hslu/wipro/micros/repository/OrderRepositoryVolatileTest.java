package ch.hslu.wipro.micros.repository;

import ch.hslu.wipro.micros.model.order.OrderBuilder;
import ch.hslu.wipro.micros.model.order.OrderDto;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class OrderRepositoryVolatileTest {
    private OrderRepository orderRepository = new OrderRepositoryVolatile();
    private final int CUSTOMER_ID = 42;

    @Before
    public void setUp() throws Exception {
        OrderDto order = new OrderBuilder()
                .atCustomer(CUSTOMER_ID)
                .build();

        orderRepository.set(order);
    }

    @Test
    public void getById() {
        OrderDto retrievedOrder = orderRepository.get(2L);
        assertEquals(CUSTOMER_ID, retrievedOrder.getCustomerId());
    }

    @Test
    public void getAll() {
        List<OrderDto> retrievedOrders = orderRepository.getAll();
        assertEquals(CUSTOMER_ID, retrievedOrders.get(0).getCustomerId());
    }
}