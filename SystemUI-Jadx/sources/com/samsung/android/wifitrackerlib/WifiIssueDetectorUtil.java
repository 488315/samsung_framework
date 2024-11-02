package com.samsung.android.wifitrackerlib;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.os.Bundle;
import com.samsung.android.wifi.SemWifiManager;
import com.sec.ims.extensions.WiFiManagerExt;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes3.dex */
public final class WifiIssueDetectorUtil {
    public final String mNameOfUid;
    public final SemWifiManager mSemWifiManager;

    public WifiIssueDetectorUtil(Context context) {
        context.getPackageName();
        this.mNameOfUid = context.getPackageManager().getNameForUid(context.getUserId());
        this.mSemWifiManager = (SemWifiManager) context.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
    }

    public final void reportConnectNetwork(WifiConfiguration wifiConfiguration) {
        String str;
        String[] strArr;
        String str2;
        if (wifiConfiguration != null) {
            int i = wifiConfiguration.networkId;
            String printableSsid = wifiConfiguration.getPrintableSsid();
            int i2 = 1;
            if (wifiConfiguration.allowedKeyManagement.get(1) || wifiConfiguration.allowedKeyManagement.get(8) ? (str = wifiConfiguration.preSharedKey) == null || str.length() <= 2 : !wifiConfiguration.allowedKeyManagement.get(0) || (strArr = wifiConfiguration.wepKeys) == null || (str2 = strArr[0]) == null || str2.length() <= 2) {
                i2 = 0;
            }
            boolean isPasspoint = wifiConfiguration.isPasspoint();
            Bundle bundle = new Bundle();
            bundle.putInt("netid", i);
            bundle.putString("ssid", printableSsid);
            bundle.putString("apiName", "connect");
            bundle.putString("callUid", this.mNameOfUid);
            bundle.putInt("hasPassword", i2);
            bundle.putInt("isPasspoint", isPasspoint ? 1 : 0);
            this.mSemWifiManager.reportIssue(103, bundle);
        }
    }
}
