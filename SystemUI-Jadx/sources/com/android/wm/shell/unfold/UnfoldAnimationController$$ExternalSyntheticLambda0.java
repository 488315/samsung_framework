package com.android.wm.shell.unfold;

import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.unfold.animation.UnfoldTaskAnimator;
import java.util.List;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final /* synthetic */ class UnfoldAnimationController$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ UnfoldAnimationController$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                UnfoldAnimationController unfoldAnimationController = (UnfoldAnimationController) this.f$0;
                ShellUnfoldProgressProvider shellUnfoldProgressProvider = unfoldAnimationController.mUnfoldProgressProvider;
                ShellExecutor shellExecutor = unfoldAnimationController.mExecutor;
                shellUnfoldProgressProvider.addListener(shellExecutor, unfoldAnimationController);
                int i = 0;
                while (true) {
                    List list = unfoldAnimationController.mAnimators;
                    if (i < list.size()) {
                        UnfoldTaskAnimator unfoldTaskAnimator = (UnfoldTaskAnimator) list.get(i);
                        unfoldTaskAnimator.init();
                        ((HandlerExecutor) shellExecutor).executeDelayed(0L, new UnfoldAnimationController$$ExternalSyntheticLambda0(unfoldTaskAnimator, 1));
                        i++;
                    } else {
                        return;
                    }
                }
            default:
                ((UnfoldTaskAnimator) this.f$0).start();
                return;
        }
    }
}
