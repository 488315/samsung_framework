package com.android.keyguard;

import android.content.Context;
import android.os.Debug;
import android.util.Log;
import java.io.PrintWriter;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public abstract /* synthetic */ class KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0 {
    public static String m(Context context, int i, StringBuilder sb) {
        sb.append(context.getString(i));
        return sb.toString();
    }

    public static String m(StringBuilder sb, boolean z, String str, boolean z2) {
        sb.append(z);
        sb.append(str);
        sb.append(z2);
        return sb.toString();
    }

    public static StringBuilder m(PrintWriter printWriter, String str, String str2) {
        printWriter.println(str);
        return new StringBuilder(str2);
    }

    public static StringBuilder m(String str, int i, String str2, boolean z, String str3) {
        StringBuilder sb = new StringBuilder(str);
        sb.append(i);
        sb.append(str2);
        sb.append(z);
        sb.append(str3);
        return sb;
    }

    public static StringBuilder m(StringBuilder sb, int i, PrintWriter printWriter, String str) {
        sb.append(i);
        printWriter.println(sb.toString());
        return new StringBuilder(str);
    }

    public static void m(int i, StringBuilder sb, String str) {
        sb.append(Debug.getCallers(i));
        Log.d(str, sb.toString());
    }

    public static void m(StringBuilder sb, int i, String str, int i2, String str2) {
        sb.append(i);
        sb.append(str);
        sb.append(i2);
        Log.d(str2, sb.toString());
    }
}
