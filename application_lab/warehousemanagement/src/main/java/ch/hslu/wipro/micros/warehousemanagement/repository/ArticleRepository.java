package ch.hslu.wipro.micros.warehousemanagement.repository;

import ch.hslu.wipro.micros.warehousemanagement.dto.ArticleCheckQuantityResultDto;
import ch.hslu.wipro.micros.warehousemanagement.dto.ArticleDto;

import java.util.List;

public interface ArticleRepository {
    void create(ArticleDto articleDto);

    ArticleCheckQuantityResultDto checkQuantity(long articleId, int quantity);

    List<ArticleDto> getAll();

    ArticleDto getById(long articleId);
}
