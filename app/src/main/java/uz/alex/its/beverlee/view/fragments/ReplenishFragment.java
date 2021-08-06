package uz.alex.its.beverlee.view.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;
import androidx.work.WorkInfo;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import uz.alex.its.beverlee.R;
import uz.alex.its.beverlee.utils.AppExecutors;
import uz.alex.its.beverlee.utils.Constants;
import uz.alex.its.beverlee.utils.NetworkConnectivity;
import uz.alex.its.beverlee.view.UiUtils;
import uz.alex.its.beverlee.view.dialog.WebDialogFragment;
import uz.alex.its.beverlee.viewmodel.TransactionViewModel;
import uz.alex.its.beverlee.viewmodel.factory.TransactionViewModelFactory;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class ReplenishFragment extends Fragment {
    private ImageView backArrowImageView;
    private EditText amountEditText;
    private TextView commissionTextView;
    private ProgressBar progressBar;
    private CircularProgressButton replenishBtn;

    private NetworkConnectivity networkConnectivity;
    private TransactionViewModel transactionViewModel;

    public ReplenishFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        networkConnectivity = new NetworkConnectivity(requireContext(), AppExecutors.getInstance());

        final TransactionViewModelFactory transactionFactory = new TransactionViewModelFactory(requireContext());
        transactionViewModel = new ViewModelProvider(getViewModelStore(), transactionFactory).get(TransactionViewModel.class);

        requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                NavHostFragment.findNavController(ReplenishFragment.this).popBackStack();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_replenish, container, false);

        backArrowImageView = root.findViewById(R.id.back_arrow_image_view);
        amountEditText = root.findViewById(R.id.amount_edit_text);
        commissionTextView = root.findViewById(R.id.amount_with_commission_text_view);
        replenishBtn = root.findViewById(R.id.replenish_btn);
        progressBar = root.findViewById(R.id.progress_bar);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        UiUtils.hideBottomNav(requireActivity());

        commissionTextView.setText(getString(R.string.amount_with_commission, 0.0));

        backArrowImageView.setOnClickListener(v -> {
            NavHostFragment.findNavController(this).popBackStack();
        });

        amountEditText.setOnFocusChangeListener((v, hasFocus) -> {
            UiUtils.setFocusChange(amountEditText, hasFocus, R.string.zero);
        });

        amountEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() <= 0) {
                    commissionTextView.setText(getString(R.string.amount_with_commission, 0.00));
                    return;
                }
                if (TextUtils.isEmpty(s)) {
                    return;
                }
                if (!TextUtils.isDigitsOnly(s)) {
                    return;
                }
                final double numericAmount = Double.parseDouble(s.toString());
                final double amountWithCommission = numericAmount + (double) numericAmount*3/100;

                commissionTextView.setText(getString(R.string.amount_with_commission, amountWithCommission));
            }
        });

        replenishBtn.setOnClickListener(v -> {
            networkConnectivity.checkInternetConnection(isConnected -> {
                if (!isConnected) {
                    NavHostFragment.findNavController(this).navigate(
                            ReplenishFragmentDirections.actionDebitFragmentToTransactionResultFragment()
                                    .setResult(false)
                                    .setType(Constants.RESULT_TYPE_TRANSFER)
                                    .setErrorMessage(Constants.NO_INTERNET),
                            new NavOptions.Builder().setPopUpTo(R.id.transferFragment, false).build());
                    return;
                }
                final String amount = amountEditText.getText().toString().trim();

                if (TextUtils.isEmpty(amount) || !TextUtils.isDigitsOnly(amount)) {
                    Toast.makeText(requireContext(), "Ошибка ввода", Toast.LENGTH_SHORT).show();
                    return;
                }
                transactionViewModel.replenish(amount);
            });
        });
    }

    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        transactionViewModel.getReplenishResult(requireContext()).observe(getViewLifecycleOwner(), workInfo -> {
            if (workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                //open web url in webView

                final WebDialogFragment webDialog = WebDialogFragment.newInstance(workInfo.getOutputData().getString(Constants.REPLENISH_URL));
                webDialog.show(getParentFragmentManager().beginTransaction(), TAG);

                progressBar.setVisibility(View.GONE);
                amountEditText.setEnabled(true);
                return;
            }
            if (workInfo.getState() == WorkInfo.State.FAILED || workInfo.getState() == WorkInfo.State.CANCELLED) {
                Toast.makeText(requireContext(), workInfo.getOutputData().getString(Constants.REQUEST_ERROR), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                amountEditText.setEnabled(true);
                return;
            }
            progressBar.setVisibility(View.VISIBLE);
            amountEditText.setEnabled(false);
        });
    }

    private static final String TAG = ReplenishFragment.class.toString();
}
