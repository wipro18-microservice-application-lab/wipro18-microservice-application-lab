package ch.hslu.wipro.micros.repository;

import ch.hslu.wipro.micros.entity.OrderEntity;
import ch.hslu.wipro.micros.entity.OrderEntityBuilder;
import ch.hslu.wipro.micros.model.order.OrderDtoBuilder;
import ch.hslu.wipro.micros.model.order.OrderDto;

import java.util.*;
import java.util.stream.Collectors;

public class OrderRepositoryVolatile implements OrderRepository {
    private Map<Long, OrderEntity> orderDtoMap = new HashMap<>();

    @Override
    public OrderDto get(Long orderId) {
        OrderEntity defaultOrder = new OrderEntityBuilder().build();

        OrderEntity orderEntity = orderDtoMap.entrySet()
                .stream()
                .filter(o -> o.getKey().equals(orderId))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(defaultOrder);

        return convert(orderEntity);
    }

    @Override
    public List<OrderDto> getAll() {
        List<OrderEntity> orderEntities = new ArrayList<>(orderDtoMap.values());

        List<OrderDto> orderDtos = orderEntities.stream()
                .map(this::convert)
                .collect(Collectors.toList());

        return Collections.unmodifiableList(orderDtos);
    }

    @Override
    public List<OrderDto> getAllByCustomerId(long customerId) {
        List<OrderEntity> orderEntities = orderDtoMap.values()
                .stream()
                .filter(o -> o.getCustomerId() == customerId)
                .collect(Collectors.toList());

        List<OrderDto> orderDtos = orderEntities.stream()
                .map(this::convert)
                .collect(Collectors.toList());

        return Collections.unmodifiableList(orderDtos);
    }

    @Override
    public void set(OrderDto orderDto) {
        long orderId = orderDtoMap.size() + 1L;
        OrderEntity orderEntity = convert(orderDto);
        orderEntity.setOrderId(orderId);

        orderDtoMap.put(orderId, orderEntity);
    }

    private OrderDto convert(OrderEntity orderEntity) {
        return new OrderDtoBuilder()
                .atOrderId(orderEntity.getOrderId())
                .atCustomer(orderEntity.getCustomerId())
                .atTotalPrice(orderEntity.getTotalPrice())
                .mapAmountToArticle(orderEntity.getAmountToArticle())
                .build();
    }

    private OrderEntity convert(OrderDto orderDto) {
        return new OrderEntityBuilder()
                .atCustomer(orderDto.getCustomerId())
                .atTotalPrice(orderDto.getTotalPrice())
                .mapAmountToArticle(orderDto.getAmountToArticle())
                .build();
    }
}