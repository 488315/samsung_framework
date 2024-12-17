package com.android.server.accessibility.autoaction;

import android.view.ViewPropertyAnimator;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class CornerActionCircleCue$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ CornerActionCircleCue f$0;

    public /* synthetic */ CornerActionCircleCue$$ExternalSyntheticLambda0(CornerActionCircleCue cornerActionCircleCue, int i) {
        this.$r8$classId = i;
        this.f$0 = cornerActionCircleCue;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        CornerActionCircleCue cornerActionCircleCue = this.f$0;
        switch (i) {
            case 0:
                cornerActionCircleCue.mProgressImageView.setVisibility(0);
                ViewPropertyAnimator animate = cornerActionCircleCue.mProgressImageView.animate();
                float f = cornerActionCircleCue.mScale;
                animate.scaleX(f).scaleY(f).setDuration(cornerActionCircleCue.mDurationTime).start();
                break;
            default:
                cornerActionCircleCue.mProgressImageView.setVisibility(8);
                cornerActionCircleCue.mProgressImageView.clearAnimation();
                cornerActionCircleCue.mProgressImageView.setScaleX(FullScreenMagnificationGestureHandler.MAX_SCALE);
                cornerActionCircleCue.mProgressImageView.setScaleY(FullScreenMagnificationGestureHandler.MAX_SCALE);
                break;
        }
    }
}
