package com.android.systemui.user.domain.interactor;

import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
@DebugMetadata(c = "com.android.systemui.user.domain.interactor.UserInteractor", f = "UserInteractor.kt", l = {621}, m = "onBroadcastReceived")
/* loaded from: classes2.dex */
public final class UserInteractor$onBroadcastReceived$1 extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ UserInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UserInteractor$onBroadcastReceived$1(UserInteractor userInteractor, Continuation<? super UserInteractor$onBroadcastReceived$1> continuation) {
        super(continuation);
        this.this$0 = userInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
        return UserInteractor.access$onBroadcastReceived(this.this$0, null, null, this);
    }
}
