package uz.alex.its.beverlee.model.transaction;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PurchaseResult {
    @Expose
    @SerializedName("url")
    private final String url;

    @Expose
    @SerializedName("balance")
    private final double balance;

    public PurchaseResult(final String url, final double balance) {
        this.url = url;
        this.balance = balance;
    }

    public String getUrl() {
        return url;
    }

    public double getBalance() {
        return balance;
    }

    @NonNull
    @Override
    public String toString() {
        return "PurchaseResult{" +
                "url='" + url + '\'' +
                ", balance=" + balance +
                '}';
    }
}
