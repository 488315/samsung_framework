package com.android.wifitrackerlib;

import android.content.ContentResolver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkRequest;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.connectivity.AccessPointControllerImpl;
import com.android.wifitrackerlib.BaseWifiTracker;
import com.samsung.android.emergencymode.SemEmergencyManager;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.wifi.SemWifiManager;
import com.samsung.android.wifitrackerlib.SemWifiUtils;
import com.samsung.android.wifitrackerlib.WifiQoSScoredCache;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final /* synthetic */ class BaseWifiTracker$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ BaseWifiTracker$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i;
        boolean z;
        boolean isSupportedQoSProvider;
        boolean isEmergencyMode;
        boolean z2 = false;
        switch (this.$r8$classId) {
            case 0:
                BaseWifiTracker baseWifiTracker = (BaseWifiTracker) this.f$0;
                ConnectivityManager connectivityManager = baseWifiTracker.mConnectivityManager;
                try {
                    baseWifiTracker.mContext.unregisterReceiver(baseWifiTracker.mBroadcastReceiver);
                    connectivityManager.unregisterNetworkCallback(baseWifiTracker.mNetworkCallback);
                    connectivityManager.unregisterNetworkCallback(baseWifiTracker.mDefaultNetworkCallback);
                    baseWifiTracker.mConnectivityDiagnosticsManager.unregisterConnectivityDiagnosticsCallback(baseWifiTracker.mConnectivityDiagnosticsCallback);
                } catch (IllegalArgumentException unused) {
                }
                baseWifiTracker.mIsInitialized = false;
                return;
            case 1:
                BaseWifiTracker baseWifiTracker2 = (BaseWifiTracker) this.f$0;
                if (!baseWifiTracker2.mIsInitialized) {
                    IntentFilter intentFilter = new IntentFilter();
                    intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
                    intentFilter.addAction("android.net.wifi.SCAN_RESULTS");
                    intentFilter.addAction("android.net.wifi.CONFIGURED_NETWORKS_CHANGE");
                    intentFilter.addAction("android.net.wifi.STATE_CHANGE");
                    intentFilter.addAction("android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED");
                    intentFilter.setPriority(1000);
                    Context context = baseWifiTracker2.mContext;
                    BaseWifiTracker.AnonymousClass1 anonymousClass1 = baseWifiTracker2.mBroadcastReceiver;
                    Handler handler = baseWifiTracker2.mWorkerHandler;
                    context.registerReceiver(anonymousClass1, intentFilter, null, handler);
                    ConnectivityManager connectivityManager2 = baseWifiTracker2.mConnectivityManager;
                    NetworkRequest networkRequest = baseWifiTracker2.mNetworkRequest;
                    connectivityManager2.registerNetworkCallback(networkRequest, baseWifiTracker2.mNetworkCallback, handler);
                    connectivityManager2.registerDefaultNetworkCallback(baseWifiTracker2.mDefaultNetworkCallback, handler);
                    baseWifiTracker2.mConnectivityDiagnosticsManager.registerConnectivityDiagnosticsCallback(networkRequest, baseWifiTracker2.mConnectivityDiagnosticsExecutor, baseWifiTracker2.mConnectivityDiagnosticsCallback);
                }
                WifiQoSScoredCache wifiQoSScoredCache = baseWifiTracker2.mQoSScoredCache;
                Context context2 = wifiQoSScoredCache.mContext;
                ContentResolver contentResolver = context2.getContentResolver();
                if (!"CCT".equals(SemWifiUtils.readSalesCode()) && !"XMO".equals(SemWifiUtils.readSalesCode())) {
                    i = 0;
                } else {
                    i = 1;
                }
                if (Settings.Global.getInt(contentResolver, "sem_wifi_network_rating_scorer_enabled", i ^ 1) == 1) {
                    z = true;
                } else {
                    z = false;
                }
                SemWifiManager semWifiManager = wifiQoSScoredCache.mSemWifiManager;
                if (semWifiManager == null) {
                    Log.i("WifiTracker.WifiWifiQoSScoreCache", "SemWifiManager: null");
                    isSupportedQoSProvider = false;
                } else {
                    isSupportedQoSProvider = semWifiManager.isSupportedQoSProvider();
                }
                if (isSupportedQoSProvider) {
                    if (!SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_SAFETYCARE") && !SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_ULTRA_POWER_SAVING") && !SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_BATTERY_CONVERSING")) {
                        isEmergencyMode = false;
                    } else {
                        isEmergencyMode = SemEmergencyManager.isEmergencyMode(context2);
                    }
                    if (!isEmergencyMode && z) {
                        z2 = true;
                    }
                }
                KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("Network Score Enabling Check ", z2, "WifiTracker.WifiWifiQoSScoreCache");
                baseWifiTracker2.mNetworkScoringUiEnabled = z2;
                baseWifiTracker2.handleOnStart();
                baseWifiTracker2.mIsInitialized = true;
                return;
            default:
                ((AccessPointControllerImpl) ((BaseWifiTracker.BaseWifiTrackerCallback) this.f$0)).scanForAccessPoints();
                return;
        }
    }
}
