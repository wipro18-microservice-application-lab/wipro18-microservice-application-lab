package ch.hslu.wipro.micros.warehousemanagement.rabbitmq.consumer;

import ch.hslu.wipro.micros.warehousemanagement.dto.ArticleDto;
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
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ArticleGetAllCommandConsumerIT {
    private static final String exchangeName = "test.exchange";
    private static final String routingKey = "test.article.check.quantity";
    private static final String replyRoutingKey = "test.article.check.reply";
    private static List<ArticleDto> allArticles;
    private static String replyQueueName;
    private static Channel channel;

    @Before
    public void setUp() throws Exception {
        ArticleRepository articleRepository = ArticleRepositorySingleton.getArticleRepository();
        ArticleRepositoryManager articleRepositoryManager = new ArticleRepositoryManager(articleRepository);
        articleRepositoryManager.generateDeterministicInventory(50);

        allArticles = articleRepository.getAll();

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

        ArticleGetAllCommandConsumer articleGetAllCommandConsumer =
                new ArticleGetAllCommandConsumer(channel);

        channel.basicConsume(queueName, false, articleGetAllCommandConsumer);
    }

    @Test
    public void testGetAllArticles() throws IOException, InterruptedException {
        /* Send a message to be consumed */
        AMQP.BasicProperties replyProperties = new AMQP.BasicProperties
                .Builder()
                .correlationId("test.correlation.id")
                .replyTo(replyQueueName)
                .build();

        channel.basicPublish(
                exchangeName,
                routingKey, replyProperties,
                "".getBytes(StandardCharsets.UTF_8));

        TimeUnit.MILLISECONDS.sleep(500L);

        GetResponse response = channel.basicGet(replyQueueName, false);
        if (response == null) {
            fail("AMQP GetReponse must not be null");
        } else {
            byte[] body = response.getBody();
            String articleDtosJson = new String(body, StandardCharsets.UTF_8);
            List<ArticleDto> articleDtos =
                    new Gson().fromJson(articleDtosJson, new TypeToken<List<ArticleDto>>() {
                    }.getType());

            long deliveryTag = response.getEnvelope().getDeliveryTag();
            channel.basicAck(deliveryTag, false);

            assertEquals(allArticles, articleDtos);
        }
    }
}