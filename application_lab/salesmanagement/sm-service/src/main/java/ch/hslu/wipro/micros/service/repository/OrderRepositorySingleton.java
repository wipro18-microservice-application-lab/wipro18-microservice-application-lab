package ch.hslu.wipro.micros.service.repository;

import ch.hslu.wipro.micros.repository.OrderRepository;
import ch.hslu.wipro.micros.repository.OrderRepositoryVolatile;

public class OrderRepositorySingleton {
    private static OrderRepository orderRepository;

    private OrderRepositorySingleton() {}

    public static OrderRepository getOrderRepository(){
        if(orderRepository == null){
            orderRepository = new OrderRepositoryVolatile();
        }
        return orderRepository;
    }
}
