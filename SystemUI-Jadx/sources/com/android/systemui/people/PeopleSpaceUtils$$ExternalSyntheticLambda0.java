package com.android.systemui.people;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final /* synthetic */ class PeopleSpaceUtils$$ExternalSyntheticLambda0 implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return ((Set) ((Map.Entry) obj).getValue()).stream();
    }
}
