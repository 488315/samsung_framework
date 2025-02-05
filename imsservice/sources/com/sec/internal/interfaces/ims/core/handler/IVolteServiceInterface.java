package com.sec.internal.interfaces.ims.core.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.sec.ims.Dialog;
import com.sec.ims.util.SipError;
import com.sec.internal.constants.ims.SipReason;
import com.sec.internal.ims.core.handler.secims.UserAgent;
import com.sec.internal.ims.servicemodules.volte2.data.CallSetupData;
import com.sec.internal.ims.servicemodules.volte2.data.ConfCallSetupData;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes.dex */
public interface IVolteServiceInterface {
    int addParticipantToNWayConferenceCall(int i, int i2);

    int addParticipantToNWayConferenceCall(int i, String str);

    int addUserForConferenceCall(int i, ConfCallSetupData confCallSetupData, boolean z);

    int answerCallWithCallType(int i, int i2);

    int answerCallWithCallType(int i, int i2, String str);

    int answerCallWithCallType(int i, int i2, String str, String str2);

    int cancelTransferCall(int i);

    void clearAllCallInternal(int i);

    int deleteTcpSocket(int i, int i2);

    int enableQuantumSecurityService(int i, boolean z);

    int endCall(int i, int i2, SipReason sipReason);

    int handleCmcCsfb(int i);

    int handleDtmf(int i, int i2, int i3, int i4, Message message);

    int holdCall(int i);

    int makeCall(int i, CallSetupData callSetupData, HashMap<String, String> hashMap, int i2);

    int modifyCallType(int i, int i2, int i3);

    int proceedIncomingCall(int i, boolean z, HashMap<String, String> hashMap, String str);

    int publishDialog(int i, String str, String str2, String str3, int i2, boolean z);

    int pullingCall(int i, String str, String str2, String str3, Dialog dialog, List<String> list);

    void registerForAudioPathUpdated(Handler handler, int i, Object obj);

    void registerForCallStateEvent(Handler handler, int i, Object obj);

    void registerForCdpnInfoEvent(Handler handler, int i, Object obj);

    void registerForCmcInfoEvent(Handler handler, int i, Object obj);

    void registerForCurrentLocationDiscoveryDuringEmergencyCallEvent(Handler handler, int i, Object obj);

    void registerForDedicatedBearerNotifyEvent(Handler handler, int i, Object obj);

    void registerForDialogEvent(Handler handler, int i, Object obj);

    void registerForDialogSubscribeStatus(Handler handler, int i, Object obj);

    void registerForDtmfEvent(Handler handler, int i, Object obj);

    void registerForIncomingCallEvent(Handler handler, int i, Object obj);

    void registerForReferStatus(Handler handler, int i, Object obj);

    void registerForRrcConnectionEvent(Handler handler, int i, Object obj);

    void registerForRtpLossRateNoti(Handler handler, int i, Object obj);

    void registerForSIPMSGEvent(Handler handler, int i, Object obj);

    void registerForTextEvent(Handler handler, int i, Object obj);

    void registerForUssdEvent(Handler handler, int i, Object obj);

    void registerQuantumSecurityStatusEvent(Handler handler, int i, Object obj);

    int rejectCall(int i, int i2, SipError sipError);

    int rejectModifyCallType(int i, int i2);

    int removeParticipantFromNWayConferenceCall(int i, int i2);

    int removeParticipantFromNWayConferenceCall(int i, String str);

    void replaceSipCallId(int i, String str);

    void replaceUserAgent(int i, int i2);

    int replyModifyCallType(int i, int i2, int i3, int i4);

    int replyModifyCallType(int i, int i2, int i3, int i4, String str);

    int replyWithIdc(int i, int i2, int i3, int i4, String str);

    int resumeCall(int i);

    int sendCmcInfo(int i, Bundle bundle);

    void sendDtmfEvent(int i, String str);

    int sendEmergencyLocationPublish(int i);

    int sendInfo(int i, int i2, String str, int i3);

    void sendNegotiatedLocalSdp(int i, String str);

    int sendReInvite(int i, SipReason sipReason);

    int sendReInviteWithIdcExtra(int i, String str);

    int sendText(int i, String str, int i2);

    int sendTtyData(int i, byte[] bArr);

    int sendVcsInfo(int i, Bundle bundle);

    void setAutomaticMode(int i, boolean z);

    void setOutOfService(boolean z, int i);

    int setQuantumSecurityInfo(int i, Bundle bundle);

    void setRttMode(int i, int i2);

    int setTtyMode(int i, int i2, int i3);

    void setTtyMode(String str);

    void setVideoCrtAudio(int i, boolean z);

    int startNWayConferenceCall(int i, ConfCallSetupData confCallSetupData);

    int startVideoEarlyMedia(int i);

    int transferCall(int i, String str);

    void unregisterForAudioPathUpdated(Handler handler, int i, Object obj);

    void unregisterForCallStateEvent(Handler handler);

    void unregisterForCdpnInfoEvent(Handler handler);

    void unregisterForCmcInfoEvent(Handler handler);

    void unregisterForCurrentLocationDiscoveryDuringEmergencyCallEvent(Handler handler);

    void unregisterForDedicatedBearerNotifyEvent(Handler handler);

    void unregisterForDialogEvent(Handler handler);

    void unregisterForDialogSubscribeStatus(Handler handler);

    void unregisterForDtmfEvent(Handler handler);

    void unregisterForIncomingCallEvent(Handler handler);

    void unregisterForReferStatus(Handler handler);

    void unregisterForRrcConnectionEvent(Handler handler);

    void unregisterForRtpLossRateNoti(Handler handler);

    void unregisterForSIPMSGEvent(Handler handler);

    void unregisterForTextEvent(Handler handler);

    void unregisterForUssdEvent(Handler handler);

    void unregisterQuantumSecurityStatusEvent(Handler handler);

    void updateAirplaneMode(boolean z);

    void updateAudioInterface(int i, String str);

    void updateAudioInterface(int i, String str, UserAgent userAgent);

    void updateNrSaModeOnStart(int i);

    void updateScreenOnOff(int i, int i2);

    void updateXqEnable(int i, boolean z);
}
