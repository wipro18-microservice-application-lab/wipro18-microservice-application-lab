package ch.hslu.wipro.micros.business.saga;

import java.io.IOException;

public class OrderEligibleState implements OrderSagaState {

    @Override
    public void process(OrderSaga saga) throws IOException {
        saga.setState(new OrderPersistState());
        saga.process();
    }
}
