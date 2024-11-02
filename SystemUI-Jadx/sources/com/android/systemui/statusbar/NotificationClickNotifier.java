package com.android.systemui.statusbar;

import com.android.internal.statusbar.IStatusBarService;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class NotificationClickNotifier {
    public final IStatusBarService barService;
    public final List listeners = new ArrayList();
    public final Executor mainExecutor;

    public NotificationClickNotifier(IStatusBarService iStatusBarService, Executor executor) {
        this.barService = iStatusBarService;
        this.mainExecutor = executor;
    }

    public static final void access$notifyListenersAboutInteraction(NotificationClickNotifier notificationClickNotifier, String str) {
        Iterator it = ((ArrayList) notificationClickNotifier.listeners).iterator();
        while (it.hasNext()) {
            ((NotificationInteractionTracker) it.next()).interactions.put(str, Boolean.TRUE);
        }
    }
}
