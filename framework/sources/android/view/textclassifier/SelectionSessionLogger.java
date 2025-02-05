package android.view.textclassifier;

import android.util.NtpTrustedTime;

/* loaded from: classes4.dex */
public final class SelectionSessionLogger {
    private static final String CLASSIFIER_ID = "androidtc";

    static boolean isPlatformLocalTextClassifierSmartSelection(String signature) {
        return "androidtc".equals(SignatureParser.getClassifierId(signature));
    }

    public static final class SignatureParser {
        static String getClassifierId(String signature) {
            int end;
            if (signature == null || (end = signature.indexOf(NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER)) < 0) {
                return "";
            }
            return signature.substring(0, end);
        }
    }
}
