package ch.hslu.wipro.micros.model.order;

import java.math.BigDecimal;
import java.util.Map;

public class OrderDto {
    private final String correlationId;
    private int customerId;
    private Map<Integer, Integer> amountToArticleMap;
    private BigDecimal totalPrice;

    OrderDto(String correlationId) {
        this.correlationId = correlationId;
    }

    void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    void setAmountToArticleMap(Map<Integer, Integer> amountToArticleMap) {
        this.amountToArticleMap = amountToArticleMap;
    }

    void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public Map<Integer, Integer> getAmountToArticleMap() {
        return amountToArticleMap;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
}