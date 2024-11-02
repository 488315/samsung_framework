package com.samsung.android.knox.lockscreen;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes3.dex */
public class InvisibleOverlay extends LinearLayout {
    public static final String TAG = "LSO_InvisibleOverlay";
    public final Context mContext;
    public View mLSOView;

    public InvisibleOverlay(Context context) {
        super(context);
        Log.d(TAG, "InvisibleOverlay(context)");
        this.mContext = context;
        setLayoutParams(new LinearLayout.LayoutParams(0, 0));
        this.mLSOView = new LockscreenOverlayView(context);
    }

    public final void changeVisibility(int i) {
        View view = this.mLSOView;
        if (view != null) {
            view.setVisibility(i);
        }
    }

    public final FrameLayout getFrameLayout() {
        ViewParent parent = getParent();
        FrameLayout frameLayout = null;
        while (parent != null && frameLayout == null) {
            Class<?> cls = parent.getClass();
            if (FrameLayout.class.isAssignableFrom(cls)) {
                Log.d(TAG, "getFrameLayout() : Attaching LockscreenOverlayView to ".concat(cls.getName()));
                frameLayout = (FrameLayout) parent;
                parent = null;
            } else {
                Log.d(TAG, "getFrameLayout() : Cannot attach FrameLayout. find parent view ".concat(cls.getName()));
                parent = parent.getParent();
            }
        }
        return frameLayout;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        FrameLayout frameLayout = getFrameLayout();
        if (frameLayout != null) {
            try {
                frameLayout.removeView(this.mLSOView);
            } catch (Exception e) {
                Log.d(TAG, "onAttachedToWindow() ignore Exception: ", e);
            }
            frameLayout.addView(this.mLSOView, new LinearLayout.LayoutParams(-1, -1));
            return;
        }
        Log.d(TAG, "onAttachedToWindow() : cannot find parentview for LSO. ");
    }

    public InvisibleOverlay(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Log.d(TAG, "InvisibleOverlay(context,attrs)");
        this.mContext = context;
        setLayoutParams(new LinearLayout.LayoutParams(0, 0));
        this.mLSOView = new LockscreenOverlayView(context);
    }
}
