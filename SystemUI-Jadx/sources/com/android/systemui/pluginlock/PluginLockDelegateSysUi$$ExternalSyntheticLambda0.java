package com.android.systemui.pluginlock;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final /* synthetic */ class PluginLockDelegateSysUi$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PluginLockDelegateSysUi f$0;
    public final /* synthetic */ String f$1;

    public /* synthetic */ PluginLockDelegateSysUi$$ExternalSyntheticLambda0(PluginLockDelegateSysUi pluginLockDelegateSysUi, String str, int i) {
        this.$r8$classId = i;
        this.f$0 = pluginLockDelegateSysUi;
        this.f$1 = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.setDynamicLockDataInternal(this.f$1);
                return;
            default:
                PluginLockDelegateSysUi pluginLockDelegateSysUi = this.f$0;
                ((PluginLockMediatorImpl) pluginLockDelegateSysUi.mMediator).updateDynamicLockData(this.f$1);
                return;
        }
    }
}