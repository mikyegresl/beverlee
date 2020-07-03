package uz.alex.its.beverlee.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import uz.alex.its.beverlee.R;

public abstract class BaseActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemReselectedListener {
    private static final String TAG = BaseActivity.class.toString();
    protected BottomNavigationView bottomNavigationView;
    private static int currentItem = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        bottomNavigationView = findViewById(R.id.bottom_nav);
        Log.i(TAG, "bottomNav: " + bottomNavigationView);

        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        currentItem = getBottomNavigationMenuItemId();

        Log.i(TAG, "currentItem=" + currentItem);
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateNavigationBarState();
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        bottomNavigationView.postDelayed(() -> {
            final int itemId = item.getItemId();

            Log.i(TAG, "selectedItem=" + itemId);

            if (currentItem == itemId) {
                return;
            }

            switch (itemId) {
                case R.id.navigation_home: {
                    startActivity(new Intent(this, MainActivity.class));
                    break;
                }
                case R.id.navigation_monitoring: {
                    startActivity(new Intent(this, MonitoringActivity.class));
                    break;
                }
                case R.id.navigation_contacts: {
                    startActivity(new Intent(this, ContactsActivity.class));
                    break;
                }
                case R.id.navigation_cards: {
                    startActivity(new Intent(this, CardsActivity.class));
                    break;
                }
            }

        }, 0);
        return true;
    }

    @Override
    public void onNavigationItemReselected(@NonNull MenuItem item) {
//        if (currentItem == item.getItemId()) {
//            Log.i(TAG, "onNavigationItemReselected: currentItem=" + currentItem + " item=" + item.getItemId());
//            return;
//        }
    }

    private void updateNavigationBarState() {
        int actionId = getBottomNavigationMenuItemId();
        selectBottomNavigationItem(actionId);
    }

    void selectBottomNavigationItem(int itemId) {
        Log.i(TAG, "bottomNav: " + bottomNavigationView);
        MenuItem item = bottomNavigationView.getMenu().findItem(itemId);
        item.setChecked(true);
    }

    abstract int getLayoutId();
    abstract int getBottomNavigationMenuItemId();
}
