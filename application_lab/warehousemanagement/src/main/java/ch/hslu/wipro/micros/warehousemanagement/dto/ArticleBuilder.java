package ch.hslu.wipro.micros.warehousemanagement.dto;

import java.math.BigDecimal;

public class ArticleBuilder {
    private String id;
    private String name;
    private String description;
    private BigDecimal price;

    public ArticleBuilder atId(String id) {
        this.id = id;
        return this;
    }

    public ArticleBuilder atName(String name) {
        this.name = name;
        return this;
    }

    public ArticleBuilder atDescription(String description) {
        this.description = description;
        return this;
    }

    public ArticleBuilder atPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public ArticleDto build() {
        ArticleDto articleDto = new ArticleDto();
        articleDto.setId(id);
        articleDto.setName(name);
        articleDto.setDescription(description);
        articleDto.setPrice(price);

        return articleDto;
    }
}