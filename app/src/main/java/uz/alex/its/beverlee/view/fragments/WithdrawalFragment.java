package uz.alex.its.beverlee.view.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import br.com.simplepass.loadingbutton.customViews.OnAnimationEndListener;
import uz.alex.its.beverlee.R;
import uz.alex.its.beverlee.model.Card;
import uz.alex.its.beverlee.model.Contact;
import uz.alex.its.beverlee.view.AnimateBtnAsyncTask;
import uz.alex.its.beverlee.view.adapters.CardListHorizontalAdapter;
import uz.alex.its.beverlee.view.adapters.ContactListHorizontalAdapter;
import uz.alex.its.beverlee.view.views.CustomProgressBar;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class WithdrawalFragment extends Fragment {
    private static final String TAG = WithdrawalFragment.class.toString();
    private Activity activity;
    private Context context;

    private View activityRootView;
    private RelativeLayout creditCard;
    private ImageView backArrowImageView;
    private ImageView plusImageView;
    private EditText sumEditText;
    private TextView accessibleSumTextView;
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

        cardList.add(new Card(true, "6346 5785 1234 9567", "01/20","Моника Белучи"));
        cardList.add(new Card(true, "6346 5785 1234 9567", "01/20","Галь Гадот"));
        cardList.add(new Card(true, "6346 5785 1234 9567", "01/20","Ирина Шейк"));
        cardList.add(new Card(true, "6346 5785 1234 9567", "01/20","Анна Серябкина"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_withdrawal, container, false);

        if (getActivity() != null) {
            activityRootView = getActivity().findViewById(R.id.activity_view_root);
        }
        creditCard = root.findViewById(R.id.credit_card);
        backArrowImageView = root.findViewById(R.id.back_arrow_image_view);
        plusImageView = root.findViewById(R.id.plus_image_view);
        sumEditText = root.findViewById(R.id.sum_edit_text);
        accessibleSumTextView = root.findViewById(R.id.accessible_number_text_view);
        withdrawBtn = root.findViewById(R.id.withdraw_btn);
        nestedScrollView = root.findViewById(R.id.card_layout);

        final RecyclerView cardRecyclerView = root.findViewById(R.id.card_list_recycler_view);
        final CardListHorizontalAdapter adapter = new CardListHorizontalAdapter(context);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(context);

        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        cardRecyclerView.setHasFixedSize(false);
        cardRecyclerView.setLayoutManager(layoutManager);
        cardRecyclerView.setAdapter(adapter);

        adapter.setCardList(cardList);

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
            if (getActivity() != null) {
                final Fragment addCardFragment = new AddCardFragment();
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).add(R.id.operations_fragment_container, addCardFragment).commit();
            }
        });

        sumEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                nestedScrollView.postDelayed(() -> {
                    nestedScrollView.smoothScrollTo(0, withdrawBtn.getId());
                }, 100);

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

        withdrawBtn.setOnClickListener(v -> {
            new AnimateBtnAsyncTask(getActivity(), withdrawBtn).execute();
        });
    }
}