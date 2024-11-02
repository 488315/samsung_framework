package com.android.systemui.biometrics;

import android.content.Context;
import javax.inject.Provider;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class BiometricNotificationBroadcastReceiver_Factory implements Provider {
    public final Provider contextProvider;
    public final Provider notificationDialogFactoryProvider;

    public BiometricNotificationBroadcastReceiver_Factory(Provider provider, Provider provider2) {
        this.contextProvider = provider;
        this.notificationDialogFactoryProvider = provider2;
    }

    public static BiometricNotificationBroadcastReceiver newInstance(Context context, BiometricNotificationDialogFactory biometricNotificationDialogFactory) {
        return new BiometricNotificationBroadcastReceiver(context, biometricNotificationDialogFactory);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new BiometricNotificationBroadcastReceiver((Context) this.contextProvider.get(), (BiometricNotificationDialogFactory) this.notificationDialogFactoryProvider.get());
    }
}
