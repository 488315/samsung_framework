package com.android.server.power;

import com.android.server.power.PowerHistorian;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class PowerHistorian$$ExternalSyntheticLambda0 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return PowerHistorian.WakeUpRecord.class.isInstance((PowerHistorian.Record) obj);
    }
}
