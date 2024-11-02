package com.android.systemui.statusbar.policy;

import android.content.Context;
import android.util.AttributeSet;
import com.android.systemui.Dependency;
import com.android.systemui.shared.shadow.DoubleShadowTextView;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.policy.QSClockBellTower;
import com.android.systemui.util.DeviceType;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public abstract class QSClock extends DoubleShadowTextView implements CommandQueue.Callbacks, QSClockBellTower.TimeAudience {
    public boolean mClockVisibleByPolicy;
    public String mViewTag;

    static {
        DeviceType.isEngOrUTBinary();
    }

    public QSClock(Context context) {
        this(context, null);
    }

    public boolean calculateVisibility() {
        return this.mClockVisibleByPolicy;
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void disable(int i, int i2, int i3, boolean z) {
        boolean z2;
        if (i != getDisplay().getDisplayId()) {
            return;
        }
        int i4 = 8388608 & i2;
        int i5 = 0;
        if (i4 == 0) {
            z2 = true;
        } else {
            z2 = false;
        }
        boolean z3 = this.mClockVisibleByPolicy;
        if (z2 != z3 && z3 != z2) {
            this.mClockVisibleByPolicy = z2;
            if (!calculateVisibility()) {
                i5 = 8;
            }
            setVisibility(i5);
        }
    }

    @Override // com.android.systemui.statusbar.policy.QSClockBellTower.TimeAudience
    public final String getTicket() {
        return this.mViewTag;
    }

    @Override // com.android.systemui.statusbar.policy.QSClockBellTower.TimeAudience
    public void notifyTimeChanged(QSClockBellSound qSClockBellSound) {
        if (qSClockBellSound.Demo) {
            return;
        }
        setText(qSClockBellSound.TimeText);
        setContentDescription(qSClockBellSound.TimeContentDescription);
    }

    @Override // android.widget.TextView, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (getTag() != null) {
            this.mViewTag = getTag().toString();
        }
        ((CommandQueue) Dependency.get(CommandQueue.class)).addCallback((CommandQueue.Callbacks) this);
        ((QSClockBellTower) Dependency.get(QSClockBellTower.class)).registerAudience(this);
    }

    @Override // android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ((CommandQueue) Dependency.get(CommandQueue.class)).removeCallback((CommandQueue.Callbacks) this);
        ((QSClockBellTower) Dependency.get(QSClockBellTower.class)).unregisterAudience(this);
    }

    @Override // android.view.View
    public void setVisibility(int i) {
        if (!calculateVisibility()) {
            i = 8;
        }
        super.setVisibility(i);
    }

    public QSClock(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public QSClock(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i, 0);
        this.mClockVisibleByPolicy = true;
        this.mViewTag = "QSClock";
    }

    public QSClock(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mClockVisibleByPolicy = true;
        this.mViewTag = "QSClock";
    }
}
