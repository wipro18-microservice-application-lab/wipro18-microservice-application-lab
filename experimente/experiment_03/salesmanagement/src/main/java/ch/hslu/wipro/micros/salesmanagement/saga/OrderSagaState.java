package ch.hslu.wipro.micros.salesmanagement.saga;

import java.io.IOException;

public interface OrderSagaState {
    void process(OrderSagaContext context) throws IOException;
}
