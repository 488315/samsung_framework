package android.inputmethodservice;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.util.SparseArray;
import android.view.InputChannel;
import android.view.InputEvent;
import android.view.InputEventReceiver;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.CursorAnchorInfo;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.InputMethodSession;
import com.android.internal.inputmethod.IInputMethodSession;
import com.android.internal.inputmethod.IRemoteInputConnection;
import com.android.internal.os.HandlerCaller;
import com.android.internal.os.SomeArgs;

/* loaded from: classes2.dex */
class IInputMethodSessionWrapper extends IInputMethodSession.Stub implements HandlerCaller.Callback {
    private static final int DO_APP_PRIVATE_COMMAND = 100;
    private static final int DO_DISPLAY_COMPLETIONS = 65;
    private static final int DO_FINISH_INPUT = 140;
    private static final int DO_FINISH_SESSION = 110;
    private static final int DO_INVALIDATE_INPUT = 150;
    private static final int DO_REMOVE_IME_SURFACE = 130;
    private static final int DO_UPDATE_CURSOR = 95;
    private static final int DO_UPDATE_CURSOR_ANCHOR_INFO = 99;
    private static final int DO_UPDATE_EXTRACTED_TEXT = 67;
    private static final int DO_UPDATE_SELECTION = 90;
    private static final int DO_VIEW_CLICKED = 115;
    private static final String TAG = "InputMethodWrapper";
    HandlerCaller mCaller;
    InputChannel mChannel;
    InputMethodSession mInputMethodSession;
    ImeInputEventReceiver mReceiver;

    public IInputMethodSessionWrapper(Context context, InputMethodSession inputMethodSession, InputChannel channel) {
        this.mCaller = new HandlerCaller(context, null, this, true);
        this.mInputMethodSession = inputMethodSession;
        this.mChannel = channel;
        if (channel != null) {
            this.mReceiver = new ImeInputEventReceiver(channel, context.getMainLooper());
        }
    }

    public InputMethodSession getInternalInputMethodSession() {
        return this.mInputMethodSession;
    }

    @Override // com.android.internal.os.HandlerCaller.Callback
    public void executeMessage(Message msg) {
        SomeArgs args;
        if (this.mInputMethodSession == null) {
            switch (msg.what) {
                case 90:
                case 100:
                    args = (SomeArgs) msg.obj;
                    return;
                default:
                    return;
            }
        }
        switch (msg.what) {
            case 65:
                this.mInputMethodSession.displayCompletions((CompletionInfo[]) msg.obj);
                return;
            case 67:
                this.mInputMethodSession.updateExtractedText(msg.arg1, (ExtractedText) msg.obj);
                return;
            case 90:
                args = (SomeArgs) msg.obj;
                this.mInputMethodSession.updateSelection(args.argi1, args.argi2, args.argi3, args.argi4, args.argi5, args.argi6);
                return;
            case 95:
                this.mInputMethodSession.updateCursor((Rect) msg.obj);
                return;
            case 99:
                this.mInputMethodSession.updateCursorAnchorInfo((CursorAnchorInfo) msg.obj);
                return;
            case 100:
                args = (SomeArgs) msg.obj;
                this.mInputMethodSession.appPrivateCommand((String) args.arg1, (Bundle) args.arg2);
                return;
            case 110:
                doFinishSession();
                return;
            case 115:
                this.mInputMethodSession.viewClicked(msg.arg1 == 1);
                return;
            case 130:
                this.mInputMethodSession.removeImeSurface();
                return;
            case 140:
                this.mInputMethodSession.finishInput();
                return;
            case 150:
                args = (SomeArgs) msg.obj;
                try {
                    this.mInputMethodSession.invalidateInputInternal((EditorInfo) args.arg1, (IRemoteInputConnection) args.arg2, msg.arg1);
                    return;
                } finally {
                    args.recycle();
                }
            default:
                Log.w(TAG, "Unhandled message code: " + msg.what);
                return;
        }
    }

    private void doFinishSession() {
        this.mInputMethodSession = null;
        if (this.mReceiver != null) {
            this.mReceiver.dispose();
            this.mReceiver = null;
        }
        if (this.mChannel != null) {
            this.mChannel.dispose();
            this.mChannel = null;
        }
    }

    @Override // com.android.internal.inputmethod.IInputMethodSession
    public void displayCompletions(CompletionInfo[] completions) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(65, completions));
    }

    @Override // com.android.internal.inputmethod.IInputMethodSession
    public void updateExtractedText(int token, ExtractedText text) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageIO(67, token, text));
    }

    @Override // com.android.internal.inputmethod.IInputMethodSession
    public void updateSelection(int oldSelStart, int oldSelEnd, int newSelStart, int newSelEnd, int candidatesStart, int candidatesEnd) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageIIIIII(90, oldSelStart, oldSelEnd, newSelStart, newSelEnd, candidatesStart, candidatesEnd));
    }

    @Override // com.android.internal.inputmethod.IInputMethodSession
    public void viewClicked(boolean z) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageI(115, z ? 1 : 0));
    }

    @Override // com.android.internal.inputmethod.IInputMethodSession
    public void removeImeSurface() {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessage(130));
    }

    @Override // com.android.internal.inputmethod.IInputMethodSession
    public void updateCursor(Rect newCursor) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(95, newCursor));
    }

    @Override // com.android.internal.inputmethod.IInputMethodSession
    public void updateCursorAnchorInfo(CursorAnchorInfo cursorAnchorInfo) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(99, cursorAnchorInfo));
    }

    @Override // com.android.internal.inputmethod.IInputMethodSession
    public void appPrivateCommand(String action, Bundle data) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageOO(100, action, data));
    }

    @Override // com.android.internal.inputmethod.IInputMethodSession
    public void finishSession() {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessage(110));
    }

    @Override // com.android.internal.inputmethod.IInputMethodSession
    public void invalidateInput(EditorInfo editorInfo, IRemoteInputConnection inputConnection, int sessionId) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageIOO(150, sessionId, editorInfo, inputConnection));
    }

    @Override // com.android.internal.inputmethod.IInputMethodSession
    public void finishInput() {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessage(140));
    }

    private final class ImeInputEventReceiver extends InputEventReceiver implements InputMethodSession.EventCallback {
        private final SparseArray<InputEvent> mPendingEvents;

        public ImeInputEventReceiver(InputChannel inputChannel, Looper looper) {
            super(inputChannel, looper);
            this.mPendingEvents = new SparseArray<>();
        }

        @Override // android.view.InputEventReceiver
        public void onInputEvent(InputEvent event) {
            if (IInputMethodSessionWrapper.this.mInputMethodSession == null) {
                finishInputEvent(event, false);
                return;
            }
            int seq = event.getSequenceNumber();
            this.mPendingEvents.put(seq, event);
            if (event instanceof KeyEvent) {
                KeyEvent keyEvent = (KeyEvent) event;
                IInputMethodSessionWrapper.this.mInputMethodSession.dispatchKeyEvent(seq, keyEvent, this);
                return;
            }
            MotionEvent motionEvent = (MotionEvent) event;
            if (motionEvent.isFromSource(4)) {
                IInputMethodSessionWrapper.this.mInputMethodSession.dispatchTrackballEvent(seq, motionEvent, this);
            } else {
                IInputMethodSessionWrapper.this.mInputMethodSession.dispatchGenericMotionEvent(seq, motionEvent, this);
            }
        }

        @Override // android.view.inputmethod.InputMethodSession.EventCallback
        public void finishedEvent(int seq, boolean handled) {
            int index = this.mPendingEvents.indexOfKey(seq);
            if (index >= 0) {
                InputEvent event = this.mPendingEvents.valueAt(index);
                this.mPendingEvents.removeAt(index);
                finishInputEvent(event, handled);
            }
        }
    }
}
