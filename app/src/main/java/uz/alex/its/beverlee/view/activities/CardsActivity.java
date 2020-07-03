package uz.alex.its.beverlee.view.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;

import uz.alex.its.beverlee.R;

public class CardsActivity extends BaseActivity {
    private static final String TAG = CardsActivity.class.toString();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    int getLayoutId() {
        return R.layout.activity_cards;
    }

    @Override
    int getBottomNavigationMenuItemId() {
        return R.id.navigation_cards;
    }
}