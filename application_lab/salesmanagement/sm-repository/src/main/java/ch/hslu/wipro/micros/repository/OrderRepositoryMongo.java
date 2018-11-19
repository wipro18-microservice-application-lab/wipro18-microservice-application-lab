package ch.hslu.wipro.micros.repository;

import ch.hslu.wipro.micros.model.order.OrderDto;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

public class OrderRepositoryMongo implements OrderRepository {

    @Override
    public OrderDto get(Long orderId) {
        throw new NotImplementedException();
    }

    @Override
    public List<OrderDto> getAll() {
        throw new NotImplementedException();
    }

    @Override
    public void set(OrderDto orderDto) {
        throw new NotImplementedException();
    }
}
