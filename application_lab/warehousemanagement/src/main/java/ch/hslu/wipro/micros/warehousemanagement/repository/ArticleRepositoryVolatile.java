package ch.hslu.wipro.micros.warehousemanagement.repository;

import ch.hslu.wipro.micros.warehousemanagement.dto.ArticleCheckQuantityResultDto;
import ch.hslu.wipro.micros.warehousemanagement.dto.ArticleDto;
import ch.hslu.wipro.micros.warehousemanagement.dto.ArticleDtoBuilder;

import java.util.ArrayList;
import java.util.List;

public class ArticleRepositoryVolatile implements ArticleRepository {
    private List<ArticleDto> articleDtos = new ArrayList<>();

    ArticleRepositoryVolatile() {}

    @Override
    public void create(ArticleDto articleDto) {
        articleDto.setArticleId(articleDtos.size());
        articleDtos.add(articleDto);
    }

    @Override
    public ArticleCheckQuantityResultDto checkQuantity(long articleId, int quantity) {
        ArticleCheckQuantityResultDto articleCheckQuantityResultDto = new ArticleCheckQuantityResultDto();
        articleCheckQuantityResultDto.setResult(ArticleCheckQuantityResultDto.UNKNOWN_ARTICLE_ID);

        articleDtos.stream()
                .filter(a -> a.getArticleId() == articleId)
                .findFirst()
                .ifPresent(a -> {
                    if (a.getQuantity() >= quantity) {
                        articleCheckQuantityResultDto.setResult(ArticleCheckQuantityResultDto.ENOUGH_ARTICLES);
                    } else {
                        articleCheckQuantityResultDto.setResult(ArticleCheckQuantityResultDto.NOT_ENOUGHT_ARTICLES);
                    }
                });

        return articleCheckQuantityResultDto;
    }

    @Override
    public List<ArticleDto> getAll() {
        return articleDtos;
    }

    @Override
    public ArticleDto getById(long articleId) {
        ArticleDto defaultArticleDto = new ArticleDtoBuilder().build();

        return articleDtos.stream()
                .filter(a -> a.getArticleId() == articleId)
                .findFirst()
                .orElse(defaultArticleDto);
    }
}
