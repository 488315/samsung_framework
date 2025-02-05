package com.sec.internal.ims.cmstore.ambs.globalsetting;

import android.text.TextUtils;
import android.util.Log;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber$PhoneNumber;
import com.sec.internal.log.IMSLog;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Random;

/* loaded from: classes.dex */
public class AmbsUtils {
    private static final String TAG = "AmbsUtils";

    public static String generateRandomString(int i, boolean z) {
        String str = z ? "0123456789" : "0123456789abcdefghijklmnopqrstuvwxyz";
        Random random = new Random();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i2 = 0; i2 < i; i2++) {
            stringBuffer.append(str.charAt(random.nextInt(str.length())));
        }
        return stringBuffer.toString();
    }

    public static String findErrorCode(String str, String str2, char c) {
        int indexOf;
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || (indexOf = str.indexOf(str2)) < 0) {
            return null;
        }
        int length = indexOf + str2.length();
        int length2 = str.length();
        if (length >= 0) {
            int findEnd = findEnd(str, c, length, length2);
            Log.d(TAG, "findErrorCode:" + str.substring(length, findEnd));
            return str.substring(length, findEnd);
        }
        return null;
    }

    private static int findEnd(String str, char c, int i, int i2) {
        if (c == 0) {
            return i2;
        }
        int indexOf = str.indexOf(c, i);
        return indexOf == -1 ? str.length() : indexOf;
    }

    public static String convertPhoneNumberToUserAct(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        if (str.trim().length() > 10) {
            return str.trim().substring(str.trim().length() - 10);
        }
        if (str.charAt(0) == '1') {
            return str.substring(1);
        }
        return str.startsWith("+1") ? str.substring(2) : str;
    }

    public static String generateSmsHashCode(String str, int i, String str2, String[] strArr, boolean z) {
        String makeE164Format;
        IMSLog.s(TAG, "generateSmsHashCode: phoneNum: " + IMSLog.checker(str) + " type: " + i + " body: " + str2);
        String str3 = null;
        if (strArr.length != 3) {
            return null;
        }
        for (String str4 : strArr) {
            if (str4 == null || str4.length() == 0) {
                Log.e(TAG, "generateSmsHashCode, wrong delimiter.");
                return null;
            }
        }
        if (z) {
            makeE164Format = getE164FormatNumber(str, "KR");
        } else {
            makeE164Format = makeE164Format(str);
        }
        StringBuffer stringBuffer = new StringBuffer();
        if (i == 1) {
            stringBuffer.append(strArr[0]);
            stringBuffer.append(makeE164Format);
            stringBuffer.append(strArr[1]);
        } else {
            stringBuffer.append(makeE164Format);
            stringBuffer.append(strArr[2]);
        }
        stringBuffer.append(str2);
        try {
            str3 = hash(stringBuffer.toString());
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            Log.e(TAG, "generateSmsHashCode, Exception : " + e);
        }
        IMSLog.i(TAG, "generateSmsHashCode, hash: " + str3);
        return str3;
    }

    private static String hash(String str) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        byte[] bytes = str.getBytes("UTF-8");
        messageDigest.update(bytes, 0, bytes.length);
        return new BigInteger(1, Arrays.copyOfRange(messageDigest.digest(), 0, 8)).toString(16);
    }

    private static String getE164FormatNumber(String str, String str2) {
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        try {
            Phonenumber$PhoneNumber parse = phoneNumberUtil.parse(str, str2);
            return ((str2.equalsIgnoreCase("KR") && (str.contains("*") || str.contains("#"))) || parse == null) ? str : phoneNumberUtil.format(parse, PhoneNumberUtil.PhoneNumberFormat.E164);
        } catch (NumberParseException | NullPointerException unused) {
            return str;
        }
    }

    private static String makeE164Format(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        if (str.charAt(0) != '+') {
            if (str.length() == 10) {
                sb.append("+1");
            }
            if (str.length() > 10) {
                sb.append("+");
            }
        }
        sb.append(str);
        return sb.toString();
    }

    public static boolean isInvalidShortCode(String str) {
        return str != null && str.length() != 0 && str.length() < 10 && str.charAt(0) == '+';
    }
}
