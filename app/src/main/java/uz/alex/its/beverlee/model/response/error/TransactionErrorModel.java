package uz.alex.its.beverlee.model.response.error;

import androidx.annotation.NonNull;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TransactionErrorModel {
    @Expose
    @SerializedName("errors")
    private final TransactionError transactionError;

    public TransactionErrorModel(final TransactionError transactionError) {
        this.transactionError = transactionError;
    }

    public TransactionError getTransactionError() {
        return transactionError;
    }

    @NonNull
    @Override
    public String toString() {
        return "TransferErrorModel{" +
                "transferError=" + transactionError +
                '}';
    }

    public static class TransactionError {
        @Expose
        @SerializedName("amount")
        private final List<String> amount;

        @Expose
        @SerializedName("pin")
        private final List<String> pin;

        public TransactionError(final List<String> amount, final List<String> pin) {
            this.amount = amount;
            this.pin = pin;
        }

        public List<String> getAmount() {
            return amount;
        }

        public List<String> getPin() {
            return pin;
        }

        @NonNull
        @Override
        public String toString() {
            return "TransactionError{" +
                    "amount='" + amount + '\'' +
                    ", pin='" + pin + '\'' +
                    '}';
        }
    }
}
