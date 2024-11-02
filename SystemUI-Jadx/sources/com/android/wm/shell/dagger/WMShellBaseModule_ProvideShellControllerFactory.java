package com.android.wm.shell.dagger;

import android.content.Context;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.sysui.ShellCommandHandler;
import com.android.wm.shell.sysui.ShellController;
import com.android.wm.shell.sysui.ShellInit;
import javax.inject.Provider;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class WMShellBaseModule_ProvideShellControllerFactory implements Provider {
    public final Provider contextProvider;
    public final Provider mainExecutorProvider;
    public final Provider shellCommandHandlerProvider;
    public final Provider shellInitProvider;

    public WMShellBaseModule_ProvideShellControllerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        this.contextProvider = provider;
        this.shellInitProvider = provider2;
        this.shellCommandHandlerProvider = provider3;
        this.mainExecutorProvider = provider4;
    }

    public static ShellController provideShellController(Context context, ShellInit shellInit, ShellCommandHandler shellCommandHandler, ShellExecutor shellExecutor) {
        return new ShellController(context, shellInit, shellCommandHandler, shellExecutor);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new ShellController((Context) this.contextProvider.get(), (ShellInit) this.shellInitProvider.get(), (ShellCommandHandler) this.shellCommandHandlerProvider.get(), (ShellExecutor) this.mainExecutorProvider.get());
    }
}
