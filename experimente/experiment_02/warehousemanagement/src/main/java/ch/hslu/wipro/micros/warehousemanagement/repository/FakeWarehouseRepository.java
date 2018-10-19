package ch.hslu.wipro.micros.warehousemanagement.repository;

import ch.hslu.wipro.micros.common.command.ChangeArticleStockCommand;
import ch.hslu.wipro.micros.common.command.WarehouseCommand;
import ch.hslu.wipro.micros.common.dto.ArticleDto;
import ch.hslu.wipro.micros.warehousemanagement.eventsourcing.EventBroker;
import ch.hslu.wipro.micros.warehousemanagement.eventsourcing.event.ArticleStockChangedEvent;

import java.util.HashMap;

import static ch.hslu.wipro.micros.common.message.WarehouseCommandState.*;

public class FakeWarehouseRepository implements WarehouseRepository {
    private HashMap<Long, ArticleStock> inventory;

    FakeWarehouseRepository() {
        inventory = new HashMap<>();
        inventory.put(0L, new ArticleStock(new ArticleDto("Apple", 100), 5));
        inventory.put(1L, new ArticleStock(new ArticleDto("Banana", 150), 4));
        inventory.put(2L, new ArticleStock(new ArticleDto("Pineapple", 150), 3));
        inventory.put(3L, new ArticleStock(new ArticleDto("Hamster", 2050), 2));
        inventory.put(4L, new ArticleStock(new ArticleDto("Cat", 15050), 1));
        inventory.put(5L, new ArticleStock(new ArticleDto("Dog", 35050), 0));
    }

    @Override
    public void brokerOnCommands(Object source, WarehouseCommand command) {
        EventBroker eb = ((EventBroker) source);

        if (command instanceof ChangeArticleStockCommand) {
            ChangeArticleStockCommand casc = (ChangeArticleStockCommand) command;

            if (inventory.containsKey(casc.articleId)) {
                ArticleStock articleStock = inventory.get(casc.articleId);
                int oldAmount = articleStock.getAmount();

                if (oldAmount - casc.amount >= 0) {
                    int newAmount = oldAmount - casc.amount;
                    articleStock.setAmount(newAmount);

                    eb.allEvents.add(new ArticleStockChangedEvent(casc.articleId, oldAmount, newAmount));

                    eb.warehouseOperationStatus.state = SUCCESS;
                } else {
                    eb.warehouseOperationStatus.state = NOT_ENOUGH_IN_STOCK;
                }
            } else {
                eb.warehouseOperationStatus.state = UNKNOWN_ARTICLE_ID;
            }
        } else {
            eb.warehouseOperationStatus.state = UNKNOWN_COMMAND;
        }
    }
}
