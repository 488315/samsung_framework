package com.android.wm.shell.splitscreen;

import com.android.wm.shell.splitscreen.SplitScreenController;
import java.util.function.Consumer;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final /* synthetic */ class SplitScreenController$$ExternalSyntheticLambda8 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ boolean f$0;

    public /* synthetic */ SplitScreenController$$ExternalSyntheticLambda8(boolean z, int i) {
        this.$r8$classId = i;
        this.f$0 = z;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((SplitScreenController.SplitTwoFingerGestureStarter) obj).mEnabled = this.f$0;
                return;
            default:
                boolean z = this.f$0;
                int i = SplitScreenController.ISplitScreenImpl.$r8$clinit;
                ((SplitScreenController) obj).mStageCoordinator.mExitSplitScreenOnHide = z;
                return;
        }
    }
}
