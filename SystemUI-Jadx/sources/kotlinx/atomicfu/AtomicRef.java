package kotlinx.atomicfu;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.atomicfu.TraceBase;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes3.dex */
public final class AtomicRef {
    public static final AtomicReferenceFieldUpdater FU;
    public final TraceBase trace;
    public volatile Object value;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes3.dex */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
        FU = AtomicReferenceFieldUpdater.newUpdater(AtomicRef.class, Object.class, "value");
    }

    public AtomicRef(Object obj, TraceBase traceBase) {
        this.trace = traceBase;
        this.value = obj;
    }

    public final boolean compareAndSet(Object obj, Object obj2) {
        boolean compareAndSet = FU.compareAndSet(this, obj, obj2);
        if (compareAndSet) {
            TraceBase.None none = TraceBase.None.INSTANCE;
            TraceBase traceBase = this.trace;
            if (traceBase != none) {
                Objects.toString(obj);
                Objects.toString(obj2);
                traceBase.getClass();
            }
        }
        return compareAndSet;
    }

    public final Object getAndSet(Object obj) {
        Object andSet = FU.getAndSet(this, obj);
        TraceBase.None none = TraceBase.None.INSTANCE;
        TraceBase traceBase = this.trace;
        if (traceBase != none) {
            Objects.toString(obj);
            Objects.toString(andSet);
            traceBase.getClass();
        }
        return andSet;
    }

    public final void lazySet(Object obj) {
        FU.lazySet(this, obj);
        TraceBase.None none = TraceBase.None.INSTANCE;
        TraceBase traceBase = this.trace;
        if (traceBase != none) {
            Objects.toString(obj);
            traceBase.getClass();
        }
    }

    public final void setValue(Object obj) {
        this.value = obj;
        TraceBase traceBase = this.trace;
        if (traceBase != TraceBase.None.INSTANCE) {
            Objects.toString(obj);
            traceBase.getClass();
        }
    }

    public final String toString() {
        return String.valueOf(this.value);
    }
}
