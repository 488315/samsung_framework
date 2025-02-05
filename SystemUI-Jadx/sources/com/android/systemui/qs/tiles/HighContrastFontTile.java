package com.android.systemui.qs.tiles;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import com.android.internal.logging.MetricsLogger;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.R;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.SettingObserver;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.settings.SecureSettings;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class HighContrastFontTile extends QSTileImpl {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ActivityStarter mActivityStarter;
    public final AnonymousClass1 mSetting;

    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.systemui.qs.tiles.HighContrastFontTile$1] */
    public HighContrastFontTile(QSHost qSHost, QsEventLogger qsEventLogger, Looper looper, Handler handler, Resources resources, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardStateController keyguardStateController, UserTracker userTracker, SecureSettings secureSettings) {
        super(qSHost, qsEventLogger, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.qs.tiles.HighContrastFontTile.2
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                HighContrastFontTile.this.refreshState(null);
            }
        };
        this.mActivityStarter = activityStarter;
        this.mSetting = new SettingObserver(secureSettings, this.mHandler, "high_text_contrast_enabled", ((UserTrackerImpl) userTracker).getUserId()) { // from class: com.android.systemui.qs.tiles.HighContrastFontTile.1
            @Override // com.android.systemui.qs.SettingObserver
            public final void handleValueChanged(int i, boolean z) {
                HighContrastFontTile highContrastFontTile = HighContrastFontTile.this;
                Integer valueOf = Integer.valueOf(i);
                int i2 = HighContrastFontTile.$r8$clinit;
                highContrastFontTile.handleRefreshState(valueOf);
            }
        };
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final void destroy() {
        super.destroy();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final Intent getLongClickIntent() {
        return new Intent("com.samsung.accessibility.HIGH_CONTRAST_FONT");
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final int getMetricsCategory() {
        return 5015;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final CharSequence getTileLabel() {
        return this.mContext.getString(R.string.quick_settings_highcontrastfont_detail_title);
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0021  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x001d  */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void handleClick(android.view.View r3) {
        /*
            r2 = this;
            java.lang.Class<com.android.systemui.knox.KnoxStateMonitor> r3 = com.android.systemui.knox.KnoxStateMonitor.class
            java.lang.Object r3 = com.android.systemui.Dependency.get(r3)
            com.android.systemui.knox.KnoxStateMonitor r3 = (com.android.systemui.knox.KnoxStateMonitor) r3
            com.android.systemui.knox.KnoxStateMonitorImpl r3 = (com.android.systemui.knox.KnoxStateMonitorImpl) r3
            com.android.systemui.knox.EdmMonitor r3 = r3.mEdmMonitor
            r0 = 1
            if (r3 == 0) goto L1a
            com.android.systemui.knox.KnoxStateMonitorImpl r1 = r3.knoxStateMonitor
            android.content.Context r1 = r1.mContext
            boolean r3 = r3.mSettingsChangesAllowed
            r3 = r3 ^ r0
            if (r3 == 0) goto L1a
            r3 = r0
            goto L1b
        L1a:
            r3 = 0
        L1b:
            if (r3 == 0) goto L21
            r2.showItPolicyToast()
            return
        L21:
            com.android.systemui.plugins.qs.QSTile$State r3 = r2.mState
            com.android.systemui.plugins.qs.QSTile$BooleanState r3 = (com.android.systemui.plugins.qs.QSTile.BooleanState) r3
            boolean r3 = r3.value
            r3 = r3 ^ r0
            com.android.systemui.qs.tiles.HighContrastFontTile$1 r2 = r2.mSetting
            r2.setValue(r3)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.tiles.HighContrastFontTile.handleClick(android.view.View):void");
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleDestroy() {
        super.handleDestroy();
        setListening(false);
    }

    /* JADX WARN: Code restructure failed: missing block: B:4:0x0017, code lost:
    
        if ((!r3.mSettingsChangesAllowed) != false) goto L8;
     */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void handleSecondaryClick(android.view.View r3) {
        /*
            r2 = this;
            java.lang.Class<com.android.systemui.knox.KnoxStateMonitor> r3 = com.android.systemui.knox.KnoxStateMonitor.class
            java.lang.Object r3 = com.android.systemui.Dependency.get(r3)
            com.android.systemui.knox.KnoxStateMonitor r3 = (com.android.systemui.knox.KnoxStateMonitor) r3
            com.android.systemui.knox.KnoxStateMonitorImpl r3 = (com.android.systemui.knox.KnoxStateMonitorImpl) r3
            com.android.systemui.knox.EdmMonitor r3 = r3.mEdmMonitor
            r0 = 0
            if (r3 == 0) goto L1a
            com.android.systemui.knox.KnoxStateMonitorImpl r1 = r3.knoxStateMonitor
            android.content.Context r1 = r1.mContext
            boolean r3 = r3.mSettingsChangesAllowed
            r1 = 1
            r3 = r3 ^ r1
            if (r3 == 0) goto L1a
            goto L1b
        L1a:
            r1 = r0
        L1b:
            if (r1 == 0) goto L21
            r2.showItPolicyToast()
            return
        L21:
            android.content.Intent r3 = r2.getLongClickIntent()
            com.android.systemui.plugins.ActivityStarter r2 = r2.mActivityStarter
            r2.postStartActivityDismissingKeyguard(r3, r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.tiles.HighContrastFontTile.handleSecondaryClick(android.view.View):void");
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleSetListening(boolean z) {
        super.handleSetListening(z);
        setListening(z);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleUpdateState(QSTile.State state, Object obj) {
        int value;
        boolean z;
        int i;
        QSTile.BooleanState booleanState = (QSTile.BooleanState) state;
        if (obj instanceof Integer) {
            value = ((Integer) obj).intValue();
        } else {
            value = getValue();
        }
        if (value != 0) {
            z = true;
        } else {
            z = false;
        }
        booleanState.value = z;
        if (z) {
            i = 2;
        } else {
            i = 1;
        }
        booleanState.state = i;
        booleanState.dualTarget = true;
        booleanState.label = this.mContext.getString(R.string.quick_settings_highcontrastfont_detail_title);
        booleanState.icon = QSTileImpl.ResourceIcon.get(R.drawable.ic_quick_panel_icon_accessibility_high_contrast_fonts);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleUserSwitch(int i) {
        AnonymousClass1 anonymousClass1 = this.mSetting;
        anonymousClass1.setUserId(i);
        handleRefreshState(Integer.valueOf(anonymousClass1.getValue()));
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final QSTile.State newTileState() {
        return new QSTile.BooleanState();
    }
}
