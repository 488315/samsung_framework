package com.android.systemui.slimindicator;

import android.content.Context;
import android.util.Log;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.slimindicator.SlimIndicatorSettingsBackUpManager;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.SettingsHelper;
import com.samsung.systemui.splugins.SPlugin;
import com.samsung.systemui.splugins.SPluginListener;
import com.samsung.systemui.splugins.SPluginManager;
import com.samsung.systemui.splugins.slimindicator.SPluginSlimIndicatorBox;
import java.util.Iterator;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class SlimIndicatorPluginMediator implements SPluginListener {
    public boolean mIsSPluginConnected = false;
    public final SlimIndicatorManager mManager;
    public final SlimIndicatorReceiverManager mReceiverManager;
    public final SlimIndicatorSettingsBackUpManager mSettingsBackUpManager;

    public SlimIndicatorPluginMediator(Context context, SlimIndicatorManager slimIndicatorManager) {
        this.mManager = slimIndicatorManager;
        SlimIndicatorSettingsBackUpManager slimIndicatorSettingsBackUpManager = new SlimIndicatorSettingsBackUpManager(context);
        this.mSettingsBackUpManager = slimIndicatorSettingsBackUpManager;
        if (!this.mIsSPluginConnected) {
            slimIndicatorSettingsBackUpManager.onPluginDisconnected();
        }
        this.mReceiverManager = new SlimIndicatorReceiverManager(slimIndicatorSettingsBackUpManager);
        ((SPluginManager) Dependency.get(SPluginManager.class)).addPluginListener(this, SPluginSlimIndicatorBox.class);
    }

    @Override // com.samsung.systemui.splugins.SPluginListener
    public final void onPluginConnected(SPlugin sPlugin, Context context) {
        SPluginSlimIndicatorBox sPluginSlimIndicatorBox = (SPluginSlimIndicatorBox) sPlugin;
        Log.d("[QuickStar]SlimIndicatorPluginMediator", "onPluginConnected() mIsSPluginConnected:" + this.mIsSPluginConnected + ", plugin:" + sPluginSlimIndicatorBox);
        if (!this.mIsSPluginConnected) {
            SlimIndicatorReceiverManager slimIndicatorReceiverManager = this.mReceiverManager;
            if (!slimIndicatorReceiverManager.mIsRegistered) {
                Iterator it = slimIndicatorReceiverManager.receivers.iterator();
                while (it.hasNext()) {
                    ((SlimIndicatorReceiver) it.next()).register();
                }
                slimIndicatorReceiverManager.mIsRegistered = true;
            }
            SlimIndicatorSettingsBackUpManager.SettingsListener settingsListener = this.mSettingsBackUpManager.mSettingsListener;
            settingsListener.getClass();
            if (Dependency.get(SettingsHelper.class) != null) {
                ((SettingsHelper) Dependency.get(SettingsHelper.class)).registerCallback(settingsListener, settingsListener.mSettingsValueList);
            }
            this.mIsSPluginConnected = true;
            SlimIndicatorViewMediatorImpl slimIndicatorViewMediatorImpl = (SlimIndicatorViewMediatorImpl) this.mManager;
            if (!slimIndicatorViewMediatorImpl.mIsAddedTunable) {
                ((TunerService) Dependency.get(TunerService.class)).addTunable(slimIndicatorViewMediatorImpl, "icon_blacklist");
                slimIndicatorViewMediatorImpl.mKeyguardUpdateMonitor.registerCallback(slimIndicatorViewMediatorImpl.mUserSwitchListener);
                slimIndicatorViewMediatorImpl.mIsAddedTunable = true;
                slimIndicatorViewMediatorImpl.notifyNewsToSubscribers();
            }
            if (sPluginSlimIndicatorBox != null) {
                try {
                    sPluginSlimIndicatorBox.onPluginConnected();
                } catch (Exception unused) {
                    Log.e("[QuickStar]SlimIndicatorPluginMediator", "Please check app version.");
                }
            }
        }
    }

    @Override // com.samsung.systemui.splugins.SPluginListener
    public final void onPluginDisconnected(SPlugin sPlugin, int i) {
        SPluginSlimIndicatorBox sPluginSlimIndicatorBox = (SPluginSlimIndicatorBox) sPlugin;
        StringBuilder sb = new StringBuilder("onPluginDisconnected() mIsSPluginConnected:");
        sb.append(this.mIsSPluginConnected);
        sb.append(", plugin:");
        sb.append(sPluginSlimIndicatorBox);
        sb.append(", reason:");
        RecyclerView$$ExternalSyntheticOutline0.m(sb, i, "[QuickStar]SlimIndicatorPluginMediator");
        if (this.mIsSPluginConnected) {
            SlimIndicatorReceiverManager slimIndicatorReceiverManager = this.mReceiverManager;
            Iterator it = slimIndicatorReceiverManager.receivers.iterator();
            while (it.hasNext()) {
                ((SlimIndicatorReceiver) it.next()).unregister();
            }
            slimIndicatorReceiverManager.mIsRegistered = false;
            this.mSettingsBackUpManager.onPluginDisconnected();
            this.mIsSPluginConnected = false;
            SlimIndicatorViewMediatorImpl slimIndicatorViewMediatorImpl = (SlimIndicatorViewMediatorImpl) this.mManager;
            if (slimIndicatorViewMediatorImpl.mIsAddedTunable) {
                ((TunerService) Dependency.get(TunerService.class)).removeTunable(slimIndicatorViewMediatorImpl);
                slimIndicatorViewMediatorImpl.mKeyguardUpdateMonitor.removeCallback(slimIndicatorViewMediatorImpl.mUserSwitchListener);
                slimIndicatorViewMediatorImpl.mIsAddedTunable = false;
                slimIndicatorViewMediatorImpl.notifyNewsToSubscribers();
            }
            if (sPluginSlimIndicatorBox != null) {
                try {
                    sPluginSlimIndicatorBox.onPluginDisconnected();
                } catch (Exception unused) {
                    Log.e("[QuickStar]SlimIndicatorPluginMediator", "Please check app version.");
                }
            }
        }
    }
}
