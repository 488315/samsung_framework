package com.sec.internal.helper.userconsent;

import android.util.Patterns;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
class HyperlinkPatterns {
    static Pattern webUrlPattern = Pattern.compile(Patterns.WEB_URL.toString() + "(?![\\S]{2,}|\\s*[\"'].*[^<].*>)");

    private HyperlinkPatterns() {
    }
}
