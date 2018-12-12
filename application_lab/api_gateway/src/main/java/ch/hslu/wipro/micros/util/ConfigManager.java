package ch.hslu.wipro.micros.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * This is a helper class to read from the config properties file.
 */
public class ConfigManager {

    private final static String CONFIG_FILE_NAME = "gateway.properties";

    private Properties properties;

    public ConfigManager() {
        properties = new Properties();
    }

    public void loadConfig() throws IOException, NullPointerException {
        InputStream in = getClass().getClassLoader().getResourceAsStream(CONFIG_FILE_NAME);
        properties.load(in);
        in.close();
    }

    /**
     * Gets the base uri from the properties. If the properties are not present the method uses default values.
     * @return base uri string
     */
    public String getGatewayUriFromProps() {
        String uri = "";
        uri += properties.getProperty("protocol", "http") + "://";
        uri += properties.getProperty("address", "0.0.0.0") + ":";
        uri += properties.getProperty("port", "8080") + "/";
        uri += properties.getProperty("base_path", "gateway");
        return uri;
    }
}
