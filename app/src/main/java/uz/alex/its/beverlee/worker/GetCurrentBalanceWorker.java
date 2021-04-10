//package uz.alex.its.beverlee.worker;
//
//import android.content.Context;
//import android.util.Log;
//
//import androidx.annotation.NonNull;
//import androidx.concurrent.futures.CallbackToFutureAdapter;
//import androidx.work.Data;
//import androidx.work.ListenableWorker;
//import androidx.work.Worker;
//import androidx.work.WorkerParameters;
//import com.google.common.util.concurrent.ListenableFuture;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import uz.alex.its.beverlee.api.RetrofitClient;
//import uz.alex.its.beverlee.model.transaction.Balance;
//import uz.alex.its.beverlee.utils.Constants;
//
//public class GetCurrentBalanceWorker extends Worker {
//
//    public GetCurrentBalanceWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
//        super(context, workerParams);
//    }
//
//    @NonNull
//    @Override
//    public Result doWork() {
//        RetrofitClient.getInstance(getApplicationContext()).getCurrentBalance(new Callback<Balance>() {
//            @Override
//            public void onResponse(@NonNull Call<Balance> call, @NonNull Response<Balance> response) {
//                if (response.code() == 200 && response.isSuccessful()) {
//                    Log.i(TAG, "onResponse(): success");
//                    final Balance currentBalance = response.body();
//                    Result.success(new Data.Builder().putDouble(Constants.CURRENT_BALANCE, currentBalance.getBalance()).build());
//                }
//                completer.set(Result.failure());
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<Balance> call, @NonNull Throwable t) {
//                Log.e(TAG, "onFailure(): ", t);
//                completer.setException(t);
//            }
//        });
//    }
//
//    private static final String TAG = GetCurrentBalanceWorker.class.toString();
//}
