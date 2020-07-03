package uz.alex.its.beverlee.view.fragments;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.fragment.NavHostFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uz.alex.its.beverlee.R;
import uz.alex.its.beverlee.model.Country;
import uz.alex.its.beverlee.model.DeviceDisplay;
import uz.alex.its.beverlee.view.adapters.CountryAdapter;

public class InputPhoneFragment extends Fragment {
    private static final String TAG = InputPhoneFragment.class.toString();
    private FragmentActivity activity;
    private Context context;

    private View parentLayout;
    private AutoCompleteTextView phoneField;
    private EditText passwordEditText;
    private ImageView passwordEyeImageView;
    private TextView forgotPasswordTextView;
    private Button signInBtn;

    private Animation bubbleAnimation;

    private static boolean showPassword = false;

    private final Map<Country, String> countryMap = new HashMap<>();

    public InputPhoneFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        countryMap.put(new Country("Uzbeksitan", R.drawable.ic_uzbekistan), "+998");
        countryMap.put(new Country("Abkhazia", R.drawable.ic_abkhazia), "+995");
        countryMap.put(new Country("Afghanistan", R.drawable.ic_afganistan), "+93");
        countryMap.put(new Country("Barbados", R.drawable.ic_barbados), "+1246");
        countryMap.put(new Country("Belarus", R.drawable.ic_belarus), "+375");
        countryMap.put(new Country("Brazil", R.drawable.ic_brazil), "+55");

        if (getActivity() != null) {
            activity = getActivity();
            context = activity.getApplicationContext();
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
        phoneField = root.findViewById(R.id.phone_field);
        passwordEditText = root.findViewById(R.id.password_edit_text);
        passwordEyeImageView = root.findViewById(R.id.password_eye_image_view);
        forgotPasswordTextView = root.findViewById(R.id.forgot_password_text_view);
        signInBtn = root.findViewById(R.id.login_btn);

        bubbleAnimation = AnimationUtils.loadAnimation(context, R.anim.bubble);

        //populate
        final CountryAdapter adapter = new CountryAdapter(context, R.layout.country_item, countryMap);
        phoneField.setThreshold(2);
        phoneField.setAdapter(adapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        phoneField.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                phoneField.setBackgroundResource(R.drawable.edit_text_active);
                phoneField.setHint("");
                return;
            }
            if (phoneField.getText().length() > 0) {
                phoneField.setBackgroundResource(R.drawable.edit_text_filled);
                phoneField.setHint("");
                return;
            }
            phoneField.setBackgroundResource(R.drawable.edit_text_locked);
            phoneField.setHint(R.string.phone_hint);
        });

        passwordEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                passwordEditText.setBackgroundResource(R.drawable.edit_text_active);
                passwordEditText.setHint("");
                return;
            }
            if (passwordEditText.getText().length() > 0) {
                passwordEditText.setBackgroundResource(R.drawable.edit_text_filled);
                passwordEditText.setHint("");
                return;
            }
            passwordEditText.setBackgroundResource(R.drawable.edit_text_locked);
            passwordEditText.setHint(R.string.password_hint);
        });

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

        phoneField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() >= 3 && phoneField.isSelected()) {
                    phoneField.dismissDropDown();
                    return;
                }
                if (s.length() < 3) {
                    phoneField.setSelected(false);

                    if (!s.toString().startsWith(getResources().getString(R.string.phone_hint))) {
                        phoneField.setText(R.string.phone_hint);
                        Selection.setSelection(phoneField.getText(), phoneField.getText().length());
                    }
                }
            }
        });

        phoneField.setOnItemClickListener((adapterView, element, position, id) -> {
            if (adapterView != null) {
                Country country = (Country) adapterView.getItemAtPosition(position);
                phoneField.setText(countryMap.get(country));
                phoneField.setCompoundDrawablesWithIntrinsicBounds(country.getResourceId(), 0, 0, 0);
                phoneField.setSelected(true);
                phoneField.setSelection(phoneField.getText().length());
            }
        });

        signInBtn.setOnClickListener(v -> {
            signInBtn.startAnimation(bubbleAnimation);
            signInBtn.postOnAnimationDelayed(() -> NavHostFragment.findNavController(InputPhoneFragment.this).navigate(R.id.action_inputPhoneFragment_to_inputSmsFragment), 100);
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
}
