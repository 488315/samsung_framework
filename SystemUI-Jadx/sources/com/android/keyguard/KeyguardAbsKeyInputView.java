package com.android.keyguard;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import com.android.internal.widget.LockscreenCredential;
import com.android.systemui.R;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public abstract class KeyguardAbsKeyInputView extends KeyguardInputView {
    public View mEcaView;
    public KeyguardAbsKeyInputViewController$$ExternalSyntheticLambda0 mKeyDownListener;

    public KeyguardAbsKeyInputView(Context context) {
        this(context, null);
    }

    public abstract LockscreenCredential getEnteredCredential();

    public abstract int getPasswordTextViewId();

    public abstract int getPromptReasonStringRes(int i);

    public int getWrongPasswordStringId() {
        return R.string.kg_wrong_password;
    }

    @Override // com.android.keyguard.KeyguardInputView, android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mEcaView = findViewById(R.id.keyguard_selector_fade_container);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        KeyguardAbsKeyInputViewController$$ExternalSyntheticLambda0 keyguardAbsKeyInputViewController$$ExternalSyntheticLambda0 = this.mKeyDownListener;
        if (keyguardAbsKeyInputViewController$$ExternalSyntheticLambda0 != null) {
            KeyguardAbsKeyInputViewController keyguardAbsKeyInputViewController = keyguardAbsKeyInputViewController$$ExternalSyntheticLambda0.f$0;
            if (i != 0) {
                keyguardAbsKeyInputViewController.onUserInput();
                return false;
            }
            keyguardAbsKeyInputViewController.getClass();
            return false;
        }
        return false;
    }

    public abstract void resetPasswordText(boolean z, boolean z2);

    public abstract void setPasswordEntryEnabled(boolean z);

    public abstract void setPasswordEntryInputEnabled(boolean z);

    public KeyguardAbsKeyInputView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
