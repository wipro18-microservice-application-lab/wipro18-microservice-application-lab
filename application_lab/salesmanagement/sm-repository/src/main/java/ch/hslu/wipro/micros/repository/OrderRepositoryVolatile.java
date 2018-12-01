package ch.hslu.wipro.micros.repository;

import ch.hslu.wipro.micros.RepositoryOperation;
import ch.hslu.wipro.micros.entity.OrderEntity;
import ch.hslu.wipro.micros.entity.OrderEntityBuilder;
import ch.hslu.wipro.micros.model.order.OrderDtoBuilder;
import ch.hslu.wipro.micros.model.order.OrderDto;
import ch.hslu.wipro.micros.model.order.OrderUpdateDto;
import ch.hslu.wipro.micros.model.order.OrderUpdateResultDto;

import java.util.*;
import java.util.stream.Collectors;

public class OrderRepositoryVolatile implements OrderRepository {
    private final Map<Long, OrderEntity> orderEntityMap = new HashMap<>();

    @Override
    public OrderDto get(Long orderId) {
        OrderEntity defaultOrder = new OrderEntityBuilder().build();

        OrderEntity orderEntity = orderEntityMap.entrySet()
                .stream()
                .filter(o -> o.getKey().equals(orderId))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(defaultOrder);

        return convert(orderEntity);
    }

    @Override
    public List<OrderDto> getAll() {
        List<OrderEntity> orderEntities = new ArrayList<>(orderEntityMap.values());

        List<OrderDto> orderDtos = orderEntities.stream()
                .map(this::convert)
                .collect(Collectors.toList());

        return Collections.unmodifiableList(orderDtos);
    }

    @Override
    public List<OrderDto> getAllByCustomerId(long customerId) {
        List<OrderEntity> orderEntities = orderEntityMap.values()
                .stream()
                .filter(o -> o.getCustomerId() == customerId)
                .collect(Collectors.toList());

        List<OrderDto> orderDtos = orderEntities.stream()
                .map(this::convert)
                .collect(Collectors.toList());

        return Collections.unmodifiableList(orderDtos);
    }

    private OrderUpdateResultDto update(OrderDto orderDto) {
        OrderUpdateResultDto orderUpdateResultDto = new OrderUpdateResultDto();

        orderEntityMap.put(orderDto.getOrderId(), convert(orderDto));
        orderUpdateResultDto.setResult(RepositoryOperation.SUCCESSFUL);

        return orderUpdateResultDto;
    }

    @Override
    public OrderUpdateResultDto updateStatus(OrderUpdateDto updateOrderDto) {
        OrderDto orderDto = get(updateOrderDto.getOrderId());

        if (orderEntityMap.containsKey(updateOrderDto.getOrderId())) {
            orderDto.setStatus(updateOrderDto.getStatus());
        } else {
            OrderUpdateResultDto orderUpdateResultDto = new OrderUpdateResultDto();
            orderUpdateResultDto.setResult(RepositoryOperation.UNKNOWN_ORDER_ID);

            return orderUpdateResultDto;
        }

        return update(orderDto);
    }

    @Override
    public long set(OrderDto orderDto) {
        long orderId = orderEntityMap.size();

        OrderEntity orderEntity = convert(orderDto);
        orderEntity.setOrderId(orderId);

        orderEntityMap.put(orderId, orderEntity);

        return orderId;
    }

    private OrderDto convert(OrderEntity orderEntity) {
        return new OrderDtoBuilder()
                .atOrderId(orderEntity.getOrderId())
                .atCustomer(orderEntity.getCustomerId())
                .atTotalPrice(orderEntity.getTotalPrice())
                .mapAmountToArticle(orderEntity.getAmountToArticle())
                .atStatus(orderEntity.getStatus())
                .build();
    }

    private OrderEntity convert(OrderDto orderDto) {
        return new OrderEntityBuilder()
                .atOrderId(orderDto.getOrderId())
                .atCustomer(orderDto.getCustomerId())
                .atTotalPrice(orderDto.getTotalPrice())
                .mapAmountToArticle(orderDto.getAmountToArticle())
                .atStatus(orderDto.getStatus())
                .build();
    }
}