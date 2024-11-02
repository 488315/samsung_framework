package com.android.systemui.controls.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.service.controls.Control;
import android.service.controls.CustomControl;
import android.service.controls.actions.BooleanAction;
import android.service.controls.actions.CommandAction;
import android.util.Log;
import android.widget.ProgressBar;
import com.android.keyguard.KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastSender;
import com.android.systemui.controls.ControlsMetricsLogger;
import com.android.systemui.controls.ControlsMetricsLoggerImpl;
import com.android.systemui.controls.settings.ControlsSettingsDialogManager;
import com.android.systemui.controls.settings.ControlsSettingsDialogManagerImpl;
import com.android.systemui.controls.settings.ControlsSettingsRepository;
import com.android.systemui.controls.settings.ControlsSettingsRepositoryImpl;
import com.android.systemui.controls.ui.ControlActionCoordinatorImpl;
import com.android.systemui.controls.ui.util.AUIFacade;
import com.android.systemui.controls.ui.util.AUIFacadeImpl;
import com.android.systemui.controls.ui.util.DesktopManagerWrapper;
import com.android.systemui.controls.ui.util.DesktopManagerWrapperImpl;
import com.android.systemui.controls.ui.util.SALogger;
import com.android.systemui.controls.ui.view.ActionIconView;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.settings.UserFileManagerImpl;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.DesktopManagerImpl;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.ExecutorImpl;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.taskview.TaskView;
import com.android.wm.shell.taskview.TaskViewFactoryController;
import com.android.wm.shell.taskview.TaskViewFactoryController$TaskViewFactoryImpl$$ExternalSyntheticLambda0;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$BooleanRef;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class ControlActionCoordinatorImpl implements ControlActionCoordinator, CustomControlActionCoordinator {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Set actionsInProgress = new LinkedHashSet();
    public Context activityContext;
    public final ActivityStarter activityStarter;
    public final AUIFacade auiFacade;
    public final DelayableExecutor bgExecutor;
    public final BroadcastSender broadcastSender;
    public final Context context;
    public final ControlsMetricsLogger controlsMetricsLogger;
    public final ControlsSettingsDialogManager controlsSettingsDialogManager;
    public final ControlsSettingsRepository controlsSettingsRepository;
    public final DesktopManagerWrapper desktopManagerWrapper;
    public Dialog dialog;
    public final FeatureFlags featureFlags;
    public final KeyguardStateController keyguardStateController;
    public Action pendingAction;
    public final SALogger saLogger;
    public final Optional taskViewFactory;
    public final DelayableExecutor uiExecutor;
    public final VibratorHelper vibrator;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class Action {
        public final boolean authIsRequired;
        public final boolean blockable;
        public final String controlId;
        public final Function0 f;

        public Action(String str, Function0 function0, boolean z, boolean z2) {
            this.controlId = str;
            this.f = function0;
            this.blockable = z;
            this.authIsRequired = z2;
        }

        public final void invoke() {
            boolean z;
            if (this.blockable) {
                final ControlActionCoordinatorImpl controlActionCoordinatorImpl = ControlActionCoordinatorImpl.this;
                Set set = controlActionCoordinatorImpl.actionsInProgress;
                final String str = this.controlId;
                if (set.add(str)) {
                    controlActionCoordinatorImpl.uiExecutor.executeDelayed(3000L, new Runnable() { // from class: com.android.systemui.controls.ui.ControlActionCoordinatorImpl$shouldRunAction$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            ControlActionCoordinatorImpl.this.actionsInProgress.remove(str);
                        }
                    });
                    z = true;
                } else {
                    z = false;
                }
                if (!z) {
                    return;
                }
            }
            this.f.invoke();
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
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

    public ControlActionCoordinatorImpl(Context context, DelayableExecutor delayableExecutor, DelayableExecutor delayableExecutor2, ActivityStarter activityStarter, BroadcastSender broadcastSender, KeyguardStateController keyguardStateController, Optional<TaskViewFactoryController.TaskViewFactoryImpl> optional, ControlsMetricsLogger controlsMetricsLogger, VibratorHelper vibratorHelper, ControlsSettingsRepository controlsSettingsRepository, ControlsSettingsDialogManager controlsSettingsDialogManager, FeatureFlags featureFlags, AUIFacade aUIFacade, SALogger sALogger, DesktopManagerWrapper desktopManagerWrapper) {
        this.context = context;
        this.bgExecutor = delayableExecutor;
        this.uiExecutor = delayableExecutor2;
        this.activityStarter = activityStarter;
        this.broadcastSender = broadcastSender;
        this.keyguardStateController = keyguardStateController;
        this.taskViewFactory = optional;
        this.controlsMetricsLogger = controlsMetricsLogger;
        this.vibrator = vibratorHelper;
        this.controlsSettingsRepository = controlsSettingsRepository;
        this.controlsSettingsDialogManager = controlsSettingsDialogManager;
        this.featureFlags = featureFlags;
        this.auiFacade = aUIFacade;
        this.saLogger = sALogger;
        this.desktopManagerWrapper = desktopManagerWrapper;
    }

    public final void bouncerOrRun(final Action action) {
        boolean z;
        if (!action.authIsRequired && ((Boolean) ((ControlsSettingsRepositoryImpl) this.controlsSettingsRepository).allowActionOnTrivialControlsInLockscreen.getValue()).booleanValue()) {
            z = false;
        } else {
            z = true;
        }
        if (((KeyguardStateControllerImpl) this.keyguardStateController).mShowing && z) {
            if (!BasicRune.CONTROLS_SAMSUNG_STYLE && isLocked()) {
                this.broadcastSender.closeSystemDialogs();
                this.pendingAction = action;
            }
            this.activityStarter.dismissKeyguardThenExecute(new ActivityStarter.OnDismissAction() { // from class: com.android.systemui.controls.ui.ControlActionCoordinatorImpl$bouncerOrRun$1
                @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
                public final boolean onDismiss() {
                    Log.d("ControlsUiController", "Device unlocked, invoking controls action");
                    ControlActionCoordinatorImpl.Action.this.invoke();
                    return true;
                }
            }, new Runnable() { // from class: com.android.systemui.controls.ui.ControlActionCoordinatorImpl$bouncerOrRun$2
                @Override // java.lang.Runnable
                public final void run() {
                    ControlActionCoordinatorImpl.this.pendingAction = null;
                }
            }, true);
            return;
        }
        showSettingsDialogIfNeeded(action);
        action.invoke();
    }

    public final Action createAction(String str, Function0 function0, boolean z, boolean z2) {
        return new Action(str, function0, z, z2);
    }

    public final void customTouch(final ControlViewHolder controlViewHolder, String str, Control control) {
        boolean z;
        boolean z2;
        CustomControl customControl;
        boolean z3;
        Log.d("ControlsUiController", "customTouch: [" + str + "]");
        ((ControlsMetricsLoggerImpl) this.controlsMetricsLogger).touch(controlViewHolder, isLocked());
        boolean z4 = true;
        if (BasicRune.CONTROLS_SAMSUNG_ANALYTICS) {
            SALogger.Event.TapCardLayout tapCardLayout = new SALogger.Event.TapCardLayout(controlViewHolder);
            SALogger sALogger = this.saLogger;
            sALogger.sendEvent(tapCardLayout);
            if (control.getCustomControl().getLayoutType() == 1) {
                z3 = true;
            } else {
                z3 = false;
            }
            if (!z3) {
                control = null;
            }
            if (control != null) {
                sALogger.sendEvent(new SALogger.Event.TapSmallTypeCard(control.getTitle().toString(), String.valueOf(control.getDeviceType())));
            }
        }
        Control control2 = controlViewHolder.getCws().control;
        if (control2 != null) {
            z = control2.isAuthRequired();
        } else {
            z = true;
        }
        if (BasicRune.CONTROLS_ALLOW_BASIC_ACTION_WHEN_LOCKED) {
            if (z) {
                Control control3 = controlViewHolder.getCws().control;
                if (control3 != null && (customControl = control3.getCustomControl()) != null) {
                    z2 = customControl.getAllowBasicActionWhenLocked();
                } else {
                    z2 = false;
                }
                if (!z2) {
                    z = true;
                }
            }
            z = false;
        }
        final Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
        if (BasicRune.CONTROLS_USE_FULL_SCREEN_DETAIL_DIALOG) {
            boolean needToShowFullScreenDetailDialog = needToShowFullScreenDetailDialog(controlViewHolder.getCws().control);
            ref$BooleanRef.element = needToShowFullScreenDetailDialog;
            if (!z && !needToShowFullScreenDetailDialog) {
                z4 = false;
            }
            z = z4;
        }
        bouncerOrRun(createAction(controlViewHolder.getCws().ci.controlId, new Function0() { // from class: com.android.systemui.controls.ui.ControlActionCoordinatorImpl$customTouch$3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Control control4 = ControlViewHolder.this.getCws().control;
                if (control4 != null) {
                    ControlActionCoordinatorImpl controlActionCoordinatorImpl = this;
                    ControlViewHolder controlViewHolder2 = ControlViewHolder.this;
                    Ref$BooleanRef ref$BooleanRef2 = ref$BooleanRef;
                    PendingIntent appIntent = control4.getAppIntent();
                    boolean z5 = ref$BooleanRef2.element;
                    int i = ControlActionCoordinatorImpl.$r8$clinit;
                    controlActionCoordinatorImpl.showDetail(controlViewHolder2, appIntent, z5);
                }
                return Unit.INSTANCE;
            }
        }, false, z));
    }

    public final boolean isLocked() {
        return !this.keyguardStateController.isUnlocked();
    }

    public final void longPress(final ControlViewHolder controlViewHolder) {
        boolean z;
        ((ControlsMetricsLoggerImpl) this.controlsMetricsLogger).longPress(controlViewHolder, isLocked());
        String str = controlViewHolder.getCws().ci.controlId;
        Function0 function0 = new Function0() { // from class: com.android.systemui.controls.ui.ControlActionCoordinatorImpl$longPress$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Control control = ControlViewHolder.this.getCws().control;
                if (control != null) {
                    ControlViewHolder controlViewHolder2 = ControlViewHolder.this;
                    ControlActionCoordinatorImpl controlActionCoordinatorImpl = this;
                    controlViewHolder2.layout.performHapticFeedback(0);
                    PendingIntent appIntent = control.getAppIntent();
                    int i = ControlActionCoordinatorImpl.$r8$clinit;
                    controlActionCoordinatorImpl.showDetail(controlViewHolder2, appIntent, false);
                }
                return Unit.INSTANCE;
            }
        };
        Control control = controlViewHolder.getCws().control;
        if (control != null) {
            z = control.isAuthRequired();
        } else {
            z = true;
        }
        bouncerOrRun(createAction(str, function0, false, z));
    }

    public final boolean needToShowFullScreenDetailDialog(Control control) {
        if (control == null) {
            return false;
        }
        if (!control.getCustomControl().getUseFullScreenDetailDialog() && (!BasicRune.CONTROLS_DEX_SUPPORT || !((DesktopManagerImpl) ((DesktopManagerWrapperImpl) this.desktopManagerWrapper).desktopManager).isStandalone())) {
            return false;
        }
        return true;
    }

    public final void showDetail(final ControlViewHolder controlViewHolder, final PendingIntent pendingIntent, final boolean z) {
        ((ExecutorImpl) this.bgExecutor).execute(new Runnable() { // from class: com.android.systemui.controls.ui.ControlActionCoordinatorImpl$showDetail$1
            @Override // java.lang.Runnable
            public final void run() {
                final List<ResolveInfo> queryIntentActivities = ControlActionCoordinatorImpl.this.context.getPackageManager().queryIntentActivities(pendingIntent.getIntent(), 65536);
                final ControlActionCoordinatorImpl controlActionCoordinatorImpl = ControlActionCoordinatorImpl.this;
                DelayableExecutor delayableExecutor = controlActionCoordinatorImpl.uiExecutor;
                final boolean z2 = z;
                final PendingIntent pendingIntent2 = pendingIntent;
                final ControlViewHolder controlViewHolder2 = controlViewHolder;
                ((ExecutorImpl) delayableExecutor).execute(new Runnable() { // from class: com.android.systemui.controls.ui.ControlActionCoordinatorImpl$showDetail$1.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        if ((!queryIntentActivities.isEmpty()) && controlActionCoordinatorImpl.taskViewFactory.isPresent()) {
                            if (BasicRune.CONTROLS_USE_FULL_SCREEN_DETAIL_DIALOG && z2) {
                                PendingIntent pendingIntent3 = pendingIntent2;
                                Intent intent = pendingIntent3.getIntent();
                                if ((intent.getFlags() & QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE) == 0) {
                                    Log.w("ControlsUiController", "makeValid: " + intent);
                                    intent.addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
                                }
                                pendingIntent3.send();
                                return;
                            }
                            if (BasicRune.CONTROLS_AOSP_BUGFIX) {
                                ControlActionCoordinatorImpl controlActionCoordinatorImpl2 = controlActionCoordinatorImpl;
                                if (controlActionCoordinatorImpl2.dialog == null) {
                                    TaskViewFactoryController.TaskViewFactoryImpl taskViewFactoryImpl = (TaskViewFactoryController.TaskViewFactoryImpl) controlActionCoordinatorImpl2.taskViewFactory.get();
                                    final ControlActionCoordinatorImpl controlActionCoordinatorImpl3 = controlActionCoordinatorImpl;
                                    Context context = controlActionCoordinatorImpl3.context;
                                    final PendingIntent pendingIntent4 = pendingIntent2;
                                    final ControlViewHolder controlViewHolder3 = controlViewHolder2;
                                    ((HandlerExecutor) TaskViewFactoryController.this.mShellExecutor).execute(new TaskViewFactoryController$TaskViewFactoryImpl$$ExternalSyntheticLambda0(taskViewFactoryImpl, context, controlActionCoordinatorImpl3.uiExecutor, new Consumer() { // from class: com.android.systemui.controls.ui.ControlActionCoordinatorImpl.showDetail.1.1.1
                                        @Override // java.util.function.Consumer
                                        public final void accept(Object obj) {
                                            TaskView taskView = (TaskView) obj;
                                            ControlActionCoordinatorImpl controlActionCoordinatorImpl4 = ControlActionCoordinatorImpl.this;
                                            Context context2 = ControlActionCoordinatorImpl.this.activityContext;
                                            Intrinsics.checkNotNull(context2);
                                            ControlActionCoordinatorImpl controlActionCoordinatorImpl5 = ControlActionCoordinatorImpl.this;
                                            DetailDialog detailDialog = new DetailDialog(context2, controlActionCoordinatorImpl5.broadcastSender, taskView, pendingIntent4, controlViewHolder3, controlActionCoordinatorImpl5.keyguardStateController, controlActionCoordinatorImpl5.activityStarter, controlActionCoordinatorImpl5.saLogger);
                                            final ControlActionCoordinatorImpl controlActionCoordinatorImpl6 = ControlActionCoordinatorImpl.this;
                                            if (BasicRune.CONTROLS_SAMSUNG_STYLE) {
                                                detailDialog.setTitle("DetailDialog");
                                            }
                                            detailDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.controls.ui.ControlActionCoordinatorImpl$showDetail$1$1$1$1$1
                                                @Override // android.content.DialogInterface.OnDismissListener
                                                public final void onDismiss(DialogInterface dialogInterface) {
                                                    ControlActionCoordinatorImpl.this.dialog = null;
                                                }
                                            });
                                            detailDialog.show();
                                            controlActionCoordinatorImpl4.dialog = detailDialog;
                                        }
                                    }));
                                    Unit unit = Unit.INSTANCE;
                                    return;
                                }
                                return;
                            }
                            TaskViewFactoryController.TaskViewFactoryImpl taskViewFactoryImpl2 = (TaskViewFactoryController.TaskViewFactoryImpl) controlActionCoordinatorImpl.taskViewFactory.get();
                            final ControlActionCoordinatorImpl controlActionCoordinatorImpl4 = controlActionCoordinatorImpl;
                            Context context2 = controlActionCoordinatorImpl4.context;
                            final PendingIntent pendingIntent5 = pendingIntent2;
                            final ControlViewHolder controlViewHolder4 = controlViewHolder2;
                            ((HandlerExecutor) TaskViewFactoryController.this.mShellExecutor).execute(new TaskViewFactoryController$TaskViewFactoryImpl$$ExternalSyntheticLambda0(taskViewFactoryImpl2, context2, controlActionCoordinatorImpl4.uiExecutor, new Consumer() { // from class: com.android.systemui.controls.ui.ControlActionCoordinatorImpl.showDetail.1.1.2
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    TaskView taskView = (TaskView) obj;
                                    ControlActionCoordinatorImpl controlActionCoordinatorImpl5 = ControlActionCoordinatorImpl.this;
                                    Context context3 = ControlActionCoordinatorImpl.this.activityContext;
                                    Intrinsics.checkNotNull(context3);
                                    ControlActionCoordinatorImpl controlActionCoordinatorImpl6 = ControlActionCoordinatorImpl.this;
                                    DetailDialog detailDialog = new DetailDialog(context3, controlActionCoordinatorImpl6.broadcastSender, taskView, pendingIntent5, controlViewHolder4, controlActionCoordinatorImpl6.keyguardStateController, controlActionCoordinatorImpl6.activityStarter, controlActionCoordinatorImpl6.saLogger);
                                    final ControlActionCoordinatorImpl controlActionCoordinatorImpl7 = ControlActionCoordinatorImpl.this;
                                    detailDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.controls.ui.ControlActionCoordinatorImpl$showDetail$1$1$2$1$1
                                        @Override // android.content.DialogInterface.OnDismissListener
                                        public final void onDismiss(DialogInterface dialogInterface) {
                                            ControlActionCoordinatorImpl.this.dialog = null;
                                        }
                                    });
                                    detailDialog.show();
                                    controlActionCoordinatorImpl5.dialog = detailDialog;
                                }
                            }));
                            return;
                        }
                        controlViewHolder2.setErrorStatus();
                    }
                });
            }
        });
    }

    public final void showSettingsDialogIfNeeded(Action action) {
        if (action.authIsRequired) {
            return;
        }
        if (!((FeatureFlagsRelease) this.featureFlags).isEnabled(Flags.USE_APP_PANELS)) {
            boolean z = BasicRune.CONTROLS_SAMSUNG_STYLE;
            ControlsSettingsDialogManager controlsSettingsDialogManager = this.controlsSettingsDialogManager;
            if (z) {
                Context context = this.activityContext;
                Intrinsics.checkNotNull(context);
                ControlActionCoordinatorImpl$showSettingsDialogIfNeeded$1 controlActionCoordinatorImpl$showSettingsDialogIfNeeded$1 = new Function0() { // from class: com.android.systemui.controls.ui.ControlActionCoordinatorImpl$showSettingsDialogIfNeeded$1
                    @Override // kotlin.jvm.functions.Function0
                    public final /* bridge */ /* synthetic */ Object invoke() {
                        return Unit.INSTANCE;
                    }
                };
                final ControlsSettingsDialogManagerImpl controlsSettingsDialogManagerImpl = (ControlsSettingsDialogManagerImpl) controlsSettingsDialogManager;
                controlsSettingsDialogManagerImpl.closeDialog();
                SharedPreferences sharedPreferences = ((UserFileManagerImpl) controlsSettingsDialogManagerImpl.userFileManager).getSharedPreferences(((UserTrackerImpl) controlsSettingsDialogManagerImpl.userTracker).getUserId(), "controls_prefs");
                int i = sharedPreferences.getInt("show_settings_attempts", 0);
                if (i < 2 && (!controlsSettingsDialogManagerImpl.getShowDeviceControlsInLockscreen() || !((Boolean) ((ControlsSettingsRepositoryImpl) controlsSettingsDialogManagerImpl.controlsSettingsRepository).allowActionOnTrivialControlsInLockscreen.getValue()).booleanValue())) {
                    ControlsSettingsDialogManagerImpl.DialogListener dialogListener = new ControlsSettingsDialogManagerImpl.DialogListener(sharedPreferences, i, controlActionCoordinatorImpl$showSettingsDialogIfNeeded$1);
                    AlertDialog alertDialog = (AlertDialog) controlsSettingsDialogManagerImpl.dialogProvider.invoke(context, 2132018528);
                    alertDialog.setOnCancelListener(dialogListener);
                    alertDialog.setButton(-3, alertDialog.getContext().getText(R.string.controls_settings_custom_dialog_neutral_button), dialogListener);
                    alertDialog.setButton(-1, alertDialog.getContext().getText(R.string.controls_settings_custom_dialog_positive_button), dialogListener);
                    if (BasicRune.CONTROLS_SAMSUNG_STYLE_TABLET) {
                        alertDialog.setTitle(R.string.controls_settings_custom_trivial_controls_dialog_title_tablet);
                        alertDialog.setMessage(alertDialog.getContext().getText(R.string.controls_settings_custom_trivial_controls_dialog_message_tablet));
                    } else {
                        alertDialog.setTitle(R.string.controls_settings_custom_trivial_controls_dialog_title);
                        alertDialog.setMessage(alertDialog.getContext().getText(R.string.controls_settings_custom_trivial_controls_dialog_message));
                    }
                    SystemUIDialog.registerDismissListener(alertDialog, new Runnable() { // from class: com.android.systemui.controls.settings.ControlsSettingsDialogManagerImpl$maybeShowCustomDialog$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            ControlsSettingsDialogManagerImpl.this.customDialog = null;
                        }
                    });
                    SystemUIDialog.setDialogSize(alertDialog);
                    SystemUIDialog.setShowForAllUsers(alertDialog);
                    controlsSettingsDialogManagerImpl.customDialog = alertDialog;
                    alertDialog.show();
                    return;
                }
                controlActionCoordinatorImpl$showSettingsDialogIfNeeded$1.getClass();
                Unit unit = Unit.INSTANCE;
                return;
            }
            Context context2 = this.activityContext;
            Intrinsics.checkNotNull(context2);
            ((ControlsSettingsDialogManagerImpl) controlsSettingsDialogManager).maybeShowDialog(context2, new Function0() { // from class: com.android.systemui.controls.ui.ControlActionCoordinatorImpl$showSettingsDialogIfNeeded$2
                @Override // kotlin.jvm.functions.Function0
                public final /* bridge */ /* synthetic */ Object invoke() {
                    return Unit.INSTANCE;
                }
            });
        }
    }

    public final void toggle(final ControlViewHolder controlViewHolder, final String str, final boolean z) {
        boolean z2;
        ((ControlsMetricsLoggerImpl) this.controlsMetricsLogger).touch(controlViewHolder, isLocked());
        String str2 = controlViewHolder.getCws().ci.controlId;
        Function0 function0 = new Function0() { // from class: com.android.systemui.controls.ui.ControlActionCoordinatorImpl$toggle$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                ControlViewHolder.this.layout.performHapticFeedback(6);
                ControlViewHolder.this.action(new BooleanAction(str, !z));
                return Unit.INSTANCE;
            }
        };
        Control control = controlViewHolder.getCws().control;
        if (control != null) {
            z2 = control.isAuthRequired();
        } else {
            z2 = true;
        }
        bouncerOrRun(createAction(str2, function0, true, z2));
    }

    public final void toggleMainAction(final ControlViewHolder controlViewHolder, final String str, final boolean z) {
        boolean z2;
        boolean z3;
        CustomControl customControl;
        Log.d("ControlsUiController", "toggleMainAction: [" + str + "]: " + z);
        ((ControlsMetricsLoggerImpl) this.controlsMetricsLogger).touch(controlViewHolder, isLocked());
        if (BasicRune.CONTROLS_SAMSUNG_ANALYTICS) {
            this.saLogger.sendEvent(new SALogger.Event.TapMainActionButton(controlViewHolder));
        }
        Control control = controlViewHolder.getCws().control;
        if (control != null) {
            z2 = control.isAuthRequired();
        } else {
            z2 = true;
        }
        if (BasicRune.CONTROLS_ALLOW_BASIC_ACTION_WHEN_LOCKED) {
            if (z2) {
                Control control2 = controlViewHolder.getCws().control;
                if (control2 != null && (customControl = control2.getCustomControl()) != null) {
                    z3 = customControl.getAllowBasicActionWhenLocked();
                } else {
                    z3 = false;
                }
                if (!z3) {
                    z2 = true;
                }
            }
            z2 = false;
        }
        bouncerOrRun(createAction(controlViewHolder.getCws().ci.controlId, new Function0() { // from class: com.android.systemui.controls.ui.ControlActionCoordinatorImpl$toggleMainAction$action$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                CustomControl customControl2;
                ProgressBar progressBar;
                ActionIconView actionIconView = ControlViewHolder.this.getCustomControlViewHolder().actionIcon;
                if (actionIconView != null) {
                    ControlActionCoordinatorImpl controlActionCoordinatorImpl = this;
                    ControlViewHolder controlViewHolder2 = ControlViewHolder.this;
                    boolean z4 = z;
                    String str2 = str;
                    int i = 0;
                    if (BasicRune.CONTROLS_CUSTOM_MAIN_ACTION_ICON_PROGRESS && (progressBar = actionIconView.actionIconProgress) != null) {
                        progressBar.setVisibility(0);
                    }
                    if (BasicRune.CONTROLS_AUI) {
                        AUIFacade aUIFacade = controlActionCoordinatorImpl.auiFacade;
                        Control control3 = controlViewHolder2.getCws().control;
                        if (control3 != null && (customControl2 = control3.getCustomControl()) != null) {
                            i = customControl2.getCustomSound();
                        }
                        ((AUIFacadeImpl) aUIFacade).playClick(i, controlViewHolder2.getCws().ci.deviceType, actionIconView.actionIcon, !z4);
                    }
                    controlViewHolder2.action(new BooleanAction(str2, !z4));
                }
                return Unit.INSTANCE;
            }
        }, true, z2));
    }

    public final void touch(final ControlViewHolder controlViewHolder, final String str, final Control control) {
        boolean z;
        if (BasicRune.CONTROLS_SAMSUNG_STYLE) {
            KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0.m("touch: [", str, "]", "ControlsUiController");
        }
        ((ControlsMetricsLoggerImpl) this.controlsMetricsLogger).touch(controlViewHolder, isLocked());
        boolean usePanel = controlViewHolder.usePanel();
        String str2 = controlViewHolder.getCws().ci.controlId;
        Function0 function0 = new Function0() { // from class: com.android.systemui.controls.ui.ControlActionCoordinatorImpl$touch$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                ControlViewHolder.this.layout.performHapticFeedback(6);
                if (ControlViewHolder.this.usePanel()) {
                    ControlActionCoordinatorImpl controlActionCoordinatorImpl = this;
                    ControlViewHolder controlViewHolder2 = ControlViewHolder.this;
                    PendingIntent appIntent = control.getAppIntent();
                    int i = ControlActionCoordinatorImpl.$r8$clinit;
                    controlActionCoordinatorImpl.showDetail(controlViewHolder2, appIntent, false);
                } else {
                    ControlViewHolder.this.action(new CommandAction(str));
                }
                return Unit.INSTANCE;
            }
        };
        Control control2 = controlViewHolder.getCws().control;
        if (control2 != null) {
            z = control2.isAuthRequired();
        } else {
            z = true;
        }
        bouncerOrRun(createAction(str2, function0, usePanel, z));
    }

    public final void touchMainAction(final ControlViewHolder controlViewHolder, final String str, final Control control) {
        boolean z;
        boolean z2;
        CustomControl customControl;
        Log.d("ControlsUiController", "touchMainAction: [" + str + "]");
        ((ControlsMetricsLoggerImpl) this.controlsMetricsLogger).touch(controlViewHolder, isLocked());
        if (BasicRune.CONTROLS_SAMSUNG_ANALYTICS) {
            this.saLogger.sendEvent(new SALogger.Event.TapMainActionButton(controlViewHolder));
        }
        Control control2 = controlViewHolder.getCws().control;
        boolean z3 = true;
        if (control2 != null) {
            z = control2.isAuthRequired();
        } else {
            z = true;
        }
        if (BasicRune.CONTROLS_ALLOW_BASIC_ACTION_WHEN_LOCKED) {
            if (z) {
                Control control3 = controlViewHolder.getCws().control;
                if (control3 != null && (customControl = control3.getCustomControl()) != null) {
                    z2 = customControl.getAllowBasicActionWhenLocked();
                } else {
                    z2 = false;
                }
                if (!z2) {
                    z = true;
                }
            }
            z = false;
        }
        final boolean usePanel = controlViewHolder.usePanel();
        final Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
        if (BasicRune.CONTROLS_USE_FULL_SCREEN_DETAIL_DIALOG) {
            boolean needToShowFullScreenDetailDialog = needToShowFullScreenDetailDialog(controlViewHolder.getCws().control);
            ref$BooleanRef.element = needToShowFullScreenDetailDialog;
            if (!z && (!usePanel || !needToShowFullScreenDetailDialog)) {
                z3 = false;
            }
            z = z3;
        }
        bouncerOrRun(createAction(controlViewHolder.getCws().ci.controlId, new Function0() { // from class: com.android.systemui.controls.ui.ControlActionCoordinatorImpl$touchMainAction$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                CustomControl customControl2;
                ProgressBar progressBar;
                ActionIconView actionIconView = ControlViewHolder.this.getCustomControlViewHolder().actionIcon;
                if (actionIconView != null) {
                    ControlActionCoordinatorImpl controlActionCoordinatorImpl = this;
                    ControlViewHolder controlViewHolder2 = ControlViewHolder.this;
                    boolean z4 = usePanel;
                    Control control4 = control;
                    Ref$BooleanRef ref$BooleanRef2 = ref$BooleanRef;
                    String str2 = str;
                    int i = 0;
                    if (BasicRune.CONTROLS_CUSTOM_MAIN_ACTION_ICON_PROGRESS && (progressBar = actionIconView.actionIconProgress) != null) {
                        progressBar.setVisibility(0);
                    }
                    if (BasicRune.CONTROLS_AUI) {
                        AUIFacade aUIFacade = controlActionCoordinatorImpl.auiFacade;
                        Control control5 = controlViewHolder2.getCws().control;
                        if (control5 != null && (customControl2 = control5.getCustomControl()) != null) {
                            i = customControl2.getCustomSound();
                        }
                        ((AUIFacadeImpl) aUIFacade).playClick(i, controlViewHolder2.getCws().ci.deviceType, actionIconView.actionIcon, true);
                    }
                    if (z4) {
                        PendingIntent appIntent = control4.getAppIntent();
                        boolean z5 = ref$BooleanRef2.element;
                        int i2 = ControlActionCoordinatorImpl.$r8$clinit;
                        controlActionCoordinatorImpl.showDetail(controlViewHolder2, appIntent, z5);
                    } else {
                        controlViewHolder2.action(new CommandAction(str2));
                    }
                }
                return Unit.INSTANCE;
            }
        }, controlViewHolder.usePanel(), z));
    }
}
