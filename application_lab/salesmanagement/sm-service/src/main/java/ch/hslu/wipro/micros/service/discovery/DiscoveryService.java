package ch.hslu.wipro.micros.service.discovery;

import ch.hslu.wipro.micros.model.discovery.RegisterDto;
import ch.hslu.wipro.micros.service.ChannelBuilder;
import ch.hslu.wipro.micros.service.config.ConfigConsts;
import ch.hslu.wipro.micros.service.config.ConfigService;
import com.google.gson.Gson;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class DiscoveryService {
    private ConfigService configService = new ConfigService();
    private final Channel channel;

    public DiscoveryService() throws IOException, TimeoutException {
        this.channel = new ChannelBuilder()
                .withHost(configService.getRabbitMqHost())
                .build();
    }

    public void register() throws IOException {
        RegisterDto registerDto = configService.getRegisterDetails();
        String jsonRegisterInformation = new Gson().toJson(registerDto);

        channel.basicPublish(
                configService.getDiscoveryExchange(),
                configService.getDiscoveryRegisterRoute(),
                configService.getBasicProperties(),
                jsonRegisterInformation.getBytes(StandardCharsets.UTF_8));
    }
}