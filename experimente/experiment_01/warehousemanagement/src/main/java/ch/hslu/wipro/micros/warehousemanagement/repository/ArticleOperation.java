package ch.hslu.wipro.micros.warehousemanagement.repository;

import ch.hslu.wipro.micros.common.ArticleDto;

public class ArticleOperation {
    private boolean success;
    private ArticleDto article;

    public ArticleOperation(boolean success, ArticleDto article) {
        this.success = success;
        this.article = article;
    }

    public boolean isSuccess() {
        return success;
    }

    public ArticleDto getArticle() {
        return article;
    }
}
