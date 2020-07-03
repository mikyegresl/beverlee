package uz.alex.its.beverlee.view.fragments;

import android.annotation.SuppressLint;
import android.graphics.Point;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import uz.alex.its.beverlee.R;
import uz.alex.its.beverlee.model.Country;
import uz.alex.its.beverlee.model.DeviceDisplay;
import uz.alex.its.beverlee.view.adapters.CountryAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {
    private EditText fullNameEditText;
    private AutoCompleteTextView phoneField;
    private EditText passwordEditText;
    private ImageView passwordEyeImageView;
    private EditText passwordRepeatedEditText;
    private ImageView passwordRepeatedEyeImageView;
    private TextView userAgreementTextView;
    private Button signUpBtn;

    private Animation bubbleAnimation;

    private static boolean showPassword = false;
    private static boolean showPasswordRepeated = false;

    private ConstraintLayout cardLayout;
    private NestedScrollView nestedScrollView;

    private final Map<Country, String> countryMap = new HashMap<>();

    public SignUpFragment() {
        // Required empty public constructor
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_sign_up, container, false);

        fullNameEditText = root.findViewById(R.id.full_name_edit_text);
        phoneField = root.findViewById(R.id.phone_field);
        passwordEditText = root.findViewById(R.id.password_edit_text);
        passwordEyeImageView = root.findViewById(R.id.password_eye_image_view);
        passwordRepeatedEditText = root.findViewById(R.id.password_repeated_edit_text);
        passwordRepeatedEyeImageView = root.findViewById(R.id.password_repeated_eye_image_view);
        userAgreementTextView = root.findViewById(R.id.user_agreement_text_view);
        signUpBtn = root.findViewById(R.id.sign_up_button);

        bubbleAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.bubble);

        nestedScrollView = root.findViewById(R.id.scroll_layout);
        cardLayout = root.findViewById(R.id.card_layout);

        //populate
        final CountryAdapter adapter = new CountryAdapter(getContext(), R.layout.country_item, countryMap);
        phoneField.setThreshold(2);
        phoneField.setAdapter(adapter);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fullNameEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                fullNameEditText.setBackgroundResource(R.drawable.edit_text_active);
                fullNameEditText.setHint("");
                return;
            }
            if (fullNameEditText.getText().length() > 0) {
                fullNameEditText.setBackgroundResource(R.drawable.edit_text_filled);
                fullNameEditText.setHint("");
                return;
            }
            fullNameEditText.setBackgroundResource(R.drawable.edit_text_locked);
            fullNameEditText.setHint(R.string.username_hint);
        });

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
            Log.i(TAG, "passwordEye clicked()" + showPassword);
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

        passwordRepeatedEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                passwordRepeatedEditText.setBackgroundResource(R.drawable.edit_text_active);
                passwordRepeatedEditText.setHint("");
                return;
            }
            if (passwordRepeatedEditText.getText().length() > 0) {
                passwordRepeatedEditText.setBackgroundResource(R.drawable.edit_text_filled);
                passwordRepeatedEditText.setHint("");
                return;
            }
            passwordRepeatedEditText.setBackgroundResource(R.drawable.edit_text_locked);
            passwordRepeatedEditText.setHint(R.string.password_hint);
        });

        passwordRepeatedEyeImageView.setOnClickListener(v -> {
            Log.i(TAG, "passwordEye clicked()" + showPasswordRepeated);
            if (showPasswordRepeated) {
                passwordRepeatedEyeImageView.setImageResource(R.drawable.ic_eye);
                passwordRepeatedEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                showPasswordRepeated = false;
            }
            else {
                passwordRepeatedEyeImageView.setImageResource(R.drawable.ic_eye_purple);
                passwordRepeatedEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                showPasswordRepeated = true;
            }
        });

        signUpBtn.setOnClickListener(v -> {
            signUpBtn.startAnimation(bubbleAnimation);
            signUpBtn.postOnAnimationDelayed(() -> {
                NavHostFragment.findNavController(SignUpFragment.this).navigate(R.id.action_signUpFragment_to_inputSmsFragment);

            }, 100);
        });
    }

    private static final String TAG = SignUpFragment.class.toString();
}
