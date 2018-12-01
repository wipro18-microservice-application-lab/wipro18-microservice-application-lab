package ch.hslu.wipro.micros.customermanagement.dto;

public class CustomerCreateResultDto {
    private CustomerDto customerDto;
    private String result;

    public CustomerDto getCustomerDto() {
        return customerDto;
    }

    public void setCustomerDto(CustomerDto customerDto) {
        this.customerDto = customerDto;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
