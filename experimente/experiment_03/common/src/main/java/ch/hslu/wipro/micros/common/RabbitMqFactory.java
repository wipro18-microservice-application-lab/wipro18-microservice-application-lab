package ch.hslu.wipro.micros.common;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

public class RabbitMqFactory {

    public static Optional<Channel> createChannel() {

        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(RabbitMqConstants.HOST_NAME);
            Connection connection = factory.newConnection();

            return Optional.of(connection.createChannel());
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
}