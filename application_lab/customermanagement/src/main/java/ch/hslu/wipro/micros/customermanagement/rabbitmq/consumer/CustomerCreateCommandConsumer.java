package ch.hslu.wipro.micros.customermanagement.rabbitmq.consumer;

import ch.hslu.wipro.micros.customermanagement.converter.JsonConverterFactory;
import ch.hslu.wipro.micros.customermanagement.dto.CustomerCreateDto;
import ch.hslu.wipro.micros.customermanagement.dto.CustomerCreateResultDto;
import ch.hslu.wipro.micros.customermanagement.dto.CustomerDto;
import ch.hslu.wipro.micros.customermanagement.dto.CustomerDtoBuilder;
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

public class CustomerCreateCommandConsumer extends DefaultConsumer {
    private static final Logger logger = LogManager.getLogger(CustomerCreateCommandConsumer.class);

    public CustomerCreateCommandConsumer(Channel channel) {
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope,
                               AMQP.BasicProperties properties, byte[] body) throws IOException {

        logger.info("handle incoming CustomerCreateCommandConsumer with correlation id: {}",
                properties.getCorrelationId());

        CustomerCreateDto customerCreateDto = new JsonConverterFactory<CustomerCreateDto>()
                .get().fromJson(body, CustomerCreateDto.class);

        CustomerDto customerDto = new CustomerDtoBuilder()
                .atFullName(customerCreateDto.getFullName())
                .atAddress(customerCreateDto.getAddress())
                .atEmail(customerCreateDto.getEmail())
                .build();

        CustomerRepository customerRepository = CustomerRepositorySingleton.getCustomerRepository();
        customerRepository.create(customerDto);

        CustomerCreateResultDto customerCreateResultDto = new CustomerCreateResultDto();
        customerCreateResultDto.setCustomerDto(customerDto);
        customerCreateResultDto.setResult("SUCCESSFUL");

        String customerResultJson = new JsonConverterFactory<CustomerCreateResultDto>()
                .get().toJson(customerCreateResultDto);

        AMQP.BasicProperties replyProperties = new AMQP.BasicProperties
                .Builder()
                .correlationId(properties.getCorrelationId())
                .build();

        String replyRoutingKey = "";
        super.getChannel().basicPublish(
                replyRoutingKey,
                properties.getReplyTo(),
                replyProperties,
                customerResultJson.getBytes(StandardCharsets.UTF_8));

        boolean noAcknowledgeAll = false;
        super.getChannel().basicAck(envelope.getDeliveryTag(), noAcknowledgeAll);
    }
}