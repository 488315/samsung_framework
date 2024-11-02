package com.android.systemui.bixby2.util;

import android.app.KeyguardManager;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.util.DesktopManager;
import javax.inject.Provider;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class ActivityLauncher_Factory implements Provider {
    private final Provider desktopManagerProvider;
    private final Provider displayLifecycleProvider;
    private final Provider keyguardManagerProvider;

    public ActivityLauncher_Factory(Provider provider, Provider provider2, Provider provider3) {
        this.desktopManagerProvider = provider;
        this.displayLifecycleProvider = provider2;
        this.keyguardManagerProvider = provider3;
    }

    public static ActivityLauncher_Factory create(Provider provider, Provider provider2, Provider provider3) {
        return new ActivityLauncher_Factory(provider, provider2, provider3);
    }

    public static ActivityLauncher newInstance(DesktopManager desktopManager, DisplayLifecycle displayLifecycle, KeyguardManager keyguardManager) {
        return new ActivityLauncher(desktopManager, displayLifecycle, keyguardManager);
    }

    @Override // javax.inject.Provider
    public ActivityLauncher get() {
        return newInstance((DesktopManager) this.desktopManagerProvider.get(), (DisplayLifecycle) this.displayLifecycleProvider.get(), (KeyguardManager) this.keyguardManagerProvider.get());
    }
}
