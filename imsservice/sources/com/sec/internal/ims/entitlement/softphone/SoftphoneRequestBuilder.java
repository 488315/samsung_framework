package com.sec.internal.ims.entitlement.softphone;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import com.sec.internal.constants.ims.XmlCreator;
import com.sec.internal.constants.ims.XmlElement;
import com.sec.internal.constants.ims.cmstore.CloudMessageProviderContract;
import com.sec.internal.constants.ims.config.ConfigConstants;
import com.sec.internal.constants.ims.entitilement.SoftphoneContract;
import com.sec.internal.constants.ims.entitilement.SoftphoneNamespaces;
import com.sec.internal.constants.ims.entitilement.softphone.requests.AddAddressRequest;
import com.sec.internal.constants.ims.entitilement.softphone.requests.AddressValidationRequest;
import com.sec.internal.constants.ims.entitilement.softphone.requests.ExchangeForAccessTokenRequest;
import com.sec.internal.constants.ims.entitilement.softphone.requests.ProvisionAccountRequest;
import com.sec.internal.constants.ims.entitilement.softphone.requests.ReleaseImsNetworkIdentifiersRequest;
import com.sec.internal.constants.ims.entitilement.softphone.requests.RevokeTokenRequest;
import com.sec.internal.constants.ims.entitilement.softphone.requests.SendSMSRequest;
import com.sec.internal.ims.cmstore.CloudMessageIntent;
import com.sec.internal.ims.entitlement.util.EncryptionHelper;
import com.sec.internal.ims.entitlement.util.SharedPrefHelper;
import com.sec.internal.log.IMSLog;
import com.sec.vsim.attsoftphone.data.CallForwardingInfo;
import com.sec.vsim.attsoftphone.data.CallWaitingInfo;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.LinkedHashMap;
import javax.crypto.SecretKey;

/* loaded from: classes.dex */
public class SoftphoneRequestBuilder {
    private static final String LOG_TAG = "SoftphoneRequestBuilder";
    private final Context mContext;

    public SoftphoneRequestBuilder(Context context) {
        this.mContext = context;
    }

    public static ExchangeForAccessTokenRequest buildExchangeForAccessTokenRequest(String str, String str2, String str3, String str4) {
        return new ExchangeForAccessTokenRequest(str, str2, str3, "password", str4, SoftphoneNamespaces.SoftphoneSettings.SCOPE);
    }

    public static RevokeTokenRequest buildRevokeTokenRequest(String str, String str2, String str3, String str4) {
        return new RevokeTokenRequest(str, str2, str3, str4);
    }

    public static ProvisionAccountRequest buildProvisionAccountRequest() {
        return new ProvisionAccountRequest("Yes");
    }

    public AddressValidationRequest buildAddressValidationRequest(int i, boolean z) {
        Log.i(LOG_TAG, "buildAddressValidationRequest [addressId: " + i + ", confirmed: " + z + "]");
        Cursor query = this.mContext.getContentResolver().query(SoftphoneContract.SoftphoneAddress.buildAddressUri((long) i), null, null, null, null);
        if (query == null) {
            return null;
        }
        if (query.moveToFirst()) {
            AddressValidationRequest addressValidationRequest = new AddressValidationRequest(new AddressValidationRequest.Address("ATT WiFi Calling", query.getString(query.getColumnIndex(SoftphoneContract.AddressColumns.HOUSE_NUMBER)), query.getString(query.getColumnIndex(SoftphoneContract.AddressColumns.HOUSE_NUMBER_EXTENSION)), query.getString(query.getColumnIndex(SoftphoneContract.AddressColumns.STREET_DIRECTION_PREFIX)), query.getString(query.getColumnIndex(SoftphoneContract.AddressColumns.STREET_NAME)), query.getString(query.getColumnIndex(SoftphoneContract.AddressColumns.STREET_NAME_SUFFIX)), query.getString(query.getColumnIndex(SoftphoneContract.AddressColumns.STREET_DIRECTION_SUFFIX)), query.getString(query.getColumnIndex(SoftphoneContract.AddressColumns.CITY)), query.getString(query.getColumnIndex("state")), query.getString(query.getColumnIndex(SoftphoneContract.AddressColumns.ZIP)), query.getString(query.getColumnIndex(SoftphoneContract.AddressColumns.ADDITIONAL_ADDRESS_INFO))), z ? CloudMessageProviderContract.JsonData.TRUE : ConfigConstants.VALUE.INFO_COMPLETED);
            query.close();
            return addressValidationRequest;
        }
        query.close();
        return null;
    }

    public AddAddressRequest buildAddAddressRequest(int i) {
        Log.i(LOG_TAG, "buildAddAddressRequest [addressId: " + i + "]");
        Cursor query = this.mContext.getContentResolver().query(SoftphoneContract.SoftphoneAddress.buildAddressUri((long) i), null, null, null, null);
        if (query == null) {
            return null;
        }
        if (query.moveToFirst()) {
            AddAddressRequest addAddressRequest = new AddAddressRequest(getDelimitedAddressString(query));
            query.close();
            return addAddressRequest;
        }
        query.close();
        return null;
    }

    public static String getDelimitedAddressString(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndex("name")) + ";" + cursor.getString(cursor.getColumnIndex(SoftphoneContract.AddressColumns.HOUSE_NUMBER)) + ";" + cursor.getString(cursor.getColumnIndex(SoftphoneContract.AddressColumns.HOUSE_NUMBER_EXTENSION)) + ";" + cursor.getString(cursor.getColumnIndex(SoftphoneContract.AddressColumns.STREET_DIRECTION_PREFIX)) + ";" + cursor.getString(cursor.getColumnIndex(SoftphoneContract.AddressColumns.STREET_NAME)) + ";" + cursor.getString(cursor.getColumnIndex(SoftphoneContract.AddressColumns.STREET_NAME_SUFFIX)) + ";" + cursor.getString(cursor.getColumnIndex(SoftphoneContract.AddressColumns.STREET_DIRECTION_SUFFIX)) + ";" + cursor.getString(cursor.getColumnIndex(SoftphoneContract.AddressColumns.CITY)) + ";" + cursor.getString(cursor.getColumnIndex("state")) + ";" + cursor.getString(cursor.getColumnIndex(SoftphoneContract.AddressColumns.ZIP)) + ";" + cursor.getString(cursor.getColumnIndex(SoftphoneContract.AddressColumns.ADDITIONAL_ADDRESS_INFO)) + ";" + cursor.getString(cursor.getColumnIndex(SoftphoneContract.AddressColumns.E911AID)) + ";" + cursor.getString(cursor.getColumnIndex(SoftphoneContract.AddressColumns.EXPIRE_DATE));
    }

    public static ReleaseImsNetworkIdentifiersRequest buildReleaseImsNetworkIdentifiersRequest(String str, String str2) {
        IMSLog.s(LOG_TAG, "buildReleaseImsNetworkIdentifiersRequest [IMPI: " + str + ", IMPU: " + str2 + "]");
        return new ReleaseImsNetworkIdentifiersRequest(str, str2);
    }

    public static String buildSetCallWaitingInfoRequest(CallWaitingInfo callWaitingInfo) {
        return XmlCreator.toXml(new XmlElement("communication-waiting", null, "ss").addAttribute(SoftphoneNamespaces.SoftphoneCallHandling.ACTIVE, callWaitingInfo.mActive ? CloudMessageProviderContract.JsonData.TRUE : ConfigConstants.VALUE.INFO_COMPLETED), "1.0", "UTF-8");
    }

    public static String buildSetCallForwardingInfoRequest(CallForwardingInfo callForwardingInfo) {
        XmlElement addChildElement;
        int i;
        XmlElement addAttribute = new XmlElement("communication-diversion", null, "ss").addAttribute(SoftphoneNamespaces.SoftphoneCallHandling.ACTIVE, callForwardingInfo.mActive ? CloudMessageProviderContract.JsonData.TRUE : ConfigConstants.VALUE.INFO_COMPLETED);
        if (callForwardingInfo.mActive && (i = callForwardingInfo.mNoReplyTimer) > 0) {
            addAttribute = addAttribute.addChildElement(new XmlElement("NoReplyTimer", Integer.toString(i), "ss"));
        }
        XmlElement namespace = new XmlElement("rule").addAttribute("id", SoftphoneNamespaces.SoftphoneCallHandling.getId(callForwardingInfo.mForwardCondition)).setNamespace(SoftphoneNamespaces.SoftphoneCallHandling.COMMON_POLICY_NS_PREFIX);
        if (callForwardingInfo.mActive) {
            addChildElement = namespace.addChildElement(new XmlElement("conditions").setNamespace(SoftphoneNamespaces.SoftphoneCallHandling.COMMON_POLICY_NS_PREFIX).addChildElement(new XmlElement(SoftphoneNamespaces.SoftphoneCallHandling.getCondition(callForwardingInfo.mForwardCondition), null, "ss")));
            if (!callForwardingInfo.mRetained) {
                addChildElement = addChildElement.addChildElement(new XmlElement(SoftphoneNamespaces.SoftphoneCallHandling.ACTIONS).setNamespace(SoftphoneNamespaces.SoftphoneCallHandling.COMMON_POLICY_NS_PREFIX).addChildElement(new XmlElement(SoftphoneNamespaces.SoftphoneCallHandling.FORWARD_TO, null, "ss").addChildElement(new XmlElement(SoftphoneNamespaces.SoftphoneCallHandling.TARGET, "tel:" + callForwardingInfo.mForwardNumber, "ss"))));
            }
        } else {
            addChildElement = namespace.addChildElement(new XmlElement("conditions").setNamespace(SoftphoneNamespaces.SoftphoneCallHandling.COMMON_POLICY_NS_PREFIX).addChildElement(new XmlElement(SoftphoneNamespaces.SoftphoneCallHandling.getCondition(callForwardingInfo.mForwardCondition), null, "ss")).addChildElement(new XmlElement("rule-deactivated", null, "ss")));
            if (callForwardingInfo.mRetained) {
                addChildElement = addChildElement.addChildElement(new XmlElement(SoftphoneNamespaces.SoftphoneCallHandling.ACTIONS).setNamespace(SoftphoneNamespaces.SoftphoneCallHandling.COMMON_POLICY_NS_PREFIX).addChildElement(new XmlElement(SoftphoneNamespaces.SoftphoneCallHandling.FORWARD_TO, null, "ss").addChildElement(new XmlElement(SoftphoneNamespaces.SoftphoneCallHandling.TARGET, "", "ss"))));
            }
        }
        return XmlCreator.toXml(addAttribute.addChildElement(new XmlElement("ruleset", null, SoftphoneNamespaces.SoftphoneCallHandling.COMMON_POLICY_NS_PREFIX).addChildElement(addChildElement)), "1.0", "UTF-8");
    }

    public LinkedHashMap<String, String> buildObtainPdCookiesQueryParams(String str, int i, SecretKey secretKey, String str2) {
        Log.i(LOG_TAG, "buildObtainPdCookiesQueryParams [accountId: " + str + "]");
        SharedPrefHelper sharedPrefHelper = new SharedPrefHelper(SoftphoneNamespaces.SoftphoneSharedPref.SHARED_PREF_NAME);
        EncryptionHelper encryptionHelper = EncryptionHelper.getInstance(SoftphoneNamespaces.SoftphoneSettings.ENCRYPTION_ALGORITHM);
        String str3 = sharedPrefHelper.get(this.mContext, str + ":" + i + ":" + SoftphoneNamespaces.SoftphoneSharedPref.PREF_TGUARD_APPID);
        String str4 = sharedPrefHelper.get(this.mContext, str + ":" + i + ":" + SoftphoneNamespaces.SoftphoneSharedPref.PREF_TGUARD_TOKEN);
        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<>();
        try {
            linkedHashMap.put("TG_OP", SoftphoneNamespaces.SoftphoneSettings.TGUARD_MSIP_OPERATION);
            linkedHashMap.put("appID", URLEncoder.encode(encryptionHelper.decrypt(str3, secretKey), Charset.defaultCharset().name()));
            linkedHashMap.put("atsToken", URLEncoder.encode(encryptionHelper.decrypt(str4, secretKey), Charset.defaultCharset().name()));
            linkedHashMap.put(CloudMessageIntent.ExtrasAMBSUI.STYLE, str2);
            linkedHashMap.put("returnErrorCode", CloudMessageProviderContract.JsonData.TRUE);
            linkedHashMap.put("targetURL", URLEncoder.encode(SoftphoneNamespaces.SoftphoneSettings.MSIP_REDICRECT_URL, Charset.defaultCharset().name()));
            linkedHashMap.put("errorURL", URLEncoder.encode(SoftphoneNamespaces.SoftphoneSettings.MSIP_ERROR_URL, Charset.defaultCharset().name()));
        } catch (UnsupportedEncodingException e) {
            IMSLog.s(LOG_TAG, "exception" + e.getMessage());
        }
        return linkedHashMap;
    }

    public static SendSMSRequest buildSendSMSRequest(String str) {
        return new SendSMSRequest(false, "AT&T Msg: You have activated NumberSync. NumberSync allows you to make and receive calls on your other device using the same mobile number as your smartphone, even when your smartphone is not nearby or connected to the same Wi-Fi network. Visit att.com/numbersync for more info.", str);
    }
}
