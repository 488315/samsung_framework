package com.android.systemui.wmshell;

import android.view.KeyEvent;
import com.android.systemui.model.SysUiState;
import com.android.systemui.wmshell.WMShell;
import com.samsung.android.knox.custom.IKnoxCustomManager;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final /* synthetic */ class WMShell$9$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ WMShell$9$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                WMShell wMShell = ((WMShell.AnonymousClass9) this.f$0).this$0;
                SysUiState sysUiState = wMShell.mSysUiState;
                sysUiState.setFlag(65536L, false);
                wMShell.mDisplayTracker.getClass();
                sysUiState.commitUpdate(0);
                return;
            case 1:
                WMShell wMShell2 = ((WMShell.AnonymousClass9) this.f$0).this$0;
                SysUiState sysUiState2 = wMShell2.mSysUiState;
                sysUiState2.setFlag(65536L, true);
                wMShell2.mDisplayTracker.getClass();
                sysUiState2.commitUpdate(0);
                return;
            case 2:
                WMShell wMShell3 = ((WMShell.AnonymousClass9) this.f$0).this$0;
                SysUiState sysUiState3 = wMShell3.mSysUiState;
                sysUiState3.setFlag(65536L, true);
                wMShell3.mDisplayTracker.getClass();
                sysUiState3.commitUpdate(0);
                return;
            default:
                ((WMShell.AnonymousClass10) this.f$0).this$0.mCommandQueue.handleSystemKey(new KeyEvent(0, IKnoxCustomManager.Stub.TRANSACTION_setBootingAnimationSub));
                return;
        }
    }
}
