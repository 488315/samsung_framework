package com.sec.internal.ims.entitlement.nsds.app.flow.ericssonnsds;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import com.sec.internal.constants.ims.entitilement.NSDSNamespaces;
import com.sec.internal.constants.ims.entitilement.data.ResponseManagePushToken;
import com.sec.internal.ims.entitlement.nsds.ericssonnsds.flow.BaseFlowImpl;
import com.sec.internal.ims.entitlement.nsds.ericssonnsds.flow.OperationUsingManagePushToken;
import com.sec.internal.ims.entitlement.nsds.strategy.IMnoNsdsStrategy;
import com.sec.internal.ims.entitlement.storagehelper.NSDSDatabaseHelper;
import com.sec.internal.ims.entitlement.storagehelper.NSDSHelper;
import com.sec.internal.ims.entitlement.storagehelper.NSDSSharedPrefHelper;
import com.sec.internal.log.IMSLog;
import java.util.Iterator;

/* loaded from: classes.dex */
public class PushTokenUpdateFlow extends NSDSAppFlowBase {
    private static final String LOG_TAG = PushTokenUpdateFlow.class.getSimpleName();
    private static final int UPDATE_PUSH_TOKEN = 1;

    @Override // com.sec.internal.ims.entitlement.nsds.app.flow.ericssonnsds.NSDSAppFlowBase
    protected void notifyNSDSFlowResponse(boolean z, String str, int i, int i2) {
    }

    @Override // com.sec.internal.ims.entitlement.nsds.app.flow.ericssonnsds.NSDSAppFlowBase
    protected void queueOperation(int i, Bundle bundle) {
    }

    public PushTokenUpdateFlow(Looper looper, Context context, BaseFlowImpl baseFlowImpl, NSDSDatabaseHelper nSDSDatabaseHelper) {
        super(looper, context, baseFlowImpl, nSDSDatabaseHelper);
    }

    public void updatePushToken() {
        sendEmptyMessage(1);
    }

    private void handleResponsePushToken(Bundle bundle) {
        if (bundle != null) {
            ResponseManagePushToken responseManagePushToken = (ResponseManagePushToken) bundle.getParcelable("managePushToken");
            if (responseManagePushToken == null || responseManagePushToken.responseCode != 1000) {
                return;
            }
            IMSLog.i(LOG_TAG, "push token is udpated in entitlment successfully");
            return;
        }
        IMSLog.e(LOG_TAG, "responseManagePushToken is NULL");
    }

    private void updatePushTokenForActiveLines() {
        String nativeMsisdn = this.mNSDSDatabaseHelper.getNativeMsisdn(this.mBaseFlowImpl.getDeviceId());
        IMnoNsdsStrategy mnoNsdsStrategy = getMnoNsdsStrategy();
        if (mnoNsdsStrategy != null) {
            Iterator<String> it = mnoNsdsStrategy.getServiceListForPushToken().iterator();
            while (it.hasNext()) {
                updatePushTokenForLine(nativeMsisdn, it.next());
            }
            for (String str : this.mNSDSDatabaseHelper.getActiveMsisdns(this.mBaseFlowImpl.getDeviceId()).keySet()) {
                if (!str.equals(nativeMsisdn)) {
                    updatePushTokenForLine(str, "vowifi");
                }
            }
        }
    }

    private void updatePushTokenForLine(String str, String str2) {
        String str3 = LOG_TAG;
        IMSLog.i(str3, "updatePushTokenForLine: serviceName " + str2);
        String mSISDNFromSIM = str == null ? NSDSHelper.getMSISDNFromSIM(this.mContext, this.mBaseFlowImpl.getSimManager().getSimSlotIndex()) : str;
        String str4 = NSDSSharedPrefHelper.get(this.mContext, this.mBaseFlowImpl.getDeviceId(), NSDSNamespaces.NSDSSharedPref.PREF_PUSH_TOKEN);
        if (str4 != null) {
            IMSLog.s(str3, "updating push token for msisn:" + mSISDNFromSIM);
            new OperationUsingManagePushToken(getLooper(), this.mContext, this.mBaseFlowImpl, new Messenger(this), "1.0", this.mUserAgent, this.mImeiForUA).updatePushToken(mSISDNFromSIM, str2, null, 0, str4);
        }
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        int i = message.what;
        if (i == 1) {
            updatePushTokenForActiveLines();
            return;
        }
        if (i == 112) {
            handleResponsePushToken(message.getData());
            return;
        }
        IMSLog.i(LOG_TAG, "Unknown flow request: " + message.what);
    }
}
