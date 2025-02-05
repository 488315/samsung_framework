package com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel;

import com.android.systemui.statusbar.pipeline.mobile.data.model.SubscriptionModel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
@DebugMetadata(c = "com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconsViewModel$subscriptionIdsFlow$1", f = "MobileIconsViewModel.kt", l = {}, m = "invokeSuspend")
/* loaded from: classes2.dex */
public final class MobileIconsViewModel$subscriptionIdsFlow$1 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;

    public MobileIconsViewModel$subscriptionIdsFlow$1(Continuation<? super MobileIconsViewModel$subscriptionIdsFlow$1> continuation) {
        super(2, continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MobileIconsViewModel$subscriptionIdsFlow$1 mobileIconsViewModel$subscriptionIdsFlow$1 = new MobileIconsViewModel$subscriptionIdsFlow$1(continuation);
        mobileIconsViewModel$subscriptionIdsFlow$1.L$0 = obj;
        return mobileIconsViewModel$subscriptionIdsFlow$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MobileIconsViewModel$subscriptionIdsFlow$1) create((List) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label == 0) {
            ResultKt.throwOnFailure(obj);
            List list = (List) this.L$0;
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
            Iterator it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(new Integer(((SubscriptionModel) it.next()).subscriptionId));
            }
            return arrayList;
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }
}
