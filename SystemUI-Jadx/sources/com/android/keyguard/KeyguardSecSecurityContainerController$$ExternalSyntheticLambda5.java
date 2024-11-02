package com.android.keyguard;

import android.util.Log;
import android.view.View;
import com.android.keyguard.KeyguardSecurityViewFlipperController;
import com.android.systemui.R;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final /* synthetic */ class KeyguardSecSecurityContainerController$$ExternalSyntheticLambda5 implements KeyguardSecurityViewFlipperController.OnViewInflatedCallback {
    @Override // com.android.keyguard.KeyguardSecurityViewFlipperController.OnViewInflatedCallback
    public final void onViewInflated(KeyguardInputViewController keyguardInputViewController) {
        if (((KeyguardInputView) keyguardInputViewController.mView).getAlpha() < 1.0f || Float.compare(((KeyguardInputView) keyguardInputViewController.mView).getScaleX(), 1.0f) != 0 || Float.compare(((KeyguardInputView) keyguardInputViewController.mView).getScaleY(), 1.0f) != 0) {
            Log.d("KeyguardInputViewController", "restoreAppearance - inputView");
            ((KeyguardInputView) keyguardInputViewController.mView).setAlpha(1.0f);
            ((KeyguardInputView) keyguardInputViewController.mView).setScaleX(1.0f);
            ((KeyguardInputView) keyguardInputViewController.mView).setScaleY(1.0f);
        }
        View findViewById = ((KeyguardInputView) keyguardInputViewController.mView).findViewById(R.id.bottom_container);
        if (findViewById != null) {
            if (findViewById.getAlpha() < 1.0f || Float.compare(findViewById.getScaleX(), 1.0f) != 0 || Float.compare(findViewById.getScaleY(), 1.0f) != 0) {
                Log.d("KeyguardInputViewController", "restoreAppearance - bottom");
                findViewById.setAlpha(1.0f);
                findViewById.setScaleX(1.0f);
                findViewById.setScaleY(1.0f);
            }
        }
    }
}
