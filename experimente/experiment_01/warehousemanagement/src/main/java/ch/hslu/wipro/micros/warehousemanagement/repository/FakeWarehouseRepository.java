package ch.hslu.wipro.micros.warehousemanagement.repository;

import ch.hslu.wipro.micros.common.ArticleDto;

import java.util.HashMap;

public class FakeWarehouseRepository implements WarehouseRepository {
    private HashMap<Double, ArticleDto> inventory;

    public FakeWarehouseRepository() {
        inventory = new HashMap<>();
        inventory.put(0d, new ArticleDto("Apple", 100d));
        inventory.put(1d, new ArticleDto("Banana", 150d));
        inventory.put(2d, new ArticleDto("Pineapple", 150d));
        inventory.put(3d, new ArticleDto("Hamster", 2050d));
        inventory.put(4d, new ArticleDto("Cat", 15050d));
        inventory.put(5d, new ArticleDto("Dog", 35050d));
    }

    @Override
    public ArticleOperation getArticleDtoById(double id) {
        if (inventory.containsKey(id)) {
            return new ArticleOperation(true, inventory.get(id));
        }

        return new ArticleOperation(false, null);
    }
}
