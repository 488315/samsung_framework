package com.android.systemui.biometrics;

import android.util.Log;
import com.android.systemui.shade.ShadeExpansionChangeEvent;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class AuthDialogPanelInteractionDetector$onPanelExpansionChanged$1 implements Runnable {
    public final /* synthetic */ ShadeExpansionChangeEvent $event;
    public final /* synthetic */ AuthDialogPanelInteractionDetector this$0;

    public AuthDialogPanelInteractionDetector$onPanelExpansionChanged$1(AuthDialogPanelInteractionDetector authDialogPanelInteractionDetector, ShadeExpansionChangeEvent shadeExpansionChangeEvent) {
        this.this$0 = authDialogPanelInteractionDetector;
        this.$event = shadeExpansionChangeEvent;
    }

    @Override // java.lang.Runnable
    public final void run() {
        AuthDialogPanelInteractionDetector authDialogPanelInteractionDetector = this.this$0;
        Action action = authDialogPanelInteractionDetector.action;
        if (action != null) {
            ShadeExpansionChangeEvent shadeExpansionChangeEvent = this.$event;
            if (shadeExpansionChangeEvent.tracking || (shadeExpansionChangeEvent.expanded && shadeExpansionChangeEvent.fraction > 0.0f && authDialogPanelInteractionDetector.panelState == 1)) {
                Log.i("AuthDialogPanelInteractionDetector", "onPanelExpansionChanged, event: " + shadeExpansionChangeEvent);
                action.onPanelInteraction.run();
                authDialogPanelInteractionDetector.disable();
            }
        }
    }
}
