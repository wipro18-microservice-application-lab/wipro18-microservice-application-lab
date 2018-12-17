package ch.hslu.wipro.micros.business.rabbitmq.consumer;

import ch.hslu.wipro.micros.business.converter.JsonConverterFactory;
import ch.hslu.wipro.micros.business.result.OrderCreateCommandResult;
import ch.hslu.wipro.micros.business.result.OrderCreateCommandResultBuilder;
import ch.hslu.wipro.micros.business.saga.OrderCreatePersistState;
import ch.hslu.wipro.micros.business.saga.OrderReduceArticleState;
import ch.hslu.wipro.micros.business.saga.OrderSaga;
import ch.hslu.wipro.micros.model.article.ArticleReduceReplyDto;
import ch.hslu.wipro.micros.model.customer.CustomerDto;
import com.google.gson.JsonSyntaxException;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class CustomerCheckReplyConsumer extends DefaultConsumer {
    private static final Logger logger = LogManager.getLogger(CustomerCheckReplyConsumer.class);
    private static final String UNKNOWN_CUSTOMER = "UNKNOWN CUSTOMER";
    private final OrderSaga saga;

    public CustomerCheckReplyConsumer(Channel channel, OrderSaga saga) {
        super(channel);
        this.saga = saga;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        logger.info("handle incoming CustomerCheckReplyConsumer with correlation id: {}", properties.getCorrelationId());

        CustomerDto customerDto;

        try {
            customerDto = new JsonConverterFactory<CustomerDto>().get().fromJson(body, CustomerDto.class);
        } catch (JsonSyntaxException e) {
            ConsumerUtil.unknownRequest(super.getChannel(), envelope.getDeliveryTag(), properties);
            return;
        }

        if (customerDto.getFullName() != null) {
            saga.setState(new OrderReduceArticleState());
            saga.process();
        } else {
            logger.info("order failed: {} does not exist",
                    saga.getContext().getCommand().getPayload().getCustomerId());

            Channel channel = saga.getContext().getChannel();
            String correlationId = saga.getContext().getCommand().getCorrelationId();
            String replyToQueue = saga.getContext().getCommand().getReplyTo();
            String replyRoutingKey = "";

            OrderCreateCommandResult orderCreateCommandResult = new OrderCreateCommandResultBuilder()
                    .withOrder(saga.getContext().getCommand().getPayload())
                    .withResult(UNKNOWN_CUSTOMER)
                    .build();

            String operationResult = new JsonConverterFactory<OrderCreateCommandResult>()
                    .get()
                    .toJson(orderCreateCommandResult);

            AMQP.BasicProperties replyProperties = new AMQP.BasicProperties
                    .Builder()
                    .correlationId(correlationId)
                    .build();

            channel.basicPublish(
                    replyRoutingKey,
                    replyToQueue,
                    replyProperties,
                    operationResult.getBytes(StandardCharsets.UTF_8));

            boolean acknowledgeAll = false;
            channel.basicAck(saga.getContext().getCommand().getDeliveryTag(), acknowledgeAll);
        }
    }
}
