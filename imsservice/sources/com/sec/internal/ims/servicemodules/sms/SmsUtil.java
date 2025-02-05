package com.sec.internal.ims.servicemodules.sms;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.SemSystemProperties;
import android.provider.Telephony;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import com.sec.ims.ImsRegistration;
import com.sec.ims.settings.ImsProfile;
import com.sec.ims.util.ImsUri;
import com.sec.ims.util.NameAddr;
import com.sec.ims.util.SipError;
import com.sec.internal.constants.Mno;
import com.sec.internal.constants.ims.DiagnosisConstants;
import com.sec.internal.constants.ims.ImsConstants;
import com.sec.internal.constants.ims.config.ConfigConstants;
import com.sec.internal.helper.DmConfigHelper;
import com.sec.internal.helper.NetworkUtil;
import com.sec.internal.helper.SimUtil;
import com.sec.internal.helper.os.IntentUtil;
import com.sec.internal.ims.diagnosis.ImsLogAgentUtil;
import com.sec.internal.ims.registry.ImsRegistry;
import com.sec.internal.ims.util.UriGenerator;
import com.sec.internal.ims.util.UriGeneratorFactory;
import com.sec.internal.interfaces.ims.core.IRegistrationGovernor;
import com.sec.internal.log.IMSLog;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes.dex */
public class SmsUtil {
    private static final int MSG_ID_BASE = 1000;
    private static final String LOG_TAG = SmsServiceModule.class.getSimpleName();
    private static int mIncommingMagId = 1000;
    private static int mRPMsgRef = 0;

    private static String getIsmoResultBySipErrorCode(String str, int i) {
        return (i == 408 || i == 708) ? DiagnosisConstants.RCSM_ORST_REGI : (i < 200 || i >= 300) ? str : "0";
    }

    protected static String getNetworkPreferredUri(ImsRegistration imsRegistration, String str, boolean z) {
        ImsUri parse;
        if (str.startsWith("sip:") || str.startsWith("tel:")) {
            parse = ImsUri.parse(str);
        } else {
            if (imsRegistration == null) {
                return null;
            }
            UriGenerator uriGenerator = UriGeneratorFactory.getInstance().get(imsRegistration.getPreferredImpu().getUri(), UriGenerator.URIServiceType.VOLTE_URI);
            Mno simMno = SimUtil.getSimMno(imsRegistration.getPhoneId());
            if (z) {
                if (simMno == Mno.VZW) {
                    String domain = imsRegistration.getDomain();
                    if (str.startsWith("+") || TextUtils.isEmpty(domain)) {
                        parse = ImsUri.parse("tel:" + str);
                    } else {
                        parse = ImsUri.parse("tel:" + str + ";phone-context=" + domain);
                    }
                } else {
                    parse = uriGenerator.getNormalizedUri(str);
                }
            } else if (simMno.isOneOf(Mno.TMOUS, Mno.DISH, Mno.MTN_SOUTHAFRICA)) {
                parse = uriGenerator.getNetworkPreferredUri(str);
            } else {
                parse = uriGenerator.getNetworkPreferredUri((ImsUri.UriType) null, str);
            }
        }
        return parse.toString();
    }

    protected static String getLocalUri(ImsRegistration imsRegistration) {
        if (imsRegistration == null) {
            return "";
        }
        NameAddr preferredImpu = imsRegistration.getPreferredImpu();
        ImsUri uri = preferredImpu == null ? null : preferredImpu.getUri();
        if (SimUtil.getSimMno(imsRegistration.getPhoneId()) == Mno.ATT) {
            for (NameAddr nameAddr : imsRegistration.getImpuList()) {
                if (nameAddr.getUri().toString().startsWith("tel:")) {
                    uri = nameAddr.getUri();
                }
            }
        }
        return uri == null ? "" : uri.toString();
    }

    protected static int getIncreasedRPRef() {
        int i = mRPMsgRef + 1;
        mRPMsgRef = i;
        if (i >= 254) {
            mRPMsgRef = 0;
        }
        return mRPMsgRef;
    }

    protected static int getRPMsgRef() {
        return mRPMsgRef;
    }

    protected static int getMessageIdByCallId(ConcurrentHashMap<Integer, SmsEvent> concurrentHashMap, String str) {
        for (Map.Entry<Integer, SmsEvent> entry : concurrentHashMap.entrySet()) {
            int intValue = entry.getKey().intValue();
            if (str.equals(entry.getValue().getCallID())) {
                return intValue;
            }
        }
        return -1;
    }

    protected static int getMessageIdByPdu(ConcurrentHashMap<Integer, SmsEvent> concurrentHashMap, byte[] bArr) {
        if (bArr != null && bArr.length > 1) {
            int i = bArr[1] & 255;
            for (Map.Entry<Integer, SmsEvent> entry : concurrentHashMap.entrySet()) {
                int intValue = entry.getKey().intValue();
                SmsEvent value = entry.getValue();
                if (i == value.getRpRef() && 100 <= value.getState() && value.getState() <= 102) {
                    return intValue;
                }
            }
        }
        return -1;
    }

    protected static int getNewMsgId() {
        int i = mIncommingMagId;
        if (i == 65535) {
            mIncommingMagId = 1000;
        } else {
            mIncommingMagId = i + 1;
        }
        return mIncommingMagId;
    }

    protected static int getIncommingMagId() {
        return mIncommingMagId;
    }

    private static boolean isKORMnoName(int i) {
        Mno simMno = SimUtil.getSimMno(i);
        return simMno == Mno.SKT || simMno == Mno.KT || simMno == Mno.LGU;
    }

    protected static boolean isServiceAvailable(TelephonyManager telephonyManager, int i, boolean z) {
        Mno simMno = SimUtil.getSimMno(i);
        int dataNetworkType = telephonyManager.getDataNetworkType(SimUtil.getSubId(i));
        if (simMno == Mno.ATT || z) {
            return SimUtil.isSoftphoneEnabled() || NetworkUtil.is3gppPsVoiceNetwork(dataNetworkType) || dataNetworkType == 18;
        }
        if (isKORMnoName(i)) {
            return NetworkUtil.is3gppPsVoiceNetwork(dataNetworkType);
        }
        return true;
    }

    protected static boolean isProhibited(ImsRegistration imsRegistration) {
        ImsRegistration imsRegistration2;
        if (imsRegistration != null) {
            Map<Integer, ImsRegistration> registrationList = ImsRegistry.getRegistrationManager().getRegistrationList();
            ImsProfile imsProfile = imsRegistration.getImsProfile();
            if (imsProfile != null && (imsRegistration2 = registrationList.get(Integer.valueOf(imsProfile.getId()))) != null) {
                imsRegistration.setProhibited(imsRegistration2.isProhibited());
                return imsRegistration2.isProhibited();
            }
        }
        return false;
    }

    protected static void broadcastDcnNumber(Context context, int i) {
        String read = DmConfigHelper.read(context, ConfigConstants.ConfigPath.OMADM_DCN_NUMBER, "", i);
        if (TextUtils.isEmpty(read)) {
            return;
        }
        Log.i(LOG_TAG, "broadcastDcnNumber : value : " + IMSLog.checker(read));
        Intent intent = new Intent(ImsConstants.Intents.ACTION_DM_CHANGED);
        intent.putExtra(ImsConstants.Intents.EXTRA_UPDATED_ITEM, "DCN_NUMBER");
        intent.putExtra("value", read);
        intent.putExtra("phoneId", i);
        context.sendBroadcast(intent);
    }

    protected static void broadcastSCBMState(Context context, boolean z, int i) {
        Log.d(LOG_TAG, "broadcastSCBMState : " + z + ", phoneId : " + i);
        Intent intent = new Intent(ImsConstants.Intents.ACTION_SMS_CALLBACK_MODE_CHANGED_INTERNAL);
        intent.addFlags(IntentUtil.FLAG_RECEIVER_INCLUDE_BACKGROUND);
        intent.putExtra("phoneInSCBMState", z);
        intent.putExtra("phoneId", i);
        context.sendBroadcast(intent);
    }

    protected static void onSipError(ImsRegistration imsRegistration, int i, String str) {
        IRegistrationGovernor registrationGovernor = ImsRegistry.getRegistrationManager().getRegistrationGovernor(imsRegistration.getHandle());
        if (registrationGovernor != null) {
            registrationGovernor.onSipError("smsip", new SipError(i, str));
        }
    }

    protected static void sendIsmoInfoToHqmAndStoreMoSmsInfoOfDrcsToImsLogAgent(Context context, int i, String str, int i2, String str2, boolean z) {
        String ismoResultBySipErrorCode = getIsmoResultBySipErrorCode(str, i2);
        sendIsmoInfoToHqm(context, i, ismoResultBySipErrorCode, i2, str2, z);
        storeMoSmsInfoOfDrcsToImsLogAgent(context, i, ismoResultBySipErrorCode);
    }

    protected static void sendSmotInfoToHQM(Context context, int i, String str, String str2, boolean z) {
        ContentValues contentValues = new ContentValues();
        prepareSmsCommonKeys(context, z, contentValues);
        contentValues.put("MOMT", str);
        contentValues.put("ITER", str2);
        ImsLogAgentUtil.sendLogToAgent(i, context, DiagnosisConstants.FEATURE_SMOT, contentValues);
    }

    private static void sendIsmoInfoToHqm(Context context, int i, String str, int i2, String str2, boolean z) {
        ContentValues contentValues = new ContentValues();
        prepareSmsCommonKeys(context, z, contentValues);
        contentValues.put("ORST", str);
        if (i2 != 0) {
            contentValues.put(DiagnosisConstants.ISMO_KEY_OSIP, Integer.valueOf(i2));
        } else if (str2 != null) {
            contentValues.put(DiagnosisConstants.ISMO_KEY_ORPC, str2);
        }
        ImsLogAgentUtil.sendLogToAgent(i, context, DiagnosisConstants.FEATURE_ISMO, contentValues);
    }

    private static void prepareSmsCommonKeys(Context context, boolean z, ContentValues contentValues) {
        contentValues.put(DiagnosisConstants.SMS_COMMON_KEY_RGST, getRgSt(z));
        String defaultSmsPackage = Telephony.Sms.getDefaultSmsPackage(context);
        if (defaultSmsPackage != null) {
            contentValues.put(DiagnosisConstants.SMS_COMMON_KEY_CSDA, defaultSmsPackage);
        }
    }

    private static void storeMoSmsInfoOfDrcsToImsLogAgent(Context context, int i, String str) {
        ContentValues contentValues = new ContentValues();
        if (str.equals("0")) {
            contentValues.put(DiagnosisConstants.DRCS_KEY_SMS_MO_IMS_SUCCESS, (Integer) 1);
        } else {
            contentValues.put(DiagnosisConstants.DRCS_KEY_SMS_MO_IMS_FAIL, (Integer) 1);
        }
        storeDrcsInfoToImsLogAgent(context, i, contentValues);
    }

    protected static void storeMtSmsInfoOfDrcsToImsLogAgent(Context context, int i) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DiagnosisConstants.DRCS_KEY_SMS_MT_IMS, (Integer) 1);
        storeDrcsInfoToImsLogAgent(context, i, contentValues);
    }

    private static void storeDrcsInfoToImsLogAgent(Context context, int i, ContentValues contentValues) {
        contentValues.put(DiagnosisConstants.KEY_SEND_MODE, (Integer) 1);
        contentValues.put(DiagnosisConstants.KEY_OVERWRITE_MODE, (Integer) 1);
        ImsLogAgentUtil.storeLogToAgent(i, context, DiagnosisConstants.FEATURE_DRCS, contentValues);
    }

    private static String getRgSt(boolean z) {
        return SemSystemProperties.getBoolean("gsm.operator.isroaming", false) ? "6" : z ? "2" : "1";
    }
}
