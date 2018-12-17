package ch.hslu.wipro.micros.business.rabbitmq.consumer;

import ch.hslu.wipro.micros.business.converter.JsonConverter;
import ch.hslu.wipro.micros.business.converter.JsonConverterFactory;
import ch.hslu.wipro.micros.model.order.OrderUpdateDto;
import ch.hslu.wipro.micros.model.order.OrderUpdateResultDto;
import ch.hslu.wipro.micros.service.repository.OrderRepositoryService;
import com.google.gson.JsonSyntaxException;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.rabbitmq.client.AMQP.BasicProperties;

public class OrderUpdateStatusCommandConsumer extends DefaultConsumer {
    private static final Logger logger = LogManager.getLogger(OrderUpdateStatusCommandConsumer.class);

    public OrderUpdateStatusCommandConsumer(Channel channel) {
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope,
                               BasicProperties properties, byte[] body) throws IOException {

        logger.info("handle incoming OrderUpdateStatusCommand with correlation id: {}", properties.getCorrelationId());
        String replyRoutingKey = "";

        JsonConverter<OrderUpdateDto> jsonConverter = new JsonConverterFactory<OrderUpdateDto>().get();

        OrderUpdateDto updateOrderDto;

        if (properties.getReplyTo() == null) {
            super.getChannel().basicReject(envelope.getDeliveryTag(), false);
            logger.warn("missing routing key. sent to dead letter exchange.");
            return;
        }

        try {
            updateOrderDto = jsonConverter.fromJson(body, OrderUpdateDto.class);
        } catch (JsonSyntaxException e) {
            ConsumerUtil.unknownRequest(super.getChannel(), envelope.getDeliveryTag(), properties);
            return;
        }

        OrderRepositoryService repositoryService = new OrderRepositoryService();
        OrderUpdateResultDto orderUpdateResultDto = repositoryService.updateStatus(updateOrderDto);

        String updateResult = new JsonConverterFactory<OrderUpdateResultDto>().get().toJson(orderUpdateResultDto);

        AMQP.BasicProperties replyProperties = new AMQP.BasicProperties
                .Builder()
                .correlationId(properties.getCorrelationId())
                .build();

        super.getChannel().basicPublish(
                replyRoutingKey,
                properties.getReplyTo(),
                replyProperties,
                updateResult.getBytes(StandardCharsets.UTF_8));

        boolean noAcknowledgeAll = false;
        super.getChannel().basicAck(envelope.getDeliveryTag(), noAcknowledgeAll);
    }
}