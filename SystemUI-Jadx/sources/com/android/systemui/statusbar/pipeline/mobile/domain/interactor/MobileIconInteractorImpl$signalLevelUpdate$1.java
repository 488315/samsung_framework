package com.android.systemui.statusbar.pipeline.mobile.domain.interactor;

import com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
@DebugMetadata(c = "com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$signalLevelUpdate$1", f = "MobileIconInteractor.kt", l = {564}, m = "invokeSuspend")
/* loaded from: classes2.dex */
public final class MobileIconInteractorImpl$signalLevelUpdate$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ MobileIconInteractorImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MobileIconInteractorImpl$signalLevelUpdate$1(MobileIconInteractorImpl mobileIconInteractorImpl, Continuation<? super MobileIconInteractorImpl$signalLevelUpdate$1> continuation) {
        super(2, continuation);
        this.this$0 = mobileIconInteractorImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MobileIconInteractorImpl$signalLevelUpdate$1 mobileIconInteractorImpl$signalLevelUpdate$1 = new MobileIconInteractorImpl$signalLevelUpdate$1(this.this$0, continuation);
        mobileIconInteractorImpl$signalLevelUpdate$1.L$0 = obj;
        return mobileIconInteractorImpl$signalLevelUpdate$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MobileIconInteractorImpl$signalLevelUpdate$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
            ProducerScope producerScope = (ProducerScope) this.L$0;
            MobileIconInteractorImpl$signalLevelUpdate$1$mSignalUpdateCallback$1 mobileIconInteractorImpl$signalLevelUpdate$1$mSignalUpdateCallback$1 = new MobileIconInteractorImpl$signalLevelUpdate$1$mSignalUpdateCallback$1(this.this$0, producerScope);
            MobileIconInteractorImpl mobileIconInteractorImpl = this.this$0;
            if (mobileIconInteractorImpl.carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.CHANGE_SIGNAL_ONE_LEVEL_PER_SEC, mobileIconInteractorImpl.slotId, new Object[0])) {
                this.this$0.mobileSignalTransition.updateCallback = mobileIconInteractorImpl$signalLevelUpdate$1$mSignalUpdateCallback$1;
            }
            AnonymousClass1 anonymousClass1 = new Function0() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$signalLevelUpdate$1.1
                @Override // kotlin.jvm.functions.Function0
                public final /* bridge */ /* synthetic */ Object invoke() {
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, anonymousClass1, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        }
        return Unit.INSTANCE;
    }
}
