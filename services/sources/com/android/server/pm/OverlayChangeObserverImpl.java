package com.android.server.pm;

import android.util.Slog;
import com.android.server.pm.PackageManagerService;
import com.samsung.android.localeoverlaymanager.OverlayChangeObserver;

/* loaded from: classes3.dex */
public final class OverlayChangeObserverImpl extends OverlayChangeObserver {
    public boolean callbackCompleted;
    public final int mDidLaunch;
    public final PackageManagerService mPm;
    public final String mTargetPackage;
    public final PackageManagerService.InstallLocaleOverlaysType mType;
    public final Object overlayLock;

    public OverlayChangeObserverImpl(int i, int i2, PackageManagerService.InstallLocaleOverlaysType installLocaleOverlaysType, int i3, String str, PackageManagerService packageManagerService) {
        super(i, i3);
        this.overlayLock = new Object();
        this.callbackCompleted = false;
        this.mDidLaunch = i2;
        this.mType = installLocaleOverlaysType;
        this.mTargetPackage = str;
        this.mPm = packageManagerService;
    }

    @Override // com.samsung.android.localeoverlaymanager.OverlayChangeObserver
    public void onChangeCompleted(boolean z, int i) {
        synchronized (this.overlayLock) {
            Slog.d("PackageManager", "onLocaleOverlaysInstalled. token= " + i + " callback done = " + this.callbackCompleted);
            if (!this.callbackCompleted) {
                this.callbackCompleted = true;
                this.mPm.overlaysInstallComplete(i, this.mDidLaunch, this.mType, this.mUserId, this.mTargetPackage, this.mTimeoutRunnable);
            }
        }
    }
}
