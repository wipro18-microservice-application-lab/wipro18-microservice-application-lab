package ch.hslu.wipro.micros.common;


import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static com.rabbitmq.client.AMQP.BasicProperties;

public class RabbitMqConstants {
    public static final String HOST_NAME = "localhost";
    public static final String JSON_MIME_TYPE = "application/json";
    public static final Charset DEFAULT_CHAR_SET = StandardCharsets.UTF_8;
    public static final BasicProperties JSON_PROPERTIES = new BasicProperties
            .Builder()
            .contentType(RabbitMqConstants.JSON_MIME_TYPE)
            .build();
}