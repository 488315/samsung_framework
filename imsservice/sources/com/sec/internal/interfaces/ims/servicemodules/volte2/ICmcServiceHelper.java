package com.sec.internal.interfaces.ims.servicemodules.volte2;

import com.android.internal.telephony.PublishDialog;
import com.sec.ims.cmc.CmcCallInfo;
import java.util.List;

/* loaded from: classes.dex */
public interface ICmcServiceHelper {
    CmcCallInfo getCmcCallInfo();

    int getCsCallPhoneIdByState(int i);

    boolean isCmcRegExist(int i);

    void onRegEventContactUriNotification(int i, List<String> list);

    void sendPublishDialog(int i, PublishDialog publishDialog, int i2);

    void setP2pServiceInfo(String str, String str2);

    void startP2p(String str, String str2);

    void startP2pDiscovery(List<String> list);
}
