package com.android.systemui.statusbar.notification.collection.coordinator;

import android.os.Trace;
import androidx.constraintlayout.motion.widget.KeyAttributes$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.noticenter.NotiCenterPlugin;
import com.android.systemui.statusbar.NotificationShelfManager;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnAfterRenderListListener;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.Invalidator;
import com.android.systemui.statusbar.notification.collection.render.NotifStackController;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class NotilusCoordinator extends Invalidator implements Coordinator {
    public NotilusCoordinator() {
        super("NotilusCoordinator");
    }

    public static boolean calculateClearableNotifStats(List list) {
        Iterator it = list.iterator();
        while (true) {
            boolean z = false;
            if (!it.hasNext()) {
                return false;
            }
            ListEntry listEntry = (ListEntry) it.next();
            NotificationEntry representativeEntry = listEntry.getRepresentativeEntry();
            if (representativeEntry != null) {
                if (representativeEntry.isClearable()) {
                    NotiCenterPlugin notiCenterPlugin = NotiCenterPlugin.INSTANCE;
                    String packageName = representativeEntry.mSbn.getPackageName();
                    notiCenterPlugin.getClass();
                    HashSet hashSet = NotiCenterPlugin.noclearAppList;
                    if (hashSet != null) {
                        z = hashSet.contains(packageName);
                    }
                    if (!z) {
                        return true;
                    }
                }
            } else {
                throw new IllegalStateException(KeyAttributes$$ExternalSyntheticOutline0.m("Null notif entry for ", listEntry.getKey()).toString());
            }
        }
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public final void attach(NotifPipeline notifPipeline) {
        NotiCenterPlugin.INSTANCE.getClass();
        NotiCenterPlugin.mListener = this;
        notifPipeline.addPreRenderInvalidator(this);
        ((ArrayList) notifPipeline.mRenderStageManager.onAfterRenderListListeners).add(new OnAfterRenderListListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.NotilusCoordinator$attach$1
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnAfterRenderListListener
            public final void onAfterRenderList(List list, NotifStackController notifStackController) {
                NotilusCoordinator.this.getClass();
                if (Trace.isTagEnabled(4096L)) {
                    Trace.traceBegin(4096L, "NotilusCoordinator.onAfterRenderList");
                    try {
                        NotiCenterPlugin.INSTANCE.getClass();
                        if (NotiCenterPlugin.isNotiCenterPluginConnected() && NotiCenterPlugin.noclearEnabled) {
                            NotiCenterPlugin.clearableNotifications = NotilusCoordinator.calculateClearableNotifStats(list);
                            ((NotificationShelfManager) Dependency.get(NotificationShelfManager.class)).updateClearAllOnShelf();
                        }
                        Unit unit = Unit.INSTANCE;
                        return;
                    } finally {
                        Trace.traceEnd(4096L);
                    }
                }
                NotiCenterPlugin.INSTANCE.getClass();
                if (NotiCenterPlugin.isNotiCenterPluginConnected() && NotiCenterPlugin.noclearEnabled) {
                    NotiCenterPlugin.clearableNotifications = NotilusCoordinator.calculateClearableNotifStats(list);
                    ((NotificationShelfManager) Dependency.get(NotificationShelfManager.class)).updateClearAllOnShelf();
                }
            }
        });
    }
}
