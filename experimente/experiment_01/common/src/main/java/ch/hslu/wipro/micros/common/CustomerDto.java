package ch.hslu.wipro.micros.common;

public class CustomerDto {
    private String firstName, lastName;

    public CustomerDto(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
