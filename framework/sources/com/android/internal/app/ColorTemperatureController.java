package com.android.internal.app;

import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Slog;
import com.android.internal.logging.MetricsLogger;

/* loaded from: classes4.dex */
public final class ColorTemperatureController {
    private static final boolean DEBUG = true;
    private static final String TAG = "ColorTemperatureController";
    private Callback mCallback;
    private final ContentObserver mContentObserver;
    private final Context mContext;
    private MetricsLogger mMetricsLogger;
    private final int mUserId;

    public ColorTemperatureController(Context context) {
        this(context, ActivityManager.getCurrentUser());
    }

    public ColorTemperatureController(Context context, int userId) {
        this.mContext = context.getApplicationContext();
        this.mUserId = userId;
        this.mContentObserver = new ContentObserver(new Handler(Looper.getMainLooper())) { // from class: com.android.internal.app.ColorTemperatureController.1
            AnonymousClass1(Handler handler) {
                super(handler);
            }

            @Override // android.database.ContentObserver
            public void onChange(boolean selfChange, Uri uri) {
                super.onChange(selfChange, uri);
                String setting = uri == null ? null : uri.getLastPathSegment();
                if (setting != null) {
                    ColorTemperatureController.this.onSettingChanged(setting);
                }
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.android.internal.app.ColorTemperatureController$1 */
    /* loaded from: classes4.dex */
    public class AnonymousClass1 extends ContentObserver {
        AnonymousClass1(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean selfChange, Uri uri) {
            super.onChange(selfChange, uri);
            String setting = uri == null ? null : uri.getLastPathSegment();
            if (setting != null) {
                ColorTemperatureController.this.onSettingChanged(setting);
            }
        }
    }

    public boolean isActivated() {
        return Settings.Secure.getIntForUser(this.mContext.getContentResolver(), Settings.Secure.COLOR_TEMP_DISPLAY_ACTIVATED, 0, this.mUserId) == 1;
    }

    public boolean setActivated(boolean z) {
        if (!z) {
            setColorTempLevel(getDefaultColorTempLevel());
        }
        return Settings.Secure.putIntForUser(this.mContext.getContentResolver(), Settings.Secure.COLOR_TEMP_DISPLAY_ACTIVATED, z ? 1 : 0, this.mUserId);
    }

    public int getColorTempLevel() {
        int colorTempLevel = Settings.Secure.getIntForUser(this.mContext.getContentResolver(), Settings.Secure.COLOR_TEMP_DISPLAY_TEMP_LEVEL, -1, this.mUserId);
        if (colorTempLevel == -1) {
            Slog.d(TAG, "Using default value for setting: color_temp_display_temp_level");
            return getDefaultColorTempLevel();
        }
        return colorTempLevel;
    }

    public boolean setColorTempLevel(int level) {
        return Settings.Secure.putIntForUser(this.mContext.getContentResolver(), Settings.Secure.COLOR_TEMP_DISPLAY_TEMP_LEVEL, level, this.mUserId);
    }

    public int getMinimumColorTempLevel() {
        return 0;
    }

    public int getMaximumColorTempLevel() {
        return 255;
    }

    public int getDefaultColorTempLevel() {
        return 116;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public void onSettingChanged(String setting) {
        char c;
        Slog.d(TAG, "onSettingChanged: " + setting);
        if (this.mCallback != null) {
            switch (setting.hashCode()) {
                case -1043773339:
                    if (setting.equals(Settings.Secure.COLOR_TEMP_DISPLAY_TEMP_LEVEL)) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 555991877:
                    if (setting.equals(Settings.Secure.COLOR_TEMP_DISPLAY_ACTIVATED)) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    this.mCallback.onActivated(isActivated());
                    return;
                case 1:
                    this.mCallback.onColorTempLevelChanged(getColorTempLevel());
                    return;
                default:
                    return;
            }
        }
    }

    public void setListener(Callback callback) {
        Callback oldCallback = this.mCallback;
        if (oldCallback != callback) {
            this.mCallback = callback;
            if (callback == null) {
                this.mContext.getContentResolver().unregisterContentObserver(this.mContentObserver);
            } else if (oldCallback == null) {
                ContentResolver cr = this.mContext.getContentResolver();
                cr.registerContentObserver(Settings.Secure.getUriFor(Settings.Secure.COLOR_TEMP_DISPLAY_ACTIVATED), false, this.mContentObserver, this.mUserId);
                cr.registerContentObserver(Settings.Secure.getUriFor(Settings.Secure.COLOR_TEMP_DISPLAY_TEMP_LEVEL), false, this.mContentObserver, this.mUserId);
            }
        }
    }

    private MetricsLogger getMetricsLogger() {
        if (this.mMetricsLogger == null) {
            this.mMetricsLogger = new MetricsLogger();
        }
        return this.mMetricsLogger;
    }

    public static boolean isAvailable(Context context) {
        return true;
    }

    /* loaded from: classes4.dex */
    public interface Callback {
        default void onActivated(boolean activated) {
        }

        default void onColorTempLevelChanged(int level) {
        }
    }
}
