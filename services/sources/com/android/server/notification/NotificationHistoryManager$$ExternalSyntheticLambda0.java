package com.android.server.notification;

import android.app.NotificationHistory;
import android.util.Slog;
import com.android.internal.util.FunctionalUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class NotificationHistoryManager$$ExternalSyntheticLambda0 implements FunctionalUtils.ThrowingRunnable {
    public final /* synthetic */ NotificationHistoryManager f$0;
    public final /* synthetic */ NotificationHistory.HistoricalNotification f$1;

    public /* synthetic */ NotificationHistoryManager$$ExternalSyntheticLambda0(NotificationHistoryManager notificationHistoryManager, NotificationHistory.HistoricalNotification historicalNotification) {
        this.f$0 = notificationHistoryManager;
        this.f$1 = historicalNotification;
    }

    public final void runOrThrow() {
        NotificationHistoryManager notificationHistoryManager = this.f$0;
        NotificationHistory.HistoricalNotification historicalNotification = this.f$1;
        synchronized (notificationHistoryManager.mLock) {
            try {
                NotificationHistoryDatabase userHistoryAndInitializeIfNeededLocked = notificationHistoryManager.getUserHistoryAndInitializeIfNeededLocked(historicalNotification.getUserId());
                if (userHistoryAndInitializeIfNeededLocked != null) {
                    userHistoryAndInitializeIfNeededLocked.addNotification(historicalNotification);
                    return;
                }
                Slog.w("NotificationHistory", "Attempted to add notif for locked/gone/disabled user " + historicalNotification.getUserId());
            } finally {
            }
        }
    }
}
