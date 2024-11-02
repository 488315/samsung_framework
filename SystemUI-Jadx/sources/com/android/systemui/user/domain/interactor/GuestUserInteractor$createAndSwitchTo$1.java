package com.android.systemui.user.domain.interactor;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
@DebugMetadata(c = "com.android.systemui.user.domain.interactor.GuestUserInteractor$createAndSwitchTo$1", f = "GuestUserInteractor.kt", l = {110}, m = "invokeSuspend")
/* loaded from: classes2.dex */
public final class GuestUserInteractor$createAndSwitchTo$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Function0 $dismissDialog;
    final /* synthetic */ Function1 $selectUser;
    final /* synthetic */ Function1 $showDialog;
    int label;
    final /* synthetic */ GuestUserInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GuestUserInteractor$createAndSwitchTo$1(GuestUserInteractor guestUserInteractor, Function1 function1, Function0 function0, Function1 function12, Continuation<? super GuestUserInteractor$createAndSwitchTo$1> continuation) {
        super(2, continuation);
        this.this$0 = guestUserInteractor;
        this.$showDialog = function1;
        this.$dismissDialog = function0;
        this.$selectUser = function12;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new GuestUserInteractor$createAndSwitchTo$1(this.this$0, this.$showDialog, this.$dismissDialog, this.$selectUser, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((GuestUserInteractor$createAndSwitchTo$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
            GuestUserInteractor guestUserInteractor = this.this$0;
            Function1 function1 = this.$showDialog;
            Function0 function0 = this.$dismissDialog;
            this.label = 1;
            int i2 = GuestUserInteractor.$r8$clinit;
            guestUserInteractor.getClass();
            obj = BuildersKt.withContext(guestUserInteractor.mainDispatcher, new GuestUserInteractor$create$2(function1, guestUserInteractor, function0, null), this);
            if (obj == coroutineSingletons) {
                return coroutineSingletons;
            }
        }
        int intValue = ((Number) obj).intValue();
        if (intValue != -10000) {
            this.$selectUser.invoke(new Integer(intValue));
        }
        return Unit.INSTANCE;
    }
}
