package com.sec.internal.ims.core;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.SystemClock;
import android.text.TextUtils;
import com.sec.ims.settings.ImsProfile;
import com.sec.ims.util.SipError;
import com.sec.internal.constants.ims.DiagnosisConstants;
import com.sec.internal.constants.ims.SipErrorBase;
import com.sec.internal.constants.ims.core.PdnFailReason;
import com.sec.internal.constants.ims.core.RegistrationConstants;
import com.sec.internal.constants.ims.core.SimConstants;
import com.sec.internal.constants.ims.os.VoPsIndication;
import com.sec.internal.constants.ims.settings.GlobalSettingsConstants;
import com.sec.internal.helper.CollectionUtils;
import com.sec.internal.helper.DmConfigHelper;
import com.sec.internal.helper.RcsConfigurationHelper;
import com.sec.internal.helper.SimUtil;
import com.sec.internal.helper.SimpleEventLog;
import com.sec.internal.helper.os.ITelephonyManager;
import com.sec.internal.helper.os.LinkPropertiesWrapper;
import com.sec.internal.ims.settings.DeviceConfigManager;
import com.sec.internal.interfaces.ims.config.IConfigModule;
import com.sec.internal.interfaces.ims.servicemodules.volte2.IVolteServiceModule;
import com.sec.internal.log.IMSLog;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/* loaded from: classes.dex */
public class RegistrationGovernorCmcc extends RegistrationGovernorBase {
    private static final long DELAYED_DEREGISTER_TIMER_MS = 15000;
    private static final String IDC_OEM_PACKAGE = "com.newcall";
    private static final String LOG_TAG = "RegiGvnCmcc";
    protected boolean mAllPcscfOver;
    protected Set<String> mInvalidPcscfIp;
    private boolean mIsIdcSupported;
    protected List<String> mRcsPcscfList;

    public RegistrationGovernorCmcc(RegistrationManagerInternal registrationManagerInternal, ITelephonyManager iTelephonyManager, RegisterTask registerTask, PdnController pdnController, IVolteServiceModule iVolteServiceModule, IConfigModule iConfigModule, Context context) {
        super(registrationManagerInternal, iTelephonyManager, registerTask, pdnController, iVolteServiceModule, iConfigModule, context);
        this.mAllPcscfOver = false;
        this.mInvalidPcscfIp = new TreeSet();
        setUpsmEventReceiver();
        updateEutranValues();
        this.mHandlePcscfOnAlternativeCall = true;
        this.mRcsPcscfList = new ArrayList();
        this.mIsIdcSupported = isIdcSupported();
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public void releaseThrottle(int i) {
        if (i == 1) {
            if (isDelayedDeregisterTimerRunning()) {
                IMSLog.i(LOG_TAG, this.mPhoneId, "releaseThrottle: delete DelayedDeregisterTimer on fligt mode");
                setDelayedDeregisterTimerRunning(false);
            }
            IMSLog.i(LOG_TAG, this.mPhoneId, "releaseThrottle: RELEASE_AIRPLANEMODE_ON");
            this.mIsPermanentStopped = false;
            this.mAllPcscfOver = false;
            this.mRegiAt = 0L;
            stopRetryTimer();
        } else if (i == 4 || i == 6) {
            this.mIsPermanentStopped = false;
            this.mAllPcscfOver = false;
            this.mRegiAt = 0L;
            stopRetryTimer();
        } else if (i == 0) {
            this.mIsPermanentStopped = false;
        }
        if (this.mIsPermanentStopped) {
            return;
        }
        IMSLog.i(LOG_TAG, this.mPhoneId, "releaseThrottle: case by " + i);
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public void onRegistrationTerminated(SipError sipError, long j, boolean z) {
        this.mRegMan.getEventLog().logAndAdd("onRegistrationTerminated: state " + this.mTask.getState() + " error " + sipError + " retryAfterMs " + j);
        if (SipErrorBase.NOTIFY_TERMINATED_REJECTED.equals(sipError)) {
            onRegistrationError(sipError, j, z);
            return;
        }
        this.mFailureCounter = 0;
        this.mCurPcscfIpIdx = 0;
        startRetryTimer(1000L);
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernor, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public void checkAcsPcscfListChange() {
        if (this.mTask.isRcsOnly()) {
            ArrayList arrayList = new ArrayList();
            String readStringParam = RcsConfigurationHelper.readStringParam(this.mContext, "address", null);
            if (readStringParam == null) {
                IMSLog.i(LOG_TAG, "checkAcsPcscfIpListChange : lboPcscfAddress is null");
                return;
            }
            arrayList.add(readStringParam);
            IMSLog.i(LOG_TAG, "checkAcsPcscfIpListChange : previous pcscf = " + this.mRcsPcscfList + ", new pcscf = " + arrayList);
            if (arrayList.equals(this.mRcsPcscfList)) {
                return;
            }
            if (this.mTask.isOneOf(RegistrationConstants.RegisterTaskState.REGISTERING, RegistrationConstants.RegisterTaskState.REGISTERED)) {
                this.mTask.setDeregiReason(8);
                this.mRegMan.deregister(this.mTask, true, false, "pcscf updated");
            }
            resetPcscfList();
            ArrayList arrayList2 = new ArrayList();
            this.mRcsPcscfList = arrayList2;
            arrayList2.add(readStringParam);
            IMSLog.i(LOG_TAG, "checkAcsPcscfIpListChange : resetPcscfList");
        }
    }

    protected boolean isInvalidPcscfIp(String str) {
        if (!TextUtils.isEmpty(str) && CollectionUtils.isNullOrEmpty(this.mInvalidPcscfIp)) {
            return false;
        }
        Iterator<String> it = this.mInvalidPcscfIp.iterator();
        while (it.hasNext()) {
            if (str.equalsIgnoreCase(it.next())) {
                return true;
            }
        }
        return false;
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public List<String> checkValidPcscfIp(List<String> list) {
        IMSLog.i(LOG_TAG, this.mPhoneId, "checkValidPcscfIp");
        if (!this.mTask.isRcsOnly()) {
            return super.checkValidPcscfIp(list);
        }
        List<String> arrayList = new ArrayList<>();
        LinkPropertiesWrapper linkProperties = this.mPdnController.getLinkProperties(this.mTask);
        if (list != null && !list.isEmpty() && linkProperties != null) {
            arrayList = addIpv6Addr(list, arrayList, linkProperties);
            if (CollectionUtils.isNullOrEmpty(arrayList)) {
                IMSLog.i(LOG_TAG, this.mPhoneId, "validPcscfIp ipv6 is null");
                arrayList = addIpv4Addr(list, arrayList, linkProperties);
            } else if (isInvalidPcscfIp(arrayList.get(0))) {
                IMSLog.i(LOG_TAG, this.mPhoneId, "validPcscfIp ipv6 is not valid");
                String str = arrayList.get(0);
                arrayList.clear();
                arrayList = addIpv4Addr(list, arrayList, linkProperties);
                if (CollectionUtils.isNullOrEmpty(arrayList) || isInvalidPcscfIp(arrayList.get(0))) {
                    arrayList.clear();
                    arrayList.add(str);
                    IMSLog.i(LOG_TAG, this.mPhoneId, "validPcscfIp ipv6 and ipv4 all is not valid, so select ipv6");
                } else {
                    IMSLog.i(LOG_TAG, this.mPhoneId, "validPcscfIp ipv4 is valid");
                }
            } else {
                IMSLog.i(LOG_TAG, this.mPhoneId, "validPcscfIp ipv6 is valid");
            }
            IMSLog.i(LOG_TAG, this.mPhoneId, "ValidPcscfIp: " + arrayList);
        }
        return arrayList;
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public void onRegistrationError(SipError sipError, long j, boolean z) {
        IMSLog.e(LOG_TAG, this.mPhoneId, "onRegistrationError: state " + this.mTask.getState() + " error " + sipError + " retryAfterMs " + j + " mCurPcscfIpIdx " + this.mCurPcscfIpIdx + " mNumOfPcscfIp " + this.mNumOfPcscfIp + " mFailureCounter " + this.mFailureCounter + " mIsPermanentStopped " + this.mIsPermanentStopped);
        SimpleEventLog eventLog = this.mRegMan.getEventLog();
        StringBuilder sb = new StringBuilder();
        sb.append("onRegistrationError : ");
        sb.append(sipError);
        sb.append(", fail count : ");
        sb.append(this.mFailureCounter);
        eventLog.logAndAdd(sb.toString());
        if (this.mTask.isRcsOnly()) {
            String currentPcscfIp = getCurrentPcscfIp();
            IMSLog.i(LOG_TAG, this.mPhoneId, "onRegistrationError: " + currentPcscfIp);
            if (!TextUtils.isEmpty(currentPcscfIp)) {
                this.mInvalidPcscfIp.add(currentPcscfIp);
            }
            super.onRegistrationError(sipError, j, z);
            return;
        }
        if (j < 0) {
            j = 0;
        }
        this.mFailureCounter++;
        this.mCurPcscfIpIdx++;
        if (SipErrorBase.isImsForbiddenError(sipError) || SipErrorBase.NOTIFY_TERMINATED_REJECTED.equals(sipError)) {
            handleForbiddenError(j);
            return;
        }
        if (SipErrorBase.MISSING_P_ASSOCIATED_URI.equals(sipError)) {
            this.mCurPcscfIpIdx--;
            this.mTask.mKeepPdn = true;
        } else if (SipErrorBase.SIP_TIMEOUT.equals(sipError)) {
            if (!z && this.mCurPcscfIpIdx == this.mNumOfPcscfIp) {
                if (this.mTask.getState() == RegistrationConstants.RegisterTaskState.CONNECTED && this.mRegMan.getTelephonyCallStatus(this.mPhoneId) == 0 && (SimUtil.getPhoneCount() != 2 || this.mTask.getRegistrationRat() == 18 || this.mRegMan.getTelephonyCallStatus(SimUtil.getOppositeSimSlot(this.mPhoneId)) == 0)) {
                    if (this.mRegMan.getCurrentNetworkByPhoneId(this.mPhoneId) == 13) {
                        this.mAllPcscfOver = true;
                    }
                    this.mRegMan.stopPdnConnectivity(this.mTask.getPdnType(), this.mTask);
                    resetPcscfList();
                    this.mTask.setState(RegistrationConstants.RegisterTaskState.IDLE);
                }
            } else {
                IMSLog.i(LOG_TAG, this.mPhoneId, "onRegistrationError: SIP_TIMEOUT error. Retry regi immediately");
                if (this.mCurPcscfIpIdx >= this.mNumOfPcscfIp) {
                    this.mCurPcscfIpIdx = 0;
                }
                this.mRegiAt = 0L;
                this.mRegHandler.sendTryRegister(this.mPhoneId);
                return;
            }
        }
        handleRetryTimer(j);
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public int getFailureType() {
        if (this.mAllPcscfOver) {
            return 33;
        }
        return super.getFailureType();
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public Set<String> filterService(Set<String> set, int i) {
        HashSet hashSet = new HashSet();
        Set<String> hashSet2 = new HashSet<>(set);
        if (isImsDisabled()) {
            return new HashSet();
        }
        if ((i == 13 || i == 20) && this.mTask.getProfile().getPdn().equals(DeviceConfigManager.IMS) && this.mRegMan.getNetworkEvent(this.mPhoneId).voiceOverPs == VoPsIndication.NOT_SUPPORTED) {
            IMSLog.i(LOG_TAG, this.mPhoneId, "filterService: IMSVoPS is not supported");
            this.mTask.setRegiFailReason(DiagnosisConstants.REGI_FRSN.VOPS_OFF.getCode());
            return new HashSet();
        }
        if (this.mTask.getProfile().getPdn().equals(DeviceConfigManager.IMS)) {
            hashSet2 = applyMmtelUserSettings(hashSet2, i);
        }
        if (DmConfigHelper.getImsSwitchValue(this.mContext, "volte", this.mPhoneId) == 1) {
            hashSet.addAll(servicesByImsSwitch(ImsProfile.getVoLteServiceList()));
            if (!hashSet.contains("mmtel")) {
                this.mTask.setRegiFailReason(DiagnosisConstants.REGI_FRSN.NO_MMTEL_IMS_SWITCH_OFF.getCode());
            }
            if (!this.mIsIdcSupported && hashSet.contains("datachannel")) {
                IMSLog.i(LOG_TAG, this.mPhoneId, "filterService: IDC is not supported");
                hashSet.remove("datachannel");
            }
        }
        if (this.mConfigModule.isValidAcsVersion(this.mPhoneId)) {
            hashSet.addAll(servicesByImsSwitch(ImsProfile.getRcsServiceList()));
        }
        hashSet2.retainAll(hashSet);
        return hashSet2;
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase
    protected boolean checkCallStatus() {
        if (!this.mTask.getProfile().getPdn().equals(DeviceConfigManager.IMS)) {
            return true;
        }
        if (this.mRegMan.getTelephonyCallStatus(this.mPhoneId) != 0) {
            IMSLog.i(LOG_TAG, this.mPhoneId, "isReadyToRegister: call state is not idle");
            return false;
        }
        if (SimConstants.DSDS_DI.equals(SimUtil.getConfigDualIMS()) && this.mRegMan.getTelephonyCallStatus(SimUtil.getOppositeSimSlot(this.mPhoneId)) != 0) {
            IMSLog.i(LOG_TAG, this.mPhoneId, "isReadyToRegister: another slot's call state is not idle");
            return false;
        }
        if (!isDelayedDeregisterTimerRunning()) {
            return true;
        }
        IMSLog.i(LOG_TAG, this.mPhoneId, "isReadyToRegister: DelayedDeregisterTimer Running.");
        if (isDeregisterWithVoPSNeeded() || isDeregisterWithRATNeeded() || this.mRegMan.getNetworkEvent(this.mPhoneId).outOfService) {
            return false;
        }
        IMSLog.i(LOG_TAG, this.mPhoneId, "isReadyToRegister: LTE or NR attached. Delete DelayedDeregisterTimer.");
        this.mRegMan.onDelayedDeregister(this.mTask);
        return true;
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernor
    protected boolean checkRcsEvent(int i) {
        if (!this.mTask.getProfile().getPdn().equals(DeviceConfigManager.IMS) && this.mTask.isRcsOnly()) {
            if (DmConfigHelper.getImsSwitchValue(this.mContext, DeviceConfigManager.DEFAULTMSGAPPINUSE, this.mPhoneId) != 1) {
                IMSLog.i(LOG_TAG, this.mPhoneId, "isReadyToRegister: Default MSG app isn't used for RCS");
                return false;
            }
            if (this.mTask.getState() != RegistrationConstants.RegisterTaskState.REGISTERED && this.mPdnController.isWifiConnected() && i != 18) {
                IMSLog.i(LOG_TAG, this.mPhoneId, "isReadyToRegister: The RCS rat is not wifi, when wifi is connected.");
                return false;
            }
        }
        return true;
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase, com.sec.internal.ims.core.RegistrationGovernor
    protected boolean checkVolteSetting(int i) {
        if (getVoiceTechType() == 0 || !this.mTask.getProfile().getPdn().equals(DeviceConfigManager.IMS)) {
            return true;
        }
        IMSLog.i(LOG_TAG, this.mPhoneId, "isReadyToRegister: volte disabled");
        this.mTask.setRegiFailReason(DiagnosisConstants.REGI_FRSN.USER_SETTINGS_OFF.getCode());
        if (this.mRegMan.getCurrentNetworkByPhoneId(this.mPhoneId) != 20) {
            return false;
        }
        this.mRegMan.notifyImsNotAvailable(this.mTask, true);
        return false;
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public boolean isReadyToRegister(int i) {
        return checkEmergencyStatus() || (checkRegiStatus() && checkVolteSetting(i) && checkCallStatus() && checkRcsEvent(i));
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernor, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public void onDeregistrationDone(boolean z) {
        if (z && !this.mTask.mKeepPdn && getVoiceTechType() == 1 && this.mTask.getPdnType() == 11) {
            this.mRegHandler.notifyVolteSettingOff(this.mTask, 1000L);
        }
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernor, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public void notifyVoLteOnOffToRil(boolean z) {
        IMSLog.i(LOG_TAG, this.mPhoneId, "notifyVoLteOnOffToRil: " + z);
        ContentValues contentValues = new ContentValues();
        if (!z) {
            contentValues.put(GlobalSettingsConstants.Registration.VOICE_DOMAIN_PREF_EUTRAN, (Integer) 1);
        } else {
            contentValues.put(GlobalSettingsConstants.Registration.VOICE_DOMAIN_PREF_EUTRAN, (Integer) 3);
        }
        this.mContext.getContentResolver().update(Uri.parse("content://com.sec.ims.settings/global").buildUpon().fragment("simslot" + this.mPhoneId).build(), contentValues, null, null);
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernor, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public void onVolteSettingChanged() {
        updateEutranValues();
    }

    private void updateEutranValues() {
        if (this.mTask.getProfile().hasService("mmtel")) {
            int voiceTechType = getVoiceTechType();
            IMSLog.i(LOG_TAG, this.mPhoneId, "updateEutranValues : voiceTech : " + voiceTechType);
            if (voiceTechType == 0) {
                this.mRegHandler.removeVolteSettingOffEvent();
                notifyVoLteOnOffToRil(true);
            } else {
                if (this.mTask.getState() == RegistrationConstants.RegisterTaskState.REGISTERED || this.mTask.getState() == RegistrationConstants.RegisterTaskState.REGISTERING) {
                    return;
                }
                notifyVoLteOnOffToRil(false);
            }
        }
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase, com.sec.internal.ims.core.RegistrationGovernor, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public void onTelephonyCallStatusChanged(int i) {
        setCallStatus(i);
        if (getCallStatus() == 0) {
            boolean z = this.mTask.getState() == RegistrationConstants.RegisterTaskState.REGISTERED || this.mTask.getState() == RegistrationConstants.RegisterTaskState.CONNECTED;
            if (this.mTask.getProfile().getPdn().equals(DeviceConfigManager.IMS) && z && (isDeregisterWithVoPSNeeded() || isDeregisterWithRATNeeded() || this.mRegMan.getNetworkEvent(this.mPhoneId).outOfService)) {
                IMSLog.i(LOG_TAG, this.mPhoneId, "onTelephonyCallStatusChanged: delayedDeregisterTimer 15000 milliseconds start");
                setDelayedDeregisterTimerRunning(true);
                this.mRegMan.sendDeregister(this.mTask, DELAYED_DEREGISTER_TIMER_MS);
            }
        }
        if (getCallStatus() == 2 && this.mTask.getState() == RegistrationConstants.RegisterTaskState.REGISTERING && this.mTask.getProfile().getPdn().equals(DeviceConfigManager.IMS)) {
            IMSLog.i(LOG_TAG, this.mPhoneId, "onTelephonyCallStatusChanged: deregister due to cs call");
            this.mTask.setDeregiReason(7);
            this.mRegMan.deregister(this.mTask, true, true, "call state changed");
        }
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public void onPdnRequestFailed(PdnFailReason pdnFailReason, int i) {
        super.onPdnRequestFailed(pdnFailReason, i);
        if (isMatchedPdnFailReason(pdnFailReason)) {
            return;
        }
        onPdnFailCounterInNr();
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernor, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public boolean isDelayedDeregisterTimerRunning() {
        return isDelayedDeregisterTimerRunningWithCallStatus();
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase, com.sec.internal.ims.core.RegistrationGovernor, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public void runDelayedDeregister() {
        if (isDelayedDeregisterTimerRunning()) {
            IMSLog.i(LOG_TAG, this.mPhoneId, "runDelayedDeregister : delete DelayedDeregisterTimer. mState [" + this.mTask.getState() + "]");
            setDelayedDeregisterTimerRunning(false);
            if (this.mTask.getState() == RegistrationConstants.RegisterTaskState.REGISTERED) {
                if (this.mRegMan.getCurrentNetworkByPhoneId(this.mPhoneId) == 20) {
                    this.mRegMan.deregister(this.mTask, false, false, "CS call end, SA deregistration");
                    return;
                } else {
                    this.mRegMan.addPendingUpdateRegistration(this.mTask, 0);
                    return;
                }
            }
            if (this.mTask.getState() == RegistrationConstants.RegisterTaskState.CONNECTED) {
                if (isDeregisterWithVoPSNeeded() || isDeregisterWithRATNeeded()) {
                    this.mRegMan.stopPdnConnectivity(this.mTask.getPdnType(), this.mTask);
                    return;
                } else {
                    this.mRegHandler.sendTryRegister(this.mTask.getPhoneId());
                    return;
                }
            }
            this.mRegHandler.sendTryRegister(this.mTask.getPhoneId());
        }
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernor, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public boolean isThrottled() {
        return this.mIsPermanentStopped || this.mAllPcscfOver || this.mRegiAt > SystemClock.elapsedRealtime();
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernor, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public void resetAllPcscfChecked() {
        this.mAllPcscfOver = false;
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase
    protected int getVoiceTechType() {
        forceTurnOnVoLteWhenMenuRemoved();
        return super.getVoiceTechType();
    }

    private boolean isIdcSupported() {
        try {
            this.mContext.getPackageManager().getApplicationInfo(IDC_OEM_PACKAGE, 128);
            IMSLog.i(LOG_TAG, this.mPhoneId, "isIdcSupported: true");
            return true;
        } catch (PackageManager.NameNotFoundException unused) {
            IMSLog.i(LOG_TAG, this.mPhoneId, "isIdcSupported: false");
            return false;
        }
    }
}
