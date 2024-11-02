package com.android.wm.shell.startingsurface;

import android.content.res.Configuration;
import com.android.wm.shell.protolog.ShellProtoLogCache;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.protolog.ShellProtoLogImpl;
import com.android.wm.shell.startingsurface.SplashscreenContentDrawer;
import com.android.wm.shell.startingsurface.StartingWindowController;
import com.android.wm.shell.sysui.ConfigurationChangeListener;
import com.android.wm.shell.sysui.ShellController;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.util.function.Supplier;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final /* synthetic */ class StartingWindowController$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ StartingWindowController f$0;

    public /* synthetic */ StartingWindowController$$ExternalSyntheticLambda2(StartingWindowController startingWindowController, int i) {
        this.$r8$classId = i;
        this.f$0 = startingWindowController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                StartingWindowController startingWindowController = this.f$0;
                StartingSurfaceDrawer startingSurfaceDrawer = startingWindowController.mStartingSurfaceDrawer;
                startingSurfaceDrawer.getClass();
                if (ShellProtoLogCache.WM_SHELL_STARTING_WINDOW_enabled) {
                    ShellProtoLogImpl.v(ShellProtoLogGroup.WM_SHELL_STARTING_WINDOW, -976436888, 0, null, null);
                }
                startingSurfaceDrawer.mWindowRecords.clearAllWindows();
                startingSurfaceDrawer.mWindowlessRecords.clearAllWindows();
                synchronized (startingWindowController.mTaskBackgroundColors) {
                    startingWindowController.mTaskBackgroundColors.clear();
                }
                return;
            default:
                final StartingWindowController startingWindowController2 = this.f$0;
                startingWindowController2.mShellTaskOrganizer.mStartingWindow = startingWindowController2;
                Supplier supplier = new Supplier() { // from class: com.android.wm.shell.startingsurface.StartingWindowController$$ExternalSyntheticLambda5
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        StartingWindowController startingWindowController3 = StartingWindowController.this;
                        startingWindowController3.getClass();
                        return new StartingWindowController.IStartingWindowImpl(startingWindowController3);
                    }
                };
                ShellController shellController = startingWindowController2.mShellController;
                shellController.addExternalInterface(QuickStepContract.KEY_EXTRA_SHELL_STARTING_WINDOW, supplier, startingWindowController2);
                shellController.addConfigurationChangeListener(new ConfigurationChangeListener() { // from class: com.android.wm.shell.startingsurface.StartingWindowController.1
                    public AnonymousClass1() {
                    }

                    @Override // com.android.wm.shell.sysui.ConfigurationChangeListener
                    public final void onConfigurationChanged(Configuration configuration) {
                        SplashscreenContentDrawer splashscreenContentDrawer = StartingWindowController.this.mStartingSurfaceDrawer.mSplashscreenContentDrawer;
                        splashscreenContentDrawer.getClass();
                        boolean isNightModeActive = configuration.isNightModeActive();
                        if (SplashscreenContentDrawer.mIsNightMode != isNightModeActive) {
                            int i = SplashscreenContentDrawer.SettingObserver.$r8$clinit;
                            splashscreenContentDrawer.mSettingObserver.updateSettings(true);
                        }
                        SplashscreenContentDrawer.mIsNightMode = isNightModeActive;
                    }
                });
                return;
        }
    }
}
