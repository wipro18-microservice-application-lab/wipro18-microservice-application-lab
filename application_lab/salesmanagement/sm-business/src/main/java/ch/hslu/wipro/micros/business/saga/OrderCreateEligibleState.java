package ch.hslu.wipro.micros.business.saga;

import java.io.IOException;

public class OrderCreateEligibleState implements OrderSagaState {

    @Override
    public void process(OrderSaga saga) throws IOException {
        saga.setState(new OrderCreatePersistState());
        saga.process();
    }
}
