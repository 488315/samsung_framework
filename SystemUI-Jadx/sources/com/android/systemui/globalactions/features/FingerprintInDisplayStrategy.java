package com.android.systemui.globalactions.features;

import com.android.systemui.globalactions.util.KeyguardUpdateMonitorWrapper;
import com.samsung.android.globalactions.presentation.SamsungGlobalActions;
import com.samsung.android.globalactions.presentation.strategies.ActionsCreationStrategy;
import com.samsung.android.globalactions.presentation.strategies.DisposingStrategy;
import com.samsung.android.globalactions.presentation.strategies.SecureConfirmStrategy;
import com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class FingerprintInDisplayStrategy implements ActionsCreationStrategy, DisposingStrategy, SecureConfirmStrategy {
    public final KeyguardUpdateMonitorWrapper mKeyguardUpdateMonitorWrapper;

    public FingerprintInDisplayStrategy(KeyguardUpdateMonitorWrapper keyguardUpdateMonitorWrapper) {
        this.mKeyguardUpdateMonitorWrapper = keyguardUpdateMonitorWrapper;
    }

    public final boolean doActionBeforeSecureConfirm(ActionViewModel actionViewModel, SamsungGlobalActions samsungGlobalActions) {
        this.mKeyguardUpdateMonitorWrapper.getClass();
        return true;
    }

    public final void onCreateActions(SamsungGlobalActions samsungGlobalActions) {
        this.mKeyguardUpdateMonitorWrapper.getClass();
    }

    public final void onDispose() {
        this.mKeyguardUpdateMonitorWrapper.getClass();
    }
}
