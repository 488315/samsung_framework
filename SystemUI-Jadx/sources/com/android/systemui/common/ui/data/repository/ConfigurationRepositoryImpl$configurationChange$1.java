package com.android.systemui.common.ui.data.repository;

import android.content.res.Configuration;
import com.android.systemui.common.coroutine.ChannelExt;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
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
@DebugMetadata(c = "com.android.systemui.common.ui.data.repository.ConfigurationRepositoryImpl$configurationChange$1", f = "ConfigurationRepository.kt", l = {93}, m = "invokeSuspend")
/* loaded from: classes.dex */
public final class ConfigurationRepositoryImpl$configurationChange$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ ConfigurationRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ConfigurationRepositoryImpl$configurationChange$1(ConfigurationRepositoryImpl configurationRepositoryImpl, Continuation<? super ConfigurationRepositoryImpl$configurationChange$1> continuation) {
        super(2, continuation);
        this.this$0 = configurationRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        ConfigurationRepositoryImpl$configurationChange$1 configurationRepositoryImpl$configurationChange$1 = new ConfigurationRepositoryImpl$configurationChange$1(this.this$0, continuation);
        configurationRepositoryImpl$configurationChange$1.L$0 = obj;
        return configurationRepositoryImpl$configurationChange$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ConfigurationRepositoryImpl$configurationChange$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.common.ui.data.repository.ConfigurationRepositoryImpl$configurationChange$1$callback$1, java.lang.Object] */
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
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final ?? r1 = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.common.ui.data.repository.ConfigurationRepositoryImpl$configurationChange$1$callback$1
                @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
                public final void onConfigChanged(Configuration configuration) {
                    ChannelExt.trySendWithFailureLogging$default(ChannelExt.INSTANCE, ProducerScope.this, Unit.INSTANCE, "ConfigurationRepository#onConfigChanged");
                }
            };
            ((ConfigurationControllerImpl) this.this$0.configurationController).addCallback(r1);
            final ConfigurationRepositoryImpl configurationRepositoryImpl = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.common.ui.data.repository.ConfigurationRepositoryImpl$configurationChange$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    ((ConfigurationControllerImpl) ConfigurationRepositoryImpl.this.configurationController).removeCallback(r1);
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, function0, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        }
        return Unit.INSTANCE;
    }
}
