package com.android.server.wm;

import com.android.server.wm.LockTaskController;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class LockTaskController$$ExternalSyntheticLambda5 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ LockTaskController$$ExternalSyntheticLambda5(int i, int i2, Object obj) {
        this.$r8$classId = i2;
        this.f$0 = obj;
        this.f$1 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                LockTaskController lockTaskController = (LockTaskController) this.f$0;
                int i = this.f$1;
                if (lockTaskController.mLockTaskModeState == 1) {
                    lockTaskController.setStatusBarState(lockTaskController.mLockTaskModeState, i);
                    lockTaskController.setKeyguardState(lockTaskController.mLockTaskModeState, i);
                    break;
                }
                break;
            default:
                LockTaskController.AnonymousClass1 anonymousClass1 = (LockTaskController.AnonymousClass1) this.f$0;
                int i2 = this.f$1;
                LockTaskController lockTaskController2 = LockTaskController.this;
                if (lockTaskController2.mPendingDisableFromDismiss == i2) {
                    lockTaskController2.mWindowManager.disableKeyguard(lockTaskController2.mToken, "Lock-to-App", i2);
                    LockTaskController.this.mPendingDisableFromDismiss = -10000;
                    break;
                }
                break;
        }
    }
}
