package com.android.server.appop;

import java.util.function.BiConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class AppOpsUidStateTrackerImpl$$ExternalSyntheticLambda0 implements BiConsumer {
    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        ((AppOpsUidStateTrackerImpl) obj).updateUidPendingStateIfNeeded(((Integer) obj2).intValue());
    }
}
