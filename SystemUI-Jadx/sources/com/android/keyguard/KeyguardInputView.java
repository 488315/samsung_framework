package com.android.keyguard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.systemui.R;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public abstract class KeyguardInputView extends LinearLayout {
    public View mBouncerMessageView;
    public Runnable mOnFinishImeAnimationRunnable;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* renamed from: com.android.keyguard.KeyguardInputView$1, reason: invalid class name */
    /* loaded from: classes.dex */
    public final class AnonymousClass1 extends AnimatorListenerAdapter {
        public boolean mIsCancel;
        public final /* synthetic */ int val$cuj;

        public AnonymousClass1(int i) {
            this.val$cuj = i;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationCancel(Animator animator) {
            this.mIsCancel = true;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator) {
            if (this.mIsCancel) {
                InteractionJankMonitor.getInstance().cancel(this.val$cuj);
            } else {
                InteractionJankMonitor.getInstance().end(this.val$cuj);
            }
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationStart(Animator animator) {
            InteractionJankMonitor.getInstance().begin(KeyguardInputView.this, this.val$cuj);
        }
    }

    public KeyguardInputView(Context context) {
        super(context);
    }

    public abstract CharSequence getTitle();

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mBouncerMessageView = findViewById(R.id.bouncer_message_view);
    }

    public boolean startDisappearAnimation(Runnable runnable) {
        return false;
    }

    public KeyguardInputView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public KeyguardInputView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void startAppearAnimation() {
    }
}
