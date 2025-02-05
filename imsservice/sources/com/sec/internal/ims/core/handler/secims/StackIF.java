package com.sec.internal.ims.core.handler.secims;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import com.google.flatbuffers.FlatBufferBuilder;
import com.sec.ims.Dialog;
import com.sec.ims.extensions.Extensions;
import com.sec.ims.options.Capabilities;
import com.sec.ims.presence.DeviceTuple;
import com.sec.ims.presence.PersonTuple;
import com.sec.ims.presence.PresenceInfo;
import com.sec.ims.presence.ServiceTuple;
import com.sec.ims.util.ImsUri;
import com.sec.ims.util.SipError;
import com.sec.internal.constants.Mno;
import com.sec.internal.constants.ims.SipReason;
import com.sec.internal.constants.ims.XmlElement;
import com.sec.internal.constants.ims.config.RcsConfig;
import com.sec.internal.constants.ims.entitilement.SoftphoneContract;
import com.sec.internal.constants.ims.gls.LocationInfo;
import com.sec.internal.constants.ims.servicemodules.volte2.IMSMediaEvent;
import com.sec.internal.helper.AsyncResult;
import com.sec.internal.helper.KeyPairManager;
import com.sec.internal.helper.RegistrantList;
import com.sec.internal.helper.os.Debug;
import com.sec.internal.helper.os.DeviceUtil;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.AdditionalContents;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Element;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Element_.Attribute;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Id;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.ImsBuffer;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Notify;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Notify_.AlarmWakeUp;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Notify_.CallSendCmcInfo;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Notify_.CallStatus;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Notify_.CancelAlarm;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Notify_.CdpnInfo;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Notify_.ConfCallChanged;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Notify_.ConfCallChanged_.Participant;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Notify_.ContactActivated;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Notify_.ContactUriInfo;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Notify_.CurrentLocationDiscoveryDuringEmergencyCall;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Notify_.DTMFDataEvent;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Notify_.DedicatedBearerEvent;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Notify_.DialogEvent;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Notify_.DnsResponse;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Notify_.DumpMessage;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Notify_.EcholocateMsg;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Notify_.IncomingCall;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Notify_.ModifyCallData;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Notify_.ModifyVideoData;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Notify_.NotifyCmcRecordEventData;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Notify_.NotifyVideoEventData;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Notify_.QuantumSecurityStatusEvent;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Notify_.ReceiveSmsNotification;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Notify_.ReferReceived;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Notify_.ReferStatus;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Notify_.RegInfoChanged;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Notify_.RegistrationAuth;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Notify_.RegistrationImpu;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Notify_.RegistrationStatus;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Notify_.RrcConnectionEvent;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Notify_.RtpLossRateNoti;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Notify_.SSGetGbaKey;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Notify_.SignDigestResponse;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Notify_.SipMessage;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Notify_.SmsRpAckNotification;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Notify_.SubscribeStatus;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Notify_.TextDataEvent;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Notify_.UpdateRouteTable;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Notify_.X509CertVerifyRequest;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Notify_.XCapMessage;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Notify_.XqMessage;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.RegiType;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.ReqMsg;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Request;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Request_.RequestAlarmWakeUp;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Request_.RequestDeleteTcpClientSocket;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Request_.RequestNtpTimeOffset;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Request_.RequestOpenSipDialog;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Request_.RequestOptionsCapExchange;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Request_.RequestOptionsSendCmcCheckMsg;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Request_.RequestOptionsSendErrorResponse;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Request_.RequestOptionsSendResponse;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Request_.RequestPresencePublish;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Request_.RequestPresenceSubscribe;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Request_.RequestPresenceUnpublish;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Request_.RequestReceiveSmsResp;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Request_.RequestRegistration;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Request_.RequestRejectCall;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Request_.RequestRtpStatsToStack;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Request_.RequestSendMediaEvent;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Request_.RequestSendMsg;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Request_.RequestSendRelayEvent;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Request_.RequestSendRpAckResp;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Request_.RequestSendSip;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Request_.RequestSetVowifi5gsaMode;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Request_.RequestSilentLogEnabled;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Request_.RequestUpdateAirplaneMode;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Request_.RequestUpdateCmcHotspotState;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Request_.RequestUpdateGeolocation;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Request_.RequestUpdateNrSaModeOnStart;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Request_.RequestUpdatePani;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Request_.RequestUpdateRat;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Request_.RequestUpdateTimeInPlani;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Request_.RequestX509CertVerifyResult;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Response;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Response_.CallResponse;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Response_.CloseSessionResponse;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Response_.CshGeneralResponse;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Response_.GeneralResponse;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Response_.SendEucResponseResponse;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Response_.SendImMessageResponse;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Response_.SendImNotiResponse;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Response_.SendMessageRevokeInternalResponse;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Response_.SendSlmResponse;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Response_.SendSmsResponse;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Response_.SipdialogGeneralResponse;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Response_.StartMediaResponse;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Response_.StartSessionResponse;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Response_.UpdateParticipantsResponse;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Response_.XdmGeneralResponse;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.ServiceTuple_.Status;
import com.sec.internal.ims.util.ImsUtil;
import com.sec.internal.interfaces.ims.IImsFramework;
import com.sec.internal.log.IMSLog;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public class StackIF implements IStackIF {
    private static final String LOG_TAG = "StackIF";
    private MiscEventListener mMiscListener;
    private static final Pattern IPV4_PATTERN = Pattern.compile("(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}");
    private static final Pattern IPV6_PATTERN = Pattern.compile("((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)::((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)");
    private static final Pattern IMPU_SIP_PATTERN = Pattern.compile("sip:[#*0-9+-]*[0-9+-]+");
    private static final Pattern IMPU_TEL_PATTERN = Pattern.compile("tel:[#*0-9+-]*[0-9+-]+");
    private static final Pattern IMEI_PATTERN = Pattern.compile("imei:+[0-9+-]+");
    private static final Pattern USERNAME_PATTERN = Pattern.compile("username=\"+[^\"]+");
    private static final Pattern P_ACCESS_NETWORK_PATTERN = Pattern.compile("P-Access-Network-Info:+[^\n]+");
    private static final Pattern SDP_O_LINE_PATTERN = Pattern.compile("o=+[0-9:+-]+");
    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("\"+[0-9+-]+\"");
    private static final Pattern PHONE_NUMBER_XML_PATTERN = Pattern.compile(">[0-9+-]{4,}<");
    private static final Pattern XCAP_USER_PATTERN = Pattern.compile("target>+.+</.*target");
    private static final Pattern FROM_PATTERN = Pattern.compile("From: +[#*0-9+-]*[0-9+-]+");
    private static final Pattern TO_PATTERN = Pattern.compile("To: +[#*0-9+-]*[0-9+-]+");
    private static final Pattern SESSION_DESCRIPTION_PATTERN = Pattern.compile("session-description>.+</session-description");
    private static final Pattern GROUPCHAT_ALIAS_PATTERN = Pattern.compile("From: \"+[^\"]+");
    private static final Pattern GROUPCHAT_SUBJECT_PATTERN = Pattern.compile("Subject:+[^\n]+");
    private static final Pattern TEXTPLAIN_HEADER_PATTERN = Pattern.compile("Content-type: +text/plain");
    private static final Pattern TEXTPLAIN_CONTENT_PATTERN = Pattern.compile("\n\n.[^\n]+");
    private static volatile StackIF sInstance = null;
    private List<ImsRequest> mRequestList = new ArrayList();

    @SuppressLint({"UseSparseArrays"})
    private Map<Integer, StackEventListener> mUaListenerList = new HashMap();
    private Map<Integer, String> mUaRegisterResponseRawSip = new HashMap();
    private final RegistrantList mIshRegistrants = new RegistrantList();
    private final RegistrantList mVshRegistrants = new RegistrantList();
    private int mHandle = -1;
    private List<SipDebugMessage> mSipHistory = new ArrayList();
    private List<DumpRequest> mStackDumpData = new ArrayList();
    private AtomicInteger sNextSerial = new AtomicInteger(0);
    private IImsFramework mImsFramework = null;
    private final Object mLock = new Object();
    private final RegistrantList mCallStatusRegistrants = new RegistrantList();
    private final RegistrantList mNewIncomingCallRegistrants = new RegistrantList();
    private final RegistrantList mModifyCallRegistrants = new RegistrantList();
    private final RegistrantList mModifyVideoRegistrants = new RegistrantList();
    private final RegistrantList mVideoEventRegistrants = new RegistrantList();
    private final RegistrantList mConferenceUpdateRegistrants = new RegistrantList();
    private final RegistrantList mNewIncomingSmsRegistrants = new RegistrantList();
    private final RegistrantList mSmsRpAckRegistrants = new RegistrantList();
    private final RegistrantList mReferReceivedRegistrants = new RegistrantList();
    private final RegistrantList mReferStatusRegistrants = new RegistrantList();
    private final RegistrantList mImRegistrants = new RegistrantList();
    private final RegistrantList mImdnRegistrants = new RegistrantList();
    private final RegistrantList mSlmRegistrants = new RegistrantList();
    private final RegistrantList mPresenceRegistrants = new RegistrantList();
    private final RegistrantList mXdmRegistrants = new RegistrantList();
    private final RegistrantList mOptionsRegistrants = new RegistrantList();
    private final RegistrantList mDialogEventRegistrants = new RegistrantList();
    private final RegistrantList mCdpnInfoRegistrants = new RegistrantList();
    private final RegistrantList mDedicatedBearerEventRegistrants = new RegistrantList();
    private final RegistrantList mRrcConnectionEventRegistrants = new RegistrantList();
    private final RegistrantList mEcholocateRegistrants = new RegistrantList();
    private final RegistrantList mRawSipIncomingRegistrants = new RegistrantList();
    private final RegistrantList mRawSipOutgoingRegistrants = new RegistrantList();
    private final RegistrantList mSSEventRegistrants = new RegistrantList();
    private final RegistrantList mDtmfRegistrants = new RegistrantList();
    private final RegistrantList mRtpLossRateNotiRegistrants = new RegistrantList();
    private final RegistrantList mEucrRegistrants = new RegistrantList();
    private final RegistrantList mXqMtripRegistrants = new RegistrantList();
    private final RegistrantList mTextRegistrants = new RegistrantList();
    private final RegistrantList mSIPMSGRegistrants = new RegistrantList();
    private final RegistrantList mCmcRecordEventRegistrants = new RegistrantList();
    private final RegistrantList mCmcInfoRegistrants = new RegistrantList();
    private final RegistrantList mCurrentLocationDiscoveryDuringEmergencyCallRegistrants = new RegistrantList();
    private final RegistrantList mQuantumSecurityStatusEventRegistrants = new RegistrantList();
    private final RegistrantList mIdcServiceEventRegistrants = new RegistrantList();

    public interface MiscEventListener {
        void onAlarmCancelled(int i);

        void onAlarmRequested(int i, int i2, boolean z);
    }

    private native void initCmc(Object obj);

    private native void initStack(Object obj);

    private native void processCommandBuffer(byte[] bArr, int i);

    public static void setMockInstance(StackIF stackIF) {
        sInstance = stackIF;
    }

    StackIF() {
    }

    private void init() {
        try {
            System.loadLibrary("sec-ims");
        } catch (UnsatisfiedLinkError e) {
            e.printStackTrace();
        }
    }

    public static synchronized StackIF getInstance() {
        StackIF stackIF;
        synchronized (StackIF.class) {
            if (sInstance == null) {
                synchronized (StackIF.class) {
                    if (sInstance == null) {
                        sInstance = new StackIF();
                        sInstance.init();
                    }
                }
            }
            stackIF = sInstance;
        }
        return stackIF;
    }

    public void setImsFramework(IImsFramework iImsFramework) {
        this.mImsFramework = iImsFramework;
    }

    public void initMediaJni(Object obj) {
        initStack(obj);
    }

    public void initCmcJni(Object obj) {
        initCmc(obj);
    }

    @Override // com.sec.internal.ims.core.handler.secims.IStackIF
    public void registerUaListener(int i, StackEventListener stackEventListener) {
        Log.i(LOG_TAG, "registerUaListener Handle : " + i);
        this.mUaListenerList.put(Integer.valueOf(i), stackEventListener);
    }

    @Override // com.sec.internal.ims.core.handler.secims.IStackIF
    public void unRegisterUaListener(int i) {
        Log.i(LOG_TAG, "unRegisterUaListener Handle : " + i);
        this.mUaListenerList.remove(Integer.valueOf(i));
    }

    public void registerCallStatusEvent(Handler handler, int i, Object obj) {
        Log.i(LOG_TAG, "registerCallStatusEvent:");
        this.mCallStatusRegistrants.addUnique(handler, i, obj);
    }

    public void registerDtmfEvent(Handler handler, int i, Object obj) {
        this.mDtmfRegistrants.addUnique(handler, i, obj);
    }

    public void registerTextEvent(Handler handler, int i, Object obj) {
        this.mTextRegistrants.addUnique(handler, i, obj);
    }

    public void registerSIPMSGEvent(Handler handler, int i, Object obj) {
        this.mSIPMSGRegistrants.addUnique(handler, i, obj);
    }

    public void registerNewIncomingCallEvent(Handler handler, int i, Object obj) {
        Log.i(LOG_TAG, "registerNewIncomingCallEvent:");
        this.mNewIncomingCallRegistrants.addUnique(handler, i, obj);
    }

    public void registerModifyCallEvent(Handler handler, int i, Object obj) {
        Log.i(LOG_TAG, "registerModifyCallEvent:");
        this.mModifyCallRegistrants.addUnique(handler, i, obj);
    }

    public void registerModifyVideoEvent(Handler handler, int i, Object obj) {
        Log.i(LOG_TAG, "registerModifyVideoEvent:");
        this.mModifyVideoRegistrants.addUnique(handler, i, obj);
    }

    public void registerCurrentLocationDiscoveryDuringEmergencyCallEvent(Handler handler, int i, Object obj) {
        Log.i(LOG_TAG, "registerCurrentLocationDiscoveryDuringEmergencyCallEvent:");
        this.mCurrentLocationDiscoveryDuringEmergencyCallRegistrants.addUnique(handler, i, obj);
    }

    public void registerVideoEvent(Handler handler, int i, Object obj) {
        Log.i(LOG_TAG, "registerVideoEvent:");
        this.mVideoEventRegistrants.addUnique(handler, i, obj);
    }

    public void registerCmcRecordEvent(Handler handler, int i, Object obj) {
        Log.i(LOG_TAG, "registerCmcRecordEvent:");
        this.mCmcRecordEventRegistrants.addUnique(handler, i, obj);
    }

    public void registerConferenceUpdateEvent(Handler handler, int i, Object obj) {
        Log.i(LOG_TAG, "registerConferenceUpdateEvent:");
        this.mConferenceUpdateRegistrants.addUnique(handler, i, obj);
    }

    public void registerNewIncomingSmsEvent(Handler handler, int i, Object obj) {
        Log.i(LOG_TAG, "registerNewIncomingSmsEvent: ");
        this.mNewIncomingSmsRegistrants.addUnique(handler, i, obj);
    }

    public void registerSmsRpAckEvent(Handler handler, int i, Object obj) {
        Log.i(LOG_TAG, "registerSmsRpAckEvent:");
        this.mSmsRpAckRegistrants.addUnique(handler, i, obj);
    }

    public void registerReferReceivedEvent(Handler handler, int i, Object obj) {
        Log.i(LOG_TAG, "registerReferReceivedEvent: ");
        this.mReferReceivedRegistrants.addUnique(handler, i, obj);
    }

    public void registerReferStatusEvent(Handler handler, int i, Object obj) {
        this.mReferStatusRegistrants.addUnique(handler, i, obj);
    }

    public void registerImHandler(Handler handler, int i, Object obj) {
        this.mImRegistrants.addUnique(handler, i, obj);
    }

    public void registerImdnHandler(Handler handler, int i, Object obj) {
        this.mImdnRegistrants.addUnique(handler, i, obj);
    }

    public void registerSlmHandler(Handler handler, int i, Object obj) {
        this.mSlmRegistrants.addUnique(handler, i, obj);
    }

    public void registerPresenceEvent(Handler handler, int i, Object obj) {
        Log.i(LOG_TAG, "registerPresenceEvent: ");
        this.mPresenceRegistrants.addUnique(handler, i, obj);
    }

    public void registerOptionsHandler(Handler handler, int i, Object obj) {
        Log.i(LOG_TAG, "registerOptionsHandler: ");
        this.mOptionsRegistrants.addUnique(handler, i, obj);
    }

    public void registerDialogEvent(Handler handler, int i, Object obj) {
        Log.i(LOG_TAG, "registerDialogEvent: ");
        this.mDialogEventRegistrants.addUnique(handler, i, obj);
    }

    public void registerCdpnInfoEvent(Handler handler, int i, Object obj) {
        Log.i(LOG_TAG, "registerCdpnInfoEvent:");
        this.mCdpnInfoRegistrants.addUnique(handler, i, obj);
    }

    public void registerIshEvent(Handler handler, int i, Object obj) {
        this.mIshRegistrants.addUnique(handler, i, obj);
    }

    public void registerVshEvent(Handler handler, int i, Object obj) {
        this.mVshRegistrants.addUnique(handler, i, obj);
    }

    public void registerDedicatedBearerEvent(Handler handler, int i, Object obj) {
        Log.i(LOG_TAG, "registerDedicatedBearerEvent:");
        this.mDedicatedBearerEventRegistrants.addUnique(handler, i, obj);
    }

    public void registerForRrcConnectionEvent(Handler handler, int i, Object obj) {
        Log.i(LOG_TAG, "registerForRrcConnectionEvent:");
        this.mRrcConnectionEventRegistrants.addUnique(handler, i, obj);
    }

    public void registerQuantumSecurityStatusEvent(Handler handler, int i, Object obj) {
        Log.i(LOG_TAG, "registerQuantumSecurityStatusEvent:");
        this.mQuantumSecurityStatusEventRegistrants.addUnique(handler, i, obj);
    }

    public void registerEcholocateEvent(Handler handler, int i, Object obj) {
        Log.i(LOG_TAG, "registerEcholocateEvent:");
        this.mEcholocateRegistrants.addUnique(handler, i, obj);
    }

    public void registerRawSipIncomingEvent(Handler handler, int i, Object obj) {
        Log.i(LOG_TAG, "registerRawSipIncomingEvent:");
        this.mRawSipIncomingRegistrants.addUnique(handler, i, obj);
    }

    public void registerRawSipOutgoingEvent(Handler handler, int i, Object obj) {
        Log.i(LOG_TAG, "registerRawSipOutgoingEvent:");
        this.mRawSipOutgoingRegistrants.addUnique(handler, i, obj);
    }

    public void registerSSEventRegistrants(Handler handler, int i, Object obj) {
        Log.i(LOG_TAG, "registerRawSipEvent: ");
        this.mSSEventRegistrants.addUnique(handler, i, obj);
    }

    public void registerRtpLossRateNoti(Handler handler, int i, Object obj) {
        Log.i(LOG_TAG, "registerRtpLossRate : ");
        this.mRtpLossRateNotiRegistrants.addUnique(handler, i, obj);
    }

    public void registerEucrEvent(Handler handler, int i, Object obj) {
        Log.i(LOG_TAG, "registerEucrEvent");
        this.mEucrRegistrants.addUnique(handler, i, obj);
    }

    public void registerXqMtrip(Handler handler, int i, Object obj) {
        this.mXqMtripRegistrants.addUnique(handler, i, obj);
    }

    public void registerCmcInfo(Handler handler, int i, Object obj) {
        this.mCmcInfoRegistrants.addUnique(handler, i, obj);
    }

    public void registerMiscListener(MiscEventListener miscEventListener) {
        this.mMiscListener = miscEventListener;
    }

    @Override // com.sec.internal.ims.core.handler.secims.IStackIF
    public void send(ResipStackRequest resipStackRequest) {
        sendRequest(resipStackRequest.mRequest, resipStackRequest.mOffset, resipStackRequest.mCallback);
    }

    private void send(ImsRequest imsRequest) {
        synchronized (this.mRequestList) {
            this.mRequestList.add(imsRequest);
        }
        synchronized (this.mLock) {
            byte[] sizedByteArray = imsRequest.getReqBuffer().sizedByteArray();
            Log.i("SECIMSJ", serialString(imsRequest.mTid) + "> " + sizedByteArray.length);
            processCommandBuffer(sizedByteArray, sizedByteArray.length);
        }
    }

    private int updatePaniReq(FlatBufferBuilder flatBufferBuilder, long j, List<String> list) {
        int createString = flatBufferBuilder.createString(list.get(0));
        int createString2 = list.size() > 1 ? flatBufferBuilder.createString(list.get(1)) : -1;
        RequestUpdatePani.startRequestUpdatePani(flatBufferBuilder);
        RequestUpdatePani.addHandle(flatBufferBuilder, j);
        RequestUpdatePani.addPani(flatBufferBuilder, createString);
        if (createString2 != -1) {
            RequestUpdatePani.addLastPani(flatBufferBuilder, createString2);
        }
        return RequestUpdatePani.endRequestUpdatePani(flatBufferBuilder);
    }

    private int ratReq(FlatBufferBuilder flatBufferBuilder, long j, long j2) {
        RequestUpdateRat.startRequestUpdateRat(flatBufferBuilder);
        RequestUpdateRat.addHandle(flatBufferBuilder, j);
        RequestUpdateRat.addRat(flatBufferBuilder, j2);
        return RequestUpdateRat.endRequestUpdateRat(flatBufferBuilder);
    }

    private int setVowifi5gsaModeReq(FlatBufferBuilder flatBufferBuilder, long j, int i) {
        RequestSetVowifi5gsaMode.startRequestSetVowifi5gsaMode(flatBufferBuilder);
        RequestSetVowifi5gsaMode.addHandle(flatBufferBuilder, j);
        RequestSetVowifi5gsaMode.addVowifi5gsaMode(flatBufferBuilder, i);
        return RequestSetVowifi5gsaMode.endRequestSetVowifi5gsaMode(flatBufferBuilder);
    }

    private int planiTimeReq(FlatBufferBuilder flatBufferBuilder, long j, long j2) {
        RequestUpdateTimeInPlani.startRequestUpdateTimeInPlani(flatBufferBuilder);
        RequestUpdateTimeInPlani.addHandle(flatBufferBuilder, j);
        RequestUpdateTimeInPlani.addTime(flatBufferBuilder, j2);
        return RequestUpdateTimeInPlani.endRequestUpdateTimeInPlani(flatBufferBuilder);
    }

    private void sendRequest(StackRequest stackRequest, Message message) {
        sendRequest(stackRequest.getBuilder(), stackRequest.getOffset(), message);
    }

    private void sendRequest(FlatBufferBuilder flatBufferBuilder, int i, Message message) {
        ImsBuffer.startImsBuffer(flatBufferBuilder);
        int andIncrement = this.sNextSerial.getAndIncrement();
        ImsBuffer.addTrid(flatBufferBuilder, andIncrement);
        ImsBuffer.addMsgType(flatBufferBuilder, (byte) 1);
        ImsBuffer.addMsg(flatBufferBuilder, i);
        flatBufferBuilder.finish(ImsBuffer.endImsBuffer(flatBufferBuilder));
        ImsRequest obtain = ImsRequest.obtain(flatBufferBuilder, message);
        obtain.mTid = andIncrement;
        send(obtain);
    }

    @Override // com.sec.internal.ims.core.handler.secims.IStackIF
    public void createUA(UaProfile uaProfile, Message message) {
        Log.i(LOG_TAG, "createUA:");
        sendRequest(RegistrationRequestBuilder.makeCreateUA(uaProfile), message);
    }

    @Override // com.sec.internal.ims.core.handler.secims.IStackIF
    public void deleteUA(int i, Message message) {
        Log.i(LOG_TAG, "deleteUA: handle " + i);
        sendRequest(RegistrationRequestBuilder.makeDeleteUA(i), message);
    }

    @Override // com.sec.internal.ims.core.handler.secims.IStackIF
    public void register(int i, String str, int i2, int i3, List<String> list, List<String> list2, Capabilities capabilities, List<String> list3, String str2, String str3, boolean z, String str4, boolean z2, Message message) {
        Log.i(LOG_TAG, "register: handle " + i + " pcscfAddr " + str + " port " + i2 + " service " + list + " imMsgTech " + str4);
        this.mHandle = i;
        sendRequest(RegistrationRequestBuilder.makeRegister(i, str, i2, i3, list, list2, capabilities, list3, str2, str3, z, str4, z2), message);
    }

    @Override // com.sec.internal.ims.core.handler.secims.IStackIF
    public void networkSuspended(int i, boolean z) {
        Log.i(LOG_TAG, "register: handle " + i + " state " + z);
        sendRequest(RegistrationRequestBuilder.makeNetworkSuspended(i, z), null);
    }

    @Override // com.sec.internal.ims.core.handler.secims.IStackIF
    public void sendAuthResponse(int i, int i2, String str) {
        Log.i(LOG_TAG, "sendAuthResponse: handle " + i + " tid " + i2 + " response " + str);
        sendRequest(RegistrationRequestBuilder.makeSendAuthResponse(i, i2, str), null);
    }

    @Override // com.sec.internal.ims.core.handler.secims.IStackIF
    public void sendSignDigestResponse(int i, String str) {
        Log.i(LOG_TAG, "sendSignDigestResponse: handle " + i);
        sendRequest(RegistrationRequestBuilder.makeSendSignDigestResponse(i, str), null);
    }

    @Override // com.sec.internal.ims.core.handler.secims.IStackIF
    public void setPreferredImpu(int i, String str) {
        Log.i(LOG_TAG, "setPreferredImpu: handle " + i + " impu " + hidePrivateInfoFromSipMsg(str));
        sendRequest(RegistrationRequestBuilder.makeSetPreferredImpu(i, str), null);
    }

    @Override // com.sec.internal.ims.core.handler.secims.IStackIF
    public void updatePani(int i, List<String> list, Message message) {
        IMSLog.s(LOG_TAG, "updatePani: " + i + " pani: " + list);
        FlatBufferBuilder flatBufferBuilder = new FlatBufferBuilder(0);
        int updatePaniReq = updatePaniReq(flatBufferBuilder, (long) i, list);
        Request.startRequest(flatBufferBuilder);
        Request.addReqid(flatBufferBuilder, 600);
        Request.addReqType(flatBufferBuilder, (byte) 68);
        Request.addReq(flatBufferBuilder, updatePaniReq);
        sendRequest(flatBufferBuilder, Request.endRequest(flatBufferBuilder), message);
    }

    @Override // com.sec.internal.ims.core.handler.secims.IStackIF
    public void updateTimeInPlani(int i, long j) {
        Log.i(LOG_TAG, "updateTimeInPlani: " + i + " time: " + j);
        FlatBufferBuilder flatBufferBuilder = new FlatBufferBuilder(0);
        int planiTimeReq = planiTimeReq(flatBufferBuilder, (long) i, j);
        Request.startRequest(flatBufferBuilder);
        Request.addReqid(flatBufferBuilder, Id.REQUEST_UPDATE_TIME_IN_PLANI);
        Request.addReqType(flatBufferBuilder, (byte) 84);
        Request.addReq(flatBufferBuilder, planiTimeReq);
        sendRequest(flatBufferBuilder, Request.endRequest(flatBufferBuilder), null);
    }

    @Override // com.sec.internal.ims.core.handler.secims.IStackIF
    public void updateRat(int i, int i2) {
        Log.i(LOG_TAG, "updateRat: " + i + " network: " + i2);
        FlatBufferBuilder flatBufferBuilder = new FlatBufferBuilder(0);
        int ratReq = ratReq(flatBufferBuilder, (long) i, (long) i2);
        Request.startRequest(flatBufferBuilder);
        Request.addReqid(flatBufferBuilder, Id.REQUEST_UPDATE_RAT);
        Request.addReqType(flatBufferBuilder, (byte) 83);
        Request.addReq(flatBufferBuilder, ratReq);
        sendRequest(flatBufferBuilder, Request.endRequest(flatBufferBuilder), null);
    }

    @Override // com.sec.internal.ims.core.handler.secims.IStackIF
    public void updateGeolocation(int i, LocationInfo locationInfo) {
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        Log.i(LOG_TAG, ": " + i);
        FlatBufferBuilder flatBufferBuilder = new FlatBufferBuilder(0);
        int createString = !TextUtils.isEmpty(locationInfo.mLatitude) ? flatBufferBuilder.createString(locationInfo.mLatitude) : -1;
        int createString2 = !TextUtils.isEmpty(locationInfo.mLongitude) ? flatBufferBuilder.createString(locationInfo.mLongitude) : -1;
        int createString3 = !TextUtils.isEmpty(locationInfo.mAltitude) ? flatBufferBuilder.createString(locationInfo.mAltitude) : -1;
        int createString4 = !TextUtils.isEmpty(locationInfo.mAccuracy) ? flatBufferBuilder.createString(locationInfo.mAccuracy) : -1;
        int createString5 = !TextUtils.isEmpty(locationInfo.mVerticalAxis) ? flatBufferBuilder.createString(locationInfo.mVerticalAxis) : -1;
        int createString6 = !TextUtils.isEmpty(locationInfo.mOrientation) ? flatBufferBuilder.createString(locationInfo.mOrientation) : -1;
        int createString7 = !TextUtils.isEmpty(locationInfo.mProviderType) ? flatBufferBuilder.createString(locationInfo.mProviderType) : -1;
        int createString8 = !TextUtils.isEmpty(locationInfo.mRetentionExpires) ? flatBufferBuilder.createString(locationInfo.mRetentionExpires) : -1;
        int createString9 = !TextUtils.isEmpty(locationInfo.mSRSName) ? flatBufferBuilder.createString(locationInfo.mSRSName) : -1;
        int createString10 = !TextUtils.isEmpty(locationInfo.mRadiusUOM) ? flatBufferBuilder.createString(locationInfo.mRadiusUOM) : -1;
        int createString11 = !TextUtils.isEmpty(locationInfo.mOS) ? flatBufferBuilder.createString(locationInfo.mOS) : -1;
        int createString12 = !TextUtils.isEmpty(locationInfo.mDeviceId) ? flatBufferBuilder.createString(locationInfo.mDeviceId) : -1;
        int createString13 = !TextUtils.isEmpty(locationInfo.mCountry) ? flatBufferBuilder.createString(locationInfo.mCountry) : -1;
        if (TextUtils.isEmpty(locationInfo.mA1)) {
            i2 = createString;
            i3 = -1;
        } else {
            i3 = flatBufferBuilder.createString(locationInfo.mA1);
            i2 = createString;
        }
        if (TextUtils.isEmpty(locationInfo.mA3)) {
            i4 = createString2;
            i5 = -1;
        } else {
            i5 = flatBufferBuilder.createString(locationInfo.mA3);
            i4 = createString2;
        }
        if (TextUtils.isEmpty(locationInfo.mA6)) {
            i6 = createString3;
            i7 = -1;
        } else {
            i7 = flatBufferBuilder.createString(locationInfo.mA6);
            i6 = createString3;
        }
        if (TextUtils.isEmpty(locationInfo.mHNO)) {
            i8 = createString4;
            i9 = -1;
        } else {
            i9 = flatBufferBuilder.createString(locationInfo.mHNO);
            i8 = createString4;
        }
        if (TextUtils.isEmpty(locationInfo.mPC)) {
            i10 = createString5;
            i11 = -1;
        } else {
            i11 = flatBufferBuilder.createString(locationInfo.mPC);
            i10 = createString5;
        }
        int createString14 = !TextUtils.isEmpty(locationInfo.mLocationTime) ? flatBufferBuilder.createString(locationInfo.mLocationTime) : -1;
        RequestUpdateGeolocation.startRequestUpdateGeolocation(flatBufferBuilder);
        if (createString14 != -1) {
            RequestUpdateGeolocation.addLocationtime(flatBufferBuilder, createString14);
        }
        if (i11 != -1) {
            RequestUpdateGeolocation.addPc(flatBufferBuilder, i11);
        }
        if (i9 != -1) {
            RequestUpdateGeolocation.addHno(flatBufferBuilder, i9);
        }
        if (i7 != -1) {
            RequestUpdateGeolocation.addA6(flatBufferBuilder, i7);
        }
        if (i5 != -1) {
            RequestUpdateGeolocation.addA3(flatBufferBuilder, i5);
        }
        if (i3 != -1) {
            RequestUpdateGeolocation.addA1(flatBufferBuilder, i3);
        }
        if (createString13 != -1) {
            RequestUpdateGeolocation.addCountry(flatBufferBuilder, createString13);
        }
        if (createString12 != -1) {
            RequestUpdateGeolocation.addDeviceid(flatBufferBuilder, createString12);
        }
        if (createString11 != -1) {
            RequestUpdateGeolocation.addOs(flatBufferBuilder, createString11);
        }
        if (createString10 != -1) {
            RequestUpdateGeolocation.addRadiusuom(flatBufferBuilder, createString10);
        }
        if (createString9 != -1) {
            RequestUpdateGeolocation.addSrsname(flatBufferBuilder, createString9);
        }
        if (createString8 != -1) {
            RequestUpdateGeolocation.addRetentionexpires(flatBufferBuilder, createString8);
        }
        if (createString7 != -1) {
            RequestUpdateGeolocation.addProvidertype(flatBufferBuilder, createString7);
        }
        if (createString6 != -1) {
            RequestUpdateGeolocation.addOrientation(flatBufferBuilder, createString6);
        }
        int i12 = i10;
        if (i12 != -1) {
            RequestUpdateGeolocation.addVerticalaxis(flatBufferBuilder, i12);
        }
        int i13 = i8;
        if (i13 != -1) {
            RequestUpdateGeolocation.addAccuracy(flatBufferBuilder, i13);
        }
        int i14 = i6;
        if (i14 != -1) {
            RequestUpdateGeolocation.addAltitude(flatBufferBuilder, i14);
        }
        int i15 = i4;
        if (i15 != -1) {
            RequestUpdateGeolocation.addLongitude(flatBufferBuilder, i15);
        }
        int i16 = i2;
        if (i16 != -1) {
            RequestUpdateGeolocation.addLatitude(flatBufferBuilder, i16);
        }
        RequestUpdateGeolocation.addHandle(flatBufferBuilder, i);
        int endRequestUpdateGeolocation = RequestUpdateGeolocation.endRequestUpdateGeolocation(flatBufferBuilder);
        Request.startRequest(flatBufferBuilder);
        Request.addReqid(flatBufferBuilder, Id.REQUEST_UPDATE_GEOLOCATION);
        Request.addReqType(flatBufferBuilder, ReqMsg.request_update_geolocation);
        Request.addReq(flatBufferBuilder, endRequestUpdateGeolocation);
        sendRequest(flatBufferBuilder, Request.endRequest(flatBufferBuilder), null);
    }

    @Override // com.sec.internal.ims.core.handler.secims.IStackIF
    public void updateVceConfig(int i, boolean z) {
        Log.i(LOG_TAG, "updateVceConfig: handle: " + i + ", vceEnabled: " + z);
        sendRequest(RegistrationRequestBuilder.makeUpdateVceConfig(i, z), null);
    }

    @Override // com.sec.internal.ims.core.handler.secims.IStackIF
    public void setVowifi5gsaMode(int i, int i2) {
        Log.i(LOG_TAG, "setVowifi5gsaMode: " + i + " vowifi5gsaMode: " + i2);
        FlatBufferBuilder flatBufferBuilder = new FlatBufferBuilder(0);
        int vowifi5gsaModeReq = setVowifi5gsaModeReq(flatBufferBuilder, (long) i, i2);
        Request.startRequest(flatBufferBuilder);
        Request.addReqid(flatBufferBuilder, 115);
        Request.addReqType(flatBufferBuilder, ReqMsg.request_set_vowifi_5gsa_mode);
        Request.addReq(flatBufferBuilder, vowifi5gsaModeReq);
        sendRequest(flatBufferBuilder, Request.endRequest(flatBufferBuilder), null);
    }

    public void sendDmState(int i, boolean z) {
        Log.i("StackIF[" + i + "]", "sendDmState():  mode: " + z);
        sendRequest(RegistrationRequestBuilder.makeSendDmState(i, z), null);
    }

    public void sendSip(int i, String str, Message message) {
        IMSLog.s(LOG_TAG, "sendSip: sipMessage: " + str);
        FlatBufferBuilder flatBufferBuilder = new FlatBufferBuilder(0);
        int createString = !TextUtils.isEmpty(str) ? flatBufferBuilder.createString(str) : -1;
        RequestSendSip.startRequestSendSip(flatBufferBuilder);
        if (createString != -1) {
            RequestSendSip.addSipMessage(flatBufferBuilder, createString);
        }
        RequestSendSip.addHandle(flatBufferBuilder, i);
        int endRequestSendSip = RequestSendSip.endRequestSendSip(flatBufferBuilder);
        Request.startRequest(flatBufferBuilder);
        Request.addReqid(flatBufferBuilder, Id.REQUEST_SIP_DIALOG_SEND_SIP);
        Request.addReqType(flatBufferBuilder, ReqMsg.request_send_sip);
        Request.addReq(flatBufferBuilder, endRequestSendSip);
        sendRequest(flatBufferBuilder, Request.endRequest(flatBufferBuilder), message);
    }

    public void openSipDialog(boolean z) {
        Log.i(LOG_TAG, "openSipDialog");
        FlatBufferBuilder flatBufferBuilder = new FlatBufferBuilder(0);
        RequestOpenSipDialog.startRequestOpenSipDialog(flatBufferBuilder);
        RequestOpenSipDialog.addIsRequired(flatBufferBuilder, z);
        int endRequestOpenSipDialog = RequestOpenSipDialog.endRequestOpenSipDialog(flatBufferBuilder);
        Request.startRequest(flatBufferBuilder);
        Request.addReqid(flatBufferBuilder, Id.REQUEST_SIP_DIALOG_OPEN);
        Request.addReqType(flatBufferBuilder, ReqMsg.request_open_sip_dialog);
        Request.addReq(flatBufferBuilder, endRequestOpenSipDialog);
        sendRequest(flatBufferBuilder, Request.endRequest(flatBufferBuilder), null);
    }

    public void sendDnsQuery(int i, String str, String str2, List<String> list, String str3, String str4, String str5, long j) {
        Log.i(LOG_TAG, "dnsQueryByNaptr: hostnames " + str2 + " dnsservers" + list + " type " + str3 + " transport " + str4 + " family " + str5 + " handle " + i);
        sendRequest(RegistrationRequestBuilder.makeSendDnsQuery(i, str, str2, list, str3, str4, str5, j), null);
    }

    public void requestUpdateFeatureTag(int i, long j) {
        Log.i(LOG_TAG, "requestUpdateFeatureTag");
        sendRequest(RegistrationRequestBuilder.makeRequestUpdateFeatureTag(i, j), null);
    }

    private int createDeviceTuplesOffset(FlatBufferBuilder flatBufferBuilder, DeviceTuple deviceTuple) {
        int i;
        int i2;
        int i3;
        Log.i(LOG_TAG, "createDevicetupleoffset enter");
        List list = deviceTuple.mDeviceCapabilities;
        int i4 = 0;
        if (list != null) {
            int[] iArr = new int[list.size()];
            Iterator<XmlElement> it = XmlDataStructureWrapper.getDeviceCapabilityElements(deviceTuple.mDeviceCapabilities).iterator();
            int i5 = 0;
            while (it.hasNext()) {
                iArr[i5] = getElementBuilderDfs(flatBufferBuilder, it.next());
                i5++;
            }
            i = com.sec.internal.ims.core.handler.secims.imsCommonStruc.DeviceTuple.createDeviceCapabilitiesVector(flatBufferBuilder, iArr);
        } else {
            i = -1;
        }
        List list2 = deviceTuple.mDescriptions;
        if (list2 != null) {
            int[] iArr2 = new int[list2.size()];
            Iterator<XmlElement> it2 = XmlDataStructureWrapper.getTextElements("description", deviceTuple.mDescriptions).iterator();
            int i6 = 0;
            while (it2.hasNext()) {
                iArr2[i6] = getElementBuilderDfs(flatBufferBuilder, it2.next());
                i6++;
            }
            i2 = com.sec.internal.ims.core.handler.secims.imsCommonStruc.DeviceTuple.createDescriptionsVector(flatBufferBuilder, iArr2);
        } else {
            i2 = -1;
        }
        List list3 = deviceTuple.mNotes;
        if (list3 != null) {
            int[] iArr3 = new int[list3.size()];
            Iterator<XmlElement> it3 = XmlDataStructureWrapper.getTextElements("note", deviceTuple.mNotes).iterator();
            while (it3.hasNext()) {
                iArr3[i4] = getElementBuilderDfs(flatBufferBuilder, it3.next());
                i4++;
            }
            i3 = com.sec.internal.ims.core.handler.secims.imsCommonStruc.DeviceTuple.createNotesVector(flatBufferBuilder, iArr3);
        } else {
            i3 = -1;
        }
        String str = deviceTuple.mDeviceId;
        int createString = str != null ? flatBufferBuilder.createString(str) : -1;
        String str2 = deviceTuple.mTimestamp;
        int createString2 = str2 != null ? flatBufferBuilder.createString(str2) : -1;
        com.sec.internal.ims.core.handler.secims.imsCommonStruc.DeviceTuple.startDeviceTuple(flatBufferBuilder);
        if (createString != -1) {
            com.sec.internal.ims.core.handler.secims.imsCommonStruc.DeviceTuple.addDeviceId(flatBufferBuilder, createString);
        }
        if (i != -1) {
            com.sec.internal.ims.core.handler.secims.imsCommonStruc.DeviceTuple.addDeviceCapabilities(flatBufferBuilder, i);
        }
        if (i2 != -1) {
            com.sec.internal.ims.core.handler.secims.imsCommonStruc.DeviceTuple.addDescriptions(flatBufferBuilder, i2);
        }
        if (i3 != -1) {
            com.sec.internal.ims.core.handler.secims.imsCommonStruc.DeviceTuple.addNotes(flatBufferBuilder, i3);
        }
        if (createString2 != -1) {
            com.sec.internal.ims.core.handler.secims.imsCommonStruc.DeviceTuple.addTimestamp(flatBufferBuilder, createString2);
        }
        return com.sec.internal.ims.core.handler.secims.imsCommonStruc.DeviceTuple.endDeviceTuple(flatBufferBuilder);
    }

    private int createServiceTuplesOffset(FlatBufferBuilder flatBufferBuilder, ServiceTuple serviceTuple) {
        int i;
        int i2;
        Log.i(LOG_TAG, "createServiceTupleOffset enter");
        if (serviceTuple.mediaCapabilities != null) {
            List<XmlElement> mediaCapabilityElements = XmlDataStructureWrapper.getMediaCapabilityElements(serviceTuple.feature);
            int[] iArr = new int[mediaCapabilityElements.size()];
            Iterator<XmlElement> it = mediaCapabilityElements.iterator();
            int i3 = 0;
            while (it.hasNext()) {
                iArr[i3] = getElementBuilderDfs(flatBufferBuilder, it.next());
                i3++;
            }
            i = com.sec.internal.ims.core.handler.secims.imsCommonStruc.ServiceTuple.createMediaCapabilitiesVector(flatBufferBuilder, iArr);
        } else {
            i = -1;
        }
        String str = serviceTuple.serviceId;
        int createString = str != null ? flatBufferBuilder.createString(str) : -1;
        String str2 = serviceTuple.tupleId;
        int createString2 = str2 != null ? flatBufferBuilder.createString(str2) : -1;
        String str3 = serviceTuple.displayText;
        int createString3 = str3 != null ? flatBufferBuilder.createString(str3) : -1;
        String str4 = serviceTuple.version;
        int createString4 = str4 != null ? flatBufferBuilder.createString(str4) : -1;
        String str5 = serviceTuple.description;
        int createString5 = str5 != null ? flatBufferBuilder.createString(str5) : -1;
        String str6 = serviceTuple.basicStatus;
        if (str6 != null) {
            int createString6 = flatBufferBuilder.createString(str6);
            Status.startStatus(flatBufferBuilder);
            Status.addBasic(flatBufferBuilder, createString6);
            i2 = Status.endStatus(flatBufferBuilder);
        } else {
            i2 = -1;
        }
        com.sec.internal.ims.core.handler.secims.imsCommonStruc.ServiceTuple.startServiceTuple(flatBufferBuilder);
        if (createString != -1) {
            com.sec.internal.ims.core.handler.secims.imsCommonStruc.ServiceTuple.addServiceId(flatBufferBuilder, createString);
        }
        if (createString2 != -1) {
            com.sec.internal.ims.core.handler.secims.imsCommonStruc.ServiceTuple.addTupleId(flatBufferBuilder, createString2);
        }
        if (createString3 != -1) {
            com.sec.internal.ims.core.handler.secims.imsCommonStruc.ServiceTuple.addDisplaytext(flatBufferBuilder, createString3);
        }
        if (createString4 != -1) {
            com.sec.internal.ims.core.handler.secims.imsCommonStruc.ServiceTuple.addVersion(flatBufferBuilder, createString4);
        }
        if (createString5 != -1) {
            com.sec.internal.ims.core.handler.secims.imsCommonStruc.ServiceTuple.addDescription(flatBufferBuilder, createString5);
        }
        if (i2 != -1) {
            com.sec.internal.ims.core.handler.secims.imsCommonStruc.ServiceTuple.addStatus(flatBufferBuilder, i2);
            if (i != -1) {
                com.sec.internal.ims.core.handler.secims.imsCommonStruc.ServiceTuple.addMediaCapabilities(flatBufferBuilder, i);
            }
            return com.sec.internal.ims.core.handler.secims.imsCommonStruc.ServiceTuple.endServiceTuple(flatBufferBuilder);
        }
        Log.e(LOG_TAG, "requestPublish: service tuple status (either basic or other status) is required");
        return -1;
    }

    private int createPersonTuplesOffset(FlatBufferBuilder flatBufferBuilder, PersonTuple personTuple) {
        int i;
        Log.i(LOG_TAG, "createPersonTupleOffset enter");
        List list = personTuple.mNotes;
        int i2 = 0;
        int size = list == null ? 0 : list.size();
        if (size > 0) {
            int[] iArr = new int[size];
            Iterator<XmlElement> it = XmlDataStructureWrapper.getTextElements("note", personTuple.mNotes).iterator();
            while (it.hasNext()) {
                iArr[i2] = getElementBuilderDfs(flatBufferBuilder, it.next());
                i2++;
            }
            i = RequestPresencePublish.createNotesVector(flatBufferBuilder, iArr);
        } else {
            i = -1;
        }
        String str = personTuple.mTimestamp;
        int createString = str != null ? flatBufferBuilder.createString(str) : -1;
        String str2 = personTuple.mStatusIcon;
        int createString2 = str2 != null ? flatBufferBuilder.createString(str2) : -1;
        com.sec.internal.ims.core.handler.secims.imsCommonStruc.PersonTuple.startPersonTuple(flatBufferBuilder);
        if (createString != -1) {
            com.sec.internal.ims.core.handler.secims.imsCommonStruc.PersonTuple.addTimestamp(flatBufferBuilder, createString);
        }
        if (createString2 != -1) {
            com.sec.internal.ims.core.handler.secims.imsCommonStruc.PersonTuple.addStatusIcon(flatBufferBuilder, createString2);
        }
        if (i != -1) {
            com.sec.internal.ims.core.handler.secims.imsCommonStruc.PersonTuple.addNotes(flatBufferBuilder, i);
        }
        return com.sec.internal.ims.core.handler.secims.imsCommonStruc.PersonTuple.endPersonTuple(flatBufferBuilder);
    }

    @Override // com.sec.internal.ims.core.handler.secims.IStackIF
    public void requestPublish(int i, PresenceInfo presenceInfo, Message message) {
        int i2;
        int i3;
        int i4;
        Log.i(LOG_TAG, "request publish enter");
        int i5 = 0;
        FlatBufferBuilder flatBufferBuilder = new FlatBufferBuilder(0);
        List deviceList = presenceInfo.getDeviceList();
        List<ServiceTuple> serviceList = presenceInfo.getServiceList();
        List personList = presenceInfo.getPersonList();
        if (personList.size() > 0) {
            int[] iArr = new int[personList.size()];
            Iterator it = personList.iterator();
            int i6 = 0;
            while (it.hasNext()) {
                iArr[i6] = createPersonTuplesOffset(flatBufferBuilder, (PersonTuple) it.next());
                i6++;
            }
            i2 = RequestPresencePublish.createPersonTuplesVector(flatBufferBuilder, iArr);
        } else {
            i2 = -1;
        }
        if (deviceList.size() > 0) {
            int[] iArr2 = new int[deviceList.size()];
            Iterator it2 = deviceList.iterator();
            int i7 = 0;
            while (it2.hasNext()) {
                iArr2[i7] = createDeviceTuplesOffset(flatBufferBuilder, (DeviceTuple) it2.next());
                i7++;
            }
            i3 = RequestPresencePublish.createDeviceTuplesVector(flatBufferBuilder, iArr2);
        } else {
            i3 = -1;
        }
        if (serviceList.size() > 0) {
            int[] iArr3 = new int[serviceList.size()];
            for (ServiceTuple serviceTuple : serviceList) {
                int i8 = i5 + 1;
                iArr3[i5] = createServiceTuplesOffset(flatBufferBuilder, serviceTuple);
                Log.d(LOG_TAG, "serviceTuple.displayText : " + serviceTuple.description + " " + i8 + " " + serviceTuple.displayText);
                i5 = i8;
            }
            i4 = RequestPresencePublish.createServiceTuplesVector(flatBufferBuilder, iArr3);
        } else {
            i4 = -1;
        }
        int createString = presenceInfo.getEtag() != null ? flatBufferBuilder.createString(presenceInfo.getEtag()) : -1;
        int createString2 = presenceInfo.getUri() != null ? flatBufferBuilder.createString(presenceInfo.getUri()) : -1;
        int createString3 = presenceInfo.getTimestamp() > 0 ? flatBufferBuilder.createString("" + presenceInfo.getTimestamp()) : -1;
        int createString4 = presenceInfo.getPidf() != null ? flatBufferBuilder.createString(presenceInfo.getPidf()) : -1;
        RequestPresencePublish.startRequestPresencePublish(flatBufferBuilder);
        if (i4 == -1) {
            return;
        }
        RequestPresencePublish.addServiceTuples(flatBufferBuilder, i4);
        if (presenceInfo.getEtag() != null) {
            RequestPresencePublish.addETag(flatBufferBuilder, createString);
        }
        RequestPresencePublish.addExpireTime(flatBufferBuilder, presenceInfo.getExpireTime());
        if (createString3 != -1) {
            RequestPresencePublish.addTimestamp(flatBufferBuilder, createString3);
        }
        if (i2 != -1) {
            RequestPresencePublish.addPersonTuples(flatBufferBuilder, i2);
        }
        if (i3 != -1) {
            RequestPresencePublish.addDeviceTuples(flatBufferBuilder, i3);
        }
        if (createString4 != -1) {
            RequestPresencePublish.addPidfXml(flatBufferBuilder, createString4);
        }
        if (createString2 != -1) {
            RequestPresencePublish.addUri(flatBufferBuilder, createString2);
        }
        RequestPresencePublish.addHandle(flatBufferBuilder, i);
        RequestPresencePublish.addGzipEnable(flatBufferBuilder, presenceInfo.getPublishGzipEnabled());
        int endRequestPresencePublish = RequestPresencePublish.endRequestPresencePublish(flatBufferBuilder);
        Request.startRequest(flatBufferBuilder);
        Request.addReqid(flatBufferBuilder, 701);
        Request.addReqType(flatBufferBuilder, (byte) 62);
        Request.addReq(flatBufferBuilder, endRequestPresencePublish);
        sendRequest(flatBufferBuilder, Request.endRequest(flatBufferBuilder), message);
        Log.i(LOG_TAG, "requestPublish: sent");
    }

    public void requestOptionsReqCapabilityExchange(int i, String str, long j, String str2, List<String> list) {
        Log.i(LOG_TAG, "requestOptionsReqCapabilityExchange: uri: " + IMSLog.checker(str) + " handle: " + i);
        FlatBufferBuilder flatBufferBuilder = new FlatBufferBuilder(0);
        int requestCapabilityExchange = requestCapabilityExchange(flatBufferBuilder, (long) i, str, j, str2, list);
        Request.startRequest(flatBufferBuilder);
        Request.addReqid(flatBufferBuilder, Id.REQUEST_OPTIONS_CAP_EXCHANGE);
        Request.addReqType(flatBufferBuilder, (byte) 81);
        Request.addReq(flatBufferBuilder, requestCapabilityExchange);
        sendRequest(flatBufferBuilder, Request.endRequest(flatBufferBuilder), null);
        Log.i(LOG_TAG, "requestOptionsReqCapabilityExchange: sent");
    }

    public void requestOptionsReqSendCmcCheckMsg(int i, String str) {
        Log.i(LOG_TAG, "requestOptionsReqSendCmcCheckMsg: uri: " + IMSLog.checker(str) + " handle: " + i);
        FlatBufferBuilder flatBufferBuilder = new FlatBufferBuilder(0);
        int createString = flatBufferBuilder.createString(str);
        RequestOptionsSendCmcCheckMsg.startRequestOptionsSendCmcCheckMsg(flatBufferBuilder);
        RequestOptionsSendCmcCheckMsg.addHandle(flatBufferBuilder, (long) i);
        RequestOptionsSendCmcCheckMsg.addUri(flatBufferBuilder, createString);
        int endRequestOptionsSendCmcCheckMsg = RequestOptionsSendCmcCheckMsg.endRequestOptionsSendCmcCheckMsg(flatBufferBuilder);
        Request.startRequest(flatBufferBuilder);
        Request.addReqid(flatBufferBuilder, Id.REQUEST_OPTIONS_SEND_CMC_CHECK_MSG);
        Request.addReqType(flatBufferBuilder, (byte) 82);
        Request.addReq(flatBufferBuilder, endRequestOptionsSendCmcCheckMsg);
        sendRequest(flatBufferBuilder, Request.endRequest(flatBufferBuilder), null);
        Log.i(LOG_TAG, "requestOptionsReqSendCmcCheckMsg: sent");
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0096  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int sendCapexResponse(int r8, java.lang.String r9, long r10, java.lang.String r12, int r13, android.os.Message r14, java.lang.String r15) {
        /*
            r7 = this;
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "sendCapexResponse: handle "
            r0.append(r1)
            r0.append(r8)
            java.lang.String r0 = r0.toString()
            java.lang.String r1 = "StackIF"
            android.util.Log.i(r1, r0)
            r0 = -1
            if (r12 != 0) goto L1b
            return r0
        L1b:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "sendCapexResponse: uri "
            r2.append(r3)
            java.lang.String r3 = com.sec.internal.log.IMSLog.checker(r9)
            r2.append(r3)
            java.lang.String r3 = "transaction Id"
            r2.append(r3)
            r2.append(r12)
            java.lang.String r2 = r2.toString()
            android.util.Log.i(r1, r2)
            com.google.flatbuffers.FlatBufferBuilder r2 = new com.google.flatbuffers.FlatBufferBuilder
            r3 = 0
            r2.<init>(r3)
            int r9 = r2.createString(r9)
            int r12 = r2.createString(r12)
            int r15 = r2.createString(r15)
            int r4 = com.sec.ims.options.Capabilities.FEATURE_OFFLINE_RCS_USER
            long r4 = (long) r4
            int r4 = (r10 > r4 ? 1 : (r10 == r4 ? 0 : -1))
            if (r4 == 0) goto L86
            java.util.List r10 = com.sec.internal.ims.core.handler.secims.StackRequestBuilderUtil.translateFeatureTag(r10)
            int r11 = r10.size()
            if (r11 <= 0) goto L86
            int r11 = r10.size()
            int[] r11 = new int[r11]
            java.util.Iterator r10 = r10.iterator()
            r4 = r3
        L6b:
            boolean r5 = r10.hasNext()
            if (r5 == 0) goto L81
            java.lang.Object r5 = r10.next()
            java.lang.Integer r5 = (java.lang.Integer) r5
            int r6 = r4 + 1
            int r5 = r5.intValue()
            r11[r4] = r5
            r4 = r6
            goto L6b
        L81:
            int r10 = com.sec.internal.ims.core.handler.secims.imsCommonStruc.Request_.RequestOptionsSendResponse.createMyFeaturesVector(r2, r11)
            goto L87
        L86:
            r10 = r0
        L87:
            com.sec.internal.ims.core.handler.secims.imsCommonStruc.Request_.RequestOptionsSendResponse.startRequestOptionsSendResponse(r2)
            long r4 = (long) r8
            com.sec.internal.ims.core.handler.secims.imsCommonStruc.Request_.RequestOptionsSendResponse.addHandle(r2, r4)
            com.sec.internal.ims.core.handler.secims.imsCommonStruc.Request_.RequestOptionsSendResponse.addUri(r2, r9)
            com.sec.internal.ims.core.handler.secims.imsCommonStruc.Request_.RequestOptionsSendResponse.addLastSeen(r2, r13)
            if (r10 == r0) goto L99
            com.sec.internal.ims.core.handler.secims.imsCommonStruc.Request_.RequestOptionsSendResponse.addMyFeatures(r2, r10)
        L99:
            com.sec.internal.ims.core.handler.secims.imsCommonStruc.Request_.RequestOptionsSendResponse.addTxId(r2, r12)
            com.sec.internal.ims.core.handler.secims.imsCommonStruc.Request_.RequestOptionsSendResponse.addExtFeature(r2, r15)
            int r8 = com.sec.internal.ims.core.handler.secims.imsCommonStruc.Request_.RequestOptionsSendResponse.endRequestOptionsSendResponse(r2)
            com.sec.internal.ims.core.handler.secims.imsCommonStruc.Request.startRequest(r2)
            r9 = 752(0x2f0, float:1.054E-42)
            com.sec.internal.ims.core.handler.secims.imsCommonStruc.Request.addReqid(r2, r9)
            r9 = 79
            com.sec.internal.ims.core.handler.secims.imsCommonStruc.Request.addReqType(r2, r9)
            com.sec.internal.ims.core.handler.secims.imsCommonStruc.Request.addReq(r2, r8)
            int r8 = com.sec.internal.ims.core.handler.secims.imsCommonStruc.Request.endRequest(r2)
            r7.sendRequest(r2, r8, r14)
            java.lang.String r7 = "sendCapexResponse: sent"
            android.util.Log.i(r1, r7)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sec.internal.ims.core.handler.secims.StackIF.sendCapexResponse(int, java.lang.String, long, java.lang.String, int, android.os.Message, java.lang.String):int");
    }

    public int sendCapexResponse(int i, String str, List<String> list, String str2, int i2, Message message) {
        Log.i(LOG_TAG, "sendCapexResponse list: handle " + i);
        if (str2 == null) {
            return -1;
        }
        Log.i(LOG_TAG, "sendCapexResponse: uri " + IMSLog.checker(str) + "transaction Id" + str2);
        FlatBufferBuilder flatBufferBuilder = new FlatBufferBuilder(0);
        int createString = flatBufferBuilder.createString(str);
        int createString2 = flatBufferBuilder.createString(str2);
        int createMyFeatureListVector = RequestOptionsSendResponse.createMyFeatureListVector(flatBufferBuilder, StackRequestBuilderUtil.getStringOffsetArray(flatBufferBuilder, list, list.size()));
        RequestOptionsSendResponse.startRequestOptionsSendResponse(flatBufferBuilder);
        RequestOptionsSendResponse.addHandle(flatBufferBuilder, i);
        RequestOptionsSendResponse.addUri(flatBufferBuilder, createString);
        RequestOptionsSendResponse.addLastSeen(flatBufferBuilder, i2);
        if (createMyFeatureListVector != -1) {
            RequestOptionsSendResponse.addMyFeatureList(flatBufferBuilder, createMyFeatureListVector);
        }
        RequestOptionsSendResponse.addTxId(flatBufferBuilder, createString2);
        int endRequestOptionsSendResponse = RequestOptionsSendResponse.endRequestOptionsSendResponse(flatBufferBuilder);
        Request.startRequest(flatBufferBuilder);
        Request.addReqid(flatBufferBuilder, Id.REQUEST_OPTIONS_SEND_RESPONSE);
        Request.addReqType(flatBufferBuilder, (byte) 79);
        Request.addReq(flatBufferBuilder, endRequestOptionsSendResponse);
        sendRequest(flatBufferBuilder, Request.endRequest(flatBufferBuilder), message);
        Log.i(LOG_TAG, "sendCapexResponse: list sent");
        return 0;
    }

    public int sendCapexErrorResponse(int i, String str, String str2, int i2, String str3, Message message) {
        Log.i(LOG_TAG, "sendCapexResvponse: handle " + i);
        if (str2 == null) {
            return -1;
        }
        Log.i(LOG_TAG, "sendCapexErrorResponse: uri " + IMSLog.checker(str) + "transaction Id" + str2);
        FlatBufferBuilder flatBufferBuilder = new FlatBufferBuilder(0);
        int createString = flatBufferBuilder.createString(str);
        int createString2 = flatBufferBuilder.createString(str2);
        int createString3 = TextUtils.isEmpty(str3) ? -1 : flatBufferBuilder.createString(str3);
        RequestOptionsSendErrorResponse.startRequestOptionsSendErrorResponse(flatBufferBuilder);
        RequestOptionsSendErrorResponse.addHandle(flatBufferBuilder, i);
        RequestOptionsSendErrorResponse.addUri(flatBufferBuilder, createString);
        RequestOptionsSendErrorResponse.addTxId(flatBufferBuilder, createString2);
        RequestOptionsSendErrorResponse.addErrorCode(flatBufferBuilder, i2);
        RequestOptionsSendErrorResponse.addReason(flatBufferBuilder, createString3);
        int endRequestOptionsSendErrorResponse = RequestOptionsSendErrorResponse.endRequestOptionsSendErrorResponse(flatBufferBuilder);
        Request.startRequest(flatBufferBuilder);
        Request.addReqid(flatBufferBuilder, Id.REQUEST_OPTIONS_SEND_ERROR_RESPONSE);
        Request.addReqType(flatBufferBuilder, (byte) 80);
        Request.addReq(flatBufferBuilder, endRequestOptionsSendErrorResponse);
        sendRequest(flatBufferBuilder, Request.endRequest(flatBufferBuilder), message);
        Log.i(LOG_TAG, "sendCapexErrorResponse: sent");
        return 0;
    }

    private int requestCapabilityExchange(FlatBufferBuilder flatBufferBuilder, long j, String str, long j2, String str2, List<String> list) {
        int i;
        int i2;
        Log.i(LOG_TAG, "requestCapabilityExchange: uri: " + IMSLog.checker(str) + " handle: " + j + "extension iari " + str2);
        int createString = flatBufferBuilder.createString(str);
        int createString2 = flatBufferBuilder.createString(str2);
        if (list != null) {
            i = RequestOptionsCapExchange.createCapabilitiesVector(flatBufferBuilder, StackRequestBuilderUtil.getStringOffsetArray(flatBufferBuilder, list, list.size()));
            i2 = -1;
        } else {
            if (j2 != Capabilities.FEATURE_OFFLINE_RCS_USER) {
                List<Integer> translateFeatureTag = StackRequestBuilderUtil.translateFeatureTag(j2);
                if (translateFeatureTag.size() > 0) {
                    int[] iArr = new int[translateFeatureTag.size()];
                    Iterator<Integer> it = translateFeatureTag.iterator();
                    int i3 = 0;
                    while (it.hasNext()) {
                        iArr[i3] = it.next().intValue();
                        i3++;
                    }
                    i2 = RequestOptionsCapExchange.createMyFeaturesVector(flatBufferBuilder, iArr);
                    i = -1;
                }
            }
            i = -1;
            i2 = -1;
        }
        RequestOptionsCapExchange.startRequestOptionsCapExchange(flatBufferBuilder);
        RequestOptionsCapExchange.addHandle(flatBufferBuilder, j);
        RequestOptionsCapExchange.addUri(flatBufferBuilder, createString);
        RequestOptionsCapExchange.addExtFeature(flatBufferBuilder, createString2);
        if (i != -1) {
            RequestOptionsCapExchange.addCapabilities(flatBufferBuilder, i);
        }
        if (i2 != -1) {
            RequestOptionsCapExchange.addMyFeatures(flatBufferBuilder, i2);
        }
        Log.i(LOG_TAG, "requestCapabilityExchange request built");
        return RequestOptionsCapExchange.endRequestOptionsCapExchange(flatBufferBuilder);
    }

    private int getElementBuilderDfs(FlatBufferBuilder flatBufferBuilder, XmlElement xmlElement) {
        int i;
        int i2;
        int size = xmlElement.mChildElements.size();
        int i3 = 0;
        if (size > 0) {
            int[] iArr = new int[size];
            Iterator<XmlElement> it = xmlElement.mChildElements.iterator();
            int i4 = 0;
            while (it.hasNext()) {
                iArr[i4] = getElementBuilderDfs(flatBufferBuilder, it.next());
                i4++;
            }
            i = Element.createElementsVector(flatBufferBuilder, iArr);
        } else {
            i = -1;
        }
        if (xmlElement.mAttributes.size() > 0) {
            int[] iArr2 = new int[xmlElement.mAttributes.size()];
            for (XmlElement.Attribute attribute : xmlElement.mAttributes) {
                String str = attribute.mNamespace;
                int createString = str != null ? flatBufferBuilder.createString(str) : -1;
                String str2 = attribute.mName;
                int createString2 = str2 != null ? flatBufferBuilder.createString(str2) : -1;
                String str3 = attribute.mValue;
                int createString3 = str3 != null ? flatBufferBuilder.createString(str3) : -1;
                Log.i(LOG_TAG, "element attr: ns: " + attribute.mNamespace + " name: " + attribute.mName + " val: " + attribute.mValue);
                Attribute.startAttribute(flatBufferBuilder);
                if (createString != -1) {
                    Attribute.addNameSpace(flatBufferBuilder, createString);
                }
                if (createString2 != -1) {
                    Attribute.addName(flatBufferBuilder, createString2);
                }
                if (createString3 != -1) {
                    Attribute.addValue(flatBufferBuilder, createString3);
                }
                iArr2[i3] = Attribute.endAttribute(flatBufferBuilder);
                i3++;
            }
            i2 = Element.createAttributesVector(flatBufferBuilder, iArr2);
        } else {
            i2 = -1;
        }
        String str4 = xmlElement.mNamespace;
        int createString4 = str4 != null ? flatBufferBuilder.createString(str4) : -1;
        String str5 = xmlElement.mName;
        int createString5 = str5 != null ? flatBufferBuilder.createString(str5) : -1;
        String str6 = xmlElement.mValue;
        int createString6 = str6 != null ? flatBufferBuilder.createString(str6) : -1;
        Element.startElement(flatBufferBuilder);
        if (createString4 != -1) {
            Element.addNameSpace(flatBufferBuilder, createString4);
        }
        if (createString5 != -1) {
            Element.addName(flatBufferBuilder, createString5);
        }
        if (createString6 != -1) {
            Element.addValue(flatBufferBuilder, createString6);
        }
        if (i2 != -1) {
            Element.addAttributes(flatBufferBuilder, i2);
        }
        if (i != -1) {
            Element.addElements(flatBufferBuilder, i);
        }
        return Element.endElement(flatBufferBuilder);
    }

    public void requestSubscribe(int i, ImsUri imsUri, boolean z, String str, Message message) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(imsUri);
        requestSubscribe(i, arrayList, z, false, str, false, 0, message);
    }

    public void requestSubscribeList(int i, List<ImsUri> list, boolean z, String str, boolean z2, int i2, Message message) {
        requestSubscribe(i, list, z, true, str, z2, i2, message);
    }

    private void requestSubscribe(int i, List<ImsUri> list, boolean z, boolean z2, String str, boolean z3, int i2, Message message) {
        int i3 = 0;
        FlatBufferBuilder flatBufferBuilder = new FlatBufferBuilder(0);
        int size = list.size();
        if (size > 0) {
            int[] iArr = new int[size];
            Iterator<ImsUri> it = list.iterator();
            while (it.hasNext()) {
                iArr[i3] = flatBufferBuilder.createString(it.next().toString());
                i3++;
            }
            i3 = RequestPresenceSubscribe.createUriVector(flatBufferBuilder, iArr);
        }
        int createString = str != null ? flatBufferBuilder.createString(str) : -1;
        RequestPresenceSubscribe.startRequestPresenceSubscribe(flatBufferBuilder);
        RequestPresenceSubscribe.addHandle(flatBufferBuilder, i);
        RequestPresenceSubscribe.addIsAnonymous(flatBufferBuilder, z);
        RequestPresenceSubscribe.addIsListSubscribe(flatBufferBuilder, z2);
        if (createString != -1) {
            RequestPresenceSubscribe.addSubscriptionId(flatBufferBuilder, createString);
        }
        RequestPresenceSubscribe.addGzipEnable(flatBufferBuilder, z3);
        if (size > 0) {
            RequestPresenceSubscribe.addUri(flatBufferBuilder, i3);
        }
        RequestPresenceSubscribe.addExpires(flatBufferBuilder, i2);
        int endRequestPresenceSubscribe = RequestPresenceSubscribe.endRequestPresenceSubscribe(flatBufferBuilder);
        Request.startRequest(flatBufferBuilder);
        Request.addReqid(flatBufferBuilder, Id.REQUEST_PRESENCE_SUBSCRIBE);
        Request.addReqType(flatBufferBuilder, (byte) 64);
        Request.addReq(flatBufferBuilder, endRequestPresenceSubscribe);
        sendRequest(flatBufferBuilder, Request.endRequest(flatBufferBuilder), message);
        Log.i(LOG_TAG, "requestSubscribe: sent");
    }

    @Override // com.sec.internal.ims.core.handler.secims.IStackIF
    public void requestUnpublish(int i) {
        FlatBufferBuilder flatBufferBuilder = new FlatBufferBuilder(0);
        RequestPresenceUnpublish.startRequestPresenceUnpublish(flatBufferBuilder);
        RequestPresenceUnpublish.addHandle(flatBufferBuilder, i);
        int endRequestPresenceUnpublish = RequestPresenceUnpublish.endRequestPresenceUnpublish(flatBufferBuilder);
        Request.startRequest(flatBufferBuilder);
        Request.addReqid(flatBufferBuilder, Id.REQUEST_PRESENCE_UNPUBLISH);
        Request.addReqType(flatBufferBuilder, (byte) 63);
        Request.addReq(flatBufferBuilder, endRequestPresenceUnpublish);
        sendRequest(flatBufferBuilder, Request.endRequest(flatBufferBuilder), null);
        Log.i(LOG_TAG, "requestUnpublish: sent");
    }

    @Override // com.sec.internal.ims.core.handler.secims.IStackIF
    public void makeCall(int i, String str, String str2, int i2, String str3, String str4, String str5, int i3, AdditionalContents additionalContents, String str6, String str7, HashMap<String, String> hashMap, String str8, boolean z, List<String> list, int i4, Bundle bundle, String str9, int i5, String str10, Message message) {
        StringBuilder sb = new StringBuilder();
        sb.append("makeCall: handle ");
        sb.append(i);
        sb.append(" destUri ");
        sb.append(IMSLog.checker(str));
        sb.append(" origUri ");
        sb.append(IMSLog.checker(str2));
        sb.append(" type ");
        sb.append(i2);
        sb.append(" dispName ");
        sb.append(IMSLog.checker(str3));
        sb.append(" ecscf ");
        sb.append(str5);
        sb.append(" cli ");
        sb.append(str6);
        sb.append(" PEmergencyInfo ");
        sb.append(str7);
        sb.append(" alertInfo ");
        sb.append(str8);
        sb.append(" isLteEpsOnlyAttached ");
        sb.append(z);
        sb.append(" p2p ");
        sb.append(list != null ? list.toString() : "null");
        sb.append(" cmcBoundSessionId ");
        sb.append(i4);
        sb.append(" replaceCallId ");
        sb.append(str9);
        sb.append(" cmcEdCallSlot ");
        sb.append(i5);
        sb.append(" idcExtra ");
        sb.append(str10);
        Log.i(LOG_TAG, sb.toString());
        sendRequest(CallRequestBuilder.makeMakeCall(i, str, str2, i2, str3, str4, str5, i3, additionalContents, str6, str7, hashMap, str8, z, list, i4, bundle, str9, i5, str10), message);
    }

    @Override // com.sec.internal.ims.core.handler.secims.IStackIF
    public void deregister(int i, boolean z, Message message) {
        Log.i(LOG_TAG, "deregister: handle " + i);
        FlatBufferBuilder flatBufferBuilder = new FlatBufferBuilder(0);
        RequestRegistration.startRequestRegistration(flatBufferBuilder);
        RequestRegistration.addHandle(flatBufferBuilder, (long) i);
        RequestRegistration.addPcscfPort(flatBufferBuilder, 0L);
        RequestRegistration.addRegExp(flatBufferBuilder, 0L);
        RequestRegistration.addIsExplicitDeregi(flatBufferBuilder, !z);
        int endRequestRegistration = RequestRegistration.endRequestRegistration(flatBufferBuilder);
        Request.startRequest(flatBufferBuilder);
        Request.addReqid(flatBufferBuilder, 104);
        Request.addReqType(flatBufferBuilder, (byte) 4);
        Request.addReq(flatBufferBuilder, endRequestRegistration);
        sendRequest(flatBufferBuilder, Request.endRequest(flatBufferBuilder), message);
    }

    @Override // com.sec.internal.ims.core.handler.secims.IStackIF
    public void rejectCall(int i, int i2, SipError sipError, Message message) {
        Log.i(LOG_TAG, "rejectCall: handle " + i + " sessionId " + i2 + " response " + sipError);
        FlatBufferBuilder flatBufferBuilder = new FlatBufferBuilder(0);
        int createString = flatBufferBuilder.createString(sipError.getReason());
        RequestRejectCall.startRequestRejectCall(flatBufferBuilder);
        RequestRejectCall.addSession(flatBufferBuilder, (long) i2);
        RequestRejectCall.addStatusCode(flatBufferBuilder, (long) sipError.getCode());
        RequestRejectCall.addReasonPhrase(flatBufferBuilder, createString);
        int endRequestRejectCall = RequestRejectCall.endRequestRejectCall(flatBufferBuilder);
        Request.startRequest(flatBufferBuilder);
        Request.addReqid(flatBufferBuilder, 214);
        Request.addReqType(flatBufferBuilder, (byte) 21);
        Request.addReq(flatBufferBuilder, endRequestRejectCall);
        sendRequest(flatBufferBuilder, Request.endRequest(flatBufferBuilder), message);
    }

    @Override // com.sec.internal.ims.core.handler.secims.IStackIF
    public void progressIncomingCall(int i, int i2, boolean z, HashMap<String, String> hashMap, String str, Message message) {
        Log.i(LOG_TAG, "progressIncomingCall: handle " + i + " sessionId " + i2 + " isBlocked " + z);
        sendRequest(CallRequestBuilder.makeProgressIncomingCall(i, i2, z, hashMap, str), message);
    }

    @Override // com.sec.internal.ims.core.handler.secims.IStackIF
    public void deleteTcpClientSocket(int i) {
        Log.i(LOG_TAG, "deleteTcpClientSocket: handle " + i);
        FlatBufferBuilder flatBufferBuilder = new FlatBufferBuilder(0);
        RequestDeleteTcpClientSocket.startRequestDeleteTcpClientSocket(flatBufferBuilder);
        RequestDeleteTcpClientSocket.addHandle(flatBufferBuilder, (long) i);
        int endRequestDeleteTcpClientSocket = RequestDeleteTcpClientSocket.endRequestDeleteTcpClientSocket(flatBufferBuilder);
        Request.startRequest(flatBufferBuilder);
        Request.addReqid(flatBufferBuilder, 1300);
        Request.addReqType(flatBufferBuilder, (byte) 8);
        Request.addReq(flatBufferBuilder, endRequestDeleteTcpClientSocket);
        sendRequest(flatBufferBuilder, Request.endRequest(flatBufferBuilder), null);
    }

    @Override // com.sec.internal.ims.core.handler.secims.IStackIF
    public void endCall(int i, int i2, SipReason sipReason, Message message) {
        Log.i(LOG_TAG, "endCall: handle " + i + " sessionId " + i2 + " reason " + sipReason);
        sendRequest(CallRequestBuilder.makeEndCall(i, i2, sipReason), message);
    }

    @Override // com.sec.internal.ims.core.handler.secims.IStackIF
    public void answerCall(int i, int i2, int i3, String str, String str2) {
        Log.i(LOG_TAG, "answerCall: handle " + i + " sessionId " + i2 + " cmcCallTime " + str + " idcExtra " + str2);
        sendRequest(CallRequestBuilder.makeAnswerCall(i, i2, i3, str, str2), null);
    }

    @Override // com.sec.internal.ims.core.handler.secims.IStackIF
    public void holdCall(int i, int i2, Message message) {
        Log.i(LOG_TAG, "holdCall: handle " + i + " sessionId " + i2);
        sendRequest(CallRequestBuilder.makeHoldCall(i, i2), message);
    }

    @Override // com.sec.internal.ims.core.handler.secims.IStackIF
    public void resumeCall(int i, int i2, Message message) {
        Log.i(LOG_TAG, "resumeCall: handle " + i + " sessionId " + i2);
        sendRequest(CallRequestBuilder.makeResumeCall(i, i2), message);
    }

    @Override // com.sec.internal.ims.core.handler.secims.IStackIF
    public void holdVideo(int i, int i2, Message message) {
        Log.i(LOG_TAG, "holdVideo: handle " + i + " sessionId " + i2);
        sendRequest(CallRequestBuilder.makeHoldVideo(i, i2), message);
    }

    @Override // com.sec.internal.ims.core.handler.secims.IStackIF
    public void resumeVideo(int i, int i2, Message message) {
        Log.i(LOG_TAG, "resumeVideo: handle " + i + " sessionId " + i2);
        sendRequest(CallRequestBuilder.makeResumeVideo(i, i2), message);
    }

    @Override // com.sec.internal.ims.core.handler.secims.IStackIF
    public void startCamera(int i, int i2, int i3) {
        Log.i(LOG_TAG, "startCamera: handle " + i + ", sessionId: " + i2 + ", cameraId: " + i3);
        sendRequest(CallRequestBuilder.makeStartCamera(i, i2, i3), null);
    }

    @Override // com.sec.internal.ims.core.handler.secims.IStackIF
    public void stopCamera(int i) {
        Log.i(LOG_TAG, "stopCamera: handle " + i);
        sendRequest(CallRequestBuilder.makeStopCamera(i), null);
    }

    @Override // com.sec.internal.ims.core.handler.secims.IStackIF
    public void mergeCall(int i, int i2, int i3, String str, int i4, String str2, String str3, String str4, String str5, String str6, String str7, String str8, boolean z, HashMap<String, String> hashMap, Message message) {
        Log.i(LOG_TAG, "mergeCall: handle " + i + " session1 " + i2 + " session2 " + i3 + " confUri " + IMSLog.checker(str) + " callType " + i4 + " eventSubscribe " + str2 + " dialogType " + str3 + " origUri " + IMSLog.checker(str4) + " referUriType " + str5 + " removeReferUriType " + str6 + " referUseAsserted " + str7 + " useAnonymousUpdate " + str8);
        sendRequest(CallRequestBuilder.makeMergeCall(i, i2, i3, str, i4, str2, str3, str4, str5, str6, str7, str8, z, hashMap), message);
    }

    @Override // com.sec.internal.ims.core.handler.secims.IStackIF
    public void conference(int i, String str, int i2, String str2, String str3, String[] strArr, String str4, String str5, String str6, String str7, String str8, boolean z, Message message) {
        Log.i(LOG_TAG, "conference: handle " + i + " confUri " + str + " subscribe " + str2 + " dialogType " + str3 + " origUri " + IMSLog.checker(str4) + " useAnonymousUpdate " + str8);
        StringBuilder sb = new StringBuilder();
        sb.append("participants: ");
        sb.append(Arrays.toString(strArr));
        Log.i(LOG_TAG, sb.toString());
        sendRequest(CallRequestBuilder.makeConference(i, str, i2, str2, str3, strArr, str4, str5, str6, str7, str8, z), message);
    }

    @Override // com.sec.internal.ims.core.handler.secims.IStackIF
    public void extendToConfCall(int i, String str, int i2, String str2, String str3, String[] strArr, int i3, String str4, String str5, String str6, String str7, String str8, boolean z) {
        Log.i(LOG_TAG, "extendToConfCall: handle " + i + " confUri " + IMSLog.checker(str) + " subscribe " + str2 + " dialogType " + str3 + " currSession " + i3);
        StringBuilder sb = new StringBuilder();
        sb.append("participants: ");
        sb.append(IMSLog.checker(Arrays.toString(strArr)));
        Log.i(LOG_TAG, sb.toString());
        sendRequest(CallRequestBuilder.makeExtendToConfCall(i, str, i2, str2, str3, strArr, i3, str4, str5, str6, str7, str8, z), null);
    }

    @Override // com.sec.internal.ims.core.handler.secims.IStackIF
    public void updateConfCall(int i, int i2, int i3, int i4, String str) {
        Log.i(LOG_TAG, "updateConfCall: handle " + i + " confSession " + i2 + " updateCmd " + i3 + " participantID " + i4 + " " + str);
        sendRequest(CallRequestBuilder.makeUpdateConfCall(i, i2, i3, i4, str), null);
    }

    @Override // com.sec.internal.ims.core.handler.secims.IStackIF
    public void transferCall(int i, int i2, String str, int i3, Message message) {
        sendRequest(CallRequestBuilder.makeTransferCall(i, i2, str, i3), message);
    }

    @Override // com.sec.internal.ims.core.handler.secims.IStackIF
    public void cancelTransferCall(int i, int i2, Message message) {
        sendRequest(CallRequestBuilder.makeCancelTransferCall(i, i2), message);
    }

    @Override // com.sec.internal.ims.core.handler.secims.IStackIF
    public void pullingCall(int i, String str, String str2, String str3, Dialog dialog, List<String> list, Message message) {
        sendRequest(CallRequestBuilder.makePullingCall(i, str, str2, str3, dialog, list), message);
    }

    @Override // com.sec.internal.ims.core.handler.secims.IStackIF
    public void publishDialog(int i, String str, String str2, String str3, int i2, Message message) {
        sendRequest(CallRequestBuilder.makePublishDialog(i, str, str2, str3, i2), message);
    }

    @Override // com.sec.internal.ims.core.handler.secims.IStackIF
    public void acceptCallTransfer(int i, int i2, boolean z, int i3, String str, Message message) {
        Log.i(LOG_TAG, "acceptTransferCall:");
        sendRequest(CallRequestBuilder.makeAcceptCallTransfer(i, i2, z, i3, str), message);
    }

    @Override // com.sec.internal.ims.core.handler.secims.IStackIF
    public void handleDtmf(int i, int i2, int i3, int i4, int i5, Message message) {
        Log.i(LOG_TAG, "handleDtmf: sessionId " + i2 + " mode " + i4 + " operation " + i5);
        sendRequest(CallRequestBuilder.makeHandleDtmf(i, i2, i3, i4, i5), message);
    }

    @Override // com.sec.internal.ims.core.handler.secims.IStackIF
    public void sendText(int i, int i2, String str, int i3) {
        Log.i(LOG_TAG, "sendText:: sessionId: " + i2 + ", text: " + IMSLog.checker(str) + ", len: " + i3);
        sendRequest(CallRequestBuilder.makeSendText(i, i2, str, i3), null);
    }

    @Override // com.sec.internal.ims.core.handler.secims.IStackIF
    public void modifyCallType(int i, int i2, int i3) {
        Log.i(LOG_TAG, "modifyCallType(): sessionId " + i + ", oldType " + i2 + ", newType " + i3);
        sendRequest(CallRequestBuilder.makeModifyCallType(i, i2, i3), null);
    }

    @Override // com.sec.internal.ims.core.handler.secims.IStackIF
    public void modifyVideoQuality(int i, int i2, int i3) {
        Log.i(LOG_TAG, "modifyVideoQuality(): sessionId " + i + ", oldQual " + i2 + ", newQual " + i3);
        sendRequest(CallRequestBuilder.makeModifyVideoQuality(i, i2, i3), null);
    }

    @Override // com.sec.internal.ims.core.handler.secims.IStackIF
    public void replyModifyCallType(int i, int i2, int i3, int i4, String str) {
        Log.i(LOG_TAG, "replyModifyCallType(): sessionId " + i + ", reqType " + i2 + ", curType " + i3 + ", repType " + i4 + ", cmcCallTime " + str);
        sendRequest(CallRequestBuilder.makeReplyModifyCallType(i, i2, i3, i4, str), null);
    }

    @Override // com.sec.internal.ims.core.handler.secims.IStackIF
    public void rejectModifyCallType(int i, int i2) {
        Log.i(LOG_TAG, "rejectModifyCallType(): sessionId " + i + ", reason" + i2);
        sendRequest(CallRequestBuilder.makeRejectModifyCallType(i, i2), null);
    }

    @Override // com.sec.internal.ims.core.handler.secims.IStackIF
    public void updateCall(int i, int i2, int i3, int i4, String str) {
        sendRequest(CallRequestBuilder.makeUpdateCall(i, i2, i3, i4, str), null);
    }

    @Override // com.sec.internal.ims.core.handler.secims.IStackIF
    public void sendInfo(int i, int i2, int i3, int i4, AdditionalContents additionalContents, Message message) {
        Log.i(LOG_TAG, "sendInfo");
        sendRequest(CallRequestBuilder.makeSendInfo(i, i2, i3, i4, additionalContents), message);
    }

    @Override // com.sec.internal.ims.core.handler.secims.IStackIF
    public void sendCmcInfo(int i, int i2, AdditionalContents additionalContents) {
        Log.i(LOG_TAG, "sendCmcInfo");
        sendRequest(CallRequestBuilder.makeSendCmcInfo(i, i2, additionalContents), null);
    }

    @Override // com.sec.internal.ims.core.handler.secims.IStackIF
    public void sendVcsInfo(int i, int i2, AdditionalContents additionalContents) {
        Log.i(LOG_TAG, "sendVcsInfo");
        sendRequest(CallRequestBuilder.makeSendVcsInfo(i, i2, additionalContents), null);
    }

    @Override // com.sec.internal.ims.core.handler.secims.IStackIF
    public void enableQuantumSecurityService(int i, boolean z) {
        sendRequest(CallRequestBuilder.makeEnableQuantumSecurityService(i, z), null);
    }

    @Override // com.sec.internal.ims.core.handler.secims.IStackIF
    public void setQuantumSecurityInfo(int i, int i2, int i3, String str, String str2) {
        sendRequest(CallRequestBuilder.makeSetQuantumSecurityInfo(i, i2, i3, str, str2), null);
    }

    @Override // com.sec.internal.ims.core.handler.secims.IStackIF
    public void updateCmcExtCallCount(int i, int i2) {
        sendRequest(CallRequestBuilder.makeUpdateCmcExtCallCount(i, i2), null);
    }

    @Override // com.sec.internal.ims.core.handler.secims.IStackIF
    public void startVideoEarlyMedia(int i, int i2) {
        sendRequest(CallRequestBuilder.makeStartVideoEarlyMedia(i, i2), null);
    }

    @Override // com.sec.internal.ims.core.handler.secims.IStackIF
    public void sendEmergencyLocationPublish(int i) {
        Log.i(LOG_TAG, "sendEmergencyLocationPublish");
        sendRequest(CallRequestBuilder.makeSendEmergencyLocationPublish(i), null);
    }

    @Override // com.sec.internal.ims.core.handler.secims.IStackIF
    public void handleCmcCsfb(int i, int i2) {
        sendRequest(CallRequestBuilder.makeHandleCmcCsfb(i, i2), null);
    }

    void configCall(int i, boolean z, boolean z2, boolean z3) {
        IMSLog.i(LOG_TAG, i, "configCall: ttySessionRequired " + z + " rttSessionRequired " + z2 + " automode " + z3);
        sendRequest(RegistrationRequestBuilder.makeConfigCall(i, z, z2, z3), null);
    }

    public void configSrvcc(int i, int i2) {
        Log.i("StackIF[" + i + "]", "configSrvcc():  mode: " + i2);
        sendRequest(RegistrationRequestBuilder.makeConfigSrvcc(i, i2), null);
    }

    public void updateXqEnable(int i, boolean z) {
        Log.i("StackIF[" + i + "]", "updateXqEnable():  enable: " + z);
        sendRequest(RegistrationRequestBuilder.makeUpdateXqEnable(i, z), null);
    }

    public void configRCS(int i, RcsConfig rcsConfig) {
        IMSLog.i(LOG_TAG, i, "configRCS: " + rcsConfig);
        sendRequest(RegistrationRequestBuilder.makeConfigRCS(i, rcsConfig), null);
    }

    public void updateScreenOnOff(int i, int i2) {
        IMSLog.i(LOG_TAG, i, "updateScreenOnOff: on " + i2);
        sendRequest(RegistrationRequestBuilder.makeUpdateScreenOnOff(i, i2), null);
    }

    public void updateServiceVersion(int i, HashMap<String, String> hashMap) {
        Log.i("StackIF[" + i + "]", "updateServiceVersion:phoneId:" + i);
        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            Log.i("StackIF[" + i + "]", entry.getKey() + " : " + entry.getValue());
        }
        sendRequest(RegistrationRequestBuilder.makeUpdateServiceVersion(i, hashMap), null);
    }

    public void configRegistration(int i, String str) {
        if (str == null) {
            IMSLog.e(LOG_TAG, i, "configRegistration: no imei");
        } else {
            sendRequest(RegistrationRequestBuilder.makeConfigRegistration(i, str), null);
        }
    }

    @Override // com.sec.internal.ims.core.handler.secims.IStackIF
    public void updateAudioInterface(int i, String str, Message message) {
        Log.i(LOG_TAG, "updateAudioInterface: handle " + i + " mode " + str);
        sendRequest(CallRequestBuilder.makeUpdateAudioInterface(i, str), message);
    }

    @Override // com.sec.internal.ims.core.handler.secims.IStackIF
    public void startLocalRingBackTone(int i, int i2, int i3, int i4, Message message) {
        Log.i(LOG_TAG, "startLocalRingBackTone: handle " + i + ", " + i2 + ", " + i3 + ", " + i4);
        sendRequest(CallRequestBuilder.makeStartLocalRingBackTone(i, i2, i3, i4), message);
    }

    @Override // com.sec.internal.ims.core.handler.secims.IStackIF
    public void stopLocalRingBackTone(int i) {
        Log.i(LOG_TAG, "stopLocalRingBackTone: handle " + i);
        sendRequest(CallRequestBuilder.makeStopLocalRingBackTone(i), null);
    }

    @Override // com.sec.internal.ims.core.handler.secims.IStackIF
    public void setVideoCrtAudio(int i, int i2, boolean z) {
        Log.i(LOG_TAG, "setVideoCrtAudio: handle " + i + " sessionId " + i2 + " on " + z);
        sendRequest(CallRequestBuilder.makeSetVideoCrtAudio(i, i2, z), null);
    }

    @Override // com.sec.internal.ims.core.handler.secims.IStackIF
    public void sendDtmfEvent(int i, int i2, String str) {
        Log.i(LOG_TAG, "sendDtmfEvent: handle " + i + " sessionId " + i2 + " dtmfEvent " + str);
        sendRequest(CallRequestBuilder.makeSendDtmfEvent(i, i2, str), null);
    }

    @Override // com.sec.internal.ims.core.handler.secims.IStackIF
    public void startRecord(int i, int i2, String str) {
        Log.i(LOG_TAG, "startRecord: handle " + i);
        sendRequest(CallRequestBuilder.makeStartRecord(i, i2, str), null);
    }

    @Override // com.sec.internal.ims.core.handler.secims.IStackIF
    public void stopRecord(int i, int i2) {
        Log.i(LOG_TAG, "stopRecord: handle " + i);
        sendRequest(CallRequestBuilder.makeStopRecord(i, i2), null);
    }

    @Override // com.sec.internal.ims.core.handler.secims.IStackIF
    public void clearAllCallInternal(int i) {
        Log.i(LOG_TAG, "clearAllCallInternal: cmcType " + i);
        sendRequest(CallRequestBuilder.makeClearAllCallInternal(i), null);
    }

    @Override // com.sec.internal.ims.core.handler.secims.IStackIF
    public void startCmcRecord(int i, int i2, int i3, int i4, long j, int i5, String str, int i6, int i7, int i8, int i9, int i10, long j2, String str2) {
        Log.i(LOG_TAG, "startCmcRecord: handle " + i);
        sendRequest(CallRequestBuilder.makeStartCmcRecord(i, i2, i3, i4, j, i5, str, i6, i7, i8, i9, i10, j2, str2), null);
    }

    @Override // com.sec.internal.ims.core.handler.secims.IStackIF
    public void sendSms(int i, String str, String str2, String str3, String str4, String str5, String str6, boolean z, Message message) {
        FlatBufferBuilder flatBufferBuilder = new FlatBufferBuilder(0);
        int createString = flatBufferBuilder.createString(str);
        int createString2 = flatBufferBuilder.createString(str2);
        int createString3 = flatBufferBuilder.createString(str3);
        int createString4 = flatBufferBuilder.createString(str4);
        int createString5 = flatBufferBuilder.createString(str5);
        int createString6 = str6 != null ? flatBufferBuilder.createString(str6) : -1;
        RequestSendMsg.startRequestSendMsg(flatBufferBuilder);
        RequestSendMsg.addHandle(flatBufferBuilder, i);
        RequestSendMsg.addSmsc(flatBufferBuilder, createString);
        RequestSendMsg.addLocalUri(flatBufferBuilder, createString2);
        RequestSendMsg.addContentLen(flatBufferBuilder, str3.length() / 2);
        RequestSendMsg.addContentBody(flatBufferBuilder, createString3);
        RequestSendMsg.addContentType(flatBufferBuilder, createString4);
        RequestSendMsg.addContentSubType(flatBufferBuilder, createString5);
        if (createString6 != -1) {
            RequestSendMsg.addInReplyTo(flatBufferBuilder, createString6);
        }
        RequestSendMsg.addIsEmergency(flatBufferBuilder, z);
        int endRequestSendMsg = RequestSendMsg.endRequestSendMsg(flatBufferBuilder);
        Request.startRequest(flatBufferBuilder);
        Request.addReqid(flatBufferBuilder, 401);
        Request.addReqType(flatBufferBuilder, (byte) 33);
        Request.addReq(flatBufferBuilder, endRequestSendMsg);
        sendRequest(flatBufferBuilder, Request.endRequest(flatBufferBuilder), message);
    }

    @Override // com.sec.internal.ims.core.handler.secims.IStackIF
    public void sendSmsRpAckResponse(int i, String str) {
        FlatBufferBuilder flatBufferBuilder = new FlatBufferBuilder(0);
        int createString = str != null ? flatBufferBuilder.createString(str) : -1;
        RequestSendRpAckResp.startRequestSendRpAckResp(flatBufferBuilder);
        RequestSendRpAckResp.addHandle(flatBufferBuilder, i);
        if (createString != -1) {
            RequestSendRpAckResp.addCallId(flatBufferBuilder, createString);
        }
        int endRequestSendRpAckResp = RequestSendRpAckResp.endRequestSendRpAckResp(flatBufferBuilder);
        Request.startRequest(flatBufferBuilder);
        Request.addReqid(flatBufferBuilder, 402);
        Request.addReqType(flatBufferBuilder, (byte) 34);
        Request.addReq(flatBufferBuilder, endRequestSendRpAckResp);
        sendRequest(flatBufferBuilder, Request.endRequest(flatBufferBuilder), null);
    }

    @Override // com.sec.internal.ims.core.handler.secims.IStackIF
    public void sendSmsResponse(int i, String str, int i2) {
        FlatBufferBuilder flatBufferBuilder = new FlatBufferBuilder(0);
        int createString = str != null ? flatBufferBuilder.createString(str) : -1;
        RequestReceiveSmsResp.startRequestReceiveSmsResp(flatBufferBuilder);
        RequestReceiveSmsResp.addHandle(flatBufferBuilder, i);
        if (createString != -1) {
            RequestReceiveSmsResp.addCallId(flatBufferBuilder, createString);
        }
        RequestReceiveSmsResp.addStatus(flatBufferBuilder, i2);
        int endRequestReceiveSmsResp = RequestReceiveSmsResp.endRequestReceiveSmsResp(flatBufferBuilder);
        Request.startRequest(flatBufferBuilder);
        Request.addReqid(flatBufferBuilder, 403);
        Request.addReqType(flatBufferBuilder, (byte) 35);
        Request.addReq(flatBufferBuilder, endRequestReceiveSmsResp);
        sendRequest(flatBufferBuilder, Request.endRequest(flatBufferBuilder), null);
    }

    public void sendAlarmWakeUp(int i) {
        FlatBufferBuilder flatBufferBuilder = new FlatBufferBuilder(0);
        RequestAlarmWakeUp.startRequestAlarmWakeUp(flatBufferBuilder);
        RequestAlarmWakeUp.addId(flatBufferBuilder, i);
        int endRequestAlarmWakeUp = RequestAlarmWakeUp.endRequestAlarmWakeUp(flatBufferBuilder);
        Request.startRequest(flatBufferBuilder);
        Request.addReqid(flatBufferBuilder, 901);
        Request.addReqType(flatBufferBuilder, ReqMsg.request_alarm_wake_up);
        Request.addReq(flatBufferBuilder, endRequestAlarmWakeUp);
        sendRequest(flatBufferBuilder, Request.endRequest(flatBufferBuilder), null);
    }

    public void sendX509CertVerifyResponse(boolean z, String str) {
        Log.i(LOG_TAG, "sendX509CertVerifyResponse(): result " + z + ", reason " + str);
        FlatBufferBuilder flatBufferBuilder = new FlatBufferBuilder(0);
        int createString = !TextUtils.isEmpty(str) ? flatBufferBuilder.createString(str) : -1;
        RequestX509CertVerifyResult.startRequestX509CertVerifyResult(flatBufferBuilder);
        if (createString != -1) {
            RequestX509CertVerifyResult.addReason(flatBufferBuilder, createString);
        }
        RequestX509CertVerifyResult.addResult(flatBufferBuilder, z);
        int endRequestX509CertVerifyResult = RequestX509CertVerifyResult.endRequestX509CertVerifyResult(flatBufferBuilder);
        Request.startRequest(flatBufferBuilder);
        Request.addReqid(flatBufferBuilder, 902);
        Request.addReqType(flatBufferBuilder, ReqMsg.request_x509_cert_verify_result);
        Request.addReq(flatBufferBuilder, endRequestX509CertVerifyResult);
        sendRequest(flatBufferBuilder, Request.endRequest(flatBufferBuilder), null);
    }

    @Override // com.sec.internal.ims.core.handler.secims.IStackIF
    public void sendMediaEvent(int i, int i2, int i3, int i4) {
        Log.i(LOG_TAG, "sendMediaEvent(): target " + i2 + ", event " + i3 + ", type " + i4);
        FlatBufferBuilder flatBufferBuilder = new FlatBufferBuilder(0);
        RequestSendMediaEvent.startRequestSendMediaEvent(flatBufferBuilder);
        RequestSendMediaEvent.addEventType(flatBufferBuilder, (long) i4);
        RequestSendMediaEvent.addEvent(flatBufferBuilder, (long) i3);
        RequestSendMediaEvent.addTarget(flatBufferBuilder, (long) i2);
        RequestSendMediaEvent.addHandle(flatBufferBuilder, (long) i);
        int endRequestSendMediaEvent = RequestSendMediaEvent.endRequestSendMediaEvent(flatBufferBuilder);
        Request.startRequest(flatBufferBuilder);
        if (i4 == 1 && (i2 == 0 || i2 == 1)) {
            Request.addReqid(flatBufferBuilder, 236);
        } else {
            Request.addReqid(flatBufferBuilder, 230);
        }
        Request.addReqType(flatBufferBuilder, (byte) 77);
        Request.addReq(flatBufferBuilder, endRequestSendMediaEvent);
        sendRequest(flatBufferBuilder, Request.endRequest(flatBufferBuilder), null);
    }

    public void sendRelayEvent(int i, int i2) {
        Log.i(LOG_TAG, "sendRelayEvent(): stream " + i + ", event " + i2);
        FlatBufferBuilder flatBufferBuilder = new FlatBufferBuilder(0);
        RequestSendRelayEvent.startRequestSendRelayEvent(flatBufferBuilder);
        RequestSendRelayEvent.addStreamId(flatBufferBuilder, (long) i);
        RequestSendRelayEvent.addEvent(flatBufferBuilder, (long) i2);
        int endRequestSendRelayEvent = RequestSendRelayEvent.endRequestSendRelayEvent(flatBufferBuilder);
        Request.startRequest(flatBufferBuilder);
        Request.addReqid(flatBufferBuilder, 237);
        Request.addReqType(flatBufferBuilder, (byte) 78);
        Request.addReq(flatBufferBuilder, endRequestSendRelayEvent);
        sendRequest(flatBufferBuilder, Request.endRequest(flatBufferBuilder), null);
    }

    public void setTextMode(int i, int i2) {
        IMSLog.i(LOG_TAG, i, "setTextMode(): mode: " + i2);
        sendRequest(RegistrationRequestBuilder.makeSetTextMode(i, i2), null);
    }

    public void sendRtpStatsToStack(IMSMediaEvent.AudioRtpStats audioRtpStats) {
        Log.i(LOG_TAG, "sendRtpStatsToStack()");
        FlatBufferBuilder flatBufferBuilder = new FlatBufferBuilder(0);
        RequestRtpStatsToStack.startRequestRtpStatsToStack(flatBufferBuilder);
        RequestRtpStatsToStack.addDirection(flatBufferBuilder, audioRtpStats.mDirection);
        RequestRtpStatsToStack.addMeasuredperiod(flatBufferBuilder, audioRtpStats.mMeasuredPeriod);
        RequestRtpStatsToStack.addJitter(flatBufferBuilder, audioRtpStats.mJitter);
        RequestRtpStatsToStack.addDelay(flatBufferBuilder, audioRtpStats.mDelay);
        RequestRtpStatsToStack.addLossrate(flatBufferBuilder, audioRtpStats.mLossData);
        RequestRtpStatsToStack.addChannelid(flatBufferBuilder, audioRtpStats.mChannelId);
        int endRequestRtpStatsToStack = RequestRtpStatsToStack.endRequestRtpStatsToStack(flatBufferBuilder);
        Request.startRequest(flatBufferBuilder);
        Request.addReqid(flatBufferBuilder, Id.REQUEST_RTP_STATS_TO_STACK);
        Request.addReqType(flatBufferBuilder, ReqMsg.request_rtp_stats_to_stack);
        Request.addReq(flatBufferBuilder, endRequestRtpStatsToStack);
        sendRequest(flatBufferBuilder, Request.endRequest(flatBufferBuilder), null);
    }

    @Override // com.sec.internal.ims.core.handler.secims.IStackIF
    public void replyWithIdc(int i, int i2, int i3, int i4, String str) {
        Log.i(LOG_TAG, "replayWithIdc(): sessionId " + i + ", idcExtra " + str);
        sendRequest(CallRequestBuilder.makeReplyWithIdc(i, i2, i3, i4, str), null);
    }

    @Override // com.sec.internal.ims.core.handler.secims.IStackIF
    public void updateWithIdc(int i, int i2, String str) {
        sendRequest(CallRequestBuilder.makeUpdateWithIdc(i, i2, str), null);
    }

    @Override // com.sec.internal.ims.core.handler.secims.IStackIF
    public void sendNegotiatedLocalSdp(int i, String str) {
        sendRequest(CallRequestBuilder.makeSendNegotiatedLocalSdp(i, str), null);
    }

    private ImsRequest findAndRemoveRequest(int i) {
        synchronized (this.mRequestList) {
            Iterator<ImsRequest> it = this.mRequestList.iterator();
            while (it.hasNext()) {
                ImsRequest next = it.next();
                if (next.mTid == i) {
                    it.remove();
                    return next;
                }
            }
            return null;
        }
    }

    private static synchronized void processMessage(byte[] bArr, int i) {
        synchronized (StackIF.class) {
            byte[] bArr2 = new byte[i];
            synchronized (bArr2) {
                System.arraycopy(bArr, 0, bArr2, 0, i);
                ImsBuffer rootAsImsBuffer = ImsBuffer.getRootAsImsBuffer(ByteBuffer.wrap(bArr2));
                long trid = rootAsImsBuffer.trid();
                byte msgType = rootAsImsBuffer.msgType();
                Log.i(LOG_TAG, "processMessage " + ((int) msgType));
                if (msgType == 3) {
                    Log.i(LOG_TAG, "Processing Notify");
                    getInstance().processNotify((Notify) rootAsImsBuffer.msg(new Notify()));
                } else if (msgType == 2) {
                    Log.i(LOG_TAG, "Processing Response");
                    getInstance().processResponse((int) trid, (Response) rootAsImsBuffer.msg(new Response()));
                }
            }
        }
    }

    private static void ImsLogC(int i, String str, boolean z) {
        IMSLog.c(i, str, z);
    }

    private void processResponse(int i, Response response) {
        Object obj;
        Message message;
        int resid = response.resid();
        Log.i("SECIMSJ", serialString(i) + "< " + resid);
        StringBuilder sb = new StringBuilder();
        sb.append("processResponse: reqId ");
        sb.append(resid);
        Log.i(LOG_TAG, sb.toString());
        if (response.respType() == 1) {
            GeneralResponse generalResponse = (GeneralResponse) response.resp(new GeneralResponse());
            int handle = ImsUtil.getHandle(generalResponse.handle());
            StringBuilder sb2 = new StringBuilder();
            sb2.append("processResponse: handle ");
            if (handle == 0) {
                handle = -1;
            }
            sb2.append(handle);
            sb2.append(" result ");
            sb2.append(generalResponse.result());
            sb2.append(" reason ");
            sb2.append(generalResponse.reason());
            Log.i(LOG_TAG, sb2.toString());
            obj = generalResponse;
        } else if (response.respType() == 2) {
            CallResponse callResponse = (CallResponse) response.resp(new CallResponse());
            Log.i(LOG_TAG, "processCallResponse: handle " + ImsUtil.getHandle(callResponse.handle()));
            obj = callResponse;
        } else if (response.respType() == 4) {
            Log.i(LOG_TAG, "processSendSmsResp:");
            obj = response.resp(new SendSmsResponse());
        } else if (response.respType() == 5) {
            Log.i(LOG_TAG, "processStartSessionResp:");
            obj = response.resp(new StartSessionResponse());
        } else if (response.respType() == 6) {
            Log.i(LOG_TAG, "processCloseSessionResp:");
            obj = response.resp(new CloseSessionResponse());
        } else if (response.respType() == 7) {
            Log.i(LOG_TAG, "processStartMediaResp:");
            obj = response.resp(new StartMediaResponse());
        } else if (response.respType() == 8) {
            Log.i(LOG_TAG, "processSendImMessageResp:");
            obj = response.resp(new SendImMessageResponse());
        } else if (response.respType() == 9) {
            Log.i(LOG_TAG, "processSendImNotiResp:");
            obj = response.resp(new SendImNotiResponse());
        } else if (response.respType() == 11) {
            Log.i(LOG_TAG, "processSendSlmResponse:");
            obj = response.resp(new SendSlmResponse());
        } else if (response.respType() == 13) {
            Log.i(LOG_TAG, "processXdmGeneralResponse");
            obj = response.resp(new XdmGeneralResponse());
        } else if (response.respType() == 14) {
            Log.i(LOG_TAG, "processCshGeneralResponse");
            obj = response.resp(new CshGeneralResponse());
        } else if (response.respType() == 10) {
            Log.i(LOG_TAG, "processUpdateParticipantsResp");
            obj = response.resp(new UpdateParticipantsResponse());
        } else if (response.respType() == 12) {
            Log.i(LOG_TAG, "processSendMessageRevokeInternalResp");
            obj = response.resp(new SendMessageRevokeInternalResponse());
        } else if (response.respType() == 15) {
            Log.i(LOG_TAG, "processSendEucResponseResponse");
            obj = response.resp(new SendEucResponseResponse());
        } else {
            if (response.respType() == 16) {
                Log.i(LOG_TAG, "processSipdialogGeneralResp");
                SipdialogGeneralResponse sipdialogGeneralResponse = (SipdialogGeneralResponse) response.resp(new SipdialogGeneralResponse());
                if (sipdialogGeneralResponse.success() && sipdialogGeneralResponse.sipmessage() != null) {
                    obj = sipdialogGeneralResponse.sipmessage();
                }
            }
            obj = null;
        }
        ImsRequest findAndRemoveRequest = findAndRemoveRequest(i);
        if (findAndRemoveRequest == null || (message = findAndRemoveRequest.mResult) == null) {
            return;
        }
        AsyncResult.forMessage(message, obj, null);
        findAndRemoveRequest.mResult.sendToTarget();
    }

    private static String replaceImpuPatterns(Pattern pattern, String str) {
        Matcher matcher = pattern.matcher(str);
        StringBuffer stringBuffer = new StringBuffer();
        StringBuilder sb = new StringBuilder();
        while (matcher.find()) {
            String group = matcher.group(0);
            int indexOf = group.indexOf(":") + 1;
            int length = group.length() - indexOf;
            int i = length >= 11 ? 3 : (length + 1) / 4;
            if (i != 0) {
                int i2 = length - (i * 2);
                sb.setLength(0);
                sb.append((CharSequence) group, 0, indexOf + i);
                for (int i3 = 0; i3 < i2; i3++) {
                    sb.append("x");
                }
                int i4 = indexOf + length;
                sb.append((CharSequence) group, i4 - i, i4);
                matcher.appendReplacement(stringBuffer, sb.toString());
            }
        }
        matcher.appendTail(stringBuffer);
        return stringBuffer.toString();
    }

    private static String hidePrivateInfoFromSipMsg(String str) {
        if (!Debug.isProductShip()) {
            return str;
        }
        String replaceAll = GROUPCHAT_SUBJECT_PATTERN.matcher(GROUPCHAT_ALIAS_PATTERN.matcher(SESSION_DESCRIPTION_PATTERN.matcher(TO_PATTERN.matcher(FROM_PATTERN.matcher(XCAP_USER_PATTERN.matcher(PHONE_NUMBER_XML_PATTERN.matcher(PHONE_NUMBER_PATTERN.matcher(SDP_O_LINE_PATTERN.matcher(P_ACCESS_NETWORK_PATTERN.matcher(USERNAME_PATTERN.matcher(IMEI_PATTERN.matcher(replaceImpuPatterns(IMPU_TEL_PATTERN, replaceImpuPatterns(IMPU_SIP_PATTERN, IPV6_PATTERN.matcher(IPV4_PATTERN.matcher(str).replaceAll("xxx.xxx.xxx.xxx")).replaceAll("xxxx:xxxx:xxxx:xxxx")))).replaceAll("imei:xxxxxxxx")).replaceAll("username=xxxxxxxxxxxxxxx")).replaceAll("P-Access-Network-Info: xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")).replaceAll("o=xxxxxxxxxxxxxxx")).replaceAll("\"xxxxxxxxxxxxxxx\"")).replaceAll(">xxxxxxxxxxxxxxx<")).replaceAll("target>xxxxxxxxxxxxxxx</target")).replaceAll("From: xxxxxxxxxxxxxxx")).replaceAll("To: xxxxxxxxxxxxxxx")).replaceAll("session-description>xxxxxxxxxxxxxxx</session-description")).replaceAll("From: \"xxxxxxxx\"")).replaceAll("Subject: xxxxxxxx");
        Matcher matcher = TEXTPLAIN_HEADER_PATTERN.matcher(replaceAll);
        Matcher matcher2 = TEXTPLAIN_CONTENT_PATTERN.matcher(replaceAll);
        if (!matcher.find() || !matcher2.find(matcher.end())) {
            return replaceAll;
        }
        StringBuffer stringBuffer = new StringBuffer();
        matcher2.appendReplacement(stringBuffer, "\n\nxxxxx");
        matcher2.appendTail(stringBuffer);
        return stringBuffer.toString();
    }

    private void processNotify(Notify notify) {
        String str;
        int notifyid = notify.notifyid();
        Log.i("SECIMSJ", "[UNSL]< " + notifyid);
        Log.i(LOG_TAG, "processNotify: id " + notifyid);
        if (notifyid == 10001) {
            if (notify.notiType() == 1) {
                RegistrationStatus registrationStatus = (RegistrationStatus) notify.noti(new RegistrationStatus());
                int handle = ImsUtil.getHandle(registrationStatus.handle());
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < registrationStatus.serviceListLength(); i++) {
                    arrayList.add(registrationStatus.serviceList(i));
                }
                ArrayList arrayList2 = new ArrayList();
                for (int i2 = 0; i2 < registrationStatus.impuListLength(); i2++) {
                    arrayList2.add(registrationStatus.impuList(i2));
                }
                Log.i(LOG_TAG, "RegistrationStatus - handle " + handle + " serviceList " + arrayList + " regiType " + RegiType.name(registrationStatus.regiType()) + " code " + registrationStatus.respCode() + " reason " + registrationStatus.respReason() + " ecmpMode " + registrationStatus.ecmpMode() + " retryAfter " + registrationStatus.retryAfter() + " reason header " + registrationStatus.reasonHeader());
                SipError sipError = new SipError(registrationStatus.respCode(), registrationStatus.respReason(), registrationStatus.reasonHeader());
                StackEventListener stackEventListener = this.mUaListenerList.get(Integer.valueOf(handle));
                if (stackEventListener != null) {
                    int regiType = registrationStatus.regiType();
                    if (regiType == 0) {
                        stackEventListener.onRegistered(handle, arrayList, arrayList2, sipError, registrationStatus.retryAfter(), registrationStatus.ecmpMode(), this.mUaRegisterResponseRawSip.get(Integer.valueOf(handle)));
                        return;
                    } else {
                        if (regiType == 1 || regiType == 2) {
                            this.mUaRegisterResponseRawSip.remove(Integer.valueOf(handle));
                            stackEventListener.onDeregistered(handle, sipError, registrationStatus.retryAfter(), regiType == 2);
                            return;
                        }
                        return;
                    }
                }
                return;
            }
            Log.i(LOG_TAG, "processNotify: msg not found.");
            return;
        }
        if (notifyid == 10025) {
            Log.i(LOG_TAG, "receive registered impu");
            if (notify.notiType() == 4) {
                RegistrationImpu registrationImpu = (RegistrationImpu) notify.noti(new RegistrationImpu());
                int handle2 = ImsUtil.getHandle(registrationImpu.handle());
                String impu = registrationImpu.impu();
                Log.v(LOG_TAG, "Handle: " + handle2 + " - impu: " + IMSLog.checker(impu));
                StackEventListener stackEventListener2 = this.mUaListenerList.get(Integer.valueOf(handle2));
                if (stackEventListener2 != null) {
                    stackEventListener2.onRegImpuNotification(handle2, impu);
                    return;
                }
                return;
            }
            return;
        }
        if (notifyid == 10013) {
            if (notify.notiType() == 3) {
                SubscribeStatus subscribeStatus = (SubscribeStatus) notify.noti(new SubscribeStatus());
                SipError sipError2 = new SipError((int) subscribeStatus.respCode(), subscribeStatus.respReason());
                int handle3 = ImsUtil.getHandle(subscribeStatus.handle());
                StackEventListener stackEventListener3 = this.mUaListenerList.get(Integer.valueOf(handle3));
                if (stackEventListener3 != null) {
                    stackEventListener3.onSubscribed(handle3, sipError2);
                    return;
                }
                return;
            }
            return;
        }
        if (notifyid == 10007) {
            if (notify.notiType() == 9) {
                Log.i(LOG_TAG, "RegiInfoChanged");
                RegInfoChanged regInfoChanged = (RegInfoChanged) notify.noti(new RegInfoChanged());
                int handle4 = ImsUtil.getHandle(regInfoChanged.handle());
                StackEventListener stackEventListener4 = this.mUaListenerList.get(Integer.valueOf(handle4));
                if (stackEventListener4 != null) {
                    stackEventListener4.onRegInfoNotification(handle4, regInfoChanged);
                    return;
                }
                return;
            }
            return;
        }
        if (notifyid == 10002) {
            if (notify.notiType() == 2) {
                RegistrationAuth registrationAuth = (RegistrationAuth) notify.noti(new RegistrationAuth());
                int handle5 = ImsUtil.getHandle(registrationAuth.handle());
                Log.i(LOG_TAG, "RegistrationAuth - handle " + handle5 + " nonce " + registrationAuth.nonce());
                StackEventListener stackEventListener5 = this.mUaListenerList.get(Integer.valueOf(handle5));
                if (stackEventListener5 != null) {
                    Log.i(LOG_TAG, "calling onISIMAuthRequested.");
                    stackEventListener5.onISIMAuthRequested(handle5, registrationAuth.nonce(), (int) registrationAuth.recvMng());
                    return;
                } else {
                    Log.i(LOG_TAG, " mUaListener not found.");
                    return;
                }
            }
            Log.i(LOG_TAG, "processNotify: msg not found.");
            return;
        }
        if (notifyid == 10004) {
            if (notify.notiType() == 6) {
                CallStatus callStatus = (CallStatus) notify.noti(new CallStatus());
                Log.i(LOG_TAG, "CallStatus - handle " + ImsUtil.getHandle(callStatus.handle()) + " session " + callStatus.session() + " status " + callStatus.state());
                this.mCallStatusRegistrants.notifyResult(callStatus);
                return;
            }
            return;
        }
        if (notifyid == 10005) {
            if (notify.notiType() == 7) {
                this.mNewIncomingCallRegistrants.notifyResult((IncomingCall) notify.noti(new IncomingCall()));
                return;
            }
            return;
        }
        if (notifyid == 10014) {
            if (notify.notiType() == 15) {
                this.mModifyVideoRegistrants.notifyResult((ModifyVideoData) notify.noti(new ModifyVideoData()));
                return;
            }
            return;
        }
        if (notifyid == 10015) {
            if (notify.notiType() == 16) {
                this.mVideoEventRegistrants.notifyResult((NotifyVideoEventData) notify.noti(new NotifyVideoEventData()));
                return;
            }
            return;
        }
        if (notifyid == 10035) {
            if (notify.notiType() == 17) {
                this.mCmcRecordEventRegistrants.notifyResult((NotifyCmcRecordEventData) notify.noti(new NotifyCmcRecordEventData()));
                return;
            }
            return;
        }
        if (notifyid == 10006) {
            if (notify.notiType() == 8) {
                ConfCallChanged confCallChanged = (ConfCallChanged) notify.noti(new ConfCallChanged());
                if (confCallChanged == null) {
                    Log.e(LOG_TAG, "cc is null");
                    return;
                }
                Log.i(LOG_TAG, "ConfCallChanged: session " + confCallChanged.session() + " event " + confCallChanged.event() + " participants " + confCallChanged.participantsLength());
                int participantsLength = confCallChanged.participantsLength();
                Participant[] participantArr = new Participant[participantsLength];
                for (int i3 = 0; i3 < confCallChanged.participantsLength(); i3++) {
                    participantArr[i3] = confCallChanged.participants(i3);
                }
                for (int i4 = 0; i4 < participantsLength; i4++) {
                    Participant participant = participantArr[i4];
                    Log.i(LOG_TAG, "   " + IMSLog.checker(participant.uri()) + " : " + participant.status());
                }
                this.mConferenceUpdateRegistrants.notifyResult(confCallChanged);
                return;
            }
            return;
        }
        if (notifyid == 10008) {
            ReferReceived referReceived = (ReferReceived) notify.noti(new ReferReceived());
            Log.i(LOG_TAG, "ReferReceived:");
            this.mReferReceivedRegistrants.notifyResult(referReceived);
            return;
        }
        if (notifyid == 10009) {
            ReferStatus referStatus = (ReferStatus) notify.noti(new ReferStatus());
            if (referStatus == null) {
                Log.e(LOG_TAG, "rs is null");
                return;
            }
            Log.i(LOG_TAG, "ReferStatus: session=" + referStatus.session() + " resp=" + referStatus.statusCode());
            this.mReferStatusRegistrants.notifyResult(referStatus);
            return;
        }
        if (notifyid == 10011) {
            if (notify.notiType() == 13) {
                ModifyCallData modifyCallData = (ModifyCallData) notify.noti(new ModifyCallData());
                if (modifyCallData == null) {
                    Log.e(LOG_TAG, "modCallData is null");
                    return;
                }
                Log.i(LOG_TAG, "ModifyCall - session: " + modifyCallData.session() + ", oldCallType: " + modifyCallData.oldType() + ", newCallType: " + modifyCallData.newType());
                this.mModifyCallRegistrants.notifyResult(modifyCallData);
                return;
            }
            return;
        }
        if (notifyid == 10010) {
            if (notify.notiType() == 12) {
                UpdateRouteTable updateRouteTable = (UpdateRouteTable) notify.noti(new UpdateRouteTable());
                if (updateRouteTable == null) {
                    Log.e(LOG_TAG, "ur is null");
                    return;
                }
                int handle6 = ImsUtil.getHandle(updateRouteTable.handle());
                Log.i(LOG_TAG, "UpdateRouteTable - handle " + handle6 + " op " + updateRouteTable.operation() + " addr " + updateRouteTable.address());
                StackEventListener stackEventListener6 = this.mUaListenerList.get(Integer.valueOf(handle6));
                if (stackEventListener6 != null) {
                    Log.i(LOG_TAG, "calling UpdateRouteTable.");
                    stackEventListener6.onUpdateRouteTableRequested(handle6, updateRouteTable.operation(), updateRouteTable.address());
                    return;
                }
                return;
            }
            Log.i(LOG_TAG, "processNotify: msg not found.");
            return;
        }
        if (notifyid == 10023) {
            if (notify.notiType() == 1) {
                int handle7 = ImsUtil.getHandle(((RegistrationStatus) notify.noti(new RegistrationStatus())).handle());
                Log.i(LOG_TAG, "calling onUpdate Pani");
                StackEventListener stackEventListener7 = this.mUaListenerList.get(Integer.valueOf(handle7));
                if (stackEventListener7 != null) {
                    stackEventListener7.onUpdatePani();
                    return;
                }
                return;
            }
            return;
        }
        if (notifyid == 10026) {
            if (notify.notiType() == 1) {
                RegistrationStatus registrationStatus2 = (RegistrationStatus) notify.noti(new RegistrationStatus());
                int handle8 = ImsUtil.getHandle(registrationStatus2.handle());
                Log.i("StackIF[" + registrationStatus2.handle() + "]", "calling onRefreshRegNotification");
                StackEventListener stackEventListener8 = this.mUaListenerList.get(Integer.valueOf(handle8));
                if (stackEventListener8 != null) {
                    stackEventListener8.onRefreshRegNotification(handle8);
                    return;
                }
                return;
            }
            return;
        }
        if (notifyid == 20008) {
            Log.i(LOG_TAG, "Echolocate Notify receive");
            if (notify.notiType() == 58) {
                this.mEcholocateRegistrants.notifyResult((EcholocateMsg) notify.noti(new EcholocateMsg()));
                return;
            }
            return;
        }
        if (notifyid == 20004) {
            ReceiveSmsNotification receiveSmsNotification = (ReceiveSmsNotification) notify.noti(new ReceiveSmsNotification());
            Log.i(LOG_TAG, "ReceiveSmsNotification: ");
            this.mNewIncomingSmsRegistrants.notifyResult(receiveSmsNotification);
            return;
        }
        if (notifyid == 20003) {
            SmsRpAckNotification smsRpAckNotification = (SmsRpAckNotification) notify.noti(new SmsRpAckNotification());
            Log.i(LOG_TAG, "SmsRpAckNotification: ");
            this.mSmsRpAckRegistrants.notifyResult(smsRpAckNotification);
            return;
        }
        if (notifyid == 10003) {
            if (notify.notiType() == 57) {
                SipMessage sipMessage = (SipMessage) notify.noti(new SipMessage());
                String format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss.SSS", Locale.US).format(new Date());
                if (sipMessage.origin() == 0) {
                    return;
                }
                String sipMessage2 = sipMessage.sipMessage() != null ? sipMessage.sipMessage() : "";
                String[] split = sipMessage2.split("\r\n");
                int length = split.length;
                String str2 = "";
                int i5 = 0;
                while (true) {
                    if (i5 >= length) {
                        break;
                    }
                    String str3 = split[i5];
                    if (str2.isEmpty()) {
                        str2 = str3;
                    }
                    if (str3.toLowerCase().contains("cseq")) {
                        str2 = str2 + " [" + str3 + "]\n";
                        break;
                    }
                    i5++;
                }
                String hidePrivateInfoFromSipMsg = hidePrivateInfoFromSipMsg(str2);
                int phoneId = (int) sipMessage.phoneId();
                String str4 = "SIPMSG[" + phoneId + "]";
                int rawContentsLength = sipMessage.rawContentsLength();
                byte[] bArr = {0};
                if (rawContentsLength > 0) {
                    bArr = new byte[rawContentsLength];
                    for (int i6 = 0; i6 < rawContentsLength; i6++) {
                        bArr[i6] = (byte) sipMessage.rawContents(i6);
                    }
                }
                Bundle bundle = new Bundle();
                bundle.putInt("phoneId", phoneId);
                bundle.putString("message", sipMessage2);
                bundle.putByteArray("rawContents", bArr);
                if (sipMessage.direction() == 0) {
                    Log.i(str4, "[-->] " + hidePrivateInfoFromSipMsg);
                    this.mRawSipOutgoingRegistrants.notifyResult(bundle);
                } else {
                    Log.i(str4, "[<--] " + hidePrivateInfoFromSipMsg);
                    this.mRawSipIncomingRegistrants.notifyResult(bundle);
                    if (hidePrivateInfoFromSipMsg.toLowerCase().contains(SoftphoneContract.SoftphoneAccount.REGISTER) && hidePrivateInfoFromSipMsg.contains("200")) {
                        this.mUaRegisterResponseRawSip.put(Integer.valueOf(this.mHandle), sipMessage2);
                    }
                }
                IImsFramework iImsFramework = this.mImsFramework;
                if (iImsFramework != null) {
                    iImsFramework.getImsDiagMonitor().onIndication(0, sipMessage2, 0, sipMessage.direction(), phoneId, format, "", "", sipMessage.hexContents());
                }
                if (IMSLog.isEngMode() || !Debug.isProductShip()) {
                    Log.i(str4, sipMessage2);
                    recordSipHistory(new SipDebugMessage(sipMessage2, hidePrivateInfoFromSipMsg, format, sipMessage.direction() == 1, phoneId));
                } else {
                    Mno translateMnoInverse = StackRequestBuilderUtil.translateMnoInverse(sipMessage.mno());
                    boolean z = !sipMessage.isRcsProfile() || (translateMnoInverse != null && translateMnoInverse.isEur());
                    boolean z2 = z;
                    if (z) {
                        String hidePrivateInfoFromSipMsg2 = hidePrivateInfoFromSipMsg(sipMessage2);
                        if (z2) {
                            hidePrivateInfoFromSipMsg2 = IMSLog.dx(str4, hidePrivateInfoFromSipMsg2);
                        } else {
                            Log.i(str4, hidePrivateInfoFromSipMsg2);
                        }
                        str = hidePrivateInfoFromSipMsg2;
                    } else {
                        str = "";
                    }
                    recordSipHistory(new SipDebugMessage(str, hidePrivateInfoFromSipMsg, format, sipMessage.direction() == 1, phoneId, z2));
                }
                this.mSIPMSGRegistrants.notifyResult(notify);
                return;
            }
            return;
        }
        if (notifyid == 10018) {
            XCapMessage xCapMessage = (XCapMessage) notify.noti(new XCapMessage());
            String format2 = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss.SSS", Locale.getDefault()).format(new Date());
            String hidePrivateInfoFromSipMsg3 = hidePrivateInfoFromSipMsg(xCapMessage.xcapMessage());
            IImsFramework iImsFramework2 = this.mImsFramework;
            if (iImsFramework2 != null) {
                iImsFramework2.getImsDiagMonitor().onIndication(1, hidePrivateInfoFromSipMsg3, 100, xCapMessage.direction(), format2, "", "", "");
            }
            recordSipHistory(new SipDebugMessage(hidePrivateInfoFromSipMsg3, "", format2, xCapMessage.direction() != 0, -1));
            return;
        }
        if (notifyid == 10019) {
            this.mSSEventRegistrants.notifyResult((SSGetGbaKey) notify.noti(new SSGetGbaKey()));
            return;
        }
        if (notifyid == 20001 || notifyid == 20014) {
            if (notify.notiType() == 74) {
                AlarmWakeUp alarmWakeUp = (AlarmWakeUp) notify.noti(new AlarmWakeUp());
                int id = (int) alarmWakeUp.id();
                int delay = (int) alarmWakeUp.delay();
                MiscEventListener miscEventListener = this.mMiscListener;
                if (miscEventListener != null) {
                    miscEventListener.onAlarmRequested(id, delay, notifyid == 20014);
                    return;
                }
                return;
            }
            Log.i(LOG_TAG, "processNotify: msg not found.");
            return;
        }
        if (notifyid == 20002) {
            if (notify.notiType() == 75) {
                int id2 = (int) ((CancelAlarm) notify.noti(new CancelAlarm())).id();
                MiscEventListener miscEventListener2 = this.mMiscListener;
                if (miscEventListener2 != null) {
                    miscEventListener2.onAlarmCancelled(id2);
                    return;
                }
                return;
            }
            Log.i(LOG_TAG, "processNotify: msg not found.");
            return;
        }
        if (notifyid == 11004 || notifyid == 11002 || notifyid == 11005 || notifyid == 11007 || notifyid == 11003 || notifyid == 19000 || notifyid == 11008 || notifyid == 12001 || notifyid == 12004 || notifyid == 12005 || notifyid == 12003 || notifyid == 12002 || notifyid == 11001 || notifyid == 11009 || notifyid == 11010 || notifyid == 11011 || notifyid == 11012 || notifyid == 11013 || notifyid == 11014 || notifyid == 20013 || notifyid == 20012 || notifyid == 20011) {
            Log.i(LOG_TAG, "processNotify: IM/FT notify received " + notifyid);
            this.mImRegistrants.notifyResult(notify);
            return;
        }
        if (notifyid == 11006 || notifyid == 11015) {
            this.mImdnRegistrants.notifyResult(notify);
            return;
        }
        if (notifyid == 18000 || notifyid == 18001 || notifyid == 18003 || notifyid == 18002 || notifyid == 18004 || notifyid == 18005) {
            this.mSlmRegistrants.notifyResult(notify);
            return;
        }
        if (notifyid == 13001 || notifyid == 13002 || notifyid == 13003 || notifyid == 13004 || notifyid == 13005) {
            this.mPresenceRegistrants.notifyResult(notify);
            return;
        }
        if (notifyid == 14001 || notifyid == 14002 || notifyid == 14003 || notifyid == 14004 || notifyid == 14005) {
            this.mXdmRegistrants.notifyResult(notify);
            return;
        }
        if (notifyid == 15001) {
            Log.i(LOG_TAG, "received NOTIFY_OPTIONS_RECEIVED");
            this.mOptionsRegistrants.notifyResult(notify);
            return;
        }
        if (notifyid == 20005) {
            this.mDialogEventRegistrants.notifyResult((DialogEvent) notify.noti(new DialogEvent()));
            return;
        }
        if (notifyid == 20006) {
            Log.i(LOG_TAG, "received NOTIFY_X509_CERT_VERIFY_REQUEST");
            this.mUaListenerList.get(0).onX509CertVerifyRequested((X509CertVerifyRequest) notify.noti(new X509CertVerifyRequest()));
            return;
        }
        if (notifyid == 10012) {
            this.mCdpnInfoRegistrants.notifyResult(((CdpnInfo) notify.noti(new CdpnInfo())).calledPartyNumber());
            return;
        }
        if (notifyid == 20007) {
            DnsResponse dnsResponse = (DnsResponse) notify.noti(new DnsResponse());
            int handle9 = ImsUtil.getHandle(dnsResponse.handle());
            for (int i7 = 0; i7 < 2; i7++) {
                StackEventListener stackEventListener9 = this.mUaListenerList.get(Integer.valueOf(i7));
                ArrayList arrayList3 = new ArrayList();
                for (int i8 = 0; i8 < dnsResponse.ipAddrListLength(); i8++) {
                    arrayList3.add(dnsResponse.ipAddrList(i8));
                }
                if (stackEventListener9 != null) {
                    stackEventListener9.onDnsResponse(dnsResponse.hostname(), arrayList3, (int) dnsResponse.port(), handle9);
                }
            }
            return;
        }
        if (notifyid == 16001 || notifyid == 16002 || notifyid == 16003 || notifyid == 16004) {
            this.mIshRegistrants.notifyResult(notify);
            return;
        }
        if (notifyid == 17001 || notifyid == 17002 || notifyid == 17003) {
            this.mVshRegistrants.notifyResult(notify);
            return;
        }
        if (notifyid == 10016) {
            if (notify.notiType() == 18) {
                this.mDedicatedBearerEventRegistrants.notifyResult((DedicatedBearerEvent) notify.noti(new DedicatedBearerEvent()));
                return;
            }
            return;
        }
        if (notifyid == 10017) {
            if (notify.notiType() == 19) {
                this.mRrcConnectionEventRegistrants.notifyResult((RrcConnectionEvent) notify.noti(new RrcConnectionEvent()));
                return;
            }
            return;
        }
        if (notifyid == 10037) {
            if (notify.notiType() == 20) {
                this.mQuantumSecurityStatusEventRegistrants.notifyResult((QuantumSecurityStatusEvent) notify.noti(new QuantumSecurityStatusEvent()));
                return;
            }
            return;
        }
        if (notifyid == 10022) {
            if (notify.notiType() == 22) {
                this.mRtpLossRateNotiRegistrants.notifyResult((RtpLossRateNoti) notify.noti(new RtpLossRateNoti()));
                return;
            }
            return;
        }
        if (notifyid == 20009) {
            if (notify.notiType() == 80) {
                DumpMessage dumpMessage = (DumpMessage) notify.noti(new DumpMessage());
                DumpRequest dumpRequest = new DumpRequest(dumpMessage.tag(), dumpMessage.value(), new SimpleDateFormat("MM/dd/yyyy HH:mm:ss.SSS", Locale.US).format(new Date()));
                if (this.mSipHistory.size() >= 100) {
                    this.mSipHistory.remove(0);
                }
                this.mStackDumpData.add(dumpRequest);
                return;
            }
            return;
        }
        if (notifyid == 10021) {
            if (notify.notiType() == 21) {
                DTMFDataEvent dTMFDataEvent = (DTMFDataEvent) notify.noti(new DTMFDataEvent());
                Log.i(LOG_TAG, "DTMF Event: " + dTMFDataEvent.event() + " Volume: " + dTMFDataEvent.volume() + " Duration: " + dTMFDataEvent.duration() + " Endbit: " + dTMFDataEvent.endbit());
                this.mDtmfRegistrants.notifyResult(dTMFDataEvent);
                return;
            }
            return;
        }
        if (notifyid == 10030 || notifyid == 10031 || notifyid == 10032 || notifyid == 10033 || notifyid == 10034) {
            this.mEucrRegistrants.notifyResult(notify);
            return;
        }
        if (notifyid == 20010) {
            if (notify.notiType() == 81) {
                this.mXqMtripRegistrants.notifyResult((XqMessage) notify.noti(new XqMessage()));
                return;
            }
            return;
        }
        if (notifyid == 10024) {
            if (notify.notiType() == 23) {
                TextDataEvent textDataEvent = (TextDataEvent) notify.noti(new TextDataEvent());
                Log.i(LOG_TAG, " Text: " + textDataEvent.text() + " len: " + textDataEvent.len());
                this.mTextRegistrants.notifyResult(textDataEvent);
                return;
            }
            return;
        }
        if (notifyid == 10028) {
            IMSLog.i(LOG_TAG, "receive contact activated");
            int handle10 = ImsUtil.getHandle(((ContactActivated) notify.noti(new ContactActivated())).handle());
            IMSLog.i(LOG_TAG, "Handle: " + handle10);
            StackEventListener stackEventListener10 = this.mUaListenerList.get(Integer.valueOf(handle10));
            if (stackEventListener10 != null) {
                stackEventListener10.onContactActivated(handle10);
                return;
            }
            return;
        }
        if (notifyid != 10029) {
            if (notifyid == 10036) {
                IMSLog.i(LOG_TAG, "receive cmc info");
                CallSendCmcInfo callSendCmcInfo = (CallSendCmcInfo) notify.noti(new CallSendCmcInfo());
                Log.i(LOG_TAG, "CmcInfo - handle " + ImsUtil.getHandle(callSendCmcInfo.handle()) + " sessionId: " + callSendCmcInfo.sessionId());
                this.mCmcInfoRegistrants.notifyResult(callSendCmcInfo);
                return;
            }
            if (notifyid == 10040) {
                IMSLog.i(LOG_TAG, "receive current location discovery during emergency call");
                if (notify.notiType() == 84) {
                    this.mCurrentLocationDiscoveryDuringEmergencyCallRegistrants.notifyResult((CurrentLocationDiscoveryDuringEmergencyCall) notify.noti(new CurrentLocationDiscoveryDuringEmergencyCall()));
                    return;
                }
                return;
            }
            if (notifyid == 20015) {
                IMSLog.i(LOG_TAG, "receive SIGN DIGEST RESPONSE!");
                if (notify.notiType() == 85) {
                    SignDigestResponse signDigestResponse = (SignDigestResponse) notify.noti(new SignDigestResponse());
                    sendSignDigestResponse(ImsUtil.getHandle(signDigestResponse.handle()), new KeyPairManager().signWithPrivateKey(signDigestResponse.response()));
                    return;
                }
                return;
            }
            return;
        }
        IMSLog.i(LOG_TAG, "receive contact uri in reg-event");
        ContactUriInfo contactUriInfo = (ContactUriInfo) notify.noti(new ContactUriInfo());
        int handle11 = ImsUtil.getHandle(contactUriInfo.handle());
        ArrayList arrayList4 = new ArrayList();
        for (int i9 = 0; i9 < contactUriInfo.uriListLength(); i9++) {
            arrayList4.add(contactUriInfo.uriList(i9));
        }
        String emergencyNumbers = contactUriInfo.emergencyNumbers();
        IMSLog.i(LOG_TAG, "Handle: " + handle11 + " uri size:" + arrayList4.size() + " uri_list:" + arrayList4 + ", emergencyNumbers: " + IMSLog.checker(emergencyNumbers));
        int handle12 = ImsUtil.getHandle(contactUriInfo.isRegi());
        String uriType = contactUriInfo.uriType();
        StringBuilder sb = new StringBuilder();
        sb.append("isRegi: ");
        sb.append(handle12);
        sb.append(", contactUriType: ");
        sb.append(uriType);
        IMSLog.d(LOG_TAG, sb.toString());
        StackEventListener stackEventListener11 = this.mUaListenerList.get(Integer.valueOf(handle11));
        if (stackEventListener11 == null || arrayList4.size() <= 0) {
            return;
        }
        stackEventListener11.onRegEventContactUriNotification(handle11, arrayList4, handle12, uriType, emergencyNumbers);
    }

    private String serialString(int i) {
        StringBuilder sb = new StringBuilder(8);
        long j = i;
        long j2 = (j - (-2147483648L)) % 10000;
        String l = Long.toString(j % 10000);
        sb.append('[');
        int length = l.length();
        for (int i2 = 0; i2 < 4 - length; i2++) {
            sb.append('0');
        }
        sb.append(l);
        sb.append(']');
        return sb.toString();
    }

    public static boolean checkLogEnable() {
        return Extensions.Build.IS_DEBUGGABLE || DeviceUtil.isKeyStringActivated();
    }

    private static class SipDebugMessage {
        private boolean mIsEncrypted;
        private boolean mIsRx;
        private String mMethod;
        private int mPhoneId;
        private String mSipMessage;
        private String mTimestamp;

        private SipDebugMessage(String str, String str2, String str3, boolean z, int i) {
            this.mSipMessage = str;
            this.mMethod = str2;
            this.mTimestamp = str3;
            this.mIsRx = z;
            this.mPhoneId = i;
            this.mIsEncrypted = false;
        }

        private SipDebugMessage(String str, String str2, String str3, boolean z, int i, boolean z2) {
            this.mSipMessage = str;
            this.mMethod = str2;
            this.mTimestamp = str3;
            this.mIsRx = z;
            this.mPhoneId = i;
            this.mIsEncrypted = z2;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.mTimestamp);
            sb.append("   slot[");
            sb.append(this.mPhoneId);
            sb.append("] ");
            sb.append(this.mIsRx ? "[<--] " : "[-->] ");
            sb.append(this.mMethod);
            sb.append(this.mSipMessage);
            return sb.toString();
        }
    }

    private void recordSipHistory(SipDebugMessage sipDebugMessage) {
        if (this.mSipHistory.size() >= 100) {
            this.mSipHistory.remove(0);
        }
        this.mSipHistory.add(sipDebugMessage);
    }

    public void dump() {
        if (checkLogEnable()) {
            IMSLog.dump(LOG_TAG, "Dump of IMS Stack:", false);
            IMSLog.increaseIndent(LOG_TAG);
            Iterator<DumpRequest> it = this.mStackDumpData.iterator();
            while (it.hasNext()) {
                IMSLog.dump(LOG_TAG, it.next().toString(), false);
            }
            IMSLog.decreaseIndent(LOG_TAG);
        }
        IMSLog.dump(LOG_TAG, "Dump of IMS SIP messages history:");
        IMSLog.increaseIndent(LOG_TAG);
        Iterator<SipDebugMessage> it2 = this.mSipHistory.iterator();
        while (it2.hasNext()) {
            IMSLog.dump(LOG_TAG, it2.next().toString(), !r0.mIsEncrypted);
        }
        IMSLog.decreaseIndent(LOG_TAG);
        IMSLog.dump(LOG_TAG, "Dump of IMS log data:");
        IMSLog.increaseIndent(LOG_TAG);
        IMSLog.dumpSecretKey(LOG_TAG);
        IMSLog.decreaseIndent(LOG_TAG);
    }

    public void setSilentLogEnabled(boolean z) {
        Log.i(LOG_TAG, "setSilentLogEnabled: onoff " + z);
        FlatBufferBuilder flatBufferBuilder = new FlatBufferBuilder(0);
        RequestSilentLogEnabled.startRequestSilentLogEnabled(flatBufferBuilder);
        RequestSilentLogEnabled.addOnoff(flatBufferBuilder, z);
        int endRequestSilentLogEnabled = RequestSilentLogEnabled.endRequestSilentLogEnabled(flatBufferBuilder);
        Request.startRequest(flatBufferBuilder);
        Request.addReqid(flatBufferBuilder, 903);
        Request.addReqType(flatBufferBuilder, ReqMsg.request_silent_log_enabled);
        Request.addReq(flatBufferBuilder, endRequestSilentLogEnabled);
        sendRequest(flatBufferBuilder, Request.endRequest(flatBufferBuilder), null);
    }

    public void updateNtpTimeOffset(long j) {
        Log.i(LOG_TAG, "updateNtpTimeOffset : " + j);
        FlatBufferBuilder flatBufferBuilder = new FlatBufferBuilder(0);
        RequestNtpTimeOffset.startRequestNtpTimeOffset(flatBufferBuilder);
        RequestNtpTimeOffset.addOffset(flatBufferBuilder, j);
        int endRequestNtpTimeOffset = RequestNtpTimeOffset.endRequestNtpTimeOffset(flatBufferBuilder);
        Request.startRequest(flatBufferBuilder);
        Request.addReqid(flatBufferBuilder, Id.REQUEST_NTP_TIME_OFFSET);
        Request.addReqType(flatBufferBuilder, ReqMsg.request_ntp_time_offset);
        Request.addReq(flatBufferBuilder, endRequestNtpTimeOffset);
        sendRequest(flatBufferBuilder, Request.endRequest(flatBufferBuilder), null);
    }

    @Override // com.sec.internal.ims.core.handler.secims.IStackIF
    public void updateNrSaModeOnStart(int i) {
        FlatBufferBuilder flatBufferBuilder = new FlatBufferBuilder(0);
        RequestUpdateNrSaModeOnStart.startRequestUpdateNrSaModeOnStart(flatBufferBuilder);
        RequestUpdateNrSaModeOnStart.addSession(flatBufferBuilder, i);
        int endRequestUpdateNrSaModeOnStart = RequestUpdateNrSaModeOnStart.endRequestUpdateNrSaModeOnStart(flatBufferBuilder);
        Request.startRequest(flatBufferBuilder);
        Request.addReqid(flatBufferBuilder, 249);
        Request.addReqType(flatBufferBuilder, ReqMsg.request_update_nr_sa_mode_on_start);
        Request.addReq(flatBufferBuilder, endRequestUpdateNrSaModeOnStart);
        sendRequest(flatBufferBuilder, Request.endRequest(flatBufferBuilder), null);
        Log.i(LOG_TAG, "updateNrSaModeOnStart: sent");
    }

    @Override // com.sec.internal.ims.core.handler.secims.IStackIF
    public void updateAirplaneMode(boolean z) {
        FlatBufferBuilder flatBufferBuilder = new FlatBufferBuilder(0);
        RequestUpdateAirplaneMode.startRequestUpdateAirplaneMode(flatBufferBuilder);
        RequestUpdateAirplaneMode.addIsOn(flatBufferBuilder, z);
        int endRequestUpdateAirplaneMode = RequestUpdateAirplaneMode.endRequestUpdateAirplaneMode(flatBufferBuilder);
        Request.startRequest(flatBufferBuilder);
        Request.addReqid(flatBufferBuilder, 253);
        Request.addReqType(flatBufferBuilder, ReqMsg.request_update_airplane_mode);
        Request.addReq(flatBufferBuilder, endRequestUpdateAirplaneMode);
        sendRequest(flatBufferBuilder, Request.endRequest(flatBufferBuilder), null);
        Log.i(LOG_TAG, "updateAirplaneMode: sent");
    }

    @Override // com.sec.internal.ims.core.handler.secims.IStackIF
    public void updateCmcHotspotState(int i, List<String> list) {
        FlatBufferBuilder flatBufferBuilder = new FlatBufferBuilder(0);
        int createHotspotAddressesVector = list == null ? -1 : RequestUpdateCmcHotspotState.createHotspotAddressesVector(flatBufferBuilder, StackRequestBuilderUtil.getStringOffsetArray(flatBufferBuilder, list, list.size()));
        RequestUpdateCmcHotspotState.startRequestUpdateCmcHotspotState(flatBufferBuilder);
        RequestUpdateCmcHotspotState.addSession(flatBufferBuilder, i);
        if (createHotspotAddressesVector != -1) {
            RequestUpdateCmcHotspotState.addHotspotAddresses(flatBufferBuilder, createHotspotAddressesVector);
        }
        int endRequestUpdateCmcHotspotState = RequestUpdateCmcHotspotState.endRequestUpdateCmcHotspotState(flatBufferBuilder);
        Request.startRequest(flatBufferBuilder);
        Request.addReqid(flatBufferBuilder, 117);
        Request.addReqType(flatBufferBuilder, ReqMsg.request_update_cmc_hotspot_state);
        Request.addReq(flatBufferBuilder, endRequestUpdateCmcHotspotState);
        sendRequest(flatBufferBuilder, Request.endRequest(flatBufferBuilder), null);
        Log.i(LOG_TAG, "updateCmcHotspotState: sessionId: " + i);
    }
}
