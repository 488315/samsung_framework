package com.android.wm.shell.compatui;

import android.graphics.Rect;
import android.os.Binder;
import android.os.Debug;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import com.android.systemui.R;
import com.samsung.android.widget.SemTipPopup;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public enum BoundsCompatUIUtil$TipPopupAdapter {
    INSTANCE;

    public static final String TAG = BoundsCompatUIUtil$TipPopupAdapter.class.getSimpleName();
    private View.OnAttachStateChangeListener mAttachStateChangeListener = new View.OnAttachStateChangeListener() { // from class: com.android.wm.shell.compatui.BoundsCompatUIUtil$TipPopupAdapter.1
        public AnonymousClass1() {
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public final void onViewAttachedToWindow(View view) {
            String str = BoundsCompatUIUtil$TipPopupAdapter.TAG;
            Log.d(str, "onViewAttachedToWindow: v=" + view);
            if (view.equals(BoundsCompatUIUtil$TipPopupAdapter.this.mViewHost)) {
                BoundsCompatUIUtil$TipPopupAdapter.m2458$$Nest$mshowTipPopup(BoundsCompatUIUtil$TipPopupAdapter.this);
                return;
            }
            Log.e(str, "HostView is not matched with the view attached, hostView=" + BoundsCompatUIUtil$TipPopupAdapter.this.mViewHost);
            BoundsCompatUIUtil$TipPopupAdapter.this.dismissTipPopup();
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public final void onViewDetachedFromWindow(View view) {
            Log.d(BoundsCompatUIUtil$TipPopupAdapter.TAG, "onViewDetachedFromWindow: v=" + view);
        }
    };
    private BoundsCompatUILayout mBoundsCompatUILayout;
    private CharSequence mMessage;
    private SemTipPopup mSemTipPopup;
    private View mViewHost;
    private WindowManager mWindowManager;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* renamed from: com.android.wm.shell.compatui.BoundsCompatUIUtil$TipPopupAdapter$1 */
    /* loaded from: classes2.dex */
    public final class AnonymousClass1 implements View.OnAttachStateChangeListener {
        public AnonymousClass1() {
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public final void onViewAttachedToWindow(View view) {
            String str = BoundsCompatUIUtil$TipPopupAdapter.TAG;
            Log.d(str, "onViewAttachedToWindow: v=" + view);
            if (view.equals(BoundsCompatUIUtil$TipPopupAdapter.this.mViewHost)) {
                BoundsCompatUIUtil$TipPopupAdapter.m2458$$Nest$mshowTipPopup(BoundsCompatUIUtil$TipPopupAdapter.this);
                return;
            }
            Log.e(str, "HostView is not matched with the view attached, hostView=" + BoundsCompatUIUtil$TipPopupAdapter.this.mViewHost);
            BoundsCompatUIUtil$TipPopupAdapter.this.dismissTipPopup();
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public final void onViewDetachedFromWindow(View view) {
            Log.d(BoundsCompatUIUtil$TipPopupAdapter.TAG, "onViewDetachedFromWindow: v=" + view);
        }
    }

    /* renamed from: -$$Nest$mbuild */
    public static BoundsCompatUIUtil$TipPopupAdapter m2457$$Nest$mbuild(BoundsCompatUIUtil$TipPopupAdapter boundsCompatUIUtil$TipPopupAdapter, BoundsCompatUIUtil$TipPopupBuilder boundsCompatUIUtil$TipPopupBuilder) {
        boundsCompatUIUtil$TipPopupAdapter.getClass();
        BoundsCompatUILayout boundsCompatUILayout = boundsCompatUIUtil$TipPopupBuilder.mBoundsCompatUILayout;
        String str = TAG;
        if (boundsCompatUILayout == null) {
            Log.e(str, "build: no layout, something went wrong.");
        } else {
            if (boundsCompatUIUtil$TipPopupAdapter.mWindowManager == null) {
                boundsCompatUIUtil$TipPopupAdapter.mWindowManager = (WindowManager) boundsCompatUILayout.getContext().getSystemService("window");
            }
            if (!boundsCompatUILayout.equals(boundsCompatUIUtil$TipPopupAdapter.mBoundsCompatUILayout)) {
                boundsCompatUIUtil$TipPopupAdapter.mBoundsCompatUILayout = boundsCompatUILayout;
            }
            boundsCompatUIUtil$TipPopupAdapter.mMessage = boundsCompatUIUtil$TipPopupBuilder.mMessage;
            View view = boundsCompatUIUtil$TipPopupAdapter.mViewHost;
            if (view != null && view.isAttachedToWindow()) {
                Log.d(str, "build: removed remained host, " + boundsCompatUIUtil$TipPopupAdapter.mViewHost);
                boundsCompatUIUtil$TipPopupAdapter.mWindowManager.removeViewImmediate(boundsCompatUIUtil$TipPopupAdapter.mViewHost);
            }
            View inflate = LayoutInflater.from(boundsCompatUIUtil$TipPopupAdapter.mBoundsCompatUILayout.getContext()).inflate(R.layout.bounds_compat_ui_tip, (ViewGroup) null);
            boundsCompatUIUtil$TipPopupAdapter.mViewHost = inflate;
            inflate.addOnAttachStateChangeListener(boundsCompatUIUtil$TipPopupAdapter.mAttachStateChangeListener);
        }
        return INSTANCE;
    }

    /* renamed from: -$$Nest$mshowTipPopup */
    public static void m2458$$Nest$mshowTipPopup(BoundsCompatUIUtil$TipPopupAdapter boundsCompatUIUtil$TipPopupAdapter) {
        FrameLayout frameLayout;
        BoundsCompatUILayout boundsCompatUILayout = boundsCompatUIUtil$TipPopupAdapter.mBoundsCompatUILayout;
        ViewGroup.LayoutParams layoutParams = null;
        if (boundsCompatUILayout != null) {
            frameLayout = boundsCompatUILayout.mSwitchableButtonContainer;
        } else {
            frameLayout = null;
        }
        if (frameLayout != null) {
            layoutParams = frameLayout.getLayoutParams();
        }
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) layoutParams;
        if (frameLayout != null && layoutParams2 != null) {
            SemTipPopup semTipPopup = new SemTipPopup(boundsCompatUIUtil$TipPopupAdapter.mViewHost);
            boundsCompatUIUtil$TipPopupAdapter.mSemTipPopup = semTipPopup;
            semTipPopup.setMessage(boundsCompatUIUtil$TipPopupAdapter.mMessage);
            boundsCompatUIUtil$TipPopupAdapter.mSemTipPopup.setExpanded(true);
            boundsCompatUIUtil$TipPopupAdapter.mSemTipPopup.setOutsideTouchEnabled(true);
            boundsCompatUIUtil$TipPopupAdapter.mSemTipPopup.setOnStateChangeListener(new SemTipPopup.OnStateChangeListener() { // from class: com.android.wm.shell.compatui.BoundsCompatUIUtil$TipPopupAdapter$$ExternalSyntheticLambda0
                public final void onStateChanged(int i) {
                    BoundsCompatUIUtil$TipPopupAdapter boundsCompatUIUtil$TipPopupAdapter2 = BoundsCompatUIUtil$TipPopupAdapter.this;
                    BoundsCompatUIUtil$TipPopupAdapter boundsCompatUIUtil$TipPopupAdapter3 = BoundsCompatUIUtil$TipPopupAdapter.INSTANCE;
                    if (i == 0) {
                        boundsCompatUIUtil$TipPopupAdapter2.dismissTipPopup();
                    } else {
                        boundsCompatUIUtil$TipPopupAdapter2.getClass();
                    }
                }
            });
            int[] locationOnScreen = frameLayout.getLocationOnScreen();
            int i = layoutParams2.gravity;
            if (i != 53) {
                if (i != 83) {
                    boundsCompatUIUtil$TipPopupAdapter.mSemTipPopup.setTargetPosition(locationOnScreen[0], locationOnScreen[1] - (layoutParams2.height >> 1));
                    boundsCompatUIUtil$TipPopupAdapter.mSemTipPopup.show(0);
                    return;
                } else {
                    boundsCompatUIUtil$TipPopupAdapter.mSemTipPopup.setTargetPosition(locationOnScreen[0] + layoutParams2.width, locationOnScreen[1] - (layoutParams2.height >> 1));
                    boundsCompatUIUtil$TipPopupAdapter.mSemTipPopup.show(1);
                    return;
                }
            }
            boundsCompatUIUtil$TipPopupAdapter.mSemTipPopup.setTargetPosition(locationOnScreen[0], locationOnScreen[1] + (layoutParams2.height >> 1));
            boundsCompatUIUtil$TipPopupAdapter.mSemTipPopup.show(2);
            return;
        }
        Log.e(TAG, "No target button with layoutParams to show guide tip.");
        boundsCompatUIUtil$TipPopupAdapter.dismissTipPopup();
    }

    BoundsCompatUIUtil$TipPopupAdapter() {
    }

    public final void dismissTipPopup() {
        View view = this.mViewHost;
        String str = TAG;
        if (view != null) {
            Log.d(str, "dismissTipPopup: mViewHost=" + this.mViewHost + ", callers=" + Debug.getCallers(6));
        }
        SemTipPopup semTipPopup = this.mSemTipPopup;
        if (semTipPopup != null && semTipPopup.isShowing()) {
            Log.d(str, "dismissTipPopup: dismiss TipPopup");
            this.mSemTipPopup.setOnStateChangeListener((SemTipPopup.OnStateChangeListener) null);
            this.mSemTipPopup.dismiss(false);
            this.mSemTipPopup = null;
        }
        View view2 = this.mViewHost;
        if (view2 != null) {
            view2.removeOnAttachStateChangeListener(this.mAttachStateChangeListener);
            this.mWindowManager.removeViewImmediate(this.mViewHost);
            this.mViewHost = null;
        }
    }

    public final void release() {
        dismissTipPopup();
        this.mWindowManager = null;
        this.mBoundsCompatUILayout = null;
        this.mMessage = null;
        this.mViewHost = null;
        this.mSemTipPopup = null;
    }

    public final void show() {
        View view = this.mViewHost;
        String str = TAG;
        if (view == null) {
            Log.e(str, "show: host is null, something went wrong.");
            return;
        }
        Rect rect = new Rect();
        this.mBoundsCompatUILayout.getWindowDisplayFrame(rect);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(rect.width(), rect.height(), 2038, 40, -3);
        layoutParams.token = new Binder();
        layoutParams.setTitle(BoundsCompatUIUtil$TipPopupAdapter.class.getSimpleName());
        layoutParams.privateFlags |= 536870976;
        layoutParams.semAddPrivateFlags(16);
        layoutParams.setFitInsetsTypes(0);
        Log.d(str, "show: mViewHost=" + this.mViewHost + ", layoutParams=" + layoutParams);
        this.mWindowManager.addView(this.mViewHost, layoutParams);
    }
}
