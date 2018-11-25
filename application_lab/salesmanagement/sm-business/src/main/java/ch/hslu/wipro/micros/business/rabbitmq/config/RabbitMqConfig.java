package ch.hslu.wipro.micros.business.rabbitmq.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class RabbitMqConfig {
    private static final Logger logger = LogManager.getLogger(RabbitMqConfig.class);
    private static final String configFile = "rabbitmq.properties";
    private final Properties properties = new Properties();

    public RabbitMqConfig() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(configFile);

        try {
            properties.load(inputStream);
            inputStream.close();
        } catch (IOException e) {
            logger.error("Error while reading from file {}", configFile);
        }
    }

    public String getHost() {
        return properties.getProperty("rabbitmq_host");
    }

    public String getDomainExchange() {
        return properties.getProperty("order_exchange");
    }
}
