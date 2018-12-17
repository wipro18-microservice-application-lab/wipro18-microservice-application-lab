package ch.hslu.wipro.micros.business.rabbitmq.consumer;

import ch.hslu.wipro.micros.business.converter.JsonConverter;
import ch.hslu.wipro.micros.business.converter.JsonConverterFactory;
import ch.hslu.wipro.micros.business.result.OrderCreateCommandResult;
import ch.hslu.wipro.micros.business.result.OrderCreateCommandResultBuilder;
import ch.hslu.wipro.micros.business.saga.OrderCreateCheckCustomerState;
import ch.hslu.wipro.micros.business.saga.OrderSaga;
import ch.hslu.wipro.micros.model.article.ArticleCheckQuantityReplyDto;
import com.google.gson.JsonSyntaxException;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

public class ArticleCheckQuantityReplyConsumer extends DefaultConsumer {
    private static final Logger logger = LogManager.getLogger(ArticleCheckQuantityReplyConsumer.class);
    private static final String ENOUGH_ARTICLES = "enough articles in stock";
    private final OrderSaga saga;

    public ArticleCheckQuantityReplyConsumer(Channel channel, OrderSaga saga) {
        super(channel);
        this.saga = saga;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        logger.info("handle incoming ArticleCheckQuantitiesReply with correlation id: {}", properties.getCorrelationId());

        JsonConverter<ArticleCheckQuantityReplyDto> jsonConverter =
                new JsonConverterFactory<ArticleCheckQuantityReplyDto>().get();

        ArticleCheckQuantityReplyDto articleCheckQuantityReplyDto;

        if (properties.getReplyTo() == null) {
            super.getChannel().basicReject(envelope.getDeliveryTag(), false);
            logger.warn("missing routing key. sent to dead letter exchange.");
            return;
        }

        try {
            articleCheckQuantityReplyDto = jsonConverter.fromJson(body, ArticleCheckQuantityReplyDto.class);
        } catch (JsonSyntaxException e) {
            ConsumerUtil.unknownRequest(super.getChannel(), envelope.getDeliveryTag(), properties);
            return;
        }

        boolean enoughArticlesInStock = Stream.of(articleCheckQuantityReplyDto.getResult().split(", "))
                .allMatch(r -> r.equals(ENOUGH_ARTICLES));

        if (enoughArticlesInStock) {
            saga.setState(new OrderCreateCheckCustomerState());
            saga.process();
        } else {
            logger.info("order failed: {}", articleCheckQuantityReplyDto.getResult());

            Channel channel = saga.getContext().getChannel();
            String correlationId = saga.getContext().getCommand().getCorrelationId();
            String replyToQueue = saga.getContext().getCommand().getReplyTo();
            String replyRoutingKey = "";

            OrderCreateCommandResult orderCreateCommandResult = new OrderCreateCommandResultBuilder()
                    .withOrder(saga.getContext().getCommand().getPayload())
                    .withResult(articleCheckQuantityReplyDto.getResult())
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
