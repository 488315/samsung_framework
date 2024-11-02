package com.android.systemui.classifier;

import android.net.Uri;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.PluginManager;
import com.android.systemui.util.DeviceConfigProxy;
import java.io.PrintWriter;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class FalsingManagerProxy implements FalsingManager, Dumpable {
    public final SecFalsingManagerDummy mInternalFalsingManager = new SecFalsingManagerDummy();

    public FalsingManagerProxy(PluginManager pluginManager, Executor executor, DeviceConfigProxy deviceConfigProxy, DumpManager dumpManager, Provider provider) {
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public final void addFalsingBeliefListener(FalsingManager.FalsingBeliefListener falsingBeliefListener) {
        this.mInternalFalsingManager.getClass();
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public final void addTapListener(FalsingManager.FalsingTapListener falsingTapListener) {
        this.mInternalFalsingManager.getClass();
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public final void dump(PrintWriter printWriter, String[] strArr) {
        this.mInternalFalsingManager.getClass();
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public final boolean isClassifierEnabled() {
        this.mInternalFalsingManager.getClass();
        return false;
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public final boolean isFalseDoubleTap() {
        this.mInternalFalsingManager.getClass();
        return false;
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public final boolean isFalseLongTap(int i) {
        this.mInternalFalsingManager.getClass();
        return false;
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public final boolean isFalseTap(int i) {
        this.mInternalFalsingManager.getClass();
        return false;
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public final boolean isFalseTouch(int i) {
        this.mInternalFalsingManager.getClass();
        return false;
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public final boolean isProximityNear() {
        this.mInternalFalsingManager.getClass();
        return false;
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public final boolean isReportingEnabled() {
        this.mInternalFalsingManager.getClass();
        return false;
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public final boolean isSimpleTap() {
        this.mInternalFalsingManager.getClass();
        return false;
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public final boolean isUnlockingDisabled() {
        this.mInternalFalsingManager.getClass();
        return false;
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public final void onProximityEvent(FalsingManager.ProximityEvent proximityEvent) {
        this.mInternalFalsingManager.getClass();
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public final void onSuccessfulUnlock() {
        this.mInternalFalsingManager.getClass();
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public final void removeFalsingBeliefListener(FalsingManager.FalsingBeliefListener falsingBeliefListener) {
        this.mInternalFalsingManager.getClass();
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public final void removeTapListener(FalsingManager.FalsingTapListener falsingTapListener) {
        this.mInternalFalsingManager.getClass();
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public final Uri reportRejectedTouch() {
        this.mInternalFalsingManager.getClass();
        return null;
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public final boolean shouldEnforceBouncer() {
        this.mInternalFalsingManager.getClass();
        return false;
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public final void cleanupInternal() {
    }
}
