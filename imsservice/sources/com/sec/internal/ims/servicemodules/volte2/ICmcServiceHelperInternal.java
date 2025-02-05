package com.sec.internal.ims.servicemodules.volte2;

import com.android.internal.telephony.PublishDialog;
import com.sec.ims.ImsRegistration;
import com.sec.ims.cmc.CmcCallInfo;
import com.sec.internal.constants.ims.servicemodules.volte2.CallConstants;
import com.sec.internal.ims.servicemodules.volte2.data.DtmfInfo;
import java.util.List;

/* loaded from: classes.dex */
public interface ICmcServiceHelperInternal {
    long getCmcCallEstablishTime(String str);

    CmcCallInfo getCmcCallInfo();

    int getCsCallPhoneIdByState(int i);

    ImsCallSession getSessionByCmcType(int i);

    ImsCallSession getSessionByCmcTypeAndState(int i, CallConstants.STATE state);

    int getSessionCountByCmcType(int i, int i2);

    boolean hasActiveCmcCallsession(int i);

    boolean isCmcRegExist(int i);

    void onCmcDtmfInfo(DtmfInfo dtmfInfo);

    void onRegEventContactUriNotification(int i, List<String> list);

    void sendDummyPublishDialog(int i, int i2);

    void sendPublishDialog(int i, PublishDialog publishDialog, int i2);

    void sendPublishDialogInternal(int i, int i2);

    void setCallEstablishTimeExtra(long j);

    void setP2pServiceInfo(String str, String str2);

    void startP2p(String str, String str2);

    void startP2pDiscovery(List<String> list);

    void stopCmcHandoverTimer(ImsRegistration imsRegistration);
}
