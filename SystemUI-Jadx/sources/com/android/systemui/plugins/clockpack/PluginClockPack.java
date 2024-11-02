package com.android.systemui.plugins.clockpack;

import android.os.Bundle;
import android.view.ViewGroup;
import com.android.systemui.plugins.Plugin;
import com.android.systemui.plugins.annotations.ProvidesInterface;
import com.android.systemui.plugins.aod.PluginAOD;
import com.android.systemui.plugins.aod.PluginAODSystemUIConfiguration;
import java.io.PrintWriter;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
@ProvidesInterface(action = PluginClockPack.ACTION, version = 1)
/* loaded from: classes2.dex */
public interface PluginClockPack extends Plugin {
    public static final String ACTION = "com.samsung.systemui.action.PLUGIN_CLOCK_PACK";
    public static final int VERSION = 1;

    void dump(PrintWriter printWriter);

    boolean needDozeAlwaysOn();

    void onChargingAnimStarted(boolean z);

    void onDreamingStarted(ViewGroup viewGroup, PluginAODSystemUIConfiguration pluginAODSystemUIConfiguration);

    void onDreamingStopped();

    void onSystemUIConfigurationChanged(PluginAODSystemUIConfiguration pluginAODSystemUIConfiguration);

    void requestMODState(int i, boolean z);

    Bundle sendExtraData(Bundle bundle);

    void setAODPluginCallback(PluginAOD.Callback callback);

    void setAODUICallback(PluginAOD.UICallback uICallback);
}
