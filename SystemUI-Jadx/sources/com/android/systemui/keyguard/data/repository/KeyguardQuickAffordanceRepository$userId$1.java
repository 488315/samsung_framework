package com.android.systemui.keyguard.data.repository;

import android.content.Context;
import com.android.systemui.common.coroutine.ChannelExt;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import java.util.concurrent.Executor;
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
@DebugMetadata(c = "com.android.systemui.keyguard.data.repository.KeyguardQuickAffordanceRepository$userId$1", f = "KeyguardQuickAffordanceRepository.kt", l = {80}, m = "invokeSuspend")
/* loaded from: classes.dex */
public final class KeyguardQuickAffordanceRepository$userId$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ KeyguardQuickAffordanceRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardQuickAffordanceRepository$userId$1(KeyguardQuickAffordanceRepository keyguardQuickAffordanceRepository, Continuation<? super KeyguardQuickAffordanceRepository$userId$1> continuation) {
        super(2, continuation);
        this.this$0 = keyguardQuickAffordanceRepository;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        KeyguardQuickAffordanceRepository$userId$1 keyguardQuickAffordanceRepository$userId$1 = new KeyguardQuickAffordanceRepository$userId$1(this.this$0, continuation);
        keyguardQuickAffordanceRepository$userId$1.L$0 = obj;
        return keyguardQuickAffordanceRepository$userId$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KeyguardQuickAffordanceRepository$userId$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.keyguard.data.repository.KeyguardQuickAffordanceRepository$userId$1$callback$1, com.android.systemui.settings.UserTracker$Callback] */
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
            final ?? r1 = new UserTracker.Callback() { // from class: com.android.systemui.keyguard.data.repository.KeyguardQuickAffordanceRepository$userId$1$callback$1
                @Override // com.android.systemui.settings.UserTracker.Callback
                public final void onUserChanged(int i2, Context context) {
                    ChannelExt.trySendWithFailureLogging$default(ChannelExt.INSTANCE, ProducerScope.this, Integer.valueOf(i2), "KeyguardQuickAffordanceRepository");
                }
            };
            ((UserTrackerImpl) this.this$0.userTracker).addCallback(r1, new Executor() { // from class: com.android.systemui.keyguard.data.repository.KeyguardQuickAffordanceRepository$userId$1.1
                @Override // java.util.concurrent.Executor
                public final void execute(Runnable runnable) {
                    runnable.run();
                }
            });
            ChannelExt.trySendWithFailureLogging$default(ChannelExt.INSTANCE, producerScope, new Integer(((UserTrackerImpl) this.this$0.userTracker).getUserId()), "KeyguardQuickAffordanceRepository");
            final KeyguardQuickAffordanceRepository keyguardQuickAffordanceRepository = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.keyguard.data.repository.KeyguardQuickAffordanceRepository$userId$1.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    ((UserTrackerImpl) KeyguardQuickAffordanceRepository.this.userTracker).removeCallback(r1);
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