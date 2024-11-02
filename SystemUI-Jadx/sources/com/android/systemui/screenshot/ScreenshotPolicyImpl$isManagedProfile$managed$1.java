package com.android.systemui.screenshot;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
@DebugMetadata(c = "com.android.systemui.screenshot.ScreenshotPolicyImpl$isManagedProfile$managed$1", f = "ScreenshotPolicyImpl.kt", l = {}, m = "invokeSuspend")
/* loaded from: classes2.dex */
public final class ScreenshotPolicyImpl$isManagedProfile$managed$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ int $userId;
    int label;
    final /* synthetic */ ScreenshotPolicyImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ScreenshotPolicyImpl$isManagedProfile$managed$1(ScreenshotPolicyImpl screenshotPolicyImpl, int i, Continuation<? super ScreenshotPolicyImpl$isManagedProfile$managed$1> continuation) {
        super(2, continuation);
        this.this$0 = screenshotPolicyImpl;
        this.$userId = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ScreenshotPolicyImpl$isManagedProfile$managed$1(this.this$0, this.$userId, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ScreenshotPolicyImpl$isManagedProfile$managed$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label == 0) {
            ResultKt.throwOnFailure(obj);
            return Boolean.valueOf(this.this$0.userMgr.isManagedProfile(this.$userId));
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }
}
