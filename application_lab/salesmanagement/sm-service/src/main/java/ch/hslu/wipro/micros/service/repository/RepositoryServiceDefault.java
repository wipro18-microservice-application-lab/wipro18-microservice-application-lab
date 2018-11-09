package ch.hslu.wipro.micros.service.repository;

import ch.hslu.wipro.micros.model.order.OrderDto;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

class RepositoryServiceDefault implements RepositoryService {

    @Override
    public OrderDto getOrder(int id) {
        throw new NotImplementedException();
    }
}