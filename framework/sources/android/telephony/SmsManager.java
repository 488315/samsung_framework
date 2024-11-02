package android.telephony;

import android.annotation.SystemApi;
import android.app.ActivityThread;
import android.app.PendingIntent;
import android.compat.Compatibility;
import android.content.Context;
import android.database.CursorWindow;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Pair;
import com.android.internal.telephony.EncodeException;
import com.android.internal.telephony.GsmAlphabet;
import com.android.internal.telephony.IIntegerConsumer;
import com.android.internal.telephony.IPhoneSubInfo;
import com.android.internal.telephony.ISms;
import com.android.internal.telephony.ITelephony;
import com.android.internal.telephony.SmsRawData;
import com.android.internal.telephony.TelephonyFeatures;
import com.android.internal.telephony.TelephonyProperties;
import com.android.internal.telephony.uicc.IccUtils;
import com.samsung.android.telephony.gsm.SemCbConfig;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public final class SmsManager {
    public static final int CDMA_SMS_RECORD_LENGTH = 255;
    public static final String EXTRA_MMS_DATA = "android.telephony.extra.MMS_DATA";
    public static final String EXTRA_MMS_HTTP_STATUS = "android.telephony.extra.MMS_HTTP_STATUS";
    public static final String EXTRA_SIM_SUBSCRIPTION_ID = "android.telephony.extra.SIM_SUBSCRIPTION_ID";
    public static final String EXTRA_SMS_MESSAGE = "android.telephony.extra.SMS_MESSAGE";
    public static final String EXTRA_STATUS = "android.telephony.extra.STATUS";
    private static final long GET_TARGET_SDK_VERSION_CODE_CHANGE = 145147528;
    public static final int ICC_TYPE_AUTO = -1;
    public static final int ICC_TYPE_CSIM = 4;
    public static final int ICC_TYPE_CSIM_DEACTIVE = 10;
    public static final int ICC_TYPE_ISIM = 5;
    public static final int ICC_TYPE_RUIM = 3;
    public static final int ICC_TYPE_SIM = 1;
    public static final int ICC_TYPE_UNKNOW = 0;
    public static final int ICC_TYPE_USIM = 2;
    private static final String ISIS_PACKAGE_NAME = "com.isis.mclient.verizon.activity";
    public static final String MMS_CONFIG_ALIAS_ENABLED = "aliasEnabled";
    public static final String MMS_CONFIG_ALIAS_MAX_CHARS = "aliasMaxChars";
    public static final String MMS_CONFIG_ALIAS_MIN_CHARS = "aliasMinChars";
    public static final String MMS_CONFIG_ALLOW_ATTACH_AUDIO = "allowAttachAudio";
    public static final String MMS_CONFIG_APPEND_TRANSACTION_ID = "enabledTransID";
    public static final String MMS_CONFIG_CLOSE_CONNECTION = "mmsCloseConnection";
    public static final String MMS_CONFIG_EMAIL_GATEWAY_NUMBER = "emailGatewayNumber";
    public static final String MMS_CONFIG_GROUP_MMS_ENABLED = "enableGroupMms";
    public static final String MMS_CONFIG_HTTP_PARAMS = "httpParams";
    public static final String MMS_CONFIG_HTTP_SOCKET_TIMEOUT = "httpSocketTimeout";
    public static final String MMS_CONFIG_MAX_IMAGE_HEIGHT = "maxImageHeight";
    public static final String MMS_CONFIG_MAX_IMAGE_WIDTH = "maxImageWidth";
    public static final String MMS_CONFIG_MAX_MESSAGE_SIZE = "maxMessageSize";
    public static final String MMS_CONFIG_MESSAGE_TEXT_MAX_SIZE = "maxMessageTextSize";
    public static final String MMS_CONFIG_MMS_DELIVERY_REPORT_ENABLED = "enableMMSDeliveryReports";
    public static final String MMS_CONFIG_MMS_ENABLED = "enabledMMS";
    public static final String MMS_CONFIG_MMS_READ_REPORT_ENABLED = "enableMMSReadReports";
    public static final String MMS_CONFIG_MULTIPART_SMS_ENABLED = "enableMultipartSMS";
    public static final String MMS_CONFIG_NAI_SUFFIX = "naiSuffix";
    public static final String MMS_CONFIG_NOTIFY_WAP_MMSC_ENABLED = "enabledNotifyWapMMSC";
    public static final String MMS_CONFIG_RECIPIENT_LIMIT = "recipientLimit";
    public static final String MMS_CONFIG_SEND_MULTIPART_SMS_AS_SEPARATE_MESSAGES = "sendMultipartSmsAsSeparateMessages";
    public static final String MMS_CONFIG_SHOW_CELL_BROADCAST_APP_LINKS = "config_cellBroadcastAppLinks";
    public static final String MMS_CONFIG_SMS_DELIVERY_REPORT_ENABLED = "enableSMSDeliveryReports";
    public static final String MMS_CONFIG_SMS_TO_MMS_TEXT_LENGTH_THRESHOLD = "smsToMmsTextLengthThreshold";
    public static final String MMS_CONFIG_SMS_TO_MMS_TEXT_THRESHOLD = "smsToMmsTextThreshold";
    public static final String MMS_CONFIG_SUBJECT_MAX_LENGTH = "maxSubjectLength";
    public static final String MMS_CONFIG_SUPPORT_HTTP_CHARSET_HEADER = "supportHttpCharsetHeader";
    public static final String MMS_CONFIG_SUPPORT_MMS_CONTENT_DISPOSITION = "supportMmsContentDisposition";
    public static final String MMS_CONFIG_UA_PROF_TAG_NAME = "uaProfTagName";
    public static final String MMS_CONFIG_UA_PROF_URL = "uaProfUrl";
    public static final String MMS_CONFIG_USER_AGENT = "userAgent";
    public static final int MMS_ERROR_CONFIGURATION_ERROR = 7;
    public static final int MMS_ERROR_DATA_DISABLED = 11;
    public static final int MMS_ERROR_HTTP_FAILURE = 4;
    public static final int MMS_ERROR_INACTIVE_SUBSCRIPTION = 10;
    public static final int MMS_ERROR_INVALID_APN = 2;
    public static final int MMS_ERROR_INVALID_SUBSCRIPTION_ID = 9;
    public static final int MMS_ERROR_IO_ERROR = 5;
    public static final int MMS_ERROR_NO_DATA_NETWORK = 8;
    public static final int MMS_ERROR_RETRY = 6;
    public static final int MMS_ERROR_TOO_LARGE_FOR_TRANSPORT = 13;
    public static final int MMS_ERROR_UNABLE_CONNECT_MMS = 3;
    public static final int MMS_ERROR_UNSPECIFIED = 1;

    @SystemApi
    public static final int PREMIUM_SMS_CONSENT_ALWAYS_ALLOW = 3;

    @SystemApi
    public static final int PREMIUM_SMS_CONSENT_ASK_USER = 1;

    @SystemApi
    public static final int PREMIUM_SMS_CONSENT_NEVER_ALLOW = 2;

    @SystemApi
    public static final int PREMIUM_SMS_CONSENT_UNKNOWN = 0;
    public static final String REGEX_PREFIX_DELIMITER = ",";
    public static final int RESULT_BLUETOOTH_DISCONNECTED = 27;
    public static final int RESULT_CANCELLED = 23;
    public static final int RESULT_ENCODING_ERROR = 18;
    public static final int RESULT_ERROR_FDN_CHECK_FAILURE = 6;
    public static final int RESULT_ERROR_GENERIC_FAILURE = 1;
    public static final int RESULT_ERROR_LIMIT_EXCEEDED = 5;
    public static final int RESULT_ERROR_NONE = 0;
    public static final int RESULT_ERROR_NO_SERVICE = 4;
    public static final int RESULT_ERROR_NULL_PDU = 3;
    public static final int RESULT_ERROR_RADIO_OFF = 2;
    public static final int RESULT_ERROR_SHORT_CODE_NEVER_ALLOWED = 8;
    public static final int RESULT_ERROR_SHORT_CODE_NOT_ALLOWED = 7;
    public static final int RESULT_INTERNAL_ERROR = 21;
    public static final int RESULT_INVALID_ARGUMENTS = 11;
    public static final int RESULT_INVALID_BLUETOOTH_ADDRESS = 26;
    public static final int RESULT_INVALID_SMSC_ADDRESS = 19;
    public static final int RESULT_INVALID_SMS_FORMAT = 14;
    public static final int RESULT_INVALID_STATE = 12;
    public static final int RESULT_MODEM_ERROR = 16;
    public static final int RESULT_NETWORK_ERROR = 17;
    public static final int RESULT_NETWORK_REJECT = 10;
    public static final int RESULT_NO_BLUETOOTH_SERVICE = 25;
    public static final int RESULT_NO_DEFAULT_SMS_APP = 32;
    public static final int RESULT_NO_MEMORY = 13;
    public static final int RESULT_NO_RESOURCES = 22;
    public static final int RESULT_OPERATION_NOT_ALLOWED = 20;
    public static final int RESULT_RADIO_NOT_AVAILABLE = 9;
    public static final int RESULT_RECEIVE_DISPATCH_FAILURE = 500;
    public static final int RESULT_RECEIVE_INJECTED_NULL_PDU = 501;
    public static final int RESULT_RECEIVE_NULL_MESSAGE_FROM_RIL = 503;
    public static final int RESULT_RECEIVE_RUNTIME_EXCEPTION = 502;
    public static final int RESULT_RECEIVE_SQL_EXCEPTION = 505;
    public static final int RESULT_RECEIVE_URI_EXCEPTION = 506;
    public static final int RESULT_RECEIVE_WHILE_ENCRYPTED = 504;
    public static final int RESULT_REMOTE_EXCEPTION = 31;
    public static final int RESULT_REQUEST_NOT_SUPPORTED = 24;
    public static final int RESULT_RIL_ABORTED = 137;
    public static final int RESULT_RIL_ACCESS_BARRED = 122;
    public static final int RESULT_RIL_BLOCKED_DUE_TO_CALL = 123;
    public static final int RESULT_RIL_CANCELLED = 119;
    public static final int RESULT_RIL_DEVICE_IN_USE = 136;
    public static final int RESULT_RIL_ENCODING_ERR = 109;
    public static final int RESULT_RIL_GENERIC_ERROR = 124;
    public static final int RESULT_RIL_INTERNAL_ERR = 113;
    public static final int RESULT_RIL_INVALID_ARGUMENTS = 104;
    public static final int RESULT_RIL_INVALID_MODEM_STATE = 115;
    public static final int RESULT_RIL_INVALID_RESPONSE = 125;
    public static final int RESULT_RIL_INVALID_SIM_STATE = 130;
    public static final int RESULT_RIL_INVALID_SMSC_ADDRESS = 110;
    public static final int RESULT_RIL_INVALID_SMS_FORMAT = 107;
    public static final int RESULT_RIL_INVALID_STATE = 103;
    public static final int RESULT_RIL_MODEM_ERR = 111;
    public static final int RESULT_RIL_NETWORK_ERR = 112;
    public static final int RESULT_RIL_NETWORK_NOT_READY = 116;
    public static final int RESULT_RIL_NETWORK_REJECT = 102;
    public static final int RESULT_RIL_NO_MEMORY = 105;
    public static final int RESULT_RIL_NO_NETWORK_FOUND = 135;
    public static final int RESULT_RIL_NO_RESOURCES = 118;
    public static final int RESULT_RIL_NO_SMS_TO_ACK = 131;
    public static final int RESULT_RIL_NO_SUBSCRIPTION = 134;
    public static final int RESULT_RIL_OPERATION_NOT_ALLOWED = 117;
    public static final int RESULT_RIL_RADIO_NOT_AVAILABLE = 100;
    public static final int RESULT_RIL_REQUEST_NOT_SUPPORTED = 114;
    public static final int RESULT_RIL_REQUEST_RATE_LIMITED = 106;
    public static final int RESULT_RIL_SIMULTANEOUS_SMS_AND_CALL_NOT_ALLOWED = 121;
    public static final int RESULT_RIL_SIM_ABSENT = 120;
    public static final int RESULT_RIL_SIM_BUSY = 132;
    public static final int RESULT_RIL_SIM_ERROR = 129;
    public static final int RESULT_RIL_SIM_FULL = 133;
    public static final int RESULT_RIL_SIM_PIN2 = 126;
    public static final int RESULT_RIL_SIM_PUK2 = 127;
    public static final int RESULT_RIL_SMS_SEND_FAIL_RETRY = 101;
    public static final int RESULT_RIL_SUBSCRIPTION_NOT_AVAILABLE = 128;
    public static final int RESULT_RIL_SYSTEM_ERR = 108;
    public static final int RESULT_SMS_BLOCKED_DURING_EMERGENCY = 29;
    public static final int RESULT_SMS_SEND_RETRY_FAILED = 30;
    public static final int RESULT_STATUS_SUCCESS = 0;
    public static final int RESULT_STATUS_TIMEOUT = 1;
    public static final int RESULT_SYSTEM_ERROR = 15;
    public static final int RESULT_UNEXPECTED_EVENT_STOP_SENDING = 28;
    public static final int RESULT_USER_NOT_ALLOWED = 33;
    public static final int SMS_CATEGORY_FREE_SHORT_CODE = 1;
    public static final int SMS_CATEGORY_NOT_SHORT_CODE = 0;
    public static final int SMS_CATEGORY_POSSIBLE_PREMIUM_SHORT_CODE = 3;
    public static final int SMS_CATEGORY_PREMIUM_SHORT_CODE = 4;
    public static final int SMS_CATEGORY_STANDARD_SHORT_CODE = 2;
    public static final int SMS_MESSAGE_PERIOD_NOT_SPECIFIED = -1;
    public static final int SMS_MESSAGE_PRIORITY_NOT_SPECIFIED = -1;
    public static final int SMS_RECORD_LENGTH = 176;
    public static final int SMS_RP_CAUSE_CALL_BARRING = 10;
    public static final int SMS_RP_CAUSE_CONGESTION = 42;
    public static final int SMS_RP_CAUSE_DESTINATION_OUT_OF_ORDER = 27;
    public static final int SMS_RP_CAUSE_FACILITY_NOT_IMPLEMENTED = 69;
    public static final int SMS_RP_CAUSE_FACILITY_NOT_SUBSCRIBED = 50;
    public static final int SMS_RP_CAUSE_FACILITY_REJECTED = 29;
    public static final int SMS_RP_CAUSE_INFORMATION_ELEMENT_NON_EXISTENT = 99;
    public static final int SMS_RP_CAUSE_INTERWORKING_UNSPECIFIED = 127;
    public static final int SMS_RP_CAUSE_INVALID_MANDATORY_INFORMATION = 96;
    public static final int SMS_RP_CAUSE_INVALID_MESSAGE_REFERENCE_VALUE = 81;
    public static final int SMS_RP_CAUSE_MESSAGE_INCOMPATIBLE_WITH_PROTOCOL_STATE = 98;
    public static final int SMS_RP_CAUSE_MESSAGE_TYPE_NON_EXISTENT = 97;
    public static final int SMS_RP_CAUSE_NETWORK_OUT_OF_ORDER = 38;
    public static final int SMS_RP_CAUSE_OPERATOR_DETERMINED_BARRING = 8;
    public static final int SMS_RP_CAUSE_PROTOCOL_ERROR = 111;
    public static final int SMS_RP_CAUSE_RESERVED = 11;
    public static final int SMS_RP_CAUSE_RESOURCES_UNAVAILABLE = 47;
    public static final int SMS_RP_CAUSE_SEMANTICALLY_INCORRECT_MESSAGE = 95;
    public static final int SMS_RP_CAUSE_SHORT_MESSAGE_TRANSFER_REJECTED = 21;
    public static final int SMS_RP_CAUSE_TEMPORARY_FAILURE = 41;
    public static final int SMS_RP_CAUSE_UNALLOCATED_NUMBER = 1;
    public static final int SMS_RP_CAUSE_UNIDENTIFIED_SUBSCRIBER = 28;
    public static final int SMS_RP_CAUSE_UNKNOWN_SUBSCRIBER = 30;
    public static final int STATUS_ON_ICC_FREE = 0;
    public static final int STATUS_ON_ICC_READ = 1;
    public static final int STATUS_ON_ICC_SENT = 5;
    public static final int STATUS_ON_ICC_UNREAD = 3;
    public static final int STATUS_ON_ICC_UNSENT = 7;
    private static final String TAG = "SmsManager";
    public static final int VALUE_INPUT_MODE_AUTO = 2;
    public static final int VALUE_INPUT_MODE_GSM7BIT = 0;
    public static final int VALUE_INPUT_MODE_UCS2 = 1;
    private final Context mContext;
    private int mSubId;
    private static final Object sLockObject = new Object();
    private static final Map<Pair<Context, Integer>, SmsManager> sSubInstances = new ArrayMap();
    private static final SmsManager DEFAULT_INSTANCE = getSmsManagerForContextAndSubscriptionId(null, Integer.MAX_VALUE);
    static int mMsgEncodingType = 0;

    /* loaded from: classes3.dex */
    public static abstract class FinancialSmsCallback {
        public abstract void onFinancialSmsMessages(CursorWindow cursorWindow);
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes3.dex */
    public @interface PremiumSmsConsent {
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes3.dex */
    public @interface Result {
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes3.dex */
    public @interface SMS_RP_CAUSE {
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes3.dex */
    public @interface SmsShortCodeCategory {
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes3.dex */
    public @interface StatusOnIcc {
    }

    /* loaded from: classes3.dex */
    public interface SubscriptionResolverResult {
        void onFailure();

        void onSuccess(int i);
    }

    /* renamed from: -$$Nest$smgetISmsServiceOrThrow */
    static /* bridge */ /* synthetic */ ISms m4427$$Nest$smgetISmsServiceOrThrow() {
        return getISmsServiceOrThrow();
    }

    private String getOpPackageName() {
        Context context = this.mContext;
        if (context == null) {
            return null;
        }
        return context.getOpPackageName();
    }

    private String getAttributionTag() {
        Context context = this.mContext;
        if (context == null) {
            return null;
        }
        return context.getAttributionTag();
    }

    public void sendTextMessage(String destinationAddress, String scAddress, String text, PendingIntent sentIntent, PendingIntent deliveryIntent) {
        sendTextMessageInternal(destinationAddress, scAddress, text, sentIntent, deliveryIntent, true, getOpPackageName(), getAttributionTag(), 0L);
    }

    public void sendTextMessage(String destinationAddress, String scAddress, String text, PendingIntent sentIntent, PendingIntent deliveryIntent, long messageId) {
        sendTextMessageInternal(destinationAddress, scAddress, text, sentIntent, deliveryIntent, true, getOpPackageName(), getAttributionTag(), messageId);
    }

    public void sendTextMessage(String destinationAddress, String scAddress, String text, PendingIntent sentIntent, PendingIntent deliveryIntent, int priority, boolean expectMore, int validityPeriod) {
        sendTextMessageInternal(destinationAddress, scAddress, text, sentIntent, deliveryIntent, true, priority, expectMore, validityPeriod);
    }

    private void sendTextMessageInternal(String destinationAddress, String scAddress, String text, PendingIntent sentIntent, PendingIntent deliveryIntent, boolean persistMessage, String packageName, String attributionTag, long messageId) {
        if (TextUtils.isEmpty(destinationAddress)) {
            throw new IllegalArgumentException("Invalid destinationAddress");
        }
        if (getMnoName().toUpperCase().contains("DOCOMO") && destinationAddress.length() >= 256) {
            throw new IllegalArgumentException("Invalid destinationAddress");
        }
        if (TextUtils.isEmpty(text) && !isSupportSendingEmptySms()) {
            throw new IllegalArgumentException("Invalid message body");
        }
        if (persistMessage) {
            resolveSubscriptionForOperation(new SubscriptionResolverResult() { // from class: android.telephony.SmsManager.1
                final /* synthetic */ String val$attributionTag;
                final /* synthetic */ PendingIntent val$deliveryIntent;
                final /* synthetic */ String val$destinationAddress;
                final /* synthetic */ long val$messageId;
                final /* synthetic */ String val$packageName;
                final /* synthetic */ boolean val$persistMessage;
                final /* synthetic */ String val$scAddress;
                final /* synthetic */ PendingIntent val$sentIntent;
                final /* synthetic */ String val$text;

                AnonymousClass1(String packageName2, String attributionTag2, String destinationAddress2, String scAddress2, String text2, PendingIntent sentIntent2, PendingIntent deliveryIntent2, boolean persistMessage2, long messageId2) {
                    packageName = packageName2;
                    attributionTag = attributionTag2;
                    destinationAddress = destinationAddress2;
                    scAddress = scAddress2;
                    text = text2;
                    sentIntent = sentIntent2;
                    deliveryIntent = deliveryIntent2;
                    persistMessage = persistMessage2;
                    messageId = messageId2;
                }

                @Override // android.telephony.SmsManager.SubscriptionResolverResult
                public void onSuccess(int subId) {
                    ISms iSms = SmsManager.m4427$$Nest$smgetISmsServiceOrThrow();
                    try {
                        iSms.sendTextForSubscriber(subId, packageName, attributionTag, destinationAddress, scAddress, text, sentIntent, deliveryIntent, persistMessage, messageId);
                    } catch (RemoteException e) {
                        Log.e(SmsManager.TAG, "sendTextMessageInternal: Couldn't send SMS, exception - " + e.getMessage() + " " + SmsManager.formatCrossStackMessageId(messageId));
                        SmsManager.notifySmsError(sentIntent, 31);
                    }
                }

                @Override // android.telephony.SmsManager.SubscriptionResolverResult
                public void onFailure() {
                    SmsManager.notifySmsError(sentIntent, 32);
                }
            });
            return;
        }
        ISms iSms = getISmsServiceOrThrow();
        try {
            iSms.sendTextForSubscriber(getSubscriptionId(), packageName2, attributionTag2, destinationAddress2, scAddress2, text2, sentIntent2, deliveryIntent2, persistMessage2, messageId2);
        } catch (RemoteException e) {
            Log.e(TAG, "sendTextMessageInternal (no persist): Couldn't send SMS, exception - " + e.getMessage() + " " + formatCrossStackMessageId(messageId2));
            notifySmsError(sentIntent2, 31);
        }
    }

    /* renamed from: android.telephony.SmsManager$1 */
    /* loaded from: classes3.dex */
    public class AnonymousClass1 implements SubscriptionResolverResult {
        final /* synthetic */ String val$attributionTag;
        final /* synthetic */ PendingIntent val$deliveryIntent;
        final /* synthetic */ String val$destinationAddress;
        final /* synthetic */ long val$messageId;
        final /* synthetic */ String val$packageName;
        final /* synthetic */ boolean val$persistMessage;
        final /* synthetic */ String val$scAddress;
        final /* synthetic */ PendingIntent val$sentIntent;
        final /* synthetic */ String val$text;

        AnonymousClass1(String packageName2, String attributionTag2, String destinationAddress2, String scAddress2, String text2, PendingIntent sentIntent2, PendingIntent deliveryIntent2, boolean persistMessage2, long messageId2) {
            packageName = packageName2;
            attributionTag = attributionTag2;
            destinationAddress = destinationAddress2;
            scAddress = scAddress2;
            text = text2;
            sentIntent = sentIntent2;
            deliveryIntent = deliveryIntent2;
            persistMessage = persistMessage2;
            messageId = messageId2;
        }

        @Override // android.telephony.SmsManager.SubscriptionResolverResult
        public void onSuccess(int subId) {
            ISms iSms = SmsManager.m4427$$Nest$smgetISmsServiceOrThrow();
            try {
                iSms.sendTextForSubscriber(subId, packageName, attributionTag, destinationAddress, scAddress, text, sentIntent, deliveryIntent, persistMessage, messageId);
            } catch (RemoteException e) {
                Log.e(SmsManager.TAG, "sendTextMessageInternal: Couldn't send SMS, exception - " + e.getMessage() + " " + SmsManager.formatCrossStackMessageId(messageId));
                SmsManager.notifySmsError(sentIntent, 31);
            }
        }

        @Override // android.telephony.SmsManager.SubscriptionResolverResult
        public void onFailure() {
            SmsManager.notifySmsError(sentIntent, 32);
        }
    }

    public void sendTextMessageWithoutPersisting(String destinationAddress, String scAddress, String text, PendingIntent sentIntent, PendingIntent deliveryIntent) {
        sendTextMessageInternal(destinationAddress, scAddress, text, sentIntent, deliveryIntent, false, getOpPackageName(), getAttributionTag(), 0L);
    }

    private void sendTextMessageInternal(String destinationAddress, String scAddress, String text, PendingIntent sentIntent, PendingIntent deliveryIntent, boolean persistMessage, int priority, boolean expectMore, int validityPeriod) {
        int priority2;
        int validityPeriod2 = validityPeriod;
        if (TextUtils.isEmpty(destinationAddress)) {
            throw new IllegalArgumentException("Invalid destinationAddress");
        }
        if (TextUtils.isEmpty(text)) {
            throw new IllegalArgumentException("Invalid message body");
        }
        if (priority >= 0 && priority <= 3) {
            priority2 = priority;
        } else {
            Log.e(TAG, "Invalid Priority " + priority);
            priority2 = -1;
        }
        if (validityPeriod2 < 5 || validityPeriod2 > 635040) {
            Log.e(TAG, "Invalid Validity Period " + validityPeriod2);
            validityPeriod2 = -1;
        }
        int finalPriority = priority2;
        int finalValidity = validityPeriod2;
        if (persistMessage) {
            resolveSubscriptionForOperation(new SubscriptionResolverResult() { // from class: android.telephony.SmsManager.2
                final /* synthetic */ PendingIntent val$deliveryIntent;
                final /* synthetic */ String val$destinationAddress;
                final /* synthetic */ boolean val$expectMore;
                final /* synthetic */ int val$finalPriority;
                final /* synthetic */ int val$finalValidity;
                final /* synthetic */ boolean val$persistMessage;
                final /* synthetic */ String val$scAddress;
                final /* synthetic */ PendingIntent val$sentIntent;
                final /* synthetic */ String val$text;

                AnonymousClass2(String destinationAddress2, String scAddress2, String text2, PendingIntent sentIntent2, PendingIntent deliveryIntent2, boolean persistMessage2, int finalPriority2, boolean expectMore2, int finalValidity2) {
                    destinationAddress = destinationAddress2;
                    scAddress = scAddress2;
                    text = text2;
                    sentIntent = sentIntent2;
                    deliveryIntent = deliveryIntent2;
                    persistMessage = persistMessage2;
                    finalPriority = finalPriority2;
                    expectMore = expectMore2;
                    finalValidity = finalValidity2;
                }

                @Override // android.telephony.SmsManager.SubscriptionResolverResult
                public void onSuccess(int subId) {
                    try {
                        ISms iSms = SmsManager.m4427$$Nest$smgetISmsServiceOrThrow();
                        if (iSms != null) {
                            iSms.sendTextForSubscriberWithOptions(subId, null, null, destinationAddress, scAddress, text, sentIntent, deliveryIntent, persistMessage, finalPriority, expectMore, finalValidity);
                        }
                    } catch (RemoteException e) {
                        Log.e(SmsManager.TAG, "sendTextMessageInternal: Couldn't send SMS, exception - " + e.getMessage());
                        SmsManager.notifySmsError(sentIntent, 31);
                    }
                }

                @Override // android.telephony.SmsManager.SubscriptionResolverResult
                public void onFailure() {
                    SmsManager.notifySmsError(sentIntent, 32);
                }
            });
            return;
        }
        try {
            ISms iSms = getISmsServiceOrThrow();
            if (iSms != null) {
                iSms.sendTextForSubscriberWithOptions(getSubscriptionId(), null, null, destinationAddress2, scAddress2, text2, sentIntent2, deliveryIntent2, persistMessage2, finalPriority2, expectMore2, finalValidity2);
            }
        } catch (RemoteException e) {
            Log.e(TAG, "sendTextMessageInternal(no persist): Couldn't send SMS, exception - " + e.getMessage());
            notifySmsError(sentIntent2, 31);
        }
    }

    /* renamed from: android.telephony.SmsManager$2 */
    /* loaded from: classes3.dex */
    public class AnonymousClass2 implements SubscriptionResolverResult {
        final /* synthetic */ PendingIntent val$deliveryIntent;
        final /* synthetic */ String val$destinationAddress;
        final /* synthetic */ boolean val$expectMore;
        final /* synthetic */ int val$finalPriority;
        final /* synthetic */ int val$finalValidity;
        final /* synthetic */ boolean val$persistMessage;
        final /* synthetic */ String val$scAddress;
        final /* synthetic */ PendingIntent val$sentIntent;
        final /* synthetic */ String val$text;

        AnonymousClass2(String destinationAddress2, String scAddress2, String text2, PendingIntent sentIntent2, PendingIntent deliveryIntent2, boolean persistMessage2, int finalPriority2, boolean expectMore2, int finalValidity2) {
            destinationAddress = destinationAddress2;
            scAddress = scAddress2;
            text = text2;
            sentIntent = sentIntent2;
            deliveryIntent = deliveryIntent2;
            persistMessage = persistMessage2;
            finalPriority = finalPriority2;
            expectMore = expectMore2;
            finalValidity = finalValidity2;
        }

        @Override // android.telephony.SmsManager.SubscriptionResolverResult
        public void onSuccess(int subId) {
            try {
                ISms iSms = SmsManager.m4427$$Nest$smgetISmsServiceOrThrow();
                if (iSms != null) {
                    iSms.sendTextForSubscriberWithOptions(subId, null, null, destinationAddress, scAddress, text, sentIntent, deliveryIntent, persistMessage, finalPriority, expectMore, finalValidity);
                }
            } catch (RemoteException e) {
                Log.e(SmsManager.TAG, "sendTextMessageInternal: Couldn't send SMS, exception - " + e.getMessage());
                SmsManager.notifySmsError(sentIntent, 31);
            }
        }

        @Override // android.telephony.SmsManager.SubscriptionResolverResult
        public void onFailure() {
            SmsManager.notifySmsError(sentIntent, 32);
        }
    }

    public void injectSmsPdu(byte[] pdu, String format, PendingIntent receivedIntent) {
        if (!format.equals("3gpp") && !format.equals("3gpp2")) {
            throw new IllegalArgumentException("Invalid pdu format. format must be either 3gpp or 3gpp2");
        }
        try {
            ISms iSms = TelephonyManager.getSmsService();
            if (iSms != null) {
                iSms.injectSmsPduForSubscriber(getSubscriptionId(), pdu, format, receivedIntent);
            }
        } catch (RemoteException e) {
            if (receivedIntent != null) {
                try {
                    receivedIntent.send(31);
                } catch (PendingIntent.CanceledException e2) {
                }
            }
        }
    }

    public ArrayList<String> divideMessage(String text) {
        if (text == null) {
            throw new IllegalArgumentException("text is null");
        }
        return SmsMessage.fragmentText(text, this);
    }

    public void sendMultipartTextMessage(String destinationAddress, String scAddress, ArrayList<String> parts, ArrayList<PendingIntent> sentIntents, ArrayList<PendingIntent> deliveryIntents) {
        sendMultipartTextMessageInternal(destinationAddress, scAddress, (List<String>) parts, (List<PendingIntent>) sentIntents, (List<PendingIntent>) deliveryIntents, true, getOpPackageName(), getAttributionTag(), 0L);
    }

    public void sendMultipartTextMessage(String destinationAddress, String scAddress, List<String> parts, List<PendingIntent> sentIntents, List<PendingIntent> deliveryIntents, long messageId) {
        sendMultipartTextMessageInternal(destinationAddress, scAddress, parts, sentIntents, deliveryIntents, true, getOpPackageName(), getAttributionTag(), messageId);
    }

    public void sendMultipartTextMessage(String destinationAddress, String scAddress, List<String> parts, List<PendingIntent> sentIntents, List<PendingIntent> deliveryIntents, String packageName, String attributionTag) {
        sendMultipartTextMessageInternal(destinationAddress, scAddress, parts, sentIntents, deliveryIntents, true, packageName, attributionTag, 0L);
    }

    private void sendMultipartTextMessageInternal(String destinationAddress, String scAddress, List<String> parts, List<PendingIntent> sentIntents, List<PendingIntent> deliveryIntents, boolean persistMessage, String packageName, String attributionTag, long messageId) {
        PendingIntent deliveryIntent;
        if (TextUtils.isEmpty(destinationAddress)) {
            throw new IllegalArgumentException("Invalid destinationAddress");
        }
        if (!getMnoName().toUpperCase().contains("DOCOMO") || destinationAddress.length() < 256) {
            if ((parts != null && parts.size() >= 1) || isSupportSendingEmptySms()) {
                if (parts.size() > 1) {
                    if (persistMessage) {
                        resolveSubscriptionForOperation(new SubscriptionResolverResult() { // from class: android.telephony.SmsManager.3
                            final /* synthetic */ String val$attributionTag;
                            final /* synthetic */ List val$deliveryIntents;
                            final /* synthetic */ String val$destinationAddress;
                            final /* synthetic */ long val$messageId;
                            final /* synthetic */ String val$packageName;
                            final /* synthetic */ List val$parts;
                            final /* synthetic */ boolean val$persistMessage;
                            final /* synthetic */ String val$scAddress;
                            final /* synthetic */ List val$sentIntents;

                            AnonymousClass3(String packageName2, String attributionTag2, String destinationAddress2, String scAddress2, List parts2, List sentIntents2, List deliveryIntents2, boolean persistMessage2, long messageId2) {
                                packageName = packageName2;
                                attributionTag = attributionTag2;
                                destinationAddress = destinationAddress2;
                                scAddress = scAddress2;
                                parts = parts2;
                                sentIntents = sentIntents2;
                                deliveryIntents = deliveryIntents2;
                                persistMessage = persistMessage2;
                                messageId = messageId2;
                            }

                            @Override // android.telephony.SmsManager.SubscriptionResolverResult
                            public void onSuccess(int subId) {
                                try {
                                    ISms iSms = SmsManager.m4427$$Nest$smgetISmsServiceOrThrow();
                                    iSms.sendMultipartTextForSubscriber(subId, packageName, attributionTag, destinationAddress, scAddress, parts, sentIntents, deliveryIntents, persistMessage, messageId);
                                } catch (RemoteException e) {
                                    Log.e(SmsManager.TAG, "sendMultipartTextMessageInternal: Couldn't send SMS - " + e.getMessage() + " " + SmsManager.formatCrossStackMessageId(messageId));
                                    SmsManager.notifySmsError((List<PendingIntent>) sentIntents, 31);
                                }
                            }

                            @Override // android.telephony.SmsManager.SubscriptionResolverResult
                            public void onFailure() {
                                SmsManager.notifySmsError((List<PendingIntent>) sentIntents, 32);
                            }
                        });
                        return;
                    }
                    try {
                        ISms iSms = getISmsServiceOrThrow();
                        if (iSms != null) {
                            iSms.sendMultipartTextForSubscriber(getSubscriptionId(), packageName2, attributionTag2, destinationAddress2, scAddress2, parts2, sentIntents2, deliveryIntents2, persistMessage2, messageId2);
                            return;
                        }
                        return;
                    } catch (RemoteException e) {
                        Log.e(TAG, "sendMultipartTextMessageInternal: Couldn't send SMS - " + e.getMessage() + " " + formatCrossStackMessageId(messageId2));
                        notifySmsError(sentIntents2, 31);
                        return;
                    }
                }
                PendingIntent sentIntent = null;
                if (sentIntents2 != null && sentIntents2.size() > 0) {
                    sentIntent = sentIntents2.get(0);
                }
                if (deliveryIntents2 != null && deliveryIntents2.size() > 0) {
                    PendingIntent deliveryIntent2 = deliveryIntents2.get(0);
                    deliveryIntent = deliveryIntent2;
                } else {
                    deliveryIntent = null;
                }
                sendTextMessageInternal(destinationAddress2, scAddress2, parts2.get(0), sentIntent, deliveryIntent, true, packageName2, attributionTag2, messageId2);
                return;
            }
            throw new IllegalArgumentException("Invalid message body");
        }
        throw new IllegalArgumentException("Invalid destinationAddress");
    }

    /* renamed from: android.telephony.SmsManager$3 */
    /* loaded from: classes3.dex */
    public class AnonymousClass3 implements SubscriptionResolverResult {
        final /* synthetic */ String val$attributionTag;
        final /* synthetic */ List val$deliveryIntents;
        final /* synthetic */ String val$destinationAddress;
        final /* synthetic */ long val$messageId;
        final /* synthetic */ String val$packageName;
        final /* synthetic */ List val$parts;
        final /* synthetic */ boolean val$persistMessage;
        final /* synthetic */ String val$scAddress;
        final /* synthetic */ List val$sentIntents;

        AnonymousClass3(String packageName2, String attributionTag2, String destinationAddress2, String scAddress2, List parts2, List sentIntents2, List deliveryIntents2, boolean persistMessage2, long messageId2) {
            packageName = packageName2;
            attributionTag = attributionTag2;
            destinationAddress = destinationAddress2;
            scAddress = scAddress2;
            parts = parts2;
            sentIntents = sentIntents2;
            deliveryIntents = deliveryIntents2;
            persistMessage = persistMessage2;
            messageId = messageId2;
        }

        @Override // android.telephony.SmsManager.SubscriptionResolverResult
        public void onSuccess(int subId) {
            try {
                ISms iSms = SmsManager.m4427$$Nest$smgetISmsServiceOrThrow();
                iSms.sendMultipartTextForSubscriber(subId, packageName, attributionTag, destinationAddress, scAddress, parts, sentIntents, deliveryIntents, persistMessage, messageId);
            } catch (RemoteException e) {
                Log.e(SmsManager.TAG, "sendMultipartTextMessageInternal: Couldn't send SMS - " + e.getMessage() + " " + SmsManager.formatCrossStackMessageId(messageId));
                SmsManager.notifySmsError((List<PendingIntent>) sentIntents, 31);
            }
        }

        @Override // android.telephony.SmsManager.SubscriptionResolverResult
        public void onFailure() {
            SmsManager.notifySmsError((List<PendingIntent>) sentIntents, 32);
        }
    }

    @SystemApi
    public void sendMultipartTextMessageWithoutPersisting(String destinationAddress, String scAddress, List<String> parts, List<PendingIntent> sentIntents, List<PendingIntent> deliveryIntents) {
        sendMultipartTextMessageInternal(destinationAddress, scAddress, parts, sentIntents, deliveryIntents, false, getOpPackageName(), getAttributionTag(), 0L);
    }

    public void sendMultipartTextMessage(String destinationAddress, String scAddress, ArrayList<String> parts, ArrayList<PendingIntent> sentIntents, ArrayList<PendingIntent> deliveryIntents, int priority, boolean expectMore, int validityPeriod) {
        sendMultipartTextMessageInternal(destinationAddress, scAddress, (List<String>) parts, (List<PendingIntent>) sentIntents, (List<PendingIntent>) deliveryIntents, true, priority, expectMore, validityPeriod);
    }

    private void sendMultipartTextMessageInternal(String destinationAddress, String scAddress, List<String> parts, List<PendingIntent> sentIntents, List<PendingIntent> deliveryIntents, boolean persistMessage, int priority, boolean expectMore, int validityPeriod) {
        int priority2;
        int validityPeriod2;
        List<PendingIntent> list;
        if (TextUtils.isEmpty(destinationAddress)) {
            throw new IllegalArgumentException("Invalid destinationAddress");
        }
        if (parts == null || parts.size() < 1) {
            throw new IllegalArgumentException("Invalid message body");
        }
        if (priority < 0 || priority > 3) {
            Log.e(TAG, "Invalid Priority " + priority);
            priority2 = -1;
        } else {
            priority2 = priority;
        }
        if (validityPeriod < 5 || validityPeriod > 635040) {
            Log.e(TAG, "Invalid Validity Period " + validityPeriod);
            validityPeriod2 = -1;
        } else {
            validityPeriod2 = validityPeriod;
        }
        if (parts.size() > 1) {
            int finalPriority = priority2;
            int finalValidity = validityPeriod2;
            if (persistMessage) {
                resolveSubscriptionForOperation(new SubscriptionResolverResult() { // from class: android.telephony.SmsManager.4
                    final /* synthetic */ List val$deliveryIntents;
                    final /* synthetic */ String val$destinationAddress;
                    final /* synthetic */ boolean val$expectMore;
                    final /* synthetic */ int val$finalPriority;
                    final /* synthetic */ int val$finalValidity;
                    final /* synthetic */ List val$parts;
                    final /* synthetic */ boolean val$persistMessage;
                    final /* synthetic */ String val$scAddress;
                    final /* synthetic */ List val$sentIntents;

                    AnonymousClass4(String destinationAddress2, String scAddress2, List parts2, List sentIntents2, List deliveryIntents2, boolean persistMessage2, int finalPriority2, boolean expectMore2, int finalValidity2) {
                        destinationAddress = destinationAddress2;
                        scAddress = scAddress2;
                        parts = parts2;
                        sentIntents = sentIntents2;
                        deliveryIntents = deliveryIntents2;
                        persistMessage = persistMessage2;
                        finalPriority = finalPriority2;
                        expectMore = expectMore2;
                        finalValidity = finalValidity2;
                    }

                    @Override // android.telephony.SmsManager.SubscriptionResolverResult
                    public void onSuccess(int subId) {
                        try {
                            ISms iSms = SmsManager.m4427$$Nest$smgetISmsServiceOrThrow();
                            if (iSms != null) {
                                iSms.sendMultipartTextForSubscriberWithOptions(subId, null, null, destinationAddress, scAddress, parts, sentIntents, deliveryIntents, persistMessage, finalPriority, expectMore, finalValidity);
                            }
                        } catch (RemoteException e) {
                            Log.e(SmsManager.TAG, "sendMultipartTextMessageInternal: Couldn't send SMS - " + e.getMessage());
                            SmsManager.notifySmsError((List<PendingIntent>) sentIntents, 31);
                        }
                    }

                    @Override // android.telephony.SmsManager.SubscriptionResolverResult
                    public void onFailure() {
                        SmsManager.notifySmsError((List<PendingIntent>) sentIntents, 32);
                    }
                });
                return;
            }
            try {
                ISms iSms = getISmsServiceOrThrow();
                if (iSms != null) {
                    list = sentIntents2;
                    try {
                        iSms.sendMultipartTextForSubscriberWithOptions(getSubscriptionId(), null, null, destinationAddress2, scAddress2, parts2, sentIntents2, deliveryIntents2, persistMessage2, finalPriority2, expectMore2, finalValidity2);
                    } catch (RemoteException e) {
                        e = e;
                        Log.e(TAG, "sendMultipartTextMessageInternal (no persist): Couldn't send SMS - " + e.getMessage());
                        notifySmsError(list, 31);
                    }
                }
            } catch (RemoteException e2) {
                e = e2;
                list = sentIntents2;
            }
        } else {
            PendingIntent sentIntent = null;
            PendingIntent deliveryIntent = null;
            if (sentIntents2 != null && sentIntents2.size() > 0) {
                sentIntent = sentIntents2.get(0);
            }
            if (deliveryIntents2 != null && deliveryIntents2.size() > 0) {
                deliveryIntent = deliveryIntents2.get(0);
            }
            sendTextMessageInternal(destinationAddress2, scAddress2, parts2.get(0), sentIntent, deliveryIntent, persistMessage2, priority2, expectMore2, validityPeriod2);
        }
    }

    /* renamed from: android.telephony.SmsManager$4 */
    /* loaded from: classes3.dex */
    public class AnonymousClass4 implements SubscriptionResolverResult {
        final /* synthetic */ List val$deliveryIntents;
        final /* synthetic */ String val$destinationAddress;
        final /* synthetic */ boolean val$expectMore;
        final /* synthetic */ int val$finalPriority;
        final /* synthetic */ int val$finalValidity;
        final /* synthetic */ List val$parts;
        final /* synthetic */ boolean val$persistMessage;
        final /* synthetic */ String val$scAddress;
        final /* synthetic */ List val$sentIntents;

        AnonymousClass4(String destinationAddress2, String scAddress2, List parts2, List sentIntents2, List deliveryIntents2, boolean persistMessage2, int finalPriority2, boolean expectMore2, int finalValidity2) {
            destinationAddress = destinationAddress2;
            scAddress = scAddress2;
            parts = parts2;
            sentIntents = sentIntents2;
            deliveryIntents = deliveryIntents2;
            persistMessage = persistMessage2;
            finalPriority = finalPriority2;
            expectMore = expectMore2;
            finalValidity = finalValidity2;
        }

        @Override // android.telephony.SmsManager.SubscriptionResolverResult
        public void onSuccess(int subId) {
            try {
                ISms iSms = SmsManager.m4427$$Nest$smgetISmsServiceOrThrow();
                if (iSms != null) {
                    iSms.sendMultipartTextForSubscriberWithOptions(subId, null, null, destinationAddress, scAddress, parts, sentIntents, deliveryIntents, persistMessage, finalPriority, expectMore, finalValidity);
                }
            } catch (RemoteException e) {
                Log.e(SmsManager.TAG, "sendMultipartTextMessageInternal: Couldn't send SMS - " + e.getMessage());
                SmsManager.notifySmsError((List<PendingIntent>) sentIntents, 31);
            }
        }

        @Override // android.telephony.SmsManager.SubscriptionResolverResult
        public void onFailure() {
            SmsManager.notifySmsError((List<PendingIntent>) sentIntents, 32);
        }
    }

    public void sendDataMessage(String destinationAddress, String scAddress, short destinationPort, byte[] data, PendingIntent sentIntent, PendingIntent deliveryIntent) {
        if (TextUtils.isEmpty(destinationAddress)) {
            throw new IllegalArgumentException("Invalid destinationAddress");
        }
        if (getMnoName().toUpperCase().contains("DOCOMO") && destinationAddress.length() >= 256) {
            throw new IllegalArgumentException("Invalid destinationAddress");
        }
        if (data == null || data.length == 0) {
            throw new IllegalArgumentException("Invalid message data");
        }
        resolveSubscriptionForOperation(new SubscriptionResolverResult() { // from class: android.telephony.SmsManager.5
            final /* synthetic */ byte[] val$data;
            final /* synthetic */ PendingIntent val$deliveryIntent;
            final /* synthetic */ String val$destinationAddress;
            final /* synthetic */ short val$destinationPort;
            final /* synthetic */ String val$scAddress;
            final /* synthetic */ PendingIntent val$sentIntent;

            AnonymousClass5(String destinationAddress2, String scAddress2, short destinationPort2, byte[] data2, PendingIntent sentIntent2, PendingIntent deliveryIntent2) {
                destinationAddress = destinationAddress2;
                scAddress = scAddress2;
                destinationPort = destinationPort2;
                data = data2;
                sentIntent = sentIntent2;
                deliveryIntent = deliveryIntent2;
            }

            @Override // android.telephony.SmsManager.SubscriptionResolverResult
            public void onSuccess(int subId) {
                try {
                    ISms iSms = SmsManager.m4427$$Nest$smgetISmsServiceOrThrow();
                    iSms.sendDataForSubscriber(subId, null, null, destinationAddress, scAddress, 65535 & destinationPort, data, sentIntent, deliveryIntent);
                } catch (RemoteException e) {
                    Log.e(SmsManager.TAG, "sendDataMessage: Couldn't send SMS - Exception: " + e.getMessage());
                    SmsManager.notifySmsError(sentIntent, 31);
                }
            }

            @Override // android.telephony.SmsManager.SubscriptionResolverResult
            public void onFailure() {
                SmsManager.notifySmsError(sentIntent, 32);
            }
        });
    }

    /* renamed from: android.telephony.SmsManager$5 */
    /* loaded from: classes3.dex */
    class AnonymousClass5 implements SubscriptionResolverResult {
        final /* synthetic */ byte[] val$data;
        final /* synthetic */ PendingIntent val$deliveryIntent;
        final /* synthetic */ String val$destinationAddress;
        final /* synthetic */ short val$destinationPort;
        final /* synthetic */ String val$scAddress;
        final /* synthetic */ PendingIntent val$sentIntent;

        AnonymousClass5(String destinationAddress2, String scAddress2, short destinationPort2, byte[] data2, PendingIntent sentIntent2, PendingIntent deliveryIntent2) {
            destinationAddress = destinationAddress2;
            scAddress = scAddress2;
            destinationPort = destinationPort2;
            data = data2;
            sentIntent = sentIntent2;
            deliveryIntent = deliveryIntent2;
        }

        @Override // android.telephony.SmsManager.SubscriptionResolverResult
        public void onSuccess(int subId) {
            try {
                ISms iSms = SmsManager.m4427$$Nest$smgetISmsServiceOrThrow();
                iSms.sendDataForSubscriber(subId, null, null, destinationAddress, scAddress, 65535 & destinationPort, data, sentIntent, deliveryIntent);
            } catch (RemoteException e) {
                Log.e(SmsManager.TAG, "sendDataMessage: Couldn't send SMS - Exception: " + e.getMessage());
                SmsManager.notifySmsError(sentIntent, 31);
            }
        }

        @Override // android.telephony.SmsManager.SubscriptionResolverResult
        public void onFailure() {
            SmsManager.notifySmsError(sentIntent, 32);
        }
    }

    @Deprecated
    public static SmsManager getDefault() {
        return DEFAULT_INSTANCE;
    }

    public static SmsManager getSmsManagerForContextAndSubscriptionId(Context context, int subId) {
        SmsManager smsManager;
        synchronized (sLockObject) {
            Pair<Context, Integer> key = new Pair<>(context, Integer.valueOf(subId));
            Map<Pair<Context, Integer>, SmsManager> map = sSubInstances;
            smsManager = map.get(key);
            if (smsManager == null) {
                smsManager = new SmsManager(context, subId);
                map.put(key, smsManager);
            }
        }
        return smsManager;
    }

    @Deprecated
    public static SmsManager getSmsManagerForSubscriptionId(int subId) {
        return getSmsManagerForContextAndSubscriptionId(null, subId);
    }

    public SmsManager createForSubscriptionId(int subId) {
        return getSmsManagerForContextAndSubscriptionId(this.mContext, subId);
    }

    private SmsManager(Context context, int subId) {
        this.mContext = context;
        this.mSubId = subId;
    }

    public int getSubscriptionId() {
        try {
            int i = this.mSubId;
            if (i != Integer.MAX_VALUE) {
                return i;
            }
            return getISmsServiceOrThrow().getPreferredSmsSubscription();
        } catch (RemoteException e) {
            return -1;
        }
    }

    private void resolveSubscriptionForOperation(SubscriptionResolverResult resolverResult) {
        int subId = getSubscriptionId();
        boolean isSmsSimPickActivityNeeded = false;
        try {
            ISms iSms = getISmsService();
            if (iSms != null) {
                isSmsSimPickActivityNeeded = iSms.isSmsSimPickActivityNeeded(subId);
            }
        } catch (RemoteException ex) {
            Log.e(TAG, "resolveSubscriptionForOperation", ex);
        }
        if (isSmsSimPickActivityNeeded) {
            Log.d(TAG, "resolveSubscriptionForOperation isSmsSimPickActivityNeeded is true for calling package. ");
            try {
                getITelephony().enqueueSmsPickResult(null, null, new IIntegerConsumer.Stub() { // from class: android.telephony.SmsManager.6
                    final /* synthetic */ SubscriptionResolverResult val$resolverResult;

                    AnonymousClass6(SubscriptionResolverResult resolverResult2) {
                        resolverResult = resolverResult2;
                    }

                    @Override // com.android.internal.telephony.IIntegerConsumer
                    public void accept(int subId2) {
                        SmsManager.this.sendResolverResult(resolverResult, subId2, true);
                    }
                });
                return;
            } catch (RemoteException ex2) {
                Log.e(TAG, "Unable to launch activity", ex2);
                sendResolverResult(resolverResult2, subId, true);
                return;
            }
        }
        sendResolverResult(resolverResult2, subId, false);
    }

    /* renamed from: android.telephony.SmsManager$6 */
    /* loaded from: classes3.dex */
    public class AnonymousClass6 extends IIntegerConsumer.Stub {
        final /* synthetic */ SubscriptionResolverResult val$resolverResult;

        AnonymousClass6(SubscriptionResolverResult resolverResult2) {
            resolverResult = resolverResult2;
        }

        @Override // com.android.internal.telephony.IIntegerConsumer
        public void accept(int subId2) {
            SmsManager.this.sendResolverResult(resolverResult, subId2, true);
        }
    }

    public void sendResolverResult(SubscriptionResolverResult resolverResult, int subId, boolean pickActivityShown) {
        if (SubscriptionManager.isValidSubscriptionId(subId)) {
            resolverResult.onSuccess(subId);
        } else if (!Compatibility.isChangeEnabled(GET_TARGET_SDK_VERSION_CODE_CHANGE) && !pickActivityShown) {
            resolverResult.onSuccess(subId);
        } else {
            resolverResult.onFailure();
        }
    }

    private static ITelephony getITelephony() {
        ITelephony binder = ITelephony.Stub.asInterface(TelephonyFrameworkInitializer.getTelephonyServiceManager().getTelephonyServiceRegisterer().get());
        if (binder == null) {
            throw new RuntimeException("Could not find Telephony Service.");
        }
        return binder;
    }

    public static void notifySmsError(PendingIntent pendingIntent, int error) {
        if (pendingIntent != null) {
            try {
                pendingIntent.send(error);
            } catch (PendingIntent.CanceledException e) {
            }
        }
    }

    public static void notifySmsError(List<PendingIntent> pendingIntents, int error) {
        if (pendingIntents != null) {
            for (PendingIntent pendingIntent : pendingIntents) {
                notifySmsError(pendingIntent, error);
            }
        }
    }

    private static ISms getISmsServiceOrThrow() {
        ISms iSms = TelephonyManager.getSmsService();
        if (iSms == null) {
            throw new UnsupportedOperationException("Sms is not supported");
        }
        return iSms;
    }

    private static ISms getISmsService() {
        return TelephonyManager.getSmsService();
    }

    public boolean copyMessageToIcc(byte[] smsc, byte[] pdu, int status) {
        if (pdu == null) {
            throw new IllegalArgumentException("pdu is null");
        }
        try {
            ISms iSms = getISmsService();
            if (iSms == null) {
                return false;
            }
            boolean success = iSms.copyMessageToIccEfForSubscriber(getSubscriptionId(), null, status, pdu, smsc);
            return success;
        } catch (RemoteException e) {
            return false;
        }
    }

    public boolean deleteMessageFromIcc(int messageIndex) {
        try {
            ISms iSms = getISmsService();
            if (iSms == null) {
                return false;
            }
            boolean success = iSms.updateMessageOnIccEfForSubscriber(getSubscriptionId(), null, messageIndex, 0, null);
            return success;
        } catch (RemoteException e) {
            return false;
        }
    }

    public boolean updateMessageOnIcc(int messageIndex, int newStatus, byte[] pdu) {
        try {
            ISms iSms = getISmsService();
            if (iSms == null) {
                return false;
            }
            boolean success = iSms.updateMessageOnIccEfForSubscriber(getSubscriptionId(), null, messageIndex, newStatus, pdu);
            return success;
        } catch (RemoteException e) {
            return false;
        }
    }

    public List<SmsMessage> getMessagesFromIcc() {
        return getAllMessagesFromIcc();
    }

    public ArrayList<SmsMessage> getAllMessagesFromIcc() {
        List<SmsRawData> records = null;
        try {
            ISms iSms = getISmsService();
            if (iSms != null) {
                records = iSms.getAllMessagesFromIccEfForSubscriber(getSubscriptionId(), null);
            }
        } catch (RemoteException e) {
        }
        return createMessageListFromRawRecords(records);
    }

    @SystemApi
    @Deprecated
    public boolean enableCellBroadcastRange(int startMessageId, int endMessageId, int ranType) {
        boolean success = false;
        if (endMessageId < startMessageId) {
            throw new IllegalArgumentException("endMessageId < startMessageId");
        }
        try {
            ISms iSms = getISmsService();
            if (iSms != null) {
                int subId = getSubscriptionId();
                success = iSms.enableCellBroadcastRangeForSubscriber(subId, startMessageId, endMessageId, ranType);
                com.android.telephony.Rlog.d(TAG, "enableCellBroadcastRange: " + (success ? "succeeded" : "failed") + " at calling enableCellBroadcastRangeForSubscriber. subId = " + subId);
            }
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.d(TAG, "enableCellBroadcastRange: ", ex);
        }
        return success;
    }

    @SystemApi
    @Deprecated
    public boolean disableCellBroadcastRange(int startMessageId, int endMessageId, int ranType) {
        boolean success = false;
        if (endMessageId < startMessageId) {
            throw new IllegalArgumentException("endMessageId < startMessageId");
        }
        try {
            ISms iSms = getISmsService();
            if (iSms != null) {
                int subId = getSubscriptionId();
                success = iSms.disableCellBroadcastRangeForSubscriber(subId, startMessageId, endMessageId, ranType);
                com.android.telephony.Rlog.d(TAG, "disableCellBroadcastRange: " + (success ? "succeeded" : "failed") + " at calling disableCellBroadcastRangeForSubscriber. subId = " + subId);
            }
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.d(TAG, "disableCellBroadcastRange: ", ex);
        }
        return success;
    }

    private ArrayList<SmsMessage> createMessageListFromRawRecords(List<SmsRawData> records) {
        SmsMessage sms;
        ArrayList<SmsMessage> messages = new ArrayList<>();
        if (records != null) {
            int count = records.size();
            for (int i = 0; i < count; i++) {
                SmsRawData data = records.get(i);
                if (data != null && (sms = SmsMessage.createFromEfRecord(i + 1, data.getBytes(), getSubscriptionId())) != null) {
                    messages.add(sms);
                }
            }
        }
        return messages;
    }

    public boolean isImsSmsSupported() {
        try {
            ISms iSms = getISmsService();
            if (iSms == null) {
                return false;
            }
            boolean boSupported = iSms.isImsSmsSupportedForSubscriber(getSubscriptionId());
            return boSupported;
        } catch (RemoteException e) {
            return false;
        }
    }

    public String getImsSmsFormat() {
        try {
            ISms iSms = getISmsService();
            if (iSms == null) {
                return "unknown";
            }
            String format = iSms.getImsSmsFormatForSubscriber(getSubscriptionId());
            return format;
        } catch (RemoteException e) {
            return "unknown";
        }
    }

    public static int getDefaultSmsSubscriptionId() {
        try {
            return getISmsService().getPreferredSmsSubscription();
        } catch (RemoteException e) {
            return -1;
        } catch (NullPointerException e2) {
            return -1;
        }
    }

    public boolean isSMSPromptEnabled() {
        try {
            ISms iSms = TelephonyManager.getSmsService();
            return iSms.isSMSPromptEnabled();
        } catch (RemoteException e) {
            return false;
        } catch (NullPointerException e2) {
            return false;
        }
    }

    public int getSmsCapacityOnIcc() {
        try {
            ISms iccISms = getISmsService();
            if (iccISms == null) {
                return 0;
            }
            int ret = iccISms.getSmsCapacityOnIccForSubscriber(getSubscriptionId());
            return ret;
        } catch (RemoteException ex) {
            Log.e(TAG, "getSmsCapacityOnIcc() RemoteException", ex);
            return 0;
        }
    }

    public void sendMultimediaMessage(Context context, Uri contentUri, String locationUrl, Bundle configOverrides, PendingIntent sentIntent) {
        sendMultimediaMessage(context, contentUri, locationUrl, configOverrides, sentIntent, 0L);
    }

    public void sendMultimediaMessage(Context context, Uri contentUri, String locationUrl, Bundle configOverrides, PendingIntent sentIntent, long messageId) {
        if (contentUri == null) {
            throw new IllegalArgumentException("Uri contentUri null");
        }
        MmsManager m = (MmsManager) context.getSystemService("mms");
        if (m != null) {
            resolveSubscriptionForOperation(new SubscriptionResolverResult() { // from class: android.telephony.SmsManager.7
                final /* synthetic */ Bundle val$configOverrides;
                final /* synthetic */ Uri val$contentUri;
                final /* synthetic */ String val$locationUrl;
                final /* synthetic */ MmsManager val$m;
                final /* synthetic */ long val$messageId;
                final /* synthetic */ PendingIntent val$sentIntent;

                AnonymousClass7(MmsManager m2, Uri contentUri2, String locationUrl2, Bundle configOverrides2, PendingIntent sentIntent2, long messageId2) {
                    m = m2;
                    contentUri = contentUri2;
                    locationUrl = locationUrl2;
                    configOverrides = configOverrides2;
                    sentIntent = sentIntent2;
                    messageId = messageId2;
                }

                @Override // android.telephony.SmsManager.SubscriptionResolverResult
                public void onSuccess(int subId) {
                    m.sendMultimediaMessage(subId, contentUri, locationUrl, configOverrides, sentIntent, messageId);
                }

                @Override // android.telephony.SmsManager.SubscriptionResolverResult
                public void onFailure() {
                    SmsManager.notifySmsError(sentIntent, 32);
                }
            });
        }
    }

    /* renamed from: android.telephony.SmsManager$7 */
    /* loaded from: classes3.dex */
    public class AnonymousClass7 implements SubscriptionResolverResult {
        final /* synthetic */ Bundle val$configOverrides;
        final /* synthetic */ Uri val$contentUri;
        final /* synthetic */ String val$locationUrl;
        final /* synthetic */ MmsManager val$m;
        final /* synthetic */ long val$messageId;
        final /* synthetic */ PendingIntent val$sentIntent;

        AnonymousClass7(MmsManager m2, Uri contentUri2, String locationUrl2, Bundle configOverrides2, PendingIntent sentIntent2, long messageId2) {
            m = m2;
            contentUri = contentUri2;
            locationUrl = locationUrl2;
            configOverrides = configOverrides2;
            sentIntent = sentIntent2;
            messageId = messageId2;
        }

        @Override // android.telephony.SmsManager.SubscriptionResolverResult
        public void onSuccess(int subId) {
            m.sendMultimediaMessage(subId, contentUri, locationUrl, configOverrides, sentIntent, messageId);
        }

        @Override // android.telephony.SmsManager.SubscriptionResolverResult
        public void onFailure() {
            SmsManager.notifySmsError(sentIntent, 32);
        }
    }

    public void downloadMultimediaMessage(Context context, String locationUrl, Uri contentUri, Bundle configOverrides, PendingIntent downloadedIntent) {
        downloadMultimediaMessage(context, locationUrl, contentUri, configOverrides, downloadedIntent, 0L);
    }

    public void downloadMultimediaMessage(Context context, String locationUrl, Uri contentUri, Bundle configOverrides, PendingIntent downloadedIntent, long messageId) {
        if (TextUtils.isEmpty(locationUrl)) {
            throw new IllegalArgumentException("Empty MMS location URL");
        }
        if (contentUri == null) {
            throw new IllegalArgumentException("Uri contentUri null");
        }
        MmsManager m = (MmsManager) context.getSystemService("mms");
        if (m != null) {
            resolveSubscriptionForOperation(new SubscriptionResolverResult() { // from class: android.telephony.SmsManager.8
                final /* synthetic */ Bundle val$configOverrides;
                final /* synthetic */ Uri val$contentUri;
                final /* synthetic */ PendingIntent val$downloadedIntent;
                final /* synthetic */ String val$locationUrl;
                final /* synthetic */ MmsManager val$m;
                final /* synthetic */ long val$messageId;

                AnonymousClass8(MmsManager m2, String locationUrl2, Uri contentUri2, Bundle configOverrides2, PendingIntent downloadedIntent2, long messageId2) {
                    m = m2;
                    locationUrl = locationUrl2;
                    contentUri = contentUri2;
                    configOverrides = configOverrides2;
                    downloadedIntent = downloadedIntent2;
                    messageId = messageId2;
                }

                @Override // android.telephony.SmsManager.SubscriptionResolverResult
                public void onSuccess(int subId) {
                    m.downloadMultimediaMessage(subId, locationUrl, contentUri, configOverrides, downloadedIntent, messageId);
                }

                @Override // android.telephony.SmsManager.SubscriptionResolverResult
                public void onFailure() {
                    SmsManager.notifySmsError(downloadedIntent, 32);
                }
            });
        }
    }

    /* renamed from: android.telephony.SmsManager$8 */
    /* loaded from: classes3.dex */
    public class AnonymousClass8 implements SubscriptionResolverResult {
        final /* synthetic */ Bundle val$configOverrides;
        final /* synthetic */ Uri val$contentUri;
        final /* synthetic */ PendingIntent val$downloadedIntent;
        final /* synthetic */ String val$locationUrl;
        final /* synthetic */ MmsManager val$m;
        final /* synthetic */ long val$messageId;

        AnonymousClass8(MmsManager m2, String locationUrl2, Uri contentUri2, Bundle configOverrides2, PendingIntent downloadedIntent2, long messageId2) {
            m = m2;
            locationUrl = locationUrl2;
            contentUri = contentUri2;
            configOverrides = configOverrides2;
            downloadedIntent = downloadedIntent2;
            messageId = messageId2;
        }

        @Override // android.telephony.SmsManager.SubscriptionResolverResult
        public void onSuccess(int subId) {
            m.downloadMultimediaMessage(subId, locationUrl, contentUri, configOverrides, downloadedIntent, messageId);
        }

        @Override // android.telephony.SmsManager.SubscriptionResolverResult
        public void onFailure() {
            SmsManager.notifySmsError(downloadedIntent, 32);
        }
    }

    public Bundle getCarrierConfigValues() {
        try {
            ISms iSms = getISmsService();
            if (iSms != null) {
                return iSms.getCarrierConfigValuesForSubscriber(getSubscriptionId());
            }
        } catch (RemoteException e) {
        }
        return new Bundle();
    }

    public String createAppSpecificSmsToken(PendingIntent intent) {
        try {
            ISms iccSms = getISmsServiceOrThrow();
            return iccSms.createAppSpecificSmsToken(getSubscriptionId(), null, intent);
        } catch (RemoteException ex) {
            ex.rethrowFromSystemServer();
            return null;
        }
    }

    public void getSmsMessagesForFinancialApp(Bundle params, Executor executor, FinancialSmsCallback callback) {
    }

    public String createAppSpecificSmsTokenWithPackageInfo(String prefixes, PendingIntent intent) {
        try {
            ISms iccSms = getISmsServiceOrThrow();
            return iccSms.createAppSpecificSmsTokenWithPackageInfo(getSubscriptionId(), null, prefixes, intent);
        } catch (RemoteException ex) {
            ex.rethrowFromSystemServer();
            return null;
        }
    }

    public void setStorageMonitorMemoryStatusOverride(boolean storageAvailable) {
        try {
            ISms iccISms = getISmsServiceOrThrow();
            if (iccISms != null) {
                iccISms.setStorageMonitorMemoryStatusOverride(getSubscriptionId(), storageAvailable);
            }
        } catch (RemoteException ex) {
            ex.rethrowFromSystemServer();
        }
    }

    public void clearStorageMonitorMemoryStatusOverride() {
        try {
            ISms iccISms = getISmsServiceOrThrow();
            if (iccISms != null) {
                iccISms.clearStorageMonitorMemoryStatusOverride(getSubscriptionId());
            }
        } catch (RemoteException ex) {
            ex.rethrowFromSystemServer();
        }
    }

    public int checkSmsShortCodeDestination(String destAddress, String countryIso) {
        try {
            ISms iccISms = getISmsServiceOrThrow();
            if (iccISms != null) {
                return iccISms.checkSmsShortCodeDestination(getSubscriptionId(), null, null, destAddress, countryIso);
            }
            return 0;
        } catch (RemoteException e) {
            Log.e(TAG, "checkSmsShortCodeDestination() RemoteException", e);
            return 0;
        }
    }

    public String getSmscAddress() {
        try {
            ISms iSms = getISmsService();
            if (iSms == null) {
                return null;
            }
            String smsc = iSms.getSmscAddressFromIccEfForSubscriber(getSubscriptionId(), null);
            return smsc;
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
    }

    public boolean setSmscAddress(String smsc) {
        byte[] encodedSmscAddress;
        char c = smsc.charAt(0);
        boolean isNumeric = PhoneNumberUtils.isDialable(c);
        if (smsc == null) {
            encodedSmscAddress = null;
        } else {
            int numberLenReal = smsc.length();
            int numberLenEffective = numberLenReal;
            boolean hasPlus = smsc.indexOf(43) != -1;
            if (hasPlus) {
                numberLenEffective--;
            }
            if (numberLenEffective > 20) {
                return false;
            }
            if (isNumeric) {
                com.android.telephony.Rlog.d(TAG, "Smsc is Numeric.");
                encodedSmscAddress = PhoneNumberUtils.networkPortionToCalledPartyBCDWithLength(smsc);
            } else {
                com.android.telephony.Rlog.i(TAG, "Smsc is Alphabetic.");
                try {
                    byte[] encodedSmscAddress2 = GsmAlphabet.stringToGsm7BitPacked(smsc);
                    encodedSmscAddress = encodedSmscAddress2;
                } catch (EncodeException ex) {
                    Log.e(TAG, "Implausible UnsupportedEncodingException ", ex);
                    return false;
                }
            }
        }
        try {
            ISms iSms = getISmsService();
            if (iSms == null) {
                return false;
            }
            return iSms.setSmscAddressOnIccEfForSubscriber(IccUtils.bytesToHexString(encodedSmscAddress), getSubscriptionId(), null);
        } catch (RemoteException ex2) {
            throw new RuntimeException(ex2);
        }
    }

    @SystemApi
    public int getPremiumSmsConsent(String packageName) {
        try {
            ISms iSms = getISmsService();
            if (iSms == null) {
                return 0;
            }
            int permission = iSms.getPremiumSmsPermission(packageName);
            return permission;
        } catch (RemoteException e) {
            Log.e(TAG, "getPremiumSmsPermission() RemoteException", e);
            return 0;
        }
    }

    @SystemApi
    public void setPremiumSmsConsent(String packageName, int permission) {
        try {
            ISms iSms = getISmsService();
            if (iSms != null) {
                if (ISIS_PACKAGE_NAME.equals(packageName) && getMnoName().toUpperCase().contains("VZW")) {
                    permission = 3;
                    Log.i(TAG, "setPremiumSmsPermission() for ISIS_PACKAGE");
                }
                iSms.setPremiumSmsPermission(packageName, permission);
            }
        } catch (RemoteException e) {
            Log.e(TAG, "setPremiumSmsPermission() RemoteException", e);
        }
    }

    @SystemApi
    @Deprecated
    public void resetAllCellBroadcastRanges() {
        try {
            ISms iSms = getISmsService();
            if (iSms != null) {
                iSms.resetAllCellBroadcastRanges(getSubscriptionId());
            }
        } catch (RemoteException ex) {
            ex.rethrowFromSystemServer();
        }
    }

    public static String formatCrossStackMessageId(long id) {
        return "{x-message-id:" + id + "}";
    }

    @SystemApi
    public Uri getSmscIdentity() {
        IPhoneSubInfo info;
        Uri smscUri = Uri.EMPTY;
        try {
            info = TelephonyManager.getSubscriberInfoService();
        } catch (RemoteException ex) {
            com.android.telephony.Rlog.e(TAG, "getSmscIdentity(): Exception : " + ex);
            ex.rethrowAsRuntimeException();
        }
        if (info == null) {
            com.android.telephony.Rlog.e(TAG, "getSmscIdentity(): IPhoneSubInfo instance is NULL");
            throw new IllegalStateException("Telephony service is not available");
        }
        smscUri = info.getSmscIdentity(getSubscriptionId(), 5);
        if (Uri.EMPTY.equals(smscUri)) {
            smscUri = info.getSmscIdentity(getSubscriptionId(), 2);
        }
        return smscUri;
    }

    public long getWapMessageSize(String locationUrl) {
        try {
            ISms iSms = getISmsService();
            if (iSms != null) {
                return iSms.getWapMessageSize(locationUrl);
            }
            throw new RuntimeException("Could not acquire ISms service.");
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
    }

    public SemCbConfig semGetCbSettings() {
        ISms iccISms;
        com.android.telephony.Rlog.d(TAG, "[CB] In getCbConfig");
        byte[] out = null;
        SemCbConfig cbConfig = new SemCbConfig();
        if (TelephonyFeatures.IS_QCOM) {
            cbConfig.msgIdMaxCount = 1000;
            return cbConfig;
        }
        try {
            iccISms = getISmsService();
        } catch (RemoteException e) {
            com.android.telephony.Rlog.d(TAG, "[CB] Exception In getCbConfig of SmsManager");
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
            com.android.telephony.Rlog.d(TAG, "[CB] IllegalArgumentException Exception In getCbConfig of SmsManager");
        } catch (NullPointerException e2) {
        }
        if (iccISms == null) {
            return null;
        }
        int subId = getSubscriptionId();
        com.android.telephony.Rlog.d(TAG, "getCbSettings subId: " + subId);
        if (subId >= 0) {
            out = iccISms.getCbSettingsForSubscriber(subId);
            if (out == null) {
                return null;
            }
            if (out[0] == 1) {
                cbConfig.bCBEnabled = true;
            } else {
                cbConfig.bCBEnabled = false;
            }
            cbConfig.selectedId = out[1];
            cbConfig.msgIdMaxCount = 1000;
            cbConfig.msgIdCount = out[3];
            int[] msgIds = new int[cbConfig.msgIdCount];
            int i = 4;
            for (int j = 0; j < msgIds.length; j++) {
                try {
                    msgIds[j] = (short) ((out[i] & 255) | ((out[i + 1] & 255) << 8));
                    i += 2;
                } catch (ArrayIndexOutOfBoundsException e3) {
                    com.android.telephony.Rlog.d(TAG, "[CB ] ArrayIndexOutOfBoundsException In getCbConfig of SmsManager.java");
                    return null;
                }
            }
            cbConfig.msgIds = msgIds;
            com.android.telephony.Rlog.d(TAG, "[SmsManger- CB] bCBEnabled = " + cbConfig.bCBEnabled + " selectedId = " + cbConfig.selectedId + " msgIdMaxCount = " + cbConfig.msgIdMaxCount + " msgIdCount = " + cbConfig.msgIdCount);
            for (int i2 = 0; i2 < cbConfig.msgIds.length; i2++) {
                com.android.telephony.Rlog.d(TAG, "[CB] msgIDs =  " + cbConfig.msgIds[i2]);
            }
            return cbConfig;
        }
        com.android.telephony.Rlog.e(TAG, "getCbSettings invalid subID : " + subId);
        return null;
    }

    public void semSendMultipartTextMessage(String destinationAddress, String scAddress, ArrayList<String> parts, ArrayList<PendingIntent> sentIntents, ArrayList<PendingIntent> deliveryIntents, boolean replyPath, int expiry, int serviceType, int encodingType) {
        com.android.telephony.Rlog.i(TAG, "semSendMultipartTextMessage with encodiing type");
        if (TextUtils.isEmpty(destinationAddress)) {
            throw new IllegalArgumentException("Invalid destinationAddress");
        }
        if (!getMnoName().toUpperCase().contains("DOCOMO") || destinationAddress.length() < 256) {
            if (parts == null) {
                throw new IllegalArgumentException("Invalid message body");
            }
            if (parts.size() < 1 && !isSupportSendingEmptySms()) {
                throw new IllegalArgumentException("Invalid message body");
            }
            if (parts.size() > 1) {
                try {
                    ISms iccISms = getISmsServiceOrThrow();
                    iccISms.sendMultipartTextwithOptionsForSubscriber(getSubscriptionId(), ActivityThread.currentPackageName(), destinationAddress, scAddress, parts, sentIntents, deliveryIntents, replyPath, expiry, serviceType, encodingType);
                    return;
                } catch (RemoteException e) {
                    return;
                }
            }
            PendingIntent sentIntent = null;
            PendingIntent deliveryIntent = null;
            if (sentIntents != null && sentIntents.size() > 0) {
                sentIntent = sentIntents.get(0);
            }
            if (deliveryIntents != null && deliveryIntents.size() > 0) {
                deliveryIntent = deliveryIntents.get(0);
            }
            sendTextMessage(destinationAddress, scAddress, parts.get(0), sentIntent, deliveryIntent, replyPath, expiry, serviceType, encodingType);
            return;
        }
        throw new IllegalArgumentException("Invalid destinationAddress");
    }

    public void semSendMultipartTextMessage(String destinationAddress, String scAddress, ArrayList<String> parts, ArrayList<PendingIntent> sentIntents, ArrayList<PendingIntent> deliveryIntents, String callbackNumber, int priority) {
        com.android.telephony.Rlog.i(TAG, "semSendMultipartTextMessage with priority");
        if (TextUtils.isEmpty(destinationAddress)) {
            throw new IllegalArgumentException("Invalid destinationAddress");
        }
        if (getMnoName().toUpperCase().contains("DOCOMO") && destinationAddress.length() >= 256) {
            throw new IllegalArgumentException("Invalid destinationAddress");
        }
        if (parts == null) {
            throw new IllegalArgumentException("Invalid parts");
        }
        if (parts.size() < 1 && !isSupportSendingEmptySms()) {
            throw new IllegalArgumentException("Invalid message body");
        }
        if (parts.size() > 1) {
            try {
                ISms iccISms = getISmsServiceOrThrow();
                if (iccISms != null) {
                    iccISms.sendMultipartTextwithCBPForSubscriber(getSubscriptionId(), ActivityThread.currentPackageName(), destinationAddress, scAddress, parts, sentIntents, deliveryIntents, callbackNumber, priority);
                    return;
                }
                return;
            } catch (RemoteException e) {
                return;
            }
        }
        PendingIntent sentIntent = null;
        PendingIntent deliveryIntent = null;
        if (sentIntents != null && sentIntents.size() > 0) {
            sentIntent = sentIntents.get(0);
        }
        if (deliveryIntents != null && deliveryIntents.size() > 0) {
            deliveryIntent = deliveryIntents.get(0);
        }
        sendTextMessage(destinationAddress, scAddress, parts.get(0), sentIntent, deliveryIntent, callbackNumber, priority);
    }

    public boolean semGetSMSPAvailable() {
        com.android.telephony.Rlog.d(TAG, "getSMSPAvailable in SmsManager");
        if (TelephonyManager.getDefault().getSimState() != 5) {
            return false;
        }
        try {
            ISms iccISms = getISmsService();
            if (iccISms == null) {
                return false;
            }
            boolean ret = iccISms.getSMSPAvailableForSubscriber(getSubscriptionId());
            return ret;
        } catch (RemoteException e) {
            com.android.telephony.Rlog.d(TAG, "Exception In getSMSPAvailable() of SmsManager.java");
            return false;
        }
    }

    public ArrayList<String> semDivideMessage(String text, int encodingType) {
        if (text == null) {
            throw new IllegalArgumentException("text is null");
        }
        return SmsMessage.fragmentText(text, encodingType, this);
    }

    public void semSendMultipartTextMessage(String destinationAddress, String scAddress, ArrayList<String> parts, ArrayList<PendingIntent> sentIntents, ArrayList<PendingIntent> deliveryIntents, boolean replyPath, int expiry, int serviceType, int encodingType, int confirmId) {
        com.android.telephony.Rlog.i(TAG, "semSendMultipartTextMessage with confirmId");
        if (TextUtils.isEmpty(destinationAddress)) {
            throw new IllegalArgumentException("Invalid destinationAddress");
        }
        if (!getMnoName().toUpperCase().contains("DOCOMO") || destinationAddress.length() < 256) {
            if (parts == null) {
                throw new IllegalArgumentException("Invalid message body");
            }
            if (parts.size() < 1 && !isSupportSendingEmptySms()) {
                throw new IllegalArgumentException("Invalid message body");
            }
            if (parts.size() > 1) {
                try {
                    ISms iccISms = getISmsServiceOrThrow();
                    iccISms.sendMultipartTextwithOptionsForSubscriber(getSubscriptionId(), ActivityThread.currentPackageName(), destinationAddress, scAddress, parts, sentIntents, deliveryIntents, replyPath, expiry, serviceType, encodingType);
                    return;
                } catch (RemoteException e) {
                    return;
                }
            }
            PendingIntent sentIntent = null;
            PendingIntent deliveryIntent = null;
            if (sentIntents != null && sentIntents.size() > 0) {
                sentIntent = sentIntents.get(0);
            }
            if (deliveryIntents != null && deliveryIntents.size() > 0) {
                deliveryIntent = deliveryIntents.get(0);
            }
            sendTextMessage(destinationAddress, scAddress, parts.get(0), sentIntent, deliveryIntent, replyPath, expiry, serviceType, encodingType, confirmId);
            return;
        }
        throw new IllegalArgumentException("Invalid destinationAddress");
    }

    public void sendTextMessage(String destinationAddress, String scAddress, String text, PendingIntent sentIntent, PendingIntent deliveryIntent, boolean replyPath, int expiry, int serviceType, int encodingType) {
        com.android.telephony.Rlog.i(TAG, "sendTextMessage with encoding Type: mno - " + getMnoName());
        if (TextUtils.isEmpty(destinationAddress)) {
            throw new IllegalArgumentException("Invalid destinationAddress");
        }
        if (getMnoName().toUpperCase().contains("DOCOMO") && destinationAddress.length() >= 256) {
            throw new IllegalArgumentException("Invalid destinationAddress");
        }
        if (TextUtils.isEmpty(text) && !isSupportSendingEmptySms()) {
            throw new IllegalArgumentException("Invalid message body");
        }
        try {
            ISms iccISms = getISmsServiceOrThrow();
            iccISms.sendTextwithOptionsForSubscriber(getSubscriptionId(), ActivityThread.currentPackageName(), destinationAddress, scAddress, text, sentIntent, deliveryIntent, replyPath, expiry, serviceType, encodingType);
        } catch (RemoteException e) {
        }
    }

    public void sendTextMessage(String destinationAddress, String scAddress, String text, PendingIntent sentIntent, PendingIntent deliveryIntent, String callbackNumber, int priority) {
        com.android.telephony.Rlog.i(TAG, "sendTextMessage with callbacknmber and priority");
        if (TextUtils.isEmpty(destinationAddress)) {
            throw new IllegalArgumentException("Invalid destinationAddress");
        }
        if (getMnoName().toUpperCase().contains("DOCOMO") && destinationAddress.length() >= 256) {
            throw new IllegalArgumentException("Invalid destinationAddress");
        }
        try {
            ISms iccISms = getISmsServiceOrThrow();
            if (iccISms != null) {
                iccISms.sendTextwithCBPForSubscriber(getSubscriptionId(), ActivityThread.currentPackageName(), destinationAddress, scAddress, text, sentIntent, deliveryIntent, callbackNumber, priority);
            }
        } catch (RemoteException e) {
        }
    }

    public void sendTextMessage(String destinationAddress, String scAddress, String text, PendingIntent sentIntent, PendingIntent deliveryIntent, boolean replyPath, int expiry, int serviceType, int encodingType, int confirmId) {
        com.android.telephony.Rlog.i(TAG, "sendTextMessage with confirmId");
        if (TextUtils.isEmpty(destinationAddress)) {
            throw new IllegalArgumentException("Invalid destinationAddress");
        }
        if (getMnoName().toUpperCase().contains("DOCOMO") && destinationAddress.length() >= 256) {
            throw new IllegalArgumentException("Invalid destinationAddress");
        }
        if (TextUtils.isEmpty(text) && !isSupportSendingEmptySms()) {
            throw new IllegalArgumentException("Invalid message body");
        }
        try {
            ISms iccISms = getISmsServiceOrThrow();
            iccISms.sendTextwithOptionsReadconfirmForSubscriber(getSubscriptionId(), ActivityThread.currentPackageName(), destinationAddress, scAddress, text, sentIntent, deliveryIntent, replyPath, expiry, serviceType, encodingType, confirmId);
        } catch (RemoteException e) {
        }
    }

    public boolean semEnableCellBroadcastRange(int startMessageId, int endMessageId) {
        return enableCellBroadcastRange(startMessageId, endMessageId, 1);
    }

    public boolean semDisableCellBroadcastRange(int startMessageId, int endMessageId) {
        return disableCellBroadcastRange(startMessageId, endMessageId, 1);
    }

    public String getMnoName() {
        try {
            ISms iSms = getISmsService();
            if (iSms == null) {
                return "default";
            }
            String value = iSms.getMnoNameForSubscriber(getSubscriptionId());
            return value;
        } catch (RemoteException e) {
            return "default";
        }
    }

    public boolean getSmsSetting(String settingName) {
        try {
            ISms iSms = getISmsService();
            if (iSms == null) {
                return false;
            }
            boolean value = iSms.getSmsSettingForSubscriber(getSubscriptionId(), settingName);
            return value;
        } catch (RemoteException e) {
            return false;
        }
    }

    private int getMessageStatusForIcc(int messageType, boolean isRead) {
        if (messageType == 2) {
            return 5;
        }
        if (messageType == 4) {
            return 7;
        }
        if (isRead) {
            return 1;
        }
        return 3;
    }

    public ArrayList<SmsMessage> getAllMessagesFromIccSimType(int iccType) {
        int iccType2;
        List<SmsRawData> records = null;
        String format = "3gpp";
        String getFormat = getCurrentFormat();
        int subId = getSubscriptionId();
        int phoneId = SubscriptionManager.getPhoneId(subId);
        if (iccType != -1 && iccType != 0) {
            iccType2 = iccType;
        } else {
            iccType2 = IccUtils.getIccType(phoneId);
        }
        if (iccType2 == 0) {
            com.android.telephony.Rlog.d(TAG, "IccType is Unknown");
            return createMessageListFromRawRecords(null, "3gpp");
        }
        try {
            try {
                ISms iccISms = getISmsService();
                if (iccISms != null) {
                    TelephonyManager.getDefault();
                    boolean isroaming = Boolean.parseBoolean(TelephonyManager.getTelephonyProperty(phoneId, TelephonyProperties.PROPERTY_OPERATOR_ISROAMING, "false"));
                    boolean isCHN = TelephonyFeatures.isCountrySpecific(phoneId, "CHN", "HKG", "TPE");
                    String voiceTypeKey = phoneId == 0 ? "voicecall_type" : "voicecall_type2";
                    Context context = ActivityThread.currentApplication().getApplicationContext();
                    boolean isCtcVolteOn = Settings.System.getInt(context.getContentResolver(), voiceTypeKey, -1) == 0;
                    if ((iccType2 == 10 && !isroaming && !isCtcVolteOn) || (iccType2 == 4 && ((isroaming || isCtcVolteOn) && isCHN))) {
                        TelephonyManager.isSelecttelecomDF = true;
                    }
                    records = iccISms.getAllMessagesFromIccEfForSubscriber(getSubscriptionId(), null);
                }
            } catch (RemoteException e) {
                com.android.telephony.Rlog.d(TAG, "getAllMessagesFromIccSimType - exception - iccType:" + iccType2);
            }
            if (iccType2 == 4) {
                format = getFormat;
            } else if (iccType2 == 10) {
                if ("3gpp".equals(getFormat)) {
                    format = "3gpp2";
                }
            } else if (iccType2 == 3) {
                format = "3gpp2";
            }
            com.android.telephony.Rlog.d(TAG, "getAllMessagesFromIccSimType, subId = " + subId + " format = " + format + " iccType = " + iccType2);
            return createMessageListFromRawRecords(records, format);
        } finally {
            TelephonyManager.isSelecttelecomDF = false;
        }
    }

    private static ArrayList<SmsMessage> createMessageListFromRawRecords(List<SmsRawData> records, String format) {
        ArrayList<SmsMessage> messages = new ArrayList<>();
        if (records != null) {
            int count = records.size();
            for (int i = 0; i < count; i++) {
                SmsRawData data = records.get(i);
                if (data != null) {
                    SmsMessage sms = SmsMessage.createFromEfRecord(i + 1, data.getBytes(), format);
                    messages.add(sms);
                    if (sms == null) {
                        Log.d(TAG, "createFromEfRecord NULL:" + format + "index:" + i);
                    }
                } else {
                    messages.add(null);
                }
            }
        }
        return messages;
    }

    public void sendOTADomestic(String destinationAddress, String scAddress, String text) {
        com.android.telephony.Rlog.i(TAG, "sendOTADomestic");
        if (TextUtils.isEmpty(destinationAddress)) {
            throw new IllegalArgumentException("Invalid destinationAddress");
        }
        if (getMnoName().toUpperCase().contains("DOCOMO") && destinationAddress.length() >= 256) {
            throw new IllegalArgumentException("Invalid destinationAddress");
        }
        if (TextUtils.isEmpty(text)) {
            throw new IllegalArgumentException("Invalid message body");
        }
        try {
            ISms iccISms = getISmsServiceOrThrow();
            iccISms.sendOTADomesticForSubscriber(getSubscriptionId(), ActivityThread.currentPackageName(), destinationAddress, scAddress, text);
        } catch (RemoteException e) {
        }
    }

    public void sendDataMessage(String destinationAddress, String scAddress, short destinationPort, short originationPort, byte[] data, PendingIntent sentIntent, PendingIntent deliveryIntent) {
        com.android.telephony.Rlog.i(TAG, "sendDataMessage");
        if (TextUtils.isEmpty(destinationAddress)) {
            throw new IllegalArgumentException("Invalid destinationAddress");
        }
        if (getMnoName().toUpperCase().contains("DOCOMO") && destinationAddress.length() >= 256) {
            throw new IllegalArgumentException("Invalid destinationAddress");
        }
        if (data == null || data.length == 0) {
            throw new IllegalArgumentException("Invalid message data");
        }
        try {
            ISms iccISms = getISmsServiceOrThrow();
            iccISms.sendDatawithOrigPortForSubscriber(getSubscriptionId(), null, destinationAddress, scAddress, destinationPort & 65535, originationPort & 65535, data, sentIntent, deliveryIntent);
        } catch (RemoteException e) {
        }
    }

    public void sendTextMessageNSRI(String destinationAddress, String scAddress, byte[] data, PendingIntent sentIntent, PendingIntent deliveryIntent, String from, int msgCount, int msgTotal) {
        com.android.telephony.Rlog.i(TAG, "sendTextMessageNSRI");
        if (TextUtils.isEmpty(destinationAddress)) {
            throw new IllegalArgumentException("Invalid destinationAddress");
        }
        Log.d(TAG, "[NSRI_SMS] sendTextMessageNSRI Addr = " + destinationAddress + " Smsc = " + scAddress + " textLen = " + data.length + " from = " + from + " msgCount = " + msgCount + " msgTotal = " + msgTotal);
        try {
            ISms iccISms = getISmsService();
            if (iccISms != null) {
                iccISms.sendTextNSRIForSubscriber(getSubscriptionId(), ActivityThread.currentPackageName(), destinationAddress, scAddress, data, sentIntent, deliveryIntent, msgCount, msgTotal);
            }
        } catch (RemoteException e) {
        }
    }

    public void sendTextMessageAutoLogin(String destinationAddress, String scAddress, String text, PendingIntent sentIntent, PendingIntent deliveryIntent) {
        com.android.telephony.Rlog.i(TAG, "sendTextMessageAutoLogin");
        if (TextUtils.isEmpty(destinationAddress)) {
            throw new IllegalArgumentException("Invalid destinationAddress");
        }
        if (getMnoName().toUpperCase().contains("DOCOMO") && destinationAddress.length() >= 256) {
            throw new IllegalArgumentException("Invalid destinationAddress");
        }
        if (TextUtils.isEmpty(text) && !isSupportSendingEmptySms()) {
            throw new IllegalArgumentException("Invalid message body");
        }
        try {
            ISms iccISms = getISmsServiceOrThrow();
            iccISms.sendTextAutoLoginForSubscriber(getSubscriptionId(), ActivityThread.currentPackageName(), destinationAddress, scAddress, text, sentIntent, deliveryIntent, false);
        } catch (RemoteException e) {
        }
    }

    public String getCurrentFormat() {
        int subId = getSubscriptionId();
        int phoneId = SubscriptionManager.getPhoneId(subId);
        TelephonyManager.getDefault();
        String mode = TelephonyManager.getTelephonyProperty(phoneId, TelephonyProperties.CURRENT_ACTIVE_PHONE, String.valueOf(1));
        com.android.telephony.Rlog.d(TAG, "getCurrentFormat, subId = " + subId + " mode = " + mode);
        switch (Integer.parseInt(mode)) {
            case 1:
                return "3gpp";
            case 2:
                return "3gpp2";
            default:
                return "3gpp";
        }
    }

    private boolean isSupportSendingEmptySms() {
        final String matchedCode = SystemProperties.get("mdc.matched_code", SystemProperties.get("ro.csc.sales_code", ""));
        if (TextUtils.isEmpty(matchedCode)) {
            return false;
        }
        String[] supportCode = {"BST", "TEL", "TLP"};
        return Arrays.stream(supportCode).anyMatch(new Predicate() { // from class: android.telephony.SmsManager$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean equals;
                equals = ((String) obj).equals(matchedCode);
                return equals;
            }
        });
    }
}
