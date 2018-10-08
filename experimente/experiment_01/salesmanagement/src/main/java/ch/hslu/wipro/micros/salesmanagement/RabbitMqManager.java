package ch.hslu.wipro.micros.salesmanagement;

import ch.hslu.wipro.micros.common.RabbitMqConstants;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static com.rabbitmq.client.AMQP.BasicProperties;

public class RabbitMqManager implements Closeable {
    private Channel channel;

    public RabbitMqManager() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RabbitMqConstants.HOST_NAME);
        Connection connection = factory.newConnection();
        channel = connection.createChannel();
    }

    /**
     * Declares an article request exchange and a queue if the do not exist yet.
     * Then it publishes a message on the article request exchange, containing the id of the wanted article.
     *
     * @param id id of the article being requested.
     * @throws IOException throws exception if rabbitmq can't be reached.
     */
    public void requestArticle(double id) throws IOException {
        channel.exchangeDeclare(RabbitMqConstants.ARTICLE_REQUEST_EXCHANGE,
                BuiltinExchangeType.DIRECT);

        channel.queueDeclare(RabbitMqConstants.ARTICLE_REQUEST_QUEUE,
                false, false, false, null);

        BasicProperties basicProperties = new BasicProperties.Builder()
                .contentType(RabbitMqConstants.JSON_MIME_TYPE)
                .build();

        String articleRequestId = Double.toString(id);

        channel.basicPublish(RabbitMqConstants.ARTICLE_REQUEST_EXCHANGE,
                "", basicProperties, articleRequestId.getBytes("UTF-8"));
    }

    public void close() throws IOException {
        channel.abort();
    }
}
