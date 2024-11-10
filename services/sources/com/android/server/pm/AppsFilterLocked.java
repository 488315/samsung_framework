package com.android.server.pm;

import com.android.server.pm.AppsFilterBase;
import java.io.PrintWriter;

/* loaded from: classes3.dex */
public abstract class AppsFilterLocked extends AppsFilterBase {
    public final Object mForceQueryableLock = new Object();
    public final Object mQueriesViaPackageLock = new Object();
    public final Object mQueriesViaComponentLock = new Object();
    public final Object mImplicitlyQueryableLock = new Object();
    public final Object mQueryableViaUsesLibraryLock = new Object();
    public final Object mProtectedBroadcastsLock = new Object();
    public final Object mQueryableViaUsesPermissionLock = new Object();
    public final Object mCacheLock = new Object();

    @Override // com.android.server.pm.AppsFilterBase
    public boolean isForceQueryable(int i) {
        boolean isForceQueryable;
        synchronized (this.mForceQueryableLock) {
            isForceQueryable = super.isForceQueryable(i);
        }
        return isForceQueryable;
    }

    @Override // com.android.server.pm.AppsFilterBase
    public boolean isQueryableViaPackage(int i, int i2) {
        boolean isQueryableViaPackage;
        synchronized (this.mQueriesViaPackageLock) {
            isQueryableViaPackage = super.isQueryableViaPackage(i, i2);
        }
        return isQueryableViaPackage;
    }

    @Override // com.android.server.pm.AppsFilterBase
    public boolean isQueryableViaComponent(int i, int i2) {
        boolean isQueryableViaComponent;
        synchronized (this.mQueriesViaComponentLock) {
            isQueryableViaComponent = super.isQueryableViaComponent(i, i2);
        }
        return isQueryableViaComponent;
    }

    @Override // com.android.server.pm.AppsFilterBase
    public boolean isImplicitlyQueryable(int i, int i2) {
        boolean isImplicitlyQueryable;
        synchronized (this.mImplicitlyQueryableLock) {
            isImplicitlyQueryable = super.isImplicitlyQueryable(i, i2);
        }
        return isImplicitlyQueryable;
    }

    @Override // com.android.server.pm.AppsFilterBase
    public boolean isRetainedImplicitlyQueryable(int i, int i2) {
        boolean isRetainedImplicitlyQueryable;
        synchronized (this.mImplicitlyQueryableLock) {
            isRetainedImplicitlyQueryable = super.isRetainedImplicitlyQueryable(i, i2);
        }
        return isRetainedImplicitlyQueryable;
    }

    @Override // com.android.server.pm.AppsFilterBase
    public boolean isQueryableViaUsesLibrary(int i, int i2) {
        boolean isQueryableViaUsesLibrary;
        synchronized (this.mQueryableViaUsesLibraryLock) {
            isQueryableViaUsesLibrary = super.isQueryableViaUsesLibrary(i, i2);
        }
        return isQueryableViaUsesLibrary;
    }

    @Override // com.android.server.pm.AppsFilterBase
    public boolean isQueryableViaUsesPermission(int i, int i2) {
        boolean isQueryableViaUsesPermission;
        synchronized (this.mQueryableViaUsesPermissionLock) {
            isQueryableViaUsesPermission = super.isQueryableViaUsesPermission(i, i2);
        }
        return isQueryableViaUsesPermission;
    }

    @Override // com.android.server.pm.AppsFilterBase
    public boolean shouldFilterApplicationUsingCache(int i, int i2, int i3) {
        boolean shouldFilterApplicationUsingCache;
        synchronized (this.mCacheLock) {
            shouldFilterApplicationUsingCache = super.shouldFilterApplicationUsingCache(i, i2, i3);
        }
        return shouldFilterApplicationUsingCache;
    }

    @Override // com.android.server.pm.AppsFilterBase
    public void dumpForceQueryable(PrintWriter printWriter, Integer num, AppsFilterBase.ToString toString) {
        synchronized (this.mForceQueryableLock) {
            super.dumpForceQueryable(printWriter, num, toString);
        }
    }

    @Override // com.android.server.pm.AppsFilterBase
    public void dumpQueriesViaPackage(PrintWriter printWriter, Integer num, AppsFilterBase.ToString toString) {
        synchronized (this.mQueriesViaPackageLock) {
            super.dumpQueriesViaPackage(printWriter, num, toString);
        }
    }

    @Override // com.android.server.pm.AppsFilterBase
    public void dumpQueriesViaComponent(PrintWriter printWriter, Integer num, AppsFilterBase.ToString toString) {
        synchronized (this.mQueriesViaComponentLock) {
            super.dumpQueriesViaComponent(printWriter, num, toString);
        }
    }

    @Override // com.android.server.pm.AppsFilterBase
    public void dumpQueriesViaImplicitlyQueryable(PrintWriter printWriter, Integer num, int[] iArr, AppsFilterBase.ToString toString) {
        synchronized (this.mImplicitlyQueryableLock) {
            super.dumpQueriesViaImplicitlyQueryable(printWriter, num, iArr, toString);
        }
    }

    @Override // com.android.server.pm.AppsFilterBase
    public void dumpQueriesViaUsesLibrary(PrintWriter printWriter, Integer num, AppsFilterBase.ToString toString) {
        synchronized (this.mQueryableViaUsesLibraryLock) {
            super.dumpQueriesViaUsesLibrary(printWriter, num, toString);
        }
    }
}
