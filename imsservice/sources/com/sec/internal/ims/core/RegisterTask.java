package com.sec.internal.ims.core;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Network;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import com.sec.ims.ImsRegistration;
import com.sec.ims.settings.ImsProfile;
import com.sec.ims.util.SipError;
import com.sec.internal.constants.Mno;
import com.sec.internal.constants.ims.DiagnosisConstants;
import com.sec.internal.constants.ims.ImsConstants;
import com.sec.internal.constants.ims.SipErrorBase;
import com.sec.internal.constants.ims.core.RegistrationConstants;
import com.sec.internal.helper.os.ITelephonyManager;
import com.sec.internal.ims.core.RcsRegistration;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Id;
import com.sec.internal.interfaces.ims.config.IConfigModule;
import com.sec.internal.interfaces.ims.core.IRegisterTask;
import com.sec.internal.interfaces.ims.core.IRegistrationGovernor;
import com.sec.internal.interfaces.ims.core.IUserAgent;
import com.sec.internal.interfaces.ims.servicemodules.volte2.IVolteServiceModule;
import com.sec.internal.log.IMSLog;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

/* loaded from: classes.dex */
public class RegisterTask implements IRegisterTask {
    private static final String LOG_TAG = "RegisterTask";
    Context mContext;
    int mDeregiReason;
    private int mDnsQueryRetryCount;
    private List<String> mFilteredReason;
    IRegistrationGovernor mGovernor;
    boolean mHasForcedPendingUpdate;
    private boolean mHasPendingDeregister;
    private boolean mHasPendingEpdgHandover;
    public boolean mHasPendingRegister;
    boolean mHasPendingUpdate;
    private boolean mImmediatePendingUpdate;
    private boolean mIsRefreshReg;
    public boolean mIsUpdateRegistering;
    private boolean mKeepEmergencyTask;
    boolean mKeepPdn;
    private String mLastPani;
    private int mLastRegiFailReason;
    Mno mMno;
    protected Network mNetworkConnected;
    int mNotAvailableReason;
    Object mObject;
    private String mPani;
    private String mPcscfHostname;
    PdnController mPdnController;
    private int mPdnType;
    int mPhoneId;
    protected ImsProfile mProfile;
    boolean mRcsProfile;
    RcsRegistration.Builder mRcsRegistrationBuilder;
    String mReason;
    String mRecoveryReason;
    ImsRegistration mReg;
    RegistrationManagerHandler mRegHandler;
    private int mRegiFailReason;
    private DiagnosisConstants.REGI_REQC mRegiRequestType;
    private int mRegistrationRat;
    RegistrationManagerInternal mRegman;
    private Message mResult;
    protected RegistrationConstants.RegisterTaskState mState;
    boolean mSuspendByIrat;
    boolean mSuspendBySnapshot;
    boolean mSuspended;
    private boolean misEpdgHandoverInProgress;

    protected RegisterTask() {
        this.mRegistrationRat = 0;
        this.mState = RegistrationConstants.RegisterTaskState.IDLE;
        this.mKeepPdn = false;
        this.mObject = null;
        this.mResult = null;
        this.mHasPendingUpdate = false;
        this.mHasForcedPendingUpdate = false;
        this.mImmediatePendingUpdate = false;
        this.mSuspended = false;
        this.mSuspendByIrat = false;
        this.mSuspendBySnapshot = false;
        this.mRcsProfile = false;
        this.mIsUpdateRegistering = false;
        this.mHasPendingRegister = false;
        this.mHasPendingDeregister = false;
        this.mIsRefreshReg = false;
        this.mReason = "";
        this.mRecoveryReason = "";
        this.mDeregiReason = 41;
        this.mNotAvailableReason = 0;
        this.mPhoneId = -1;
        this.mReg = null;
        this.mMno = Mno.DEFAULT;
        this.mContext = null;
        this.mPdnController = null;
        this.mDnsQueryRetryCount = 0;
        this.mRcsRegistrationBuilder = RcsRegistration.getBuilder();
        this.mNetworkConnected = null;
        this.misEpdgHandoverInProgress = false;
        this.mHasPendingEpdgHandover = false;
        this.mFilteredReason = new CopyOnWriteArrayList();
        this.mKeepEmergencyTask = false;
        this.mPhoneId = 0;
    }

    protected RegisterTask(int i) {
        this.mRegistrationRat = 0;
        this.mState = RegistrationConstants.RegisterTaskState.IDLE;
        this.mKeepPdn = false;
        this.mObject = null;
        this.mResult = null;
        this.mHasPendingUpdate = false;
        this.mHasForcedPendingUpdate = false;
        this.mImmediatePendingUpdate = false;
        this.mSuspended = false;
        this.mSuspendByIrat = false;
        this.mSuspendBySnapshot = false;
        this.mRcsProfile = false;
        this.mIsUpdateRegistering = false;
        this.mHasPendingRegister = false;
        this.mHasPendingDeregister = false;
        this.mIsRefreshReg = false;
        this.mReason = "";
        this.mRecoveryReason = "";
        this.mDeregiReason = 41;
        this.mNotAvailableReason = 0;
        this.mPhoneId = -1;
        this.mReg = null;
        this.mMno = Mno.DEFAULT;
        this.mContext = null;
        this.mPdnController = null;
        this.mDnsQueryRetryCount = 0;
        this.mRcsRegistrationBuilder = RcsRegistration.getBuilder();
        this.mNetworkConnected = null;
        this.misEpdgHandoverInProgress = false;
        this.mHasPendingEpdgHandover = false;
        this.mFilteredReason = new CopyOnWriteArrayList();
        this.mKeepEmergencyTask = false;
        this.mPhoneId = i;
    }

    public RegisterTask(ImsProfile imsProfile, RegistrationManagerInternal registrationManagerInternal, ITelephonyManager iTelephonyManager, PdnController pdnController, Context context, IVolteServiceModule iVolteServiceModule, IConfigModule iConfigModule, int i) {
        this.mRegistrationRat = 0;
        this.mState = RegistrationConstants.RegisterTaskState.IDLE;
        this.mKeepPdn = false;
        this.mObject = null;
        this.mResult = null;
        this.mHasPendingUpdate = false;
        this.mHasForcedPendingUpdate = false;
        this.mImmediatePendingUpdate = false;
        this.mSuspended = false;
        this.mSuspendByIrat = false;
        this.mSuspendBySnapshot = false;
        this.mRcsProfile = false;
        this.mIsUpdateRegistering = false;
        this.mHasPendingRegister = false;
        this.mHasPendingDeregister = false;
        this.mIsRefreshReg = false;
        this.mReason = "";
        this.mRecoveryReason = "";
        this.mDeregiReason = 41;
        this.mNotAvailableReason = 0;
        this.mPhoneId = -1;
        this.mReg = null;
        this.mMno = Mno.DEFAULT;
        this.mContext = null;
        this.mPdnController = null;
        this.mDnsQueryRetryCount = 0;
        this.mRcsRegistrationBuilder = RcsRegistration.getBuilder();
        this.mNetworkConnected = null;
        this.misEpdgHandoverInProgress = false;
        this.mHasPendingEpdgHandover = false;
        this.mFilteredReason = new CopyOnWriteArrayList();
        this.mProfile = imsProfile;
        this.mContext = context;
        this.mRegman = registrationManagerInternal;
        this.mPdnController = pdnController;
        this.mPhoneId = i;
        this.mKeepEmergencyTask = false;
        if (imsProfile.isSamsungMdmnEnabled()) {
            this.mMno = Mno.MDMN;
        } else {
            this.mMno = Mno.fromName(this.mProfile.getMnoName());
        }
        if (!imsProfile.hasService("mmtel-video") && !imsProfile.hasService("mmtel") && !imsProfile.hasService("smsip")) {
            this.mRcsProfile = true;
        }
        this.mGovernor = RegiGovernorCreator.getInstance(this.mMno, this.mRegman, iTelephonyManager, this, this.mPdnController, iVolteServiceModule, iConfigModule, this.mContext);
        int code = DiagnosisConstants.REGI_FRSN.UNKNOWN.getCode();
        this.mRegiFailReason = code;
        this.mLastRegiFailReason = code;
        this.mRegiRequestType = DiagnosisConstants.REGI_REQC.INITIAL;
        if (registrationManagerInternal != null) {
            this.mRegHandler = registrationManagerInternal.getRegistrationManagerHandler();
        }
        if (this.mMno == Mno.TMOBILE && getProfile().getPdnType() == 11) {
            createAndLaunchDailyReRegisterTimer();
        }
    }

    @Override // com.sec.internal.interfaces.ims.core.PdnEventListener
    public void onConnected(int i, Network network) {
        if (i != this.mPdnType) {
            return;
        }
        this.mNetworkConnected = network;
        Log.i(LOG_TAG, "onConnected: pdntype=" + i + " network=" + network + " mPdnType=" + this.mPdnType + " profile=" + this.mProfile.getName());
        this.mRegHandler.notifyPdnConnected(this);
    }

    @Override // com.sec.internal.interfaces.ims.core.PdnEventListener
    public void onDisconnected(int i) {
        Message message;
        if (i != this.mPdnType) {
            return;
        }
        this.mNetworkConnected = null;
        Log.i(LOG_TAG, "onDisconnected: pdntype=" + i + " mPdnType=" + this.mPdnType + " profile=" + this.mProfile.getName());
        this.mRegHandler.notifyPdnDisconnected(this);
        if (!this.mProfile.hasEmergencySupport() || (message = this.mResult) == null) {
            return;
        }
        message.arg1 = -1;
        message.sendToTarget();
        this.mResult = null;
    }

    @Override // com.sec.internal.interfaces.ims.core.PdnEventListener
    public void onSuspended(int i) {
        Log.i(LOG_TAG, "onSuspended: networkType=" + i + "mIsUpdateRegistering=" + this.mIsUpdateRegistering + "mSuspended=" + this.mSuspended + "mSuspendByIrat=" + this.mSuspendByIrat + "mSuspendBySnapshot=" + this.mSuspendBySnapshot);
        if (this.mIsUpdateRegistering) {
            this.mHasPendingRegister = true;
        }
        this.mRegHandler.sendSuspend(this, true, 0);
    }

    @Override // com.sec.internal.interfaces.ims.core.PdnEventListener
    public void onResumed(int i) {
        Log.i(LOG_TAG, "onResumed: networkType=" + i + "mIsUpdateRegistering=" + this.mIsUpdateRegistering + "mHasPendingRegister=" + this.mHasPendingRegister + "mSuspended=" + this.mSuspended + "mSuspendByIrat=" + this.mSuspendByIrat + "mSuspendBySnapshot=" + this.mSuspendBySnapshot);
        if (this.mIsUpdateRegistering && this.mHasPendingRegister) {
            this.mHasPendingRegister = false;
            this.mHasForcedPendingUpdate = true;
            this.mRegman.doPendingUpdateRegistration();
        }
        this.mRegHandler.sendSuspend(this, false, 0);
    }

    @Override // com.sec.internal.interfaces.ims.core.PdnEventListener
    public void onSuspendedBySnapshot(int i) {
        Log.i(LOG_TAG, "onSuspendedBySnapshot: networkType=" + i + "mSuspended=" + this.mSuspended + "mSuspendByIrat=" + this.mSuspendByIrat + "mSuspendBySnapshot=" + this.mSuspendBySnapshot);
        this.mRegHandler.sendSuspend(this, true, 2);
    }

    @Override // com.sec.internal.interfaces.ims.core.PdnEventListener
    public void onResumedBySnapshot(int i) {
        Log.i(LOG_TAG, "onResumedBySnapshot: networkType=" + i + "mSuspended=" + this.mSuspended + "mSuspendByIrat=" + this.mSuspendByIrat + "mSuspendBySnapshot=" + this.mSuspendBySnapshot);
        this.mRegHandler.sendSuspend(this, false, 2);
    }

    @Override // com.sec.internal.interfaces.ims.core.PdnEventListener
    public void onPcscfAddressChanged(int i, Network network, List<String> list) {
        List<String> checkValidPcscfIp;
        Log.i(LOG_TAG, "onPcscfAddressChanged: networkType=" + i + " mPdnType=" + this.mPdnType + " mno=" + this.mMno + " profile=" + this.mProfile.getName() + " Pcscf Preference=" + this.mProfile.getPcscfPreference());
        if (i != this.mPdnType) {
            return;
        }
        this.mNetworkConnected = network;
        if ((this.mMno == Mno.CMCC && this.mProfile.getPcscfPreference() != 0) || (checkValidPcscfIp = this.mGovernor.checkValidPcscfIp(list)) == null || checkValidPcscfIp.isEmpty()) {
            return;
        }
        this.mGovernor.updatePcscfIpList(checkValidPcscfIp);
    }

    @Override // com.sec.internal.interfaces.ims.core.PdnEventListener
    public void onPcscfRestorationNotified(int i, List<String> list) {
        List<String> checkValidPcscfIpForPcscfRestoration;
        Log.i(LOG_TAG, "onPcscfRestorationNotified: networkType=" + i + " mPdnType=" + this.mPdnType + " mno=" + this.mMno + " profile=" + this.mProfile.getName() + " Pcscf Preference=" + this.mProfile.getPcscfPreference());
        if (i != this.mPdnType || (checkValidPcscfIpForPcscfRestoration = this.mGovernor.checkValidPcscfIpForPcscfRestoration(list)) == null || checkValidPcscfIpForPcscfRestoration.isEmpty()) {
            return;
        }
        this.mGovernor.handlePcscfRestoration(checkValidPcscfIpForPcscfRestoration);
    }

    @Override // com.sec.internal.interfaces.ims.core.PdnEventListener
    public void onLocalIpChanged(int i, Network network, boolean z) {
        Log.i(LOG_TAG, "onLocalIpChanged: networkType=" + i + " isStackedIpChanged=" + z + " mPdnType=" + this.mPdnType + " profile=" + this.mProfile.getName());
        if (i != this.mPdnType) {
            return;
        }
        this.mNetworkConnected = network;
        this.mRegHandler.notifyLocalIpChanged(this, z);
    }

    public String toString() {
        return "RegisterTask[" + this.mPhoneId + "][mProfile=" + this.mProfile.getName() + ", mRegistrationRat=" + this.mRegistrationRat + ", mPdnType=" + this.mPdnType + ", mState=" + this.mState + ", mObject=" + this.mObject + ", mReason=" + this.mReason + ", mPcscfHostname=" + this.mPcscfHostname + ", mDeregiReason=" + this.mDeregiReason + "]";
    }

    public void suspendByIrat() {
        Log.i(LOG_TAG, "suspendByIrat:mSuspended=" + this.mSuspended + "mSuspendByIrat=" + this.mSuspendByIrat + "mSuspendBySnapshot=" + this.mSuspendBySnapshot);
        this.mRegHandler.sendSuspend(this, true, 1);
    }

    public void resumeByIrat() {
        Log.i(LOG_TAG, "resumeByIrat:mSuspended=" + this.mSuspended + "mSuspendByIrat=" + this.mSuspendByIrat + "mSuspendBySnapshot=" + this.mSuspendBySnapshot);
        this.mRegHandler.sendSuspend(this, false, 1);
    }

    public void setProfile(ImsProfile imsProfile) {
        this.mProfile = imsProfile;
    }

    public void setDeregiCause(int i) {
        if (i != 1) {
            if (i == 42) {
                setDeregiReason(25);
                return;
            }
            if (i == 124) {
                setDeregiReason(27);
                return;
            }
            if (i == 143) {
                setDeregiReason(9);
                return;
            }
            if (i == 802) {
                setDeregiReason(50);
                return;
            }
            if (i == 807) {
                setDeregiReason(51);
                return;
            }
            if (i != 3 && i != 4) {
                if (i == 5) {
                    setDeregiReason(31);
                    return;
                }
                if (i == 6) {
                    setDeregiReason(52);
                    return;
                }
                if (i == 12) {
                    setDeregiReason(23);
                    return;
                } else if (i == 13) {
                    setDeregiReason(26);
                    return;
                } else {
                    setDeregiReason(21);
                    return;
                }
            }
        }
        setDeregiReason(24);
    }

    public void addFilteredReason(String str, String str2) {
        this.mFilteredReason.add(str + ":" + str2);
    }

    public void clearFilteredReason() {
        this.mFilteredReason.clear();
    }

    @Override // com.sec.internal.interfaces.ims.core.IRegisterTask
    public boolean isRcsOnly() {
        return this.mRcsProfile;
    }

    @Override // com.sec.internal.interfaces.ims.core.IRegisterTask
    public boolean isOneOf(RegistrationConstants.RegisterTaskState... registerTaskStateArr) {
        for (RegistrationConstants.RegisterTaskState registerTaskState : registerTaskStateArr) {
            if (this.mState == registerTaskState) {
                return true;
            }
        }
        return false;
    }

    @Override // com.sec.internal.interfaces.ims.core.IRegisterTask
    public int getPhoneId() {
        return this.mPhoneId;
    }

    @Override // com.sec.internal.interfaces.ims.core.IRegisterTask
    public void setPdnType(int i) {
        this.mPdnType = i;
    }

    @Override // com.sec.internal.interfaces.ims.core.IRegisterTask
    public int getPdnType() {
        return this.mPdnType;
    }

    @Override // com.sec.internal.interfaces.ims.core.IRegisterTask
    public ImsProfile getProfile() {
        return this.mProfile;
    }

    @Override // com.sec.internal.interfaces.ims.core.IRegisterTask
    public Mno getMno() {
        return this.mMno;
    }

    @Override // com.sec.internal.interfaces.ims.core.IRegisterTask
    public RegistrationConstants.RegisterTaskState getState() {
        return this.mState;
    }

    @Override // com.sec.internal.interfaces.ims.core.IRegisterTask
    public IRegistrationGovernor getGovernor() {
        return this.mGovernor;
    }

    @Override // com.sec.internal.interfaces.ims.core.IRegisterTask
    public IUserAgent getUserAgent() {
        return (IUserAgent) this.mObject;
    }

    @Override // com.sec.internal.interfaces.ims.core.IRegisterTask
    public ImsRegistration getImsRegistration() {
        return this.mReg;
    }

    @Override // com.sec.internal.interfaces.ims.core.IRegisterTask
    public void setState(RegistrationConstants.RegisterTaskState registerTaskState) {
        this.mState = registerTaskState;
    }

    @Override // com.sec.internal.interfaces.ims.core.IRegisterTask
    public boolean isSuspended() {
        Log.i(LOG_TAG, "isSuspended: mSuspended(" + this.mSuspended + ") mSuspendByIrat(" + this.mSuspendByIrat + ")");
        return this.mSuspended || this.mSuspendByIrat || this.mSuspendBySnapshot;
    }

    @Override // com.sec.internal.interfaces.ims.core.IRegisterTask
    public void setRegistrationRat(int i) {
        this.mRegistrationRat = i;
    }

    @Override // com.sec.internal.interfaces.ims.core.IRegisterTask
    public int getRegistrationRat() {
        return this.mRegistrationRat;
    }

    @Override // com.sec.internal.interfaces.ims.core.IRegisterTask
    public void clearSuspended() {
        this.mSuspended = false;
    }

    @Override // com.sec.internal.interfaces.ims.core.IRegisterTask
    public void clearSuspendedBySnapshot() {
        this.mSuspendBySnapshot = false;
    }

    @Override // com.sec.internal.interfaces.ims.core.IRegisterTask
    public int getRegiFailReason() {
        return this.mRegiFailReason;
    }

    @Override // com.sec.internal.interfaces.ims.core.IRegisterTask
    public void clearUserAgent() {
        this.mObject = null;
    }

    @Override // com.sec.internal.interfaces.ims.core.IRegisterTask
    public String getReason() {
        return this.mReason;
    }

    @Override // com.sec.internal.interfaces.ims.core.IRegisterTask
    public String getPcscfHostname() {
        return this.mPcscfHostname;
    }

    @Override // com.sec.internal.interfaces.ims.core.IRegisterTask
    public int getLastRegiFailReason() {
        return this.mLastRegiFailReason;
    }

    @Override // com.sec.internal.interfaces.ims.core.IRegisterTask
    public void setRegiFailReason(int i) {
        this.mRegiFailReason = i;
    }

    @Override // com.sec.internal.interfaces.ims.core.IRegisterTask
    public void setKeepPdn(boolean z) {
        this.mKeepPdn = z;
    }

    @Override // com.sec.internal.interfaces.ims.core.IRegisterTask
    public boolean isKeepPdn() {
        return this.mKeepPdn;
    }

    @Override // com.sec.internal.interfaces.ims.core.IRegisterTask
    public boolean isNeedOmadmConfig() {
        return this.mProfile.getNeedOmadmConfig();
    }

    @Override // com.sec.internal.interfaces.ims.core.IRegisterTask
    public void setDeregiReason(int i) {
        this.mDeregiReason = i;
    }

    @Override // com.sec.internal.interfaces.ims.core.IRegisterTask
    public int getDeregiReason() {
        return this.mDeregiReason;
    }

    @Override // com.sec.internal.interfaces.ims.core.IRegisterTask
    public void setReason(String str) {
        this.mReason = str;
    }

    @Override // com.sec.internal.interfaces.ims.core.IRegisterTask
    public void setIsRefreshReg(boolean z) {
        this.mIsRefreshReg = z;
    }

    @Override // com.sec.internal.interfaces.ims.core.IRegisterTask
    public boolean isRefreshReg() {
        return this.mIsRefreshReg;
    }

    @Override // com.sec.internal.interfaces.ims.core.IRegisterTask
    public void setPcscfHostname(String str) {
        this.mPcscfHostname = str;
    }

    @Override // com.sec.internal.interfaces.ims.core.IRegisterTask
    public Network getNetworkConnected() {
        return this.mNetworkConnected;
    }

    @Override // com.sec.internal.interfaces.ims.core.IRegisterTask
    public void setHasPendingDeregister(boolean z) {
        this.mHasPendingDeregister = z;
    }

    @Override // com.sec.internal.interfaces.ims.core.IRegisterTask
    public boolean hasPendingDeregister() {
        return this.mHasPendingDeregister;
    }

    @Override // com.sec.internal.interfaces.ims.core.IRegisterTask
    public int getDeregiCause(SipError sipError) {
        IMSLog.i(LOG_TAG, getPhoneId(), "transportErrorCode: reason : " + this.mDeregiReason + ", error " + sipError);
        int code = sipError.getCode();
        int i = this.mDeregiReason;
        if (i >= 1 && i <= 20) {
            code = 1;
        } else if (i >= 21 && i <= 40) {
            code = 2;
        } else if (i >= 41 && i <= 70) {
            code = 16;
        } else if (i >= 71 && i <= 80) {
            code = 32;
        }
        if (this.mGovernor.getWFCSubscribeForbiddenCounter() > 0) {
            return 2409;
        }
        return (this.mMno == Mno.SPRINT && SipErrorBase.FORBIDDEN.equals(sipError) && !SipErrorBase.isImsForbiddenError(sipError)) ? Id.REQUEST_UPDATE_SIGN_DIGEST_RESPONSE : code;
    }

    @Override // com.sec.internal.interfaces.ims.core.IRegisterTask
    public DiagnosisConstants.REGI_REQC getRegiRequestType() {
        return this.mRegiRequestType;
    }

    @Override // com.sec.internal.interfaces.ims.core.IRegisterTask
    public void setLastRegiFailReason(int i) {
        this.mLastRegiFailReason = i;
    }

    @Override // com.sec.internal.interfaces.ims.core.IRegisterTask
    public List<String> getFilteredReason() {
        return this.mFilteredReason;
    }

    @Override // com.sec.internal.interfaces.ims.core.IRegisterTask
    public void setUpdateRegistering(boolean z) {
        this.mIsUpdateRegistering = z;
    }

    @Override // com.sec.internal.interfaces.ims.core.IRegisterTask
    public boolean isUpdateRegistering() {
        return this.mIsUpdateRegistering;
    }

    @Override // com.sec.internal.interfaces.ims.core.IRegisterTask
    public void setPendingUpdate(boolean z) {
        this.mHasPendingUpdate = z;
    }

    @Override // com.sec.internal.interfaces.ims.core.IRegisterTask
    public boolean hasPendingUpdate() {
        return this.mHasPendingUpdate;
    }

    @Override // com.sec.internal.interfaces.ims.core.IRegisterTask
    public void setHasForcedPendingUpdate(boolean z) {
        this.mHasForcedPendingUpdate = z;
    }

    @Override // com.sec.internal.interfaces.ims.core.IRegisterTask
    public boolean hasForcedPendingUpdate() {
        return this.mHasForcedPendingUpdate;
    }

    @Override // com.sec.internal.interfaces.ims.core.IRegisterTask
    public void setHasPendingEpdgHandover(boolean z) {
        this.mHasPendingEpdgHandover = z;
    }

    @Override // com.sec.internal.interfaces.ims.core.IRegisterTask
    public boolean hasPendingEpdgHandover() {
        return this.mHasPendingEpdgHandover;
    }

    @Override // com.sec.internal.interfaces.ims.core.IRegisterTask
    public void setImmediatePendingUpdate(boolean z) {
        this.mImmediatePendingUpdate = z;
    }

    @Override // com.sec.internal.interfaces.ims.core.IRegisterTask
    public boolean isImmediatePendingUpdate() {
        return this.mImmediatePendingUpdate;
    }

    @Override // com.sec.internal.interfaces.ims.core.IRegisterTask
    public void setUserAgent(IUserAgent iUserAgent) {
        if (iUserAgent != null) {
            this.mObject = iUserAgent;
        }
    }

    @Override // com.sec.internal.interfaces.ims.core.IRegisterTask
    public void setRegiRequestType(DiagnosisConstants.REGI_REQC regi_reqc) {
        this.mRegiRequestType = regi_reqc;
    }

    @Override // com.sec.internal.interfaces.ims.core.IRegisterTask
    public void setEpdgHandoverInProgress(boolean z) {
        this.misEpdgHandoverInProgress = z;
    }

    @Override // com.sec.internal.interfaces.ims.core.IRegisterTask
    public boolean isEpdgHandoverInProgress() {
        return this.misEpdgHandoverInProgress;
    }

    @Override // com.sec.internal.interfaces.ims.core.IRegisterTask
    public void keepEmergencyTask(boolean z) {
        this.mKeepEmergencyTask = z;
    }

    @Override // com.sec.internal.interfaces.ims.core.IRegisterTask
    public boolean needKeepEmergencyTask() {
        return this.mKeepEmergencyTask;
    }

    @Override // com.sec.internal.interfaces.ims.core.IRegisterTask
    public void setResultMessage(Message message) {
        this.mResult = message;
    }

    @Override // com.sec.internal.interfaces.ims.core.IRegisterTask
    public Message getResultMessage() {
        return this.mResult;
    }

    @Override // com.sec.internal.interfaces.ims.core.IRegisterTask
    public void setNotAvailableReason(int i) {
        this.mNotAvailableReason = i;
    }

    @Override // com.sec.internal.interfaces.ims.core.IRegisterTask
    public int getNotAvailableReason() {
        return this.mNotAvailableReason;
    }

    @Override // com.sec.internal.interfaces.ims.core.IRegisterTask
    public void clearNotAvailableReason() {
        this.mNotAvailableReason = 0;
    }

    @Override // com.sec.internal.interfaces.ims.core.IRegisterTask
    public void clearUpdateRegisteringFlag() {
        this.mIsUpdateRegistering = false;
        this.mHasPendingRegister = false;
        this.mHasPendingDeregister = false;
        this.misEpdgHandoverInProgress = false;
        this.mImmediatePendingUpdate = false;
    }

    @Override // com.sec.internal.interfaces.ims.core.IRegisterTask
    public void setDnsQueryRetryCount(int i) {
        this.mDnsQueryRetryCount = i;
    }

    @Override // com.sec.internal.interfaces.ims.core.IRegisterTask
    public int getDnsQueryRetryCount() {
        return this.mDnsQueryRetryCount;
    }

    @Override // com.sec.internal.interfaces.ims.core.IRegisterTask
    public void setRecoveryReason(String str) {
        this.mRecoveryReason = str;
    }

    @Override // com.sec.internal.interfaces.ims.core.IRegisterTask
    public String getRecoveryReason() {
        return this.mRecoveryReason;
    }

    @Override // com.sec.internal.interfaces.ims.core.IRegisterTask
    public void setImsRegistration(ImsRegistration imsRegistration) {
        this.mReg = imsRegistration;
    }

    @Override // com.sec.internal.interfaces.ims.core.PdnEventListener
    public void onNetworkRequestFail() {
        this.mState = RegistrationConstants.RegisterTaskState.IDLE;
    }

    @Override // com.sec.internal.interfaces.ims.core.IRegisterTask
    public void resetTaskOnPdnDisconnected() {
        clearSuspended();
        clearSuspendedBySnapshot();
        this.mGovernor.resetPcscfList();
        this.mGovernor.resetPcoType();
    }

    @Override // com.sec.internal.interfaces.ims.core.IRegisterTask
    public String getPani() {
        return this.mPani;
    }

    public String getLastPani() {
        return this.mLastPani;
    }

    @Override // com.sec.internal.interfaces.ims.core.IRegisterTask
    public void setPaniSet(String str, String str2) {
        this.mPani = str;
        this.mLastPani = str2;
    }

    @Override // com.sec.internal.interfaces.ims.core.IRegisterTask
    public RcsRegistration.Builder getRcsRegistrationBuilder() {
        return this.mRcsRegistrationBuilder;
    }

    RcsRegistration buildRcsRegistration() {
        return this.mRcsRegistrationBuilder.build();
    }

    private PendingIntent createDailyReRegisterTimerIntent() {
        return PendingIntent.getBroadcast(this.mContext, 0, new Intent(ImsConstants.Intents.ACTION_CHECK_REGISTRATION_DAILY), 201326592);
    }

    private void createAndLaunchDailyReRegisterTimer() {
        Log.i(LOG_TAG, "Creating and launching daily re-register timer.");
        ((AlarmManager) this.mContext.getSystemService("alarm")).setInexactRepeating(3, SystemClock.elapsedRealtime() + ThreadLocalRandom.current().nextInt(1, 86400000), 86400000L, createDailyReRegisterTimerIntent());
    }

    public void stopDailyReRegisterTimer() {
        ((AlarmManager) this.mContext.getSystemService("alarm")).cancel(createDailyReRegisterTimerIntent());
    }
}
