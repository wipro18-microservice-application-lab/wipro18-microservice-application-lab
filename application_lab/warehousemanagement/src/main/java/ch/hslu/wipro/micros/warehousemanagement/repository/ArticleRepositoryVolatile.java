package ch.hslu.wipro.micros.warehousemanagement.repository;

import ch.hslu.wipro.micros.warehousemanagement.dto.ArticleCheckQuantityResultDto;
import ch.hslu.wipro.micros.warehousemanagement.dto.ArticleDto;
import ch.hslu.wipro.micros.warehousemanagement.dto.ArticleDtoBuilder;
import ch.hslu.wipro.micros.warehousemanagement.dto.ArticleReduceResultDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ArticleRepositoryVolatile implements ArticleRepository {
    private final List<ArticleDto> articleDtos = new ArrayList<>();

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

    @Override
    public ArticleCheckQuantityResultDto checkQuantities(Map<Long, Integer> amountToArticle) {
        ArticleCheckQuantityResultDto articleCheckQuantityResultDto = new ArticleCheckQuantityResultDto();

        String articleCheckQuantityResult = amountToArticle
                .entrySet()
                .stream()
                .map(a -> checkQuantity(a.getKey(), a.getValue()))
                .map(ArticleCheckQuantityResultDto::getResult)
                .collect(Collectors.joining(", "));

        articleCheckQuantityResultDto.setResult(articleCheckQuantityResult);

        return articleCheckQuantityResultDto;
    }

    @Override
    public ArticleReduceResultDto reduceQuantity(long articleId, int quantity) {
        ArticleReduceResultDto articleReduceResultDto = new ArticleReduceResultDto();
        articleReduceResultDto.setResult(ArticleReduceResultDto.SUCCESS);

        articleDtos.stream()
                .filter(a -> a.getArticleId() == articleId)
                .findFirst()
                .ifPresent(a -> a.setQuantity(a.getQuantity() - quantity));

        return articleReduceResultDto;
    }
}
