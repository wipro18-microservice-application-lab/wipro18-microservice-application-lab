package ch.hslu.wipro.micros.warehousemanagement;

import ch.hslu.wipro.micros.common.RabbitMqConstants;
import ch.hslu.wipro.micros.common.util.RabbitMqFunctions;
import ch.hslu.wipro.micros.warehousemanagement.consumer.ArticleRequestConsumer;
import ch.hslu.wipro.micros.warehousemanagement.repository.WarehouseRepository;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

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
     * Declares an ArticleResponse exchange and a queue and binds them together if the do not exist yet.
     * Then it publishes a message on the article request exchange, containing the requested Article.
     *
     * @param jsonArticle the requested article as json.
     * @throws IOException throws exception if rabbitmq can't be reached.
     */
    public void sendArticleResponse(String jsonArticle) throws IOException {
        RabbitMqFunctions rabbitMqFunctions = new RabbitMqFunctions(channel);
        rabbitMqFunctions.createAndBindQueueToExchange(RabbitMqConstants.ARTICLE_RESPONSE_QUEUE,
                RabbitMqConstants.ARTICLE_RESPONSE_EXCHANGE);

        AMQP.BasicProperties basicProperties = new AMQP.BasicProperties.Builder()
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
