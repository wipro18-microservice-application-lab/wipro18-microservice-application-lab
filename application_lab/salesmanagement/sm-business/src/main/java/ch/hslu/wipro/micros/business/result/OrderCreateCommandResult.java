package ch.hslu.wipro.micros.business.result;

import ch.hslu.wipro.micros.model.order.OrderDto;

public class OrderCreateCommandResult {
    private OrderDto orderDto;
    private String result;

    OrderCreateCommandResult() {}

    void setOrderDto(OrderDto orderDto) {
        this.orderDto = orderDto;
    }

    void setResult(String result) {
        this.result = result;
    }

    public OrderDto getOrderDto() {
        return orderDto;
    }

    public String getResult() {
        return result;
    }
}