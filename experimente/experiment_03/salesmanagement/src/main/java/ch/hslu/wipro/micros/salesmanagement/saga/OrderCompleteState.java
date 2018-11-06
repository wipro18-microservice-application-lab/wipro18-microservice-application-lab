package ch.hslu.wipro.micros.salesmanagement.saga;

import ch.hslu.wipro.micros.common.RabbitMqChannelBuilder;
import ch.hslu.wipro.micros.common.RabbitMqConstants;
import ch.hslu.wipro.micros.common.RoutingKey;
import ch.hslu.wipro.micros.common.event.EventFactory;
import ch.hslu.wipro.micros.salesmanagement.config.ConfigConsts;
import ch.hslu.wipro.micros.salesmanagement.config.ConfigUtils;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class OrderCompleteState implements OrderSagaState {
    private final Channel channel;
    private ConfigUtils configUtils = new ConfigUtils(ConfigConsts.CONFIG_FILE);

    public OrderCompleteState() throws IOException, TimeoutException {
        channel = new RabbitMqChannelBuilder()
                .withHost(RabbitMqConstants.HOST_NAME)
                .build();
    }

    @Override
    public void process(OrderSagaContext context) throws IOException {
        String jsonOrderCompleteEvent = EventFactory.getOrderCompleteAsJson();

        channel.basicPublish(
                configUtils.getExchange(),
                RoutingKey.EVENT,
                RabbitMqConstants.JSON_PROPERTIES,
                jsonOrderCompleteEvent.getBytes(RabbitMqConstants.DEFAULT_CHAR_SET));
    }
}