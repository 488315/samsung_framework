package com.android.systemui.user.domain.interactor;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
@DebugMetadata(c = "com.android.systemui.user.domain.interactor.GuestUserInteractor$exit$1$info$1", f = "GuestUserInteractor.kt", l = {}, m = "invokeSuspend")
/* loaded from: classes2.dex */
final class GuestUserInteractor$exit$1$info$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ int $lastSelectedNonGuestUserHandle;
    int label;
    final /* synthetic */ GuestUserInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GuestUserInteractor$exit$1$info$1(GuestUserInteractor guestUserInteractor, int i, Continuation<? super GuestUserInteractor$exit$1$info$1> continuation) {
        super(2, continuation);
        this.this$0 = guestUserInteractor;
        this.$lastSelectedNonGuestUserHandle = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new GuestUserInteractor$exit$1$info$1(this.this$0, this.$lastSelectedNonGuestUserHandle, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((GuestUserInteractor$exit$1$info$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label == 0) {
            ResultKt.throwOnFailure(obj);
            return this.this$0.manager.getUserInfo(this.$lastSelectedNonGuestUserHandle);
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }
}
