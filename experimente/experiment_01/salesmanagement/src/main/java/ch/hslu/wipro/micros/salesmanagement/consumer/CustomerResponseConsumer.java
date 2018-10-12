package ch.hslu.wipro.micros.salesmanagement.consumer;

import ch.hslu.wipro.micros.common.dto.CustomerDto;
import ch.hslu.wipro.micros.common.message.RequestOperation;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;

import static com.rabbitmq.client.AMQP.BasicProperties;

public class CustomerResponseConsumer extends DefaultConsumer {
    private static final Logger logger = LogManager.getLogger(CustomerResponseConsumer.class);

    public CustomerResponseConsumer(Channel channel) {
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope,
                               BasicProperties properties, byte[] body) {

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        RequestOperation<CustomerDto> requestOperation =
                gson.fromJson(new String(body, StandardCharsets.UTF_8),
                        new TypeToken<RequestOperation<CustomerDto>>() {
                        }.getType());

        if (requestOperation.isSuccessful()) {
            printSuccessfulDelivery(requestOperation, envelope);
        } else {
            printFailedDelivery(requestOperation, envelope);
        }
    }

    private void printSuccessfulDelivery(RequestOperation<CustomerDto> requestOperation, Envelope envelope) {
        String deliveryInfo = String.format("exchange: %s, deliveryTag: %s, customer name: %s %s",
                envelope.getExchange(),
                envelope.getDeliveryTag(),
                requestOperation.getDto().getFirstName(),
                requestOperation.getDto().getLastName());

        logger.info(deliveryInfo);
    }

    private void printFailedDelivery(RequestOperation<CustomerDto> requestOperation, Envelope envelope) {
        String deliveryInfo = String.format("exchange: %s, deliveryTag: %s, no customer with %d id found.",
                envelope.getExchange(),
                envelope.getDeliveryTag(),
                requestOperation.getRequestedId());

        logger.warn(deliveryInfo);
    }
}
