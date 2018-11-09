package ch.hslu.wipro.micros.repository;

import ch.hslu.wipro.micros.model.order.OrderDto;

public interface OrderRepository {
    OrderDto get(int id);
}
