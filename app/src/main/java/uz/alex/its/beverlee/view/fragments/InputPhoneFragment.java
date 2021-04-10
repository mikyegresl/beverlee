package uz.alex.its.beverlee.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import uz.alex.its.beverlee.R;
import uz.alex.its.beverlee.model.DeviceDisplay;
import uz.alex.its.beverlee.storage.SharedPrefs;
import uz.alex.its.beverlee.utils.Constants;
import uz.alex.its.beverlee.view.UiUtils;
import uz.alex.its.beverlee.viewmodel.AuthViewModel;
import uz.alex.its.beverlee.viewmodel_factory.AuthViewModelFactory;

public class InputPhoneFragment extends Fragment {
    private FragmentActivity activity;
    private Context context;

    private AuthViewModel authViewModel;

    private View parentLayout;
    private EditText phoneEditText;
    private EditText passwordEditText;
    private ImageView passwordEyeImageView;
    private TextView forgotPasswordTextView;
    private Button signInBtn;
    private ProgressBar progressBar;

    private static boolean showPassword = false;

    public InputPhoneFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getActivity() != null) {
            activity = getActivity();
            context = requireContext();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_input_phone, container, false);

        parentLayout = root;
        phoneEditText = root.findViewById(R.id.phone_edit_text);
        passwordEditText = root.findViewById(R.id.password_edit_text);
        passwordEyeImageView = root.findViewById(R.id.password_eye_image_view);
        forgotPasswordTextView = root.findViewById(R.id.forgot_password_text_view);
        signInBtn = root.findViewById(R.id.login_btn);
        progressBar = root.findViewById(R.id.progress_bar);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        phoneEditText.setOnFocusChangeListener((v, hasFocus) -> {
            UiUtils.setFocusChange(phoneEditText, hasFocus, R.string.phone_hint);
        });

        passwordEditText.setOnFocusChangeListener((v, hasFocus) -> UiUtils.setFocusChange(passwordEditText, hasFocus, R.string.password_hint));

        passwordEyeImageView.setOnClickListener(v -> {
            if (showPassword) {
                passwordEyeImageView.setImageResource(R.drawable.ic_eye);
                passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                showPassword = false;
            }
            else {
                passwordEyeImageView.setImageResource(R.drawable.ic_eye_purple);
                passwordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                showPassword = true;
            }
        });

        signInBtn.setOnClickListener(v -> {
            signInBtn.startAnimation(AnimationUtils.loadAnimation(context, R.anim.bubble));

            final String phone = phoneEditText.getText().toString().trim();

            if (TextUtils.isEmpty(phone)) {
                Toast.makeText(activity, "Введите номер телефона", Toast.LENGTH_SHORT).show();
                return;
            }
            if (phone.length() <= 10) {
                Toast.makeText(activity, "Неверный формат", Toast.LENGTH_SHORT).show();
                return;
            }

            final String password = passwordEditText.getText().toString().trim();

            if (TextUtils.isEmpty(password)) {
                Toast.makeText(activity, "Введите пароль", Toast.LENGTH_SHORT).show();
                return;
            }
            if (password.length() <= 4) {
                Toast.makeText(activity, "Введите минимум 5 символов", Toast.LENGTH_SHORT).show();
                return;
            }

            WorkManager.getInstance(context).getWorkInfoByIdLiveData(authViewModel.login(phone, password)).observe(getViewLifecycleOwner(), workInfo -> {
                if (workInfo.getState() == WorkInfo.State.FAILED || workInfo.getState() == WorkInfo.State.CANCELLED) {
                    progressBar.setVisibility(View.GONE);
                    signInBtn.setEnabled(true);
                    signInBtn.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.btn_purple, null));
                    Toast.makeText(context, workInfo.getOutputData().getString(Constants.REQUEST_ERROR), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                    final String bearerToken = workInfo.getOutputData().getString(Constants.BEARER_TOKEN);

                    SharedPrefs.getInstance(context).putString(Constants.BEARER_TOKEN, bearerToken);
                    SharedPrefs.getInstance(context).putString(Constants.PHONE, phone);

                    progressBar.setVisibility(View.GONE);
                    signInBtn.setEnabled(true);
                    signInBtn.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.btn_purple, null));

                    final InputPhoneFragmentDirections.ActionInputPhoneFragmentToInputSmsFragment action = InputPhoneFragmentDirections.actionInputPhoneFragmentToInputSmsFragment();
                    action.setPhone(phone);
                    NavHostFragment.findNavController(InputPhoneFragment.this).navigate(action);
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                signInBtn.setEnabled(false);
                signInBtn.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.btn_locked, null));
            });
        });

        parentLayout.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            final ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) signInBtn.getLayoutParams();
            if (DeviceDisplay.getInstance(activity).getKeyboardHeight(parentLayout) > 500) {
                layoutParams.topToBottom = forgotPasswordTextView.getId();
                layoutParams.topMargin = DeviceDisplay.getInstance(activity).dpToPx(context, 50);
            }
            else if (DeviceDisplay.getInstance(activity).getKeyboardHeight(parentLayout) == 0) {
                layoutParams.topToBottom = -1;
                layoutParams.topMargin = -1;
            }
            signInBtn.setLayoutParams(layoutParams);
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final AuthViewModelFactory factory = new AuthViewModelFactory(context);

        authViewModel = new ViewModelProvider(getViewModelStore(), factory).get(AuthViewModel.class);
    }

    private static final String TAG = InputPhoneFragment.class.toString();
}
