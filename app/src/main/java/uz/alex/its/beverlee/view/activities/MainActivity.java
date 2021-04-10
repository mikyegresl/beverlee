package uz.alex.its.beverlee.view.activities;

import android.Manifest;
import android.os.Bundle;

import androidx.annotation.Nullable;

import uz.alex.its.beverlee.R;
import uz.alex.its.beverlee.utils.Constants;
import uz.alex.its.beverlee.utils.PermissionManager;

public class MainActivity extends BaseActivity {
    private static final String[] permissionArray = { Manifest.permission.READ_CONTACTS };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!PermissionManager.getInstance().permissionsGranted(this, permissionArray, Constants.REQUEST_CODE_READ_CONTACTS)) {
            PermissionManager.getInstance().requestPermissions(this, permissionArray, Constants.REQUEST_CODE_READ_CONTACTS);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    int getBottomNavigationMenuItemId() {
        return R.id.navigation_home;
    }

    private static final String TAG = MainActivity.class.toString();
}