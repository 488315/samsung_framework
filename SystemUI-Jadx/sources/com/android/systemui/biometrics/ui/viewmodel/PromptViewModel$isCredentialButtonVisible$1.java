package com.android.systemui.biometrics.ui.viewmodel;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
@DebugMetadata(c = "com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$isCredentialButtonVisible$1", f = "PromptViewModel.kt", l = {}, m = "invokeSuspend")
/* loaded from: classes.dex */
public final class PromptViewModel$isCredentialButtonVisible$1 extends SuspendLambda implements Function4 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ boolean Z$0;
    int label;

    public PromptViewModel$isCredentialButtonVisible$1(Continuation<? super PromptViewModel$isCredentialButtonVisible$1> continuation) {
        super(4, continuation);
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        boolean booleanValue = ((Boolean) obj3).booleanValue();
        PromptViewModel$isCredentialButtonVisible$1 promptViewModel$isCredentialButtonVisible$1 = new PromptViewModel$isCredentialButtonVisible$1((Continuation) obj4);
        promptViewModel$isCredentialButtonVisible$1.L$0 = (PromptSize) obj;
        promptViewModel$isCredentialButtonVisible$1.L$1 = (PromptAuthState) obj2;
        promptViewModel$isCredentialButtonVisible$1.Z$0 = booleanValue;
        return promptViewModel$isCredentialButtonVisible$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x001f, code lost:
    
        if (r1 != false) goto L11;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r2) {
        /*
            r1 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r0 = r1.label
            if (r0 != 0) goto L28
            kotlin.ResultKt.throwOnFailure(r2)
            java.lang.Object r2 = r1.L$0
            com.android.systemui.biometrics.ui.viewmodel.PromptSize r2 = (com.android.systemui.biometrics.ui.viewmodel.PromptSize) r2
            java.lang.Object r0 = r1.L$1
            com.android.systemui.biometrics.ui.viewmodel.PromptAuthState r0 = (com.android.systemui.biometrics.ui.viewmodel.PromptAuthState) r0
            boolean r1 = r1.Z$0
            boolean r2 = com.android.systemui.biometrics.ui.viewmodel.PromptSizeKt.isNotSmall(r2)
            if (r2 == 0) goto L22
            boolean r2 = r0.isAuthenticated
            r0 = 1
            r2 = r2 ^ r0
            if (r2 == 0) goto L22
            if (r1 == 0) goto L22
            goto L23
        L22:
            r0 = 0
        L23:
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r0)
            return r1
        L28:
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r1.<init>(r2)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$isCredentialButtonVisible$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
