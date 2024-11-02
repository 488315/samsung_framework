package com.android.systemui.qs.tiles.dialog;

import android.content.Context;
import android.os.Handler;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import java.util.concurrent.Executor;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class InternetDialogFactory {
    public static InternetDialog internetDialog;
    public final Context context;
    public final DialogLaunchAnimator dialogLaunchAnimator;
    public final Executor executor;
    public final Handler handler;
    public final InternetDialogController internetDialogController;
    public final KeyguardStateController keyguardStateController;
    public final UiEventLogger uiEventLogger;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public InternetDialogFactory(Handler handler, Executor executor, InternetDialogController internetDialogController, Context context, UiEventLogger uiEventLogger, DialogLaunchAnimator dialogLaunchAnimator, KeyguardStateController keyguardStateController) {
        this.handler = handler;
        this.executor = executor;
        this.internetDialogController = internetDialogController;
        this.context = context;
        this.uiEventLogger = uiEventLogger;
        this.dialogLaunchAnimator = dialogLaunchAnimator;
        this.keyguardStateController = keyguardStateController;
    }
}
