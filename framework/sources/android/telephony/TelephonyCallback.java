package android.telephony;

import android.annotation.SystemApi;
import android.os.Binder;
import android.telephony.TelephonyCallback;
import android.telephony.emergency.EmergencyNumber;
import android.telephony.ims.ImsReasonInfo;
import android.telephony.ims.MediaQualityStatus;
import android.telephony.satellite.NtnSignalStrength;
import android.telephony.satellite.SemSatelliteServiceState;
import android.telephony.satellite.SemSatelliteSignalStrength;
import android.util.Log;
import com.android.internal.telephony.IPhoneStateListener;
import com.android.internal.telephony.flags.Flags;
import com.android.internal.util.FunctionalUtils;
import dalvik.system.VMRuntime;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

/* loaded from: classes4.dex */
public class TelephonyCallback {
    public static final int DEFAULT_PER_PID_REGISTRATION_LIMIT = 50;

    @SystemApi
    public static final int EVENT_ACTIVE_DATA_SUBSCRIPTION_ID_CHANGED = 23;

    @SystemApi
    public static final int EVENT_ALLOWED_NETWORK_TYPE_LIST_CHANGED = 35;

    @SystemApi
    public static final int EVENT_ALWAYS_REPORTED_SIGNAL_STRENGTH_CHANGED = 10;

    @SystemApi
    public static final int EVENT_BARRING_INFO_CHANGED = 32;

    @SystemApi
    public static final int EVENT_CALL_ATTRIBUTES_CHANGED = 27;

    @SystemApi
    public static final int EVENT_CALL_DISCONNECT_CAUSE_CHANGED = 26;

    @SystemApi
    public static final int EVENT_CALL_FORWARDING_INDICATOR_CHANGED = 4;

    @SystemApi
    public static final int EVENT_CALL_STATE_CHANGED = 6;

    @SystemApi
    public static final int EVENT_CARRIER_NETWORK_CHANGED = 17;
    public static final int EVENT_CARRIER_ROAMING_NTN_AVAILABLE_SERVICES_CHANGED = 44;
    public static final int EVENT_CARRIER_ROAMING_NTN_ELIGIBLE_STATE_CHANGED = 43;
    public static final int EVENT_CARRIER_ROAMING_NTN_MODE_CHANGED = 42;
    public static final int EVENT_CARRIER_ROAMING_NTN_SIGNAL_STRENGTH_CHANGED = 45;

    @SystemApi
    public static final int EVENT_CELL_INFO_CHANGED = 11;

    @SystemApi
    public static final int EVENT_CELL_LOCATION_CHANGED = 5;

    @SystemApi
    public static final int EVENT_DATA_ACTIVATION_STATE_CHANGED = 19;

    @SystemApi
    public static final int EVENT_DATA_ACTIVITY_CHANGED = 8;

    @SystemApi
    @Deprecated
    public static final int EVENT_DATA_CONNECTION_REAL_TIME_INFO_CHANGED = 14;

    @SystemApi
    public static final int EVENT_DATA_CONNECTION_STATE_CHANGED = 7;

    @SystemApi
    public static final int EVENT_DATA_ENABLED_CHANGED = 34;

    @SystemApi
    public static final int EVENT_DISPLAY_INFO_CHANGED = 21;
    public static final int EVENT_EMERGENCY_CALLBACK_MODE_CHANGED = 40;

    @SystemApi
    public static final int EVENT_EMERGENCY_NUMBER_LIST_CHANGED = 25;

    @SystemApi
    public static final int EVENT_IMS_CALL_DISCONNECT_CAUSE_CHANGED = 28;

    @SystemApi
    public static final int EVENT_LEGACY_CALL_STATE_CHANGED = 36;

    @SystemApi
    public static final int EVENT_LINK_CAPACITY_ESTIMATE_CHANGED = 37;

    @SystemApi
    public static final int EVENT_MEDIA_QUALITY_STATUS_CHANGED = 39;

    @SystemApi
    public static final int EVENT_MESSAGE_WAITING_INDICATOR_CHANGED = 3;

    @SystemApi
    public static final int EVENT_OEM_HOOK_RAW = 15;

    @SystemApi
    public static final int EVENT_OUTGOING_EMERGENCY_CALL = 29;

    @SystemApi
    public static final int EVENT_OUTGOING_EMERGENCY_SMS = 30;

    @SystemApi
    public static final int EVENT_PHONE_CAPABILITY_CHANGED = 22;

    @SystemApi
    public static final int EVENT_PHYSICAL_CHANNEL_CONFIG_CHANGED = 33;

    @SystemApi
    public static final int EVENT_PRECISE_CALL_STATE_CHANGED = 12;

    @SystemApi
    public static final int EVENT_PRECISE_DATA_CONNECTION_STATE_CHANGED = 13;

    @SystemApi
    public static final int EVENT_RADIO_POWER_STATE_CHANGED = 24;

    @SystemApi
    public static final int EVENT_REGISTRATION_FAILURE = 31;
    public static final int EVENT_SEM_SATELLITE_SERVICE_STATE_CHANGED = 10000;
    public static final int EVENT_SEM_SATELLITE_SIGNAL_STRENGTH_CHANGED = 10001;

    @SystemApi
    public static final int EVENT_SERVICE_STATE_CHANGED = 1;

    @SystemApi
    public static final int EVENT_SIGNAL_STRENGTHS_CHANGED = 9;

    @SystemApi
    public static final int EVENT_SIGNAL_STRENGTH_CHANGED = 2;

    @SystemApi
    public static final int EVENT_SIMULTANEOUS_CELLULAR_CALLING_SUBSCRIPTIONS_CHANGED = 41;

    @SystemApi
    public static final int EVENT_SRVCC_STATE_CHANGED = 16;
    public static final int EVENT_TRIGGER_NOTIFY_ANBR = 38;

    @SystemApi
    public static final int EVENT_USER_MOBILE_DATA_STATE_CHANGED = 20;

    @SystemApi
    public static final int EVENT_VOICE_ACTIVATION_STATE_CHANGED = 18;
    public static final String FLAG_PER_PID_REGISTRATION_LIMIT = "phone_state_listener_per_pid_registration_limit";
    private static final String LOG_TAG = "TelephonyCallback";
    public static final long PHONE_STATE_LISTENER_LIMIT_CHANGE_ID = 150880553;
    public IPhoneStateListener callback;

    public interface ActiveDataSubscriptionIdListener {
        void onActiveDataSubscriptionIdChanged(int i);
    }

    @SystemApi
    public interface AllowedNetworkTypesListener {
        void onAllowedNetworkTypesChanged(int i, long j);
    }

    public interface BarringInfoListener {
        void onBarringInfoChanged(BarringInfo barringInfo);
    }

    public interface CallDisconnectCauseListener {
        void onCallDisconnectCauseChanged(int i, int i2);
    }

    public interface CallForwardingIndicatorListener {
        void onCallForwardingIndicatorChanged(boolean z);
    }

    public interface CallStateListener {
        void onCallStateChanged(int i);
    }

    public interface CarrierNetworkListener {
        void onCarrierNetworkChange(boolean z);
    }

    public interface CellInfoListener {
        void onCellInfoChanged(List<CellInfo> list);
    }

    public interface CellLocationListener {
        void onCellLocationChanged(CellLocation cellLocation);
    }

    public interface DataActivationStateListener {
        void onDataActivationStateChanged(int i);
    }

    public interface DataActivityListener {
        void onDataActivity(int i);
    }

    public interface DataConnectionStateListener {
        void onDataConnectionStateChanged(int i, int i2);
    }

    @SystemApi
    public interface DataEnabledListener {
        void onDataEnabledChanged(boolean z, int i);
    }

    public interface DisplayInfoListener {
        void onDisplayInfoChanged(TelephonyDisplayInfo telephonyDisplayInfo);
    }

    public interface EmergencyCallbackModeListener {
        void onCallBackModeStarted(int i);

        void onCallBackModeStopped(int i, int i2);
    }

    public interface EmergencyNumberListListener {
        void onEmergencyNumberListChanged(Map<Integer, List<EmergencyNumber>> map);
    }

    public interface ImsCallDisconnectCauseListener {
        void onImsCallDisconnectCauseChanged(ImsReasonInfo imsReasonInfo);
    }

    @SystemApi
    public interface LinkCapacityEstimateChangedListener {
        void onLinkCapacityEstimateChanged(List<LinkCapacityEstimate> list);
    }

    @SystemApi
    public interface MediaQualityStatusChangedListener {
        void onMediaQualityStatusChanged(MediaQualityStatus mediaQualityStatus);
    }

    public interface MessageWaitingIndicatorListener {
        void onMessageWaitingIndicatorChanged(boolean z);
    }

    @SystemApi
    public interface OutgoingEmergencyCallListener {
        void onOutgoingEmergencyCall(EmergencyNumber emergencyNumber, int i);
    }

    @SystemApi
    public interface OutgoingEmergencySmsListener {
        void onOutgoingEmergencySms(EmergencyNumber emergencyNumber, int i);
    }

    @SystemApi
    public interface PhoneCapabilityListener {
        void onPhoneCapabilityChanged(PhoneCapability phoneCapability);
    }

    public interface PhysicalChannelConfigListener {
        void onPhysicalChannelConfigChanged(List<PhysicalChannelConfig> list);
    }

    @SystemApi
    public interface PreciseCallStateListener {
        void onPreciseCallStateChanged(PreciseCallState preciseCallState);
    }

    public interface PreciseDataConnectionStateListener {
        void onPreciseDataConnectionStateChanged(PreciseDataConnectionState preciseDataConnectionState);
    }

    @SystemApi
    public interface RadioPowerStateListener {
        void onRadioPowerStateChanged(int i);
    }

    public interface RegistrationFailedListener {
        void onRegistrationFailed(CellIdentity cellIdentity, String str, int i, int i2, int i3);
    }

    public interface ServiceStateListener {
        void onServiceStateChanged(ServiceState serviceState);
    }

    public interface SignalStrengthsListener {
        void onSignalStrengthsChanged(SignalStrength signalStrength);
    }

    @SystemApi
    public interface SimultaneousCellularCallingSupportListener {
        void onSimultaneousCellularCallingSubscriptionsChanged(Set<Integer> set);
    }

    @SystemApi
    public interface SrvccStateListener {
        void onSrvccStateChanged(int i);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface TelephonyEvent {
    }

    public interface UserMobileDataStateListener {
        void onUserMobileDataStateChanged(boolean z);
    }

    @SystemApi
    public interface VoiceActivationStateListener {
        void onVoiceActivationStateChanged(int i);
    }

    public void init(Executor executor) {
        if (executor == null) {
            throw new IllegalArgumentException("TelephonyCallback Executor must be non-null");
        }
        this.callback = new IPhoneStateListenerStub(this, executor);
    }

    @SystemApi
    public interface CallAttributesListener {
        @Deprecated
        default void onCallAttributesChanged(CallAttributes callAttributes) {
            Log.w(TelephonyCallback.LOG_TAG, "onCallAttributesChanged(List<CallState>) should be overridden.");
        }

        default void onCallStatesChanged(List<CallState> callStateList) {
            if (callStateList.size() > 0) {
                int foregroundCallState = 0;
                int backgroundCallState = 0;
                int ringingCallState = 0;
                for (CallState cs : callStateList) {
                    switch (cs.getCallClassification()) {
                        case 0:
                            ringingCallState = cs.getCallState();
                            break;
                        case 1:
                            foregroundCallState = cs.getCallState();
                            break;
                        case 2:
                            backgroundCallState = cs.getCallState();
                            break;
                    }
                }
                onCallAttributesChanged(new CallAttributes(new PreciseCallState(ringingCallState, foregroundCallState, backgroundCallState, -1, -1), callStateList.get(0).getNetworkType(), callStateList.get(0).getCallQuality()));
                return;
            }
            onCallAttributesChanged(new CallAttributes(new PreciseCallState(0, 0, 0, -1, -1), 0, new CallQuality()));
        }
    }

    public interface CarrierRoamingNtnModeListener {
        void onCarrierRoamingNtnModeChanged(boolean z);

        default void onCarrierRoamingNtnEligibleStateChanged(boolean eligible) {
        }

        default void onCarrierRoamingNtnAvailableServicesChanged(List<Integer> availableServices) {
        }

        default void onCarrierRoamingNtnSignalStrengthChanged(NtnSignalStrength ntnSignalStrength) {
        }
    }

    public interface SemSatelliteStateListener {
        default void onSemSatelliteServiceStateChanged(SemSatelliteServiceState serviceState) {
        }

        default void onSemSatelliteSignalStrengthChanged(SemSatelliteSignalStrength signalStrength) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class IPhoneStateListenerStub extends IPhoneStateListener.Stub {
        private Executor mExecutor;
        private WeakReference<TelephonyCallback> mTelephonyCallbackWeakRef;

        IPhoneStateListenerStub(TelephonyCallback telephonyCallback, Executor executor) {
            this.mTelephonyCallbackWeakRef = new WeakReference<>(telephonyCallback);
            this.mExecutor = executor;
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onServiceStateChanged(final ServiceState serviceState) {
            final ServiceStateListener listener = (ServiceStateListener) this.mTelephonyCallbackWeakRef.get();
            if (listener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda56
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onServiceStateChanged$1(listener, serviceState);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onServiceStateChanged$1(final ServiceStateListener listener, final ServiceState serviceState) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda78
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.ServiceStateListener.this.onServiceStateChanged(serviceState);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onSignalStrengthChanged(int asu) {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onMessageWaitingIndicatorChanged(final boolean mwi) {
            final MessageWaitingIndicatorListener listener = (MessageWaitingIndicatorListener) this.mTelephonyCallbackWeakRef.get();
            if (listener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda19
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onMessageWaitingIndicatorChanged$3(listener, mwi);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onMessageWaitingIndicatorChanged$3(final MessageWaitingIndicatorListener listener, final boolean mwi) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.MessageWaitingIndicatorListener.this.onMessageWaitingIndicatorChanged(mwi);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCallForwardingIndicatorChanged(final boolean cfi) {
            final CallForwardingIndicatorListener listener = (CallForwardingIndicatorListener) this.mTelephonyCallbackWeakRef.get();
            if (listener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda9
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onCallForwardingIndicatorChanged$5(listener, cfi);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCallForwardingIndicatorChanged$5(final CallForwardingIndicatorListener listener, final boolean cfi) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.CallForwardingIndicatorListener.this.onCallForwardingIndicatorChanged(cfi);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCellLocationChanged(CellIdentity cellIdentity) {
            final CellLocation location = cellIdentity == null ? CellLocation.getEmpty() : cellIdentity.asCellLocation();
            final CellLocationListener listener = (CellLocationListener) this.mTelephonyCallbackWeakRef.get();
            if (listener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda60
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onCellLocationChanged$7(listener, location);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCellLocationChanged$7(final CellLocationListener listener, final CellLocation location) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda15
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.CellLocationListener.this.onCellLocationChanged(location);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onLegacyCallStateChanged(int state, String incomingNumber) {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCallStateChanged(final int state) {
            final CallStateListener listener = (CallStateListener) this.mTelephonyCallbackWeakRef.get();
            if (listener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda77
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onCallStateChanged$9(listener, state);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCallStateChanged$9(final CallStateListener listener, final int state) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda55
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.CallStateListener.this.onCallStateChanged(state);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onDataConnectionStateChanged(final int state, final int networkType) {
            final DataConnectionStateListener listener = (DataConnectionStateListener) this.mTelephonyCallbackWeakRef.get();
            if (listener == null) {
                return;
            }
            if (state == 4 && VMRuntime.getRuntime().getTargetSdkVersion() < 30) {
                Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda37
                    @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                    public final void runOrThrow() {
                        TelephonyCallback.IPhoneStateListenerStub.this.lambda$onDataConnectionStateChanged$11(listener, networkType);
                    }
                });
            } else {
                Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda38
                    @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                    public final void runOrThrow() {
                        TelephonyCallback.IPhoneStateListenerStub.this.lambda$onDataConnectionStateChanged$13(listener, state, networkType);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDataConnectionStateChanged$11(final DataConnectionStateListener listener, final int networkType) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.DataConnectionStateListener.this.onDataConnectionStateChanged(2, networkType);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDataConnectionStateChanged$13(final DataConnectionStateListener listener, final int state, final int networkType) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.DataConnectionStateListener.this.onDataConnectionStateChanged(state, networkType);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onDataActivity(final int direction) {
            final DataActivityListener listener = (DataActivityListener) this.mTelephonyCallbackWeakRef.get();
            if (listener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda17
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onDataActivity$15(listener, direction);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDataActivity$15(final DataActivityListener listener, final int direction) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda11
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.DataActivityListener.this.onDataActivity(direction);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onSignalStrengthsChanged(final SignalStrength signalStrength) {
            final SignalStrengthsListener listener = (SignalStrengthsListener) this.mTelephonyCallbackWeakRef.get();
            if (listener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda35
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onSignalStrengthsChanged$17(listener, signalStrength);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSignalStrengthsChanged$17(final SignalStrengthsListener listener, final SignalStrength signalStrength) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda48
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.SignalStrengthsListener.this.onSignalStrengthsChanged(signalStrength);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCellInfoChanged(final List<CellInfo> cellInfo) {
            final CellInfoListener listener = (CellInfoListener) this.mTelephonyCallbackWeakRef.get();
            if (listener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda64
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onCellInfoChanged$19(listener, cellInfo);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCellInfoChanged$19(final CellInfoListener listener, final List cellInfo) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.CellInfoListener.this.onCellInfoChanged(cellInfo);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onPreciseCallStateChanged(final PreciseCallState callState) {
            final PreciseCallStateListener listener = (PreciseCallStateListener) this.mTelephonyCallbackWeakRef.get();
            if (listener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda16
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onPreciseCallStateChanged$21(listener, callState);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPreciseCallStateChanged$21(final PreciseCallStateListener listener, final PreciseCallState callState) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda62
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.PreciseCallStateListener.this.onPreciseCallStateChanged(callState);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCallDisconnectCauseChanged(final int disconnectCause, final int preciseDisconnectCause) {
            final CallDisconnectCauseListener listener = (CallDisconnectCauseListener) this.mTelephonyCallbackWeakRef.get();
            if (listener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda4
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onCallDisconnectCauseChanged$23(listener, disconnectCause, preciseDisconnectCause);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCallDisconnectCauseChanged$23(final CallDisconnectCauseListener listener, final int disconnectCause, final int preciseDisconnectCause) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda39
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.CallDisconnectCauseListener.this.onCallDisconnectCauseChanged(disconnectCause, preciseDisconnectCause);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onPreciseDataConnectionStateChanged(final PreciseDataConnectionState dataConnectionState) {
            final PreciseDataConnectionStateListener listener = (PreciseDataConnectionStateListener) this.mTelephonyCallbackWeakRef.get();
            if (listener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda59
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onPreciseDataConnectionStateChanged$25(listener, dataConnectionState);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPreciseDataConnectionStateChanged$25(final PreciseDataConnectionStateListener listener, final PreciseDataConnectionState dataConnectionState) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda20
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.PreciseDataConnectionStateListener.this.onPreciseDataConnectionStateChanged(dataConnectionState);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onDataConnectionRealTimeInfoChanged(DataConnectionRealTimeInfo dcRtInfo) {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onSrvccStateChanged(final int state) {
            final SrvccStateListener listener = (SrvccStateListener) this.mTelephonyCallbackWeakRef.get();
            if (listener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda68
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onSrvccStateChanged$27(listener, state);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSrvccStateChanged$27(final SrvccStateListener listener, final int state) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda65
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.SrvccStateListener.this.onSrvccStateChanged(state);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onVoiceActivationStateChanged(final int activationState) {
            final VoiceActivationStateListener listener = (VoiceActivationStateListener) this.mTelephonyCallbackWeakRef.get();
            if (listener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda12
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onVoiceActivationStateChanged$29(listener, activationState);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onVoiceActivationStateChanged$29(final VoiceActivationStateListener listener, final int activationState) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda32
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.VoiceActivationStateListener.this.onVoiceActivationStateChanged(activationState);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onDataActivationStateChanged(final int activationState) {
            final DataActivationStateListener listener = (DataActivationStateListener) this.mTelephonyCallbackWeakRef.get();
            if (listener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda54
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onDataActivationStateChanged$31(listener, activationState);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDataActivationStateChanged$31(final DataActivationStateListener listener, final int activationState) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda84
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.DataActivationStateListener.this.onDataActivationStateChanged(activationState);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onUserMobileDataStateChanged(final boolean enabled) {
            final UserMobileDataStateListener listener = (UserMobileDataStateListener) this.mTelephonyCallbackWeakRef.get();
            if (listener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda13
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onUserMobileDataStateChanged$33(listener, enabled);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onUserMobileDataStateChanged$33(final UserMobileDataStateListener listener, final boolean enabled) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda27
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.UserMobileDataStateListener.this.onUserMobileDataStateChanged(enabled);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onDisplayInfoChanged(final TelephonyDisplayInfo telephonyDisplayInfo) {
            final DisplayInfoListener listener = (DisplayInfoListener) this.mTelephonyCallbackWeakRef.get();
            if (listener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda36
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onDisplayInfoChanged$35(listener, telephonyDisplayInfo);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDisplayInfoChanged$35(final DisplayInfoListener listener, final TelephonyDisplayInfo telephonyDisplayInfo) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda33
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.DisplayInfoListener.this.onDisplayInfoChanged(telephonyDisplayInfo);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onOemHookRawEvent(byte[] rawData) {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCarrierNetworkChange(final boolean active) {
            final CarrierNetworkListener listener = (CarrierNetworkListener) this.mTelephonyCallbackWeakRef.get();
            if (listener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda71
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onCarrierNetworkChange$37(listener, active);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCarrierNetworkChange$37(final CarrierNetworkListener listener, final boolean active) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.CarrierNetworkListener.this.onCarrierNetworkChange(active);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onEmergencyNumberListChanged(final Map emergencyNumberList) {
            final EmergencyNumberListListener listener = (EmergencyNumberListListener) this.mTelephonyCallbackWeakRef.get();
            if (listener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda43
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onEmergencyNumberListChanged$39(listener, emergencyNumberList);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onEmergencyNumberListChanged$39(final EmergencyNumberListListener listener, final Map emergencyNumberList) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda46
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.EmergencyNumberListListener.this.onEmergencyNumberListChanged(emergencyNumberList);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onOutgoingEmergencyCall(final EmergencyNumber placedEmergencyNumber, final int subscriptionId) {
            final OutgoingEmergencyCallListener listener = (OutgoingEmergencyCallListener) this.mTelephonyCallbackWeakRef.get();
            if (listener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda34
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onOutgoingEmergencyCall$41(listener, placedEmergencyNumber, subscriptionId);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onOutgoingEmergencyCall$41(final OutgoingEmergencyCallListener listener, final EmergencyNumber placedEmergencyNumber, final int subscriptionId) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda66
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.OutgoingEmergencyCallListener.this.onOutgoingEmergencyCall(placedEmergencyNumber, subscriptionId);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onOutgoingEmergencySms(final EmergencyNumber sentEmergencyNumber, final int subscriptionId) {
            final OutgoingEmergencySmsListener listener = (OutgoingEmergencySmsListener) this.mTelephonyCallbackWeakRef.get();
            if (listener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda57
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onOutgoingEmergencySms$43(listener, sentEmergencyNumber, subscriptionId);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onOutgoingEmergencySms$43(final OutgoingEmergencySmsListener listener, final EmergencyNumber sentEmergencyNumber, final int subscriptionId) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda26
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.OutgoingEmergencySmsListener.this.onOutgoingEmergencySms(sentEmergencyNumber, subscriptionId);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onPhoneCapabilityChanged(final PhoneCapability capability) {
            final PhoneCapabilityListener listener = (PhoneCapabilityListener) this.mTelephonyCallbackWeakRef.get();
            if (listener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda81
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onPhoneCapabilityChanged$45(listener, capability);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPhoneCapabilityChanged$45(final PhoneCapabilityListener listener, final PhoneCapability capability) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda72
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.PhoneCapabilityListener.this.onPhoneCapabilityChanged(capability);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onRadioPowerStateChanged(final int state) {
            final RadioPowerStateListener listener = (RadioPowerStateListener) this.mTelephonyCallbackWeakRef.get();
            if (listener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda50
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onRadioPowerStateChanged$47(listener, state);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onRadioPowerStateChanged$47(final RadioPowerStateListener listener, final int state) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.RadioPowerStateListener.this.onRadioPowerStateChanged(state);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCallStatesChanged(final List<CallState> callStateList) {
            final CallAttributesListener listener = (CallAttributesListener) this.mTelephonyCallbackWeakRef.get();
            if (listener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda24
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onCallStatesChanged$49(listener, callStateList);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCallStatesChanged$49(final CallAttributesListener listener, final List callStateList) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda75
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.CallAttributesListener.this.onCallStatesChanged(callStateList);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onActiveDataSubIdChanged(final int subId) {
            final ActiveDataSubscriptionIdListener listener = (ActiveDataSubscriptionIdListener) this.mTelephonyCallbackWeakRef.get();
            if (listener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda25
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onActiveDataSubIdChanged$51(listener, subId);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onActiveDataSubIdChanged$51(final ActiveDataSubscriptionIdListener listener, final int subId) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda69
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.ActiveDataSubscriptionIdListener.this.onActiveDataSubscriptionIdChanged(subId);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onImsCallDisconnectCauseChanged(final ImsReasonInfo disconnectCause) {
            final ImsCallDisconnectCauseListener listener = (ImsCallDisconnectCauseListener) this.mTelephonyCallbackWeakRef.get();
            if (listener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda44
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onImsCallDisconnectCauseChanged$53(listener, disconnectCause);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onImsCallDisconnectCauseChanged$53(final ImsCallDisconnectCauseListener listener, final ImsReasonInfo disconnectCause) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda73
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.ImsCallDisconnectCauseListener.this.onImsCallDisconnectCauseChanged(disconnectCause);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onRegistrationFailed(final CellIdentity cellIdentity, final String chosenPlmn, final int domain, final int causeCode, final int additionalCauseCode) {
            final RegistrationFailedListener listener = (RegistrationFailedListener) this.mTelephonyCallbackWeakRef.get();
            if (listener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda67
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onRegistrationFailed$55(listener, cellIdentity, chosenPlmn, domain, causeCode, additionalCauseCode);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onRegistrationFailed$55(final RegistrationFailedListener listener, final CellIdentity cellIdentity, final String chosenPlmn, final int domain, final int causeCode, final int additionalCauseCode) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda21
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.RegistrationFailedListener.this.onRegistrationFailed(cellIdentity, chosenPlmn, domain, causeCode, additionalCauseCode);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onBarringInfoChanged(final BarringInfo barringInfo) {
            final BarringInfoListener listener = (BarringInfoListener) this.mTelephonyCallbackWeakRef.get();
            if (listener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda82
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onBarringInfoChanged$57(listener, barringInfo);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onBarringInfoChanged$57(final BarringInfoListener listener, final BarringInfo barringInfo) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda28
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.BarringInfoListener.this.onBarringInfoChanged(barringInfo);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onPhysicalChannelConfigChanged(final List<PhysicalChannelConfig> configs) {
            final PhysicalChannelConfigListener listener = (PhysicalChannelConfigListener) this.mTelephonyCallbackWeakRef.get();
            if (listener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda40
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onPhysicalChannelConfigChanged$59(listener, configs);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPhysicalChannelConfigChanged$59(final PhysicalChannelConfigListener listener, final List configs) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda22
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.PhysicalChannelConfigListener.this.onPhysicalChannelConfigChanged(configs);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onDataEnabledChanged(final boolean enabled, final int reason) {
            final DataEnabledListener listener = (DataEnabledListener) this.mTelephonyCallbackWeakRef.get();
            if (listener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda1
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onDataEnabledChanged$61(listener, enabled, reason);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDataEnabledChanged$61(final DataEnabledListener listener, final boolean enabled, final int reason) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda18
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.DataEnabledListener.this.onDataEnabledChanged(enabled, reason);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onAllowedNetworkTypesChanged(final int reason, final long allowedNetworkType) {
            final AllowedNetworkTypesListener listener = (AllowedNetworkTypesListener) this.mTelephonyCallbackWeakRef.get();
            if (listener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda79
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onAllowedNetworkTypesChanged$63(listener, reason, allowedNetworkType);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAllowedNetworkTypesChanged$63(final AllowedNetworkTypesListener listener, final int reason, final long allowedNetworkType) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda61
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.AllowedNetworkTypesListener.this.onAllowedNetworkTypesChanged(reason, allowedNetworkType);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onSimultaneousCallingStateChanged(final int[] subIds) {
            final SimultaneousCellularCallingSupportListener listener = (SimultaneousCellularCallingSupportListener) this.mTelephonyCallbackWeakRef.get();
            if (listener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda30
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onSimultaneousCallingStateChanged$65(listener, subIds);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSimultaneousCallingStateChanged$65(final SimultaneousCellularCallingSupportListener listener, final int[] subIds) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda29
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.SimultaneousCellularCallingSupportListener.this.onSimultaneousCellularCallingSubscriptionsChanged((Set) Arrays.stream(subIds).boxed().collect(Collectors.toSet()));
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onLinkCapacityEstimateChanged(final List<LinkCapacityEstimate> linkCapacityEstimateList) {
            final LinkCapacityEstimateChangedListener listener = (LinkCapacityEstimateChangedListener) this.mTelephonyCallbackWeakRef.get();
            if (listener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda85
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onLinkCapacityEstimateChanged$67(listener, linkCapacityEstimateList);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onLinkCapacityEstimateChanged$67(final LinkCapacityEstimateChangedListener listener, final List linkCapacityEstimateList) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda41
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.LinkCapacityEstimateChangedListener.this.onLinkCapacityEstimateChanged(linkCapacityEstimateList);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onMediaQualityStatusChanged(final MediaQualityStatus mediaQualityStatus) {
            final MediaQualityStatusChangedListener listener = (MediaQualityStatusChangedListener) this.mTelephonyCallbackWeakRef.get();
            if (listener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda42
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onMediaQualityStatusChanged$69(listener, mediaQualityStatus);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onMediaQualityStatusChanged$69(final MediaQualityStatusChangedListener listener, final MediaQualityStatus mediaQualityStatus) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda80
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.MediaQualityStatusChangedListener.this.onMediaQualityStatusChanged(mediaQualityStatus);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCallBackModeStarted(final int type) {
            final EmergencyCallbackModeListener listener = (EmergencyCallbackModeListener) this.mTelephonyCallbackWeakRef.get();
            Log.d(TelephonyCallback.LOG_TAG, "onCallBackModeStarted:type=" + type + ", listener=" + listener);
            if (listener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda52
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onCallBackModeStarted$71(listener, type);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCallBackModeStarted$71(final EmergencyCallbackModeListener listener, final int type) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda58
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.EmergencyCallbackModeListener.this.onCallBackModeStarted(type);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCallBackModeStopped(final int type, final int reason) {
            final EmergencyCallbackModeListener listener = (EmergencyCallbackModeListener) this.mTelephonyCallbackWeakRef.get();
            Log.d(TelephonyCallback.LOG_TAG, "onCallBackModeStopped:type=" + type + ", reason=" + reason + ", listener=" + listener);
            if (listener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda74
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onCallBackModeStopped$73(listener, type, reason);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCallBackModeStopped$73(final EmergencyCallbackModeListener listener, final int type, final int reason) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda45
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.EmergencyCallbackModeListener.this.onCallBackModeStopped(type, reason);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCarrierRoamingNtnModeChanged(final boolean active) {
            final CarrierRoamingNtnModeListener listener;
            if (Flags.carrierEnabledSatelliteFlag() && (listener = (CarrierRoamingNtnModeListener) this.mTelephonyCallbackWeakRef.get()) != null) {
                Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda83
                    @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                    public final void runOrThrow() {
                        TelephonyCallback.IPhoneStateListenerStub.this.lambda$onCarrierRoamingNtnModeChanged$75(listener, active);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCarrierRoamingNtnModeChanged$75(final CarrierRoamingNtnModeListener listener, final boolean active) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda63
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.CarrierRoamingNtnModeListener.this.onCarrierRoamingNtnModeChanged(active);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCarrierRoamingNtnEligibleStateChanged(final boolean eligible) {
            final CarrierRoamingNtnModeListener listener;
            if (Flags.carrierRoamingNbIotNtn() && (listener = (CarrierRoamingNtnModeListener) this.mTelephonyCallbackWeakRef.get()) != null) {
                Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda76
                    @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                    public final void runOrThrow() {
                        TelephonyCallback.IPhoneStateListenerStub.this.lambda$onCarrierRoamingNtnEligibleStateChanged$77(listener, eligible);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCarrierRoamingNtnEligibleStateChanged$77(final CarrierRoamingNtnModeListener listener, final boolean eligible) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.CarrierRoamingNtnModeListener.this.onCarrierRoamingNtnEligibleStateChanged(eligible);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCarrierRoamingNtnAvailableServicesChanged(int[] availableServices) {
            final CarrierRoamingNtnModeListener listener;
            if (Flags.carrierRoamingNbIotNtn() && (listener = (CarrierRoamingNtnModeListener) this.mTelephonyCallbackWeakRef.get()) != null) {
                final List<Integer> ServiceList = (List) Arrays.stream(availableServices).boxed().collect(Collectors.toList());
                Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda31
                    @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                    public final void runOrThrow() {
                        TelephonyCallback.IPhoneStateListenerStub.this.lambda$onCarrierRoamingNtnAvailableServicesChanged$79(listener, ServiceList);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCarrierRoamingNtnAvailableServicesChanged$79(final CarrierRoamingNtnModeListener listener, final List ServiceList) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda47
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.CarrierRoamingNtnModeListener.this.onCarrierRoamingNtnAvailableServicesChanged(ServiceList);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCarrierRoamingNtnSignalStrengthChanged(final NtnSignalStrength ntnSignalStrength) {
            final CarrierRoamingNtnModeListener listener;
            if (Flags.carrierRoamingNbIotNtn() && (listener = (CarrierRoamingNtnModeListener) this.mTelephonyCallbackWeakRef.get()) != null) {
                Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda51
                    @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                    public final void runOrThrow() {
                        TelephonyCallback.IPhoneStateListenerStub.this.lambda$onCarrierRoamingNtnSignalStrengthChanged$81(listener, ntnSignalStrength);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCarrierRoamingNtnSignalStrengthChanged$81(final CarrierRoamingNtnModeListener listener, final NtnSignalStrength ntnSignalStrength) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda70
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.CarrierRoamingNtnModeListener.this.onCarrierRoamingNtnSignalStrengthChanged(ntnSignalStrength);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onSemSatelliteServiceStateChanged(final SemSatelliteServiceState serviceState) {
            final SemSatelliteStateListener listener = (SemSatelliteStateListener) this.mTelephonyCallbackWeakRef.get();
            if (listener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda14
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onSemSatelliteServiceStateChanged$83(listener, serviceState);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSemSatelliteServiceStateChanged$83(final SemSatelliteStateListener listener, final SemSatelliteServiceState serviceState) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda53
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.SemSatelliteStateListener.this.onSemSatelliteServiceStateChanged(serviceState);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onSemSatelliteSignalStrengthChanged(final SemSatelliteSignalStrength signalStrength) {
            final SemSatelliteStateListener listener = (SemSatelliteStateListener) this.mTelephonyCallbackWeakRef.get();
            if (listener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda23
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onSemSatelliteSignalStrengthChanged$85(listener, signalStrength);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSemSatelliteSignalStrengthChanged$85(final SemSatelliteStateListener listener, final SemSatelliteSignalStrength signalStrength) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda49
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.SemSatelliteStateListener.this.onSemSatelliteSignalStrengthChanged(signalStrength);
                }
            });
        }
    }
}
