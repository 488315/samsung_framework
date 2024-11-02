package com.airbnb.lottie;

import com.airbnb.lottie.LottieDrawable;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final /* synthetic */ class LottieDrawable$$ExternalSyntheticLambda5 implements LottieDrawable.LazyCompositionTask {
    public final /* synthetic */ LottieDrawable f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ LottieDrawable$$ExternalSyntheticLambda5(LottieDrawable lottieDrawable, int i, int i2) {
        this.f$0 = lottieDrawable;
        this.f$1 = i;
        this.f$2 = i2;
    }

    @Override // com.airbnb.lottie.LottieDrawable.LazyCompositionTask
    public final void run() {
        LottieDrawable lottieDrawable = this.f$0;
        LottieComposition lottieComposition = lottieDrawable.composition;
        int i = this.f$1;
        int i2 = this.f$2;
        if (lottieComposition == null) {
            lottieDrawable.lazyCompositionTasks.add(new LottieDrawable$$ExternalSyntheticLambda5(lottieDrawable, i, i2));
        } else {
            lottieDrawable.animator.setMinAndMaxFrames(i, i2 + 0.99f);
        }
    }
}
