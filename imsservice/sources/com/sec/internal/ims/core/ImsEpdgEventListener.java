package com.sec.internal.ims.core;

import android.text.TextUtils;
import com.sec.ims.ImsManager;
import com.sec.internal.constants.ims.os.NetworkState;
import com.sec.internal.helper.SimUtil;
import com.sec.internal.helper.os.ITelephonyManager;
import com.sec.internal.ims.settings.DeviceConfigManager;
import com.sec.internal.interfaces.ims.IImsFramework;
import com.sec.internal.interfaces.ims.core.IGeolocationController;
import com.sec.internal.interfaces.ims.core.NetworkStateListener;
import com.sec.internal.log.IMSLog;
import java.util.Iterator;

/* loaded from: classes.dex */
class ImsEpdgEventListener extends ImsManager.EpdgListener {
    private static final String LOG_TAG = ImsEpdgEventListener.class.getSimpleName();
    private final IImsFramework mImsFramework;
    private final PdnController mPdnController;

    public void onEpdgShowPopup(int i, int i2) {
    }

    public ImsEpdgEventListener(PdnController pdnController, IImsFramework iImsFramework) {
        this.mPdnController = pdnController;
        this.mImsFramework = iImsFramework;
    }

    public void onEpdgAvailable(int i, int i2, int i3) {
        NetworkState networkState = this.mPdnController.getNetworkState(i);
        boolean z = i2 == 1;
        IMSLog.i(LOG_TAG, i, "onEpdgAvailable :  availability : " + z + " physicalInterface : " + i3);
        networkState.setEpdgAvailable(z);
        networkState.setEpdgPhysicalInterface(i3);
        this.mPdnController.setPendedEPDGWeakSignal(i, z ^ true);
        IGeolocationController geolocationController = this.mImsFramework.getGeolocationController();
        if (geolocationController != null) {
            geolocationController.notifyEpdgAvailable(i, i2);
        }
    }

    public void onEpdgHandoverResult(int i, int i2, int i3, String str) {
        boolean z = i3 == 1;
        String str2 = i2 == 1 ? "LTE_TO_WLAN" : "WLAN_TO_LTE";
        IMSLog.i(LOG_TAG, i, "onEpdgHandoverResult :  Direction : " + str2 + " result : " + z);
        if (z) {
            this.mPdnController.setPendedEPDGWeakSignal(i, false);
            PdnController pdnController = this.mPdnController;
            pdnController.sendMessage(pdnController.obtainMessage(104, i, i2, str));
        }
    }

    public void onEpdgDeregister(int i) {
        IMSLog.i(LOG_TAG, i, "onEpdgDeregister");
        this.mPdnController.setPendedEPDGWeakSignal(i, true);
        notifyEpdgRequest(i, false, false);
    }

    public void onEpdgIpsecConnection(int i, String str, int i2, int i3) {
        ITelephonyManager iTelephonyManager;
        IMSLog.i(LOG_TAG, i, "onEpdgIpsecConnection :  ikeError : " + i2 + " apnType : " + str);
        int activeDataSubscriptionId = SimUtil.getActiveDataSubscriptionId();
        int slotId = activeDataSubscriptionId != -1 ? SimUtil.getSlotId(activeDataSubscriptionId) : SimUtil.getActiveDataPhoneId();
        if (i2 == 0 && (this.mPdnController.isWifiConnected() || (i != slotId && (iTelephonyManager = this.mPdnController.mTelephonyManager) != null && iTelephonyManager.semGetDataState(activeDataSubscriptionId) == 2))) {
            this.mPdnController.setPendedEPDGWeakSignal(i, false);
            PdnController pdnController = this.mPdnController;
            pdnController.sendMessage(pdnController.obtainMessage(104, i, 1, str));
        } else if (i2 == 24) {
            PdnController pdnController2 = this.mPdnController;
            pdnController2.sendMessage(pdnController2.obtainMessage(109, Integer.valueOf(i)));
        }
    }

    public void onEpdgIpsecDisconnection(int i, String str) {
        IMSLog.i(LOG_TAG, i, "onEpdgIpsecDisconnection :  apnType : " + str);
        if (TextUtils.equals(str, DeviceConfigManager.IMS)) {
            Iterator<NetworkStateListener> it = this.mPdnController.mNetworkStateListeners.iterator();
            while (it.hasNext()) {
                it.next().onEpdgIpsecDisconnected(i);
            }
        }
    }

    public void onEpdgRegister(int i, boolean z) {
        IMSLog.i(LOG_TAG, i, "onEpdgRegister");
        notifyEpdgRequest(i, z, true);
    }

    public void onEpdgHandoverEnableChanged(int i, boolean z) {
        Iterator<NetworkStateListener> it = this.mPdnController.mNetworkStateListeners.iterator();
        while (it.hasNext()) {
            it.next().onEpdgHandoverEnableChanged(i, z);
        }
    }

    private void notifyEpdgRequest(int i, boolean z, boolean z2) {
        IMSLog.i(LOG_TAG, i, "notifyEpdgRequest:");
        for (NetworkStateListener networkStateListener : this.mPdnController.mNetworkStateListeners) {
            if (z2) {
                networkStateListener.onEpdgRegisterRequested(i, z);
            } else {
                networkStateListener.onEpdgDeregisterRequested(i);
            }
        }
    }
}
