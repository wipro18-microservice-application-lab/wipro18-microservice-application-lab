package ch.hslu.wipro.micros.salesmanagement.saga;

public interface OrderSagaState {
    void process(OrderSagaContext context);
}
