package ch.hslu.wipro.micros.service.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ErrorService {
    private Properties properties = new Properties();

    public ErrorService() {
        String configFile = ConfigConsts.MESSAGES_FILE;
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(configFile);

        try {
            properties.load(inputStream);
            inputStream.close();
        } catch (IOException e) {
            Logger logger = LogManager.getLogger(ErrorService.class);
            logger.error("Error while reading from file {}", configFile);
        }
    }

    public void logConnectionError(Class<?> target) {
        String errorMessage = properties.getProperty("discovery_io_exception");
        LogManager.getLogger(target).error(errorMessage);
    }
}
