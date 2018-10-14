package ch.hslu.wipro.micros.warehousemanagement.eventsourcing.event;

import ch.hslu.wipro.micros.warehousemanagement.consumer.ArticleRequestConsumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ArticleStockChangedEvent implements WarehouseEvent {
    private static final Logger logger = LogManager.getLogger(ArticleRequestConsumer.class);
    private long articleId;
    private int oldValue, newValue;

    public ArticleStockChangedEvent(long articleId, int oldValue, int newValue) {
        this.articleId = articleId;
        this.oldValue = oldValue;
        this.newValue = newValue;

        logger.info(this);
    }

    @Override
    public String toString() {
        return String.format("article stock with id %d changed from %d to %d",
                articleId, oldValue, newValue);
    }
}
