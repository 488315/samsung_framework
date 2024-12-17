package com.android.server.biometrics.sensors;

import android.R;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;
import android.util.Slog;
import com.samsung.android.knox.custom.KnoxCustomManagerService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class BiometricNotificationImpl {
    public static void sendFaceEnrollNotification(Context context) {
        Intent intent = BiometricNotificationUtils.DISMISS_FRR_INTENT;
        Slog.d("BiometricNotificationUtils", "Showing Face Enroll Notification");
        String string = context.getString(R.string.gsm_alphabet_default_charset);
        String string2 = context.getString(R.string.capability_desc_canRequestTouchExploration);
        String string3 = context.getString(R.string.capability_desc_canPerformGestures);
        Intent intent2 = new Intent("android.settings.FACE_ENROLL");
        intent2.setPackage(KnoxCustomManagerService.SETTING_PKG_NAME);
        intent2.putExtra("enroll_reason", 1);
        BiometricNotificationUtils.showNotificationHelper(context, string, string2, string3, PendingIntent.getActivityAsUser(context, 0, intent2, 67108864, null, UserHandle.CURRENT), null, null, "FaceEnrollNotificationChannel", "recommendation", "FaceEnroll", 1, true, 0);
    }

    public static void sendFpEnrollNotification(Context context) {
        Intent intent = BiometricNotificationUtils.DISMISS_FRR_INTENT;
        Slog.d("BiometricNotificationUtils", "Showing Fingerprint Enroll Notification");
        String string = context.getString(R.string.gsm_alphabet_default_charset);
        String string2 = context.getString(R.string.capability_desc_canRequestTouchExploration);
        String string3 = context.getString(R.string.capability_desc_canRequestFilterKeyEvents);
        Intent intent2 = new Intent("android.settings.FINGERPRINT_ENROLL");
        intent2.setPackage(KnoxCustomManagerService.SETTING_PKG_NAME);
        intent2.putExtra("enroll_reason", 1);
        BiometricNotificationUtils.showNotificationHelper(context, string, string2, string3, PendingIntent.getActivityAsUser(context, 0, intent2, 67108864, null, UserHandle.CURRENT), null, null, "recommendation", "FingerprintEnrollNotificationChannel", "FingerprintEnroll", 1, true, 0);
    }
}
