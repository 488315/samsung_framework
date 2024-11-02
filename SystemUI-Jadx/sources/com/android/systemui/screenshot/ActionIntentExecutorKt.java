package com.android.systemui.screenshot;

import android.os.RemoteException;
import android.util.Log;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.IRemoteAnimationRunner;
import android.view.RemoteAnimationTarget;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public abstract class ActionIntentExecutorKt {
    public static final ActionIntentExecutorKt$SCREENSHOT_REMOTE_RUNNER$1 SCREENSHOT_REMOTE_RUNNER = new IRemoteAnimationRunner.Stub() { // from class: com.android.systemui.screenshot.ActionIntentExecutorKt$SCREENSHOT_REMOTE_RUNNER$1
        public final void onAnimationStart(int i, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
            try {
                iRemoteAnimationFinishedCallback.onAnimationFinished();
            } catch (RemoteException e) {
                Log.e("ActionIntentExecutor", "Error finishing screenshot remote animation", e);
            }
        }

        public final void onAnimationCancelled() {
        }
    };
}
