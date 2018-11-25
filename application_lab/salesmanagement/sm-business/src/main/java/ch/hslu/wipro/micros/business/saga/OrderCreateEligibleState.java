package ch.hslu.wipro.micros.business.saga;

public class OrderCreateEligibleState implements OrderSagaState {

    @Override
    public void process(OrderSaga saga) {
        /** ToDo: Chat with Warehousemanagement */

        saga.setState(new OrderCreatePersistState());
        saga.process();
    }
}
