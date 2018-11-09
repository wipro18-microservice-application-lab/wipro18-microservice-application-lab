package ch.hslu.wipro.micros.service.convertion;

import ch.hslu.wipro.micros.model.order.OrderDto;
import org.junit.Test;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class OrderCreateCommandConverterTest {
    private static final int CUSTOMER_ID = 11;
    private static final BigDecimal TOTAL_PRICE = BigDecimal.valueOf(30.0);
    private static final Map<Integer, Integer> AMOUNT_TO_ARTICLE = new HashMap<>();

    private final String jsonTestSubject = "{\"customerId\":11,\"amountToArticle\":{},\"totalPrice\":30.0}";

    @Test
    public void testOrderCommandConverterCustomer() {
        OrderDto orderDto = OrderCreateCommandConverter.fromBytes(
                jsonTestSubject.getBytes(StandardCharsets.UTF_8));

        assertEquals(CUSTOMER_ID, orderDto.getCustomerId());
    }

    @Test
    public void testOrderCommandConverterTotalPrice() {
        OrderDto orderDto = OrderCreateCommandConverter.fromBytes(
                jsonTestSubject.getBytes(StandardCharsets.UTF_8));

        assertEquals(TOTAL_PRICE, orderDto.getTotalPrice());
    }

    @Test
    public void testOrderCommandConverterAmountToArticle() {
        OrderDto orderDto = OrderCreateCommandConverter.fromBytes(
                jsonTestSubject.getBytes(StandardCharsets.UTF_8));

        assertEquals(AMOUNT_TO_ARTICLE, orderDto.getAmountToArticle());
    }
}