package ch.hslu.wipro.micros.model.order;

import java.math.BigDecimal;
import java.util.Map;

public class OrderBuilder {
    private String correlationId;
    private int customerId;
    private Map<Integer, Integer> amountToArticleMap;
    private BigDecimal totalPrice;

    public OrderBuilder withCorrelationId(String correlationId) {
        this.correlationId = correlationId;
        return this;
    }

    public OrderBuilder atCustomer(int customerId) {
        this.customerId = customerId;
        return this;
    }

    public OrderBuilder mapAmountToArticle(Map<Integer, Integer> amountToArticleMap) {
        this.amountToArticleMap = amountToArticleMap;
        return this;
    }

    public OrderBuilder atTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public OrderDto build() {
        OrderDto orderDto = new OrderDto(correlationId);
        orderDto.setCustomerId(customerId);
        orderDto.setAmountToArticleMap(amountToArticleMap);
        orderDto.setTotalPrice(totalPrice);

        return orderDto;
    }
}