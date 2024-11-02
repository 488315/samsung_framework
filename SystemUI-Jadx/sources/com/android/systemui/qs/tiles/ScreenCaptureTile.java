package com.android.systemui.qs.tiles;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
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
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.samsung.android.feature.SemFloatingFeature;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class ScreenCaptureTile extends SQSTileImpl {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public boolean mNeedDoScreenCapture;
    public final AnonymousClass1 mReceiver;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.qs.tiles.ScreenCaptureTile$1, android.content.BroadcastReceiver] */
    public ScreenCaptureTile(QSHost qSHost, QsEventLogger qsEventLogger, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, BroadcastDispatcher broadcastDispatcher, PanelInteractor panelInteractor) {
        super(qSHost, qsEventLogger, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.mNeedDoScreenCapture = false;
        ?? r1 = new BroadcastReceiver() { // from class: com.android.systemui.qs.tiles.ScreenCaptureTile.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                ScreenCaptureTile screenCaptureTile = ScreenCaptureTile.this;
                int i = ScreenCaptureTile.$r8$clinit;
                Log.d(screenCaptureTile.TAG, "action:" + action);
                String str = "doScreenCapture";
                if ("com.samsung.systemui.statusbar.COLLAPSED".equals(action)) {
                    final ScreenCaptureTile screenCaptureTile2 = ScreenCaptureTile.this;
                    if (screenCaptureTile2.mNeedDoScreenCapture) {
                        final Intent intent2 = new Intent("com.samsung.android.capture.ScreenshotExecutor");
                        intent2.putExtra("capturedOrigin", 3);
                        new Thread(str) { // from class: com.android.systemui.qs.tiles.ScreenCaptureTile.2
                            @Override // java.lang.Thread, java.lang.Runnable
                            public final void run() {
                                int i2;
                                try {
                                    ScreenCaptureTile screenCaptureTile3 = ScreenCaptureTile.this;
                                    int i3 = ScreenCaptureTile.$r8$clinit;
                                    if (r0.widthPixels / screenCaptureTile3.mContext.getResources().getDisplayMetrics().density > 457.0f) {
                                        i2 = 370;
                                    } else {
                                        i2 = 100;
                                    }
                                    Thread.sleep(i2);
                                    ScreenCaptureTile.this.mContext.sendBroadcast(intent2, "com.samsung.permission.CAPTURE");
                                    Log.d(ScreenCaptureTile.this.TAG, "doScreenCapture Send com.samsung.android.capture.ScreenshotExecutor");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }.start();
                        ScreenCaptureTile.this.mNeedDoScreenCapture = false;
                        return;
                    }
                    return;
                }
                if ("com.samsung.systemui.statusbar.EXPANDED".equals(action) && ScreenCaptureTile.this.mNeedDoScreenCapture && ((KeyguardStateControllerImpl) ((KeyguardStateController) Dependency.get(KeyguardStateController.class))).mShowing) {
                    final ScreenCaptureTile screenCaptureTile3 = ScreenCaptureTile.this;
                    screenCaptureTile3.getClass();
                    final Intent intent3 = new Intent("com.samsung.android.capture.ScreenshotExecutor");
                    intent3.putExtra("capturedOrigin", 3);
                    new Thread(str) { // from class: com.android.systemui.qs.tiles.ScreenCaptureTile.2
                        @Override // java.lang.Thread, java.lang.Runnable
                        public final void run() {
                            int i2;
                            try {
                                ScreenCaptureTile screenCaptureTile32 = ScreenCaptureTile.this;
                                int i3 = ScreenCaptureTile.$r8$clinit;
                                if (r0.widthPixels / screenCaptureTile32.mContext.getResources().getDisplayMetrics().density > 457.0f) {
                                    i2 = 370;
                                } else {
                                    i2 = 100;
                                }
                                Thread.sleep(i2);
                                ScreenCaptureTile.this.mContext.sendBroadcast(intent3, "com.samsung.permission.CAPTURE");
                                Log.d(ScreenCaptureTile.this.TAG, "doScreenCapture Send com.samsung.android.capture.ScreenshotExecutor");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                    ScreenCaptureTile.this.mNeedDoScreenCapture = false;
                }
            }
        };
        this.mReceiver = r1;
        this.mBroadcastDispatcher = broadcastDispatcher;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.samsung.systemui.statusbar.COLLAPSED");
        intentFilter.addAction("com.samsung.systemui.statusbar.EXPANDED");
        broadcastDispatcher.registerReceiver(intentFilter, r1);
    }

    @Override // com.android.systemui.qs.tileimpl.SQSTileImpl
    public final String composeChangeAnnouncement() {
        return this.mContext.getString(R.string.quick_settings_screen_capture_label);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final Intent getLongClickIntent() {
        Intent intent = new Intent();
        if (SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_SCREEN_RECORDER")) {
            intent.setClassName("com.samsung.android.app.smartcapture", "com.samsung.android.app.settings.SettingsActivity");
            return intent;
        }
        intent.setClassName("com.samsung.android.app.smartcapture", "com.samsung.android.app.settings.SettingsAliasActivity");
        return intent;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final int getMetricsCategory() {
        return 112;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final CharSequence getTileLabel() {
        return this.mContext.getString(R.string.quick_settings_screen_capture_label);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleClick(View view) {
        Log.d(this.TAG, "handleClick : " + ((QSTile.BooleanState) this.mState).value);
        this.mNeedDoScreenCapture = true;
        ((CommandQueue) Dependency.get(CommandQueue.class)).animateCollapsePanels();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleDestroy() {
        super.handleDestroy();
        this.mBroadcastDispatcher.unregisterReceiver(this.mReceiver);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleUpdateState(QSTile.State state, Object obj) {
        QSTile.BooleanState booleanState = (QSTile.BooleanState) state;
        booleanState.value = false;
        booleanState.state = 1;
        booleanState.dualTarget = true;
        Context context = this.mContext;
        booleanState.label = context.getString(R.string.quick_settings_screen_capture_label);
        booleanState.icon = QSTileImpl.ResourceIcon.get(R.drawable.quick_panel_icon_smart_capture);
        booleanState.contentDescription = context.getString(R.string.quick_settings_screen_capture_label);
    }

    @Override // com.android.systemui.qs.tileimpl.SQSTileImpl, com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final boolean isAvailable() {
        return true;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final QSTile.State newTileState() {
        return new QSTile.BooleanState();
    }
}
