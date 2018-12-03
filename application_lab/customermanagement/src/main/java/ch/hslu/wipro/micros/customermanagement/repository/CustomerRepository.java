package ch.hslu.wipro.micros.customermanagement.repository;

import ch.hslu.wipro.micros.customermanagement.dto.CustomerDto;
import ch.hslu.wipro.micros.customermanagement.dto.CustomerFlagResultDto;

import java.util.List;

public interface CustomerRepository {
    void create(CustomerDto customerDto);

    List<CustomerDto> getAll();

    CustomerDto getById(long customerId);

    CustomerFlagResultDto flagCustomerById(long customerId);
}
