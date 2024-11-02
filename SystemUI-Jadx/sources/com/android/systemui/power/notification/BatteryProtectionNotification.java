package com.android.systemui.power.notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import android.provider.Settings;
import com.android.systemui.R;
import com.android.systemui.power.PowerUiConstants;
import com.android.systemui.power.PowerUtils;
import com.android.systemui.power.SecBatteryStatsSnapshot;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class BatteryProtectionNotification extends PowerUiNotification {
    public int mBatteryProtectionValue;
    public int mRechargeLevel;

    public BatteryProtectionNotification(Context context) {
        super(context);
    }

    @Override // com.android.systemui.power.notification.PowerUiNotification
    public final void dismissNotification() {
        this.mNotificationManager.cancelAsUser("tag_battery_protection", R.id.notification_power, UserHandle.ALL);
    }

    @Override // com.android.systemui.power.notification.PowerUiNotification
    public final Notification.Builder getBuilder() {
        String string;
        Bundle bundle = new Bundle();
        Context context = this.mContext;
        bundle.putString("android.substName", context.getString(R.string.charging_notice_app_name));
        int i = this.mBatteryProtectionValue;
        if (i != 1 && i != 2) {
            string = context.getString(R.string.battery_protection_noti_title);
        } else {
            string = context.getString(R.string.maximum_protection_noti_title);
        }
        return getCommonBuilder(string, getContentText(), "CHR").setSmallIcon(R.drawable.stat_notify_battery_protection).setGroup("CHARGING").setOnlyAlertOnce(true).setContentIntent(PendingIntent.getActivityAsUser(this.mContext, 0, new Intent("com.samsung.android.sm.ACTION_BATTERY_PROTECTION").setPackage(PowerUiConstants.DC_PACKAGE_NAME).setFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE), 335544320, null, UserHandle.CURRENT)).setOngoing(true).addExtras(bundle).setPriority(-2).setCategory("sys");
    }

    public final String getContentText() {
        int i = this.mBatteryProtectionValue;
        Context context = this.mContext;
        if (i != 1 && i != 2) {
            return context.getString(R.string.battery_protection_not_charging_noti_content, Integer.valueOf(this.mRechargeLevel));
        }
        return context.getString(R.string.maximum_protection_noti_content);
    }

    @Override // com.android.systemui.power.notification.PowerUiNotification
    public final void setInformation(SecBatteryStatsSnapshot secBatteryStatsSnapshot) {
        Context context = this.mContext;
        this.mRechargeLevel = Settings.Global.getInt(context.getContentResolver(), "battery_protection_recharge_level", 95);
        this.mBatteryProtectionValue = PowerUtils.getProtectBatteryValue(context);
    }

    @Override // com.android.systemui.power.notification.PowerUiNotification
    public final void showNotification() {
        String string;
        Notification.Builder builder = getBuilder();
        Notification.BigTextStyle bigTextStyle = new Notification.BigTextStyle();
        int i = this.mBatteryProtectionValue;
        Context context = this.mContext;
        if (i != 1 && i != 2) {
            string = context.getString(R.string.battery_protection_noti_title);
        } else {
            string = context.getString(R.string.maximum_protection_noti_title);
        }
        bigTextStyle.setBigContentTitle(string);
        bigTextStyle.bigText(getContentText());
        builder.setStyle(bigTextStyle);
        this.mNotificationManager.notifyAsUser("tag_battery_protection", R.id.notification_power, builder.build(), UserHandle.ALL);
    }
}
