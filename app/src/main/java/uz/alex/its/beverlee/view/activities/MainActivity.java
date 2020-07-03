package uz.alex.its.beverlee.view.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;

import uz.alex.its.beverlee.R;

public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.toString();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    int getBottomNavigationMenuItemId() {
        return R.id.navigation_home;
    }
}