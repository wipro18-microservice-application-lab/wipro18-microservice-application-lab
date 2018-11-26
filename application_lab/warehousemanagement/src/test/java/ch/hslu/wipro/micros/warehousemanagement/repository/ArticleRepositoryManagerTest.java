package ch.hslu.wipro.micros.warehousemanagement.repository;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ArticleRepositoryManagerTest {
    private static final int amountToGenerate = 50;
    private ArticleRepository articleRepository = ArticleRepositorySingleton.getArticleRepository();

    @Before
    public void setUp() throws Exception {
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
}