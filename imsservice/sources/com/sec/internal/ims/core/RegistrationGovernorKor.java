package com.sec.internal.ims.core;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.os.SemSystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;
import com.sec.ims.extensions.Extensions;
import com.sec.ims.settings.ImsProfile;
import com.sec.ims.settings.NvConfiguration;
import com.sec.ims.util.SipError;
import com.sec.imsservice.R;
import com.sec.internal.constants.Mno;
import com.sec.internal.constants.ims.DiagnosisConstants;
import com.sec.internal.constants.ims.ImsConstants;
import com.sec.internal.constants.ims.SipErrorBase;
import com.sec.internal.constants.ims.cmstore.CloudMessageProviderContract;
import com.sec.internal.constants.ims.config.ConfigConstants;
import com.sec.internal.constants.ims.core.PdnFailReason;
import com.sec.internal.constants.ims.core.RegistrationConstants;
import com.sec.internal.constants.ims.os.NetworkEvent;
import com.sec.internal.constants.ims.os.VoPsIndication;
import com.sec.internal.constants.ims.settings.GlobalSettingsConstants;
import com.sec.internal.helper.CollectionUtils;
import com.sec.internal.helper.DmConfigHelper;
import com.sec.internal.helper.ImsSharedPrefHelper;
import com.sec.internal.helper.NetworkUtil;
import com.sec.internal.helper.OmcCode;
import com.sec.internal.helper.RcsConfigurationHelper;
import com.sec.internal.helper.SimUtil;
import com.sec.internal.helper.SimpleEventLog;
import com.sec.internal.helper.os.ITelephonyManager;
import com.sec.internal.helper.os.LinkPropertiesWrapper;
import com.sec.internal.ims.core.RegistrationManager;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Id;
import com.sec.internal.ims.core.sim.SimManagerFactory;
import com.sec.internal.ims.rcs.util.RcsUtils;
import com.sec.internal.ims.registry.ImsRegistry;
import com.sec.internal.ims.servicemodules.im.ImModule;
import com.sec.internal.ims.settings.DeviceConfigManager;
import com.sec.internal.ims.settings.DmProfileLoader;
import com.sec.internal.ims.settings.ImsAutoUpdate;
import com.sec.internal.ims.settings.ImsProfileCache;
import com.sec.internal.imscr.LogClass;
import com.sec.internal.interfaces.ims.config.IConfigModule;
import com.sec.internal.interfaces.ims.core.IRegistrationGovernor;
import com.sec.internal.interfaces.ims.core.ISimManager;
import com.sec.internal.interfaces.ims.servicemodules.volte2.IVolteServiceModule;
import com.sec.internal.log.IMSLog;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

/* loaded from: classes.dex */
public class RegistrationGovernorKor extends RegistrationGovernorBase {
    private static final Long DEFAULT_RETRY_AFTER_BUFFER_MS = 500L;
    private static final long DEFAULT_TIMS_TIMER_MS = 60000;
    private static final long DNS_RETRY_TIME_MS = 4000;
    private static final int IMS_NOT_AVAILABLE_REG_FAIL_RETRY = 2;
    protected static final String INTENT_NEW_OUTGOING_CALL = "android.intent.action.NEW_OUTGOING_CALL";
    protected static final String INTENT_USIMDOWNLOAD_END = "com.sec.android.UsimRegistrationKOR.UsimDownload.end";
    protected static final String INTENT_WAP_PUSH_DM_NOTI_RECEIVED = "com.samsung.provider.Telephony.WAP_PUSH_DM_NOTI_RECEIVED";
    private static final int MAX_REQUESTPDN_COUNT = 5;
    private static final String OMADM_KT_DEFAULT_PCSCF = "volte.imskt.com";
    private static final String OMADM_SKT_DEFAULT_PCSCF = "172.28.109.141,fd00:0e15:0501:5::141,172.28.109.73,fd00:e15:301:5::73,211.188.227.140,2001:2d8:e0:227::140";
    static final long REG_RETRY_MAX_TIME_FOR_UNLIMITED_404_MS = 14400000;
    private static final int REQUESTPDN_INTERVAL = 3;
    private static final long REQUEST_INTERNETPDN_TIMER_MS = 30000;
    private String LOG_TAG;
    private final String[] allowedPackages;
    private long mAllowedNetworkType;
    private AllowedNetworkTypesListener mAllowedNetworkTypesListener;
    private int mConsecutiveForbiddenCounter;
    Message mDmPollingTimer;
    boolean mDmUpdatedFlag;
    int mDnsQueryCount;
    private boolean mHasNetworkFailure;
    protected boolean mHasPendingInitRegistrationByDMConfigChange;
    protected boolean mHasPendingNotifyImsNotAvailable;
    protected BroadcastReceiver mIntentReceiverKor;
    private boolean mIpsecEnabled;
    private boolean mIsAkaChallengeTimeout;
    protected boolean mIsShipBuild;
    private List<InetAddress> mLocalAddress;
    private boolean mLteModeOn;
    private boolean mNeedDelayedDeregister;
    protected Message mPDNdisconnectTimeout;
    private List<String> mPcscfList;
    List<String> mRcsPcscfList;
    private int mRequestPdnTimeoutCount;
    private boolean mSmsOverIp;
    private int mSubId;
    protected boolean mVolteServiceStatus;

    protected enum VoltePreferenceChangedReason {
        VOLTE_SETTING,
        LTE_MODE
    }

    private boolean checkValidRejectCode(int i) {
        return i == 2 || i == 3 || i == 6 || i == 8;
    }

    public RegistrationGovernorKor(RegistrationManagerInternal registrationManagerInternal, ITelephonyManager iTelephonyManager, RegisterTask registerTask, PdnController pdnController, IVolteServiceModule iVolteServiceModule, IConfigModule iConfigModule, Context context) {
        super(registrationManagerInternal, iTelephonyManager, registerTask, pdnController, iVolteServiceModule, iConfigModule, context);
        this.LOG_TAG = null;
        this.mRequestPdnTimeoutCount = 0;
        this.mHasPendingInitRegistrationByDMConfigChange = false;
        this.mHasPendingNotifyImsNotAvailable = false;
        this.mDmUpdatedFlag = false;
        this.mConsecutiveForbiddenCounter = 0;
        this.mVolteServiceStatus = true;
        this.mLocalAddress = null;
        this.mIpsecEnabled = true;
        this.mSmsOverIp = false;
        this.mLteModeOn = true;
        this.mIsShipBuild = false;
        this.mNeedDelayedDeregister = false;
        this.mIsAkaChallengeTimeout = false;
        this.mDnsQueryCount = 0;
        this.mPDNdisconnectTimeout = null;
        this.mDmPollingTimer = null;
        this.mHasNetworkFailure = false;
        this.mAllowedNetworkType = -1L;
        this.mAllowedNetworkTypesListener = null;
        this.allowedPackages = new String[]{"com.sec.imsservice", "com.skt.skaf.OA00199800", "com.samsung.android.app.telephonyui", "root", "com.android.shell"};
        if (registerTask.isRcsOnly()) {
            this.LOG_TAG = "RegiGvnKor-RCS<" + this.mPhoneId + ">";
        } else if (this.mTask.getProfile().hasEmergencySupport()) {
            this.LOG_TAG = "RegiGvnKor-EMC<" + this.mPhoneId + ">";
        } else {
            this.LOG_TAG = "RegiGvnKor<" + this.mPhoneId + ">";
        }
        this.mDmUpdatedFlag = false;
        this.mVolteServiceStatus = getVolteServiceStatus();
        this.mDnsQueryCount = 0;
        this.mPcscfList = new ArrayList();
        this.mRcsPcscfList = new ArrayList();
        this.mIpsecEnabled = true;
        this.mSmsOverIp = false;
        this.mLteModeOn = true;
        this.mThrottledforImsNotAvailable = false;
        this.mIsShipBuild = CloudMessageProviderContract.JsonData.TRUE.equals(SemSystemProperties.get("ro.product_ship", ConfigConstants.VALUE.INFO_COMPLETED));
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ImsConstants.Intents.ACTION_PERIODIC_POLLING_TIMEOUT);
        intentFilter.addAction(ImsConstants.Intents.ACTION_FLIGHT_MODE);
        intentFilter.addAction(ImsConstants.Intents.ACTION_AIRPLANE_MODE);
        intentFilter.addAction(INTENT_USIMDOWNLOAD_END);
        intentFilter.addAction(ImsConstants.Intents.INTENT_ACTION_REGIST_REJECT);
        intentFilter.addAction(ImsConstants.Intents.INTENT_ACTION_LTE_REJECT);
        intentFilter.addAction(INTENT_WAP_PUSH_DM_NOTI_RECEIVED);
        intentFilter.addAction(INTENT_NEW_OUTGOING_CALL);
        intentFilter.addAction("android.intent.action.BOOT_COMPLETED");
        Log.i(this.LOG_TAG, "intent added");
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.sec.internal.ims.core.RegistrationGovernorKor.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                Log.i(RegistrationGovernorKor.this.LOG_TAG, "onReceive:" + intent.getAction() + " mTask:" + RegistrationGovernorKor.this.mTask.getProfile().getName() + "(" + RegistrationGovernorKor.this.mTask.getState() + ")");
                String action = intent.getAction();
                action.hashCode();
                switch (action) {
                    case "com.samsung.intent.action.LTE_REJECT":
                    case "com.samsung.intent.action.regist_reject":
                        RegistrationGovernorKor.this.handleNwRejectIntent(intent);
                        break;
                    case "com.sec.android.internal.ims.FLIGHT_MODE":
                        RegistrationGovernorKor.this.handleFlightModeIntent(intent);
                        break;
                    case "android.intent.action.AIRPLANE_MODE":
                        RegistrationGovernorKor.this.handleAirplaneModeIntent(intent);
                        break;
                    case "com.samsung.provider.Telephony.WAP_PUSH_DM_NOTI_RECEIVED":
                        RegistrationGovernorKor.this.handleWapPushDmNotiReceivedIntent();
                        break;
                    case "com.sec.android.UsimRegistrationKOR.UsimDownload.end":
                        RegistrationGovernorKor.this.handleUsimDownloadEndIntent();
                        break;
                    case "android.intent.action.BOOT_COMPLETED":
                        RegistrationGovernorKor.this.handleBootCompletedIntent();
                        break;
                    case "com.sec.internal.ims.imsservice.dm_polling_timeout":
                        RegistrationGovernorKor.this.handlePeriodicPollingTimeoutIntent();
                        break;
                    case "android.intent.action.NEW_OUTGOING_CALL":
                        RegistrationGovernorKor.this.handleNewOutgoingCallIntent();
                        break;
                }
            }
        };
        this.mIntentReceiverKor = broadcastReceiver;
        this.mContext.registerReceiver(broadcastReceiver, intentFilter);
        updateEutranValues();
        int rcsUserSetting = ImsConstants.SystemSettings.getRcsUserSetting(this.mContext, -1, this.mPhoneId);
        if (rcsUserSetting == -2) {
            this.mRegMan.getEventLog().logAndAdd(this.mPhoneId, "Stucked on RCS_DISABLED_BY_NETWORK. Force to DISABLED.");
            ImsConstants.SystemSettings.setRcsUserSetting(this.mContext, 0, this.mPhoneId);
        } else if (rcsUserSetting == 2) {
            this.mRegMan.getEventLog().logAndAdd(this.mPhoneId, "Stucked on RCS_TURNING_OFF. Force to ENABLED.");
            ImsConstants.SystemSettings.setRcsUserSetting(this.mContext, 1, this.mPhoneId);
        }
    }

    void checkUnprocessedOmadmConfig() {
        NetworkEvent networkEvent;
        if (this.mRegMan == null || !this.mTask.isNeedOmadmConfig() || !OmcCode.isKOROmcCode() || (networkEvent = this.mRegMan.getNetworkEvent(this.mPhoneId)) == null || networkEvent.isDataRoaming) {
            return;
        }
        Log.i(this.LOG_TAG, "checkUnprocessedOmadmConfig");
        this.mRegHandler.sendCheckUnprocessedOmadmConfig(this.mTask);
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public void onRegistrationTerminated(SipError sipError, long j, boolean z) {
        if (!needImsNotAvailable() || (needImsNotAvailable() && j > 0)) {
            stopTimsTimer(RegistrationConstants.REASON_REGISTRATION_ERROR);
        }
        if (SipErrorBase.NOTIFY_TERMINATED_DEACTIVATED.equals(sipError) || SipErrorBase.NOTIFY_TERMINATED_PROBATION.equals(sipError)) {
            Log.e(this.LOG_TAG, "onRegistrationError: Notify terminated expired.");
            this.mRegHandler.sendTryRegister(this.mPhoneId);
            return;
        }
        if (SipErrorBase.OK.equals(sipError)) {
            this.mFailureCounter = 0;
            this.mConsecutiveForbiddenCounter = 0;
            this.mIsAkaChallengeTimeout = false;
            this.mDnsQueryCount = 0;
            stopPDNdisconnectTimer();
            stopRetryTimer();
            startRetryTimer(1000L);
            this.mDnsQueryCount = 0;
            return;
        }
        if (SipErrorBase.NOTIFY_TERMINATED_REJECTED.equals(sipError)) {
            this.mFailureCounter = 0;
            this.mRegiAt = 0L;
            this.mConsecutiveForbiddenCounter = 0;
            this.mIsAkaChallengeTimeout = false;
            this.mDnsQueryCount = 0;
            stopPDNdisconnectTimer();
            stopRetryTimer();
            resetIPSecAllow();
            this.mRegMan.getEventLog().logAndAdd("onRegistrationError: Notify terminated rejected.");
            this.mIsPermanentStopped = true;
            this.mDnsQueryCount = 0;
        }
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase
    protected void handleForbiddenError(long j) {
        int i = this.mConsecutiveForbiddenCounter + 1;
        this.mConsecutiveForbiddenCounter = i;
        if (i >= 2) {
            this.mRegMan.getEventLog().logAndAdd("onRegistrationError: Two consecutive 403 errors");
            this.mFailureCounter = 0;
            this.mRegiAt = 0L;
            this.mConsecutiveForbiddenCounter = 0;
            this.mIsAkaChallengeTimeout = false;
            this.mDnsQueryCount = 0;
            stopPDNdisconnectTimer();
            stopRetryTimer();
            resetIPSecAllow();
            this.mIsPermanentStopped = true;
            makeRegistrationFailedToast();
            return;
        }
        Log.i(this.LOG_TAG, "onRegistrationError: 403 error. Need OmaDM trial only for KOR device in domestic");
        if (this.mTask.isNeedOmadmConfig()) {
            if (OmcCode.isKOROmcCode() && !this.mRegMan.getNetworkEvent(this.mPhoneId).isDataRoaming) {
                this.mRegHandler.sendRequestDmConfig(this.mTask);
            } else {
                this.mRegHandler.sendTryRegister(this.mPhoneId);
            }
        }
        if (this.mTask.getProfile().getNeedAutoconfig()) {
            this.mConfigModule.startAcs(this.mPhoneId);
        }
        if (this.mTask.isNeedOmadmConfig() || this.mTask.getProfile().getNeedAutoconfig()) {
            return;
        }
        this.mRegHandler.sendTryRegister(this.mPhoneId);
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x02e2  */
    /* JADX WARN: Removed duplicated region for block: B:104:0x02e8  */
    @Override // com.sec.internal.ims.core.RegistrationGovernorBase, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onRegistrationError(com.sec.ims.util.SipError r8, long r9, boolean r11) {
        /*
            Method dump skipped, instructions count: 758
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sec.internal.ims.core.RegistrationGovernorKor.onRegistrationError(com.sec.ims.util.SipError, long, boolean):void");
    }

    void onRegErrorforImsNotAvailable(SipError sipError, long j) {
        Log.i(this.LOG_TAG, "onRegErrorforImsNotAvailable:");
        boolean isRefreshReg = this.mTask.isRefreshReg();
        this.mFailureCounter++;
        this.mTask.setDeregiReason(41);
        if (getCallStatus() != 0 && isRefreshReg) {
            this.mCurPcscfIpIdx = 0;
            this.mFailureCounter = 0;
            this.mConsecutiveForbiddenCounter = 0;
            this.mIsAkaChallengeTimeout = false;
            this.mDnsQueryCount = 0;
            resetIPSecAllow();
            this.mHasPendingNotifyImsNotAvailable = true;
            Log.i(this.LOG_TAG, "onRegErrorforImsNotAvailable(Postpone notifyImsNotAvailable during call)");
            return;
        }
        if (SipErrorBase.INTERVAL_TOO_BRIEF.equals(sipError)) {
            this.mCurPcscfIpIdx = 0;
            this.mFailureCounter = 0;
            this.mConsecutiveForbiddenCounter = 0;
            this.mIsAkaChallengeTimeout = false;
            this.mDnsQueryCount = 0;
            resetIPSecAllow();
            this.mRegMan.stopPdnConnectivity(this.mTask.getPdnType(), this.mTask);
            this.mRegMan.notifyImsNotAvailable(this.mTask, true);
            this.mRegMan.getEventLog().logAndAdd("onRegErrorforImsNotAvailable(423)");
            return;
        }
        if (SipErrorBase.NOT_ACCEPTABLE.equals(sipError)) {
            Log.i(this.LOG_TAG, "onRegErrorforImsNotAvailable: 406 error. Ipsec not allow");
            this.mIPsecAllow = false;
            this.mFailureCounter = 0;
            if (j > 0) {
                startRetryTimer(j);
                return;
            } else {
                this.mRegHandler.sendTryRegister(this.mPhoneId);
                return;
            }
        }
        Log.i(this.LOG_TAG, "onRegErrorforImsNotAvailable: ETC error");
        if (!isRefreshReg) {
            this.mCurPcscfIpIdx++;
        }
        if (!isRefreshReg && this.mNumOfPcscfIp >= 2) {
            resetIPSecAllow();
        }
        if (this.mFailureCounter == 2) {
            this.mCurPcscfIpIdx = 0;
            this.mFailureCounter = 0;
            resetIPSecAllow();
            this.mRegMan.stopPdnConnectivity(this.mTask.getPdnType(), this.mTask);
            this.mRegMan.notifyImsNotAvailable(this.mTask, true);
            this.mRegMan.getEventLog().logAndAdd("onRegErrorforImsNotAvailable(ETC)");
        } else if (!isRefreshReg) {
            if (j > 0) {
                startRetryTimer(j);
            } else {
                this.mRegHandler.sendTryRegister(this.mPhoneId);
            }
            Log.i(this.LOG_TAG, "onRegErrorforImsNotAvailable: ETC error. Initial Reg retry");
        } else {
            if (j == 0) {
                j = 1000;
            }
            this.mTask.mKeepPdn = true;
            Log.i(this.LOG_TAG, "onRegErrorforImsNotAvailable: ETC error. Refresh Reg retry with same Call-ID");
            this.mRegHandler.sendUpdateRegistration(this.mTask.getProfile(), this.mPhoneId, j - DEFAULT_RETRY_AFTER_BUFFER_MS.longValue());
        }
        this.mConsecutiveForbiddenCounter = 0;
        this.mIsAkaChallengeTimeout = false;
        this.mDnsQueryCount = 0;
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public void onRegistrationDone() {
        Log.i(this.LOG_TAG, "onRegistrationDone: clear mConsecutiveForbiddenCounter.");
        this.mFailureCounter = 0;
        this.mConsecutiveForbiddenCounter = 0;
        this.mIsAkaChallengeTimeout = false;
        this.mThrottledforImsNotAvailable = false;
        this.mRegiAt = 0L;
        this.mDnsQueryCount = 0;
        stopPDNdisconnectTimer();
        stopRetryTimer();
        stopTimsTimer(RegistrationConstants.REASON_REGISTERED);
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernor, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public void onSubscribeError(int i, SipError sipError) {
        Log.i(this.LOG_TAG, "onSubscribeError: state " + this.mTask.getState() + " error " + sipError + ", event " + i);
        if (i == 0 && sipError.equals(new SipError(Id.REQUEST_IM_SENDMSG, "Subscribe 504 with init-regi"))) {
            Log.e(this.LOG_TAG, "onSubscribeError: SUBSCRIBE 504 with init regi.");
            this.mTask.setDeregiReason(44);
            this.mRegMan.deregister(this.mTask, true, true, "SUBSCRIBE 504 with init regi.");
            this.mFailureCounter = 0;
            this.mRegiAt = 0L;
            this.mConsecutiveForbiddenCounter = 0;
            this.mIsAkaChallengeTimeout = false;
            this.mDnsQueryCount = 0;
            stopPDNdisconnectTimer();
            stopRetryTimer();
        }
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public void onCallStatus(IRegistrationGovernor.CallEvent callEvent, SipError sipError, int i) {
        Log.i(this.LOG_TAG, "onCallStatus: event=" + callEvent + " error=" + sipError);
        if (callEvent == IRegistrationGovernor.CallEvent.EVENT_CALL_LAST_CALL_END && this.mTask.getState() == RegistrationConstants.RegisterTaskState.IDLE) {
            this.mRegHandler.sendTryRegister(this.mPhoneId);
        }
        super.onCallStatus(callEvent, sipError, i);
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public SipError onSipError(String str, SipError sipError) {
        Log.i(this.LOG_TAG, "onSipError: service=" + str + " error=" + sipError);
        if (SipErrorBase.SIP_INVITE_TIMEOUT.equals(sipError)) {
            removeCurrentPcscfAndInitialRegister(true);
            if (needImsNotAvailable()) {
                Log.i(this.LOG_TAG, "onSipError: 709 error. Initial Reg at the next P-CSCF");
                this.mFailureCounter++;
            }
        } else if (SipErrorBase.FORBIDDEN.equals(sipError)) {
            if ("smsip".equals(str) && this.mTask.getMno() == Mno.LGU) {
                return sipError;
            }
            this.mTask.setDeregiReason(43);
            this.mRegMan.deregister(this.mTask, true, true, "403 Forbidden");
        } else {
            ImsProfile profile = this.mTask.getProfile();
            if (SipErrorBase.NOT_ACCEPTABLE.equals(sipError) && (profile.hasService("mmtel") || profile.hasService("mmtel-video"))) {
                Log.e(this.LOG_TAG, "onSipError: 406 error. Ipsec not allow");
                this.mIPsecAllow = false;
                if (this.mTask.getUserAgent() != null) {
                    int deregTimeout = profile.getDeregTimeout(13);
                    Log.i(this.LOG_TAG, "try regsiter after " + deregTimeout);
                    this.mRegHandler.startRegistrationTimer(this.mTask, (long) deregTimeout);
                }
                this.mTask.setDeregiReason(21);
                this.mRegMan.deregister(this.mTask, true, true, "user triggered");
            } else if ("initial_registration".equals(sipError.getReason())) {
                this.mTask.setDeregiReason(43);
                this.mRegMan.deregister(this.mTask, true, true, sipError.getCode() + " Initial Registration");
            } else {
                return super.onSipError(str, sipError);
            }
        }
        return sipError;
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public void onAdsChanged(int i) {
        super.onAdsChanged(i);
        if (this.mTask.isRcsOnly()) {
            return;
        }
        Log.i(this.LOG_TAG, "onAdsChanged: ActiveDataPhoneId[" + i + "] mTask:" + this.mTask.getProfile().getName() + "(" + this.mTask.getState() + ")");
        int oppositeSimSlot = SimUtil.getOppositeSimSlot(i);
        NetworkEvent networkEvent = this.mRegMan.getNetworkEvent(oppositeSimSlot);
        if (networkEvent.isDataRoaming || SimUtil.getPhoneCount() <= 1 || oppositeSimSlot == SimUtil.getActiveDataPhoneId() || !SemSystemProperties.get("ro.boot.hardware", "").contains("qcom") || networkEvent.csOutOfService || networkEvent.voiceNetwork != 3) {
            return;
        }
        this.mTask.setDeregiReason(35);
        this.mTask.setReason("onAdsChanged: VoLTE disabled(qcom non DDS is cs only in 3G)");
        this.mRegMan.tryDeregisterInternal(this.mTask, false, false);
        if (!this.mTask.isOneOf(RegistrationConstants.RegisterTaskState.REGISTERING, RegistrationConstants.RegisterTaskState.REGISTERED, RegistrationConstants.RegisterTaskState.DEREGISTERING)) {
            this.mRegHandler.sendDisconnectPdnByVolteDisabled(this.mTask, 0L);
        }
        Log.i(this.LOG_TAG, "onAdsChanged: VoLTE disabled(qcom non DDS is cs only in 3G)");
    }

    private long getActualWaitTime() {
        return (long) (getWaitTime() * ((Math.random() * 0.5d) + 0.5d));
    }

    long getActualWaitTimeForUnlimited404() {
        long pow = this.mRegBaseTimeMs * ((long) Math.pow(2.0d, this.mFailureCounter));
        return pow < 0 ? REG_RETRY_MAX_TIME_FOR_UNLIMITED_404_MS : Math.min(REG_RETRY_MAX_TIME_FOR_UNLIMITED_404_MS, pow);
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase
    protected long getWaitTime() {
        long pow = this.mRegBaseTimeMs * ((long) Math.pow(2.0d, this.mFailureCounter));
        if (pow < 0) {
            return this.mRegMaxTimeMs;
        }
        return Math.min(this.mRegMaxTimeMs, pow);
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase
    protected void removeCurrentPcscfAndInitialRegister(boolean z) {
        Log.i(this.LOG_TAG, "removeCurrentPcscfAndInitialRegister()");
        this.mMoveToNextPcscfAfterTimerB = true;
        resetIPSecAllow();
        String moveToNextPcscfIp = moveToNextPcscfIp();
        updatePcscfIpList(this.mPcscfIpList, z);
        Log.i(this.LOG_TAG, "removeCurrentPcscfAndInitialRegister(): nextPcscfIp " + moveToNextPcscfIp + " mNumOfPcscfIp " + this.mNumOfPcscfIp + " mCurPcscfIpIdx " + this.mCurPcscfIpIdx + " mPcscfIpList " + this.mPcscfIpList);
    }

    private void moveToNextPcscfAndInitialRegister() {
        Log.i(this.LOG_TAG, "moveToNextPcscfAndInitialRegister()");
        resetIPSecAllow();
        String moveToNextPcscfIp = moveToNextPcscfIp();
        if (this.mPcscfIpList == null) {
            Log.e(this.LOG_TAG, "moveToNextPcscfAndInitialRegister: null P-CSCF list!");
            return;
        }
        boolean z = this.mNumOfPcscfIp > 0;
        this.mIsValid = z;
        if (this.mCurPcscfIpIdx >= 0 && z) {
            Log.i(this.LOG_TAG, "moveToNextPcscfAndInitialRegister: forceInitialRegi");
            this.mFailureCounter = 0;
            this.mCurImpu = 0;
            this.mRegiAt = 0L;
            if (this.mTask.isOneOf(RegistrationConstants.RegisterTaskState.REGISTERING, RegistrationConstants.RegisterTaskState.REGISTERED)) {
                this.mTask.setDeregiReason(8);
                this.mRegMan.deregister(this.mTask, true, this.mIsValid, "pcscf updated");
            }
        }
        Log.i(this.LOG_TAG, "moveToNextPcscfAndInitialRegister(): nextPcscfIp " + moveToNextPcscfIp + " mNumOfPcscfIp " + this.mNumOfPcscfIp + " mCurPcscfIpIdx " + this.mCurPcscfIpIdx + " mPcscfIpList " + this.mPcscfIpList);
    }

    protected String moveToNextPcscfIp() {
        Log.i(this.LOG_TAG, "moveToNextPcscfIp: mCurPcscfIpIdx = " + this.mCurPcscfIpIdx + " mPcscfIpList = " + this.mPcscfIpList);
        List<String> list = this.mPcscfIpList;
        if (list == null || list.isEmpty()) {
            Log.e(this.LOG_TAG, "moveToNextPcscfIp: empty P-CSCF list.");
            return "";
        }
        int size = (this.mCurPcscfIpIdx + 1) % this.mPcscfIpList.size();
        this.mCurPcscfIpIdx = size;
        return this.mPcscfIpList.get(size);
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernor, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public boolean isThrottled() {
        return super.isThrottled();
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernor, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public void checkProfileUpdateFromDM(boolean z) {
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        if (this.mTask.getProfile().hasService("mmtel") || this.mTask.getProfile().hasService("mmtel-video")) {
            Log.i(this.LOG_TAG, "checkProfileUpdateFromDM: force=" + z + " pcscf_pref=" + this.mTask.getProfile().getPcscfPreference());
            if (this.mMno == Mno.KT && "20".equals(this.mTelephonyManager.semGetTelephonyProperty(this.mPhoneId, "ril.simtype", ""))) {
                Log.i(this.LOG_TAG, "checkProfileUpdateFromDM : KT_unreg SIM");
                z2 = true;
            } else {
                z2 = false;
            }
            if (this.mMno == Mno.LGU && "18".equals(this.mTelephonyManager.semGetTelephonyProperty(this.mPhoneId, "ril.simtype", ""))) {
                Log.i(this.LOG_TAG, "checkProfileUpdateFromDM : LGT_unreg SIM");
                z3 = true;
            } else {
                z3 = false;
            }
            ImsProfile profile = DmProfileLoader.getProfile(this.mContext, this.mTask.getProfile(), this.mPhoneId);
            this.mRegBaseTimeMs = profile.getRegRetryBaseTime() * 1000;
            this.mRegMaxTimeMs = profile.getRegRetryMaxTime() * 1000;
            if (isNeedForcibleSmsOverImsOn()) {
                NvConfiguration.setSmsIpNetworkIndi(this.mContext, true, this.mPhoneId);
                profile.setSupportSmsOverIms(true);
                this.mTask.setProfile(profile);
                Log.e(this.LOG_TAG, "checkProfileUpdateFromDM: SmsOverIms is false. set it as true forcibly");
                this.mRegMan.getEventLog().logAndAdd("checkProfileUpdateFromDM : SmsOverIms is false. set it as true forcibly");
            }
            if (this.mTask.isNeedOmadmConfig() || z2 || z3) {
                if (this.mRegMan.hasOmaDmFinished(this.mPhoneId) || z2 || z3) {
                    Log.i(this.LOG_TAG, "checkProfileUpdateFromDM()");
                    if (this.mMno == Mno.LGU && z3) {
                        ArrayList arrayList = new ArrayList();
                        profile.setPcscfPreference(0);
                        profile.setPcscfList(arrayList);
                        this.mTask.setProfile(profile);
                        Log.i(this.LOG_TAG, "checkProfileUpdateFromDM: LGTUnregSIM PCO");
                    }
                    if (this.mDmUpdatedFlag && !z) {
                        Log.i(this.LOG_TAG, "mDmUpdatedFlag true");
                        return;
                    }
                    if (this.mMno == Mno.KT && profile.getPcscfPreference() == 0) {
                        ArrayList arrayList2 = new ArrayList();
                        profile.setPcscfPreference(0);
                        profile.setPcscfList(arrayList2);
                        Log.i(this.LOG_TAG, "[KT 5G] P-CSCF discovery PCO>DM>DEFAULT pcscf: " + arrayList2);
                    } else {
                        List<String> lboPcscfAddressList = profile.getLboPcscfAddressList();
                        if (lboPcscfAddressList != null && lboPcscfAddressList.size() > 0) {
                            for (String str : lboPcscfAddressList) {
                                if (Patterns.DOMAIN_NAME.matcher(str).matches() || NetworkUtil.isIPv4Address(str) || NetworkUtil.isIPv6Address(str)) {
                                    Log.i(this.LOG_TAG, "DM pcscf is valid : " + str);
                                    z4 = true;
                                    break;
                                }
                            }
                        }
                        z4 = false;
                        if (lboPcscfAddressList != null && lboPcscfAddressList.size() > 0 && z4) {
                            int lboPcscfPort = profile.getLboPcscfPort();
                            profile.setPcscfPreference(5);
                            profile.setPcscfList(lboPcscfAddressList);
                            if (lboPcscfPort > 0) {
                                profile.setSipPort(lboPcscfPort);
                                Log.i(this.LOG_TAG, "DM updated lbo pcscf port found : " + lboPcscfPort);
                            }
                        } else {
                            List<String> arrayList3 = new ArrayList<>();
                            Mno mno = this.mMno;
                            if (mno == Mno.KT) {
                                arrayList3 = getPcscfFromFile(mno);
                                Log.i(this.LOG_TAG, "DM pcscf is empty. [KT 5G] P-CSCF discovery PCO>DM>DEFAULT pcscfList: " + arrayList3);
                            } else {
                                Log.i(this.LOG_TAG, "DM pcscf is empty");
                                profile.setPcscfPreference(0);
                            }
                            profile.setPcscfList(arrayList3);
                        }
                    }
                    if (profile.isSupportSmsOverIms()) {
                        Log.i(this.LOG_TAG, "SMS over IMS is enabled by OMADM");
                        for (Integer num : profile.getNetworkSet()) {
                            Set serviceSet = profile.getServiceSet(num);
                            Iterator it = serviceSet.iterator();
                            while (true) {
                                if (it.hasNext()) {
                                    if ("smsip".equals((String) it.next())) {
                                        z5 = true;
                                        break;
                                    }
                                } else {
                                    z5 = false;
                                    break;
                                }
                            }
                            if (!z5) {
                                serviceSet.add("smsip");
                                profile.setServiceSet(num.intValue(), serviceSet);
                            }
                        }
                    }
                    if (profile.getDmPollingPeriod() > 0) {
                        Log.i(this.LOG_TAG, "DmPollingPeriod : " + profile.getDmPollingPeriod());
                        startDmPollingTimer(profile.getDmPollingPeriod());
                    }
                    checkDMConfigChange(profile);
                    this.mTask.setProfile(profile);
                    this.mDmUpdatedFlag = true;
                    return;
                }
                if (OmcCode.isKOROmcCode()) {
                    if (this.mRegMan.getNetworkEvent(this.mPhoneId).isDataRoaming) {
                        Log.i(this.LOG_TAG, "Roaming, so use PCO");
                        ArrayList arrayList4 = new ArrayList();
                        profile.setPcscfPreference(0);
                        profile.setPcscfList(arrayList4);
                        this.mTask.setProfile(profile);
                        return;
                    }
                    Log.i(this.LOG_TAG, "not Roaming");
                    return;
                }
                Log.i(this.LOG_TAG, "oversea device and KOR SIM, so use PCO");
                ArrayList arrayList5 = new ArrayList();
                profile.setPcscfPreference(0);
                profile.setPcscfList(arrayList5);
                this.mTask.setProfile(profile);
            }
        }
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public void updatePcscfIpList(List<String> list) {
        boolean z;
        if (list == null) {
            Log.e(this.LOG_TAG, "updatePcscfIpList: null P-CSCF list!");
            return;
        }
        ArrayList arrayList = new ArrayList();
        LinkPropertiesWrapper linkProperties = this.mPdnController.getLinkProperties(this.mTask);
        if (linkProperties == null) {
            Log.e(this.LOG_TAG, "updatePcscfIpList: null LinkProperties");
            this.mIsValid = false;
            return;
        }
        Iterator<String> it = list.iterator();
        while (true) {
            if (it.hasNext()) {
                if (NetworkUtil.isIPv4Address(it.next())) {
                    z = true;
                    break;
                }
            } else {
                z = false;
                break;
            }
        }
        int i = (linkProperties.hasGlobalIPv6Address() || linkProperties.hasIPv6DefaultRoute()) ? 2 : 1;
        int ipVer = this.mTask.getProfile().getIpVer();
        if (this.mTask.isRcsOnly() && z) {
            Log.i(this.LOG_TAG, "updatePcscfIpList: value ipv4 addr above ipv6 addr for RCS");
            i = linkProperties.hasIPv4Address() ? 1 : 2;
        }
        Log.i(this.LOG_TAG, "updatePcscfIpList: localIpType=" + i + ", profileIpType=" + ipVer);
        for (int i2 = 0; i2 < list.size(); i2++) {
            if (ipVer != 1) {
                if (ipVer != 2) {
                    if (ipVer == 3) {
                        if (i == 1) {
                            if (NetworkUtil.isIPv4Address(list.get(i2))) {
                                arrayList.add(list.get(i2));
                            }
                        } else if (NetworkUtil.isIPv6Address(list.get(i2))) {
                            arrayList.add(list.get(i2));
                        }
                    }
                } else if (NetworkUtil.isIPv6Address(list.get(i2))) {
                    arrayList.add(list.get(i2));
                }
            } else if (NetworkUtil.isIPv4Address(list.get(i2))) {
                arrayList.add(list.get(i2));
            }
        }
        Log.i(this.LOG_TAG, "updatePcscfIpList tmpPcscfIpList = " + arrayList);
        super.updatePcscfIpList(arrayList);
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernor, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public void checkAcsPcscfListChange() {
        if (this.mTask.isRcsOnly()) {
            ArrayList arrayList = new ArrayList();
            String readStringParam = RcsConfigurationHelper.readStringParam(this.mContext, "address", null);
            if (readStringParam == null) {
                Log.i(this.LOG_TAG, "checkAcsPcscfIpListChange : lboPcscfAddress is null");
                return;
            }
            arrayList.add(readStringParam);
            Log.i(this.LOG_TAG, "checkAcsPcscfIpListChange : previous pcscf = " + this.mRcsPcscfList + ", new pcscf = " + arrayList);
            if (arrayList.equals(this.mRcsPcscfList)) {
                return;
            }
            resetPcscfList();
            ArrayList arrayList2 = new ArrayList();
            this.mRcsPcscfList = arrayList2;
            arrayList2.add(readStringParam);
            if (this.mTask.isOneOf(RegistrationConstants.RegisterTaskState.REGISTERING, RegistrationConstants.RegisterTaskState.REGISTERED)) {
                this.mTask.setDeregiReason(8);
                this.mRegMan.deregister(this.mTask, true, true, "checkAcsPcscfIpListChange : pcscf updated");
                this.mRegHandler.sendTryRegister(this.mPhoneId);
            }
            Log.i(this.LOG_TAG, "checkAcsPcscfIpListChange : resetPcscfList");
        }
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernor, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public void notifyReattachToRil() {
        Log.i(this.LOG_TAG, "notifyReattachToRil");
        sendRawRequestToTelephony(this.mContext, buildReattachNotiOemHookCmd());
    }

    private void startDmPollingTimer(int i) {
        if (this.mDmPollingTimer != null) {
            stopPollingTimer();
        }
        Log.i(this.LOG_TAG, "startDmPollingTimer: Timer " + i + " sec");
        this.mDmPollingTimer = this.mRegHandler.startDmConfigTimer(this.mTask, ((long) i) * 1000);
    }

    void stopPollingTimer() {
        if (this.mDmPollingTimer == null) {
            return;
        }
        Log.i(this.LOG_TAG, "stopPollingTimer");
        this.mRegHandler.stopTimer(this.mDmPollingTimer);
        this.mDmPollingTimer = null;
    }

    public byte[] buildReattachNotiOemHookCmd() {
        return new byte[]{9, 11, 0, 4};
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public Set<String> filterService(Set<String> set, int i) {
        if (isImsDisabled()) {
            return new HashSet();
        }
        Set<String> hashSet = new HashSet<>();
        HashSet hashSet2 = set != null ? new HashSet(set) : new HashSet();
        if (!this.mTask.isRcsOnly()) {
            NetworkEvent networkEvent = this.mRegMan.getNetworkEvent(this.mPhoneId);
            if (networkEvent.isDataRoaming) {
                if (!NetworkUtil.is3gppPsVoiceNetwork(networkEvent.network) || networkEvent.voiceOverPs != VoPsIndication.SUPPORTED) {
                    Log.i(this.LOG_TAG, "filterService: NW is not LTE/NR or VoPS is not supported in roaming");
                    this.mTask.setRegiFailReason(DiagnosisConstants.REGI_FRSN.VOPS_OFF.getCode());
                    return new HashSet();
                }
            } else if (SimUtil.getActiveSubInfoCount() > 1 && this.mPhoneId != SimUtil.getActiveDataPhoneId() && SemSystemProperties.get("ro.boot.hardware", "").contains("qcom") && !networkEvent.csOutOfService && networkEvent.voiceNetwork == 3) {
                Log.i(this.LOG_TAG, "filterService: QC non DDS is CS only in 3G");
                this.mTask.setRegiFailReason(DiagnosisConstants.REGI_FRSN.NON_DDS_CS_ONLY_IN_3G.getCode());
                return new HashSet();
            }
            boolean z = false;
            if (DmConfigHelper.getImsSwitchValue(this.mContext, "volte", this.mPhoneId) == 1) {
                hashSet.addAll(servicesByImsSwitch(ImsProfile.getVoLteServiceList()));
                if (!hashSet.contains("mmtel")) {
                    this.mTask.setRegiFailReason(DiagnosisConstants.REGI_FRSN.NO_MMTEL_IMS_SWITCH_OFF.getCode());
                }
            }
            hashSet = applyMmtelUserSettings(hashSet, i);
            ImsProfile profile = DmProfileLoader.getProfile(this.mContext, this.mTask.getProfile(), this.mPhoneId);
            if (networkEvent.isDataRoaming && this.mMno == Mno.KT && OmcCode.isKTTOmcCode()) {
                z = true;
            }
            Log.i(this.LOG_TAG, "filterService: isKTInRoaming: " + z);
            if (!profile.isSupportSmsOverIms() && !z) {
                removeService(hashSet, "smsip", "isSupportSmsOverIms disabled");
            }
        }
        if (this.mTask.isRcsOnly()) {
            if (this.mConfigModule.isValidAcsVersion(this.mPhoneId)) {
                hashSet.addAll(servicesByImsSwitch(ImsProfile.getRcsServiceList()));
            }
            if (this.mTask.getProfile().getSupportRcsAcrossSalesCode() && SimUtil.getActiveSubInfoCount() > 1 && !RcsUtils.DualRcs.isRegAllowed(this.mContext, this.mPhoneId)) {
                Log.i(this.LOG_TAG, "filterService: RCS is not supported for non DDS");
                this.mTask.setRegiFailReason(DiagnosisConstants.REGI_FRSN.RCS_NOT_DDS.getCode());
                return new HashSet();
            }
            UserManager userManager = (UserManager) this.mContext.getSystemService("user");
            if (userManager != null && UserManager.supportsMultipleUsers()) {
                int currentUser = Extensions.ActivityManager.getCurrentUser();
                if (userManager.hasUserRestriction("no_sms", UserHandle.of(currentUser))) {
                    Log.i(this.LOG_TAG, "filterService: RCS is not supported for MUM with DISALLOW_SMS (" + currentUser + "|" + Extensions.UserHandle.myUserId() + ")");
                    this.mTask.setRegiFailReason(DiagnosisConstants.REGI_FRSN.RCS_MUM_DISALLOW_SMS.getCode());
                    return new HashSet();
                }
            }
        }
        if (!hashSet2.isEmpty()) {
            hashSet2.retainAll(hashSet);
        }
        Log.i(this.LOG_TAG, "filterService : filteredServices = " + hashSet2);
        return hashSet2;
    }

    boolean checkOtaStatus() {
        if (this.mMno != Mno.SKT || !CloudMessageProviderContract.JsonData.TRUE.equals(SemSystemProperties.get("ril.domesticOtaStart"))) {
            return true;
        }
        Log.i(this.LOG_TAG, "isReadyToRegister : OTA is working, don't try register");
        this.mTask.setRegiFailReason(DiagnosisConstants.REGI_FRSN.ONGOING_OTA.getCode());
        return false;
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase
    protected boolean checkRegiStatus() {
        RegisterTask registerTask = this.mTask;
        if (registerTask.mIsUpdateRegistering || registerTask.getState() == RegistrationConstants.RegisterTaskState.REGISTERING) {
            return false;
        }
        if (this.mTask.getState() != RegistrationConstants.RegisterTaskState.CONNECTED || this.mPDNdisconnectTimeout == null || this.mTask.isRcsOnly()) {
            return true;
        }
        Log.i(this.LOG_TAG, "isReadyToRegister: mPDNdisconnectTimeout is not null");
        return false;
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernor
    protected boolean checkRcsEvent(int i) {
        if (this.mTask.isRcsOnly()) {
            boolean z = ImsConstants.SystemSettings.getRcsUserSetting(this.mContext, -1, this.mPhoneId) == 1;
            if (RcsConfigurationHelper.readIntParam(this.mContext, "version", 0).intValue() <= 0 && !z) {
                Log.i(this.LOG_TAG, "isReadyToRegister: User don't try RCS service yet");
                return false;
            }
            if (DmConfigHelper.getImsSwitchValue(this.mContext, DeviceConfigManager.DEFAULTMSGAPPINUSE, this.mPhoneId) != 1) {
                Log.i(this.LOG_TAG, "isReadyToRegister: Default MSG app isn't used for RCS");
                return false;
            }
            ISimManager simManagerFromSimSlot = SimManagerFactory.getSimManagerFromSimSlot(this.mPhoneId);
            if (simManagerFromSimSlot != null) {
                String str = "IMSI_" + simManagerFromSimSlot.getImsi();
                if (TextUtils.isEmpty(simManagerFromSimSlot.getMsisdn()) && TextUtils.isEmpty(ImsSharedPrefHelper.getString(this.mPhoneId, this.mContext, IConfigModule.MSISDN_FROM_PAU, str, ""))) {
                    Log.i(this.LOG_TAG, "isReadyToRegister: MSISDN is null, try to RCS ACS after registered VoLTE");
                    IMSLog.c(LogClass.KOR_PENDING_RCS, this.mPhoneId + "PENDING RCS");
                    return false;
                }
            }
        }
        return true;
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase
    protected boolean checkCallStatus() {
        if (!this.mTask.getProfile().getPdn().equals(DeviceConfigManager.IMS)) {
            return true;
        }
        int telephonyCallStatus = this.mRegMan.getTelephonyCallStatus(this.mTask.getPhoneId());
        Log.i(this.LOG_TAG, "isReadyToRegister : getTelephonyCallStatus is " + telephonyCallStatus);
        return telephonyCallStatus == 0 || telephonyCallStatus == 2;
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public boolean isReadyToRegister(int i) {
        return checkEmergencyStatus() || (checkOtaStatus() && i != 0 && checkRegiStatus() && checkRcsEvent(i) && checkCallStatus());
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public void releaseThrottle(int i) {
        if ((this.mIsAkaChallengeTimeout && (i == 1 || i == 5)) || ((this.mThrottledforImsNotAvailable && (i == 9 || i == 1)) || i == 4 || i == 10)) {
            this.mIsPermanentStopped = false;
            this.mThrottledforImsNotAvailable = false;
            resetIPSecAllow();
            this.mCurImpu = 0;
        } else if (i == 1) {
            resetRetry();
            resetAllRetryFlow();
        }
        if (this.mIsPermanentStopped) {
            return;
        }
        this.mRegMan.getEventLog().logAndAdd("releaseThrottle: case by " + i);
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public void onPdnRequestFailed(PdnFailReason pdnFailReason, int i) {
        super.onPdnRequestFailed(pdnFailReason, i);
        if (isMatchedPdnFailReason(pdnFailReason)) {
            this.mRegMan.getEventLog().logAndAdd(pdnFailReason + ": Release throttle.");
            releaseThrottle(10);
            this.mIsReadyToGetReattach = false;
        }
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernor, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public void startTimsTimer(String str) {
        Log.i(this.LOG_TAG, "startTimsTimer : " + this.mTask.getProfile().getName() + "(" + this.mTask.getState() + ") Pdn(" + this.mTask.getPdnType() + "," + this.mPdnController.isConnected(this.mTask.getPdnType(), this.mTask) + ")");
        if (RegistrationUtils.isCmcProfile(this.mTask.getProfile())) {
            return;
        }
        if (!this.mTask.isRcsOnly()) {
            if (needImsNotAvailable()) {
                if (SlotBasedConfig.getInstance(this.mPhoneId).isNotifiedImsNotAvailable()) {
                    return;
                }
                startTimsEstablishTimer(this.mTask, 60000L, str);
                return;
            } else {
                int i = this.mRequestPdnTimeoutCount;
                if (i < 5) {
                    this.mRequestPdnTimeoutCount = i + 1;
                    startTimsEstablishTimer(this.mTask, 180000L, str);
                    return;
                }
                return;
            }
        }
        if (isMobilePreferredForRcs()) {
            PdnController pdnController = this.mPdnController;
            int translateNetworkBearer = pdnController.translateNetworkBearer(pdnController.getDefaultNetworkBearer());
            if (this.mTask.getPdnType() == 0 && NetworkUtil.isMobileDataOn(this.mContext) && NetworkUtil.isMobileDataPressed(this.mContext) && ImsConstants.SystemSettings.AIRPLANE_MODE.get(this.mContext, 0) != ImsConstants.SystemSettings.AIRPLANE_MODE_ON && !this.mRegMan.getNetworkEvent(this.mTask.getPhoneId()).outOfService && translateNetworkBearer == 1 && !this.mPdnController.isConnected(this.mTask.getPdnType(), this.mTask)) {
                startTimsEstablishTimer(this.mTask, 30000L, str);
                return;
            }
            int i2 = this.mRequestPdnTimeoutCount;
            if (i2 < 5) {
                this.mRequestPdnTimeoutCount = i2 + 1;
                startTimsEstablishTimer(this.mTask, 180000L, str);
            }
        }
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernor, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public void stopTimsTimer(String str) {
        Log.i(this.LOG_TAG, "stopTimsTimer : " + this.mTask.getProfile().getName() + "(" + this.mTask.getState() + ") Pdn(" + this.mTask.getPdnType() + "," + this.mPdnController.isConnected(this.mTask.getPdnType(), this.mTask) + ")");
        if (RegistrationUtils.isCmcProfile(this.mTask.getProfile())) {
            return;
        }
        if (this.mTask.isRcsOnly()) {
            this.mHasNetworkFailure = false;
        }
        stopTimsEstablishTimer(this.mTask, str);
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public void onTimsTimerExpired() {
        String str;
        Log.i(this.LOG_TAG, "onTimsTimerExpired : " + this.mTask.getProfile().getName() + "(" + this.mTask.getState() + ") Pdn(" + this.mTask.getPdnType() + "," + this.mPdnController.isConnected(this.mTask.getPdnType(), this.mTask) + ")");
        boolean needImsNotAvailable = needImsNotAvailable();
        if (RegistrationUtils.isCmcProfile(this.mTask.getProfile())) {
            return;
        }
        if (!this.mTask.isRcsOnly()) {
            SimpleEventLog eventLog = this.mRegMan.getEventLog();
            StringBuilder sb = new StringBuilder();
            sb.append("onTimsTimerExpired. ");
            sb.append(needImsNotAvailable);
            if (needImsNotAvailable) {
                str = "";
            } else {
                str = ",Count is " + this.mRequestPdnTimeoutCount;
            }
            sb.append(str);
            eventLog.logAndAdd(sb.toString());
            if (needImsNotAvailable) {
                super.onTimsTimerExpired();
                return;
            } else {
                stopTimsEstablishTimer(this.mTask, RegistrationConstants.REASON_TIMS_EXPIRED);
                deregisterIfConnecting(13);
                return;
            }
        }
        if (isMobilePreferredForRcs()) {
            this.mRegMan.getEventLog().logAndAdd("onTimsTimerExpired for RCS. Count is " + this.mRequestPdnTimeoutCount);
            stopTimsEstablishTimer(this.mTask, RegistrationConstants.REASON_TIMS_EXPIRED);
            this.mHasNetworkFailure = true;
            deregisterIfConnecting(13);
        }
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase
    protected int getVoiceTechType() {
        Mno mno = this.mMno;
        if (mno == Mno.LGU || (mno == Mno.KT && this.mIsShipBuild)) {
            Log.i(this.LOG_TAG, "getVoiceTechType : LGU device or KT ship device have to enable VOLTE always, regardless of DB");
            forceTurnOnVoLte();
            return 0;
        }
        if (mno == Mno.SKT && this.mIsShipBuild) {
            String str = (this.mPhoneId == ImsConstants.Phone.SLOT_1 ? ImsConstants.SystemSettings.VOLTE_SLOT1 : ImsConstants.SystemSettings.VOLTE_SLOT2).getPackage();
            if (!Arrays.asList(this.allowedPackages).contains(str)) {
                Log.i(this.LOG_TAG, "getVoiceTechType : modifier pkg:" + str);
                forceTurnOnVoLte();
                return 0;
            }
        } else if (ImsConstants.SystemSettings.getVoiceCallType(this.mContext, -1, this.mPhoneId) == -1) {
            Log.i(this.LOG_TAG, "getVoiceTechType : voicecall_type is corrupted. recover it");
            ImsConstants.SystemSettings.setVoiceCallType(this.mContext, 0, this.mPhoneId);
        }
        return super.getVoiceTechType();
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernor, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public boolean isIPSecAllow() {
        if (!OmcCode.isKOROmcCode() && this.mMno == Mno.SKT) {
            Log.i(this.LOG_TAG, "isIPSecAllow : oversea device and SKT sim. do not use IPSec");
            return false;
        }
        if (this.mRegMan.getNetworkEvent(this.mPhoneId).isDataRoaming) {
            return false;
        }
        return this.mIPsecAllow;
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase
    public String toString() {
        return "RegistrationGovernorKor [mRegBaseTimeMs=" + this.mRegBaseTimeMs + ", mDmUpdatedFlag=" + this.mDmUpdatedFlag + ", mConsecutiveForbiddenCounter=" + this.mConsecutiveForbiddenCounter + ", mHasPendingInitRegistrationByDMConfigChange=" + this.mHasPendingInitRegistrationByDMConfigChange + ", mIsAkaChallengeTimeout=" + this.mIsAkaChallengeTimeout + ", mHasPendingNotifyImsNotAvailable=" + this.mHasPendingNotifyImsNotAvailable + ", pcscf_pref " + this.mTask.getProfile().getPcscfPreference() + "] " + super.toString();
    }

    private boolean isVolteEnabled() {
        return isVolteSettingEnabled() && getVolteServiceStatus() && isLTEDataModeEnabled();
    }

    void setOldVolteServiceStatus(boolean z) {
        Log.i(this.LOG_TAG, "setOldVolteServiceStatus : " + z);
        this.mVolteServiceStatus = z;
    }

    private boolean getVolteServiceStatus() {
        boolean isVolteServiceStatus = DmProfileLoader.getProfile(this.mContext, this.mTask.getProfile(), this.mPhoneId).isVolteServiceStatus();
        Log.i(this.LOG_TAG, "getVolteServiceStatus : " + isVolteServiceStatus);
        return isVolteServiceStatus;
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernor, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public boolean needImsNotAvailable() {
        boolean z = this.mMno == Mno.LGU && this.mRegMan.getNetworkEvent(this.mPhoneId).isDataRoaming;
        boolean supportImsNotAvailable = this.mTask.getProfile().getSupportImsNotAvailable();
        boolean z2 = (this.mTask.isRcsOnly() || RegistrationUtils.isCmcProfile(this.mTask.getProfile())) ? false : true;
        Log.i(this.LOG_TAG, "isLGUInVoLTERoaming : " + z + " isImsNotAvailableSupported : " + supportImsNotAvailable + " isVoLTEOnly : " + z2);
        return z && supportImsNotAvailable && z2;
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernor, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public boolean hasNetworkFailure() {
        return this.mHasNetworkFailure;
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernor, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public boolean isMobilePreferredForRcs() {
        return this.mTask.isRcsOnly() && !this.mRegMan.getNetworkEvent(this.mTask.getPhoneId()).isDataRoaming;
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernor, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public void onVolteSettingChanged() {
        Log.i(this.LOG_TAG, "onVolteSettingChanged ");
        if (this.mTask.isRcsOnly()) {
            return;
        }
        checkVoLTEStatusChanged(VoltePreferenceChangedReason.VOLTE_SETTING);
    }

    protected boolean isVolteSettingEnabled() {
        return getVoiceTechType() == 0;
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernor, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public void onLteDataNetworkModeSettingChanged(boolean z) {
        Log.i(this.LOG_TAG, "onLteDataNetworkModeSettingChanged : " + z);
        if (this.mTask.isRcsOnly()) {
            return;
        }
        if (!z && SimUtil.getActiveSubInfoCount() > 1 && this.mPhoneId != SimUtil.getActiveDataPhoneId() && SemSystemProperties.get("ro.boot.hardware", "").contains("qcom")) {
            this.mTask.setDeregiReason(31);
            this.mTask.setReason("onLteDataNetworkModeSettingChanged: VoLTE disabled(qcom non DDS is cs only in 3G)");
            this.mRegMan.tryDeregisterInternal(this.mTask, false, false);
            Log.i(this.LOG_TAG, "onLteDataNetworkModeSettingChanged: VoLTE disabled(qcom non DDS is cs only in 3G)");
            return;
        }
        checkVoLTEStatusChanged(VoltePreferenceChangedReason.LTE_MODE);
    }

    public void onPreferredNetworkModeChanged() {
        Log.i(this.LOG_TAG, "onPreferredNetworkModeChanged mLteModeOn: " + this.mLteModeOn);
        this.mRegHandler.notifyLteDataNetworkModeSettingChanged(this.mLteModeOn, this.mPhoneId);
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernor, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public void registerAllowedNetworkTypesListener() {
        Mno mno;
        this.mSubId = SimUtil.getSubId(this.mPhoneId);
        unregisterAllowedNetworkTypesListener();
        if (!OmcCode.isKOROmcCode() || (mno = this.mMno) == Mno.KT || mno == Mno.LGU) {
            return;
        }
        if (!SubscriptionManager.isValidSubscriptionId(this.mSubId)) {
            Log.i(this.LOG_TAG, "registerAllowedNetworkTypesListener : not ValidSubscriptionId");
            return;
        }
        TelephonyManager createForSubscriptionId = ((TelephonyManager) this.mContext.getSystemService(TelephonyManager.class)).createForSubscriptionId(this.mSubId);
        if (createForSubscriptionId == null) {
            Log.i(this.LOG_TAG, "registerAllowedNetworkTypesListener : TelephonyManager null");
            return;
        }
        if (this.mAllowedNetworkTypesListener == null) {
            Log.i(this.LOG_TAG, "registerAllowedNetworkTypesListener : AllowedNetworkTypesListener null");
            this.mAllowedNetworkTypesListener = new AllowedNetworkTypesListener();
        }
        createForSubscriptionId.registerTelephonyCallback(this.mContext.getMainExecutor(), this.mAllowedNetworkTypesListener);
        this.mAllowedNetworkType = createForSubscriptionId.getAllowedNetworkTypesBitmask();
        Log.i(this.LOG_TAG, "registerAllowedNetworkTypesListener : " + this.mAllowedNetworkType + " " + this.mAllowedNetworkTypesListener);
    }

    void unregisterAllowedNetworkTypesListener() {
        if (this.mAllowedNetworkTypesListener == null) {
            return;
        }
        TelephonyManager createForSubscriptionId = ((TelephonyManager) this.mContext.getSystemService(TelephonyManager.class)).createForSubscriptionId(this.mSubId);
        if (createForSubscriptionId == null) {
            Log.i(this.LOG_TAG, "unregisterAllowedNetworkTypesListener : TelephonyManager null");
        } else {
            createForSubscriptionId.unregisterTelephonyCallback(this.mAllowedNetworkTypesListener);
            this.mAllowedNetworkTypesListener = null;
        }
    }

    public class AllowedNetworkTypesListener extends TelephonyCallback implements TelephonyCallback.AllowedNetworkTypesListener {
        public AllowedNetworkTypesListener() {
        }

        public void onAllowedNetworkTypesChanged(int i, long j) {
            RegistrationGovernorKor registrationGovernorKor;
            Mno mno;
            if (!OmcCode.isKOROmcCode() || (mno = (registrationGovernorKor = RegistrationGovernorKor.this).mMno) == Mno.KT || mno == Mno.LGU) {
                return;
            }
            Log.i(registrationGovernorKor.LOG_TAG, "onAllowedNetworkTypesChanged : prev= " + RegistrationGovernorKor.this.mAllowedNetworkType + " new= " + j);
            if (RegistrationGovernorKor.this.mAllowedNetworkType != j) {
                RegistrationGovernorKor.this.mAllowedNetworkType = j;
                RegistrationGovernorKor.this.handleAllowedNetworkTypeChanged();
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x008a  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0099  */
    /* JADX WARN: Removed duplicated region for block: B:25:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x008c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected boolean isLTEDataModeInternal() {
        /*
            r9 = this;
            r0 = 0
            r1 = 1
            int r2 = r9.mPhoneId     // Catch: java.lang.Exception -> L6a
            int r2 = com.sec.internal.helper.SimUtil.getSubId(r2)     // Catch: java.lang.Exception -> L6a
            android.content.Context r3 = r9.mContext     // Catch: java.lang.Exception -> L6a
            java.lang.Class<android.telephony.TelephonyManager> r4 = android.telephony.TelephonyManager.class
            java.lang.Object r3 = r3.getSystemService(r4)     // Catch: java.lang.Exception -> L6a
            android.telephony.TelephonyManager r3 = (android.telephony.TelephonyManager) r3     // Catch: java.lang.Exception -> L6a
            android.telephony.TelephonyManager r3 = r3.createForSubscriptionId(r2)     // Catch: java.lang.Exception -> L6a
            if (r3 != 0) goto L20
            java.lang.String r2 = r9.LOG_TAG     // Catch: java.lang.Exception -> L6a
            java.lang.String r3 = "isLTEDataModeInternal : TelephonyManager null"
            android.util.Log.i(r2, r3)     // Catch: java.lang.Exception -> L6a
            return r1
        L20:
            long r4 = r3.getAllowedNetworkTypesForReason(r0)     // Catch: java.lang.Exception -> L6a
            long r6 = r3.getAllowedNetworkTypesForReason(r1)     // Catch: java.lang.Exception -> L6a
            long r4 = r4 & r6
            r6 = 2
            long r7 = r3.getAllowedNetworkTypesForReason(r6)     // Catch: java.lang.Exception -> L6a
            long r4 = r4 & r7
            r7 = 3
            long r7 = r3.getAllowedNetworkTypesForReason(r7)     // Catch: java.lang.Exception -> L6a
            long r3 = r4 & r7
            int r3 = (int) r3     // Catch: java.lang.Exception -> L6a
            int r3 = android.telephony.RadioAccessFamily.getNetworkTypeFromRaf(r3)     // Catch: java.lang.Exception -> L6a
            if (r3 == 0) goto L4a
            if (r3 == r6) goto L4a
            r4 = 14
            if (r3 == r4) goto L4a
            r4 = 18
            if (r3 != r4) goto L48
            goto L4a
        L48:
            r4 = r1
            goto L4b
        L4a:
            r4 = r0
        L4b:
            java.lang.String r5 = r9.LOG_TAG     // Catch: java.lang.Exception -> L6b
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L6b
            r6.<init>()     // Catch: java.lang.Exception -> L6b
            java.lang.String r7 = "isLTEDataModeInternal : netType = "
            r6.append(r7)     // Catch: java.lang.Exception -> L6b
            r6.append(r3)     // Catch: java.lang.Exception -> L6b
            java.lang.String r3 = " subid = "
            r6.append(r3)     // Catch: java.lang.Exception -> L6b
            r6.append(r2)     // Catch: java.lang.Exception -> L6b
            java.lang.String r2 = r6.toString()     // Catch: java.lang.Exception -> L6b
            android.util.Log.i(r5, r2)     // Catch: java.lang.Exception -> L6b
            goto L72
        L6a:
            r4 = r1
        L6b:
            java.lang.String r2 = r9.LOG_TAG
            java.lang.String r3 = "isLTEDataModeInternal : getAllowedNetworkTypesForReason fail"
            android.util.Log.i(r2, r3)
        L72:
            java.lang.String r2 = r9.LOG_TAG
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r5 = "isLTEDataModeInternal : prev= "
            r3.append(r5)
            boolean r9 = r9.mLteModeOn
            r3.append(r9)
            java.lang.String r9 = " new="
            r3.append(r9)
            if (r4 != r1) goto L8c
            r9 = r1
            goto L8d
        L8c:
            r9 = r0
        L8d:
            r3.append(r9)
            java.lang.String r9 = r3.toString()
            android.util.Log.i(r2, r9)
            if (r4 != r1) goto L9a
            r0 = r1
        L9a:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sec.internal.ims.core.RegistrationGovernorKor.isLTEDataModeInternal():boolean");
    }

    protected void handleAllowedNetworkTypeChanged() {
        boolean isLTEDataModeInternal;
        if (this.mTask.isRcsOnly() || RegistrationUtils.isCmcProfile(this.mTask.getProfile()) || this.mLteModeOn == (isLTEDataModeInternal = isLTEDataModeInternal())) {
            return;
        }
        this.mLteModeOn = isLTEDataModeInternal;
        onPreferredNetworkModeChanged();
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x006f  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0074 A[Catch: Exception -> 0x009f, TryCatch #0 {Exception -> 0x009f, blocks: (B:12:0x0029, B:22:0x004d, B:26:0x0074, B:29:0x008e), top: B:11:0x0029 }] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0071  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected boolean isLTEDataModeEnabled() {
        /*
            r8 = this;
            boolean r0 = com.sec.internal.helper.OmcCode.isKOROmcCode()
            r1 = 1
            if (r0 == 0) goto Lc1
            com.sec.internal.constants.Mno r0 = r8.mMno
            com.sec.internal.constants.Mno r2 = com.sec.internal.constants.Mno.KT
            if (r0 == r2) goto Lc1
            com.sec.internal.constants.Mno r2 = com.sec.internal.constants.Mno.LGU
            if (r0 != r2) goto L13
            goto Lc1
        L13:
            com.sec.internal.ims.core.RegisterTask r0 = r8.mTask
            boolean r0 = r0.isRcsOnly()
            if (r0 != 0) goto Lc1
            com.sec.internal.ims.core.RegisterTask r0 = r8.mTask
            com.sec.ims.settings.ImsProfile r0 = r0.getProfile()
            boolean r0 = com.sec.internal.ims.core.RegistrationUtils.isCmcProfile(r0)
            if (r0 == 0) goto L29
            goto Lc1
        L29:
            int r0 = r8.mPhoneId     // Catch: java.lang.Exception -> L9f
            int r0 = com.sec.internal.helper.SimUtil.getSubId(r0)     // Catch: java.lang.Exception -> L9f
            com.sec.internal.helper.os.ITelephonyManager r2 = r8.mTelephonyManager     // Catch: java.lang.Exception -> L9f
            int r3 = r8.mPhoneId     // Catch: java.lang.Exception -> L9f
            int r3 = com.sec.internal.helper.SimUtil.getSubId(r3)     // Catch: java.lang.Exception -> L9f
            int r2 = r2.getPreferredNetworkType(r3)     // Catch: java.lang.Exception -> L9f
            r3 = 0
            if (r2 == 0) goto L4c
            r4 = 2
            if (r2 == r4) goto L4c
            r4 = 14
            if (r2 == r4) goto L4c
            r4 = 18
            if (r2 != r4) goto L4a
            goto L4c
        L4a:
            r4 = r1
            goto L4d
        L4c:
            r4 = r3
        L4d:
            java.lang.String r5 = r8.LOG_TAG     // Catch: java.lang.Exception -> L9f
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L9f
            r6.<init>()     // Catch: java.lang.Exception -> L9f
            java.lang.String r7 = "isLTEDataModeEnabled : netType = "
            r6.append(r7)     // Catch: java.lang.Exception -> L9f
            r6.append(r2)     // Catch: java.lang.Exception -> L9f
            java.lang.String r2 = " subid = "
            r6.append(r2)     // Catch: java.lang.Exception -> L9f
            r6.append(r0)     // Catch: java.lang.Exception -> L9f
            java.lang.String r0 = r6.toString()     // Catch: java.lang.Exception -> L9f
            android.util.Log.i(r5, r0)     // Catch: java.lang.Exception -> L9f
            boolean r0 = r8.mLteModeOn     // Catch: java.lang.Exception -> L9f
            if (r4 != r1) goto L71
            r2 = r1
            goto L72
        L71:
            r2 = r3
        L72:
            if (r0 == r2) goto La6
            java.lang.String r0 = r8.LOG_TAG     // Catch: java.lang.Exception -> L9f
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L9f
            r2.<init>()     // Catch: java.lang.Exception -> L9f
            java.lang.String r5 = "isLTEDataModeEnabled : not match! mLteModeOn = "
            r2.append(r5)     // Catch: java.lang.Exception -> L9f
            boolean r5 = r8.mLteModeOn     // Catch: java.lang.Exception -> L9f
            r2.append(r5)     // Catch: java.lang.Exception -> L9f
            java.lang.String r5 = " isLTEDataMode ="
            r2.append(r5)     // Catch: java.lang.Exception -> L9f
            if (r4 != r1) goto L8d
            goto L8e
        L8d:
            r1 = r3
        L8e:
            r2.append(r1)     // Catch: java.lang.Exception -> L9f
            java.lang.String r1 = r2.toString()     // Catch: java.lang.Exception -> L9f
            android.util.Log.i(r0, r1)     // Catch: java.lang.Exception -> L9f
            boolean r0 = r8.isLTEDataModeInternal()     // Catch: java.lang.Exception -> L9f
            r8.mLteModeOn = r0     // Catch: java.lang.Exception -> L9f
            goto La6
        L9f:
            java.lang.String r0 = r8.LOG_TAG
            java.lang.String r1 = "isLTEDataModeEnabled : getPreferredNetworkType fail"
            android.util.Log.i(r0, r1)
        La6:
            java.lang.String r0 = r8.LOG_TAG
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "isLTEDataModeEnabled : "
            r1.append(r2)
            boolean r2 = r8.mLteModeOn
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            android.util.Log.i(r0, r1)
            boolean r8 = r8.mLteModeOn
            return r8
        Lc1:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sec.internal.ims.core.RegistrationGovernorKor.isLTEDataModeEnabled():boolean");
    }

    /* JADX WARN: Code restructure failed: missing block: B:4:0x0013, code lost:
    
        if (r2 != false) goto L6;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void checkVoLTEStatusChanged(com.sec.internal.ims.core.RegistrationGovernorKor.VoltePreferenceChangedReason r7) {
        /*
            r6 = this;
            boolean r0 = r6.getVolteServiceStatus()
            boolean r1 = r6.isVolteSettingEnabled()
            boolean r2 = r6.isLTEDataModeEnabled()
            com.sec.internal.ims.core.RegistrationGovernorKor$VoltePreferenceChangedReason r3 = com.sec.internal.ims.core.RegistrationGovernorKor.VoltePreferenceChangedReason.VOLTE_SETTING
            r4 = 1
            if (r7 != r3) goto L17
            if (r0 == 0) goto L21
            if (r2 == 0) goto L21
        L15:
            r0 = r4
            goto L23
        L17:
            com.sec.internal.ims.core.RegistrationGovernorKor$VoltePreferenceChangedReason r3 = com.sec.internal.ims.core.RegistrationGovernorKor.VoltePreferenceChangedReason.LTE_MODE
            if (r7 != r3) goto L21
            if (r0 == 0) goto L21
            if (r1 == 0) goto L21
            r1 = r2
            goto L15
        L21:
            r0 = 0
            r1 = r4
        L23:
            java.lang.String r2 = r6.LOG_TAG
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r5 = "checkVoLTEStatusChanged : needReregi = "
            r3.append(r5)
            r3.append(r0)
            java.lang.String r5 = ", isVolteOn = "
            r3.append(r5)
            r3.append(r1)
            java.lang.String r1 = r3.toString()
            android.util.Log.i(r2, r1)
            if (r0 == 0) goto Lac
            com.sec.internal.ims.core.RegisterTask r0 = r6.mTask
            com.sec.internal.constants.ims.core.RegistrationConstants$RegisterTaskState r0 = r0.getState()
            com.sec.internal.constants.ims.core.RegistrationConstants$RegisterTaskState r1 = com.sec.internal.constants.ims.core.RegistrationConstants.RegisterTaskState.IDLE
            if (r0 != r1) goto L84
            com.sec.internal.ims.core.RegistrationGovernorKor$VoltePreferenceChangedReason r0 = com.sec.internal.ims.core.RegistrationGovernorKor.VoltePreferenceChangedReason.LTE_MODE
            if (r7 != r0) goto L84
            com.sec.internal.ims.core.RegistrationManagerHandler r7 = r6.mRegHandler
            r0 = 107(0x6b, float:1.5E-43)
            boolean r7 = r7.hasMessages(r0)
            if (r7 != 0) goto L7c
            com.sec.internal.ims.core.RegisterTask r7 = r6.mTask
            r0 = 31
            r7.setDeregiReason(r0)
            com.sec.internal.ims.core.RegistrationManagerInternal r7 = r6.mRegMan
            com.sec.internal.ims.core.RegisterTask r0 = r6.mTask
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "checkVoLTEStatusChanged : abnormal case need de-reg and init reg"
            r1.append(r2)
            com.sec.internal.ims.core.RegisterTask r2 = r6.mTask
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r7.deregister(r0, r4, r4, r1)
        L7c:
            com.sec.internal.ims.core.RegistrationManagerHandler r7 = r6.mRegHandler
            int r6 = r6.mPhoneId
            r7.sendTryRegister(r6)
            return
        L84:
            java.lang.String r0 = r6.LOG_TAG
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "checkVoLTEStatusChanged: force update "
            r1.append(r2)
            com.sec.internal.ims.core.RegisterTask r2 = r6.mTask
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            android.util.Log.i(r0, r1)
            com.sec.internal.ims.core.RegistrationManagerHandler r0 = r6.mRegHandler
            com.sec.internal.ims.core.RegisterTask r6 = r6.mTask
            com.sec.internal.ims.core.RegistrationGovernorKor$VoltePreferenceChangedReason r1 = com.sec.internal.ims.core.RegistrationGovernorKor.VoltePreferenceChangedReason.LTE_MODE
            if (r7 != r1) goto La7
            r1 = 150(0x96, double:7.4E-322)
            goto La9
        La7:
            r1 = 0
        La9:
            r0.requestForcedUpdateRegistration(r6, r1)
        Lac:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sec.internal.ims.core.RegistrationGovernorKor.checkVoLTEStatusChanged(com.sec.internal.ims.core.RegistrationGovernorKor$VoltePreferenceChangedReason):void");
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernor, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public boolean isThrottledforImsNotAvailable() {
        return this.mThrottledforImsNotAvailable;
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernor, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public boolean isOmadmConfigAvailable() {
        if (this.mRegMan.getNetworkEvent(this.mPhoneId).isDataRoaming) {
            return false;
        }
        if (this.mMno == Mno.KT && "20".equals(this.mTelephonyManager.semGetTelephonyProperty(this.mPhoneId, "ril.simtype", ""))) {
            Log.i(this.LOG_TAG, "isOmadmConfigAvailable : KT_unreg SIM. do not trigger DM");
            return false;
        }
        if (this.mMno == Mno.LGU && "18".equals(this.mTelephonyManager.semGetTelephonyProperty(this.mPhoneId, "ril.simtype", ""))) {
            Log.i(this.LOG_TAG, "isOmadmConfigAvailable : LGT_unreg SIM. do not trigger DM");
            return false;
        }
        if (!OmcCode.isKOROmcCode()) {
            Log.i(this.LOG_TAG, "isOmadmConfigAvailable : oversea device and KOR sim. do not trigger DM");
            return false;
        }
        try {
            this.mContext.getPackageManager().getPackageInfo(ImsConstants.Packages.PACKAGE_DM_CLIENT, 0);
            return true;
        } catch (PackageManager.NameNotFoundException unused) {
            Log.i(this.LOG_TAG, "isOmadmConfigAvailable : DM Package not found");
            return false;
        }
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernor, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public void retryDNSQuery() {
        Log.i(this.LOG_TAG, "retryDNSQuery : getPcscfPreference(" + this.mTask.getProfile().getPcscfPreference() + ") mDnsQueryCount(" + this.mDnsQueryCount + ") mDmUpdatedFlag(" + this.mDmUpdatedFlag + ")");
        Mno mno = this.mMno;
        if (mno == Mno.KT) {
            int i = this.mDnsQueryCount;
            if (i < 1) {
                if (this.mTask.getProfile().getPcscfPreference() == 0) {
                    this.mTask.getProfile().setPcscfPreference(5);
                    if (this.mDmUpdatedFlag) {
                        checkProfileUpdateFromDM(true);
                    } else {
                        List lboPcscfAddressList = this.mTask.getProfile().getLboPcscfAddressList();
                        if (lboPcscfAddressList.isEmpty()) {
                            List<String> pcscfFromFile = getPcscfFromFile(this.mMno);
                            this.mTask.getProfile().setPcscfList(pcscfFromFile);
                            Log.i(this.LOG_TAG, "retryDNSQuery : use DEFAULT pcscf: " + pcscfFromFile);
                        } else {
                            this.mTask.getProfile().setPcscfList(lboPcscfAddressList);
                            Log.i(this.LOG_TAG, "retryDNSQuery : use OMADM pcscf: " + lboPcscfAddressList);
                        }
                    }
                    this.mRegHandler.sendTryRegister(this.mPhoneId);
                    this.mDnsQueryCount = 0;
                    return;
                }
                this.mRegHandler.sendTryRegister(this.mPhoneId, DNS_RETRY_TIME_MS);
            } else if (i == 1) {
                List<String> pcscfFromFile2 = getPcscfFromFile(mno);
                this.mTask.getProfile().setPcscfList(pcscfFromFile2);
                Log.i(this.LOG_TAG, "retryDNSQuery : use DEFAULT pcscf: " + pcscfFromFile2);
                this.mRegHandler.sendTryRegister(this.mPhoneId, DNS_RETRY_TIME_MS);
            } else {
                this.mTask.getProfile().setPcscfPreference(0);
                this.mDnsQueryCount = 0;
                return;
            }
            this.mDnsQueryCount++;
            return;
        }
        if (mno == Mno.SKT && this.mTask.getProfile().getPcscfPreference() == 0) {
            this.mTask.getProfile().setPcscfPreference(5);
            List<String> pcscfFromFile3 = getPcscfFromFile(this.mMno);
            this.mTask.getProfile().setPcscfList(pcscfFromFile3);
            this.mTask.getProfile().setLboPcscfAddressList(pcscfFromFile3);
            Log.i(this.LOG_TAG, "retryDNSQuery : use DEFAULT pcscf: " + pcscfFromFile3);
            this.mRegHandler.sendTryRegister(this.mPhoneId);
            this.mDnsQueryCount = 0;
        }
    }

    private List<String> getPcscfFromFile(Mno mno) {
        List<String> asList;
        ArrayList arrayList = new ArrayList();
        if (mno == Mno.SKT) {
            String readFromJsonFile = ImsAutoUpdate.readFromJsonFile("SKT VoLTE", "pcscf");
            if (!CollectionUtils.isNullOrEmpty(readFromJsonFile)) {
                Log.i(this.LOG_TAG, "getPcscfFromFile : SKT ImsAutoUpdate " + readFromJsonFile);
                asList = Arrays.asList(TextUtils.split(readFromJsonFile, ","));
            } else {
                String readFromJsonFile2 = ImsProfileCache.readFromJsonFile(this.mContext, "SKT VoLTE", "pcscf");
                if (!CollectionUtils.isNullOrEmpty(readFromJsonFile2)) {
                    Log.i(this.LOG_TAG, "getPcscfFromFile : SKT ImsProfileCache " + readFromJsonFile2);
                    asList = Arrays.asList(TextUtils.split(readFromJsonFile2, ","));
                } else {
                    Log.i(this.LOG_TAG, "getPcscfFromFile : SKT fail to read pcscf from file");
                    asList = Arrays.asList(TextUtils.split(OMADM_SKT_DEFAULT_PCSCF, ","));
                }
            }
            return asList;
        }
        if (mno != Mno.KT) {
            return arrayList;
        }
        String readFromJsonFile3 = ImsAutoUpdate.readFromJsonFile("KT VoLTE", "pcscf");
        if (!CollectionUtils.isNullOrEmpty(readFromJsonFile3)) {
            Log.i(this.LOG_TAG, "getPcscfFromFile : KT ImsAutoUpdate " + readFromJsonFile3);
            arrayList.add(readFromJsonFile3);
            return arrayList;
        }
        String readFromJsonFile4 = ImsProfileCache.readFromJsonFile(this.mContext, "KT VoLTE", "pcscf");
        if (!CollectionUtils.isNullOrEmpty(readFromJsonFile4)) {
            Log.i(this.LOG_TAG, "getPcscfFromFile : KT ImsProfileCache " + readFromJsonFile4);
            arrayList.add(readFromJsonFile4);
            return arrayList;
        }
        Log.i(this.LOG_TAG, "getPcscfFromFile : KT fail to read pcscf from file");
        arrayList.add(OMADM_KT_DEFAULT_PCSCF);
        return arrayList;
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernor, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public boolean isNeedDelayedDeregister() {
        Log.i(this.LOG_TAG, "isNeedDelayedDeregister :  mNeedDelayedDeregister = " + this.mNeedDelayedDeregister);
        return this.mNeedDelayedDeregister || ((Boolean) Optional.ofNullable((ImModule) ImsRegistry.getServiceModuleManager().getImModule()).map(new Function() { // from class: com.sec.internal.ims.core.RegistrationGovernorKor$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Boolean lambda$isNeedDelayedDeregister$0;
                lambda$isNeedDelayedDeregister$0 = RegistrationGovernorKor.this.lambda$isNeedDelayedDeregister$0((ImModule) obj);
                return lambda$isNeedDelayedDeregister$0;
            }
        }).orElse(Boolean.FALSE)).booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Boolean lambda$isNeedDelayedDeregister$0(ImModule imModule) {
        return Boolean.valueOf(imModule.hasIncomingSessionForA2P(this.mTask.getPhoneId()));
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernor, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public void setNeedDelayedDeregister(boolean z) {
        Log.i(this.LOG_TAG, "setNeedDelayedDeregister :  val = " + z);
        this.mNeedDelayedDeregister = z;
    }

    void checkDMConfigChange(ImsProfile imsProfile) {
        if (imsProfile == null) {
            Log.i(this.LOG_TAG, "checkDMConfigChange : dmProfile in null");
            return;
        }
        List lboPcscfAddressList = imsProfile.getLboPcscfAddressList();
        boolean z = ((lboPcscfAddressList == null || lboPcscfAddressList.equals(this.mPcscfList)) && !this.mPcscfList.isEmpty() && imsProfile.isIpSecEnabled() == this.mIpsecEnabled && imsProfile.isSupportSmsOverIms() == this.mSmsOverIp && imsProfile.isVolteServiceStatus() == this.mVolteServiceStatus) ? false : true;
        Log.i(this.LOG_TAG, "checkDMConfigChange : previous pcscf = " + this.mPcscfList + ", new pcscf = " + lboPcscfAddressList);
        Log.i(this.LOG_TAG, "checkDMConfigChange : previous IpSecEnabled = " + this.mIpsecEnabled + ", new IpSecEnabled = " + imsProfile.isIpSecEnabled());
        Log.i(this.LOG_TAG, "checkDMConfigChange : previous SmsOverIp = " + this.mSmsOverIp + ", new SmsOverIp = " + imsProfile.isSupportSmsOverIms());
        Log.i(this.LOG_TAG, "checkDMConfigChange : previous ServiceStatus = " + this.mVolteServiceStatus + ", new ServiceStatus = " + imsProfile.isVolteServiceStatus());
        if (lboPcscfAddressList != null && !lboPcscfAddressList.equals(this.mPcscfList)) {
            resetPcscfList();
            resetIPSecAllow();
            Log.i(this.LOG_TAG, "checkDMConfigChange : resetPcscfList");
        }
        this.mPcscfList = imsProfile.getLboPcscfAddressList();
        this.mIpsecEnabled = imsProfile.isIpSecEnabled();
        this.mSmsOverIp = imsProfile.isSupportSmsOverIms();
        setOldVolteServiceStatus(imsProfile.isVolteServiceStatus());
        if (this.mTask.isOneOf(RegistrationConstants.RegisterTaskState.REGISTERED, RegistrationConstants.RegisterTaskState.REGISTERING) && z) {
            if (this.mTelephonyManager.getCallState() == 0) {
                Log.i(this.LOG_TAG, "checkDMConfigChange : need de-reg and init reg");
                this.mHasPendingInitRegistrationByDMConfigChange = false;
                this.mTask.setDeregiReason(29);
                this.mRegMan.deregister(this.mTask, true, true, "checkDMConfigChange : need de-reg and init reg");
                this.mRegHandler.sendTryRegister(this.mPhoneId);
                return;
            }
            Log.i(this.LOG_TAG, "checkDMConfigChange : de-reg and init reg after call end");
            this.mHasPendingInitRegistrationByDMConfigChange = true;
        }
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase, com.sec.internal.ims.core.RegistrationGovernor, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public void onTelephonyCallStatusChanged(int i) {
        IVolteServiceModule iVolteServiceModule;
        setCallStatus(i);
        Log.i(this.LOG_TAG, "onTelephonyCallStatusChanged: " + i + " mTask:" + this.mTask.getProfile().getName() + "(" + this.mTask.getState() + ")");
        super.onTelephonyCallStatusChanged(i);
        if (this.mTask.isRcsOnly()) {
            return;
        }
        if (getCallStatus() == 0) {
            if (this.mHasPendingInitRegistrationByDMConfigChange) {
                Log.i(this.LOG_TAG, "onTelephonyCallStatusChanged : do pending de-reg and init reg");
                this.mHasPendingInitRegistrationByDMConfigChange = false;
                this.mTask.setDeregiReason(29);
                this.mRegMan.deregister(this.mTask, true, true, "onTelephonyCallStatusChanged : do pending de-reg and init reg");
                this.mRegHandler.sendTryRegister(this.mPhoneId);
                return;
            }
            if (this.mHasPendingNotifyImsNotAvailable) {
                this.mRegMan.getEventLog().logAndAdd("onTelephonyCallStatusChanged : send pending notifyImsNotAvailable");
                this.mHasPendingNotifyImsNotAvailable = false;
                this.mRegMan.stopPdnConnectivity(this.mTask.getPdnType(), this.mTask);
                this.mRegMan.notifyImsNotAvailable(this.mTask, true);
                return;
            }
        }
        if (SimUtil.getPhoneCount() > 1 && getCallStatus() == 2 && this.mTask.getState() == RegistrationConstants.RegisterTaskState.IDLE && (iVolteServiceModule = this.mVsm) != null && iVolteServiceModule.hasCsCall(this.mPhoneId)) {
            Log.i(this.LOG_TAG, "onTelephonyCallStatusChanged : tryregister during cs call");
            this.mRegHandler.sendTryRegister(this.mPhoneId);
        }
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernor, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public void resetIPSecAllow() {
        this.mIPsecAllow = true;
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernor, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public void resetPcscfPreference() {
        if (this.mTask.isRcsOnly() || this.mTask.getProfile().getPcscfPreference() == 2) {
            return;
        }
        if (this.mMno == Mno.KT) {
            this.mTask.getProfile().setPcscfPreference(0);
        } else if (this.mTask.isNeedOmadmConfig()) {
            this.mTask.getProfile().setPcscfPreference(5);
        }
        Log.i(this.LOG_TAG, "resetPcscfPreference : getPcscfPreference = " + this.mTask.getProfile().getPcscfPreference());
    }

    protected void startPDNdisconnectTimer(long j) {
        stopPDNdisconnectTimer();
        Log.i(this.LOG_TAG, "startPDNdisconnectTimer: millis " + j);
        this.mPDNdisconnectTimeout = this.mRegHandler.startDisconnectPdnTimer(this.mTask, j);
    }

    protected void stopPDNdisconnectTimer() {
        if (this.mPDNdisconnectTimeout == null) {
            return;
        }
        Log.i(this.LOG_TAG, "stopPDNdisconnectTimer");
        this.mRegHandler.stopTimer(this.mPDNdisconnectTimeout);
        this.mPDNdisconnectTimeout = null;
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernor, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public void resetAllRetryFlow() {
        this.mConsecutiveForbiddenCounter = 0;
        this.mIsAkaChallengeTimeout = false;
        this.mDnsQueryCount = 0;
        stopPDNdisconnectTimer();
        stopRetryTimer();
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase, com.sec.internal.ims.core.RegistrationGovernor, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public void unRegisterIntentReceiver() {
        Log.i(this.LOG_TAG, "Un-register Intent receiver(s)");
        try {
            this.mContext.unregisterReceiver(this.mIntentReceiverKor);
        } catch (IllegalArgumentException unused) {
            Log.e(this.LOG_TAG, "unRegisterIntentReceiver: Receiver not registered!");
        }
    }

    void makeRegistrationFailedToast() {
        Mno mno = this.mMno;
        if (mno == Mno.SKT) {
            Context context = this.mContext;
            Toast.makeText(context, context.getResources().getString(R.string.regi_failed_msg_skt), 1).show();
        } else if (mno == Mno.KT) {
            Context context2 = this.mContext;
            Toast.makeText(context2, context2.getResources().getString(R.string.regi_failed_msg_kt), 1).show();
            this.mIsReadyToGetReattach = true;
        } else if (mno == Mno.LGU) {
            Context context3 = this.mContext;
            Toast.makeText(context3, context3.getResources().getString(R.string.regi_failed_msg_lgu, "1544-0010"), 1).show();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x007d, code lost:
    
        if (r1 == false) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0088, code lost:
    
        r2 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0086, code lost:
    
        if (r1 == false) goto L18;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    boolean isNeedForcibleSmsOverImsOn() {
        /*
            r9 = this;
            com.sec.internal.constants.Mno r0 = r9.mMno
            com.sec.internal.constants.Mno r1 = com.sec.internal.constants.Mno.KT
            r2 = 0
            if (r0 == r1) goto Lb
            com.sec.internal.constants.Mno r1 = com.sec.internal.constants.Mno.LGU
            if (r0 != r1) goto L89
        Lb:
            boolean r0 = r9.isVolteEnabled()
            android.content.Context r1 = r9.mContext
            java.lang.String r3 = ""
            int r4 = r9.mPhoneId
            java.lang.String r5 = "sms_over_ip_network_indication"
            java.lang.String r1 = com.sec.ims.settings.NvConfiguration.get(r1, r5, r3, r4)
            java.lang.String r3 = "1"
            boolean r1 = android.text.TextUtils.equals(r1, r3)
            java.lang.String r3 = r9.LOG_TAG
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "isNeedForcibleSmsOverImsOn: isVolteEnabled "
            r4.append(r5)
            r4.append(r0)
            java.lang.String r5 = " isSMSIP "
            r4.append(r5)
            r4.append(r1)
            java.lang.String r4 = r4.toString()
            android.util.Log.i(r3, r4)
            com.sec.internal.constants.Mno r3 = r9.mMno
            com.sec.internal.constants.Mno r4 = com.sec.internal.constants.Mno.KT
            r5 = 1
            if (r3 != r4) goto L80
            com.sec.internal.ims.core.RegistrationManagerInternal r3 = r9.mRegMan
            int r4 = r9.mPhoneId
            com.sec.internal.constants.ims.os.NetworkEvent r3 = r3.getNetworkEvent(r4)
            boolean r3 = r3.isDataRoaming
            com.sec.internal.ims.core.PdnController r4 = r9.mPdnController
            int r6 = r9.mPhoneId
            boolean r4 = r4.isEpsOnlyReg(r6)
            java.lang.String r6 = r9.LOG_TAG
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = "isNeedForcibleSmsOverImsOn: isDataRoaming "
            r7.append(r8)
            r7.append(r3)
            java.lang.String r8 = " isEpsOnlyReg "
            r7.append(r8)
            r7.append(r4)
            java.lang.String r7 = r7.toString()
            android.util.Log.i(r6, r7)
            if (r3 != 0) goto L89
            if (r4 == 0) goto L89
            if (r0 == 0) goto L89
            if (r1 != 0) goto L89
            goto L88
        L80:
            com.sec.internal.constants.Mno r4 = com.sec.internal.constants.Mno.LGU
            if (r3 != r4) goto L89
            if (r0 == 0) goto L89
            if (r1 != 0) goto L89
        L88:
            r2 = r5
        L89:
            java.lang.String r9 = r9.LOG_TAG
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "isNeedForcibleSmsOverImsOn: isNeedSmsOverImsOn "
            r0.append(r1)
            r0.append(r2)
            java.lang.String r0 = r0.toString()
            android.util.Log.i(r9, r0)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sec.internal.ims.core.RegistrationGovernorKor.isNeedForcibleSmsOverImsOn():boolean");
    }

    private boolean hasCall() {
        IVolteServiceModule iVolteServiceModule = this.mVsm;
        boolean z = iVolteServiceModule != null && iVolteServiceModule.getSessionCount(this.mPhoneId) > 0 && this.mVsm.hasActiveCall(this.mPhoneId);
        IMSLog.i(this.LOG_TAG, this.mPhoneId, "hasCall:" + z);
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handlePeriodicPollingTimeoutIntent() {
        if (this.mTask.isRcsOnly()) {
            return;
        }
        Log.i(this.LOG_TAG, "onReceive: dm polling timeout");
        this.mRegHandler.sendRequestDmConfig(this.mTask);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleFlightModeIntent(Intent intent) {
        Intent registerReceiver;
        if (this.mTask.isOneOf(RegistrationConstants.RegisterTaskState.REGISTERING, RegistrationConstants.RegisterTaskState.REGISTERED)) {
            this.mTask.setDeregiReason(23);
            int intExtra = intent.getIntExtra("powerofftriggered", -1);
            Log.i(this.LOG_TAG, "powerOff :" + intExtra);
            if (intExtra != -1 && (registerReceiver = this.mContext.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"))) != null) {
                int intExtra2 = (registerReceiver.getIntExtra("level", 0) * 100) / registerReceiver.getIntExtra("scale", 100);
                Log.i(this.LOG_TAG, "battery level: " + intExtra2);
                if (intExtra2 <= 2) {
                    this.mTask.setDeregiReason(33);
                }
            }
            Log.i(this.LOG_TAG, "onReceive: FLIGHT_MODE is changed - reason : " + this.mTask.getDeregiReason());
            setNeedDelayedDeregister(true);
            Log.i(this.LOG_TAG, "deregister delay 300 ms for sending BYE");
            this.mRegMan.deregister(this.mTask, false, false, "flight mode enabled");
            resetRetry();
            resetAllRetryFlow();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleAirplaneModeIntent(Intent intent) {
        if (this.mTask.isRcsOnly()) {
            if (((Boolean) Optional.ofNullable(intent.getExtras()).map(new Function() { // from class: com.sec.internal.ims.core.RegistrationGovernorKor$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    Boolean lambda$handleAirplaneModeIntent$1;
                    lambda$handleAirplaneModeIntent$1 = RegistrationGovernorKor.lambda$handleAirplaneModeIntent$1((Bundle) obj);
                    return lambda$handleAirplaneModeIntent$1;
                }
            }).orElse(Boolean.FALSE)).booleanValue()) {
                return;
            }
            this.mConfigModule.getAcsConfig(this.mPhoneId).setAcsCompleteStatus(false);
            this.mConfigModule.getAcsConfig(this.mPhoneId).setForceAcs(true);
            Log.i(this.LOG_TAG, "onReceive: AIRPLANE_MODE off. reset ACS Info");
            return;
        }
        resetPcscfPreference();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Boolean lambda$handleAirplaneModeIntent$1(Bundle bundle) {
        return Boolean.valueOf(bundle.getBoolean("state", false));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleUsimDownloadEndIntent() {
        if (this.mTask.isOneOf(RegistrationConstants.RegisterTaskState.REGISTERING, RegistrationConstants.RegisterTaskState.REGISTERED)) {
            this.mRegHandler.sendTryRegister(this.mPhoneId);
        }
    }

    void handleNwRejectIntent(Intent intent) {
        int i;
        String str;
        if (this.mTask.isRcsOnly()) {
            String stringExtra = intent.getStringExtra(ImsConstants.Intents.EXTRA_CAUSE_KEY);
            if (stringExtra == null || stringExtra.isEmpty()) {
                Log.e(this.LOG_TAG, "empty CAUSE");
                return;
            }
            try {
                i = Integer.parseInt(stringExtra);
            } catch (NumberFormatException unused) {
                Log.e(this.LOG_TAG, "invalid CAUSE");
                i = 0;
            }
            Log.i(this.LOG_TAG, "onReceive: " + intent.getAction() + ", CAUSE: " + i);
            if (checkValidRejectCode(i)) {
                this.mTask.setDeregiReason(10);
                if (ImsConstants.Intents.INTENT_ACTION_REGIST_REJECT.equals(intent.getAction())) {
                    str = "nw_regist_reject";
                } else {
                    str = ImsConstants.Intents.INTENT_ACTION_LTE_REJECT.equals(intent.getAction()) ? "nw_lte_reject" : null;
                }
                this.mRegMan.deregister(this.mTask, false, true, str);
                resetRetry();
                resetAllRetryFlow();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleWapPushDmNotiReceivedIntent() {
        if (this.mTask.isRcsOnly()) {
            return;
        }
        Log.i(this.LOG_TAG, "onReceive: INTENT_WAP_PUSH_DM_NOTI_RECEIVED is received");
        if (this.mIsPermanentStopped) {
            this.mIsPermanentStopped = false;
            resetIPSecAllow();
            this.mCurImpu = 0;
            this.mRegMan.getEventLog().logAndAdd("handleWapPushDmNotiReceivedIntent: reset mIsPermanentStopped");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleNewOutgoingCallIntent() {
        if ((!this.mTask.getProfile().hasService("mmtel") && !this.mTask.getProfile().hasService("mmtel-video")) || this.mTask.getState() == RegistrationConstants.RegisterTaskState.REGISTERED || this.mRegMan.getTelephonyCallStatus(this.mPhoneId) == 1 || this.mTask.getProfile().hasEmergencySupport() || hasCall()) {
            return;
        }
        Log.i(this.LOG_TAG, "onReceive: INTENT_NEW_OUTGOING_CALL is received");
        resetRetry();
        resetAllRetryFlow();
        deregisterIfConnecting(37);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleBootCompletedIntent() {
        if (this.mTask.isRcsOnly()) {
            return;
        }
        Log.i(this.LOG_TAG, "onReceive: ACTION_BOOT_COMPLETED is received");
        checkUnprocessedOmadmConfig();
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase
    protected Set<String> applyMmtelUserSettings(Set<String> set, int i) {
        if (set == null) {
            return new HashSet();
        }
        if (!isVolteEnabled()) {
            if (!isVolteSettingEnabled()) {
                this.mTask.setRegiFailReason(DiagnosisConstants.REGI_FRSN.NO_MMTEL_USER_SETTINGS_OFF.getCode());
            } else if (!getVolteServiceStatus()) {
                this.mTask.setRegiFailReason(DiagnosisConstants.REGI_FRSN.NO_MMTEL_DM_OFF.getCode());
            } else if (!isLTEDataModeEnabled()) {
                this.mTask.setRegiFailReason(DiagnosisConstants.REGI_FRSN.NO_MMTEL_3G_PREFERRED_MODE.getCode());
            }
            removeService(set, "mmtel", "isVolteEnabled disabled.");
        }
        return set;
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernor, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public void onPdnConnected() {
        LinkPropertiesWrapper linkProperties = this.mPdnController.getLinkProperties(this.mTask);
        if (linkProperties == null) {
            Log.e(this.LOG_TAG, "onPdnConnected: LinkProperties are not exist! return..");
            return;
        }
        if (this.mTask.getPdnType() == 11) {
            this.mTask.clearSuspended();
            this.mTask.clearSuspendedBySnapshot();
            if (this.mLocalAddress == null) {
                this.mLocalAddress = linkProperties.getAddresses();
            }
            if (this.mLocalAddress.equals(linkProperties.getAddresses())) {
                return;
            }
            Log.i(this.LOG_TAG, "onPdnConnected: local IP is changed. dm&initial regi. are needed.");
            resetRetry();
            this.mLocalAddress = linkProperties.getAddresses();
            this.mRegMan.setOmadmState(this.mPhoneId, RegistrationManager.OmadmConfigState.IDLE);
            resetPcscfPreference();
            resetIPSecAllow();
            releaseThrottle(5);
        }
    }

    void deregisterIfConnecting(int i) {
        this.mTask.setDeregiReason(i);
        if (i == 13 && this.mTask.getUserAgent() == null && this.mTask.getState() == RegistrationConstants.RegisterTaskState.CONNECTING) {
            this.mRegMan.stopPdnConnectivity(this.mTask.getPdnType(), this.mTask);
            this.mRegHandler.sendTryRegister(this.mPhoneId, 1000L);
            this.mTask.setState(RegistrationConstants.RegisterTaskState.IDLE);
            Log.i(this.LOG_TAG, "deregisterIfConnecting : stopPdnConnectivity");
            return;
        }
        boolean z = this.mTask.getState() != RegistrationConstants.RegisterTaskState.CONNECTING;
        RegisterTask registerTask = this.mTask;
        registerTask.mKeepPdn = z;
        this.mRegMan.deregister(registerTask, true, z, "user triggered");
        Log.i(this.LOG_TAG, "deregisterIfConnecting : deregister");
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernor, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public void resetPdnFailureInfo() {
        super.resetPdnFailureInfo();
        if (this.mPdnController.isConnected(this.mTask.getPdnType(), this.mTask)) {
            this.mRequestPdnTimeoutCount = 0;
            if (isMobilePreferredForRcs() && this.mTask.getPdnType() == 0) {
                Log.i(this.LOG_TAG, "resetPdnFailureInfo: rcs");
                this.mHasNetworkFailure = false;
            }
        }
    }

    boolean needToHandleUnlimited404() {
        return !OmcCode.isKOROmcCode() && this.mTask.getMno() == Mno.KT;
    }

    void updateEutranValues() {
        if (this.mTask.getProfile().hasService("mmtel")) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(GlobalSettingsConstants.Registration.VOICE_DOMAIN_PREF_EUTRAN, (Integer) 3);
            this.mContext.getContentResolver().update(Uri.parse("content://com.sec.ims.settings/global").buildUpon().fragment("simslot" + this.mPhoneId).build(), contentValues, null, null);
        }
    }

    @Override // com.sec.internal.ims.core.RegistrationGovernorBase, com.sec.internal.ims.core.RegistrationGovernor, com.sec.internal.interfaces.ims.core.IRegistrationGovernor
    public void clear() {
        super.clear();
        unRegisterIntentReceiver();
        unregisterAllowedNetworkTypesListener();
    }
}
