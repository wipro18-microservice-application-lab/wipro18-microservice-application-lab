package ch.hslu.wipro.micros.warehousemanagement.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigUtils {
    private static final Logger logger = LogManager.getLogger(ConfigUtils.class);
    private Properties properties;

    public ConfigUtils(String configFile) {
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

    public String getInventoryCommandQueue() {
        return properties.getProperty("INVENTORY_COMMAND_QUEUE");
    }

    public String getInventoryCommandRoutingKey() {
        return properties.getProperty("INVENTORY_COMMAND_ROUTING_KEY");
    }
}