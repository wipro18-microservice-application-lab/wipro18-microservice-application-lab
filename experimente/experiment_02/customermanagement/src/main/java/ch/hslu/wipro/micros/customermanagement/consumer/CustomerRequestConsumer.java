package ch.hslu.wipro.micros.customermanagement.consumer;

import ch.hslu.wipro.micros.common.dto.CustomerDto;
import ch.hslu.wipro.micros.common.message.RequestOperation;
import ch.hslu.wipro.micros.customermanagement.RabbitMqManager;
import ch.hslu.wipro.micros.customermanagement.repository.CustomerOperation;
import ch.hslu.wipro.micros.customermanagement.repository.CustomerRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class CustomerRequestConsumer extends DefaultConsumer {
    private static final Logger logger = LogManager.getLogger(CustomerRequestConsumer.class);
    private RabbitMqManager rabbitMqManager;
    private CustomerRepository customerRepository;

    public CustomerRequestConsumer(RabbitMqManager rabbitMqManager,
                                   CustomerRepository customerRepository,
                                   Channel channel) {
        super(channel);
        this.rabbitMqManager = rabbitMqManager;
        this.customerRepository = customerRepository;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope,
                               AMQP.BasicProperties properties, byte[] body) throws IOException {

        String messageUtf8 = new String(body, StandardCharsets.UTF_8);

        try {
            long customerId = Long.parseLong(messageUtf8);
            printSuccessfulDelivery(customerId, envelope);

            CustomerOperation op = customerRepository.getCustomerById(customerId);
            RequestOperation<CustomerDto> requestOperation;

            if (op.isSuccess()) {
                requestOperation = new RequestOperation<>(true, customerId, op.getCustomer());
            } else {
                requestOperation = new RequestOperation<>(false, customerId, null);
            }

            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();

            String jsonRequestOperation = gson.toJson(requestOperation);
            rabbitMqManager.sendCustomerResponse(jsonRequestOperation);
            rabbitMqManager.sendAck(envelope.getDeliveryTag());
        } catch (NumberFormatException e) {
            String error = String.format("wrong customer id format. expected long, received %s", messageUtf8);
            logger.error(error);
        }


        System.out.println(new String(body, StandardCharsets.UTF_8));
    }

    private void printSuccessfulDelivery(long customerRequestId, Envelope envelope) {
        String deliveryInformation = String.format("exchange: %s, deliveryTag: %s, customer request: %d",
                envelope.getExchange(), envelope.getDeliveryTag(), customerRequestId);

        logger.info(deliveryInformation);
    }
}
