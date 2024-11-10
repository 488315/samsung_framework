package com.android.server.display;

import android.content.Context;
import android.database.ContentObserver;
import android.hardware.display.BrightnessInfo;
import android.hardware.display.DisplayManager;
import android.hardware.display.IDisplayManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.SemSystemProperties;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.MathUtils;
import android.util.Slog;
import android.util.TimeUtils;
import android.view.SurfaceControlHdrLayerInfoListener;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.display.DisplayDeviceConfig;
import com.android.server.display.DisplayManagerService;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.hardware.display.IRefreshRateToken;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.Iterator;

/* loaded from: classes2.dex */
public class HighBrightnessModeController {
    public static final boolean FEATURE_SUPPORT_HDR_SOLUTION = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_MMFW_SUPPORT_HW_HDR");
    public static final boolean FEATURE_SUPPORT_PHOTOHDR = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_MMFW_SUPPORT_PHOTOHDR");
    static final float HBM_TRANSITION_POINT_INVALID = Float.POSITIVE_INFINITY;
    public float mAmbientLux;
    public float mBrightness;
    public final float mBrightnessMax;
    public final float mBrightnessMin;
    public final DisplayManagerService.Clock mClock;
    public final Context mContext;
    public int mDisplayStatsId;
    public final Handler mHandler;
    public final Runnable mHbmChangeCallback;
    public DisplayDeviceConfig.HighBrightnessModeData mHbmData;
    public int mHbmMode;
    public int mHbmStatsState;
    public HdrBrightnessDeviceConfig mHdrBrightnessCfg;
    public HdrListener mHdrListener;
    public int mHeight;
    public HighBrightnessModeMetadata mHighBrightnessModeMetadata;
    public final Injector mInjector;
    public boolean mIsAutoBrightnessEnabled;
    public boolean mIsAutoBrightnessOffByState;
    public boolean mIsBlockedByLowPowerMode;
    public boolean mIsHdrLayerPresent;
    public boolean mIsInAllowedAmbientRange;
    public boolean mIsTimeAvailable;
    public int mLogLevel;
    public float mMaxDesiredHdrSdrRatio;
    public final Runnable mRecalcRunnable;
    public IBinder mRegisteredDisplayToken;
    public int mSDRDimming;
    public final SettingsObserver mSettingsObserver;
    public boolean mStaticVRR;
    public boolean mSupportHdrSolution;
    public boolean mSupportPhotoHdr;
    public IBinder mSurfaceFlinger;
    public float mThresRatio;
    public int mThrottlingReason;
    public float mUnthrottledBrightness;
    public boolean mUseTimeAllowance;
    public int mWidth;

    /* loaded from: classes2.dex */
    public interface HdrBrightnessDeviceConfig {
        float getHdrBrightnessFromSdr(float f, float f2);
    }

    public HighBrightnessModeController(Handler handler, int i, int i2, IBinder iBinder, String str, float f, float f2, DisplayDeviceConfig.HighBrightnessModeData highBrightnessModeData, HdrBrightnessDeviceConfig hdrBrightnessDeviceConfig, Runnable runnable, HighBrightnessModeMetadata highBrightnessModeMetadata, Context context) {
        this(new Injector(), handler, i, i2, iBinder, str, f, f2, highBrightnessModeData, hdrBrightnessDeviceConfig, runnable, highBrightnessModeMetadata, context);
    }

    public HighBrightnessModeController(Injector injector, Handler handler, int i, int i2, IBinder iBinder, String str, float f, float f2, DisplayDeviceConfig.HighBrightnessModeData highBrightnessModeData, HdrBrightnessDeviceConfig hdrBrightnessDeviceConfig, Runnable runnable, HighBrightnessModeMetadata highBrightnessModeMetadata, Context context) {
        this.mLogLevel = 0;
        this.mThresRatio = 0.9f;
        this.mSupportHdrSolution = false;
        this.mStaticVRR = false;
        this.mSupportPhotoHdr = false;
        this.mSurfaceFlinger = ServiceManager.getService("SurfaceFlinger");
        this.mSDRDimming = 0;
        this.mIsInAllowedAmbientRange = false;
        this.mIsTimeAvailable = false;
        this.mIsAutoBrightnessEnabled = false;
        this.mIsAutoBrightnessOffByState = false;
        this.mThrottlingReason = 0;
        this.mHbmMode = 0;
        this.mIsHdrLayerPresent = false;
        this.mMaxDesiredHdrSdrRatio = 1.0f;
        this.mIsBlockedByLowPowerMode = false;
        this.mHbmStatsState = 1;
        this.mUseTimeAllowance = false;
        this.mHighBrightnessModeMetadata = null;
        this.mInjector = injector;
        this.mContext = context;
        this.mClock = injector.getClock();
        this.mHandler = handler;
        this.mBrightness = f;
        this.mBrightnessMin = f;
        this.mBrightnessMax = f2;
        this.mHbmChangeCallback = runnable;
        this.mHighBrightnessModeMetadata = highBrightnessModeMetadata;
        this.mSettingsObserver = new SettingsObserver(handler);
        this.mRecalcRunnable = new Runnable() { // from class: com.android.server.display.HighBrightnessModeController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                HighBrightnessModeController.this.recalculateTimeAllowance();
            }
        };
        this.mHdrListener = new HdrListener();
        this.mLogLevel = Integer.parseInt(SemSystemProperties.get("debug.hbmc.log", "0"));
        final String str2 = SemSystemProperties.get("debug.hbmc.enforce.brt_up", "null");
        String str3 = SemSystemProperties.get("debug.hbmc.enforce.vrr", "null");
        this.mThresRatio = Float.parseFloat(SemSystemProperties.get("debug.hbmc.thres", "0.9"));
        int sDRDimmingEnalbe = getSDRDimmingEnalbe();
        this.mSDRDimming = sDRDimmingEnalbe;
        this.mSupportPhotoHdr = FEATURE_SUPPORT_PHOTOHDR;
        this.mThresRatio = sDRDimmingEnalbe > 0 ? 0.2f : this.mThresRatio;
        handler.post(new Runnable() { // from class: com.android.server.display.HighBrightnessModeController$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                HighBrightnessModeController.this.lambda$new$0(str2);
            }
        });
        String upperCase = str3.toUpperCase();
        upperCase.hashCode();
        if (upperCase.equals("ON")) {
            this.mStaticVRR = true;
            Slog.d("HighBrightnessModeController", "Enforce Static VRR @HDR");
        } else if (upperCase.equals("OFF")) {
            this.mStaticVRR = false;
            Slog.d("HighBrightnessModeController", "Prevent Static VRR @HDR");
        } else {
            this.mStaticVRR = false;
        }
        resetHbmData(i, i2, iBinder, str, highBrightnessModeData, hdrBrightnessDeviceConfig);
    }

    public /* synthetic */ void lambda$new$0(String str) {
        int[] supportedHdrTypes;
        String upperCase = str.toUpperCase();
        upperCase.hashCode();
        if (upperCase.equals("ON")) {
            this.mSupportHdrSolution = true;
            Slog.d("HighBrightnessModeController", "Enforce HighBrightnessMode @HDR");
        } else {
            if (upperCase.equals("OFF")) {
                this.mSupportHdrSolution = false;
                Slog.d("HighBrightnessModeController", "Prevent HighBrightnessMode @HDR");
            } else {
                boolean z = FEATURE_SUPPORT_HDR_SOLUTION;
                this.mSupportHdrSolution = z;
                if (!z && (supportedHdrTypes = ((DisplayManager) this.mContext.getSystemService(DisplayManager.class)).getDisplay(0).getHdrCapabilities().getSupportedHdrTypes()) != null) {
                    for (int i : supportedHdrTypes) {
                        if (i == 2 || i == 4 || i == 3) {
                            this.mSupportHdrSolution = true;
                            break;
                        }
                    }
                }
            }
        }
        Slog.i("HighBrightnessModeController", "mSupportHdrSolution: " + this.mSupportHdrSolution);
    }

    public void setAutoBrightnessEnabled(int i) {
        boolean z = i == 1;
        this.mIsAutoBrightnessOffByState = i == 3;
        if (!deviceSupportsHbm() || z == this.mIsAutoBrightnessEnabled) {
            return;
        }
        this.mIsAutoBrightnessEnabled = z;
        this.mIsInAllowedAmbientRange = false;
        recalculateTimeAllowance();
    }

    public float getCurrentBrightnessMin() {
        return this.mBrightnessMin;
    }

    public float getCurrentBrightnessMax() {
        if (!deviceSupportsHbm() || isCurrentlyAllowed()) {
            return this.mBrightnessMax;
        }
        return this.mHbmData.transitionPoint;
    }

    public float getNormalBrightnessMax() {
        return deviceSupportsHbm() ? this.mHbmData.transitionPoint : this.mBrightnessMax;
    }

    public float getHdrBrightnessValue() {
        HdrBrightnessDeviceConfig hdrBrightnessDeviceConfig = this.mHdrBrightnessCfg;
        if (hdrBrightnessDeviceConfig != null) {
            float hdrBrightnessFromSdr = hdrBrightnessDeviceConfig.getHdrBrightnessFromSdr(this.mBrightness, this.mMaxDesiredHdrSdrRatio);
            if (hdrBrightnessFromSdr != -1.0f) {
                return hdrBrightnessFromSdr;
            }
        }
        return MathUtils.map(getCurrentBrightnessMin(), getCurrentBrightnessMax(), this.mBrightnessMin, this.mBrightnessMax, this.mBrightness);
    }

    public void onAmbientLuxChange(float f) {
        this.mAmbientLux = f;
        if (deviceSupportsHbm() && this.mIsAutoBrightnessEnabled) {
            boolean z = f >= this.mHbmData.minimumLux;
            if (z != this.mIsInAllowedAmbientRange) {
                this.mIsInAllowedAmbientRange = z;
                recalculateTimeAllowance();
            }
        }
    }

    public void onBrightnessChanged(float f, float f2, int i) {
        if (deviceSupportsHbm()) {
            this.mBrightness = f;
            this.mUnthrottledBrightness = f2;
            this.mThrottlingReason = i;
            long runningStartTimeMillis = this.mHighBrightnessModeMetadata.getRunningStartTimeMillis();
            boolean z = runningStartTimeMillis != -1;
            boolean z2 = this.mBrightness > this.mHbmData.transitionPoint && !this.mIsHdrLayerPresent;
            if (z != z2) {
                long uptimeMillis = this.mClock.uptimeMillis();
                if (z2) {
                    this.mHighBrightnessModeMetadata.setRunningStartTimeMillis(uptimeMillis);
                } else {
                    this.mHighBrightnessModeMetadata.addHbmEvent(new HbmEvent(runningStartTimeMillis, uptimeMillis));
                    this.mHighBrightnessModeMetadata.setRunningStartTimeMillis(-1L);
                }
            }
            recalculateTimeAllowance();
        }
    }

    public int getHighBrightnessMode() {
        return this.mHbmMode;
    }

    public float getTransitionPoint() {
        return deviceSupportsHbm() ? this.mHbmData.transitionPoint : HBM_TRANSITION_POINT_INVALID;
    }

    public void stop() {
        registerHdrListener(null);
        this.mSettingsObserver.stopObserving();
    }

    public void setHighBrightnessModeMetadata(HighBrightnessModeMetadata highBrightnessModeMetadata) {
        this.mHighBrightnessModeMetadata = highBrightnessModeMetadata;
    }

    public void resetHbmData(int i, int i2, IBinder iBinder, String str, DisplayDeviceConfig.HighBrightnessModeData highBrightnessModeData, HdrBrightnessDeviceConfig hdrBrightnessDeviceConfig) {
        this.mWidth = i;
        this.mHeight = i2;
        this.mHbmData = highBrightnessModeData;
        this.mHdrBrightnessCfg = hdrBrightnessDeviceConfig;
        this.mDisplayStatsId = str.hashCode();
        unregisterHdrListener();
        this.mSettingsObserver.stopObserving();
        if (deviceSupportsHbm()) {
            registerHdrListener(iBinder);
            DisplayDeviceConfig.HighBrightnessModeData highBrightnessModeData2 = this.mHbmData;
            if (highBrightnessModeData2.timeWindowMillis == 0 && highBrightnessModeData2.timeMaxMillis == 0 && highBrightnessModeData2.timeMinMillis == 0) {
                this.mUseTimeAllowance = false;
                this.mIsTimeAvailable = true;
            } else {
                this.mUseTimeAllowance = true;
            }
            recalculateTimeAllowance();
            if (this.mHbmData.allowInLowPowerMode) {
                return;
            }
            this.mIsBlockedByLowPowerMode = false;
            this.mSettingsObserver.startObserving();
        }
    }

    public void dump(final PrintWriter printWriter) {
        this.mHandler.runWithScissors(new Runnable() { // from class: com.android.server.display.HighBrightnessModeController$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                HighBrightnessModeController.this.lambda$dump$1(printWriter);
            }
        }, 1000L);
    }

    public HdrListener getHdrListener() {
        return this.mHdrListener;
    }

    /* renamed from: dumpLocal */
    public final void lambda$dump$1(PrintWriter printWriter) {
        String str;
        printWriter.println("HighBrightnessModeController:");
        printWriter.println("  mBrightness=" + this.mBrightness);
        printWriter.println("  mUnthrottledBrightness=" + this.mUnthrottledBrightness);
        printWriter.println("  mThrottlingReason=" + BrightnessInfo.briMaxReasonToString(this.mThrottlingReason));
        printWriter.println("  mCurrentMin=" + getCurrentBrightnessMin());
        printWriter.println("  mCurrentMax=" + getCurrentBrightnessMax());
        StringBuilder sb = new StringBuilder();
        sb.append("  mHbmMode=");
        sb.append(BrightnessInfo.hbmToString(this.mHbmMode));
        if (this.mHbmMode == 2) {
            str = "(" + getHdrBrightnessValue() + ")";
        } else {
            str = "";
        }
        sb.append(str);
        printWriter.println(sb.toString());
        printWriter.println("  mHbmStatsState=" + hbmStatsStateToString(this.mHbmStatsState));
        printWriter.println("  mHbmData=" + this.mHbmData);
        StringBuilder sb2 = new StringBuilder();
        sb2.append("  mAmbientLux=");
        sb2.append(this.mAmbientLux);
        sb2.append(this.mIsAutoBrightnessEnabled ? "" : " (old/invalid)");
        printWriter.println(sb2.toString());
        printWriter.println("  mIsInAllowedAmbientRange=" + this.mIsInAllowedAmbientRange);
        printWriter.println("  mIsAutoBrightnessEnabled=" + this.mIsAutoBrightnessEnabled);
        printWriter.println("  mIsAutoBrightnessOffByState=" + this.mIsAutoBrightnessOffByState);
        printWriter.println("  mIsHdrLayerPresent=" + this.mIsHdrLayerPresent);
        printWriter.println("  mBrightnessMin=" + this.mBrightnessMin);
        printWriter.println("  mBrightnessMax=" + this.mBrightnessMax);
        printWriter.println("  remainingTime=" + calculateRemainingTime(this.mClock.uptimeMillis()));
        printWriter.println("  mIsTimeAvailable= " + this.mIsTimeAvailable);
        printWriter.println("  mRunningStartTimeMillis=" + TimeUtils.formatUptime(this.mHighBrightnessModeMetadata.getRunningStartTimeMillis()));
        printWriter.println("  mIsBlockedByLowPowerMode=" + this.mIsBlockedByLowPowerMode);
        printWriter.println("  width*height=" + this.mWidth + "*" + this.mHeight);
        printWriter.println("  mEvents=");
        long uptimeMillis = this.mClock.uptimeMillis();
        long runningStartTimeMillis = this.mHighBrightnessModeMetadata.getRunningStartTimeMillis();
        if (runningStartTimeMillis != -1) {
            uptimeMillis = dumpHbmEvent(printWriter, new HbmEvent(runningStartTimeMillis, uptimeMillis));
        }
        Iterator it = this.mHighBrightnessModeMetadata.getHbmEventQueue().iterator();
        while (it.hasNext()) {
            HbmEvent hbmEvent = (HbmEvent) it.next();
            if (uptimeMillis > hbmEvent.getEndTimeMillis()) {
                printWriter.println("    event: [normal brightness]: " + TimeUtils.formatDuration(uptimeMillis - hbmEvent.getEndTimeMillis()));
            }
            uptimeMillis = dumpHbmEvent(printWriter, hbmEvent);
        }
    }

    public final long dumpHbmEvent(PrintWriter printWriter, HbmEvent hbmEvent) {
        printWriter.println("    event: [" + TimeUtils.formatUptime(hbmEvent.getStartTimeMillis()) + ", " + TimeUtils.formatUptime(hbmEvent.getEndTimeMillis()) + "] (" + TimeUtils.formatDuration(hbmEvent.getEndTimeMillis() - hbmEvent.getStartTimeMillis()) + ")");
        return hbmEvent.getStartTimeMillis();
    }

    public final boolean isCurrentlyAllowed() {
        return this.mIsAutoBrightnessEnabled && this.mIsTimeAvailable && this.mIsInAllowedAmbientRange && !this.mIsBlockedByLowPowerMode;
    }

    public final boolean deviceSupportsHbm() {
        return this.mHbmData != null;
    }

    public final long calculateRemainingTime(long j) {
        long j2;
        if (!deviceSupportsHbm()) {
            return 0L;
        }
        long runningStartTimeMillis = this.mHighBrightnessModeMetadata.getRunningStartTimeMillis();
        if (runningStartTimeMillis > 0) {
            if (runningStartTimeMillis > j) {
                Slog.e("HighBrightnessModeController", "Start time set to the future. curr: " + j + ", start: " + runningStartTimeMillis);
                this.mHighBrightnessModeMetadata.setRunningStartTimeMillis(j);
                runningStartTimeMillis = j;
            }
            j2 = j - runningStartTimeMillis;
        } else {
            j2 = 0;
        }
        long j3 = j - this.mHbmData.timeWindowMillis;
        Iterator it = this.mHighBrightnessModeMetadata.getHbmEventQueue().iterator();
        while (it.hasNext()) {
            HbmEvent hbmEvent = (HbmEvent) it.next();
            if (hbmEvent.getEndTimeMillis() < j3) {
                it.remove();
            } else {
                j2 += hbmEvent.getEndTimeMillis() - Math.max(hbmEvent.getStartTimeMillis(), j3);
            }
        }
        return Math.max(0L, this.mHbmData.timeMaxMillis - j2);
    }

    public final void recalculateTimeAllowance() {
        long j;
        if (this.mUseTimeAllowance) {
            long uptimeMillis = this.mClock.uptimeMillis();
            long calculateRemainingTime = calculateRemainingTime(uptimeMillis);
            DisplayDeviceConfig.HighBrightnessModeData highBrightnessModeData = this.mHbmData;
            boolean z = true;
            boolean z2 = calculateRemainingTime >= highBrightnessModeData.timeMinMillis;
            boolean z3 = !z2 && calculateRemainingTime > 0 && this.mBrightness > highBrightnessModeData.transitionPoint;
            if (!z2 && !z3) {
                z = false;
            }
            this.mIsTimeAvailable = z;
            ArrayDeque hbmEventQueue = this.mHighBrightnessModeMetadata.getHbmEventQueue();
            if (this.mBrightness > this.mHbmData.transitionPoint) {
                j = uptimeMillis + calculateRemainingTime;
            } else if (this.mIsTimeAvailable || hbmEventQueue.size() <= 0) {
                j = -1;
            } else {
                long j2 = uptimeMillis - this.mHbmData.timeWindowMillis;
                j = (uptimeMillis + ((Math.max(j2, ((HbmEvent) hbmEventQueue.peekLast()).getStartTimeMillis()) + this.mHbmData.timeMinMillis) - j2)) - calculateRemainingTime;
            }
            if (j != -1) {
                this.mHandler.removeCallbacks(this.mRecalcRunnable);
                this.mHandler.postAtTime(this.mRecalcRunnable, j + 1);
            }
        }
        updateHbmMode();
    }

    public final void updateHbmMode() {
        int calculateHighBrightnessMode = calculateHighBrightnessMode();
        updateHbmStats(calculateHighBrightnessMode);
        if (this.mHbmMode != calculateHighBrightnessMode) {
            this.mHbmMode = calculateHighBrightnessMode;
            this.mHbmChangeCallback.run();
        }
    }

    public final void updateHbmStats(int i) {
        int i2 = 3;
        int i3 = (i != 2 || getHdrBrightnessValue() <= this.mHbmData.transitionPoint) ? (i != 1 || this.mBrightness <= this.mHbmData.transitionPoint) ? 1 : 3 : 2;
        int i4 = this.mHbmStatsState;
        if (i3 == i4) {
            return;
        }
        boolean z = i4 == 3;
        boolean z2 = i3 == 3;
        if (z && !z2) {
            boolean z3 = this.mIsAutoBrightnessEnabled;
            if (!z3 && this.mIsAutoBrightnessOffByState) {
                i2 = 6;
            } else if (!z3) {
                i2 = 7;
            } else if (!this.mIsInAllowedAmbientRange) {
                i2 = 1;
            } else if (!this.mIsTimeAvailable) {
                i2 = 2;
            } else if (!isThermalThrottlingActive()) {
                if (this.mIsHdrLayerPresent) {
                    i2 = 4;
                } else if (this.mIsBlockedByLowPowerMode) {
                    i2 = 5;
                } else if (this.mBrightness <= this.mHbmData.transitionPoint) {
                    i2 = 9;
                }
            }
            this.mInjector.reportHbmStateChange(this.mDisplayStatsId, i3, i2);
            this.mHbmStatsState = i3;
        }
        i2 = 0;
        this.mInjector.reportHbmStateChange(this.mDisplayStatsId, i3, i2);
        this.mHbmStatsState = i3;
    }

    public boolean isThermalThrottlingActive() {
        float f = this.mUnthrottledBrightness;
        float f2 = this.mHbmData.transitionPoint;
        return f > f2 && this.mBrightness <= f2 && this.mThrottlingReason == 1;
    }

    public final String hbmStatsStateToString(int i) {
        return i != 1 ? i != 2 ? i != 3 ? String.valueOf(i) : "HBM_ON_SUNLIGHT" : "HBM_ON_HDR" : "HBM_OFF";
    }

    public final int calculateHighBrightnessMode() {
        if (!deviceSupportsHbm()) {
            return 0;
        }
        if (this.mIsHdrLayerPresent) {
            return 2;
        }
        return isCurrentlyAllowed() ? 1 : 0;
    }

    public final void registerHdrListener(IBinder iBinder) {
        if (this.mRegisteredDisplayToken == iBinder) {
            return;
        }
        unregisterHdrListener();
        this.mRegisteredDisplayToken = iBinder;
        if (iBinder != null) {
            this.mHdrListener.register(iBinder);
        }
    }

    public final void unregisterHdrListener() {
        IBinder iBinder = this.mRegisteredDisplayToken;
        if (iBinder != null) {
            this.mHdrListener.unregister(iBinder);
            this.mIsHdrLayerPresent = false;
        }
    }

    public boolean isResolutionChanged(int i, int i2) {
        return (this.mWidth == i && this.mHeight == i2) ? false : true;
    }

    public void handleResolutionChange(int i, int i2) {
        this.mWidth = i;
        this.mHeight = i2;
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x005b  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0060  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int getSDRDimmingEnalbe() {
        /*
            r6 = this;
            java.lang.String r0 = "HighBrightnessModeController"
            android.os.Parcel r1 = android.os.Parcel.obtain()
            android.os.Parcel r2 = android.os.Parcel.obtain()
            if (r1 == 0) goto L58
            if (r2 == 0) goto L58
            android.os.IBinder r3 = r6.mSurfaceFlinger     // Catch: java.lang.Throwable -> L50
            if (r3 == 0) goto L58
            java.lang.String r3 = "android.ui.ISurfaceComposer"
            r1.writeInterfaceToken(r3)     // Catch: java.lang.Throwable -> L50
            r3 = 0
            android.os.IBinder r4 = r6.mSurfaceFlinger     // Catch: java.lang.Exception -> L21 java.lang.SecurityException -> L26 android.os.RemoteException -> L2c java.lang.Throwable -> L50
            r5 = 1135(0x46f, float:1.59E-42)
            boolean r3 = r4.transact(r5, r1, r2, r3)     // Catch: java.lang.Exception -> L21 java.lang.SecurityException -> L26 android.os.RemoteException -> L2c java.lang.Throwable -> L50
            goto L31
        L21:
            r4 = move-exception
            r4.printStackTrace()     // Catch: java.lang.Throwable -> L50
            goto L31
        L26:
            java.lang.String r4 = "SecurityException: Need system privilege"
            android.util.Slog.d(r0, r4)     // Catch: java.lang.Throwable -> L50
            goto L31
        L2c:
            java.lang.String r4 = "getSDRDimmingEnalbe() FAILED!"
            android.util.Slog.d(r0, r4)     // Catch: java.lang.Throwable -> L50
        L31:
            if (r3 == 0) goto L58
            int r3 = r2.readInt()     // Catch: java.lang.Throwable -> L50
            int r6 = r6.mLogLevel     // Catch: java.lang.Throwable -> L50
            if (r6 <= 0) goto L59
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L50
            r6.<init>()     // Catch: java.lang.Throwable -> L50
            java.lang.String r4 = "SDR Dimming OnOff: "
            r6.append(r4)     // Catch: java.lang.Throwable -> L50
            r6.append(r3)     // Catch: java.lang.Throwable -> L50
            java.lang.String r6 = r6.toString()     // Catch: java.lang.Throwable -> L50
            android.util.Slog.d(r0, r6)     // Catch: java.lang.Throwable -> L50
            goto L59
        L50:
            r6 = move-exception
            r1.recycle()
            r2.recycle()
            throw r6
        L58:
            r3 = -1
        L59:
            if (r1 == 0) goto L5e
            r1.recycle()
        L5e:
            if (r2 == 0) goto L63
            r2.recycle()
        L63:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.display.HighBrightnessModeController.getSDRDimmingEnalbe():int");
    }

    /* loaded from: classes2.dex */
    public class HdrListener extends SurfaceControlHdrLayerInfoListener {
        public boolean mIsBrightnessScaledUp = false;
        public boolean mIsBrightnessScaledUpPrev = false;
        public IDisplayManager mDisplayManager = IDisplayManager.Stub.asInterface(ServiceManager.getService("display"));
        public IRefreshRateToken mRefreshRateToken = null;
        public IBinder mToken = new Binder();

        public HdrListener() {
        }

        /* JADX WARN: Code restructure failed: missing block: B:17:0x00a0, code lost:
        
            if (r5 >= com.android.server.display.HighBrightnessModeController.this.mThresRatio) goto L97;
         */
        /* JADX WARN: Code restructure failed: missing block: B:18:0x00b1, code lost:
        
            r10 = false;
         */
        /* JADX WARN: Code restructure failed: missing block: B:19:0x00b2, code lost:
        
            r18.mIsBrightnessScaledUp = r10;
         */
        /* JADX WARN: Code restructure failed: missing block: B:20:0x00ba, code lost:
        
            if (com.android.server.display.HighBrightnessModeController.this.mLogLevel > 0) goto L105;
         */
        /* JADX WARN: Code restructure failed: missing block: B:22:0x00c0, code lost:
        
            if (r18.mIsBrightnessScaledUp == r18.mIsBrightnessScaledUpPrev) goto L104;
         */
        /* JADX WARN: Code restructure failed: missing block: B:23:0x00c3, code lost:
        
            r7 = r17;
         */
        /* JADX WARN: Code restructure failed: missing block: B:54:0x00c7, code lost:
        
            r7 = r17;
            android.util.Slog.d(r7, "brt scaled up: " + r18.mIsBrightnessScaledUp + ", SDR Dimming: " + com.android.server.display.HighBrightnessModeController.this.mSDRDimming + ", HdrLayerSize: " + r21 + "x" + r22 + "(" + r0 + "), DisplaySize: " + com.android.server.display.HighBrightnessModeController.this.mWidth + "x" + com.android.server.display.HighBrightnessModeController.this.mHeight + "(" + r2 + "), deviceRatio: " + r7 + " maxAvailSize: " + r3 + "x" + r1 + "(" + r4 + "), availRatio: " + r5 + "(" + com.android.server.display.HighBrightnessModeController.this.mThresRatio + ")");
         */
        /* JADX WARN: Code restructure failed: missing block: B:55:0x00af, code lost:
        
            r10 = true;
         */
        /* JADX WARN: Code restructure failed: missing block: B:57:0x00ad, code lost:
        
            if (r7 >= com.android.server.display.HighBrightnessModeController.this.mThresRatio) goto L97;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void onHdrInfoChanged(android.os.IBinder r19, final int r20, final int r21, final int r22, int r23, final float r24) {
            /*
                Method dump skipped, instructions count: 732
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.display.HighBrightnessModeController.HdrListener.onHdrInfoChanged(android.os.IBinder, int, int, int, int, float):void");
        }

        public /* synthetic */ void lambda$onHdrInfoChanged$0(int i, int i2, int i3, float f) {
            HighBrightnessModeController highBrightnessModeController = HighBrightnessModeController.this;
            highBrightnessModeController.mIsHdrLayerPresent = i > 0 && ((float) (i2 * i3)) >= ((float) (highBrightnessModeController.mWidth * HighBrightnessModeController.this.mHeight)) * HighBrightnessModeController.this.mThresRatio;
            if (!HighBrightnessModeController.this.mIsHdrLayerPresent || HighBrightnessModeController.this.mHdrBrightnessCfg == null) {
                f = 1.0f;
            }
            if (f >= 1.0f) {
                HighBrightnessModeController.this.mMaxDesiredHdrSdrRatio = f;
            } else {
                Slog.w("HighBrightnessModeController", "Ignoring invalid desired HDR/SDR Ratio: " + f);
                HighBrightnessModeController.this.mMaxDesiredHdrSdrRatio = 1.0f;
            }
            HighBrightnessModeController highBrightnessModeController2 = HighBrightnessModeController.this;
            highBrightnessModeController2.onBrightnessChanged(highBrightnessModeController2.mBrightness, HighBrightnessModeController.this.mUnthrottledBrightness, HighBrightnessModeController.this.mThrottlingReason);
        }

        public /* synthetic */ void lambda$onHdrInfoChanged$1() {
            HighBrightnessModeController.this.mIsHdrLayerPresent = this.mIsBrightnessScaledUp;
            HighBrightnessModeController highBrightnessModeController = HighBrightnessModeController.this;
            highBrightnessModeController.onBrightnessChanged(highBrightnessModeController.mBrightness, HighBrightnessModeController.this.mUnthrottledBrightness, HighBrightnessModeController.this.mThrottlingReason);
        }
    }

    /* loaded from: classes2.dex */
    public final class SettingsObserver extends ContentObserver {
        public final Uri mLowPowerModeSetting;
        public boolean mStarted;

        public SettingsObserver(Handler handler) {
            super(handler);
            this.mLowPowerModeSetting = Settings.Global.getUriFor("low_power");
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri) {
            updateLowPower();
        }

        public void startObserving() {
            if (this.mStarted) {
                return;
            }
            HighBrightnessModeController.this.mContext.getContentResolver().registerContentObserver(this.mLowPowerModeSetting, false, this, -1);
            this.mStarted = true;
            updateLowPower();
        }

        public void stopObserving() {
            HighBrightnessModeController.this.mIsBlockedByLowPowerMode = false;
            if (this.mStarted) {
                HighBrightnessModeController.this.mContext.getContentResolver().unregisterContentObserver(this);
                this.mStarted = false;
            }
        }

        public final void updateLowPower() {
            boolean isLowPowerMode = isLowPowerMode();
            if (isLowPowerMode == HighBrightnessModeController.this.mIsBlockedByLowPowerMode) {
                return;
            }
            HighBrightnessModeController.this.mIsBlockedByLowPowerMode = isLowPowerMode;
            HighBrightnessModeController.this.updateHbmMode();
        }

        public final boolean isLowPowerMode() {
            return Settings.Global.getInt(HighBrightnessModeController.this.mContext.getContentResolver(), "low_power", 0) != 0;
        }
    }

    /* loaded from: classes2.dex */
    public class Injector {
        public DisplayManagerService.Clock getClock() {
            return new DisplayManagerService.Clock() { // from class: com.android.server.display.HighBrightnessModeController$Injector$$ExternalSyntheticLambda0
                @Override // com.android.server.display.DisplayManagerService.Clock
                public final long uptimeMillis() {
                    return SystemClock.uptimeMillis();
                }
            };
        }

        public void reportHbmStateChange(int i, int i2, int i3) {
            FrameworkStatsLog.write(FrameworkStatsLog.DISPLAY_HBM_STATE_CHANGED, i, i2, i3);
        }
    }
}
