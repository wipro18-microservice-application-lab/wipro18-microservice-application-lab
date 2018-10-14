package ch.hslu.wipro.micros.warehousemanagement.repository;

public class WarehouseRepositoryFactory {
    private static final WarehouseRepository warehouseRepository = new FakeWarehouseRepository();

    private WarehouseRepositoryFactory() {}

    public static WarehouseRepository getRepository() {
        return warehouseRepository;
    }
}