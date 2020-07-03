package uz.alex.its.beverlee.view.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import uz.alex.its.beverlee.R;
import uz.alex.its.beverlee.model.DeviceDisplay;
import uz.alex.its.beverlee.view.AnimateBtnAsyncTask;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class DebitFragment extends Fragment {
    private FragmentActivity activity;
    private Context context;

    private View parentLayout;
    private ImageView backArrowImageView;
    private ImageView plusImageView;
    private TextView accessibleSumTextView;
    private EditText sumEditText;
    private RelativeLayout chooseCardField;
    private EditText chooseCardEditText;
    private CircularProgressButton debitBtn;

    public DebitFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getActivity() != null) {
            activity = getActivity();
            context = activity.getApplicationContext();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_debit, container, false);

        parentLayout = root;
        backArrowImageView = root.findViewById(R.id.back_arrow_image_view);
        plusImageView = root.findViewById(R.id.plus_image_view);
        sumEditText = root.findViewById(R.id.sum_edit_text);
        chooseCardField = root.findViewById(R.id.choose_card_field);
        chooseCardEditText = root.findViewById(R.id.choose_card_edit_text);
        accessibleSumTextView = root.findViewById(R.id.accessible_number_text_view);
        debitBtn = root.findViewById(R.id.debit_btn);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String summaryText = "<font color=#3B3A39>$ 23 650,</font><font color=#BABAB9>92</font>";
        accessibleSumTextView.setText(Html.fromHtml(summaryText));

        backArrowImageView.setOnClickListener(v -> {
            if (activity.getCurrentFocus() == null) {
                activity.onBackPressed();
                return;
            }
            InputMethodManager imm = (InputMethodManager)activity.getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
            activity.getCurrentFocus().clearFocus();
        });

        plusImageView.setOnClickListener(v -> {
            final Fragment addCardFragment = new AddCardFragment();
            activity.getSupportFragmentManager().beginTransaction().addToBackStack(null).add(R.id.operations_fragment_container, addCardFragment).commit();
        });

        sumEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                sumEditText.setBackgroundResource(R.drawable.edit_text_active);
                sumEditText.setHint("");
                return;
            }
            if (sumEditText.getText().length() > 0) {
                sumEditText.setBackgroundResource(R.drawable.edit_text_filled);
                sumEditText.setHint("");
                return;
            }
            sumEditText.setBackgroundResource(R.drawable.edit_text_locked);
            sumEditText.setHint(R.string.zero);
        });

        chooseCardEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                chooseCardEditText.setBackgroundResource(R.drawable.edit_text_active);
                chooseCardEditText.setHint("");
                return;
            }
            if (chooseCardEditText.getText().length() > 0) {
                chooseCardEditText.setBackgroundResource(R.drawable.edit_text_filled);
                chooseCardEditText.setHint("");
                return;
            }
            chooseCardEditText.setBackgroundResource(R.drawable.edit_text_locked);
            chooseCardEditText.setHint(R.string.zero);
        });

        debitBtn.setOnClickListener(v -> {
            new AnimateBtnAsyncTask(getActivity(), debitBtn).execute();
        });

        parentLayout.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            final ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) debitBtn.getLayoutParams();
            if (DeviceDisplay.getInstance(activity).getKeyboardHeight(parentLayout) > 500) {
                layoutParams.topToBottom = chooseCardField.getId();
                layoutParams.topMargin = DeviceDisplay.getInstance(activity).dpToPx(context, 50);
            }
            else if (DeviceDisplay.getInstance(activity).getKeyboardHeight(parentLayout) == 0) {
                layoutParams.topToBottom = -1;
                layoutParams.topMargin = -1;
            }
            debitBtn.setLayoutParams(layoutParams);
        });
    }
}
