package ch.hslu.wipro.micros.common.message;

public class RequestOperation<T> {
    private boolean successful;
    private long requestedId;
    private T requestedDto;

    public RequestOperation(boolean successful, long requestedId, T requestedDto) {
        this.successful = successful;
        this.requestedId = requestedId;
        this.requestedDto = requestedDto;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public long getRequestedId() {
        return requestedId;
    }

    public T getDto() {
        return requestedDto;
    }
}
