package com.android.systemui.statusbar.phone;

import android.app.Notification;
import android.app.PendingIntent;
import android.os.SystemClock;
import android.service.notification.StatusBarNotification;
import android.util.EventLog;
import com.android.systemui.navigationbar.NavBarHelper;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.shade.NotificationShadeWindowState;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.wm.shell.bubbles.BubbleController;
import com.android.wm.shell.bubbles.BubbleController$$ExternalSyntheticLambda15;
import com.android.wm.shell.bubbles.BubbleController$$ExternalSyntheticLambda19;
import com.android.wm.shell.bubbles.Bubbles;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.startingsurface.StartingWindowController;
import com.android.wm.shell.startingsurface.StartingWindowController$$ExternalSyntheticLambda1;
import java.util.function.Consumer;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final /* synthetic */ class CentralSurfacesImpl$$ExternalSyntheticLambda13 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ CentralSurfacesImpl f$0;

    public /* synthetic */ CentralSurfacesImpl$$ExternalSyntheticLambda13(CentralSurfacesImpl centralSurfacesImpl, int i) {
        this.$r8$classId = i;
        this.f$0 = centralSurfacesImpl;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        boolean z;
        int i = 1;
        int i2 = 6;
        switch (this.$r8$classId) {
            case 0:
                CentralSurfacesImpl centralSurfacesImpl = this.f$0;
                Bubbles bubbles = (Bubbles) obj;
                int i3 = centralSurfacesImpl.mStatusBarMode;
                if (i3 == 3 || i3 == 6 || centralSurfacesImpl.mStatusBarWindowState == 2) {
                    NavBarHelper navBarHelper = (NavBarHelper) centralSurfacesImpl.mNavBarHelperLazy.get();
                    navBarHelper.getClass();
                    if (new NavBarHelper.CurrentSysuiState(navBarHelper).mWindowState == 2) {
                        z = false;
                        BubbleController.BubblesImpl bubblesImpl = (BubbleController.BubblesImpl) bubbles;
                        ((HandlerExecutor) BubbleController.this.mMainExecutor).execute(new BubbleController$$ExternalSyntheticLambda19(i, bubblesImpl, z));
                        return;
                    }
                }
                z = true;
                BubbleController.BubblesImpl bubblesImpl2 = (BubbleController.BubblesImpl) bubbles;
                ((HandlerExecutor) BubbleController.this.mMainExecutor).execute(new BubbleController$$ExternalSyntheticLambda19(i, bubblesImpl2, z));
                return;
            case 1:
                CentralSurfacesImpl centralSurfacesImpl2 = this.f$0;
                NotificationEntry notificationEntry = (NotificationEntry) obj;
                centralSurfacesImpl2.getClass();
                StatusBarNotification statusBarNotification = notificationEntry.mSbn;
                Notification notification2 = statusBarNotification.getNotification();
                if (notification2.fullScreenIntent != null) {
                    try {
                        EventLog.writeEvent(36003, statusBarNotification.getKey());
                        centralSurfacesImpl2.wakeUpForFullScreenIntent(statusBarNotification.getPackageName());
                        notification2.fullScreenIntent.send();
                        notificationEntry.interruption = true;
                        notificationEntry.lastFullScreenIntentLaunchTime = SystemClock.elapsedRealtime();
                        return;
                    } catch (PendingIntent.CanceledException unused) {
                        return;
                    }
                }
                return;
            case 2:
                CentralSurfacesImpl centralSurfacesImpl3 = this.f$0;
                centralSurfacesImpl3.getClass();
                BubbleController.BubblesImpl bubblesImpl3 = (BubbleController.BubblesImpl) ((Bubbles) obj);
                ((HandlerExecutor) BubbleController.this.mMainExecutor).execute(new BubbleController$$ExternalSyntheticLambda15(i2, bubblesImpl3, new CentralSurfacesImpl$$ExternalSyntheticLambda0(centralSurfacesImpl3)));
                return;
            case 3:
                CentralSurfacesImpl centralSurfacesImpl4 = this.f$0;
                StartingWindowController.StartingSurfaceImpl startingSurfaceImpl = (StartingWindowController.StartingSurfaceImpl) obj;
                centralSurfacesImpl4.getClass();
                ((HandlerExecutor) StartingWindowController.this.mSplashScreenExecutor).execute(new StartingWindowController$$ExternalSyntheticLambda1(i, startingSurfaceImpl, new CentralSurfacesImpl$$ExternalSyntheticLambda0(centralSurfacesImpl4)));
                return;
            case 4:
                CentralSurfacesImpl centralSurfacesImpl5 = this.f$0;
                centralSurfacesImpl5.getClass();
                int intValue = ((Integer) obj).intValue();
                NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = (NotificationShadeWindowControllerImpl) centralSurfacesImpl5.mNotificationShadeWindowController;
                NotificationShadeWindowState notificationShadeWindowState = notificationShadeWindowControllerImpl.mCurrentState;
                if (intValue != notificationShadeWindowState.scrimsVisibility) {
                    boolean isExpanded = notificationShadeWindowControllerImpl.isExpanded(notificationShadeWindowState);
                    notificationShadeWindowState.scrimsVisibility = intValue;
                    if (isExpanded != notificationShadeWindowControllerImpl.isExpanded(notificationShadeWindowState)) {
                        notificationShadeWindowControllerImpl.apply(notificationShadeWindowState);
                    }
                    notificationShadeWindowControllerImpl.mScrimsVisibilityListener.accept(Integer.valueOf(intValue));
                    return;
                }
                return;
            case 5:
                CentralSurfacesImpl centralSurfacesImpl6 = this.f$0;
                centralSurfacesImpl6.getClass();
                CentralSurfacesImpl$$ExternalSyntheticLambda5 centralSurfacesImpl$$ExternalSyntheticLambda5 = new CentralSurfacesImpl$$ExternalSyntheticLambda5(centralSurfacesImpl6, i2);
                if (((Boolean) obj).booleanValue()) {
                    centralSurfacesImpl6.mLightRevealScrim.post(centralSurfacesImpl$$ExternalSyntheticLambda5);
                    return;
                } else {
                    centralSurfacesImpl$$ExternalSyntheticLambda5.run();
                    return;
                }
            default:
                this.f$0.getClass();
                return;
        }
    }
}
