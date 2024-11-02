package com.android.systemui.keyguard;

import android.net.Uri;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
@DebugMetadata(c = "com.android.systemui.keyguard.CustomizationProvider$delete$1", f = "CustomizationProvider.kt", l = {177}, m = "invokeSuspend")
/* loaded from: classes.dex */
final class CustomizationProvider$delete$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ String[] $selectionArgs;
    final /* synthetic */ Uri $uri;
    int label;
    final /* synthetic */ CustomizationProvider this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CustomizationProvider$delete$1(CustomizationProvider customizationProvider, Uri uri, String[] strArr, Continuation<? super CustomizationProvider$delete$1> continuation) {
        super(2, continuation);
        this.this$0 = customizationProvider;
        this.$uri = uri;
        this.$selectionArgs = strArr;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CustomizationProvider$delete$1(this.this$0, this.$uri, this.$selectionArgs, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CustomizationProvider$delete$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
            CustomizationProvider customizationProvider = this.this$0;
            Uri uri = this.$uri;
            String[] strArr = this.$selectionArgs;
            this.label = 1;
            obj = CustomizationProvider.access$deleteSelection(customizationProvider, uri, strArr, this);
            if (obj == coroutineSingletons) {
                return coroutineSingletons;
            }
        }
        return obj;
    }
}
