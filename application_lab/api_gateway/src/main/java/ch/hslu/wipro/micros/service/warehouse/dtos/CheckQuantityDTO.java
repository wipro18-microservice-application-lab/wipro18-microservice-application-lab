package ch.hslu.wipro.micros.service.warehouse.dtos;

public class CheckQuantityDTO {

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
