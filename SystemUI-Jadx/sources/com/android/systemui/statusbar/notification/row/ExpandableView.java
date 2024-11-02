package com.android.systemui.statusbar.notification.row;

import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.statusbar.NotificationShelf;
import com.android.systemui.statusbar.StatusBarIconView;
import com.android.systemui.statusbar.notification.Roundable;
import com.android.systemui.statusbar.notification.RoundableState;
import com.android.systemui.statusbar.notification.stack.ExpandableViewState;
import com.android.systemui.statusbar.notification.stack.ViewState;
import com.android.systemui.util.DumpUtilsKt;
import com.samsung.android.nexus.video.VideoPlayer;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public abstract class ExpandableView extends FrameLayout implements Dumpable, Roundable {
    public static final Rect mClipRect = new Rect();
    public int mActualHeight;
    public boolean mChangingPosition;
    public int mClipBottomAmount;
    public boolean mClipToActualHeight;
    public int mClipTopAmount;
    public int mContentShift;
    public float mContentTransformationAmount;
    public float mContentTranslation;
    public float mExtraWidthForClipping;
    public boolean mInShelf;
    public boolean mIsLastChild;
    public final ArrayList mMatchParentViews;
    public int mMinimumHeightForClipping;
    public OnHeightChangedListener mOnHeightChangedListener;
    public RoundableState mRoundableState;
    public boolean mTransformingInShelf;
    public ViewGroup mTransientContainer;
    public final ExpandableViewState mViewState;
    public boolean mWillBeGone;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public interface OnHeightChangedListener {
        void onHeightChanged(ExpandableView expandableView, boolean z);

        void onReset(ExpandableNotificationRow expandableNotificationRow);
    }

    public ExpandableView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mRoundableState = null;
        this.mMinimumHeightForClipping = 0;
        this.mExtraWidthForClipping = 0.0f;
        this.mMatchParentViews = new ArrayList();
        this.mClipToActualHeight = true;
        this.mChangingPosition = false;
        this.mViewState = createExpandableViewState();
        this.mContentShift = getResources().getDimensionPixelSize(R.dimen.shelf_transform_content_shift);
    }

    public boolean areChildrenExpanded() {
        return false;
    }

    public ExpandableViewState createExpandableViewState() {
        return new ExpandableViewState();
    }

    public void dump(PrintWriter printWriter, final String[] strArr) {
        final IndentingPrintWriter asIndenting = DumpUtilsKt.asIndenting(printWriter);
        asIndenting.println(getClass().getSimpleName());
        DumpUtilsKt.withIncreasedIndent(asIndenting, new Runnable() { // from class: com.android.systemui.statusbar.notification.row.ExpandableView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ExpandableView expandableView = ExpandableView.this;
                PrintWriter printWriter2 = asIndenting;
                String[] strArr2 = strArr;
                ExpandableViewState expandableViewState = expandableView.mViewState;
                if (expandableViewState == null) {
                    printWriter2.println("no viewState!!!");
                } else {
                    expandableViewState.dump(printWriter2, strArr2);
                    printWriter2.println();
                }
            }
        });
    }

    public void getBoundsOnScreen(Rect rect, boolean z) {
        super.getBoundsOnScreen(rect, z);
        if (getTranslationY() + getTop() < 0.0f) {
            rect.top = (int) (getTranslationY() + getTop() + rect.top);
        }
        int i = rect.top;
        rect.bottom = this.mActualHeight + i;
        rect.top = Math.max(0, this.mClipTopAmount) + i;
    }

    public int getCollapsedHeight() {
        return getHeight();
    }

    @Override // android.view.View
    public final void getDrawingRect(Rect rect) {
        super.getDrawingRect(rect);
        rect.left = (int) (getTranslationX() + rect.left);
        rect.right = (int) (getTranslationX() + rect.right);
        rect.bottom = (int) (getTranslationY() + rect.top + this.mActualHeight);
        rect.top = (int) (getTranslationY() + this.mClipTopAmount + rect.top);
    }

    public float getHeaderVisibleAmount() {
        return 1.0f;
    }

    public int getHeadsUpHeightWithoutHeader() {
        return getHeight();
    }

    public int getHeightWithoutLockscreenConstraints() {
        return getHeight();
    }

    public int getIntrinsicHeight() {
        return getHeight();
    }

    public int getMaxContentHeight() {
        return getHeight();
    }

    public int getMinHeight(boolean z) {
        return getHeight();
    }

    public float getOutlineAlpha() {
        return 0.0f;
    }

    public int getOutlineTranslation() {
        return 0;
    }

    public int getPinnedHeadsUpHeight() {
        return getIntrinsicHeight();
    }

    public final int getRelativeTopPadding(View view) {
        int i = 0;
        while (view.getParent() instanceof ViewGroup) {
            i += view.getTop();
            view = (View) view.getParent();
            if (view == this) {
                break;
            }
        }
        return i;
    }

    public RoundableState getRoundableState() {
        if (this.mRoundableState == null) {
            this.mRoundableState = new RoundableState(this, this, 0.0f);
        }
        return this.mRoundableState;
    }

    public StatusBarIconView getShelfIcon() {
        return null;
    }

    public View getShelfTransformationTarget() {
        return null;
    }

    public float getTranslation() {
        return getTranslationX();
    }

    public boolean hasExpandingChild() {
        return false;
    }

    public boolean hasNoContentHeight() {
        return false;
    }

    @Override // android.view.View
    public boolean hasOverlappingRendering() {
        if (super.hasOverlappingRendering() && this.mActualHeight <= getHeight()) {
            return true;
        }
        return false;
    }

    public boolean isAboveShelf() {
        return false;
    }

    public boolean isChildInGroup() {
        return false;
    }

    public boolean isContentExpandable() {
        return false;
    }

    public boolean isExpandAnimationRunning() {
        return false;
    }

    public boolean isGroupExpanded() {
        return false;
    }

    public boolean isGroupExpansionChanging() {
        return false;
    }

    public boolean isHeadsUpAnimatingAway() {
        return false;
    }

    public boolean isPinned() {
        return false;
    }

    public boolean isSummaryWithChildren() {
        return false;
    }

    public boolean isTransparent() {
        return false;
    }

    public boolean mustStayOnScreen() {
        return false;
    }

    public boolean needsClippingToShelf() {
        return !(this instanceof NotificationShelf);
    }

    public void notifyHeightChanged(boolean z) {
        OnHeightChangedListener onHeightChangedListener = this.mOnHeightChangedListener;
        if (onHeightChangedListener != null) {
            onHeightChangedListener.onHeightChanged(this, z);
        }
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mContentShift = getResources().getDimensionPixelSize(R.dimen.shelf_transform_content_shift);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        updateClipping();
    }

    @Override // android.widget.FrameLayout, android.view.View
    public void onMeasure(int i, int i2) {
        int i3;
        int size = View.MeasureSpec.getSize(i2);
        int paddingEnd = getPaddingEnd() + getPaddingStart();
        int mode = View.MeasureSpec.getMode(i2);
        int i4 = Integer.MAX_VALUE;
        if (mode != 0 && size != 0) {
            i4 = Math.min(size, Integer.MAX_VALUE);
        }
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(i4, VideoPlayer.MEDIA_ERROR_SYSTEM);
        int childCount = getChildCount();
        int i5 = 0;
        for (int i6 = 0; i6 < childCount; i6++) {
            View childAt = getChildAt(i6);
            if (childAt.getVisibility() != 8) {
                ViewGroup.LayoutParams layoutParams = childAt.getLayoutParams();
                int i7 = layoutParams.height;
                if (i7 != -1) {
                    if (i7 >= 0) {
                        i3 = View.MeasureSpec.makeMeasureSpec(Math.min(i7, i4), VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
                    } else {
                        i3 = makeMeasureSpec;
                    }
                    childAt.measure(FrameLayout.getChildMeasureSpec(i, paddingEnd, layoutParams.width), i3);
                    i5 = Math.max(i5, childAt.getMeasuredHeight());
                } else {
                    this.mMatchParentViews.add(childAt);
                }
            }
        }
        if (mode != 1073741824) {
            size = Math.min(i4, i5);
        }
        int makeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(size, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
        Iterator it = this.mMatchParentViews.iterator();
        while (it.hasNext()) {
            View view = (View) it.next();
            view.measure(FrameLayout.getChildMeasureSpec(i, paddingEnd, view.getLayoutParams().width), makeMeasureSpec2);
        }
        this.mMatchParentViews.clear();
        setMeasuredDimension(View.MeasureSpec.getSize(i), size);
    }

    public void performAddAnimation(long j, long j2) {
        performAddAnimation(j, j2, false);
    }

    public abstract void performAddAnimation(long j, long j2, boolean z);

    public abstract long performRemoveAnimation(long j, long j2, float f, boolean z, float f2, Runnable runnable, AnimatorListenerAdapter animatorListenerAdapter);

    public boolean pointInView(float f, float f2, float f3) {
        float max = Math.max(0, this.mClipTopAmount);
        float f4 = this.mActualHeight;
        if (f < (-f3) || f2 < max - f3 || f >= (((FrameLayout) this).mRight - ((FrameLayout) this).mLeft) + f3 || f2 >= f4 + f3) {
            return false;
        }
        return true;
    }

    public final void removeFromTransientContainer() {
        ViewGroup viewGroup = this.mTransientContainer;
        if (viewGroup == null) {
            return;
        }
        ViewParent parent = getParent();
        if (parent != viewGroup) {
            Log.w("ExpandableView", "Expandable view " + this + " has transient container " + viewGroup + " but different parent " + parent);
            this.mTransientContainer = null;
            return;
        }
        viewGroup.removeTransientView(this);
        this.mTransientContainer = null;
    }

    public final void removeFromTransientContainerForAdditionTo(ViewGroup viewGroup) {
        ViewParent parent = getParent();
        ViewGroup viewGroup2 = this.mTransientContainer;
        if (parent != null && parent != viewGroup) {
            if (viewGroup2 != null) {
                if (viewGroup2 == parent) {
                    Log.w("ExpandableView", "Removing view " + this + " from transient container " + viewGroup2 + " in preparation for moving to parent " + viewGroup);
                    viewGroup2.removeTransientView(this);
                    this.mTransientContainer = null;
                    return;
                }
                throw new IllegalStateException("Expandable view " + this + " has transient container " + viewGroup2 + " but different parent " + parent);
            }
            throw new IllegalStateException("Can't add view " + this + " to container " + viewGroup + "; current parent " + parent + " is not a transient container");
        }
        removeFromTransientContainer();
    }

    public final void resetViewState() {
        boolean z;
        this.mViewState.height = getIntrinsicHeight();
        ExpandableViewState expandableViewState = this.mViewState;
        if (getVisibility() == 8) {
            z = true;
        } else {
            z = false;
        }
        expandableViewState.gone = z;
        this.mViewState.setAlpha(1.0f);
        ExpandableViewState expandableViewState2 = this.mViewState;
        expandableViewState2.notGoneIndex = -1;
        expandableViewState2.setXTranslation(getTranslationX());
        ExpandableViewState expandableViewState3 = this.mViewState;
        expandableViewState3.hidden = false;
        float scaleX = getScaleX();
        if (ViewState.isValidFloat(scaleX, "scaleX")) {
            expandableViewState3.mScaleX = scaleX;
        }
        ExpandableViewState expandableViewState4 = this.mViewState;
        float scaleY = getScaleY();
        expandableViewState4.getClass();
        if (ViewState.isValidFloat(scaleY, "scaleY")) {
            expandableViewState4.mScaleY = scaleY;
        }
        ExpandableViewState expandableViewState5 = this.mViewState;
        expandableViewState5.inShelf = false;
        expandableViewState5.headsUpIsVisible = false;
        expandableViewState5.isAnimatable = true;
        if (this instanceof ExpandableNotificationRow) {
            ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) this;
            List attachedChildren = expandableNotificationRow.getAttachedChildren();
            if (expandableNotificationRow.mIsSummaryWithChildren && attachedChildren != null) {
                Iterator it = attachedChildren.iterator();
                while (it.hasNext()) {
                    ((ExpandableNotificationRow) it.next()).resetViewState();
                }
            }
        }
    }

    public void setActualHeight(int i, boolean z) {
        if (this.mActualHeight != i) {
            this.mActualHeight = i;
            updateClipping();
            if (z) {
                notifyHeightChanged(false);
            }
        }
    }

    public void setClipBottomAmount(int i) {
        this.mClipBottomAmount = i;
        updateClipping();
    }

    public void setClipTopAmount(int i) {
        this.mClipTopAmount = i;
        updateClipping();
    }

    @Override // android.view.View
    public final void setLayerType(int i, Paint paint) {
        if (i == 0 || hasOverlappingRendering()) {
            super.setLayerType(i, paint);
        }
    }

    public boolean shouldClipToActualHeight() {
        return true;
    }

    public boolean showingPulsing() {
        return false;
    }

    public void updateClipping() {
        if (this.mClipToActualHeight && shouldClipToActualHeight()) {
            int i = this.mClipTopAmount;
            int max = Math.max(Math.max(this.mActualHeight - this.mClipBottomAmount, i), this.mMinimumHeightForClipping);
            Rect rect = mClipRect;
            rect.set(VideoPlayer.MEDIA_ERROR_SYSTEM, i, Integer.MAX_VALUE, max);
            setClipBounds(rect);
            return;
        }
        setClipBounds(null);
    }

    public void setActualHeightAnimating(boolean z) {
    }

    public void setBelowSpeedBump(boolean z) {
    }

    public void setDistanceToTopRoundness(float f) {
    }

    public void setHideSensitiveForIntrinsicHeight(boolean z) {
    }

    public void setHeadsUpIsVisible() {
    }

    public void setDimmed(boolean z, boolean z2) {
    }

    public void setFakeShadowIntensity(int i, float f, float f2, int i2) {
    }

    public void setHideSensitive(boolean z, boolean z2, long j, long j2) {
    }
}
