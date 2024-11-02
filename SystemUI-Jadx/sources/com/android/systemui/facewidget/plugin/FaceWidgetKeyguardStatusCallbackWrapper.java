package com.android.systemui.facewidget.plugin;

import android.animation.Animator;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;
import com.android.systemui.facewidget.KeyguardStatusCallback;
import com.android.systemui.keyguard.KeyguardUnlockInfo;
import com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusCallback;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class FaceWidgetKeyguardStatusCallbackWrapper implements PluginKeyguardStatusCallback {
    public KeyguardStatusCallback mStatusCallback;

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusCallback
    public final boolean isDozing() {
        KeyguardStatusCallback keyguardStatusCallback = this.mStatusCallback;
        if (keyguardStatusCallback != null) {
            return NotificationPanelViewController.this.mDozing;
        }
        return false;
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusCallback
    public final boolean isKeyguardState() {
        KeyguardStatusCallback keyguardStatusCallback = this.mStatusCallback;
        if (keyguardStatusCallback != null) {
            return NotificationPanelViewController.this.isKeyguardShowing();
        }
        return true;
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusCallback
    public final void setFullScreenMode(boolean z, long j) {
        KeyguardStatusCallback keyguardStatusCallback = this.mStatusCallback;
        if (keyguardStatusCallback != null) {
            ((NotificationPanelViewController.AnonymousClass9) keyguardStatusCallback).setFullScreenMode(z, j, null);
        }
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusCallback
    public final void setMusicShown(boolean z) {
        KeyguardStatusCallback keyguardStatusCallback = this.mStatusCallback;
        if (keyguardStatusCallback != null) {
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            notificationPanelViewController.getClass();
            Log.d("NotificationPanelView", "setMusicShown() shown = " + z);
            notificationPanelViewController.positionClockAndNotifications(false);
        }
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusCallback
    public final void startActivity(PendingIntent pendingIntent) {
        KeyguardStatusCallback keyguardStatusCallback = this.mStatusCallback;
        if (keyguardStatusCallback != null) {
            final NotificationPanelViewController.AnonymousClass9 anonymousClass9 = (NotificationPanelViewController.AnonymousClass9) keyguardStatusCallback;
            KeyguardUnlockInfo.setUnlockTrigger(KeyguardUnlockInfo.UnlockTrigger.TRIGGER_FACE_WIDGET);
            NotificationPanelViewController.this.mActivityStarter.startPendingIntentDismissingKeyguard(pendingIntent, new Runnable() { // from class: com.android.systemui.shade.NotificationPanelViewController$9$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    NotificationPanelViewController.this.mStatusBarKeyguardViewManager.readyForKeyguardDone();
                }
            });
        }
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusCallback
    public final void userActivity() {
        KeyguardStatusCallback keyguardStatusCallback = this.mStatusCallback;
        if (keyguardStatusCallback != null) {
            ((CentralSurfacesImpl) NotificationPanelViewController.this.mCentralSurfaces).userActivity();
        }
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusCallback
    public final void setFullScreenMode(boolean z, long j, Animator.AnimatorListener animatorListener) {
        KeyguardStatusCallback keyguardStatusCallback = this.mStatusCallback;
        if (keyguardStatusCallback != null) {
            ((NotificationPanelViewController.AnonymousClass9) keyguardStatusCallback).setFullScreenMode(z, j, animatorListener);
        }
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusCallback
    public final void startActivity(Intent intent, boolean z, int i) {
        KeyguardStatusCallback keyguardStatusCallback = this.mStatusCallback;
        if (keyguardStatusCallback != null) {
            NotificationPanelViewController.AnonymousClass9 anonymousClass9 = (NotificationPanelViewController.AnonymousClass9) keyguardStatusCallback;
            anonymousClass9.getClass();
            KeyguardUnlockInfo.setUnlockTrigger(KeyguardUnlockInfo.UnlockTrigger.TRIGGER_FACE_WIDGET);
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            notificationPanelViewController.mIsLaunchTransitionFinished = true;
            notificationPanelViewController.mActivityStarter.startActivityDismissingKeyguard(intent, false, z, false, null, i, null, null);
        }
    }
}
