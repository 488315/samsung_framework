package com.android.wm.shell.dagger;

import android.content.Context;
import com.android.wm.shell.pip.phone.PhonePipKeepClearAlgorithm;
import javax.inject.Provider;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class WMShellModule_ProvidePhonePipKeepClearAlgorithmFactory implements Provider {
    public final Provider contextProvider;

    public WMShellModule_ProvidePhonePipKeepClearAlgorithmFactory(Provider provider) {
        this.contextProvider = provider;
    }

    public static PhonePipKeepClearAlgorithm providePhonePipKeepClearAlgorithm(Context context) {
        return new PhonePipKeepClearAlgorithm(context);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new PhonePipKeepClearAlgorithm((Context) this.contextProvider.get());
    }
}
