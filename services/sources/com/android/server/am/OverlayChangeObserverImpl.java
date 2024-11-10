package com.android.server.am;

import android.content.res.Configuration;
import android.util.Slog;
import com.samsung.android.localeoverlaymanager.OverlayChangeObserver;

/* loaded from: classes.dex */
public final class OverlayChangeObserverImpl extends OverlayChangeObserver {
    public final ActivityManagerService mAm;
    public final String mCallingAttributionTag;
    public final String mCallingPackage;
    public final int mCallingPid;
    public final Configuration mConfiguration;

    public OverlayChangeObserverImpl(Configuration configuration, String str, String str2, int i, int i2, ActivityManagerService activityManagerService) {
        super(0, i);
        this.mConfiguration = configuration;
        this.mCallingPackage = str;
        this.mCallingAttributionTag = str2;
        this.mCallingPid = i2;
        this.mAm = activityManagerService;
    }

    @Override // com.samsung.android.localeoverlaymanager.OverlayChangeObserver
    public void onChangeCompleted(boolean z, int i) {
        synchronized (this.overlayLock) {
            Slog.d("ActivityManager", "onLocaleOverlaysInstalled. token= " + i + " callback done = " + this.callbackCompleted);
            if (!this.callbackCompleted) {
                this.callbackCompleted = true;
                this.mAm.overlaysInstallComplete(this.mConfiguration, this.mCallingPackage, this.mCallingAttributionTag, this.mUserId, this.mCallingPid, this.mTimeoutRunnable);
            }
        }
    }
}
