package com.android.systemui.facewidget.plugin;

import android.graphics.Point;
import android.util.DisplayMetrics;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.plugins.keyguardstatusview.PluginDisplayLifeCycle;
import com.android.systemui.util.LogUtil;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class FaceWidgetDisplayLifeCycleWrapper implements PluginDisplayLifeCycle {
    public final DisplayLifecycle mDisplayLifecycle;

    public FaceWidgetDisplayLifeCycleWrapper(DisplayLifecycle displayLifecycle) {
        this.mDisplayLifecycle = displayLifecycle;
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginDisplayLifeCycle
    public final DisplayMetrics getDisplayMetrics() {
        DisplayLifecycle displayLifecycle = this.mDisplayLifecycle;
        if (displayLifecycle.getDisplay(0) == null) {
            displayLifecycle.addDisplay(0);
        }
        DisplayMetrics displayMetrics = (DisplayMetrics) displayLifecycle.mDisplayMetricsHash.get(0);
        if (displayMetrics == null) {
            LogUtil.w("DisplayLifecycle", "getDisplayMetrics(%d) is null, return empty Point", 0);
            return new DisplayMetrics();
        }
        return displayMetrics;
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginDisplayLifeCycle
    public final Point getRealSize() {
        return this.mDisplayLifecycle.getRealSize();
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginDisplayLifeCycle
    public final boolean isFolderOpened() {
        return this.mDisplayLifecycle.mIsFolderOpened;
    }
}
