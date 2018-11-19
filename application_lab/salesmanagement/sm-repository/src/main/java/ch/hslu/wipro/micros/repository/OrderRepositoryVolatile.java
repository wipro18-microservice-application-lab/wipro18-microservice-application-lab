package ch.hslu.wipro.micros.repository;

import ch.hslu.wipro.micros.model.order.OrderBuilder;
import ch.hslu.wipro.micros.model.order.OrderDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderRepositoryVolatile implements OrderRepository {
    private Map<Long, OrderDto> orderDtoMap = new HashMap<>();

    public OrderRepositoryVolatile() {
        Map<Integer, Integer> amountToArticle = new HashMap<>();
        amountToArticle.put(1, 1);
        amountToArticle.put(2, 2);

        OrderDto orderDtoDummy = new OrderBuilder()
                .atCustomer(42L)
                .mapAmountToArticle(amountToArticle)
                .atTotalPrice(BigDecimal.valueOf(15.5))
                .build();

        set(orderDtoDummy);
    }

    @Override
    public OrderDto get(Long orderId) {
        OrderDto defaultOrder = new OrderBuilder().build();

        return orderDtoMap.entrySet()
                .stream()
                .filter(o -> o.getKey().equals(orderId))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(defaultOrder);
    }

    @Override
    public List<OrderDto> getAll() {
        return new ArrayList<>(orderDtoMap.values());
    }

    @Override
    public void set(OrderDto orderDto) {
        long orderId = orderDtoMap.size() + 1L;

        orderDto.setOrderId(orderId);
        orderDtoMap.put(orderId, orderDto);
    }
}