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
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import uz.alex.its.beverlee.R;
import uz.alex.its.beverlee.model.DeviceDisplay;
import uz.alex.its.beverlee.view.AnimateBtnAsyncTask;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransferFragment extends Fragment {
    private FragmentActivity activity;
    private Context context;

    private View parentLayout;
    private ImageView backArrowImageView;
    private EditText senderEditText;
    private EditText sumEditText;
    private CircularProgressButton transferBtn;

    public TransferFragment() {
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
        final View root = inflater.inflate(R.layout.fragment_transfer, container, false);

        parentLayout = root;
        backArrowImageView = root.findViewById(R.id.back_arrow_image_view);
        senderEditText = root.findViewById(R.id.sender_edit_text);
        sumEditText = root.findViewById(R.id.sum_edit_text);
        transferBtn = root.findViewById(R.id.transfer_btn);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        backArrowImageView.setOnClickListener(v -> {
            if (activity.getCurrentFocus() == null) {
                activity.onBackPressed();
                return;
            }
            InputMethodManager imm = (InputMethodManager)activity.getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
            activity.getCurrentFocus().clearFocus();
        });

        senderEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                senderEditText.setBackgroundResource(R.drawable.edit_text_active);
                senderEditText.setHint("");
                return;
            }
            if (senderEditText.getText().length() > 0) {
                senderEditText.setBackgroundResource(R.drawable.edit_text_filled);
                senderEditText.setHint("");
                return;
            }
            senderEditText.setBackgroundResource(R.drawable.edit_text_locked);
            senderEditText.setHint(R.string.fullname_or_id);
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

        transferBtn.setOnClickListener(v -> {
            new AnimateBtnAsyncTask(getActivity(), transferBtn).execute();
        });

        parentLayout.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            final ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) transferBtn.getLayoutParams();
            if (DeviceDisplay.getInstance(activity).getKeyboardHeight(parentLayout) > 500) {
                layoutParams.topToBottom = sumEditText.getId();
                layoutParams.topMargin = DeviceDisplay.getInstance(activity).dpToPx(context, 50);
            }
            else if (DeviceDisplay.getInstance(activity).getKeyboardHeight(parentLayout) == 0) {
                layoutParams.topToBottom = -1;
                layoutParams.topMargin = -1;
            }
            transferBtn.setLayoutParams(layoutParams);
        });
    }
}
