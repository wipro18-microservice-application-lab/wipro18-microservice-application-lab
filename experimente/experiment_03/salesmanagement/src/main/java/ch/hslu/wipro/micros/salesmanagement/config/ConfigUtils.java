package ch.hslu.wipro.micros.salesmanagement.config;

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
            logger.error("Error while reading from file {}", configFile);
        }
    }

    public String getDomain() {
        return properties.getProperty("domain");
    }

    public String getExchange() {
        return properties.getProperty("exchange");
    }

    public String getCommandQueue() {
        return properties.getProperty("command");
    }

    public String getEventQueue() {
        return properties.getProperty("event");
    }
}