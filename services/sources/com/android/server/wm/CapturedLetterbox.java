package com.android.server.wm;

import android.os.SystemClock;
import android.util.Slog;
import android.view.SurfaceControl;
import com.android.server.wm.WindowManagerService;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class CapturedLetterbox {
    public SurfaceControl mCapturedLetterboxSurface;
    public final DisplayContent mDisplay;
    public boolean mShouldUseCapturedLetterbox;
    public final CapturedLetterbox$$ExternalSyntheticLambda0 mShowCapturedLetterboxRunnable = new Runnable() { // from class: com.android.server.wm.CapturedLetterbox$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            CapturedLetterbox capturedLetterbox = CapturedLetterbox.this;
            WindowManagerGlobalLock windowManagerGlobalLock = capturedLetterbox.mDisplay.mWmService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    capturedLetterbox.removeCapturedLetterboxSurface();
                    if (!capturedLetterbox.mShouldUseCapturedLetterbox) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    DisplayContent displayContent = capturedLetterbox.mDisplay;
                    Transition transition = displayContent.mTransitionController.mCollectingTransition;
                    if (transition == null || !transition.mTargetDisplays.contains(displayContent)) {
                        capturedLetterbox.createCapturedLetterboxSurface();
                        capturedLetterbox.mDisplay.mWmService.mH.removeCallbacks(capturedLetterbox.mShowCapturedLetterboxRunnable);
                        WindowManagerService.resetPriorityAfterLockedSection();
                    } else {
                        DisplayContent displayContent2 = capturedLetterbox.mDisplay;
                        WindowManagerService.H h = displayContent2.mWmService.mH;
                        CapturedLetterbox$$ExternalSyntheticLambda0 capturedLetterbox$$ExternalSyntheticLambda0 = capturedLetterbox.mShowCapturedLetterboxRunnable;
                        h.removeCallbacks(capturedLetterbox$$ExternalSyntheticLambda0);
                        displayContent2.mWmService.mH.postDelayed(capturedLetterbox$$ExternalSyntheticLambda0, 5000L);
                        WindowManagerService.resetPriorityAfterLockedSection();
                    }
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }
    };

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.server.wm.CapturedLetterbox$$ExternalSyntheticLambda0] */
    public CapturedLetterbox(DisplayContent displayContent) {
        Objects.requireNonNull(displayContent);
        this.mDisplay = displayContent;
    }

    public static void checkTime(long j, String str) {
        long uptimeMillis = SystemClock.uptimeMillis() - j;
        if (uptimeMillis > 100) {
            Slog.d("CapturedLetterbox", "Slow operation: " + uptimeMillis + "ms so far, now at " + str);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:48:0x0186 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0187  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void createCapturedLetterboxSurface() {
        /*
            Method dump skipped, instructions count: 434
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.CapturedLetterbox.createCapturedLetterboxSurface():void");
    }

    public final void removeCapturedLetterboxSurface() {
        if (this.mCapturedLetterboxSurface == null) {
            return;
        }
        Slog.d("CapturedLetterbox", "removeCapturedLetterboxSurface");
        DisplayContent displayContent = this.mDisplay;
        SurfaceControl.Transaction transaction = (SurfaceControl.Transaction) displayContent.mWmService.mTransactionFactory.get();
        transaction.reparent(this.mCapturedLetterboxSurface, null);
        transaction.remove(this.mCapturedLetterboxSurface);
        this.mCapturedLetterboxSurface = null;
        displayContent.mWmService.mRoot.forAllActivities(new CapturedLetterbox$$ExternalSyntheticLambda1(transaction));
        transaction.apply();
    }
}
