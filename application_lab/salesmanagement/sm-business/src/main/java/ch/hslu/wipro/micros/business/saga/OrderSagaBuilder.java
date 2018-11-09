package ch.hslu.wipro.micros.business.saga;

import ch.hslu.wipro.micros.model.order.OrderDto;

import java.util.UUID;

public class OrderSagaBuilder {
    private OrderSagaContext context;
    private OrderSagaState state;

    public OrderSagaBuilder() {
        this.context = new OrderSagaContext();
    }

    public OrderSagaBuilder withStartState(OrderSagaState state) {
        this.state = state;
        return this;
    }

    public OrderSagaBuilder atOrder(OrderDto order) {
        context.setOrder(order);
        return this;
    }

    public OrderSaga build() {
        context.setCorrelationId(UUID.randomUUID().toString());
        OrderSaga orderSaga = new OrderSaga(context);
        orderSaga.setState(state);

        return orderSaga;
    }
}
