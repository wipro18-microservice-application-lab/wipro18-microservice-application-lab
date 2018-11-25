package ch.hslu.wipro.micros.model.order;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

public class OrderDto {
    private long orderId;
    private long customerId;
    private Map<Integer, Integer> amountToArticle;
    private BigDecimal totalPrice;
    private String status;

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

    public void setStatus(String status) {
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDto that = (OrderDto) o;
        return Objects.equals(orderId, that.orderId) &&
                Objects.equals(customerId, that.customerId) &&
                Objects.equals(amountToArticle, that.amountToArticle) &&
                Objects.equals(totalPrice, that.totalPrice) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                orderId, customerId, amountToArticle, totalPrice, status
        );
    }
}