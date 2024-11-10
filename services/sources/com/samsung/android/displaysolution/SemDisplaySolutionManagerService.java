package com.samsung.android.displaysolution;

import android.R;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.PowerManager;
import android.os.SystemProperties;
import android.provider.Settings;
import android.util.Slog;
import com.android.server.display.DisplayPowerController2;
import com.samsung.android.displaysolution.ISemDisplaySolutionManager;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.view.SemWindowManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/* loaded from: classes2.dex */
public class SemDisplaySolutionManagerService extends ISemDisplaySolutionManager.Stub {
    public static float RETURN_ERROR_F = -1.0f;
    public int DOU_BRIGHTNESS_STANDARD_VALUE;
    public int DOU_BRIGHTNESS_SUPPORT_VALUE;
    public int DOU_VERSION;
    public boolean SEC_FEATURE_EXTENDED_BRIGHTNESS;
    public AdaptiveDisplaySolutionService adsService;
    public BigDataLoggingService bdlsService;
    public EyeComfortSolutionService ecsService;
    public int mAfcType;
    public boolean mAutoCurrentLimitOffEnable;
    public String[] mBrightnessBacklightValueStringArray;
    public float[] mBrightnessCameraAppArray;
    public float[] mBrightnessExtraBrightnessArray;
    public float[] mBrightnessGalleryAppArray;
    public String[] mBrightnessNitsValueStringArray;
    public float[] mBrightnessOverHeatAppArray;
    public float[] mBrightnessSamsungVideoAppArray;
    public float[] mBrightnessVideoEnhancerArray;
    public String[] mBurnInScaleFactorStringArray;
    public float[] mBurnInScaleFactorValueArray;
    public String[] mCameraAppBrightnessStringArray;
    public boolean mCameraEnable;
    public final Context mContext;
    public boolean mDouAppEnable;
    public String[] mExtraBrightnessStringArray;
    public SemWindowManager.FoldStateListener mFoldStateListener;
    public String[] mGalleryAppBrightnessStringArray;
    public boolean mGalleryEnable;
    public float[] mGammaArray;
    public String[] mGammaStringArray;
    public boolean mMSCSEnable;
    public String mName;
    public String mOnPixelRatioValue;
    public String[] mOverHeatAppBrightnessStringArray;
    public float mPlatformBrightnessValue;
    public PowerManager mPowerManager;
    public String[] mSamsungVideoAppBrightnessStringArray;
    public float mScaleFactor;
    public String[] mScaleFactorStringArray;
    public float[] mScaleFactorValueArray;
    public int mScreenBrightnessExtendedMaximumConfig;
    public String mSettingValue;
    public SettingsObserver mSettingsObserver;
    public String mTitle;
    public boolean mUseAdaptiveDisplaySolutionServiceConfig;
    public boolean mUseBigDataLoggingServiceConfig;
    public boolean mUseEyeComfortSolutionServiceConfig;
    public boolean mUseMdnieScenarioControlServiceConfig;
    public boolean mVideoEnable;
    public String[] mVideoEnhancerBrightnessStringArray;
    public MdnieScenarioControlService mscsService;
    public String temp_APP_BRIGHTNESS_SCALE_ON;
    public String temp_BROWSER_APP_BRIGHTNESS_ON;
    public String temp_BURNIN_PREVENTION_ON;
    public String temp_COLOR_BLIND_ON;
    public String temp_EXTRA_BRIGHTNESS_ON;
    public String temp_REAL_HDR_ON;
    public final boolean DEBUG = "eng".equals(Build.TYPE);
    public final Object mLock = new Object();
    public boolean mIsFolded = false;
    public boolean mColorBlind = false;
    public boolean mRealHDR = false;
    public boolean mExtraBrightness = false;
    public boolean mAppBrightnessScale = false;
    public boolean mBrowserAppBrightnessControl = false;
    public boolean mBurnInPrevention = false;
    public boolean mAutoBrightnessModeEnabled = false;
    public boolean mAdaptiveScreenModeEnabled = false;
    public boolean mExtraBrightnessModeEnabled = false;
    public boolean mHighBrightnessModeEnabled = false;
    public boolean mAutoBrightnessMode = false;
    public float settingsValueEM = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
    public float settingsValuePSM = 1.0f;
    public float settingsValueUPSM = 1.0f;
    public float mBrightnessCompensation = 1.0f;
    public float mLastScaleFactorValue = 1.0f;
    public float mHighDynamicRangeScaleFactorValue = 1.0f;
    public String IRC_MODE_NODE = "/sys/class/lcd/panel/irc_mode";
    public String IRC_MODE_SUB_NODE = "/sys/class/lcd/panel1/irc_mode";
    public String NIGHT_DIM_MODE_NODE = "/sys/class/lcd/panel/night_dim";
    public String NIGHT_DIM_MODE_SUB_NODE = "/sys/class/lcd/panel1/night_dim";
    public String BURN_IN_APPLY_COUNT = "/efs/afc/apply_count";
    public String BURN_IN_APPLY_COUNT_SUB = "/efs/afc1/apply_count";
    public final String COLOR_BLIND_ON = "color_blind_on";
    public final String COLOR_BLIND_OFF = "color_blind_off";
    public final String REAL_HDR_ON = "real_hdr_on";
    public final String REAL_HDR_OFF = "real_hdr_off";
    public final String EXTRA_BRIGHTNESS_ON = "extra_brightness_on";
    public final String EXTRA_BRIGHTNESS_OFF = "extra_brightness_off";
    public final String BRIGHTNESS_SCALE_ON_1 = "brightness_scale_on_1";
    public final String BRIGHTNESS_SCALE_ON_2 = "brightness_scale_on_2";
    public final String BRIGHTNESS_SCALE_ON_3 = "brightness_scale_on_3";
    public final String BRIGHTNESS_SCALE_ON_4 = "brightness_scale_on_4";
    public final String BRIGHTNESS_SCALE_ON_5 = "brightness_scale_on_5";
    public final String BRIGHTNESS_SCALE_ON_6 = "brightness_scale_on_6";
    public final String BRIGHTNESS_SCALE_ON_7 = "brightness_scale_on_7";
    public final String BRIGHTNESS_SCALE_OFF = "brightness_scale_off";
    public final String BROWSER_APP_BRIGHTNESS_ON = "browser_brightness_on";
    public final String BROWSER_APP_BRIGHTNESS_OFF = "browser_brightness_off";
    public final String BURNIN_PREVENTION_ON = "burnin_prevention_on";
    public final String BURNIN_PREVENTION_OFF = "burnin_prevention_off";
    public final String SCREEN_MODE_AUTOMATIC_SETTING = "screen_mode_automatic_setting";
    public final String HIGH_BRIGHTNESS_MODE_PMS_ENTER = "high_brightness_mode_pms_enter";
    public final String mSCafeVersion = SystemProperties.get("ro.build.scafe.version");
    public final int mNumberDisplaySolutionScaleFactor = 1;
    public final int mNumberDisplaySolutionBrowserAppScaleFactorStep = 20;
    public final int mNumberDisplaySolutionBurnInScaleFactor = 10;
    public final int mNumberDisplaySolutionExtraBrightnessFactor = 1;
    public final int mNumberDisplaySolutionGalleryAppBrightnessFactor = 1;
    public final int mNumberDisplaySolutionCameraAppBrightnessFactor = 1;
    public final int mNumberDisplaySolutionSamsungVideoAppBrightnessFactor = 1;
    public final int mNumberDisplaySolutionOverHeatAppBrightnessFactor = 1;
    public final int mNumberDisplaySolutionVideoEnhancerBrightnessFactor = 2;
    public final int mNumberDisplaySolutionGammaFactor = 1;

    public SemDisplaySolutionManagerService(Context context) {
        this.mscsService = null;
        this.adsService = null;
        this.ecsService = null;
        this.bdlsService = null;
        this.mUseMdnieScenarioControlServiceConfig = false;
        this.mUseAdaptiveDisplaySolutionServiceConfig = false;
        this.mUseEyeComfortSolutionServiceConfig = false;
        this.mUseBigDataLoggingServiceConfig = false;
        this.mMSCSEnable = false;
        this.SEC_FEATURE_EXTENDED_BRIGHTNESS = false;
        this.DOU_BRIGHTNESS_SUPPORT_VALUE = 0;
        this.DOU_BRIGHTNESS_STANDARD_VALUE = 0;
        this.DOU_VERSION = 0;
        this.mAfcType = 0;
        this.mScreenBrightnessExtendedMaximumConfig = IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT;
        this.mContext = context;
        this.mUseMdnieScenarioControlServiceConfig = context.getResources().getBoolean(17891768);
        this.mUseAdaptiveDisplaySolutionServiceConfig = context.getResources().getBoolean(R.bool.config_allowTheaterModeWakeFromDock);
        this.mUseBigDataLoggingServiceConfig = context.getResources().getBoolean(R.bool.config_defaultRingtonePickerEnabled);
        this.mScreenBrightnessExtendedMaximumConfig = context.getResources().getInteger(R.integer.leanback_setup_alpha_forward_out_content_duration);
        this.mScaleFactorStringArray = context.getResources().getStringArray(R.array.config_longPressVibePattern);
        this.mBurnInScaleFactorStringArray = context.getResources().getStringArray(R.array.config_locationExtraPackageNames);
        this.mGammaStringArray = context.getResources().getStringArray(R.array.config_locationProviderPackageNames);
        this.mExtraBrightnessStringArray = context.getResources().getStringArray(R.array.config_screenBrighteningThresholds);
        this.mGalleryAppBrightnessStringArray = context.getResources().getStringArray(R.array.config_scrollBarrierVibePattern);
        this.mCameraAppBrightnessStringArray = context.getResources().getStringArray(R.array.config_globalActionsList);
        this.mSamsungVideoAppBrightnessStringArray = context.getResources().getStringArray(R.array.config_usbHostBlacklist);
        this.mOverHeatAppBrightnessStringArray = context.getResources().getStringArray(R.array.config_tether_wimax_regexs);
        this.mVideoEnhancerBrightnessStringArray = context.getResources().getStringArray(R.array.disallowed_apps_managed_user);
        this.mBrightnessBacklightValueStringArray = context.getResources().getStringArray(R.array.config_vvmSmsFilterRegexes);
        this.mBrightnessNitsValueStringArray = context.getResources().getStringArray(R.array.config_wakeonlan_supported_interfaces);
        this.DOU_BRIGHTNESS_SUPPORT_VALUE = context.getResources().getInteger(R.integer.config_fingerprintMaxTemplatesPerUser);
        this.DOU_BRIGHTNESS_STANDARD_VALUE = context.getResources().getInteger(R.integer.config_faceMaxTemplatesPerUser);
        this.DOU_VERSION = context.getResources().getInteger(R.integer.config_networkWakeupPacketMark);
        this.mAfcType = context.getResources().getInteger(R.integer.config_activityDefaultDur);
        if (SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_LCD_SUPPORT_BLUE_FILTER_ADAPTIVE_MODE", 0) > 0) {
            this.mUseEyeComfortSolutionServiceConfig = true;
        }
        if (this.mUseMdnieScenarioControlServiceConfig) {
            this.mMSCSEnable = true;
            this.mscsService = new MdnieScenarioControlService(context);
        }
        if (this.mUseAdaptiveDisplaySolutionServiceConfig) {
            this.adsService = new AdaptiveDisplaySolutionService(context);
        }
        if (this.mUseEyeComfortSolutionServiceConfig) {
            this.ecsService = new EyeComfortSolutionService(context);
        }
        if (this.mUseBigDataLoggingServiceConfig) {
            this.bdlsService = new BigDataLoggingService(context);
        }
        if (this.mScreenBrightnessExtendedMaximumConfig > 255) {
            this.SEC_FEATURE_EXTENDED_BRIGHTNESS = true;
        }
        Slog.d("SemDisplaySolutionManagerService", "mUseMdnieScenarioControlServiceConfig : " + this.mUseMdnieScenarioControlServiceConfig);
        Slog.d("SemDisplaySolutionManagerService", "mUseAdaptiveDisplaySolutionServiceConfig : " + this.mUseAdaptiveDisplaySolutionServiceConfig);
        Slog.d("SemDisplaySolutionManagerService", "mUseEyeComfortSolutionServiceConfig : " + this.mUseEyeComfortSolutionServiceConfig);
        Slog.d("SemDisplaySolutionManagerService", "mUseBigDataLoggingServiceConfig : " + this.mUseMdnieScenarioControlServiceConfig);
        Slog.d("SemDisplaySolutionManagerService", "mScreenBrightnessExtendedMaximumConfig : " + this.mScreenBrightnessExtendedMaximumConfig);
        this.mScaleFactorValueArray = new float[1];
        int i = 0;
        while (true) {
            String[] strArr = this.mScaleFactorStringArray;
            if (i >= strArr.length) {
                break;
            }
            this.mScaleFactorValueArray[i] = Float.valueOf(Float.parseFloat(strArr[i])).floatValue();
            i++;
        }
        this.mBurnInScaleFactorValueArray = new float[10];
        int i2 = 0;
        while (true) {
            String[] strArr2 = this.mBurnInScaleFactorStringArray;
            if (i2 >= strArr2.length) {
                break;
            }
            this.mBurnInScaleFactorValueArray[i2] = Float.valueOf(Float.parseFloat(strArr2[i2])).floatValue();
            i2++;
        }
        this.mBrightnessExtraBrightnessArray = new float[1];
        int i3 = 0;
        while (true) {
            String[] strArr3 = this.mExtraBrightnessStringArray;
            if (i3 >= strArr3.length) {
                break;
            }
            this.mBrightnessExtraBrightnessArray[i3] = Float.valueOf(Float.parseFloat(strArr3[i3])).floatValue();
            i3++;
        }
        this.mBrightnessGalleryAppArray = new float[1];
        int i4 = 0;
        while (true) {
            String[] strArr4 = this.mGalleryAppBrightnessStringArray;
            if (i4 >= strArr4.length) {
                break;
            }
            this.mBrightnessGalleryAppArray[i4] = Float.valueOf(Float.parseFloat(strArr4[i4])).floatValue();
            i4++;
        }
        this.mBrightnessCameraAppArray = new float[1];
        int i5 = 0;
        while (true) {
            String[] strArr5 = this.mCameraAppBrightnessStringArray;
            if (i5 >= strArr5.length) {
                break;
            }
            this.mBrightnessCameraAppArray[i5] = Float.valueOf(Float.parseFloat(strArr5[i5])).floatValue();
            i5++;
        }
        this.mBrightnessSamsungVideoAppArray = new float[1];
        int i6 = 0;
        while (true) {
            String[] strArr6 = this.mSamsungVideoAppBrightnessStringArray;
            if (i6 >= strArr6.length) {
                break;
            }
            this.mBrightnessSamsungVideoAppArray[i6] = Float.valueOf(Float.parseFloat(strArr6[i6])).floatValue();
            i6++;
        }
        this.mBrightnessOverHeatAppArray = new float[1];
        int i7 = 0;
        while (true) {
            String[] strArr7 = this.mOverHeatAppBrightnessStringArray;
            if (i7 >= strArr7.length) {
                break;
            }
            this.mBrightnessOverHeatAppArray[i7] = Float.valueOf(Float.parseFloat(strArr7[i7])).floatValue();
            i7++;
        }
        this.mBrightnessVideoEnhancerArray = new float[2];
        int i8 = 0;
        while (true) {
            String[] strArr8 = this.mVideoEnhancerBrightnessStringArray;
            if (i8 >= strArr8.length) {
                break;
            }
            this.mBrightnessVideoEnhancerArray[i8] = Float.valueOf(Float.parseFloat(strArr8[i8])).floatValue();
            i8++;
        }
        this.mGammaArray = new float[1];
        int i9 = 0;
        while (true) {
            String[] strArr9 = this.mGammaStringArray;
            if (i9 < strArr9.length) {
                this.mGammaArray[i9] = Float.valueOf(Float.parseFloat(strArr9[i9])).floatValue();
                i9++;
            } else {
                this.mSettingsObserver = new SettingsObserver(new Handler());
                ContentResolver contentResolver = this.mContext.getContentResolver();
                this.mPowerManager = (PowerManager) this.mContext.getSystemService("power");
                contentResolver.registerContentObserver(Settings.System.getUriFor("high_brightness_mode_pms_enter"), false, this.mSettingsObserver, -1);
                contentResolver.registerContentObserver(Settings.System.getUriFor("screen_mode_automatic_setting"), false, this.mSettingsObserver, -1);
                contentResolver.registerContentObserver(Settings.Secure.getUriFor("screen_extra_brightness"), false, this.mSettingsObserver, -1);
                contentResolver.registerContentObserver(Settings.System.getUriFor("screen_brightness_mode"), false, this.mSettingsObserver, -1);
                contentResolver.registerContentObserver(Settings.System.getUriFor("blue_light_filter"), false, this.mSettingsObserver, -1);
                contentResolver.registerContentObserver(Settings.System.getUriFor("blue_light_filter_night_dim"), false, this.mSettingsObserver, -1);
                registerDisplayStateListener();
                setting_is_changed();
                display_setting_value_check();
                return;
            }
        }
    }

    /* loaded from: classes2.dex */
    public final class SettingsObserver extends ContentObserver {
        public final Uri HIGH_BRIGHTNESS_MODE_PMS_ENTER_URI;
        public final Uri SCREEN_BRIGHTNESS_MODE_URI;
        public final Uri SCREEN_EXTRA_BRIGHTNESS_URI;
        public final Uri SCREEN_MODE_AUTOMATIC_SETTING_URI;
        public ContentResolver resolver;

        public SettingsObserver(Handler handler) {
            super(handler);
            this.resolver = SemDisplaySolutionManagerService.this.mContext.getContentResolver();
            this.HIGH_BRIGHTNESS_MODE_PMS_ENTER_URI = Settings.System.getUriFor("high_brightness_mode_pms_enter");
            this.SCREEN_MODE_AUTOMATIC_SETTING_URI = Settings.System.getUriFor("screen_mode_automatic_setting");
            this.SCREEN_EXTRA_BRIGHTNESS_URI = Settings.Secure.getUriFor("screen_extra_brightness");
            this.SCREEN_BRIGHTNESS_MODE_URI = Settings.System.getUriFor("screen_brightness_mode");
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri) {
            SemDisplaySolutionManagerService.this.setting_is_changed();
            if ((this.SCREEN_EXTRA_BRIGHTNESS_URI.equals(uri) || this.SCREEN_BRIGHTNESS_MODE_URI.equals(uri)) && SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_LCD_SUPPORT_EXTRA_BRIGHTNESS", false)) {
                Slog.d("SemDisplaySolutionManagerService", "mAutoBrightnessModeEnabled : " + SemDisplaySolutionManagerService.this.mAutoBrightnessModeEnabled + " , mExtraBrightnessModeEnabled : " + SemDisplaySolutionManagerService.this.mExtraBrightnessModeEnabled);
                if (!SemDisplaySolutionManagerService.this.mAutoBrightnessModeEnabled && SemDisplaySolutionManagerService.this.mExtraBrightnessModeEnabled) {
                    SemDisplaySolutionManagerService.this.setMultipleScreenBrightness("extra_brightness_on");
                } else {
                    SemDisplaySolutionManagerService.this.setMultipleScreenBrightness("extra_brightness_off");
                }
            }
            if (this.SCREEN_MODE_AUTOMATIC_SETTING_URI.equals(uri) || this.HIGH_BRIGHTNESS_MODE_PMS_ENTER_URI.equals(uri)) {
                Slog.d("SemDisplaySolutionManagerService", "mHighBrightnessModeEnabled : " + SemDisplaySolutionManagerService.this.mHighBrightnessModeEnabled + " , mAdaptiveScreenModeEnabled : " + SemDisplaySolutionManagerService.this.mAdaptiveScreenModeEnabled);
                if (!SemDisplaySolutionManagerService.this.mHighBrightnessModeEnabled && !SemDisplaySolutionManagerService.this.mAdaptiveScreenModeEnabled) {
                    SemDisplaySolutionManagerService.sysfsWrite(SemDisplaySolutionManagerService.this.IRC_MODE_NODE, 1);
                    if (new File(SemDisplaySolutionManagerService.this.IRC_MODE_SUB_NODE).exists()) {
                        SemDisplaySolutionManagerService.sysfsWrite(SemDisplaySolutionManagerService.this.IRC_MODE_SUB_NODE, 1);
                    }
                    Slog.d("SemDisplaySolutionManagerService", "IRC Mode : flat_gamma_mode");
                    return;
                }
                SemDisplaySolutionManagerService.sysfsWrite(SemDisplaySolutionManagerService.this.IRC_MODE_NODE, 0);
                if (new File(SemDisplaySolutionManagerService.this.IRC_MODE_SUB_NODE).exists()) {
                    SemDisplaySolutionManagerService.sysfsWrite(SemDisplaySolutionManagerService.this.IRC_MODE_SUB_NODE, 0);
                }
                Slog.d("SemDisplaySolutionManagerService", "IRC Mode : moderato_mode");
            }
        }
    }

    public final void registerDisplayStateListener() {
        Slog.d("SemDisplaySolutionManagerService", "registerDisplayStateListener");
        this.mFoldStateListener = new SemWindowManager.FoldStateListener() { // from class: com.samsung.android.displaysolution.SemDisplaySolutionManagerService.1
            public void onTableModeChanged(boolean z) {
            }

            public void onFoldStateChanged(boolean z) {
                synchronized (SemDisplaySolutionManagerService.this.mLock) {
                    SemDisplaySolutionManagerService.this.mIsFolded = z;
                    if (SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_LARGE_COVER_SCREEN", false)) {
                        if (!SemDisplaySolutionManagerService.this.mAutoBrightnessModeEnabled && SemDisplaySolutionManagerService.this.mExtraBrightnessModeEnabled && !SemDisplaySolutionManagerService.this.mIsFolded) {
                            SemDisplaySolutionManagerService.this.setMultipleScreenBrightness("extra_brightness_on");
                        } else {
                            SemDisplaySolutionManagerService.this.setMultipleScreenBrightness("extra_brightness_off");
                        }
                    }
                }
            }
        };
        SemWindowManager.getInstance().registerFoldStateListener(this.mFoldStateListener, (Handler) null);
    }

    public final void burn_in_brightness_compensation() {
        String str;
        if (new File(this.BURN_IN_APPLY_COUNT).exists() || new File(this.BURN_IN_APPLY_COUNT_SUB).exists()) {
            try {
                boolean z = this.mIsFolded;
                if (!z) {
                    str = getStringFromFile(this.BURN_IN_APPLY_COUNT);
                    Slog.d("SemDisplaySolutionManagerService", "burn_in_brightness_compensation() raw : " + str);
                } else if (z) {
                    str = getStringFromFile(this.BURN_IN_APPLY_COUNT_SUB);
                    Slog.d("SemDisplaySolutionManagerService", "burn_in_brightness_compensation() sub raw : " + str);
                } else {
                    str = null;
                }
                if (str != null) {
                    String[] split = str.trim().split(" ");
                    try {
                        if (split.length == 2) {
                            int i = this.mAfcType;
                            if (i == 2) {
                                this.mBrightnessCompensation = Float.valueOf(Float.parseFloat(split[0].trim()) * 0.01f).floatValue() + 1.0f;
                            } else if (i >= 3 && i <= 7) {
                                this.mBrightnessCompensation = Float.valueOf(Float.parseFloat(split[0].trim()) * 0.005f).floatValue() + 1.0f;
                            } else if (i >= 8) {
                                this.mBrightnessCompensation = Float.valueOf(Float.parseFloat(split[0].trim()) * 0.007f).floatValue() + 1.0f;
                            }
                        } else {
                            this.mBrightnessCompensation = 1.0f;
                        }
                        return;
                    } catch (NumberFormatException e) {
                        Slog.e("SemDisplaySolutionManagerService", "NumberFormatException : " + e);
                        return;
                    }
                }
                this.mBrightnessCompensation = 1.0f;
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public final void setting_is_changed() {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        this.mHighBrightnessModeEnabled = Settings.System.getIntForUser(contentResolver, "high_brightness_mode_pms_enter", 0, -2) == 1;
        this.mAdaptiveScreenModeEnabled = Settings.System.getIntForUser(contentResolver, "screen_mode_automatic_setting", 1, -2) == 1;
        this.mExtraBrightnessModeEnabled = Settings.Secure.getIntForUser(contentResolver, "screen_extra_brightness", 0, -2) == 1;
        this.mAutoBrightnessModeEnabled = Settings.System.getIntForUser(contentResolver, "screen_brightness_mode", 0, -2) == 1;
        Slog.d("SemDisplaySolutionManagerService", "mHighBrightnessModeEnabled : " + this.mHighBrightnessModeEnabled + " , mAdaptiveScreenModeEnabled : " + this.mAdaptiveScreenModeEnabled + " , mExtraBrightnessModeEnabled : " + this.mExtraBrightnessModeEnabled + " , mAutoBrightnessModeEnabled : " + this.mAutoBrightnessModeEnabled);
    }

    public final void display_setting_value_check() {
        if (SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_LCD_SUPPORT_EXTRA_BRIGHTNESS", false)) {
            Slog.d("SemDisplaySolutionManagerService", "mAutoBrightnessModeEnabled : " + this.mAutoBrightnessModeEnabled + " , mExtraBrightnessModeEnabled : " + this.mExtraBrightnessModeEnabled);
            if (!this.mAutoBrightnessModeEnabled && this.mExtraBrightnessModeEnabled) {
                setMultipleScreenBrightness("extra_brightness_on");
            } else {
                setMultipleScreenBrightness("extra_brightness_off");
            }
        }
        Slog.d("SemDisplaySolutionManagerService", "mHighBrightnessModeEnabled : " + this.mHighBrightnessModeEnabled + " , mAdaptiveScreenModeEnabled : " + this.mAdaptiveScreenModeEnabled);
        if (!this.mHighBrightnessModeEnabled && !this.mAdaptiveScreenModeEnabled) {
            sysfsWrite(this.IRC_MODE_NODE, 1);
            if (new File(this.IRC_MODE_SUB_NODE).exists()) {
                sysfsWrite(this.IRC_MODE_SUB_NODE, 1);
            }
            Slog.d("SemDisplaySolutionManagerService", "IRC Mode : flat_gamma_mode");
            return;
        }
        sysfsWrite(this.IRC_MODE_NODE, 0);
        if (new File(this.IRC_MODE_SUB_NODE).exists()) {
            sysfsWrite(this.IRC_MODE_SUB_NODE, 0);
        }
        Slog.d("SemDisplaySolutionManagerService", "IRC Mode : moderato_mode");
    }

    public final void controlScaleFactorValue(float f, String str, String str2) {
        Binder binder = new Binder();
        this.mScaleFactor = f;
        this.mTitle = str;
        this.mName = str2;
        burn_in_brightness_compensation();
        if (this.mTitle.equals("application") && this.SEC_FEATURE_EXTENDED_BRIGHTNESS && this.mScaleFactor * this.mBrightnessCompensation >= DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
            Slog.d("SemDisplaySolutionManagerService", "where : " + this.mTitle + " what : " + this.mName + " setScreenBrightnessScaleFactor(" + this.mScaleFactor + ") , mBrightnessCompensation : " + this.mBrightnessCompensation + " , PMS value : " + (this.mScaleFactor * this.mBrightnessCompensation));
            this.mPowerManager.setScreenBrightnessScaleFactor(this.mScaleFactor * this.mBrightnessCompensation, binder);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0076 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:33:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0080 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r0v1 */
    /* JADX WARN: Type inference failed for: r0v2, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r0v3 */
    /* JADX WARN: Type inference failed for: r0v5, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r4v1, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r5v5, types: [java.lang.String] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.String getStringFromFile(java.lang.String r9) {
        /*
            r8 = this;
            r8 = 128(0x80, float:1.794E-43)
            byte[] r0 = new byte[r8]
            r1 = 0
            r2 = r1
        L6:
            if (r2 >= r8) goto Ld
            r0[r2] = r1
            int r2 = r2 + 1
            goto L6
        Ld:
            java.lang.String r8 = "File Close error"
            java.lang.String r2 = "SemDisplaySolutionManagerService"
            r3 = 0
            if (r9 == 0) goto L24
            java.io.FileInputStream r4 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L1f java.lang.Exception -> L21
            java.io.File r5 = new java.io.File     // Catch: java.lang.Throwable -> L1f java.lang.Exception -> L21
            r5.<init>(r9)     // Catch: java.lang.Throwable -> L1f java.lang.Exception -> L21
            r4.<init>(r5)     // Catch: java.lang.Throwable -> L1f java.lang.Exception -> L21
            goto L25
        L1f:
            r9 = move-exception
            goto L7e
        L21:
            r9 = move-exception
            r0 = r3
            goto L45
        L24:
            r4 = r3
        L25:
            if (r4 == 0) goto L88
            int r9 = r4.read(r0)     // Catch: java.lang.Throwable -> L3f java.lang.Exception -> L42
            if (r9 <= 0) goto L37
            java.lang.String r5 = new java.lang.String     // Catch: java.lang.Exception -> L3b java.lang.Throwable -> L3f
            int r6 = r9 + (-1)
            java.nio.charset.Charset r7 = java.nio.charset.StandardCharsets.UTF_8     // Catch: java.lang.Exception -> L3b java.lang.Throwable -> L3f
            r5.<init>(r0, r1, r6, r7)     // Catch: java.lang.Exception -> L3b java.lang.Throwable -> L3f
            r3 = r5
        L37:
            r4.close()     // Catch: java.lang.Exception -> L3b java.lang.Throwable -> L3f
            goto L88
        L3b:
            r0 = move-exception
            r1 = r9
            r9 = r0
            goto L43
        L3f:
            r9 = move-exception
            r3 = r4
            goto L7e
        L42:
            r9 = move-exception
        L43:
            r0 = r3
            r3 = r4
        L45:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L1f
            r4.<init>()     // Catch: java.lang.Throwable -> L1f
            java.lang.String r5 = "Exception : "
            r4.append(r5)     // Catch: java.lang.Throwable -> L1f
            r4.append(r9)     // Catch: java.lang.Throwable -> L1f
            java.lang.String r5 = " , in : "
            r4.append(r5)     // Catch: java.lang.Throwable -> L1f
            r4.append(r3)     // Catch: java.lang.Throwable -> L1f
            java.lang.String r5 = " , value : "
            r4.append(r5)     // Catch: java.lang.Throwable -> L1f
            r4.append(r0)     // Catch: java.lang.Throwable -> L1f
            java.lang.String r5 = " , length : "
            r4.append(r5)     // Catch: java.lang.Throwable -> L1f
            r4.append(r1)     // Catch: java.lang.Throwable -> L1f
            java.lang.String r1 = r4.toString()     // Catch: java.lang.Throwable -> L1f
            android.util.Slog.e(r2, r1)     // Catch: java.lang.Throwable -> L1f
            r9.printStackTrace()     // Catch: java.lang.Throwable -> L1f
            if (r3 == 0) goto L92
            r3.close()     // Catch: java.lang.Exception -> L7a
            goto L92
        L7a:
            android.util.Slog.e(r2, r8)
            goto L92
        L7e:
            if (r3 == 0) goto L87
            r3.close()     // Catch: java.lang.Exception -> L84
            goto L87
        L84:
            android.util.Slog.e(r2, r8)
        L87:
            throw r9
        L88:
            if (r4 == 0) goto L91
            r4.close()     // Catch: java.lang.Exception -> L8e
            goto L91
        L8e:
            android.util.Slog.e(r2, r8)
        L91:
            r0 = r3
        L92:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.displaysolution.SemDisplaySolutionManagerService.getStringFromFile(java.lang.String):java.lang.String");
    }

    public static boolean sysfsWrite(String str, int i) {
        FileOutputStream fileOutputStream;
        IOException e;
        File file = new File(str);
        if (file.exists()) {
            try {
                try {
                    fileOutputStream = new FileOutputStream(file);
                    try {
                        fileOutputStream.write(Integer.toString(i).getBytes());
                        fileOutputStream.close();
                        return true;
                    } catch (IOException e2) {
                        e = e2;
                        e.printStackTrace();
                        try {
                            fileOutputStream.close();
                        } catch (Exception e3) {
                            e3.printStackTrace();
                        }
                        return false;
                    }
                } catch (FileNotFoundException e4) {
                    e4.printStackTrace();
                    return false;
                }
            } catch (IOException e5) {
                fileOutputStream = null;
                e = e5;
            }
        }
        return false;
    }

    public final boolean getting_auto_brightness_mode_enabled() {
        boolean z = Settings.System.getIntForUser(this.mContext.getContentResolver(), "screen_brightness_mode", 0, -2) == 1;
        this.mAutoBrightnessMode = z;
        return z;
    }

    public final float getting_platform_brightness_value() {
        float intForUser = Settings.System.getIntForUser(this.mContext.getContentResolver(), "screen_brightness", 0, -2);
        this.mPlatformBrightnessValue = intForUser;
        return intForUser;
    }

    public boolean getVideoModeEnable() {
        return this.mVideoEnable;
    }

    public boolean getGalleryModeEnable() {
        return this.mGalleryEnable;
    }

    public boolean getCameraModeEnable() {
        return this.mCameraEnable;
    }

    public boolean getDouAppModeEnable() {
        return this.mDouAppEnable;
    }

    public boolean getAutoCurrentLimitOffModeEnabled() {
        return this.mAutoCurrentLimitOffEnable;
    }

    public String getOnPixelRatioValueForPMS() {
        return this.mOnPixelRatioValue;
    }

    public int getVideoEnhancerSettingState(String str) {
        return this.mscsService.findVideoEnhancerSettingState(str);
    }

    public float getFingerPrintBacklightValue(int i) {
        int i2 = 0;
        while (true) {
            String[] strArr = this.mBrightnessNitsValueStringArray;
            if (i2 <= strArr.length - 1) {
                if (i <= Integer.parseInt(strArr[i2])) {
                    Slog.d("SemDisplaySolutionManagerService", "getFingerPrintBacklightValue() brightnessNits : " + i + " , FingerPrintBacklightValue : " + Float.parseFloat(this.mBrightnessBacklightValueStringArray[i2]) + " , mBrightnessNitsValueStringArray_Size : " + this.mBrightnessNitsValueStringArray.length);
                    return Float.parseFloat(this.mBrightnessBacklightValueStringArray[i2]);
                }
                i2++;
            } else {
                return RETURN_ERROR_F;
            }
        }
    }

    public float getAlphaMaskLevel(float f, float f2, float f3) {
        Slog.d("SemDisplaySolutionManagerService", "getAlphaMaskLevel() CurrentPlatformBrightnessValue : " + f + " , FingerPrintPlatformValue : " + f2 + " , br_ctrl : " + f3 + " , gamma : " + this.mGammaArray[0]);
        return calculateAlphaBlendingValue(getNitsFromBrightness(f), getNitsFromBrightness(f2), f3, this.mGammaArray[0]);
    }

    public final float getNitsFromBrightness(float f) {
        if (f >= DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
            return Float.parseFloat(this.mBrightnessNitsValueStringArray[(int) f]);
        }
        return RETURN_ERROR_F;
    }

    public final float calculateAlphaBlendingValue(float f, float f2, float f3, float f4) {
        Slog.d("SemDisplaySolutionManagerService", "calculateAlphaBlendingValue() currentNits : " + f + " , targetNits : " + f2 + " , br_ctrl : " + f3 + " , gamma : " + f4);
        return (float) (1.0d - Math.pow((f * f3) / f2, 1.0f / f4));
    }

    public boolean isMdnieScenarioControlServiceEnabled() {
        return this.mMSCSEnable;
    }

    public void onDetailVeiwStateChanged(boolean z) {
        synchronized (this.mLock) {
            if (z) {
                AdaptiveDisplaySolutionService adaptiveDisplaySolutionService = this.adsService;
                if (adaptiveDisplaySolutionService != null) {
                    adaptiveDisplaySolutionService.setGalleryDetailViewMode(z);
                }
            } else {
                AdaptiveDisplaySolutionService adaptiveDisplaySolutionService2 = this.adsService;
                if (adaptiveDisplaySolutionService2 != null) {
                    adaptiveDisplaySolutionService2.setGalleryDetailViewMode(z);
                }
            }
        }
    }

    public void onAutoCurrentLimitStateChanged(boolean z) {
        synchronized (this.mLock) {
            if (z) {
                AdaptiveDisplaySolutionService adaptiveDisplaySolutionService = this.adsService;
                if (adaptiveDisplaySolutionService != null) {
                    adaptiveDisplaySolutionService.setAutoCurrentLimitState(z);
                }
            } else {
                AdaptiveDisplaySolutionService adaptiveDisplaySolutionService2 = this.adsService;
                if (adaptiveDisplaySolutionService2 != null) {
                    adaptiveDisplaySolutionService2.setAutoCurrentLimitState(z);
                }
            }
        }
    }

    public void onAutoCurrentLimitStateChangedWithBrightness(boolean z) {
        synchronized (this.mLock) {
            if (z) {
                AdaptiveDisplaySolutionService adaptiveDisplaySolutionService = this.adsService;
                if (adaptiveDisplaySolutionService != null) {
                    adaptiveDisplaySolutionService.setAutoCurrentLimitStateWithBrightness(z);
                }
            } else {
                AdaptiveDisplaySolutionService adaptiveDisplaySolutionService2 = this.adsService;
                if (adaptiveDisplaySolutionService2 != null) {
                    adaptiveDisplaySolutionService2.setAutoCurrentLimitStateWithBrightness(z);
                }
            }
        }
    }

    public void onAutoCurrentLimitStateChangedInt(int i) {
        synchronized (this.mLock) {
            AdaptiveDisplaySolutionService adaptiveDisplaySolutionService = this.adsService;
            if (adaptiveDisplaySolutionService != null) {
                adaptiveDisplaySolutionService.setAutoCurrentLimitStateInt(i);
            }
        }
    }

    public void onAutoCurrentLimitOffMode(boolean z) {
        synchronized (this.mLock) {
            if (z) {
                if (this.adsService != null) {
                    this.mscsService.setAutoCurrentLimitOffMode(z);
                }
            } else if (this.adsService != null) {
                this.mscsService.setAutoCurrentLimitOffMode(z);
            }
        }
    }

    public void onBurnInPreventionDisabled(boolean z) {
        synchronized (this.mLock) {
            if (z) {
                if (this.adsService != null) {
                    this.mscsService.setAutoCurrentLimitOffModeSNS(z);
                }
            } else if (this.adsService != null) {
                this.mscsService.setAutoCurrentLimitOffModeSNS(z);
            }
        }
    }

    public void setVideoModeEnable(boolean z) {
        synchronized (this.mLock) {
            this.mVideoEnable = z;
        }
    }

    public void setGalleryModeEnable(boolean z) {
        synchronized (this.mLock) {
            this.mGalleryEnable = z;
        }
    }

    public void setCameraModeEnable(boolean z) {
        synchronized (this.mLock) {
            this.mCameraEnable = z;
        }
    }

    public void setDouAppModeEnable(boolean z) {
        synchronized (this.mLock) {
            this.mDouAppEnable = z;
        }
    }

    public void setAutoCurrentLimitOffModeEnabled(boolean z) {
        synchronized (this.mLock) {
            this.mAutoCurrentLimitOffEnable = z;
        }
    }

    public void setMdnieScenarioControlServiceEnable(boolean z) {
        synchronized (this.mLock) {
            this.mMSCSEnable = z;
        }
    }

    public void setHighDynamicRangeMode(boolean z) {
        synchronized (this.mLock) {
            MdnieScenarioControlService mdnieScenarioControlService = this.mscsService;
            if (mdnieScenarioControlService != null) {
                mdnieScenarioControlService.isHighDynamicRangeModeState(z);
            }
        }
    }

    public void setIRCompensationMode(boolean z) {
        synchronized (this.mLock) {
            if (z) {
                sysfsWrite(this.IRC_MODE_NODE, 0);
                Slog.d("SemDisplaySolutionManagerService", "setIRCompensationMode(" + z + ") IRC Mode : moderato_mode");
            } else if (this.mAdaptiveScreenModeEnabled) {
                sysfsWrite(this.IRC_MODE_NODE, 0);
                Slog.d("SemDisplaySolutionManagerService", "setIRCompensationMode(" + z + ") IRC Mode : moderato_mode , vivid mode :" + this.mAdaptiveScreenModeEnabled);
            } else {
                sysfsWrite(this.IRC_MODE_NODE, 1);
                Slog.d("SemDisplaySolutionManagerService", "setIRCompensationMode(" + z + ") IRC Mode : flat_gamma_mode , vivid mode :" + this.mAdaptiveScreenModeEnabled);
            }
        }
    }

    public void setEyeComfortWeightingFactor(float f) {
        synchronized (this.mLock) {
            EyeComfortSolutionService eyeComfortSolutionService = this.ecsService;
            if (eyeComfortSolutionService != null) {
                eyeComfortSolutionService.setEyeComfortWeightingFactorValue(f);
            }
        }
    }

    public void setVideoEnhancerSettingState(String str, int i) {
        synchronized (this.mLock) {
            MdnieScenarioControlService mdnieScenarioControlService = this.mscsService;
            if (mdnieScenarioControlService != null) {
                mdnieScenarioControlService.updateVideoEnhancerSettingState(str, i);
            }
        }
    }

    public void setSleepPatternBLF(String str, long j, long j2, float f) {
        synchronized (this.mLock) {
            EyeComfortSolutionService eyeComfortSolutionService = this.ecsService;
            if (eyeComfortSolutionService != null) {
                eyeComfortSolutionService.updateRubinSleepPattern(str, j, j2, f);
            }
        }
    }

    public void setBlfEnableTimeBySchedule(boolean z, int i) {
        synchronized (this.mLock) {
            EyeComfortSolutionService eyeComfortSolutionService = this.ecsService;
            if (eyeComfortSolutionService != null) {
                eyeComfortSolutionService.setBlfEnableTimeBySchedule(z, i);
            }
        }
    }

    public boolean isBlueLightFilterScheduledTime() {
        synchronized (this.mLock) {
            EyeComfortSolutionService eyeComfortSolutionService = this.ecsService;
            if (eyeComfortSolutionService == null) {
                return false;
            }
            return eyeComfortSolutionService.isBlueLightFilterScheduledTime();
        }
    }

    public void setEadIndexOffset(int i) {
        synchronized (this.mLock) {
            EyeComfortSolutionService eyeComfortSolutionService = this.ecsService;
            if (eyeComfortSolutionService != null) {
                eyeComfortSolutionService.setEadIndexOffset(i);
            }
        }
    }

    public int getBlfAdaptiveCurrentIndex() {
        synchronized (this.mLock) {
            EyeComfortSolutionService eyeComfortSolutionService = this.ecsService;
            if (eyeComfortSolutionService == null) {
                return -1;
            }
            return eyeComfortSolutionService.getBlfAdaptiveCurrentIndex();
        }
    }

    public void setScreenBrightnessForPreview(int i) {
        synchronized (this.mLock) {
        }
    }

    public void setMultipleScreenBrightnessValueForHDR(float f) {
        synchronized (this.mLock) {
            this.mHighDynamicRangeScaleFactorValue = f;
        }
    }

    public void setOnPixelRatioValueForPMS(String str) {
        synchronized (this.mLock) {
            this.mOnPixelRatioValue = str;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:51:0x01b3 A[Catch: all -> 0x07b6, TryCatch #0 {, blocks: (B:4:0x0013, B:6:0x003e, B:8:0x0042, B:10:0x004c, B:13:0x0058, B:15:0x0063, B:18:0x0070, B:20:0x007a, B:23:0x0086, B:25:0x0090, B:27:0x009a, B:29:0x00a4, B:31:0x00ae, B:33:0x00b8, B:35:0x00c2, B:37:0x00cc, B:40:0x00d7, B:42:0x00e1, B:45:0x00ec, B:47:0x00f6, B:49:0x01af, B:51:0x01b3, B:52:0x01db, B:54:0x01df, B:55:0x0203, B:57:0x0207, B:58:0x022f, B:62:0x0239, B:65:0x0254, B:72:0x029a, B:74:0x02aa, B:75:0x02c8, B:68:0x02ed, B:79:0x0334, B:81:0x0338, B:83:0x0342, B:84:0x037a, B:87:0x0388, B:89:0x03c2, B:91:0x03c6, B:93:0x03cb, B:95:0x03d1, B:97:0x03e6, B:98:0x041e, B:99:0x0462, B:101:0x046a, B:102:0x04a0, B:103:0x04d1, B:105:0x04db, B:107:0x04df, B:109:0x0509, B:110:0x0531, B:112:0x053b, B:114:0x053f, B:116:0x0569, B:117:0x0591, B:120:0x059d, B:122:0x05cf, B:123:0x05ff, B:126:0x060b, B:128:0x0612, B:129:0x06c7, B:130:0x06ef, B:132:0x06f9, B:133:0x0721, B:137:0x0729, B:139:0x0744, B:144:0x0789, B:145:0x0100, B:147:0x010a, B:148:0x0112, B:150:0x011c, B:151:0x0120, B:153:0x012a, B:154:0x0132, B:156:0x013c, B:157:0x0140, B:159:0x014a, B:160:0x014d, B:161:0x0154, B:163:0x015e, B:164:0x0165, B:166:0x016f, B:167:0x0172, B:169:0x017d, B:170:0x0184, B:172:0x018f, B:173:0x0192, B:175:0x019c, B:176:0x01a3, B:178:0x01ad, B:179:0x07b4), top: B:3:0x0013 }] */
    /* JADX WARN: Removed duplicated region for block: B:52:0x01db A[Catch: all -> 0x07b6, TryCatch #0 {, blocks: (B:4:0x0013, B:6:0x003e, B:8:0x0042, B:10:0x004c, B:13:0x0058, B:15:0x0063, B:18:0x0070, B:20:0x007a, B:23:0x0086, B:25:0x0090, B:27:0x009a, B:29:0x00a4, B:31:0x00ae, B:33:0x00b8, B:35:0x00c2, B:37:0x00cc, B:40:0x00d7, B:42:0x00e1, B:45:0x00ec, B:47:0x00f6, B:49:0x01af, B:51:0x01b3, B:52:0x01db, B:54:0x01df, B:55:0x0203, B:57:0x0207, B:58:0x022f, B:62:0x0239, B:65:0x0254, B:72:0x029a, B:74:0x02aa, B:75:0x02c8, B:68:0x02ed, B:79:0x0334, B:81:0x0338, B:83:0x0342, B:84:0x037a, B:87:0x0388, B:89:0x03c2, B:91:0x03c6, B:93:0x03cb, B:95:0x03d1, B:97:0x03e6, B:98:0x041e, B:99:0x0462, B:101:0x046a, B:102:0x04a0, B:103:0x04d1, B:105:0x04db, B:107:0x04df, B:109:0x0509, B:110:0x0531, B:112:0x053b, B:114:0x053f, B:116:0x0569, B:117:0x0591, B:120:0x059d, B:122:0x05cf, B:123:0x05ff, B:126:0x060b, B:128:0x0612, B:129:0x06c7, B:130:0x06ef, B:132:0x06f9, B:133:0x0721, B:137:0x0729, B:139:0x0744, B:144:0x0789, B:145:0x0100, B:147:0x010a, B:148:0x0112, B:150:0x011c, B:151:0x0120, B:153:0x012a, B:154:0x0132, B:156:0x013c, B:157:0x0140, B:159:0x014a, B:160:0x014d, B:161:0x0154, B:163:0x015e, B:164:0x0165, B:166:0x016f, B:167:0x0172, B:169:0x017d, B:170:0x0184, B:172:0x018f, B:173:0x0192, B:175:0x019c, B:176:0x01a3, B:178:0x01ad, B:179:0x07b4), top: B:3:0x0013 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void setMultipleScreenBrightness(java.lang.String r9) {
        /*
            Method dump skipped, instructions count: 1977
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.displaysolution.SemDisplaySolutionManagerService.setMultipleScreenBrightness(java.lang.String):void");
    }
}
