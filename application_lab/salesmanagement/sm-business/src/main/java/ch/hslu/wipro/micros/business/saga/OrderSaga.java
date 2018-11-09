package ch.hslu.wipro.micros.business.saga;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class OrderSaga {
    private static final Logger logger = LogManager.getLogger(OrderSaga.class);
    private OrderSagaContext context;
    private OrderSagaState state;

    OrderSaga(OrderSagaContext context) {
        this.context = context;
    }

    void setState(OrderSagaState state) {
        OrderSagaState oldState = this.state;
        this.state = state;

        logger.info("state changed for {} from {} to {}",
                context.getCorrelationId(),
                oldState,
                state);
    }

    public void process() {
        try {
            state.process(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public OrderSagaContext getContext() {
        return context;
    }

    public OrderSagaState getState() {
        return state;
    }
}