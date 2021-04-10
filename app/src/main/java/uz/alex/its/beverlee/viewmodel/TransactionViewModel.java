package uz.alex.its.beverlee.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uz.alex.its.beverlee.model.transaction.Balance;
import uz.alex.its.beverlee.repository.TransactionRepository;

public class TransactionViewModel extends ViewModel {
    private final TransactionRepository repository;
    private final MutableLiveData<Balance> balanceMutableLiveData;

    public TransactionViewModel(final Context context) {
        this.repository = new TransactionRepository(context);
        this.balanceMutableLiveData = new MutableLiveData<>();
    }

    public void fetchCurrentBalance() {
        repository.getCurrentBalance(new Callback<Balance>() {
            @Override
            public void onResponse(@NonNull Call<Balance> call, @NonNull Response<Balance> response) {
                if (response.code() == 200 && response.isSuccessful()) {
                    balanceMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Balance> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure(): ", t);
            }
        });
    }

    public LiveData<Balance> getBalance() {
        return balanceMutableLiveData;
    }

    private final String TAG = TransactionViewModel.class.toString();
}
