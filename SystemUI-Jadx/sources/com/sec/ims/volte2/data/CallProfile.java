package com.sec.ims.volte2.data;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.sec.ims.util.ImsUri;
import java.util.HashMap;
import java.util.List;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes3.dex */
public class CallProfile implements Parcelable {
    public static final Parcelable.Creator<CallProfile> CREATOR = new Parcelable.Creator<CallProfile>() { // from class: com.sec.ims.volte2.data.CallProfile.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CallProfile createFromParcel(Parcel parcel) {
            return new CallProfile(parcel, 0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CallProfile[] newArray(int i) {
            return new CallProfile[i];
        }
    };
    public static final int DIRECTION_MO = 0;
    public static final int DIRECTION_MT = 1;
    public static final int DIRECTION_PULLED_MO = 2;
    public static final int DIRECTION_PULLED_MT = 3;
    public static final int DIRECTION_UNKNOWN = -1;
    public static final int GROUP_CONFERENCE = 2;
    public static final int NONE_CONFERENCE = 0;
    public static final int NWAY_CONFERENCE = 1;
    private HashMap<String, String> mAdditionalSipHeaders;
    private String mAlertInfo;
    private int mAudioEarlyMediaDir;
    private int mAudioRxTrackId;
    private String mCLI;
    private int mCallType;
    private int mCmcBoundSessionId;
    private String mCmcCallTime;
    private String mCmcDeviceId;
    private int mCmcDtmfKey;
    private int mCmcEdCallSlot;
    private int mCmcRecordEvent;
    private int mCmcType;
    private Bundle mComposerData;
    private int mConfSessionId;
    private int mConferenceCall;
    private String mConferenceSupported;
    private boolean mDelayRinging;
    private String mDialingDevice;
    private String mDialingNumber;
    private String mDialogId;
    private int mDirection;
    private String mDtmfEvent;
    private boolean mEPSFBsuccess;
    private String mEchoCallId;
    private String mEchoCellId;
    private String mEmergencyRat;
    private boolean mEnableScr;
    private String mFeatureCaps;
    private boolean mForceCSFB;
    private int mForegroundSessionId;
    private int mHDIcon;
    private boolean mHasCSFBError;
    private boolean mHasDSDAVideoCapa;
    private boolean mHasDiversion;
    private boolean mHasRemoteVideoCapa;
    private String mHistoryInfo;
    private boolean mIdcArCallRunning;
    private boolean mIdcScreenShareRunning;
    private boolean mIsCrossSimCall;
    private boolean mIsDowngradedAtEstablish;
    private boolean mIsDowngradedVideoCall;
    private boolean mIsECallConvertedToNormal;
    private String mIsFocus;
    private boolean mIsPullCall;
    private boolean mIsRemoteHeld;
    private boolean mIsSamsungMdmnCall;
    private boolean mIsVideoCrbt;
    private boolean mIsVideoCrbtValid;
    private String mLetteringText;
    private String mLineMsisdn;
    private String mLocalHoldTone;
    private String mMTConference;
    private int mMaxConferenceCallUsers;
    private MediaProfile mMediaProfile;
    private String mModifyHeader;
    private int mNetworkType;
    private String mNumberPlus;
    private String mOrganization;
    private ImsUri mOriginatingUri;
    private List<String> mP2p;
    private int mPhoneId;
    private String mPhotoRing;
    private QuantumSecurityInfo mQuantumSecurityInfo;
    private int mRadioTech;
    private int mRecordState;
    private String mReferredBy;
    private int mRejectCause;
    private int mRejectCode;
    private String mRejectProtocol;
    private String mRejectText;
    private String mReplaceSipCallId;
    private int mRetryAfterTime;
    private String mSipCallId;
    private String mSipInviteMsg;
    private boolean mTouchScreenEnabled;
    private String mUrn;
    private boolean mVCrtIsAlerting;
    private String mVerstat;

    public /* synthetic */ CallProfile(Parcel parcel, int i) {
        this(parcel);
    }

    private void readFromParcel(Parcel parcel) {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        boolean z6;
        boolean z7;
        boolean z8;
        boolean z9;
        boolean z10;
        boolean z11;
        boolean z12;
        boolean z13;
        boolean z14;
        boolean z15;
        boolean z16;
        this.mCallType = parcel.readInt();
        this.mDirection = parcel.readInt();
        this.mNetworkType = parcel.readInt();
        this.mConferenceCall = parcel.readInt();
        this.mForegroundSessionId = parcel.readInt();
        boolean z17 = true;
        if (parcel.readInt() == 1) {
            this.mOriginatingUri = ImsUri.parse(parcel.readString());
        } else {
            this.mOriginatingUri = null;
        }
        this.mDialingNumber = parcel.readString();
        this.mDialingDevice = parcel.readString();
        if (parcel.readInt() == 1) {
            this.mUrn = parcel.readString();
        } else {
            this.mUrn = null;
        }
        this.mCLI = parcel.readString();
        this.mLetteringText = parcel.readString();
        this.mAlertInfo = parcel.readString();
        this.mPhotoRing = parcel.readString();
        this.mHistoryInfo = parcel.readString();
        this.mDtmfEvent = parcel.readString();
        this.mNumberPlus = parcel.readString();
        this.mConferenceSupported = parcel.readString();
        this.mIsFocus = parcel.readString();
        if (parcel.readInt() == 1) {
            this.mReferredBy = parcel.readString();
        }
        this.mSipCallId = (String) parcel.readValue(null);
        if (parcel.readInt() == 1) {
            this.mSipInviteMsg = parcel.readString();
        }
        this.mLineMsisdn = (String) parcel.readValue(null);
        this.mDialogId = parcel.readString();
        this.mMediaProfile = (MediaProfile) parcel.readParcelable(MediaProfile.class.getClassLoader());
        if (parcel.readInt() == 1) {
            z = true;
        } else {
            z = false;
        }
        this.mIsPullCall = z;
        if (parcel.readInt() == 1) {
            z2 = true;
        } else {
            z2 = false;
        }
        this.mIsDowngradedVideoCall = z2;
        if (parcel.readInt() == 1) {
            z3 = true;
        } else {
            z3 = false;
        }
        this.mIsDowngradedAtEstablish = z3;
        if (parcel.readInt() == 1) {
            z4 = true;
        } else {
            z4 = false;
        }
        this.mIsSamsungMdmnCall = z4;
        this.mHDIcon = parcel.readInt();
        if (parcel.readInt() == 1) {
            this.mAdditionalSipHeaders = (HashMap) parcel.readBundle().getSerializable("extra_header");
        }
        if (parcel.readInt() == 1) {
            z5 = true;
        } else {
            z5 = false;
        }
        this.mHasRemoteVideoCapa = z5;
        if (parcel.readInt() == 1) {
            z6 = true;
        } else {
            z6 = false;
        }
        this.mHasCSFBError = z6;
        if (parcel.readInt() == 1) {
            this.mEmergencyRat = parcel.readString();
        }
        if (parcel.readInt() == 1) {
            z7 = true;
        } else {
            z7 = false;
        }
        this.mIsVideoCrbt = z7;
        if (parcel.readInt() == 1) {
            z8 = true;
        } else {
            z8 = false;
        }
        this.mIsVideoCrbtValid = z8;
        this.mPhoneId = parcel.readInt();
        this.mRadioTech = parcel.readInt();
        this.mP2p = parcel.createStringArrayList();
        this.mCmcBoundSessionId = parcel.readInt();
        this.mReplaceSipCallId = parcel.readString();
        this.mCmcType = parcel.readInt();
        if (parcel.readInt() == 1) {
            z9 = true;
        } else {
            z9 = false;
        }
        this.mForceCSFB = z9;
        if (parcel.readInt() == 1) {
            this.mVerstat = parcel.readString();
        } else {
            this.mVerstat = null;
        }
        this.mOrganization = parcel.readString();
        this.mCmcDeviceId = parcel.readString();
        this.mCmcDtmfKey = parcel.readInt();
        this.mComposerData = parcel.readBundle();
        this.mRecordState = parcel.readInt();
        this.mCmcCallTime = parcel.readString();
        this.mFeatureCaps = parcel.readString();
        this.mAudioEarlyMediaDir = parcel.readInt();
        if (parcel.readInt() == 1) {
            z10 = true;
        } else {
            z10 = false;
        }
        this.mHasDiversion = z10;
        if (parcel.readInt() == 1) {
            z11 = true;
        } else {
            z11 = false;
        }
        this.mDelayRinging = z11;
        this.mRejectProtocol = parcel.readString();
        this.mRejectCode = parcel.readInt();
        this.mRejectText = parcel.readString();
        if (parcel.readInt() == 1) {
            z12 = true;
        } else {
            z12 = false;
        }
        this.mVCrtIsAlerting = z12;
        if (parcel.readInt() == 1) {
            z13 = true;
        } else {
            z13 = false;
        }
        this.mIsRemoteHeld = z13;
        this.mCmcEdCallSlot = parcel.readInt();
        if (parcel.readInt() == 1) {
            z14 = true;
        } else {
            z14 = false;
        }
        this.mIsECallConvertedToNormal = z14;
        this.mQuantumSecurityInfo = (QuantumSecurityInfo) parcel.readParcelable(QuantumSecurityInfo.class.getClassLoader());
        this.mConfSessionId = parcel.readInt();
        if (parcel.readInt() == 1) {
            z15 = true;
        } else {
            z15 = false;
        }
        this.mTouchScreenEnabled = z15;
        if (parcel.readInt() == 1) {
            z16 = true;
        } else {
            z16 = false;
        }
        this.mIdcScreenShareRunning = z16;
        if (parcel.readInt() != 1) {
            z17 = false;
        }
        this.mIdcArCallRunning = z17;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public HashMap<String, String> getAdditionalSipHeaders() {
        return this.mAdditionalSipHeaders;
    }

    public String getAlertInfo() {
        return this.mAlertInfo;
    }

    public int getAudioEarlyMediaDir() {
        return this.mAudioEarlyMediaDir;
    }

    public int getAudioRxTrackId() {
        return this.mAudioRxTrackId;
    }

    public String getCLI() {
        return this.mCLI;
    }

    public int getCallType() {
        return this.mCallType;
    }

    public int getCmcBoundSessionId() {
        return this.mCmcBoundSessionId;
    }

    public String getCmcCallTime() {
        return this.mCmcCallTime;
    }

    public String getCmcDeviceId() {
        return this.mCmcDeviceId;
    }

    public int getCmcDtmfKey() {
        return this.mCmcDtmfKey;
    }

    public int getCmcEdCallSlot() {
        return this.mCmcEdCallSlot;
    }

    public int getCmcRecordEvent() {
        return this.mCmcRecordEvent;
    }

    public int getCmcType() {
        return this.mCmcType;
    }

    public Bundle getComposerData() {
        return this.mComposerData;
    }

    public int getConfSessionId() {
        return this.mConfSessionId;
    }

    public String getConferenceSupported() {
        return this.mConferenceSupported;
    }

    public int getConferenceType() {
        return this.mConferenceCall;
    }

    public boolean getDelayRinging() {
        return this.mDelayRinging;
    }

    public String getDialingDevice() {
        return this.mDialingDevice;
    }

    public String getDialingNumber() {
        return this.mDialingNumber;
    }

    public String getDialogId() {
        return this.mDialogId;
    }

    public int getDirection() {
        return this.mDirection;
    }

    public String getDtmfEvent() {
        return this.mDtmfEvent;
    }

    public boolean getEPSFBsuccess() {
        return this.mEPSFBsuccess;
    }

    public String getEchoCallId() {
        return this.mEchoCallId;
    }

    public String getEchoCellId() {
        return this.mEchoCellId;
    }

    public String getEmergencyRat() {
        return this.mEmergencyRat;
    }

    public boolean getEnableScr() {
        return this.mEnableScr;
    }

    public String getFeatureCaps() {
        return this.mFeatureCaps;
    }

    public int getForegroundSessionId() {
        return this.mForegroundSessionId;
    }

    public int getHDIcon() {
        return this.mHDIcon;
    }

    public boolean getHasDiversion() {
        return this.mHasDiversion;
    }

    public String getHistoryInfo() {
        return this.mHistoryInfo;
    }

    public boolean getIdcArCallRunning() {
        return this.mIdcArCallRunning;
    }

    public boolean getIdcScreenShareRunning() {
        return this.mIdcScreenShareRunning;
    }

    public String getIsFocus() {
        return this.mIsFocus;
    }

    public String getLetteringText() {
        return this.mLetteringText;
    }

    public String getLineMsisdn() {
        return this.mLineMsisdn;
    }

    public String getLocalHoldTone() {
        return this.mLocalHoldTone;
    }

    public String getMTConference() {
        return this.mMTConference;
    }

    public int getMaxConferenceCallUsers() {
        return this.mMaxConferenceCallUsers;
    }

    public MediaProfile getMediaProfile() {
        return this.mMediaProfile;
    }

    public String getModifyHeader() {
        return this.mModifyHeader;
    }

    public int getNetworkType() {
        return this.mNetworkType;
    }

    public String getNumberPlus() {
        return this.mNumberPlus;
    }

    public String getOrganization() {
        return this.mOrganization;
    }

    public ImsUri getOriginatingUri() {
        return this.mOriginatingUri;
    }

    public List<String> getP2p() {
        return this.mP2p;
    }

    public int getPhoneId() {
        return this.mPhoneId;
    }

    public String getPhotoRing() {
        return this.mPhotoRing;
    }

    public QuantumSecurityInfo getQuantumSecurityInfo() {
        return this.mQuantumSecurityInfo;
    }

    public int getRadioTech() {
        return this.mRadioTech;
    }

    public int getRecordState() {
        return this.mRecordState;
    }

    public String getReferredBy() {
        return this.mReferredBy;
    }

    public int getRejectCause() {
        return this.mRejectCause;
    }

    public int getRejectCode() {
        return this.mRejectCode;
    }

    public String getRejectProtocol() {
        return this.mRejectProtocol;
    }

    public String getRejectText() {
        return this.mRejectText;
    }

    public String getReplaceSipCallId() {
        return this.mReplaceSipCallId;
    }

    public int getRetryAfterTime() {
        return this.mRetryAfterTime;
    }

    public String getSipCallId() {
        return this.mSipCallId;
    }

    public String getSipInviteMsg() {
        return this.mSipInviteMsg;
    }

    public boolean getTouchScreenEnabled() {
        return this.mTouchScreenEnabled;
    }

    public String getUrn() {
        return this.mUrn;
    }

    public boolean getVCrtIsAlerting() {
        return this.mVCrtIsAlerting;
    }

    public String getVerstat() {
        return this.mVerstat;
    }

    public boolean hasCSFBError() {
        return this.mHasCSFBError;
    }

    public boolean hasRemoteVideoCapa() {
        return this.mHasDSDAVideoCapa & this.mHasRemoteVideoCapa;
    }

    public boolean isConferenceCall() {
        if (this.mConferenceCall == 0) {
            return false;
        }
        return true;
    }

    public boolean isCrossSimCall() {
        return this.mIsCrossSimCall;
    }

    public boolean isDowngradedAtEstablish() {
        return this.mIsDowngradedAtEstablish;
    }

    public boolean isDowngradedVideoCall() {
        return this.mIsDowngradedVideoCall;
    }

    public boolean isECallConvertedToNormal() {
        return this.mIsECallConvertedToNormal;
    }

    public boolean isForceCSFB() {
        return this.mForceCSFB;
    }

    public boolean isMOCall() {
        int i = this.mDirection;
        if (i != 0 && i != 2) {
            return false;
        }
        return true;
    }

    public boolean isMTCall() {
        int i = this.mDirection;
        if (i == 1 || i == 3) {
            return true;
        }
        return false;
    }

    public boolean isPullCall() {
        return this.mIsPullCall;
    }

    public boolean isRemoteHeld() {
        return this.mIsRemoteHeld;
    }

    public boolean isSamsungMdmnCall() {
        return this.mIsSamsungMdmnCall;
    }

    public boolean isVideoCRBT() {
        return this.mIsVideoCrbt;
    }

    public boolean isVideoCrbtValid() {
        return this.mIsVideoCrbtValid;
    }

    public void setAdditionalSipHeaders(HashMap<String, String> hashMap) {
        this.mAdditionalSipHeaders = hashMap;
    }

    public void setAlertInfo(String str) {
        this.mAlertInfo = str;
    }

    public void setAudioEarlyMediaDir(int i) {
        this.mAudioEarlyMediaDir = i;
    }

    public void setAudioRxTrackId(int i) {
        this.mAudioRxTrackId = i;
    }

    public void setCLI(String str) {
        this.mCLI = str;
    }

    public void setCallType(int i) {
        this.mCallType = i;
    }

    public void setCmcBoundSessionId(int i) {
        this.mCmcBoundSessionId = i;
    }

    public void setCmcCallTime(String str) {
        this.mCmcCallTime = str;
    }

    public void setCmcDeviceId(String str) {
        this.mCmcDeviceId = str;
    }

    public void setCmcDtmfKey(int i) {
        this.mCmcDtmfKey = i;
    }

    public void setCmcEdCallSlot(int i) {
        this.mCmcEdCallSlot = i;
    }

    public void setCmcRecordEvent(int i) {
        this.mCmcRecordEvent = i;
    }

    public void setCmcType(int i) {
        this.mCmcType = i;
    }

    public void setComposerData(Bundle bundle) {
        this.mComposerData = bundle;
    }

    public void setConfSessionId(int i) {
        this.mConfSessionId = i;
    }

    public void setConferenceCall(int i) {
        this.mConferenceCall = i;
    }

    public void setConferenceSupported(String str) {
        this.mConferenceSupported = str;
    }

    public void setCrossSimCall(boolean z) {
        this.mIsCrossSimCall = z;
    }

    public void setDSDAVideoCapa(boolean z) {
        this.mHasDSDAVideoCapa = z;
    }

    public void setDelayRinging(boolean z) {
        this.mDelayRinging = z;
    }

    public void setDialingDevice(String str) {
        this.mDialingDevice = str;
    }

    public void setDialingNumber(String str) {
        this.mDialingNumber = str;
        this.mQuantumSecurityInfo.setRemoteTelNum(str);
    }

    public void setDialogId(String str) {
        this.mDialogId = str;
    }

    public void setDirection(int i) {
        this.mDirection = i;
        this.mQuantumSecurityInfo.setCallDirection(i);
    }

    public void setDowngradedAtEstablish(boolean z) {
        this.mIsDowngradedAtEstablish = z;
    }

    public void setDowngradedVideoCall(boolean z) {
        this.mIsDowngradedVideoCall = z;
    }

    public void setDtmfEvent(String str) {
        this.mDtmfEvent = str;
    }

    public void setECallConvertedToNormal(boolean z) {
        this.mIsECallConvertedToNormal = z;
    }

    public void setEPSFBsuccess(boolean z) {
        this.mEPSFBsuccess = z;
    }

    public void setEchoCallId(String str) {
        this.mEchoCallId = str;
    }

    public void setEchoCellId(String str) {
        this.mEchoCellId = str;
    }

    public void setEmergencyRat(String str) {
        this.mEmergencyRat = str;
    }

    public void setEnableScr(boolean z) {
        this.mEnableScr = z;
    }

    public void setFeatureCaps(String str) {
        this.mFeatureCaps = str;
    }

    public void setForceCSFB(boolean z) {
        this.mForceCSFB = z;
    }

    public void setForegroundSessionId(int i) {
        this.mForegroundSessionId = i;
    }

    public void setHDIcon(int i) {
        this.mHDIcon = i;
    }

    public void setHasCSFBError(boolean z) {
        this.mHasCSFBError = z;
    }

    public void setHasDiversion(boolean z) {
        this.mHasDiversion = z;
    }

    public void setHistoryInfo(String str) {
        this.mHistoryInfo = str;
    }

    public void setIdcArCallRunning(boolean z) {
        this.mIdcArCallRunning = z;
    }

    public void setIdcScreenShareRunning(boolean z) {
        this.mIdcScreenShareRunning = z;
    }

    public void setIsFocus(String str) {
        this.mIsFocus = str;
    }

    public void setLetteringText(String str) {
        this.mLetteringText = str;
    }

    public void setLineMsisdn(String str) {
        this.mLineMsisdn = str;
    }

    public void setLocalHoldTone(String str) {
        this.mLocalHoldTone = str;
    }

    public void setMTConference(String str) {
        this.mMTConference = str;
    }

    public void setMaxConferenceCallUsers(int i) {
        this.mMaxConferenceCallUsers = i;
    }

    public void setMediaProfile(MediaProfile mediaProfile) {
        this.mMediaProfile = mediaProfile;
    }

    public void setModifyHeader(String str) {
        this.mModifyHeader = str;
    }

    public void setNetworkType(int i) {
        this.mNetworkType = i;
    }

    public void setNumberPlus(String str) {
        this.mNumberPlus = str;
    }

    public void setOrganization(String str) {
        this.mOrganization = str;
    }

    public void setOriginatingUri(ImsUri imsUri) {
        this.mOriginatingUri = imsUri;
    }

    public void setP2p(List<String> list) {
        this.mP2p = list;
    }

    public void setPhoneId(int i) {
        this.mPhoneId = i;
    }

    public void setPhotoRing(String str) {
        this.mPhotoRing = str;
    }

    public void setPullCall(boolean z) {
        this.mIsPullCall = z;
    }

    public void setRadioTech(int i) {
        this.mRadioTech = i;
    }

    public void setRecordState(int i) {
        this.mRecordState = i;
    }

    public void setReferredBy(String str) {
        this.mReferredBy = str;
    }

    public void setRejectCause(int i) {
        this.mRejectCause = i;
    }

    public void setRejectCode(int i) {
        this.mRejectCode = i;
    }

    public void setRejectProtocol(String str) {
        this.mRejectProtocol = str;
    }

    public void setRejectText(String str) {
        this.mRejectText = str;
    }

    public void setRemoteHeld(boolean z) {
        this.mIsRemoteHeld = z;
    }

    public void setRemoteVideoCapa(boolean z) {
        this.mHasRemoteVideoCapa = z;
    }

    public void setReplaceSipCallId(String str) {
        this.mReplaceSipCallId = str;
    }

    public void setRetryAfterTime(int i) {
        this.mRetryAfterTime = i;
    }

    public void setSamsungMdmnCall(boolean z) {
        this.mIsSamsungMdmnCall = z;
    }

    public void setSipCallId(String str) {
        this.mSipCallId = str;
    }

    public void setSipInviteMsg(String str) {
        this.mSipInviteMsg = str;
    }

    public void setTouchScreenEnabled(boolean z) {
        this.mTouchScreenEnabled = z;
    }

    public void setUrn(String str) {
        this.mUrn = str;
    }

    public void setVCrtIsAlerting(boolean z) {
        this.mVCrtIsAlerting = z;
    }

    public void setVerstat(String str) {
        this.mVerstat = str;
    }

    public void setVideoCRBT(boolean z) {
        this.mIsVideoCrbt = z;
    }

    public void setVideoCrbtValid(boolean z) {
        this.mIsVideoCrbtValid = z;
    }

    public String toString() {
        String str;
        String m;
        String m2;
        String str2;
        int i = this.mCallType;
        if (i != 1) {
            if (i != 2) {
                str = "callType: [UNKNOWN";
            } else {
                str = "callType: [CALL_TYPE_VIDEO";
            }
        } else {
            str = "callType: [CALL_TYPE_VOICE";
        }
        String m3 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, "], direction: [");
        int i2 = this.mDirection;
        if (i2 != 0) {
            if (i2 != 1) {
                if (i2 != 2) {
                    if (i2 != 3) {
                        m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(m3, "UNKNOWN");
                    } else {
                        m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(m3, "PULLED_MT");
                    }
                } else {
                    m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(m3, "PULLED_MO");
                }
            } else {
                m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(m3, "MT");
            }
        } else {
            m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(m3, "MO");
        }
        String m4 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(m, "], networkType: [");
        int i3 = this.mNetworkType;
        if (i3 != -1) {
            if (i3 != 0) {
                if (i3 != 1) {
                    if (i3 != 11) {
                        m2 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(m4, "UNKNOWN");
                    } else {
                        m2 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(m4, "IMS");
                    }
                } else {
                    m2 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(m4, "WIFI");
                }
            } else {
                m2 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(m4, "MOBILE");
            }
        } else {
            m2 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(m4, "NONE (emergency)");
        }
        StringBuilder m5 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(m2, "], mIsVideoCrbt: [");
        m5.append(this.mIsVideoCrbt);
        StringBuilder m6 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(m5.toString(), "], mIsVideoCrbtValid: [");
        m6.append(this.mIsVideoCrbtValid);
        StringBuilder m7 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(m6.toString(), "], mP2p: [");
        List<String> list = this.mP2p;
        if (list != null) {
            str2 = list.toString();
        } else {
            str2 = "null";
        }
        m7.append(str2);
        StringBuilder m8 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(m7.toString(), "], mCmcBoundSessionId: [");
        m8.append(this.mCmcBoundSessionId);
        StringBuilder m9 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(m8.toString(), "], mRejectProtocol: [");
        m9.append(this.mRejectProtocol);
        StringBuilder m10 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(m9.toString(), "], mRejectCode: [");
        m10.append(this.mRejectCode);
        StringBuilder m11 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(m10.toString(), "], mRejectText: [");
        m11.append(this.mRejectText);
        StringBuilder m12 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(m11.toString(), "], mIsRemoteHeld: [");
        m12.append(this.mIsRemoteHeld);
        StringBuilder m13 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(m12.toString(), "], mCmcEdCallSlot: [");
        m13.append(this.mCmcEdCallSlot);
        StringBuilder m14 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(m13.toString(), "], mQuantumSecurityInfo: [");
        m14.append(this.mQuantumSecurityInfo);
        StringBuilder m15 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(m14.toString(), "], mConfSessionId: [");
        m15.append(this.mConfSessionId);
        StringBuilder m16 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(m15.toString(), "], mTouchScreenEnabled: [");
        m16.append(this.mTouchScreenEnabled);
        StringBuilder m17 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(m16.toString(), "], mIdcScreenShareRunning: [");
        m17.append(this.mIdcScreenShareRunning);
        StringBuilder m18 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(m17.toString(), "], mIdcArCallRunning: [");
        m18.append(this.mIdcArCallRunning);
        return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(m18.toString(), "]");
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        if (parcel == null) {
            return;
        }
        parcel.writeInt(this.mCallType);
        parcel.writeInt(this.mDirection);
        parcel.writeInt(this.mNetworkType);
        parcel.writeInt(this.mConferenceCall);
        parcel.writeInt(this.mForegroundSessionId);
        if (this.mOriginatingUri != null) {
            parcel.writeInt(1);
            parcel.writeString(this.mOriginatingUri.toString());
        } else {
            parcel.writeInt(0);
        }
        parcel.writeString(this.mDialingNumber);
        parcel.writeString(this.mDialingDevice);
        if (this.mUrn != null) {
            parcel.writeInt(1);
            parcel.writeString(this.mUrn);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeString(this.mCLI);
        parcel.writeString(this.mLetteringText);
        parcel.writeString(this.mAlertInfo);
        parcel.writeString(this.mPhotoRing);
        parcel.writeString(this.mHistoryInfo);
        parcel.writeString(this.mDtmfEvent);
        parcel.writeString(this.mNumberPlus);
        parcel.writeString(this.mConferenceSupported);
        parcel.writeString(this.mIsFocus);
        if (this.mReferredBy != null) {
            parcel.writeInt(1);
            parcel.writeString(this.mReferredBy);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeValue(this.mSipCallId);
        if (this.mSipInviteMsg != null) {
            parcel.writeInt(1);
            parcel.writeString(this.mSipInviteMsg);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeValue(this.mLineMsisdn);
        parcel.writeString(this.mDialogId);
        parcel.writeParcelable(this.mMediaProfile, i);
        parcel.writeInt(this.mIsPullCall ? 1 : 0);
        parcel.writeInt(this.mIsDowngradedVideoCall ? 1 : 0);
        parcel.writeInt(this.mIsDowngradedAtEstablish ? 1 : 0);
        parcel.writeInt(this.mIsSamsungMdmnCall ? 1 : 0);
        parcel.writeInt(this.mHDIcon);
        if (this.mAdditionalSipHeaders != null) {
            parcel.writeInt(1);
            Bundle bundle = new Bundle();
            bundle.putSerializable("extra_header", this.mAdditionalSipHeaders);
            parcel.writeBundle(bundle);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.mHasRemoteVideoCapa ? 1 : 0);
        parcel.writeInt(this.mHasCSFBError ? 1 : 0);
        if (this.mEmergencyRat != null) {
            parcel.writeInt(1);
            parcel.writeString(this.mEmergencyRat);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.mIsVideoCrbt ? 1 : 0);
        parcel.writeInt(this.mIsVideoCrbtValid ? 1 : 0);
        parcel.writeInt(this.mPhoneId);
        parcel.writeInt(this.mRadioTech);
        parcel.writeStringList(this.mP2p);
        parcel.writeInt(this.mCmcBoundSessionId);
        parcel.writeString(this.mReplaceSipCallId);
        parcel.writeInt(this.mCmcType);
        parcel.writeInt(this.mForceCSFB ? 1 : 0);
        if (this.mVerstat != null) {
            parcel.writeInt(1);
            parcel.writeString(this.mVerstat);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeString(this.mOrganization);
        parcel.writeString(this.mCmcDeviceId);
        parcel.writeInt(this.mCmcDtmfKey);
        parcel.writeBundle(this.mComposerData);
        parcel.writeInt(this.mRecordState);
        parcel.writeString(this.mCmcCallTime);
        parcel.writeString(this.mFeatureCaps);
        parcel.writeInt(this.mAudioEarlyMediaDir);
        parcel.writeInt(this.mHasDiversion ? 1 : 0);
        parcel.writeInt(this.mDelayRinging ? 1 : 0);
        parcel.writeString(this.mRejectProtocol);
        parcel.writeInt(this.mRejectCode);
        parcel.writeString(this.mRejectText);
        parcel.writeInt(this.mVCrtIsAlerting ? 1 : 0);
        parcel.writeInt(this.mIsRemoteHeld ? 1 : 0);
        parcel.writeInt(this.mCmcEdCallSlot);
        parcel.writeInt(this.mIsECallConvertedToNormal ? 1 : 0);
        parcel.writeParcelable(this.mQuantumSecurityInfo, i);
        parcel.writeInt(this.mConfSessionId);
        parcel.writeInt(this.mTouchScreenEnabled ? 1 : 0);
        parcel.writeInt(this.mIdcScreenShareRunning ? 1 : 0);
        parcel.writeInt(this.mIdcArCallRunning ? 1 : 0);
    }

    private CallProfile(Parcel parcel) {
        this.mCallType = 0;
        this.mDirection = -1;
        this.mNetworkType = -1;
        this.mConferenceCall = 0;
        this.mForegroundSessionId = -1;
        this.mOriginatingUri = null;
        this.mDialingNumber = null;
        this.mDialingDevice = null;
        this.mUrn = null;
        this.mCLI = null;
        this.mLetteringText = null;
        this.mAlertInfo = null;
        this.mPhotoRing = null;
        this.mHistoryInfo = null;
        this.mDtmfEvent = null;
        this.mNumberPlus = null;
        this.mHasRemoteVideoCapa = false;
        this.mHasDSDAVideoCapa = true;
        this.mModifyHeader = null;
        this.mMTConference = null;
        this.mHDIcon = 0;
        this.mRetryAfterTime = 0;
        this.mMaxConferenceCallUsers = 0;
        this.mLocalHoldTone = null;
        this.mMediaProfile = null;
        this.mReferredBy = null;
        this.mSipCallId = null;
        this.mSipInviteMsg = null;
        this.mLineMsisdn = null;
        this.mDialogId = null;
        this.mEnableScr = true;
        this.mIsPullCall = false;
        this.mIsDowngradedVideoCall = false;
        this.mIsDowngradedAtEstablish = false;
        this.mIsSamsungMdmnCall = false;
        this.mHasCSFBError = false;
        this.mEmergencyRat = null;
        this.mIsVideoCrbt = false;
        this.mIsVideoCrbtValid = false;
        this.mPhoneId = 0;
        this.mRadioTech = 0;
        this.mIsCrossSimCall = false;
        this.mP2p = null;
        this.mCmcBoundSessionId = -1;
        this.mReplaceSipCallId = null;
        this.mCmcType = 0;
        this.mForceCSFB = false;
        this.mVerstat = null;
        this.mOrganization = null;
        this.mCmcDeviceId = null;
        this.mAudioRxTrackId = 0;
        this.mCmcDtmfKey = -1;
        this.mCmcRecordEvent = -1;
        this.mComposerData = null;
        this.mRecordState = -1;
        this.mCmcCallTime = null;
        this.mFeatureCaps = null;
        this.mAudioEarlyMediaDir = 0;
        this.mHasDiversion = false;
        this.mDelayRinging = false;
        this.mRejectCause = 0;
        this.mRejectProtocol = null;
        this.mRejectCode = -1;
        this.mEchoCellId = null;
        this.mRejectText = null;
        this.mEchoCallId = null;
        this.mVCrtIsAlerting = false;
        this.mIsRemoteHeld = false;
        this.mEPSFBsuccess = false;
        this.mCmcEdCallSlot = -1;
        this.mIsECallConvertedToNormal = false;
        this.mQuantumSecurityInfo = null;
        this.mConfSessionId = -1;
        this.mTouchScreenEnabled = false;
        this.mIdcScreenShareRunning = false;
        this.mIdcArCallRunning = false;
        readFromParcel(parcel);
    }

    public CallProfile() {
        this.mCallType = 0;
        this.mDirection = -1;
        this.mNetworkType = -1;
        this.mConferenceCall = 0;
        this.mForegroundSessionId = -1;
        this.mOriginatingUri = null;
        this.mDialingNumber = null;
        this.mDialingDevice = null;
        this.mUrn = null;
        this.mCLI = null;
        this.mLetteringText = null;
        this.mAlertInfo = null;
        this.mPhotoRing = null;
        this.mHistoryInfo = null;
        this.mDtmfEvent = null;
        this.mNumberPlus = null;
        this.mHasRemoteVideoCapa = false;
        this.mHasDSDAVideoCapa = true;
        this.mModifyHeader = null;
        this.mMTConference = null;
        this.mHDIcon = 0;
        this.mRetryAfterTime = 0;
        this.mMaxConferenceCallUsers = 0;
        this.mLocalHoldTone = null;
        this.mMediaProfile = null;
        this.mReferredBy = null;
        this.mSipCallId = null;
        this.mSipInviteMsg = null;
        this.mLineMsisdn = null;
        this.mDialogId = null;
        this.mEnableScr = true;
        this.mIsPullCall = false;
        this.mIsDowngradedVideoCall = false;
        this.mIsDowngradedAtEstablish = false;
        this.mIsSamsungMdmnCall = false;
        this.mHasCSFBError = false;
        this.mEmergencyRat = null;
        this.mIsVideoCrbt = false;
        this.mIsVideoCrbtValid = false;
        this.mPhoneId = 0;
        this.mRadioTech = 0;
        this.mIsCrossSimCall = false;
        this.mP2p = null;
        this.mCmcBoundSessionId = -1;
        this.mReplaceSipCallId = null;
        this.mCmcType = 0;
        this.mForceCSFB = false;
        this.mVerstat = null;
        this.mOrganization = null;
        this.mCmcDeviceId = null;
        this.mAudioRxTrackId = 0;
        this.mCmcDtmfKey = -1;
        this.mCmcRecordEvent = -1;
        this.mComposerData = null;
        this.mRecordState = -1;
        this.mCmcCallTime = null;
        this.mFeatureCaps = null;
        this.mAudioEarlyMediaDir = 0;
        this.mHasDiversion = false;
        this.mDelayRinging = false;
        this.mRejectCause = 0;
        this.mRejectProtocol = null;
        this.mRejectCode = -1;
        this.mEchoCellId = null;
        this.mRejectText = null;
        this.mEchoCallId = null;
        this.mVCrtIsAlerting = false;
        this.mIsRemoteHeld = false;
        this.mEPSFBsuccess = false;
        this.mCmcEdCallSlot = -1;
        this.mIsECallConvertedToNormal = false;
        this.mQuantumSecurityInfo = null;
        this.mConfSessionId = -1;
        this.mTouchScreenEnabled = false;
        this.mIdcScreenShareRunning = false;
        this.mIdcArCallRunning = false;
        this.mMediaProfile = new MediaProfile();
        this.mQuantumSecurityInfo = new QuantumSecurityInfo();
    }
}
