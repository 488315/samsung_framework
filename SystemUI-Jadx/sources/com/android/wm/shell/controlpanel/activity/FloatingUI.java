package com.android.wm.shell.controlpanel.activity;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public abstract class FloatingUI {
    public final Context mContext;
    public WindowManager.LayoutParams mLayoutParam;
    public View mOverlayView;
    public final WindowManager mWindowManager;

    public FloatingUI(Context context) {
        this.mContext = context;
        this.mWindowManager = (WindowManager) context.getSystemService("window");
    }

    public void addView() {
        Log.i("ControlPanelService", "addView ");
        this.mOverlayView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { // from class: com.android.wm.shell.controlpanel.activity.FloatingUI.1
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public final boolean onPreDraw() {
                Log.i("ControlPanelService", "onPreDraw");
                View view = FloatingUI.this.mOverlayView;
                if (view != null && view.getViewTreeObserver() != null) {
                    FloatingUI.this.mOverlayView.getViewTreeObserver().removeOnPreDrawListener(this);
                }
                FloatingUI.this.getClass();
                return true;
            }
        });
        this.mWindowManager.addView(this.mOverlayView, this.mLayoutParam);
        fadeInAnimation();
    }

    public abstract void connectUIObject();

    public abstract void fadeInAnimation();

    public void removeView() {
        View view = this.mOverlayView;
        if (view != null) {
            this.mWindowManager.removeView(view);
        }
        this.mOverlayView = null;
    }

    public abstract void setAppendLayoutParams();

    public final void showView() {
        this.mLayoutParam = new WindowManager.LayoutParams(-1, -1, 1002, 552, -3);
        setAppendLayoutParams();
        connectUIObject();
        addView();
    }
}
