package com.android.systemui.biometrics.domain.interactor;

import android.hardware.biometrics.IBiometricContextListener;
import android.os.IBinder;
import android.util.Log;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ErrorsKt$catch$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
@DebugMetadata(c = "com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl$addBiometricContextListener$1", f = "LogContextInteractor.kt", l = {}, m = "invokeSuspend")
/* loaded from: classes.dex */
public final class LogContextInteractorImpl$addBiometricContextListener$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ IBiometricContextListener $listener;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ LogContextInteractorImpl this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    @DebugMetadata(c = "com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl$addBiometricContextListener$1$1", f = "LogContextInteractor.kt", l = {}, m = "invokeSuspend")
    /* renamed from: com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl$addBiometricContextListener$1$1, reason: invalid class name */
    /* loaded from: classes.dex */
    public final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ IBiometricContextListener $listener;
        /* synthetic */ int I$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(IBiometricContextListener iBiometricContextListener, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$listener = iBiometricContextListener;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$listener, continuation);
            anonymousClass1.I$0 = ((Number) obj).intValue();
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create(Integer.valueOf(((Number) obj).intValue()), (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label == 0) {
                ResultKt.throwOnFailure(obj);
                this.$listener.onFoldChanged(this.I$0);
                return Unit.INSTANCE;
            }
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    @DebugMetadata(c = "com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl$addBiometricContextListener$1$2", f = "LogContextInteractor.kt", l = {}, m = "invokeSuspend")
    /* renamed from: com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl$addBiometricContextListener$1$2, reason: invalid class name */
    /* loaded from: classes.dex */
    public final class AnonymousClass2 extends SuspendLambda implements Function3 {
        /* synthetic */ Object L$0;
        int label;

        public AnonymousClass2(Continuation<? super AnonymousClass2> continuation) {
            super(3, continuation);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2((Continuation) obj3);
            anonymousClass2.L$0 = (Throwable) obj2;
            return anonymousClass2.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label == 0) {
                ResultKt.throwOnFailure(obj);
                Log.w("ContextRepositoryImpl", "failed to notify new fold state", (Throwable) this.L$0);
                return Unit.INSTANCE;
            }
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    @DebugMetadata(c = "com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl$addBiometricContextListener$1$3", f = "LogContextInteractor.kt", l = {}, m = "invokeSuspend")
    /* renamed from: com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl$addBiometricContextListener$1$3, reason: invalid class name */
    /* loaded from: classes.dex */
    public final class AnonymousClass3 extends SuspendLambda implements Function2 {
        final /* synthetic */ IBiometricContextListener $listener;
        /* synthetic */ int I$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass3(IBiometricContextListener iBiometricContextListener, Continuation<? super AnonymousClass3> continuation) {
            super(2, continuation);
            this.$listener = iBiometricContextListener;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass3 anonymousClass3 = new AnonymousClass3(this.$listener, continuation);
            anonymousClass3.I$0 = ((Number) obj).intValue();
            return anonymousClass3;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass3) create(Integer.valueOf(((Number) obj).intValue()), (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label == 0) {
                ResultKt.throwOnFailure(obj);
                this.$listener.onDisplayStateChanged(this.I$0);
                return Unit.INSTANCE;
            }
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    @DebugMetadata(c = "com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl$addBiometricContextListener$1$4", f = "LogContextInteractor.kt", l = {}, m = "invokeSuspend")
    /* renamed from: com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl$addBiometricContextListener$1$4, reason: invalid class name */
    /* loaded from: classes.dex */
    public final class AnonymousClass4 extends SuspendLambda implements Function3 {
        /* synthetic */ Object L$0;
        int label;

        public AnonymousClass4(Continuation<? super AnonymousClass4> continuation) {
            super(3, continuation);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            AnonymousClass4 anonymousClass4 = new AnonymousClass4((Continuation) obj3);
            anonymousClass4.L$0 = (Throwable) obj2;
            return anonymousClass4.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label == 0) {
                ResultKt.throwOnFailure(obj);
                Log.w("ContextRepositoryImpl", "failed to notify new display state", (Throwable) this.L$0);
                return Unit.INSTANCE;
            }
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LogContextInteractorImpl$addBiometricContextListener$1(LogContextInteractorImpl logContextInteractorImpl, IBiometricContextListener iBiometricContextListener, Continuation<? super LogContextInteractorImpl$addBiometricContextListener$1> continuation) {
        super(2, continuation);
        this.this$0 = logContextInteractorImpl;
        this.$listener = iBiometricContextListener;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        LogContextInteractorImpl$addBiometricContextListener$1 logContextInteractorImpl$addBiometricContextListener$1 = new LogContextInteractorImpl$addBiometricContextListener$1(this.this$0, this.$listener, continuation);
        logContextInteractorImpl$addBiometricContextListener$1.L$0 = obj;
        return logContextInteractorImpl$addBiometricContextListener$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((LogContextInteractorImpl$addBiometricContextListener$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label == 0) {
            ResultKt.throwOnFailure(obj);
            final CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            FlowKt.launchIn(new FlowKt__ErrorsKt$catch$$inlined$unsafeFlow$1(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(this.this$0.foldState, new AnonymousClass1(this.$listener, null)), new AnonymousClass2(null)), coroutineScope);
            FlowKt.launchIn(new FlowKt__ErrorsKt$catch$$inlined$unsafeFlow$1(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.distinctUntilChanged(this.this$0.displayState), new AnonymousClass3(this.$listener, null)), new AnonymousClass4(null)), coroutineScope);
            this.$listener.asBinder().linkToDeath(new IBinder.DeathRecipient() { // from class: com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl$addBiometricContextListener$1.5
                @Override // android.os.IBinder.DeathRecipient
                public final void binderDied() {
                    CoroutineScopeKt.cancel$default(CoroutineScope.this);
                }
            }, 0);
            return Unit.INSTANCE;
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }
}
