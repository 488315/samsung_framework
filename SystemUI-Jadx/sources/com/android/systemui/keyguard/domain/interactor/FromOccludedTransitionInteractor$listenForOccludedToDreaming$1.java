package com.android.systemui.keyguard.domain.interactor;

import android.animation.ValueAnimator;
import com.android.app.animation.Interpolators;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.TransitionInfo;
import com.android.systemui.util.kotlin.FlowKt;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;
import kotlin.time.Duration;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.SafeFlow;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
@DebugMetadata(c = "com.android.systemui.keyguard.domain.interactor.FromOccludedTransitionInteractor$listenForOccludedToDreaming$1", f = "FromOccludedTransitionInteractor.kt", l = {59}, m = "invokeSuspend")
/* loaded from: classes.dex */
final class FromOccludedTransitionInteractor$listenForOccludedToDreaming$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ FromOccludedTransitionInteractor this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* renamed from: com.android.systemui.keyguard.domain.interactor.FromOccludedTransitionInteractor$listenForOccludedToDreaming$1$2, reason: invalid class name */
    /* loaded from: classes.dex */
    public final /* synthetic */ class AnonymousClass2 extends AdaptedFunctionReference implements Function3 {
        public static final AnonymousClass2 INSTANCE = new AnonymousClass2();

        public AnonymousClass2() {
            super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            return new Pair(Boolean.valueOf(((Boolean) obj).booleanValue()), (KeyguardState) obj2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FromOccludedTransitionInteractor$listenForOccludedToDreaming$1(FromOccludedTransitionInteractor fromOccludedTransitionInteractor, Continuation<? super FromOccludedTransitionInteractor$listenForOccludedToDreaming$1> continuation) {
        super(2, continuation);
        this.this$0 = fromOccludedTransitionInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new FromOccludedTransitionInteractor$listenForOccludedToDreaming$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FromOccludedTransitionInteractor$listenForOccludedToDreaming$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
            FromOccludedTransitionInteractor fromOccludedTransitionInteractor = this.this$0;
            SafeFlow sample = FlowKt.sample(fromOccludedTransitionInteractor.keyguardInteractor.isAbleToDream, fromOccludedTransitionInteractor.keyguardTransitionInteractor.finishedKeyguardState, AnonymousClass2.INSTANCE);
            final FromOccludedTransitionInteractor fromOccludedTransitionInteractor2 = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.domain.interactor.FromOccludedTransitionInteractor$listenForOccludedToDreaming$1.3
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    KeyguardState keyguardState;
                    Pair pair = (Pair) obj2;
                    boolean booleanValue = ((Boolean) pair.component1()).booleanValue();
                    KeyguardState keyguardState2 = (KeyguardState) pair.component2();
                    if (booleanValue && keyguardState2 == (keyguardState = KeyguardState.OCCLUDED)) {
                        FromOccludedTransitionInteractor fromOccludedTransitionInteractor3 = FromOccludedTransitionInteractor.this;
                        KeyguardTransitionRepository keyguardTransitionRepository = fromOccludedTransitionInteractor3.keyguardTransitionRepository;
                        KeyguardState keyguardState3 = KeyguardState.DREAMING;
                        ValueAnimator valueAnimator = new ValueAnimator();
                        valueAnimator.setInterpolator(Interpolators.LINEAR);
                        valueAnimator.setDuration(Duration.m2575getInWholeMillisecondsimpl(FromOccludedTransitionInteractor.DEFAULT_DURATION));
                        ((KeyguardTransitionRepositoryImpl) keyguardTransitionRepository).startTransition(new TransitionInfo(fromOccludedTransitionInteractor3.name, keyguardState, keyguardState3, valueAnimator), false);
                    }
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (sample.collect(flowCollector, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        }
        return Unit.INSTANCE;
    }
}
