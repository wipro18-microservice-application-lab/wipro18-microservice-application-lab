package ch.hslu.wipro.micros.service.repository;

import ch.hslu.wipro.micros.model.order.OrderDto;
import ch.hslu.wipro.micros.repository.OrderRepository;

public class RepositoryService {
    private OrderRepository orderRepository;

    RepositoryService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderDto getOrder(String correlationId) {
        return orderRepository.get(correlationId);
    }

    public void setOrder(String correlationId, OrderDto orderDto) {
        orderRepository.set(correlationId, orderDto);
    }
}