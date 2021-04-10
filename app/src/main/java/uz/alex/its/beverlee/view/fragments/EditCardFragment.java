package uz.alex.its.beverlee.view.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

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
import uz.alex.its.beverlee.model.DeviceDisplay;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditCardFragment extends Fragment {
    private FragmentActivity activity;
    private Context context;

    private View parentLayout;
    private ImageView backArrowImageView;
    private EditText cardNumberEditText;
    private EditText fullnameEditText;
    private EditText monthYearEditText;
    private EditText cardNameEditText;
    private Button editCardBtn;

    private Animation bubbleAnimation;

    private NestedScrollView nestedScrollView;

    public EditCardFragment() {
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
        final View root = inflater.inflate(R.layout.fragment_edit_card, container, false);

        parentLayout = root;
        backArrowImageView = root.findViewById(R.id.back_arrow_image_view);
        cardNumberEditText = root.findViewById(R.id.card_number_edit_text);
        fullnameEditText = root.findViewById(R.id.first_name_edit_text);
        monthYearEditText = root.findViewById(R.id.month_year_edit_text);
        cardNameEditText = root.findViewById(R.id.card_name_edit_text);
        editCardBtn = root.findViewById(R.id.edit_card_btn);

        bubbleAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.bubble);

        nestedScrollView = root.findViewById(R.id.scroll_layout);

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

        cardNumberEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                cardNumberEditText.setBackgroundResource(R.drawable.edit_text_active);
                cardNumberEditText.setHint("");
                return;
            }
            if (cardNumberEditText.getText().length() > 0) {
                cardNumberEditText.setBackgroundResource(R.drawable.edit_text_filled);
                cardNumberEditText.setHint("");
                return;
            }
            cardNumberEditText.setBackgroundResource(R.drawable.edit_text_locked);
            cardNumberEditText.setHint(R.string.card_no);
        });

        fullnameEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                fullnameEditText.setBackgroundResource(R.drawable.edit_text_active);
                fullnameEditText.setHint("");
                return;
            }
            if (fullnameEditText.getText().length() > 0) {
                fullnameEditText.setBackgroundResource(R.drawable.edit_text_filled);
                fullnameEditText.setHint("");
                return;
            }
            fullnameEditText.setBackgroundResource(R.drawable.edit_text_locked);
            fullnameEditText.setHint(R.string.card_username);
        });

        monthYearEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                monthYearEditText.setBackgroundResource(R.drawable.edit_text_active);
                monthYearEditText.setHint("");
                return;
            }
            if (monthYearEditText.getText().length() > 0) {
                monthYearEditText.setBackgroundResource(R.drawable.edit_text_filled);
                monthYearEditText.setHint("");
                return;
            }
            monthYearEditText.setBackgroundResource(R.drawable.edit_text_locked);
            monthYearEditText.setHint(R.string.card_expiration_date);
        });

        cardNameEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                nestedScrollView.postDelayed(() -> {
                    nestedScrollView.smoothScrollTo(0, editCardBtn.getBottom());
                }, 100);

                cardNameEditText.setBackgroundResource(R.drawable.edit_text_active);
                cardNameEditText.setHint("");
                return;
            }
            if (cardNameEditText.getText().length() > 0) {
                cardNameEditText.setBackgroundResource(R.drawable.edit_text_filled);
                cardNameEditText.setHint("");
                return;
            }
            cardNameEditText.setBackgroundResource(R.drawable.edit_text_locked);
            cardNameEditText.setHint(R.string.card_name);
        });

        editCardBtn.setOnClickListener(v -> {
            editCardBtn.startAnimation(bubbleAnimation);
            editCardBtn.postOnAnimationDelayed(() -> {
                if (getActivity() != null) {
                    getActivity().onBackPressed();
                }
            }, 100);
        });

        parentLayout.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            final ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) editCardBtn.getLayoutParams();
            if (DeviceDisplay.getInstance(activity).getKeyboardHeight(parentLayout) > 500) {
                layoutParams.topToBottom = cardNameEditText.getId();
                layoutParams.topMargin = DeviceDisplay.getInstance(activity).dpToPx(context, 50);
            }
            else if (DeviceDisplay.getInstance(activity).getKeyboardHeight(parentLayout) == 0) {
                layoutParams.topToBottom = -1;
                layoutParams.topMargin = -1;
            }
            editCardBtn.setLayoutParams(layoutParams);
        });
    }
}
