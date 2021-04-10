package uz.alex.its.beverlee.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;

import uz.alex.its.beverlee.R;
import uz.alex.its.beverlee.view.fragments.AddCardFragment;
import uz.alex.its.beverlee.view.fragments.CalendarFragment;
import uz.alex.its.beverlee.view.fragments.ReplenishFragment;
import uz.alex.its.beverlee.view.fragments.EditCardFragment;
import uz.alex.its.beverlee.view.fragments.NotificationSettingsFragment;
import uz.alex.its.beverlee.view.fragments.NotificationsFragment;
import uz.alex.its.beverlee.view.fragments.TransferFragment;
import uz.alex.its.beverlee.view.fragments.WithdrawalFragment;

public class OperationsContainerActivity extends AppCompatActivity {
    public static final String FRAGMENT_FLAG = "FRAGMENT_FLAG";
    public static final int NOTIFICATIONS_FRAGMENT = 0x00;
    public static final int NOTIFICATIONS_SETTINGS_FRAGMENT = 0x01;
    public static final int DEBIT_FRAGMENT = 0x02;
    public static final int WITHDRAWAL_FRAGMENT = 0x03;
    public static final int TRANSFER_FRAGMENT = 0x04;
    public static final int EDIT_CARD_FRAGMENT = 0x05;
    public static final int CALENDAR_FRAGMENT = 0x06;
    public static final int ADD_CARD_FRAGMENT = 0x07;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operations_container);

        final FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment nextFragment = null;
        final Intent receivedIntent = getIntent();

        if (receivedIntent != null) {
            switch (receivedIntent.getIntExtra(FRAGMENT_FLAG, -1)) {
                case NOTIFICATIONS_FRAGMENT: {
                    nextFragment = new NotificationsFragment();
                    break;
                }
                case NOTIFICATIONS_SETTINGS_FRAGMENT: {
                    nextFragment = new NotificationSettingsFragment();
                    break;
                }
                case DEBIT_FRAGMENT: {
                    nextFragment = new ReplenishFragment();
                    break;
                }
                case WITHDRAWAL_FRAGMENT: {
                    nextFragment = new WithdrawalFragment();
                    break;
                }
                case TRANSFER_FRAGMENT: {
                    nextFragment = new TransferFragment();
                    break;
                }
                case EDIT_CARD_FRAGMENT: {
                    nextFragment = new EditCardFragment();
                    break;
                }
                case CALENDAR_FRAGMENT: {
                    nextFragment = new CalendarFragment();
                    break;
                }
                case ADD_CARD_FRAGMENT: {
                    nextFragment = new AddCardFragment();
                    break;
                }
                case -1: {
                    break;
                }
            }
            if (nextFragment != null) {
                fragmentManager.beginTransaction().add(R.id.operations_fragment_container, nextFragment).commit();
            }
        }
    }
}