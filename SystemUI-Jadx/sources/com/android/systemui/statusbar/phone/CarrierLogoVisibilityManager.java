package com.android.systemui.statusbar.phone;

import android.telephony.ServiceState;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import com.android.systemui.controls.ui.util.ControlExtension$Companion$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator;
import com.android.systemui.statusbar.pipeline.mobile.data.model.SimType;
import com.android.systemui.statusbar.pipeline.mobile.util.SimCardInfoUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class CarrierLogoVisibilityManager {
    public final CarrierInfraMediator carrierInfraMediator;
    public boolean featureEnabled;
    public final CarrierInfraMediator.Conditions featureName;
    public boolean matchedSim;
    public boolean networkCondition;
    public final SimCardInfoUtil simCardInfoUtil;
    public int defaultSubscriptionSlotId = SubscriptionManager.getSlotIndex(SubscriptionManager.getDefaultSubscriptionId());
    public final HashMap serviceStateHash = new HashMap();
    public boolean settingEnabled = true;
    public boolean quickStarEnabled = true;
    public ArrayList simTypes = new ArrayList();

    public CarrierLogoVisibilityManager(CarrierInfraMediator carrierInfraMediator, CarrierInfraMediator.Conditions conditions, SimCardInfoUtil simCardInfoUtil, TelephonyManager telephonyManager) {
        boolean z;
        this.carrierInfraMediator = carrierInfraMediator;
        this.featureName = conditions;
        this.simCardInfoUtil = simCardInfoUtil;
        if (telephonyManager.getServiceState() != null) {
            ServiceState serviceState = telephonyManager.getServiceState();
            Intrinsics.checkNotNull(serviceState);
            if (serviceState.getState() == 0) {
                ServiceState serviceState2 = telephonyManager.getServiceState();
                Intrinsics.checkNotNull(serviceState2);
                if (!serviceState2.getRoaming()) {
                    z = true;
                    this.networkCondition = z;
                    this.featureEnabled = carrierInfraMediator.isEnabled(conditions, this.defaultSubscriptionSlotId, new Object[0]);
                    this.matchedSim = true;
                }
            }
        }
        z = false;
        this.networkCondition = z;
        this.featureEnabled = carrierInfraMediator.isEnabled(conditions, this.defaultSubscriptionSlotId, new Object[0]);
        this.matchedSim = true;
    }

    public final int getVisible() {
        if (this.featureEnabled && this.networkCondition && this.settingEnabled && this.matchedSim && this.quickStarEnabled) {
            return 0;
        }
        return 8;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("visibility=" + getVisible() + " ");
        ControlExtension$Companion$$ExternalSyntheticOutline0.m("networkCondition=", this.networkCondition, " ", sb);
        ControlExtension$Companion$$ExternalSyntheticOutline0.m("settingEnabled=", this.settingEnabled, " ", sb);
        ControlExtension$Companion$$ExternalSyntheticOutline0.m("matchedSim=", this.matchedSim, " (", sb);
        Iterator it = this.simTypes.iterator();
        while (it.hasNext()) {
            sb.append(((SimType) it.next()) + " ");
        }
        ControlExtension$Companion$$ExternalSyntheticOutline0.m(") quickStarEnabled=", this.quickStarEnabled, " ", sb);
        sb.append("default subscription id=" + this.defaultSubscriptionSlotId + " ");
        sb.append("service sate=" + this.serviceStateHash);
        return sb.toString();
    }
}
