package ch.hslu.wipro.micros.repository;

import ch.hslu.wipro.micros.model.order.OrderBuilder;
import ch.hslu.wipro.micros.model.order.OrderDto;

import java.util.HashMap;
import java.util.Map;

public class OrderRepositoryVolatile implements OrderRepository {
    private Map<Integer, OrderDto> orderDtoMap = new HashMap<>();

    @Override
    public OrderDto get(int id) {
        OrderDto defaultOrder = new OrderBuilder().build();

        return orderDtoMap.entrySet()
                .stream()
                .filter(o -> o.getKey() == id)
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(defaultOrder);
    }
}
