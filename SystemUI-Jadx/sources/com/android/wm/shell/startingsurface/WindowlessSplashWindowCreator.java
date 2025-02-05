package com.android.wm.shell.startingsurface;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.SystemClock;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.window.SplashScreenView;
import android.window.StartingWindowRemovalInfo;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.TransactionPool;
import com.android.wm.shell.startingsurface.StartingSurfaceDrawer;
import com.android.wm.shell.startingsurface.WindowlessSplashWindowCreator;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class WindowlessSplashWindowCreator extends AbsSplashWindowCreator {
    public final TransactionPool mTransactionPool;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public final class SplashWindowRecord extends StartingSurfaceDrawer.StartingWindowRecord {
        public SurfaceControl mChildSurface;
        public final long mCreateTime;
        public final SplashScreenView mSplashView;
        public SurfaceControlViewHost mViewHost;

        public SplashWindowRecord(SurfaceControlViewHost surfaceControlViewHost, SplashScreenView splashScreenView, SurfaceControl surfaceControl, int i) {
            this.mViewHost = surfaceControlViewHost;
            this.mSplashView = splashScreenView;
            this.mChildSurface = surfaceControl;
            this.mBGColor = i;
            this.mCreateTime = SystemClock.uptimeMillis();
        }

        public final void release() {
            if (this.mChildSurface != null) {
                WindowlessSplashWindowCreator windowlessSplashWindowCreator = WindowlessSplashWindowCreator.this;
                SurfaceControl.Transaction acquire = windowlessSplashWindowCreator.mTransactionPool.acquire();
                acquire.remove(this.mChildSurface).apply();
                windowlessSplashWindowCreator.mTransactionPool.release(acquire);
                this.mChildSurface = null;
            }
            SurfaceControlViewHost surfaceControlViewHost = this.mViewHost;
            if (surfaceControlViewHost != null) {
                surfaceControlViewHost.release();
                this.mViewHost = null;
            }
        }

        @Override // com.android.wm.shell.startingsurface.StartingSurfaceDrawer.StartingWindowRecord
        public final boolean removeIfPossible(StartingWindowRemovalInfo startingWindowRemovalInfo, boolean z) {
            if (!z) {
                WindowlessSplashWindowCreator.this.mSplashscreenContentDrawer.applyExitAnimation(this.mSplashView, startingWindowRemovalInfo.windowAnimationLeash, startingWindowRemovalInfo.mainFrame, new Runnable() { // from class: com.android.wm.shell.startingsurface.WindowlessSplashWindowCreator$SplashWindowRecord$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        WindowlessSplashWindowCreator.SplashWindowRecord.this.release();
                    }
                }, this.mCreateTime, 0.0f, startingWindowRemovalInfo.duration);
                return true;
            }
            release();
            return true;
        }
    }

    public WindowlessSplashWindowCreator(SplashscreenContentDrawer splashscreenContentDrawer, Context context, ShellExecutor shellExecutor, DisplayManager displayManager, StartingSurfaceDrawer.StartingWindowRecordManager startingWindowRecordManager, TransactionPool transactionPool) {
        super(splashscreenContentDrawer, context, shellExecutor, displayManager, startingWindowRecordManager);
        this.mTransactionPool = transactionPool;
    }
}
