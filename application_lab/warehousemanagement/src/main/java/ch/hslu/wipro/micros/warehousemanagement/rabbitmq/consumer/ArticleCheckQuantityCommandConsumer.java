package ch.hslu.wipro.micros.warehousemanagement.rabbitmq.consumer;

import ch.hslu.wipro.micros.warehousemanagement.converter.JsonConverterFactory;
import ch.hslu.wipro.micros.warehousemanagement.dto.ArticleCheckQuantityDto;
import ch.hslu.wipro.micros.warehousemanagement.dto.ArticleCheckQuantityResultDto;
import ch.hslu.wipro.micros.warehousemanagement.repository.ArticleRepository;
import ch.hslu.wipro.micros.warehousemanagement.repository.ArticleRepositorySingleton;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ArticleCheckQuantityCommandConsumer extends DefaultConsumer {
    private static final Logger logger = LogManager.getLogger(ArticleCheckQuantityCommandConsumer.class);

    public ArticleCheckQuantityCommandConsumer(Channel channel) {
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope,
                               AMQP.BasicProperties properties, byte[] body) throws IOException {

        logger.info("handle incoming ArticleCheckQuantityCommand with correlation id: {}", properties.getCorrelationId());

        ArticleCheckQuantityDto articleCheckQuantityDto = new JsonConverterFactory<ArticleCheckQuantityDto>()
                .get().fromJson(body, ArticleCheckQuantityDto.class);

        ArticleRepository articleRepository = ArticleRepositorySingleton.getArticleRepository();
        ArticleCheckQuantityResultDto articleCheckQuantityResultDto = articleRepository.checkQuantity(
                articleCheckQuantityDto.getArticleId(),
                articleCheckQuantityDto.getQuantity());

        String articleCheckQuantityResult = new JsonConverterFactory<ArticleCheckQuantityResultDto>()
                .get().toJson(articleCheckQuantityResultDto);

        AMQP.BasicProperties replyProperties = new AMQP.BasicProperties
                .Builder()
                .correlationId(properties.getCorrelationId())
                .build();

        String replyRoutingKey = "";
        super.getChannel().basicPublish(
                replyRoutingKey,
                properties.getReplyTo(),
                replyProperties,
                articleCheckQuantityResult.getBytes(StandardCharsets.UTF_8));

        boolean noAcknowledgeAll = false;
        super.getChannel().basicAck(envelope.getDeliveryTag(), noAcknowledgeAll);
    }
}