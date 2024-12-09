package com.sec.internal.helper;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Vector;

/* loaded from: classes.dex */
public class StateMachine {
    public static final boolean HANDLED = true;
    public static final boolean NOT_HANDLED = false;
    private static final int SM_INIT_CMD = -2;
    private static final int SM_QUIT_CMD = -1;
    private String mName;
    private SmHandler mSmHandler;
    private HandlerThread mSmThread;

    protected String getLogRecString(Message message) {
        return "";
    }

    protected String getWhatToString(int i) {
        return null;
    }

    protected void haltedProcessMessage(Message message) {
    }

    protected void onHalting() {
    }

    protected void onPostHandleMessage(Message message) {
    }

    protected void onPreHandleMessage(Message message) {
    }

    protected void onQuitting() {
    }

    protected boolean recordLogRec(Message message) {
        return true;
    }

    public static class LogRec {
        private IState mDstState;
        private String mInfo;
        private IState mOrgState;
        private StateMachine mSm;
        private IState mState;
        private long mTime;
        private int mWhat;

        LogRec(StateMachine stateMachine, Message message, String str, IState iState, IState iState2, IState iState3) {
            update(stateMachine, message, str, iState, iState2, iState3);
        }

        public void update(StateMachine stateMachine, Message message, String str, IState iState, IState iState2, IState iState3) {
            this.mSm = stateMachine;
            this.mTime = System.currentTimeMillis();
            this.mWhat = message != null ? message.what : 0;
            this.mInfo = str;
            this.mState = iState;
            this.mOrgState = iState2;
            this.mDstState = iState3;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("time=");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(this.mTime);
            sb.append(String.format("%tm-%td %tH:%tM:%tS.%tL", calendar, calendar, calendar, calendar, calendar, calendar));
            sb.append(" processed=");
            IState iState = this.mState;
            sb.append(iState == null ? "<null>" : iState.getName());
            sb.append(" org=");
            IState iState2 = this.mOrgState;
            sb.append(iState2 == null ? "<null>" : iState2.getName());
            sb.append(" dest=");
            IState iState3 = this.mDstState;
            sb.append(iState3 != null ? iState3.getName() : "<null>");
            sb.append(" what=");
            StateMachine stateMachine = this.mSm;
            String whatToString = stateMachine != null ? stateMachine.getWhatToString(this.mWhat) : "";
            if (TextUtils.isEmpty(whatToString)) {
                sb.append(this.mWhat);
                sb.append("(0x");
                sb.append(Integer.toHexString(this.mWhat));
                sb.append(")");
            } else {
                sb.append(whatToString);
            }
            if (!TextUtils.isEmpty(this.mInfo)) {
                sb.append(" ");
                sb.append(this.mInfo);
            }
            return sb.toString();
        }
    }

    private static class LogRecords {
        private static final int DEFAULT_SIZE = 20;
        private int mCount;
        private boolean mLogOnlyTransitions;
        private Vector<LogRec> mLogRecVector;
        private int mMaxSize;
        private int mOldestIndex;

        private LogRecords() {
            this.mLogRecVector = new Vector<>();
            this.mMaxSize = 20;
            this.mOldestIndex = 0;
            this.mCount = 0;
            this.mLogOnlyTransitions = false;
        }

        synchronized boolean logOnlyTransitions() {
            return this.mLogOnlyTransitions;
        }

        synchronized int size() {
            return this.mLogRecVector.size();
        }

        synchronized int count() {
            return this.mCount;
        }

        synchronized void cleanup() {
            this.mLogRecVector.clear();
        }

        synchronized LogRec get(int i) {
            int i2 = this.mOldestIndex + i;
            int i3 = this.mMaxSize;
            if (i2 >= i3) {
                i2 -= i3;
            }
            if (i2 >= size()) {
                return null;
            }
            return this.mLogRecVector.get(i2);
        }

        synchronized void add(StateMachine stateMachine, Message message, String str, IState iState, IState iState2, IState iState3) {
            this.mCount++;
            if (this.mLogRecVector.size() < this.mMaxSize) {
                this.mLogRecVector.add(new LogRec(stateMachine, message, str, iState, iState2, iState3));
            } else {
                LogRec logRec = this.mLogRecVector.get(this.mOldestIndex);
                int i = this.mOldestIndex + 1;
                this.mOldestIndex = i;
                if (i >= this.mMaxSize) {
                    this.mOldestIndex = 0;
                }
                logRec.update(stateMachine, message, str, iState, iState2, iState3);
            }
        }
    }

    private static class SmHandler extends Handler {
        private static final Object mSmHandlerObj = new Object();
        private boolean mDbg;
        private ArrayList<Message> mDeferredMessages;
        private State mDestState;
        private HaltingState mHaltingState;
        private boolean mHasQuit;
        private State mInitialState;
        private boolean mIsConstructionCompleted;
        private LogRecords mLogRecords;
        private Message mMsg;
        private QuittingState mQuittingState;
        private StateMachine mSm;
        private HashMap<State, StateInfo> mStateInfo;
        private StateInfo[] mStateStack;
        private int mStateStackTopIndex;
        private StateInfo[] mTempStateStack;
        private int mTempStateStackCount;

        private static class StateInfo {
            boolean active;
            StateInfo parentStateInfo;
            State state;

            private StateInfo() {
            }

            public String toString() {
                StringBuilder sb = new StringBuilder();
                sb.append("state=");
                sb.append(this.state.getName());
                sb.append(",active=");
                sb.append(this.active);
                sb.append(",parent=");
                StateInfo stateInfo = this.parentStateInfo;
                sb.append(stateInfo == null ? "null" : stateInfo.state.getName());
                return sb.toString();
            }
        }

        private class HaltingState extends State {
            private HaltingState() {
            }

            @Override // com.sec.internal.helper.State, com.sec.internal.helper.IState
            public boolean processMessage(Message message) {
                SmHandler.this.mSm.haltedProcessMessage(message);
                return true;
            }
        }

        private static class QuittingState extends State {
            @Override // com.sec.internal.helper.State, com.sec.internal.helper.IState
            public boolean processMessage(Message message) {
                return false;
            }

            private QuittingState() {
            }
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            State state;
            int i;
            StateMachine stateMachine;
            StateMachine stateMachine2;
            int i2;
            if (this.mHasQuit) {
                return;
            }
            StateMachine stateMachine3 = this.mSm;
            if (stateMachine3 != null && (i2 = message.what) != -2 && i2 != -1) {
                stateMachine3.onPreHandleMessage(message);
            }
            if (this.mDbg && (stateMachine2 = this.mSm) != null) {
                stateMachine2.log("handleMessage: E msg.what=" + message.what);
            }
            this.mMsg = message;
            boolean z = this.mIsConstructionCompleted;
            if (z) {
                state = processMsg(message);
            } else if (!z && message.what == -2 && message.obj == mSmHandlerObj) {
                this.mIsConstructionCompleted = true;
                invokeEnterMethods(0);
                state = null;
            } else {
                throw new RuntimeException("StateMachine.handleMessage: The start method not called, received msg: " + message);
            }
            performTransitions(state, message);
            if (this.mDbg && (stateMachine = this.mSm) != null) {
                stateMachine.log("handleMessage: X");
            }
            StateMachine stateMachine4 = this.mSm;
            if (stateMachine4 == null || (i = message.what) == -2 || i == -1) {
                return;
            }
            stateMachine4.onPostHandleMessage(message);
        }

        private void performTransitions(State state, Message message) {
            StateMachine stateMachine;
            StateMachine stateMachine2 = this.mSm;
            if (stateMachine2 == null) {
                return;
            }
            State state2 = this.mStateStack[this.mStateStackTopIndex].state;
            boolean z = stateMachine2.recordLogRec(this.mMsg) && message.obj != mSmHandlerObj;
            if (this.mLogRecords.logOnlyTransitions()) {
                if (this.mDestState != null) {
                    LogRecords logRecords = this.mLogRecords;
                    StateMachine stateMachine3 = this.mSm;
                    Message message2 = this.mMsg;
                    logRecords.add(stateMachine3, message2, stateMachine3.getLogRecString(message2), state, state2, this.mDestState);
                }
            } else if (z) {
                LogRecords logRecords2 = this.mLogRecords;
                StateMachine stateMachine4 = this.mSm;
                Message message3 = this.mMsg;
                logRecords2.add(stateMachine4, message3, stateMachine4.getLogRecString(message3), state, state2, this.mDestState);
            }
            State state3 = this.mDestState;
            if (state3 != null) {
                while (true) {
                    if (this.mDbg && (stateMachine = this.mSm) != null) {
                        stateMachine.log("handleMessage: new destination call exit/enter");
                    }
                    StateInfo stateInfo = setupTempStateStackWithStatesToEnter(state3);
                    synchronized (this) {
                        invokeExitMethods(stateInfo);
                        invokeEnterMethods(moveTempStateStackToStateStack());
                    }
                    moveDeferredMessageAtFrontOfQueue();
                    State state4 = this.mDestState;
                    if (state3 == state4) {
                        break;
                    } else {
                        state3 = state4;
                    }
                }
                this.mDestState = null;
            }
            if (state3 != null) {
                if (state3 == this.mQuittingState) {
                    this.mSm.onQuitting();
                    cleanupAfterQuitting();
                } else if (state3 == this.mHaltingState) {
                    this.mSm.onHalting();
                }
            }
        }

        private final void cleanupAfterQuitting() {
            if (this.mSm.mSmThread != null) {
                getLooper().quit();
                this.mSm.mSmThread = null;
            }
            this.mSm.mSmHandler = null;
            this.mSm = null;
            this.mMsg = null;
            this.mLogRecords.cleanup();
            this.mStateStack = null;
            this.mTempStateStack = null;
            this.mStateInfo.clear();
            this.mInitialState = null;
            this.mDestState = null;
            this.mDeferredMessages.clear();
            this.mHasQuit = true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void completeConstruction() {
            if (this.mDbg) {
                this.mSm.log("completeConstruction: E");
            }
            int i = 0;
            for (StateInfo stateInfo : this.mStateInfo.values()) {
                int i2 = 0;
                while (stateInfo != null) {
                    stateInfo = stateInfo.parentStateInfo;
                    i2++;
                }
                if (i < i2) {
                    i = i2;
                }
            }
            if (this.mDbg) {
                this.mSm.log("completeConstruction: maxDepth=" + i);
            }
            this.mStateStack = new StateInfo[i];
            this.mTempStateStack = new StateInfo[i];
            setupInitialStateStack();
            sendMessageAtFrontOfQueue(obtainMessage(-2, mSmHandlerObj));
            if (this.mDbg) {
                this.mSm.log("completeConstruction: X");
            }
        }

        private final State processMsg(Message message) {
            StateInfo stateInfo = this.mStateStack[this.mStateStackTopIndex];
            if (this.mDbg) {
                this.mSm.log("processMsg: " + stateInfo.state.getName());
            }
            if (isQuit(message)) {
                transitionTo(this.mQuittingState);
            } else {
                while (true) {
                    if (stateInfo.state.processMessage(message)) {
                        break;
                    }
                    stateInfo = stateInfo.parentStateInfo;
                    if (stateInfo == null) {
                        this.mSm.unhandledMessage(message);
                        break;
                    }
                    if (this.mDbg) {
                        this.mSm.log("processMsg: " + stateInfo.state.getName());
                    }
                }
            }
            if (stateInfo != null) {
                return stateInfo.state;
            }
            return null;
        }

        private final void invokeExitMethods(StateInfo stateInfo) {
            StateInfo stateInfo2;
            while (true) {
                int i = this.mStateStackTopIndex;
                if (i < 0 || (stateInfo2 = this.mStateStack[i]) == stateInfo) {
                    return;
                }
                State state = stateInfo2.state;
                if (this.mDbg) {
                    this.mSm.log("invokeExitMethods: " + state.getName());
                }
                state.exit();
                StateInfo[] stateInfoArr = this.mStateStack;
                int i2 = this.mStateStackTopIndex;
                stateInfoArr[i2].active = false;
                this.mStateStackTopIndex = i2 - 1;
            }
        }

        private final void invokeEnterMethods(int i) {
            while (i <= this.mStateStackTopIndex) {
                if (this.mDbg) {
                    this.mSm.log("invokeEnterMethods: " + this.mStateStack[i].state.getName());
                }
                this.mStateStack[i].state.enter();
                this.mStateStack[i].active = true;
                i++;
            }
        }

        private final void moveDeferredMessageAtFrontOfQueue() {
            for (int size = this.mDeferredMessages.size() - 1; size >= 0; size--) {
                Message message = this.mDeferredMessages.get(size);
                if (this.mDbg) {
                    this.mSm.log("moveDeferredMessageAtFrontOfQueue; what=" + message.what);
                }
                sendMessageAtFrontOfQueue(message);
            }
            this.mDeferredMessages.clear();
        }

        private final int moveTempStateStackToStateStack() {
            int i = this.mStateStackTopIndex + 1;
            int i2 = i;
            for (int i3 = this.mTempStateStackCount - 1; i3 >= 0; i3--) {
                if (this.mDbg) {
                    this.mSm.log("moveTempStackToStateStack: i=" + i3 + ",j=" + i2);
                }
                this.mStateStack[i2] = this.mTempStateStack[i3];
                i2++;
            }
            this.mStateStackTopIndex = i2 - 1;
            if (this.mDbg) {
                this.mSm.log("moveTempStackToStateStack: X mStateStackTop=" + this.mStateStackTopIndex + ",startingIndex=" + i + ",Top=" + this.mStateStack[this.mStateStackTopIndex].state.getName());
            }
            return i;
        }

        private final StateInfo setupTempStateStackWithStatesToEnter(State state) {
            this.mTempStateStackCount = 0;
            StateInfo stateInfo = this.mStateInfo.get(state);
            do {
                StateInfo[] stateInfoArr = this.mTempStateStack;
                int i = this.mTempStateStackCount;
                this.mTempStateStackCount = i + 1;
                stateInfoArr[i] = stateInfo;
                stateInfo = stateInfo.parentStateInfo;
                if (stateInfo == null) {
                    break;
                }
            } while (!stateInfo.active);
            if (this.mDbg) {
                this.mSm.log("setupTempStateStackWithStatesToEnter: X mTempStateStackCount=" + this.mTempStateStackCount + ",curStateInfo: " + stateInfo);
            }
            return stateInfo;
        }

        private final void setupInitialStateStack() {
            if (this.mDbg) {
                this.mSm.log("setupInitialStateStack: E mInitialState=" + this.mInitialState.getName());
            }
            StateInfo stateInfo = this.mStateInfo.get(this.mInitialState);
            this.mTempStateStackCount = 0;
            while (stateInfo != null) {
                StateInfo[] stateInfoArr = this.mTempStateStack;
                int i = this.mTempStateStackCount;
                stateInfoArr[i] = stateInfo;
                stateInfo = stateInfo.parentStateInfo;
                this.mTempStateStackCount = i + 1;
            }
            this.mStateStackTopIndex = -1;
            moveTempStateStackToStateStack();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final IState getCurrentState() {
            StateInfo[] stateInfoArr;
            StateInfo stateInfo;
            int i = this.mStateStackTopIndex;
            if (i < 0 || (stateInfoArr = this.mStateStack) == null || (stateInfo = stateInfoArr[i]) == null) {
                return null;
            }
            return stateInfo.state;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final StateInfo addState(State state, State state2) {
            StateInfo stateInfo;
            if (this.mDbg) {
                StateMachine stateMachine = this.mSm;
                StringBuilder sb = new StringBuilder();
                sb.append("addStateInternal: E state=");
                sb.append(state.getName());
                sb.append(",parent=");
                sb.append(state2 == null ? "" : state2.getName());
                stateMachine.log(sb.toString());
            }
            if (state2 != null) {
                stateInfo = this.mStateInfo.get(state2);
                if (stateInfo == null) {
                    stateInfo = addState(state2, null);
                }
            } else {
                stateInfo = null;
            }
            StateInfo stateInfo2 = this.mStateInfo.get(state);
            if (stateInfo2 == null) {
                stateInfo2 = new StateInfo();
                this.mStateInfo.put(state, stateInfo2);
            }
            StateInfo stateInfo3 = stateInfo2.parentStateInfo;
            if (stateInfo3 != null && stateInfo3 != stateInfo) {
                throw new RuntimeException("state already added");
            }
            stateInfo2.state = state;
            stateInfo2.parentStateInfo = stateInfo;
            stateInfo2.active = false;
            if (this.mDbg) {
                this.mSm.log("addStateInternal: X stateInfo: " + stateInfo2);
            }
            return stateInfo2;
        }

        private SmHandler(Looper looper, StateMachine stateMachine) {
            super(looper);
            this.mHasQuit = false;
            this.mDbg = false;
            this.mLogRecords = new LogRecords();
            this.mStateStackTopIndex = -1;
            this.mHaltingState = new HaltingState();
            this.mQuittingState = new QuittingState();
            this.mStateInfo = new HashMap<>();
            this.mDeferredMessages = new ArrayList<>();
            this.mSm = stateMachine;
            addState(this.mHaltingState, null);
            addState(this.mQuittingState, null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void setInitialState(State state) {
            if (this.mDbg) {
                this.mSm.log("setInitialState: initialState=" + state.getName());
            }
            this.mInitialState = state;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void transitionTo(IState iState) {
            this.mDestState = (State) iState;
            if (this.mDbg) {
                this.mSm.log("transitionTo: destState=" + this.mDestState.getName());
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void deferMessage(Message message) {
            if (this.mDbg) {
                this.mSm.log("deferMessage: msg=" + message.what);
            }
            Message obtainMessage = obtainMessage();
            obtainMessage.copyFrom(message);
            this.mDeferredMessages.add(obtainMessage);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void quit() {
            if (this.mDbg) {
                this.mSm.log("quit:");
            }
            sendMessage(obtainMessage(-1, mSmHandlerObj));
        }

        private final boolean isQuit(Message message) {
            return message.what == -1 && message.obj == mSmHandlerObj;
        }
    }

    private void initStateMachine(String str, Looper looper) {
        this.mName = str;
        this.mSmHandler = new SmHandler(looper, this);
    }

    protected StateMachine(String str, Looper looper) {
        initStateMachine(str, looper);
    }

    protected StateMachine(String str, Handler handler) {
        initStateMachine(str, handler.getLooper());
    }

    protected final void addState(State state, State state2) {
        this.mSmHandler.addState(state, state2);
    }

    protected final void addState(State state) {
        this.mSmHandler.addState(state, null);
    }

    protected final void setInitialState(State state) {
        this.mSmHandler.setInitialState(state);
    }

    public final IState getCurrentState() {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return null;
        }
        return smHandler.getCurrentState();
    }

    public final void transitionTo(IState iState) {
        this.mSmHandler.transitionTo(iState);
    }

    public final void deferMessage(Message message) {
        this.mSmHandler.deferMessage(message);
    }

    protected void unhandledMessage(Message message) {
        if (this.mSmHandler.mDbg) {
            loge(" - unhandledMessage: msg.what=" + message.what);
        }
    }

    public final String getName() {
        return this.mName;
    }

    public final int getLogRecSize() {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return 0;
        }
        return smHandler.mLogRecords.size();
    }

    public final int getLogRecCount() {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return 0;
        }
        return smHandler.mLogRecords.count();
    }

    public final LogRec getLogRec(int i) {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return null;
        }
        return smHandler.mLogRecords.get(i);
    }

    public final Handler getHandler() {
        return this.mSmHandler;
    }

    public final Message obtainMessage(int i) {
        return Message.obtain(this.mSmHandler, i);
    }

    public final Message obtainMessage(int i, Object obj) {
        return Message.obtain(this.mSmHandler, i, obj);
    }

    public final Message obtainMessage(int i, int i2) {
        return Message.obtain(this.mSmHandler, i, i2, 0);
    }

    public final Message obtainMessage(int i, int i2, int i3) {
        return Message.obtain(this.mSmHandler, i, i2, i3);
    }

    public final Message obtainMessage(int i, int i2, int i3, Object obj) {
        return Message.obtain(this.mSmHandler, i, i2, i3, obj);
    }

    public final void sendMessage(int i) {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return;
        }
        smHandler.sendMessage(obtainMessage(i));
    }

    public final void sendMessage(int i, Object obj) {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return;
        }
        smHandler.sendMessage(obtainMessage(i, obj));
    }

    public final void sendMessage(int i, int i2) {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return;
        }
        smHandler.sendMessage(obtainMessage(i, i2));
    }

    public final void sendMessage(int i, int i2, int i3) {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return;
        }
        smHandler.sendMessage(obtainMessage(i, i2, i3));
    }

    public final void sendMessage(int i, int i2, int i3, Object obj) {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return;
        }
        smHandler.sendMessage(obtainMessage(i, i2, i3, obj));
    }

    public final void sendMessage(Message message) {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return;
        }
        smHandler.sendMessage(message);
    }

    public final void sendMessageDelayed(int i, long j) {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return;
        }
        smHandler.sendMessageDelayed(obtainMessage(i), j);
    }

    public final void sendMessageDelayed(int i, Object obj, long j) {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return;
        }
        smHandler.sendMessageDelayed(obtainMessage(i, obj), j);
    }

    public final void sendMessageDelayed(int i, int i2, long j) {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return;
        }
        smHandler.sendMessageDelayed(obtainMessage(i, i2), j);
    }

    public final void sendMessageDelayed(int i, int i2, int i3, long j) {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return;
        }
        smHandler.sendMessageDelayed(obtainMessage(i, i2, i3), j);
    }

    public final void sendMessageDelayed(int i, int i2, int i3, Object obj, long j) {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return;
        }
        smHandler.sendMessageDelayed(obtainMessage(i, i2, i3, obj), j);
    }

    public final void sendMessageDelayed(Message message, long j) {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return;
        }
        smHandler.sendMessageDelayed(message, j);
    }

    protected final void sendMessageAtFrontOfQueue(int i) {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return;
        }
        smHandler.sendMessageAtFrontOfQueue(obtainMessage(i));
    }

    protected final void sendMessageAtFrontOfQueue(int i, Object obj) {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return;
        }
        smHandler.sendMessageAtFrontOfQueue(obtainMessage(i, obj));
    }

    protected final void sendMessageAtFrontOfQueue(int i, int i2) {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return;
        }
        smHandler.sendMessageAtFrontOfQueue(obtainMessage(i, i2));
    }

    protected final void sendMessageAtFrontOfQueue(int i, int i2, int i3) {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return;
        }
        smHandler.sendMessageAtFrontOfQueue(obtainMessage(i, i2, i3));
    }

    protected final void sendMessageAtFrontOfQueue(int i, int i2, int i3, Object obj) {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return;
        }
        smHandler.sendMessageAtFrontOfQueue(obtainMessage(i, i2, i3, obj));
    }

    protected final void sendMessageAtFrontOfQueue(Message message) {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return;
        }
        smHandler.sendMessageAtFrontOfQueue(message);
    }

    public final void removeMessages(int i) {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return;
        }
        smHandler.removeMessages(i);
    }

    public final void removeMessages(int i, Object obj) {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler == null || obj == null) {
            return;
        }
        smHandler.removeMessages(i, obj);
    }

    public final void quit() {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return;
        }
        smHandler.quit();
    }

    public void start() {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return;
        }
        smHandler.completeConstruction();
    }

    public final boolean hasMessages(int i) {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return false;
        }
        return smHandler.hasMessages(i);
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.println(getName() + ":");
        printWriter.println(" total records=" + getLogRecCount());
        for (int i = 0; i < getLogRecSize(); i++) {
            LogRec logRec = getLogRec(i);
            printWriter.println(" rec[" + i + "]: " + (logRec != null ? logRec.toString() : "NULL"));
            printWriter.flush();
        }
        IState currentState = getCurrentState();
        if (currentState != null) {
            printWriter.println("curState=" + currentState.getName());
        }
    }

    public String toString() {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        dump(null, printWriter, null);
        printWriter.flush();
        printWriter.close();
        return stringWriter.toString();
    }

    public void log(String str) {
        Log.d(this.mName, str);
    }

    public void logi(String str) {
        Log.i(this.mName, str);
    }

    public void loge(String str) {
        Log.e(this.mName, str);
    }
}
