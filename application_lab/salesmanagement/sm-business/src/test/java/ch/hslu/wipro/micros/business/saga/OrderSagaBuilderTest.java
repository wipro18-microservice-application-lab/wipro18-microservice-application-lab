package ch.hslu.wipro.micros.business.saga;

import ch.hslu.wipro.micros.model.order.OrderBuilder;
import ch.hslu.wipro.micros.model.order.OrderDto;
import com.rabbitmq.client.AMQP;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class OrderSagaBuilderTest {
    private OrderDto ORDER;
    private OrderSagaState ORDER_SAGA_STATE;
    private static final long DELIVERY_TAG = 0L;
    private OrderSaga orderSaga;

    @Before
    public void setUp() throws Exception {
        ORDER = new OrderBuilder().build();
        ORDER_SAGA_STATE = new OrderCreateState();
        orderSaga = new OrderSagaBuilder()
                .withProperties(new AMQP.BasicProperties())
                .atDeliveryTag(DELIVERY_TAG)
                .withStateSequence(ORDER_SAGA_STATE)
                .atOrder(ORDER)
                .build();
    }

    @Test
    public void testOrderSagaBuilderOrder() {
        assertEquals(ORDER, orderSaga.getContext().getOrder());
    }

    @Test
    public void testOrderSagaBuilderState() {
        assertEquals(ORDER_SAGA_STATE, orderSaga.getState());
    }
}