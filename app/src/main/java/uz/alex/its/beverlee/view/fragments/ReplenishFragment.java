package uz.alex.its.beverlee.view.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import uz.alex.its.beverlee.R;
import uz.alex.its.beverlee.model.DeviceDisplay;
import uz.alex.its.beverlee.view.AnimateBtnAsyncTask;
import uz.alex.its.beverlee.view.UiUtils;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class ReplenishFragment extends Fragment {
    private FragmentActivity activity;
    private Context context;

    private View parentLayout;
    private ImageView backArrowImageView;
    private EditText amountEditText;
    private TextView amountWithCommissionTextView;
    private CircularProgressButton replenishBtn;

    public ReplenishFragment() {
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
        final View root = inflater.inflate(R.layout.fragment_replenish, container, false);

        parentLayout = root;
        backArrowImageView = root.findViewById(R.id.back_arrow_image_view);
        amountEditText = root.findViewById(R.id.amount_edit_text);
        amountWithCommissionTextView = root.findViewById(R.id.amount_with_commission_text_view);
        replenishBtn = root.findViewById(R.id.replenish_btn);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String summaryText = "<font color=#3B3A39>$ 23 650,</font><font color=#BABAB9>92</font>";
        amountWithCommissionTextView.setText(getString(R.string.amount_with_commission, 0));

        backArrowImageView.setOnClickListener(v -> {
            if (activity.getCurrentFocus() == null) {
                activity.onBackPressed();
                return;
            }
            InputMethodManager imm = (InputMethodManager)activity.getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
            activity.getCurrentFocus().clearFocus();
        });

        amountEditText.setOnFocusChangeListener((v, hasFocus) -> {
            UiUtils.setFocusChange(amountEditText, hasFocus, R.string.zero);
        });

        replenishBtn.setOnClickListener(v -> {
            new AnimateBtnAsyncTask(getActivity(), replenishBtn).execute();
        });

//        parentLayout.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
//            final ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) replenishBtn.getLayoutParams();
//            if (DeviceDisplay.getInstance(activity).getKeyboardHeight(parentLayout) > 500) {
//                layoutParams.topToBottom = chooseCardField.getId();
//                layoutParams.topMargin = DeviceDisplay.getInstance(activity).dpToPx(context, 50);
//            }
//            else if (DeviceDisplay.getInstance(activity).getKeyboardHeight(parentLayout) == 0) {
//                layoutParams.topToBottom = -1;
//                layoutParams.topMargin = -1;
//            }
//            replenishBtn.setLayoutParams(layoutParams);
//        });
    }
}
