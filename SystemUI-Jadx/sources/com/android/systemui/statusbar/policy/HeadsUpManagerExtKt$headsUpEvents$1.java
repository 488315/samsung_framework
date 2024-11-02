package com.android.systemui.statusbar.policy;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import kotlin.Pair;
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
@DebugMetadata(c = "com.android.systemui.statusbar.policy.HeadsUpManagerExtKt$headsUpEvents$1", f = "HeadsUpManagerExt.kt", l = {37}, m = "invokeSuspend")
/* loaded from: classes2.dex */
final class HeadsUpManagerExtKt$headsUpEvents$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ HeadsUpManager $this_headsUpEvents;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public HeadsUpManagerExtKt$headsUpEvents$1(HeadsUpManager headsUpManager, Continuation<? super HeadsUpManagerExtKt$headsUpEvents$1> continuation) {
        super(2, continuation);
        this.$this_headsUpEvents = headsUpManager;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        HeadsUpManagerExtKt$headsUpEvents$1 headsUpManagerExtKt$headsUpEvents$1 = new HeadsUpManagerExtKt$headsUpEvents$1(this.$this_headsUpEvents, continuation);
        headsUpManagerExtKt$headsUpEvents$1.L$0 = obj;
        return headsUpManagerExtKt$headsUpEvents$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((HeadsUpManagerExtKt$headsUpEvents$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.statusbar.policy.HeadsUpManagerExtKt$headsUpEvents$1$listener$1, com.android.systemui.statusbar.policy.OnHeadsUpChangedListener] */
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
            final ?? r1 = new OnHeadsUpChangedListener() { // from class: com.android.systemui.statusbar.policy.HeadsUpManagerExtKt$headsUpEvents$1$listener$1
                @Override // com.android.systemui.statusbar.policy.OnHeadsUpChangedListener
                public final void onHeadsUpStateChanged(NotificationEntry notificationEntry, boolean z) {
                    ((ChannelCoroutine) ProducerScope.this).mo2584trySendJP2dKIU(new Pair(notificationEntry, Boolean.valueOf(z)));
                }
            };
            this.$this_headsUpEvents.addListener(r1);
            final HeadsUpManager headsUpManager = this.$this_headsUpEvents;
            Function0 function0 = new Function0() { // from class: com.android.systemui.statusbar.policy.HeadsUpManagerExtKt$headsUpEvents$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    HeadsUpManager headsUpManager2 = HeadsUpManager.this;
                    headsUpManager2.mListeners.remove(r1);
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
