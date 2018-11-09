package ch.hslu.wipro.micros.repository;

import ch.hslu.wipro.micros.model.order.OrderDto;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class OrderRepositoryMongo implements OrderRepository {

    @Override
    public OrderDto get(String correlationId) {
        throw new NotImplementedException();
    }

    @Override
    public void set(String correlationId, OrderDto orderDto) {
        throw new NotImplementedException();
    }
}
