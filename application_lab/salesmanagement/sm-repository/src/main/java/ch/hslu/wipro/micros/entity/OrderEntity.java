package ch.hslu.wipro.micros.entity;

import java.math.BigDecimal;
import java.util.Map;

public class OrderEntity {
    private long orderId;
    private long customerId;
    private Map<Long, Integer> amountToArticle;
    private BigDecimal totalPrice;
    private String status;

    OrderEntity() {}

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    void setAmountToArticle(Map<Long, Integer> amountToArticle) {
        this.amountToArticle = amountToArticle;
    }

    void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    void setStatus(String status) {
        this.status = status;
    }

    public long getOrderId() {
        return orderId;
    }

    public long getCustomerId() {
        return customerId;
    }

    public Map<Long, Integer> getAmountToArticle() {
        return amountToArticle;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public String getStatus() {
        return status;
    }
}
