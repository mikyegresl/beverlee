package uz.alex.its.beverlee.view.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;

import uz.alex.its.beverlee.R;

public class MonitoringActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    int getLayoutId() {
        return R.layout.activity_monitoring;
    }

    @Override
    int getBottomNavigationMenuItemId() {
        return R.id.navigation_monitoring;
    }
}