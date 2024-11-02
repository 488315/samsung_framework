package com.android.systemui.globalactions.util;

import android.content.Context;
import android.content.SharedPreferences;
import com.samsung.android.globalactions.util.LogWrapper;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class ScreenCapturePopupController {
    public final LogWrapper mLogWrapper;
    public final SharedPreferences mPrefrerences;

    public ScreenCapturePopupController(Context context, LogWrapper logWrapper) {
        this.mLogWrapper = logWrapper;
        this.mPrefrerences = context.getSharedPreferences("device_options_screen_capture", 0);
    }
}
