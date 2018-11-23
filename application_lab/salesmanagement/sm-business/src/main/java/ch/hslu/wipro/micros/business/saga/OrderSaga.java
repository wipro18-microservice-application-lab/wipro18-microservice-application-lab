package ch.hslu.wipro.micros.business.saga;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class OrderSaga {
    private static final Logger logger = LogManager.getLogger(OrderSaga.class);
    private OrderSagaContext context;
    private OrderSagaState state;

    public OrderSaga(OrderSagaContext context, OrderSagaState state) {
        this.context = context;
        this.state = state;
    }

    void setState(OrderSagaState state) {
        OrderSagaState oldState = this.state;
        this.state = state;

        logger.info("state changed for {} from {} to {}",
                context.getCommand().getCorrelationId(),
                oldState,
                state);
    }

    public void process() {
        try {
            state.process(this);
        } catch (IOException e) {
            logger.error("lost connection to rabbitmq");
        }
    }

    OrderSagaContext getContext() {
        return context;
    }

    public OrderSagaState getState() {
        return state;
    }
}