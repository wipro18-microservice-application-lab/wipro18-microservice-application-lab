package ch.hslu.wipro.micros.customermanagement.repository;

import ch.hslu.wipro.micros.customermanagement.dto.CustomerDto;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class CustomerRepositoryManagerTest {
    private static final CustomerRepository customerRepository = CustomerRepositorySingleton.getCustomerRepository();
    private static final int CUSTOMER_AMOUNT = 50;

    @Before
    public void setUp() throws Exception {
        if (customerRepository.getAll().size() == 0) {
            CustomerRepositoryManager customerRepositoryManager = new CustomerRepositoryManager(customerRepository);
            customerRepositoryManager.generateCustomers(CUSTOMER_AMOUNT);
        }
    }

    @Test
    public void checkGeneratedCustomerAmount() {
        assertEquals(CUSTOMER_AMOUNT, customerRepository.getAll().size());
    }

    @Test
    public void checkGeneratedCustomerNotEmpty() {
        List<CustomerDto> customerDtos = customerRepository.getAll();

        customerDtos.forEach(customerDto -> {
            assertFalse(customerDto.getFullName().isEmpty());
            assertFalse(customerDto.getAddress().isEmpty());
            assertFalse(customerDto.getEmail().isEmpty());
        });
    }
}