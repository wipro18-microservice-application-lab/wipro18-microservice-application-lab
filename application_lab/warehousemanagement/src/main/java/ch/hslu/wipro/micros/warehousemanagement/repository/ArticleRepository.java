package ch.hslu.wipro.micros.warehousemanagement.repository;

import ch.hslu.wipro.micros.warehousemanagement.dto.ArticleCheckQuantityResultDto;
import ch.hslu.wipro.micros.warehousemanagement.dto.ArticleDto;
import ch.hslu.wipro.micros.warehousemanagement.dto.ArticleReduceResultDto;

import java.util.List;
import java.util.Map;

public interface ArticleRepository {
    void create(ArticleDto articleDto);

    ArticleCheckQuantityResultDto checkQuantity(long articleId, int quantity);

    List<ArticleDto> getAll();

    ArticleDto getById(long articleId);

    ArticleCheckQuantityResultDto checkQuantities(Map<Long, Integer> amountToArticle);

    ArticleReduceResultDto reduceQuantity(Map<Long, Integer> amountToArticle);
}
