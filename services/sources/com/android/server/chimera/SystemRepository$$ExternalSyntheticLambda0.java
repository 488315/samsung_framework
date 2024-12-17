package com.android.server.chimera;

import android.accessibilityservice.AccessibilityServiceInfo;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class SystemRepository$$ExternalSyntheticLambda0 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        AccessibilityServiceInfo accessibilityServiceInfo = (AccessibilityServiceInfo) obj;
        return (accessibilityServiceInfo == null || accessibilityServiceInfo.getResolveInfo() == null || accessibilityServiceInfo.getResolveInfo().serviceInfo == null) ? false : true;
    }
}
