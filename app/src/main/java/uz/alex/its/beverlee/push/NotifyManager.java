package uz.alex.its.beverlee.push;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.RemoteMessage;

import uz.alex.its.beverlee.R;
import uz.alex.its.beverlee.storage.SharedPrefs;
import uz.alex.its.beverlee.utils.Constants;

public class NotifyManager {
    private final Context context;
    private final NotificationManager manager;

    public NotifyManager(final Context context) {
        this.context = context;
        this.manager = context.getSystemService(NotificationManager.class);
    }

    public void createNotificationChannel(final String channelId, final String channelName) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            final NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.enableLights(true);
            notificationChannel.setShowBadge(true);
            notificationChannel.enableVibration(true);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

            if (manager == null) {
                Log.e(TAG, "notificationManager is NULL");
                return;
            }
            manager.createNotificationChannel(notificationChannel);
        }
    }

    public void showPush(@NonNull final String packageName,
                         @NonNull final String intentClass,
                         @NonNull final String keyMessage,
                         @Nullable final RemoteMessage remoteMessage,
                         final int notificationId,
                         @NonNull final String channelId) {
        if (remoteMessage == null) {
            return;
        }
        final NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, channelId);
        final Intent notifyIntent = new Intent();
        notifyIntent.setComponent(new ComponentName(packageName, packageName + intentClass));
        notifyIntent.putExtra(keyMessage, notificationId);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntentWithParentStack(notifyIntent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.setContentIntent(pendingIntent);

        final String title = remoteMessage.getData().get(Constants.PUSH_TITLE);
        final String body = remoteMessage.getData().get(Constants.PUSH_BODY);

        if (title != null) {
            notificationBuilder.setContentTitle(title);
        }
        notificationBuilder.setContentText(body);
        notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        notificationBuilder.setOnlyAlertOnce(true);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setSmallIcon(R.drawable.ic_push);
        notificationBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        NotificationManagerCompat.from(context).notify(notificationId, notificationBuilder.build());
    }

    public boolean areNewsEnabled() {
        return SharedPrefs.getInstance(context).getBoolean(NOTIFY_NEWS);
    }

    public boolean areBonusesEnabled() {
        return SharedPrefs.getInstance(context).getBoolean(NOTIFY_BONUSES);
    }

    public boolean isIncomeEnabled() {
        return SharedPrefs.getInstance(context).getBoolean(NOTIFY_INCOME);
    }

    public boolean isPurchaseEnabled() {
        return SharedPrefs.getInstance(context).getBoolean(NOTIFY_PURCHASE);
    }

    public boolean isReplenishEnabled() {
        return SharedPrefs.getInstance(context).getBoolean(NOTIFY_REPLENISH);
    }

    public boolean isWithdrawalEnabled() {
        return SharedPrefs.getInstance(context).getBoolean(NOTIFY_WITHDRAWAL);
    }

    public void setNewsEnabled(boolean isChecked) {
        SharedPrefs.getInstance(context).putBoolean(NOTIFY_NEWS, isChecked);
    }

    public void setBonusesEnabled(boolean isChecked) {
        SharedPrefs.getInstance(context).putBoolean(NOTIFY_BONUSES, isChecked);
    }

    public void setIncomeEnabled(boolean isChecked) {
        SharedPrefs.getInstance(context).putBoolean(NOTIFY_INCOME, isChecked);
    }

    public void setPurchaseEnabled(boolean isChecked) {
        SharedPrefs.getInstance(context).putBoolean(NOTIFY_PURCHASE, isChecked);
    }

    public void setReplenishEnabled(boolean isChecked) {
        SharedPrefs.getInstance(context).putBoolean(NOTIFY_REPLENISH, isChecked);
    }

    public void setWithdrawalEnabled(boolean isChecked) {
        SharedPrefs.getInstance(context).putBoolean(NOTIFY_WITHDRAWAL, isChecked);
    }

    /* For Android below O (API 23) */
    public void setNotificationsEnabled(boolean isChecked) {
        SharedPrefs.getInstance(context).putBoolean(NOTIFICATIONS_ENABLED, isChecked);
    }

    public boolean areNotificationsEnabled() {
        return SharedPrefs.getInstance(context).getBoolean(NOTIFICATIONS_ENABLED);
    }

    private static final String NOTIFICATIONS_ENABLED = "notifications_enabled";
    private static final String NOTIFY_NEWS = "notify_news";
    private static final String NOTIFY_BONUSES = "notify_bonuses";
    private static final String NOTIFY_INCOME = "notify_income";
    private static final String NOTIFY_PURCHASE = "notify_purchase";
    private static final String NOTIFY_REPLENISH = "notify_replenish";
    private static final String NOTIFY_WITHDRAWAL = "notify_withdrawal";

    private static final String TAG = NotifyManager.class.toString();
}
