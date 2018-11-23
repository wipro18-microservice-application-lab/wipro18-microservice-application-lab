package ch.hslu.wipro.micros.entity;

import java.math.BigDecimal;
import java.util.Map;

public class OrderEntityBuilder {
    private long customerId;
    private Map<Integer, Integer> amountToArticleMap;
    private BigDecimal totalPrice;

    public OrderEntityBuilder atCustomer(long customerId) {
        this.customerId = customerId;
        return this;
    }

    public OrderEntityBuilder mapAmountToArticle(Map<Integer, Integer> amountToArticleMap) {
        this.amountToArticleMap = amountToArticleMap;
        return this;
    }

    public OrderEntityBuilder atTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public OrderEntity build() {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setCustomerId(customerId);
        orderEntity.setAmountToArticle(amountToArticleMap);
        orderEntity.setTotalPrice(totalPrice);

        return orderEntity;
    }
}