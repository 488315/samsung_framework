package com.android.keyguard;

import android.content.res.Resources;
import com.android.internal.widget.LockPatternUtils;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import javax.inject.Provider;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class KeyguardSecurityModel_Factory implements Provider {
    public final Provider keyguardUpdateMonitorProvider;
    public final Provider lockPatternUtilsProvider;
    public final Provider mKeyguardStateControllerProvider;
    public final Provider resourcesProvider;

    public KeyguardSecurityModel_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        this.resourcesProvider = provider;
        this.lockPatternUtilsProvider = provider2;
        this.keyguardUpdateMonitorProvider = provider3;
        this.mKeyguardStateControllerProvider = provider4;
    }

    public static KeyguardSecurityModel newInstance(Resources resources, LockPatternUtils lockPatternUtils, KeyguardUpdateMonitor keyguardUpdateMonitor) {
        return new KeyguardSecurityModel(resources, lockPatternUtils, keyguardUpdateMonitor);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        KeyguardSecurityModel keyguardSecurityModel = new KeyguardSecurityModel((Resources) this.resourcesProvider.get(), (LockPatternUtils) this.lockPatternUtilsProvider.get(), (KeyguardUpdateMonitor) this.keyguardUpdateMonitorProvider.get());
        keyguardSecurityModel.mKeyguardStateController = (KeyguardStateController) this.mKeyguardStateControllerProvider.get();
        return keyguardSecurityModel;
    }
}
