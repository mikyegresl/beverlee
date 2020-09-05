package uz.alex.its.beverlee.view.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import uz.alex.its.beverlee.R;
import uz.alex.its.beverlee.model.Contact;
import uz.alex.its.beverlee.view.activities.OperationsContainerActivity;
import uz.alex.its.beverlee.view.activities.ProfileActivity;
import uz.alex.its.beverlee.view.adapters.ContactListHorizontalAdapter;

public class HomeFragment extends Fragment {
    private static final String TAG = HomeFragment.class.toString();
    private Context context;

    private TextView summaryTextView;
    private TextView profitSummaryTextView;
    private EditText searchFieldEditText;
    private ImageView crownImageView;
    private ImageView bellImageView;
    private Button debitBtn;
    private Button withdrawBtn;

    private final List<Contact> contactList = new ArrayList<>();

    private FloatingActionButton fab;

    public HomeFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() != null) {
            context = getActivity().getApplicationContext();
        }
        if (getArguments() != null) {
            Log.i(TAG, "onCreate: " + getArguments().getSerializable("interface"));
        }

        contactList.add(new Contact("Георгий Павлович"));
        contactList.add(new Contact("Юрий Александрович"));
        contactList.add(new Contact("Василий Петрович"));
        contactList.add(new Contact("Андрей Ололоевич"));
        contactList.add(new Contact("Федор Михайлович"));
        contactList.add(new Contact("Владимир Владимирович"));
        contactList.add(new Contact("Александр Сергеевич"));
        contactList.add(new Contact("Алексей"));
        contactList.add(new Contact("Вадим"));
        contactList.add(new Contact("Никита"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_home, container, false);

        summaryTextView = root.findViewById(R.id.summary_text_view);
        profitSummaryTextView = root.findViewById(R.id.profit_summary_text_view);
        searchFieldEditText = root.findViewById(R.id.home_search_edit_text);
        crownImageView = root.findViewById(R.id.crown_image_view);
        bellImageView = root.findViewById(R.id.bell_image_view);
        debitBtn = root.findViewById(R.id.debit_btn);
        withdrawBtn = root.findViewById(R.id.withdraw_btn);

        final RecyclerView contactRecyclerView = root.findViewById(R.id.contact_recycler_view);
        final ContactListHorizontalAdapter adapter = new ContactListHorizontalAdapter(context);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(context);

        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        contactRecyclerView.setHasFixedSize(false);
        contactRecyclerView.setLayoutManager(layoutManager);
        contactRecyclerView.setAdapter(adapter);
        adapter.setContactList(contactList);

        if (getActivity() != null) {
            fab = getActivity().findViewById(R.id.floating_btn);
        }

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String summaryText = "<font color=#FFFFFF>$ 23 650,</font><font color=#888785>92</font>";
        String profitSummaryText = "<font color=#FFFFFF>+ 1200,</font><font color=#888785>00</font> <font color=#FFFFFF>$</font>";
        summaryTextView.setText(Html.fromHtml(summaryText));
        profitSummaryTextView.setText(Html.fromHtml(profitSummaryText));

        fab.setOnClickListener(v -> {
            final Intent addCardIntent = new Intent(getActivity(), OperationsContainerActivity.class);
            addCardIntent.putExtra(OperationsContainerActivity.FRAGMENT_FLAG, OperationsContainerActivity.TRANSFER_FRAGMENT);
            startActivity(addCardIntent);
        });

        searchFieldEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                searchFieldEditText.setHint("");
            }
            else {
                searchFieldEditText.setHint(R.string.search);
            }
        });

        crownImageView.setOnClickListener(v -> {
            final Intent profileIntent = new Intent(getActivity(), ProfileActivity.class);
            startActivity(profileIntent);
        });

        bellImageView.setOnClickListener(v -> {
            final Intent operationsIntent = new Intent(getActivity(), OperationsContainerActivity.class);
            operationsIntent.putExtra(OperationsContainerActivity.FRAGMENT_FLAG, OperationsContainerActivity.NOTIFICATIONS_FRAGMENT);
            startActivity(operationsIntent);
        });

        debitBtn.setOnClickListener(v -> {
            final Intent debitIntent = new Intent(getActivity(), OperationsContainerActivity.class);
            debitIntent.putExtra(OperationsContainerActivity.FRAGMENT_FLAG, OperationsContainerActivity.DEBIT_FRAGMENT);
            startActivity(debitIntent);
        });

        withdrawBtn.setOnClickListener(v -> {
            final Intent withdrawIntent = new Intent(getActivity(), OperationsContainerActivity.class);
            withdrawIntent.putExtra(OperationsContainerActivity.FRAGMENT_FLAG, OperationsContainerActivity.WITHDRAWAL_FRAGMENT);
            startActivity(withdrawIntent);
        });


    }
}
