package ch.hslu.wipro.micros.warehousemanagement;

import ch.hslu.wipro.micros.common.RabbitMqConstants;
import ch.hslu.wipro.micros.warehousemanagement.consumer.ArticleRequestConsumer;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static com.rabbitmq.client.AMQP.BasicProperties;

public class RabbitMqManager implements Closeable {
    private Channel channel;

    RabbitMqManager() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RabbitMqConstants.HOST_NAME);
        Connection connection = factory.newConnection();
        channel = connection.createChannel();
    }

    /**
     * Subscribes to the ArticleRequest queue and consumes the request.
     * Only after a successful process it will send an Acknowledge.
     *
     * @throws IOException throws exception if rabbitmq can't be reached.
     */
    void listenForArticleRequest() throws IOException {
        channel.basicConsume(RabbitMqConstants.ARTICLE_REQUEST_QUEUE, false,
                new ArticleRequestConsumer(this, channel));
    }

    /**
     * Publishes a message on the article request exchange, containing the a message if the request could be processed.
     *
     * @param jsonArticle success or fail of the received warehouse command.
     * @throws IOException throws exception if rabbitmq can't be reached.
     */
    public void sendArticleResponse(String jsonArticle) throws IOException {
        BasicProperties basicProperties = new BasicProperties.Builder()
                .contentType(RabbitMqConstants.JSON_MIME_TYPE)
                .build();

        channel.basicPublish(RabbitMqConstants.ARTICLE_RESPONSE_EXCHANGE,
                "", basicProperties, jsonArticle.getBytes("UTF-8"));
    }

    public void sendAck(long deliveryTag) throws IOException {
        channel.basicAck(deliveryTag, false);
    }

    @Override
    public void close() throws IOException {
        channel.abort();
    }
}
