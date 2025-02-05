package android.view.inputmethod;

import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.RemoteException;
import android.util.Log;
import com.android.internal.inputmethod.IAccessibilityInputMethodSession;
import com.android.internal.inputmethod.IRemoteAccessibilityInputConnection;

/* loaded from: classes4.dex */
final class IAccessibilityInputMethodSessionInvoker {
    private static final String TAG = "IAccessibilityInputMethodSessionInvoker";
    private static Handler sAsyncBinderEmulationHandler;
    private static final Object sAsyncBinderEmulationHandlerLock = new Object();
    private final Handler mCustomHandler;
    private final IAccessibilityInputMethodSession mSession;

    private IAccessibilityInputMethodSessionInvoker(IAccessibilityInputMethodSession session, Handler customHandler) {
        this.mSession = session;
        this.mCustomHandler = customHandler;
    }

    public static IAccessibilityInputMethodSessionInvoker createOrNull(IAccessibilityInputMethodSession session) {
        Handler customHandler;
        if (session != null && !Binder.isProxy(session)) {
            synchronized (sAsyncBinderEmulationHandlerLock) {
                if (sAsyncBinderEmulationHandler == null) {
                    HandlerThread thread = new HandlerThread("IMM.IAIMS");
                    thread.start();
                    sAsyncBinderEmulationHandler = Handler.createAsync(thread.getLooper());
                }
                customHandler = sAsyncBinderEmulationHandler;
            }
        } else {
            customHandler = null;
        }
        if (session == null) {
            return null;
        }
        return new IAccessibilityInputMethodSessionInvoker(session, customHandler);
    }

    void finishInput() {
        if (this.mCustomHandler == null) {
            finishInputInternal();
        } else {
            this.mCustomHandler.post(new Runnable() { // from class: android.view.inputmethod.IAccessibilityInputMethodSessionInvoker$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    IAccessibilityInputMethodSessionInvoker.this.finishInputInternal();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finishInputInternal() {
        try {
            this.mSession.finishInput();
        } catch (RemoteException e) {
            Log.w(TAG, "A11yIME died", e);
        }
    }

    void updateSelection(final int oldSelStart, final int oldSelEnd, final int selStart, final int selEnd, final int candidatesStart, final int candidatesEnd) {
        if (this.mCustomHandler == null) {
            lambda$updateSelection$0(oldSelStart, oldSelEnd, selStart, selEnd, candidatesStart, candidatesEnd);
        } else {
            this.mCustomHandler.post(new Runnable() { // from class: android.view.inputmethod.IAccessibilityInputMethodSessionInvoker$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    IAccessibilityInputMethodSessionInvoker.this.lambda$updateSelection$0(oldSelStart, oldSelEnd, selStart, selEnd, candidatesStart, candidatesEnd);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: updateSelectionInternal, reason: merged with bridge method [inline-methods] */
    public void lambda$updateSelection$0(int oldSelStart, int oldSelEnd, int selStart, int selEnd, int candidatesStart, int candidatesEnd) {
        try {
            this.mSession.updateSelection(oldSelStart, oldSelEnd, selStart, selEnd, candidatesStart, candidatesEnd);
        } catch (RemoteException e) {
            Log.w(TAG, "A11yIME died", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void invalidateInput(final EditorInfo editorInfo, final IRemoteAccessibilityInputConnection connection, final int sessionId) {
        if (this.mCustomHandler == null) {
            lambda$invalidateInput$1(editorInfo, connection, sessionId);
        } else {
            this.mCustomHandler.post(new Runnable() { // from class: android.view.inputmethod.IAccessibilityInputMethodSessionInvoker$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    IAccessibilityInputMethodSessionInvoker.this.lambda$invalidateInput$1(editorInfo, connection, sessionId);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: invalidateInputInternal, reason: merged with bridge method [inline-methods] */
    public void lambda$invalidateInput$1(EditorInfo editorInfo, IRemoteAccessibilityInputConnection connection, int sessionId) {
        try {
            this.mSession.invalidateInput(editorInfo, connection, sessionId);
        } catch (RemoteException e) {
            Log.w(TAG, "A11yIME died", e);
        }
    }
}
