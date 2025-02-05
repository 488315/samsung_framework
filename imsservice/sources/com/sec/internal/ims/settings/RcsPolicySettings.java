package com.sec.internal.ims.settings;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.sec.imsservice.R;
import com.sec.internal.helper.JsonUtil;
import com.sec.internal.helper.SimUtil;
import com.sec.internal.ims.servicemodules.im.strategy.RcsPolicyType;
import com.sec.internal.ims.servicemodules.im.strategy.RcsPolicyTypeFactory;
import com.sec.internal.log.IMSLog;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class RcsPolicySettings {
    private static final String LOG_TAG = "RcsPolicySettings";
    private static final String RCS_AS_POLICY = "rcs_as_policy";
    private Context mContext;
    private int mPhoneId;
    private JsonElement mRcsPolicy = JsonNull.INSTANCE;

    public static class RcsPolicy {
        public static final String ALLOW_LIST_CAPEX = "allow_list_capex";
        public static final String ALLOW_ONLY_OPENGROUPCHAT = "allow_only_opengroupchat";
        public static final String AUTH_BASED_SESSION_CONTROL = "auth_based_session_control";
        public static final String AUTO_ACCEPT_GLS = "auto_accept_gls";
        public static final String AUTO_ACCEPT_RRAM = "auto_accept_rram";
        public static final String AUTO_RESEND_FAILED_FT = "auto_resend_failed_ft";
        public static final String BLOCK_CHATBOT_NW = "block_chatbot_nw";
        public static final String BLOCK_FT_AUTO_DOWNLOAD_FOR_GC = "block_ft_auto_download_for_gc";
        public static final String BLOCK_MSG = "block_msg";
        public static final String CANCEL_FOR_DEREGI_PROMPTLY = "cancel_for_deregi_promptly";
        public static final String CANCEL_FT_WIFI_DISCONNECTED = "cancel_ft_wifi_disconnected";
        public static final String CAPA_SKIP_NOTIFY_FORCE_REFRESH_SYNC = "capa_skip_notify_force_refresh_sync";
        public static final String CENTRAL_MSG_STORE = "central_msg_store";
        public static final String CHECK_BYECAUSE = "check_byecause";
        public static final String CHECK_GONEMEMBERS_OF_FULLNOTIFY = "check_gonemembers_of_fullnotify";
        public static final String CHECK_IMSIBASED_REGI = "check_imsibased_regi";
        public static final String CHECK_INITIATOR_SESSIONURI = "check_initiator_sessionuri";
        public static final String CHECK_MSGAPP_IMSESSION_REJECT = "check_msgapp_imsession_reject";
        public static final String CHECK_PARTICIPANT_OF_PARTIAL_STATE = "check_participant_of_partial_state";
        public static final String CHECK_PRESENCE_RULES = "check_presence_rules";
        public static final String CHECK_P_ASSERTED_IDENTITY = "check_p_asserted_identity";
        public static final String COMPOSING_NOTIFICATION_IDLE_INTERVAL = "compsing_noti_idle_interval";
        public static final String CONFINFO_UPDATE_NOT_ALLOWED = "confinfo_update_not_allowed";
        public static final String CONTENTLENGTH_IN_BYTE = "contentlength_in_byte";
        public static final String DELAY_TO_CANCEL_FOR_DEREGI = "delay_to_cancel_for_deregi";
        public static final String DELAY_TO_DEREGI_FOR_A2P_SESSION = "delay_to_deregi_for_a2p_session";
        public static final String DISPLAY_FT_IN_GALLERY = "display_ft_in_gallery";
        public static final String DISPLAY_INVITED_SYSTEMMESSAGE = "display_invited_systemmessage";
        public static final String DUAL_SIMHANDLING = "dual_simhandling";
        public static final String FILE_NAME_LENGTH_LIMIT_IN_SERVER = "file_name_length_limit_In_server";
        public static final String FIRSTMSG_GROUPCHAT_INVITE = "firstmsg_groupchat_invite";
        public static final String FORCE_AUTO_ACCEPT = "force_auto_accept";
        public static final String FTHTTP_FORCE_AUTO_ACCEPT_ON_WIFI = "fthttp_force_auto_accept_on_wifi";
        public static final String FTHTTP_IGNORE_WHEN_UNTRUSTED_URL = "fthttp_ignore_when_untrusted_url";
        public static final String FTHTTP_NON_STANDARD_URLS = "fthttp_non_standard_urls";
        public static final String FTHTTP_SINGLE_THREAD = "fthttp_single_thread";
        public static final String FTHTTP_UPLOAD_RESUME_FROM_THE_START = "fthttp_upload_resume_from_the_start";
        public static final String FT_FALLBACK_DIRECTLY_OFFLINE = "ft_fallback_directly_offline";
        public static final String FT_INTERNET_PDN = "ft_internet_pdn";
        public static final String FT_NET_CAPABILITY = "ft_net_capability";
        public static final String FT_WITH_GBA = "ft_with_gba";
        public static final String GONE_SHOULD_ENDSESSION = "gone_should_endsession";
        public static final String GROUPCHAT_AUTO_REJOIN = "groupchat_auto_rejoin";
        public static final String GROUPCHAT_INVITATIONUI_USED = "groupchat_invitationui_used";
        public static final String HANDLE_LEAVE_OGC_FAILURE = "handle_leave_ogc_failure";
        public static final String IGNORE_STATE_TO_FIND_ABSENT_PARTICIPANT = "ignore_state_to_find_absent_participant";
        public static final String IGNORE_WIFI_WARNSIZE = "ignore_wifi_warnsize";
        public static final String IS_EAP_SUPPORTED = "is_eap_supported";
        public static final String LIST_SUB_URI_TRANSLATION = "list_sub_uri_translation";
        public static final String MAX_SIPINVITE_ATONCE = "max_sipinvite_atonce";
        public static final String NOTIFY_RCS_MSG = "notify_rcs_msg";
        public static final String NUM_OF_DISPLAY_NOTIFICATION_ATONCE = "num_of_display_notification_atonce";
        public static final String ONEKEY_REPORT_PSI = "onekey_report_psi";
        public static final String PARTICIPANTBASED_CLOSED_GROUPCHAT = "participantbased_closed_groupchat";
        public static final String PENDING_FOR_REGI = "pending_for_regi";
        public static final String POLL_ALLOWED = "poll_allowed";
        public static final String PS_ONLY_NETWORK = "ps_only_network";
        public static final String REMOVE_FAILED_PARTICIPANT_GROUPCHAT = "remove_failed_participant_groupchat";
        public static final String REPLACE_SPECIALCHARACTER = "replace_specialcharacter";
        public static final String RESUME_WITH_COMPLETE_FILE = "resume_with_complete_file";
        public static final String SENDMSG_RESP_TIMEOUT = "sendmsg_resp_timeout";
        public static final String SESSION_ESTABLISH_TIMER = "session_establish_timer";
        public static final String SKIP_BLOCK_CHATBOT_MSG = "skip_block_chatbot_msg";
        public static final String START_SESSION_WHEN_CREATE_GROUPCHAT = "start_session_when_create_groupchat";
        public static final String SUPPORT_7DIGIT_MSG = "support_7digit_msg";
        public static final String SUPPORT_CHAT_CLOSE_BY_SERVER = "support_chat_close_by_server";
        public static final String SUPPORT_ENCODING_FILENAME_BY_SERVER = "support_encoding_filename_by_server";
        public static final String SUPPORT_FTHTTP_CONTENTLENGTH = "support_fthttp_contentlength";
        public static final String SUPPORT_HIGHRESOLUTIONVIDEO_THUMBNAIL = "support_highresolutionvideo_thumbnail";
        public static final String SUPPORT_LARGE_MSG_RESIZING = "support_large_msg_resizing";
        public static final String SUPPORT_OFFLINE_GC_INVITATION = "support_offline_gc_invitation";
        public static final String SUPPORT_QUICKFT = "support_quickft";
        public static final String SUPPORT_REVOKE_MSG_FOR_486_RESP = "support_revoke_msg_for_486_resp";
        public static final String SUPPORT_SENDMSG_RESP_TIMEOUT = "support_sendmsg_resp_timeout";
        public static final String TRIGGER_CAPEX_WHEN_STARTTYPING = "trigger_capex_when_starttyping";
        public static final String TRIGGER_INVITE_AFTER_18X = "trigger_invite_after_18x";
        public static final String UPDATE_ACSREADY_NETWORK = "update_acsready_network";
        public static final String UPDATE_SESSION_AFTER_REGISTRATION = "update_session_after_registration";
        public static final String USERAGENT_HAS_MSGAPPVERSION = "useragent_has_msgappversion";
        public static final String USE_AGGREGATION_DISPLAYED_IMDN = "use_aggregation_displayed_imdn";
        public static final String USE_AVAILABLE_FEATURES_FOR_CHATBOT = "use_available_features_for_chatbot";
        public static final String USE_CAPCACHE_EXPIRY = "use_capcache_expiry";
        public static final String USE_CHATBOT_MANUALACCEPT = "use_chatbot_manualaccept";
        public static final String USE_INDIVIDUAL_REFER = "use_individual_refer";
        public static final String USE_MSRP = "use_msrp";
        public static final String USE_PROVISIONAL_RESPONSE_ASSENT = "use_provisional_response_assent";
        public static final String USE_RAND_DELAY_PERIODIC_POLL = "use_rand_delay_periodic_poll";
        public static final String USE_SIPURI_FOR_URIGENERATOR = "use_sipuri_for_urigenerator";
        public static final String USE_TEMPFILE_WHEN_DOWNLOAD = "use_tempfile_when_download";
        public static final String USE_USERIDENTITY_FOR_FTHTTP = "use_useridentity_for_fthttp";
        public static final String WAIT_DEACTVAING_DELETE_CHAT = "wait_deactvaing_delete_chat";
    }

    public RcsPolicySettings(Context context, int i) {
        this.mContext = null;
        this.mContext = context;
        this.mPhoneId = i;
        load(false);
    }

    public boolean load(boolean z) {
        RcsPolicyType policyType = RcsPolicyTypeFactory.getPolicyType(SimUtil.getSimMno(this.mPhoneId), this.mPhoneId, this.mContext);
        String typeName = policyType.getTypeName();
        String str = "";
        if (TextUtils.equals(this.mRcsPolicy.isJsonNull() ? "" : this.mRcsPolicy.getAsJsonObject().get(ImsAutoUpdate.TAG_POLICY_NAME).getAsString(), typeName) && !z) {
            IMSLog.i(LOG_TAG, this.mPhoneId, "policy not changed skip reloading");
            return false;
        }
        String str2 = LOG_TAG;
        IMSLog.i(str2, this.mPhoneId, "load from rcspolicy.json " + typeName);
        try {
            InputStream openRawResource = this.mContext.getResources().openRawResource(R.raw.rcspolicy);
            try {
                JsonParser jsonParser = new JsonParser();
                JsonReader jsonReader = new JsonReader(new BufferedReader(new InputStreamReader(openRawResource)));
                JsonElement parse = jsonParser.parse(jsonReader);
                jsonReader.close();
                if (openRawResource != null) {
                    openRawResource.close();
                }
                JsonObject asJsonObject = parse.getAsJsonObject();
                String str3 = ImsAutoUpdate.TAG_DEFAULT_RCS_POLICY;
                JsonElement jsonElement = asJsonObject.get(ImsAutoUpdate.TAG_DEFAULT_RCS_POLICY);
                JsonElement jsonElement2 = asJsonObject.get(ImsAutoUpdate.TAG_DEFAULT_UP_POLICY);
                if (jsonElement.isJsonNull() || jsonElement2.isJsonNull()) {
                    IMSLog.i(str2, this.mPhoneId, "load: No default_rcs_policy or default_up_policy. load failed");
                    return false;
                }
                if (RcsPolicyType.fromString(typeName).isUp()) {
                    str3 = ImsAutoUpdate.TAG_DEFAULT_UP_POLICY;
                    jsonElement = jsonElement2;
                }
                JsonArray asJsonArray = asJsonObject.getAsJsonArray(ImsAutoUpdate.TAG_RCS_POLICY);
                JsonElement jsonElement3 = JsonNull.INSTANCE;
                Iterator it = asJsonArray.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    JsonElement jsonElement4 = (JsonElement) it.next();
                    if (TextUtils.equals(typeName, jsonElement4.getAsJsonObject().get(ImsAutoUpdate.TAG_POLICY_NAME).getAsString())) {
                        jsonElement3 = jsonElement4;
                        break;
                    }
                }
                JsonElement jsonElement5 = JsonNull.INSTANCE;
                if (jsonElement3 == jsonElement5) {
                    IMSLog.i(LOG_TAG, this.mPhoneId, "No policy for " + typeName + " in rcspolicy.json");
                } else if (jsonElement3.getAsJsonObject().has(RCS_AS_POLICY)) {
                    str = jsonElement3.getAsJsonObject().get(RCS_AS_POLICY).getAsString();
                    IMSLog.i(LOG_TAG, this.mPhoneId, "use RCS AS policy " + str);
                    Iterator it2 = asJsonArray.iterator();
                    while (true) {
                        if (!it2.hasNext()) {
                            break;
                        }
                        JsonElement jsonElement6 = (JsonElement) it2.next();
                        if (TextUtils.equals(str, jsonElement6.getAsJsonObject().get(ImsAutoUpdate.TAG_POLICY_NAME).getAsString())) {
                            jsonElement5 = jsonElement6;
                            break;
                        }
                    }
                }
                if (jsonElement5 == JsonNull.INSTANCE) {
                    IMSLog.i(LOG_TAG, this.mPhoneId, "No rcsAsPolicy in rcspolicy.json");
                }
                ImsAutoUpdate imsAutoUpdate = ImsAutoUpdate.getInstance(this.mContext, this.mPhoneId);
                JsonElement rcsDefaultPolicyUpdate = imsAutoUpdate.getRcsDefaultPolicyUpdate(jsonElement, policyType.isUp());
                JsonElement rcsPolicyUpdate = imsAutoUpdate.getRcsPolicyUpdate(jsonElement5, str);
                JsonElement rcsPolicyUpdate2 = imsAutoUpdate.getRcsPolicyUpdate(jsonElement3, typeName);
                if (JsonUtil.isValidJsonElement(rcsPolicyUpdate2)) {
                    IMSLog.i(LOG_TAG, this.mPhoneId, "policy updated: " + typeName);
                    if (JsonUtil.isValidJsonElement(rcsPolicyUpdate)) {
                        rcsPolicyUpdate2 = JsonUtil.merge(rcsPolicyUpdate, rcsPolicyUpdate2);
                    }
                    this.mRcsPolicy = JsonUtil.merge(rcsDefaultPolicyUpdate, rcsPolicyUpdate2);
                    return true;
                }
                IMSLog.i(LOG_TAG, this.mPhoneId, "policy not valid " + typeName + " use updated default policy " + str3);
                this.mRcsPolicy = rcsDefaultPolicyUpdate;
                return true;
            } finally {
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean readBool(String str) {
        JsonObject asJsonObject = this.mRcsPolicy.getAsJsonObject();
        if (asJsonObject.has(str)) {
            boolean asBoolean = asJsonObject.get(str).getAsBoolean();
            Log.d(LOG_TAG, "readBool:" + str + " : " + asBoolean);
            return asBoolean;
        }
        Log.d(LOG_TAG, "readBool: " + str + " not exist");
        return false;
    }

    public int readInt(String str) {
        JsonObject asJsonObject = this.mRcsPolicy.getAsJsonObject();
        if (asJsonObject.has(str)) {
            int asInt = asJsonObject.get(str).getAsInt();
            Log.d(LOG_TAG, "readInt:" + str + " : " + asInt);
            return asInt;
        }
        Log.d(LOG_TAG, "readInt: " + str + " not exist");
        return 0;
    }

    public String readString(String str) {
        JsonObject asJsonObject = this.mRcsPolicy.getAsJsonObject();
        if (asJsonObject.has(str)) {
            String asString = asJsonObject.get(str).getAsString();
            Log.d(LOG_TAG, "readString:" + str + " : " + asString);
            return asString;
        }
        Log.d(LOG_TAG, "readString: " + str + " not exist");
        return "";
    }

    public List<String> readStringArray(String str) {
        ArrayList arrayList = new ArrayList();
        JsonObject asJsonObject = this.mRcsPolicy.getAsJsonObject();
        if (asJsonObject.has(str)) {
            Log.d(LOG_TAG, "readStringArray: " + str + " exists");
            Iterator it = asJsonObject.get(str).getAsJsonArray().iterator();
            while (it.hasNext()) {
                arrayList.add(((JsonElement) it.next()).getAsString());
            }
        }
        Log.d(LOG_TAG, "readStringArray: " + str + " : " + arrayList);
        return arrayList;
    }
}
