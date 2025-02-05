package com.sec.internal.ims.servicemodules.volte2;

import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Bundle;
import android.os.Message;
import android.os.SemSystemProperties;
import android.os.SystemClock;
import android.util.Log;
import com.sec.ims.ImsRegistration;
import com.sec.ims.util.SipError;
import com.sec.ims.volte2.data.CallProfile;
import com.sec.internal.constants.Mno;
import com.sec.internal.constants.ims.SipErrorBase;
import com.sec.internal.constants.ims.SipReason;
import com.sec.internal.helper.BlockedNumberUtil;
import com.sec.internal.helper.ImsCallUtil;
import com.sec.internal.helper.SimUtil;
import com.sec.internal.helper.os.DeviceUtil;
import com.sec.internal.ims.core.RegistrationGovernor;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Id;
import com.sec.internal.ims.core.sim.SimManagerFactory;
import com.sec.internal.ims.registry.ImsRegistry;
import com.sec.internal.ims.servicemodules.ss.UtStateMachine;
import com.sec.internal.ims.servicemodules.volte2.vcid.VcidHelper;
import com.sec.internal.ims.servicemodules.volte2.vcid.exception.NoFileUrlOnAlertInfoException;
import com.sec.internal.interfaces.ims.core.IRegistrationGovernor;
import com.sec.internal.interfaces.ims.core.handler.IVolteServiceInterface;
import com.sec.internal.log.IMSLog;
import com.sec.sve.generalevent.BuiltVcidEvent;
import com.sec.sve.generalevent.VcidEvent;

/* loaded from: classes.dex */
public class ImsIncomingCall extends CallState {
    private ConnectivityManager.NetworkCallback mDefaultNetworkCallback;
    private Message mDummyDnsTimeoutMessage;
    private boolean mIsADSChanged;
    private Message mReinviteTimeoutMessage;

    ImsIncomingCall(CallStateMachine callStateMachine) {
        super(callStateMachine);
        this.mDummyDnsTimeoutMessage = null;
        this.mReinviteTimeoutMessage = null;
        this.mIsADSChanged = false;
        this.mDefaultNetworkCallback = null;
    }

    @Override // com.sec.internal.helper.State, com.sec.internal.helper.IState
    public void enter() {
        this.mCsm.resetCallTypeAndErrorFlags();
        Log.i(this.LOG_TAG, "Enter [IncomingCall]");
        if (this.mCsm.needToLogForATTGate(this.mSession.getCallProfile().getCallType())) {
            IMSLog.g("GATE", "<GATE-M>INCOMING_VIDEO_CALL</GATE-M>");
        }
        IMSLog.lazer(IMSLog.LAZER_TYPE.CALL, this.mSession.getCallId() + " - START INCOMING");
        this.mSession.mStartTime = System.currentTimeMillis();
        this.mCsm.mCallRingingTime = SystemClock.elapsedRealtime();
        if (this.mRegistration != null) {
            this.mCsm.startRingTimer(r0.getImsProfile().getRingingTimer() * 1000);
        }
        KeepAliveSender keepAliveSender = this.mSession.mKaSender;
        if (keepAliveSender != null) {
            keepAliveSender.start();
        }
        handleCameraForIncomingCall();
        if (this.mSession.getCallProfile().getHistoryInfo() != null) {
            this.mCsm.notifyOnCallForwarded();
        }
        handleVCIDEvent();
    }

    private void handleCameraForIncomingCall() {
        int determineCamera = this.mCsm.determineCamera(this.mSession.getCallProfile().getCallType(), false);
        if (determineCamera >= 0) {
            if (this.mModule.getVideoCallCount(-1) > 1) {
                this.mSession.startCamera(determineCamera);
            } else if (ImsCallUtil.isTPhoneMode(this.mContext)) {
                Log.d(this.LOG_TAG, "delay camera start due to check isTPhoneRelaxMode");
                this.mCsm.sendMessageDelayed(24, 0, -1, 1500L);
            } else {
                Log.d(this.LOG_TAG, "delay camera start due to check using by other app");
                this.mCsm.sendMessageDelayed(24, 0, -1, 100L);
            }
        }
    }

    private void handleVCIDEvent() {
        Log.i(this.LOG_TAG, "determine vcid event");
        if (isVcidAvailable(this.mSession.getCallProfile().getAlertInfo())) {
            BuiltVcidEvent build = new BuiltVcidEvent.Builder().setAction(VcidEvent.BUNDLE_VALUE_ACTION_SET_VCID_ENGINE).build();
            this.mMediaController.sendGeneralBundleEvent(build.getEvent(), build.getBundle());
            if (ImsCallUtil.isDataPreferredModeDuringCalling(this.mContext) && SimUtil.getActiveDataPhoneId() != this.mSession.getPhoneId()) {
                Log.i(this.LOG_TAG, "registerForADSChange");
                this.mIsADSChanged = false;
                registerDefaultNetworkCallback();
                SimManagerFactory.registerForADSChange(this.mCsm.getHandler(), 900, null);
                this.mCsm.sendMessageDelayed(800, 1000L);
                return;
            }
            this.mCsm.sendMessage(800);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.sec.internal.helper.State, com.sec.internal.helper.IState
    public boolean processMessage(Message message) {
        Log.i(this.LOG_TAG, "[IncomingCall] processMessage " + message.what);
        switch (message.what) {
            case 1:
                int terminate_IncomingCall = terminate_IncomingCall(message);
                if (terminate_IncomingCall != -1) {
                    return terminate_IncomingCall == 1;
                }
                reject_IncomingCall(message);
                return true;
            case 3:
            case 4:
            case 94:
            case 100:
            case 400:
                return false;
            case 22:
                accept_IncomingCall(message);
                return true;
            case 23:
                reject_IncomingCall(message);
                return true;
            case 24:
                delayedCamStart_IncomingCall(message);
                return true;
            case 32:
                earlymedia_IncomingCall(message);
                return true;
            case 41:
                established_IncomingCall(message);
                return true;
            case 51:
                hold_IncomingCall(message);
                return true;
            case 52:
                update_IncomingCall(message);
                return true;
            case 64:
                sendText_IncomingCall(message);
                return true;
            case 80:
                Log.i(this.LOG_TAG, "[IncomingCall] Hold video defered");
                this.mCsm.deferMessage(message);
                return true;
            case 81:
                Log.i(this.LOG_TAG, "[IncomingCall] Resume video defered");
                CallStateMachine callStateMachine = this.mCsm;
                callStateMachine.isDeferedVideoResume = true;
                callStateMachine.deferMessage(message);
                return true;
            case 86:
                Log.i(this.LOG_TAG, "[IncomingCall] Receive CMC DTMF EVENT.");
                this.mCsm.notifyCmcDtmfEvent(message.arg1);
                return true;
            case 204:
                ringTimeout_IncomingCall(message);
                return true;
            case 302:
                Log.i(this.LOG_TAG, "[IncomingCall] Re-INVITE Timer expired defered");
                this.mCsm.deferMessage(message);
                return true;
            case 305:
                Log.i(this.LOG_TAG, "[IncomingCall] Sending Dummy Dns");
                this.mRegistrationManager.sendDummyDnsQuery();
                startDummyDnsTimer();
                return true;
            case 502:
                this.mCsm.mReinvite = true;
                Log.i(this.LOG_TAG, "[IncomingCall] Re-INVITE defered");
                this.mCsm.deferMessage(message);
                return true;
            case 800:
                removePendingVcidEvent();
                sendVcidEvent(this.mSession.getCallProfile().getAlertInfo());
                return true;
            case 900:
                Log.i(this.LOG_TAG, "ADSChanged for VCID is set as true");
                this.mIsADSChanged = true;
                return true;
            case 5000:
                dbrLost_IncomingCall(message);
                return true;
            default:
                Log.e(this.LOG_TAG, "[" + getName() + "] msg:" + message.what + " ignored !!!");
                return true;
        }
    }

    private boolean isVcidAvailable(String str) {
        ImsRegistration imsRegistration = this.mRegistration;
        return imsRegistration != null && imsRegistration.getImsProfile().isEnableVcid() && VcidHelper.isVcidUrlExist(str) && VcidHelper.isVcidCapable(this.mContext, str) && !ImsCallUtil.isTPhoneMode(this.mContext);
    }

    private void removePendingVcidEvent() {
        if (this.mCsm.hasMessages(800)) {
            Log.i(this.LOG_TAG, "unregisterForADSChange");
            this.mCsm.removeMessages(800);
            SimManagerFactory.unregisterForADSChange(this.mCsm.getHandler());
        }
        this.mIsADSChanged = false;
        unregisterNetworkCallback();
    }

    private void sendVcidEvent(String str) {
        if (isVcidAvailable(str)) {
            try {
                BuiltVcidEvent build = new BuiltVcidEvent.Builder().setAction("start").setFileUrl(VcidHelper.getFileUrl(str)).setServiceType(VcidHelper.isVideoVcid(str) ? "VCID" : VcidEvent.BUNDLE_VALUE_SERVICE_TYPE_MYVIEW).setSessionId(this.mSession.getSessionId()).setSubId(SimUtil.getSubId(this.mSession.getPhoneId())).build();
                IMSLog.d(this.LOG_TAG, "requesting Vcid, " + build.toString());
                this.mMediaController.sendGeneralBundleEvent(build.getEvent(), build.getBundle());
            } catch (NoFileUrlOnAlertInfoException e) {
                e.printStackTrace();
            }
        }
    }

    protected void startDummyDnsTimer() {
        stopDummyDnsTimer();
        Log.i(this.LOG_TAG, "startDummyDnsTimer");
        Message obtainMessage = this.mCsm.obtainMessage(305);
        this.mDummyDnsTimeoutMessage = obtainMessage;
        this.mSession.mAm.sendMessageDelayed(obtainMessage, 8000L);
    }

    protected void stopDummyDnsTimer() {
        if (this.mDummyDnsTimeoutMessage == null) {
            return;
        }
        Log.i(this.LOG_TAG, "stopDummyDnsTimer");
        this.mSession.mAm.removeMessage(this.mDummyDnsTimeoutMessage);
        this.mDummyDnsTimeoutMessage = null;
    }

    @Override // com.sec.internal.helper.State, com.sec.internal.helper.IState
    public void exit() {
        removePendingVcidEvent();
        BuiltVcidEvent build = new BuiltVcidEvent.Builder().setAction(VcidEvent.BUNDLE_VALUE_ACTION_STOP).build();
        this.mMediaController.sendGeneralBundleEvent(build.getEvent(), build.getBundle());
        this.mCsm.removeMessages(24);
        this.mCsm.setPreviousState(this);
        this.mCsm.stopRingTimer();
    }

    private void delayedCamStart_IncomingCall(Message message) {
        int i = SemSystemProperties.getInt("service.camera.running", 0);
        int i2 = SemSystemProperties.getInt("service.camera.rec.running", 0);
        int i3 = message.arg1;
        if (i3 < 50 && (i == 1 || i2 == 1)) {
            Log.e(this.LOG_TAG, "trying " + message.arg1 + " delayType = " + message.arg2);
            CallStateMachine callStateMachine = this.mCsm;
            callStateMachine.mCameraUsedAtOtherApp = true;
            if (i == 0 && i2 == 1) {
                callStateMachine.sendMessageDelayed(24, message.arg1 + 1, 2, 100L);
                return;
            } else {
                callStateMachine.sendMessageDelayed(24, message.arg1 + 1, -1, 100L);
                return;
            }
        }
        if (message.arg2 == 2) {
            this.mCsm.sendMessageDelayed(24, i3 + 12, -1, 1200L);
        } else {
            if ((this.mMno == Mno.SKT && this.mSession.isTPhoneRelaxMode()) || BlockedNumberUtil.isBlockedNumber(this.mContext, this.mSession.getCallProfile().getDialingNumber())) {
                return;
            }
            this.mSession.startCamera(this.mCsm.determineCamera(this.mSession.getCallProfile().getCallType(), false));
        }
    }

    private void accept_IncomingCall(Message message) {
        if (this.mRegistration != null && !ImsCallUtil.isCmcPrimaryType(this.mSession.getCmcType())) {
            Log.i(this.LOG_TAG, "bindToNetwork for MT");
            this.mMediaController.bindToNetwork(this.mRegistration.getNetwork());
        }
        if (this.mSession.mModifyRequestedProfile != null) {
            Log.i(this.LOG_TAG, "[IncomingCall] start reinvite timer");
            startReinviteTimer(UtStateMachine.HTTP_READ_TIMEOUT_GCF);
        }
        KeepAliveSender keepAliveSender = this.mSession.mKaSender;
        if (keepAliveSender != null) {
            keepAliveSender.stop();
        }
        this.mCsm.callType = ((CallProfile) message.obj).getCallType();
        handleCallTypeAtIncomingCall();
        Log.i(this.LOG_TAG, "answerCall with callType: " + this.mCsm.callType);
        handleCameraForAcceptCall();
        this.mSession.setIsEstablished(true);
        if (ImsCallUtil.isCmcPrimaryType(this.mSession.getCmcType())) {
            if (this.mVolteSvcIntf.answerCallWithCallType(this.mSession.getSessionId(), this.mCsm.callType, getCMCCallTime()) < 0) {
                this.mCsm.sendMessage(4, 0, -1, new SipError(230, "call session already released"));
                return;
            }
        } else if (this.mVolteSvcIntf.answerCallWithCallType(this.mSession.getSessionId(), this.mCsm.callType) < 0) {
            this.mCsm.sendMessage(4, 0, -1, new SipError(230, "call session already released"));
            return;
        }
        this.mCsm.sendMessageDelayed(212, RegistrationGovernor.RETRY_AFTER_PDNLOST_MS);
        handleFastAccept();
        this.mCsm.mUserAnswered = true;
    }

    private void handleCameraForAcceptCall() {
        this.mCsm.removeMessages(24);
        CallStateMachine callStateMachine = this.mCsm;
        if (callStateMachine.isStartedCamera(callStateMachine.callType, false)) {
            return;
        }
        this.mSession.stopCamera();
        this.mSession.mLastUsedCamera = -1;
    }

    private String getCMCCallTime() {
        Log.i(this.LOG_TAG, "mSession.getCallProfile().getReplaceSipCallId(): " + this.mSession.getCallProfile().getReplaceSipCallId());
        ImsCallSession sessionBySipCallId = this.mModule.getSessionBySipCallId(this.mSession.getCallProfile().getReplaceSipCallId());
        int cmcBoundSessionId = this.mSession.getCallProfile().getCmcBoundSessionId();
        ImsCallSession session = cmcBoundSessionId > 0 ? this.mModule.getSession(cmcBoundSessionId) : null;
        if (sessionBySipCallId != null) {
            this.mSession.getCallProfile().setCmcBoundSessionId(sessionBySipCallId.getSessionId());
            sessionBySipCallId.getCallProfile().setCmcBoundSessionId(this.mSession.mSessionId);
            Log.i(this.LOG_TAG, "PS PD to SD pull");
            return this.mCsm.calculateCmcCallTime(sessionBySipCallId, null);
        }
        if (session != null) {
            Log.i(this.LOG_TAG, "do nothing when SD call answer for PS");
        } else {
            Log.i(this.LOG_TAG, "bounded session is not found");
            if (this.mSession.getCallProfile().getReplaceSipCallId() == null) {
                long currentTimeMillis = System.currentTimeMillis();
                Log.i(this.LOG_TAG, "save SD call answer time for CS : " + currentTimeMillis);
                this.mModule.getCmcServiceHelper().setCallEstablishTimeExtra(currentTimeMillis);
            } else {
                Log.i(this.LOG_TAG, "CS PD to SD pull");
                return this.mCsm.calculateCmcCallTime(null, this.mSession.getCallProfile().getReplaceSipCallId());
            }
        }
        return "";
    }

    private void startReinviteTimer(long j) {
        Log.i(this.LOG_TAG, "startReinviteTimer: " + j);
        stopReinviteTimer();
        Message obtainMessage = this.mCsm.obtainMessage(302);
        this.mReinviteTimeoutMessage = obtainMessage;
        this.mSession.mAm.sendMessageDelayed(obtainMessage, j);
    }

    private void stopReinviteTimer() {
        if (this.mReinviteTimeoutMessage == null) {
            return;
        }
        Log.i(this.LOG_TAG, "stopReinviteTimer");
        this.mSession.mAm.removeMessage(this.mReinviteTimeoutMessage);
        this.mReinviteTimeoutMessage = null;
    }

    private void handleCallTypeAtIncomingCall() {
        ImsRegistration imsRegistration;
        if (ImsCallUtil.isVideoCall(this.mCsm.callType) && !this.mModule.isCallServiceAvailable(this.mSession.getPhoneId(), "mmtel-video") && this.mCsm.callType != 8) {
            Log.i(this.LOG_TAG, "Call Type change Video to Voice for no video feature tag");
            this.mCsm.callType = 1;
        }
        if (ImsCallUtil.isTtyCall(this.mCsm.callType) && (imsRegistration = this.mRegistration) != null && (imsRegistration.getImsProfile().getTtyType() == 1 || this.mRegistration.getImsProfile().getTtyType() == 3)) {
            Log.i(this.LOG_TAG, "CS TTY Enable so do not answer IMS TTY call");
            this.mCsm.callType = 1;
        }
        if (ImsCallUtil.isRttCall(this.mSession.getCallProfile().getCallType())) {
            CallStateMachine callStateMachine = this.mCsm;
            int i = callStateMachine.callType;
            if (i == 1) {
                callStateMachine.callType = 14;
            } else if (i == 2 && !this.mMno.isOneOf(Mno.TMOUS, Mno.DISH)) {
                this.mCsm.callType = 15;
            }
            this.mSession.getCallProfile().getMediaProfile().setRttMode(1);
            return;
        }
        this.mSession.getCallProfile().getMediaProfile().setRttMode(0);
    }

    private void handleFastAccept() {
        if (this.mMno.isKor() || this.mMno == Mno.RJIL) {
            if (ImsCallUtil.isVideoCall(this.mSession.getCallProfile().getCallType()) && !ImsCallUtil.isVideoCall(this.mCsm.callType)) {
                this.mSession.getCallProfile().setDowngradedVideoCall(true);
                this.mSession.getCallProfile().setDowngradedAtEstablish(true);
                this.mSession.setUserCameraOff(false);
            }
            if (this.mSession.getCallProfile().getCallType() == 9) {
                this.mCsm.callType = 9;
            }
            if (this.mMno.isChn() && !ImsCallUtil.isVideoCall(this.mSession.getCallProfile().getCallType())) {
                this.mCsm.callType = 1;
            }
            this.mSession.getCallProfile().setCallType(this.mCsm.callType);
            int totalCallCount = this.mModule.getTotalCallCount(this.mSession.getPhoneId());
            int i = ImsRegistry.getICmcConnectivityController().isEnabledWifiDirectFeature() ? 7 : 5;
            int i2 = 0;
            for (int i3 = 1; i3 <= i; i3 += 2) {
                i2 += this.mModule.getCmcServiceHelper().getSessionCountByCmcType(this.mSession.getPhoneId(), i3);
            }
            Log.i(this.LOG_TAG, "Notify fake ESTABLISH event. callsCount: " + totalCallCount + " pdCallCount: " + i2);
            if ((this.mMno.isKor() || this.mMno.isChn() || this.mMno.isJpn()) && totalCallCount - i2 > 1) {
                Log.i(this.LOG_TAG, "force to set modifiable to false for fake ESTABLISH");
                this.mSession.getCallProfile().setRemoteVideoCapa(false);
            }
            this.mCsm.notifyOnEstablished();
        }
    }

    private int terminate_IncomingCall(Message message) {
        Mno mno;
        CallStateMachine callStateMachine = this.mCsm;
        boolean z = callStateMachine.mUserAnswered;
        if (!z || message.arg1 == 8) {
            return 0;
        }
        if (!z || (mno = this.mMno) == Mno.CHT || mno == Mno.RJIL) {
            return -1;
        }
        callStateMachine.deferMessage(message);
        return 1;
    }

    private void reject_IncomingCall(Message message) {
        this.mCsm.callType = this.mSession.getCallProfile().getCallType();
        this.mCsm.sipError = ImsCallUtil.convertSipErrorToFramework(this.mMno, message.arg1);
        if (ImsCallUtil.isCameraUsingCall(this.mCsm.callType)) {
            this.mCsm.removeMessages(24);
            this.mSession.stopCamera();
        }
        IVolteServiceInterface iVolteServiceInterface = this.mVolteSvcIntf;
        int sessionId = this.mSession.getSessionId();
        CallStateMachine callStateMachine = this.mCsm;
        if (iVolteServiceInterface.rejectCall(sessionId, callStateMachine.callType, callStateMachine.sipError) < 0) {
            this.mCsm.sendMessage(4, 0, -1, new SipError(1006, ""));
            return;
        }
        if (ImsCallUtil.isCmcPrimaryType(this.mSession.getCmcType()) && this.mSession.getCallProfile().getReplaceSipCallId() != null && message.arg1 == 3) {
            this.mModule.getCmcServiceHelper().sendDummyPublishDialog(this.mSession.getPhoneId(), this.mSession.getCmcType());
        }
        if (!this.mCsm.mCameraUsedAtOtherApp) {
            this.mCsm.notifyOnEnded(ImsCallUtil.convertCallEndReasonToFramework(2, message.arg1));
        }
        CallStateMachine callStateMachine2 = this.mCsm;
        callStateMachine2.transitionTo(callStateMachine2.mEndingCall);
    }

    private void dbrLost_IncomingCall(Message message) {
        this.mCsm.callType = this.mSession.getCallProfile().getCallType();
        if (message.arg1 == 1) {
            this.mCsm.sipError = SipErrorBase.PRECONDITION_FAILURE;
            IVolteServiceInterface iVolteServiceInterface = this.mVolteSvcIntf;
            int sessionId = this.mSession.getSessionId();
            CallStateMachine callStateMachine = this.mCsm;
            if (iVolteServiceInterface.rejectCall(sessionId, callStateMachine.callType, callStateMachine.sipError) < 0) {
                this.mCsm.sendMessage(4, 0, -1, new SipError(Id.REQUEST_SIP_DIALOG_OPEN, ""));
                return;
            }
            if (this.mModule.isNotifyRejectedCall(this.mSession.getPhoneId())) {
                this.mSession.getCallProfile().setRejectCause(1613);
            }
            this.mCsm.notifyOnEnded(Id.REQUEST_SIP_DIALOG_OPEN);
            CallStateMachine callStateMachine2 = this.mCsm;
            callStateMachine2.transitionTo(callStateMachine2.mEndingCall);
        }
    }

    private void ringTimeout_IncomingCall(Message message) {
        Mno mno = this.mMno;
        if (mno == Mno.ATT || mno == Mno.NAMASTE) {
            this.mCsm.sipError = ImsCallUtil.convertSipErrorToFramework(mno, 9);
        } else {
            this.mCsm.sipError = ImsCallUtil.convertSipErrorToFramework(mno, 13);
        }
        IVolteServiceInterface iVolteServiceInterface = this.mVolteSvcIntf;
        int sessionId = this.mSession.getSessionId();
        CallStateMachine callStateMachine = this.mCsm;
        if (iVolteServiceInterface.rejectCall(sessionId, callStateMachine.callType, callStateMachine.sipError) < 0) {
            this.mCsm.sendMessage(4, 0, -1, new SipError(1006, ""));
            return;
        }
        this.mSession.setEndType(2);
        this.mSession.setEndReason(13);
        this.mCsm.notifyOnEnded(ImsCallUtil.convertCallEndReasonToFramework(2, 13));
        CallStateMachine callStateMachine2 = this.mCsm;
        callStateMachine2.transitionTo(callStateMachine2.mEndingCall);
    }

    private void established_IncomingCall(Message message) {
        IRegistrationGovernor registrationGovernor;
        CallStateMachine callStateMachine = this.mCsm;
        callStateMachine.transitionTo(callStateMachine.mInCall);
        ImsRegistration imsRegistration = this.mRegistration;
        if (imsRegistration == null || (registrationGovernor = this.mRegistrationManager.getRegistrationGovernor(imsRegistration.getHandle())) == null) {
            return;
        }
        registrationGovernor.onCallStatus(IRegistrationGovernor.CallEvent.EVENT_CALL_ESTABLISHED, null, this.mSession.getCallProfile().isDowngradedVideoCall() ? 2 : this.mSession.getCallProfile().getCallType());
    }

    private void update_IncomingCall(Message message) {
        Bundle bundle = (Bundle) message.obj;
        CallProfile parcelable = bundle.getParcelable("profile");
        int srvccVersion = this.mModule.getSrvccVersion(this.mSession.getPhoneId());
        if (parcelable == null && srvccVersion != 0 && (srvccVersion >= 10 || DeviceUtil.getGcfMode())) {
            Log.i(this.LOG_TAG, "MT aSRVCC supported");
            this.mVolteSvcIntf.sendReInvite(this.mSession.getSessionId(), new SipReason("SIP", bundle.getInt("cause"), bundle.getString("reasonText"), new String[0]));
            return;
        }
        Log.i(this.LOG_TAG, "Postpone update request till established state");
        this.mSession.mModifyRequestedProfile = parcelable;
        if (parcelable == null || !ImsCallUtil.isTtyCall(parcelable.getCallType())) {
            Log.i(this.LOG_TAG, "deferMessage only for non TTY UPDATE");
            this.mCsm.deferMessage(message);
        }
    }

    private void hold_IncomingCall(Message message) {
        Log.i(this.LOG_TAG, "received hold request maybe because of FAST_ACCEPT");
        Mno mno = this.mMno;
        if (mno == Mno.RJIL || mno.isKor()) {
            this.mCsm.deferMessage(message);
        }
    }

    private void sendText_IncomingCall(Message message) {
        CallStateMachine callStateMachine = this.mCsm;
        if (callStateMachine.mUserAnswered) {
            callStateMachine.sendRTTtext(message);
        }
    }

    private void earlymedia_IncomingCall(Message message) {
        Log.i(this.LOG_TAG, "mSession.getCallProfile().isVideoCRBT: " + this.mSession.getCallProfile().isVideoCRBT());
        if (this.mRegistration != null && this.mSession.getCallProfile().isVideoCRBT()) {
            if (!ImsCallUtil.isCmcPrimaryType(this.mSession.getCmcType())) {
                Log.i(this.LOG_TAG, "bindToNetwork for MT");
                this.mMediaController.bindToNetwork(this.mRegistration.getNetwork());
            }
            this.mVolteSvcIntf.startVideoEarlyMedia(this.mSession.getSessionId());
        }
        this.mCsm.notifyOnEarlyMediaStarted(message.arg1);
    }

    private void registerDefaultNetworkCallback() {
        ConnectivityManager connectivityManager;
        if (this.mDefaultNetworkCallback != null || (connectivityManager = (ConnectivityManager) this.mContext.getSystemService("connectivity")) == null) {
            return;
        }
        IMSLog.i(this.LOG_TAG, "registerDefaultNetworkCallback");
        ConnectivityManager.NetworkCallback defaultNetworkCallback = getDefaultNetworkCallback();
        this.mDefaultNetworkCallback = defaultNetworkCallback;
        connectivityManager.registerDefaultNetworkCallback(defaultNetworkCallback);
    }

    private void unregisterNetworkCallback() {
        ConnectivityManager connectivityManager;
        if (this.mDefaultNetworkCallback == null || (connectivityManager = (ConnectivityManager) this.mContext.getSystemService("connectivity")) == null) {
            return;
        }
        IMSLog.i(this.LOG_TAG, "unregisterNetworkCallback");
        connectivityManager.unregisterNetworkCallback(this.mDefaultNetworkCallback);
        this.mDefaultNetworkCallback = null;
    }

    private ConnectivityManager.NetworkCallback getDefaultNetworkCallback() {
        return new ConnectivityManager.NetworkCallback() { // from class: com.sec.internal.ims.servicemodules.volte2.ImsIncomingCall.1
            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onAvailable(Network network) {
                Log.i(ImsIncomingCall.this.LOG_TAG, "getDefaultNetworkCallback: onAvailable network=" + network + " for VCID");
                if (ImsIncomingCall.this.mIsADSChanged) {
                    Log.i(ImsIncomingCall.this.LOG_TAG, "VCID start after both ADSChanged and onAvailable are completed");
                    ImsIncomingCall.this.mCsm.sendMessage(800);
                }
            }

            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onLost(Network network) {
                Log.i(ImsIncomingCall.this.LOG_TAG, "getDefaultNetworkCallback: onLost network=" + network + " for VCID");
            }
        };
    }
}
