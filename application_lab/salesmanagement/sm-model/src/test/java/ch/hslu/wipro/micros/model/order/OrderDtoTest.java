package ch.hslu.wipro.micros.model.order;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class OrderDtoTest {
    private OrderDto orderDto;
    private String expectedJson;

    @Before
    public void setUp() throws Exception {
        Map<Integer, Integer> amountToArticle = new HashMap<>();
        amountToArticle.put(1, 1);
        amountToArticle.put(2, 2);

        orderDto = new OrderBuilder()
                .atCustomer(0)
                .atTotalPrice(BigDecimal.valueOf(1.50))
                .mapAmountToArticle(amountToArticle)
                .build();

        expectedJson = "{\"orderId\":0,\"customerId\":0,\"amountToArticle\":{\"1\":1,\"2\":2},\"totalPrice\":1.5}";
    }

    @Test
    public void testToJson() {
        String orderAsJson = new Gson().toJson(orderDto);

        assertEquals(expectedJson, orderAsJson);
    }
}