package com.android.wm.shell.transition;

import android.animation.Animator;
import android.os.Debug;
import android.os.IBinder;
import android.util.Log;
import android.util.Slog;
import android.window.IRemoteTransitionFinishedCallback;
import android.window.WindowContainerTransaction;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.transition.RemoteTransitionHandler;
import com.android.wm.shell.transition.Transitions;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final /* synthetic */ class RemoteTransitionHandler$1$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ IRemoteTransitionFinishedCallback.Stub f$0;
    public final /* synthetic */ IBinder f$1;
    public final /* synthetic */ Transitions.TransitionFinishCallback f$2;
    public final /* synthetic */ WindowContainerTransaction f$3;

    public /* synthetic */ RemoteTransitionHandler$1$$ExternalSyntheticLambda0(IRemoteTransitionFinishedCallback.Stub stub, IBinder iBinder, Transitions.TransitionFinishCallback transitionFinishCallback, WindowContainerTransaction windowContainerTransaction, int i) {
        this.$r8$classId = i;
        this.f$0 = stub;
        this.f$1 = iBinder;
        this.f$2 = transitionFinishCallback;
        this.f$3 = windowContainerTransaction;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                RemoteTransitionHandler.AnonymousClass1 anonymousClass1 = (RemoteTransitionHandler.AnonymousClass1) this.f$0;
                IBinder iBinder = this.f$1;
                Transitions.TransitionFinishCallback transitionFinishCallback = this.f$2;
                WindowContainerTransaction windowContainerTransaction = this.f$3;
                RemoteTransitionHandler.this.mRequestedRemotes.remove(iBinder);
                if (CoreRune.MW_FREEFORM_FORCE_HIDING_TRANSITION) {
                    RemoteTransitionHandler remoteTransitionHandler = RemoteTransitionHandler.this;
                    ArrayList arrayList = remoteTransitionHandler.mForceHidingAnimators;
                    if (!arrayList.isEmpty()) {
                        Slog.d("RemoteTransitionHandler", "cancelForceHideAnimationsIfNeeded: animators=" + new ArrayList(arrayList) + ", Callers=" + Debug.getCallers(5));
                        Iterator it = arrayList.iterator();
                        while (it.hasNext()) {
                            ((HandlerExecutor) remoteTransitionHandler.mAnimExecutor).execute(new RemoteTransitionHandler$$ExternalSyntheticLambda0((Animator) it.next(), 1));
                        }
                    }
                }
                if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_MERGE) {
                    RemoteTransitionHandler.this.mRequestedInfos.remove(iBinder);
                }
                transitionFinishCallback.onTransitionFinished(windowContainerTransaction, null);
                return;
            default:
                RemoteTransitionHandler.AnonymousClass2 anonymousClass2 = (RemoteTransitionHandler.AnonymousClass2) this.f$0;
                IBinder iBinder2 = this.f$1;
                Transitions.TransitionFinishCallback transitionFinishCallback2 = this.f$2;
                WindowContainerTransaction windowContainerTransaction2 = this.f$3;
                if (!RemoteTransitionHandler.this.mRequestedRemotes.containsKey(iBinder2)) {
                    Log.e("RemoteTransitionHandler", "Merged transition finished after it's mergeTarget (the transition it was supposed to merge into). This usually means that the mergeTarget's RemoteTransition impl erroneously accepted/ran the merge request after finishing the mergeTarget");
                }
                transitionFinishCallback2.onTransitionFinished(windowContainerTransaction2, null);
                return;
        }
    }
}
