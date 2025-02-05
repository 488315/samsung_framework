package com.android.wm.shell.dagger;

import dagger.internal.Preconditions;
import java.util.Optional;
import javax.inject.Provider;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class WMShellBaseModule_ProvideSplitScreenFactory implements Provider {
    public final Provider splitScreenControllerProvider;

    public WMShellBaseModule_ProvideSplitScreenFactory(Provider provider) {
        this.splitScreenControllerProvider = provider;
    }

    public static Optional provideSplitScreen(Optional optional) {
        Optional map = optional.map(new WMShellBaseModule$$ExternalSyntheticLambda0(3));
        Preconditions.checkNotNullFromProvides(map);
        return map;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideSplitScreen((Optional) this.splitScreenControllerProvider.get());
    }
}
