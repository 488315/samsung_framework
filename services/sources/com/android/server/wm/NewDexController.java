package com.android.server.wm;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.hardware.display.DisplayManager;
import android.hardware.display.DisplayManagerGlobal;
import android.hardware.display.VirtualDisplay;
import android.hardware.display.VirtualDisplayConfig;
import android.media.projection.MediaProjection;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.SparseArray;
import android.view.Display;
import com.android.server.LocalServices;
import com.android.server.UiModeManagerInternal;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public class NewDexController implements IController {
    public static final boolean SAFE_DEBUG = CoreRune.SAFE_DEBUG;
    public final ActivityTaskManagerService mAtm;
    public DisplayManager mDisplayManager;
    public H mH;
    public UiModeManagerInternal mUiModeManagerInternal;
    public WindowManagerService mWm;
    public VirtualDisplay mPrimaryDesktopDisplay = null;
    public int mPrimaryDesktopDisplayId = -1;
    public final SparseArray mExtraDisplays = new SparseArray();
    public boolean mNewDexModeSet = false;
    public DisplayManager.DisplayListener mDisplayListener = new DisplayManager.DisplayListener() { // from class: com.android.server.wm.NewDexController.1
        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayChanged(int i) {
        }

        public AnonymousClass1() {
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayAdded(int i) {
            NewDexController.this.updateExtraDisplayStage(true, i);
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayRemoved(int i) {
            NewDexController.this.updateExtraDisplayStage(false, i);
        }
    };
    public int mDesktopMode = -1;

    /* renamed from: com.android.server.wm.NewDexController$1 */
    /* loaded from: classes3.dex */
    public class AnonymousClass1 implements DisplayManager.DisplayListener {
        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayChanged(int i) {
        }

        public AnonymousClass1() {
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayAdded(int i) {
            NewDexController.this.updateExtraDisplayStage(true, i);
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayRemoved(int i) {
            NewDexController.this.updateExtraDisplayStage(false, i);
        }
    }

    public NewDexController(ActivityTaskManagerService activityTaskManagerService) {
        this.mAtm = activityTaskManagerService;
    }

    @Override // com.android.server.wm.IController
    public void initialize() {
        this.mH = new H(this.mAtm.mH.getLooper());
    }

    @Override // com.android.server.wm.IController
    public void setWindowManager(WindowManagerService windowManagerService) {
        this.mWm = windowManagerService;
        DisplayManager displayManager = (DisplayManager) this.mAtm.mContext.getSystemService(DisplayManager.class);
        this.mDisplayManager = displayManager;
        if (CoreRune.MT_NEW_DEX_DISPLAY_MANAGEMENT) {
            displayManager.registerDisplayListener(this.mDisplayListener, this.mH);
        }
    }

    public final void updateExtraDisplayStage(boolean z, int i) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (!this.mNewDexModeSet) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                if (z && this.mDesktopMode != 2) {
                    Display display = this.mDisplayManager.getDisplay(i);
                    if (display != null && isSupportedDisplay(display)) {
                        this.mExtraDisplays.put(i, display);
                        this.mH.post(new Runnable() { // from class: com.android.server.wm.NewDexController$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                NewDexController.this.lambda$updateExtraDisplayStage$0();
                            }
                        });
                    }
                } else if (!z && this.mDesktopMode == 2 && this.mExtraDisplays.contains(i)) {
                    this.mExtraDisplays.remove(i);
                    if (this.mExtraDisplays.size() == 0) {
                        this.mH.post(new Runnable() { // from class: com.android.server.wm.NewDexController$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                NewDexController.this.lambda$updateExtraDisplayStage$1();
                            }
                        });
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public /* synthetic */ void lambda$updateExtraDisplayStage$0() {
        Settings.System.putIntForUser(this.mAtm.mContext.getContentResolver(), "new_dex", 2, 0);
    }

    public /* synthetic */ void lambda$updateExtraDisplayStage$1() {
        Settings.System.putIntForUser(this.mAtm.mContext.getContentResolver(), "new_dex", 0, 0);
    }

    public final VirtualDisplay createDesktopVirtualDisplay(boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append("Desktop");
        sb.append(z ? "_p" : "_s");
        return DisplayManagerGlobal.getInstance().createVirtualDisplay(this.mAtm.mContext, (MediaProjection) null, new VirtualDisplayConfig.Builder(sb.toString(), 1920, 1080, 160).setFlags(33566209).build(), (VirtualDisplay.Callback) null, (Executor) null);
    }

    public void adjustDesktopModeConfiguration(Configuration configuration, DisplayContent displayContent) {
        if (configuration.isDexMode() || this.mAtm.mDexController.getDexModeLocked() == 2) {
            return;
        }
        if (displayContent.isDefaultDisplay || displayContent.getDisplayId() == this.mPrimaryDesktopDisplayId) {
            if (this.mUiModeManagerInternal == null) {
                UiModeManagerInternal uiModeManagerInternal = (UiModeManagerInternal) LocalServices.getService(UiModeManagerInternal.class);
                this.mUiModeManagerInternal = uiModeManagerInternal;
                if (uiModeManagerInternal == null) {
                    return;
                }
            }
            TaskDisplayArea defaultTaskDisplayArea = displayContent.getDefaultTaskDisplayArea();
            boolean z = defaultTaskDisplayArea.getWindowingMode() == 5;
            if (z) {
                configuration.semDesktopModeEnabled = 1;
                configuration.dexMode = displayContent.isDefaultDisplay ? 3 : 4;
                this.mH.post(new Runnable() { // from class: com.android.server.wm.NewDexController$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        NewDexController.this.lambda$adjustDesktopModeConfiguration$2();
                    }
                });
            } else {
                configuration.semDesktopModeEnabled = 0;
                configuration.dexMode = 0;
                this.mH.post(new Runnable() { // from class: com.android.server.wm.NewDexController$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        NewDexController.this.lambda$adjustDesktopModeConfiguration$3();
                    }
                });
            }
            if (displayContent.isDefaultDisplay) {
                this.mAtm.mWindowManager.mExt.mPolicyExt.onDexModeChangedLw(configuration.dexMode);
            }
            if (CoreRune.MT_DEX_SIZE_COMPAT_MODE && displayContent.isDefaultDisplay) {
                DexSizeCompatController.getInstance().toggle(defaultTaskDisplayArea, z);
            }
        }
    }

    public /* synthetic */ void lambda$adjustDesktopModeConfiguration$2() {
        this.mUiModeManagerInternal.setNewDexMode(true);
    }

    public /* synthetic */ void lambda$adjustDesktopModeConfiguration$3() {
        this.mUiModeManagerInternal.setNewDexMode(false);
    }

    public boolean shouldAbortStartActivity(ActivityInfo activityInfo) {
        return (getNewDexPolicyFlags(activityInfo, activityInfo.applicationInfo) & 4) != 0;
    }

    public int getNewDexPolicyFlags(ActivityInfo activityInfo, ApplicationInfo applicationInfo) {
        return isNotSupportHomeApp(activityInfo, applicationInfo) ? 4 : 0;
    }

    public final boolean isNotSupportHomeApp(ActivityInfo activityInfo, ApplicationInfo applicationInfo) {
        Bundle bundle;
        Intent homeIntent;
        ResolveInfo resolveActivity;
        Bundle bundle2;
        String str = applicationInfo.packageName;
        if (str != null && !DexController.DEFAULT_ALLOW_HOME_SET.contains(this.mAtm.mDexController.toHashText(str)) && ((activityInfo == null || (bundle2 = activityInfo.metaData) == null || !bundle2.getBoolean("com.samsung.android.dex.launchpolicy.allow_home_activity", false)) && ((bundle = applicationInfo.metaData) == null || !bundle.getBoolean("com.samsung.android.dex.launchpolicy.allow_home_app", false)))) {
            ResolveInfo resolveActivityAsUser = this.mAtm.mContext.getPackageManager().resolveActivityAsUser(new Intent("android.intent.action.MAIN").addCategory("android.intent.category.HOME").setPackage(applicationInfo.packageName), PackageManager.ResolveInfoFlags.of(65536L), UserHandle.getUserId(applicationInfo.uid));
            if (resolveActivityAsUser != null && (homeIntent = this.mAtm.mRootWindowContainer.mService.getHomeIntent()) != null && (resolveActivity = this.mAtm.mContext.getPackageManager().resolveActivity(homeIntent, PackageManager.ResolveInfoFlags.of(65536L))) != null && !resolveActivity.getComponentInfo().packageName.equals(resolveActivityAsUser.getComponentInfo().packageName)) {
                return true;
            }
        }
        return false;
    }

    public void updateDesktopModeActive() {
        this.mWm.getDefaultDisplayContentLocked().reconfigureDisplayLocked();
    }

    public boolean keepReqOverrideConfigOnParentChanged(WindowContainer windowContainer, Task task) {
        Task asTask;
        return windowContainer != null && this.mAtm.getConfiguration().isNewDexMode() && (asTask = windowContainer.asTask()) != null && asTask.isOrganized() && asTask.inFullscreenWindowingMode() && task.isOrganized() && task.inSplitScreenWindowingMode();
    }

    public void updateDesktopModeSettings(boolean z) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (this.mNewDexModeSet != z) {
                    this.mNewDexModeSet = z;
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public void updateNewDexMode(int i) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                int i2 = this.mDesktopMode;
                if (i2 == i) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                if (i == 2 && i2 == -1) {
                    final int i3 = CoreRune.MT_NEW_DEX_DEFAULT_DISPLAY_LAUNCH_POLICY ? 1 : 0;
                    this.mH.post(new Runnable() { // from class: com.android.server.wm.NewDexController$$ExternalSyntheticLambda4
                        @Override // java.lang.Runnable
                        public final void run() {
                            NewDexController.this.lambda$updateNewDexMode$4(i3);
                        }
                    });
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                this.mDesktopMode = i;
                if (i == 2 && this.mPrimaryDesktopDisplay == null) {
                    VirtualDisplay createDesktopVirtualDisplay = createDesktopVirtualDisplay(true);
                    this.mPrimaryDesktopDisplay = createDesktopVirtualDisplay;
                    this.mPrimaryDesktopDisplayId = createDesktopVirtualDisplay.getDisplay().getDisplayId();
                }
                VirtualDisplay virtualDisplay = this.mPrimaryDesktopDisplay;
                if (virtualDisplay != null) {
                    virtualDisplay.setDisplayState(i == 2);
                }
                boolean z = i > 0;
                this.mAtm.mMultiWindowEnableController.setMultiWindowForceEnabledForUser("NewDexController", z ? "New DeX On" : "New DeX Off", z, -1);
                if (CoreRune.MT_NEW_DEX_DISPLAY_MANAGEMENT) {
                    this.mWm.mDisplayManagerInternal.updateDesktopDisplayState(i == 2, this.mPrimaryDesktopDisplayId);
                }
                WindowManagerService.resetPriorityAfterLockedSection();
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public /* synthetic */ void lambda$updateNewDexMode$4(int i) {
        Settings.System.putIntForUser(this.mAtm.mContext.getContentResolver(), "new_dex", i, 0);
    }

    public final boolean isSupportedDisplay(Display display) {
        return display.getType() == 2;
    }

    /* loaded from: classes3.dex */
    public final class H extends Handler {
        public H(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
        }
    }

    public final void updateOverlayState(PrintWriter printWriter) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (!this.mNewDexModeSet) {
                    printWriter.println("$ adb shell content call --uri \"content://com.sec.android.desktopmode.uiservice.SettingsProvider/settings\" --method \"setSettings\" --extra key:s:dex_mode --extra val:s:[new/classic]");
                    printWriter.println("should set to 'new' before enable overlay");
                    WindowManagerService.resetPriorityAfterLockedSection();
                } else if (this.mDesktopMode != 2) {
                    printWriter.println("$ adb shell settings put system new_dex [0/1/2]");
                    printWriter.println("should set to 2 before enable overlay");
                    WindowManagerService.resetPriorityAfterLockedSection();
                } else {
                    this.mWm.mDisplayManagerInternal.updateOverlayState();
                    WindowManagerService.resetPriorityAfterLockedSection();
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final void updateDualOverlayState() {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mWm.mDisplayManagerInternal.updateDualOverlayState();
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final void switchOverlay() {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mWm.mDisplayManagerInternal.switchOverlay();
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final void printInfo(PrintWriter printWriter) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                printWriter.println("Current status:");
                printWriter.println("    mNewDexModeSet=" + this.mNewDexModeSet);
                printWriter.println("    mDesktopMode=" + this.mDesktopMode);
                printWriter.println("    mPrimaryDesktopDisplayId=" + this.mPrimaryDesktopDisplayId);
                if (this.mPrimaryDesktopDisplayId != -1) {
                    printWriter.println();
                    printWriter.println("Command for scrcpy:");
                    printWriter.println("    scrcpy --display " + this.mPrimaryDesktopDisplayId);
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final void removeDesktopDisplay() {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                VirtualDisplay virtualDisplay = this.mPrimaryDesktopDisplay;
                if (virtualDisplay != null) {
                    virtualDisplay.release();
                    this.mPrimaryDesktopDisplay = null;
                    this.mPrimaryDesktopDisplayId = -1;
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x004a, code lost:
    
        if (r8.equals("switch-overlay") == false) goto L46;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onCommand(java.lang.String[] r8, java.io.PrintWriter r9) {
        /*
            r7 = this;
            boolean r0 = com.samsung.android.rune.CoreRune.MT_NEW_DEX_EMULATOR
            if (r0 == 0) goto L70
            r0 = 0
            r8 = r8[r0]
            r8.hashCode()
            int r1 = r8.hashCode()
            java.lang.String r2 = "remove-display"
            java.lang.String r3 = "overlay2"
            java.lang.String r4 = "overlay"
            java.lang.String r5 = "switch-overlay"
            r6 = -1
            switch(r1) {
                case -1429968201: goto L46;
                case -1091287984: goto L3d;
                case 3237038: goto L32;
                case 529810914: goto L29;
                case 1129721977: goto L20;
                default: goto L1e;
            }
        L1e:
            r0 = r6
            goto L4d
        L20:
            boolean r8 = r8.equals(r2)
            if (r8 != 0) goto L27
            goto L1e
        L27:
            r0 = 4
            goto L4d
        L29:
            boolean r8 = r8.equals(r3)
            if (r8 != 0) goto L30
            goto L1e
        L30:
            r0 = 3
            goto L4d
        L32:
            java.lang.String r0 = "info"
            boolean r8 = r8.equals(r0)
            if (r8 != 0) goto L3b
            goto L1e
        L3b:
            r0 = 2
            goto L4d
        L3d:
            boolean r8 = r8.equals(r4)
            if (r8 != 0) goto L44
            goto L1e
        L44:
            r0 = 1
            goto L4d
        L46:
            boolean r8 = r8.equals(r5)
            if (r8 != 0) goto L4d
            goto L1e
        L4d:
            switch(r0) {
                case 0: goto L6a;
                case 1: goto L63;
                case 2: goto L5f;
                case 3: goto L58;
                case 4: goto L51;
                default: goto L50;
            }
        L50:
            goto L70
        L51:
            r9.println(r2)
            r7.removeDesktopDisplay()
            goto L70
        L58:
            r9.println(r3)
            r7.updateDualOverlayState()
            goto L70
        L5f:
            r7.printInfo(r9)
            goto L70
        L63:
            r9.println(r4)
            r7.updateOverlayState(r9)
            goto L70
        L6a:
            r9.println(r5)
            r7.switchOverlay()
        L70:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.NewDexController.onCommand(java.lang.String[], java.io.PrintWriter):void");
    }

    @Override // com.android.server.wm.IController
    public void dumpLocked(PrintWriter printWriter, String str) {
        printWriter.println("[NewDexController]");
        printWriter.println();
    }
}
