package uz.alex.its.beverlee.view.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.work.WorkInfo;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import uz.alex.its.beverlee.R;
import uz.alex.its.beverlee.storage.SharedPrefs;
import uz.alex.its.beverlee.utils.AppExecutors;
import uz.alex.its.beverlee.utils.Constants;
import uz.alex.its.beverlee.utils.NetworkConnectivity;
import uz.alex.its.beverlee.view.UiUtils;
import uz.alex.its.beverlee.viewmodel.PinViewModel;
import uz.alex.its.beverlee.viewmodel_factory.PinViewModelFactory;

import static androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG;
import static androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL;

public class PinFragment extends Fragment implements View.OnClickListener {
    /* pincode views */
    private ImageView firstStarImageView;
    private ImageView secondStarImageView;
    private ImageView thirdStarImageView;
    private ImageView forthStarImageView;
    private ImageView eraseImageView;

    private TextView pinTextView;
    private TextView pinErrorTextView;
    private TextView zeroImageView;
    private TextView oneTextView;
    private TextView twoImageView;
    private TextView threeImageView;
    private TextView fourImageView;
    private TextView fiveImageView;
    private TextView sixImageView;
    private TextView sevenImageView;
    private TextView eightImageView;
    private TextView nineImageView;

    private ProgressBar progressBar;

    private static volatile boolean pinAssigned;
    private static volatile List<Integer> pinArray;

    private PinViewModel pinViewModel;
    private NetworkConnectivity networkConnectivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pinArray = new ArrayList<>();

        if (requireActivity().getIntent() != null) {
            pinAssigned = requireActivity().getIntent().getBooleanExtra(Constants.PIN_ASSIGNED, false);
        }
        else {
            pinAssigned = true;
        }

        /*init ViewModel */
        final PinViewModelFactory factory = new PinViewModelFactory(requireContext());
        pinViewModel = new ViewModelProvider(getViewModelStore(), factory).get(PinViewModel.class);

        /* internet connection checker */
        networkConnectivity = new NetworkConnectivity(requireContext(), AppExecutors.getInstance());

        /* handle fingerprint */
        final Executor executor = ContextCompat.getMainExecutor(requireContext());
        final BiometricManager biometricManager = BiometricManager.from(requireContext());

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

                //todo: NavHost
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
//                if (pinViewModel.isFingerprintOn(this)) {
//                    biometricPrompt.authenticate(promptInfo);
//                }
                break;
            }
        }
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_pin, container, false);

        /* init views */
        firstStarImageView = root.findViewById(R.id.first_star_image_view);
        secondStarImageView = root.findViewById(R.id.second_star_image_view);
        thirdStarImageView = root.findViewById(R.id.third_star_image_view);
        forthStarImageView = root.findViewById(R.id.forth_star_image_view);
        eraseImageView = root.findViewById(R.id.erase_image_view);

        oneTextView = root.findViewById(R.id.one_text_view);
        twoImageView = root.findViewById(R.id.two_text_view);
        threeImageView = root.findViewById(R.id.three_text_view);
        fourImageView = root.findViewById(R.id.four_text_view);
        fiveImageView = root.findViewById(R.id.five_text_view);
        sixImageView = root.findViewById(R.id.six_text_view);
        sevenImageView = root.findViewById(R.id.seven_text_view);
        eightImageView = root.findViewById(R.id.eight_text_view);
        nineImageView = root.findViewById(R.id.nine_text_view);
        zeroImageView = root.findViewById(R.id.zero_text_view);

        pinTextView = root.findViewById(R.id.pin_text_view);
        pinErrorTextView = root.findViewById(R.id.pin_error_text_view);

        progressBar = root.findViewById(R.id.progress_bar);

        UiUtils.hideBottomNav(requireActivity());

        return root;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (pinAssigned) {
            pinTextView.setText(R.string.enter_pin);
        }
        else {
            pinTextView.setText(R.string.create_pin);
        }

        oneTextView.setOnClickListener(this);
        twoImageView.setOnClickListener(this);
        threeImageView.setOnClickListener(this);
        fourImageView.setOnClickListener(this);
        fiveImageView.setOnClickListener(this);
        sixImageView.setOnClickListener(this);
        sevenImageView.setOnClickListener(this);
        eightImageView.setOnClickListener(this);
        nineImageView.setOnClickListener(this);
        zeroImageView.setOnClickListener(this);
        eraseImageView.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        pinViewModel.getAssignPinResult(requireContext()).observe(getViewLifecycleOwner(), workInfo -> {
            if (workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                SharedPrefs.getInstance(requireContext()).putBoolean(Constants.PIN_ASSIGNED, true);
                pinAssigned = true;
                pinArray.clear();

                firstStarImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_star_grey, null));
                secondStarImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_star_grey, null));
                thirdStarImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_star_grey, null));
                forthStarImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_star_grey, null));

                pinErrorTextView.setVisibility(View.INVISIBLE);
                pinTextView.setText(R.string.repeat_pin);
                progressBar.setVisibility(View.INVISIBLE);

                oneTextView.setEnabled(true);
                twoImageView.setEnabled(true);
                threeImageView.setEnabled(true);
                fourImageView.setEnabled(true);
                fiveImageView.setEnabled(true);
                sixImageView.setEnabled(true);
                sevenImageView.setEnabled(true);
                eightImageView.setEnabled(true);
                nineImageView.setEnabled(true);
                zeroImageView.setEnabled(true);
                eraseImageView.setEnabled(true);

                return;
            }
            if (workInfo.getState() == WorkInfo.State.FAILED || workInfo.getState() == WorkInfo.State.CANCELLED) {
                SharedPrefs.getInstance(requireContext()).putBoolean(Constants.PIN_ASSIGNED, false);
                pinErrorTextView.setText(R.string.error_pin_asign);
                pinErrorTextView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);

                oneTextView.setEnabled(true);
                twoImageView.setEnabled(true);
                threeImageView.setEnabled(true);
                fourImageView.setEnabled(true);
                fiveImageView.setEnabled(true);
                sixImageView.setEnabled(true);
                sevenImageView.setEnabled(true);
                eightImageView.setEnabled(true);
                nineImageView.setEnabled(true);
                zeroImageView.setEnabled(true);
                eraseImageView.setEnabled(true);

                return;
            }
            oneTextView.setEnabled(false);
            twoImageView.setEnabled(false);
            threeImageView.setEnabled(false);
            fourImageView.setEnabled(false);
            fiveImageView.setEnabled(false);
            sixImageView.setEnabled(false);
            sevenImageView.setEnabled(false);
            eightImageView.setEnabled(false);
            nineImageView.setEnabled(false);
            zeroImageView.setEnabled(false);
            eraseImageView.setEnabled(false);
            pinErrorTextView.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        });

        pinViewModel.getVerifyPinResult(requireContext()).observe(getViewLifecycleOwner(), workInfo -> {
            if (workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                NavHostFragment.findNavController(this).navigate(PinFragmentDirections.actionPinFragmentToHomeFragment());

                pinErrorTextView.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.INVISIBLE);


                oneTextView.setEnabled(true);
                twoImageView.setEnabled(true);
                threeImageView.setEnabled(true);
                fourImageView.setEnabled(true);
                fiveImageView.setEnabled(true);
                sixImageView.setEnabled(true);
                sevenImageView.setEnabled(true);
                eightImageView.setEnabled(true);
                nineImageView.setEnabled(true);
                zeroImageView.setEnabled(true);
                eraseImageView.setEnabled(true);

                return;
            }
            if (workInfo.getState() == WorkInfo.State.FAILED || workInfo.getState() == WorkInfo.State.CANCELLED) {
                pinArray.clear();

                firstStarImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_star_grey, null));
                secondStarImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_star_grey, null));
                thirdStarImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_star_grey, null));
                forthStarImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_star_grey, null));

                pinErrorTextView.setText(R.string.error_wrong_pin);
                pinErrorTextView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);

                oneTextView.setEnabled(true);
                twoImageView.setEnabled(true);
                threeImageView.setEnabled(true);
                fourImageView.setEnabled(true);
                fiveImageView.setEnabled(true);
                sixImageView.setEnabled(true);
                sevenImageView.setEnabled(true);
                eightImageView.setEnabled(true);
                nineImageView.setEnabled(true);
                zeroImageView.setEnabled(true);
                eraseImageView.setEnabled(true);

                return;
            }
            oneTextView.setEnabled(false);
            twoImageView.setEnabled(false);
            threeImageView.setEnabled(false);
            fourImageView.setEnabled(false);
            fiveImageView.setEnabled(false);
            sixImageView.setEnabled(false);
            sevenImageView.setEnabled(false);
            eightImageView.setEnabled(false);
            nineImageView.setEnabled(false);
            zeroImageView.setEnabled(false);
            eraseImageView.setEnabled(false);
            pinErrorTextView.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        });
    }

    @Override
    public void onClick(final View v) {
        if (v.getId() == R.id.one_text_view) {
            pinArray.add(1);
        }
        else if (v.getId() == R.id.two_text_view) {
            pinArray.add(2);
        }
        else if (v.getId() == R.id.three_text_view) {
            pinArray.add(3);
        }
        else if (v.getId() == R.id.four_text_view) {
            pinArray.add(4);
        }
        else if (v.getId() == R.id.five_text_view) {
            pinArray.add(5);
        }
        else if (v.getId() == R.id.six_text_view) {
            pinArray.add(6);
        }
        else if (v.getId() == R.id.seven_text_view) {
            pinArray.add(7);
        }
        else if (v.getId() == R.id.eight_text_view) {
            pinArray.add(8);
        }
        else if (v.getId() == R.id.nine_text_view) {
            pinArray.add(9);
        }
        else if (v.getId() == R.id.zero_text_view) {
            pinArray.add(0);
        }
        else if (v.getId() == R.id.erase_image_view) {
            if (pinArray.isEmpty()) {
                return;
            }
            pinArray.remove(pinArray.size() - 1);
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

            final StringBuilder pinBuilder = new StringBuilder();

            for (final int i : pinArray) {
                pinBuilder.append(i);
            }
            final String pin = pinBuilder.toString();

            /* if pin does not exist on server -> assign new pin -> return */
            networkConnectivity.checkInternetConnection(isConnected -> {
                if (!isConnected) {
                    NavHostFragment.findNavController(this).navigate(
                            PinFragmentDirections.actionPinFragmentToTransactionResultFragment()
                                    .setResult(false)
                                    .setType(Constants.RESULT_TYPE_PROFILE)
                                    .setErrorMessage(Constants.NO_INTERNET));
                    return;
                }
                if (!pinAssigned) {
                    pinViewModel.assignPin(pin);
                    return;
                }
                pinViewModel.verifyPin(pin);
            });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private static final String TAG = PinFragment.class.toString();
}