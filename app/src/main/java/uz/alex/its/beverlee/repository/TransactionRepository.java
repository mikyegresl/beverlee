package uz.alex.its.beverlee.repository;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ListenableWorker;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uz.alex.its.beverlee.api.RetrofitClient;
import uz.alex.its.beverlee.model.transaction.Balance;
import uz.alex.its.beverlee.utils.Constants;

public class TransactionRepository {
    private final Context context;

    public TransactionRepository(final Context context) {
        this.context = context;
    }

    /* Текущий баланс в уе */
    public void getCurrentBalance(final Callback<Balance> callback) {
//        final Constraints constraints = new Constraints.Builder()
//                .setRequiredNetworkType(NetworkType.CONNECTED)
//                .setRequiresDeviceIdle(false)
//                .setRequiresStorageNotLow(false)
//                .setRequiresCharging(false)
//                .setRequiresBatteryNotLow(false)
//                .build();
//        final OneTimeWorkRequest getCurrentBalanceRequest = new OneTimeWorkRequest.Builder(GetCurrentBalanceWorker.class)
//                .setConstraints(constraints)
//                .build();
//        WorkManager.getInstance(context).enqueue(getCurrentBalanceRequest);
//        return getCurrentBalanceRequest.getId();
        RetrofitClient.getInstance(context).getCurrentBalance(callback);
    }

//    /* Поступления/списания за месяц */
//    public UUID getMonthlyBalanceHistory() {
//
//    }
//
//    /* Поступления/списания за месяц по дням */
//    public UUID getMonthlyBalanceHistoryByDays() {
//
//    }
//
//    public UUID withdrawFunds() {
//
//    }
//
//    public UUID transferFunds() {
//
//    }
//
//    public UUID verifyTransfer() {
//
//    }
//
//    public UUID getTransactionHistory() {
//
//    }

    private static final String TAG = TransactionRepository.class.toString();
}
