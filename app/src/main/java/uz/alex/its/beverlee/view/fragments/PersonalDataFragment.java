package uz.alex.its.beverlee.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import uz.alex.its.beverlee.R;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class PersonalDataFragment extends Fragment {
    private static final String TAG = PersonalDataFragment.class.toString();

    private ImageView backArrowImageView;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText positionEditText;
    private EditText contactNumberEditText;
    private EditText emailEditText;
    private EditText countryEditText;
    private Button saveBtn;

    private Animation bubbleAnimation;

    private NestedScrollView nestedScrollView;

    public PersonalDataFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_personal_data, container, false);

        backArrowImageView = root.findViewById(R.id.back_arrow_image_view);
        firstNameEditText = root.findViewById(R.id.first_name_edit_text);
        lastNameEditText = root.findViewById(R.id.last_name_edit_text);
        positionEditText = root.findViewById(R.id.position_edit_text);
        contactNumberEditText = root.findViewById(R.id.contact_number_edit_text);
        emailEditText = root.findViewById(R.id.email_edit_text);
        countryEditText = root.findViewById(R.id.country_edit_text);
        saveBtn = root.findViewById(R.id.save_btn);

        bubbleAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.bubble);

        nestedScrollView = root.findViewById(R.id.scroll_layout);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        backArrowImageView.setOnClickListener(v -> {
            if (getActivity() != null) {
                if (getActivity().getCurrentFocus() == null) {
                    getActivity().getSupportFragmentManager().popBackStack();
                    return;
                }
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                getActivity().getCurrentFocus().clearFocus();
            }
        });

        firstNameEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                firstNameEditText.setBackgroundResource(R.drawable.edit_text_active);
                firstNameEditText.setHint("");
                return;
            }
            if (firstNameEditText.getText().length() > 0) {
                firstNameEditText.setBackgroundResource(R.drawable.edit_text_filled);
                firstNameEditText.setHint("");
                return;
            }
            firstNameEditText.setBackgroundResource(R.drawable.edit_text_locked);
            firstNameEditText.setHint(R.string.first_name_example);
        });

        lastNameEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                lastNameEditText.setBackgroundResource(R.drawable.edit_text_active);
                lastNameEditText.setHint("");
                return;
            }
            if (lastNameEditText.getText().length() > 0) {
                lastNameEditText.setBackgroundResource(R.drawable.edit_text_filled);
                lastNameEditText.setHint("");
                return;
            }
            lastNameEditText.setBackgroundResource(R.drawable.edit_text_locked);
            lastNameEditText.setHint(R.string.last_name_example);
        });

        positionEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                positionEditText.setBackgroundResource(R.drawable.edit_text_active);
                positionEditText.setHint("");
                return;
            }
            if (positionEditText.getText().length() > 0) {
                positionEditText.setBackgroundResource(R.drawable.edit_text_filled);
                positionEditText.setHint("");
                return;
            }
            positionEditText.setBackgroundResource(R.drawable.edit_text_locked);
            positionEditText.setHint(R.string.position_example);
        });

        contactNumberEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                contactNumberEditText.setBackgroundResource(R.drawable.edit_text_active);
                contactNumberEditText.setHint("");
                return;
            }
            if (contactNumberEditText.getText().length() > 0) {
                contactNumberEditText.setBackgroundResource(R.drawable.edit_text_filled);
                contactNumberEditText.setHint("");
                return;
            }
            contactNumberEditText.setBackgroundResource(R.drawable.edit_text_locked);
            contactNumberEditText.setHint(R.string.contact_number_example);
        });

        emailEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                emailEditText.setBackgroundResource(R.drawable.edit_text_active);
                emailEditText.setHint("");
                return;
            }
            if (emailEditText.getText().length() > 0) {
                emailEditText.setBackgroundResource(R.drawable.edit_text_filled);
                emailEditText.setHint("");
                return;
            }
            emailEditText.setBackgroundResource(R.drawable.edit_text_locked);
            emailEditText.setHint(R.string.email_example);
        });

        countryEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                countryEditText.setBackgroundResource(R.drawable.edit_text_active);
                countryEditText.setHint("");
                return;
            }
            if (countryEditText.getText().length() > 0) {
                countryEditText.setBackgroundResource(R.drawable.edit_text_filled);
                countryEditText.setHint("");
                return;
            }
            countryEditText.setBackgroundResource(R.drawable.edit_text_locked);
            countryEditText.setHint(R.string.country_example);
        });

        saveBtn.setOnClickListener(v -> {
            saveBtn.startAnimation(bubbleAnimation);
            saveBtn.postOnAnimationDelayed(() -> {
                if (getActivity() != null) {
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            }, 100);
        });
    }
}