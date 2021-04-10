package uz.alex.its.beverlee.view.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import uz.alex.its.beverlee.R;
import uz.alex.its.beverlee.model.actor.Contact;
import uz.alex.its.beverlee.storage.SharedPrefs;
import uz.alex.its.beverlee.utils.Constants;
import uz.alex.its.beverlee.utils.PermissionManager;
import uz.alex.its.beverlee.view.activities.MonitoringActivity;
import uz.alex.its.beverlee.view.activities.OperationsContainerActivity;
import uz.alex.its.beverlee.view.activities.ProfileActivity;
import uz.alex.its.beverlee.view.adapters.ContactListHorizontalAdapter;
import uz.alex.its.beverlee.view.adapters.ContactListVerticalAdapter;
import uz.alex.its.beverlee.view.interfaces.ContactCallback;
import uz.alex.its.beverlee.viewmodel.ContactsViewModel;
import uz.alex.its.beverlee.viewmodel.TransactionViewModel;
import uz.alex.its.beverlee.viewmodel_factory.ContactsViewModelFactory;
import uz.alex.its.beverlee.viewmodel_factory.TransactionViewModelFactory;

public class HomeFragment extends Fragment implements ContactCallback {
    private Context context;

    private TextView currentBalanceTextView;
    private TextView profitSummaryTextView;
    private EditText searchFieldEditText;
    private ImageView crownImageView;
    private ImageView bellImageView;
    private Button debitBtn;
    private Button withdrawBtn;

    private View cardProfit;

    private FloatingActionButton fab;

    /* bottomSheet for contactList */
    private BottomNavigationView bottomNavigationView;
    private LinearLayout bottomSheetContacts;
    private TextView bottomSheetContactsTransfer;
    private TextView bottomSheetAddToFavs;
    private TextView bottomSheetDelete;
    private BottomSheetBehavior contactsSheetBehavior;

    /* contact list */
    private RecyclerView contactListRecyclerView;
    private ContactListHorizontalAdapter adapter;

    /* selected contacts */
    private ContactListHorizontalAdapter.ContactHorizontalViewHolder selectedHolder = null;
    private boolean contactSelected = false;

    /* contacts permission */
    private static final String[] permissionArray = { Manifest.permission.READ_CONTACTS };

    /* news */
    private CardView newsCardView;

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
        Log.i(TAG, "onCreate(): bearerToken=" + SharedPrefs.getInstance(context).getString(Constants.BEARER_TOKEN));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_home, container, false);

        currentBalanceTextView = root.findViewById(R.id.current_balance_text_view);
        profitSummaryTextView = root.findViewById(R.id.profit_summary_text_view);
        searchFieldEditText = root.findViewById(R.id.home_search_edit_text);
        crownImageView = root.findViewById(R.id.crown_image_view);
        bellImageView = root.findViewById(R.id.bell_image_view);
        debitBtn = root.findViewById(R.id.replenish_btn);
        withdrawBtn = root.findViewById(R.id.withdraw_btn);
        cardProfit = root.findViewById(R.id.card_profit);
        contactListRecyclerView = root.findViewById(R.id.contact_recycler_view);

        /* news */
        newsCardView = root.findViewById(R.id.news_card_view);

        adapter = new ContactListHorizontalAdapter(context, this);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);

        contactListRecyclerView.setLayoutManager(layoutManager);
        contactListRecyclerView.setAdapter(adapter);

        if (getActivity() != null) {
            fab = getActivity().findViewById(R.id.floating_btn);
            /* bottomSheet */
            bottomNavigationView = getActivity().findViewById(R.id.bottom_nav);
            bottomSheetContacts = getActivity().findViewById(R.id.bottom_sheet_contacts);
            bottomSheetContactsTransfer = getActivity().findViewById(R.id.contacts_transfer);
            bottomSheetAddToFavs = getActivity().findViewById(R.id.add_to_favorites);
            bottomSheetDelete = getActivity().findViewById(R.id.delete_contact);
        }
        bottomNavigationView.setVisibility(View.VISIBLE);
        fab.setVisibility(View.VISIBLE);

        contactsSheetBehavior = BottomSheetBehavior.from(bottomSheetContacts);
        contactsSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String profitSummaryText = "<font color=#FFFFFF>+ 1200,</font><font color=#888785>00</font> <font color=#FFFFFF>$</font>";
        profitSummaryTextView.setText(Html.fromHtml(profitSummaryText));

        cardProfit.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), MonitoringActivity.class));
        });

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

        BottomSheetBehavior.BottomSheetCallback callback = new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_EXPANDED: {
                        bottomNavigationView.setVisibility(View.INVISIBLE);
                        fab.setVisibility(View.INVISIBLE);
                        Log.i(TAG, "onStateChanged(): expanded");
                        break;
                    }
                    case BottomSheetBehavior.STATE_SETTLING: {
                        if (!contactSelected) {
                            Log.i(TAG, "onStateChanged(): trigger");
                            bottomNavigationView.setVisibility(View.VISIBLE);
                            fab.setVisibility(View.VISIBLE);
                        }
                        Log.i(TAG, "onStateChanged(): hidden");
                        break;
                    }
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_HALF_EXPANDED:
                        break;
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                return;
            }
        };

        contactsSheetBehavior.addBottomSheetCallback(callback);

        bottomSheetContactsTransfer.setOnClickListener(v -> {
            final Intent transferIntent = new Intent(getActivity(), OperationsContainerActivity.class);
            transferIntent.putExtra(OperationsContainerActivity.FRAGMENT_FLAG, OperationsContainerActivity.TRANSFER_FRAGMENT);
            startActivity(transferIntent);
        });

        bottomSheetAddToFavs.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Добавлено в избранные", Toast.LENGTH_SHORT).show();
        });

        bottomSheetDelete.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Контакт удален", Toast.LENGTH_SHORT).show();
        });

        /* news */
        newsCardView.setOnClickListener(v -> {
            NavHostFragment.findNavController(HomeFragment.this).navigate(R.id.action_homeFragment_to_newsFragment);
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final ContactsViewModelFactory contactsFactory = new ContactsViewModelFactory(context);
        final ContactsViewModel contactsViewModel = new ViewModelProvider(getViewModelStore(), contactsFactory).get(ContactsViewModel.class);

        final TransactionViewModelFactory transactionFactory = new TransactionViewModelFactory(context);
        final TransactionViewModel transactionViewModel = new ViewModelProvider(getViewModelStore(), transactionFactory).get(TransactionViewModel.class);

        if (PermissionManager.getInstance().permissionsGranted(requireContext(), permissionArray, Constants.REQUEST_CODE_READ_CONTACTS)) {
            contactsViewModel.loadContactList();
        }

        contactsViewModel.getContactList().observe(getViewLifecycleOwner(), contactList -> {
            Log.i(TAG, "contactList: " + contactList);
            adapter.setContactList(contactList);
        });

        transactionViewModel.fetchCurrentBalance();

        transactionViewModel.getBalance().observe(getViewLifecycleOwner(), balance -> {
            if (balance != null) {
                currentBalanceTextView.setText(getString(R.string.current_balance, String.valueOf(balance.getBalance())));
            }
        });
    }

    @Override
    public void onHorizontalContactSelected(Contact contact, ContactListHorizontalAdapter.ContactHorizontalViewHolder holder) {
        if (!contactSelected) {
            contactSelected = true;
            selectedHolder = holder;
            selectedHolder.checkImageView.setVisibility(View.VISIBLE);
            contactsSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            return;
        }
        final ImageView selectedCheckImageView = selectedHolder.checkImageView;

        contactSelected = false;
        selectedHolder = null;

        selectedCheckImageView.setVisibility(View.INVISIBLE);
        contactsSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    @Override
    public void onVerticalContactSelected(Contact contact, ContactListVerticalAdapter.ContactVerticalViewHolder holder) {
        //do nothing
    }

    private static final String TAG = HomeFragment.class.toString();
}
