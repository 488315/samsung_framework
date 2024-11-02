package com.android.systemui.statusbar.notification.stack;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.util.Log;
import android.util.Property;
import com.android.systemui.R;
import com.android.systemui.statusbar.NotificationShelf;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Stack;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class StackStateAnimator {
    public final AnonymousClass1 mAnimationProperties;
    public ValueAnimator mBottomOverScrollAnimator;
    public long mCurrentAdditionalDelay;
    public long mCurrentLength;
    public int mHeadsUpAppearHeightBottom;
    public final NotificationStackScrollLayout mHostLayout;
    public final int mKeyguardStatusBarHeight;
    public StackStateLogger mLogger;
    public boolean mShadeExpanded;
    public NotificationShelf mShelf;
    public ValueAnimator mTopOverScrollAnimator;
    public float mTopYWhenGoToFullShade;
    public final ExpandableViewState mTmpState = new ExpandableViewState();
    public final ArrayList mNewEvents = new ArrayList();
    public final ArrayList mNewAddChildren = new ArrayList();
    public final HashSet mHeadsUpAppearChildren = new HashSet();
    public final HashSet mHeadsUpDisappearChildren = new HashSet();
    public final HashSet mAnimatorSet = new HashSet();
    public final Stack mAnimationListenerPool = new Stack();
    public final AnimationFilter mAnimationFilter = new AnimationFilter();
    public final ArrayList mTransientViewsToRemove = new ArrayList();
    public final int[] mTmpLocation = new int[2];

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* renamed from: com.android.systemui.statusbar.notification.stack.StackStateAnimator$1, reason: invalid class name */
    /* loaded from: classes2.dex */
    public final class AnonymousClass1 extends AnimationProperties {
        public AnonymousClass1() {
        }

        @Override // com.android.systemui.statusbar.notification.stack.AnimationProperties
        public final AnimationFilter getAnimationFilter() {
            return StackStateAnimator.this.mAnimationFilter;
        }

        @Override // com.android.systemui.statusbar.notification.stack.AnimationProperties
        public final AnimatorListenerAdapter getAnimationFinishListener(Property property) {
            StackStateAnimator stackStateAnimator = StackStateAnimator.this;
            Stack stack = stackStateAnimator.mAnimationListenerPool;
            if (!stack.empty()) {
                return (AnimatorListenerAdapter) stack.pop();
            }
            return new AnonymousClass2();
        }

        @Override // com.android.systemui.statusbar.notification.stack.AnimationProperties
        public final boolean wasAdded(ExpandableView expandableView) {
            return StackStateAnimator.this.mNewAddChildren.contains(expandableView);
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* renamed from: com.android.systemui.statusbar.notification.stack.StackStateAnimator$2, reason: invalid class name */
    /* loaded from: classes2.dex */
    public final class AnonymousClass2 extends AnimatorListenerAdapter {
        public boolean mWasCancelled;

        public AnonymousClass2() {
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationCancel(Animator animator) {
            Log.d("StackScroller", "getGlobalAnimationFinishedListener onAnimationCancel");
            this.mWasCancelled = true;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator) {
            StackStateAnimator.this.mAnimatorSet.remove(animator);
            if (StackStateAnimator.this.mAnimatorSet.isEmpty() && !this.mWasCancelled) {
                Log.d("StackScroller", "getGlobalAnimationFinishedListener animation end");
                StackStateAnimator.this.onAnimationFinished();
            }
            StackStateAnimator.this.mAnimationListenerPool.push(this);
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationStart(Animator animator) {
            this.mWasCancelled = false;
            StackStateAnimator.this.mAnimatorSet.add(animator);
        }
    }

    public StackStateAnimator(NotificationStackScrollLayout notificationStackScrollLayout) {
        this.mHostLayout = notificationStackScrollLayout;
        notificationStackScrollLayout.getContext().getResources().getDimensionPixelSize(R.dimen.go_to_full_shade_appearing_translation);
        notificationStackScrollLayout.getContext().getResources().getDimensionPixelSize(R.dimen.pulsing_notification_appear_translation);
        this.mKeyguardStatusBarHeight = notificationStackScrollLayout.getContext().getResources().getDimensionPixelSize(R.dimen.status_bar_header_height_keyguard);
        this.mAnimationProperties = new AnonymousClass1();
    }

    public final void onAnimationFinished() {
        NotificationStackScrollLayout notificationStackScrollLayout = this.mHostLayout;
        notificationStackScrollLayout.setAnimationRunning(false);
        notificationStackScrollLayout.requestChildrenUpdate();
        Iterator it = notificationStackScrollLayout.mAnimationFinishedRunnables.iterator();
        while (it.hasNext()) {
            ((Runnable) it.next()).run();
        }
        notificationStackScrollLayout.mAnimationFinishedRunnables.clear();
        Iterator it2 = notificationStackScrollLayout.mClearTransientViewsWhenFinished.iterator();
        while (it2.hasNext()) {
            ((ExpandableView) it2.next()).removeFromTransientContainer();
        }
        notificationStackScrollLayout.mClearTransientViewsWhenFinished.clear();
        notificationStackScrollLayout.clearHeadsUpDisappearRunning();
        Log.d("StackScroller", "onChildAnimationFinished clearTemporaryViews");
        notificationStackScrollLayout.finalizeClearAllAnimation();
        ArrayList arrayList = this.mTransientViewsToRemove;
        Iterator it3 = arrayList.iterator();
        while (it3.hasNext()) {
            ((ExpandableView) it3.next()).removeFromTransientContainer();
        }
        arrayList.clear();
    }
}
