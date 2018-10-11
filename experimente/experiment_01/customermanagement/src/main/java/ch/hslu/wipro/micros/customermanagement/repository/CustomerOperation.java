package ch.hslu.wipro.micros.customermanagement.repository;

import ch.hslu.wipro.micros.common.dto.CustomerDto;

public class CustomerOperation {
    private boolean success;
    private CustomerDto customer;

    public CustomerOperation(boolean success, CustomerDto article) {
        this.success = success;
        this.customer = article;
    }

    public boolean isSuccess() {
        return success;
    }

    public CustomerDto getCustomer() {
        return customer;
    }
}
