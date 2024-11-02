package com.android.systemui.vibrate;

import android.content.Context;
import android.os.Vibrator;
import android.view.accessibility.AccessibilityManager;
import com.android.systemui.LsRune;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class VibrationUtil {
    public final AccessibilityManager mAccessibilityManager;
    public final VibratorType mVibratorType;

    public VibrationUtil(Context context) {
        this.mAccessibilityManager = AccessibilityManager.getInstance(context);
        Vibrator vibrator = (Vibrator) context.getSystemService("vibrator");
        if (vibrator != null) {
            if (LsRune.SECURITY_HAPTIC_FEEDBACK_ON_DC_MOTOR && vibrator.semGetSupportedVibrationType() == 1) {
                this.mVibratorType = VibratorType.create(2);
            } else {
                this.mVibratorType = VibratorType.create(1);
            }
            this.mVibratorType.setVibrator(vibrator);
            return;
        }
        this.mVibratorType = VibratorType.create(0);
    }

    public final void playVibration(int i) {
        boolean z = true;
        if (i == 1 && !this.mAccessibilityManager.isTouchExplorationEnabled()) {
            z = false;
        }
        if (z) {
            this.mVibratorType.playVibration(this, i);
        }
    }
}
