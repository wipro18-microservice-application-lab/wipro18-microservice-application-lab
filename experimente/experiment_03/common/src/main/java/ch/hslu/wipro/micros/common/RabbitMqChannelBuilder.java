package ch.hslu.wipro.micros.common;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMqChannelBuilder {
    private String host;

    public RabbitMqChannelBuilder withHost(String host) {
        this.host = host;
        return this;
    }

    public Channel build() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        Connection connection = factory.newConnection();
        return connection.createChannel();
    }
}
