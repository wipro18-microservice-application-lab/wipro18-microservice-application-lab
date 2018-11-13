package ch.hslu.wipro.micros.warehousemanagement.consumer;

import ch.hslu.wipro.micros.warehousemanagement.dto.ArticleBuilder;
import ch.hslu.wipro.micros.warehousemanagement.dto.ArticleDto;
import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.rabbitmq.client.AMQP.BasicProperties;

public class GetArticleCommandConsumer extends DefaultConsumer {
    private static final Logger logger = LogManager.getLogger(GetArticleCommandConsumer.class);
    private List<ArticleDto> articleDtos = new ArrayList<>();

    public GetArticleCommandConsumer(Channel channel) {
        super(channel);

        articleDtos.add(
                new ArticleBuilder()
                        .atId(UUID.randomUUID().toString())
                        .atName("Samsung Galaxy A6")
                        .atDescription("Smartphone")
                        .atPrice(BigDecimal.valueOf(500.0))
                        .build()
        );
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope,
                               BasicProperties properties, byte[] body) throws IOException {

        BasicProperties replyProperties = new BasicProperties();
        String replyToQueue = properties.getReplyTo();
        String replyRoutingKey = "";

        String jsonArticles = new Gson().toJson(articleDtos);

        super.getChannel().basicPublish(
                replyRoutingKey,
                replyToQueue,
                replyProperties,
                jsonArticles.getBytes(StandardCharsets.UTF_8));

        super.getChannel().basicAck(envelope.getDeliveryTag(), false);
    }
}