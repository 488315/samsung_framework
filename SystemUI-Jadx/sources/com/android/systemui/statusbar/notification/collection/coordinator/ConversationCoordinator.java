package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeRenderListListener;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifComparator;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifPromoter;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner;
import com.android.systemui.statusbar.notification.collection.provider.HighPriorityProvider;
import com.android.systemui.statusbar.notification.collection.provider.HighPriorityProvider$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.notification.collection.render.NodeController;
import com.android.systemui.statusbar.notification.icon.ConversationIconManager;
import com.android.systemui.statusbar.notification.icon.IconManager;
import com.android.systemui.statusbar.notification.people.PeopleNotificationIdentifier;
import com.android.systemui.statusbar.notification.people.PeopleNotificationIdentifierImpl;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class ConversationCoordinator implements Coordinator {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ConversationIconManager conversationIconManager;
    public final HighPriorityProvider highPriorityProvider;
    public final PeopleNotificationIdentifier peopleNotificationIdentifier;
    public final Map promotedEntriesToSummaryOfSameChannel = new LinkedHashMap();
    public final ConversationCoordinator$onBeforeRenderListListener$1 onBeforeRenderListListener = new OnBeforeRenderListListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.ConversationCoordinator$onBeforeRenderListListener$1
        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeRenderListListener
        public final void onBeforeRenderList(List list) {
            ConversationCoordinator conversationCoordinator = ConversationCoordinator.this;
            Map map = conversationCoordinator.promotedEntriesToSummaryOfSameChannel;
            ArrayList arrayList = new ArrayList();
            Iterator it = ((LinkedHashMap) map).entrySet().iterator();
            while (true) {
                boolean z = true;
                if (!it.hasNext()) {
                    break;
                }
                Map.Entry entry = (Map.Entry) it.next();
                NotificationEntry notificationEntry = (NotificationEntry) entry.getKey();
                NotificationEntry notificationEntry2 = (NotificationEntry) entry.getValue();
                GroupEntry parent = notificationEntry2.getParent();
                String str = null;
                if (parent != null && !Intrinsics.areEqual(parent, notificationEntry.getParent()) && parent.getParent() != null && Intrinsics.areEqual(parent.mSummary, notificationEntry2)) {
                    List list2 = parent.mUnmodifiableChildren;
                    if (!list2.isEmpty()) {
                        Iterator it2 = list2.iterator();
                        while (it2.hasNext()) {
                            if (Intrinsics.areEqual(((NotificationEntry) it2.next()).getChannel(), notificationEntry2.getChannel())) {
                                break;
                            }
                        }
                    }
                    z = false;
                    if (!z) {
                        str = notificationEntry2.mKey;
                    }
                }
                if (str != null) {
                    arrayList.add(str);
                }
            }
            IconManager iconManager = (IconManager) conversationCoordinator.conversationIconManager;
            iconManager.getClass();
            Set set = CollectionsKt___CollectionsKt.toSet(arrayList);
            boolean z2 = !Intrinsics.areEqual(iconManager.unimportantConversationKeys, set);
            iconManager.unimportantConversationKeys = set;
            if (z2) {
                iconManager.recalculateForImportantConversationChange();
            }
            ((LinkedHashMap) conversationCoordinator.promotedEntriesToSummaryOfSameChannel).clear();
        }
    };
    public final ConversationCoordinator$notificationPromoter$1 notificationPromoter = new NotifPromoter() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.ConversationCoordinator$notificationPromoter$1
        {
            super("ConversationCoordinator");
        }

        /* JADX WARN: Code restructure failed: missing block: B:4:0x000b, code lost:
        
            if (r0.isImportantConversation() == true) goto L8;
         */
        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifPromoter
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final boolean shouldPromoteToTopLevel(com.android.systemui.statusbar.notification.collection.NotificationEntry r5) {
            /*
                r4 = this;
                android.app.NotificationChannel r0 = r5.getChannel()
                if (r0 == 0) goto Le
                boolean r0 = r0.isImportantConversation()
                r1 = 1
                if (r0 != r1) goto Le
                goto Lf
            Le:
                r1 = 0
            Lf:
                if (r1 == 0) goto L32
                com.android.systemui.statusbar.notification.collection.GroupEntry r0 = r5.getParent()
                if (r0 == 0) goto L1a
                com.android.systemui.statusbar.notification.collection.NotificationEntry r0 = r0.mSummary
                goto L1b
            L1a:
                r0 = 0
            L1b:
                if (r0 == 0) goto L32
                android.app.NotificationChannel r2 = r5.getChannel()
                android.app.NotificationChannel r3 = r0.getChannel()
                boolean r2 = kotlin.jvm.internal.Intrinsics.areEqual(r2, r3)
                if (r2 == 0) goto L32
                com.android.systemui.statusbar.notification.collection.coordinator.ConversationCoordinator r4 = com.android.systemui.statusbar.notification.collection.coordinator.ConversationCoordinator.this
                java.util.Map r4 = r4.promotedEntriesToSummaryOfSameChannel
                r4.put(r5, r0)
            L32:
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.collection.coordinator.ConversationCoordinator$notificationPromoter$1.shouldPromoteToTopLevel(com.android.systemui.statusbar.notification.collection.NotificationEntry):boolean");
        }
    };
    public final ConversationCoordinator$peopleAlertingSectioner$1 peopleAlertingSectioner = new NotifSectioner() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.ConversationCoordinator$peopleAlertingSectioner$1
        {
            super("People(alerting)", 7);
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public final NotifComparator getComparator() {
            return ConversationCoordinator.this.notifComparator;
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public final NodeController getHeaderNodeController() {
            int i = ConversationCoordinator.$r8$clinit;
            ConversationCoordinator.this.getClass();
            return null;
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public final boolean isInSection(ListEntry listEntry) {
            boolean z;
            HighPriorityProvider highPriorityProvider = ConversationCoordinator.this.highPriorityProvider;
            highPriorityProvider.getClass();
            NotificationEntry representativeEntry = listEntry.getRepresentativeEntry();
            if (representativeEntry == null) {
                return false;
            }
            if (((PeopleNotificationIdentifierImpl) highPriorityProvider.mPeopleNotificationIdentifier).getPeopleNotificationType(representativeEntry) != 0) {
                z = true;
            } else {
                z = false;
            }
            if (!z) {
                return false;
            }
            if (representativeEntry.mRanking.getImportance() >= 3) {
                return true;
            }
            if (!(listEntry instanceof GroupEntry)) {
                return false;
            }
            return ((GroupEntry) listEntry).mUnmodifiableChildren.stream().anyMatch(new HighPriorityProvider$$ExternalSyntheticLambda0());
        }
    };
    public final ConversationCoordinator$peopleSilentSectioner$1 peopleSilentSectioner = new NotifSectioner() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.ConversationCoordinator$peopleSilentSectioner$1
        {
            super("People(silent)", 7);
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public final NotifComparator getComparator() {
            return ConversationCoordinator.this.notifComparator;
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public final NodeController getHeaderNodeController() {
            int i = ConversationCoordinator.$r8$clinit;
            ConversationCoordinator.this.getClass();
            return null;
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public final boolean isInSection(ListEntry listEntry) {
            int i = ConversationCoordinator.$r8$clinit;
            if (ConversationCoordinator.this.getPeopleType(listEntry) != 0) {
                return true;
            }
            return false;
        }
    };
    public final ConversationCoordinator$notifComparator$1 notifComparator = new NotifComparator() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.ConversationCoordinator$notifComparator$1
        {
            super("People");
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifComparator, java.util.Comparator
        public final int compare(ListEntry listEntry, ListEntry listEntry2) {
            ConversationCoordinator conversationCoordinator = ConversationCoordinator.this;
            int i = ConversationCoordinator.$r8$clinit;
            return Intrinsics.compare(ConversationCoordinator.this.getPeopleType(listEntry2), conversationCoordinator.getPeopleType(listEntry));
        }
    };

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.statusbar.notification.collection.coordinator.ConversationCoordinator$onBeforeRenderListListener$1] */
    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.statusbar.notification.collection.coordinator.ConversationCoordinator$notificationPromoter$1] */
    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.statusbar.notification.collection.coordinator.ConversationCoordinator$peopleAlertingSectioner$1] */
    /* JADX WARN: Type inference failed for: r1v5, types: [com.android.systemui.statusbar.notification.collection.coordinator.ConversationCoordinator$peopleSilentSectioner$1] */
    /* JADX WARN: Type inference failed for: r1v6, types: [com.android.systemui.statusbar.notification.collection.coordinator.ConversationCoordinator$notifComparator$1] */
    public ConversationCoordinator(PeopleNotificationIdentifier peopleNotificationIdentifier, ConversationIconManager conversationIconManager, HighPriorityProvider highPriorityProvider, NodeController nodeController) {
        this.peopleNotificationIdentifier = peopleNotificationIdentifier;
        this.conversationIconManager = conversationIconManager;
        this.highPriorityProvider = highPriorityProvider;
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public final void attach(NotifPipeline notifPipeline) {
        notifPipeline.addPromoter(this.notificationPromoter);
        notifPipeline.addOnBeforeRenderListListener(this.onBeforeRenderListListener);
    }

    public final int getPeopleType(ListEntry listEntry) {
        NotificationEntry representativeEntry = listEntry.getRepresentativeEntry();
        if (representativeEntry != null) {
            return ((PeopleNotificationIdentifierImpl) this.peopleNotificationIdentifier).getPeopleNotificationType(representativeEntry);
        }
        return 0;
    }
}
