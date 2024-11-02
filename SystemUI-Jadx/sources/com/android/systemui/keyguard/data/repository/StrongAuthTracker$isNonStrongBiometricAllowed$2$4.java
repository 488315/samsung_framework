package com.android.systemui.keyguard.data.repository;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
@DebugMetadata(c = "com.android.systemui.keyguard.data.repository.StrongAuthTracker$isNonStrongBiometricAllowed$2$4", f = "BiometricSettingsRepository.kt", l = {363}, m = "invokeSuspend")
/* loaded from: classes.dex */
public final class StrongAuthTracker$isNonStrongBiometricAllowed$2$4 extends SuspendLambda implements Function2 {
    final /* synthetic */ int $userId;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ StrongAuthTracker this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public StrongAuthTracker$isNonStrongBiometricAllowed$2$4(StrongAuthTracker strongAuthTracker, int i, Continuation<? super StrongAuthTracker$isNonStrongBiometricAllowed$2$4> continuation) {
        super(2, continuation);
        this.this$0 = strongAuthTracker;
        this.$userId = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        StrongAuthTracker$isNonStrongBiometricAllowed$2$4 strongAuthTracker$isNonStrongBiometricAllowed$2$4 = new StrongAuthTracker$isNonStrongBiometricAllowed$2$4(this.this$0, this.$userId, continuation);
        strongAuthTracker$isNonStrongBiometricAllowed$2$4.L$0 = obj;
        return strongAuthTracker$isNonStrongBiometricAllowed$2$4;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((StrongAuthTracker$isNonStrongBiometricAllowed$2$4) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i != 0) {
            if (i == 1) {
                ResultKt.throwOnFailure(obj);
            } else {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        } else {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Boolean valueOf = Boolean.valueOf(this.this$0.isNonStrongBiometricAllowedAfterIdleTimeout(this.$userId));
            this.label = 1;
            if (flowCollector.emit(valueOf, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        }
        return Unit.INSTANCE;
    }
}
