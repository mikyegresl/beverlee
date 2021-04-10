package uz.alex.its.beverlee.view.activities;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import uz.alex.its.beverlee.R;
import uz.alex.its.beverlee.push.NotifyManager;
import uz.alex.its.beverlee.push.TokenReceiver;
import uz.alex.its.beverlee.storage.SharedPrefs;
import uz.alex.its.beverlee.utils.Constants;

public class SignActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        final TokenReceiver tokenReceiver = new TokenReceiver(this);
        tokenReceiver.obtainFcmToken();

        final NotifyManager notifyManager = new NotifyManager(this);
        notifyManager.createNotificationChannel(Constants.DEFAULT_CHANNEL_ID, Constants.DEFAULT_CHANNEL_NAME);
        notifyManager.createNotificationChannel(Constants.NEWS_CHANNEL_ID, Constants.NEWS_CHANNEL_NAME);
        notifyManager.createNotificationChannel(Constants.BONUS_CHANNEL_ID, Constants.BONUS_CHANNEL_NAME);
        notifyManager.createNotificationChannel(Constants.INCOME_CHANNEL_ID, Constants.INCOME_CHANNEL_NAME);
        notifyManager.createNotificationChannel(Constants.PURCHASE_CHANNEL_ID, Constants.PURCHASE_CHANNEL_NAME);
        notifyManager.createNotificationChannel(Constants.REPLENISH_CHANNEL_ID, Constants.REPLENISH_CHANNEL_NAME);
        notifyManager.createNotificationChannel(Constants.WITHDRAWAL_CHANNEL_ID, Constants.WITHDRAWAL_CHANNEL_NAME);

        Log.i(TAG, "onCreate(): bearerToken=" + SharedPrefs.getInstance(this).getString(Constants.BEARER_TOKEN));
        Log.i(TAG, "onCreate(): phone=" + SharedPrefs.getInstance(this).getString(Constants.PHONE));
        Log.i(TAG, "onCreate(): phoneVerified=" + SharedPrefs.getInstance(this).getBoolean(Constants.PHONE_VERIFIED));

        if (!TextUtils.isEmpty(SharedPrefs.getInstance(this).getString(Constants.BEARER_TOKEN))
                && !TextUtils.isEmpty(SharedPrefs.getInstance(this).getString(Constants.PHONE))
                && SharedPrefs.getInstance(this).getBoolean(Constants.PHONE_VERIFIED)) {
            startActivity(new Intent(this, MainActivity.class));
            overridePendingTransition(0, 0);
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public static float dpToPx(Context context, float valueInDp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, metrics);
    }

    private static final String TAG = SignActivity.class.toString();
}
