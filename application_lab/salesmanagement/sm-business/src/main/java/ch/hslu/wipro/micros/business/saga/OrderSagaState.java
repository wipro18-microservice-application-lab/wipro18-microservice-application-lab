package ch.hslu.wipro.micros.business.saga;

import java.io.IOException;

public interface OrderSagaState {
    void process(OrderSaga saga) throws IOException;
}
