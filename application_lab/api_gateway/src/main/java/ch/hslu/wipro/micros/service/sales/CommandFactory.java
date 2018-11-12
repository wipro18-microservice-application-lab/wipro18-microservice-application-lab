package ch.hslu.wipro.micros.service.sales;

public class CommandFactory {

    public static SalesCommand createCreateOrderCommand(OrderDTO orderDTO) {
        String label = "order.command.create";
        SalesCommand command = new SalesCommand();
        command.setLabel(label);
        command.setOrderDTO(orderDTO);
        return command;
    }

    public static SalesCommand createCreateArticleCommand(OrderDTO orderDTO) {
        String label = "article.command.inventory";
        SalesCommand command = new SalesCommand();
        command.setLabel(label);
        command.setOrderDTO(orderDTO);
        return command;
    }
}
