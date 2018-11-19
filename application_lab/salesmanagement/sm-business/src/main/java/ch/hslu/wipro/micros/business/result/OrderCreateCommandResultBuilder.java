package ch.hslu.wipro.micros.business.result;

import ch.hslu.wipro.micros.model.order.OrderDto;

public class OrderCreateCommandResultBuilder {
    private OrderDto orderDto;
    private String result;

    public OrderCreateCommandResultBuilder withOrder(OrderDto orderDto) {
        this.orderDto = orderDto;
        return this;
    }

    public OrderCreateCommandResultBuilder withResult(String result) {
        this.result = result;
        return this;
    }

    public OrderCreateCommandResult build() {
        OrderCreateCommandResult orderCreateCommandResult = new OrderCreateCommandResult();
        orderCreateCommandResult.setOrderDto(orderDto);
        orderCreateCommandResult.setResult(result);

        return orderCreateCommandResult;
    }
}