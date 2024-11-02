package com.android.systemui.statusbar.pipeline.mobile.domain.interactor;

import android.os.ParcelUuid;
import android.telephony.CarrierConfigManager;
import com.android.systemui.statusbar.pipeline.mobile.data.model.SubscriptionModel;
import com.android.systemui.util.CarrierConfigTracker;
import java.util.Collections;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
@DebugMetadata(c = "com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$filteredSubscriptions$1", f = "MobileIconsInteractor.kt", l = {}, m = "invokeSuspend")
/* loaded from: classes2.dex */
public final class MobileIconsInteractorImpl$filteredSubscriptions$1 extends SuspendLambda implements Function5 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ Object L$2;
    int label;
    final /* synthetic */ MobileIconsInteractorImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MobileIconsInteractorImpl$filteredSubscriptions$1(MobileIconsInteractorImpl mobileIconsInteractorImpl, Continuation<? super MobileIconsInteractorImpl$filteredSubscriptions$1> continuation) {
        super(5, continuation);
        this.this$0 = mobileIconsInteractorImpl;
    }

    @Override // kotlin.jvm.functions.Function5
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        ((Number) obj3).intValue();
        MobileIconsInteractorImpl$filteredSubscriptions$1 mobileIconsInteractorImpl$filteredSubscriptions$1 = new MobileIconsInteractorImpl$filteredSubscriptions$1(this.this$0, (Continuation) obj5);
        mobileIconsInteractorImpl$filteredSubscriptions$1.L$0 = (List) obj;
        mobileIconsInteractorImpl$filteredSubscriptions$1.L$1 = (Integer) obj2;
        mobileIconsInteractorImpl$filteredSubscriptions$1.L$2 = (Integer) obj4;
        return mobileIconsInteractorImpl$filteredSubscriptions$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label == 0) {
            ResultKt.throwOnFailure(obj);
            List list = (List) this.L$0;
            Integer num = (Integer) this.L$1;
            Integer num2 = (Integer) this.L$2;
            if (list.size() != 2) {
                return list;
            }
            SubscriptionModel subscriptionModel = (SubscriptionModel) list.get(0);
            SubscriptionModel subscriptionModel2 = (SubscriptionModel) list.get(1);
            ParcelUuid parcelUuid = subscriptionModel.groupUuid;
            if (parcelUuid != null && Intrinsics.areEqual(parcelUuid, subscriptionModel2.groupUuid)) {
                boolean z = subscriptionModel.isOpportunistic;
                if (!z && !subscriptionModel2.isOpportunistic) {
                    return list;
                }
                CarrierConfigTracker carrierConfigTracker = this.this$0.carrierConfigTracker;
                if (!carrierConfigTracker.mDefaultAlwaysShowPrimarySignalBarInOpportunisticNetworkConfigLoaded) {
                    carrierConfigTracker.mDefaultAlwaysShowPrimarySignalBarInOpportunisticNetworkConfig = CarrierConfigManager.getDefaultConfig().getBoolean("always_show_primary_signal_bar_in_opportunistic_network_boolean");
                    carrierConfigTracker.mDefaultAlwaysShowPrimarySignalBarInOpportunisticNetworkConfigLoaded = true;
                }
                if (carrierConfigTracker.mDefaultAlwaysShowPrimarySignalBarInOpportunisticNetworkConfig) {
                    if (z) {
                        return Collections.singletonList(subscriptionModel2);
                    }
                    return Collections.singletonList(subscriptionModel);
                }
                if (num2 != null) {
                    num = num2;
                }
                if (num != null) {
                    if (subscriptionModel.subscriptionId == num.intValue()) {
                        return Collections.singletonList(subscriptionModel);
                    }
                }
                return Collections.singletonList(subscriptionModel2);
            }
            return list;
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }
}
