package ch.hslu.wipro.micros.warehousemanagement.rabbitmq.consumer;

import ch.hslu.wipro.micros.warehousemanagement.converter.JsonConverterFactory;
import ch.hslu.wipro.micros.warehousemanagement.dto.ArticleCheckQuantityDto;
import ch.hslu.wipro.micros.warehousemanagement.dto.ArticleCheckQuantityResultDto;
import ch.hslu.wipro.micros.warehousemanagement.repository.ArticleRepository;
import ch.hslu.wipro.micros.warehousemanagement.repository.ArticleRepositoryManager;
import ch.hslu.wipro.micros.warehousemanagement.repository.ArticleRepositorySingleton;
import com.github.fridujo.rabbitmq.mock.MockConnectionFactory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rabbitmq.client.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ArticleCheckQuantitiesCommandConsumerIT {
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

        ArticleCheckQuantitiesCommandConsumer articleCheckQuantitiesCommandConsumer =
                new ArticleCheckQuantitiesCommandConsumer(channel);

        channel.basicConsume(queueName, false, articleCheckQuantitiesCommandConsumer);
    }

    @Test
    public void testEnoughEnoughNotEnoughArticlesInStock() throws IOException, InterruptedException {
        String expectedResult = "enough articles in stock, enough articles in stock, not enough articles in stock";

        /* Send an update dto message to be consumed */
        String orderUpdateJson = "{\"amountToArticle\":{\"0\":50,\"1\":50,\"2\":150}}";

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
        assertGetResponse(expectedResult, response);
    }

    private void assertGetResponse(String expectedResult, GetResponse response) throws IOException {
        if (response == null) {
            fail("AMQP GetReponse must not be null");
        } else {
            byte[] body = response.getBody();

            ArticleCheckQuantityResultDto articleCheckQuantityResultDto =
                    new JsonConverterFactory<ArticleCheckQuantityResultDto>().get()
                            .fromJson(body, ArticleCheckQuantityResultDto.class);

            long deliveryTag = response.getEnvelope().getDeliveryTag();
            channel.basicAck(deliveryTag, false);

            assertEquals(expectedResult,
                    articleCheckQuantityResultDto.getResult());
        }
    }

    @Test
    public void testUnknownIdEnoughNotEnoughArticlesInStock() throws IOException, InterruptedException {
        String expectedResult = "unknown article id, enough articles in stock, enough articles in stock";

        /* Send an update dto message to be consumed */
        String orderUpdateJson = "{\"amountToArticle\":{\"150\":50,\"1\":50,\"2\":50}}";

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
        assertGetResponse(expectedResult, response);
    }
}