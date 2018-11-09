package ch.hslu.wipro.micros.repository;

import ch.hslu.wipro.micros.model.order.OrderDto;

public interface OrderRepository {
    OrderDto get(String correlationId);
    void set(String correlationId, OrderDto orderDto);
}
