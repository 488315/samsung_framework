package com.android.systemui.searcle;

import java.util.Arrays;
import java.util.Calendar;
import kotlin.jvm.internal.StringCompanionObject;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class SearcleTipPopupUtil {
    public static final SearcleTipPopupUtil INSTANCE = new SearcleTipPopupUtil();

    private SearcleTipPopupUtil() {
    }

    public static String getTimeFormatString(long j) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(j);
        int i = StringCompanionObject.$r8$clinit;
        return String.format("%02d:%02d:%02d.%03d", Arrays.copyOf(new Object[]{Integer.valueOf(calendar.get(11)), Integer.valueOf(calendar.get(12)), Integer.valueOf(calendar.get(13)), Integer.valueOf(calendar.get(14))}, 4));
    }
}
