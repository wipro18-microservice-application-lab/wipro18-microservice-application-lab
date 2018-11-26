package ch.hslu.wipro.micros.warehousemanagement.dto;

import java.math.BigDecimal;

public class ArticleDtoBuilder {
    private long articleId;
    private String name;
    private String description;
    private BigDecimal price;
    private int quantity;

    public ArticleDtoBuilder atArticleId(long articleId) {
        this.articleId = articleId;
        return this;
    }

    public ArticleDtoBuilder atName(String name) {
        this.name = name;
        return this;
    }

    public ArticleDtoBuilder atDescription(String description) {
        this.description = description;
        return this;
    }

    public ArticleDtoBuilder atPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public ArticleDtoBuilder atQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public ArticleDto build() {
        ArticleDto articleDto = new ArticleDto();
        articleDto.setArticleId(articleId);
        articleDto.setName(name);
        articleDto.setDescription(description);
        articleDto.setPrice(price);
        articleDto.setQuantity(quantity);

        return articleDto;
    }
}