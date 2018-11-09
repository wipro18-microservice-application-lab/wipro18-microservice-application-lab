package ch.hslu.wipro.micros.business.saga;

import ch.hslu.wipro.micros.model.order.OrderDto;

public class OrderSagaContext {
    private OrderDto order;
    private String correlationId;

    void setOrder(OrderDto order) {
        this.order = order;
    }

    void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public OrderDto getOrder() {
        return order;
    }

    public String getCorrelationId() {
        return correlationId;
    }
}