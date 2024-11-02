package com.android.wm.shell.dagger;

import android.content.Context;
import android.os.Handler;
import android.view.IWindowManager;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.splitscreen.EnterSplitGestureHandler;
import com.android.wm.shell.sysui.ShellInit;
import dagger.internal.Preconditions;
import java.util.Optional;
import javax.inject.Provider;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class WMShellBaseModule_ProvideEnterSplitGestureHandlerFactory implements Provider {
    public final Provider contextProvider;
    public final Provider displayControllerProvider;
    public final Provider mainExecutorProvider;
    public final Provider mainHandlerProvider;
    public final Provider shellInitProvider;
    public final Provider splitScreenControllerProvider;
    public final Provider wmServiceProvider;

    public WMShellBaseModule_ProvideEnterSplitGestureHandlerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7) {
        this.contextProvider = provider;
        this.shellInitProvider = provider2;
        this.mainHandlerProvider = provider3;
        this.displayControllerProvider = provider4;
        this.mainExecutorProvider = provider5;
        this.wmServiceProvider = provider6;
        this.splitScreenControllerProvider = provider7;
    }

    public static Optional provideEnterSplitGestureHandler(Context context, ShellInit shellInit, Handler handler, DisplayController displayController, ShellExecutor shellExecutor, IWindowManager iWindowManager, Optional optional) {
        Optional of = Optional.of(new EnterSplitGestureHandler(context, shellInit, handler, displayController, shellExecutor, iWindowManager, optional));
        Preconditions.checkNotNullFromProvides(of);
        return of;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideEnterSplitGestureHandler((Context) this.contextProvider.get(), (ShellInit) this.shellInitProvider.get(), (Handler) this.mainHandlerProvider.get(), (DisplayController) this.displayControllerProvider.get(), (ShellExecutor) this.mainExecutorProvider.get(), (IWindowManager) this.wmServiceProvider.get(), (Optional) this.splitScreenControllerProvider.get());
    }
}
