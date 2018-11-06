package ch.hslu.wipro.micros.common.event;

import com.google.gson.annotations.SerializedName;

public class OrderConfirmedEvent extends Event {

    @SerializedName("type")
    public final String commandType;

    private final String attendant;

    public OrderConfirmedEvent(String attendant) {
        this.commandType = this.getClass().getName();
        this.attendant = attendant;
    }

    public String getAttendant() {
        return attendant;
    }
}
