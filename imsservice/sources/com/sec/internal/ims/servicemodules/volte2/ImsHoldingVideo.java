package com.sec.internal.ims.servicemodules.volte2;

import android.os.Message;
import android.util.Log;
import com.sec.internal.ims.servicemodules.ss.UtStateMachine;

/* loaded from: classes.dex */
public class ImsHoldingVideo extends CallState {
    ImsHoldingVideo(CallStateMachine callStateMachine) {
        super(callStateMachine);
    }

    @Override // com.sec.internal.helper.State, com.sec.internal.helper.IState
    public void enter() {
        Log.i(this.LOG_TAG, "Enter [HoldingVideo]");
    }

    @Override // com.sec.internal.helper.State, com.sec.internal.helper.IState
    public boolean processMessage(Message message) {
        Log.i(this.LOG_TAG, "[HoldingVideo] processMessage " + message.what);
        int i = message.what;
        if (i == 51) {
            Log.i(this.LOG_TAG, "[HoldingVideo] defer hold request.");
            this.mCsm.deferMessage(message);
        } else if (i == 71) {
            Log.i(this.LOG_TAG, "[HoldingVideo] defer resume request.");
            this.mCsm.deferMessage(message);
        } else {
            switch (i) {
                case 80:
                    Log.i(this.LOG_TAG, "[HoldingVideo] defer hold video request.");
                    this.mCsm.deferMessage(message);
                    break;
                case 81:
                    Log.i(this.LOG_TAG, "[HoldingVideo] defer resume video request.");
                    CallStateMachine callStateMachine = this.mCsm;
                    callStateMachine.isDeferedVideoResume = true;
                    callStateMachine.deferMessage(message);
                    break;
                case 82:
                    Log.i(this.LOG_TAG, "[HoldingVideo] Video held.");
                    this.mCsm.notifyOnModified(this.mSession.getCallProfile().getCallType());
                    CallStateMachine callStateMachine2 = this.mCsm;
                    callStateMachine2.transitionTo(callStateMachine2.mVideoHeld);
                    break;
                case 83:
                    Log.i(this.LOG_TAG, "[HoldingVideo] do not handle video resumed");
                    break;
                case 84:
                    Log.i(this.LOG_TAG, "[HoldingVideo] video hold failed, Try again");
                    CallStateMachine callStateMachine3 = this.mCsm;
                    callStateMachine3.transitionTo(callStateMachine3.mInCall);
                    this.mCsm.sendMessageDelayed(80, UtStateMachine.HTTP_READ_TIMEOUT_GCF);
                    break;
                default:
                    return false;
            }
        }
        return true;
    }

    @Override // com.sec.internal.helper.State, com.sec.internal.helper.IState
    public void exit() {
        this.mCsm.setPreviousState(this);
    }
}
