package ch.hslu.wipro.micros.salesmanagement;

import ch.hslu.wipro.micros.common.RabbitMqConstants;
import ch.hslu.wipro.micros.common.command.ChangeArticleStockCommand;
import ch.hslu.wipro.micros.common.command.WarehouseCommand;
import ch.hslu.wipro.micros.common.util.RabbitMqFunctions;
import ch.hslu.wipro.micros.salesmanagement.consumer.ArticleResponseConsumer;
import ch.hslu.wipro.micros.salesmanagement.consumer.CustomerResponseConsumer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
     * Declares an ArticleRequest exchange and a queue and bind them together if the do not exist yet.
     * Then it publishes a message on the article request exchange, containing a command for the warehouse management.
     *
     * @param warehouseCommand command for the warehouse management to process.
     * @throws IOException throws exception if rabbitmq can't be reached.
     */
    void sendArticleRequest(WarehouseCommand warehouseCommand) throws IOException {
        RabbitMqFunctions rabbitMqFunctions = new RabbitMqFunctions(channel);
        rabbitMqFunctions.createAndBindQueueToExchange(RabbitMqConstants.ARTICLE_REQUEST_QUEUE,
                RabbitMqConstants.ARTICLE_REQUEST_EXCHANGE);

        BasicProperties basicProperties = new BasicProperties.Builder()
                .contentType(RabbitMqConstants.JSON_MIME_TYPE)
                .build();

        String articleRequestJson = convertToJson(warehouseCommand);

        channel.basicPublish(RabbitMqConstants.ARTICLE_REQUEST_EXCHANGE,
                "", basicProperties, articleRequestJson.getBytes("UTF-8"));
    }

    private String convertToJson(WarehouseCommand warehouseCommand) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        return gson.toJson(warehouseCommand);
    }

    void sendCustomerRequest(long id) throws IOException {
        RabbitMqFunctions rabbitMqFunctions = new RabbitMqFunctions(channel);
        rabbitMqFunctions.createAndBindQueueToExchange(RabbitMqConstants.CUSTOMER_REQUEST_QUEUE,
                RabbitMqConstants.CUSTOMER_REQUEST_EXCHANGE);

        BasicProperties basicProperties = new BasicProperties.Builder()
                .contentType(RabbitMqConstants.JSON_MIME_TYPE)
                .build();

        String articleRequestId = Long.toString(id);

        channel.basicPublish(RabbitMqConstants.CUSTOMER_REQUEST_EXCHANGE,
                "", basicProperties, articleRequestId.getBytes("UTF-8"));
    }

    void listenForArticleResponse() throws IOException {
        channel.basicConsume(RabbitMqConstants.ARTICLE_RESPONSE_QUEUE, true,
                new ArticleResponseConsumer(channel));
    }

    void listenForCustomerResponse() throws IOException {
        channel.basicConsume(RabbitMqConstants.CUSTOMER_RESPONSE_QUEUE, true,
                new CustomerResponseConsumer(channel));
    }

    public void close() throws IOException {
        channel.abort();
    }
}
