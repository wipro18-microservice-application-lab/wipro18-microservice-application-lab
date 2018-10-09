package ch.hslu.wipro.micros.warehousemanagement.repository;

public interface WarehouseRepository {

    /**
     * Returns a flagged ArticleOperation containing information about the success of the operation
     * and the requested articleDto.
     *
     * @param id    the id of the article requested.
     * @return      ArticleOperation containing success and articleDto.
     */
    ArticleOperation getArticleDtoById(double id);
}
