package com.sec.internal.ims.util;

import android.content.Context;
import android.content.IntentFilter;
import android.telephony.BarringInfo;
import android.telephony.CellInfo;
import android.telephony.PhoneStateListener;
import android.telephony.PreciseDataConnectionState;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.sec.internal.constants.ims.os.PhoneConstants;
import com.sec.internal.helper.CollectionUtils;
import com.sec.internal.helper.SimUtil;
import com.sec.internal.log.IMSLog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public class ImsPhoneStateManager {
    private static String INTENT_ACTION_SUBINFO_UPDATED = "android.intent.action.ACTION_SUBINFO_RECORD_UPDATED";
    private static String LOG_TAG = "ImsPhoneStateManager";
    private Context mContext;
    private int mListenEvent;
    private Map<Integer, PhoneStateListener> mListener = new HashMap();
    private List<PhoneStateListenerInternal> mPhoneStateListenerInternal = new ArrayList();
    private TelephonyManager mTelephonyManager;

    public ImsPhoneStateManager(Context context, int i) {
        this.mListenEvent = 0;
        this.mContext = context;
        this.mTelephonyManager = (TelephonyManager) context.getSystemService(PhoneConstants.PHONE_KEY);
        this.mListenEvent = i;
        new IntentFilter().addAction(INTENT_ACTION_SUBINFO_UPDATED);
    }

    public void registerListener(PhoneStateListener phoneStateListener) {
        int activeDataSubscriptionId = SubscriptionManager.getActiveDataSubscriptionId();
        registerListener(phoneStateListener, activeDataSubscriptionId, SimUtil.getSlotId(activeDataSubscriptionId));
    }

    public void registerListener(PhoneStateListener phoneStateListener, int i, int i2) {
        Log.d(LOG_TAG + "[" + i2 + "](" + i + ")", "registerListener:");
        this.mListener.put(Integer.valueOf(i2), phoneStateListener);
        registerPhoneStateListenerInternal(i, i2);
    }

    public void unRegisterListener(int i) {
        unRegisterPhoneStateListenerInternal(i);
        this.mListener.remove(Integer.valueOf(i));
    }

    public boolean hasListener(int i) {
        return !CollectionUtils.isNullOrEmpty(this.mListener) && this.mListener.containsKey(Integer.valueOf(i));
    }

    private void registerPhoneStateListenerInternal(int i, int i2) {
        PhoneStateListenerInternal phoneStateListenerInternal = new PhoneStateListenerInternal(i, i2);
        this.mPhoneStateListenerInternal.add(phoneStateListenerInternal);
        if (this.mTelephonyManager.createForSubscriptionId(i) != null) {
            IMSLog.d(LOG_TAG, i2, "registerPhoneStateListenerInternal:");
            this.mTelephonyManager.createForSubscriptionId(i).listen(phoneStateListenerInternal, this.mListenEvent);
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

    private PhoneStateListenerInternal getPhoneStateListenerInternal(int i) {
        for (PhoneStateListenerInternal phoneStateListenerInternal : this.mPhoneStateListenerInternal) {
            if (phoneStateListenerInternal.getSimSlot() == i) {
                return phoneStateListenerInternal;
            }
        }
        return null;
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
        public void onBarringInfoChanged(BarringInfo barringInfo) {
            PhoneStateListener phoneStateListener = (PhoneStateListener) ImsPhoneStateManager.this.mListener.get(Integer.valueOf(this.mSimSlot));
            if (phoneStateListener == null) {
                return;
            }
            phoneStateListener.onBarringInfoChanged(barringInfo);
        }

        @Override // android.telephony.PhoneStateListener
        public void onCallForwardingIndicatorChanged(boolean z) {
            PhoneStateListener phoneStateListener = (PhoneStateListener) ImsPhoneStateManager.this.mListener.get(Integer.valueOf(this.mSimSlot));
            if (phoneStateListener == null) {
                return;
            }
            phoneStateListener.onCallForwardingIndicatorChanged(z);
        }

        @Override // android.telephony.PhoneStateListener
        public void onCallStateChanged(int i, String str) {
            PhoneStateListener phoneStateListener = (PhoneStateListener) ImsPhoneStateManager.this.mListener.get(Integer.valueOf(this.mSimSlot));
            if (phoneStateListener == null) {
                return;
            }
            phoneStateListener.onCallStateChanged(i, str);
        }

        @Override // android.telephony.PhoneStateListener
        public void onCellInfoChanged(List<CellInfo> list) {
            PhoneStateListener phoneStateListener = (PhoneStateListener) ImsPhoneStateManager.this.mListener.get(Integer.valueOf(this.mSimSlot));
            if (phoneStateListener == null) {
                return;
            }
            phoneStateListener.onCellInfoChanged(list);
        }

        @Override // android.telephony.PhoneStateListener
        public void onDataActivity(int i) {
            PhoneStateListener phoneStateListener = (PhoneStateListener) ImsPhoneStateManager.this.mListener.get(Integer.valueOf(this.mSimSlot));
            if (phoneStateListener == null) {
                return;
            }
            phoneStateListener.onDataActivity(i);
        }

        @Override // android.telephony.PhoneStateListener
        public void onDataConnectionStateChanged(int i, int i2) {
            IMSLog.d(ImsPhoneStateManager.LOG_TAG, this.mSimSlot, "onDataConnectionStateChanged(s, n) E");
            PhoneStateListener phoneStateListener = (PhoneStateListener) ImsPhoneStateManager.this.mListener.get(Integer.valueOf(this.mSimSlot));
            if (phoneStateListener == null) {
                return;
            }
            IMSLog.d(ImsPhoneStateManager.LOG_TAG, this.mSimSlot, "onDataConnectionStateChanged(s, n) X");
            phoneStateListener.onDataConnectionStateChanged(i, i2);
        }

        @Override // android.telephony.PhoneStateListener
        public void onDataConnectionStateChanged(int i) {
            IMSLog.d(ImsPhoneStateManager.LOG_TAG, this.mSimSlot, "onDataConnectionStateChanged(s) E");
            PhoneStateListener phoneStateListener = (PhoneStateListener) ImsPhoneStateManager.this.mListener.get(Integer.valueOf(this.mSimSlot));
            if (phoneStateListener == null) {
                return;
            }
            IMSLog.d(ImsPhoneStateManager.LOG_TAG, this.mSimSlot, "onDataConnectionStateChanged(s) X");
            phoneStateListener.onDataConnectionStateChanged(i);
        }

        @Override // android.telephony.PhoneStateListener
        public void onMessageWaitingIndicatorChanged(boolean z) {
            PhoneStateListener phoneStateListener = (PhoneStateListener) ImsPhoneStateManager.this.mListener.get(Integer.valueOf(this.mSimSlot));
            if (phoneStateListener == null) {
                return;
            }
            phoneStateListener.onMessageWaitingIndicatorChanged(z);
        }

        @Override // android.telephony.PhoneStateListener
        public void onServiceStateChanged(ServiceState serviceState) {
            IMSLog.d(ImsPhoneStateManager.LOG_TAG, this.mSimSlot, "onServiceStateChanged E");
            PhoneStateListener phoneStateListener = (PhoneStateListener) ImsPhoneStateManager.this.mListener.get(Integer.valueOf(this.mSimSlot));
            if (phoneStateListener == null) {
                return;
            }
            IMSLog.d(ImsPhoneStateManager.LOG_TAG, this.mSimSlot, "onServiceStateChanged X");
            phoneStateListener.onServiceStateChanged(serviceState);
        }

        @Override // android.telephony.PhoneStateListener
        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
            PhoneStateListener phoneStateListener = (PhoneStateListener) ImsPhoneStateManager.this.mListener.get(Integer.valueOf(this.mSimSlot));
            if (phoneStateListener == null) {
                return;
            }
            phoneStateListener.onSignalStrengthsChanged(signalStrength);
        }

        public void onSrvccStateChanged(int i) {
            PhoneStateListener phoneStateListener = (PhoneStateListener) ImsPhoneStateManager.this.mListener.get(Integer.valueOf(this.mSimSlot));
            if (phoneStateListener == null) {
                return;
            }
            phoneStateListener.onSrvccStateChanged(i);
        }

        @Override // android.telephony.PhoneStateListener
        public void onPreciseDataConnectionStateChanged(PreciseDataConnectionState preciseDataConnectionState) {
            PhoneStateListener phoneStateListener = (PhoneStateListener) ImsPhoneStateManager.this.mListener.get(Integer.valueOf(this.mSimSlot));
            if (phoneStateListener == null) {
                return;
            }
            phoneStateListener.onPreciseDataConnectionStateChanged(preciseDataConnectionState);
        }
    }
}
