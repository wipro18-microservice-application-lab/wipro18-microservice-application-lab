package ch.hslu.wipro.micros.service.repository;

import ch.hslu.wipro.micros.repository.OrderRepository;
import ch.hslu.wipro.micros.model.order.OrderDto;

import java.util.List;

public class OrderRepositoryService {
    private final OrderRepository orderRepository = OrderRepositorySingleton.getOrderRepository();

    public OrderDto getOrder(Long orderId) {
        return orderRepository.get(orderId);
    }

    public void setOrder(OrderDto orderDto) {
        orderRepository.set(orderDto);
    }

    public List<OrderDto> getAllOrders() {
        return orderRepository.getAll();
    }

    public List<OrderDto> getAllOrdersByCustomerId(long customerId) {
        return orderRepository.getAllByCustomerId(customerId);
    }
}