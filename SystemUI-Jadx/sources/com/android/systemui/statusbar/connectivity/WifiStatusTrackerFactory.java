package com.android.systemui.statusbar.connectivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkScoreManager;
import android.net.wifi.WifiManager;
import android.os.Handler;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class WifiStatusTrackerFactory {
    public final ConnectivityManager mConnectivityManager;
    public final Context mContext;
    public final Handler mMainHandler;
    public final NetworkScoreManager mNetworkScoreManager;
    public final WifiManager mWifiManager;

    public WifiStatusTrackerFactory(Context context, WifiManager wifiManager, NetworkScoreManager networkScoreManager, ConnectivityManager connectivityManager, Handler handler) {
        this.mContext = context;
        this.mWifiManager = wifiManager;
        this.mNetworkScoreManager = networkScoreManager;
        this.mConnectivityManager = connectivityManager;
        this.mMainHandler = handler;
    }
}
