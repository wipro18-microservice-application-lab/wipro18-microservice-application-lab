package ch.hslu.wipro.micros.customermanagement.repository;

public class CustomerRepositorySingleton {
    private static CustomerRepository customerRepository;

    private CustomerRepositorySingleton() {
    }

    public static CustomerRepository getCustomerRepository() {
        if (customerRepository == null) {
            customerRepository = new CustomerRepositoryVolatile();
        }

        return customerRepository;
    }
}
