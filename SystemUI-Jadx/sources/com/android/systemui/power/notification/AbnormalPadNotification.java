package com.android.systemui.power.notification;

import android.app.Notification;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.UserHandle;
import android.util.Log;
import com.android.systemui.R;
import com.android.systemui.SystemUIApplication;
import com.android.systemui.power.PowerUtils;
import com.android.systemui.power.SecBatteryStatsSnapshot;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class AbnormalPadNotification extends PowerUiNotification {
    public AbnormalPadNotification(Context context) {
        super(context);
    }

    @Override // com.android.systemui.power.notification.PowerUiNotification
    public final void dismissNotification() {
        this.mNotificationManager.cancelAsUser("abnormal_pad", R.id.notification_power, UserHandle.ALL);
    }

    @Override // com.android.systemui.power.notification.PowerUiNotification
    public final Notification.Builder getBuilder() {
        Context context = this.mContext;
        String string = context.getString(R.string.abnormal_pad_noti_content);
        Notification.Builder addAction = getCommonBuilder(context.getString(R.string.abnormal_pad_noti_title), string, "ALR").setSmallIcon(R.drawable.stat_notify_slowcharging).setShowWhen(false).setOngoing(true).setOnlyAlertOnce(true).setStyle(new Notification.BigTextStyle().bigText(string)).addAction(0, context.getString(R.string.warning_alert_check), PowerUtils.pendingBroadcast(context, "PNW.abnormalPadNoThanks"));
        SystemUIApplication.overrideNotificationAppName(context, addAction, false);
        return addAction;
    }

    @Override // com.android.systemui.power.notification.PowerUiNotification
    public final void showNotification() {
        SharedPreferences sharedPreferences = this.mContext.getSharedPreferences("com.android.systemui.abnormal_pad", 0);
        if (sharedPreferences != null && sharedPreferences.getBoolean("DoNotShowAbnormalPadNoti", false)) {
            Log.w("PowerUi.AbnormalPadNotification", "User have ever checked do_not_show_again in this notification, so we do nothing !!");
        } else {
            this.mNotificationManager.notifyAsUser("abnormal_pad", R.id.notification_power, getBuilder().build(), UserHandle.ALL);
        }
    }

    @Override // com.android.systemui.power.notification.PowerUiNotification
    public final void setInformation(SecBatteryStatsSnapshot secBatteryStatsSnapshot) {
    }
}
