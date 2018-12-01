package ch.hslu.wipro.micros.business.rabbitmq.consumer;

import ch.hslu.wipro.micros.business.converter.JsonConverterFactory;
import ch.hslu.wipro.micros.model.order.OrderDto;
import ch.hslu.wipro.micros.model.order.OrderDtoBuilder;
import ch.hslu.wipro.micros.model.order.OrderUpdateDto;
import ch.hslu.wipro.micros.service.repository.OrderRepositoryService;
import com.github.fridujo.rabbitmq.mock.MockConnectionFactory;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.assertEquals;

public class OrderCreateCommandConsumerIT {
    private static final String exchangeName = "test.exchange";
    private static final String routingKey = "test.update.order.status";

    private OrderRepositoryService orderRepositoryService;
    private static OrderDto createOrderDto;
    private Channel channel;

    @Before
    public void setUp() throws Exception {
        Map<Long, Integer> amountToArticle = new HashMap<>();
        amountToArticle.put(1L, 5);
        amountToArticle.put(2L, 4);
        amountToArticle.put(3L, 1);

        createOrderDto = new OrderDtoBuilder()
                .atCustomer(42)
                .atTotalPrice(BigDecimal.valueOf(420L))
                .mapAmountToArticle(amountToArticle)
                .atStatus("open")
                .build();

        orderRepositoryService = new OrderRepositoryService();

        ConnectionFactory factory = new MockConnectionFactory();
        try (Connection conn = factory.newConnection()) {
            channel = conn.createChannel();
        }
    }

    @Test
    public void handleDelivery() throws IOException, InterruptedException {
        /* Setup OrderUpdateStatusCommand Consumer to listen to messages */
        channel.exchangeDeclare(exchangeName, "direct");
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, exchangeName, routingKey);

        OrderCreateCommandConsumer orderCreateCommandConsumer =
                new OrderCreateCommandConsumer(channel);

        channel.basicConsume(queueName, false, orderCreateCommandConsumer);

        /* Send an update dto message to be consumed */
        String createOrderJson = new JsonConverterFactory<OrderDto>().get().toJson(createOrderDto);

        AMQP.BasicProperties replyProperties = new AMQP.BasicProperties
                .Builder()
                .correlationId("test.correlation.id")
                .replyTo("void")
                .build();

        channel.basicPublish(
                exchangeName,
                routingKey, replyProperties,
                createOrderJson.getBytes(StandardCharsets.UTF_8));

        TimeUnit.MILLISECONDS.sleep(500L);

        OrderDto retrievedOrderDto = orderRepositoryService.getOrder(0L);
        assertEquals(createOrderDto, retrievedOrderDto);
    }
}