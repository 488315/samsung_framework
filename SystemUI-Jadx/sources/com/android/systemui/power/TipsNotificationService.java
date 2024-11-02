package com.android.systemui.power;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.android.systemui.R;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public class TipsNotificationService extends Service {
    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        Log.i("PowerUI.TipsNotificationService", "onBind");
        return null;
    }

    @Override // android.app.Service
    public final void onCreate() {
        Log.i("PowerUI.TipsNotificationService", "onCreate");
        super.onCreate();
    }

    @Override // android.app.Service
    public final void onDestroy() {
        Log.i("PowerUI.TipsNotificationService", "onDestroy");
        stopForeground(true);
        super.onDestroy();
    }

    @Override // android.app.Service
    public final int onStartCommand(Intent intent, int i, int i2) {
        Log.i("PowerUI.TipsNotificationService", "onStartCommand");
        Notification.Builder builder = new Notification.Builder(this, "CHR");
        builder.setContentTitle(getApplicationContext().getString(R.string.app_label)).setOngoing(true);
        startForeground(140080, builder.build());
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.android.systemui.power_tips_notification", 0);
        if (sharedPreferences != null) {
            int i3 = sharedPreferences.getInt("tipsNotiRegisteredCount", 0);
            ListPopupWindow$$ExternalSyntheticOutline0.m("saveCount => current noti count = ", i3, "PowerUI.TipsNotificationService");
            if (i3 < 3) {
                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.putBoolean("tipsNotiConfirmed", false);
                edit.putInt("tipsNotiRegisteredCount", i3 + 1);
                edit.commit();
            }
        }
        stopSelf();
        return 2;
    }
}
