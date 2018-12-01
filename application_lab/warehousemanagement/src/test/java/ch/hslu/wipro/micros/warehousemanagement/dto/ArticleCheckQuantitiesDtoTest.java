package ch.hslu.wipro.micros.warehousemanagement.dto;

import ch.hslu.wipro.micros.warehousemanagement.converter.JsonConverterFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class ArticleCheckQuantitiesDtoTest {
    private Map<Long, Integer> amountToArticles;
    private String articleCheckQuantitiesJson;

    @Before
    public void setUp() throws Exception {
        amountToArticles = new HashMap<>();
        amountToArticles.put(0L, 50);
        amountToArticles.put(1L, 50);
        amountToArticles.put(2L, 150);

        ArticleCheckQuantitiesDto articleCheckQuantitiesDto = new ArticleCheckQuantitiesDto();
        articleCheckQuantitiesDto.setAmountToArticle(amountToArticles);

        articleCheckQuantitiesJson = new JsonConverterFactory<ArticleCheckQuantitiesDto>()
                .get().toJson(articleCheckQuantitiesDto);
    }

    @Test
    public void getAmountToArticleTest() {
        ArticleCheckQuantitiesDto articleCheckQuantitiesDto = new JsonConverterFactory<ArticleCheckQuantitiesDto>()
                .get().fromJson(articleCheckQuantitiesJson, ArticleCheckQuantitiesDto.class);

        assertEquals(amountToArticles, articleCheckQuantitiesDto.getAmountToArticle());
    }
}