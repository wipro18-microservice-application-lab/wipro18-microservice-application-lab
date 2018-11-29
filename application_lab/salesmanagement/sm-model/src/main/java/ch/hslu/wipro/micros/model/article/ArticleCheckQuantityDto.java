package ch.hslu.wipro.micros.model.article;

import java.util.Map;

public class ArticleCheckQuantityDto {
    private Map<Integer, Long> amountToArticle;

    public Map<Integer, Long> getAmountToArticle() {
        return amountToArticle;
    }

    public void setAmountToArticle(Map<Integer, Long> amountToArticle) {
        this.amountToArticle = amountToArticle;
    }
}
