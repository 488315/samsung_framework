package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import android.telephony.TelephonyCallback;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class MobileConnectionsRepositoryImpl$activeMobileDataSubscriptionId$1$callback$1 extends TelephonyCallback implements TelephonyCallback.ActiveDataSubscriptionIdListener {
    public final /* synthetic */ ProducerScope $$this$conflatedCallbackFlow;

    public MobileConnectionsRepositoryImpl$activeMobileDataSubscriptionId$1$callback$1(ProducerScope producerScope) {
        this.$$this$conflatedCallbackFlow = producerScope;
    }

    @Override // android.telephony.TelephonyCallback.ActiveDataSubscriptionIdListener
    public final void onActiveDataSubscriptionIdChanged(int i) {
        if (i != -1) {
            ((ChannelCoroutine) this.$$this$conflatedCallbackFlow).mo2584trySendJP2dKIU(Integer.valueOf(i));
            return;
        }
        ((ChannelCoroutine) this.$$this$conflatedCallbackFlow).mo2584trySendJP2dKIU(null);
    }
}
