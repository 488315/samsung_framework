package com.android.systemui.statusbar.notification.collection.coordinator;

import android.os.Handler;
import android.os.SystemClock;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.RemoteInputController;
import com.android.systemui.statusbar.RemoteInputNotificationRebuilder;
import com.android.systemui.statusbar.SmartReplyController;
import com.android.systemui.statusbar.notification.collection.NotifCollection;
import com.android.systemui.statusbar.notification.collection.NotifCollection$$ExternalSyntheticLambda4;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import com.android.systemui.statusbar.notification.collection.notifcollection.SelfTrackingLifetimeExtender;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class RemoteInputCoordinator implements Coordinator, Dumpable {
    public final RemoteInputCoordinator$mCollectionListener$1 mCollectionListener;
    public final Handler mMainHandler;
    public NotifCollection$$ExternalSyntheticLambda4 mNotifUpdater;
    public final NotificationRemoteInputManager mNotificationRemoteInputManager;
    public final RemoteInputNotificationRebuilder mRebuilder;
    public final RemoteInputActiveExtender mRemoteInputActiveExtender;
    public final RemoteInputHistoryExtender mRemoteInputHistoryExtender;
    public final List mRemoteInputLifetimeExtenders;
    public final SmartReplyController mSmartReplyController;
    public final SmartReplyHistoryExtender mSmartReplyHistoryExtender;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public final class RemoteInputActiveExtender extends SelfTrackingLifetimeExtender {
        public RemoteInputActiveExtender() {
            super("RemoteInputCoordinator", "RemoteInputActive", RemoteInputCoordinatorKt.access$getDEBUG(), RemoteInputCoordinator.this.mMainHandler);
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.SelfTrackingLifetimeExtender
        public final boolean queryShouldExtendLifetime(NotificationEntry notificationEntry) {
            RemoteInputController remoteInputController = RemoteInputCoordinator.this.mNotificationRemoteInputManager.mRemoteInputController;
            if (remoteInputController != null && remoteInputController.pruneWeakThenRemoveAndContains(notificationEntry, null, null)) {
                return true;
            }
            return false;
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public final class RemoteInputHistoryExtender extends SelfTrackingLifetimeExtender {
        public RemoteInputHistoryExtender() {
            super("RemoteInputCoordinator", "RemoteInputHistory", RemoteInputCoordinatorKt.access$getDEBUG(), RemoteInputCoordinator.this.mMainHandler);
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.SelfTrackingLifetimeExtender
        public final void onStartedLifetimeExtension(NotificationEntry notificationEntry) {
            RemoteInputCoordinator remoteInputCoordinator = RemoteInputCoordinator.this;
            RemoteInputNotificationRebuilder remoteInputNotificationRebuilder = remoteInputCoordinator.mRebuilder;
            remoteInputNotificationRebuilder.getClass();
            CharSequence charSequence = notificationEntry.remoteInputText;
            if (TextUtils.isEmpty(charSequence)) {
                charSequence = notificationEntry.remoteInputTextWhenReset;
            }
            StatusBarNotification rebuildWithRemoteInputInserted = remoteInputNotificationRebuilder.rebuildWithRemoteInputInserted(notificationEntry, charSequence, false, notificationEntry.remoteInputMimeType, notificationEntry.remoteInputUri);
            notificationEntry.lastRemoteInputSent = -2000L;
            NotifCollection$$ExternalSyntheticLambda4 notifCollection$$ExternalSyntheticLambda4 = null;
            notificationEntry.remoteInputTextWhenReset = null;
            NotifCollection$$ExternalSyntheticLambda4 notifCollection$$ExternalSyntheticLambda42 = remoteInputCoordinator.mNotifUpdater;
            if (notifCollection$$ExternalSyntheticLambda42 != null) {
                notifCollection$$ExternalSyntheticLambda4 = notifCollection$$ExternalSyntheticLambda42;
            }
            notifCollection$$ExternalSyntheticLambda4.onInternalNotificationUpdate("Extending lifetime of notification with remote input", rebuildWithRemoteInputInserted);
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.SelfTrackingLifetimeExtender
        public final boolean queryShouldExtendLifetime(NotificationEntry notificationEntry) {
            boolean z;
            boolean z2;
            NotificationRemoteInputManager notificationRemoteInputManager = RemoteInputCoordinator.this.mNotificationRemoteInputManager;
            notificationRemoteInputManager.getClass();
            if (!NotificationRemoteInputManager.FORCE_REMOTE_INPUT_HISTORY) {
                return false;
            }
            String str = notificationEntry.mKey;
            RemoteInputController remoteInputController = notificationRemoteInputManager.mRemoteInputController;
            if (remoteInputController != null && remoteInputController.mSpinning.containsKey(str)) {
                z = true;
            } else {
                z = false;
            }
            if (!z) {
                if (SystemClock.elapsedRealtime() < notificationEntry.lastRemoteInputSent + 500) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                if (!z2) {
                    return false;
                }
            }
            return true;
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public final class SmartReplyHistoryExtender extends SelfTrackingLifetimeExtender {
        public SmartReplyHistoryExtender() {
            super("RemoteInputCoordinator", "SmartReplyHistory", RemoteInputCoordinatorKt.access$getDEBUG(), RemoteInputCoordinator.this.mMainHandler);
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.SelfTrackingLifetimeExtender
        public final void onCanceledLifetimeExtension(NotificationEntry notificationEntry) {
            RemoteInputCoordinator.this.mSmartReplyController.stopSending(notificationEntry);
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.SelfTrackingLifetimeExtender
        public final void onStartedLifetimeExtension(NotificationEntry notificationEntry) {
            RemoteInputCoordinator remoteInputCoordinator = RemoteInputCoordinator.this;
            StatusBarNotification rebuildWithRemoteInputInserted = remoteInputCoordinator.mRebuilder.rebuildWithRemoteInputInserted(notificationEntry, null, false, null, null);
            remoteInputCoordinator.mSmartReplyController.stopSending(notificationEntry);
            NotifCollection$$ExternalSyntheticLambda4 notifCollection$$ExternalSyntheticLambda4 = remoteInputCoordinator.mNotifUpdater;
            if (notifCollection$$ExternalSyntheticLambda4 == null) {
                notifCollection$$ExternalSyntheticLambda4 = null;
            }
            notifCollection$$ExternalSyntheticLambda4.onInternalNotificationUpdate("Extending lifetime of notification with smart reply", rebuildWithRemoteInputInserted);
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.SelfTrackingLifetimeExtender
        public final boolean queryShouldExtendLifetime(NotificationEntry notificationEntry) {
            NotificationRemoteInputManager notificationRemoteInputManager = RemoteInputCoordinator.this.mNotificationRemoteInputManager;
            notificationRemoteInputManager.getClass();
            if (!NotificationRemoteInputManager.FORCE_REMOTE_INPUT_HISTORY) {
                return false;
            }
            return ((ArraySet) notificationRemoteInputManager.mSmartReplyController.mSendingKeys).contains(notificationEntry.mKey);
        }
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.statusbar.notification.collection.coordinator.RemoteInputCoordinator$mCollectionListener$1] */
    public RemoteInputCoordinator(DumpManager dumpManager, RemoteInputNotificationRebuilder remoteInputNotificationRebuilder, NotificationRemoteInputManager notificationRemoteInputManager, Handler handler, SmartReplyController smartReplyController) {
        this.mRebuilder = remoteInputNotificationRebuilder;
        this.mNotificationRemoteInputManager = notificationRemoteInputManager;
        this.mMainHandler = handler;
        this.mSmartReplyController = smartReplyController;
        RemoteInputHistoryExtender remoteInputHistoryExtender = new RemoteInputHistoryExtender();
        this.mRemoteInputHistoryExtender = remoteInputHistoryExtender;
        SmartReplyHistoryExtender smartReplyHistoryExtender = new SmartReplyHistoryExtender();
        this.mSmartReplyHistoryExtender = smartReplyHistoryExtender;
        RemoteInputActiveExtender remoteInputActiveExtender = new RemoteInputActiveExtender();
        this.mRemoteInputActiveExtender = remoteInputActiveExtender;
        this.mRemoteInputLifetimeExtenders = CollectionsKt__CollectionsKt.listOf(remoteInputHistoryExtender, smartReplyHistoryExtender, remoteInputActiveExtender);
        dumpManager.registerDumpable(this);
        this.mCollectionListener = new NotifCollectionListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.RemoteInputCoordinator$mCollectionListener$1
            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public final void onEntryRemoved(NotificationEntry notificationEntry, int i) {
                boolean booleanValue;
                booleanValue = ((Boolean) RemoteInputCoordinatorKt.DEBUG$delegate.getValue()).booleanValue();
                if (booleanValue) {
                    ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder("mCollectionListener.onEntryRemoved(entry="), notificationEntry.mKey, ")", "RemoteInputCoordinator");
                }
                RemoteInputCoordinator remoteInputCoordinator = RemoteInputCoordinator.this;
                remoteInputCoordinator.mSmartReplyController.stopSending(notificationEntry);
                boolean z = true;
                if (i == 1 || i == 2) {
                    NotificationRemoteInputManager notificationRemoteInputManager2 = remoteInputCoordinator.mNotificationRemoteInputManager;
                    RemoteInputController remoteInputController = notificationRemoteInputManager2.mRemoteInputController;
                    if (remoteInputController == null || !remoteInputController.pruneWeakThenRemoveAndContains(notificationEntry, null, null)) {
                        z = false;
                    }
                    if (z) {
                        notificationEntry.mRemoteEditImeVisible = false;
                        notificationRemoteInputManager2.mRemoteInputController.removeRemoteInput(notificationEntry, null);
                    }
                }
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public final void onEntryUpdated(NotificationEntry notificationEntry, boolean z) {
                boolean booleanValue;
                booleanValue = ((Boolean) RemoteInputCoordinatorKt.DEBUG$delegate.getValue()).booleanValue();
                if (booleanValue) {
                    Log.d("RemoteInputCoordinator", "mCollectionListener.onEntryUpdated(entry=" + notificationEntry.mKey + ", fromSystem=" + z + ")");
                }
                if (z) {
                    RemoteInputCoordinator.this.mSmartReplyController.stopSending(notificationEntry);
                }
            }
        };
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public final void attach(NotifPipeline notifPipeline) {
        NotificationRemoteInputManager notificationRemoteInputManager = this.mNotificationRemoteInputManager;
        if (notificationRemoteInputManager.mRemoteInputListener == null) {
            notificationRemoteInputManager.mRemoteInputListener = this;
            if (notificationRemoteInputManager.mRemoteInputController != null) {
                this.mSmartReplyController.mCallback = new RemoteInputCoordinator$setRemoteInputController$1(this);
            }
            Iterator it = this.mRemoteInputLifetimeExtenders.iterator();
            while (it.hasNext()) {
                notifPipeline.addNotificationLifetimeExtender((SelfTrackingLifetimeExtender) it.next());
            }
            NotifCollection notifCollection = notifPipeline.mNotifCollection;
            notifCollection.getClass();
            this.mNotifUpdater = new NotifCollection$$ExternalSyntheticLambda4(notifCollection, "RemoteInputCoordinator");
            notifPipeline.addCollectionListener(this.mCollectionListener);
            return;
        }
        throw new IllegalStateException("mRemoteInputListener is already set");
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        Iterator it = this.mRemoteInputLifetimeExtenders.iterator();
        while (it.hasNext()) {
            ((SelfTrackingLifetimeExtender) it.next()).dump(printWriter, strArr);
        }
    }

    public static /* synthetic */ void getMRemoteInputActiveExtender$annotations() {
    }

    public static /* synthetic */ void getMRemoteInputHistoryExtender$annotations() {
    }

    public static /* synthetic */ void getMSmartReplyHistoryExtender$annotations() {
    }
}
