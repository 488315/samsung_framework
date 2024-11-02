package com.android.systemui.slimindicator;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.Dependency;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.keyguard.CustomizationProvider$$ExternalSyntheticOutline0;
import com.samsung.systemui.splugins.slimindicator.SPluginSlimIndicatorModel;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class SlimIndicatorIconBlacklistReceiver extends SlimIndicatorReceiver {
    public final String TAG;

    public SlimIndicatorIconBlacklistReceiver(SlimIndicatorSettingsBackUpManager slimIndicatorSettingsBackUpManager) {
        super(slimIndicatorSettingsBackUpManager);
        this.TAG = "[QuickStar]SlimIndicatorIconBlacklistReceiver";
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        String str;
        if (intent == null) {
            return;
        }
        String action = intent.getAction();
        if (intent.getData() != null) {
            str = intent.getData().getSchemeSpecificPart();
        } else {
            str = null;
        }
        if ("com.samsung.systemui.action.SPLUGIN_SLIMINDICATOR".equals(action)) {
            CustomizationProvider$$ExternalSyntheticOutline0.m("onReceive - action:", action, ",  pkgName: ", str, this.TAG);
            String stringExtra = intent.getStringExtra(SPluginSlimIndicatorModel.INTENT_EXTRA_ICON_BLACKLIST);
            if (!TextUtils.isEmpty(stringExtra)) {
                Settings.Secure.putStringForUser(this.mSettingsBackUpManager.mContext.getContentResolver(), "icon_blacklist", stringExtra, KeyguardUpdateMonitor.getCurrentUser());
            } else {
                Settings.Secure.putStringForUser(this.mSettingsBackUpManager.mContext.getContentResolver(), "icon_blacklist", SPluginSlimIndicatorModel.DB_KEY_DEFAULT_NULL, KeyguardUpdateMonitor.getCurrentUser());
            }
        }
    }

    @Override // com.android.systemui.slimindicator.SlimIndicatorReceiver
    public final void register() {
        ((BroadcastDispatcher) Dependency.get(BroadcastDispatcher.class)).registerReceiver(this, this.mFilter, null, UserHandle.ALL, 2, "android.permission.WRITE_SECURE_SETTINGS");
    }

    @Override // com.android.systemui.slimindicator.SlimIndicatorReceiver
    public final void setFilter() {
        IntentFilter intentFilter = new IntentFilter();
        this.mFilter = intentFilter;
        intentFilter.addAction("com.samsung.systemui.action.SPLUGIN_SLIMINDICATOR");
    }

    @Override // com.android.systemui.slimindicator.SlimIndicatorReceiver
    public final void unregister() {
        ((BroadcastDispatcher) Dependency.get(BroadcastDispatcher.class)).unregisterReceiver(this);
    }
}
