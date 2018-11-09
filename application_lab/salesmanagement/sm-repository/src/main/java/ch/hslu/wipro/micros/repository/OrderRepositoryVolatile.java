package ch.hslu.wipro.micros.repository;

import ch.hslu.wipro.micros.model.order.OrderBuilder;
import ch.hslu.wipro.micros.model.order.OrderDto;

import java.util.HashMap;
import java.util.Map;

public class OrderRepositoryVolatile implements OrderRepository {
    private Map<String, OrderDto> orderDtoMap = new HashMap<>();

    @Override
    public OrderDto get(String correlationId) {
        OrderDto defaultOrder = new OrderBuilder().build();

        return orderDtoMap.entrySet()
                .stream()
                .filter(o -> o.getKey().equals(correlationId))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(defaultOrder);
    }

    @Override
    public void set(String correlationId, OrderDto orderDto) {
        orderDtoMap.put(correlationId, orderDto);
    }
}