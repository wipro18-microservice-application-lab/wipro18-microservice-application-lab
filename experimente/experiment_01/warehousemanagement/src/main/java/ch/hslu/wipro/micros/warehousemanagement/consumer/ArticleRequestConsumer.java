package ch.hslu.wipro.micros.warehousemanagement.consumer;

import ch.hslu.wipro.micros.common.RabbitMqConstants;
import ch.hslu.wipro.micros.warehousemanagement.RabbitMqManager;
import ch.hslu.wipro.micros.warehousemanagement.repository.ArticleOperation;
import ch.hslu.wipro.micros.warehousemanagement.repository.FakeWarehouseRepository;
import ch.hslu.wipro.micros.warehousemanagement.repository.WarehouseRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ArticleRequestConsumer extends DefaultConsumer {
    private WarehouseRepository warehouseRepository = new FakeWarehouseRepository();
    private RabbitMqManager rabbitMqManager;

    public ArticleRequestConsumer(RabbitMqManager rabbitMqManager, Channel channel) {
        super(channel);
        this.rabbitMqManager = rabbitMqManager;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope,
                               AMQP.BasicProperties properties, byte[] body) throws IOException {

        long articleRequestId = Long.parseLong(new String(body, StandardCharsets.UTF_8));
        printReceivedDelivery(articleRequestId, envelope);

        ArticleOperation op = warehouseRepository.getArticleDtoById(articleRequestId);
        if (op.isSuccess()) {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();

            String jsonArticle = gson.toJson(op.getArticle());
            rabbitMqManager.sendArticleResponse(jsonArticle);

            rabbitMqManager.sendAck(envelope.getDeliveryTag());
        }
    }


    private void printReceivedDelivery(long articleRequestId, Envelope envelope) {
        String deliveryInformation = String.format("exchange: %s, deliveryTag: %s, article request: %d",
                envelope.getExchange(), envelope.getDeliveryTag(), articleRequestId);

        System.out.println(deliveryInformation);
    }
}
