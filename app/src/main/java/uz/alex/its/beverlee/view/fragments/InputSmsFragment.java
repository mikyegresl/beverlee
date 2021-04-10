package uz.alex.its.beverlee.view.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import uz.alex.its.beverlee.R;
import uz.alex.its.beverlee.model.actor.User;
import uz.alex.its.beverlee.storage.SharedPrefs;
import uz.alex.its.beverlee.utils.Constants;
import uz.alex.its.beverlee.view.CounterTask;
import uz.alex.its.beverlee.view.UiUtils;
import uz.alex.its.beverlee.view.activities.MainActivity;
import uz.alex.its.beverlee.viewmodel.AuthViewModel;
import uz.alex.its.beverlee.viewmodel_factory.AuthViewModelFactory;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class InputSmsFragment extends Fragment {
    private Context context;

    private AuthViewModel authViewModel;

    private TextView openInstructionTextView;
    private TextView instructionTextView;
    private TextView counterTextView;
    private EditText phoneEditText;
    private EditText verificationCodeEditText;
    private Button sendAgainBtn;
    private Button callBtn;

    private Animation bubbleAnimation;

    private Resources resources;
    private CounterTask counterTask;

    private User currentUser;

    public InputSmsFragment() { }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = requireContext();
        resources = getResources();

        if (getActivity() != null) {
            if (getActivity().getCurrentFocus() != null) {
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                getActivity().getCurrentFocus().clearFocus();
            }
        }
        if (getArguments() != null) {
            currentUser = new User(InputSmsFragmentArgs.fromBundle(getArguments()).getFirstName(),
                    InputSmsFragmentArgs.fromBundle(getArguments()).getLastName(),
                    InputSmsFragmentArgs.fromBundle(getArguments()).getPhone(),
                    InputSmsFragmentArgs.fromBundle(getArguments()).getEmail(),
                    InputSmsFragmentArgs.fromBundle(getArguments()).getCountryId(),
                    InputSmsFragmentArgs.fromBundle(getArguments()).getCity());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_input_sms, container, false);

        openInstructionTextView = root.findViewById(R.id.open_verification_instruction_text_view);
        instructionTextView = root.findViewById(R.id.verification_instruction_text_view);
        phoneEditText = root.findViewById(R.id.phone_edit_text);
        verificationCodeEditText = root.findViewById(R.id.verification_code_edit_text);
        counterTextView = root.findViewById(R.id.counter_text_view);
        sendAgainBtn = root.findViewById(R.id.send_again_btn);
        callBtn = root.findViewById(R.id.call_btn);

        bubbleAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.bubble);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (counterTask != null) {
            if (!counterTask.isCancelled()) {
                counterTask.cancel(true);
            }
            counterTask = null;
        }
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        counterTask = new CounterTask(getResources(), counterTextView, sendAgainBtn, callBtn);
        counterTask.execute();

        phoneEditText.setText(currentUser.getPhone());
        phoneEditText.setOnFocusChangeListener((v, hasFocus) -> UiUtils.setFocusChange(phoneEditText, hasFocus, R.string.phone_hint));

        verificationCodeEditText.setOnFocusChangeListener((v, hasFocus) -> UiUtils.setFocusChange(verificationCodeEditText, hasFocus, R.string.enter_code));
        verificationCodeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 4) {
                    WorkManager.getInstance(context).getWorkInfoByIdLiveData(authViewModel.submitVerification(s.toString())).observe(getViewLifecycleOwner(), workInfo -> {
                        if (workInfo.getState() == WorkInfo.State.FAILED || workInfo.getState() == WorkInfo.State.CANCELLED) {
                            Toast.makeText(context, workInfo.getOutputData().getString(Constants.REQUEST_ERROR), Toast.LENGTH_SHORT).show();

                            if (counterTask != null) {
                                if (!counterTask.isCancelled()) {
                                    counterTask.cancel(true);
                                }
                                counterTask = null;
                            }
                            return;
                        }
                        if (workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                            if (counterTask != null) {
                                if (!counterTask.isCancelled()) {
                                    counterTask.cancel(true);
                                }
                                counterTask = null;
                            }

                            SharedPrefs.getInstance(context).putString(Constants.FIRST_NAME, currentUser.getFirstName());
                            SharedPrefs.getInstance(context).putString(Constants.LAST_NAME, currentUser.getLastName());
                            SharedPrefs.getInstance(context).putString(Constants.PHONE, currentUser.getPhone());
                            SharedPrefs.getInstance(context).putString(Constants.EMAIL, currentUser.getEmail());
                            SharedPrefs.getInstance(context).putLong(Constants.COUNTRY_ID, currentUser.getCountryId());
                            SharedPrefs.getInstance(context).putString(Constants.CITY, currentUser.getCity());
                            SharedPrefs.getInstance(context).putBoolean(Constants.PHONE_VERIFIED, true);

                            startActivity(new Intent(requireActivity(), MainActivity.class));
                            requireActivity().finish();
                        }
                    });
                }
            }
        });

        openInstructionTextView.setOnClickListener(v -> {
            if (instructionTextView.getVisibility() == View.VISIBLE) {
                instructionTextView.setVisibility(View.GONE);
            }
            else if (instructionTextView.getVisibility() == View.GONE) {
                instructionTextView.setVisibility(View.VISIBLE);
            }
        });

        sendAgainBtn.setOnClickListener(v -> {
            sendAgainBtn.startAnimation(AnimationUtils.loadAnimation(context, R.anim.bubble));

            authViewModel.verifyPhoneBySms();
            new CounterTask(getResources(), counterTextView, sendAgainBtn, callBtn).execute();
        });

        callBtn.setOnClickListener(v -> {
            callBtn.startAnimation(AnimationUtils.loadAnimation(context, R.anim.bubble));

            authViewModel.verifyPhoneByCall();
            new CounterTask(getResources(), counterTextView, sendAgainBtn, callBtn).execute();
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final AuthViewModelFactory factory = new AuthViewModelFactory(context);

        authViewModel = new ViewModelProvider(getViewModelStore(), factory).get(AuthViewModel.class);

        WorkManager.getInstance(context).getWorkInfoByIdLiveData(authViewModel.verifyPhoneBySms()).observe(getViewLifecycleOwner(), workInfo -> {
            if (workInfo.getState() == WorkInfo.State.FAILED || workInfo.getState() == WorkInfo.State.CANCELLED) {
                Toast.makeText(context, workInfo.getOutputData().getString(Constants.REQUEST_ERROR), Toast.LENGTH_SHORT).show();
                return;
            }
            if (workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                //do nothing:
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private static final String TAG = InputSmsFragment.class.toString();
}
