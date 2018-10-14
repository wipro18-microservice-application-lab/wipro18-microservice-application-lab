package ch.hslu.wipro.micros.customermanagement;

import ch.hslu.wipro.micros.common.RabbitMqConstants;
import ch.hslu.wipro.micros.common.util.RabbitMqFunctions;
import ch.hslu.wipro.micros.customermanagement.consumer.CustomerRequestConsumer;
import ch.hslu.wipro.micros.customermanagement.repository.CustomerRepository;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMqManager implements Closeable {
    private CustomerRepository customerRepository;
    private Channel channel;

    RabbitMqManager(CustomerRepository customerRepository) throws IOException, TimeoutException {
        this.customerRepository = customerRepository;

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RabbitMqConstants.HOST_NAME);
        Connection connection = factory.newConnection();
        channel = connection.createChannel();
    }

    void listenForCustomerRequest() throws IOException {
        channel.basicConsume(RabbitMqConstants.CUSTOMER_REQUEST_QUEUE, false,
                new CustomerRequestConsumer(this, customerRepository, channel));
    }

    public void sendCustomerResponse(String jsonCustomer) throws IOException {
        RabbitMqFunctions rabbitMqFunctions = new RabbitMqFunctions(channel);
        rabbitMqFunctions.createAndBindQueueToExchange(RabbitMqConstants.CUSTOMER_REQUEST_QUEUE,
                RabbitMqConstants.CUSTOMER_REQUEST_EXCHANGE);

        AMQP.BasicProperties basicProperties = new AMQP.BasicProperties.Builder()
                .contentType(RabbitMqConstants.JSON_MIME_TYPE)
                .build();

        channel.basicPublish(RabbitMqConstants.CUSTOMER_RESPONSE_EXCHANGE,
                "", basicProperties, jsonCustomer.getBytes("UTF-8"));
    }

    public void sendAck(long deliveryTag) throws IOException {
        channel.basicAck(deliveryTag, false);
    }

    @Override
    public void close() throws IOException {
        channel.abort();
    }
}
