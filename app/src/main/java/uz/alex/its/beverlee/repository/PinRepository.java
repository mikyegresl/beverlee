package uz.alex.its.beverlee.repository;

import android.content.Context;

import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import java.util.UUID;

import uz.alex.its.beverlee.utils.Constants;
import uz.alex.its.beverlee.worker.AssignPinWorker;
import uz.alex.its.beverlee.worker.CheckPinAssignedWorker;
import uz.alex.its.beverlee.worker.CheckVerifiedWorker;

public class PinRepository {
    private final Context context;

    public PinRepository(final Context context) {
        this.context = context;
    }


    public UUID assignPin(final String pin) {
        final Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresDeviceIdle(false)
                .setRequiresStorageNotLow(false)
                .setRequiresCharging(false)
                .setRequiresBatteryNotLow(false)
                .build();
        final Data inputData = new Data.Builder()
                .putString(Constants.PINCODE, pin)
                .build();
        final OneTimeWorkRequest assignPinRequest = new OneTimeWorkRequest.Builder(AssignPinWorker.class)
                .setConstraints(constraints)
                .setInputData(inputData)
                .build();
        WorkManager.getInstance(context).enqueue(assignPinRequest);
        return assignPinRequest.getId();
    }

    public UUID checkPinAssigned() {
        final Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresDeviceIdle(false)
                .setRequiresStorageNotLow(false)
                .setRequiresCharging(false)
                .setRequiresBatteryNotLow(false)
                .build();
        final OneTimeWorkRequest registerRequest = new OneTimeWorkRequest.Builder(CheckPinAssignedWorker.class)
                .setConstraints(constraints)
                .build();
        WorkManager.getInstance(context).enqueue(registerRequest);
        return registerRequest.getId();
    }

}
