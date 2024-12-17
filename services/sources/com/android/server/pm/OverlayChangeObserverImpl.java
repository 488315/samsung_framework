package com.android.server.pm;

import android.util.Slog;
import com.android.server.pm.PackageManagerService;
import com.samsung.android.localeoverlaymanager.OverlayChangeObserver;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
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
    public final void onChangeCompleted(int i) {
        synchronized (this.overlayLock) {
            try {
                Slog.d("PackageManager", "onLocaleOverlaysInstalled. token= " + i + " callback done = " + this.callbackCompleted);
                if (!this.callbackCompleted) {
                    this.callbackCompleted = true;
                    this.mPm.overlaysInstallComplete(i, this.mDidLaunch, this.mType, this.mUserId, this.mTargetPackage, this.mTimeoutRunnable);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
