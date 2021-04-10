package uz.alex.its.beverlee.worker;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import java.io.IOException;
import java.util.List;
import okhttp3.ResponseBody;
import retrofit2.Response;
import uz.alex.its.beverlee.api.RetrofitClient;
import uz.alex.its.beverlee.model.response.CountriesResponse;
import uz.alex.its.beverlee.model.Country;
import uz.alex.its.beverlee.storage.LocalDatabase;
import uz.alex.its.beverlee.utils.Constants;

public class GetCountriesWorker extends Worker {
    private final Context context;

    public GetCountriesWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);

        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        final Data.Builder outputDataBuilder = new Data.Builder();

        try {
            final Response<CountriesResponse> response = RetrofitClient.getInstance(context).getCountryList();

            if (response.code() == 200 && response.isSuccessful()) {
                final CountriesResponse customizableObject = response.body();

                if (customizableObject == null) {
                    Log.w(TAG, "doWork(): empty response from server");
                    return Result.success();
                }

                final List<Country> countryList = customizableObject.getCountryList();

                if (countryList == null) {
                    Log.w(TAG, "doWork(): countryList is NULL");
                    return Result.success();
                }
                if (countryList.isEmpty()) {
                    Log.w(TAG, "doWork(): countryList is empty");
                    return Result.success();
                }

                LocalDatabase.getInstance(context).countryDao().insertCountryList(countryList);
                return Result.success();
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

    private static final String TAG = GetCountriesWorker.class.toString();
}
