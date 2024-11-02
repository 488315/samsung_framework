package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.os.Handler;
import android.view.IWindowManager;
import android.view.MotionEvent;
import android.view.accessibility.AccessibilityManager;
import com.android.systemui.BasicRune;
import com.android.systemui.statusbar.AutoHideUiElement;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class AutoHideController {
    public final AccessibilityManager mAccessibilityManager;
    public final AutoHideController$$ExternalSyntheticLambda0 mAutoHide = new AutoHideController$$ExternalSyntheticLambda0(this, 1);
    public boolean mAutoHideSuspended;
    public final int mDisplayId;
    public boolean mGameToolsShown;
    public final Handler mHandler;
    public boolean mIsVisible;
    public AutoHideUiElement mNavigationBar;
    public final AutoHideUiElementObserver mObserver;
    public boolean mShouldHide;
    public AutoHideUiElement mStatusBar;
    public final IWindowManager mWindowManagerService;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public final class AutoHideUiElementObserver extends SystemBarObserver {
        public final List mList;

        public /* synthetic */ AutoHideUiElementObserver(int i) {
            this();
        }

        public final void notify(Consumer consumer) {
            ((ArrayList) this.mList).forEach(new AutoHideController$$ExternalSyntheticLambda1(consumer, 5));
        }

        private AutoHideUiElementObserver() {
            this.mList = new ArrayList();
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public final class Factory {
        public final Handler mHandler;
        public final IWindowManager mIWindowManager;

        public Factory(Handler handler, IWindowManager iWindowManager) {
            this.mHandler = handler;
            this.mIWindowManager = iWindowManager;
        }
    }

    public AutoHideController(Context context, Handler handler, IWindowManager iWindowManager) {
        this.mAccessibilityManager = (AccessibilityManager) context.getSystemService(AccessibilityManager.class);
        this.mHandler = handler;
        this.mWindowManagerService = iWindowManager;
        this.mDisplayId = context.getDisplayId();
        if (BasicRune.NAVBAR_SUPPORT_POLICY_VISIBILITY) {
            this.mObserver = new AutoHideUiElementObserver(0);
        }
    }

    public final void checkUserAutoHide(MotionEvent motionEvent) {
        boolean z;
        if (isAnyTransientBarShown() && motionEvent.getAction() == 4 && motionEvent.getX() == 0.0f && motionEvent.getY() == 0.0f) {
            z = true;
        } else {
            z = false;
        }
        AutoHideUiElement autoHideUiElement = this.mStatusBar;
        if (autoHideUiElement != null) {
            z &= autoHideUiElement.shouldHideOnTouch();
        }
        boolean z2 = BasicRune.NAVBAR_SUPPORT_POLICY_VISIBILITY;
        AccessibilityManager accessibilityManager = this.mAccessibilityManager;
        AutoHideController$$ExternalSyntheticLambda0 autoHideController$$ExternalSyntheticLambda0 = this.mAutoHide;
        Handler handler = this.mHandler;
        if (z2) {
            this.mShouldHide = z;
            this.mObserver.notify(new AutoHideController$$ExternalSyntheticLambda1(this, 3));
            if (this.mShouldHide) {
                this.mAutoHideSuspended = false;
                handler.removeCallbacks(autoHideController$$ExternalSyntheticLambda0);
                handler.postDelayed(autoHideController$$ExternalSyntheticLambda0, accessibilityManager.getRecommendedTimeoutMillis(350, 4));
                return;
            }
            return;
        }
        AutoHideUiElement autoHideUiElement2 = this.mNavigationBar;
        if (autoHideUiElement2 != null) {
            z &= autoHideUiElement2.shouldHideOnTouch();
        }
        if (z) {
            this.mAutoHideSuspended = false;
            handler.removeCallbacks(autoHideController$$ExternalSyntheticLambda0);
            handler.postDelayed(autoHideController$$ExternalSyntheticLambda0, accessibilityManager.getRecommendedTimeoutMillis(350, 4));
        }
    }

    public final AutoHideController$$ExternalSyntheticLambda0 getCheckBarModesRunnable(AutoHideUiElement autoHideUiElement) {
        if (this.mStatusBar != null) {
            return new AutoHideController$$ExternalSyntheticLambda0(this, 0);
        }
        if (autoHideUiElement != null) {
            return new AutoHideController$$ExternalSyntheticLambda0(autoHideUiElement, 2);
        }
        return null;
    }

    public final boolean isAnyTransientBarShown() {
        AutoHideUiElement autoHideUiElement = this.mStatusBar;
        if (autoHideUiElement != null && autoHideUiElement.isVisible()) {
            return true;
        }
        if (BasicRune.NAVBAR_SUPPORT_POLICY_VISIBILITY) {
            this.mIsVisible = false;
            this.mObserver.notify(new AutoHideController$$ExternalSyntheticLambda1(this, 0));
            if (this.mIsVisible) {
                return true;
            }
        }
        AutoHideUiElement autoHideUiElement2 = this.mNavigationBar;
        if (autoHideUiElement2 != null && autoHideUiElement2.isVisible()) {
            return true;
        }
        return false;
    }

    public final void notifyRequestedGameToolsWin(boolean z) {
        this.mGameToolsShown = z;
        if (z) {
            suspendAutoHide();
            return;
        }
        boolean isAnyTransientBarShown = isAnyTransientBarShown();
        this.mAutoHideSuspended = isAnyTransientBarShown;
        if (isAnyTransientBarShown) {
            this.mAutoHideSuspended = false;
            Handler handler = this.mHandler;
            AutoHideController$$ExternalSyntheticLambda0 autoHideController$$ExternalSyntheticLambda0 = this.mAutoHide;
            handler.removeCallbacks(autoHideController$$ExternalSyntheticLambda0);
            handler.postDelayed(autoHideController$$ExternalSyntheticLambda0, this.mAccessibilityManager.getRecommendedTimeoutMillis(350, 4));
            if (BasicRune.NAVBAR_SUPPORT_POLICY_VISIBILITY) {
                this.mObserver.notify(new AutoHideController$$ExternalSyntheticLambda1(this, 4));
            } else {
                AutoHideController$$ExternalSyntheticLambda0 checkBarModesRunnable = getCheckBarModesRunnable(this.mNavigationBar);
                if (checkBarModesRunnable != null) {
                    handler.post(checkBarModesRunnable);
                }
            }
        }
    }

    public final void resumeSuspendedAutoHide() {
        if (this.mAutoHideSuspended) {
            this.mAutoHideSuspended = false;
            Handler handler = this.mHandler;
            AutoHideController$$ExternalSyntheticLambda0 autoHideController$$ExternalSyntheticLambda0 = this.mAutoHide;
            handler.removeCallbacks(autoHideController$$ExternalSyntheticLambda0);
            handler.postDelayed(autoHideController$$ExternalSyntheticLambda0, this.mAccessibilityManager.getRecommendedTimeoutMillis(2250, 4));
            if (BasicRune.NAVBAR_SUPPORT_POLICY_VISIBILITY) {
                this.mObserver.notify(new AutoHideController$$ExternalSyntheticLambda1(this, 2));
            } else {
                AutoHideController$$ExternalSyntheticLambda0 checkBarModesRunnable = getCheckBarModesRunnable(this.mNavigationBar);
                if (checkBarModesRunnable != null) {
                    handler.postDelayed(checkBarModesRunnable, 500L);
                }
            }
        }
    }

    public final void suspendAutoHide() {
        Handler handler = this.mHandler;
        handler.removeCallbacks(this.mAutoHide);
        if (BasicRune.NAVBAR_SUPPORT_POLICY_VISIBILITY) {
            this.mObserver.notify(new AutoHideController$$ExternalSyntheticLambda1(this, 1));
            this.mAutoHideSuspended = isAnyTransientBarShown();
        } else {
            AutoHideController$$ExternalSyntheticLambda0 checkBarModesRunnable = getCheckBarModesRunnable(this.mNavigationBar);
            if (checkBarModesRunnable != null) {
                handler.removeCallbacks(checkBarModesRunnable);
            }
            this.mAutoHideSuspended = isAnyTransientBarShown();
        }
    }

    public final void touchAutoHide() {
        boolean isAnyTransientBarShown = isAnyTransientBarShown();
        Handler handler = this.mHandler;
        AutoHideController$$ExternalSyntheticLambda0 autoHideController$$ExternalSyntheticLambda0 = this.mAutoHide;
        if (isAnyTransientBarShown) {
            this.mAutoHideSuspended = false;
            handler.removeCallbacks(autoHideController$$ExternalSyntheticLambda0);
            handler.postDelayed(autoHideController$$ExternalSyntheticLambda0, this.mAccessibilityManager.getRecommendedTimeoutMillis(2250, 4));
        } else {
            this.mAutoHideSuspended = false;
            handler.removeCallbacks(autoHideController$$ExternalSyntheticLambda0);
        }
    }
}
