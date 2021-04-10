package uz.alex.its.beverlee.model.transaction;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Transaction {
    @Expose
    @SerializedName("id")
    private final long id;

    @Expose
    @SerializedName("type_id")
    private final int typeId;

    @Expose
    @SerializedName("type_title")
    private final String typeTitle;

    @Expose
    @SerializedName("user_id")
    private final long userId;

    @Expose
    @SerializedName("user_fio")
    private final String userFullName;

    @Expose
    @SerializedName("amount")
    private final double amount;

    @Expose
    @SerializedName("is_balance_icrease")
    private final boolean isBalanceIncrease;

    @Expose
    @SerializedName("created_at")
    private final long createdAt;

    public Transaction(final long id,
                       final int typeId,
                       final String typeTitle,
                       final long userId,
                       final String userFullName,
                       final double amount,
                       final boolean isBalanceIncrease,
                       final long createdAt) {
        this.id = id;
        this.typeId = typeId;
        this.typeTitle = typeTitle;
        this.userId = userId;
        this.userFullName = userFullName;
        this.amount = amount;
        this.isBalanceIncrease = isBalanceIncrease;
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public int getTypeId() {
        return typeId;
    }

    public String getTypeTitle() {
        return typeTitle;
    }

    public long getUserId() {
        return userId;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public double getAmount() {
        return amount;
    }

    public boolean isBalanceIncrease() {
        return isBalanceIncrease;
    }

    public long getCreatedAt() {
        return createdAt;
    }
}
