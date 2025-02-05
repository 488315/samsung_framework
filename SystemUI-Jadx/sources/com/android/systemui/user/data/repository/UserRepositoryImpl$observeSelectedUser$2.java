package com.android.systemui.user.data.repository;

import android.content.pm.UserInfo;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
@DebugMetadata(c = "com.android.systemui.user.data.repository.UserRepositoryImpl$observeSelectedUser$2", f = "UserRepository.kt", l = {}, m = "invokeSuspend")
/* loaded from: classes2.dex */
public final class UserRepositoryImpl$observeSelectedUser$2 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ UserRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UserRepositoryImpl$observeSelectedUser$2(UserRepositoryImpl userRepositoryImpl, Continuation<? super UserRepositoryImpl$observeSelectedUser$2> continuation) {
        super(2, continuation);
        this.this$0 = userRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        UserRepositoryImpl$observeSelectedUser$2 userRepositoryImpl$observeSelectedUser$2 = new UserRepositoryImpl$observeSelectedUser$2(this.this$0, continuation);
        userRepositoryImpl$observeSelectedUser$2.L$0 = obj;
        return userRepositoryImpl$observeSelectedUser$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((UserRepositoryImpl$observeSelectedUser$2) create((UserInfo) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label == 0) {
            ResultKt.throwOnFailure(obj);
            UserInfo userInfo = (UserInfo) this.L$0;
            if (!userInfo.isGuest()) {
                this.this$0.lastSelectedNonGuestUserId = userInfo.id;
            }
            this.this$0._selectedUserInfo.setValue(userInfo);
            return Unit.INSTANCE;
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }
}
