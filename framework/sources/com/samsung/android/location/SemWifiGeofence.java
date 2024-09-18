package com.samsung.android.location;

/* loaded from: classes5.dex */
public class SemWifiGeofence {
    private final String mBssid;
    private final String mRequestId;

    public SemWifiGeofence(String bssid, String requestId) {
        this.mBssid = bssid;
        this.mRequestId = requestId;
    }

    public String getBssid() {
        return this.mBssid;
    }

    public String getRequestId() {
        return this.mRequestId;
    }
}
