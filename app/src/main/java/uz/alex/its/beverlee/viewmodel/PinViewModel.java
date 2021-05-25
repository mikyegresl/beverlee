package uz.alex.its.beverlee.viewmodel;

import android.content.Context;
import android.text.TextUtils;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import java.util.List;
import java.util.UUID;

import uz.alex.its.beverlee.repository.PinRepository;
import uz.alex.its.beverlee.storage.SharedPrefs;
import uz.alex.its.beverlee.utils.Constants;

public class PinViewModel extends ViewModel {
    private final PinRepository pinRepository;

    private final MutableLiveData<UUID> checkPinAssignedUUID;
    private final MutableLiveData<UUID> assignPinUUID;
    private final MutableLiveData<UUID> verifyPinUUID;

    public PinViewModel(final Context context) {
        this.pinRepository = new PinRepository(context);
        this.checkPinAssignedUUID = new MutableLiveData<>();
        this.assignPinUUID = new MutableLiveData<>();
        this.verifyPinUUID = new MutableLiveData<>();
    }

    public void checkPinAssigned() {
        checkPinAssignedUUID.setValue(pinRepository.checkPinAssigned());
    }

    public void assignPin(final String pin) {
        assignPinUUID.setValue(pinRepository.assignPin(pin));
    }

    public void verifyPin(final String pincode) {
        verifyPinUUID.setValue(pinRepository.verifyPin(pincode));
    }

    public LiveData<WorkInfo> getCheckPinAssignedResult(final Context context) {
        return Transformations.switchMap(checkPinAssignedUUID, input -> WorkManager.getInstance(context).getWorkInfoByIdLiveData(input));
    }

    public LiveData<WorkInfo> getAssignPinResult(final Context context) {
        return Transformations.switchMap(assignPinUUID, input -> WorkManager.getInstance(context).getWorkInfoByIdLiveData(input));
    }

    public LiveData<WorkInfo> getVerifyPinResult(final Context context) {
        return Transformations.switchMap(verifyPinUUID, input -> WorkManager.getInstance(context).getWorkInfoByIdLiveData(input));
    }

//    public String obtainPin(final List<Integer> pinArray) {
//        final StringBuilder pinBuilder = new StringBuilder();
//
//        if (pinArray == null) {
//            return null;
//        }
//        if (pinArray.isEmpty()) {
//            return null;
//        }
//        if (pinArray.size() < 4) {
//            return null;
//        }
//        for (final int digit : pinArray) {
//            pinBuilder.append(digit);
//        }
//        return pinBuilder.toString();
//    }
//
//    public boolean authenticateByPin(final Context context, final String pin) {
//        if (pin == null) {
//            return false;
//        }
//        if (TextUtils.isEmpty(pin)) {
//            return false;
//        }
//        return SharedPrefs.getInstance(context).getString(Constants.PINCODE).equalsIgnoreCase(pin);
//    }
//
//    public boolean isFingerprintOn(final Context context) {
//        return SharedPrefs.getInstance(context).getBoolean(Constants.FINGERPRINT_ON);
//    }

    private static final String TAG = PinViewModel.class.toString();
}
