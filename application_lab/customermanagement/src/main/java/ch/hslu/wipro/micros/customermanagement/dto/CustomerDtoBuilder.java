package ch.hslu.wipro.micros.customermanagement.dto;

public class CustomerDtoBuilder {
    private String fullName;
    private String address;
    private String email;

    public CustomerDtoBuilder atFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public CustomerDtoBuilder atAddress(String address) {
        this.address = address;
        return this;
    }

    public CustomerDtoBuilder atEmail(String email) {
        this.email = email;
        return this;
    }

    public CustomerDto build() {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setFullName(fullName);
        customerDto.setAddress(address);
        customerDto.setEmail(email);

        return customerDto;
    }
}