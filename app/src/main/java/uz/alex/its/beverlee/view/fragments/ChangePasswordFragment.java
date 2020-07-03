package uz.alex.its.beverlee.view.fragments;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.fragment.NavHostFragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import uz.alex.its.beverlee.R;
import uz.alex.its.beverlee.model.DeviceDisplay;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class ChangePasswordFragment extends Fragment {
    private static final String TAG = ChangePasswordFragment.class.toString();
    private FragmentActivity activity;
    private Context context;

    private ImageView backArrowImageView;
    private EditText oldPasswordEditText;
    private EditText newPasswordEditText;
    private EditText newPasswordRepeatedEditText;
    private ImageView newPasswordEye;
    private ImageView newPasswordRepeatedEye;
    private Button saveBtn;

    private boolean showNewPassword = false;
    private boolean showNewRepeatedPassword = false;
    private boolean keyboardIsShown = false;

    private Animation bubbleAnimation;

    private NestedScrollView nestedScrollView;

    //to handle keyboard popup
    private View parentLayout;
    private RelativeLayout confirmPasswordField;

    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getActivity() != null) {
            activity = getActivity();
            context = activity.getApplicationContext();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_change_password, container, false);

        backArrowImageView = root.findViewById(R.id.back_arrow_image_view);
        oldPasswordEditText = root.findViewById(R.id.old_password_edit_text);
        newPasswordEditText = root.findViewById(R.id.new_password_edit_text);
        newPasswordRepeatedEditText = root.findViewById(R.id.confirm_new_password_edit_text);
        newPasswordEye = root.findViewById(R.id.new_password_eye_image_view);
        newPasswordRepeatedEye = root.findViewById(R.id.confirm_new__password_eye_image_view);
        saveBtn = root.findViewById(R.id.save_btn);

        bubbleAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.bubble);

        nestedScrollView = root.findViewById(R.id.scroll_layout);

        parentLayout = root;
        confirmPasswordField = root.findViewById(R.id.confirm_new_password_field);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        backArrowImageView.setOnClickListener(v -> {
            if (activity.getCurrentFocus() == null) {
                activity.getSupportFragmentManager().popBackStack();
                return;
            }
            Log.i(TAG, "getCurrentFocus(): " + activity.getCurrentFocus());
            InputMethodManager imm = (InputMethodManager)activity.getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
            activity.getCurrentFocus().clearFocus();
        });

        oldPasswordEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                oldPasswordEditText.setBackgroundResource(R.drawable.edit_text_active);
                oldPasswordEditText.setHint("");
                return;
            }
            if (oldPasswordEditText.getText().length() > 0) {
                oldPasswordEditText.setBackgroundResource(R.drawable.edit_text_filled);
                oldPasswordEditText.setHint("");
                return;
            }
            oldPasswordEditText.setBackgroundResource(R.drawable.edit_text_locked);
            oldPasswordEditText.setHint(R.string.password_hint);
        });

        newPasswordEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                newPasswordEditText.setBackgroundResource(R.drawable.edit_text_active);
                newPasswordEditText.setHint("");
                return;
            }
            if (newPasswordEditText.getText().length() > 0) {
                newPasswordEditText.setBackgroundResource(R.drawable.edit_text_filled);
                newPasswordEditText.setHint("");
                return;
            }
            newPasswordEditText.setBackgroundResource(R.drawable.edit_text_locked);
            newPasswordEditText.setHint(R.string.password_hint);
        });

        newPasswordEye.setOnClickListener(v -> {
            if (showNewPassword) {
                newPasswordEye.setImageResource(R.drawable.ic_eye);
                newPasswordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                showNewPassword = false;
            }
            else {
                newPasswordEye.setImageResource(R.drawable.ic_eye_purple);
                newPasswordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                showNewPassword = true;
            }
        });

        newPasswordRepeatedEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                nestedScrollView.postDelayed(() -> {
                    nestedScrollView.smoothScrollTo(0, saveBtn.getBottom());
                }, 100);
                newPasswordRepeatedEditText.setBackgroundResource(R.drawable.edit_text_active);
                newPasswordRepeatedEditText.setHint("");
                return;
            }

            if (newPasswordRepeatedEditText.getText().length() > 0) {
                newPasswordRepeatedEditText.setBackgroundResource(R.drawable.edit_text_filled);
                newPasswordRepeatedEditText.setHint("");
                return;
            }
            newPasswordRepeatedEditText.setBackgroundResource(R.drawable.edit_text_locked);
            newPasswordRepeatedEditText.setHint(R.string.password_hint);
        });

        newPasswordRepeatedEye.setOnClickListener(v -> {
            if (showNewRepeatedPassword) {
                newPasswordRepeatedEye.setImageResource(R.drawable.ic_eye);
                newPasswordRepeatedEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                showNewRepeatedPassword = false;
            }
            else {
                newPasswordRepeatedEye.setImageResource(R.drawable.ic_eye_purple);
                newPasswordRepeatedEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                showNewRepeatedPassword = true;
            }
        });

        saveBtn.setOnClickListener(v -> {
            saveBtn.startAnimation(bubbleAnimation);
            saveBtn.postOnAnimationDelayed(() -> {
                activity.getSupportFragmentManager().popBackStack();
            }, 100);
        });

        parentLayout.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            final ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) confirmPasswordField.getLayoutParams();
            if (DeviceDisplay.getInstance(activity).getKeyboardHeight(parentLayout) > 500) {
                layoutParams.bottomToTop = saveBtn.getId();
                layoutParams.bottomMargin = DeviceDisplay.getInstance(activity).dpToPx(context, 50);
            }
            else if (DeviceDisplay.getInstance(activity).getKeyboardHeight(parentLayout) == 0) {
                layoutParams.bottomToTop = -1;
                layoutParams.bottomMargin = -1;
            }
            confirmPasswordField.setLayoutParams(layoutParams);
        });
    }
}