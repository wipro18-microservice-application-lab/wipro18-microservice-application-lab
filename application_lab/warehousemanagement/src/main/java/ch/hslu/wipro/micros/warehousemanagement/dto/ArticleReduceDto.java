package ch.hslu.wipro.micros.warehousemanagement.dto;

import java.util.Map;

public class ArticleReduceDto {
    private Map<Long, Integer> amountToArticle;

    public Map<Long, Integer> getAmountToArticle() {
        return amountToArticle;
    }

    public void setAmountToArticle(Map<Long, Integer> amountToArticle) {
        this.amountToArticle = amountToArticle;
    }
}
