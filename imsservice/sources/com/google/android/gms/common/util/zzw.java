package com.google.android.gms.common.util;

import java.util.regex.Pattern;

/* loaded from: classes.dex */
public final class zzw {
    private static final Pattern zzglh = Pattern.compile("\\$\\{(.*?)\\}");

    public static boolean zzhb(String str) {
        return str == null || str.trim().isEmpty();
    }
}
