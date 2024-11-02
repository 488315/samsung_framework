package com.android.wifitrackerlib;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.sharedconnectivity.app.HotspotNetwork;
import android.net.wifi.sharedconnectivity.app.SharedConnectivityManager;
import android.os.Handler;
import android.text.BidiFormatter;
import android.util.Log;
import com.android.systemui.R;
import com.android.wifitrackerlib.StandardWifiEntry;
import com.android.wifitrackerlib.WifiEntry;
import java.util.ArrayList;
import java.util.Objects;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class HotspotNetworkEntry extends WifiEntry {
    public final Context mContext;
    public HotspotNetwork mHotspotNetworkData;
    public HotspotNetworkEntryKey mKey;
    public boolean mServerInitiatedConnection;
    public final SharedConnectivityManager mSharedConnectivityManager;

    public HotspotNetworkEntry(WifiTrackerInjector wifiTrackerInjector, Context context, Handler handler, WifiManager wifiManager, SharedConnectivityManager sharedConnectivityManager, HotspotNetwork hotspotNetwork) {
        super(handler, wifiManager, false);
        this.mServerInitiatedConnection = false;
        this.mContext = context;
        this.mSharedConnectivityManager = sharedConnectivityManager;
        this.mHotspotNetworkData = hotspotNetwork;
        this.mKey = new HotspotNetworkEntryKey(hotspotNetwork);
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final boolean canConnect() {
        if (getConnectedState() == 0) {
            return true;
        }
        return false;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final boolean canDisconnect() {
        if (getConnectedState() != 0) {
            return true;
        }
        return false;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final void connect(final WifiEntry.ConnectCallback connectCallback) {
        this.mConnectCallback = connectCallback;
        SharedConnectivityManager sharedConnectivityManager = this.mSharedConnectivityManager;
        if (sharedConnectivityManager == null) {
            if (connectCallback != null) {
                final int i = 1;
                this.mCallbackHandler.post(new Runnable() { // from class: com.android.wifitrackerlib.HotspotNetworkEntry$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        switch (i) {
                            case 0:
                                ((HotspotNetworkEntry) connectCallback).mConnectCallback.onConnectResult(2);
                                return;
                            default:
                                ((WifiEntry.ConnectCallback) connectCallback).onConnectResult(2);
                                return;
                        }
                    }
                });
                return;
            }
            return;
        }
        sharedConnectivityManager.connectHotspotNetwork(this.mHotspotNetworkData);
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized boolean connectionInfoMatches(WifiInfo wifiInfo) {
        HotspotNetworkEntryKey hotspotNetworkEntryKey = this.mKey;
        if (hotspotNetworkEntryKey.mIsVirtualEntry) {
            return false;
        }
        return Objects.equals(hotspotNetworkEntryKey.mBssid, wifiInfo.getBSSID());
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final String getKey() {
        return this.mKey.toString();
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final String getSummary(boolean z) {
        if (this.mHotspotNetworkData == null) {
            return "";
        }
        int connectedState = getConnectedState();
        Context context = this.mContext;
        if (connectedState != 2 && this.mServerInitiatedConnection) {
            return context.getString(R.string.wifitrackerlib_hotspot_network_connecting);
        }
        return context.getString(R.string.wifitrackerlib_hotspot_network_summary, BidiFormatter.getInstance().unicodeWrap(this.mHotspotNetworkData.getNetworkName()), BidiFormatter.getInstance().unicodeWrap(this.mHotspotNetworkData.getNetworkProviderInfo().getModelName()));
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final String getTitle() {
        HotspotNetwork hotspotNetwork = this.mHotspotNetworkData;
        if (hotspotNetwork == null) {
            return "";
        }
        return hotspotNetwork.getNetworkProviderInfo().getDeviceName();
    }

    public final synchronized void updateHotspotNetworkData(HotspotNetwork hotspotNetwork) {
        this.mHotspotNetworkData = hotspotNetwork;
        this.mKey = new HotspotNetworkEntryKey(hotspotNetwork);
        notifyOnUpdated();
    }

    public HotspotNetworkEntry(WifiTrackerInjector wifiTrackerInjector, Context context, Handler handler, WifiManager wifiManager, SharedConnectivityManager sharedConnectivityManager, HotspotNetworkEntryKey hotspotNetworkEntryKey) {
        super(handler, wifiManager, false);
        this.mServerInitiatedConnection = false;
        this.mContext = context;
        this.mSharedConnectivityManager = sharedConnectivityManager;
        this.mHotspotNetworkData = null;
        this.mKey = hotspotNetworkEntryKey;
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public final class HotspotNetworkEntryKey {
        public final String mBssid;
        public final long mDeviceId;
        public final boolean mIsVirtualEntry;
        public final StandardWifiEntry.ScanResultKey mScanResultKey;

        public HotspotNetworkEntryKey(HotspotNetwork hotspotNetwork) {
            this.mDeviceId = hotspotNetwork.getDeviceId();
            if (hotspotNetwork.getHotspotSsid() != null && hotspotNetwork.getHotspotBssid() != null && hotspotNetwork.getHotspotSecurityTypes() != null) {
                this.mIsVirtualEntry = false;
                this.mBssid = hotspotNetwork.getHotspotBssid();
                this.mScanResultKey = new StandardWifiEntry.ScanResultKey(hotspotNetwork.getHotspotSsid(), new ArrayList(hotspotNetwork.getHotspotSecurityTypes()));
            } else {
                this.mIsVirtualEntry = true;
                this.mBssid = null;
                this.mScanResultKey = null;
            }
        }

        public final String toString() {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("IS_VIRTUAL_ENTRY_KEY", this.mIsVirtualEntry);
                jSONObject.put("DEVICE_ID_KEY", this.mDeviceId);
                String str = this.mBssid;
                if (str != null) {
                    jSONObject.put("BSSID_KEY", str);
                }
                StandardWifiEntry.ScanResultKey scanResultKey = this.mScanResultKey;
                if (scanResultKey != null) {
                    jSONObject.put("SCAN_RESULT_KEY", scanResultKey.toString());
                }
            } catch (JSONException e) {
                Log.wtf("HotspotNetworkEntry", "JSONException while converting HotspotNetworkEntryKey to string: " + e);
            }
            return "HotspotNetworkEntry:" + jSONObject.toString();
        }

        public HotspotNetworkEntryKey(String str) {
            this.mScanResultKey = null;
            if (!str.startsWith("HotspotNetworkEntry:")) {
                Log.e("HotspotNetworkEntry", "String key does not start with key prefix!");
                return;
            }
            try {
                JSONObject jSONObject = new JSONObject(str.substring(20));
                if (jSONObject.has("IS_VIRTUAL_ENTRY_KEY")) {
                    this.mIsVirtualEntry = jSONObject.getBoolean("IS_VIRTUAL_ENTRY_KEY");
                }
                if (jSONObject.has("DEVICE_ID_KEY")) {
                    this.mDeviceId = jSONObject.getLong("DEVICE_ID_KEY");
                }
                if (jSONObject.has("BSSID_KEY")) {
                    this.mBssid = jSONObject.getString("BSSID_KEY");
                }
                if (jSONObject.has("SCAN_RESULT_KEY")) {
                    this.mScanResultKey = new StandardWifiEntry.ScanResultKey(jSONObject.getString("SCAN_RESULT_KEY"));
                }
            } catch (JSONException e) {
                Log.e("HotspotNetworkEntry", "JSONException while converting HotspotNetworkEntryKey to string: " + e);
            }
        }
    }
}
