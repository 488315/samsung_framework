package com.sec.internal.ims.aec.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Handler;

/* loaded from: classes.dex */
public class DefaultNetwork {
    private final ConnectivityManager mConnMgr;
    ConnectivityManager.NetworkCallback mDefaultNetworkCallback = null;
    private final Handler mModuleHandler;

    public DefaultNetwork(Context context, Handler handler) {
        this.mModuleHandler = handler;
        this.mConnMgr = (ConnectivityManager) context.getSystemService("connectivity");
    }

    public void registerDefaultNetworkCallback() {
        if (this.mDefaultNetworkCallback == null) {
            ConnectivityManager.NetworkCallback defaultNetworkCallback = getDefaultNetworkCallback();
            this.mDefaultNetworkCallback = defaultNetworkCallback;
            this.mConnMgr.registerDefaultNetworkCallback(defaultNetworkCallback);
        }
    }

    public void unregisterNetworkCallback() {
        ConnectivityManager.NetworkCallback networkCallback = this.mDefaultNetworkCallback;
        if (networkCallback != null) {
            this.mConnMgr.unregisterNetworkCallback(networkCallback);
            this.mDefaultNetworkCallback = null;
        }
    }

    private ConnectivityManager.NetworkCallback getDefaultNetworkCallback() {
        return new ConnectivityManager.NetworkCallback() { // from class: com.sec.internal.ims.aec.util.DefaultNetwork.1
            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onAvailable(Network network) {
                if (network != null) {
                    DefaultNetwork.this.mModuleHandler.sendEmptyMessage(3);
                }
            }

            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
                if (network != null) {
                    if (networkCapabilities != null && networkCapabilities.hasCapability(12) && networkCapabilities.hasCapability(16)) {
                        DefaultNetwork.this.mModuleHandler.sendEmptyMessage(3);
                    }
                }
            }
        };
    }
}
