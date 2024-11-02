package com.android.systemui.classifier;

import android.net.Uri;
import com.android.systemui.plugins.FalsingManager;
import java.io.PrintWriter;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class SecFalsingManagerDummy implements FalsingManager {
    @Override // com.android.systemui.plugins.FalsingManager
    public final boolean isClassifierEnabled() {
        return false;
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public final boolean isFalseDoubleTap() {
        return false;
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public final boolean isFalseLongTap(int i) {
        return false;
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public final boolean isFalseTap(int i) {
        return false;
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public final boolean isFalseTouch(int i) {
        return false;
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public final boolean isProximityNear() {
        return false;
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public final boolean isReportingEnabled() {
        return false;
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public final boolean isSimpleTap() {
        return false;
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public final boolean isUnlockingDisabled() {
        return false;
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public final Uri reportRejectedTouch() {
        return null;
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public final boolean shouldEnforceBouncer() {
        return false;
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public final void addFalsingBeliefListener(FalsingManager.FalsingBeliefListener falsingBeliefListener) {
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public final void addTapListener(FalsingManager.FalsingTapListener falsingTapListener) {
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public final void onProximityEvent(FalsingManager.ProximityEvent proximityEvent) {
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public final void removeFalsingBeliefListener(FalsingManager.FalsingBeliefListener falsingBeliefListener) {
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public final void removeTapListener(FalsingManager.FalsingTapListener falsingTapListener) {
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public final void cleanupInternal() {
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public final void onSuccessfulUnlock() {
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public final void dump(PrintWriter printWriter, String[] strArr) {
    }
}
