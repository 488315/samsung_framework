package com.sec.internal.ims.entitlement.nsds.app.flow.xaawfcentitlement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import com.sec.ims.extensions.ContextExt;
import com.sec.internal.constants.ims.entitilement.NSDSErrorTranslator;
import com.sec.internal.constants.ims.entitilement.NSDSNamespaces;
import com.sec.internal.constants.ims.entitilement.data.LineDetail;
import com.sec.internal.constants.ims.entitilement.data.ResponseGetMSISDN;
import com.sec.internal.constants.ims.entitilement.data.ResponseManageConnectivity;
import com.sec.internal.constants.ims.entitilement.data.ResponseManagePushToken;
import com.sec.internal.helper.os.IntentUtil;
import com.sec.internal.ims.entitlement.nsds.app.flow.ericssonnsds.NSDSAppFlowBase;
import com.sec.internal.ims.entitlement.nsds.ericssonnsds.flow.BaseFlowImpl;
import com.sec.internal.ims.entitlement.nsds.ericssonnsds.flow.SIMDeviceActivation;
import com.sec.internal.ims.entitlement.nsds.ericssonnsds.persist.PushTokenHelper;
import com.sec.internal.ims.entitlement.storagehelper.NSDSDatabaseHelper;
import com.sec.internal.ims.entitlement.storagehelper.NSDSSharedPrefHelper;
import com.sec.internal.interfaces.ims.entitlement.nsds.ISIMDeviceImplicitActivation;
import com.sec.internal.log.IMSLog;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class XaaSimDeviceImplicitActivation extends NSDSAppFlowBase implements ISIMDeviceImplicitActivation {
    private static final String LOG_TAG = XaaSimDeviceImplicitActivation.class.getSimpleName();
    private static final int START_SIM_ACTIVATION = 1;
    protected LineDetail mNativeLineDetail;

    public XaaSimDeviceImplicitActivation(Looper looper, Context context, BaseFlowImpl baseFlowImpl, NSDSDatabaseHelper nSDSDatabaseHelper) {
        super(looper, context, baseFlowImpl, nSDSDatabaseHelper);
        this.mNativeLineDetail = new LineDetail();
    }

    protected NSDSAppFlowBase.NSDSResponseStatus handleSimDeviceActivationResponse(Bundle bundle) {
        int i;
        int i2;
        int i3;
        int i4;
        int httpErrRespCode = getHttpErrRespCode(bundle);
        String str = LOG_TAG;
        IMSLog.i(str, "handleSimDeviceActivationResponse: errorResponseCode: " + httpErrRespCode);
        NSDSAppFlowBase.NSDSResponseStatus nSDSResponseStatus = new NSDSAppFlowBase.NSDSResponseStatus(httpErrRespCode, NSDSNamespaces.NSDSMethodNamespace.MANAGE_CONNECTIVITY, -1);
        if (bundle != null && httpErrRespCode <= 0) {
            ResponseManageConnectivity responseManageConnectivity = (ResponseManageConnectivity) bundle.getParcelable(NSDSNamespaces.NSDSMethodNamespace.MANAGE_CONNECTIVITY);
            ResponseManagePushToken responseManagePushToken = (ResponseManagePushToken) bundle.getParcelable("managePushToken");
            ResponseGetMSISDN responseGetMSISDN = (ResponseGetMSISDN) bundle.getParcelable(NSDSNamespaces.NSDSMethodNamespace.GET_MSISDN);
            handleResponsePushToken(responseManagePushToken);
            hannldeResponseGetMsisdn(responseGetMSISDN);
            if (responseManageConnectivity != null && (i4 = responseManageConnectivity.responseCode) == 1000 && responseManagePushToken != null && responseManagePushToken.responseCode == 1000 && responseGetMSISDN != null && responseGetMSISDN.responseCode == 1000) {
                nSDSResponseStatus.responseCode = i4;
            } else {
                if (responseGetMSISDN != null && (i3 = responseGetMSISDN.responseCode) != 1000) {
                    nSDSResponseStatus.methodName = NSDSNamespaces.NSDSMethodNamespace.GET_MSISDN;
                    nSDSResponseStatus.responseCode = i3;
                } else if (responseManageConnectivity != null && (i2 = responseManageConnectivity.responseCode) != 1000) {
                    nSDSResponseStatus.methodName = NSDSNamespaces.NSDSMethodNamespace.MANAGE_CONNECTIVITY;
                    nSDSResponseStatus.failedOperation = 0;
                    nSDSResponseStatus.responseCode = i2;
                } else if (responseManagePushToken != null && (i = responseManagePushToken.responseCode) != 1000) {
                    nSDSResponseStatus.methodName = "managePushToken";
                    nSDSResponseStatus.responseCode = i;
                }
                IMSLog.e(str, "SIMDevice activation failed:");
            }
        }
        return nSDSResponseStatus;
    }

    protected void hannldeResponseGetMsisdn(ResponseGetMSISDN responseGetMSISDN) {
        if (responseGetMSISDN != null) {
            IMSLog.i(LOG_TAG, "responseGetMsisdn : messageId:" + responseGetMSISDN.messageId + "responseCode:" + responseGetMSISDN.responseCode + "msisdn:" + responseGetMSISDN.msisdn + "service_fingerprint:" + responseGetMSISDN.serviceFingerprint);
            if (responseGetMSISDN.responseCode != 1000 || responseGetMSISDN.msisdn == null || responseGetMSISDN.serviceFingerprint == null) {
                return;
            }
            this.mNativeLineDetail.lineId = this.mNSDSDatabaseHelper.insertOrUpdateNativeLine(0L, this.mBaseFlowImpl.getDeviceId(), responseGetMSISDN);
            LineDetail lineDetail = this.mNativeLineDetail;
            lineDetail.msisdn = responseGetMSISDN.msisdn;
            lineDetail.serviceFingerPrint = responseGetMSISDN.serviceFingerprint;
            return;
        }
        IMSLog.e(LOG_TAG, "ResponseGetMSISDN is NULL");
    }

    protected void handleResponsePushToken(ResponseManagePushToken responseManagePushToken) {
        if (responseManagePushToken != null) {
            IMSLog.i(LOG_TAG, "responsePushToken : messageId:" + responseManagePushToken.messageId + "responseCode:" + responseManagePushToken.responseCode);
            return;
        }
        IMSLog.e(LOG_TAG, "ResponseManagePushToken is NULL");
    }

    @Override // com.sec.internal.interfaces.ims.entitlement.nsds.ISIMDeviceImplicitActivation
    public void performSimDeviceImplicitActivation(int i, int i2) {
        IMSLog.i(LOG_TAG, "performSimDeviceImplicitActivation: eventType-" + i);
        this.mDeviceEventType = i;
        this.mRetryCount = i2;
        performNextOperationIf(-1, new NSDSAppFlowBase.NSDSResponseStatus(-1, null, -1), null);
    }

    @Override // com.sec.internal.ims.entitlement.nsds.app.flow.ericssonnsds.NSDSAppFlowBase
    protected void queueOperation(int i, Bundle bundle) {
        int i2 = 1;
        if (i != 1) {
            IMSLog.i(LOG_TAG, "queueOperation: did not match any nsds base operations");
            i2 = -1;
        }
        if (i2 != -1) {
            Message obtainMessage = obtainMessage(i2);
            obtainMessage.setData(bundle);
            sendMessage(obtainMessage);
        }
    }

    private void startSimDeviceActivation() {
        String str = LOG_TAG;
        IMSLog.i(str, "startSimDeviceActivation:");
        if (NSDSSharedPrefHelper.isDeviceInActivationProgress(this.mContext, this.mBaseFlowImpl.getDeviceId())) {
            IMSLog.i(str, "startSimDeviceActivation: activation in progress. do not do any thing");
        } else {
            NSDSSharedPrefHelper.save(this.mContext, this.mBaseFlowImpl.getDeviceId(), NSDSNamespaces.NSDSSharedPref.PREF_DEVICE_STATE, NSDSNamespaces.NSDSDeviceState.ACTIVATION_IN_PROGRESS);
            new SIMDeviceActivation(getLooper(), this.mContext, this.mBaseFlowImpl, new Messenger(this), "1.0").activateSIMDevice(null, PushTokenHelper.getPushToken(this.mContext, this.mBaseFlowImpl.getDeviceId()), 0L);
        }
    }

    protected void updateDeviceState(boolean z) {
        IMSLog.i(LOG_TAG, "updateDeviceState: flow " + z);
        if (z) {
            NSDSSharedPrefHelper.save(this.mContext, this.mBaseFlowImpl.getDeviceId(), NSDSNamespaces.NSDSSharedPref.PREF_DEVICE_STATE, NSDSNamespaces.NSDSDeviceState.ACTIVATED);
        } else {
            NSDSSharedPrefHelper.save(this.mContext, this.mBaseFlowImpl.getDeviceId(), NSDSNamespaces.NSDSSharedPref.PREF_DEVICE_STATE, NSDSNamespaces.NSDSDeviceState.DEACTIVATED);
            NSDSSharedPrefHelper.removePrefForSlot(this.mContext, this.mBaseFlowImpl.getSimManager().getSimSlotIndex(), NSDSNamespaces.NSDSSharedPref.PREF_IMSI_EAP);
        }
    }

    private int translateErrorCode(boolean z, String str, int i, int i2) {
        if (z || str == null || i2 == -1) {
            return -1;
        }
        return NSDSErrorTranslator.translate(str, i, i2);
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        IMSLog.i(LOG_TAG, "msg:" + message.what);
        int i = message.what;
        if (i == 1) {
            startSimDeviceActivation();
        } else {
            if (i != 103) {
                return;
            }
            performNextOperationIf(1, handleSimDeviceActivationResponse(message.getData()), null);
        }
    }

    @Override // com.sec.internal.ims.entitlement.nsds.app.flow.ericssonnsds.NSDSAppFlowBase
    protected void notifyNSDSFlowResponse(boolean z, String str, int i, int i2) {
        IMSLog.i(LOG_TAG, "notifyNSDSFlowResponse: success " + z);
        updateDeviceState(z);
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(Integer.valueOf(translateErrorCode(z, str, i, i2)));
        int simSlotIndex = this.mBaseFlowImpl.getSimManager().getSimSlotIndex();
        Intent intent = new Intent(NSDSNamespaces.NSDSActions.SIM_DEVICE_ACTIVATED);
        intent.putExtra(NSDSNamespaces.NSDSExtras.SIM_SLOT_IDX, simSlotIndex);
        intent.putExtra(NSDSNamespaces.NSDSExtras.REQUEST_STATUS, z);
        intent.putExtra("retry_count", this.mRetryCount);
        intent.putExtra(NSDSNamespaces.NSDSExtras.DEVICE_EVENT_TYPE, this.mDeviceEventType);
        intent.putExtra(NSDSNamespaces.NSDSExtras.ORIG_ERROR_CODE, i2);
        intent.putIntegerArrayListExtra(NSDSNamespaces.NSDSExtras.ERROR_CODES, arrayList);
        IntentUtil.sendBroadcast(this.mContext, intent, ContextExt.CURRENT_OR_SELF);
        notifyCallbackForNsdsEvent(0, simSlotIndex);
    }
}
