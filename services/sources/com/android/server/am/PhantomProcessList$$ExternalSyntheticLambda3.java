package com.android.server.am;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class PhantomProcessList$$ExternalSyntheticLambda3 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ((PhantomProcessRecord) obj).killLocked("Trimming Orphan processes", true);
    }
}
