package ch.hslu.wipro.micros.warehousemanagement.repository;

public class ArticleRepositorySingleton {
    private static ArticleRepository articleRepository;

    private ArticleRepositorySingleton() {
    }

    public static ArticleRepository getArticleRepository() {
        if (articleRepository == null) {
            articleRepository = new ArticleRepositoryVolatile();
        }

        return articleRepository;
    }
}
