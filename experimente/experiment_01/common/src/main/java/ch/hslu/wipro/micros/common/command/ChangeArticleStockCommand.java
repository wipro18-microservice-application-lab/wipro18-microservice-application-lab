package ch.hslu.wipro.micros.common.command;

import com.google.gson.annotations.SerializedName;

public class ChangeArticleStockCommand implements WarehouseCommand {
    @SerializedName("type")
    public String type;
    public long articleId;
    public int amount;

    public ChangeArticleStockCommand(long articleId, int amount) {
        this.articleId = articleId;
        this.amount = amount;

        type = this.getClass().getName();
    }
}
