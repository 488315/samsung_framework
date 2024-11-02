package com.samsung.systemui.splugins;

import android.content.Context;
import android.os.Looper;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.plugins.ActivityStarter;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes3.dex */
public class SPluginInitializerImpl implements SPluginInitializer {
    private static final boolean WTFS_SHOULD_CRASH = false;
    private boolean mWtfsSet;

    @Override // com.samsung.systemui.splugins.SPluginInitializer
    public String[] getAllowedPlugins(Context context) {
        return context.getResources().getStringArray(R.array.config_pluginAllowlist);
    }

    @Override // com.samsung.systemui.splugins.SPluginInitializer
    public Looper getBgLooper() {
        return (Looper) Dependency.get(Dependency.BG_LOOPER);
    }

    @Override // com.samsung.systemui.splugins.SPluginInitializer
    public SPluginEnabler getPluginEnabler(Context context) {
        return new SPluginEnablerImpl(context);
    }

    @Override // com.samsung.systemui.splugins.SPluginInitializer
    public void onPluginManagerInit() {
        ((SPluginDependencyProvider) Dependency.get(SPluginDependencyProvider.class)).allowPluginDependency(ActivityStarter.class);
    }

    @Override // com.samsung.systemui.splugins.SPluginInitializer
    public void handleWtfs() {
    }
}
