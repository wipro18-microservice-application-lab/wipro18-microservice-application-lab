package ch.hslu.wipro.micros.business.saga;

public class OrderCreateState implements OrderSagaState {

    @Override
    public void process(OrderSaga saga) {
        saga.setState(new OrderCreateEligibleState());
        saga.process();
    }
}