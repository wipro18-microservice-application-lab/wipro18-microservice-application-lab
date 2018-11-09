package ch.hslu.wipro.micros.business.manager;

import ch.hslu.wipro.micros.business.consumer.OrderConsumer;
import ch.hslu.wipro.micros.service.ChannelBuilder;
import ch.hslu.wipro.micros.service.config.ConfigService;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class OrderManager {
    private ConfigService configService = new ConfigService();
    private final Channel channel;

    public OrderManager() throws IOException, TimeoutException {
        this.channel = new ChannelBuilder()
                .withHost(configService.getRabbitMqHost())
                .build();
    }

    public OrderManager setup() throws IOException {
        channel.exchangeDeclare(configService.getOrderExchange(), BuiltinExchangeType.TOPIC);
        channel.queueDeclare(configService.getOrderCommandQueue(), false, false, false, null);
        channel.queueBind(configService.getOrderCommandQueue(),
                configService.getOrderExchange(),
                configService.getRoutingKey());

        return this;
    }

    public void handleIncomingOrders() throws IOException {
        channel.basicConsume(configService.getOrderCommandQueue(),
                false,
                new OrderConsumer(channel));
    }
}
