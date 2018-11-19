package ch.hslu.wipro.micros.business.saga;

import ch.hslu.wipro.micros.model.order.OrderDto;
import ch.hslu.wipro.micros.service.repository.RepositoryFactory;
import ch.hslu.wipro.micros.service.repository.RepositoryService;

import java.io.IOException;

public class OrderCreatePersistState implements OrderSagaState {

    @Override
    public void process(OrderSaga saga) throws IOException {
        String correlationId = saga.getContext().getCorrelationId();
        OrderDto order = saga.getContext().getOrder();

        RepositoryService repositoryService = RepositoryFactory.getRepository();
        repositoryService.setOrder(order);

        saga.setState(new OrderCreateCompleteState());
        saga.process();
    }
}