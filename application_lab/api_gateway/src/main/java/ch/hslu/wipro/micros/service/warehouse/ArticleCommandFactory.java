package ch.hslu.wipro.micros.service.warehouse;

import ch.hslu.wipro.micros.rabbit.Command;
import ch.hslu.wipro.micros.service.MessageManager;

public class ArticleCommandFactory {

    private static MessageManager messageManager = ArticleMessageDomainFactory.getMessageManager();

    private ArticleCommandFactory() {

    }

    public static Command<String> createGetAllArticleCommand() {
        Command<String> command = new Command<>();
        String routingKey = messageManager.getCommandKey("getAll");
        command.setRoutingKey(routingKey);
        command.setPayload("");
        return command;
    }
}
