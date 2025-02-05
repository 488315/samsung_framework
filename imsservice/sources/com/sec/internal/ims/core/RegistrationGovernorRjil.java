package com.sec.internal.ims.core;

import android.content.Context;
import android.util.Log;
import com.sec.ims.settings.ImsProfile;
import com.sec.ims.util.SipError;
import com.sec.internal.constants.ims.DiagnosisConstants;
import com.sec.internal.constants.ims.ImsConstants;
import com.sec.internal.constants.ims.SipErrorBase;
import com.sec.internal.constants.ims.SipErrorSwa;
import com.sec.internal.constants.ims.core.RegistrationConstants;
import com.sec.internal.helper.CollectionUtils;
import com.sec.internal.helper.DmConfigHelper;
import com.sec.internal.helper.NetworkUtil;
import com.sec.internal.helper.RcsConfigurationHelper;
import com.sec.internal.helper.os.DeviceUtil;
import com.sec.internal.helper.os.ITelephonyManager;
import com.sec.internal.helper.os.LinkPropertiesWrapper;
import com.sec.internal.ims.settings.DeviceConfigManager;
import com.sec.internal.ims.util.ConfigUtil;
import com.sec.internal.ims.util.SemTelephonyAdapter;
import com.sec.internal.interfaces.ims.config.IConfigModule;
import com.sec.internal.interfaces.ims.servicemodules.volte2.IVolteServiceModule;
import com.sec.internal.log.IMSLog;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* loaded from: classes.dex */
public class RegistrationGovernorRjil extends RegistrationGovernorBase {
    private static final String LOG_TAG = "RegiGvnRjil";
    protected List<String> mLastPcscfList;
    protected boolean mSaEnabled;

    public RegistrationGovernorRjil(RegistrationManagerInternal registrationManagerInternal, ITelephonyManager iTelephonyManager, RegisterTask registerTask, PdnController pdnController, IVolteServiceModule iVolteServiceModule, IConfigModule iConfigModule, Context context) {
        super(registrationManagerInternal, iTelephonyManager, registerTask, pdnController, iVolteServiceModule, iConfigModule, context);
        this.mLastPcscfList = null;
        this.mSaEnabled = true;
        this.mNeedToCheckSrvcc = true;
        this.mHandlePcscfOnAlternativeCall = true;
        int i = this.mPhoneId;
        SemTelephonyAdapter.sendVolteState(i, getVoiceTechType(i) == 0);
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public void onRegistrationTerminated(SipError sipError, long j, boolean z) {
        this.mRegMan.getEventLog().logAndAdd("onRegistrationTerminated: state " + this.mTask.getState() + " error " + sipError + " retryAfterMs " + j);
        if (SipErrorBase.NOTIFY_TERMINATED_REJECTED.equals(sipError)) {
            onRegistrationError(sipError, j, z);
        }
        super.onRegistrationTerminated(sipError, j, z);
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public void onRegistrationError(SipError sipError, long j, boolean z) {
        this.mRegMan.getEventLog().logAndAdd(this.mPhoneId, "onRegistrationError: state " + this.mTask.getState() + " error " + sipError + " retryAfterMs " + j + " mCurPcscfIpIdx " + this.mCurPcscfIpIdx + " mNumOfPcscfIp " + this.mNumOfPcscfIp + " mFailureCounter " + this.mFailureCounter + " mIsPermanentStopped " + this.mIsPermanentStopped);
        if (j < 0) {
            j = 0;
        }
        this.mFailureCounter++;
        this.mCurPcscfIpIdx++;
        if (this.mTask.getProfile().hasEmergencySupport() && SipErrorBase.SIP_TIMEOUT.equals(sipError) && this.mTask.getProfile().getE911RegiTime() > 0) {
            handleTimeOutEmerRegiError();
            return;
        }
        if (SipErrorBase.isImsForbiddenError(sipError) || SipErrorBase.NOTIFY_TERMINATED_REJECTED.equals(sipError)) {
            if (!z) {
                handleForbiddenError(j);
            } else {
                IMSLog.e(LOG_TAG, this.mPhoneId, "onRegistrationError: 403 is received for Re-REG, retry according to RFC 5626.");
            }
        } else if (SipErrorBase.SIP_TIMEOUT.equals(sipError)) {
            handleTimeoutError(j);
        } else if (SipErrorSwa.AKA_CHANLENGE_TIMEOUT.equals(sipError)) {
            Log.e(LOG_TAG, "onRegistrationError: Permanently prohibited.");
            this.mTask.setDeregiReason(71);
            this.mIsPermanentStopped = true;
            resetPcscfList();
            this.mRegMan.deregister(this.mTask, true, false, "Aka challenge timeout");
            this.mRegMan.stopPdnConnectivity(this.mTask.getPdnType(), this.mTask);
            this.mTask.setState(RegistrationConstants.RegisterTaskState.IDLE);
        }
        if (this.mIsPermanentStopped && this.mTask.getProfile().getPdnType() == 11) {
            this.mRegMan.notifyImsNotAvailable(this.mTask, true, true);
        }
        handleRetryTimer(j);
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public void releaseThrottle(int i) {
        if (i == 4) {
            this.mIsPermanentStopped = false;
            this.mCurImpu = 0;
        } else if (i == 0) {
            this.mIsPermanentStopped = false;
        } else if (i == 1) {
            this.mIsPermanentStopped = false;
        } else if (i == 11) {
            this.mIsPermanentStopped = false;
        }
        if (this.mIsPermanentStopped) {
            return;
        }
        IMSLog.i(LOG_TAG, this.mPhoneId, "releaseThrottle: case by " + i);
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public void updatePcscfIpList(List<String> list) {
        if (list == null) {
            IMSLog.e(LOG_TAG, this.mPhoneId, "updatePcscfIpList: null P-CSCF list!");
        } else if (this.mRegMan.getTelephonyCallStatus(this.mPhoneId) != 0) {
            IMSLog.e(LOG_TAG, this.mPhoneId, "RJIL Specific: Delaying P-CSCF change as call is in progress");
            this.mLastPcscfList = new ArrayList(list);
        } else {
            super.updatePcscfIpList(list);
        }
    }

    @Override // com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public void onEpdgConnected() {
        if (this.mTelephonyManager.getCallState(this.mPhoneId) != 0) {
            boolean semIsVoNrEnabled = this.mTelephonyManager.semIsVoNrEnabled(this.mPhoneId);
            if (!DeviceUtil.isSupportNrMode(this.mTelephonyManager, this.mPhoneId) || semIsVoNrEnabled) {
                return;
            }
            this.mSaEnabled = false;
            this.mTelephonyManager.semSetNrMode(this.mPhoneId, 4);
            IMSLog.i(LOG_TAG, this.mPhoneId, "SA disabled after call handover");
        }
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase, com.sec.internal.ims.core.RegistrationGovernor, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public void onTelephonyCallStatusChanged(int i) {
        boolean semIsVoNrEnabled = this.mTelephonyManager.semIsVoNrEnabled(this.mPhoneId);
        boolean isSupportNrMode = DeviceUtil.isSupportNrMode(this.mTelephonyManager, this.mPhoneId);
        if (i == 0) {
            if (!this.mSaEnabled) {
                this.mSaEnabled = true;
                this.mTelephonyManager.semSetNrMode(this.mPhoneId, 3);
            }
            List<String> list = this.mLastPcscfList;
            if (list == null || list.isEmpty()) {
                return;
            }
            IMSLog.e(LOG_TAG, this.mPhoneId, "RJIL Specific: Delayed P-CSCF change when call state changed to idle");
            updatePcscfIpList(this.mLastPcscfList);
            this.mLastPcscfList = null;
            return;
        }
        IVolteServiceModule iVolteServiceModule = this.mVsm;
        if (iVolteServiceModule == null || iVolteServiceModule.getEpdgCallCount(this.mPhoneId) <= 0 || !isSupportNrMode || semIsVoNrEnabled) {
            return;
        }
        this.mSaEnabled = false;
        IMSLog.i(LOG_TAG, this.mPhoneId, "SA disabled when VoWIFI call is started");
        this.mTelephonyManager.semSetNrMode(this.mPhoneId, 4);
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase
    protected boolean checkCallStatus() {
        if (!this.mTask.getProfile().getPdn().equals(DeviceConfigManager.IMS) || this.mRegMan.getTelephonyCallStatus(this.mPhoneId) == 0 || this.mTask.isEpdgHandoverInProgress()) {
            return true;
        }
        if (isSrvccCase()) {
            IMSLog.i(LOG_TAG, this.mPhoneId, "isReadyToRegister: SRVCC case");
            return true;
        }
        IMSLog.i(LOG_TAG, this.mPhoneId, "isReadyToRegister: call state is not idle");
        return false;
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public boolean isReadyToRegister(int i) {
        return checkEmergencyStatus() || (checkRegiStatus() && checkRoamingStatus(i) && checkCallStatus() && checkNetworkEvent(i)) || checkMdmnProfile();
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public Set<String> filterService(Set<String> set, int i) {
        if (isImsDisabled()) {
            return new HashSet();
        }
        HashSet hashSet = new HashSet();
        final HashSet hashSet2 = new HashSet();
        if (set != null) {
            hashSet2.addAll(set);
            if (DmConfigHelper.getImsSwitchValue(this.mContext, "volte", this.mPhoneId) == 1) {
                IMSLog.i(LOG_TAG, this.mPhoneId, "Forcefully change voiceCall_type to PS(App checks value for making VOLTE Call)");
                ImsConstants.SystemSettings.setVoiceCallType(this.mContext, 0, this.mPhoneId);
                hashSet.addAll(servicesByImsSwitch(ImsProfile.getVoLteServiceList()));
                if (!hashSet.contains("mmtel")) {
                    this.mTask.setRegiFailReason(DiagnosisConstants.REGI_FRSN.NO_MMTEL_IMS_SWITCH_OFF.getCode());
                }
            }
            Set<String> applyImsSwitch = applyImsSwitch(hashSet, i);
            if ((DmConfigHelper.getImsSwitchValue(this.mContext, DeviceConfigManager.DEFAULTMSGAPPINUSE, this.mPhoneId) == 1) && this.mConfigModule.isValidAcsVersion(this.mPhoneId)) {
                applyImsSwitch.addAll(servicesByImsSwitch(ImsProfile.getRcsServiceList()));
                Context context = this.mContext;
                int i2 = this.mPhoneId;
                final List<String> rcsEnabledServiceList = RcsConfigurationHelper.getRcsEnabledServiceList(context, i2, ConfigUtil.getRcsProfileWithFeature(context, i2, this.mTask.getProfile()));
                Arrays.stream(ImsProfile.getRcsServiceList()).filter(new Predicate() { // from class: com.sec.internal.ims.core.RegistrationGovernorRjil$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        boolean lambda$filterService$0;
                        lambda$filterService$0 = RegistrationGovernorRjil.lambda$filterService$0(rcsEnabledServiceList, (String) obj);
                        return lambda$filterService$0;
                    }
                }).forEach(new Consumer() { // from class: com.sec.internal.ims.core.RegistrationGovernorRjil$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        RegistrationGovernorRjil.this.lambda$filterService$1(hashSet2, (String) obj);
                    }
                });
            }
            if (applyImsSwitch.isEmpty()) {
                return applyImsSwitch;
            }
            if ((i == 13 || i == 20) && this.mTask.getProfile().getPdnType() == 11) {
                applyImsSwitch = applyVoPsPolicy(applyImsSwitch);
                if (applyImsSwitch.isEmpty()) {
                    this.mTask.setRegiFailReason(DiagnosisConstants.REGI_FRSN.VOPS_OFF.getCode());
                    return applyImsSwitch;
                }
            }
            if (!hashSet2.isEmpty()) {
                hashSet2.retainAll(applyImsSwitch);
            }
            if (!hashSet2.contains("im") && !hashSet2.contains("ec")) {
                IMSLog.i(LOG_TAG, this.mPhoneId, "No RCS services, Remove options");
                hashSet2.remove("options");
            }
            return hashSet2;
        }
        IMSLog.i(LOG_TAG, this.mPhoneId, "filterServices: services null");
        return hashSet2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$filterService$0(List list, String str) {
        return !list.contains(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$filterService$1(Set set, String str) {
        removeService(set, str, "Disable from ACS.");
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase
    protected List<String> addIpv4Addr(List<String> list, List<String> list2, LinkPropertiesWrapper linkPropertiesWrapper) {
        if (linkPropertiesWrapper.hasIPv4Address()) {
            IMSLog.i(LOG_TAG, this.mPhoneId, "ipv4");
            for (String str : list) {
                if (NetworkUtil.isIPv4Address(str)) {
                    list2.add(str);
                }
            }
        }
        return list2;
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernor, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public void enableRcsOverIms(ImsProfile imsProfile) {
        Set serviceSet = this.mTask.getProfile().getServiceSet(13);
        serviceSet.addAll(imsProfile.getServiceSet(13));
        this.mTask.getProfile().setServiceSet(13, serviceSet);
        this.mTask.getProfile().setNeedAutoconfig(true);
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernor, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public void startTimsTimer(String str) {
        if (this.mTask.getPdnType() != 11) {
            Log.i(LOG_TAG, "If not IMS PDN, no need to start TimsTimer");
            return;
        }
        Log.i(LOG_TAG, "startTimsTimer : " + this.mTask.getProfile().getName() + "(" + this.mTask.getState() + ") Pdn(" + this.mTask.getPdnType() + "," + this.mPdnController.isConnected(this.mTask.getPdnType(), this.mTask) + ")");
        long j = 120000;
        if (!RegistrationConstants.REASON_IMS_PDN_REQUEST.equals(str)) {
            if (this.mTimEshtablishTimeout != null && RegistrationConstants.REASON_IMS_PDN_REQUEST.equals(this.mTimEshtablishTimeoutReason)) {
                stopTimsTimer(RegistrationConstants.REASON_IMS_PDN_REQUEST);
            }
            if (!CollectionUtils.isNullOrEmpty(this.mPcscfIpList)) {
                j = this.mPcscfIpList.size() * this.mTask.getProfile().getTimerF();
            }
        }
        startTimsEstablishTimer(this.mTask, j, str);
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernor, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public void stopTimsTimer(String str) {
        stopTimsEstablishTimer(this.mTask, str);
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public void onRegistrationDone() {
        super.onRegistrationDone();
        stopTimsTimer(RegistrationConstants.REASON_REGISTERED);
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase
    protected void handleForbiddenError(long j) {
        String regRetryPcscfPolicyOn403 = this.mTask.getProfile().getRegRetryPcscfPolicyOn403();
        if (RegistrationGovernor.RETRY_TO_NEXT_PCSCF.equals(regRetryPcscfPolicyOn403)) {
            IMSLog.e(LOG_TAG, this.mPhoneId, "onRegistrationError: Retry to next PCSCF address in case 403 Forbidden");
            return;
        }
        if (RegistrationGovernor.RETRY_TO_SAME_PCSCF.equals(regRetryPcscfPolicyOn403)) {
            IMSLog.e(LOG_TAG, this.mPhoneId, "onRegistrationError: Retry to same PCSCF address in case 403 Forbidden");
            this.mCurPcscfIpIdx--;
            return;
        }
        Log.e(LOG_TAG, "onRegistrationError: Permanently prohibited.");
        if (checkEmergencyInProgress()) {
            IMSLog.e(LOG_TAG, this.mPhoneId, "onRegistrationError: No Need to permant fail in emergency registering");
        } else {
            IMSLog.e(LOG_TAG, this.mPhoneId, "onRegistrationError: Permanently prohibited.");
            this.mIsPermanentStopped = true;
        }
        if (this.mTask.getState() == RegistrationConstants.RegisterTaskState.CONNECTED) {
            this.mRegMan.stopPdnConnectivity(this.mTask.getPdnType(), this.mTask);
            resetPcscfList();
        }
    }
}
