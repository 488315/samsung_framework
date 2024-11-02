package com.android.systemui.qs.tiles;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.R;
import com.android.systemui.animation.DialogCuj;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.pipeline.domain.interactor.PanelInteractor;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.qs.tileimpl.SQSTileImpl;
import com.android.systemui.screenrecord.RecordingController;
import com.android.systemui.screenrecord.ScreenRecordDialog;
import com.android.systemui.statusbar.phone.KeyguardDismissUtil;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import java.util.Iterator;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class ScreenRecordTile extends SQSTileImpl implements RecordingController.RecordingStateChangeCallback {
    public final RecordingController mController;
    public final DialogLaunchAnimator mDialogLaunchAnimator;
    public final FeatureFlags mFlags;
    public final KeyguardDismissUtil mKeyguardDismissUtil;
    public final KeyguardStateController mKeyguardStateController;
    public long mMillisUntilFinished;
    public final PanelInteractor mPanelInteractor;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public final class Callback implements RecordingController.RecordingStateChangeCallback {
        public /* synthetic */ Callback(ScreenRecordTile screenRecordTile, int i) {
            this();
        }

        @Override // com.android.systemui.screenrecord.RecordingController.RecordingStateChangeCallback
        public final void onCountdown(long j) {
            ScreenRecordTile screenRecordTile = ScreenRecordTile.this;
            screenRecordTile.mMillisUntilFinished = j;
            screenRecordTile.refreshState(null);
        }

        @Override // com.android.systemui.screenrecord.RecordingController.RecordingStateChangeCallback
        public final void onCountdownEnd() {
            ScreenRecordTile.this.refreshState(null);
        }

        @Override // com.android.systemui.screenrecord.RecordingController.RecordingStateChangeCallback
        public final void onRecordingEnd() {
            ScreenRecordTile.this.refreshState(null);
        }

        @Override // com.android.systemui.screenrecord.RecordingController.RecordingStateChangeCallback
        public final void onRecordingStart() {
            ScreenRecordTile.this.refreshState(null);
        }

        private Callback() {
        }
    }

    public ScreenRecordTile(QSHost qSHost, QsEventLogger qsEventLogger, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, FeatureFlags featureFlags, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, RecordingController recordingController, KeyguardDismissUtil keyguardDismissUtil, KeyguardStateController keyguardStateController, DialogLaunchAnimator dialogLaunchAnimator, PanelInteractor panelInteractor) {
        super(qSHost, qsEventLogger, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        Callback callback = new Callback(this, 0);
        this.mMillisUntilFinished = 0L;
        this.mController = recordingController;
        recordingController.getClass();
        recordingController.observe(((QSTileImpl) this).mLifecycle, callback);
        this.mFlags = featureFlags;
        this.mKeyguardDismissUtil = keyguardDismissUtil;
        this.mKeyguardStateController = keyguardStateController;
        this.mDialogLaunchAnimator = dialogLaunchAnimator;
        this.mPanelInteractor = panelInteractor;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final Intent getLongClickIntent() {
        return null;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final CharSequence getTileLabel() {
        return this.mContext.getString(R.string.quick_settings_screen_record_label);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleClick(final View view) {
        boolean z;
        RecordingController recordingController = this.mController;
        if (recordingController.mIsStarting) {
            Log.d("ScreenRecordTile", "Cancelling countdown");
            RecordingController.AnonymousClass3 anonymousClass3 = recordingController.mCountDownTimer;
            if (anonymousClass3 != null) {
                anonymousClass3.cancel();
            } else {
                Log.e("RecordingController", "Timer was null");
            }
            recordingController.mIsStarting = false;
            Iterator it = recordingController.mListeners.iterator();
            while (it.hasNext()) {
                ((RecordingController.RecordingStateChangeCallback) it.next()).onCountdownEnd();
            }
        } else {
            synchronized (recordingController) {
                z = recordingController.mIsRecording;
            }
            if (z) {
                recordingController.stopRecording();
            } else {
                this.mUiHandler.post(new Runnable() { // from class: com.android.systemui.qs.tiles.ScreenRecordTile$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        final boolean z2;
                        final ScreenRecordTile screenRecordTile = ScreenRecordTile.this;
                        final View view2 = view;
                        if (view2 != null) {
                            if (!((KeyguardStateControllerImpl) screenRecordTile.mKeyguardStateController).mShowing) {
                                z2 = true;
                                Runnable runnable = new Runnable() { // from class: com.android.systemui.qs.tiles.ScreenRecordTile$$ExternalSyntheticLambda1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        ScreenRecordTile screenRecordTile2 = ScreenRecordTile.this;
                                        screenRecordTile2.mDialogLaunchAnimator.disableAllCurrentDialogsExitAnimations();
                                        screenRecordTile2.mPanelInteractor.collapsePanels();
                                    }
                                };
                                Context context = screenRecordTile.mContext;
                                DialogLaunchAnimator dialogLaunchAnimator = screenRecordTile.mDialogLaunchAnimator;
                                ActivityStarter activityStarter = screenRecordTile.mActivityStarter;
                                RecordingController recordingController2 = screenRecordTile.mController;
                                recordingController2.getClass();
                                Flags flags = Flags.INSTANCE;
                                recordingController2.mFlags.getClass();
                                FeatureFlags featureFlags = screenRecordTile.mFlags;
                                featureFlags.getClass();
                                final ScreenRecordDialog screenRecordDialog = new ScreenRecordDialog(context, recordingController2, activityStarter, recordingController2.mUserContextProvider, featureFlags, dialogLaunchAnimator, runnable);
                                screenRecordTile.mKeyguardDismissUtil.executeWhenUnlocked(new ActivityStarter.OnDismissAction() { // from class: com.android.systemui.qs.tiles.ScreenRecordTile$$ExternalSyntheticLambda2
                                    @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
                                    public final boolean onDismiss() {
                                        ScreenRecordTile screenRecordTile2 = ScreenRecordTile.this;
                                        screenRecordTile2.getClass();
                                        Dialog dialog = screenRecordDialog;
                                        if (z2) {
                                            DialogCuj dialogCuj = new DialogCuj(58, "screen_record");
                                            View view3 = view2;
                                            DialogLaunchAnimator dialogLaunchAnimator2 = screenRecordTile2.mDialogLaunchAnimator;
                                            dialogLaunchAnimator2.getClass();
                                            DialogLaunchAnimator.showFromView$default(dialogLaunchAnimator2, dialog, view3, dialogCuj, false, 8);
                                            return false;
                                        }
                                        dialog.show();
                                        return false;
                                    }
                                }, false, true);
                            }
                        } else {
                            screenRecordTile.getClass();
                        }
                        z2 = false;
                        Runnable runnable2 = new Runnable() { // from class: com.android.systemui.qs.tiles.ScreenRecordTile$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                ScreenRecordTile screenRecordTile2 = ScreenRecordTile.this;
                                screenRecordTile2.mDialogLaunchAnimator.disableAllCurrentDialogsExitAnimations();
                                screenRecordTile2.mPanelInteractor.collapsePanels();
                            }
                        };
                        Context context2 = screenRecordTile.mContext;
                        DialogLaunchAnimator dialogLaunchAnimator2 = screenRecordTile.mDialogLaunchAnimator;
                        ActivityStarter activityStarter2 = screenRecordTile.mActivityStarter;
                        RecordingController recordingController22 = screenRecordTile.mController;
                        recordingController22.getClass();
                        Flags flags2 = Flags.INSTANCE;
                        recordingController22.mFlags.getClass();
                        FeatureFlags featureFlags2 = screenRecordTile.mFlags;
                        featureFlags2.getClass();
                        final ScreenRecordDialog screenRecordDialog2 = new ScreenRecordDialog(context2, recordingController22, activityStarter2, recordingController22.mUserContextProvider, featureFlags2, dialogLaunchAnimator2, runnable2);
                        screenRecordTile.mKeyguardDismissUtil.executeWhenUnlocked(new ActivityStarter.OnDismissAction() { // from class: com.android.systemui.qs.tiles.ScreenRecordTile$$ExternalSyntheticLambda2
                            @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
                            public final boolean onDismiss() {
                                ScreenRecordTile screenRecordTile2 = ScreenRecordTile.this;
                                screenRecordTile2.getClass();
                                Dialog dialog = screenRecordDialog2;
                                if (z2) {
                                    DialogCuj dialogCuj = new DialogCuj(58, "screen_record");
                                    View view3 = view2;
                                    DialogLaunchAnimator dialogLaunchAnimator22 = screenRecordTile2.mDialogLaunchAnimator;
                                    dialogLaunchAnimator22.getClass();
                                    DialogLaunchAnimator.showFromView$default(dialogLaunchAnimator22, dialog, view3, dialogCuj, false, 8);
                                    return false;
                                }
                                dialog.show();
                                return false;
                            }
                        }, false, true);
                    }
                });
            }
        }
        refreshState(null);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleUpdateState(QSTile.State state, Object obj) {
        boolean z;
        boolean z2;
        int i;
        int i2;
        CharSequence concat;
        QSTile.BooleanState booleanState = (QSTile.BooleanState) state;
        RecordingController recordingController = this.mController;
        boolean z3 = recordingController.mIsStarting;
        synchronized (recordingController) {
            z = recordingController.mIsRecording;
        }
        boolean z4 = false;
        if (!z && !z3) {
            z2 = false;
        } else {
            z2 = true;
        }
        booleanState.value = z2;
        if (!z && !z3) {
            i = 1;
        } else {
            i = 2;
        }
        booleanState.state = i;
        Context context = this.mContext;
        booleanState.label = context.getString(R.string.quick_settings_screen_record_label);
        if (booleanState.value) {
            i2 = R.drawable.qs_screen_record_icon_on;
        } else {
            i2 = R.drawable.qs_screen_record_icon_off;
        }
        booleanState.icon = QSTileImpl.ResourceIcon.get(i2);
        if (booleanState.state == 1) {
            z4 = true;
        }
        booleanState.forceExpandIcon = z4;
        if (z) {
            booleanState.secondaryLabel = context.getString(R.string.quick_settings_screen_record_stop);
        } else if (z3) {
            booleanState.secondaryLabel = String.format("%d...", Integer.valueOf((int) Math.floorDiv(this.mMillisUntilFinished + 500, 1000)));
        } else {
            booleanState.secondaryLabel = context.getString(R.string.quick_settings_screen_record_start);
        }
        if (TextUtils.isEmpty(booleanState.secondaryLabel)) {
            concat = booleanState.label;
        } else {
            concat = TextUtils.concat(booleanState.label, ", ", booleanState.secondaryLabel);
        }
        booleanState.contentDescription = concat;
        booleanState.expandedAccessibilityClassName = Switch.class.getName();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final QSTile.State newTileState() {
        QSTile.BooleanState booleanState = new QSTile.BooleanState();
        booleanState.label = this.mContext.getString(R.string.quick_settings_screen_record_label);
        booleanState.handlesLongClick = false;
        return booleanState;
    }
}
