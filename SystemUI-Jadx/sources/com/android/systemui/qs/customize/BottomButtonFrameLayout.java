package com.android.systemui.qs.customize;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class BottomButtonFrameLayout extends FrameLayout {
    public BottomButtonFrameLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void dispatchWindowFocusChanged(boolean z) {
        if (!isPressed()) {
            super.dispatchWindowFocusChanged(z);
        }
    }
}