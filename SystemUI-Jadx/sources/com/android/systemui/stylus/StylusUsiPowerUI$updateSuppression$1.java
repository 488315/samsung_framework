package com.android.systemui.stylus;

import android.os.Build;
import com.android.systemui.log.DebugLogger;
import kotlin.jvm.internal.Reflection;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class StylusUsiPowerUI$updateSuppression$1 implements Runnable {
    public final /* synthetic */ boolean $suppress;
    public final /* synthetic */ StylusUsiPowerUI this$0;

    public StylusUsiPowerUI$updateSuppression$1(StylusUsiPowerUI stylusUsiPowerUI, boolean z) {
        this.this$0 = stylusUsiPowerUI;
        this.$suppress = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        if (this.this$0.suppressed == this.$suppress) {
            return;
        }
        int i = DebugLogger.$r8$clinit;
        boolean z = Build.IS_DEBUGGABLE;
        Reflection.getOrCreateKotlinClass(StylusUsiPowerUI.class).getSimpleName();
        StylusUsiPowerUI stylusUsiPowerUI = this.this$0;
        stylusUsiPowerUI.suppressed = this.$suppress;
        stylusUsiPowerUI.handler.post(new StylusUsiPowerUI$refresh$1(stylusUsiPowerUI));
    }
}
