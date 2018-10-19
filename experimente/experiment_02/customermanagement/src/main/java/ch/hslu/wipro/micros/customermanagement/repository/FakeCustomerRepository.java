package ch.hslu.wipro.micros.customermanagement.repository;

import ch.hslu.wipro.micros.common.dto.CustomerDto;

import java.util.HashMap;

public class FakeCustomerRepository implements CustomerRepository {
    private HashMap<Long, CustomerDto> inventory;

    public FakeCustomerRepository() {
        inventory = new HashMap<>();
        inventory.put(0L, new CustomerDto("Alan", "Meile"));
        inventory.put(1L, new CustomerDto("Alan", "Meile"));
    }

    @Override
    public CustomerOperation getCustomerById(long id) {
        if (inventory.containsKey(id)) {
            return new CustomerOperation(true, inventory.get(id));
        }

        return new CustomerOperation(false, null);
    }
}
