package ch.hslu.wipro.micros.warehousemanagement.rabbitmq;

import com.nurkiewicz.asyncretry.AsyncRetryExecutor;
import com.nurkiewicz.asyncretry.RetryExecutor;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class RabbitMqConnector {

    private RabbitMqConnector() {}

    public static RetryExecutor getExecutor() {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

        return new AsyncRetryExecutor(scheduler)
                .retryOn(IOException.class)
                .withFixedBackoff(5000)
                .withMaxRetries(10);
    }
}
