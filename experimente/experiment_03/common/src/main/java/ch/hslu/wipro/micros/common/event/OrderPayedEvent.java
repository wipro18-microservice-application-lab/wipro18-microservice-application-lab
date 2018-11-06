package ch.hslu.wipro.micros.common.event;

import com.google.gson.annotations.SerializedName;

public class OrderPayedEvent extends Event {

    @SerializedName("type")
    public final String commandType;

    public OrderPayedEvent() {
        this.commandType = this.getClass().getName();
    }
}