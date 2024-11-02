package com.android.wm.shell.common;

import android.view.inputmethod.ImeTracker;
import com.android.wm.shell.common.DisplayInsetsController;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final /* synthetic */ class DisplayInsetsController$PerDisplay$DisplayWindowInsetsControllerImpl$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DisplayInsetsController.PerDisplay.DisplayWindowInsetsControllerImpl f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ boolean f$2;
    public final /* synthetic */ ImeTracker.Token f$3;

    public /* synthetic */ DisplayInsetsController$PerDisplay$DisplayWindowInsetsControllerImpl$$ExternalSyntheticLambda0(DisplayInsetsController.PerDisplay.DisplayWindowInsetsControllerImpl displayWindowInsetsControllerImpl, int i, boolean z, ImeTracker.Token token, int i2) {
        this.$r8$classId = i2;
        this.f$0 = displayWindowInsetsControllerImpl;
        this.f$1 = i;
        this.f$2 = z;
        this.f$3 = token;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                DisplayInsetsController.PerDisplay.DisplayWindowInsetsControllerImpl displayWindowInsetsControllerImpl = this.f$0;
                int i = this.f$1;
                ImeTracker.Token token = this.f$3;
                DisplayInsetsController.PerDisplay perDisplay = displayWindowInsetsControllerImpl.this$1;
                CopyOnWriteArrayList copyOnWriteArrayList = (CopyOnWriteArrayList) perDisplay.this$0.mListeners.get(perDisplay.mDisplayId);
                if (copyOnWriteArrayList == null) {
                    ImeTracker.forLogging().onFailed(token, 25);
                    return;
                }
                ImeTracker.forLogging().onProgress(token, 25);
                Iterator it = copyOnWriteArrayList.iterator();
                while (it.hasNext()) {
                    ((DisplayInsetsController.OnInsetsChangedListener) it.next()).hideInsets(i, token);
                }
                return;
            default:
                DisplayInsetsController.PerDisplay.DisplayWindowInsetsControllerImpl displayWindowInsetsControllerImpl2 = this.f$0;
                int i2 = this.f$1;
                ImeTracker.Token token2 = this.f$3;
                DisplayInsetsController.PerDisplay perDisplay2 = displayWindowInsetsControllerImpl2.this$1;
                CopyOnWriteArrayList copyOnWriteArrayList2 = (CopyOnWriteArrayList) perDisplay2.this$0.mListeners.get(perDisplay2.mDisplayId);
                if (copyOnWriteArrayList2 == null) {
                    ImeTracker.forLogging().onFailed(token2, 25);
                    return;
                }
                ImeTracker.forLogging().onProgress(token2, 25);
                Iterator it2 = copyOnWriteArrayList2.iterator();
                while (it2.hasNext()) {
                    ((DisplayInsetsController.OnInsetsChangedListener) it2.next()).showInsets(i2, token2);
                }
                return;
        }
    }
}
