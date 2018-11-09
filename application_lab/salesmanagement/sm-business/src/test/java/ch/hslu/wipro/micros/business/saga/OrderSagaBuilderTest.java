package ch.hslu.wipro.micros.business.saga;

import ch.hslu.wipro.micros.model.order.OrderBuilder;
import ch.hslu.wipro.micros.model.order.OrderDto;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class OrderSagaBuilderTest {
    private OrderDto ORDER;
    private OrderSagaState ORDER_SAGA_STATE;

    @Before
    public void setUp() throws Exception {
        ORDER = new OrderBuilder().build();
        ORDER_SAGA_STATE = new OrderCreateState();
    }

    @Test
    public void testOrderSagaBuilderOrder() {
        OrderSaga orderSaga = new OrderSagaBuilder()
                .atOrder(ORDER)
                .build();

        assertEquals(ORDER, orderSaga.getContext().getOrder());
    }

    @Test
    public void testOrderSagaBuilderState() {
        OrderSaga orderSaga = new OrderSagaBuilder()
                .withStateSequence(ORDER_SAGA_STATE)
                .build();

        assertEquals(ORDER_SAGA_STATE, orderSaga.getState());
    }

    @Test
    public void testOrderSagaBuilderCorrelationId() {
        OrderSaga orderSaga = new OrderSagaBuilder()
                .atOrder(ORDER)
                .withStateSequence(ORDER_SAGA_STATE)
                .build();

        assertNotNull(orderSaga.getContext().getCorrelationId());
    }
}