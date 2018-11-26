package ch.hslu.wipro.micros.warehousemanagement.rabbitmq.consumer;

import ch.hslu.wipro.micros.warehousemanagement.converter.JsonConverterFactory;
import ch.hslu.wipro.micros.warehousemanagement.dto.ArticleCheckQuantityDto;
import ch.hslu.wipro.micros.warehousemanagement.dto.ArticleCheckQuantityResultDto;
import ch.hslu.wipro.micros.warehousemanagement.repository.ArticleRepository;
import ch.hslu.wipro.micros.warehousemanagement.repository.ArticleRepositoryManager;
import ch.hslu.wipro.micros.warehousemanagement.repository.ArticleRepositorySingleton;
import com.github.fridujo.rabbitmq.mock.MockConnectionFactory;
import com.rabbitmq.client.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ArticleCheckQuantityCommandConsumerIT {
    private static final String exchangeName = "test.exchange";
    private static final String routingKey = "test.article.check.quantity";
    private static final String replyRoutingKey = "test.article.check.reply";
    private static Channel channel;
    private static String replyQueueName;

    @Before
    public void setUp() throws Exception {
        ArticleRepository articleRepository = ArticleRepositorySingleton.getArticleRepository();
        ArticleRepositoryManager articleRepositoryManager = new ArticleRepositoryManager(articleRepository);
        articleRepositoryManager.generateDeterministicInventory(50);

        ConnectionFactory factory = new MockConnectionFactory();
        try (Connection conn = factory.newConnection()) {
            channel = conn.createChannel();
        }

        /* Setup reply channel */
        channel.exchangeDeclare(exchangeName, "direct");
        replyQueueName = channel.queueDeclare().getQueue();
        channel.queueBind(replyQueueName, exchangeName, replyRoutingKey);

        /* Setup ArticleCheckQuantity Consumer to listen to messages */
        channel.exchangeDeclare(exchangeName, "direct");
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, exchangeName, routingKey);

        ArticleCheckQuantityCommandConsumer orderUpdateStatusCommandConsumer =
                new ArticleCheckQuantityCommandConsumer(channel);

        channel.basicConsume(queueName, false, orderUpdateStatusCommandConsumer);
    }

    @Test
    public void testEnoughArticlesInStock() throws IOException, InterruptedException {
        /* Send an update dto message to be consumed */
        ArticleCheckQuantityDto articleCheckQuantityDto = new ArticleCheckQuantityDto();
        articleCheckQuantityDto.setArticleId(0);
        articleCheckQuantityDto.setQuantity(15);

        String orderUpdateJson = new JsonConverterFactory<ArticleCheckQuantityDto>()
                .get().toJson(articleCheckQuantityDto);

        AMQP.BasicProperties replyProperties = new AMQP.BasicProperties
                .Builder()
                .correlationId("test.correlation.id")
                .replyTo(replyQueueName)
                .build();

        channel.basicPublish(
                exchangeName,
                routingKey, replyProperties,
                orderUpdateJson.getBytes(StandardCharsets.UTF_8));

        TimeUnit.MILLISECONDS.sleep(500L);

        GetResponse response = channel.basicGet(replyQueueName, false);
        if (response == null) {
            fail("AMQP GetReponse must not be null");
        } else {
            byte[] body = response.getBody();
            ArticleCheckQuantityResultDto articleCheckQuantityResultDto =
                    new JsonConverterFactory<ArticleCheckQuantityResultDto>()
                            .get().fromJson(body, ArticleCheckQuantityResultDto.class);

            long deliveryTag = response.getEnvelope().getDeliveryTag();
            channel.basicAck(deliveryTag, false);

            assertEquals(ArticleCheckQuantityResultDto.ENOUGH_ARTICLES, articleCheckQuantityResultDto.getResult());
        }
    }

    @Test
    public void testNotEnoughArticlesInStock() throws IOException, InterruptedException {
        /* Send an update dto message to be consumed */
        ArticleCheckQuantityDto articleCheckQuantityDto = new ArticleCheckQuantityDto();
        articleCheckQuantityDto.setArticleId(0);
        articleCheckQuantityDto.setQuantity(55);

        String orderUpdateJson = new JsonConverterFactory<ArticleCheckQuantityDto>()
                .get().toJson(articleCheckQuantityDto);

        AMQP.BasicProperties replyProperties = new AMQP.BasicProperties
                .Builder()
                .correlationId("test.correlation.id")
                .replyTo(replyQueueName)
                .build();

        channel.basicPublish(
                exchangeName,
                routingKey, replyProperties,
                orderUpdateJson.getBytes(StandardCharsets.UTF_8));

        TimeUnit.MILLISECONDS.sleep(500L);

        GetResponse response = channel.basicGet(replyQueueName, false);
        if (response == null) {
            fail("AMQP GetReponse must not be null");
        } else {
            byte[] body = response.getBody();
            ArticleCheckQuantityResultDto articleCheckQuantityResultDto =
                    new JsonConverterFactory<ArticleCheckQuantityResultDto>()
                            .get().fromJson(body, ArticleCheckQuantityResultDto.class);

            long deliveryTag = response.getEnvelope().getDeliveryTag();
            channel.basicAck(deliveryTag, false);

            assertEquals(ArticleCheckQuantityResultDto.NOT_ENOUGHT_ARTICLES, articleCheckQuantityResultDto.getResult());
        }
    }
}