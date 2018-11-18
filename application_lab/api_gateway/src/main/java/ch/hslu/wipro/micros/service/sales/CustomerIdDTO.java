package ch.hslu.wipro.micros.service.sales;

public class CustomerIdDTO {
    private long customerId;

    public CustomerIdDTO() {
        customerId = 0L;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }
}
