package uz.alex.its.beverlee.view.activities;

import android.Manifest;
import android.os.Bundle;

import androidx.annotation.Nullable;

import uz.alex.its.beverlee.R;
import uz.alex.its.beverlee.utils.Constants;
import uz.alex.its.beverlee.utils.PermissionManager;

public class ContactsActivity extends BaseActivity {
    private static final String[] permissionArray = { Manifest.permission.READ_CONTACTS };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!PermissionManager.getInstance().permissionsGranted(this, permissionArray, Constants.REQUEST_CODE_READ_CONTACTS)) {
            PermissionManager.getInstance().requestPermissions(this, permissionArray, Constants.REQUEST_CODE_READ_CONTACTS);
        }
    }

    @Override
    int getLayoutId() {
        return R.layout.activity_contacts;
    }

    @Override
    int getBottomNavigationMenuItemId() {
        return R.id.navigation_contacts;
    }

    private static final String TAG = ContactsActivity.class.toString();
}