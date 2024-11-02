package com.android.systemui.bixby2.util;

import android.content.Context;
import android.hardware.biometrics.BiometricPrompt;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.android.internal.statusbar.IStatusBarService;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public class BiometricPromptWrapperBixby {
    private static final String TAG = "BiometricPromptWrapperBixby";
    private final BiometricPrompt.Builder mBuilder;
    private final Context mContext;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private BiometricPrompt.AuthenticationCallback mCallback = new BiometricPrompt.AuthenticationCallback() { // from class: com.android.systemui.bixby2.util.BiometricPromptWrapperBixby.1
        @Override // android.hardware.biometrics.BiometricPrompt.AuthenticationCallback
        public void onAuthenticationError(int i, CharSequence charSequence) {
            Log.d(BiometricPromptWrapperBixby.TAG, "onAuthenticationError() code : " + i + ", errString : " + ((Object) charSequence));
            super.onAuthenticationError(i, charSequence);
            if (i == 14 || i == 11) {
                Log.d(BiometricPromptWrapperBixby.TAG, "power off!");
                IStatusBarService asInterface = IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));
                if (asInterface != null) {
                    try {
                        asInterface.shutdownByBixby();
                    } catch (RemoteException e) {
                        Log.e(BiometricPromptWrapperBixby.TAG, "shutdown RemoteException ", e);
                    }
                }
            }
        }

        @Override // android.hardware.biometrics.BiometricPrompt.AuthenticationCallback
        public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult authenticationResult) {
            IStatusBarService asInterface = IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));
            Log.d(BiometricPromptWrapperBixby.TAG, "onAuthenticationSucceeded");
            if (asInterface != null) {
                try {
                    asInterface.shutdownByBixby();
                } catch (RemoteException e) {
                    Log.e(BiometricPromptWrapperBixby.TAG, "shutdown RemoteException ", e);
                }
            }
            super.onAuthenticationSucceeded(authenticationResult);
        }
    };

    public BiometricPromptWrapperBixby(Context context) {
        this.mContext = context;
        this.mBuilder = new BiometricPrompt.Builder(context);
    }

    public void buildAndRun(CancellationSignal cancellationSignal) {
        this.mBuilder.build().authenticate(cancellationSignal, new BiometricPromptWrapperBixby$$ExternalSyntheticLambda0(), this.mCallback);
    }

    public void initPrompt(String str, boolean z) {
        this.mBuilder.setTitle(str).setDeviceCredentialAllowed(z);
    }
}
