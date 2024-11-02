package com.android.wm.shell.splitscreen;

import android.app.ActivityManager;
import com.android.wm.shell.splitscreen.SplitScreen;
import com.android.wm.shell.splitscreen.SplitScreenController;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final /* synthetic */ class SplitScreenController$Impl$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ boolean f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ SplitScreenController$Impl$$ExternalSyntheticLambda0(SplitScreenController.Impl impl, boolean z, int i) {
        this.f$0 = impl;
        this.f$1 = z;
        this.f$2 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i;
        switch (this.$r8$classId) {
            case 0:
                SplitScreenController.Impl impl = (SplitScreenController.Impl) this.f$0;
                boolean z = this.f$1;
                int i2 = this.f$2;
                if (z) {
                    SplitScreenController splitScreenController = SplitScreenController.this;
                    ActivityManager.RunningTaskInfo runningTaskInfo = splitScreenController.mTaskOrganizer.getRunningTaskInfo(i2);
                    if (runningTaskInfo != null) {
                        i = runningTaskInfo.configuration.windowConfiguration.getStagePosition();
                    } else {
                        i = 0;
                    }
                    if (splitScreenController.mFocusedTaskPosition != i) {
                        splitScreenController.mFocusedTaskPosition = i;
                        return;
                    }
                    return;
                }
                impl.getClass();
                return;
            default:
                SplitScreenController.SplitScreenImpl.AnonymousClass1 anonymousClass1 = (SplitScreenController.SplitScreenImpl.AnonymousClass1) this.f$0;
                int i3 = this.f$2;
                ((SplitScreen.SplitScreenListener) SplitScreenController.SplitScreenImpl.this.mExecutors.keyAt(i3)).onSplitVisibilityChanged(this.f$1);
                return;
        }
    }

    public /* synthetic */ SplitScreenController$Impl$$ExternalSyntheticLambda0(SplitScreenController.SplitScreenImpl.AnonymousClass1 anonymousClass1, int i, boolean z) {
        this.f$0 = anonymousClass1;
        this.f$2 = i;
        this.f$1 = z;
    }
}
