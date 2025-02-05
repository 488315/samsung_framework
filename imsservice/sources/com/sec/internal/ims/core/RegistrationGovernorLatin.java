package com.sec.internal.ims.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.util.Log;
import com.sec.ims.settings.ImsProfile;
import com.sec.ims.util.SipError;
import com.sec.internal.constants.Mno;
import com.sec.internal.constants.ims.DiagnosisConstants;
import com.sec.internal.constants.ims.ImsConstants;
import com.sec.internal.constants.ims.SipErrorBase;
import com.sec.internal.helper.DmConfigHelper;
import com.sec.internal.helper.NetworkUtil;
import com.sec.internal.helper.RcsConfigurationHelper;
import com.sec.internal.helper.os.ITelephonyManager;
import com.sec.internal.ims.settings.DeviceConfigManager;
import com.sec.internal.ims.util.ConfigUtil;
import com.sec.internal.ims.util.ImsUtil;
import com.sec.internal.ims.util.SemTelephonyAdapter;
import com.sec.internal.interfaces.ims.config.IConfigModule;
import com.sec.internal.interfaces.ims.core.IRegistrationManager;
import com.sec.internal.interfaces.ims.servicemodules.volte2.IVolteServiceModule;
import com.sec.internal.log.IMSLog;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* loaded from: classes.dex */
public class RegistrationGovernorLatin extends RegistrationGovernorBase {
    private static final String INTENT_ACTION_IMS_DEREGISTERED = "com.sec.imsservice.action.IMS_DEREGISTERED";
    private static final String INTENT_ACTION_IMS_REGISTERED = "com.sec.imsservice.action.IMS_REGISTERED";
    protected static final String INTENT_ACTION_SETUPWIZARD_COMPLETE = "com.sec.android.app.secsetupwizard.SETUPWIZARD_COMPLETE";
    protected static final String INTENT_RCS_REGISTRATION = "com.samsung.android.messaging.action.REQUEST_RCS_REGISTRATION";
    private static final String LOG_TAG = "RegiGvnLatin";
    protected BroadcastReceiver mIntentReceiverLatin;
    protected String mRegisteredNetworkType;
    ScheduledExecutorService mVolteOffExecutor;

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase, com.sec.internal.ims.core.RegistrationGovernor
    protected boolean checkVolteSetting(int i) {
        return true;
    }

    public RegistrationGovernorLatin(RegistrationManagerInternal registrationManagerInternal, ITelephonyManager iTelephonyManager, RegisterTask registerTask, PdnController pdnController, IVolteServiceModule iVolteServiceModule, IConfigModule iConfigModule, Context context) {
        super(registrationManagerInternal, iTelephonyManager, registerTask, pdnController, iVolteServiceModule, iConfigModule, context);
        this.mRegisteredNetworkType = null;
        this.mVolteOffExecutor = Executors.newSingleThreadScheduledExecutor();
        this.mIntentReceiverLatin = new BroadcastReceiver() { // from class: com.sec.internal.ims.core.RegistrationGovernorLatin.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                Log.i(RegistrationGovernorLatin.LOG_TAG, "intent = " + intent.getAction());
                if (RegistrationGovernorLatin.INTENT_ACTION_SETUPWIZARD_COMPLETE.equals(intent.getAction())) {
                    Log.i(RegistrationGovernorLatin.LOG_TAG, "Try register after setupwizard is completed");
                    RegistrationGovernorLatin registrationGovernorLatin = RegistrationGovernorLatin.this;
                    registrationGovernorLatin.mRegHandler.sendTryRegister(registrationGovernorLatin.mPhoneId);
                } else if (RegistrationGovernorLatin.INTENT_RCS_REGISTRATION.equals(intent.getAction())) {
                    Log.i(RegistrationGovernorLatin.LOG_TAG, "Try register when user trigger rcs registration on MSG app");
                    RegistrationGovernorLatin registrationGovernorLatin2 = RegistrationGovernorLatin.this;
                    registrationGovernorLatin2.mConfigModule.getAcsConfig(registrationGovernorLatin2.mPhoneId).setAcsCompleteStatus(false);
                    RegistrationGovernorLatin registrationGovernorLatin3 = RegistrationGovernorLatin.this;
                    registrationGovernorLatin3.mRegHandler.sendTryRegister(registrationGovernorLatin3.mPhoneId);
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(INTENT_ACTION_SETUPWIZARD_COMPLETE);
        intentFilter.addAction(INTENT_RCS_REGISTRATION);
        this.mContext.registerReceiver(this.mIntentReceiverLatin, intentFilter);
        this.mHandlePcscfOnAlternativeCall = true;
        int i = this.mPhoneId;
        SemTelephonyAdapter.sendVolteState(i, getVoiceTechType(i) == 0);
        IMSLog.i(LOG_TAG, this.mPhoneId, "send VoLTE state for Latin Carriers: voiceTech : " + getVoiceTechType(this.mPhoneId));
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase, com.sec.internal.ims.core.RegistrationGovernor, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public void unRegisterIntentReceiver() {
        Log.i(LOG_TAG, "Un-register Intent receiver(s)");
        try {
            this.mContext.unregisterReceiver(this.mIntentReceiverLatin);
        } catch (IllegalArgumentException unused) {
            Log.e(LOG_TAG, "unRegisterIntentReceiver: Receiver not registered!");
        }
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public boolean isReadyToRegister(int i) {
        if (this.mTask.isRcsOnly()) {
            if (RcsConfigurationHelper.readIntParam(this.mContext, ImsUtil.getPathWithPhoneId("version", this.mPhoneId), -1).intValue() <= 0 && ImsConstants.SystemSettings.getRcsUserSetting(this.mContext, -1, this.mPhoneId) == -1) {
                IMSLog.i(LOG_TAG, this.mPhoneId, "isReadyToRegister: User don't try RCS service yet");
                return false;
            }
            if (DmConfigHelper.getImsSwitchValue(this.mContext, DeviceConfigManager.DEFAULTMSGAPPINUSE, this.mPhoneId) != 1) {
                IMSLog.i(LOG_TAG, this.mPhoneId, "isReadyToRegister: Default MSG app isn't used for RCS");
                return false;
            }
        }
        return super.isReadyToRegister(i);
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernor, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public void onDeregistrationDone(boolean z) {
        super.onDeregistrationDone(z);
        if (this.mMno == Mno.VIVO_BRAZIL && this.mTask.getProfile().getPdnType() == 11) {
            Intent intent = new Intent(INTENT_ACTION_IMS_DEREGISTERED);
            if (TextUtils.isEmpty(this.mRegisteredNetworkType)) {
                return;
            }
            intent.putExtra("rat", this.mRegisteredNetworkType);
            this.mContext.sendBroadcast(intent, "android.permission.READ_PHONE_STATE");
            Log.i(LOG_TAG, "Broadcast intent: " + intent.getAction() + ", rat [" + intent.getStringExtra("rat") + "]");
            this.mRegisteredNetworkType = null;
        }
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public Set<String> filterService(Set<String> set, int i) {
        if (isImsDisabled()) {
            return new HashSet();
        }
        Set<String> hashSet = new HashSet<>();
        HashSet hashSet2 = new HashSet(set);
        if (DmConfigHelper.getImsSwitchValue(this.mContext, "volte", this.mPhoneId) == 1) {
            hashSet.addAll(servicesByReadSwitch((String[]) servicesByImsSwitch(ImsProfile.getVoLteServiceList()).toArray(new String[0])));
            if (!hashSet.contains("mmtel")) {
                this.mTask.setRegiFailReason(DiagnosisConstants.REGI_FRSN.NO_MMTEL_IMS_SWITCH_OFF.getCode());
            }
        }
        if (this.mConfigModule.isValidAcsVersion(this.mPhoneId)) {
            hashSet.addAll(servicesByReadSwitch((String[]) servicesByImsSwitch(ImsProfile.getRcsServiceList()).toArray(new String[0])));
            Context context = this.mContext;
            int i2 = this.mPhoneId;
            List<String> rcsEnabledServiceList = RcsConfigurationHelper.getRcsEnabledServiceList(context, i2, ConfigUtil.getRcsProfileWithFeature(context, i2, this.mTask.getProfile()));
            for (String str : ImsProfile.getRcsServiceList()) {
                if (!rcsEnabledServiceList.contains(str)) {
                    removeService(hashSet2, str, "Disabled from ACS");
                }
            }
        }
        if (i == 13 && this.mTask.getProfile().getPdnType() == 11) {
            hashSet = applyVoPsPolicy(hashSet);
            if (hashSet.isEmpty()) {
                this.mTask.setRegiFailReason(DiagnosisConstants.REGI_FRSN.VOPS_OFF.getCode());
                return hashSet;
            }
        } else if (!NetworkUtil.isMobileDataOn(this.mContext) && i != 18) {
            Log.i(LOG_TAG, "Mobile off!");
            if (this.mTask.getProfile().hasService("im") || this.mTask.getProfile().hasService("ft")) {
                return new HashSet();
            }
        }
        Set<String> applyMmtelUserSettings = applyMmtelUserSettings(hashSet2, i);
        if (!applyMmtelUserSettings.isEmpty()) {
            applyMmtelUserSettings.retainAll(hashSet);
        }
        return applyMmtelUserSettings;
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public boolean isLocationInfoLoaded(int i) {
        Log.i(LOG_TAG, "Latin operator allow registration even without geo-location");
        return true;
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public void onRegistrationDone() {
        super.onRegistrationDone();
        if (this.mMno == Mno.VIVO_BRAZIL && this.mTask.getProfile().getPdnType() == 11) {
            Intent intent = new Intent(INTENT_ACTION_IMS_REGISTERED);
            if (this.mRegMan.getCurrentNetworkByPhoneId(this.mPhoneId) == 18) {
                this.mRegisteredNetworkType = "wifi";
            } else {
                this.mRegisteredNetworkType = "mobile";
            }
            intent.putExtra("rat", this.mRegisteredNetworkType);
            this.mContext.sendBroadcast(intent, "android.permission.READ_PHONE_STATE");
            Log.i(LOG_TAG, "Broadcast intent: " + intent.getAction() + ", rat [" + intent.getStringExtra("rat") + "]");
        }
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public SipError onSipError(String str, SipError sipError) {
        Log.e(LOG_TAG, "onSipError: service=" + str + " error=" + sipError);
        if ("mmtel".equals(str) && this.mMno == Mno.TIGO_GUATEMALA && SipErrorBase.SERVER_TIMEOUT.equals(sipError)) {
            removeCurrentPcscfAndInitialRegister(true);
        }
        return sipError;
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase
    protected void handleAlternativeCallState() {
        if (this.mMno == Mno.TIGO_GUATEMALA) {
            super.handleAlternativeCallState();
        }
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase
    protected Set<String> applyMmtelUserSettings(Set<String> set, int i) {
        if (set == null) {
            return new HashSet();
        }
        if (!isVideoCallEnabled()) {
            removeService(set, "mmtel-video", "VideoCall disable.");
        }
        if (getVoiceTechType() == 0 || this.mTask.getRegistrationRat() == 18) {
            return set;
        }
        Log.i(LOG_TAG, "by VoLTE OFF, remove all service, RAT :" + this.mTask.getRegistrationRat());
        this.mTask.setRegiFailReason(DiagnosisConstants.REGI_FRSN.USER_SETTINGS_OFF.getCode());
        return new HashSet();
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernor, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public void onVolteSettingChanged() {
        if (this.mTask.isRcsOnly()) {
            IMSLog.e(LOG_TAG, this.mPhoneId, this.mTask, "onVolteSettingChanged: Ignore");
            return;
        }
        boolean z = getVoiceTechType(this.mPhoneId) == 0;
        IMSLog.i(LOG_TAG, this.mPhoneId, this.mTask, "onVolteSettingChanged: " + z);
        if (z) {
            Optional.ofNullable(this.mDelayedVolteOffFuture).filter(new Predicate() { // from class: com.sec.internal.ims.core.RegistrationGovernorLatin$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean lambda$onVolteSettingChanged$0;
                    lambda$onVolteSettingChanged$0 = RegistrationGovernorLatin.lambda$onVolteSettingChanged$0((ScheduledFuture) obj);
                    return lambda$onVolteSettingChanged$0;
                }
            }).ifPresent(new Consumer() { // from class: com.sec.internal.ims.core.RegistrationGovernorLatin$$ExternalSyntheticLambda1
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
        this.mDelayedVolteOffFuture = this.mVolteOffExecutor.schedule(new Runnable() { // from class: com.sec.internal.ims.core.RegistrationGovernorLatin$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                RegistrationGovernorLatin.this.lambda$onVolteSettingChanged$2();
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

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase
    protected int getVoiceTechType() {
        forceTurnOnVoLteWhenMenuRemoved();
        return super.getVoiceTechType();
    }
}
