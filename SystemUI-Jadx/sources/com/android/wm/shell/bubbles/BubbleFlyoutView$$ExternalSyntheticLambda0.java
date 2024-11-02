package com.android.wm.shell.bubbles;

import android.graphics.PointF;
import com.android.wm.shell.bubbles.Bubble;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final /* synthetic */ class BubbleFlyoutView$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ BubbleFlyoutView f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ PointF f$2;
    public final /* synthetic */ boolean f$3;

    public /* synthetic */ BubbleFlyoutView$$ExternalSyntheticLambda0(BubbleFlyoutView bubbleFlyoutView, PointF pointF, boolean z, BubbleStackView$$ExternalSyntheticLambda3 bubbleStackView$$ExternalSyntheticLambda3) {
        this.f$0 = bubbleFlyoutView;
        this.f$2 = pointF;
        this.f$3 = z;
        this.f$1 = bubbleStackView$$ExternalSyntheticLambda3;
    }

    @Override // java.lang.Runnable
    public final void run() {
        float width;
        switch (this.$r8$classId) {
            case 0:
                final BubbleFlyoutView bubbleFlyoutView = this.f$0;
                Bubble.FlyoutMessage flyoutMessage = (Bubble.FlyoutMessage) this.f$1;
                final PointF pointF = this.f$2;
                final boolean z = this.f$3;
                bubbleFlyoutView.updateFlyoutMessage(flyoutMessage);
                bubbleFlyoutView.post(new Runnable() { // from class: com.android.wm.shell.bubbles.BubbleFlyoutView$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        BubbleFlyoutView bubbleFlyoutView2 = BubbleFlyoutView.this;
                        PointF pointF2 = pointF;
                        boolean z2 = z;
                        bubbleFlyoutView2.getClass();
                        bubbleFlyoutView2.fade(true, pointF2, z2, new BubbleFlyoutView$$ExternalSyntheticLambda2());
                    }
                });
                return;
            default:
                BubbleFlyoutView bubbleFlyoutView2 = this.f$0;
                PointF pointF2 = this.f$2;
                boolean z2 = this.f$3;
                Runnable runnable = (Runnable) this.f$1;
                bubbleFlyoutView2.getClass();
                float height = ((bubbleFlyoutView2.mBubbleSize - bubbleFlyoutView2.mFlyoutTextContainer.getHeight()) / 2.0f) + pointF2.y;
                bubbleFlyoutView2.mFlyoutY = height;
                bubbleFlyoutView2.setTranslationY(height);
                float f = pointF2.x;
                if (bubbleFlyoutView2.mArrowPointingLeft) {
                    width = f + bubbleFlyoutView2.mBubbleSize + bubbleFlyoutView2.mFlyoutSpaceFromBubble;
                } else {
                    width = (f - bubbleFlyoutView2.getWidth()) - bubbleFlyoutView2.mFlyoutSpaceFromBubble;
                }
                bubbleFlyoutView2.mRestingTranslationX = width;
                bubbleFlyoutView2.updateDot(pointF2, z2);
                if (runnable != null) {
                    runnable.run();
                    return;
                }
                return;
        }
    }

    public /* synthetic */ BubbleFlyoutView$$ExternalSyntheticLambda0(BubbleFlyoutView bubbleFlyoutView, Bubble.FlyoutMessage flyoutMessage, PointF pointF, boolean z) {
        this.f$0 = bubbleFlyoutView;
        this.f$1 = flyoutMessage;
        this.f$2 = pointF;
        this.f$3 = z;
    }
}
