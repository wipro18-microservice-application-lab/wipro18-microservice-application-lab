package ch.hslu.wipro.micros.warehousemanagement.repository;

import ch.hslu.wipro.micros.common.command.WarehouseCommand;

public interface WarehouseRepository {
    /**
     * Handle WarehouseCommands issued over the EventBroker.
     * WarehouseRepository needs to be subscribed to the EventBroker.
     *
     * @param source  EventBroker reference
     * @param command WarehouseCommand reference
     */
    void brokerOnCommands(Object source, WarehouseCommand command);
}
