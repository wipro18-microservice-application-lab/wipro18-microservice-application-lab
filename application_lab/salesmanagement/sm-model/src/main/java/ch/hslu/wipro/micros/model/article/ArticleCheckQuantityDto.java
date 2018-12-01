package ch.hslu.wipro.micros.model.article;

import java.util.Map;

public class ArticleCheckQuantityDto {
    private Map<Long, Integer> amountToArticle;

    public Map<Long, Integer> getAmountToArticle() {
        return amountToArticle;
    }

    public void setAmountToArticle(Map<Long, Integer> amountToArticle) {
        this.amountToArticle = amountToArticle;
    }
}
