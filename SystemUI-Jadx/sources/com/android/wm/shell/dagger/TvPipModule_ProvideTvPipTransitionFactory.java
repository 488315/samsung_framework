package com.android.wm.shell.dagger;

import android.content.Context;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.pip.PipAnimationController;
import com.android.wm.shell.pip.PipDisplayLayoutState;
import com.android.wm.shell.pip.PipSurfaceTransactionHelper;
import com.android.wm.shell.pip.PipTransitionState;
import com.android.wm.shell.pip.tv.TvPipBoundsAlgorithm;
import com.android.wm.shell.pip.tv.TvPipBoundsState;
import com.android.wm.shell.pip.tv.TvPipMenuController;
import com.android.wm.shell.pip.tv.TvPipTransition;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.transition.Transitions;
import java.util.Optional;
import javax.inject.Provider;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class TvPipModule_ProvideTvPipTransitionFactory implements Provider {
    public final Provider contextProvider;
    public final Provider pipAnimationControllerProvider;
    public final Provider pipDisplayLayoutStateProvider;
    public final Provider pipMenuControllerProvider;
    public final Provider pipSurfaceTransactionHelperProvider;
    public final Provider pipTransitionStateProvider;
    public final Provider shellInitProvider;
    public final Provider shellTaskOrganizerProvider;
    public final Provider transitionsProvider;
    public final Provider tvPipBoundsAlgorithmProvider;
    public final Provider tvPipBoundsStateProvider;

    public TvPipModule_ProvideTvPipTransitionFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11) {
        this.contextProvider = provider;
        this.shellInitProvider = provider2;
        this.shellTaskOrganizerProvider = provider3;
        this.transitionsProvider = provider4;
        this.tvPipBoundsStateProvider = provider5;
        this.pipDisplayLayoutStateProvider = provider6;
        this.pipTransitionStateProvider = provider7;
        this.pipMenuControllerProvider = provider8;
        this.tvPipBoundsAlgorithmProvider = provider9;
        this.pipAnimationControllerProvider = provider10;
        this.pipSurfaceTransactionHelperProvider = provider11;
    }

    public static TvPipTransition provideTvPipTransition(Context context, ShellInit shellInit, ShellTaskOrganizer shellTaskOrganizer, Transitions transitions, TvPipBoundsState tvPipBoundsState, PipDisplayLayoutState pipDisplayLayoutState, PipTransitionState pipTransitionState, TvPipMenuController tvPipMenuController, TvPipBoundsAlgorithm tvPipBoundsAlgorithm, PipAnimationController pipAnimationController, PipSurfaceTransactionHelper pipSurfaceTransactionHelper) {
        return new TvPipTransition(context, shellInit, shellTaskOrganizer, transitions, tvPipBoundsState, pipDisplayLayoutState, pipTransitionState, tvPipMenuController, tvPipBoundsAlgorithm, pipAnimationController, pipSurfaceTransactionHelper, Optional.empty());
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideTvPipTransition((Context) this.contextProvider.get(), (ShellInit) this.shellInitProvider.get(), (ShellTaskOrganizer) this.shellTaskOrganizerProvider.get(), (Transitions) this.transitionsProvider.get(), (TvPipBoundsState) this.tvPipBoundsStateProvider.get(), (PipDisplayLayoutState) this.pipDisplayLayoutStateProvider.get(), (PipTransitionState) this.pipTransitionStateProvider.get(), (TvPipMenuController) this.pipMenuControllerProvider.get(), (TvPipBoundsAlgorithm) this.tvPipBoundsAlgorithmProvider.get(), (PipAnimationController) this.pipAnimationControllerProvider.get(), (PipSurfaceTransactionHelper) this.pipSurfaceTransactionHelperProvider.get());
    }
}
