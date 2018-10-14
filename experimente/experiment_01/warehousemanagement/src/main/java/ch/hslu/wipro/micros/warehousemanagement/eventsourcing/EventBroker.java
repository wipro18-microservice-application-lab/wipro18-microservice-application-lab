package ch.hslu.wipro.micros.warehousemanagement.eventsourcing;

import ch.hslu.wipro.micros.common.message.WarehouseCommandState;
import ch.hslu.wipro.micros.warehousemanagement.WarehouseOperationStatus;
import ch.hslu.wipro.micros.common.command.WarehouseCommand;
import ch.hslu.wipro.micros.warehousemanagement.eventsourcing.event.WarehouseEvent;
import ch.hslu.wipro.micros.warehousemanagement.eventsourcing.query.WarehouseQuery;

import java.util.ArrayList;
import java.util.List;

public class EventBroker {
    public List<WarehouseEvent> allEvents = new ArrayList<>();
    public EventHandler<WarehouseCommand> commands = new EventHandler<>();
    public EventHandler<WarehouseQuery> queries = new EventHandler<>();
    public WarehouseOperationStatus warehouseOperationStatus = new WarehouseOperationStatus();

    EventBroker() {
    }

    public void command(WarehouseCommand c) {
        commands.invoke(this, c);
    }

    public <T extends WarehouseQuery<T>> WarehouseQuery<T> query(WarehouseQuery<T> q) {
        return q.getResult();
    }

    public void undoLast() {
        WarehouseEvent e = allEvents.get(allEvents.size() - 1);
    }

    public WarehouseCommandState getCommandStatus() {
        return warehouseOperationStatus.state;
    }
}