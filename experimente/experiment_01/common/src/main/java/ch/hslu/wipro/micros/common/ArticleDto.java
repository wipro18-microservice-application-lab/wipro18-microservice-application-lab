package ch.hslu.wipro.micros.common;

public class ArticleDto {
    private String name;
    private double price;

    public ArticleDto(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}
