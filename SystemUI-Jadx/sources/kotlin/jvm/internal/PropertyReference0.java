package kotlin.jvm.internal;

import kotlin.reflect.KCallable;
import kotlin.reflect.KProperty0;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes3.dex */
public abstract class PropertyReference0 extends PropertyReference implements KProperty0 {
    public PropertyReference0() {
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KCallable computeReflected() {
        Reflection.factory.getClass();
        return this;
    }

    @Override // kotlin.reflect.KProperty0
    public final void getGetter() {
        ((KProperty0) getReflected()).getGetter();
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        return get();
    }

    public PropertyReference0(Object obj) {
        super(obj);
    }

    public PropertyReference0(Object obj, Class cls, String str, String str2, int i) {
        super(obj, cls, str, str2, i);
    }
}
