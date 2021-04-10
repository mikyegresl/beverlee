package uz.alex.its.beverlee.worker;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Response;
import uz.alex.its.beverlee.api.RetrofitClient;
import uz.alex.its.beverlee.model.Token;
import uz.alex.its.beverlee.model.requestParams.AuthParams;
import uz.alex.its.beverlee.utils.Constants;

public class LoginWorker extends Worker {
    private final Context context;

    private final AuthParams authParams;

    public LoginWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);

        this.context = getApplicationContext();
        this.authParams = new AuthParams(getInputData().getString(Constants.PHONE), getInputData().getString(Constants.PASSWORD));
    }

    @NonNull
    @Override
    public Result doWork() {
        final Data.Builder outputDataBuilder = new Data.Builder();

        try {
            final Response<Token> response = RetrofitClient.getInstance(context).login(authParams);

            if (response.code() == 200 && response.isSuccessful()) {
                final Token bearerToken = response.body();
                return Result.success(outputDataBuilder.putString(
                        Constants.BEARER_TOKEN,
                        bearerToken != null ? bearerToken.getToken() : null).build());
            }
            final ResponseBody error = response.errorBody();

            if (error == null) {
                return Result.failure(outputDataBuilder.putString(Constants.REQUEST_ERROR, Constants.UNKNOWN_ERROR).build());
            }
            return Result.failure(outputDataBuilder.putString(Constants.REQUEST_ERROR, error.string()).build());
        }
        catch (IOException e) {
            Log.e(TAG, "doWork(): ", e);
            return Result.failure(outputDataBuilder.putString(Constants.REQUEST_ERROR, e.getMessage()).build());
        }
    }

    private static final String TAG = LoginWorker.class.toString();
}
