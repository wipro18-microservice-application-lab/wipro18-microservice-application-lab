package ch.hslu.wipro.micros.customermanagement.repository;

import ch.hslu.wipro.micros.customermanagement.dto.CustomerDto;
import ch.hslu.wipro.micros.customermanagement.dto.CustomerFlagResultDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CustomerRepositoryVolatile implements CustomerRepository {
    private final List<CustomerDto> customerDtos = new ArrayList<>();

    CustomerRepositoryVolatile() {}

    @Override
    public void create(CustomerDto customerDto) {
        customerDto.setCustomerId(customerDtos.size());
        customerDtos.add(customerDto);
    }

    @Override
    public List<CustomerDto> getAll() {
        return customerDtos;
    }

    @Override
    public CustomerDto getById(long customerId) {
        CustomerDto defaultCustomerDto = new CustomerDto();

        return customerDtos.stream()
                .filter(a -> a.getCustomerId() == customerId)
                .findFirst()
                .orElse(defaultCustomerDto);
    }

    @Override
    public CustomerFlagResultDto flagCustomerById(long customerId) {
        CustomerFlagResultDto customerFlagResultDto = new CustomerFlagResultDto();
        customerFlagResultDto.setResult("failed");

        customerDtos.stream()
                .filter(c -> c.getCustomerId() == customerId)
                .findFirst()
                .ifPresent(c -> {
                    c.setFlagged(true);
                    customerFlagResultDto.setResult("success");
                });

        return customerFlagResultDto;
    }
}
