package ch.hslu.wipro.micros.warehousemanagement.rabbitmq.consumer;

import ch.hslu.wipro.micros.warehousemanagement.converter.JsonConverterFactory;
import ch.hslu.wipro.micros.warehousemanagement.dto.ArticleCheckQuantitiesDto;
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

public class ArticleCheckQuantitiesCommandConsumer extends DefaultConsumer {
    private static final Logger logger = LogManager.getLogger(ArticleCheckQuantitiesCommandConsumer.class);

    public ArticleCheckQuantitiesCommandConsumer(Channel channel) {
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope,
                               AMQP.BasicProperties properties, byte[] body) throws IOException {

        logger.info("handle incoming ArticleCheckQuantitiesCommand with correlation id: {}", properties.getCorrelationId());

        ArticleCheckQuantitiesDto articleCheckQuantityDto = new JsonConverterFactory<ArticleCheckQuantitiesDto>()
                .get().fromJson(body, ArticleCheckQuantitiesDto.class);

        ArticleRepository articleRepository = ArticleRepositorySingleton.getArticleRepository();
        ArticleCheckQuantityResultDto articleCheckQuantityResultDtos = articleRepository.checkQuantities(
                articleCheckQuantityDto.getAmountToArticle()
        );

        String articleCheckQuantityResultJson = new JsonConverterFactory<ArticleCheckQuantityResultDto>()
                .get().toJson(articleCheckQuantityResultDtos);

        AMQP.BasicProperties replyProperties = new AMQP.BasicProperties
                .Builder()
                .correlationId(properties.getCorrelationId())
                .build();

        String replyRoutingKey = "";
        super.getChannel().basicPublish(
                replyRoutingKey,
                properties.getReplyTo(),
                replyProperties,
                articleCheckQuantityResultJson.getBytes(StandardCharsets.UTF_8));

        boolean noAcknowledgeAll = false;
        super.getChannel().basicAck(envelope.getDeliveryTag(), noAcknowledgeAll);
    }
}