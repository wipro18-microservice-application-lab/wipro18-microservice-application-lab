package ch.hslu.wipro.micros.warehousemanagement.rabbitmq.consumer;

import ch.hslu.wipro.micros.warehousemanagement.converter.JsonConverterFactory;
import ch.hslu.wipro.micros.warehousemanagement.dto.ArticleDto;
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
import java.util.List;

public class ArticleGetAllCommandConsumer extends DefaultConsumer {
    private static final Logger logger = LogManager.getLogger(ArticleGetAllCommandConsumer.class);

    public ArticleGetAllCommandConsumer(Channel channel) {
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope,
                               AMQP.BasicProperties properties, byte[] body) throws IOException {

        logger.info("handle incoming ArticleGetAllCommand with correlation id: {}", properties.getCorrelationId());

        ArticleRepository articleRepository = ArticleRepositorySingleton.getArticleRepository();
        List<ArticleDto> articleDtos = articleRepository.getAll();

        String articleDtosJson = new JsonConverterFactory<List<ArticleDto>>()
                .get().toJson(articleDtos);

        AMQP.BasicProperties replyProperties = new AMQP.BasicProperties
                .Builder()
                .correlationId(properties.getCorrelationId())
                .build();

        String replyRoutingKey = "";
        super.getChannel().basicPublish(
                replyRoutingKey,
                properties.getReplyTo(),
                replyProperties,
                articleDtosJson.getBytes(StandardCharsets.UTF_8));

        boolean noAcknowledgeAll = false;
        super.getChannel().basicAck(envelope.getDeliveryTag(), noAcknowledgeAll);
    }
}