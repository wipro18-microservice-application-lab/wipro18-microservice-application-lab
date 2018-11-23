package ch.hslu.wipro.micros.repository;

import ch.hslu.wipro.micros.model.order.OrderDto;

import java.util.List;

public interface OrderRepository {
    OrderDto get(Long orderId);

    void set(OrderDto orderDto);

    List<OrderDto> getAll();

    List<OrderDto> getAllByCustomerId(long customerId);
}
