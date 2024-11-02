package com.android.systemui.statusbar;

import com.android.systemui.plugins.statusbar.StatusBarStateController;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
@DebugMetadata(c = "com.android.systemui.statusbar.StatusBarStateControllerExtKt$expansionChanges$1", f = "StatusBarStateControllerExt.kt", l = {35}, m = "invokeSuspend")
/* loaded from: classes2.dex */
final class StatusBarStateControllerExtKt$expansionChanges$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ StatusBarStateController $this_expansionChanges;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public StatusBarStateControllerExtKt$expansionChanges$1(StatusBarStateController statusBarStateController, Continuation<? super StatusBarStateControllerExtKt$expansionChanges$1> continuation) {
        super(2, continuation);
        this.$this_expansionChanges = statusBarStateController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        StatusBarStateControllerExtKt$expansionChanges$1 statusBarStateControllerExtKt$expansionChanges$1 = new StatusBarStateControllerExtKt$expansionChanges$1(this.$this_expansionChanges, continuation);
        statusBarStateControllerExtKt$expansionChanges$1.L$0 = obj;
        return statusBarStateControllerExtKt$expansionChanges$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((StatusBarStateControllerExtKt$expansionChanges$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.statusbar.StatusBarStateControllerExtKt$expansionChanges$1$listener$1, com.android.systemui.plugins.statusbar.StatusBarStateController$StateListener] */
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
            final ?? r1 = new StatusBarStateController.StateListener() { // from class: com.android.systemui.statusbar.StatusBarStateControllerExtKt$expansionChanges$1$listener$1
                @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
                public final void onExpandedChanged(boolean z) {
                    ((ChannelCoroutine) ProducerScope.this).mo2584trySendJP2dKIU(Boolean.valueOf(z));
                }
            };
            ((ChannelCoroutine) producerScope).mo2584trySendJP2dKIU(Boolean.valueOf(this.$this_expansionChanges.isExpanded()));
            this.$this_expansionChanges.addCallback(r1);
            final StatusBarStateController statusBarStateController = this.$this_expansionChanges;
            Function0 function0 = new Function0() { // from class: com.android.systemui.statusbar.StatusBarStateControllerExtKt$expansionChanges$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    StatusBarStateController.this.removeCallback(r1);
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
