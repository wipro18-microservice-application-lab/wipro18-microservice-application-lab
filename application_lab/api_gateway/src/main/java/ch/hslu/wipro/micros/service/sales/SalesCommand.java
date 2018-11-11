package ch.hslu.wipro.micros.service.sales;

import com.google.gson.Gson;

public class SalesCommand {

    private String label;
    private OrderDTO orderDTO;

    public SalesCommand() {}

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public OrderDTO getOrderDTO() {
        return orderDTO;
    }

    public void setOrderDTO(OrderDTO orderDTO) {
        this.orderDTO = orderDTO;
    }

    public String toJson() {
        return new Gson().toJson(orderDTO);
    }
}
