package com.android.systemui.statusbar;

import android.app.ActivityManager;
import android.content.res.Resources;
import android.os.SystemProperties;
import android.os.Trace;
import android.util.IndentingPrintWriter;
import android.util.MathUtils;
import android.view.CrossWindowBlurListeners;
import android.view.SurfaceControl;
import android.view.ViewRootImpl;
import com.android.systemui.DisplayCutoutBaseView$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.dump.DumpManager;
import java.io.PrintWriter;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class BlurUtils implements Dumpable {
    public final CrossWindowBlurListeners crossWindowBlurListeners;
    public boolean earlyWakeupEnabled;
    public int lastAppliedBlur;
    public final int maxBlurRadius;
    public final int minBlurRadius;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public BlurUtils(Resources resources, CrossWindowBlurListeners crossWindowBlurListeners, DumpManager dumpManager) {
        this.crossWindowBlurListeners = crossWindowBlurListeners;
        this.minBlurRadius = resources.getDimensionPixelSize(R.dimen.min_window_blur_radius);
        this.maxBlurRadius = resources.getDimensionPixelSize(R.dimen.max_window_blur_radius);
        DumpManager.registerDumpable$default(dumpManager, BlurUtils.class.getName(), this);
    }

    public final void applyBlur(ViewRootImpl viewRootImpl, int i, boolean z) {
        if (viewRootImpl != null && viewRootImpl.getSurfaceControl().isValid() && !QpRune.QUICK_PANEL_BLUR) {
            SurfaceControl.Transaction createTransaction = createTransaction();
            try {
                if (supportsBlursOnWindows()) {
                    createTransaction.setBackgroundBlurRadius(viewRootImpl.getSurfaceControl(), i);
                    if (!this.earlyWakeupEnabled && this.lastAppliedBlur == 0 && i != 0) {
                        Trace.asyncTraceForTrackBegin(4096L, "BlurUtils", "eEarlyWakeup (applyBlur)", 0);
                        createTransaction.setEarlyWakeupStart();
                        this.earlyWakeupEnabled = true;
                    }
                    if (this.earlyWakeupEnabled && this.lastAppliedBlur != 0 && i == 0) {
                        createTransaction.setEarlyWakeupEnd();
                        Trace.asyncTraceForTrackEnd(4096L, "BlurUtils", 0);
                        this.earlyWakeupEnabled = false;
                    }
                    this.lastAppliedBlur = i;
                }
                createTransaction.setOpaque(viewRootImpl.getSurfaceControl(), z);
                createTransaction.apply();
                Unit unit = Unit.INSTANCE;
                CloseableKt.closeFinally(createTransaction, null);
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    CloseableKt.closeFinally(createTransaction, th);
                    throw th2;
                }
            }
        }
    }

    public final float blurRadiusOfRatio(float f) {
        boolean z;
        if (f == 0.0f) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            return 0.0f;
        }
        return MathUtils.lerp(this.minBlurRadius, this.maxBlurRadius, f);
    }

    public SurfaceControl.Transaction createTransaction() {
        return new SurfaceControl.Transaction();
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
        indentingPrintWriter.println("BlurUtils:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("minBlurRadius: " + this.minBlurRadius);
        indentingPrintWriter.println("maxBlurRadius: " + this.maxBlurRadius);
        DisplayCutoutBaseView$$ExternalSyntheticOutline0.m("supportsBlursOnWindows: ", supportsBlursOnWindows(), indentingPrintWriter);
        indentingPrintWriter.println("CROSS_WINDOW_BLUR_SUPPORTED: " + CrossWindowBlurListeners.CROSS_WINDOW_BLUR_SUPPORTED);
        DisplayCutoutBaseView$$ExternalSyntheticOutline0.m("isHighEndGfx: ", ActivityManager.isHighEndGfx(), indentingPrintWriter);
    }

    public final boolean supportsBlursOnWindows() {
        if (!CrossWindowBlurListeners.CROSS_WINDOW_BLUR_SUPPORTED || !ActivityManager.isHighEndGfx() || !this.crossWindowBlurListeners.isCrossWindowBlurEnabled() || SystemProperties.getBoolean("persist.sysui.disableBlur", false)) {
            return false;
        }
        return true;
    }
}
