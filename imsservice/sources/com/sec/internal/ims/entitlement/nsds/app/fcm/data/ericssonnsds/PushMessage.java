package com.sec.internal.ims.entitlement.nsds.app.fcm.data.ericssonnsds;

import android.content.Context;
import android.content.Intent;
import android.os.SemSystemProperties;
import com.google.gson.annotations.SerializedName;
import com.sec.ims.extensions.ContextExt;
import com.sec.internal.constants.ims.cmstore.CloudMessageProviderContract;
import com.sec.internal.constants.ims.entitilement.NSDSNamespaces;
import com.sec.internal.helper.os.IntentUtil;
import com.sec.internal.ims.entitlement.nsds.NSDSMultiSimService;
import com.sec.internal.log.IMSLog;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class PushMessage extends FcmMessage {
    private static final boolean ENG_MODE;
    private static final String LOG_TAG = "PushMessage";
    private static final String PNS_SUBTYPE_CONFIG_CHANGE = "config-change";
    private static final String PNS_TYPE_CONN_MGR = "conn_mgr";
    private static final String PNS_TYPE_IAM = "IAM";
    private static final String PNS_TYPE_NOTIFY = "Notify";
    private static final String PNS_TYPE_SES = "ENA";

    @SerializedName("cc")
    public CarbonCopyRecipient[] cc;
    private String confirmationUrl;

    @SerializedName("message")
    public Message mMessage;

    @SerializedName("pns-subtype")
    public String pnsSubtype;

    @SerializedName("pns-time")
    public String pnsTime;

    @SerializedName("pns-type")
    public String pnsType;

    @SerializedName("recipients")
    public Recipient[] recipients;

    @SerializedName(CloudMessageProviderContract.VVMMessageColumns.SENDER)
    public Sender sender;

    @SerializedName("serviceName")
    public String serviceName;

    public static class Message {

        @SerializedName("device-id")
        public String deviceId;

        @SerializedName("device-name")
        public String deviceName;

        @SerializedName("event-type")
        public String eventType;

        @SerializedName("imsi")
        public String imsi;

        @SerializedName("msisdn")
        public String msisdn;

        @SerializedName("transaction-id")
        public String transactionId;
    }

    static {
        ENG_MODE = SemSystemProperties.getInt("ro.debuggable", 0) == 1;
    }

    @Override // com.sec.internal.ims.entitlement.nsds.app.fcm.data.ericssonnsds.FcmMessage
    public boolean shouldBroadcast(Context context) {
        if (PNS_TYPE_NOTIFY.equalsIgnoreCase(this.pnsType) && PNS_SUBTYPE_CONFIG_CHANGE.equalsIgnoreCase(this.pnsSubtype)) {
            handleConfigChange(context);
        }
        confirmPushMsgDelivery(context);
        if (PNS_TYPE_CONN_MGR.equalsIgnoreCase(this.pnsType) || PNS_TYPE_SES.equalsIgnoreCase(this.pnsType) || PNS_TYPE_IAM.equalsIgnoreCase(this.pnsType)) {
            return ENG_MODE;
        }
        return true;
    }

    public void setConfirmUrl(String str) {
        this.confirmationUrl = str;
    }

    private void handleConfigChange(Context context) {
        IMSLog.i(LOG_TAG, "refresh Device config:");
        Intent intent = new Intent(context, (Class<?>) NSDSMultiSimService.class);
        intent.setAction(NSDSNamespaces.NSDSActions.ACTION_REFRESH_DEVICE_CONFIG);
        context.startService(intent);
    }

    private void confirmPushMsgDelivery(Context context) {
        IMSLog.i(LOG_TAG, "confirmPushMsgDelivery: url " + this.confirmationUrl);
        if (this.confirmationUrl != null) {
            Intent intent = new Intent(context, (Class<?>) NSDSMultiSimService.class);
            intent.setAction(NSDSNamespaces.NSDSActions.ACTION_CONFIRM_PUSH_MSG_DELIVERY);
            intent.putExtra("imsi", this.mMessage.imsi);
            intent.putExtra("confirmation_url", this.confirmationUrl);
            context.startService(intent);
        }
    }

    @Override // com.sec.internal.ims.entitlement.nsds.app.fcm.data.ericssonnsds.FcmMessage
    public void broadcastFcmMessage(Context context) {
        ArrayList<String> arrayList = new ArrayList<>();
        if (this.recipients != null) {
            int i = 0;
            while (true) {
                Recipient[] recipientArr = this.recipients;
                if (i >= recipientArr.length) {
                    break;
                }
                String deriveMsisdnFromRecipientUri = deriveMsisdnFromRecipientUri(recipientArr[i].uri);
                if (deriveMsisdnFromRecipientUri != null) {
                    arrayList.add(deriveMsisdnFromRecipientUri);
                }
                i++;
            }
        }
        Intent intent = new Intent(NSDSNamespaces.NSDSActions.RECEIVED_PUSH_NOTIFICATION);
        intent.putStringArrayListExtra(NSDSNamespaces.NSDSExtras.MSISDN_LIST, arrayList);
        intent.putExtra(NSDSNamespaces.NSDSExtras.ORIG_PUSH_MESSAGE, this.origMessage);
        intent.putExtra(NSDSNamespaces.NSDSExtras.PNS_TYPE, this.pnsType);
        intent.putExtra(NSDSNamespaces.NSDSExtras.PNS_SUBTYPE, this.pnsSubtype);
        intent.putExtra(NSDSNamespaces.NSDSExtras.NOTIFCATION_TITLE, getNotificationTitle());
        intent.putExtra(NSDSNamespaces.NSDSExtras.NOTIFCATION_CONTENT, getNotificationContent());
        IMSLog.i(LOG_TAG, "push notification broadcastIntent: " + intent.toString() + intent.getExtras());
        IntentUtil.sendBroadcast(context, intent, ContextExt.CURRENT_OR_SELF);
    }

    private String getNotificationTitle() {
        return "Time: " + this.pnsTime;
    }

    private String getNotificationContent() {
        return "type:" + this.pnsType + " subtype:" + this.pnsSubtype;
    }

    @Override // com.sec.internal.ims.entitlement.nsds.app.fcm.data.ericssonnsds.FcmMessage
    public void setOrigMessage(String str) {
        this.origMessage = str;
    }
}
