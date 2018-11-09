package ch.hslu.wipro.micros.service.repository;

import ch.hslu.wipro.micros.model.order.OrderDto;
import ch.hslu.wipro.micros.repository.*;

class RepositoryServiceMongo implements RepositoryService {
    private OrderRepository orderRepository = new OrderRepositoryMongo();

    @Override
    public OrderDto getOrder(int id) {
        return orderRepository.get(id);
    }
}
