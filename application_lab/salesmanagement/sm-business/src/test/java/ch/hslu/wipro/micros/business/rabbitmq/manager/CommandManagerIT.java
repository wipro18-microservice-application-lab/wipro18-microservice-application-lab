package ch.hslu.wipro.micros.business.rabbitmq.manager;

import ch.hslu.wipro.micros.business.CommandTopicsConsumerMap;
import ch.hslu.wipro.micros.business.converter.JsonConverterFactory;
import ch.hslu.wipro.micros.model.order.OrderDto;
import ch.hslu.wipro.micros.model.order.OrderDtoBuilder;
import ch.hslu.wipro.micros.service.repository.OrderRepositoryService;
import com.github.fridujo.rabbitmq.mock.MockConnectionFactory;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class CommandManagerIT {
    private static Channel channel;
    private static OrderDto createOrderDto;
    private OrderRepositoryService orderRepositoryService = new OrderRepositoryService();

    @Before
    public void setUp() throws Exception {
        CommandManager commandManager = new CommandManager(new CommandTopicsConsumerMap()
                .getAsMap());

        channel = new MockConnectionFactory().newConnection().createChannel();
        commandManager.startWithChannel(channel);

        Map<Integer, Integer> amountToArticle = new HashMap<>();
        amountToArticle.put(1, 5);
        amountToArticle.put(2, 4);
        amountToArticle.put(3, 1);

        createOrderDto = new OrderDtoBuilder()
                .atCustomer(42)
                .atTotalPrice(BigDecimal.valueOf(420L))
                .mapAmountToArticle(amountToArticle)
                .atStatus("open")
                .build();
    }

    @Test
    public void testOrderCreateCommandConsumerRepository() throws InterruptedException, IOException {
        String createOrderJson = new JsonConverterFactory<OrderDto>().get().toJson(createOrderDto);

        AMQP.BasicProperties replyProperties = new AMQP.BasicProperties
                .Builder()
                .correlationId("test.orderCreateCommandConsumer")
                .replyTo("void")
                .build();

        channel.basicPublish(
                "ch.hslu.wipro.micros.Order",
                "order.command.create", replyProperties,
                createOrderJson.getBytes(StandardCharsets.UTF_8));

        TimeUnit.MILLISECONDS.sleep(1500L);

        OrderDto foundOrderDto = orderRepositoryService.getOrder(0L);
        assertEquals(createOrderDto, foundOrderDto);
    }
}