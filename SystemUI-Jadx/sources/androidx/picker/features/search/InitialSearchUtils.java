package androidx.picker.features.search;

import android.database.CharArrayBuffer;
import android.net.Uri;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class InitialSearchUtils {
    public static final String[] KOREAN_RANGE_PATTERN = {"[\\uAC00-\\uAE4A]", "[\\uAE4C-\\uB091]", "", "[\\uB098-\\uB2E2]", "", "", "[\\uB2E4-\\uB52A]", "[\\uB530-\\uB775]", "[\\uB77C-\\uB9C1]", "", "", "", "", "", "", "", "[\\uB9C8-\\uBC11]", "[\\uBC14-\\uBE5B]", "[\\uBE60-\\uC0A5]", "", "[\\uC0AC-\\uC2F6]", "[\\uC2F8-\\uC53D]", "[\\uC544-\\uC78E]", "[\\uC790-\\uC9DA]", "[\\uC9DC-\\uCC27]", "[\\uCC28-\\uCE6D]", "[\\uCE74-\\uD0B9]", "[\\uD0C0-\\uD305]", "[\\uD30C-\\uD551]", "[\\uD558-\\uD79D]"};
    public static final Uri SCS_PROVIDER_URI = new Uri.Builder().scheme("content").authority("com.samsung.android.scs.ai.search").appendPath("v1").appendPath("application").build();

    static {
        new Uri.Builder().scheme("content").authority("com.samsung.android.bixby.service.bixbysearch.ai.search").appendPath("v1").appendPath("application").build();
    }

    public static int getMatchedStringOffset(String str, String str2) {
        boolean z;
        CharArrayBuffer charArrayBuffer = new CharArrayBuffer(128);
        char[] cArr = charArrayBuffer.data;
        if (cArr != null && cArr.length >= str.length()) {
            str.getChars(0, str.length(), cArr, 0);
        } else {
            charArrayBuffer.data = str.toCharArray();
        }
        charArrayBuffer.sizeCopied = str.length();
        StringBuilder sb = new StringBuilder("(");
        if (str2 == null || !str2.matches("[0-9|a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힣| ]*")) {
            if (str2 != null) {
                str2 = Pattern.quote(str2);
            } else {
                str2 = "";
            }
        }
        int length = str2.length();
        StringBuilder sb2 = new StringBuilder();
        sb2.setLength(0);
        int i = 0;
        while (true) {
            int i2 = i + 1;
            int codePointAt = str2.codePointAt(i);
            boolean z2 = true;
            if ((codePointAt >= 4352 && codePointAt <= 4370) || ((codePointAt >= 12593 && codePointAt <= 12622) || (codePointAt >= 44032 && codePointAt <= 44032))) {
                z = true;
            } else {
                z = false;
            }
            if (!z) {
                sb2.appendCodePoint(codePointAt);
            } else {
                if (codePointAt < 12593 || codePointAt > 12622) {
                    z2 = false;
                }
                if (z2) {
                    sb2.append(KOREAN_RANGE_PATTERN[codePointAt - 12593]);
                } else {
                    sb2.appendCodePoint(codePointAt);
                }
            }
            if (i2 >= length) {
                break;
            }
            i = i2;
        }
        sb.append(sb2.toString());
        sb.append(")");
        Matcher matcher = Pattern.compile(sb.toString()).matcher(new String(charArrayBuffer.data, 0, charArrayBuffer.sizeCopied));
        if (matcher.find()) {
            return matcher.start();
        }
        return -1;
    }
}
