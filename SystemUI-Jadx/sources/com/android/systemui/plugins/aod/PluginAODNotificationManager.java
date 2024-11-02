package com.android.systemui.plugins.aod;

import android.graphics.drawable.Icon;
import android.service.notification.StatusBarNotification;
import android.widget.ImageView;
import com.android.systemui.plugins.annotations.VersionCheck;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public interface PluginAODNotificationManager extends PluginAODBaseManager {

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public interface Callback {
        void animateExpandLockedShadePanel(StatusBarNotification statusBarNotification);

        void clickNotification(String str);

        Icon getNotificationIcon(String str);

        void requestActiveNotifications();

        void requestVisibleNotifications();

        void showSubScreenNotification(String str);
    }

    void addNotification(StatusBarNotification statusBarNotification);

    @VersionCheck(version = 1050)
    void lockscreenNotificationTypeChanged(int i);

    void removeNotification(String str);

    void setCallback(Callback callback);

    void setTagId(int i, int i2);

    @VersionCheck(version = 1050)
    void subLauncherUpdateNotification(ArrayList<ImageView> arrayList);

    void updateActiveNotifications(List<StatusBarNotification> list);

    void updateNotification(StatusBarNotification statusBarNotification);

    void updateNotification(ArrayList<ImageView> arrayList);

    void updateVisibleNotifications(List<StatusBarNotification> list, List<StatusBarNotification> list2, int i);
}
