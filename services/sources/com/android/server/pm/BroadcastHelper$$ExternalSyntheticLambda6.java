package com.android.server.pm;

import android.os.Bundle;
import java.util.function.BiFunction;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class BroadcastHelper$$ExternalSyntheticLambda6 implements BiFunction {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Computer f$0;

    public /* synthetic */ BroadcastHelper$$ExternalSyntheticLambda6(Computer computer, int i) {
        this.$r8$classId = i;
        this.f$0 = computer;
    }

    @Override // java.util.function.BiFunction
    public final Object apply(Object obj, Object obj2) {
        int i = this.$r8$classId;
        Computer computer = this.f$0;
        Integer num = (Integer) obj;
        Bundle bundle = (Bundle) obj2;
        switch (i) {
        }
        return BroadcastHelper.filterExtrasChangedPackageList(computer, num.intValue(), bundle);
    }
}
