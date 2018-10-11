package ch.hslu.wipro.micros.customermanagement;

import ch.hslu.wipro.micros.common.RabbitMqConstants;
import ch.hslu.wipro.micros.customermanagement.consumer.CustomerRequestConsumer;
import ch.hslu.wipro.micros.customermanagement.repository.CustomerRepository;
import com.rabbitmq.client.*;

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
        channel.basicConsume(RabbitMqConstants.CUSTOMER_REQUEST_QUEUE, true,
                new CustomerRequestConsumer(this, customerRepository, channel));
    }

    public void sendCustomerResponse(String jsonCustomer) throws IOException {
        channel.exchangeDeclare(RabbitMqConstants.CUSTOMER_RESPONSE_EXCHANGE,
                BuiltinExchangeType.DIRECT);

        channel.queueDeclare(RabbitMqConstants.CUSTOMER_RESPONSE_QUEUE,
                false, false, false, null);

        channel.queueBind(RabbitMqConstants.CUSTOMER_RESPONSE_QUEUE,
                RabbitMqConstants.CUSTOMER_RESPONSE_EXCHANGE, "");

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
