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
public final class RgbGainAPIController {
    private static final boolean DEBUG = true;
    private static final String TAG = "RgbGainController";
    private Callback mCallback;
    private final ContentObserver mContentObserver;
    private final Context mContext;
    private MetricsLogger mMetricsLogger;
    private final int mUserId;

    public RgbGainAPIController(Context context) {
        this(context, ActivityManager.getCurrentUser());
    }

    public RgbGainAPIController(Context context, int userId) {
        this.mContext = context.getApplicationContext();
        this.mUserId = userId;
        this.mContentObserver = new ContentObserver(new Handler(Looper.getMainLooper())) { // from class: com.android.internal.app.RgbGainAPIController.1
            @Override // android.database.ContentObserver
            public void onChange(boolean selfChange, Uri uri) {
                super.onChange(selfChange, uri);
                String setting = uri == null ? null : uri.getLastPathSegment();
                if (setting != null) {
                    RgbGainAPIController.this.onSettingChanged(setting);
                }
            }
        };
    }

    public boolean isActivated() {
        return Settings.Secure.getIntForUser(this.mContext.getContentResolver(), Settings.Secure.RGB_GAIN_API_DISPLAY_ACTIVATED, 0, this.mUserId) == 1;
    }

    public boolean setActivated(boolean z) {
        if (!z) {
            setRgbGainRedLevel(getDefaultRgbGainLevel());
            setRgbGainGreenLevel(getDefaultRgbGainLevel());
            setRgbGainBlueLevel(getDefaultRgbGainLevel());
        }
        return Settings.Secure.putIntForUser(this.mContext.getContentResolver(), Settings.Secure.RGB_GAIN_API_DISPLAY_ACTIVATED, z ? 1 : 0, this.mUserId);
    }

    public int getRgbGainRedLevel() {
        int level = Settings.Secure.getIntForUser(this.mContext.getContentResolver(), Settings.Secure.RGB_GAIN_API_DISPLAY_RED_LEVEL, -1, this.mUserId);
        if (level == -1) {
            Slog.d(TAG, "Using default value for setting: rgb_gain_api_display_red_level");
            return getDefaultRgbGainLevel();
        }
        return level;
    }

    public int getRgbGainGreenLevel() {
        int level = Settings.Secure.getIntForUser(this.mContext.getContentResolver(), Settings.Secure.RGB_GAIN_API_DISPLAY_GREEN_LEVEL, -1, this.mUserId);
        if (level == -1) {
            Slog.d(TAG, "Using default value for setting: rgb_gain_api_display_green_level");
            return getDefaultRgbGainLevel();
        }
        return level;
    }

    public int getRgbGainBlueLevel() {
        int level = Settings.Secure.getIntForUser(this.mContext.getContentResolver(), Settings.Secure.RGB_GAIN_API_DISPLAY_BLUE_LEVEL, -1, this.mUserId);
        if (level == -1) {
            Slog.d(TAG, "Using default value for setting: rgb_gain_api_display_blue_level");
            return getDefaultRgbGainLevel();
        }
        return level;
    }

    public boolean setRgbGainRedLevel(int level) {
        return Settings.Secure.putIntForUser(this.mContext.getContentResolver(), Settings.Secure.RGB_GAIN_API_DISPLAY_RED_LEVEL, level, this.mUserId);
    }

    public boolean setRgbGainGreenLevel(int level) {
        return Settings.Secure.putIntForUser(this.mContext.getContentResolver(), Settings.Secure.RGB_GAIN_API_DISPLAY_GREEN_LEVEL, level, this.mUserId);
    }

    public boolean setRgbGainBlueLevel(int level) {
        return Settings.Secure.putIntForUser(this.mContext.getContentResolver(), Settings.Secure.RGB_GAIN_API_DISPLAY_BLUE_LEVEL, level, this.mUserId);
    }

    public int getMinimumRgbGainLevel() {
        return 0;
    }

    public int getMaximumRgbGainLevel() {
        return 255;
    }

    public int getDefaultRgbGainLevel() {
        return 255;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public void onSettingChanged(String setting) {
        char c;
        Slog.d(TAG, "onSettingChanged: " + setting);
        if (this.mCallback != null) {
            switch (setting.hashCode()) {
                case -2138609265:
                    if (setting.equals(Settings.Secure.RGB_GAIN_API_DISPLAY_BLUE_LEVEL)) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case -1675436639:
                    if (setting.equals(Settings.Secure.RGB_GAIN_API_DISPLAY_ACTIVATED)) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case -462333402:
                    if (setting.equals(Settings.Secure.RGB_GAIN_API_DISPLAY_RED_LEVEL)) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case -235364584:
                    if (setting.equals(Settings.Secure.RGB_GAIN_API_DISPLAY_GREEN_LEVEL)) {
                        c = 2;
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
                    this.mCallback.onRgbGainRedLevelChanged(getRgbGainRedLevel());
                    return;
                case 2:
                    this.mCallback.onRgbGainGreenLevelChanged(getRgbGainGreenLevel());
                    return;
                case 3:
                    this.mCallback.onRgbGainBlueLevelChanged(getRgbGainBlueLevel());
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
                return;
            }
            if (oldCallback == null) {
                ContentResolver cr = this.mContext.getContentResolver();
                cr.registerContentObserver(Settings.Secure.getUriFor(Settings.Secure.RGB_GAIN_API_DISPLAY_ACTIVATED), false, this.mContentObserver, this.mUserId);
                cr.registerContentObserver(Settings.Secure.getUriFor(Settings.Secure.RGB_GAIN_API_DISPLAY_RED_LEVEL), false, this.mContentObserver, this.mUserId);
                cr.registerContentObserver(Settings.Secure.getUriFor(Settings.Secure.RGB_GAIN_API_DISPLAY_GREEN_LEVEL), false, this.mContentObserver, this.mUserId);
                cr.registerContentObserver(Settings.Secure.getUriFor(Settings.Secure.RGB_GAIN_API_DISPLAY_BLUE_LEVEL), false, this.mContentObserver, this.mUserId);
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

        default void onRgbGainRedLevelChanged(int level) {
        }

        default void onRgbGainGreenLevelChanged(int level) {
        }

        default void onRgbGainBlueLevelChanged(int level) {
        }
    }
}
