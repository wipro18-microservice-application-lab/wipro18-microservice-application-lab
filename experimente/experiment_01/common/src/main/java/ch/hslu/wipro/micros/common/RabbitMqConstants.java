package ch.hslu.wipro.micros.common;

public class RabbitMqConstants {
    public static final String HOST_NAME = "localhost";
    public static final String JSON_MIME_TYPE = "application/json";

    public static final String ARTICLE_REQUEST_EXCHANGE = "ch.hslu.wipro.micros.ArticleRequestExchange";
    public static final String ARTICLE_REQUEST_QUEUE = "ch.hslu.wipro.micros.ArticleRequestQueue";

    public static final String ARTICLE_RESPONSE_EXCHANGE = "ch.hslu.wipro.micros.ArticleResponseExchange";
    public static final String ARTICLE_RESPONSE_QUEUE = "ch.hslu.wipro.micros.ArticleResponseQueue";

    public static final String CUSTOMER_REQUEST_EXCHANGE = "ch.hslu.wipro.micros.CustomerRequestExchange";
    public static final String CUSTOMER_REQUEST_QUEUE = "ch.hslu.wipro.micros.CustomerRequestQueue";

    public static final String CUSTOMER_RESPONSE_EXCHANGE = "ch.hslu.wipro.micros.CustomerResponseExchange";
    public static final String CUSTOMER_RESPONSE_QUEUE = "ch.hslu.wipro.micros.CustomerResponseQueue";
}
