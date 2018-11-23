package ch.hslu.wipro.micros.entity;

import java.math.BigDecimal;
import java.util.Map;

public class OrderEntity {
    private long orderId;
    private long customerId;
    private Map<Integer, Integer> amountToArticle;
    private BigDecimal totalPrice;

    OrderEntity() {}

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    void setAmountToArticle(Map<Integer, Integer> amountToArticle) {
        this.amountToArticle = amountToArticle;
    }

    void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public long getOrderId() {
        return orderId;
    }

    public long getCustomerId() {
        return customerId;
    }

    public Map<Integer, Integer> getAmountToArticle() {
        return amountToArticle;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
}
