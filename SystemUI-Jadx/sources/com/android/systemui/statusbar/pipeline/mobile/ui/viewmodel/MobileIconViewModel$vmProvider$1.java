package com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
@DebugMetadata(c = "com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModel$vmProvider$1", f = "MobileIconViewModel.kt", l = {}, m = "invokeSuspend")
/* loaded from: classes2.dex */
public final class MobileIconViewModel$vmProvider$1 extends SuspendLambda implements Function2 {
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ MobileIconViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MobileIconViewModel$vmProvider$1(MobileIconViewModel mobileIconViewModel, Continuation<? super MobileIconViewModel$vmProvider$1> continuation) {
        super(2, continuation);
        this.this$0 = mobileIconViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MobileIconViewModel$vmProvider$1 mobileIconViewModel$vmProvider$1 = new MobileIconViewModel$vmProvider$1(this.this$0, continuation);
        mobileIconViewModel$vmProvider$1.Z$0 = ((Boolean) obj).booleanValue();
        return mobileIconViewModel$vmProvider$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MobileIconViewModel$vmProvider$1) create(Boolean.valueOf(((Boolean) obj).booleanValue()), (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label == 0) {
            ResultKt.throwOnFailure(obj);
            if (this.Z$0) {
                return (CarrierBasedSatelliteViewModelImpl) this.this$0.satelliteProvider$delegate.getValue();
            }
            return (CellularIconViewModel) this.this$0.cellProvider$delegate.getValue();
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }
}