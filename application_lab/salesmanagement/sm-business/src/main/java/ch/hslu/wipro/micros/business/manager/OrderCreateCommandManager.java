package ch.hslu.wipro.micros.business.manager;

import ch.hslu.wipro.micros.business.consumer.OrderCreateCommandConsumer;
import ch.hslu.wipro.micros.service.ChannelBuilder;
import ch.hslu.wipro.micros.service.config.ConfigService;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class OrderCreateCommandManager implements CommandManager {
    private final Channel channel;
    private ConfigService configService = new ConfigService();

    public OrderCreateCommandManager() throws IOException, TimeoutException {
        this.channel = new ChannelBuilder()
                .withHost(configService.getRabbitMqHost())
                .build();
    }

    @Override
    public CommandManager setup() throws IOException {
        channel.exchangeDeclare(configService.getOrderExchange(), BuiltinExchangeType.TOPIC);
        channel.queueDeclare(configService.getOrderCreateCommandQueue(), false, false, false, null);
        channel.queueBind(configService.getOrderCreateCommandQueue(),
                configService.getOrderExchange(),
                configService.getRoutingKeyFor(SalesCommands.CREATE));

        return this;
    }

    @Override
    public void handleIncomingCommands() throws IOException {
        boolean autoAck = false;

        channel.basicConsume(configService.getOrderCreateCommandQueue(),
                autoAck,
                new OrderCreateCommandConsumer(channel));
    }
}
