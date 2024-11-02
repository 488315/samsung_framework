package com.android.systemui.screenshot;

import android.os.Process;
import android.util.Log;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import com.android.systemui.screenshot.ScreenshotController;
import com.android.systemui.screenshot.sep.SnackbarController;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final /* synthetic */ class ScreenshotController$$ExternalSyntheticLambda2 implements ScreenshotController.ActionsReadyListener, CallbackToFutureAdapter.Resolver, SnackbarController.DismissedCallback {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ScreenshotController f$0;

    public /* synthetic */ ScreenshotController$$ExternalSyntheticLambda2(ScreenshotController screenshotController, int i) {
        this.$r8$classId = i;
        this.f$0 = screenshotController;
    }

    @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
    public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
        ScreenshotController screenshotController = this.f$0;
        screenshotController.getClass();
        screenshotController.mBgExecutor.execute(new ScreenshotController$$ExternalSyntheticLambda6(screenshotController, completer, 1));
        return "ScreenshotController#loadCameraSound";
    }

    public final void onActionsReady(ScreenshotController.SavedImageData savedImageData) {
        int i = this.$r8$classId;
        ScreenshotController screenshotController = this.f$0;
        switch (i) {
            case 0:
                screenshotController.logSuccessOnActionsReady(savedImageData);
                return;
            case 1:
                screenshotController.logSuccessOnActionsReady(savedImageData);
                TimeoutHandler timeoutHandler = screenshotController.mScreenshotHandler;
                timeoutHandler.resetTimeout();
                if (savedImageData.uri != null) {
                    if (!savedImageData.owner.equals(Process.myUserHandle())) {
                        Log.d("Screenshot", "Screenshot saved to user " + savedImageData.owner + " as " + savedImageData.uri);
                    }
                    timeoutHandler.post(new ScreenshotController$$ExternalSyntheticLambda6(screenshotController, savedImageData, 0));
                    return;
                }
                return;
            default:
                screenshotController.logSuccessOnActionsReady(savedImageData);
                return;
        }
    }
}
