package com.samsung.android.core.pm.mm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import com.android.internal.R;

/* loaded from: classes5.dex */
public class MaintenanceModeNotificationService extends Service {
    private static final String CHANNEL_ID = "maintenance_mode_channel";
    private static final String CHANNEL_NAME = "maintenance_mode";
    private static final int NOTIFICATION_ID = 150707;
    private static final String TAG = "MaintenanceMode";
    private final ComponentName OUTRO_COMPONENT = new ComponentName("android", "com.samsung.android.core.pm.mm.MaintenanceModeOutroActivity");

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override // android.app.Service
    public void onCreate() {
        String string;
        super.onCreate();
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, 3);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
        Intent intent = new Intent();
        intent.setComponent(this.OUTRO_COMPONENT);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 67108864);
        boolean isTablet = MaintenanceModeUtils.isTablet();
        Notification.Builder smallIcon = new Notification.Builder(getApplicationContext(), CHANNEL_ID).setSmallIcon(R.drawable.ic_maintenance_mode_notification);
        if (isTablet) {
            string = getResources().getString(R.string.maintenance_mode_notification_title_tablet);
        } else {
            string = getResources().getString(R.string.maintenance_mode_notification_title_phone);
        }
        Notification.Builder builder = smallIcon.setContentTitle(string).setContentText(getResources().getString(R.string.maintenance_mode_notification_message)).setOngoing(true).setContentIntent(pendingIntent);
        startForeground(NOTIFICATION_ID, builder.build());
    }
}
