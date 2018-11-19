package ch.hslu.wipro.micros.service.repository;

import ch.hslu.wipro.micros.model.order.OrderDto;
import ch.hslu.wipro.micros.repository.OrderRepository;

import java.util.List;

public class RepositoryService {
    private OrderRepository orderRepository;

    RepositoryService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderDto getOrder(Long orderId) {
        return orderRepository.get(orderId);
    }

    public void setOrder(OrderDto orderDto) {
        orderRepository.set(orderDto);
    }

    public List<OrderDto> getAllOrders() {
        return orderRepository.getAll();
    }
}