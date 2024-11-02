package com.android.systemui.power;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.Binder;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import com.android.systemui.R;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class InattentiveSleepWarningView extends FrameLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public boolean mDismissing;
    public final Animator mFadeOutAnimator;
    public final WindowManager mWindowManager;
    public final IBinder mWindowToken;

    public InattentiveSleepWarningView(Context context) {
        super(context);
        this.mWindowToken = new Binder();
        this.mWindowManager = (WindowManager) ((FrameLayout) this).mContext.getSystemService(WindowManager.class);
        LayoutInflater.from(((FrameLayout) this).mContext).inflate(R.layout.inattentive_sleep_warning, (ViewGroup) this, true);
        setFocusable(true);
        setOnKeyListener(new InattentiveSleepWarningView$$ExternalSyntheticLambda0());
        Animator loadAnimator = AnimatorInflater.loadAnimator(getContext(), android.R.animator.fade_out);
        this.mFadeOutAnimator = loadAnimator;
        loadAnimator.setTarget(this);
        loadAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.power.InattentiveSleepWarningView.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
                InattentiveSleepWarningView inattentiveSleepWarningView = InattentiveSleepWarningView.this;
                inattentiveSleepWarningView.mDismissing = false;
                inattentiveSleepWarningView.setAlpha(1.0f);
                InattentiveSleepWarningView.this.setVisibility(0);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                InattentiveSleepWarningView inattentiveSleepWarningView = InattentiveSleepWarningView.this;
                int i = InattentiveSleepWarningView.$r8$clinit;
                if (inattentiveSleepWarningView.mDismissing) {
                    inattentiveSleepWarningView.setVisibility(4);
                    inattentiveSleepWarningView.mWindowManager.removeView(inattentiveSleepWarningView);
                }
            }
        });
    }
}
