package uz.alex.its.beverlee.view.fragments;

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
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import uz.alex.its.beverlee.R;
import uz.alex.its.beverlee.view.CounterTask;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class InputSmsFragment extends Fragment {
    private static final String TAG = InputSmsFragment.class.toString();
    private EditText codeEditText;
    private TextView lastTwoDigitsTextView;
    private TextView counterTextView;
    private Button sendAgainBtn;

    private Animation bubbleAnimation;

    private Resources resources;
//    private CounterTask counterTask;

    public InputSmsFragment() { }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resources = getResources();

        if (getActivity() != null) {
            if (getActivity().getCurrentFocus() != null) {
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                getActivity().getCurrentFocus().clearFocus();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_input_sms, container, false);

        codeEditText = root.findViewById(R.id.code_edit_text);
        lastTwoDigitsTextView = root.findViewById(R.id.last_two_digits_text_view);
        sendAgainBtn = root.findViewById(R.id.send_again_btn);
        counterTextView = root.findViewById(R.id.counter_text_view);

        bubbleAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.bubble);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        if (counterTask != null) {
//            if (!counterTask.isCancelled()) {
//                counterTask.cancel(true);
//            }
//            counterTask = null;
//        }
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "onViewCreated: ");
//        counterTask = new CounterTask(getResources(), codeEditText, counterTextView, sendAgainBtn);
//        counterTask.execute();

        codeEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                codeEditText.setBackgroundResource(R.drawable.edit_text_active);
                codeEditText.setHint("");
                return;
            }
            if (codeEditText.getText().length() > 0) {
                codeEditText.setBackgroundResource(R.drawable.edit_text_filled);
                codeEditText.setHint("");
                return;
            }
            codeEditText.setBackgroundResource(R.drawable.edit_text_locked);
            codeEditText.setHint(R.string.input_code);
        });

        sendAgainBtn.setOnClickListener(v -> {
            sendAgainBtn.startAnimation(bubbleAnimation);
            sendAgainBtn.postOnAnimationDelayed(() -> NavHostFragment.findNavController(InputSmsFragment.this).navigate(R.id.action_inputSmsFragment_to_fingerprintFragment), 100);
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

}
