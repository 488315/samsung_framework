package com.android.systemui.people;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.function.Predicate;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final /* synthetic */ class NotificationHelper$$ExternalSyntheticLambda0 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return NotificationHelper.isMissedCallOrHasContent((NotificationEntry) obj);
    }
}
