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
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.assertEquals;

public class OrderUpdateStatusCommandConsumerIT {
    private static final String OLD_STATUS = "old status";
    private static final String NEW_STATUS = "new status";

    private static final String exchangeName = "test.exchange";
    private static final String routingKey = "test.update.order.status";

    private OrderRepositoryService orderRepositoryService;
    private OrderDto orderDto;
    private Channel channel;

    @Before
    public void setUp() throws Exception {
        orderDto = new OrderDtoBuilder()
                .atCustomer(42L)
                .atTotalPrice(BigDecimal.valueOf(240))
                .atStatus(OLD_STATUS)
                .build();

        orderRepositoryService = new OrderRepositoryService();
        orderRepositoryService.setOrder(new OrderDtoBuilder().build());
        orderRepositoryService.setOrder(new OrderDtoBuilder().build());
        long orderDtoId = orderRepositoryService.setOrder(orderDto);
        orderDto.setOrderId(orderDtoId);

        ConnectionFactory factory = new MockConnectionFactory();
        try (Connection conn = factory.newConnection()) {
            channel = conn.createChannel();
        }
    }

    @Test
    public void handleDelivery() throws IOException, TimeoutException, InterruptedException {
        OrderUpdateDto orderUpdateDto = new OrderUpdateDto();
        orderUpdateDto.setOrderId(orderDto.getOrderId());
        orderUpdateDto.setStatus(NEW_STATUS);

        /* Setup OrderUpdateStatusCommand Consumer to listen to messages */
        channel.exchangeDeclare(exchangeName, "direct");
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, exchangeName, routingKey);

        OrderUpdateStatusCommandConsumer orderUpdateStatusCommandConsumer =
                new OrderUpdateStatusCommandConsumer(channel);

        channel.basicConsume(queueName, false, orderUpdateStatusCommandConsumer);

        /* Send an update dto message to be consumed */
        String orderUpdateJson = new JsonConverterFactory<OrderUpdateDto>().get().toJson(orderUpdateDto);

        AMQP.BasicProperties replyProperties = new AMQP.BasicProperties
                .Builder()
                .correlationId("test.correlation.id")
                .replyTo("void")
                .build();

        channel.basicPublish(
                exchangeName,
                routingKey, replyProperties,
                orderUpdateJson.getBytes(StandardCharsets.UTF_8));

        TimeUnit.MILLISECONDS.sleep(500L);

        OrderDto updatedOrderDto = orderRepositoryService.getOrder(orderDto.getOrderId());
        assertEquals(NEW_STATUS, updatedOrderDto.getStatus());
    }
}