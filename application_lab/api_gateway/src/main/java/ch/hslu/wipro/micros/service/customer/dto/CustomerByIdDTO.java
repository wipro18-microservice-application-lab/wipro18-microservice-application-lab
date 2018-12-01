package ch.hslu.wipro.micros.service.customer.dto;

public class CustomerByIdDTO {

    private long customerId;

    public CustomerByIdDTO() {
        customerId = 0L;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }
}
