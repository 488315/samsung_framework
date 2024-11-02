package com.android.settingslib.mobile;

import android.os.Handler;
import android.os.Looper;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyDisplayInfo;
import android.telephony.TelephonyManager;
import android.util.Log;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.mediarouter.media.MediaRoute2Provider$$ExternalSyntheticLambda0;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.settingslib.mobile.MobileStatusTracker;
import com.android.systemui.statusbar.connectivity.MobileSignalController;
import java.util.Objects;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class MobileStatusTracker {
    public final Callback mCallback;
    public final SubscriptionDefaults mDefaults;
    public boolean mListening = false;
    public final MobileStatus mMobileStatus;
    public final TelephonyManager mPhone;
    public final Handler mReceiverHandler;
    public final SubscriptionInfo mSubscriptionInfo;
    public final MobileTelephonyCallback mTelephonyCallback;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public interface Callback {
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class MobileTelephonyCallback extends TelephonyCallback implements TelephonyCallback.ServiceStateListener, TelephonyCallback.SignalStrengthsListener, TelephonyCallback.DataConnectionStateListener, TelephonyCallback.DataActivityListener, TelephonyCallback.CarrierNetworkListener, TelephonyCallback.ActiveDataSubscriptionIdListener, TelephonyCallback.DisplayInfoListener {
        public MobileTelephonyCallback() {
        }

        @Override // android.telephony.TelephonyCallback.ActiveDataSubscriptionIdListener
        public final void onActiveDataSubscriptionIdChanged(int i) {
            if (Log.isLoggable("MobileStatusTracker", 3)) {
                ListPopupWindow$$ExternalSyntheticOutline0.m("onActiveDataSubscriptionIdChanged: subId=", i, "MobileStatusTracker");
            }
            MobileStatusTracker.this.updateDataSim();
            MobileStatusTracker mobileStatusTracker = MobileStatusTracker.this;
            ((MobileSignalController.AnonymousClass1) mobileStatusTracker.mCallback).onMobileStatusChanged(true, new MobileStatus(mobileStatusTracker.mMobileStatus));
        }

        @Override // android.telephony.TelephonyCallback.CarrierNetworkListener
        public final void onCarrierNetworkChange(boolean z) {
            if (Log.isLoggable("MobileStatusTracker", 3)) {
                KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("onCarrierNetworkChange: active=", z, "MobileStatusTracker");
            }
            MobileStatusTracker mobileStatusTracker = MobileStatusTracker.this;
            MobileStatus mobileStatus = mobileStatusTracker.mMobileStatus;
            mobileStatus.carrierNetworkChangeMode = z;
            ((MobileSignalController.AnonymousClass1) mobileStatusTracker.mCallback).onMobileStatusChanged(true, new MobileStatus(mobileStatus));
        }

        @Override // android.telephony.TelephonyCallback.DataActivityListener
        public final void onDataActivity(int i) {
            boolean z;
            if (Log.isLoggable("MobileStatusTracker", 3)) {
                ListPopupWindow$$ExternalSyntheticOutline0.m("onDataActivity: direction=", i, "MobileStatusTracker");
            }
            MobileStatusTracker mobileStatusTracker = MobileStatusTracker.this;
            mobileStatusTracker.getClass();
            boolean z2 = true;
            if (i != 3 && i != 1) {
                z = false;
            } else {
                z = true;
            }
            MobileStatus mobileStatus = mobileStatusTracker.mMobileStatus;
            mobileStatus.activityIn = z;
            if (i != 3 && i != 2) {
                z2 = false;
            }
            mobileStatus.activityOut = z2;
            MobileStatusTracker mobileStatusTracker2 = MobileStatusTracker.this;
            ((MobileSignalController.AnonymousClass1) mobileStatusTracker2.mCallback).onMobileStatusChanged(false, new MobileStatus(mobileStatusTracker2.mMobileStatus));
        }

        @Override // android.telephony.TelephonyCallback.DataConnectionStateListener
        public final void onDataConnectionStateChanged(int i, int i2) {
            if (Log.isLoggable("MobileStatusTracker", 3)) {
                SuggestionsAdapter$$ExternalSyntheticOutline0.m("onDataConnectionStateChanged: state=", i, " type=", i2, "MobileStatusTracker");
            }
            MobileStatusTracker mobileStatusTracker = MobileStatusTracker.this;
            MobileStatus mobileStatus = mobileStatusTracker.mMobileStatus;
            mobileStatus.dataState = i;
            ((MobileSignalController.AnonymousClass1) mobileStatusTracker.mCallback).onMobileStatusChanged(true, new MobileStatus(mobileStatus));
        }

        @Override // android.telephony.TelephonyCallback.DisplayInfoListener
        public final void onDisplayInfoChanged(TelephonyDisplayInfo telephonyDisplayInfo) {
            if (Log.isLoggable("MobileStatusTracker", 3)) {
                Log.d("MobileStatusTracker", "onDisplayInfoChanged: telephonyDisplayInfo=" + telephonyDisplayInfo);
            }
            MobileStatusTracker mobileStatusTracker = MobileStatusTracker.this;
            MobileStatus mobileStatus = mobileStatusTracker.mMobileStatus;
            mobileStatus.telephonyDisplayInfo = telephonyDisplayInfo;
            ((MobileSignalController.AnonymousClass1) mobileStatusTracker.mCallback).onMobileStatusChanged(true, new MobileStatus(mobileStatus));
        }

        @Override // android.telephony.TelephonyCallback.ServiceStateListener
        public final void onServiceStateChanged(ServiceState serviceState) {
            Object valueOf;
            if (Log.isLoggable("MobileStatusTracker", 3)) {
                StringBuilder sb = new StringBuilder("onServiceStateChanged voiceState=");
                Object obj = "";
                if (serviceState == null) {
                    valueOf = "";
                } else {
                    valueOf = Integer.valueOf(serviceState.getState());
                }
                sb.append(valueOf);
                sb.append(" dataState=");
                if (serviceState != null) {
                    obj = Integer.valueOf(serviceState.getDataRegistrationState());
                }
                sb.append(obj);
                Log.d("MobileStatusTracker", sb.toString());
            }
            MobileStatusTracker mobileStatusTracker = MobileStatusTracker.this;
            MobileStatus mobileStatus = mobileStatusTracker.mMobileStatus;
            mobileStatus.serviceState = serviceState;
            ((MobileSignalController.AnonymousClass1) mobileStatusTracker.mCallback).onMobileStatusChanged(true, new MobileStatus(mobileStatus));
        }

        @Override // android.telephony.TelephonyCallback.SignalStrengthsListener
        public final void onSignalStrengthsChanged(SignalStrength signalStrength) {
            String str;
            if (Log.isLoggable("MobileStatusTracker", 3)) {
                StringBuilder sb = new StringBuilder("onSignalStrengthsChanged signalStrength=");
                sb.append(signalStrength);
                if (signalStrength == null) {
                    str = "";
                } else {
                    str = " level=" + signalStrength.getLevel();
                }
                ExifInterface$$ExternalSyntheticOutline0.m(sb, str, "MobileStatusTracker");
            }
            MobileStatusTracker mobileStatusTracker = MobileStatusTracker.this;
            MobileStatus mobileStatus = mobileStatusTracker.mMobileStatus;
            mobileStatus.signalStrength = signalStrength;
            ((MobileSignalController.AnonymousClass1) mobileStatusTracker.mCallback).onMobileStatusChanged(true, new MobileStatus(mobileStatus));
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class SubscriptionDefaults {
    }

    public MobileStatusTracker(TelephonyManager telephonyManager, Looper looper, SubscriptionInfo subscriptionInfo, SubscriptionDefaults subscriptionDefaults, Callback callback) {
        this.mPhone = telephonyManager;
        Handler handler = new Handler(looper);
        this.mReceiverHandler = handler;
        this.mTelephonyCallback = new MobileTelephonyCallback();
        this.mSubscriptionInfo = subscriptionInfo;
        this.mDefaults = subscriptionDefaults;
        this.mCallback = callback;
        this.mMobileStatus = new MobileStatus();
        updateDataSim();
        handler.post(new Runnable() { // from class: com.android.settingslib.mobile.MobileStatusTracker$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                MobileStatusTracker mobileStatusTracker = MobileStatusTracker.this;
                ((MobileSignalController.AnonymousClass1) mobileStatusTracker.mCallback).onMobileStatusChanged(false, new MobileStatusTracker.MobileStatus(mobileStatusTracker.mMobileStatus));
            }
        });
    }

    public final void setListening(boolean z) {
        if (this.mListening == z) {
            return;
        }
        this.mListening = z;
        MobileTelephonyCallback mobileTelephonyCallback = this.mTelephonyCallback;
        TelephonyManager telephonyManager = this.mPhone;
        if (z) {
            Handler handler = this.mReceiverHandler;
            Objects.requireNonNull(handler);
            telephonyManager.registerTelephonyCallback(new MediaRoute2Provider$$ExternalSyntheticLambda0(handler), mobileTelephonyCallback);
            return;
        }
        telephonyManager.unregisterTelephonyCallback(mobileTelephonyCallback);
    }

    public final void updateDataSim() {
        this.mDefaults.getClass();
        int activeDataSubscriptionId = SubscriptionManager.getActiveDataSubscriptionId();
        boolean isValidSubscriptionId = SubscriptionManager.isValidSubscriptionId(activeDataSubscriptionId);
        boolean z = true;
        MobileStatus mobileStatus = this.mMobileStatus;
        if (isValidSubscriptionId) {
            if (activeDataSubscriptionId != this.mSubscriptionInfo.getSubscriptionId()) {
                z = false;
            }
            mobileStatus.dataSim = z;
            return;
        }
        mobileStatus.dataSim = true;
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class MobileStatus {
        public boolean activityIn;
        public boolean activityOut;
        public boolean carrierNetworkChangeMode;
        public boolean dataSim;
        public int dataState;
        public ServiceState serviceState;
        public SignalStrength signalStrength;
        public TelephonyDisplayInfo telephonyDisplayInfo;

        public MobileStatus() {
            this.dataState = 0;
            this.telephonyDisplayInfo = new TelephonyDisplayInfo(0, 0, false);
        }

        public final String toString() {
            String str;
            Object valueOf;
            StringBuilder sb = new StringBuilder("[activityIn=");
            sb.append(this.activityIn);
            sb.append(",activityOut=");
            sb.append(this.activityOut);
            sb.append(",dataSim=");
            sb.append(this.dataSim);
            sb.append(",carrierNetworkChangeMode=");
            sb.append(this.carrierNetworkChangeMode);
            sb.append(",dataState=");
            sb.append(this.dataState);
            sb.append(",serviceState=");
            String str2 = "";
            if (this.serviceState == null) {
                str = "";
            } else {
                str = "mVoiceRegState=" + this.serviceState.getState() + "(" + ServiceState.rilServiceStateToString(this.serviceState.getState()) + "), mDataRegState=" + this.serviceState.getDataRegState() + "(" + ServiceState.rilServiceStateToString(this.serviceState.getDataRegState()) + ")";
            }
            sb.append(str);
            sb.append(",signalStrength=");
            SignalStrength signalStrength = this.signalStrength;
            if (signalStrength == null) {
                valueOf = "";
            } else {
                valueOf = Integer.valueOf(signalStrength.getLevel());
            }
            sb.append(valueOf);
            sb.append(",telephonyDisplayInfo=");
            TelephonyDisplayInfo telephonyDisplayInfo = this.telephonyDisplayInfo;
            if (telephonyDisplayInfo != null) {
                str2 = telephonyDisplayInfo.toString();
            }
            sb.append(str2);
            sb.append(']');
            return sb.toString();
        }

        public MobileStatus(MobileStatus mobileStatus) {
            this.dataState = 0;
            this.telephonyDisplayInfo = new TelephonyDisplayInfo(0, 0, false);
            this.activityIn = mobileStatus.activityIn;
            this.activityOut = mobileStatus.activityOut;
            this.dataSim = mobileStatus.dataSim;
            this.carrierNetworkChangeMode = mobileStatus.carrierNetworkChangeMode;
            this.dataState = mobileStatus.dataState;
            this.serviceState = mobileStatus.serviceState;
            this.signalStrength = mobileStatus.signalStrength;
            this.telephonyDisplayInfo = mobileStatus.telephonyDisplayInfo;
        }
    }
}
