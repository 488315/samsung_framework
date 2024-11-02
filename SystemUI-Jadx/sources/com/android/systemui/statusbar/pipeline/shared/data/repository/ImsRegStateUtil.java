package com.android.systemui.statusbar.pipeline.shared.data.repository;

import android.content.Context;
import com.android.systemui.BasicRune;
import com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator;
import com.android.systemui.statusbar.pipeline.shared.data.model.ImsRegState;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class ImsRegStateUtil {
    public final StateFlowImpl _ePDGConnected;
    public final CarrierInfraMediator carrierInfraMediator;
    public final Context context;
    public final ReadonlyStateFlow ePDGConnected;
    public final Map imsManagers;
    public final ImsRegState imsRegState = new ImsRegState(false, false, false);
    public final List imsRegStateChangedCallbacks;
    public final Map imsRegStates;
    public final CoroutineScope scope;

    public ImsRegStateUtil(Context context, CarrierInfraMediator carrierInfraMediator, CoroutineScope coroutineScope) {
        int i;
        this.context = context;
        this.carrierInfraMediator = carrierInfraMediator;
        this.scope = coroutineScope;
        if (BasicRune.STATUS_NETWORK_MULTI_SIM) {
            i = 2;
        } else {
            i = 1;
        }
        ArrayList arrayList = new ArrayList(i);
        for (int i2 = 0; i2 < i; i2++) {
            arrayList.add(new ArrayList());
        }
        this.imsRegStateChangedCallbacks = arrayList;
        this.imsRegStates = new LinkedHashMap();
        this.imsManagers = new LinkedHashMap();
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(Boolean.valueOf(ePDGConnected()));
        this._ePDGConnected = MutableStateFlow;
        this.ePDGConnected = FlowKt.stateIn(MutableStateFlow, this.scope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion), Boolean.valueOf(ePDGConnected()));
    }

    public final boolean ePDGConnected() {
        Iterator it = this.imsRegStates.entrySet().iterator();
        while (it.hasNext()) {
            if (((ImsRegState) ((Map.Entry) it.next()).getValue()).ePDGRegState) {
                return true;
            }
        }
        return false;
    }

    public final boolean isVoWifiConnected(int i) {
        Map map = this.imsRegStates;
        if (((LinkedHashMap) map).get(Integer.valueOf(i)) != null) {
            Object obj = ((LinkedHashMap) map).get(Integer.valueOf(i));
            Intrinsics.checkNotNull(obj);
            return ((ImsRegState) obj).voWifiRegState;
        }
        return false;
    }
}
