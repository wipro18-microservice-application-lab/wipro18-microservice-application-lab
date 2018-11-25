package ch.hslu.wipro.micros.service.warehouse.dtos;

public class ArticleIdDTO {

    private long articleId;

    public ArticleIdDTO() {
        articleId = 0L;
    }

    public long getArticleId() {
        return articleId;
    }

    public void setArticleId(long articleId) {
        this.articleId = articleId;
    }
}
