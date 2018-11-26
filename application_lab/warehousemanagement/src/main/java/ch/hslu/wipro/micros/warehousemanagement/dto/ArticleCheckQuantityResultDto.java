package ch.hslu.wipro.micros.warehousemanagement.dto;

public class ArticleCheckQuantityResultDto {
    public static final String UNKNOWN_ARTICLE_ID = "unknown article id";
    public static final String ENOUGH_ARTICLES = "enough articles in stock";
    public static final String NOT_ENOUGHT_ARTICLES = "not enough articles in stock";

    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
