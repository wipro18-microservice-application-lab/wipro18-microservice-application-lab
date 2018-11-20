package ch.hslu.wipro.micros.service.warehouse;

import ch.hslu.wipro.micros.service.MessageManager;

public class ArticleMessageDomainFactory {

    private ArticleMessageDomainFactory() {

    }

    public static MessageManager getMessageManager() {
        MessageManager articleManager = new ArticleMessageManager();
        articleManager.prepareMessageDomain();
        return articleManager;
    }
}
