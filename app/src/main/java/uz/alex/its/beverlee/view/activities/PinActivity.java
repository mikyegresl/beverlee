package uz.alex.its.beverlee.view.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import uz.alex.its.beverlee.R;
import uz.alex.its.beverlee.model.actor.User;
import uz.alex.its.beverlee.storage.SharedPrefs;
import uz.alex.its.beverlee.utils.Constants;
import uz.alex.its.beverlee.viewmodel.PinViewModel;
import uz.alex.its.beverlee.viewmodel_factory.PinViewModelFactory;

import static androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG;
import static androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL;

public class PinActivity extends AppCompatActivity {

    @Nullable
    @Override
    public View onCreateView(@Nullable View parent, @NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* init views */
        final ImageView firstStarImageView = findViewById(R.id.first_star_image_view);
        final ImageView secondStarImageView = findViewById(R.id.second_star_image_view);
        final ImageView thirdStarImageView = findViewById(R.id.third_star_image_view);
        final ImageView forthStarImageView = findViewById(R.id.forth_star_image_view);
        final TextView oneImageView = findViewById(R.id.one_text_view);
        final TextView twoImageView = findViewById(R.id.two_text_view);
        final TextView threeImageView = findViewById(R.id.three_text_view);
        final TextView fourImageView = findViewById(R.id.four_text_view);
        final TextView fiveImageView = findViewById(R.id.five_text_view);
        final TextView sixImageView = findViewById(R.id.six_text_view);
        final TextView sevenImageView = findViewById(R.id.seven_text_view);
        final TextView eightImageView = findViewById(R.id.eight_text_view);
        final TextView nineImageView = findViewById(R.id.nine_text_view);
        final TextView zeroImageView = findViewById(R.id.zero_text_view);
        final ImageView eraseImageView = findViewById(R.id.erase_image_view);

        final TextView pinTextView = findViewById(R.id.pin_text_view);
        final TextView pinErrorTextView = findViewById(R.id.pin_error_text_view);

        /* get user data from local cache */
        final User currentUser = new User(SharedPrefs.getInstance(this).getString(Constants.FIRST_NAME),
                SharedPrefs.getInstance(this).getString(Constants.LAST_NAME),
                SharedPrefs.getInstance(this).getString(Constants.PHONE),
                SharedPrefs.getInstance(this).getString(Constants.EMAIL),
                SharedPrefs.getInstance(this).getLong(Constants.COUNTRY_ID),
                SharedPrefs.getInstance(this).getString(Constants.CITY));

        /*init ViewModel */
        final PinViewModelFactory factory = new PinViewModelFactory(this);
        final PinViewModel pinViewModel = new ViewModelProvider(getViewModelStore(), factory).get(PinViewModel.class);

        /* biometric authentication */
        final Executor executor = ContextCompat.getMainExecutor(this);
        final BiometricManager biometricManager = BiometricManager.from(this);

        final BiometricPrompt biometricPrompt = new BiometricPrompt(this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Log.e(TAG, "onAuthenticationError(): " + errorCode + " " + errString);
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);

                Log.i(TAG, "onAuthenticationSucceeded(): " + result);

                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Log.e(TAG, "onAuthenticationFailed(): ");
            }
        });

        final BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle(getString(R.string.fingerprint_text))
                .setNegativeButtonText(getString(R.string.or_enter_pin))
                .build();

        switch (biometricManager.canAuthenticate(BIOMETRIC_STRONG | DEVICE_CREDENTIAL)) {
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
            case BiometricManager.BIOMETRIC_STATUS_UNKNOWN:
            case BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED: {
                Log.e(TAG, "isFingerprintSupported(): Security update required");
                break;
            }
            case BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED: {
                Log.e(TAG, "isFingerprintSupported(): Biometric unsupported because the specified options are incompatible with the current Android version");
                break;
            }
            case BiometricManager.BIOMETRIC_SUCCESS: {
                if (pinViewModel.isFingerprintOn(this)) {
                    biometricPrompt.authenticate(promptInfo);
                }
                break;
            }
        }

        /* init listeners */
        final View.OnClickListener onPinNumberClick = v -> {
            switch (v.getId()) {
                case R.id.one_text_view: {
                    pinArray.add(1);
                    break;
                }
                case R.id.two_text_view: {
                    pinArray.add(2);
                    break;
                }
                case R.id.three_text_view: {
                    pinArray.add(3);
                    break;
                }
                case R.id.four_text_view: {
                    pinArray.add(4);
                    break;
                }
                case R.id.five_text_view: {
                    pinArray.add(5);
                    break;
                }
                case R.id.six_text_view: {
                    pinArray.add(6);
                    break;
                }
                case R.id.seven_text_view: {
                    pinArray.add(7);
                    break;
                }
                case R.id.eight_text_view: {
                    pinArray.add(8);
                    break;
                }
                case R.id.nine_text_view: {
                    pinArray.add(9);
                    break;
                }
                case R.id.zero_text_view: {
                    pinArray.add(0);
                    break;
                }
                case R.id.erase_image_view: {
                    if (pinArray.isEmpty()) {
                        return;
                    }
                    pinArray.remove(pinArray.size() - 1);
                }
            }
            if (pinArray.isEmpty()) {
                firstStarImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_star_grey, null));
                secondStarImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_star_grey, null));
                thirdStarImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_star_grey, null));
                forthStarImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_star_grey, null));
                return;
            }
            if (pinArray.size() == 1) {
                firstStarImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_star_purple, null));
                secondStarImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_star_grey, null));
                thirdStarImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_star_grey, null));
                forthStarImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_star_grey, null));

                pinErrorTextView.setVisibility(View.INVISIBLE);

                return;
            }
            if (pinArray.size() == 2) {
                firstStarImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_star_purple, null));
                secondStarImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_star_purple, null));
                thirdStarImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_star_grey, null));
                forthStarImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_star_grey, null));
                return;
            }
            if (pinArray.size() == 3) {
                firstStarImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_star_purple, null));
                secondStarImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_star_purple, null));
                thirdStarImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_star_purple, null));
                forthStarImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_star_grey, null));
                return;
            }
            if (pinArray.size() == 4) {
                firstStarImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_star_purple, null));
                secondStarImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_star_purple, null));
                thirdStarImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_star_purple, null));
                forthStarImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_star_purple, null));

                final String pin = pinViewModel.obtainPin(pinArray);

                pinArray.clear();

                if (SharedPrefs.getInstance(this).getString(Constants.PINCODE) == null) {
                    pinViewModel.assignPin(this, this, pin);
                    return;
                }
                if (!pinViewModel.authenticateByPin(this, pin)) {
                    pinErrorTextView.setText(R.string.error_wrong_pin);
                    pinErrorTextView.setVisibility(View.VISIBLE);
                    return;
                }
                pinErrorTextView.setVisibility(View.INVISIBLE);
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }
        };
        oneImageView.setOnClickListener(onPinNumberClick);
        twoImageView.setOnClickListener(onPinNumberClick);
        threeImageView.setOnClickListener(onPinNumberClick);
        fourImageView.setOnClickListener(onPinNumberClick);
        fiveImageView.setOnClickListener(onPinNumberClick);
        sixImageView.setOnClickListener(onPinNumberClick);
        sevenImageView.setOnClickListener(onPinNumberClick);
        eightImageView.setOnClickListener(onPinNumberClick);
        nineImageView.setOnClickListener(onPinNumberClick);
        zeroImageView.setOnClickListener(onPinNumberClick);
        eraseImageView.setOnClickListener(onPinNumberClick);

        /* make network requests using PinViewModel */
        pinViewModel.checkPinAssigned(this, this);

        pinViewModel.doesPinExist().observe(this, doesPinExist -> {
            if (doesPinExist == null) {
                pinErrorTextView.setText(R.string.error_check_internet);
                pinErrorTextView.setVisibility(View.VISIBLE);
                return;
            }
            pinErrorTextView.setVisibility(View.INVISIBLE);

            if (!doesPinExist) {
                //assign pin
                pinTextView.setText(R.string.create_pin);
                return;
            }
            //pin already assigned
            pinTextView.setText(R.string.enter_pin);
        });

        pinViewModel.pinAssignedSuccessfully().observe(this, isPinAssigned -> {
            if (isPinAssigned == null) {
                pinErrorTextView.setText(R.string.pin_assign_error);
                pinErrorTextView.setVisibility(View.VISIBLE);
                return;
            }
            pinErrorTextView.setVisibility(View.INVISIBLE);

            if (isPinAssigned) {
                pinTextView.setText(R.string.repeat_pin);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private static final String TAG = PinActivity.class.toString();
    private static final List<Integer> pinArray = new ArrayList<>();
}