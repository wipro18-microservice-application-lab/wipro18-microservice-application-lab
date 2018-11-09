package ch.hslu.wipro.micros.service.repository;

import ch.hslu.wipro.micros.model.order.OrderDto;

public interface RepositoryService {
    OrderDto getOrder(int id);
}
