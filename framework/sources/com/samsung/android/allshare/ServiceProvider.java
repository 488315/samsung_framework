package com.samsung.android.allshare;

import com.samsung.android.allshare.ServiceConnector;
import com.samsung.android.allshare.extension.SECDownloader;

/* loaded from: classes5.dex */
public abstract class ServiceProvider {
    public static final String SERVICE_FILE = "com.samsung.android.allshare.file";
    public static final String SERVICE_MEDIA = "com.samsung.android.allshare.media";

    public abstract DeviceFinder getDeviceFinder();

    public abstract SECDownloader getDownloader();

    public abstract ServiceConnector.ServiceState getServiceState();

    public abstract String getServiceVersion();
}