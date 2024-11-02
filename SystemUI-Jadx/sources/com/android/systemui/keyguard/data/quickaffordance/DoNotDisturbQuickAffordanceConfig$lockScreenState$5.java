package com.android.systemui.keyguard.data.quickaffordance;

import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
@DebugMetadata(c = "com.android.systemui.keyguard.data.quickaffordance.DoNotDisturbQuickAffordanceConfig$lockScreenState$5", f = "DoNotDisturbQuickAffordanceConfig.kt", l = {}, m = "invokeSuspend")
/* loaded from: classes.dex */
public final class DoNotDisturbQuickAffordanceConfig$lockScreenState$5 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    int label;

    public DoNotDisturbQuickAffordanceConfig$lockScreenState$5(Continuation<? super DoNotDisturbQuickAffordanceConfig$lockScreenState$5> continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        ((Number) obj2).intValue();
        DoNotDisturbQuickAffordanceConfig$lockScreenState$5 doNotDisturbQuickAffordanceConfig$lockScreenState$5 = new DoNotDisturbQuickAffordanceConfig$lockScreenState$5((Continuation) obj3);
        doNotDisturbQuickAffordanceConfig$lockScreenState$5.L$0 = (KeyguardQuickAffordanceConfig.LockScreenState) obj;
        return doNotDisturbQuickAffordanceConfig$lockScreenState$5.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label == 0) {
            ResultKt.throwOnFailure(obj);
            return (KeyguardQuickAffordanceConfig.LockScreenState) this.L$0;
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }
}
