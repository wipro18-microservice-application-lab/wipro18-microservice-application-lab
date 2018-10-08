package ch.hslu.wipro.micros.common;

public class OrderDto {
    private ArticleDto article;
    private CustomerDto customer;
    private double total;

    public OrderDto(ArticleDto article, CustomerDto customer) {
        this.article = article;
        this.customer = customer;
        this.total = article.getPrice();
    }

    public ArticleDto getArticle() {
        return article;
    }

    public CustomerDto getCustomer() {
        return customer;
    }

    public double getTotal() {
        return total;
    }
}
