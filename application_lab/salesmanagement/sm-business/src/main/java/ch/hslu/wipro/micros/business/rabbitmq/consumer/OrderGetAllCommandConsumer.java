package ch.hslu.wipro.micros.business.rabbitmq.consumer;

import ch.hslu.wipro.micros.business.converter.JsonConverterFactory;
import ch.hslu.wipro.micros.model.order.OrderDto;
import ch.hslu.wipro.micros.service.repository.OrderRepositoryService;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.rabbitmq.client.AMQP.BasicProperties;

public class OrderGetAllCommandConsumer extends DefaultConsumer {
    private static final Logger logger = LogManager.getLogger(OrderGetAllCommandConsumer.class);

    public OrderGetAllCommandConsumer(Channel channel) {
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope,
                               BasicProperties properties, byte[] body) throws IOException {

        logger.info("handle incoming OrderGetAllCommand with correlation id: {}", properties.getCorrelationId());
        String replyRoutingKey = "";

        OrderRepositoryService repositoryService = new OrderRepositoryService();
        List<OrderDto> orderDtos = repositoryService.getAllOrders();

        String jsonOrders = new JsonConverterFactory<List<OrderDto>>().get().toJson(orderDtos);

        AMQP.BasicProperties replyProperties = new AMQP.BasicProperties
                .Builder()
                .correlationId(properties.getCorrelationId())
                .build();

        super.getChannel().basicPublish(
                replyRoutingKey,
                properties.getReplyTo(),
                replyProperties,
                jsonOrders.getBytes(StandardCharsets.UTF_8));

        boolean noAcknowledgeAll = false;
        super.getChannel().basicAck(envelope.getDeliveryTag(), noAcknowledgeAll);
    }
}