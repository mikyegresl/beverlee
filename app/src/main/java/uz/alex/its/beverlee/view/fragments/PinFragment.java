//package uz.alex.its.beverlee.view.fragments;
//
//import android.content.Intent;
//import android.os.Bundle;
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.biometric.BiometricManager;
//import androidx.biometric.BiometricPrompt;
//import androidx.core.content.ContextCompat;
//import androidx.core.content.res.ResourcesCompat;
//import androidx.fragment.app.Fragment;
//import androidx.lifecycle.ViewModelProvider;
//import androidx.navigation.fragment.NavHostFragment;
//
//import android.provider.Settings;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//import com.google.android.material.bottomnavigation.BottomNavigationView;
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.Executor;
//import uz.alex.its.beverlee.R;
//import uz.alex.its.beverlee.model.actor.User;
//import uz.alex.its.beverlee.storage.SharedPrefs;
//import uz.alex.its.beverlee.utils.Constants;
//import uz.alex.its.beverlee.viewmodel.PinViewModel;
//import uz.alex.its.beverlee.viewmodel_factory.PinViewModelFactory;
//
//import static androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG;
//import static androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL;
//
///**
// * A simple {@link Fragment} subclass.
// */
//public class PinFragment extends Fragment {
//    private ImageView firstStarImageView;
//    private ImageView secondStarImageView;
//    private ImageView thirdStarImageView;
//    private ImageView forthStarImageView;
//    private TextView oneImageView;
//    private TextView twoImageView;
//    private TextView threeImageView;
//    private TextView fourImageView;
//    private TextView fiveImageView;
//    private TextView sixImageView;
//    private TextView sevenImageView;
//    private TextView eightImageView;
//    private TextView nineImageView;
//    private TextView zeroImageView;
//    private ImageView eraseImageView;
//
//    private TextView pinTextView;
//    private TextView pinErrorTextView;
//
//    private BiometricManager biometricManager;
//    private BiometricPrompt biometricPrompt;
//    private BiometricPrompt.PromptInfo promptInfo;
//
//    private PinViewModel pinViewModel;
//
//    private User currentUser;
//
//    public PinFragment() {
//        // Required empty public constructor
//    }
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        currentUser = new User(SharedPrefs.getInstance(requireContext()).getString(Constants.FIRST_NAME),
//                SharedPrefs.getInstance(requireContext()).getString(Constants.LAST_NAME),
//                SharedPrefs.getInstance(requireContext()).getString(Constants.PHONE),
//                SharedPrefs.getInstance(requireContext()).getString(Constants.EMAIL),
//                SharedPrefs.getInstance(requireContext()).getLong(Constants.COUNTRY_ID),
//                SharedPrefs.getInstance(requireContext()).getString(Constants.CITY));
//
//        /* biometric authentication */
//        final Executor executor = ContextCompat.getMainExecutor(requireContext());
//        biometricManager = BiometricManager.from(requireContext());
//
//        biometricPrompt = new BiometricPrompt(this, executor, new BiometricPrompt.AuthenticationCallback() {
//            @Override
//            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
//                super.onAuthenticationError(errorCode, errString);
//                Log.e(TAG, "onAuthenticationError(): " + errorCode + " " + errString);
//            }
//
//            @Override
//            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
//                super.onAuthenticationSucceeded(result);
//
//                Log.i(TAG, "onAuthenticationSucceeded(): " + result);
//
//                NavHostFragment.findNavController(PinFragment.this).navigate(R.id.action_pinFragment_to_homeFragment);
//            }
//
//            @Override
//            public void onAuthenticationFailed() {
//                super.onAuthenticationFailed();
//                Log.e(TAG, "onAuthenticationFailed(): ");
//            }
//        });
//
//        promptInfo = new BiometricPrompt.PromptInfo.Builder()
//                .setTitle(getString(R.string.fingerprint_text))
//                .setNegativeButtonText(getString(R.string.or_enter_pin))
//                .build();
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        final View root = inflater.inflate(R.layout.fragment_pin, container, false);
//        firstStarImageView = root.findViewById(R.id.first_star_image_view);
//        secondStarImageView = root.findViewById(R.id.second_star_image_view);
//        thirdStarImageView = root.findViewById(R.id.third_star_image_view);
//        forthStarImageView = root.findViewById(R.id.forth_star_image_view);
//        oneImageView = root.findViewById(R.id.one_image_view);
//        twoImageView = root.findViewById(R.id.two_image_view);
//        threeImageView = root.findViewById(R.id.three_image_view);
//        fourImageView = root.findViewById(R.id.four_image_view);
//        fiveImageView = root.findViewById(R.id.five_image_view);
//        sixImageView = root.findViewById(R.id.six_image_view);
//        sevenImageView = root.findViewById(R.id.seven_image_view);
//        eightImageView = root.findViewById(R.id.eight_image_view);
//        nineImageView = root.findViewById(R.id.nine_image_view);
//        zeroImageView = root.findViewById(R.id.zero_image_view);
//        eraseImageView = root.findViewById(R.id.erase_image_view);
//
//        pinTextView = root.findViewById(R.id.pin_text_view);
//        pinErrorTextView = root.findViewById(R.id.pin_error_text_view);
//
//        final BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_nav);
//        final FloatingActionButton floatingActionBtn = getActivity().findViewById(R.id.floating_btn);
//        bottomNavigationView.setVisibility(View.GONE);
//        floatingActionBtn.setVisibility(View.GONE);
//
//        return root;
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        final View.OnClickListener onPinNumberClick = v -> {
//            switch (v.getId()) {
//                case R.id.one_image_view: {
//                    pinArray.add(1);
//                    break;
//                }
//                case R.id.two_image_view: {
//                    pinArray.add(2);
//                    break;
//                }
//                case R.id.three_image_view: {
//                    pinArray.add(3);
//                    break;
//                }
//                case R.id.four_image_view: {
//                    pinArray.add(4);
//                    break;
//                }
//                case R.id.five_image_view: {
//                    pinArray.add(5);
//                    break;
//                }
//                case R.id.six_image_view: {
//                    pinArray.add(6);
//                    break;
//                }
//                case R.id.seven_image_view: {
//                    pinArray.add(7);
//                    break;
//                }
//                case R.id.eight_image_view: {
//                    pinArray.add(8);
//                    break;
//                }
//                case R.id.nine_image_view: {
//                    pinArray.add(9);
//                    break;
//                }
//                case R.id.zero_image_view: {
//                    pinArray.add(0);
//                    break;
//                }
//                case R.id.erase_image_view: {
//                    if (pinArray.isEmpty()) {
//                        return;
//                    }
//                    pinArray.remove(pinArray.size() - 1);
//                }
//            }
//            if (pinArray.isEmpty()) {
//                firstStarImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_star_grey, null));
//                secondStarImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_star_grey, null));
//                thirdStarImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_star_grey, null));
//                forthStarImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_star_grey, null));
//                return;
//            }
//            if (pinArray.size() == 1) {
//                firstStarImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_star_purple, null));
//                secondStarImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_star_grey, null));
//                thirdStarImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_star_grey, null));
//                forthStarImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_star_grey, null));
//
//                pinErrorTextView.setVisibility(View.INVISIBLE);
//
//                return;
//            }
//            if (pinArray.size() == 2) {
//                firstStarImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_star_purple, null));
//                secondStarImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_star_purple, null));
//                thirdStarImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_star_grey, null));
//                forthStarImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_star_grey, null));
//                return;
//            }
//            if (pinArray.size() == 3) {
//                firstStarImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_star_purple, null));
//                secondStarImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_star_purple, null));
//                thirdStarImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_star_purple, null));
//                forthStarImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_star_grey, null));
//                return;
//            }
//            if (pinArray.size() == 4) {
//                firstStarImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_star_purple, null));
//                secondStarImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_star_purple, null));
//                thirdStarImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_star_purple, null));
//                forthStarImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_star_purple, null));
//
//                final String pin = pinViewModel.obtainPin(pinArray);
//
//                pinArray.clear();
//
//                if (pinViewModel == null) {
//                    pinErrorTextView.setText(R.string.error_unknown);
//                    pinErrorTextView.setVisibility(View.VISIBLE);
//                    return;
//                }
//                if (SharedPrefs.getInstance(requireContext()).getString(Constants.PINCODE) == null) {
//                    pinViewModel.assignPin(requireContext(), getViewLifecycleOwner(), pin);
//                    return;
//                }
//                if (!pinViewModel.authenticateByPin(requireContext(), pin)) {
//                    pinErrorTextView.setText(R.string.error_wrong_pin);
//                    pinErrorTextView.setVisibility(View.VISIBLE);
//                    return;
//                }
//                pinErrorTextView.setVisibility(View.INVISIBLE);
//                NavHostFragment.findNavController(PinFragment.this).navigate(R.id.action_pinFragment_to_homeFragment);
//            }
//        };
//        oneImageView.setOnClickListener(onPinNumberClick);
//        twoImageView.setOnClickListener(onPinNumberClick);
//        threeImageView.setOnClickListener(onPinNumberClick);
//        fourImageView.setOnClickListener(onPinNumberClick);
//        fiveImageView.setOnClickListener(onPinNumberClick);
//        sixImageView.setOnClickListener(onPinNumberClick);
//        sevenImageView.setOnClickListener(onPinNumberClick);
//        eightImageView.setOnClickListener(onPinNumberClick);
//        nineImageView.setOnClickListener(onPinNumberClick);
//        zeroImageView.setOnClickListener(onPinNumberClick);
//        eraseImageView.setOnClickListener(onPinNumberClick);
//    }
//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//
//        final PinViewModelFactory factory = new PinViewModelFactory(requireContext());
//
//        pinViewModel = new ViewModelProvider(getViewModelStore(), factory).get(PinViewModel.class);
//
//        pinViewModel.checkPinAssigned(requireContext(), getViewLifecycleOwner());
//
//        pinViewModel.doesPinExist().observe(getViewLifecycleOwner(), doesPinExist -> {
//            if (doesPinExist == null) {
//                pinErrorTextView.setText(R.string.error_check_internet);
//                pinErrorTextView.setVisibility(View.VISIBLE);
//                return;
//            }
//            pinErrorTextView.setVisibility(View.INVISIBLE);
//
//            if (!doesPinExist) {
//                //assign pin
//                pinTextView.setText(R.string.create_pin);
//                return;
//            }
//            //pin already assigned
//            pinTextView.setText(R.string.enter_pin);
//        });
//
//        pinViewModel.pinAssignedSuccessfully().observe(getViewLifecycleOwner(), isPinAssigned -> {
//            if (isPinAssigned == null) {
//                pinErrorTextView.setText(R.string.pin_assign_error);
//                pinErrorTextView.setVisibility(View.VISIBLE);
//                return;
//            }
//            pinErrorTextView.setVisibility(View.INVISIBLE);
//
//            if (isPinAssigned) {
//                pinTextView.setText(R.string.repeat_pin);
//            }
//        });
//
//        switch (biometricManager.canAuthenticate(BIOMETRIC_STRONG | DEVICE_CREDENTIAL)) {
//            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
//            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
//            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
//            case BiometricManager.BIOMETRIC_STATUS_UNKNOWN:
//            case BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED: {
//                Log.e(TAG, "isFingerprintSupported(): Security update required");
//                break;
//            }
//            case BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED: {
//                Log.e(TAG, "isFingerprintSupported(): Biometric unsupported because the specified options are incompatible with the current Android version");
//                break;
//            }
//            case BiometricManager.BIOMETRIC_SUCCESS: {
//                if (pinViewModel.isFingerprintOn(requireContext())) {
//                    biometricPrompt.authenticate(promptInfo);
//                }
//                break;
//            }
//        }
//    }
//
//    private static final String TAG = PinFragment.class.toString();
//    private static final List<Integer> pinArray = new ArrayList<>();
//}
