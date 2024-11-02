package com.android.systemui.qs.tiles;

import android.os.Handler;
import android.os.Looper;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.DejankUtils;
import com.android.systemui.R;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.DetailAdapter;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.pipeline.domain.interactor.PanelInteractor;
import com.android.systemui.qs.tiles.SensorPrivacyToggleTile;
import com.android.systemui.statusbar.policy.IndividualSensorPrivacyController;
import com.android.systemui.statusbar.policy.IndividualSensorPrivacyControllerImpl;
import com.android.systemui.statusbar.policy.KeyguardStateController;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class MicrophoneToggleTile extends SensorPrivacyToggleTile {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final MicrophoneToggleDetailAdapter mDetailAdapter;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public final class MicrophoneToggleDetailAdapter extends SensorPrivacyToggleTile.SensorPrivacyToggleDetailAdapter {
        public MicrophoneToggleDetailAdapter() {
            super();
        }

        @Override // com.android.systemui.qs.tiles.SensorPrivacyToggleTile.SensorPrivacyToggleDetailAdapter
        public final String getDetailSummary() {
            int i = MicrophoneToggleTile.$r8$clinit;
            return MicrophoneToggleTile.this.mContext.getString(R.string.qs_microphone_toggle_detail_summary);
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public final CharSequence getTitle() {
            int i = MicrophoneToggleTile.$r8$clinit;
            return MicrophoneToggleTile.this.mContext.getString(R.string.qs_microphone_toggle_detail_title);
        }
    }

    public MicrophoneToggleTile(QSHost qSHost, QsEventLogger qsEventLogger, Looper looper, Handler handler, MetricsLogger metricsLogger, FalsingManager falsingManager, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, IndividualSensorPrivacyController individualSensorPrivacyController, KeyguardStateController keyguardStateController, PanelInteractor panelInteractor) {
        super(qSHost, qsEventLogger, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger, individualSensorPrivacyController, keyguardStateController, panelInteractor);
        this.mDetailAdapter = new MicrophoneToggleDetailAdapter();
    }

    @Override // com.android.systemui.qs.tileimpl.SQSTileImpl, com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final DetailAdapter getDetailAdapter() {
        return this.mDetailAdapter;
    }

    @Override // com.android.systemui.qs.tiles.SensorPrivacyToggleTile
    public final int getIconRes() {
        return R.drawable.quick_panel_icon_microphoneaccess;
    }

    @Override // com.android.systemui.qs.tiles.SensorPrivacyToggleTile
    public final String getRestriction() {
        return "disallow_microphone_toggle";
    }

    @Override // com.android.systemui.qs.tiles.SensorPrivacyToggleTile
    public final int getSensorId() {
        return 1;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final CharSequence getTileLabel() {
        return this.mContext.getString(R.string.sec_quick_settings_mic_label);
    }

    @Override // com.android.systemui.qs.tileimpl.SQSTileImpl, com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final boolean isAvailable() {
        if (((IndividualSensorPrivacyControllerImpl) this.mSensorPrivacyController).mSensorPrivacyManager.supportsSensorToggle(1) && ((Boolean) DejankUtils.whitelistIpcs(new MicrophoneToggleTile$$ExternalSyntheticLambda0())).booleanValue()) {
            return true;
        }
        return false;
    }
}
