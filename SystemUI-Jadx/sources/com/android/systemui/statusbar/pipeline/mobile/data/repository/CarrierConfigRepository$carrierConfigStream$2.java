package com.android.systemui.statusbar.pipeline.mobile.data.repository;

import com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
@DebugMetadata(c = "com.android.systemui.statusbar.pipeline.mobile.data.repository.CarrierConfigRepository$carrierConfigStream$2", f = "CarrierConfigRepository.kt", l = {}, m = "invokeSuspend")
/* loaded from: classes2.dex */
public final class CarrierConfigRepository$carrierConfigStream$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ MobileInputLogger $logger;
    /* synthetic */ int I$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CarrierConfigRepository$carrierConfigStream$2(MobileInputLogger mobileInputLogger, Continuation<? super CarrierConfigRepository$carrierConfigStream$2> continuation) {
        super(2, continuation);
        this.$logger = mobileInputLogger;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        CarrierConfigRepository$carrierConfigStream$2 carrierConfigRepository$carrierConfigStream$2 = new CarrierConfigRepository$carrierConfigStream$2(this.$logger, continuation);
        carrierConfigRepository$carrierConfigStream$2.I$0 = ((Number) obj).intValue();
        return carrierConfigRepository$carrierConfigStream$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CarrierConfigRepository$carrierConfigStream$2) create(Integer.valueOf(((Number) obj).intValue()), (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label == 0) {
            ResultKt.throwOnFailure(obj);
            this.$logger.logCarrierConfigChanged(this.I$0);
            return Unit.INSTANCE;
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }
}
