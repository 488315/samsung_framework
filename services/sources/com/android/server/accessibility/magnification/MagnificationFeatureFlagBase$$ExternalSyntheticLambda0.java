package com.android.server.accessibility.magnification;

import android.provider.DeviceConfig;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class MagnificationFeatureFlagBase$$ExternalSyntheticLambda0 implements DeviceConfig.OnPropertiesChangedListener {
    public final /* synthetic */ MagnificationFeatureFlagBase f$0;
    public final /* synthetic */ Runnable f$1;

    public /* synthetic */ MagnificationFeatureFlagBase$$ExternalSyntheticLambda0(MagnificationFeatureFlagBase magnificationFeatureFlagBase, Runnable runnable) {
        this.f$0 = magnificationFeatureFlagBase;
        this.f$1 = runnable;
    }

    public final void onPropertiesChanged(DeviceConfig.Properties properties) {
        MagnificationFeatureFlagBase magnificationFeatureFlagBase = this.f$0;
        Runnable runnable = this.f$1;
        magnificationFeatureFlagBase.getClass();
        if (properties.getKeyset().contains(magnificationFeatureFlagBase.getFeatureName())) {
            runnable.run();
        }
    }
}
