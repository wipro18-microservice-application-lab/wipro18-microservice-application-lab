package ch.hslu.wipro.micros.warehousemanagement.consumer;

import ch.hslu.wipro.micros.common.dto.ArticleDto;
import ch.hslu.wipro.micros.common.message.RequestOperation;
import ch.hslu.wipro.micros.warehousemanagement.RabbitMqManager;
import ch.hslu.wipro.micros.warehousemanagement.repository.ArticleOperation;
import ch.hslu.wipro.micros.warehousemanagement.repository.WarehouseRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ArticleRequestConsumer extends DefaultConsumer {
    private static final Logger logger = LogManager.getLogger(ArticleRequestConsumer.class);
    private WarehouseRepository warehouseRepository;
    private RabbitMqManager rabbitMqManager;

    public ArticleRequestConsumer(RabbitMqManager rabbitMqManager,
                                  WarehouseRepository warehouseRepository,
                                  Channel channel) {
        super(channel);
        this.rabbitMqManager = rabbitMqManager;
        this.warehouseRepository = warehouseRepository;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope,
                               AMQP.BasicProperties properties, byte[] body) throws IOException {

        String messageUtf8 = new String(body, StandardCharsets.UTF_8);

        try {
            long articleRequestId = Long.parseLong(messageUtf8);
            printSuccessfulDelivery(articleRequestId, envelope);

            ArticleOperation op = warehouseRepository.getArticleDtoById(articleRequestId);
            RequestOperation<ArticleDto> requestOperation;
            if (op.isSuccess()) {
                requestOperation = new RequestOperation<>(true, articleRequestId, op.getArticle());
            } else {
                requestOperation = new RequestOperation<>(false, articleRequestId, null);
            }

            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();

            String jsonRequestOperation = gson.toJson(requestOperation);
            rabbitMqManager.sendArticleResponse(jsonRequestOperation);
            rabbitMqManager.sendAck(envelope.getDeliveryTag());
        } catch (NumberFormatException e) {
            String warning = String.format("wrong article id format. expected long, received %s", messageUtf8);
            logger.warn(warning);
        }
    }


    private void printSuccessfulDelivery(long articleRequestId, Envelope envelope) {
        String deliveryInformation = String.format("exchange: %s, deliveryTag: %s, article request: %d",
                envelope.getExchange(), envelope.getDeliveryTag(), articleRequestId);

        logger.info(deliveryInformation);
    }
}
