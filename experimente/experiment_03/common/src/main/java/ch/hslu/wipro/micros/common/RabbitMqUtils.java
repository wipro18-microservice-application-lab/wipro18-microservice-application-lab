package ch.hslu.wipro.micros.common;

import static com.rabbitmq.client.AMQP.BasicProperties;

public class RabbitMqUtils {

    public static BasicProperties getPropertiesForReplyChannel(String correlationId, String replyQueue) {

        return new BasicProperties
                .Builder()
                .contentType(RabbitMqConstants.JSON_MIME_TYPE)
                .correlationId(correlationId)
                .replyTo(replyQueue)
                .build();
    }
}