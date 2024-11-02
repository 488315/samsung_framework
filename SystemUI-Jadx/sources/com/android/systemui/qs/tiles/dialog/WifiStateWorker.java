package com.android.systemui.qs.tiles.dialog;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.ExecutorImpl;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class WifiStateWorker extends BroadcastReceiver {
    public final DelayableExecutor mBackgroundExecutor;
    public final WifiManager mWifiManager;
    public int mWifiState = 1;

    public WifiStateWorker(BroadcastDispatcher broadcastDispatcher, DelayableExecutor delayableExecutor, WifiManager wifiManager) {
        this.mWifiManager = wifiManager;
        this.mBackgroundExecutor = delayableExecutor;
        broadcastDispatcher.registerReceiver(new IntentFilter("android.net.wifi.WIFI_STATE_CHANGED"), this);
        ((ExecutorImpl) delayableExecutor).execute(new Runnable() { // from class: com.android.systemui.qs.tiles.dialog.WifiStateWorker$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                WifiStateWorker wifiStateWorker = WifiStateWorker.this;
                WifiManager wifiManager2 = wifiStateWorker.mWifiManager;
                if (wifiManager2 != null) {
                    wifiStateWorker.mWifiState = wifiManager2.getWifiState();
                    TooltipPopup$$ExternalSyntheticOutline0.m(new StringBuilder("WifiManager.getWifiState():"), wifiStateWorker.mWifiState, "WifiStateWorker");
                }
            }
        });
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        int intExtra;
        if (intent == null || !"android.net.wifi.WIFI_STATE_CHANGED".equals(intent.getAction()) || (intExtra = intent.getIntExtra("wifi_state", 1)) == 4) {
            return;
        }
        this.mWifiState = intExtra;
    }
}
