package uz.alex.its.beverlee.view.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import uz.alex.its.beverlee.R;
import uz.alex.its.beverlee.model.transaction.Card;
import uz.alex.its.beverlee.view.AnimateBtnAsyncTask;
import uz.alex.its.beverlee.view.UiUtils;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class WithdrawalFragment extends Fragment {
    private Activity activity;
    private Context context;

    private View activityRootView;
    private ImageView backArrowImageView;
    private TextView recipientDataTextView;
    private EditText fullNameEditText;
    private EditText phoneEditText;
    private AutoCompleteTextView countryAutoCompleteTextView;
    private EditText cityEditText;
    private EditText amountEditText;
    private TextView amountWithCommissionTextView;
    private CircularProgressButton withdrawBtn;

    private final List<Card> cardList = new ArrayList<>();

    private NestedScrollView nestedScrollView;

    public WithdrawalFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getActivity() != null) {
            this.context = getContext();
            activity = getActivity();
        }

        cardList.add(new Card(1, "6346 5785 1234 9567", "01/20","Моника Белучи"));
        cardList.add(new Card(1, "6346 5785 1234 9567", "01/20","Галь Гадот"));
        cardList.add(new Card(1, "6346 5785 1234 9567", "01/20","Ирина Шейк"));
        cardList.add(new Card(1, "6346 5785 1234 9567", "01/20","Анна Серябкина"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_withdrawal, container, false);

        if (getActivity() != null) {
            activityRootView = getActivity().findViewById(R.id.activity_view_root);
        }
        backArrowImageView = root.findViewById(R.id.back_arrow_image_view);
        recipientDataTextView = root.findViewById(R.id.recipient_data_text_view);
        fullNameEditText = root.findViewById(R.id.full_name_edit_text);
        phoneEditText = root.findViewById(R.id.phone_edit_text);
        countryAutoCompleteTextView = root.findViewById(R.id.country_spinner);
        cityEditText = root.findViewById(R.id.city_edit_text);
        amountEditText = root.findViewById(R.id.amount_edit_text);
        amountWithCommissionTextView = root.findViewById(R.id.amount_with_commission_text_view);
        withdrawBtn = root.findViewById(R.id.withdraw_btn);
        nestedScrollView = root.findViewById(R.id.scroll_layout);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recipientDataTextView.setText(getString(R.string.recipient_data, "Western Union"));
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

        withdrawBtn.setOnClickListener(v -> {
            new AnimateBtnAsyncTask(getActivity(), withdrawBtn).execute();
        });
    }

    private static final String TAG = WithdrawalFragment.class.toString();
}