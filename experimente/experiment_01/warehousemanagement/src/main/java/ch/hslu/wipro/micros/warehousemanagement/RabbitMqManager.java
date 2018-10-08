package ch.hslu.wipro.micros.warehousemanagement;

import ch.hslu.wipro.micros.common.RabbitMqConstants;
import ch.hslu.wipro.micros.warehousemanagement.consumer.ArticleRequestConsumer;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMqManager implements Closeable {
    private Channel channel;

    public RabbitMqManager() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RabbitMqConstants.HOST_NAME);
        Connection connection = factory.newConnection();
        channel = connection.createChannel();
    }

    public void listenForArticleRequest() throws IOException {
        channel.basicConsume(RabbitMqConstants.ARTICLE_REQUEST_QUEUE, true,
                new ArticleRequestConsumer(channel));
    }

    @Override
    public void close() throws IOException {
        channel.abort();
    }
}
