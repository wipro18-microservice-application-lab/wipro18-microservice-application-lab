package ch.hslu.wipro.micros.common;


import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static com.rabbitmq.client.AMQP.*;

public class RabbitMqConstants {
    public static final String HOST_NAME = "localhost";
    public static final String JSON_MIME_TYPE = "application/json";
    public static final BasicProperties jsonMimeType = new BasicProperties.Builder()
                                                            .contentType(RabbitMqConstants.JSON_MIME_TYPE)
                                                            .build();

    public static Charset defaultCharSet = StandardCharsets.UTF_8;
}