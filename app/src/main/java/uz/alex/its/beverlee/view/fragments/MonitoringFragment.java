package uz.alex.its.beverlee.view.fragments;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import uz.alex.its.beverlee.R;
import uz.alex.its.beverlee.view.activities.OperationsContainerActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class MonitoringFragment extends Fragment {
    private static final String TAG = MonitoringFragment.class.toString();
    private boolean chartHidden = false;
    private FragmentActivity activity;

    private TextView monthIncomeTextView;
    private TextView monthIncomeSummaryTextView;
    private RadioGroup radioGroup;
    private RadioButton incomeRadioBtn;
    private RadioButton expenditureRadioBtn;
    private EditText searchFieldEditText;

    private View monitoringCard;
    private ImageView minimizeChartImageView;
    private Animation shrinkAnim;

    private ImageView chartImageView;
    private ImageView prevChartImageView;
    private ImageView nextChartImageView;
    private TextView receivedTextView;
    private TextView debitedTextView;
    private TextView bonusTextView;
    private View calculationLayout;

    private FloatingActionButton fab;

    public MonitoringFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_monitoring, container, false);

        monthIncomeTextView = root.findViewById(R.id.month_income_text_view);
        monthIncomeSummaryTextView = root.findViewById(R.id.month_income_summary_text_view);
        radioGroup = root.findViewById(R.id.radio_group);
        incomeRadioBtn = root.findViewById(R.id.income_radio_btn);
        expenditureRadioBtn = root.findViewById(R.id.expenditure_radio_btn);
        searchFieldEditText = root.findViewById(R.id.monitoring_search_edit_text);
        minimizeChartImageView = root.findViewById(R.id.minimize_chart_image_view);
        monitoringCard = root.findViewById(R.id.monitoring_card);
        chartImageView = root.findViewById(R.id.chart_image_view);
        prevChartImageView = root.findViewById(R.id.prev_chart_image_view);
        nextChartImageView = root.findViewById(R.id.next_chart_image_view);
        receivedTextView = root.findViewById(R.id.received_text_view);
        debitedTextView = root.findViewById(R.id.debited_text_view);
        bonusTextView = root.findViewById(R.id.bonus_text_view);
        calculationLayout = root.findViewById(R.id.calculations_layout);

        shrinkAnim = AnimationUtils.loadAnimation(getContext(), R.anim.shrink_vertically);

        if (getActivity() != null) {
            fab = getActivity().findViewById(R.id.floating_btn);
        }
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String monthIncomeText = "<font color=#FFFFFF>Доходы за</font> <font color=#FBBC39>Май</font>";
        String monthIncomeSummaryText = "<font color=#FFFFFF>+ 1200,</font><font color=#888785>00</font> <font color=#FFFFFF>$</font>";
        monthIncomeTextView.setText(Html.fromHtml(monthIncomeText));
        monthIncomeSummaryTextView.setText(Html.fromHtml(monthIncomeSummaryText));

        fab.setOnClickListener(v -> {
            final Intent addCardIntent = new Intent(getActivity(), OperationsContainerActivity.class);
            addCardIntent.putExtra(OperationsContainerActivity.FRAGMENT_FLAG, OperationsContainerActivity.TRANSFER_FRAGMENT);
            startActivity(addCardIntent);
        });

        incomeRadioBtn.setOnClickListener(v -> {
            radioGroup.setBackground(getResources().getDrawable(R.drawable.radio_group_income, null));
            incomeRadioBtn.setTextColor(getResources().getColor(R.color.colorWhite, null));
            expenditureRadioBtn.setTextColor(getResources().getColor(R.color.colorDarkGrey, null));
        });

        expenditureRadioBtn.setOnClickListener(v -> {
            radioGroup.setBackground(getResources().getDrawable(R.drawable.radio_group_exp, null));
            incomeRadioBtn.setTextColor(getResources().getColor(R.color.colorDarkGrey, null));
            expenditureRadioBtn.setTextColor(getResources().getColor(R.color.colorWhite, null));
        });

        searchFieldEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                searchFieldEditText.setHint("");
            }
            else {
                searchFieldEditText.setHint(R.string.search);
            }
        });

        ObjectAnimator minimizeAnimator = ObjectAnimator.ofInt(calculationLayout, "top",1600, 870);
        ObjectAnimator maximizeAnimator = ObjectAnimator.ofInt(calculationLayout, "top",870, 1670);

        minimizeAnimator.setDuration(200);
        minimizeAnimator.setInterpolator(new LinearInterpolator());
        maximizeAnimator.setDuration(200);
        maximizeAnimator.setInterpolator(new LinearInterpolator());

        minimizeChartImageView.setOnClickListener(v -> {
            if (chartHidden) {
                Log.i(TAG, "expanded h=" + monitoringCard.getHeight());
                chartHidden = false;
                maximizeAnimator.start();
                chartImageView.setVisibility(View.VISIBLE);
                prevChartImageView.setVisibility(View.VISIBLE);
                nextChartImageView.setVisibility(View.VISIBLE);
                receivedTextView.setVisibility(View.VISIBLE);
                debitedTextView.setVisibility(View.VISIBLE);
                bonusTextView.setVisibility(View.VISIBLE);
                minimizeChartImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_minimize, null));
                return;
            }
            Log.i(TAG, "onclick(): ");
            chartHidden = true;
            Log.i(TAG, "shrank h=" + monitoringCard.getHeight());
            minimizeAnimator.start();
            minimizeChartImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_maximize, null));
        });

        monitoringCard.setOnClickListener(v -> {
            if (!chartHidden) {
                final Intent calendarIntent = new Intent(getContext(), OperationsContainerActivity.class);
                calendarIntent.putExtra(OperationsContainerActivity.FRAGMENT_FLAG, OperationsContainerActivity.CALENDAR_FRAGMENT);
                startActivity(calendarIntent);

            }
        });
    }
}
