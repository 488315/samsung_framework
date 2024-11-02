package com.android.systemui.statusbar.phone;

import android.graphics.Rect;
import android.os.Handler;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController;
import com.android.systemui.statusbar.phone.ongoingcall.OngoingCallListener;
import com.android.systemui.statusbar.policy.KeyguardStateController;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class IndicatorTouchHandler {
    public int doubleTapCount;
    public boolean isTouchOnCallChip;
    public final KeyguardStateController keyguardStateController;
    public final KnoxStateMonitor knoxStateMonitor;
    public final Handler mainHandler;
    public final OngoingCallController ongoingCallController;
    public final PowerManager powerManager;
    public float touchDownX;
    public float touchDownY;
    public final Rect callChipRect = new Rect();
    public final Rect keyguardCallChipRect = new Rect();
    public final IndicatorTouchHandler$callChipLayoutChangeListener$1 callChipLayoutChangeListener = new View.OnLayoutChangeListener() { // from class: com.android.systemui.statusbar.phone.IndicatorTouchHandler$callChipLayoutChangeListener$1
        @Override // android.view.View.OnLayoutChangeListener
        public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            if (i != i5 || i3 != i7) {
                IndicatorTouchHandler.access$updateCallChipRect(IndicatorTouchHandler.this);
            }
        }
    };
    public final IndicatorTouchHandler$ongoingCallListener$1 ongoingCallListener = new OngoingCallListener() { // from class: com.android.systemui.statusbar.phone.IndicatorTouchHandler$ongoingCallListener$1
        @Override // com.android.systemui.statusbar.phone.ongoingcall.OngoingCallListener
        public final void onOngoingCallStateChanged() {
            IndicatorTouchHandler indicatorTouchHandler = IndicatorTouchHandler.this;
            indicatorTouchHandler.callChipRect.setEmpty();
            indicatorTouchHandler.keyguardCallChipRect.setEmpty();
            OngoingCallController ongoingCallController = indicatorTouchHandler.ongoingCallController;
            boolean hasOngoingCall = ongoingCallController.hasOngoingCall();
            IndicatorTouchHandler$callChipLayoutChangeListener$1 indicatorTouchHandler$callChipLayoutChangeListener$1 = indicatorTouchHandler.callChipLayoutChangeListener;
            if (hasOngoingCall) {
                IndicatorTouchHandler.access$updateCallChipRect(indicatorTouchHandler);
                View view = ongoingCallController.keyguardCallChipController.chipView;
                if (view != null) {
                    view.addOnLayoutChangeListener(indicatorTouchHandler$callChipLayoutChangeListener$1);
                }
                View view2 = ongoingCallController.chipView;
                if (view2 != null) {
                    view2.addOnLayoutChangeListener(indicatorTouchHandler$callChipLayoutChangeListener$1);
                    return;
                }
                return;
            }
            View view3 = ongoingCallController.chipView;
            if (view3 != null) {
                view3.removeOnLayoutChangeListener(indicatorTouchHandler$callChipLayoutChangeListener$1);
            }
            View view4 = ongoingCallController.keyguardCallChipController.chipView;
            if (view4 != null) {
                view4.removeOnLayoutChangeListener(indicatorTouchHandler$callChipLayoutChangeListener$1);
            }
        }
    };
    public final IndicatorTouchHandler$doubleTapTimeoutRunnable$1 doubleTapTimeoutRunnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.IndicatorTouchHandler$doubleTapTimeoutRunnable$1
        @Override // java.lang.Runnable
        public final void run() {
            IndicatorTouchHandler.this.doubleTapCount = 0;
        }
    };

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.statusbar.phone.IndicatorTouchHandler$callChipLayoutChangeListener$1] */
    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.statusbar.phone.IndicatorTouchHandler$ongoingCallListener$1] */
    /* JADX WARN: Type inference failed for: r1v5, types: [com.android.systemui.statusbar.phone.IndicatorTouchHandler$doubleTapTimeoutRunnable$1] */
    public IndicatorTouchHandler(Handler handler, OngoingCallController ongoingCallController, KnoxStateMonitor knoxStateMonitor, KeyguardStateController keyguardStateController, PowerManager powerManager) {
        this.mainHandler = handler;
        this.ongoingCallController = ongoingCallController;
        this.knoxStateMonitor = knoxStateMonitor;
        this.keyguardStateController = keyguardStateController;
        this.powerManager = powerManager;
    }

    public static final void access$updateCallChipRect(IndicatorTouchHandler indicatorTouchHandler) {
        OngoingCallController ongoingCallController = indicatorTouchHandler.ongoingCallController;
        View view = ongoingCallController.chipView;
        Rect rect = indicatorTouchHandler.callChipRect;
        if (view != null) {
            int[] iArr = new int[2];
            view.getLocationInWindow(iArr);
            int i = iArr[0];
            rect.set(i, 0, view.getWidth() + i, view.getHeight() + iArr[1]);
        }
        View view2 = ongoingCallController.keyguardCallChipController.chipView;
        Rect rect2 = indicatorTouchHandler.keyguardCallChipRect;
        if (view2 != null) {
            int[] iArr2 = new int[2];
            view2.getLocationInWindow(iArr2);
            int i2 = iArr2[0];
            rect2.set(i2, 0, view2.getWidth() + i2, view2.getHeight() + iArr2[1]);
        }
        Log.d("IndicatorTouchHandler", "update keyguard rect=" + rect2 + " call chip rect=" + rect);
    }
}
