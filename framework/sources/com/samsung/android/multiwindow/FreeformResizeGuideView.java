package com.samsung.android.multiwindow;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.app.WindowConfiguration;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Rect;
import android.os.UserHandle;
import android.util.AttributeSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.android.internal.R;
import com.samsung.android.multiwindow.FreeformResizeGuide;
import com.samsung.android.util.InterpolatorUtils;
import java.util.ArrayList;

/* loaded from: classes6.dex */
public class FreeformResizeGuideView extends FrameLayout {
    private ValueAnimator mAlphaAnimator;
    private final ArrayList<Animator> mAnimList;
    private final AnimatorSet mAnimatorSet;
    private int mAppIconSize;
    private ImageView mAppIconView;
    private ImageView mDimView;
    private int mDimViewMargin;
    private int mFullscreenDimViewMargin;
    private ValueAnimator mHeightAnimator;
    private boolean mIsTransition;
    private ValueAnimator mLeftMarginAnimator;
    private boolean mToFullScreen;
    private ValueAnimator mTopMarginAnimator;
    private ValueAnimator mWidthAnimator;

    public FreeformResizeGuideView(Context context) {
        super(context);
        this.mAnimList = new ArrayList<>();
        this.mAnimatorSet = new AnimatorSet();
    }

    public FreeformResizeGuideView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mAnimList = new ArrayList<>();
        this.mAnimatorSet = new AnimatorSet();
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mDimView = (ImageView) findViewById(R.id.freeform_resize_guide_dim);
        this.mAppIconView = (ImageView) findViewById(R.id.freeform_resize_guide_app_icon);
        this.mDimViewMargin = getResources().getDimensionPixelSize(R.dimen.freeform_resize_guide_view_dim_margin);
        this.mFullscreenDimViewMargin = getResources().getDimensionPixelSize(R.dimen.freeform_resize_guide_view_fullscreen_dim_margin);
        this.mAppIconSize = getResources().getDimensionPixelSize(R.dimen.freeform_resize_guide_view_app_icon_size);
    }

    void update(int dexDockingState, ComponentName componentName) {
        if (WindowConfiguration.isDexTaskDocking(dexDockingState)) {
            this.mDimViewMargin = 0;
            this.mFullscreenDimViewMargin = 0;
        }
        this.mDimView.lambda$setImageURIAsync$0(getResources().getDrawable(getGuideResourceId(dexDockingState)));
        if (componentName != null) {
            this.mAppIconView.lambda$setImageURIAsync$0(MultiWindowUtils.getAppIcon(this.mContext, componentName, UserHandle.myUserId(), componentName.getPackageName()));
        }
    }

    private int getGuideResourceId(int dexDockingState) {
        switch (dexDockingState) {
            case 1:
                return R.drawable.dex_docking_resize_guide_left;
            case 2:
                return R.drawable.dex_docking_resize_guide_right;
            default:
                return R.drawable.mw_popupview_img_resizingguide;
        }
    }

    void show(Rect lastBounds, Rect bounds, boolean toFullScreen) {
        show(lastBounds, bounds, toFullScreen, toFullScreen, null);
    }

    void show(Rect lastBounds, Rect bounds, boolean isTransition, boolean toFullScreen, FreeformResizeGuide.TransitionInfo transitionInfo) {
        boolean isTransitionAnimation;
        TimeInterpolator interpolator;
        if (this.mIsTransition != isTransition || this.mToFullScreen != toFullScreen) {
            this.mIsTransition = isTransition;
            this.mToFullScreen = toFullScreen;
            isTransitionAnimation = true;
        } else {
            isTransitionAnimation = false;
        }
        int startMargin = this.mDimViewMargin;
        int endMargin = toFullScreen ? this.mFullscreenDimViewMargin : this.mDimViewMargin;
        int fromLeftMargin = lastBounds.left - startMargin;
        int fromTopMargin = lastBounds.top - startMargin;
        int fromWidth = lastBounds.width() + (startMargin * 2);
        int fromHeight = lastBounds.height() + (startMargin * 2);
        int toLeftMargin = bounds.left - endMargin;
        int toTopMargin = bounds.top - endMargin;
        int toWidth = bounds.width() + (endMargin * 2);
        int toHeight = bounds.height() + (endMargin * 2);
        final FrameLayout.LayoutParams dimLp = (FrameLayout.LayoutParams) this.mDimView.getLayoutParams();
        FrameLayout.LayoutParams minimizeLp = (FrameLayout.LayoutParams) this.mAppIconView.getLayoutParams();
        if (!isTransitionAnimation) {
            if (!this.mIsTransition) {
                this.mAnimatorSet.cancel();
                if (dimLp != null) {
                    dimLp.leftMargin = toLeftMargin;
                    dimLp.topMargin = toTopMargin;
                    dimLp.width = toWidth;
                    dimLp.height = toHeight;
                    this.mDimView.setLayoutParams(dimLp);
                }
                if (minimizeLp != null) {
                    minimizeLp.leftMargin = (bounds.left + (bounds.width() / 2)) - (this.mAppIconSize / 2);
                    minimizeLp.topMargin = (bounds.top + (bounds.height() / 2)) - (this.mAppIconSize / 2);
                    this.mAppIconView.setLayoutParams(minimizeLp);
                }
            }
        } else {
            this.mAnimList.clear();
            if (dimLp != null) {
                removeAllUpdateListenersIfNeeded(this.mLeftMarginAnimator, this.mTopMarginAnimator, this.mWidthAnimator, this.mHeightAnimator, this.mAlphaAnimator);
                boolean equalLeftMargin = fromLeftMargin == toLeftMargin;
                if (!equalLeftMargin) {
                    this.mLeftMarginAnimator = getOrCreateValueAnimator(this.mLeftMarginAnimator, fromLeftMargin, toLeftMargin, new ValueAnimator.AnimatorUpdateListener() { // from class: com.samsung.android.multiwindow.FreeformResizeGuideView$$ExternalSyntheticLambda0
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                            FreeformResizeGuideView.this.lambda$show$0(dimLp, valueAnimator);
                        }
                    });
                    this.mAnimList.add(this.mLeftMarginAnimator);
                }
                boolean equalTopMargin = fromTopMargin == toTopMargin;
                if (!equalTopMargin) {
                    this.mTopMarginAnimator = getOrCreateValueAnimator(this.mTopMarginAnimator, fromTopMargin, toTopMargin, new ValueAnimator.AnimatorUpdateListener() { // from class: com.samsung.android.multiwindow.FreeformResizeGuideView$$ExternalSyntheticLambda1
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                            FreeformResizeGuideView.this.lambda$show$1(dimLp, valueAnimator);
                        }
                    });
                    this.mAnimList.add(this.mTopMarginAnimator);
                }
                boolean equalWidth = fromWidth == toWidth;
                if (!equalWidth) {
                    this.mWidthAnimator = getOrCreateValueAnimator(this.mWidthAnimator, fromWidth, toWidth, new ValueAnimator.AnimatorUpdateListener() { // from class: com.samsung.android.multiwindow.FreeformResizeGuideView$$ExternalSyntheticLambda2
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                            FreeformResizeGuideView.this.lambda$show$2(dimLp, valueAnimator);
                        }
                    });
                    this.mAnimList.add(this.mWidthAnimator);
                }
                boolean equalHeight = fromHeight == toHeight;
                if (!equalHeight) {
                    this.mHeightAnimator = getOrCreateValueAnimator(this.mHeightAnimator, fromHeight, toHeight, new ValueAnimator.AnimatorUpdateListener() { // from class: com.samsung.android.multiwindow.FreeformResizeGuideView$$ExternalSyntheticLambda3
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                            FreeformResizeGuideView.this.lambda$show$3(dimLp, valueAnimator);
                        }
                    });
                    this.mAnimList.add(this.mHeightAnimator);
                }
                long duration = 300;
                TimeInterpolator interpolator2 = InterpolatorUtils.ONE_EASING;
                if (transitionInfo != null) {
                    long duration2 = transitionInfo.getAnimationDuration(300L);
                    TimeInterpolator interpolator3 = transitionInfo.getInterpolator(interpolator2);
                    int fromAlpha = transitionInfo.getFromAlpha();
                    int toAlpha = transitionInfo.getToAlpha();
                    if (fromAlpha < 0 || toAlpha < 0) {
                        interpolator = interpolator3;
                    } else {
                        interpolator = interpolator3;
                        this.mAlphaAnimator = getOrCreateValueAnimator(this.mAlphaAnimator, fromAlpha, toAlpha, new ValueAnimator.AnimatorUpdateListener() { // from class: com.samsung.android.multiwindow.FreeformResizeGuideView$$ExternalSyntheticLambda4
                            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                                FreeformResizeGuideView.this.lambda$show$4(valueAnimator);
                            }
                        });
                        this.mAnimList.add(this.mAlphaAnimator);
                    }
                    transitionInfo.addDismissListener(this.mAnimatorSet);
                    duration = duration2;
                    interpolator2 = interpolator;
                }
                this.mAnimatorSet.setDuration(duration);
                this.mAnimatorSet.setInterpolator(interpolator2);
                this.mAnimatorSet.playTogether(this.mAnimList);
                this.mAnimatorSet.start();
            }
        }
        this.mDimView.setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$show$0(FrameLayout.LayoutParams dimLp, ValueAnimator animation) {
        dimLp.leftMargin = ((Integer) animation.getAnimatedValue()).intValue();
        this.mDimView.setLayoutParams(dimLp);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$show$1(FrameLayout.LayoutParams dimLp, ValueAnimator animation) {
        dimLp.topMargin = ((Integer) animation.getAnimatedValue()).intValue();
        this.mDimView.setLayoutParams(dimLp);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$show$2(FrameLayout.LayoutParams dimLp, ValueAnimator animation) {
        dimLp.width = ((Integer) animation.getAnimatedValue()).intValue();
        this.mDimView.setLayoutParams(dimLp);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$show$3(FrameLayout.LayoutParams dimLp, ValueAnimator animation) {
        dimLp.height = ((Integer) animation.getAnimatedValue()).intValue();
        this.mDimView.setLayoutParams(dimLp);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$show$4(ValueAnimator animation) {
        int value = ((Integer) animation.getAnimatedValue()).intValue();
        float alpha = value > 0 ? value / 100.0f : 0.0f;
        this.mDimView.setAlpha(alpha);
    }

    void hide() {
        this.mDimView.setVisibility(8);
        this.mAppIconView.setVisibility(8);
    }

    void dismiss() {
        this.mAnimatorSet.cancel();
        this.mAnimList.clear();
        removeAllViews();
    }

    void setDimViewVisibility(int visibility) {
        this.mDimView.setVisibility(visibility);
    }

    void startShowAppIconAnimation() {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(this.mAppIconView, "scaleX", 0.0f, 0.95f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(this.mAppIconView, "scaleY", 0.0f, 0.95f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(InterpolatorUtils.SINE_IN_OUT_60);
        animatorSet.setDuration(300L);
        animatorSet.playTogether(scaleX, scaleY);
        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.samsung.android.multiwindow.FreeformResizeGuideView.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
                FreeformResizeGuideView.this.mAppIconView.setVisibility(0);
                FreeformResizeGuideView.this.mAppIconView.setAlpha(1.0f);
            }
        });
        animatorSet.start();
        ObjectAnimator scaleX2 = ObjectAnimator.ofFloat(this.mAppIconView, "scaleX", 0.95f, 0.9f);
        ObjectAnimator scaleY2 = ObjectAnimator.ofFloat(this.mAppIconView, "scaleY", 0.95f, 0.9f);
        AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.setInterpolator(AnimationUtils.loadInterpolator(this.mContext, R.interpolator.sine_in_out_33));
        animatorSet2.setDuration(300L);
        animatorSet2.setStartDelay(300L);
        animatorSet2.playTogether(scaleX2, scaleY2);
        animatorSet2.start();
    }

    void startHideAppIconAnimation() {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(this.mAppIconView, "scaleX", 0.9f, 0.5f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(this.mAppIconView, "scaleY", 0.9f, 0.5f);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(this.mAppIconView, "alpha", 1.0f, 0.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.setDuration(100L);
        animatorSet.playTogether(scaleX, scaleY, alpha);
        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.samsung.android.multiwindow.FreeformResizeGuideView.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                FreeformResizeGuideView.this.mAppIconView.setVisibility(4);
            }
        });
        animatorSet.start();
    }

    boolean isShowingAppIcon() {
        return this.mAppIconView.getVisibility() == 0;
    }

    private void removeAllUpdateListenersIfNeeded(ValueAnimator... valueAnimators) {
        if (valueAnimators == null || valueAnimators.length == 0) {
            return;
        }
        for (ValueAnimator animator : valueAnimators) {
            if (animator != null) {
                animator.removeAllUpdateListeners();
            }
        }
    }

    private ValueAnimator getOrCreateValueAnimator(ValueAnimator valueAnimator, int from, int to, ValueAnimator.AnimatorUpdateListener listener) {
        if (valueAnimator == null) {
            valueAnimator = new ValueAnimator();
        }
        valueAnimator.setIntValues(from, to);
        valueAnimator.addUpdateListener(listener);
        return valueAnimator;
    }
}
