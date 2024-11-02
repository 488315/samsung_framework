package com.android.systemui.tuner;

import android.content.Context;
import android.os.Handler;
import com.android.systemui.demomode.DemoModeController;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.util.leak.LeakDetector;
import javax.inject.Provider;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class TunerServiceImpl_Factory implements Provider {
    public final Provider contextProvider;
    public final Provider demoModeControllerProvider;
    public final Provider leakDetectorProvider;
    public final Provider mainHandlerProvider;
    public final Provider userTrackerProvider;

    public TunerServiceImpl_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        this.contextProvider = provider;
        this.mainHandlerProvider = provider2;
        this.leakDetectorProvider = provider3;
        this.demoModeControllerProvider = provider4;
        this.userTrackerProvider = provider5;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new TunerServiceImpl((Context) this.contextProvider.get(), (Handler) this.mainHandlerProvider.get(), (LeakDetector) this.leakDetectorProvider.get(), (DemoModeController) this.demoModeControllerProvider.get(), (UserTracker) this.userTrackerProvider.get());
    }
}
