package ch.hslu.wipro.micros.warehousemanagement.eventsourcing;

import ch.hslu.wipro.micros.common.command.ChangeArticleStockCommand;
import ch.hslu.wipro.micros.warehousemanagement.repository.WarehouseRepositoryFactory;
import org.junit.Before;
import org.junit.Test;

import static ch.hslu.wipro.micros.common.message.WarehouseCommandState.*;
import static org.junit.Assert.assertEquals;

public class EventBrokerTest {
    private static final long APPLE_ID = 0;
    private static final long UNKNOWN_ID = Long.MAX_VALUE;
    private EventBroker eb;

    @Before
    public void setUp() {
        eb = EventBrokerFactory.getBroker();
        eb.commands.close();
        eb.commands.subscribe(WarehouseRepositoryFactory.getRepository()::brokerOnCommands);
    }

    @Test
    public void testSuccessfulArticleChangeCommand() {
        eb.command(new ChangeArticleStockCommand(APPLE_ID, 5));
        assertEquals(SUCCESS, eb.getCommandStatus());
    }

    @Test
    public void testNotEnoughArticleChangeCommand() {
        eb.command(new ChangeArticleStockCommand(APPLE_ID, Integer.MAX_VALUE));
        assertEquals(NOT_ENOUGH_IN_STOCK, eb.getCommandStatus());
    }

    @Test
    public void testUnknownIdArticleChangeCommand() {
        eb.command(new ChangeArticleStockCommand(UNKNOWN_ID, 1));
        assertEquals(UNKNOWN_ARTICLE_ID, eb.getCommandStatus());
    }

    @Test
    public void testUnknownCommandArticleChangeCommand() {
        eb.command(null);
        assertEquals(UNKNOWN_COMMAND, eb.getCommandStatus());
    }
}