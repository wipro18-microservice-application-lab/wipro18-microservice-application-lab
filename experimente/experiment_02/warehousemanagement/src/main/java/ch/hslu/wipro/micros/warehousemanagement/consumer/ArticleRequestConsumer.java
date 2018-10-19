package ch.hslu.wipro.micros.warehousemanagement.consumer;

import ch.hslu.wipro.micros.common.command.ChangeArticleStockCommand;
import ch.hslu.wipro.micros.common.command.UndoLastCommand;
import ch.hslu.wipro.micros.common.command.WarehouseCommand;
import ch.hslu.wipro.micros.common.message.WarehouseCommandState;
import ch.hslu.wipro.micros.warehousemanagement.RabbitMqManager;
import ch.hslu.wipro.micros.warehousemanagement.eventsourcing.EventBroker;
import ch.hslu.wipro.micros.warehousemanagement.eventsourcing.EventBrokerFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.rabbitmq.client.AMQP.BasicProperties;

public class ArticleRequestConsumer extends DefaultConsumer {
    private static final Logger logger = LogManager.getLogger(ArticleRequestConsumer.class);
    private EventBroker eventBroker = EventBrokerFactory.getBroker();
    private RabbitMqManager rabbitMqManager;

    public ArticleRequestConsumer(RabbitMqManager rabbitMqManager, Channel channel) {
        super(channel);
        this.rabbitMqManager = rabbitMqManager;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope,
                               BasicProperties properties, byte[] body) throws IOException {

        WarehouseCommand warehouseCommand = convertFromJson(body);
        printReceivedDelivery(warehouseCommand, envelope);

        if (warehouseCommand instanceof UndoLastCommand) {
            eventBroker.undoLast();
        } else {
            eventBroker.command(warehouseCommand);
        }

        eventBroker.getCommandStatus();
        String jsonResponse = convertToJson(eventBroker.getCommandStatus());

        rabbitMqManager.sendArticleResponse(jsonResponse);
        rabbitMqManager.sendAck(envelope.getDeliveryTag());
    }

    private String convertToJson(WarehouseCommandState commandStatus) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        return gson.toJson(commandStatus);
    }

    private WarehouseCommand convertFromJson(byte[] body) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        String bodyUtf8 = new String(body, StandardCharsets.UTF_8);

        if (bodyUtf8.contains(ChangeArticleStockCommand.class.getName())) {
            return gson.fromJson(bodyUtf8,
                    ChangeArticleStockCommand.class);
        }

        if (bodyUtf8.contains(UndoLastCommand.class.getName())) {
            return gson.fromJson(bodyUtf8,
                    UndoLastCommand.class);
        }

        return gson.fromJson(bodyUtf8, WarehouseCommand.class);
    }

    private void printReceivedDelivery(WarehouseCommand warehouseCommand, Envelope envelope) {
        String deliveryInformation = String.format("exchange: %s, deliveryTag: %s, command: %s",
                envelope.getExchange(), envelope.getDeliveryTag(), warehouseCommand.getClass());

        logger.info(deliveryInformation);
    }
}