package com.android.server.accessibility.magnification;

import android.R;
import android.content.Context;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class AlwaysOnMagnificationFeatureFlag extends MagnificationFeatureFlagBase {
    public Context mContext;

    @Override // com.android.server.accessibility.magnification.MagnificationFeatureFlagBase
    public final boolean getDefaultValue() {
        return this.mContext.getResources().getBoolean(R.bool.config_navBarCanMove);
    }

    @Override // com.android.server.accessibility.magnification.MagnificationFeatureFlagBase
    public final String getFeatureName() {
        return "AlwaysOnMagnifier__enable_always_on_magnifier";
    }

    @Override // com.android.server.accessibility.magnification.MagnificationFeatureFlagBase
    public final String getNamespace() {
        return "window_manager";
    }

    @Override // com.android.server.accessibility.magnification.MagnificationFeatureFlagBase
    public /* bridge */ /* synthetic */ boolean setFeatureFlagEnabled(boolean z) {
        return super.setFeatureFlagEnabled(z);
    }
}
