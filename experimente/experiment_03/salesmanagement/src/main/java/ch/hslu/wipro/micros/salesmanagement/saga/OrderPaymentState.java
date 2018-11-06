package ch.hslu.wipro.micros.salesmanagement.saga;

import ch.hslu.wipro.micros.salesmanagement.SalesManagementApp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class OrderPaymentState implements OrderSagaState {
    private static final Logger logger = LogManager.getLogger(SalesManagementApp.class);

    @Override
    public void process(OrderSagaContext context) {

        try {
            logger.info("processing incoming payment");
            Thread.sleep(1000);
            logger.info("payment accepted");
            context.setState(new OrderCompleteState());
            context.process();
        } catch (InterruptedException | TimeoutException | IOException e) {
            e.printStackTrace();
        }
    }
}
