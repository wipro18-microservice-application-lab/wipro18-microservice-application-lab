package ch.hslu.wipro.micros.model.order;

import java.math.BigDecimal;
import java.util.Map;

public class OrderDto {
    private int customerId;
    private Map<Integer, Integer> amountToArticle;
    private BigDecimal totalPrice;

    void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    void setAmountToArticle(Map<Integer, Integer> amountToArticle) {
        this.amountToArticle = amountToArticle;
    }

    void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getCustomerId() {
        return customerId;
    }

    public Map<Integer, Integer> getAmountToArticle() {
        return amountToArticle;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
}