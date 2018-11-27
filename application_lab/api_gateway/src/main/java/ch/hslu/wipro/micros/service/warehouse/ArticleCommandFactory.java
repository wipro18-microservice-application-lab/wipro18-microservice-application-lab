package ch.hslu.wipro.micros.service.warehouse;

import ch.hslu.wipro.micros.rabbit.Command;
import ch.hslu.wipro.micros.service.MessageManager;
import ch.hslu.wipro.micros.service.warehouse.dtos.ArticleIdDTO;

public class ArticleCommandFactory {

    private static MessageManager messageManager = ArticleMessageDomainFactory.getMessageManager();

    private ArticleCommandFactory() {

    }

    public static Command<String> createGetAllArticleCommand() {
        Command<String> command = new Command<>();
        String routingKey = messageManager.getCommandKey("getAll");
        command.setRoutingKey(routingKey);
        command.setPayload("");
        command.setToExchange(messageManager.getExchange());
        return command;
    }

    public static Command<ArticleIdDTO> createGetByIdCommand(ArticleIdDTO dto) {
        Command<ArticleIdDTO> command = new Command<>();
        String routingKey = messageManager.getCommandKey("getById");
        command.setRoutingKey(routingKey);
        command.setPayload(dto);
        command.setToExchange(messageManager.getExchange());
        return command;
    }
}
