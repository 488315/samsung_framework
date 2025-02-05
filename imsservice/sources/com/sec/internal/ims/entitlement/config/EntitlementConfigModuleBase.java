package com.sec.internal.ims.entitlement.config;

import android.os.Handler;
import android.os.Looper;
import com.sec.internal.helper.State;

/* loaded from: classes.dex */
public abstract class EntitlementConfigModuleBase extends Handler {
    State mState;

    protected static class InitialState extends State {
    }

    protected static class ReadyState extends State {
    }

    protected static class RunningState extends State {
    }

    public void dump() {
    }

    public void forceConfigUpdate() {
    }

    public void onDeviceReady() {
    }

    public void onSimReady(boolean z) {
    }

    public void retriveAkaToken() {
    }

    public void unregisterReceiver() {
    }

    public void updateTelephonyCallStatus(int i, int i2) {
    }

    protected EntitlementConfigModuleBase(Looper looper) {
        super(looper);
        this.mState = new InitialState();
    }

    public void init() {
        updateState(new ReadyState());
    }

    public void start() {
        updateState(new RunningState());
    }

    private void updateState(State state) {
        State state2 = this.mState;
        if (state2 == state) {
            return;
        }
        state2.exit();
        this.mState = state;
        state.enter();
    }
}
