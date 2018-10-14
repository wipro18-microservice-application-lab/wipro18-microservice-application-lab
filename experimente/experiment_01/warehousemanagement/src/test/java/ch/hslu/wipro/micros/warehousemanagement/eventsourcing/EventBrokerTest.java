package ch.hslu.wipro.micros.warehousemanagement.eventsourcing;

import ch.hslu.wipro.micros.warehousemanagement.WarehouseOperationStatus;
import ch.hslu.wipro.micros.warehousemanagement.eventsourcing.command.ChangeArticleStockCommand;
import ch.hslu.wipro.micros.warehousemanagement.repository.WarehouseRepository;
import ch.hslu.wipro.micros.warehousemanagement.repository.WarehouseRepositoryFactory;
import org.junit.Before;
import org.junit.Test;

import static ch.hslu.wipro.micros.warehousemanagement.WarehouseStatusEnum.*;
import static org.junit.Assert.assertEquals;

public class EventBrokerTest {
    private static final long APPLE_ID = 0;
    private static final long UNKNOWN_ID = Long.MAX_VALUE;
    private EventBroker eb;
    private WarehouseRepository warehouseRepository;
    private WarehouseOperationStatus warehouseOperationStatus;

    @Before
    public void setUp() {
        warehouseOperationStatus = new WarehouseOperationStatus();
        eb = new EventBroker(warehouseOperationStatus);
        warehouseRepository = WarehouseRepositoryFactory.getRepository();
        eb.commands.subscribe(warehouseRepository::brokerOnCommands);
    }

    @Test
    public void testSuccessfulArticleChangeCommand() {
        eb.command(new ChangeArticleStockCommand(APPLE_ID, 5));
        assertEquals(SUCCESS, warehouseOperationStatus.state);
    }

    @Test
    public void testNotEnoughArticleChangeCommand() {
        eb.command(new ChangeArticleStockCommand(APPLE_ID, Integer.MAX_VALUE));
        assertEquals(NOT_ENOUGH_IN_STOCK, warehouseOperationStatus.state);
    }

    @Test
    public void testUnknownIdArticleChangeCommand() {
        eb.command(new ChangeArticleStockCommand(UNKNOWN_ID, 1));
        assertEquals(UNKNOWN_ARTICLE_ID, warehouseOperationStatus.state);
    }

    @Test
    public void testUnknownCommandArticleChangeCommand() {
        eb.command(null);
        assertEquals(UNKNOWN_COMMAND, warehouseOperationStatus.state);
    }
}