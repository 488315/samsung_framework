package com.android.systemui.statusbar.notification.stack;

import android.util.IndentingPrintWriter;
import com.android.systemui.util.DumpUtilsKt;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final /* synthetic */ class NotificationStackScrollLayout$$ExternalSyntheticLambda7 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NotificationStackScrollLayout f$0;
    public final /* synthetic */ IndentingPrintWriter f$1;

    public /* synthetic */ NotificationStackScrollLayout$$ExternalSyntheticLambda7(NotificationStackScrollLayout notificationStackScrollLayout, IndentingPrintWriter indentingPrintWriter, int i) {
        this.$r8$classId = i;
        this.f$0 = notificationStackScrollLayout;
        this.f$1 = indentingPrintWriter;
    }

    @Override // java.lang.Runnable
    public final void run() {
        final boolean z;
        boolean z2 = true;
        switch (this.$r8$classId) {
            case 0:
                final NotificationStackScrollLayout notificationStackScrollLayout = this.f$0;
                final IndentingPrintWriter indentingPrintWriter = this.f$1;
                int i = NotificationStackScrollLayout.$r8$clinit;
                if (notificationStackScrollLayout.mClearAllEnabled && notificationStackScrollLayout.mController.hasNotifications(0, true)) {
                    z = true;
                } else {
                    z = false;
                }
                StringBuilder sb = new StringBuilder("showFooterView: ");
                if ((!z && notificationStackScrollLayout.mController.mNotifStats.numActiveNotifs <= 0) || !notificationStackScrollLayout.mIsCurrentUserSetup || notificationStackScrollLayout.onKeyguard() || notificationStackScrollLayout.mUpcomingStatusBarState == 1 || ((notificationStackScrollLayout.mQsExpansionFraction == 1.0f && notificationStackScrollLayout.mQsFullScreen) || notificationStackScrollLayout.mScreenOffAnimationController.shouldHideNotificationsFooter() || notificationStackScrollLayout.mIsRemoteInputActive)) {
                    z2 = false;
                }
                sb.append(z2);
                indentingPrintWriter.println(sb.toString());
                DumpUtilsKt.withIncreasedIndent(indentingPrintWriter, new Runnable() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$$ExternalSyntheticLambda8
                    @Override // java.lang.Runnable
                    public final void run() {
                        NotificationStackScrollLayout notificationStackScrollLayout2 = NotificationStackScrollLayout.this;
                        IndentingPrintWriter indentingPrintWriter2 = indentingPrintWriter;
                        boolean z3 = z;
                        int i2 = NotificationStackScrollLayout.$r8$clinit;
                        notificationStackScrollLayout2.getClass();
                        indentingPrintWriter2.println("showDismissView: " + z3);
                        DumpUtilsKt.withIncreasedIndent(indentingPrintWriter2, new NotificationStackScrollLayout$$ExternalSyntheticLambda7(notificationStackScrollLayout2, indentingPrintWriter2, 1));
                        indentingPrintWriter2.println();
                        indentingPrintWriter2.println("showHistory: " + notificationStackScrollLayout2.mController.isHistoryEnabled());
                        indentingPrintWriter2.println();
                        indentingPrintWriter2.println("visibleNotificationCount: " + notificationStackScrollLayout2.mController.mNotifStats.numActiveNotifs);
                        indentingPrintWriter2.println("mIsCurrentUserSetup: " + notificationStackScrollLayout2.mIsCurrentUserSetup);
                        indentingPrintWriter2.println("onKeyguard: " + notificationStackScrollLayout2.onKeyguard());
                        indentingPrintWriter2.println("mUpcomingStatusBarState: " + notificationStackScrollLayout2.mUpcomingStatusBarState);
                        indentingPrintWriter2.println("mQsExpansionFraction: " + notificationStackScrollLayout2.mQsExpansionFraction);
                        indentingPrintWriter2.println("mQsFullScreen: " + notificationStackScrollLayout2.mQsFullScreen);
                        indentingPrintWriter2.println("mScreenOffAnimationController.shouldHideNotificationsFooter: " + notificationStackScrollLayout2.mScreenOffAnimationController.shouldHideNotificationsFooter());
                        indentingPrintWriter2.println("mIsRemoteInputActive: " + notificationStackScrollLayout2.mIsRemoteInputActive);
                    }
                });
                return;
            default:
                NotificationStackScrollLayout notificationStackScrollLayout2 = this.f$0;
                IndentingPrintWriter indentingPrintWriter2 = this.f$1;
                int i2 = NotificationStackScrollLayout.$r8$clinit;
                indentingPrintWriter2.println("mClearAllEnabled: " + notificationStackScrollLayout2.mClearAllEnabled);
                indentingPrintWriter2.println("hasActiveClearableNotifications: " + notificationStackScrollLayout2.mController.hasNotifications(0, true));
                return;
        }
    }
}
