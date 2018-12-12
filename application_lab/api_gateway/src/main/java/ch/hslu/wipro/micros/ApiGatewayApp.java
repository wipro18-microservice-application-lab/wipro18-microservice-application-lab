package ch.hslu.wipro.micros;

import ch.hslu.wipro.micros.http.GrizzlyServerInstance;
import ch.hslu.wipro.micros.util.ConfigManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class ApiGatewayApp {
    private static Logger LOGGER = LogManager.getLogger();

    public static void main(String[] args) {
        String uri = getUriFromProps();
        LOGGER.info("The base uri for the api gateway is " + uri);
        GrizzlyServerInstance grizzlyServer = new GrizzlyServerInstance("ch.hslu.wipro.micros.service");
        grizzlyServer.runServerNonBlocking();
    }

    private static String getUriFromProps() {
        ConfigManager configManager = new ConfigManager();
        try {
            configManager.loadConfig();
        } catch (IOException | NullPointerException e) {
            LOGGER.error(e.getMessage());
            LOGGER.warn("Could not read from config file use default config instead");
        }
        return configManager.getGatewayUriFromProps();
    }
}
