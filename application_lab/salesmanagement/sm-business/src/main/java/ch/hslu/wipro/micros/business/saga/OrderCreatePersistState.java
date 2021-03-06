package ch.hslu.wipro.micros.business.saga;

import ch.hslu.wipro.micros.model.order.OrderDto;
import ch.hslu.wipro.micros.service.repository.OrderRepositoryService;

public class OrderCreatePersistState implements OrderSagaState {

    @Override
    public void process(OrderSaga saga) {
        OrderDto order = saga.getContext().getCommand().getPayload();

        OrderRepositoryService repositoryService = new OrderRepositoryService();
        long orderId = repositoryService.setOrder(order);

        order.setOrderId(orderId);

        saga.setState(new OrderCreateCompleteState());
        saga.process();
    }
}