package com.android.wm.shell.bubbles.animation;

import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import com.android.wm.shell.bubbles.animation.PhysicsAnimationLayout;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final /* synthetic */ class PhysicsAnimationLayout$PhysicsPropertyAnimator$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ PhysicsAnimationLayout$PhysicsPropertyAnimator$$ExternalSyntheticLambda1(Object obj, Object obj2, int i, Object obj3) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
        this.f$2 = obj3;
    }

    @Override // java.lang.Runnable
    public final void run() {
        boolean z;
        int i = 0;
        switch (this.$r8$classId) {
            case 0:
                PhysicsAnimationLayout.PhysicsPropertyAnimator physicsPropertyAnimator = (PhysicsAnimationLayout.PhysicsPropertyAnimator) this.f$0;
                SpringAnimation springAnimation = (SpringAnimation) this.f$1;
                SpringAnimation springAnimation2 = (SpringAnimation) this.f$2;
                physicsPropertyAnimator.getClass();
                if (!springAnimation.mRunning && !springAnimation2.mRunning) {
                    Runnable[] runnableArr = physicsPropertyAnimator.mPositionEndActions;
                    if (runnableArr != null) {
                        int length = runnableArr.length;
                        while (i < length) {
                            runnableArr[i].run();
                            i++;
                        }
                    }
                    physicsPropertyAnimator.mPositionEndActions = null;
                    return;
                }
                return;
            default:
                PhysicsAnimationLayout.PhysicsAnimationController physicsAnimationController = (PhysicsAnimationLayout.PhysicsAnimationController) this.f$0;
                DynamicAnimation.ViewProperty[] viewPropertyArr = (DynamicAnimation.ViewProperty[]) this.f$1;
                Runnable runnable = (Runnable) this.f$2;
                PhysicsAnimationLayout physicsAnimationLayout = physicsAnimationController.mLayout;
                int i2 = 0;
                while (true) {
                    if (i2 < physicsAnimationLayout.getChildCount()) {
                        if (PhysicsAnimationLayout.arePropertiesAnimatingOnView(physicsAnimationLayout.getChildAt(i2), viewPropertyArr)) {
                            z = true;
                        } else {
                            i2++;
                        }
                    } else {
                        z = false;
                    }
                }
                if (!z) {
                    runnable.run();
                    int length2 = viewPropertyArr.length;
                    while (i < length2) {
                        physicsAnimationController.mLayout.mEndActionForProperty.remove(viewPropertyArr[i]);
                        i++;
                    }
                    return;
                }
                return;
        }
    }
}
