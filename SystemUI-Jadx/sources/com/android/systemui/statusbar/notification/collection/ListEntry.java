package com.android.systemui.statusbar.notification.collection;

import com.android.systemui.statusbar.notification.collection.listbuilder.NotifSection;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public abstract class ListEntry {
    public final long mCreationTime;
    public final String mKey;
    public final ListAttachState mPreviousAttachState = ListAttachState.create();
    public final ListAttachState mAttachState = ListAttachState.create();

    public ListEntry(String str, long j) {
        this.mKey = str;
        this.mCreationTime = j;
    }

    public static void checkNull(ListAttachState listAttachState) {
        if (listAttachState == null) {
            ListAttachState.create();
        }
    }

    public final void beginNewAttachState() {
        ListAttachState listAttachState = this.mAttachState;
        checkNull(listAttachState);
        ListAttachState listAttachState2 = this.mPreviousAttachState;
        checkNull(listAttachState2);
        listAttachState2.getClass();
        listAttachState2.parent = listAttachState.parent;
        listAttachState2.section = listAttachState.section;
        listAttachState2.excludingFilter = listAttachState.excludingFilter;
        listAttachState2.promoter = listAttachState.promoter;
        listAttachState2.groupPruneReason = listAttachState.groupPruneReason;
        SuppressedAttachState suppressedAttachState = listAttachState2.suppressedChanges;
        suppressedAttachState.getClass();
        SuppressedAttachState suppressedAttachState2 = listAttachState.suppressedChanges;
        suppressedAttachState.parent = suppressedAttachState2.parent;
        suppressedAttachState.section = suppressedAttachState2.section;
        suppressedAttachState.wasPruneSuppressed = suppressedAttachState2.wasPruneSuppressed;
        listAttachState2.stableIndex = listAttachState.stableIndex;
        listAttachState.parent = null;
        listAttachState.section = null;
        listAttachState.excludingFilter = null;
        listAttachState.promoter = null;
        listAttachState.groupPruneReason = null;
        suppressedAttachState2.parent = null;
        suppressedAttachState2.section = null;
        suppressedAttachState2.wasPruneSuppressed = false;
        listAttachState.stableIndex = -1;
    }

    public String getKey() {
        return this.mKey;
    }

    public final GroupEntry getParent() {
        ListAttachState listAttachState = this.mAttachState;
        checkNull(listAttachState);
        return listAttachState.parent;
    }

    public abstract NotificationEntry getRepresentativeEntry();

    public final NotifSection getSection() {
        ListAttachState listAttachState = this.mAttachState;
        checkNull(listAttachState);
        return listAttachState.section;
    }

    public final void setParent(GroupEntry groupEntry) {
        ListAttachState listAttachState = this.mAttachState;
        checkNull(listAttachState);
        listAttachState.parent = groupEntry;
    }

    public final boolean wasAttachedInPreviousPass() {
        ListAttachState listAttachState = this.mPreviousAttachState;
        checkNull(listAttachState);
        if (listAttachState.parent != null) {
            return true;
        }
        return false;
    }
}
