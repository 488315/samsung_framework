package com.android.systemui.statusbar.notification.row;

import android.view.View;
import com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final /* synthetic */ class NotificationGutsManager$$ExternalSyntheticLambda4 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NotificationGutsManager f$0;
    public final /* synthetic */ View f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ int f$3;
    public final /* synthetic */ NotificationMenuRowPlugin.MenuItem f$4;

    public /* synthetic */ NotificationGutsManager$$ExternalSyntheticLambda4(NotificationGutsManager notificationGutsManager, View view, int i, int i2, NotificationMenuRowPlugin.MenuItem menuItem, int i3) {
        this.$r8$classId = i3;
        this.f$0 = notificationGutsManager;
        this.f$1 = view;
        this.f$2 = i;
        this.f$3 = i2;
        this.f$4 = menuItem;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                NotificationGutsManager notificationGutsManager = this.f$0;
                View view = this.f$1;
                int i = this.f$2;
                int i2 = this.f$3;
                NotificationMenuRowPlugin.MenuItem menuItem = this.f$4;
                notificationGutsManager.getClass();
                notificationGutsManager.mMainHandler.post(new NotificationGutsManager$$ExternalSyntheticLambda4(notificationGutsManager, view, i, i2, menuItem, 1));
                return;
            default:
                this.f$0.openGutsInternal(this.f$1, this.f$2, this.f$3, this.f$4);
                return;
        }
    }
}
