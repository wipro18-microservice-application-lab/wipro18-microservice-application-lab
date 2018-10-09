package ch.hslu.wipro.micros.warehousemanagement.repository;

import ch.hslu.wipro.micros.common.ArticleDto;

import java.util.HashMap;

public class FakeWarehouseRepository implements WarehouseRepository {
    private HashMap<Long, ArticleDto> inventory;

    public FakeWarehouseRepository() {
        inventory = new HashMap<>();
        inventory.put(0L, new ArticleDto("Apple", 100));
        inventory.put(1L, new ArticleDto("Banana", 150));
        inventory.put(2L, new ArticleDto("Pineapple", 150));
        inventory.put(3L, new ArticleDto("Hamster", 2050));
        inventory.put(4L, new ArticleDto("Cat", 15050));
        inventory.put(5L, new ArticleDto("Dog", 35050));
    }

    @Override
    public ArticleOperation getArticleDtoById(long id) {
        if (inventory.containsKey(id)) {
            return new ArticleOperation(true, inventory.get(id));
        }

        return new ArticleOperation(false, null);
    }
}
