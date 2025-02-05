package com.sec.internal.ims.cmstore.utils;

import android.content.Context;
import android.telephony.CellInfo;
import android.telephony.PhoneStateListener;
import android.telephony.PreciseDataConnectionState;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.sec.internal.constants.ims.os.PhoneConstants;
import com.sec.internal.helper.SimUtil;
import com.sec.internal.log.IMSLog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public class PhoneStateManager {
    private static String LOG_TAG = "PhoneStateManager";
    private Context mContext;
    private int mListenEvent;
    private Map<Integer, PhoneStateListener> mListener = new HashMap();
    private List<PhoneStateListenerInternal> mPhoneStateListenerInternal = new ArrayList();
    private TelephonyManager mTelephonyManager;

    public PhoneStateManager(Context context, int i) {
        this.mListenEvent = 0;
        this.mContext = context;
        this.mTelephonyManager = (TelephonyManager) context.getSystemService(PhoneConstants.PHONE_KEY);
        this.mListenEvent = i;
    }

    public void registerListener(PhoneStateListener phoneStateListener, int i) {
        int subId = SimUtil.getSubId(i);
        this.mListener.put(Integer.valueOf(i), phoneStateListener);
        registerPhoneStateListenerInternal(subId, i);
        Log.d(LOG_TAG + "[" + i + "](" + subId + ")", "registerListener:");
    }

    public void unRegisterListener(int i) {
        unRegisterPhoneStateListenerInternal(i);
        this.mListener.remove(Integer.valueOf(i));
    }

    private PhoneStateListenerInternal getPhoneStateListenerInternal(int i) {
        for (PhoneStateListenerInternal phoneStateListenerInternal : this.mPhoneStateListenerInternal) {
            if (phoneStateListenerInternal.getSimSlot() == i) {
                return phoneStateListenerInternal;
            }
        }
        return null;
    }

    private void registerPhoneStateListenerInternal(int i, int i2) {
        PhoneStateListenerInternal phoneStateListenerInternal = new PhoneStateListenerInternal(i, i2);
        this.mPhoneStateListenerInternal.add(phoneStateListenerInternal);
        TelephonyManager createForSubscriptionId = this.mTelephonyManager.createForSubscriptionId(i);
        if (createForSubscriptionId != null) {
            IMSLog.d(LOG_TAG, "registerListener subid:" + i);
            createForSubscriptionId.listen(phoneStateListenerInternal, this.mListenEvent);
        }
    }

    private void unRegisterPhoneStateListenerInternal(int i) {
        if (getPhoneStateListenerInternal(i) == null) {
            Log.d(LOG_TAG, "unRegisterPhoneStateListenerInternal, phoneStateListenerInternal[" + i + "] is not exist. return..");
            return;
        }
        PhoneStateListenerInternal phoneStateListenerInternal = getPhoneStateListenerInternal(i);
        if (this.mTelephonyManager.createForSubscriptionId(phoneStateListenerInternal.getSubId()) != null) {
            IMSLog.d(LOG_TAG, i, "registerPhoneStateListenerInternal:");
            this.mTelephonyManager.createForSubscriptionId(phoneStateListenerInternal.getSubId()).listen(phoneStateListenerInternal, 0);
        }
        this.mPhoneStateListenerInternal.remove(phoneStateListenerInternal);
    }

    private class PhoneStateListenerInternal extends PhoneStateListener {
        int mSimSlot;
        int mSubId;

        public PhoneStateListenerInternal(int i, int i2) {
            this.mSimSlot = i2;
            this.mSubId = i;
        }

        public int getSimSlot() {
            return this.mSimSlot;
        }

        public int getSubId() {
            return this.mSubId;
        }

        @Override // android.telephony.PhoneStateListener
        public void onCallForwardingIndicatorChanged(boolean z) {
            PhoneStateListener phoneStateListener = (PhoneStateListener) PhoneStateManager.this.mListener.get(Integer.valueOf(this.mSimSlot));
            if (phoneStateListener == null) {
                return;
            }
            phoneStateListener.onCallForwardingIndicatorChanged(z);
        }

        @Override // android.telephony.PhoneStateListener
        public void onCallStateChanged(int i, String str) {
            PhoneStateListener phoneStateListener = (PhoneStateListener) PhoneStateManager.this.mListener.get(Integer.valueOf(this.mSimSlot));
            if (phoneStateListener == null) {
                return;
            }
            phoneStateListener.onCallStateChanged(i, str);
        }

        @Override // android.telephony.PhoneStateListener
        public void onCellInfoChanged(List<CellInfo> list) {
            PhoneStateListener phoneStateListener = (PhoneStateListener) PhoneStateManager.this.mListener.get(Integer.valueOf(this.mSimSlot));
            if (phoneStateListener == null) {
                return;
            }
            phoneStateListener.onCellInfoChanged(list);
        }

        @Override // android.telephony.PhoneStateListener
        public void onDataActivity(int i) {
            PhoneStateListener phoneStateListener = (PhoneStateListener) PhoneStateManager.this.mListener.get(Integer.valueOf(this.mSimSlot));
            if (phoneStateListener == null) {
                return;
            }
            phoneStateListener.onDataActivity(i);
        }

        @Override // android.telephony.PhoneStateListener
        public void onDataConnectionStateChanged(int i, int i2) {
            IMSLog.d(PhoneStateManager.LOG_TAG, this.mSimSlot, "onDataConnectionStateChanged(s, n) E");
            PhoneStateListener phoneStateListener = (PhoneStateListener) PhoneStateManager.this.mListener.get(Integer.valueOf(this.mSimSlot));
            if (phoneStateListener == null) {
                return;
            }
            IMSLog.d(PhoneStateManager.LOG_TAG, this.mSimSlot, "onDataConnectionStateChanged(s, n) X");
            phoneStateListener.onDataConnectionStateChanged(i, i2);
        }

        @Override // android.telephony.PhoneStateListener
        public void onDataConnectionStateChanged(int i) {
            IMSLog.d(PhoneStateManager.LOG_TAG, this.mSimSlot, "onDataConnectionStateChanged(s) E");
            PhoneStateListener phoneStateListener = (PhoneStateListener) PhoneStateManager.this.mListener.get(Integer.valueOf(this.mSimSlot));
            if (phoneStateListener == null) {
                return;
            }
            IMSLog.d(PhoneStateManager.LOG_TAG, this.mSimSlot, "onDataConnectionStateChanged(s) X");
            phoneStateListener.onDataConnectionStateChanged(i);
        }

        @Override // android.telephony.PhoneStateListener
        public void onMessageWaitingIndicatorChanged(boolean z) {
            PhoneStateListener phoneStateListener = (PhoneStateListener) PhoneStateManager.this.mListener.get(Integer.valueOf(this.mSimSlot));
            if (phoneStateListener == null) {
                return;
            }
            phoneStateListener.onMessageWaitingIndicatorChanged(z);
        }

        @Override // android.telephony.PhoneStateListener
        public void onServiceStateChanged(ServiceState serviceState) {
            IMSLog.d(PhoneStateManager.LOG_TAG, this.mSimSlot, "onServiceStateChanged E");
            PhoneStateListener phoneStateListener = (PhoneStateListener) PhoneStateManager.this.mListener.get(Integer.valueOf(this.mSimSlot));
            if (phoneStateListener == null) {
                return;
            }
            IMSLog.d(PhoneStateManager.LOG_TAG, this.mSimSlot, "onServiceStateChanged X");
            phoneStateListener.onServiceStateChanged(serviceState);
        }

        @Override // android.telephony.PhoneStateListener
        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
            PhoneStateListener phoneStateListener = (PhoneStateListener) PhoneStateManager.this.mListener.get(Integer.valueOf(this.mSimSlot));
            if (phoneStateListener == null) {
                return;
            }
            phoneStateListener.onSignalStrengthsChanged(signalStrength);
        }

        @Override // android.telephony.PhoneStateListener
        public void onPreciseDataConnectionStateChanged(PreciseDataConnectionState preciseDataConnectionState) {
            PhoneStateListener phoneStateListener = (PhoneStateListener) PhoneStateManager.this.mListener.get(Integer.valueOf(this.mSimSlot));
            if (phoneStateListener == null) {
                return;
            }
            phoneStateListener.onPreciseDataConnectionStateChanged(preciseDataConnectionState);
        }
    }
}
