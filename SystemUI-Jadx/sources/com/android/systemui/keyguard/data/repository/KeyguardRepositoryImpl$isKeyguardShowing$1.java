package com.android.systemui.keyguard.data.repository;

import com.android.systemui.common.coroutine.ChannelExt;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.samsung.android.knox.custom.IKnoxCustomManager;
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
@DebugMetadata(c = "com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl$isKeyguardShowing$1", f = "KeyguardRepository.kt", l = {IKnoxCustomManager.Stub.TRANSACTION_removeFavoriteApp}, m = "invokeSuspend")
/* loaded from: classes.dex */
public final class KeyguardRepositoryImpl$isKeyguardShowing$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ KeyguardRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardRepositoryImpl$isKeyguardShowing$1(KeyguardRepositoryImpl keyguardRepositoryImpl, Continuation<? super KeyguardRepositoryImpl$isKeyguardShowing$1> continuation) {
        super(2, continuation);
        this.this$0 = keyguardRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        KeyguardRepositoryImpl$isKeyguardShowing$1 keyguardRepositoryImpl$isKeyguardShowing$1 = new KeyguardRepositoryImpl$isKeyguardShowing$1(this.this$0, continuation);
        keyguardRepositoryImpl$isKeyguardShowing$1.L$0 = obj;
        return keyguardRepositoryImpl$isKeyguardShowing$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KeyguardRepositoryImpl$isKeyguardShowing$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl$isKeyguardShowing$1$callback$1, java.lang.Object] */
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
            final KeyguardRepositoryImpl keyguardRepositoryImpl = this.this$0;
            final ?? r1 = new KeyguardStateController.Callback() { // from class: com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl$isKeyguardShowing$1$callback$1
                @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
                public final void onKeyguardShowingChanged() {
                    ChannelExt channelExt = ChannelExt.INSTANCE;
                    Boolean valueOf = Boolean.valueOf(((KeyguardStateControllerImpl) keyguardRepositoryImpl.keyguardStateController).mShowing);
                    channelExt.getClass();
                    ChannelExt.trySendWithFailureLogging(ProducerScope.this, valueOf, "KeyguardRepositoryImpl", "updated isKeyguardShowing");
                }
            };
            ((KeyguardStateControllerImpl) this.this$0.keyguardStateController).addCallback(r1);
            ChannelExt channelExt = ChannelExt.INSTANCE;
            Boolean valueOf = Boolean.valueOf(((KeyguardStateControllerImpl) this.this$0.keyguardStateController).mShowing);
            channelExt.getClass();
            ChannelExt.trySendWithFailureLogging(producerScope, valueOf, "KeyguardRepositoryImpl", "initial isKeyguardShowing");
            final KeyguardRepositoryImpl keyguardRepositoryImpl2 = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl$isKeyguardShowing$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    ((KeyguardStateControllerImpl) KeyguardRepositoryImpl.this.keyguardStateController).removeCallback(r1);
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
