package com.samsung.android.biometrics.app.setting;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.hardware.display.IDisplayManager;
import android.hardware.fingerprint.IFingerprintService;
import android.os.Binder;
import android.os.FileObserver;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;
import android.view.Display;
import com.android.internal.annotations.VisibleForTesting;
import com.samsung.android.biometrics.ISemBiometricSysUiDisplayStateCallback;
import com.samsung.android.biometrics.app.setting.DisplayStateManager;
import com.samsung.android.biometrics.app.setting.Utils;
import com.samsung.android.biometrics.app.setting.fingerprint.HbmListener;
import com.samsung.android.displaysolution.SemDisplaySolutionManager;
import com.samsung.android.hardware.display.IRefreshRateToken;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes.dex */
public final class DisplayStateManager implements DisplayManager.DisplayListener, Handler.Callback {

    @VisibleForTesting
    static final int AOD_DEFAULT_HIGH_BRIGHTNESS_NIT = 60;

    @VisibleForTesting
    static final int AOD_DEFAULT_LOW_BRIGHTNESS_NIT = 2;

    @VisibleForTesting
    static final String RESOURCES_NAME_AOD_BRIGHTNESS_VALUES = "config_aodBrightnessValues";

    @VisibleForTesting
    static final int TIME_DELAY_LIMIT_OFF = 500;
    private int[] mAodBrightnessValues;
    private final Handler mBgHandler;
    private final List<Callback> mCallbacks;
    private final Context mContext;
    private int mCurrentDisplayState;
    private int mCurrentRotation;
    private int mCurrentStateLogical;
    private final SemDisplaySolutionManager mDisplaySolutionManager;
    private final Set<String> mDozeRequesters;

    @VisibleForTesting
    FileObserver mFileObserve;
    private final Handler mHandler;
    private final List<HbmListener> mHbmListeners;
    private final Injector mInjector;
    private boolean mIsLimitedDisplayOn;
    private final List<LimitDisplayStateCallback> mLimitDisplayCallbacks;
    private long mLimitStatusOperatingTime;
    private IRefreshRateToken mPassiveModeToken;
    private int mPrevRotation;
    private IRefreshRateToken mRefreshRateToken;
    private Runnable mRunnableReleaseRefreshRate;
    private int mDisplayStateFromKeyguard = 0;
    private AtomicBoolean mIsLimitedDisplayInProgress = new AtomicBoolean(false);
    private final IBinder mTokenForPassiveMode = new Binder();
    private final IBinder mTokenForRefreshRate = new Binder();
    private final AtomicBoolean mVirtualHbmNode = new AtomicBoolean(false);

    @VisibleForTesting
    final ISemBiometricSysUiDisplayStateCallback mBioSysUiDisplayStateCallback = new AnonymousClass1();

    /* renamed from: com.samsung.android.biometrics.app.setting.DisplayStateManager$1, reason: invalid class name */
    final class AnonymousClass1 extends ISemBiometricSysUiDisplayStateCallback.Stub {
        AnonymousClass1() {
        }

        public final void onFinish(int i, int i2, int i3) {
            DisplayStateManager.this.mHandler.post(new DisplayStateManager$1$$ExternalSyntheticLambda0(this, i, 0));
        }

        public final void onStart(int i, int i2, int i3) {
            DisplayStateManager.this.mHandler.post(new DisplayStateManager$1$$ExternalSyntheticLambda0(this, i, 1));
        }
    }

    /* renamed from: com.samsung.android.biometrics.app.setting.DisplayStateManager$2, reason: invalid class name */
    final class AnonymousClass2 extends FileObserver {
        AnonymousClass2(File file) {
            super(file, DisplayStateManager.AOD_DEFAULT_LOW_BRIGHTNESS_NIT);
        }

        @Override // android.os.FileObserver
        public final void onEvent(int i, String str) {
            if (i != DisplayStateManager.AOD_DEFAULT_LOW_BRIGHTNESS_NIT) {
                return;
            }
            final boolean isEnabledHbm = DisplayStateManager.this.isEnabledHbm();
            DisplayStateManager.this.mHandler.post(new Runnable() { // from class: com.samsung.android.biometrics.app.setting.DisplayStateManager$2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    List list;
                    Runnable runnable;
                    Runnable runnable2;
                    DisplayStateManager.AnonymousClass2 anonymousClass2 = DisplayStateManager.AnonymousClass2.this;
                    boolean z = isEnabledHbm;
                    list = DisplayStateManager.this.mHbmListeners;
                    Iterator it = ((ArrayList) list).iterator();
                    while (it.hasNext()) {
                        ((HbmListener) it.next()).onHbmChanged(z);
                    }
                    if (z) {
                        return;
                    }
                    runnable = DisplayStateManager.this.mRunnableReleaseRefreshRate;
                    if (runnable != null) {
                        runnable2 = DisplayStateManager.this.mRunnableReleaseRefreshRate;
                        runnable2.run();
                        DisplayStateManager.this.mRunnableReleaseRefreshRate = null;
                    }
                }
            });
        }
    }

    public interface Callback {
        void onDisplayStateChanged(int i);

        void onRotationStateChanged(int i);
    }

    @VisibleForTesting
    public static class Injector {
        private final Context mContext;
        private DisplayManager mDisplayManager;
        private IFingerprintService mIFingerprintService;
        private PowerManager.WakeLock mPartialWakeLock;

        public Injector(Context context) {
            this.mContext = context;
        }

        final void acquireWakeLock(Context context) {
            if (this.mPartialWakeLock == null) {
                PowerManager.WakeLock newWakeLock = ((PowerManager) context.getSystemService(PowerManager.class)).newWakeLock(1, "BSS_DisplayStateManager:P");
                this.mPartialWakeLock = newWakeLock;
                newWakeLock.setReferenceCounted(false);
            }
            this.mPartialWakeLock.acquire(2000L);
        }

        final int getDisplayState() {
            if (this.mDisplayManager == null) {
                this.mDisplayManager = (DisplayManager) this.mContext.getSystemService(DisplayManager.class);
            }
            Display display = this.mDisplayManager.getDisplay(0);
            if (display != null) {
                return display.getState();
            }
            Log.w("BSS_DisplayStateManager", "default display is NULL");
            return 0;
        }

        public final IFingerprintService getIFingerprintService() {
            if (this.mIFingerprintService == null) {
                this.mIFingerprintService = IFingerprintService.Stub.asInterface(ServiceManager.getService("fingerprint"));
            }
            return this.mIFingerprintService;
        }

        final int getRotation() {
            return Utils.getRotation(this.mContext);
        }

        final boolean isRefreshRateNormalMode() {
            return Utils.getIntDb(this.mContext, "refresh_rate_mode", true, 0) == 0;
        }

        final void registerDisplayListener(DisplayManager.DisplayListener displayListener, Handler handler) {
            if (this.mDisplayManager == null) {
                this.mDisplayManager = (DisplayManager) this.mContext.getSystemService(DisplayManager.class);
            }
            this.mDisplayManager.registerDisplayListener(displayListener, handler);
        }

        public final void registerDisplayStateCallback(ISemBiometricSysUiDisplayStateCallback iSemBiometricSysUiDisplayStateCallback) {
            if (this.mIFingerprintService == null) {
                this.mIFingerprintService = IFingerprintService.Stub.asInterface(ServiceManager.getService("fingerprint"));
            }
            IFingerprintService iFingerprintService = this.mIFingerprintService;
            if (iFingerprintService == null) {
                Log.w("BSS_DisplayStateManager", "IFingerprintService is NULL");
                return;
            }
            try {
                iFingerprintService.semRegisterDisplayStateCallback(iSemBiometricSysUiDisplayStateCallback);
            } catch (Exception e) {
                DisplayStateManager$Injector$$ExternalSyntheticOutline0.m(e, new StringBuilder("registerDisplayStateCallbackForOpt: "), "BSS_DisplayStateManager");
            }
        }

        final void releaseWakeLock() {
            PowerManager.WakeLock wakeLock = this.mPartialWakeLock;
            if (wakeLock == null) {
                return;
            }
            wakeLock.release();
        }

        final void requestDisplayLimitState(boolean z) {
            if (this.mIFingerprintService == null) {
                this.mIFingerprintService = IFingerprintService.Stub.asInterface(ServiceManager.getService("fingerprint"));
            }
            IFingerprintService iFingerprintService = this.mIFingerprintService;
            if (iFingerprintService == null) {
                Log.w("BSS_DisplayStateManager", "IFingerprintService is NULL");
                return;
            }
            try {
                iFingerprintService.semBioSysUiRequest(1, z ? 1 : 0, 0L, (String) null);
            } catch (RemoteException e) {
                Log.w("BSS_DisplayStateManager", "enableDisplayLimitState: " + e.getMessage());
            }
        }

        final void unregisterDisplayListener(DisplayManager.DisplayListener displayListener) {
            DisplayManager displayManager = this.mDisplayManager;
            if (displayManager == null) {
                return;
            }
            displayManager.unregisterDisplayListener(displayListener);
        }

        public final void unregisterDisplayStateCallback() {
            if (this.mIFingerprintService == null) {
                this.mIFingerprintService = IFingerprintService.Stub.asInterface(ServiceManager.getService("fingerprint"));
            }
            IFingerprintService iFingerprintService = this.mIFingerprintService;
            if (iFingerprintService == null) {
                Log.w("BSS_DisplayStateManager", "IFingerprintService is NULL");
                return;
            }
            try {
                iFingerprintService.semUnregisterDisplayStateCallback();
            } catch (Exception e) {
                DisplayStateManager$Injector$$ExternalSyntheticOutline0.m(e, new StringBuilder("unregisterDisplayStateCallbackForOpt: "), "BSS_DisplayStateManager");
            }
        }
    }

    public interface LimitDisplayStateCallback {
        void onLimitDisplayStateChanged(boolean z);
    }

    public static /* synthetic */ void $r8$lambda$6_8cZpCi3B4a7EP8WkuwIxNQLP0(DisplayStateManager displayStateManager) {
        Iterator it = ((ArrayList) displayStateManager.mHbmListeners).iterator();
        while (it.hasNext()) {
            ((HbmListener) it.next()).onHbmChanged(displayStateManager.mVirtualHbmNode.get());
        }
    }

    public static /* synthetic */ void $r8$lambda$jufplp9SAEsLDjToPqv_ZMdXlgI(DisplayStateManager displayStateManager, LimitDisplayStateCallback limitDisplayStateCallback) {
        if (((ArrayList) displayStateManager.mLimitDisplayCallbacks).contains(limitDisplayStateCallback)) {
            return;
        }
        ((ArrayList) displayStateManager.mLimitDisplayCallbacks).add(limitDisplayStateCallback);
    }

    @VisibleForTesting
    DisplayStateManager(Context context, Handler handler, Looper looper, Injector injector) {
        int i = 0;
        this.mContext = context;
        this.mHandler = handler;
        this.mBgHandler = new Handler(looper, this);
        this.mInjector = injector;
        int rotation = injector.getRotation();
        this.mPrevRotation = rotation;
        this.mCurrentRotation = rotation;
        this.mCallbacks = new ArrayList();
        this.mLimitDisplayCallbacks = new ArrayList();
        this.mHbmListeners = new ArrayList();
        this.mDozeRequesters = new ArraySet(AOD_DEFAULT_LOW_BRIGHTNESS_NIT);
        this.mDisplaySolutionManager = (SemDisplaySolutionManager) context.getSystemService(SemDisplaySolutionManager.class);
        try {
            int[] intArray = context.getResources().getIntArray(context.getResources().getIdentifier(RESOURCES_NAME_AOD_BRIGHTNESS_VALUES, "array", "android"));
            this.mAodBrightnessValues = intArray;
            if (intArray.length >= AOD_DEFAULT_LOW_BRIGHTNESS_NIT) {
                if (Utils.DEBUG) {
                    StringBuilder sb = new StringBuilder();
                    int[] iArr = this.mAodBrightnessValues;
                    int length = iArr.length;
                    while (i < length) {
                        sb.append(iArr[i]);
                        sb.append(",");
                        i++;
                    }
                    Log.d("BSS_DisplayStateManager", "setAodBrightnessValues: " + sb.toString());
                    return;
                }
                return;
            }
            Log.e("BSS_DisplayStateManager", "setAodBrightnessValues: not matched!");
            if (Utils.DEBUG) {
                StringBuilder sb2 = new StringBuilder();
                int[] iArr2 = this.mAodBrightnessValues;
                int length2 = iArr2.length;
                while (i < length2) {
                    sb2.append(iArr2[i]);
                    sb2.append(",");
                    i++;
                }
                Log.d("BSS_DisplayStateManager", "setAodBrightnessValues: " + sb2.toString());
            }
            this.mAodBrightnessValues = null;
        } catch (Exception unused) {
            Log.e("BSS_DisplayStateManager", "Fail to get service array");
        }
    }

    private void handleDisplayStateLimit(boolean z) {
        try {
            if (z) {
                if (!this.mIsLimitedDisplayOn) {
                    if (this.mCurrentDisplayState == AOD_DEFAULT_LOW_BRIGHTNESS_NIT) {
                        return;
                    }
                    this.mInjector.requestDisplayLimitState(true);
                    this.mIsLimitedDisplayOn = true;
                    Log.i("BSS_DisplayStateManager", "setDisplayStateLimit: -> ON");
                    if (Utils.DEBUG) {
                        this.mLimitStatusOperatingTime = SystemClock.elapsedRealtime();
                    }
                    Iterator it = ((ArrayList) this.mLimitDisplayCallbacks).iterator();
                    while (it.hasNext()) {
                        ((LimitDisplayStateCallback) it.next()).onLimitDisplayStateChanged(true);
                    }
                }
            } else if (this.mIsLimitedDisplayOn) {
                this.mIsLimitedDisplayOn = false;
                Log.i("BSS_DisplayStateManager", "setDisplayStateLimit: -> OFF");
                this.mInjector.requestDisplayLimitState(false);
                if (Utils.DEBUG) {
                    Log.w("BSS_DisplayStateManager", "[[[[[ Limit Display operating time = " + (SystemClock.elapsedRealtime() - this.mLimitStatusOperatingTime) + " ms ]]]]]");
                    this.mLimitStatusOperatingTime = 0L;
                }
                Iterator it2 = ((ArrayList) this.mLimitDisplayCallbacks).iterator();
                while (it2.hasNext()) {
                    ((LimitDisplayStateCallback) it2.next()).onLimitDisplayStateChanged(false);
                }
            }
        } finally {
            this.mInjector.releaseWakeLock();
            this.mIsLimitedDisplayInProgress.set(false);
        }
    }

    public static String stateToString(int i) {
        return i != 0 ? i != 1 ? i != AOD_DEFAULT_LOW_BRIGHTNESS_NIT ? i != 3 ? i != 4 ? i != 1001 ? i != 1002 ? Integer.toString(i) : "GOING_TO_ON" : "GOING_TO_OFF" : "DOZE_SUSPEND" : "DOZE" : "ON" : "OFF" : "UNKNOWN";
    }

    public final void acquireRefreshRateForSeamlessMode() {
        int i;
        this.mRunnableReleaseRefreshRate = null;
        this.mInjector.getClass();
        IDisplayManager asInterface = IDisplayManager.Stub.asInterface(ServiceManager.getService("display"));
        if (asInterface != null) {
            try {
                if (this.mInjector.isRefreshRateNormalMode()) {
                    if (this.mPassiveModeToken == null) {
                        Log.i("BSS_DisplayStateManager", "acquirePassiveModeToken");
                        this.mPassiveModeToken = asInterface.acquirePassiveModeToken(this.mTokenForPassiveMode, "BSS_DisplayStateManager");
                    }
                    i = AOD_DEFAULT_HIGH_BRIGHTNESS_NIT;
                } else {
                    i = 120;
                }
                if (this.mRefreshRateToken == null) {
                    Log.i("BSS_DisplayStateManager", "acquireRefreshRateMinLimitToken : " + i);
                    this.mRefreshRateToken = asInterface.acquireRefreshRateMinLimitToken(this.mTokenForRefreshRate, i, "BSS_DisplayStateManager");
                }
            } catch (RemoteException unused) {
                Log.w("BSS_DisplayStateManager", "Error : acquireRefreshRateForSeamlessMode");
            }
        }
    }

    public final float getAlphaMaskLevel(float f, float f2) {
        return this.mDisplaySolutionManager.getAlphaMaskLevel(f, f2, 1.0f);
    }

    public final int getCurrentRotation() {
        return this.mCurrentRotation;
    }

    public final int getDisplayState() {
        return this.mCurrentDisplayState;
    }

    public final int getDisplayStateLogical() {
        return this.mCurrentStateLogical;
    }

    /* JADX WARN: Code restructure failed: missing block: B:28:0x003f, code lost:
    
        if (r1 != com.samsung.android.biometrics.app.setting.DisplayStateManager.AOD_DEFAULT_LOW_BRIGHTNESS_NIT) goto L15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0026, code lost:
    
        r5 = com.samsung.android.biometrics.app.setting.DisplayStateManager.AOD_DEFAULT_HIGH_BRIGHTNESS_NIT;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0024, code lost:
    
        if (r1 != com.samsung.android.biometrics.app.setting.DisplayStateManager.AOD_DEFAULT_LOW_BRIGHTNESS_NIT) goto L15;
     */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0048  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0051  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final float getFingerPrintBacklightValue(int r11) {
        /*
            r10 = this;
            boolean r0 = com.samsung.android.biometrics.app.setting.Utils.Config.FEATURE_SUPPORT_AOD
            if (r0 == 0) goto L57
            boolean r0 = r10.isOnState()
            if (r0 != 0) goto L57
            com.samsung.android.displaysolution.SemDisplaySolutionManager r0 = r10.mDisplaySolutionManager
            float r11 = (float) r11
            int[] r1 = r10.mAodBrightnessValues
            r2 = -1
            r3 = 60
            r4 = 2
            if (r1 == 0) goto L45
            int r1 = r1.length
            int r1 = r1 + r2
        L17:
            if (r1 < 0) goto L45
            r5 = 30
            r6 = 10
            r7 = 1
            if (r1 != 0) goto L2c
            if (r1 == 0) goto L2a
            if (r1 == r7) goto L28
            if (r1 == r4) goto L46
        L26:
            r5 = r3
            goto L46
        L28:
            r5 = r6
            goto L46
        L2a:
            r5 = r4
            goto L46
        L2c:
            int[] r8 = r10.mAodBrightnessValues
            r8 = r8[r1]
            if (r8 == 0) goto L42
            float r8 = (float) r8
            float r8 = r11 / r8
            r9 = 1065353216(0x3f800000, float:1.0)
            int r8 = (r8 > r9 ? 1 : (r8 == r9 ? 0 : -1))
            if (r8 < 0) goto L42
            if (r1 == 0) goto L2a
            if (r1 == r7) goto L28
            if (r1 == r4) goto L46
            goto L26
        L42:
            int r1 = r1 + (-1)
            goto L17
        L45:
            r5 = r2
        L46:
            if (r5 != r2) goto L51
            r10 = 1119092736(0x42b40000, float:90.0)
            int r10 = (r11 > r10 ? 1 : (r11 == r10 ? 0 : -1))
            if (r10 <= 0) goto L4f
            goto L52
        L4f:
            r3 = r4
            goto L52
        L51:
            r3 = r5
        L52:
            float r10 = r0.getFingerPrintBacklightValue(r3)
            return r10
        L57:
            float r10 = (float) r11
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.biometrics.app.setting.DisplayStateManager.getFingerPrintBacklightValue(int):float");
    }

    public final int getPrevRotation() {
        return this.mPrevRotation;
    }

    @VisibleForTesting
    void handleDisplayStateChanged(int i) {
        Log.i("BSS_DisplayStateManager", "handleDisplayStateChanged: " + stateToString(this.mCurrentDisplayState) + " -> " + stateToString(i));
        if (i == 1002) {
            i = AOD_DEFAULT_LOW_BRIGHTNESS_NIT;
        }
        if (this.mCurrentDisplayState == i) {
            return;
        }
        this.mCurrentDisplayState = i;
        if (i == AOD_DEFAULT_LOW_BRIGHTNESS_NIT) {
            ((ArraySet) this.mDozeRequesters).clear();
        }
        Iterator it = ((ArrayList) this.mCallbacks).iterator();
        while (it.hasNext()) {
            ((Callback) it.next()).onDisplayStateChanged(this.mCurrentDisplayState);
        }
        if (Utils.Config.FP_FEATURE_SENSOR_IS_OPTICAL && this.mCurrentDisplayState == AOD_DEFAULT_LOW_BRIGHTNESS_NIT) {
            setDisplayStateLimit(500L, false);
        }
    }

    @Override // android.os.Handler.Callback
    public final boolean handleMessage(Message message) {
        Log.d("BSS_DisplayStateManager", Utils.getLogFormat(message));
        int i = message.what;
        if (i == 1) {
            handleDisplayStateLimit(true);
        } else if (i == AOD_DEFAULT_LOW_BRIGHTNESS_NIT) {
            handleDisplayStateLimit(false);
        }
        return true;
    }

    public final boolean isDozeState() {
        int i = this.mCurrentDisplayState;
        return i == 3 || i == 4;
    }

    /* JADX WARN: Code restructure failed: missing block: B:39:0x0045, code lost:
    
        if (r0 == null) goto L28;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v12 */
    /* JADX WARN: Type inference failed for: r0v13 */
    /* JADX WARN: Type inference failed for: r0v3, types: [boolean] */
    /* JADX WARN: Type inference failed for: r0v4 */
    /* JADX WARN: Type inference failed for: r0v7, types: [java.io.FileInputStream] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isEnabledHbm() {
        /*
            r5 = this;
            boolean r0 = com.samsung.android.biometrics.app.setting.Utils.Config.FP_FEATURE_LOCAL_HBM
            if (r0 == 0) goto Lb
            java.util.concurrent.atomic.AtomicBoolean r5 = r5.mVirtualHbmNode
            boolean r5 = r5.get()
            return r5
        Lb:
            java.io.File r5 = new java.io.File
            java.lang.String r0 = "/sys/class/lcd/panel/actual_mask_brightness"
            r5.<init>(r0)
            boolean r0 = com.samsung.android.biometrics.app.setting.Utils.DEBUG
            boolean r0 = r5.exists()
            r1 = 0
            if (r0 != 0) goto L1c
            goto L4b
        L1c:
            java.io.FileInputStream r0 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L3c java.lang.Exception -> L3e
            r0.<init>(r5)     // Catch: java.lang.Throwable -> L3c java.lang.Exception -> L3e
            long r2 = r5.length()     // Catch: java.lang.Throwable -> L34 java.lang.Exception -> L37
            int r5 = (int) r2     // Catch: java.lang.Throwable -> L34 java.lang.Exception -> L37
            byte[] r5 = new byte[r5]     // Catch: java.lang.Throwable -> L34 java.lang.Exception -> L37
            int r2 = r0.read(r5)     // Catch: java.lang.Exception -> L32 java.lang.Throwable -> L34
            if (r2 >= 0) goto L47
            r0.close()     // Catch: java.lang.Exception -> L4b
            goto L4b
        L32:
            r1 = move-exception
            goto L42
        L34:
            r5 = move-exception
            r1 = r0
            goto L7d
        L37:
            r5 = move-exception
            r4 = r1
            r1 = r5
            r5 = r4
            goto L42
        L3c:
            r5 = move-exception
            goto L7d
        L3e:
            r5 = move-exception
            r0 = r1
            r1 = r5
            r5 = r0
        L42:
            r1.printStackTrace()     // Catch: java.lang.Throwable -> L34
            if (r0 == 0) goto L4a
        L47:
            r0.close()     // Catch: java.lang.Exception -> L4a
        L4a:
            r1 = r5
        L4b:
            r5 = 0
            if (r1 == 0) goto L7c
            java.lang.String r0 = new java.lang.String
            java.nio.charset.Charset r2 = java.nio.charset.StandardCharsets.UTF_8
            r0.<init>(r1, r2)
            java.lang.String r0 = r0.trim()
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "HBM="
            r1.<init>(r2)
            r1.append(r0)
            java.lang.String r1 = r1.toString()
            java.lang.String r2 = "BSS_DisplayStateManager"
            android.util.Log.i(r2, r1)
            int r1 = r0.length()
            r2 = 1
            if (r1 > r2) goto L7b
            char r0 = r0.charAt(r5)
            r1 = 48
            if (r0 == r1) goto L7c
        L7b:
            r5 = r2
        L7c:
            return r5
        L7d:
            if (r1 == 0) goto L82
            r1.close()     // Catch: java.lang.Exception -> L82
        L82:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.biometrics.app.setting.DisplayStateManager.isEnabledHbm():boolean");
    }

    public final boolean isLimitedDisplayInProgress() {
        return this.mIsLimitedDisplayInProgress.get();
    }

    public final boolean isOnState() {
        return this.mCurrentDisplayState == AOD_DEFAULT_LOW_BRIGHTNESS_NIT;
    }

    public final void onAodStart() {
        if (this.mCurrentDisplayState == 1001) {
            handleDisplayStateChanged(1);
        }
    }

    public final void onConfigurationChanged() {
        int rotation = this.mInjector.getRotation();
        this.mPrevRotation = this.mCurrentRotation;
        this.mCurrentRotation = rotation;
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public final void onDisplayChanged(int i) {
        int rotation;
        int i2;
        if (i == 0 && (rotation = this.mInjector.getRotation()) != (i2 = this.mCurrentRotation) && (i2 ^ rotation) == AOD_DEFAULT_LOW_BRIGHTNESS_NIT) {
            this.mPrevRotation = i2;
            this.mCurrentRotation = rotation;
            Iterator it = ((ArrayList) this.mCallbacks).iterator();
            while (it.hasNext()) {
                ((Callback) it.next()).onRotationStateChanged(rotation);
            }
        }
    }

    public final void onScreenOffFromKeyguard() {
        if (Utils.DEBUG) {
            Log.d("BSS_DisplayStateManager", "onScreenOffFromKeyguard");
        }
        this.mDisplayStateFromKeyguard = 1;
        if (this.mCurrentStateLogical == 1) {
            handleDisplayStateChanged(1);
        } else if (isOnState()) {
            handleDisplayStateChanged(1001);
        }
    }

    public final void onScreenOnFromKeyguard() {
        if (Utils.DEBUG) {
            Log.d("BSS_DisplayStateManager", "onScreenOnFromKeyguard");
        }
        this.mDisplayStateFromKeyguard = AOD_DEFAULT_LOW_BRIGHTNESS_NIT;
        handleDisplayStateChanged(AOD_DEFAULT_LOW_BRIGHTNESS_NIT);
    }

    public final void onScreenUnknownFromKeyguard() {
        if (Utils.DEBUG) {
            Log.d("BSS_DisplayStateManager", "onScreenUnknownFromKeyguard");
        }
        this.mDisplayStateFromKeyguard = 0;
    }

    public final void registerCallback(Callback callback) {
        if (callback == null || ((ArrayList) this.mCallbacks).contains(callback)) {
            return;
        }
        ((ArrayList) this.mCallbacks).add(callback);
    }

    public final void registerHbmListener(HbmListener hbmListener) {
        if (hbmListener == null) {
            return;
        }
        Log.i("BSS_DisplayStateManager", "registerHbmListener: " + hbmListener);
        if (((ArrayList) this.mHbmListeners).contains(hbmListener)) {
            return;
        }
        ((ArrayList) this.mHbmListeners).add(hbmListener);
    }

    public final void registerLimitDisplayStateCallback(LimitDisplayStateCallback limitDisplayStateCallback) {
        if (limitDisplayStateCallback == null) {
            return;
        }
        this.mBgHandler.post(new DisplayStateManager$$ExternalSyntheticLambda1(this, limitDisplayStateCallback, 1));
    }

    public final void releaseRefreshRateForSeamlessMode() {
        if (isEnabledHbm()) {
            this.mRunnableReleaseRefreshRate = new DisplayStateManager$$ExternalSyntheticLambda0(this, 0);
            return;
        }
        try {
            if (this.mPassiveModeToken != null) {
                Log.i("BSS_DisplayStateManager", "releasePassiveModeToken");
                this.mPassiveModeToken.release();
                this.mPassiveModeToken = null;
            }
            if (this.mRefreshRateToken != null) {
                Log.i("BSS_DisplayStateManager", "releaseRefreshRateMinLimitToken");
                this.mRefreshRateToken.release();
                this.mRefreshRateToken = null;
            }
        } catch (RemoteException unused) {
            Log.w("BSS_DisplayStateManager", "Error : releaseRefreshRateForSeamlessMode");
        }
    }

    public final void setDisplayStateLimit(boolean z) {
        setDisplayStateLimit(0L, z);
    }

    public final void setVirtualHbmNode(boolean z) {
        this.mVirtualHbmNode.set(z);
        this.mHandler.post(new DisplayStateManager$$ExternalSyntheticLambda0(this, 1));
    }

    public final void start() {
        this.mInjector.registerDisplayListener(this, this.mHandler);
        this.mInjector.registerDisplayStateCallback(this.mBioSysUiDisplayStateCallback);
        int displayState = this.mInjector.getDisplayState();
        this.mCurrentDisplayState = displayState;
        this.mCurrentStateLogical = displayState;
        updateDisplayState();
        if (!Utils.Config.FP_FEATURE_SENSOR_IS_OPTICAL || Utils.Config.FP_FEATURE_LOCAL_HBM) {
            return;
        }
        if (this.mFileObserve == null) {
            this.mFileObserve = new AnonymousClass2(new File("/sys/class/lcd/panel/actual_mask_brightness"));
        }
        this.mFileObserve.startWatching();
    }

    public final void stop() {
        FileObserver fileObserver;
        ((ArrayList) this.mCallbacks).clear();
        ((ArrayList) this.mLimitDisplayCallbacks).clear();
        ((ArrayList) this.mHbmListeners).clear();
        ((ArraySet) this.mDozeRequesters).clear();
        this.mInjector.unregisterDisplayListener(this);
        this.mInjector.unregisterDisplayStateCallback();
        if (Utils.Config.FP_FEATURE_SENSOR_IS_OPTICAL && (fileObserver = this.mFileObserve) != null) {
            fileObserver.stopWatching();
            this.mFileObserve = null;
        }
        Runnable runnable = this.mRunnableReleaseRefreshRate;
        if (runnable != null) {
            runnable.run();
            this.mRunnableReleaseRefreshRate = null;
        }
    }

    public final void turnOffDoze(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        Log.d("BSS_DisplayStateManager", "turnOffDoze from ".concat(str));
        ((ArraySet) this.mDozeRequesters).remove(str);
        if (((ArraySet) this.mDozeRequesters).isEmpty()) {
            try {
                this.mInjector.getIFingerprintService().semBioSysUiRequest(AOD_DEFAULT_LOW_BRIGHTNESS_NIT, 0, 0L, (String) null);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public final void turnOffDozeHLPM() {
        try {
            this.mInjector.getIFingerprintService().semBioSysUiRequest(3, 0, 0L, (String) null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public final void turnOnDoze(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        Log.d("BSS_DisplayStateManager", "turnOnDoze from ".concat(str));
        ((ArraySet) this.mDozeRequesters).add(str);
        try {
            this.mInjector.getIFingerprintService().semBioSysUiRequest(AOD_DEFAULT_LOW_BRIGHTNESS_NIT, 1, 0L, (String) null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public final void turnOnDozeHLPM() {
        try {
            this.mInjector.getIFingerprintService().semBioSysUiRequest(3, 1, 0L, (String) null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public final void unregisterHbmListener(HbmListener hbmListener) {
        if (hbmListener == null) {
            return;
        }
        ((ArrayList) this.mHbmListeners).remove(hbmListener);
    }

    public final void unregisterLimitDisplayStateCallback(LimitDisplayStateCallback limitDisplayStateCallback) {
        if (limitDisplayStateCallback == null) {
            return;
        }
        this.mBgHandler.post(new DisplayStateManager$$ExternalSyntheticLambda1(this, limitDisplayStateCallback, 0));
    }

    public final void updateDisplayState() {
        if (this.mCurrentDisplayState == AOD_DEFAULT_LOW_BRIGHTNESS_NIT) {
            Injector injector = this.mInjector;
            Context context = this.mContext;
            injector.getClass();
            if (!((PowerManager) context.getSystemService(PowerManager.class)).isInteractive()) {
                this.mCurrentDisplayState = 1001;
            }
        }
        if (Utils.DEBUG) {
            Log.i("BSS_DisplayStateManager", "DisplayStateManager#updateDisplayState: state=" + stateToString(this.mCurrentDisplayState));
        }
    }

    public final void updateDisplayStateInAuthenticationSucceeded() {
        if (Utils.DEBUG) {
            Log.d("BSS_DisplayStateManager", "updateDisplayStateInAuthenticationSucceeded");
        }
        if (isOnState()) {
            return;
        }
        handleDisplayStateChanged(this.mCurrentStateLogical);
    }

    private void setDisplayStateLimit(long j, boolean z) {
        this.mInjector.acquireWakeLock(this.mContext);
        this.mIsLimitedDisplayInProgress.set(true);
        if (z) {
            this.mBgHandler.removeMessages(AOD_DEFAULT_LOW_BRIGHTNESS_NIT);
            this.mBgHandler.sendEmptyMessageDelayed(1, j);
        } else {
            this.mBgHandler.removeMessages(1);
            this.mBgHandler.sendEmptyMessageDelayed(AOD_DEFAULT_LOW_BRIGHTNESS_NIT, j);
        }
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public final void onDisplayAdded(int i) {
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public final void onDisplayRemoved(int i) {
    }
}
