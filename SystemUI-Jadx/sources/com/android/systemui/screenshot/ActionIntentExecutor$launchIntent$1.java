package com.android.systemui.screenshot;

import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
@DebugMetadata(c = "com.android.systemui.screenshot.ActionIntentExecutor", f = "ActionIntentExecutor.kt", l = {74, 77, 79}, m = "launchIntent")
/* loaded from: classes2.dex */
public final class ActionIntentExecutor$launchIntent$1 extends ContinuationImpl {
    int I$0;
    Object L$0;
    Object L$1;
    Object L$2;
    boolean Z$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ ActionIntentExecutor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ActionIntentExecutor$launchIntent$1(ActionIntentExecutor actionIntentExecutor, Continuation<? super ActionIntentExecutor$launchIntent$1> continuation) {
        super(continuation);
        this.this$0 = actionIntentExecutor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
        return this.this$0.launchIntent(null, null, 0, false, this);
    }
}