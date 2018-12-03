package ch.hslu.wipro.micros.warehousemanagement.rabbitmq.consumer;

import ch.hslu.wipro.micros.warehousemanagement.converter.JsonConverterFactory;
import ch.hslu.wipro.micros.warehousemanagement.dto.ArticleCheckQuantityDto;
import ch.hslu.wipro.micros.warehousemanagement.dto.ArticleCheckQuantityResultDto;
import ch.hslu.wipro.micros.warehousemanagement.dto.ArticleReduceDto;
import ch.hslu.wipro.micros.warehousemanagement.dto.ArticleReduceResultDto;
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

public class ArticleReduceCommandConsumer extends DefaultConsumer {
    private static final Logger logger = LogManager.getLogger(ArticleReduceCommandConsumer.class);

    public ArticleReduceCommandConsumer(Channel channel) {
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope,
                               AMQP.BasicProperties properties, byte[] body) throws IOException {

        logger.info("handle incoming ArticleReduceCommand with correlation id: {}", properties.getCorrelationId());

        ArticleReduceDto articleReduceDto = new JsonConverterFactory<ArticleReduceDto>()
                .get().fromJson(body, ArticleReduceDto.class);

        ArticleRepository articleRepository = ArticleRepositorySingleton.getArticleRepository();
        ArticleReduceResultDto articleReduceResultDto = articleRepository.reduceQuantity(
                articleReduceDto.getArticleId(),
                articleReduceDto.getQuantity());

        String articleReduceResultJson = new JsonConverterFactory<ArticleReduceResultDto>()
                .get().toJson(articleReduceResultDto);

        AMQP.BasicProperties replyProperties = new AMQP.BasicProperties
                .Builder()
                .correlationId(properties.getCorrelationId())
                .build();

        String replyRoutingKey = "";
        super.getChannel().basicPublish(
                replyRoutingKey,
                properties.getReplyTo(),
                replyProperties,
                articleReduceResultJson.getBytes(StandardCharsets.UTF_8));

        boolean noAcknowledgeAll = false;
        super.getChannel().basicAck(envelope.getDeliveryTag(), noAcknowledgeAll);
    }
}