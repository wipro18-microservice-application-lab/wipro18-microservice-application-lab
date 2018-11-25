package ch.hslu.wipro.micros.business.converter;

import ch.hslu.wipro.micros.model.order.OrderDtoBuilder;
import ch.hslu.wipro.micros.model.order.OrderDto;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class JsonConverterTest {
    private final JsonConverter<OrderDto> jsonConverter = new GsonConverter<>();
    private static final BigDecimal TOTAL_PRICE = BigDecimal.valueOf(1.50);
    private static final long CUSTOMER_ID = 530L;
    private OrderDto orderDto;

    @Before
    public void setUp() {
        Map<Integer, Integer> amountToArticle = new HashMap<>();
        amountToArticle.put(1, 1);
        amountToArticle.put(2, 2);
        amountToArticle.put(3, 3);

        orderDto = new OrderDtoBuilder()
                .atCustomer(CUSTOMER_ID)
                .atTotalPrice(TOTAL_PRICE)
                .mapAmountToArticle(amountToArticle)
                .build();
    }

    @Test
    public void toJson() {
        String json = jsonConverter.toJson(orderDto);
        String expectedJson =
                "{\"orderId\":0,\"customerId\":530,\"amountToArticle\":{\"1\":1,\"2\":2,\"3\":3},\"totalPrice\":1.5,\"status\":null}";

        assertEquals(expectedJson, json);
    }

    @Test
    public void fromJson() {
        String json = jsonConverter.toJson(orderDto);

        assertEquals(orderDto, jsonConverter.fromJson(json, OrderDto.class));
    }
}