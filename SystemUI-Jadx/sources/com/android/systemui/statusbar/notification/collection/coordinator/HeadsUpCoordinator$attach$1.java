package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator;
import com.android.systemui.util.time.SystemClockImpl;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final /* synthetic */ class HeadsUpCoordinator$attach$1 {
    public final /* synthetic */ HeadsUpCoordinator $tmp0;

    public HeadsUpCoordinator$attach$1(HeadsUpCoordinator headsUpCoordinator) {
        this.$tmp0 = headsUpCoordinator;
    }

    public final void onBeforeTransformGroups() {
        final HeadsUpCoordinator headsUpCoordinator = this.$tmp0;
        ((SystemClockImpl) headsUpCoordinator.mSystemClock).getClass();
        headsUpCoordinator.mNow = System.currentTimeMillis();
        if (!headsUpCoordinator.mPostedEntries.isEmpty()) {
            HeadsUpCoordinatorKt.access$modifyHuns(headsUpCoordinator.mHeadsUpManager, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$onBeforeTransformGroups$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    HunMutatorImpl hunMutatorImpl = (HunMutatorImpl) obj;
                    List<HeadsUpCoordinator.PostedEntry> list = CollectionsKt___CollectionsKt.toList(HeadsUpCoordinator.this.mPostedEntries.values());
                    HeadsUpCoordinator headsUpCoordinator2 = HeadsUpCoordinator.this;
                    for (HeadsUpCoordinator.PostedEntry postedEntry : list) {
                        if (!postedEntry.entry.mSbn.isGroup()) {
                            HeadsUpCoordinator.access$handlePostedEntry(headsUpCoordinator2, postedEntry, hunMutatorImpl, "non-group");
                            headsUpCoordinator2.mPostedEntries.remove(postedEntry.key);
                        }
                    }
                    return Unit.INSTANCE;
                }
            });
        }
    }
}
