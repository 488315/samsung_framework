package com.sec.internal.ims.core.handler.secims;

import java.util.regex.Pattern;

/* loaded from: classes.dex */
public class ResipUtils {
    private static final String EMAIL_REGEX_PATTERN = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

    public static boolean validateEmailAddressFormat(String str) {
        return Pattern.compile(EMAIL_REGEX_PATTERN).matcher(str).matches();
    }
}
