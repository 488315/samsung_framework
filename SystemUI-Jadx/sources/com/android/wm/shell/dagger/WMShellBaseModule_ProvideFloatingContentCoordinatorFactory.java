package com.android.wm.shell.dagger;

import com.android.wm.shell.common.FloatingContentCoordinator;
import javax.inject.Provider;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class WMShellBaseModule_ProvideFloatingContentCoordinatorFactory implements Provider {
    public static FloatingContentCoordinator provideFloatingContentCoordinator() {
        return new FloatingContentCoordinator();
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new FloatingContentCoordinator();
    }
}