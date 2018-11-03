package ch.hslu.wipro.micros.salesmanagement.saga;

public class OrderPaymentState implements OrderSagaState {

    @Override
    public void process(OrderSagaContext context) {
        System.out.println("YOU NEED TO PAY!");
    }
}
