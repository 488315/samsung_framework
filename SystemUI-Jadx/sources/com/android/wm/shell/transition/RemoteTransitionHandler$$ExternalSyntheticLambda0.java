package com.android.wm.shell.transition;

import android.animation.Animator;
import android.util.Pair;
import android.window.RemoteTransition;
import com.android.wm.shell.transition.RemoteTransitionHandler;
import com.android.wm.shell.transition.Transitions;
import com.samsung.android.rune.CoreRune;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final /* synthetic */ class RemoteTransitionHandler$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ RemoteTransitionHandler$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ((Transitions.TransitionFinishCallback) this.f$0).onTransitionFinished(null, null);
                return;
            case 1:
                ((Animator) this.f$0).cancel();
                return;
            default:
                RemoteTransitionHandler.RemoteDeathHandler remoteDeathHandler = (RemoteTransitionHandler.RemoteDeathHandler) this.f$0;
                int size = remoteDeathHandler.this$0.mFilters.size();
                while (true) {
                    size--;
                    if (size >= 0) {
                        if (remoteDeathHandler.mRemote.equals(((RemoteTransition) ((Pair) remoteDeathHandler.this$0.mFilters.get(size)).second).asBinder())) {
                            remoteDeathHandler.this$0.mFilters.remove(size);
                        }
                    } else {
                        int size2 = remoteDeathHandler.this$0.mRequestedRemotes.size();
                        while (true) {
                            size2--;
                            if (size2 >= 0) {
                                if (remoteDeathHandler.mRemote.equals(((RemoteTransition) remoteDeathHandler.this$0.mRequestedRemotes.valueAt(size2)).asBinder())) {
                                    remoteDeathHandler.this$0.mRequestedRemotes.removeAt(size2);
                                    if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_MERGE) {
                                        remoteDeathHandler.this$0.mRequestedInfos.removeAt(size2);
                                    }
                                }
                            } else {
                                int size3 = remoteDeathHandler.mPendingFinishCallbacks.size();
                                while (true) {
                                    size3--;
                                    if (size3 >= 0) {
                                        ((Transitions.TransitionFinishCallback) remoteDeathHandler.mPendingFinishCallbacks.get(size3)).onTransitionFinished(null, null);
                                    } else {
                                        remoteDeathHandler.mPendingFinishCallbacks.clear();
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
        }
    }
}
