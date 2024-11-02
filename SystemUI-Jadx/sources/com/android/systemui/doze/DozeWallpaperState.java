package com.android.systemui.doze;

import android.app.IWallpaperManager;
import android.util.Log;
import com.android.keyguard.KeyguardClockSwitchController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.doze.DozeMachine;
import com.android.systemui.statusbar.phone.BiometricUnlockController;
import com.android.systemui.statusbar.phone.DozeParameters;
import java.io.PrintWriter;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class DozeWallpaperState implements DozeMachine.Part {
    public static final boolean DEBUG = Log.isLoggable("DozeWallpaperState", 3);
    public final BiometricUnlockController mBiometricUnlockController;
    public final DozeParameters mDozeParameters;
    public boolean mIsAmbientMode;
    public final IWallpaperManager mWallpaperManagerService;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* renamed from: com.android.systemui.doze.DozeWallpaperState$1, reason: invalid class name */
    /* loaded from: classes.dex */
    public abstract /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$android$systemui$doze$DozeMachine$State;

        static {
            int[] iArr = new int[DozeMachine.State.values().length];
            $SwitchMap$com$android$systemui$doze$DozeMachine$State = iArr;
            try {
                iArr[DozeMachine.State.DOZE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE_AOD.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE_AOD_DOCKED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE_AOD_PAUSING.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE_AOD_PAUSED.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE_REQUEST_PULSE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE_PULSE_DONE.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE_PULSING.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE_PULSING_BRIGHT.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
        }
    }

    public DozeWallpaperState(IWallpaperManager iWallpaperManager, BiometricUnlockController biometricUnlockController, DozeParameters dozeParameters) {
        this.mWallpaperManagerService = iWallpaperManager;
        this.mBiometricUnlockController = biometricUnlockController;
        this.mDozeParameters = dozeParameters;
    }

    @Override // com.android.systemui.doze.DozeMachine.Part
    public final void dump(PrintWriter printWriter) {
        boolean z;
        StringBuilder m = KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(printWriter, "DozeWallpaperState:", " isAmbientMode: "), this.mIsAmbientMode, printWriter, " hasWallpaperService: ");
        if (this.mWallpaperManagerService != null) {
            z = true;
        } else {
            z = false;
        }
        KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(m, z, printWriter);
    }

    /* JADX WARN: Code restructure failed: missing block: B:38:0x003d, code lost:
    
        if (r9 != false) goto L25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0041, code lost:
    
        r3 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0042, code lost:
    
        r8 = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x003f, code lost:
    
        if (r8 != false) goto L26;
     */
    @Override // com.android.systemui.doze.DozeMachine.Part
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void transitionTo(com.android.systemui.doze.DozeMachine.State r8, com.android.systemui.doze.DozeMachine.State r9) {
        /*
            r7 = this;
            java.lang.String r0 = "DozeWallpaperState"
            java.lang.String r1 = "AOD wallpaper state changed to: "
            int[] r2 = com.android.systemui.doze.DozeWallpaperState.AnonymousClass1.$SwitchMap$com$android$systemui$doze$DozeMachine$State
            int r3 = r9.ordinal()
            r2 = r2[r3]
            r3 = 0
            r4 = 1
            switch(r2) {
                case 1: goto L13;
                case 2: goto L13;
                case 3: goto L13;
                case 4: goto L13;
                case 5: goto L13;
                case 6: goto L13;
                case 7: goto L13;
                case 8: goto L13;
                default: goto L11;
            }
        L11:
            r2 = r3
            goto L14
        L13:
            r2 = r4
        L14:
            com.android.systemui.statusbar.phone.DozeParameters r5 = r7.mDozeParameters
            if (r2 == 0) goto L1b
            boolean r8 = r5.mControlScreenOffAnimation
            goto L43
        L1b:
            com.android.systemui.doze.DozeMachine$State r6 = com.android.systemui.doze.DozeMachine.State.DOZE_PULSING
            if (r8 != r6) goto L25
            com.android.systemui.doze.DozeMachine$State r8 = com.android.systemui.doze.DozeMachine.State.FINISH
            if (r9 != r8) goto L25
            r8 = r4
            goto L26
        L25:
            r8 = r3
        L26:
            boolean r9 = r5.getDisplayNeedsBlanking()
            r9 = r9 ^ r4
            if (r9 == 0) goto L3f
            com.android.systemui.statusbar.phone.BiometricUnlockController r9 = r7.mBiometricUnlockController
            boolean r5 = r9.isWakeAndUnlock()
            if (r5 != 0) goto L3c
            boolean r9 = r9.mFadedAwayAfterWakeAndUnlock
            if (r9 == 0) goto L3a
            goto L3c
        L3a:
            r9 = r3
            goto L3d
        L3c:
            r9 = r4
        L3d:
            if (r9 == 0) goto L41
        L3f:
            if (r8 == 0) goto L42
        L41:
            r3 = r4
        L42:
            r8 = r3
        L43:
            boolean r9 = r7.mIsAmbientMode
            if (r2 == r9) goto L8a
            r7.mIsAmbientMode = r2
            android.app.IWallpaperManager r9 = r7.mWallpaperManagerService
            if (r9 == 0) goto L8a
            if (r8 == 0) goto L52
            r2 = 500(0x1f4, double:2.47E-321)
            goto L54
        L52:
            r2 = 0
        L54:
            boolean r8 = com.android.systemui.doze.DozeWallpaperState.DEBUG     // Catch: android.os.RemoteException -> L77
            if (r8 == 0) goto L71
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch: android.os.RemoteException -> L77
            r8.<init>(r1)     // Catch: android.os.RemoteException -> L77
            boolean r1 = r7.mIsAmbientMode     // Catch: android.os.RemoteException -> L77
            r8.append(r1)     // Catch: android.os.RemoteException -> L77
            java.lang.String r1 = ", animationDuration: "
            r8.append(r1)     // Catch: android.os.RemoteException -> L77
            r8.append(r2)     // Catch: android.os.RemoteException -> L77
            java.lang.String r8 = r8.toString()     // Catch: android.os.RemoteException -> L77
            android.util.Log.i(r0, r8)     // Catch: android.os.RemoteException -> L77
        L71:
            boolean r8 = r7.mIsAmbientMode     // Catch: android.os.RemoteException -> L77
            r9.setInAmbientMode(r8, r2)     // Catch: android.os.RemoteException -> L77
            goto L8a
        L77:
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            java.lang.String r9 = "Cannot notify state to WallpaperManagerService: "
            r8.<init>(r9)
            boolean r7 = r7.mIsAmbientMode
            r8.append(r7)
            java.lang.String r7 = r8.toString()
            android.util.Log.w(r0, r7)
        L8a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.doze.DozeWallpaperState.transitionTo(com.android.systemui.doze.DozeMachine$State, com.android.systemui.doze.DozeMachine$State):void");
    }
}
