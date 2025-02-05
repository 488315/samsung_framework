package com.sec.internal.ims.core;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import com.sec.ims.settings.ImsProfile;
import com.sec.ims.util.SipError;
import com.sec.internal.constants.Mno;
import com.sec.internal.constants.ims.DiagnosisConstants;
import com.sec.internal.constants.ims.ImsConstants;
import com.sec.internal.constants.ims.SipErrorBase;
import com.sec.internal.constants.ims.config.ConfigConstants;
import com.sec.internal.constants.ims.core.PdnFailReason;
import com.sec.internal.constants.ims.core.RegistrationConstants;
import com.sec.internal.constants.ims.gls.LocationInfo;
import com.sec.internal.constants.ims.os.VoPsIndication;
import com.sec.internal.constants.ims.settings.GlobalSettingsConstants;
import com.sec.internal.helper.CollectionUtils;
import com.sec.internal.helper.DmConfigHelper;
import com.sec.internal.helper.NetworkUtil;
import com.sec.internal.helper.RcsConfigurationHelper;
import com.sec.internal.helper.os.ITelephonyManager;
import com.sec.internal.helper.os.LinkPropertiesWrapper;
import com.sec.internal.ims.core.sim.SimManagerFactory;
import com.sec.internal.ims.registry.ImsRegistry;
import com.sec.internal.ims.settings.DeviceConfigManager;
import com.sec.internal.ims.util.ConfigUtil;
import com.sec.internal.ims.util.ImsUtil;
import com.sec.internal.ims.util.SemTelephonyAdapter;
import com.sec.internal.interfaces.ims.aec.IAECModule;
import com.sec.internal.interfaces.ims.config.IConfigModule;
import com.sec.internal.interfaces.ims.core.IRegistrationGovernor;
import com.sec.internal.interfaces.ims.core.IRegistrationManager;
import com.sec.internal.interfaces.ims.core.ISimManager;
import com.sec.internal.interfaces.ims.servicemodules.volte2.IVolteServiceModule;
import com.sec.internal.log.IMSLog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* loaded from: classes.dex */
public class RegistrationGovernorEur extends RegistrationGovernorBase {
    private static final long DELAYED_DEREGISTER_TIMER_MS = 10000;
    private static final String LOG_TAG = "RegiGvnEur";
    protected static final int REGI_RETRY_LIMIT = 4;
    private boolean checkEndPcscfList;
    protected IAECModule mAECModule;
    private DailyReRegisterIntentReceiver mDailyReRegisterIntentReceiver;
    protected boolean mHasPendingReRegistration;
    private boolean mIsRetryAbandon;
    protected List<String> mLastPcscfList;
    boolean mNeedDirectRetry;
    protected Map<String, Long> mPcscfRetryTimeMap;
    protected int mRegiRetryCount;
    ScheduledExecutorService mVolteOffExecutor;

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public boolean isLocationInfoLoaded(int i) {
        return true;
    }

    public RegistrationGovernorEur(RegistrationManagerInternal registrationManagerInternal, ITelephonyManager iTelephonyManager, RegisterTask registerTask, PdnController pdnController, IVolteServiceModule iVolteServiceModule, IConfigModule iConfigModule, Context context) {
        super(registrationManagerInternal, iTelephonyManager, registerTask, pdnController, iVolteServiceModule, iConfigModule, context);
        this.mLastPcscfList = null;
        this.mRegiRetryCount = 0;
        this.mIsRetryAbandon = false;
        this.checkEndPcscfList = false;
        this.mPcscfRetryTimeMap = new HashMap();
        this.mNeedDirectRetry = false;
        this.mHasPendingReRegistration = false;
        this.mVolteOffExecutor = Executors.newSingleThreadScheduledExecutor();
        this.mNeedToCheckSrvcc = true;
        this.mNeedToCheckLocationSetting = false;
        if (this.mMno.isOneOf(Mno.ORANGE_POLAND, Mno.TELIA_NORWAY, Mno.TELIA_SWE, Mno.ORANGE)) {
            updateEutranValues();
        }
        this.mAECModule = ImsRegistry.getAECModule();
        Mno mno = this.mMno;
        if (mno != Mno.EDF) {
            this.mHandlePcscfOnAlternativeCall = true;
        }
        if (mno == Mno.TMOBILE && this.mTask.getProfile().getPdnType() == 11) {
            IMSLog.i(LOG_TAG, "Registering receiver for daily re-registration");
            this.mDailyReRegisterIntentReceiver = new DailyReRegisterIntentReceiver();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(ImsConstants.Intents.ACTION_CHECK_REGISTRATION_DAILY);
            this.mContext.registerReceiver(this.mDailyReRegisterIntentReceiver, intentFilter);
        }
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public void onRegistrationError(SipError sipError, long j, boolean z) {
        this.mRegMan.getEventLog().logAndAdd(this.mPhoneId, "onRegistrationError: state " + this.mTask.getState() + " error " + sipError + " retryAfterMs " + j + " mCurPcscfIpIdx " + this.mCurPcscfIpIdx + " mNumOfPcscfIp " + this.mNumOfPcscfIp + " mFailureCounter " + this.mFailureCounter + " mIsPermanentStopped " + this.mIsPermanentStopped);
        this.mNeedDirectRetry = false;
        this.mIsRetryAbandon = false;
        if (j < 0) {
            j = 0;
        }
        this.mFailureCounter++;
        this.mCurPcscfIpIdx++;
        if (this.mTask.getProfile().hasEmergencySupport() && SipErrorBase.SIP_TIMEOUT.equals(sipError) && this.mTask.getProfile().getE911RegiTime() > 0) {
            handleTimeOutEmerRegiError();
            return;
        }
        if (SipErrorBase.isImsForbiddenError(sipError)) {
            handleForbiddenError(j);
        } else if (SipErrorBase.SIP_TIMEOUT.equals(sipError)) {
            handleTimeoutError(j);
        } else if (SipErrorBase.SERVICE_UNAVAILABLE.equals(sipError)) {
            handleServiceUnavailable(j);
        } else if (SipErrorBase.SERVER_INTERNAL_ERROR.equals(sipError) && this.mMno.isOrangeGPG() && this.mTask.getProfile().getPdnType() == 11 && isLastPcscfAddr() && j == 0 && this.mTask.getState() == RegistrationConstants.RegisterTaskState.CONNECTED) {
            IMSLog.i(LOG_TAG, this.mPhoneId, "onRegistrationError: ORANGE LastPCSF want to stop ims pdn");
            this.mRegMan.stopPdnConnectivity(this.mTask.getPdnType(), this.mTask);
            resetPcscfList();
        }
        if (this.mIsPermanentStopped || this.mIsRetryAbandon) {
            return;
        }
        if ((this.mMno.isTmobile() || this.mMno == Mno.TELEKOM_ALBANIA) && this.mTask.getProfile().getPdnType() == 11) {
            adjustTmobileErrorTreatment(sipError, j);
        }
        handleRetryTimer(j);
    }

    public String getLastPcscfIp() {
        if (CollectionUtils.isNullOrEmpty(this.mPcscfIpList)) {
            Log.e(LOG_TAG, "getPcscf: empty P-CSCF list.");
            return "";
        }
        int i = this.mCurPcscfIpIdx;
        if (i < 0) {
            return "";
        }
        int i2 = i - 1;
        if (i == 0) {
            i2 = this.mNumOfPcscfIp;
        }
        List<String> list = this.mPcscfIpList;
        String str = list.get(i2 % list.size());
        return str == null ? "" : str;
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernor, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public void resetPcscfList() {
        this.mIsValid = false;
        this.checkEndPcscfList = false;
        this.mPcscfRetryTimeMap.clear();
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernor, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public void onSubscribeError(int i, SipError sipError) {
        IMSLog.i(LOG_TAG, this.mPhoneId, "onSubscribeError: state " + this.mTask.getState() + ", error " + sipError + ", event " + i);
        Mno mno = this.mMno;
        if ((mno == Mno.TELENOR_DK || mno == Mno.BEELINE_RUSSIA) && i == 0) {
            if (sipError.getCode() == 403) {
                this.mSubscribeForbiddenCounter++;
                this.mTask.setDeregiReason(44);
                this.mRegMan.deregister(this.mTask, true, true, "Subscribe Error. Deregister..");
                this.mFailureCounter = this.mSubscribeForbiddenCounter;
                IMSLog.i(LOG_TAG, this.mPhoneId, " onSubscribeError: state " + this.mTask.getState() + " error " + sipError + " mFailureCounter: " + this.mFailureCounter);
                if (isLastPcscfAddr()) {
                    this.mCurPcscfIpIdx = 0;
                }
                long waitTime = getWaitTime();
                this.mThrottleReason = 0;
                startRetryTimer(waitTime);
                return;
            }
            this.mSubscribeForbiddenCounter = 0;
        }
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernor, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public boolean isThrottled() {
        if (this.mIsPermanentStopped || this.mRegiAt > SystemClock.elapsedRealtime()) {
            return true;
        }
        if (this.mIsPermanentPdnFailed && this.mTask.getProfile().getPdnType() == 11) {
            return (this.mMno.isOneOf(Mno.SWISSCOM, Mno.SFR, Mno.VODAFONE) && this.mRegMan.getCurrentNetworkByPhoneId(this.mPhoneId) == 18) ? false : true;
        }
        return false;
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public void releaseThrottle(int i) {
        if (i == 6) {
            this.mIsPermanentStopped = false;
            this.mIsPermanentPdnFailed = false;
            this.mNonVoLTESimByPdnFail = false;
        } else if (i == 1) {
            if (isDelayedDeregisterTimerRunning()) {
                IMSLog.i(LOG_TAG, this.mPhoneId, "releaseThrottle: delete DelayedDeregisterTimer on fligt mode");
                setDelayedDeregisterTimerRunning(false);
            } else if (this.mTask.isRcsOnly()) {
                IMSLog.i(LOG_TAG, this.mPhoneId, "onReceive: FLIGHT_MODE is changed");
                this.mTask.setDeregiReason(23);
                this.mRegMan.deregister(this.mTask, false, false, "flight mode enabled");
            }
            this.mIsPermanentStopped = false;
            this.mIsPermanentPdnFailed = false;
            this.mNonVoLTESimByPdnFail = false;
        } else if (i == 4) {
            this.mIsPermanentStopped = false;
            this.mIsPermanentPdnFailed = false;
            this.mCurImpu = 0;
            this.mNonVoLTESimByPdnFail = false;
        } else if (i == 11) {
            this.mIsPermanentStopped = false;
            this.mIsPermanentPdnFailed = false;
            this.mNonVoLTESimByPdnFail = false;
        }
        if (this.mIsPermanentStopped && this.mIsPermanentPdnFailed) {
            return;
        }
        IMSLog.i(LOG_TAG, this.mPhoneId, "releaseThrottle: case by " + i);
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public void onPdnRequestFailed(PdnFailReason pdnFailReason, int i) {
        boolean z;
        super.onPdnRequestFailed(pdnFailReason, i);
        if (!NetworkUtil.is3gppPsVoiceNetwork(this.mRegMan.getCurrentNetworkByPhoneId(this.mPhoneId))) {
            if (this.mMno.isOneOf(Mno.BOG, Mno.TELECOM_ITALY)) {
                IMSLog.i(LOG_TAG, this.mPhoneId, "onPdnRequestFailed apply in non LTE/NR");
            } else {
                IMSLog.i(LOG_TAG, this.mPhoneId, "onPdnRequestFailed ignore in non LTE/NR");
                return;
            }
        }
        String matchedPdnFailReasonFromGlobalSettings = getMatchedPdnFailReasonFromGlobalSettings(pdnFailReason);
        if (TextUtils.isEmpty(matchedPdnFailReasonFromGlobalSettings)) {
            z = false;
        } else {
            setRetryTimeOnPdnFail(matchedPdnFailReasonFromGlobalSettings.contains(":") ? Long.parseLong(matchedPdnFailReasonFromGlobalSettings.substring(matchedPdnFailReasonFromGlobalSettings.indexOf(":") + 1)) : -1L);
            z = true;
        }
        if (z) {
            this.mRegMan.notifyImsNotAvailable(this.mTask, true, true);
            this.mRegMan.stopPdnConnectivity(this.mTask.getPdnType(), this.mTask);
            this.mTask.setState(RegistrationConstants.RegisterTaskState.IDLE);
            this.mIsPermanentPdnFailed = true;
            this.mNonVoLTESimByPdnFail = true;
            if (!ImsRegistry.getWfcEpdgManager().isEpdgServiceConnected()) {
                ImsRegistry.getWfcEpdgManager().onPermanentPdnFail();
            }
            this.mRegHandler.notifyPdnDisconnected(this.mTask);
            Mno mno = this.mMno;
            if (mno == Mno.TELIA_NORWAY || mno == Mno.TELIA_SWE) {
                updateEutranValues();
                return;
            }
            return;
        }
        onPdnFailCounterInNr();
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase
    protected List<String> addIpv4Addr(List<String> list, List<String> list2, LinkPropertiesWrapper linkPropertiesWrapper) {
        boolean z;
        ImsProfile profile = this.mTask.getProfile();
        if (this.mTask.isRcsOnly() && profile.getNeedIpv4Dns()) {
            Iterator<String> it = list.iterator();
            while (true) {
                if (!it.hasNext()) {
                    z = false;
                    break;
                }
                if (NetworkUtil.isIPv4Address(it.next())) {
                    z = true;
                    break;
                }
            }
            if (z) {
                Log.i(LOG_TAG, "ipv4 address found. RCS service prefers ipv4.");
                list2.clear();
                if (list2.isEmpty()) {
                    for (String str : list) {
                        if (NetworkUtil.isIPv4Address(str)) {
                            list2.add(str);
                        }
                    }
                }
            } else {
                Log.i(LOG_TAG, "Ipv4 pcscf addr isn't exist - for RCS : ");
            }
        } else if (linkPropertiesWrapper.hasIPv4Address()) {
            IMSLog.i(LOG_TAG, this.mPhoneId, "ipv4");
            for (String str2 : list) {
                if (NetworkUtil.isIPv4Address(str2)) {
                    list2.add(str2);
                }
            }
        }
        return list2;
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public void updatePcscfIpList(List<String> list) {
        if (list == null) {
            Log.e("RegiGvnEur[" + this.mPhoneId + "]", "updatePcscfIpList: null P-CSCF list!");
            return;
        }
        if (this.mTask.getProfile().getDelayPcscfChangeDuringCall() && this.mCallStatus != 0) {
            this.mLastPcscfList = new ArrayList(list);
        } else {
            super.updatePcscfIpList(list);
        }
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase, com.sec.internal.ims.core.RegistrationGovernor, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public void onTelephonyCallStatusChanged(int i) {
        List<String> list;
        this.mCallStatus = i;
        if (this.mTask.getProfile().getDelayPcscfChangeDuringCall() && this.mCallStatus == 0 && (list = this.mLastPcscfList) != null && !list.isEmpty()) {
            updatePcscfIpList(this.mLastPcscfList);
            this.mLastPcscfList = null;
        }
        if (this.mTask.getProfile().getBlockDeregiOnSrvcc() && this.mCallStatus == 0 && this.mTask.isOneOf(RegistrationConstants.RegisterTaskState.REGISTERED, RegistrationConstants.RegisterTaskState.CONNECTED)) {
            if (isDeregisterWithVoPSNeeded() || isDeregisterWithRATNeeded() || this.mRegMan.getNetworkEvent(this.mPhoneId).outOfService) {
                IMSLog.i(LOG_TAG, this.mPhoneId, "onTelephonyCallStatusChanged: delayedDeregisterTimer 10 seconds start");
                setDelayedDeregisterTimerRunning(true);
                this.mRegMan.sendDeregister(this.mTask, 10000L);
            }
        }
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public Set<String> filterService(Set<String> set, int i) {
        if (isImsDisabled()) {
            return new HashSet();
        }
        HashSet hashSet = new HashSet();
        HashSet hashSet2 = new HashSet(set);
        if (DmConfigHelper.getImsSwitchValue(this.mContext, "volte", this.mPhoneId) == 1) {
            hashSet.addAll(servicesByImsSwitch(ImsProfile.getVoLteServiceList()));
            if (!hashSet.contains("mmtel")) {
                this.mTask.setRegiFailReason(DiagnosisConstants.REGI_FRSN.NO_MMTEL_IMS_SWITCH_OFF.getCode());
            }
        }
        Set<String> applyImsSwitch = applyImsSwitch(hashSet, i);
        applyRcsSwitch(set, applyImsSwitch, hashSet2, i);
        if (applyImsSwitch.isEmpty()) {
            return applyImsSwitch;
        }
        if (NetworkUtil.is3gppPsVoiceNetwork(i) && this.mTask.getProfile().getPdnType() == 11) {
            applyImsSwitch = applyVoPsPolicy(applyImsSwitch);
            if (applyImsSwitch.isEmpty()) {
                this.mTask.setRegiFailReason(DiagnosisConstants.REGI_FRSN.VOPS_OFF.getCode());
                return applyImsSwitch;
            }
        }
        if (this.mTask.getProfile().getPdnType() == 11) {
            applyImsSwitch = applyMmtelUserSettings(applyImsSwitch, i);
        }
        IAECModule iAECModule = this.mAECModule;
        if (iAECModule != null && iAECModule.isEntitlementRequired(this.mPhoneId)) {
            applyImsSwitch = applyEntitlementStatus(applyImsSwitch, i);
        }
        if (this.mTask.getProfile().getPdnType() == 11) {
            applyImsSwitch = applyPsDataOffExempt(applyImsSwitch, i);
        }
        if (this.mRegMan.getNetworkEvent(this.mPhoneId).isDataRoaming && this.mTask.getProfile().getPdnType() == 11 && i != 18) {
            if (!this.mTask.getProfile().isAllowedOnRoaming()) {
                IMSLog.i(LOG_TAG, this.mPhoneId, "filterEnabledCoreService: Roaming not support.");
                return new HashSet();
            }
            if (this.mTask.getProfile().getMediaTypeRestrictionPolicy().equalsIgnoreCase("Voice_Only")) {
                IMSLog.i(LOG_TAG, this.mPhoneId, "Support Voice Only when roaming. just filtering mmtel-video.");
                removeService(applyImsSwitch, "mmtel-video", "voice only when roaming");
            }
        }
        if (this.mMno == Mno.PLAY && i != 18 && this.mPdnController.isInternationalRoaming(this.mPhoneId)) {
            IMSLog.i(LOG_TAG, this.mPhoneId, "Videocall disabled in international roaming");
            removeService(applyImsSwitch, "mmtel-video", "Videocall disabled in international roaming");
        }
        if (!hashSet2.isEmpty()) {
            hashSet2.retainAll(applyImsSwitch);
        }
        if (!(DmConfigHelper.getImsSwitchValue(this.mContext, DeviceConfigManager.DEFAULTMSGAPPINUSE, this.mPhoneId) == 1) && this.mTask.isRcsOnly()) {
            for (String str : ImsProfile.getRcsServiceList()) {
                removeService(hashSet2, str, "RCS service off");
            }
        }
        return hashSet2;
    }

    void applyRcsSwitch(Set<String> set, Set<String> set2, Set<String> set3, int i) {
        ContentValues imsSwitchValue = DmConfigHelper.getImsSwitchValue(this.mContext, (String[]) set.toArray(new String[0]), this.mPhoneId);
        if (!isDataAllowed() && i != 18) {
            IMSLog.i(LOG_TAG, this.mPhoneId, "Mobile off!");
            if (imsSwitchValue != null && imsSwitchValue.size() > 0) {
                for (String str : ImsProfile.getRcsServiceList()) {
                    Integer asInteger = imsSwitchValue.getAsInteger(str);
                    if (asInteger != null && asInteger.intValue() == 1) {
                        removeService(set3, str, "MobileOff");
                    }
                }
            }
        }
        if (this.mConfigModule.isValidAcsVersion(this.mPhoneId)) {
            boolean isGlsEnabled = isGlsEnabled(this.mPhoneId);
            boolean isEcEnabled = isEcEnabled(this.mPhoneId);
            if (imsSwitchValue == null || imsSwitchValue.size() <= 0) {
                return;
            }
            for (String str2 : ImsProfile.getRcsServiceList()) {
                Integer asInteger2 = imsSwitchValue.getAsInteger(str2);
                if (asInteger2 != null && asInteger2.intValue() == 1) {
                    IMSLog.i(LOG_TAG, this.mPhoneId, "by switch and DM + service " + str2);
                    if (str2.equals("gls") && !isGlsEnabled) {
                        IMSLog.i(LOG_TAG, this.mPhoneId, "skip service " + str2 + " , isEnableGls is " + isGlsEnabled);
                    } else if (str2.equals("ec") && !isEcEnabled) {
                        IMSLog.i(LOG_TAG, this.mPhoneId, "skip service " + str2 + " , isEnableEc is " + isEcEnabled);
                    } else {
                        set2.add(str2);
                    }
                }
            }
        }
    }

    Set<String> applyEntitlementStatus(Set<String> set, int i) {
        if (set == null) {
            return new HashSet();
        }
        if (i != 18) {
            if (this.mAECModule.getEntitlementForVoLte(this.mPhoneId) && !this.mAECModule.getVoLteEntitlementStatus(this.mPhoneId)) {
                IMSLog.i(LOG_TAG, this.mPhoneId, "VoLTE ES not ready");
                removeService(set, "mmtel-video", "VoLTE ES not ready");
                removeService(set, "mmtel", "VoLTE ES not ready");
                if (!this.mAECModule.getEntitlementForSMSoIp(this.mPhoneId)) {
                    IMSLog.i(LOG_TAG, this.mPhoneId, "SMS ES not ready because of VoLTE ES");
                    removeService(set, "smsip", "SMS ES not ready");
                }
            }
            if (this.mAECModule.getEntitlementForSMSoIp(this.mPhoneId) && !this.mAECModule.getSMSoIpEntitlementStatus(this.mPhoneId)) {
                IMSLog.i(LOG_TAG, this.mPhoneId, "SMS ES not ready");
                removeService(set, "smsip", "SMS ES not ready");
            }
        }
        if (i == 18) {
            if (this.mAECModule.getEntitlementForVoWiFi(this.mPhoneId) && !this.mAECModule.getVoWiFiEntitlementStatus(this.mPhoneId)) {
                IMSLog.i(LOG_TAG, this.mPhoneId, "VoWiFi ES not ready");
                removeService(set, "mmtel-video", "VoWiFi ES not ready");
                removeService(set, "mmtel", "VoWiFi ES not ready");
                if (!this.mAECModule.getEntitlementForSMSoIp(this.mPhoneId)) {
                    IMSLog.i(LOG_TAG, this.mPhoneId, "SMS ES not ready because of VoWiFi ES at IWLAN");
                    removeService(set, "smsip", "SMS ES not ready at IWLAN");
                }
            }
            if (this.mAECModule.getEntitlementForSMSoIp(this.mPhoneId) && !this.mAECModule.getSMSoIpEntitlementStatus(this.mPhoneId)) {
                IMSLog.i(LOG_TAG, this.mPhoneId, "SMS ES not ready at IWLAN");
                removeService(set, "smsip", "SMS ES not ready at IWLAN");
            }
        }
        return set;
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase
    protected Set<String> applyVoPsPolicy(Set<String> set) {
        if (set == null) {
            return new HashSet();
        }
        if ((this.mMno != Mno.H3G || this.mRegMan.getTelephonyCallStatus(this.mPhoneId) == 0) && this.mRegMan.getNetworkEvent(this.mPhoneId).voiceOverPs == VoPsIndication.NOT_SUPPORTED) {
            if (this.mTask.getProfile().getSmsoipUsagePolicy().equalsIgnoreCase("Irrespective_of_voice")) {
                IMSLog.i(LOG_TAG, this.mPhoneId, "SMSoIP can be used even if VoPS not supported. just filtering mmtel, mmtel-video.");
                removeService(set, "mmtel-video", "VoPS Off");
                removeService(set, "mmtel", "VoPS Off");
            } else {
                IMSLog.i(LOG_TAG, this.mPhoneId, "by VoPS policy: remove all service");
                return new HashSet();
            }
        }
        return set;
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public SipError onSipError(String str, SipError sipError) {
        IMSLog.i(LOG_TAG, this.mPhoneId, "onSipError: service=" + str + " error=" + sipError);
        if (ImsProfile.isRcsService(str) && SipErrorBase.FORBIDDEN.equals(sipError)) {
            this.mRegMan.deregister(this.mTask, true, true, "403 Forbidden for RCS service");
            Log.i(LOG_TAG, "onSipError() deregister RCS by 403 Forbidden");
        }
        if ("smsip".equals(str)) {
            if (SipErrorBase.SIP_TIMEOUT.equals(sipError)) {
                removeCurrentPcscfAndInitialRegister(true);
            }
            Mno mno = this.mMno;
            if ((mno == Mno.EDF || mno == Mno.SWISSCOM) && "initial_registration".equals(sipError.getReason())) {
                Log.i(LOG_TAG, "smsip onSipError() initial registration after 504 Server Time-out");
                removeCurrentPcscfAndInitialRegister(true);
            }
        }
        if ("mmtel".equals(str)) {
            if (SipErrorBase.SIP_INVITE_TIMEOUT.equals(sipError) || SipErrorBase.SIP_TIMEOUT.equals(sipError)) {
                removeCurrentPcscfAndInitialRegister(true);
            }
            if (this.mMno == Mno.VODAFONE && SipErrorBase.SERVER_TIMEOUT.equals(sipError)) {
                removeCurrentPcscfAndInitialRegister(true);
            }
            Mno mno2 = this.mMno;
            if ((mno2 == Mno.TELECOM_ANDORRA || mno2.isTmobile()) && SipErrorBase.SERVICE_UNAVAILABLE.equals(sipError)) {
                removeCurrentPcscfAndInitialRegister(true);
            }
            if (this.mMno.isTmobile() && SipErrorBase.REQUEST_TIMEOUT.equals(sipError)) {
                removeCurrentPcscfAndInitialRegister(true);
            }
        }
        return sipError;
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase
    protected long getWaitTime() {
        long pow = this.mRegBaseTimeMs * ((long) Math.pow(2.0d, this.mFailureCounter));
        return (pow <= 0 || pow > this.mRegMaxTimeMs) ? this.mRegMaxTimeMs : pow;
    }

    protected long getActualWaitTime() {
        long waitTime = getWaitTime();
        return ThreadLocalRandom.current().nextLong(waitTime / 2, waitTime + 1);
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase
    protected boolean checkCallStatus() {
        if (!this.mTask.getProfile().getPdn().equals(DeviceConfigManager.IMS) || this.mRegMan.getTelephonyCallStatus(this.mPhoneId) == 0 || this.mTask.isEpdgHandoverInProgress()) {
            return true;
        }
        if (isSrvccCase()) {
            if (this.mMno.isOneOf(Mno.ORANGE, Mno.ORANGE_SWITZERLAND, Mno.TELEKOM_ALBANIA) || this.mMno.isTmobile() || this.mTask.getProfile().getBlockDeregiOnSrvcc()) {
                IMSLog.i(LOG_TAG, this.mPhoneId, "isReadyToRegister: Skip deregister SRVCC");
                return false;
            }
            IMSLog.i(LOG_TAG, this.mPhoneId, "isReadyToRegister: SRVCC case");
            return true;
        }
        IMSLog.i(LOG_TAG, this.mPhoneId, "isReadyToRegister: call state is not idle");
        return false;
    }

    protected boolean checkSetupWizard() {
        boolean z = Settings.Secure.getInt(this.mContext.getContentResolver(), "user_setup_complete", 0) == 1;
        if (!this.mTask.isRcsOnly() || z) {
            return true;
        }
        Log.i(LOG_TAG, "SetupWizard is not completed");
        return false;
    }

    protected boolean checkAvailableRat(int i) {
        if (NetworkUtil.is3gppPsVoiceNetwork(i) || i == 18 || this.mTask.getProfile().getPdnType() != 11) {
            return true;
        }
        if (!this.mMno.isTmobile() && !this.mMno.isOneOf(Mno.ORANGE, Mno.ORANGE_POLAND, Mno.WINDTRE, Mno.VODAFONE, Mno.TELEKOM_ALBANIA)) {
            return true;
        }
        IMSLog.i(LOG_TAG, this.mPhoneId, "isReadyToRegister: No PS Voice capable RAT");
        this.mTask.setRegiFailReason(DiagnosisConstants.REGI_FRSN.DATA_RAT_IS_NOT_PS_VOICE.getCode());
        this.mTask.setRegistrationRat(i);
        if (this.mTask.getImsRegistration() == null) {
            return false;
        }
        this.mTask.getImsRegistration().setCurrentRat(i);
        return false;
    }

    protected boolean checkDeregisterTimer() {
        if (this.mMno.isOneOf(Mno.TELEFONICA_UK, Mno.TELEFONICA_UK_LAB) && isDelayedDeregisterTimerRunning()) {
            IMSLog.i(LOG_TAG, this.mPhoneId, "isReadyToRegister: DelayedDeregisterTimer Running for Telefonica UK SRVCC handover. Skip delete timer if LTE attached");
            return false;
        }
        if (!this.mTask.getProfile().getBlockDeregiOnSrvcc() || !isDelayedDeregisterTimerRunning()) {
            return true;
        }
        IMSLog.i(LOG_TAG, this.mPhoneId, "isReadyToRegister: DelayedDeregisterTimer Running.");
        if (isDeregisterWithVoPSNeeded() || isDeregisterWithRATNeeded() || this.mRegMan.getNetworkEvent(this.mPhoneId).outOfService) {
            return false;
        }
        IMSLog.i(LOG_TAG, this.mPhoneId, "isReadyToRegister: LTE attached. Delete DelayedDeregisterTimer.");
        this.mRegMan.onDelayedDeregister(this.mTask);
        return true;
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernor
    protected boolean checkRcsEvent(int i) {
        if (this.mTask.isRcsOnly() && ConfigUtil.isRcsPreConsent(this.mPhoneId)) {
            boolean z = ImsConstants.SystemSettings.getRcsUserSetting(this.mContext, -1, this.mPhoneId) == 1;
            if (RcsConfigurationHelper.readIntParam(this.mContext, "version", 0).intValue() <= 0 && !z) {
                Log.i(LOG_TAG, "isReadyToRegister: User don't try RCS service yet");
                return false;
            }
            if (DmConfigHelper.getImsSwitchValue(this.mContext, DeviceConfigManager.DEFAULTMSGAPPINUSE, this.mPhoneId) != 1) {
                Log.i(LOG_TAG, "isReadyToRegister: Default MSG app isn't used for RCS");
                return false;
            }
        }
        return true;
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public boolean isReadyToRegister(int i) {
        if (checkEmergencyStatus()) {
            return true;
        }
        return checkSetupWizard() && checkRegiStatus() && checkRoamingStatus(i) && checkAvailableRat(i) && checkCallStatus() && checkWFCsettings(i) && checkDeregisterTimer() && checkNetworkEvent(i) && checkDelayedStopPdnEvent() && checkRcsEvent(i);
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernor, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public void onVolteSettingChanged() {
        if (this.mTask.isRcsOnly()) {
            IMSLog.e(LOG_TAG, this.mPhoneId, this.mTask, "onVolteSettingChanged: Ignore");
            return;
        }
        if (this.mMno.isOneOf(Mno.ORANGE_POLAND, Mno.TELIA_NORWAY, Mno.TELIA_SWE, Mno.ORANGE)) {
            updateEutranValues();
        }
        boolean z = getVoiceTechType(this.mPhoneId) == 0;
        IMSLog.i(LOG_TAG, this.mPhoneId, this.mTask, "onVolteSettingChanged: " + z);
        if (z) {
            Optional.ofNullable(this.mDelayedVolteOffFuture).filter(new Predicate() { // from class: com.sec.internal.ims.core.RegistrationGovernorEur$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean lambda$onVolteSettingChanged$0;
                    lambda$onVolteSettingChanged$0 = RegistrationGovernorEur.lambda$onVolteSettingChanged$0((ScheduledFuture) obj);
                    return lambda$onVolteSettingChanged$0;
                }
            }).ifPresent(new Consumer() { // from class: com.sec.internal.ims.core.RegistrationGovernorEur$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((ScheduledFuture) obj).cancel(false);
                }
            });
            SemTelephonyAdapter.sendVolteState(this.mPhoneId, true);
            return;
        }
        long deregistrationTimeout = IRegistrationManager.getDeregistrationTimeout(this.mTask.getProfile(), this.mTask.getRegistrationRat()) * 2;
        IMSLog.i(LOG_TAG, this.mPhoneId, this.mTask, "onVolteSettingChanged: Pending sendVolteState in " + deregistrationTimeout + "msec");
        this.mDelayedVolteOffFuture = this.mVolteOffExecutor.schedule(new Runnable() { // from class: com.sec.internal.ims.core.RegistrationGovernorEur$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                RegistrationGovernorEur.this.lambda$onVolteSettingChanged$2();
            }
        }, deregistrationTimeout, TimeUnit.MILLISECONDS);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$onVolteSettingChanged$0(ScheduledFuture scheduledFuture) {
        return !scheduledFuture.isDone();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onVolteSettingChanged$2() {
        SemTelephonyAdapter.sendVolteState(this.mPhoneId, false);
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernor, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public boolean isDelayedDeregisterTimerRunning() {
        return isDelayedDeregisterTimerRunningWithCallStatus();
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public void onRegistrationDone() {
        IMSLog.i(LOG_TAG, this.mPhoneId, "onRegistrationDone: state " + this.mTask.getState());
        this.mRegiRetryCount = 0;
        this.mIsRetryAbandon = false;
        this.mFailureCounter = 0;
        this.mRegiAt = 0L;
        this.mThrottleReason = 0;
        stopRetryTimer();
        stopTimsTimer(RegistrationConstants.REASON_REGISTERED);
    }

    private boolean isGlsEnabled(int i) {
        return RcsConfigurationHelper.readBoolParam(this.mContext, ImsUtil.getPathWithPhoneId(ConfigConstants.ConfigTable.SERVICES_GEOPUSH_AUTH, i)).booleanValue();
    }

    public void updateEutranValues() {
        if (this.mTask.getProfile().hasService("mmtel")) {
            int voiceTechType = getVoiceTechType();
            IMSLog.i(LOG_TAG, this.mPhoneId, "updateEutranValues : voiceTech : " + voiceTechType);
            ContentValues contentValues = new ContentValues();
            if (voiceTechType == 0) {
                contentValues.put(GlobalSettingsConstants.Registration.VOICE_DOMAIN_PREF_EUTRAN, (Integer) 3);
            } else {
                contentValues.put(GlobalSettingsConstants.Registration.VOICE_DOMAIN_PREF_EUTRAN, (Integer) 1);
            }
            if (this.mMno.isOneOf(Mno.TELIA_NORWAY, Mno.TELIA_SWE) && this.mNonVoLTESimByPdnFail) {
                contentValues.put(GlobalSettingsConstants.Registration.VOICE_DOMAIN_PREF_EUTRAN, (Integer) 1);
            }
            this.mContext.getContentResolver().update(Uri.parse("content://com.sec.ims.settings/global").buildUpon().fragment("simslot" + this.mPhoneId).build(), contentValues, null, null);
        }
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public boolean allowRoaming() {
        if (this.mTask.getProfile().hasEmergencySupport()) {
            IMSLog.i(LOG_TAG, this.mPhoneId, "allowRoaming: Emergency profile. Return true.");
            return true;
        }
        if (this.mMno == Mno.BTOP && !this.mTask.getProfile().isAllowedOnRoaming()) {
            if (this.mPdnController.isInternationalRoaming(this.mPhoneId)) {
                return false;
            }
            IMSLog.i(LOG_TAG, this.mPhoneId, "allowRoaming: Domestic roaming. Return true.");
            return true;
        }
        return this.mTask.getProfile().isAllowedOnRoaming();
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase
    protected void handleForbiddenError(long j) {
        if (this.mTask.isRcsOnly() && this.mTask.getProfile().getNeedAutoconfig()) {
            int i = this.mRegiRetryCount;
            if (i < 4) {
                this.mRegiRetryCount = i + 1;
                this.mConfigModule.startAcs(this.mPhoneId);
            } else {
                IMSLog.i(LOG_TAG, this.mPhoneId, "onRegistrationError: REGI_RETRY_LIMIT is 4 so ship re-config.");
            }
            this.mIsRetryAbandon = true;
            return;
        }
        String regRetryPcscfPolicyOn403 = this.mTask.getProfile().getRegRetryPcscfPolicyOn403();
        if (RegistrationGovernor.RETRY_TO_NEXT_PCSCF.equals(regRetryPcscfPolicyOn403)) {
            if (this.mMno.isOrangeGPG()) {
                if (j > 0) {
                    IMSLog.e(LOG_TAG, this.mPhoneId, "onRegistrationError: Being retryAfter header Retry to same PCSCF address in case 403 Forbidden");
                    this.mCurPcscfIpIdx--;
                } else if (isLastPcscfAddr() && j == 0 && this.mTask.getState() == RegistrationConstants.RegisterTaskState.CONNECTED) {
                    this.mRegMan.stopPdnConnectivity(this.mTask.getPdnType(), this.mTask);
                    resetPcscfList();
                }
            }
            IMSLog.e(LOG_TAG, this.mPhoneId, "onRegistrationError: Retry to next PCSCF address in case 403 Forbidden");
            return;
        }
        if (RegistrationGovernor.RETRY_TO_SAME_PCSCF.equals(regRetryPcscfPolicyOn403)) {
            IMSLog.e(LOG_TAG, this.mPhoneId, "onRegistrationError: Retry to same PCSCF address in case 403 Forbidden");
            this.mCurPcscfIpIdx--;
            return;
        }
        if (this.mMno.isOneOf(Mno.TELIA_NORWAY, Mno.EE, Mno.EE_ESN) && checkEmergencyInProgress() && this.mTask.getState() == RegistrationConstants.RegisterTaskState.CONNECTED) {
            IMSLog.e(LOG_TAG, this.mPhoneId, "onRegistrationError: No need permant fail in emergency registering");
            this.mRegMan.stopPdnConnectivity(this.mTask.getPdnType(), this.mTask);
            resetPcscfList();
            return;
        }
        IMSLog.e(LOG_TAG, this.mPhoneId, "onRegistrationError: Permanently prohibited.");
        this.mIsPermanentStopped = true;
        if (this.mTask.getState() == RegistrationConstants.RegisterTaskState.CONNECTED) {
            this.mRegMan.stopPdnConnectivity(this.mTask.getPdnType(), this.mTask);
            resetPcscfList();
        }
        if (this.mTask.getProfile().getPdnType() == 11) {
            this.mRegMan.notifyImsNotAvailable(this.mTask, true, true);
        }
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase
    protected void handleTimeoutError(long j) {
        if (isLastPcscfAddr() && this.mTask.getState() == RegistrationConstants.RegisterTaskState.CONNECTED) {
            if (!this.mTask.isRcsOnly()) {
                this.mRegMan.stopPdnConnectivity(this.mTask.getPdnType(), this.mTask);
                resetPcscfList();
            } else {
                IMSLog.e(LOG_TAG, this.mPhoneId, "onRegistrationError: skip resetPcscfList.");
            }
        }
        if (this.mMno == Mno.ORANGE && j == 0) {
            IMSLog.e(LOG_TAG, this.mPhoneId, "onRegistrationError: Orange requirment,send Try register after timer F next PCSF address");
            this.mNeedDirectRetry = true;
        }
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase
    protected void handleRetryTimer(long j) {
        if (isLastPcscfAddr()) {
            this.mCurPcscfIpIdx = 0;
            if (this.mTask.isRcsOnly()) {
                this.checkEndPcscfList = true;
            }
        }
        long j2 = 1000;
        if (this.mNeedDirectRetry) {
            j = 1000;
        }
        if (j == 0) {
            j = getActualWaitTime();
            Log.i(LOG_TAG, "retryAfter set to ActualWaitTime = " + j + "; mFailureCounter = " + this.mFailureCounter);
        }
        if (!this.mTask.isRcsOnly() || !this.checkEndPcscfList) {
            j2 = j;
        } else if (this.mPcscfRetryTimeMap.containsKey(getCurrentPcscfIp())) {
            long longValue = this.mPcscfRetryTimeMap.get(getCurrentPcscfIp()).longValue();
            if (longValue - SystemClock.elapsedRealtime() > 0) {
                j2 = longValue - SystemClock.elapsedRealtime();
            }
        } else {
            j2 = getWaitTime();
        }
        this.mThrottleReason = 0;
        startRetryTimer(j2);
    }

    boolean isLastPcscfAddr() {
        return this.mCurPcscfIpIdx >= this.mNumOfPcscfIp;
    }

    long handleServiceUnavailable(long j) {
        if (this.mMno.isOrangeGPG()) {
            if (this.mTask.getProfile().getPdnType() == 11 && isLastPcscfAddr() && j == 0 && this.mTask.getState() == RegistrationConstants.RegisterTaskState.CONNECTED) {
                this.mRegMan.stopPdnConnectivity(this.mTask.getPdnType(), this.mTask);
                resetPcscfList();
            }
        } else if (this.mMno.isOneOf(Mno.TELIA_SWE, Mno.MEGAFON_RUSSIA)) {
            if (isLastPcscfAddr() && this.mTask.getState() == RegistrationConstants.RegisterTaskState.CONNECTED) {
                this.mRegMan.stopPdnConnectivity(this.mTask.getPdnType(), this.mTask);
                resetPcscfList();
            }
            this.mNeedDirectRetry = true;
        } else if (this.mMno == Mno.TELEFONICA_SPAIN) {
            if (j != 0) {
                this.mCurPcscfIpIdx--;
            }
        } else if (this.mTask.isRcsOnly()) {
            if (j == 0) {
                j = getWaitTime();
            }
            long elapsedRealtime = SystemClock.elapsedRealtime() + j;
            String lastPcscfIp = getLastPcscfIp();
            if (!lastPcscfIp.isEmpty()) {
                this.mPcscfRetryTimeMap.put(lastPcscfIp, Long.valueOf(elapsedRealtime));
            }
            this.mNeedDirectRetry = !this.checkEndPcscfList;
        }
        return j;
    }

    void adjustTmobileErrorTreatment(SipError sipError, long j) {
        SipError sipError2 = SipErrorBase.USE_PROXY;
        if (sipError2.equals(sipError) || SipErrorBase.SipErrorType.ERROR_4XX.equals(sipError) || SipErrorBase.SipErrorType.ERROR_5XX.equals(sipError) || SipErrorBase.SipErrorType.ERROR_6XX.equals(sipError)) {
            if (j != 0) {
                this.mCurPcscfIpIdx--;
            }
            if (isLastPcscfAddr() && this.mTask.getState() == RegistrationConstants.RegisterTaskState.CONNECTED) {
                this.mRegMan.stopPdnConnectivity(this.mTask.getPdnType(), this.mTask);
                resetPcscfList();
                if (sipError2.equals(sipError) || SipErrorBase.NOTIFY_TERMINATED_DEACTIVATED.equals(sipError)) {
                    this.mNeedDirectRetry = true;
                    this.mFailureCounter = 0;
                }
            }
        }
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public boolean determineDeRegistration(int i, int i2) {
        if (i == 0) {
            Mno mno = this.mMno;
            if (mno == Mno.TELENOR_DK) {
                this.mTask.setDeregiReason(4);
                this.mRegMan.deregister(this.mTask, false, false, 5000, "Telenor DK delay 5s to deregister");
                return true;
            }
            boolean z = mno == Mno.TELEKOM_ALBANIA && this.mTask.getProfile().getPdn().equals(DeviceConfigManager.IMS) && isSrvccCase();
            IMSLog.i(LOG_TAG, this.mPhoneId, "isNeedToDeRegistration: no IMS service for network " + i2 + ". Deregister.");
            this.mTask.setReason("no IMS service for network : " + i2);
            this.mTask.setDeregiReason(4);
            this.mRegMan.tryDeregisterInternal(this.mTask, z, false);
            return true;
        }
        return super.determineDeRegistration(i, i2);
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernor, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public boolean onUpdateGeolocation(LocationInfo locationInfo) {
        if (this.mMno == Mno.TELENOR_DK && !TextUtils.isEmpty(locationInfo.mCountry) && isThrottled()) {
            releaseThrottle(6);
        }
        if (!this.mMno.isOneOf(Mno.TELEFONICA_UK)) {
            return false;
        }
        updateGeolocation(locationInfo.mCountry);
        return false;
    }

    private boolean isDataAllowed() {
        ISimManager simManagerFromSimSlot = SimManagerFactory.getSimManagerFromSimSlot(this.mPhoneId);
        if (simManagerFromSimSlot == null) {
            return false;
        }
        int subscriptionId = simManagerFromSimSlot.getSubscriptionId();
        ImsConstants.SystemSettings.SettingsItem settingsItem = ImsConstants.SystemSettings.DATA_ROAMING;
        boolean z = settingsItem.getbySubId(this.mContext, ImsConstants.SystemSettings.DATA_ROAMING_UNKNOWN, subscriptionId) == ImsConstants.SystemSettings.ROAMING_DATA_ENABLED || settingsItem.get(this.mContext, ImsConstants.SystemSettings.DATA_ROAMING_UNKNOWN) == ImsConstants.SystemSettings.ROAMING_DATA_ENABLED;
        boolean isMobileDataOn = NetworkUtil.isMobileDataOn(this.mContext);
        boolean isNetworkRoaming = this.mTelephonyManager.isNetworkRoaming(subscriptionId);
        IMSLog.i(LOG_TAG, "isDataAllowed: isRoaming = " + isNetworkRoaming + ", isDataOn = " + isMobileDataOn + ", isDataRoamingOn =" + z);
        return (isMobileDataOn && !isNetworkRoaming) || (isMobileDataOn && isNetworkRoaming && z);
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
    public void onCallStatus(IRegistrationGovernor.CallEvent callEvent, SipError sipError, int i) {
        Log.i(LOG_TAG, "onCallStatus: event=" + callEvent + " error=" + sipError);
        if (callEvent == IRegistrationGovernor.CallEvent.EVENT_CALL_ESTABLISHED) {
            this.mHasVoLteCall = true;
            return;
        }
        if (callEvent == IRegistrationGovernor.CallEvent.EVENT_CALL_LAST_CALL_END) {
            this.mHasVoLteCall = false;
            if (this.mHasPendingReRegistration) {
                IMSLog.i(LOG_TAG, "onCallStatus : call ended, proceeding with pending re-registration task");
                this.mTask.setHasForcedPendingUpdate(true);
                this.mRegMan.addPendingUpdateRegistration(this.mTask, 0);
                this.mHasPendingReRegistration = false;
                return;
            }
            return;
        }
        if (callEvent == IRegistrationGovernor.CallEvent.EVENT_CALL_ALT_SERVICE_INITIAL_REGI) {
            handleAlternativeCallState();
        }
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernor, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public void onSrvccComplete() {
        if (this.mMno.isOneOf(Mno.TELEFONICA_UK, Mno.TELEFONICA_UK_LAB)) {
            IMSLog.i(LOG_TAG, this.mPhoneId, "onSrvccComplete: delayedDeregisterTimer 4 seconds start");
            setDelayedDeregisterTimerRunning(true);
            this.mRegMan.sendDeregister(this.mTask, 4000L);
            return;
        }
        Mno mno = this.mMno;
        if (mno != Mno.MEO_PORTUGAL) {
            if (mno == Mno.DNA_FINLAND) {
                IMSLog.i(LOG_TAG, this.mPhoneId, "onSrvccComplete: delayedDeregisterTimer 1 second start");
                setDelayedDeregisterTimerRunning(true);
                this.mRegMan.sendDeregister(this.mTask, 1000L);
                return;
            }
            return;
        }
        if (this.mTask.getState().equals(RegistrationConstants.RegisterTaskState.REGISTERED)) {
            this.mTask.setDeregiReason(4);
            this.mRegMan.tryDeregisterInternal(this.mTask, true, false);
        }
    }

    private class DailyReRegisterIntentReceiver extends BroadcastReceiver {
        private DailyReRegisterIntentReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (ImsConstants.Intents.ACTION_CHECK_REGISTRATION_DAILY.equals(intent.getAction())) {
                IMSLog.i(RegistrationGovernorEur.LOG_TAG, "DailyReRegisterIntentReceiver : CHECK_REGISTRATION_DAILY received");
                RegistrationGovernorEur registrationGovernorEur = RegistrationGovernorEur.this;
                if (NetworkUtil.is3gppPsVoiceNetwork(registrationGovernorEur.mRegMan.getCurrentNetworkByPhoneId(registrationGovernorEur.mPhoneId))) {
                    IMSLog.i(RegistrationGovernorEur.LOG_TAG, "DailyReRegisterIntentReceiver : NETWORK_TYPE_LTE/NR");
                    if (!RegistrationGovernorEur.this.mTask.getState().equals(RegistrationConstants.RegisterTaskState.REGISTERED)) {
                        IMSLog.i(RegistrationGovernorEur.LOG_TAG, "DailyReRegisterIntentReceiver : state not REGISTERED, proceeding with initial registration");
                        RegistrationGovernorEur registrationGovernorEur2 = RegistrationGovernorEur.this;
                        registrationGovernorEur2.mRegMan.stopPdnConnectivity(registrationGovernorEur2.mTask.getPdnType(), RegistrationGovernorEur.this.mTask);
                        RegistrationGovernorEur.this.mTask.setState(RegistrationConstants.RegisterTaskState.IDLE);
                        RegistrationGovernorEur.this.releaseThrottle(6);
                        RegistrationGovernorEur registrationGovernorEur3 = RegistrationGovernorEur.this;
                        registrationGovernorEur3.mRegMan.updateRegistration(registrationGovernorEur3.mTask.getPhoneId(), RegistrationConstants.UpdateRegiReason.DAILYREREGISTER);
                        return;
                    }
                    if (!RegistrationGovernorEur.this.mHasVoLteCall) {
                        IMSLog.i(RegistrationGovernorEur.LOG_TAG, "DailyReRegisterIntentReceiver : state REGISTERED, proceeding with re-register message");
                        RegistrationGovernorEur.this.mTask.setHasForcedPendingUpdate(true);
                        RegistrationGovernorEur registrationGovernorEur4 = RegistrationGovernorEur.this;
                        registrationGovernorEur4.mRegMan.addPendingUpdateRegistration(registrationGovernorEur4.mTask, 0);
                        return;
                    }
                    IMSLog.i(RegistrationGovernorEur.LOG_TAG, "DailyReRegisterIntentReceiver : state REGISTERED, but call active - postponing re-registration until call ended");
                    RegistrationGovernorEur.this.mHasPendingReRegistration = true;
                }
            }
        }
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase, com.sec.internal.ims.core.RegistrationGovernor, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public void unRegisterIntentReceiver() {
        Log.i(LOG_TAG, "Un-register Intent receiver(s)");
        try {
            this.mContext.unregisterReceiver(this.mUpsmEventReceiver);
            this.mContext.unregisterReceiver(this.mPackageDataClearedIntentReceiver);
            DailyReRegisterIntentReceiver dailyReRegisterIntentReceiver = this.mDailyReRegisterIntentReceiver;
            if (dailyReRegisterIntentReceiver != null) {
                this.mContext.unregisterReceiver(dailyReRegisterIntentReceiver);
                this.mDailyReRegisterIntentReceiver = null;
            }
        } catch (IllegalArgumentException unused) {
            Log.e(LOG_TAG, "unRegisterIntentReceiver: Receiver not registered!");
        }
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public int getNumOfEmerPcscfIp() {
        if (this.mMno == Mno.H3G_AT) {
            return 1;
        }
        return this.mNumOfPcscfIp;
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernor, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public boolean blockImmediatelyUpdateRegi() {
        IVolteServiceModule iVolteServiceModule;
        return (this.mTask.getMno() == Mno.TMOBILE || this.mTask.getMno() == Mno.TDC_DK) && (iVolteServiceModule = this.mVsm) != null && iVolteServiceModule.hasCsCall(this.mPhoneId);
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase
    protected int getVoiceTechType() {
        forceTurnOnVoLteWhenMenuRemoved();
        return super.getVoiceTechType();
    }
}
