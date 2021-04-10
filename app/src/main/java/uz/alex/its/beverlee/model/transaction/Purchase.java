package uz.alex.its.beverlee.model.transaction;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Purchase {
    @Expose
    @SerializedName("id")
    private final long id;

    @Expose
    @SerializedName("amount")
    private final double amount;

    @Expose
    @SerializedName("description")
    private final String description;

    @Expose
    @SerializedName("created_at")
    private final long createdAt;

    public Purchase(final long id, final double amount, final String description, final long createdAt) {
        this.id = id;
        this.amount = amount;
        this.description = description;
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    @NonNull
    @Override
    public String toString() {
        return "Purchase{" +
                "id=" + id +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
