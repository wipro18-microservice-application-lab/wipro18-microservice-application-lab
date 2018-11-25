package ch.hslu.wipro.micros.service.warehouse;

import ch.hslu.wipro.micros.service.MessageManager;

public class ArticleMessageManager extends MessageManager {

    public ArticleMessageManager() {
        super();
    }

    @Override
    public void prepareMessageDomain() {
        setExchange("ch.hslu.wipro.micros.Article");
        addCommandKey("article.command.getAll");
        addCommandKey("article.command.getById");
    }
}
