package ch.hslu.wipro.micros.common.command;

import com.google.gson.annotations.SerializedName;

public class UndoLastCommand implements WarehouseCommand {
    @SerializedName("type")
    public String type;

    public UndoLastCommand() {
        type = this.getClass().getName();
    }
}
