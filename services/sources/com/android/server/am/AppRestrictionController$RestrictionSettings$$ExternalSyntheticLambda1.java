package com.android.server.am;

import com.android.server.am.AppRestrictionController;
import java.util.function.ToIntFunction;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class AppRestrictionController$RestrictionSettings$$ExternalSyntheticLambda1 implements ToIntFunction {
    @Override // java.util.function.ToIntFunction
    public final int applyAsInt(Object obj) {
        return ((AppRestrictionController.RestrictionSettings.PkgSettings) obj).mUid;
    }
}
