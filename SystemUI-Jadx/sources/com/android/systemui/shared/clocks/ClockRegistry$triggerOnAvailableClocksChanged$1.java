package com.android.systemui.shared.clocks;

import com.android.systemui.shared.clocks.ClockRegistry;
import com.android.systemui.util.Assert;
import java.util.Iterator;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
@DebugMetadata(c = "com.android.systemui.shared.clocks.ClockRegistry$triggerOnAvailableClocksChanged$1", f = "ClockRegistry.kt", l = {}, m = "invokeSuspend")
/* loaded from: classes2.dex */
public final class ClockRegistry$triggerOnAvailableClocksChanged$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ ClockRegistry this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ClockRegistry$triggerOnAvailableClocksChanged$1(ClockRegistry clockRegistry, Continuation<? super ClockRegistry$triggerOnAvailableClocksChanged$1> continuation) {
        super(2, continuation);
        this.this$0 = clockRegistry;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ClockRegistry$triggerOnAvailableClocksChanged$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ClockRegistry$triggerOnAvailableClocksChanged$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label == 0) {
            ResultKt.throwOnFailure(obj);
            this.this$0.getClass();
            Assert.isMainThread();
            this.this$0.isClockListChanged.set(false);
            Iterator it = this.this$0.clockChangeListeners.iterator();
            while (it.hasNext()) {
                ((ClockRegistry.ClockChangeListener) it.next()).onAvailableClocksChanged();
            }
            return Unit.INSTANCE;
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }
}