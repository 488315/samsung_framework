package com.samsung.systemui.splugins.noticenter;

import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.widget.RemoteViews;
import com.samsung.systemui.splugins.SPlugin;
import com.samsung.systemui.splugins.annotations.ProvidesInterface;
import com.samsung.systemui.splugins.annotations.Requires;
import com.sec.ims.volte2.data.VolteConstants;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
@ProvidesInterface(action = PluginNotiCenter.ACTION, version = 7001)
/* loaded from: classes3.dex */
public interface PluginNotiCenter extends SPlugin {
    public static final String ACTION = "com.samsung.systemui.action.PLUGIN_NOTICENTER";
    public static final int MAJOR_VERSION = 7;
    public static final int MINOR_VERSION = 1;
    public static final int VERSION = 7001;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes3.dex */
    public interface Callback {
        void onChangedVisibilityOnKeyguard(boolean z);

        void onNoclearAppListUpdate(Bundle bundle);

        void onNoclearUpdate(boolean z);

        void onNotiCenterPanelUpdate(RemoteViews remoteViews);

        void onNotiStarPanelShowOnKeyguard(boolean z);
    }

    @Requires(target = PluginNotiCenter.class, version = VolteConstants.ErrorCode.MDMN_CALL_FORWARDED)
    void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr);

    void enterKeyguard();

    @Override // com.samsung.systemui.splugins.SPlugin
    default int getVersion() {
        return 7001;
    }

    @Deprecated
    void insert(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap);

    void requestNotify(StatusBarNotification statusBarNotification);

    @Requires(target = PluginNotiCenter.class, version = VolteConstants.ErrorCode.CANCEL_CALL_COMPLETED_ELSEWHERE)
    void requestVocHelp(Bundle bundle, StatusBarNotification statusBarNotification);

    void setCallback(Callback callback);

    void unLock();

    void updateSettings(Bundle bundle);
}
