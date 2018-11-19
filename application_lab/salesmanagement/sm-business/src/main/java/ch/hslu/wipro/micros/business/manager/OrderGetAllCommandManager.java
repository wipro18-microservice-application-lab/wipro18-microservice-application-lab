package ch.hslu.wipro.micros.business.manager;

import ch.hslu.wipro.micros.business.consumer.OrderCreateCommandConsumer;
import ch.hslu.wipro.micros.business.consumer.OrderGetAllCommandConsumer;
import ch.hslu.wipro.micros.service.ChannelBuilder;
import ch.hslu.wipro.micros.service.config.ConfigService;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class OrderGetAllCommandManager implements CommandManager {
    private final Channel channel;
    private ConfigService configService = new ConfigService();

    public OrderGetAllCommandManager() throws IOException, TimeoutException {
        this.channel = new ChannelBuilder()
                .withHost(configService.getRabbitMqHost())
                .build();
    }

    @Override
    public CommandManager setup() throws IOException {
        channel.exchangeDeclare(configService.getOrderExchange(), BuiltinExchangeType.TOPIC);
        channel.queueDeclare(configService.getOrderGetAllCommandQueue(), false, false, false, null);
        channel.queueBind(configService.getOrderGetAllCommandQueue(),
                configService.getOrderExchange(),
                configService.getRoutingKeyFor(SalesCommands.GET_ALL));

        return this;
    }

    @Override
    public void handleIncomingCommands() throws IOException {
        boolean autoAck = false;

        channel.basicConsume(configService.getOrderGetAllCommandQueue(),
                autoAck,
                new OrderGetAllCommandConsumer(channel));
    }
}
