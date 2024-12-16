package com.samsung.android.audio;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.AudioSystem;
import android.media.MediaMetrics;
import android.os.Binder;
import android.os.SystemProperties;
import android.telecom.Logging.Session;
import android.text.TextUtils;
import android.util.Log;
import com.samsung.android.common.AsPackageName;
import com.samsung.android.media.AudioParameter;
import com.samsung.android.wallpaperbackup.BnRConstants;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

/* loaded from: classes5.dex */
public class AudioManagerHelper {
    private static final int LOGGING_CALLER_DEFAULT_DEPTH = 5;
    private static final String TAG = "AudioManagerHelper";
    private static final boolean USER_SHIP;
    private static final ArrayList<String> mLoggingPackages = new ArrayList<>(Arrays.asList("android", AsPackageName.SYSTEMUI, "com.android.settings"));

    /* JADX WARN: Code restructure failed: missing block: B:4:0x002d, code lost:
    
        if (android.os.SystemProperties.getBoolean("ro.product_ship", true) != false) goto L8;
     */
    static {
        /*
            java.util.ArrayList r0 = new java.util.ArrayList
            java.lang.String r1 = "com.android.systemui"
            java.lang.String r2 = "com.android.settings"
            java.lang.String r3 = "android"
            java.lang.String[] r1 = new java.lang.String[]{r3, r1, r2}
            java.util.List r1 = java.util.Arrays.asList(r1)
            r0.<init>(r1)
            com.samsung.android.audio.AudioManagerHelper.mLoggingPackages = r0
            java.lang.String r0 = "ro.build.type"
            java.lang.String r1 = "user"
            java.lang.String r0 = android.os.SystemProperties.get(r0, r1)
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L30
            java.lang.String r0 = "ro.product_ship"
            r1 = 1
            boolean r0 = android.os.SystemProperties.getBoolean(r0, r1)
            if (r0 == 0) goto L30
            goto L31
        L30:
            r1 = 0
        L31:
            com.samsung.android.audio.AudioManagerHelper.USER_SHIP = r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.audio.AudioManagerHelper.<clinit>():void");
    }

    public static void setMusicShareSyncDelay(int delay) {
        AudioParameter param = new AudioParameter.Builder().setParam(AudioParameter.SEC_GLOBAL_SET_A2DP_AV_SYNC, "musicshare," + delay).build();
        AudioSystem.setParameters(param.toString());
    }

    public static boolean isFMPlayerActive() {
        try {
            String fmPlayer = SystemProperties.get("persist.audio.isfmactive");
            return Integer.parseInt(fmPlayer) == 1;
        } catch (Exception e) {
            return false;
        }
    }

    public static String getFmRadioPackageName(Context context) {
        PackageManager pm = context.getPackageManager();
        int caller = Binder.getCallingUid();
        String[] packageNames = pm.getPackagesForUid(caller);
        if (packageNames == null) {
            return AsPackageName.FM_RADIO;
        }
        return packageNames[0];
    }

    public static void semSetAudioHDR(boolean z) {
        AudioManager.setAudioServiceConfig("audioHDR=" + (z ? 1 : 0));
    }

    public static String getAddressForLog(String addr) {
        if (addr == null) {
            return null;
        }
        if (addr.length() == 17 && USER_SHIP) {
            String ret = addr.replaceAll(":", "");
            return ret.substring(0, 6) + Session.SESSION_SEPARATION_CHAR_CHILD + ret.substring(11);
        }
        return addr;
    }

    public static String getAddressForLog(BluetoothDevice device) {
        if (device == null) {
            return null;
        }
        return getAddressForLog(device.getAddress());
    }

    public static boolean isSupportSoftPhone() {
        return isTablet() && isAttModelWithSoftPhone();
    }

    private static boolean isTablet() {
        return SystemProperties.get("ro.build.characteristics", "").contains(BnRConstants.DEVICETYPE_TABLET);
    }

    private static boolean isAttModelWithSoftPhone() {
        try {
            String salesCode = SystemProperties.get("persist.omc.sales_code");
            if (TextUtils.isEmpty(salesCode)) {
                salesCode = SystemProperties.get("ro.csc.sales_code");
            }
            if (TextUtils.equals("ATT", salesCode)) {
                return true;
            }
            if (TextUtils.equals("APP", salesCode)) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean needToLogCaller(String packageName) {
        return mLoggingPackages.contains(packageName);
    }

    public static void logCaller() {
        logging(getTrace(), "");
    }

    public static void logCaller(String format, Object... args) {
        logging(getTrace(), String.format(format, args));
    }

    private static StackTraceElement[] getTrace() {
        return Thread.currentThread().getStackTrace();
    }

    private static void logging(StackTraceElement[] stack, String logContent) {
        String callerClassName = getSimpleClassName(stack[4]);
        String callerMethodName = stack[4].getMethodName();
        String callStack = buildCallStack(stack, 5);
        Log.d(callerClassName, callerMethodName + " : " + logContent + " callers=" + callStack);
    }

    public static String buildCallStack(StackTraceElement[] stack, int depth) {
        return (String) List.of((Object[]) stack).subList(5, Math.min(depth + 5, stack.length)).stream().map(new Function() { // from class: com.samsung.android.audio.AudioManagerHelper$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                String makeMethodString;
                makeMethodString = AudioManagerHelper.makeMethodString((StackTraceElement) obj);
                return makeMethodString;
            }
        }).collect(Collectors.joining("<-"));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String makeMethodString(StackTraceElement element) {
        return getSimpleClassName(element) + MediaMetrics.SEPARATOR + element.getMethodName() + ":" + element.getLineNumber();
    }

    private static String getSimpleClassName(StackTraceElement element) {
        return (String) Arrays.stream(element.getClassName().split("\\.")).reduce(new BinaryOperator() { // from class: com.samsung.android.audio.AudioManagerHelper$$ExternalSyntheticLambda0
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return AudioManagerHelper.lambda$getSimpleClassName$0((String) obj, (String) obj2);
            }
        }).orElse("");
    }

    static /* synthetic */ String lambda$getSimpleClassName$0(String first, String second) {
        return second;
    }

    public static String convertStartingPathToSystem(String path) {
        if (path != null && path.startsWith("/product/media/audio/ui/")) {
            String path2 = path.replaceFirst("/product", "/system");
            Log.e(TAG, "convert starting path: " + path2);
            return path2;
        }
        return path;
    }
}
