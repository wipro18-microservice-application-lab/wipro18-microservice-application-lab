package ch.hslu.wipro.micros.warehousemanagement.repository;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import static org.junit.Assert.assertEquals;

public class ArticleRepositoryManagerTest {
    private static final int amountToGenerate = 50;
    private final ArticleRepository articleRepository = ArticleRepositorySingleton.getArticleRepository();

    @Before
    public void setUp() {
        if (articleRepository.getAll().size() == 0) {
            ArticleRepositoryManager articleRepositoryManager = new ArticleRepositoryManager(articleRepository);
            articleRepositoryManager.generateRandomInventory(amountToGenerate);
        }
    }

    @Test
    public void checkGeneratedInventoryAmount() {
        assertEquals(amountToGenerate, articleRepository.getAll().size());
    }

    @Test
    public void checkGeneratedInventoryFirstArticle() {
        assertEquals("article" + 0, articleRepository.getById(0).getName());
    }

    @Test
    public void checkGeneratedInventoryLastArticle() {
        assertEquals("article" + (amountToGenerate - 1), articleRepository
                .getById(amountToGenerate - 1).getName());
    }

    @Test
    public void testForTheEye() {
        articleRepository.getAll().forEach(a -> System.out.println(a.getPrice()));
    }
}