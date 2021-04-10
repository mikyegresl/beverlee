package uz.alex.its.beverlee.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

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
import uz.alex.its.beverlee.model.actor.User;
import uz.alex.its.beverlee.storage.SharedPrefs;
import uz.alex.its.beverlee.utils.Constants;
import uz.alex.its.beverlee.view.UiUtils;
import uz.alex.its.beverlee.viewmodel.AuthViewModel;
import uz.alex.its.beverlee.viewmodel_factory.AuthViewModelFactory;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class PersonalDataFragment extends Fragment {
    private ImageView backArrowImageView;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText middleNameEditText;
    private EditText positionEditText;
    private EditText phoneEditText;
    private EditText emailEditText;
    private EditText countryEditText;
    private EditText cityEditText;
    private EditText addressEditText;
    private Button saveBtn;

    private Animation bubbleAnimation;

    private NestedScrollView nestedScrollView;

    private User currentUser;

    public PersonalDataFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        currentUser = new User(SharedPrefs.getInstance(requireContext()).getString(Constants.FIRST_NAME),
                SharedPrefs.getInstance(requireContext()).getString(Constants.LAST_NAME),
                SharedPrefs.getInstance(requireContext()).getString(Constants.PHONE),
                SharedPrefs.getInstance(requireContext()).getString(Constants.EMAIL),
                SharedPrefs.getInstance(requireContext()).getLong(Constants.COUNTRY_ID),
                SharedPrefs.getInstance(requireContext()).getString(Constants.CITY));
        currentUser.setMiddleName(SharedPrefs.getInstance(requireContext()).getString(Constants.MIDDLE_NAME));
        currentUser.setPosition(SharedPrefs.getInstance(requireContext()).getString(Constants.POSITION));
        currentUser.setAddress(SharedPrefs.getInstance(requireContext()).getString(Constants.ADDRESS));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_personal_data, container, false);

        backArrowImageView = root.findViewById(R.id.back_arrow_image_view);
        firstNameEditText = root.findViewById(R.id.first_name_edit_text);
        lastNameEditText = root.findViewById(R.id.last_name_edit_text);
        middleNameEditText = root.findViewById(R.id.middle_name_edit_text);
        positionEditText = root.findViewById(R.id.position_edit_text);
        phoneEditText = root.findViewById(R.id.contact_number_edit_text);
        emailEditText = root.findViewById(R.id.email_edit_text);
        countryEditText = root.findViewById(R.id.country_spinner);
        cityEditText = root.findViewById(R.id.city_edit_text);
        addressEditText = root.findViewById(R.id.address_edit_text);
        saveBtn = root.findViewById(R.id.save_btn);

        bubbleAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.bubble);

        nestedScrollView = root.findViewById(R.id.scroll_layout);

        firstNameEditText.setText(currentUser.getFirstName());
        lastNameEditText.setText(currentUser.getLastName());
        middleNameEditText.setText(currentUser.getMiddleName());
        positionEditText.setText(currentUser.getPosition());
        phoneEditText.setText(currentUser.getPhone());
        emailEditText.setText(currentUser.getEmail());
        cityEditText.setText(currentUser.getCity());
        addressEditText.setText(currentUser.getAddress());

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
            UiUtils.setFocusChange(firstNameEditText, hasFocus, R.string.first_name_hint);
        });

        lastNameEditText.setOnFocusChangeListener((v, hasFocus) -> {
            UiUtils.setFocusChange(lastNameEditText, hasFocus, R.string.last_name_hint);
        });

        middleNameEditText.setOnFocusChangeListener((v, hasFocus) -> {
            UiUtils.setFocusChange(middleNameEditText, hasFocus, R.string.middle_name);
        });

        positionEditText.setOnFocusChangeListener((v, hasFocus) -> {
            UiUtils.setFocusChange(positionEditText, hasFocus, R.string.position);
        });

        phoneEditText.setOnFocusChangeListener((v, hasFocus) -> {
            UiUtils.setFocusChange(phoneEditText, hasFocus, R.string.phone_hint);
        });

        emailEditText.setOnFocusChangeListener((v, hasFocus) -> {
            UiUtils.setFocusChange(emailEditText, hasFocus, R.string.email_hint);
        });

        countryEditText.setOnFocusChangeListener((v, hasFocus) -> {
            UiUtils.setFocusChange(countryEditText, hasFocus, R.string.country);
        });

        cityEditText.setOnFocusChangeListener((v, hasFocus) -> {
            UiUtils.setFocusChange(cityEditText, hasFocus, R.string.city);
        });

        addressEditText.setOnFocusChangeListener((v, hasFocus) -> {
            UiUtils.setFocusChange(addressEditText, hasFocus, R.string.address);
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final AuthViewModelFactory factory = new AuthViewModelFactory(requireContext());

        final AuthViewModel viewModel = new ViewModelProvider(getViewModelStore(), factory).get(AuthViewModel.class);

        viewModel.getCountry(currentUser.getCountryId()).observe(getViewLifecycleOwner(), country -> {
            if (country != null) {
                countryEditText.setText(country.getTitle());
            }
        });
    }

    private static final String TAG = PersonalDataFragment.class.toString();
}