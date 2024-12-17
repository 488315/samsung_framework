package com.android.internal.util.jobs;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ParseUtils {
    private ParseUtils() {
    }

    public static boolean parseBoolean(String str, boolean z) {
        if ("true".equals(str)) {
            return true;
        }
        return ("false".equals(str) || parseInt(str, z ? 1 : 0) == 0) ? false : true;
    }

    public static double parseDouble(String str, double d) {
        if (str == null) {
            return d;
        }
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException unused) {
            return d;
        }
    }

    public static float parseFloat(String str, float f) {
        if (str == null) {
            return f;
        }
        try {
            return Float.parseFloat(str);
        } catch (NumberFormatException unused) {
            return f;
        }
    }

    public static int parseInt(String str, int i) {
        return parseIntWithBase(str, 10, i);
    }

    public static int parseIntWithBase(String str, int i, int i2) {
        if (str == null) {
            return i2;
        }
        try {
            return Integer.parseInt(str, i);
        } catch (NumberFormatException unused) {
            return i2;
        }
    }

    public static long parseLong(String str, long j) {
        return parseLongWithBase(str, 10, j);
    }

    public static long parseLongWithBase(String str, int i, long j) {
        if (str == null) {
            return j;
        }
        try {
            return Long.parseLong(str, i);
        } catch (NumberFormatException unused) {
            return j;
        }
    }
}
