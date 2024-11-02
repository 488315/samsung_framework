package com.android.wm.shell.pip.tv;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Handler;
import com.android.systemui.R;
import com.android.wm.shell.pip.PipUtils;
import com.android.wm.shell.pip.tv.TvPipKeepClearAlgorithm;
import com.android.wm.shell.protolog.ShellProtoLogCache;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.protolog.ShellProtoLogImpl;
import java.util.Objects;
import java.util.function.Supplier;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class TvPipBoundsController {
    static final long POSITION_DEBOUNCE_TIMEOUT_MILLIS = 300;
    public final TvPipBoundsController$$ExternalSyntheticLambda1 mApplyPendingPlacementRunnable = new Runnable() { // from class: com.android.wm.shell.pip.tv.TvPipBoundsController$$ExternalSyntheticLambda1
        @Override // java.lang.Runnable
        public final void run() {
            TvPipBoundsController tvPipBoundsController = TvPipBoundsController.this;
            tvPipBoundsController.getClass();
            if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
                ShellProtoLogImpl.d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 638393598, 0, "%s: applyPendingPlacement()", "TvPipBoundsController");
            }
            TvPipKeepClearAlgorithm.Placement placement = tvPipBoundsController.mPendingPlacement;
            if (placement != null) {
                tvPipBoundsController.applyPlacement(placement, tvPipBoundsController.mPendingStash, tvPipBoundsController.mPendingPlacementAnimationDuration);
                tvPipBoundsController.mPendingStash = false;
                tvPipBoundsController.mPendingPlacement = null;
            }
        }
    };
    public final Supplier mClock;
    public Rect mCurrentPlacementBounds;
    public PipBoundsListener mListener;
    public final Handler mMainHandler;
    public TvPipKeepClearAlgorithm.Placement mPendingPlacement;
    public int mPendingPlacementAnimationDuration;
    public boolean mPendingStash;
    public Rect mPipTargetBounds;
    public int mResizeAnimationDuration;
    public int mStashDurationMs;
    public final TvPipBoundsAlgorithm mTvPipBoundsAlgorithm;
    public final TvPipBoundsState mTvPipBoundsState;
    public TvPipBoundsController$$ExternalSyntheticLambda0 mUnstashRunnable;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public interface PipBoundsListener {
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.wm.shell.pip.tv.TvPipBoundsController$$ExternalSyntheticLambda1] */
    public TvPipBoundsController(Context context, Supplier<Long> supplier, Handler handler, TvPipBoundsState tvPipBoundsState, TvPipBoundsAlgorithm tvPipBoundsAlgorithm) {
        this.mClock = supplier;
        this.mMainHandler = handler;
        this.mTvPipBoundsState = tvPipBoundsState;
        this.mTvPipBoundsAlgorithm = tvPipBoundsAlgorithm;
        Resources resources = context.getResources();
        this.mResizeAnimationDuration = resources.getInteger(R.integer.config_pipResizeAnimationDuration);
        this.mStashDurationMs = resources.getInteger(R.integer.config_pipStashDuration);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r8v3, types: [java.lang.Runnable, com.android.wm.shell.pip.tv.TvPipBoundsController$$ExternalSyntheticLambda0] */
    public final void applyPlacement(final TvPipKeepClearAlgorithm.Placement placement, boolean z, int i) {
        int i2 = placement.stashType;
        Rect rect = placement.unstashDestinationBounds;
        if (i2 != 0 && z) {
            TvPipBoundsController$$ExternalSyntheticLambda0 tvPipBoundsController$$ExternalSyntheticLambda0 = this.mUnstashRunnable;
            Handler handler = this.mMainHandler;
            if (tvPipBoundsController$$ExternalSyntheticLambda0 != null) {
                handler.removeCallbacks(tvPipBoundsController$$ExternalSyntheticLambda0);
                this.mUnstashRunnable = null;
            }
            if (rect != null) {
                ?? r8 = new Runnable() { // from class: com.android.wm.shell.pip.tv.TvPipBoundsController$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        TvPipBoundsController tvPipBoundsController = TvPipBoundsController.this;
                        TvPipKeepClearAlgorithm.Placement placement2 = placement;
                        tvPipBoundsController.getClass();
                        tvPipBoundsController.applyPlacementBounds(tvPipBoundsController.mResizeAnimationDuration, placement2.unstashDestinationBounds);
                        tvPipBoundsController.mUnstashRunnable = null;
                    }
                };
                this.mUnstashRunnable = r8;
                handler.postAtTime(r8, ((Long) this.mClock.get()).longValue() + this.mStashDurationMs);
            }
        }
        TvPipBoundsController$$ExternalSyntheticLambda0 tvPipBoundsController$$ExternalSyntheticLambda02 = this.mUnstashRunnable;
        Rect rect2 = placement.bounds;
        if (tvPipBoundsController$$ExternalSyntheticLambda02 == null) {
            if (rect == null) {
                rect = rect2;
            }
            rect2 = rect;
        }
        applyPlacementBounds(i, rect2);
    }

    public final void applyPlacementBounds(int i, Rect rect) {
        int i2;
        int i3;
        if (rect == null) {
            return;
        }
        this.mCurrentPlacementBounds = rect;
        Rect adjustBoundsForTemporaryDecor = this.mTvPipBoundsAlgorithm.adjustBoundsForTemporaryDecor(rect);
        if (!Objects.equals(this.mPipTargetBounds, adjustBoundsForTemporaryDecor)) {
            this.mPipTargetBounds = adjustBoundsForTemporaryDecor;
            boolean z = false;
            if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
                ShellProtoLogImpl.d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 1496564310, 0, "%s: movePipTo() - new pip bounds: %s", "TvPipBoundsController", String.valueOf(adjustBoundsForTemporaryDecor.toShortString()));
            }
            PipBoundsListener pipBoundsListener = this.mListener;
            if (pipBoundsListener != null) {
                TvPipController tvPipController = (TvPipController) pipBoundsListener;
                tvPipController.mPipTaskOrganizer.scheduleAnimateResizePip(adjustBoundsForTemporaryDecor, i, 0);
                TvPipMenuView tvPipMenuView = tvPipController.mTvPipMenuController.mPipMenuView;
                if (tvPipMenuView != null) {
                    if (tvPipMenuView.mCurrentPipBounds != null && PipUtils.aspectRatioChanged(r7.width() / tvPipMenuView.mCurrentPipBounds.height(), adjustBoundsForTemporaryDecor.width() / adjustBoundsForTemporaryDecor.height())) {
                        tvPipMenuView.mPipBackground.animate().alpha(1.0f).setInterpolator(TvPipInterpolators.EXIT).setDuration(tvPipMenuView.mResizeAnimationDuration / 2).start();
                    }
                    if (adjustBoundsForTemporaryDecor.height() > adjustBoundsForTemporaryDecor.width()) {
                        i2 = 1;
                    } else {
                        i2 = 0;
                    }
                    if (tvPipMenuView.mButtonLayoutManager.getOrientation() == 1) {
                        i3 = 1;
                    } else {
                        i3 = 0;
                    }
                    if (i2 != i3) {
                        z = true;
                    }
                    if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
                        ShellProtoLogImpl.d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -236872543, 12, "%s: onPipTransitionToTargetBoundsStarted(), orientation changed %b", "TvPipMenuView", Boolean.valueOf(z));
                    }
                    if (z) {
                        if (tvPipMenuView.mCurrentMenuMode == 2) {
                            tvPipMenuView.mActionButtonsRecyclerView.animate().alpha(0.0f).setInterpolator(TvPipInterpolators.EXIT).setDuration(tvPipMenuView.mResizeAnimationDuration / 2);
                        } else {
                            tvPipMenuView.mButtonLayoutManager.setOrientation(i2);
                        }
                    }
                }
            }
        }
    }

    public final void cancelScheduledPlacement() {
        Handler handler = this.mMainHandler;
        handler.removeCallbacks(this.mApplyPendingPlacementRunnable);
        this.mPendingPlacement = null;
        TvPipBoundsController$$ExternalSyntheticLambda0 tvPipBoundsController$$ExternalSyntheticLambda0 = this.mUnstashRunnable;
        if (tvPipBoundsController$$ExternalSyntheticLambda0 != null) {
            handler.removeCallbacks(tvPipBoundsController$$ExternalSyntheticLambda0);
            this.mUnstashRunnable = null;
        }
    }

    public void recalculatePipBounds(boolean z, boolean z2, int i, boolean z3) {
        int i2;
        TvPipKeepClearAlgorithm.Placement tvPipPlacement = this.mTvPipBoundsAlgorithm.getTvPipPlacement();
        boolean z4 = false;
        int i3 = tvPipPlacement.stashType;
        if (z2) {
            i2 = 0;
        } else {
            i2 = i3;
        }
        this.mTvPipBoundsState.setStashed(i2, false);
        if (z) {
            cancelScheduledPlacement();
            applyPlacementBounds(i, tvPipPlacement.anchorBounds);
            return;
        }
        Rect rect = tvPipPlacement.bounds;
        if (z2) {
            cancelScheduledPlacement();
            Rect rect2 = tvPipPlacement.unstashDestinationBounds;
            if (rect2 != null) {
                rect = rect2;
            }
            applyPlacementBounds(i, rect);
            return;
        }
        boolean z5 = tvPipPlacement.triggerStash;
        if (z3) {
            if (this.mUnstashRunnable != null || z5) {
                z4 = true;
            }
            cancelScheduledPlacement();
            applyPlacement(tvPipPlacement, z4, i);
            return;
        }
        Rect rect3 = this.mCurrentPlacementBounds;
        if (rect3 != null) {
            applyPlacementBounds(i, rect3);
        }
        if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
            ShellProtoLogImpl.d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -104893959, 0, "%s: schedulePinnedStackPlacement() - pip bounds: %s", "TvPipBoundsController", String.valueOf(rect.toShortString()));
        }
        TvPipKeepClearAlgorithm.Placement placement = this.mPendingPlacement;
        if (placement != null && Objects.equals(placement.bounds, rect)) {
            if (this.mPendingStash || z5) {
                z4 = true;
            }
            this.mPendingStash = z4;
            return;
        }
        if (i3 != 0 && (this.mPendingStash || z5)) {
            z4 = true;
        }
        this.mPendingStash = z4;
        Handler handler = this.mMainHandler;
        TvPipBoundsController$$ExternalSyntheticLambda1 tvPipBoundsController$$ExternalSyntheticLambda1 = this.mApplyPendingPlacementRunnable;
        handler.removeCallbacks(tvPipBoundsController$$ExternalSyntheticLambda1);
        this.mPendingPlacement = tvPipPlacement;
        this.mPendingPlacementAnimationDuration = i;
        handler.postAtTime(tvPipBoundsController$$ExternalSyntheticLambda1, ((Long) this.mClock.get()).longValue() + POSITION_DEBOUNCE_TIMEOUT_MILLIS);
    }
}
