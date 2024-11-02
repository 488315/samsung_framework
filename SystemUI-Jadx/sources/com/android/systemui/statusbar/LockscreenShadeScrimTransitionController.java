package com.android.systemui.statusbar;

import android.content.Context;
import android.util.IndentingPrintWriter;
import android.util.MathUtils;
import com.android.systemui.R;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.phone.ScrimController;
import com.android.systemui.statusbar.policy.ConfigurationController;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class LockscreenShadeScrimTransitionController extends AbstractLockscreenShadeTransitionController {
    public float notificationsScrimDragAmount;
    public float notificationsScrimProgress;
    public int notificationsScrimTransitionDelay;
    public int notificationsScrimTransitionDistance;
    public final ScrimController scrimController;
    public float scrimProgress;
    public int scrimTransitionDistance;

    public LockscreenShadeScrimTransitionController(ScrimController scrimController, Context context, ConfigurationController configurationController, DumpManager dumpManager) {
        super(context, configurationController, dumpManager);
        this.scrimController = scrimController;
    }

    @Override // com.android.systemui.statusbar.AbstractLockscreenShadeTransitionController
    public final void dump(IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("LockscreenShadeScrimTransitionController:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("Resources:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("scrimTransitionDistance: " + this.scrimTransitionDistance);
        indentingPrintWriter.println("notificationsScrimTransitionDelay: " + this.notificationsScrimTransitionDelay);
        indentingPrintWriter.println("notificationsScrimTransitionDistance: " + this.notificationsScrimTransitionDistance);
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("State");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("dragDownAmount: " + this.dragDownAmount);
        indentingPrintWriter.println("scrimProgress: " + this.scrimProgress);
        indentingPrintWriter.println("notificationsScrimProgress: " + this.notificationsScrimProgress);
        indentingPrintWriter.println("notificationsScrimDragAmount: " + this.notificationsScrimDragAmount);
    }

    @Override // com.android.systemui.statusbar.AbstractLockscreenShadeTransitionController
    public final void onDragDownAmountChanged(float f) {
        boolean z;
        this.scrimProgress = MathUtils.saturate(f / this.scrimTransitionDistance);
        float f2 = f - this.notificationsScrimTransitionDelay;
        this.notificationsScrimDragAmount = f2;
        float saturate = MathUtils.saturate(f2 / this.notificationsScrimTransitionDistance);
        this.notificationsScrimProgress = saturate;
        float f3 = this.scrimProgress;
        ScrimController scrimController = this.scrimController;
        if (f3 != scrimController.mTransitionToFullShadeProgress || saturate != scrimController.mTransitionToLockScreenFullShadeNotificationsProgress) {
            scrimController.mTransitionToFullShadeProgress = f3;
            scrimController.mTransitionToLockScreenFullShadeNotificationsProgress = saturate;
            if (f3 <= 0.0f && saturate <= 0.0f) {
                z = false;
            } else {
                z = true;
            }
            if (z != scrimController.mTransitioningToFullShade) {
                scrimController.mTransitioningToFullShade = z;
            }
            scrimController.applyAndDispatchState();
        }
    }

    @Override // com.android.systemui.statusbar.AbstractLockscreenShadeTransitionController
    public final void updateResources() {
        Context context = this.context;
        this.scrimTransitionDistance = context.getResources().getDimensionPixelSize(R.dimen.lockscreen_shade_scrim_transition_distance);
        this.notificationsScrimTransitionDelay = context.getResources().getDimensionPixelSize(R.dimen.lockscreen_shade_notifications_scrim_transition_delay);
        this.notificationsScrimTransitionDistance = context.getResources().getDimensionPixelSize(R.dimen.lockscreen_shade_notifications_scrim_transition_distance);
    }
}
