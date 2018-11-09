package ch.hslu.wipro.micros.service.config;

import ch.hslu.wipro.micros.model.discovery.RegisterBuilder;
import ch.hslu.wipro.micros.model.discovery.RegisterDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static com.rabbitmq.client.AMQP.BasicProperties;

public class ConfigService {
    private static final Logger logger = LogManager.getLogger(ConfigService.class);
    private Properties properties = new Properties();

    public ConfigService() {
        String configFile = ConfigConsts.CONFIG_FILE;
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(configFile);

        try {
            properties.load(inputStream);
            inputStream.close();
        } catch (IOException e) {
            logger.error("Error while reading from file {}", configFile);
        }
    }

    public String getRepositoryStrategy() {
        return properties.getProperty("repository");
    }

    public String getDiscoveryExchange() {
        return properties.getProperty("discovery_exchange");
    }

    public String getDiscoveryRegisterRoute() {
        return properties.getProperty("discovery_register_route");
    }

    public String getRabbitMqHost() {
        return properties.getProperty("rabbitmq_host");
    }

    public RegisterDto getRegisterDetails() {
        String domain = properties.getProperty("domain");
        String exchange = properties.getProperty("domain");
        List<String> topics = Arrays.asList(properties.getProperty("topics").split(";"));

        return new RegisterBuilder()
                .forDomain(domain)
                .withExchange(exchange)
                .withTopics(topics)
                .build();
    }

    public String getContentType() {
        return properties.getProperty("rabbitmq_content_type");
    }

    public BasicProperties getBasicProperties() {
        return new BasicProperties
                .Builder()
                .contentType(getContentType())
                .build();
    }

    public String getOrderCommandQueue() {
        return properties.getProperty("queue");
    }

    public String getOrderExchange() {
        return properties.getProperty("exchange");
    }

    public String getRoutingKey() {
        return properties.getProperty("routing");
    }
}