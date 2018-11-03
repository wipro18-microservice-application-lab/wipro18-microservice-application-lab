package ch.hslu.wipro.micros.common.command;

import ch.hslu.wipro.micros.common.dto.OrderDto;
import com.google.gson.annotations.SerializedName;

public class CheckOrderCommand extends Command {

    @SerializedName("type")
    public final String commandType;

    private final OrderDto orderDto;

    public CheckOrderCommand(OrderDto orderDto) {
        this.commandType = this.getClass().getName();
        this.orderDto = orderDto;
    }

    public OrderDto getOrderDto() {
        return orderDto;
    }
}
