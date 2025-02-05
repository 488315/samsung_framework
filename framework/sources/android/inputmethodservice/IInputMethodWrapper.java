package android.inputmethodservice;

import android.Manifest;
import android.content.Context;
import android.os.Binder;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.InputChannel;
import android.view.MotionEvent;
import android.view.inputmethod.CursorAnchorInfo;
import android.view.inputmethod.ImeTracker;
import android.view.inputmethod.InputBinding;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodSession;
import android.view.inputmethod.InputMethodSubtype;
import com.android.internal.inputmethod.CancellationGroup;
import com.android.internal.inputmethod.IConnectionlessHandwritingCallback;
import com.android.internal.inputmethod.IInlineSuggestionsRequestCallback;
import com.android.internal.inputmethod.IInputMethod;
import com.android.internal.inputmethod.IInputMethodSession;
import com.android.internal.inputmethod.IInputMethodSessionCallback;
import com.android.internal.inputmethod.IRemoteInputConnection;
import com.android.internal.inputmethod.InlineSuggestionsRequestInfo;
import com.android.internal.os.HandlerCaller;
import com.android.internal.os.SomeArgs;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
class IInputMethodWrapper extends IInputMethod.Stub implements HandlerCaller.Callback {
    private static final int DO_CAN_START_STYLUS_HANDWRITING = 100;
    private static final int DO_CHANGE_INPUTMETHOD_SUBTYPE = 80;
    private static final int DO_COMMIT_HANDWRITING_DELEGATION_TEXT_IF_AVAILABLE = 170;
    private static final int DO_CREATE_INLINE_SUGGESTIONS_REQUEST = 90;
    private static final int DO_CREATE_SESSION = 40;
    private static final int DO_DISCARD_HANDWRITING_DELEGATION_TEXT = 180;
    private static final int DO_DUMP = 1;
    private static final int DO_FINISH_STYLUS_HANDWRITING = 130;
    private static final int DO_HIDE_SOFT_INPUT = 70;
    private static final int DO_INITIALIZE_INTERNAL = 10;
    private static final int DO_INIT_INK_WINDOW = 120;
    private static final int DO_MINIMIZE_SOFT_INPUT = 200;
    private static final int DO_ON_NAV_BUTTON_FLAGS_CHANGED = 35;
    private static final int DO_REMOVE_STYLUS_HANDWRITING_WINDOW = 150;
    private static final int DO_SET_INPUT_CONTEXT = 20;
    private static final int DO_SET_SESSION_ENABLED = 45;
    private static final int DO_SET_STYLUS_WINDOW_IDLE_TIMEOUT = 160;
    private static final int DO_SHOW_SOFT_INPUT = 60;
    private static final int DO_START_INPUT = 32;
    private static final int DO_START_STYLUS_HANDWRITING = 110;
    private static final int DO_UNSET_INPUT_CONTEXT = 30;
    private static final int DO_UPDATE_TOOL_TYPE = 140;
    private static final String TAG = "InputMethodWrapper";
    private static final int UNDO_MINIMIZE_SOFT_INPUT = 210;
    final HandlerCaller mCaller;
    CancellationGroup mCancellationGroup = null;
    final Context mContext;
    final WeakReference<InputMethod> mInputMethod;
    final WeakReference<InputMethodServiceInternal> mTarget;
    final int mTargetSdkVersion;

    static final class InputMethodSessionCallbackWrapper implements InputMethod.SessionCallback {
        final IInputMethodSessionCallback mCb;
        final InputChannel mChannel;
        final Context mContext;

        InputMethodSessionCallbackWrapper(Context context, InputChannel channel, IInputMethodSessionCallback cb) {
            this.mContext = context;
            this.mChannel = channel;
            this.mCb = cb;
        }

        @Override // android.view.inputmethod.InputMethod.SessionCallback
        public void sessionCreated(InputMethodSession session) {
            try {
                if (session != null) {
                    IInputMethodSessionWrapper wrap = new IInputMethodSessionWrapper(this.mContext, session, this.mChannel);
                    this.mCb.sessionCreated(wrap);
                } else {
                    if (this.mChannel != null) {
                        this.mChannel.dispose();
                    }
                    this.mCb.sessionCreated(null);
                }
            } catch (RemoteException e) {
            }
        }
    }

    IInputMethodWrapper(InputMethodServiceInternal imsInternal, InputMethod inputMethod) {
        this.mTarget = new WeakReference<>(imsInternal);
        this.mContext = imsInternal.getContext().getApplicationContext();
        this.mCaller = new HandlerCaller(this.mContext, null, this, true);
        this.mInputMethod = new WeakReference<>(inputMethod);
        this.mTargetSdkVersion = imsInternal.getContext().getApplicationInfo().targetSdkVersion;
    }

    @Override // com.android.internal.os.HandlerCaller.Callback
    public void executeMessage(Message msg) {
        InputMethod inputMethod = this.mInputMethod.get();
        InputMethodServiceInternal target = this.mTarget.get();
        switch (msg.what) {
            case 1:
                SomeArgs args = (SomeArgs) msg.obj;
                if (isValid(inputMethod, target, "DO_DUMP")) {
                    FileDescriptor fd = (FileDescriptor) args.arg1;
                    PrintWriter fout = (PrintWriter) args.arg2;
                    String[] dumpArgs = (String[]) args.arg3;
                    CountDownLatch latch = (CountDownLatch) args.arg4;
                    try {
                        try {
                            target.dump(fd, fout, dumpArgs);
                        } catch (RuntimeException e) {
                            fout.println("Exception: " + e);
                        }
                    } finally {
                        latch.countDown();
                    }
                }
                args.recycle();
                return;
            case 10:
                if (isValid(inputMethod, target, "DO_INITIALIZE_INTERNAL")) {
                    inputMethod.initializeInternal((IInputMethod.InitParams) msg.obj);
                    return;
                }
                return;
            case 20:
                if (isValid(inputMethod, target, "DO_SET_INPUT_CONTEXT")) {
                    inputMethod.bindInput((InputBinding) msg.obj);
                    return;
                }
                return;
            case 30:
                if (isValid(inputMethod, target, "DO_UNSET_INPUT_CONTEXT")) {
                    inputMethod.unbindInput();
                    return;
                }
                return;
            case 32:
                SomeArgs args2 = (SomeArgs) msg.obj;
                if (isValid(inputMethod, target, "DO_START_INPUT")) {
                    InputConnection inputConnection = (InputConnection) args2.arg1;
                    IInputMethod.StartInputParams params = (IInputMethod.StartInputParams) args2.arg2;
                    inputMethod.dispatchStartInput(inputConnection, params);
                }
                args2.recycle();
                return;
            case 35:
                if (isValid(inputMethod, target, "DO_ON_NAV_BUTTON_FLAGS_CHANGED")) {
                    inputMethod.onNavButtonFlagsChanged(msg.arg1);
                    return;
                }
                return;
            case 40:
                SomeArgs args3 = (SomeArgs) msg.obj;
                if (isValid(inputMethod, target, "DO_CREATE_SESSION")) {
                    inputMethod.createSession(new InputMethodSessionCallbackWrapper(this.mContext, (InputChannel) args3.arg1, (IInputMethodSessionCallback) args3.arg2));
                }
                args3.recycle();
                return;
            case 45:
                if (isValid(inputMethod, target, "DO_SET_SESSION_ENABLED")) {
                    inputMethod.setSessionEnabled((InputMethodSession) msg.obj, msg.arg1 != 0);
                    return;
                }
                return;
            case 60:
                SomeArgs args4 = (SomeArgs) msg.obj;
                ImeTracker.Token statsToken = (ImeTracker.Token) args4.arg3;
                if (isValid(inputMethod, target, "DO_SHOW_SOFT_INPUT")) {
                    ImeTracker.forLogging().onProgress(statsToken, 12);
                    inputMethod.showSoftInputWithToken(msg.arg1, (ResultReceiver) args4.arg2, (IBinder) args4.arg1, statsToken);
                } else {
                    ImeTracker.forLogging().onFailed(statsToken, 12);
                }
                args4.recycle();
                return;
            case 70:
                SomeArgs args5 = (SomeArgs) msg.obj;
                ImeTracker.Token statsToken2 = (ImeTracker.Token) args5.arg3;
                if (isValid(inputMethod, target, "DO_HIDE_SOFT_INPUT")) {
                    ImeTracker.forLogging().onProgress(statsToken2, 12);
                    inputMethod.hideSoftInputWithToken(msg.arg1, (ResultReceiver) args5.arg2, (IBinder) args5.arg1, statsToken2);
                } else {
                    ImeTracker.forLogging().onFailed(statsToken2, 12);
                }
                args5.recycle();
                return;
            case 80:
                if (isValid(inputMethod, target, "DO_CHANGE_INPUTMETHOD_SUBTYPE")) {
                    inputMethod.changeInputMethodSubtype((InputMethodSubtype) msg.obj);
                    return;
                }
                return;
            case 90:
                SomeArgs args6 = (SomeArgs) msg.obj;
                if (isValid(inputMethod, target, "DO_CREATE_INLINE_SUGGESTIONS_REQUEST")) {
                    inputMethod.onCreateInlineSuggestionsRequest((InlineSuggestionsRequestInfo) args6.arg1, (IInlineSuggestionsRequestCallback) args6.arg2);
                }
                args6.recycle();
                return;
            case 100:
                SomeArgs args7 = (SomeArgs) msg.obj;
                if (isValid(inputMethod, target, "DO_CAN_START_STYLUS_HANDWRITING")) {
                    inputMethod.canStartStylusHandwriting(msg.arg1, (IConnectionlessHandwritingCallback) args7.arg1, (CursorAnchorInfo) args7.arg2, msg.arg2 != 0);
                }
                args7.recycle();
                return;
            case 110:
                SomeArgs args8 = (SomeArgs) msg.obj;
                if (isValid(inputMethod, target, "DO_START_STYLUS_HANDWRITING")) {
                    inputMethod.startStylusHandwriting(msg.arg1, (InputChannel) args8.arg1, (List) args8.arg2);
                }
                args8.recycle();
                return;
            case 120:
                if (isValid(inputMethod, target, "DO_INIT_INK_WINDOW")) {
                    inputMethod.initInkWindow();
                    return;
                }
                return;
            case 130:
                if (isValid(inputMethod, target, "DO_FINISH_STYLUS_HANDWRITING")) {
                    inputMethod.finishStylusHandwriting();
                    return;
                }
                return;
            case 140:
                if (isValid(inputMethod, target, "DO_UPDATE_TOOL_TYPE")) {
                    inputMethod.updateEditorToolType(msg.arg1);
                    return;
                }
                return;
            case 150:
                if (isValid(inputMethod, target, "DO_REMOVE_STYLUS_HANDWRITING_WINDOW")) {
                    inputMethod.removeStylusHandwritingWindow();
                    return;
                }
                return;
            case 160:
                if (isValid(inputMethod, target, "DO_SET_STYLUS_WINDOW_IDLE_TIMEOUT")) {
                    inputMethod.setStylusWindowIdleTimeoutForTest(((Long) msg.obj).longValue());
                    return;
                }
                return;
            case 170:
                if (isValid(inputMethod, target, "DO_COMMIT_HANDWRITING_DELEGATION_TEXT_IF_AVAILABLE")) {
                    inputMethod.commitHandwritingDelegationTextIfAvailable();
                    return;
                }
                return;
            case 180:
                if (isValid(inputMethod, target, "DO_DISCARD_HANDWRITING_DELEGATION_TEXT")) {
                    inputMethod.discardHandwritingDelegationText();
                    return;
                }
                return;
            case 200:
                inputMethod.minimizeSoftInput(msg.arg1);
                return;
            case 210:
                inputMethod.unMinimizeSoftInput();
                return;
            default:
                Log.w(TAG, "Unhandled message code: " + msg.what);
                return;
        }
    }

    @Override // android.os.Binder
    protected void dump(FileDescriptor fd, PrintWriter fout, String[] args) {
        InputMethodServiceInternal target = this.mTarget.get();
        if (target == null) {
            return;
        }
        if (target.getContext().checkCallingOrSelfPermission(Manifest.permission.DUMP) != 0) {
            fout.println("Permission Denial: can't dump InputMethodManager from from pid=" + Binder.getCallingPid() + ", uid=" + Binder.getCallingUid());
            return;
        }
        CountDownLatch latch = new CountDownLatch(1);
        this.mCaller.getHandler().sendMessageAtFrontOfQueue(this.mCaller.obtainMessageOOOO(1, fd, fout, args, latch));
        try {
            if (!latch.await(5L, TimeUnit.SECONDS)) {
                fout.println("Timeout waiting for dump");
            }
        } catch (InterruptedException e) {
            fout.println("Interrupted waiting for dump");
        }
    }

    @Override // com.android.internal.inputmethod.IInputMethod
    public void initializeInternal(IInputMethod.InitParams params) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(10, params));
    }

    @Override // com.android.internal.inputmethod.IInputMethod
    public void onCreateInlineSuggestionsRequest(InlineSuggestionsRequestInfo requestInfo, IInlineSuggestionsRequestCallback cb) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageOO(90, requestInfo, cb));
    }

    @Override // com.android.internal.inputmethod.IInputMethod
    public void bindInput(InputBinding binding) {
        if (this.mCancellationGroup != null) {
            Log.e(TAG, "bindInput must be paired with unbindInput.");
        }
        this.mCancellationGroup = new CancellationGroup();
        InputConnection ic = new RemoteInputConnection(this.mTarget, IRemoteInputConnection.Stub.asInterface(binding.getConnectionToken()), this.mCancellationGroup);
        InputBinding nu = new InputBinding(ic, binding);
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(20, nu));
    }

    @Override // com.android.internal.inputmethod.IInputMethod
    public void unbindInput() {
        if (this.mCancellationGroup != null) {
            this.mCancellationGroup.cancelAll();
            this.mCancellationGroup = null;
        } else {
            Log.e(TAG, "unbindInput must be paired with bindInput.");
        }
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessage(30));
    }

    @Override // com.android.internal.inputmethod.IInputMethod
    public void startInput(IInputMethod.StartInputParams params) {
        if (this.mCancellationGroup == null) {
            Log.e(TAG, "startInput must be called after bindInput.");
            this.mCancellationGroup = new CancellationGroup();
        }
        params.editorInfo.makeCompatible(this.mTargetSdkVersion);
        InputConnection ic = params.remoteInputConnection == null ? null : new RemoteInputConnection(this.mTarget, params.remoteInputConnection, this.mCancellationGroup);
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageOO(32, ic, params));
    }

    @Override // com.android.internal.inputmethod.IInputMethod
    public void onNavButtonFlagsChanged(int navButtonFlags) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageI(35, navButtonFlags));
    }

    @Override // com.android.internal.inputmethod.IInputMethod
    public void createSession(InputChannel channel, IInputMethodSessionCallback callback) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageOO(40, channel, callback));
    }

    @Override // com.android.internal.inputmethod.IInputMethod
    public void setSessionEnabled(IInputMethodSession session, boolean enabled) {
        try {
            InputMethodSession ls = ((IInputMethodSessionWrapper) session).getInternalInputMethodSession();
            if (ls == null) {
                Log.w(TAG, "Session is already finished: " + session);
            } else {
                this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageIO(45, enabled ? 1 : 0, ls));
            }
        } catch (ClassCastException e) {
            Log.w(TAG, "Incoming session not of correct type: " + session, e);
        }
    }

    @Override // com.android.internal.inputmethod.IInputMethod
    public void showSoftInput(IBinder showInputToken, ImeTracker.Token statsToken, int flags, ResultReceiver resultReceiver) {
        ImeTracker.forLogging().onProgress(statsToken, 11);
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageIOOO(60, flags, showInputToken, resultReceiver, statsToken));
    }

    @Override // com.android.internal.inputmethod.IInputMethod
    public void hideSoftInput(IBinder hideInputToken, ImeTracker.Token statsToken, int flags, ResultReceiver resultReceiver) {
        ImeTracker.forLogging().onProgress(statsToken, 11);
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageIOOO(70, flags, hideInputToken, resultReceiver, statsToken));
    }

    @Override // com.android.internal.inputmethod.IInputMethod
    public void changeInputMethodSubtype(InputMethodSubtype subtype) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(80, subtype));
    }

    @Override // com.android.internal.inputmethod.IInputMethod
    public void canStartStylusHandwriting(int i, IConnectionlessHandwritingCallback iConnectionlessHandwritingCallback, CursorAnchorInfo cursorAnchorInfo, boolean z) throws RemoteException {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageIIOO(100, i, z ? 1 : 0, iConnectionlessHandwritingCallback, cursorAnchorInfo));
    }

    @Override // com.android.internal.inputmethod.IInputMethod
    public void updateEditorToolType(int toolType) throws RemoteException {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageI(140, toolType));
    }

    @Override // com.android.internal.inputmethod.IInputMethod
    public void startStylusHandwriting(int requestId, InputChannel channel, List<MotionEvent> stylusEvents) throws RemoteException {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageIOO(110, requestId, channel, stylusEvents));
    }

    @Override // com.android.internal.inputmethod.IInputMethod
    public void commitHandwritingDelegationTextIfAvailable() {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessage(170));
    }

    @Override // com.android.internal.inputmethod.IInputMethod
    public void discardHandwritingDelegationText() {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessage(180));
    }

    @Override // com.android.internal.inputmethod.IInputMethod
    public void initInkWindow() {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessage(120));
    }

    @Override // com.android.internal.inputmethod.IInputMethod
    public void finishStylusHandwriting() {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessage(130));
    }

    @Override // com.android.internal.inputmethod.IInputMethod
    public void removeStylusHandwritingWindow() {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessage(150));
    }

    @Override // com.android.internal.inputmethod.IInputMethod
    public void setStylusWindowIdleTimeoutForTest(long timeout) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(160, Long.valueOf(timeout)));
    }

    private static boolean isValid(InputMethod inputMethod, InputMethodServiceInternal target, String msg) {
        if (inputMethod != null && target != null && !target.isServiceDestroyed()) {
            return true;
        }
        Log.w(TAG, "Ignoring " + msg + ", InputMethod:" + inputMethod + ", InputMethodServiceInternal:" + target);
        return false;
    }

    @Override // com.android.internal.inputmethod.IInputMethod
    public void minimizeSoftInput(int height) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageI(200, height));
    }

    @Override // com.android.internal.inputmethod.IInputMethod
    public void undoMinimizeSoftInput() {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageI(210, 0));
    }
}
