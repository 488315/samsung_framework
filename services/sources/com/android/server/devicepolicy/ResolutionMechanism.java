package com.android.server.devicepolicy;

import android.app.admin.PolicyValue;
import java.util.LinkedHashMap;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class ResolutionMechanism {
    public abstract android.app.admin.ResolutionMechanism getParcelableResolutionMechanism();

    public abstract PolicyValue resolve(LinkedHashMap linkedHashMap);

    public PolicyValue resolve(List list) {
        throw new UnsupportedOperationException();
    }
}
