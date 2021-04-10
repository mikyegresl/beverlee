package uz.alex.its.beverlee.view.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import uz.alex.its.beverlee.R;
import uz.alex.its.beverlee.push.NotifyManager;

public class NotificationSettingsFragment extends Fragment {
    private ImageView backArrowImageView;
//    private Button saveBtn;

    private CheckBox notifyNewsCheckBox;
    private CheckBox notifyBonusesCheckBox;
    private CheckBox notifyIncomeCheckBox;
    private CheckBox notifyPurchaseCheckBox;
    private CheckBox notifyReplenishCheckBox;
    private CheckBox notifyWithdrawalCheckBox;

    private Animation bubbleAnimation;

    private NotifyManager notifyManager;

    public NotificationSettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        notifyManager = new NotifyManager(requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_notification_settings, container, false);

        backArrowImageView = root.findViewById(R.id.back_arrow_image_view);
//        saveBtn = root.findViewById(R.id.save_btn);

        final TextView notifyNewsTextView = root.findViewById(R.id.notify_news_text_view);
        final TextView notifyBonusesTextView = root.findViewById(R.id.notify_bonuses_text_view);
        final TextView notifyIncomeTextView = root.findViewById(R.id.notify_income_text_view);
        final TextView notifyPurchaseTextView = root.findViewById(R.id.notify_purchase_text_view);
        final TextView notifyReplenishTextView = root.findViewById(R.id.notify_replenish_text_view);
        final TextView notifyWithdrawalTextView = root.findViewById(R.id.notify_withdrawal_text_view);

        notifyNewsCheckBox = root.findViewById(R.id.notify_news_check_box);
        notifyBonusesCheckBox = root.findViewById(R.id.notify_bonuses_check_box);
        notifyIncomeCheckBox = root.findViewById(R.id.notify_income_check_box);
        notifyPurchaseCheckBox = root.findViewById(R.id.notify_purchase_check_box);
        notifyReplenishCheckBox = root.findViewById(R.id.notify_replenish_check_box);
        notifyWithdrawalCheckBox = root.findViewById(R.id.notify_withdrawal_check_box);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            notifyBonusesTextView.setVisibility(View.GONE);
            notifyIncomeTextView.setVisibility(View.GONE);
            notifyPurchaseTextView.setVisibility(View.GONE);
            notifyReplenishTextView.setVisibility(View.GONE);
            notifyWithdrawalTextView.setVisibility(View.GONE);

            notifyBonusesCheckBox.setVisibility(View.GONE);
            notifyIncomeCheckBox.setVisibility(View.GONE);
            notifyPurchaseCheckBox.setVisibility(View.GONE);
            notifyReplenishCheckBox.setVisibility(View.GONE);
            notifyWithdrawalCheckBox.setVisibility(View.GONE);

            notifyNewsTextView.setText(R.string.notify_default);
        }

        bubbleAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.bubble);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notifyNewsCheckBox.setChecked(notifyManager.areNewsEnabled());
            notifyBonusesCheckBox.setChecked(notifyManager.areBonusesEnabled());
            notifyIncomeCheckBox.setChecked(notifyManager.isIncomeEnabled());
            notifyPurchaseCheckBox.setChecked(notifyManager.isPurchaseEnabled());
            notifyReplenishCheckBox.setChecked(notifyManager.isReplenishEnabled());
            notifyWithdrawalCheckBox.setChecked(notifyManager.isWithdrawalEnabled());

            notifyNewsCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> notifyManager.setNewsEnabled(isChecked));
            notifyBonusesCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> notifyManager.setBonusesEnabled(isChecked));
            notifyIncomeCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> notifyManager.setIncomeEnabled(isChecked));
            notifyPurchaseCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> notifyManager.setPurchaseEnabled(isChecked));
            notifyReplenishCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> notifyManager.setReplenishEnabled(isChecked));
            notifyWithdrawalCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> notifyManager.setWithdrawalEnabled(isChecked));
        }
        else {
            notifyNewsCheckBox.setChecked(notifyManager.areNotificationsEnabled());
            notifyNewsCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> notifyManager.setNotificationsEnabled(isChecked));
        }

        backArrowImageView.setOnClickListener(v -> {
            if (getActivity() != null) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

//        saveBtn.setOnClickListener(v -> {
//            saveBtn.startAnimation(bubbleAnimation);
//            saveBtn.postOnAnimationDelayed(() -> {
//                if (getActivity() != null) {
//                    getActivity().getSupportFragmentManager().popBackStack();
//                }
//            }, 100);
//        });
    }
}