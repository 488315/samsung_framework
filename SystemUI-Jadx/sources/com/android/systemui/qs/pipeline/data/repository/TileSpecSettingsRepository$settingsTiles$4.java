package com.android.systemui.qs.pipeline.data.repository;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
@DebugMetadata(c = "com.android.systemui.qs.pipeline.data.repository.TileSpecSettingsRepository$settingsTiles$4", f = "TileSpecRepository.kt", l = {}, m = "invokeSuspend")
/* loaded from: classes2.dex */
final class TileSpecSettingsRepository$settingsTiles$4 extends SuspendLambda implements Function2 {
    final /* synthetic */ int $userId;
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ TileSpecSettingsRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TileSpecSettingsRepository$settingsTiles$4(TileSpecSettingsRepository tileSpecSettingsRepository, int i, Continuation<? super TileSpecSettingsRepository$settingsTiles$4> continuation) {
        super(2, continuation);
        this.this$0 = tileSpecSettingsRepository;
        this.$userId = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        TileSpecSettingsRepository$settingsTiles$4 tileSpecSettingsRepository$settingsTiles$4 = new TileSpecSettingsRepository$settingsTiles$4(this.this$0, this.$userId, continuation);
        tileSpecSettingsRepository$settingsTiles$4.L$0 = obj;
        return tileSpecSettingsRepository$settingsTiles$4;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((TileSpecSettingsRepository$settingsTiles$4) create((String) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label == 0) {
            ResultKt.throwOnFailure(obj);
            this.this$0.logger.logTilesChangedInSettings(this.$userId, (String) this.L$0);
            return Unit.INSTANCE;
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }
}
