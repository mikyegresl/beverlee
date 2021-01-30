package uz.alex.its.beverlee.view.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import uz.alex.its.beverlee.R;
import uz.alex.its.beverlee.viewmodel.ContactsViewModel;

public class ContactsActivity extends BaseActivity {
    private static final String TAG = ContactsActivity.class.toString();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ContactsViewModel viewModel = new ViewModelProvider(this).get(ContactsViewModel.class);
        viewModel.getContactList().observe(this, contactList -> {
            //updateUI
        });
    }

    @Override
    int getLayoutId() {
        return R.layout.activity_contacts;
    }

    @Override
    int getBottomNavigationMenuItemId() {
        return R.id.navigation_contacts;
    }
}