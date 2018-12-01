package ch.hslu.wipro.micros.customermanagement.repository;

import ch.hslu.wipro.micros.customermanagement.dto.CustomerDto;

import java.util.List;
import java.util.Map;

public interface CustomerRepository {
    void create(CustomerDto customerDto);

    List<CustomerDto> getAll();

    CustomerDto getById(long customerId);
}
