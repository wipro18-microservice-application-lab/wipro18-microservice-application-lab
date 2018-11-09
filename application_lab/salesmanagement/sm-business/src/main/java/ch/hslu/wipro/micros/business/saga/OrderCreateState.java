package ch.hslu.wipro.micros.business.saga;

import java.io.IOException;

public class OrderCreateState implements OrderSagaState {

    @Override
    public void process(OrderSaga saga) throws IOException {
        saga.setState(new OrderEligibleState());
        saga.process();
    }
}