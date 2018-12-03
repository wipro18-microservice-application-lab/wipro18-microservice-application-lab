package ch.hslu.wipro.micros.business.rabbitmq.consumer;

import ch.hslu.wipro.micros.business.converter.JsonConverter;
import ch.hslu.wipro.micros.business.converter.JsonConverterFactory;
import ch.hslu.wipro.micros.business.result.OrderCreateCommandResult;
import ch.hslu.wipro.micros.business.result.OrderCreateCommandResultBuilder;
import ch.hslu.wipro.micros.business.saga.OrderCreateCheckCustomerState;
import ch.hslu.wipro.micros.business.saga.OrderCreatePersistState;
import ch.hslu.wipro.micros.business.saga.OrderSaga;
import ch.hslu.wipro.micros.model.article.ArticleCheckQuantityReplyDto;
import ch.hslu.wipro.micros.model.article.ArticleReduceReplyDto;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

public class ArticleReduceReplyConsumer extends DefaultConsumer {
    private static final Logger logger = LogManager.getLogger(ArticleReduceReplyConsumer.class);
    private static final String ENOUGH_ARTICLES = "enough articles in stock";
    private final OrderSaga saga;

    public ArticleReduceReplyConsumer(Channel channel, OrderSaga saga) {
        super(channel);
        this.saga = saga;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        logger.info("handle incoming ArticleReduceReplyCommand with correlation id: {}", properties.getCorrelationId());

        JsonConverter<ArticleReduceReplyDto> jsonConverter =
                new JsonConverterFactory<ArticleReduceReplyDto>().get();

        ArticleReduceReplyDto articleReduceReplyDto
                = jsonConverter.fromJson(body, ArticleReduceReplyDto.class);

        logger.info("reduce articles: ", articleReduceReplyDto.getResult());

        saga.setState(new OrderCreatePersistState());
        saga.process();
    }
}
