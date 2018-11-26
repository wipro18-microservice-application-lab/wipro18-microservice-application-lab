package ch.hslu.wipro.micros.warehousemanagement.rabbitmq.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class RabbitMqConfig {
    private static final Logger logger = LogManager.getLogger(RabbitMqConfig.class);
    private final Properties properties;

    public RabbitMqConfig(String configFile) {
        properties = new Properties();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(configFile);

        try {
            properties.load(inputStream);
            inputStream.close();
        } catch (IOException e) {
            logger.error("error while reading from file {}", configFile);
        }
    }

    public String getHost() {
        return properties.getProperty("HOST");
    }

    public String getArticleExchange() {
        return properties.getProperty("ARTICLE_EXCHANGE");
    }
}