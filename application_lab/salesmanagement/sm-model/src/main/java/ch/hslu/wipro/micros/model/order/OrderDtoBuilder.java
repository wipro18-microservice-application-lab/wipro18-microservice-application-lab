package ch.hslu.wipro.micros.model.order;

import java.math.BigDecimal;
import java.util.Map;

public class OrderDtoBuilder {
    private long orderId;
    private long customerId;
    private Map<Long, Integer> amountToArticleMap;
    private BigDecimal totalPrice;
    private String status;

    public OrderDtoBuilder atOrderId(long orderId) {
        this.orderId = orderId;
        return this;
    }

    public OrderDtoBuilder atCustomer(long customerId) {
        this.customerId = customerId;
        return this;
    }

    public OrderDtoBuilder mapAmountToArticle(Map<Long, Integer> amountToArticleMap) {
        this.amountToArticleMap = amountToArticleMap;
        return this;
    }

    public OrderDtoBuilder atTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public OrderDtoBuilder atStatus(String status) {
        this.status = status;
        return this;
    }

    public OrderDto build() {
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderId(orderId);
        orderDto.setCustomerId(customerId);
        orderDto.setAmountToArticle(amountToArticleMap);
        orderDto.setTotalPrice(totalPrice);
        orderDto.setStatus(status);

        return orderDto;
    }
}