package ch.hslu.wipro.micros.model.order;

public class OrderUpdateDto {
    private long orderId;
    private String status;

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getOrderId() {
        return orderId;
    }

    public String getStatus() {
        return status;
    }
}