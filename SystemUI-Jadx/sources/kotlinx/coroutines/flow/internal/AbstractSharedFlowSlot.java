package kotlinx.coroutines.flow.internal;

import kotlin.coroutines.Continuation;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes3.dex */
public abstract class AbstractSharedFlowSlot {
    public abstract boolean allocateLocked(AbstractSharedFlow abstractSharedFlow);

    public abstract Continuation[] freeLocked(AbstractSharedFlow abstractSharedFlow);
}
