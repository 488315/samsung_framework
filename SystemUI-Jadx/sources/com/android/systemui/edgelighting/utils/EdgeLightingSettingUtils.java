package com.android.systemui.edgelighting.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.text.TextUtils;
import android.util.Slog;
import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.edgelighting.Feature;
import com.android.systemui.edgelighting.data.EdgeLightingConstants;
import com.android.systemui.edgelighting.effect.data.EdgeEffectInfo;
import com.samsung.systemui.splugins.edgelightingplus.PluginEdgeLightingPlus;
import com.sec.ims.settings.ImsProfile;
import java.util.HashMap;
import java.util.List;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class EdgeLightingSettingUtils {
    public static final int[] mSimilarColorIndex = {3, 3, 3, 3, 3, 12, 13, 10, 5, 6, 7, 8, 12, 13};
    public static final int[] mSimilarColorIndexForONEUI_4 = {3, 3, 3, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};

    public static String effectInfoToString(EdgeEffectInfo edgeEffectInfo, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("Effect=" + str);
        if (edgeEffectInfo.mEffectColors != null) {
            sb.append(",Color=" + String.format("#%x", Integer.valueOf(edgeEffectInfo.mEffectColors[0])));
        }
        sb.append(",Width=" + edgeEffectInfo.mStrokeWidth);
        sb.append(",Transparency=" + edgeEffectInfo.mStrokeAlpha);
        sb.append(",Duration=" + edgeEffectInfo.mLightingDuration);
        return sb.toString();
    }

    public static List getAppInfoSupportingEdgeLighting(PackageManager packageManager, String str) {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.LAUNCHER");
        intent.setPackage(str);
        return packageManager.queryIntentActivities(intent, 0);
    }

    public static int getEdgeLightingBasicColorIndex(ContentResolver contentResolver) {
        int intForUser = Settings.System.getIntForUser(contentResolver, "edge_lighting_basic_color_index", 3, -2);
        if (intForUser == 100) {
            return 3;
        }
        return intForUser;
    }

    public static int getEdgeLightingDuration(int i) {
        if (i != 1) {
            if (i != 2) {
                return ImsProfile.DEFAULT_DEREG_TIMEOUT;
            }
            return PluginEdgeLightingPlus.VERSION;
        }
        return 5000;
    }

    public static int getEdgeLightingStylePreDefineColor(Context context, int i, boolean z) {
        int i2 = i - 3;
        if (i2 >= 0 && i2 < 11) {
            return EdgeLightingConstants.DEFAULT_COLOR_LIST[i2];
        }
        Slog.i("EdgeLightingSettingUtils", "Invalid index value : " + i);
        if (!z) {
            setEdgeLightingColorType(context.getContentResolver(), 3);
            setEdgeLightingBasicColorIndex(context.getContentResolver(), 100);
        }
        return DeviceColorMonitor.getDeviceWallPaperColorIndex(context.getContentResolver());
    }

    public static float getEdgeLightingStyleWidth(Context context, String str, int i) {
        int dimensionPixelSize;
        float dimensionPixelSize2 = context.getResources().getDimensionPixelSize(R.dimen.edge_lighting_style_min_width);
        if (TextUtils.equals(str, "preload/glow")) {
            dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.edge_lighting_style_max_width_type_2);
        } else {
            dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.edge_lighting_style_max_width_type_1);
        }
        float f = dimensionPixelSize;
        float f2 = (((f - dimensionPixelSize2) / 4.0f) * i) + dimensionPixelSize2;
        Slog.i("EdgeLightingSettingUtils", " getEdgeLightingStyleWidth : width " + f2 + " max : " + f);
        return f2;
    }

    public static int getEdgeLightingWidth(int i, Context context) {
        if (i != 0) {
            if (i != 1) {
                if (i != 2) {
                    if (i != 3) {
                        if (i != 4) {
                            return 0;
                        }
                        return context.getResources().getDimensionPixelSize(R.dimen.edge_lighting_width_type_5);
                    }
                    return context.getResources().getDimensionPixelSize(R.dimen.edge_lighting_width_type_4);
                }
                return context.getResources().getDimensionPixelSize(R.dimen.edge_lighting_width_type_3);
            }
            return context.getResources().getDimensionPixelSize(R.dimen.edge_lighting_width_type_2);
        }
        return context.getResources().getDimensionPixelSize(R.dimen.edge_lighting_width_type_1);
    }

    public static int[] getLightingColor(Context context, String[] strArr, String str, int[] iArr) {
        int i;
        String str2;
        if (strArr != null && !strArr[0].isEmpty()) {
            String str3 = strArr[0];
            if ("com.samsung.android.messaging".equals(str) && (str2 = strArr[0].split(" \\(")[0]) != null) {
                str3 = str2;
            }
            if (str3 != null) {
                str3 = str3.replace("\u2068", "").replace("\u2069", "").replace("\u200f", "");
            }
            HashMap loadCustomTextList = loadCustomTextList(context);
            if (loadCustomTextList != null && loadCustomTextList.containsKey(str3)) {
                i = ((Integer) loadCustomTextList.get(str3)).intValue();
            } else {
                i = -1;
            }
            if (i == -1) {
                return iArr;
            }
            return new int[]{i, 0};
        }
        return iArr;
    }

    public static void initColorTypeIndex(Context context) {
        boolean z = true;
        int intForUser = Settings.System.getIntForUser(context.getContentResolver(), "edge_lighting_color_type", 1, -2);
        int edgeLightingBasicColorIndex = getEdgeLightingBasicColorIndex(context.getContentResolver());
        ContentResolver contentResolver = context.getContentResolver();
        if (intForUser != 0 && edgeLightingBasicColorIndex == 3) {
            setEdgeLightingBasicColorIndex(contentResolver, intForUser);
        }
        if (intForUser == 1) {
            edgeLightingBasicColorIndex = 0;
        } else if (intForUser == 2) {
            edgeLightingBasicColorIndex = 99;
        } else if (intForUser == 0) {
            int i = edgeLightingBasicColorIndex - 3;
            if (i < 0 || i >= 11) {
                z = false;
            }
            if (!z) {
                edgeLightingBasicColorIndex = 100;
                intForUser = 3;
            }
        }
        setEdgeLightingColorType(contentResolver, intForUser);
        setEdgeLightingBasicColorIndex(contentResolver, edgeLightingBasicColorIndex);
    }

    public static void initializeSettingValue(ContentResolver contentResolver, boolean z) {
        if (Settings.System.getIntForUser(contentResolver, "edge_lighting", !Feature.isEdgeLightingDefaultOff() ? 1 : 0, -2) == -1 || z) {
            Settings.System.putIntForUser(contentResolver, "edge_lighting", !Feature.isEdgeLightingDefaultOff() ? 1 : 0, -2);
        }
        if (Settings.System.getIntForUser(contentResolver, "edge_lighting_show_condition", -1, -2) == -1 || z) {
            if (Feature.FEATURE_SUPPORT_AOD) {
                Settings.System.putIntForUser(contentResolver, "edge_lighting_show_condition", 0, -2);
            } else {
                Settings.System.putIntForUser(contentResolver, "edge_lighting_show_condition", 1, -2);
            }
        }
        if (Settings.System.getInt(contentResolver, "edge_lighting_version", 0) != 1) {
            Settings.System.putInt(contentResolver, "edge_lighting_version", 1);
        }
    }

    public static boolean isEdgeLightingEnabled(ContentResolver contentResolver) {
        if (Settings.System.getIntForUser(contentResolver, "edge_lighting", !Feature.isEdgeLightingDefaultOff() ? 1 : 0, -2) == 1) {
            return true;
        }
        return false;
    }

    public static int loadAppCustomColor(Context context, String str) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("edge_lighting_app_color", 0);
        if (sharedPreferences.contains(str)) {
            int i = sharedPreferences.getInt(str, 0);
            StringBuilder m = ActivityResultRegistry$$ExternalSyntheticOutline0.m(" loadAppCustomColor : ", str, " color : ");
            m.append(Integer.toHexString(i));
            Slog.i("EdgeLightingSettingUtils", m.toString());
            return i;
        }
        Slog.i("EdgeLightingSettingUtils", " loadAppCustomColor : " + str + " don't set custom app color");
        return 0;
    }

    public static HashMap loadCustomTextList(Context context) {
        String[] split;
        HashMap hashMap = new HashMap();
        String stringForUser = Settings.System.getStringForUser(context.getContentResolver(), "edge_lighting_custom_text_color", -2);
        if (stringForUser == null || (split = stringForUser.split(";")) == null || split.length % 2 != 0) {
            return null;
        }
        for (int i = 0; i < split.length; i++) {
            if (i % 2 == 0) {
                hashMap.put(split[i], Integer.valueOf(Integer.parseInt(split[i + 1])));
            }
        }
        return hashMap;
    }

    public static int loadEdgeLightingDurationOptionType(Context context) {
        return Settings.System.getIntForUser(context.getContentResolver(), "edge_lighting_duration", 0, -2);
    }

    public static void rematchingSimilarColorChip(ContentResolver contentResolver, int i) {
        StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(" re-matching similar color index  : ", i, " backup version : ");
        m.append(Settings.Global.getInt(contentResolver, "lighting_color_backup_version", 0));
        Slog.i("EdgeLightingSettingUtils", m.toString());
        if (Settings.Global.getInt(contentResolver, "lighting_color_backup_version", 0) == 3) {
            Slog.i("EdgeLightingSettingUtils", " don't need rematching.");
            setEdgeLightingBasicColorIndex(contentResolver, i);
            return;
        }
        if (i <= 13 && i >= 0) {
            int i2 = mSimilarColorIndexForONEUI_4[mSimilarColorIndex[i]];
            Slog.i("EdgeLightingSettingUtils", " re-matching similar color index  : " + i + " -> " + i2);
            setEdgeLightingBasicColorIndex(contentResolver, i2);
            Settings.Global.putInt(contentResolver, "lighting_color_backup_version", 3);
            return;
        }
        Slog.i("EdgeLightingSettingUtils", " wrong index.");
        setEdgeLightingBasicColorIndex(contentResolver, 3);
        Settings.Global.putInt(contentResolver, "lighting_color_backup_version", 3);
    }

    public static void resetAppCustomColor(Context context) {
        SharedPreferences.Editor edit = context.getSharedPreferences("edge_lighting_app_color", 0).edit();
        edit.clear();
        edit.putBoolean("app_color_changed", false);
        edit.apply();
    }

    public static void saveAppCustomColor(Context context, String str, int i) {
        SharedPreferences.Editor edit = context.getSharedPreferences("edge_lighting_app_color", 0).edit();
        edit.putInt(str, i);
        StringBuilder m = ActivityResultRegistry$$ExternalSyntheticOutline0.m("saveAppCustomColor pkgName : ", str, " Color : ");
        m.append(Integer.toHexString(i));
        Slog.i("EdgeLightingSettingUtils", m.toString());
        edit.apply();
    }

    public static void setEdgeLightingBasicColorIndex(ContentResolver contentResolver, int i) {
        Settings.System.putIntForUser(contentResolver, "edge_lighting_basic_color_index", i, -2);
    }

    public static void setEdgeLightingColorType(ContentResolver contentResolver, int i) {
        Settings.System.putIntForUser(contentResolver, "edge_lighting_color_type", i, -2);
    }
}
