package ch.hslu.wipro.micros.warehousemanagement.repository;

import ch.hslu.wipro.micros.common.dto.ArticleDto;

class ArticleStock {
    private ArticleDto article;
    private int amount;

    ArticleStock(ArticleDto article, int amount) {
        this.article = article;
        this.amount = amount;
    }

    ArticleDto getArticle() {
        return article;
    }

    int getAmount() {
        return amount;
    }

    void setAmount(int amount) {
        this.amount = amount;
    }
}
