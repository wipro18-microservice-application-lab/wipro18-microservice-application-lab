package ch.hslu.wipro.micros.service.sales;

public class UpdateOrderDTO {
    private long orderId;
    private String status;

    public UpdateOrderDTO() {
        orderId = 0L;
        status = null;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
