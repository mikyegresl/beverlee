package uz.alex.its.beverlee.view.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import uz.alex.its.beverlee.R;
import uz.alex.its.beverlee.model.actor.Contact;
import uz.alex.its.beverlee.utils.Constants;
import uz.alex.its.beverlee.utils.PermissionManager;
import uz.alex.its.beverlee.view.activities.OperationsContainerActivity;
import uz.alex.its.beverlee.view.adapters.ContactListHorizontalAdapter;
import uz.alex.its.beverlee.view.adapters.ContactListVerticalAdapter;
import uz.alex.its.beverlee.view.interfaces.ContactCallback;
import uz.alex.its.beverlee.viewmodel.ContactsViewModel;
import uz.alex.its.beverlee.viewmodel_factory.ContactsViewModelFactory;

public class ContactsFragment extends Fragment implements ContactCallback {
    private Context context;

    private ImageView plusImageView;
    private RadioGroup radioGroup;
    private RadioButton allRadioBtn;
    private RadioButton favoritesRadioBtn;
    private EditText searchFieldEditText;
    private FloatingActionButton fab;

    /* bottomSheet for contactList */
    private BottomNavigationView bottomNavigationView;
    private LinearLayout bottomSheetContacts;
    private TextView bottomSheetContactsTransfer;
    private TextView bottomSheetAddToFavs;
    private TextView bottomSheetDelete;
    private BottomSheetBehavior contactsSheetBehavior;

    /* bottomSheet for favContactList */
    private LinearLayout bottomSheetFavContacts;
    private TextView bottomSheetFavContactsTransfer;
    private TextView bottomSheetRemoveFromFav;
    private BottomSheetBehavior favsSheetBehavior;

    /* select/deselect contactItem */
    private ContactListVerticalAdapter.ContactVerticalViewHolder selectedHolder = null;
    private boolean contactSelected = false;

    /* contact list */
    private RecyclerView contactListRecyclerView;
    private ContactListVerticalAdapter adapter;

    /* contacts permission */
    private static final String[] permissionArray = { Manifest.permission.READ_CONTACTS };

    public ContactsFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_contacts, container, false);

        plusImageView = root.findViewById(R.id.plus_image_view);
        radioGroup = root.findViewById(R.id.radio_group);
        allRadioBtn = root.findViewById(R.id.all_radio_btn);
        favoritesRadioBtn = root.findViewById(R.id.favorites_radio_btn);
        searchFieldEditText = root.findViewById(R.id.contacts_search_edit_text);
        contactListRecyclerView = root.findViewById(R.id.contact_list_recycler_view);

        adapter = new ContactListVerticalAdapter(context, this);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(RecyclerView.VERTICAL);

        contactListRecyclerView.setLayoutManager(layoutManager);
        contactListRecyclerView.setAdapter(adapter);

        if (getActivity() != null) {
            fab = getActivity().findViewById(R.id.floating_btn);
            bottomNavigationView = getActivity().findViewById(R.id.bottom_nav);

            //contacts bottom sheet
            bottomSheetContacts = getActivity().findViewById(R.id.bottom_sheet_contacts);
            bottomSheetContactsTransfer = getActivity().findViewById(R.id.contacts_transfer);
            bottomSheetAddToFavs = getActivity().findViewById(R.id.add_to_favorites);
            bottomSheetDelete = getActivity().findViewById(R.id.delete_contact);

            //fav contacts bottom sheet
            bottomSheetFavContacts = getActivity().findViewById(R.id.bottom_sheet_fav_contacts);
            bottomSheetFavContactsTransfer = getActivity().findViewById(R.id.fav_contacts_transfer);
            bottomSheetRemoveFromFav = getActivity().findViewById(R.id.remove_from_favorites);

            contactsSheetBehavior = BottomSheetBehavior.from(bottomSheetContacts);
            favsSheetBehavior = BottomSheetBehavior.from(bottomSheetFavContacts);
            contactsSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            favsSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        }

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fab.setOnClickListener(v -> {
            final Intent addCardIntent = new Intent(getActivity(), OperationsContainerActivity.class);
            addCardIntent.putExtra(OperationsContainerActivity.FRAGMENT_FLAG, OperationsContainerActivity.TRANSFER_FRAGMENT);
            startActivity(addCardIntent);
        });

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == allRadioBtn.getId()) {
                radioGroup.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.radio_group_income, null));
                allRadioBtn.setTextColor(getResources().getColor(R.color.colorWhite, null));
                favoritesRadioBtn.setTextColor(getResources().getColor(R.color.colorDarkGrey, null));
//                adapter.setContactList(contactList);
                revertItems();
                return;
            }
            if (checkedId == favoritesRadioBtn.getId()) {
                radioGroup.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.radio_group_exp, null));
                allRadioBtn.setTextColor(getResources().getColor(R.color.colorDarkGrey, null));
                favoritesRadioBtn.setTextColor(getResources().getColor(R.color.colorWhite, null));
//                adapter.setContactList(favContactList);
                revertItems();
            }
        });

        searchFieldEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                searchFieldEditText.setHint("");
            }
            else {
                searchFieldEditText.setHint(R.string.search);
            }
        });

        BottomSheetBehavior.BottomSheetCallback callback = new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_EXPANDED: {
                        bottomNavigationView.setVisibility(View.INVISIBLE);
                        fab.setVisibility(View.INVISIBLE);
                        Log.i(TAG, "onStateChanged() expanded: ");
                        break;
                    }
                    case BottomSheetBehavior.STATE_SETTLING: {
                        if (!contactSelected) {
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
        favsSheetBehavior.addBottomSheetCallback(callback);

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

        bottomSheetFavContactsTransfer.setOnClickListener(v -> {
            final Intent transferIntent = new Intent(getActivity(), OperationsContainerActivity.class);
            transferIntent.putExtra(OperationsContainerActivity.FRAGMENT_FLAG, OperationsContainerActivity.TRANSFER_FRAGMENT);
            startActivity(transferIntent);
        });

        bottomSheetRemoveFromFav.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Удалено из избранных", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final ContactsViewModelFactory factory = new ContactsViewModelFactory(context);
        final ContactsViewModel contactsViewModel = new ViewModelProvider(getViewModelStore(), factory).get(ContactsViewModel.class);

        if (PermissionManager.getInstance().permissionsGranted(requireContext(), permissionArray, Constants.REQUEST_CODE_READ_CONTACTS)) {
            contactsViewModel.loadContactList();
        }

        contactsViewModel.getContactList().observe(getViewLifecycleOwner(), contactList -> {
            Log.i(TAG, "contactList: " + contactList);
            adapter.setContactList(contactList);
        });
    }

    @Override
    public void onVerticalContactSelected(final Contact contact, ContactListVerticalAdapter.ContactVerticalViewHolder holder) {
        if (!contactSelected) {
            contactSelected = true;
            selectedHolder = holder;

            final RelativeLayout.LayoutParams avatarParams = (RelativeLayout.LayoutParams) selectedHolder.avatarImageView.getLayoutParams();
            final RelativeLayout.LayoutParams nameParams = (RelativeLayout.LayoutParams) selectedHolder.contactNameTextView.getLayoutParams();
            final RelativeLayout.LayoutParams checkParams = (RelativeLayout.LayoutParams) selectedHolder.checkImageView.getLayoutParams();

            avatarParams.leftMargin = avatarParams.leftMargin + 32;
            nameParams.leftMargin = nameParams.leftMargin + 32;
            checkParams.leftMargin = checkParams.leftMargin + 32;
            selectedHolder.avatarImageView.setLayoutParams(avatarParams);
            selectedHolder.contactNameTextView.setLayoutParams(nameParams);
            selectedHolder.checkImageView.setLayoutParams(checkParams);
            selectedHolder.checkImageView.setVisibility(View.VISIBLE);

            if (radioGroup.getCheckedRadioButtonId() == allRadioBtn.getId()) {
                contactsSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                return;
            }
            if (radioGroup.getCheckedRadioButtonId() == favoritesRadioBtn.getId()) {
                favsSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
            return;
        }
        final ImageView selectedAvatarImageView = selectedHolder.avatarImageView;
        final TextView selectedNameTextView = selectedHolder.contactNameTextView;
        final ImageView selectedCheckImageView = selectedHolder.checkImageView;

        final RelativeLayout.LayoutParams avatarParams = (RelativeLayout.LayoutParams) selectedAvatarImageView.getLayoutParams();
        final RelativeLayout.LayoutParams nameParams = (RelativeLayout.LayoutParams) selectedNameTextView.getLayoutParams();
        final RelativeLayout.LayoutParams checkParams = (RelativeLayout.LayoutParams) selectedCheckImageView.getLayoutParams();

        avatarParams.leftMargin = avatarParams.leftMargin - 32;
        nameParams.leftMargin = nameParams.leftMargin - 32;
        checkParams.leftMargin = checkParams.leftMargin - 32;
        selectedAvatarImageView.setLayoutParams(avatarParams);
        selectedNameTextView.setLayoutParams(nameParams);
        selectedCheckImageView.setLayoutParams(checkParams);
        selectedCheckImageView.setVisibility(View.INVISIBLE);

        contactSelected = false;
        selectedHolder = null;

        if (radioGroup.getCheckedRadioButtonId() == allRadioBtn.getId()) {
            contactsSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            return;
        }
        if (radioGroup.getCheckedRadioButtonId() == favoritesRadioBtn.getId()) {
            favsSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        }
    }

    @Override
    public void onHorizontalContactSelected(Contact contact, ContactListHorizontalAdapter.ContactHorizontalViewHolder holder) {
        //do nothing
    }

    private void revertItems() {
        contactSelected = false;
        if (selectedHolder != null) {
            final ImageView selectedAvatarImageView = selectedHolder.avatarImageView;
            final TextView selectedNameTextView = selectedHolder.contactNameTextView;
            final ImageView selectedCheckImageView = selectedHolder.checkImageView;

            final RelativeLayout.LayoutParams avatarParams = (RelativeLayout.LayoutParams) selectedAvatarImageView.getLayoutParams();
            final RelativeLayout.LayoutParams nameParams = (RelativeLayout.LayoutParams) selectedNameTextView.getLayoutParams();
            final RelativeLayout.LayoutParams checkParams = (RelativeLayout.LayoutParams) selectedCheckImageView.getLayoutParams();

            avatarParams.leftMargin = avatarParams.leftMargin - 32;
            nameParams.leftMargin = nameParams.leftMargin - 32;
            checkParams.leftMargin = checkParams.leftMargin - 32;
            selectedAvatarImageView.setLayoutParams(avatarParams);
            selectedNameTextView.setLayoutParams(nameParams);
            selectedCheckImageView.setLayoutParams(checkParams);
            selectedCheckImageView.setVisibility(View.INVISIBLE);
            selectedHolder = null;
        }
        fab.setVisibility(View.VISIBLE);
        bottomNavigationView.setVisibility(View.VISIBLE);

        if (radioGroup.getCheckedRadioButtonId() == allRadioBtn.getId()) {
            Log.i(TAG, "revertItems: all" );
            contactsSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            return;
        }
        if (radioGroup.getCheckedRadioButtonId() == favoritesRadioBtn.getId()) {
            Log.i(TAG, "revertItems: favs");
            favsSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        }
    }

    private static final String TAG = ContactsFragment.class.toString();
}
