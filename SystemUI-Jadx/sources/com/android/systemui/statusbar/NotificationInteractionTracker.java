package com.android.systemui.statusbar;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import com.android.systemui.util.Assert;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class NotificationInteractionTracker implements NotifCollectionListener {
    public final Map interactions = new LinkedHashMap();

    public NotificationInteractionTracker(NotificationClickNotifier notificationClickNotifier) {
        Assert.isMainThread();
        ((ArrayList) notificationClickNotifier.listeners).add(this);
    }

    public final boolean hasUserInteractedWith(String str) {
        Boolean bool = (Boolean) ((LinkedHashMap) this.interactions).get(str);
        if (bool != null) {
            return bool.booleanValue();
        }
        return false;
    }

    @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
    public final void onEntryAdded(NotificationEntry notificationEntry) {
        this.interactions.put(notificationEntry.mKey, Boolean.FALSE);
    }

    @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
    public final void onEntryCleanUp(NotificationEntry notificationEntry) {
        this.interactions.remove(notificationEntry.mKey);
    }
}
