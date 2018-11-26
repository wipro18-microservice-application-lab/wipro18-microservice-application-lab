package ch.hslu.wipro.micros.warehousemanagement.dto;

public class ArticleCheckQuantityDto {

    private long articleId;
    private int quantity;

    public long getArticleId() {
        return articleId;
    }

    public void setArticleId(long articleId) {
        this.articleId = articleId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
