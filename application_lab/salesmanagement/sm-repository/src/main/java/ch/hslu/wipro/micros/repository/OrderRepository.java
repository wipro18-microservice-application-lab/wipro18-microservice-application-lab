package ch.hslu.wipro.micros.repository;

import ch.hslu.wipro.micros.model.order.OrderDto;
import ch.hslu.wipro.micros.model.order.OrderUpdateDto;
import ch.hslu.wipro.micros.model.order.OrderUpdateResultDto;

import java.util.List;

public interface OrderRepository {
    OrderDto get(Long orderId);

    long set(OrderDto orderDto);

    List<OrderDto> getAll();

    List<OrderDto> getAllByCustomerId(long customerId);

    OrderUpdateResultDto updateStatus(OrderUpdateDto updateOrderDto);
}
