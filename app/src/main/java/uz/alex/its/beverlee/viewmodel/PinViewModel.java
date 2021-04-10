package uz.alex.its.beverlee.viewmodel;

import android.content.Context;
import android.text.TextUtils;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import java.util.List;

import uz.alex.its.beverlee.repository.PinRepository;
import uz.alex.its.beverlee.storage.SharedPrefs;
import uz.alex.its.beverlee.utils.Constants;

public class PinViewModel extends ViewModel {
    private final PinRepository pinRepository;

    private final MutableLiveData<Boolean> doesPinExist;
    private final MutableLiveData<Boolean> pinAssignedSuccessfully;

    public PinViewModel(final Context context) {
        this.pinRepository = new PinRepository(context);

        this.doesPinExist = new MutableLiveData<>();
        this.pinAssignedSuccessfully = new MutableLiveData<>();
    }

    public void checkPinAssigned(final Context context, final LifecycleOwner lifecycleOwner) {
        WorkManager.getInstance(context).getWorkInfoByIdLiveData(pinRepository.checkPinAssigned()).observe(lifecycleOwner, workInfo -> {
            if (workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                doesPinExist.setValue(true);
                return;
            }
            if (workInfo.getState() == WorkInfo.State.FAILED || workInfo.getState() == WorkInfo.State.CANCELLED) {
                doesPinExist.setValue(false);
            }
        });
    }

    public void assignPin(final Context context, final LifecycleOwner owner, final String pin) {
        WorkManager.getInstance(context).getWorkInfoByIdLiveData(pinRepository.assignPin(pin)).observe(owner, workInfo -> {
            if (workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                pinAssignedSuccessfully.setValue(true);
                SharedPrefs.getInstance(context).putString(Constants.PINCODE, pin);
                return;
            }
            if (workInfo.getState() == WorkInfo.State.FAILED || workInfo.getState() == WorkInfo.State.CANCELLED) {
                pinAssignedSuccessfully.setValue(false);
            }
        });
    }

    public String obtainPin(final List<Integer> pinArray) {
        final StringBuilder pinBuilder = new StringBuilder();

        if (pinArray == null) {
            return null;
        }
        if (pinArray.isEmpty()) {
            return null;
        }
        if (pinArray.size() < 4) {
            return null;
        }
        for (final int digit : pinArray) {
            pinBuilder.append(digit);
        }
        return pinBuilder.toString();
    }

    public boolean authenticateByPin(final Context context, final String pin) {
        if (pin == null) {
            return false;
        }
        if (TextUtils.isEmpty(pin)) {
            return false;
        }
        return SharedPrefs.getInstance(context).getString(Constants.PINCODE).equalsIgnoreCase(pin);
    }

    public LiveData<Boolean> pinAssignedSuccessfully() {
        return pinAssignedSuccessfully;
    }

    public LiveData<Boolean> doesPinExist() {
        return doesPinExist;
    }

    public boolean isFingerprintOn(final Context context) {
        return SharedPrefs.getInstance(context).getBoolean(Constants.FINGERPRINT_ON);
    }

    private static final String TAG = PinViewModel.class.toString();
}
