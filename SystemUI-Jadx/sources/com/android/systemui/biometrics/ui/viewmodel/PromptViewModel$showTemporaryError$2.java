package com.android.systemui.biometrics.ui.viewmodel;

import com.android.systemui.biometrics.domain.model.BiometricModality;
import com.android.systemui.biometrics.ui.viewmodel.PromptMessage;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.flow.StateFlowImpl;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
@DebugMetadata(c = "com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$showTemporaryError$2", f = "PromptViewModel.kt", l = {}, m = "invokeSuspend")
/* loaded from: classes.dex */
public final class PromptViewModel$showTemporaryError$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ boolean $authenticateAfterError;
    final /* synthetic */ BiometricModality $failedModality;
    final /* synthetic */ String $message;
    final /* synthetic */ String $messageAfterError;
    final /* synthetic */ boolean $suppressIfErrorShowing;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ PromptViewModel this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    @DebugMetadata(c = "com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$showTemporaryError$2$1", f = "PromptViewModel.kt", l = {IKnoxCustomManager.Stub.TRANSACTION_removeFavoriteApp, IKnoxCustomManager.Stub.TRANSACTION_getZeroPageState}, m = "invokeSuspend")
    /* renamed from: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$showTemporaryError$2$1, reason: invalid class name */
    /* loaded from: classes.dex */
    public final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ boolean $authenticateAfterError;
        final /* synthetic */ String $messageAfterError;
        int label;
        final /* synthetic */ PromptViewModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(boolean z, PromptViewModel promptViewModel, String str, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$authenticateAfterError = z;
            this.this$0 = promptViewModel;
            this.$messageAfterError = str;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$authenticateAfterError, this.this$0, this.$messageAfterError, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i != 0) {
                if (i != 1) {
                    if (i == 2) {
                        ResultKt.throwOnFailure(obj);
                        return Unit.INSTANCE;
                    }
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            } else {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                if (DelayKt.delay(2000L, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            }
            if (this.$authenticateAfterError) {
                PromptViewModel.showAuthenticating$default(this.this$0, this.$messageAfterError, false, 2);
            } else {
                PromptViewModel promptViewModel = this.this$0;
                String str = this.$messageAfterError;
                this.label = 2;
                if (promptViewModel.showHelp(str) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PromptViewModel$showTemporaryError$2(PromptViewModel promptViewModel, boolean z, BiometricModality biometricModality, String str, boolean z2, String str2, Continuation<? super PromptViewModel$showTemporaryError$2> continuation) {
        super(2, continuation);
        this.this$0 = promptViewModel;
        this.$suppressIfErrorShowing = z;
        this.$failedModality = biometricModality;
        this.$message = str;
        this.$authenticateAfterError = z2;
        this.$messageAfterError = str2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        PromptViewModel$showTemporaryError$2 promptViewModel$showTemporaryError$2 = new PromptViewModel$showTemporaryError$2(this.this$0, this.$suppressIfErrorShowing, this.$failedModality, this.$message, this.$authenticateAfterError, this.$messageAfterError, continuation);
        promptViewModel$showTemporaryError$2.L$0 = obj;
        return promptViewModel$showTemporaryError$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((PromptViewModel$showTemporaryError$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label == 0) {
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            if (((PromptAuthState) this.this$0._isAuthenticated.getValue()).isAuthenticated) {
                return Unit.INSTANCE;
            }
            boolean z = false;
            if (((PromptMessage) this.this$0._message.getValue()).isErrorOrHelp() && this.$suppressIfErrorShowing) {
                PromptViewModel promptViewModel = this.this$0;
                BiometricModality biometricModality = this.$failedModality;
                if (!((PromptAuthState) promptViewModel._isAuthenticated.getValue()).isAuthenticated) {
                    if (biometricModality == BiometricModality.Face) {
                        z = true;
                    }
                    promptViewModel._canTryAgainNow.setValue(Boolean.valueOf(z));
                }
                return Unit.INSTANCE;
            }
            this.this$0._isAuthenticating.setValue(Boolean.FALSE);
            this.this$0._isAuthenticated.setValue(new PromptAuthState(false, null, false, 0L, 14, null));
            this.this$0._forceMediumSize.setValue(Boolean.TRUE);
            StateFlowImpl stateFlowImpl = this.this$0._canTryAgainNow;
            if (this.$failedModality == BiometricModality.Face) {
                z = true;
            }
            stateFlowImpl.setValue(Boolean.valueOf(z));
            this.this$0._message.setValue(new PromptMessage.Error(this.$message));
            this.this$0._legacyState.setValue(new Integer(4));
            Job job = this.this$0.messageJob;
            if (job != null) {
                job.cancel(null);
            }
            PromptViewModel promptViewModel2 = this.this$0;
            promptViewModel2.messageJob = BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass1(this.$authenticateAfterError, promptViewModel2, this.$messageAfterError, null), 3);
            return Unit.INSTANCE;
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }
}
