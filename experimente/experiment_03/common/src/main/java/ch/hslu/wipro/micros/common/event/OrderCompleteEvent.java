package ch.hslu.wipro.micros.common.event;

import com.google.gson.annotations.SerializedName;

public class OrderCompleteEvent extends Event {
    @SerializedName("type")
    public final String commandType;

    public OrderCompleteEvent() {
        this.commandType = getClass().getName();
    }
}