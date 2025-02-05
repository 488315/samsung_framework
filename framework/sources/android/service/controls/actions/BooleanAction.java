package android.service.controls.actions;

import android.os.Bundle;

/* loaded from: classes3.dex */
public final class BooleanAction extends ControlAction {
    private static final String KEY_NEW_STATE = "key_new_state";
    private static final int TYPE = 1;
    private final boolean mNewState;

    public BooleanAction(String templateId, boolean newState) {
        this(templateId, newState, null);
    }

    public BooleanAction(String templateId, boolean newState, String challengeValue) {
        super(templateId, challengeValue);
        this.mNewState = newState;
    }

    BooleanAction(Bundle b) {
        super(b);
        this.mNewState = b.getBoolean(KEY_NEW_STATE);
    }

    public boolean getNewState() {
        return this.mNewState;
    }

    @Override // android.service.controls.actions.ControlAction
    public int getActionType() {
        return 1;
    }

    @Override // android.service.controls.actions.ControlAction
    Bundle getDataBundle() {
        Bundle b = super.getDataBundle();
        b.putBoolean(KEY_NEW_STATE, this.mNewState);
        return b;
    }
}
