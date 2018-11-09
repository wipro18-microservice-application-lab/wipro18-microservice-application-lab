package ch.hslu.wipro.micros.service.sales;

public class CommandFactory {

    public static SalesCommand createCreateOrderCommand(OrderDTO orderDTO) {
        String label = "order.command.create";
        SalesCommand command = new SalesCommand();
        command.setLabel(label);
        command.setOrderDTO(orderDTO);
        return command;
    }
}
