package com.android.internal.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Property;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ActionMenuPresenter;
import android.widget.ActionMenuView;
import com.android.internal.R;

/* loaded from: classes5.dex */
public abstract class AbsActionBarView extends ViewGroup {
    private static final int FADE_DURATION = 200;
    private static final TimeInterpolator sAlphaInterpolator = new DecelerateInterpolator();
    protected ActionMenuPresenter mActionMenuPresenter;
    protected int mContentHeight;
    private boolean mEatingHover;
    private boolean mEatingTouch;
    protected boolean mIsThemeDeviceDefaultFamily;
    protected ActionMenuView mMenuView;
    protected final Context mPopupContext;
    protected boolean mSplitActionBar;
    protected ViewGroup mSplitView;
    protected boolean mSplitWhenNarrow;
    protected final VisibilityAnimListener mVisAnimListener;
    protected Animator mVisibilityAnim;

    public AbsActionBarView(Context context) {
        this(context, null);
    }

    public AbsActionBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AbsActionBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public AbsActionBarView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mVisAnimListener = new VisibilityAnimListener();
        TypedValue tv = new TypedValue();
        if (context.getTheme().resolveAttribute(16843917, tv, true) && tv.resourceId != 0) {
            this.mPopupContext = new ContextThemeWrapper(context, tv.resourceId);
        } else {
            this.mPopupContext = context;
        }
        TypedValue outValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.parentIsDeviceDefault, outValue, true);
        this.mIsThemeDeviceDefaultFamily = outValue.data != 0;
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        TypedArray a = getContext().obtainStyledAttributes(null, R.styleable.ActionBar, 16843470, 0);
        setContentHeight(a.getLayoutDimension(4, 0));
        a.recycle();
        if (this.mSplitWhenNarrow) {
            setSplitToolbar(getContext().getResources().getBoolean(R.bool.split_action_bar_is_narrow));
        }
        if (this.mActionMenuPresenter != null) {
            this.mActionMenuPresenter.onConfigurationChanged(newConfig);
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getActionMasked();
        if (action == 0) {
            this.mEatingTouch = false;
        }
        if (!this.mEatingTouch) {
            boolean handled = super.onTouchEvent(ev);
            if (action == 0 && !handled) {
                this.mEatingTouch = true;
            }
        }
        if (action == 1 || action == 3) {
            this.mEatingTouch = false;
        }
        return true;
    }

    @Override // android.view.View
    public boolean onHoverEvent(MotionEvent ev) {
        int action = ev.getActionMasked();
        if (action == 9) {
            this.mEatingHover = false;
        }
        if (!this.mEatingHover) {
            boolean handled = super.onHoverEvent(ev);
            if (action == 9 && !handled) {
                this.mEatingHover = true;
            }
        }
        if (action == 10 || action == 3) {
            this.mEatingHover = false;
        }
        return true;
    }

    public void setSplitToolbar(boolean split) {
        this.mSplitActionBar = split;
    }

    public void setSplitWhenNarrow(boolean splitWhenNarrow) {
        this.mSplitWhenNarrow = splitWhenNarrow;
    }

    public void setContentHeight(int height) {
        this.mContentHeight = height;
        requestLayout();
    }

    public int getContentHeight() {
        return this.mContentHeight;
    }

    public void setSplitView(ViewGroup splitView) {
        this.mSplitView = splitView;
    }

    public int getAnimatedVisibility() {
        if (this.mVisibilityAnim != null) {
            return this.mVisAnimListener.mFinalVisibility;
        }
        return getVisibility();
    }

    public Animator setupAnimatorToVisibility(int visibility, long duration) {
        if (this.mVisibilityAnim != null) {
            this.mVisibilityAnim.cancel();
        }
        if (visibility == 0) {
            if (getVisibility() != 0) {
                setAlpha(0.0f);
                if (this.mSplitView != null && this.mMenuView != null) {
                    this.mMenuView.setAlpha(0.0f);
                }
            }
            ObjectAnimator anim = ObjectAnimator.ofFloat(this, (Property<AbsActionBarView, Float>) View.ALPHA, 1.0f);
            anim.setDuration(duration);
            anim.setInterpolator(sAlphaInterpolator);
            if (this.mSplitView != null && this.mMenuView != null) {
                AnimatorSet set = new AnimatorSet();
                ObjectAnimator splitAnim = ObjectAnimator.ofFloat(this.mMenuView, (Property<ActionMenuView, Float>) View.ALPHA, 1.0f);
                splitAnim.setDuration(duration);
                set.addListener(this.mVisAnimListener.withFinalVisibility(visibility));
                set.play(anim).with(splitAnim);
                return set;
            }
            anim.addListener(this.mVisAnimListener.withFinalVisibility(visibility));
            return anim;
        }
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(this, (Property<AbsActionBarView, Float>) View.ALPHA, 0.0f);
        anim2.setDuration(duration);
        anim2.setInterpolator(sAlphaInterpolator);
        if (this.mSplitView != null && this.mMenuView != null) {
            AnimatorSet set2 = new AnimatorSet();
            ObjectAnimator splitAnim2 = ObjectAnimator.ofFloat(this.mMenuView, (Property<ActionMenuView, Float>) View.ALPHA, 0.0f);
            splitAnim2.setDuration(duration);
            set2.addListener(this.mVisAnimListener.withFinalVisibility(visibility));
            set2.play(anim2).with(splitAnim2);
            return set2;
        }
        anim2.addListener(this.mVisAnimListener.withFinalVisibility(visibility));
        return anim2;
    }

    public void animateToVisibility(int visibility) {
        Animator anim = setupAnimatorToVisibility(visibility, 200L);
        anim.start();
    }

    @Override // android.view.View
    public void setVisibility(int visibility) {
        if (visibility != getVisibility()) {
            if (this.mVisibilityAnim != null) {
                this.mVisibilityAnim.end();
            }
            super.setVisibility(visibility);
        }
    }

    public boolean showOverflowMenu() {
        if (this.mActionMenuPresenter != null) {
            return this.mActionMenuPresenter.showOverflowMenu();
        }
        return false;
    }

    public void postShowOverflowMenu() {
        post(new Runnable() { // from class: com.android.internal.widget.AbsActionBarView.1
            @Override // java.lang.Runnable
            public void run() {
                AbsActionBarView.this.showOverflowMenu();
            }
        });
    }

    public boolean hideOverflowMenu() {
        if (this.mActionMenuPresenter != null) {
            return this.mActionMenuPresenter.hideOverflowMenu();
        }
        return false;
    }

    public boolean isOverflowMenuShowing() {
        if (this.mActionMenuPresenter != null) {
            return this.mActionMenuPresenter.isOverflowMenuShowing();
        }
        return false;
    }

    public boolean isOverflowMenuShowPending() {
        if (this.mActionMenuPresenter != null) {
            return this.mActionMenuPresenter.isOverflowMenuShowPending();
        }
        return false;
    }

    public boolean isOverflowReserved() {
        return this.mActionMenuPresenter != null && this.mActionMenuPresenter.isOverflowReserved();
    }

    public boolean canShowOverflowMenu() {
        return isOverflowReserved() && getVisibility() == 0;
    }

    public void dismissPopupMenus() {
        if (this.mActionMenuPresenter != null) {
            this.mActionMenuPresenter.dismissPopupMenus();
        }
    }

    protected int measureChildView(View child, int availableWidth, int childSpecHeight, int spacing) {
        child.measure(View.MeasureSpec.makeMeasureSpec(availableWidth, Integer.MIN_VALUE), childSpecHeight);
        return Math.max(0, (availableWidth - child.getMeasuredWidth()) - spacing);
    }

    protected static int next(int x, int val, boolean isRtl) {
        return isRtl ? x - val : x + val;
    }

    protected int positionChild(View child, int x, int y, int contentHeight, boolean reverse) {
        int childWidth = child.getMeasuredWidth();
        int childHeight = child.getMeasuredHeight();
        int childTop = ((contentHeight - childHeight) / 2) + y;
        if (reverse) {
            child.layout(x - childWidth, childTop, x, childTop + childHeight);
        } else {
            child.layout(x, childTop, x + childWidth, childTop + childHeight);
        }
        return reverse ? -childWidth : childWidth;
    }

    protected class VisibilityAnimListener implements Animator.AnimatorListener {
        private boolean mCanceled = false;
        int mFinalVisibility;

        protected VisibilityAnimListener() {
        }

        public VisibilityAnimListener withFinalVisibility(int visibility) {
            this.mFinalVisibility = visibility;
            return this;
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationStart(Animator animation) {
            AbsActionBarView.this.setVisibility(0);
            AbsActionBarView.this.mVisibilityAnim = animation;
            this.mCanceled = false;
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animation) {
            if (this.mCanceled) {
                return;
            }
            AbsActionBarView.this.mVisibilityAnim = null;
            AbsActionBarView.this.setVisibility(this.mFinalVisibility);
            if (AbsActionBarView.this.mSplitView != null && AbsActionBarView.this.mMenuView != null) {
                AbsActionBarView.this.mMenuView.setVisibility(this.mFinalVisibility);
            }
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationCancel(Animator animation) {
            this.mCanceled = true;
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationRepeat(Animator animation) {
        }
    }
}
