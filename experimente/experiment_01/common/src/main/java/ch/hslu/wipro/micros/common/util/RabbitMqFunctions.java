package ch.hslu.wipro.micros.common.util;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

import java.io.IOException;

public class RabbitMqFunctions {
    private Channel channel;

    public RabbitMqFunctions(Channel channel) {
        this.channel = channel;
    }

    public void createAndBindQueueToExchange(
            String rabbitmqQueue,
            String rabbitmqExchange) throws IOException {

        channel.exchangeDeclare(rabbitmqExchange,
                BuiltinExchangeType.DIRECT);

        channel.queueDeclare(rabbitmqQueue,
                false, false, false, null);

        channel.queueBind(rabbitmqQueue,
                rabbitmqExchange, "");
    }
}