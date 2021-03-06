package ch.hslu.wipro.micros.customermanagement.rabbitmq.consumer;

import ch.hslu.wipro.micros.customermanagement.converter.JsonConverterFactory;
import ch.hslu.wipro.micros.customermanagement.dto.CustomerDto;
import ch.hslu.wipro.micros.customermanagement.repository.CustomerRepository;
import ch.hslu.wipro.micros.customermanagement.repository.CustomerRepositorySingleton;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class CustomerGetAllCommandConsumer extends DefaultConsumer {
    private static final Logger logger = LogManager.getLogger(CustomerGetAllCommandConsumer.class);

    public CustomerGetAllCommandConsumer(Channel channel) {
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope,
                               AMQP.BasicProperties properties, byte[] body) throws IOException {

        logger.info("handle incoming CustomerCreateCommandConsumer with correlation id: {}",
                properties.getCorrelationId());

        CustomerRepository customerRepository = CustomerRepositorySingleton.getCustomerRepository();
        List<CustomerDto> customerDtos = customerRepository.getAll();

        String customerDtosJson = new JsonConverterFactory<List<CustomerDto>>()
                .get().toJson(customerDtos);

        AMQP.BasicProperties replyProperties = new AMQP.BasicProperties
                .Builder()
                .correlationId(properties.getCorrelationId())
                .build();

        String replyRoutingKey = "";
        super.getChannel().basicPublish(
                replyRoutingKey,
                properties.getReplyTo(),
                replyProperties,
                customerDtosJson.getBytes(StandardCharsets.UTF_8));

        boolean noAcknowledgeAll = false;
        super.getChannel().basicAck(envelope.getDeliveryTag(), noAcknowledgeAll);
    }
}