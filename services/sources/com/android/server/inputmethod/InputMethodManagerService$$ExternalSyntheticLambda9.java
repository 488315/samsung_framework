package com.android.server.inputmethod;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class InputMethodManagerService$$ExternalSyntheticLambda9 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ InputMethodManagerService f$0;

    public /* synthetic */ InputMethodManagerService$$ExternalSyntheticLambda9(int i, InputMethodManagerService inputMethodManagerService) {
        this.$r8$classId = i;
        this.f$0 = inputMethodManagerService;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        InputMethodManagerService inputMethodManagerService = this.f$0;
        switch (i) {
            case 0:
                OverlayableSystemBooleanResourceWrapper overlayableSystemBooleanResourceWrapper = (OverlayableSystemBooleanResourceWrapper) obj;
                inputMethodManagerService.getClass();
                synchronized (ImfLock.class) {
                    try {
                        if (overlayableSystemBooleanResourceWrapper == inputMethodManagerService.mImeDrawsImeNavBarRes) {
                            inputMethodManagerService.sendOnNavButtonFlagsChangedLocked();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                return;
            default:
                ClientState clientState = (ClientState) obj;
                inputMethodManagerService.getClass();
                inputMethodManagerService.finishSessionLocked(clientState.mCurSession);
                clientState.mCurSession = null;
                clientState.mSessionRequested = false;
                InputMethodManagerService.clearClientSessionForAccessibilityLocked(clientState);
                return;
        }
    }
}
