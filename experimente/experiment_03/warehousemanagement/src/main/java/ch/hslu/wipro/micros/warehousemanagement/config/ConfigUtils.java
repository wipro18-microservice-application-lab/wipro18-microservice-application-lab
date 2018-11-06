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

    public int getConnectionPeriod() {
        return Integer.parseInt(properties.getProperty("connection_period"));
    }

    public int getConnectionDelay() {
        return Integer.parseInt(properties.getProperty("connection_delay"));
    }
}