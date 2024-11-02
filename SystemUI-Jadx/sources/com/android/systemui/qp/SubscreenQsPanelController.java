package com.android.systemui.qp;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.util.Log;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.qs.InjectionInflationController;
import com.android.systemui.qs.QSTileHost;
import com.android.systemui.util.QsResetSettingsManager;
import com.android.systemui.util.ViewController;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class SubscreenQsPanelController implements QsResetSettingsManager.ResetSettingsApplier, QsResetSettingsManager.DemoResetSettingsApplier {
    public static Context mContext;
    public static SubscreenSubRoomQuickSettings mSubRoomQuickSettings;
    public ViewController mBaseViewController;
    public final SubroomQuickSettingsBaseViewFactory mBaseViewFactory;
    public final SubscreenQuickSettingsControllerFactory mFactory;
    public final QSTileHost mHost;
    public final InjectionInflationController mInjectionInflater;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public final class SubroomQuickSettingsBaseViewFactory {
        public SubroomQuickSettingsBaseViewFactory() {
        }
    }

    public SubscreenQsPanelController(Context context, InjectionInflationController injectionInflationController, QSTileHost qSTileHost) {
        mContext = context;
        this.mInjectionInflater = injectionInflationController;
        this.mHost = qSTileHost;
        mSubRoomQuickSettings = SubscreenSubRoomQuickSettings.getInstance(context, injectionInflationController);
        ((QsResetSettingsManager) Dependency.get(QsResetSettingsManager.class)).registerApplier(this);
        ((QsResetSettingsManager) Dependency.get(QsResetSettingsManager.class)).registerDemoApplier(this);
        if (!QpRune.QUICK_PANEL_SUBSCREEN) {
            Settings.System.putInt(mContext.getContentResolver(), "sub_screen_brightness_mode", 0);
        }
        this.mFactory = new SubscreenQuickSettingsControllerFactory();
        this.mBaseViewFactory = new SubroomQuickSettingsBaseViewFactory();
    }

    @Override // com.android.systemui.util.QsResetSettingsManager.DemoResetSettingsApplier
    public final void applyDemoResetSetting() {
        Log.d("SubscreenQsPanelController", "applyDemoResetSetting");
        SharedPreferences.Editor edit = mContext.getSharedPreferences("ShowDifferentHelpViewText", 0).edit();
        edit.putInt("helpViewTextCount", 0);
        edit.commit();
    }

    @Override // com.android.systemui.util.QsResetSettingsManager.ResetSettingsApplier
    public final void applyResetSetting() {
        Log.d("SubscreenQsPanelController", "applyResetSetting");
        if (QpRune.QUICK_PANEL_SUBSCREEN) {
            Settings.System.putInt(mContext.getContentResolver(), "sub_screen_brightness_mode", 1);
        } else {
            Settings.System.putInt(mContext.getContentResolver(), "sub_screen_brightness_mode", 0);
        }
    }

    public final SubscreenQSControllerContract$Presenter getInstance(int i) {
        Context context = mContext;
        this.mFactory.getClass();
        if (i != 1) {
            if (i != 2) {
                if (i != 3) {
                    return null;
                }
                return SubscreenAirplaneController.getInstance(context);
            }
            return SubscreenBleController.getInstance(context);
        }
        return SubscreenWifiController.getInstance(context);
    }

    public final SubscreenSubRoomQuickSettings getSubRoomQuickPanel() {
        if (mSubRoomQuickSettings == null) {
            mSubRoomQuickSettings = SubscreenSubRoomQuickSettings.getInstance(mContext, this.mInjectionInflater);
        }
        return mSubRoomQuickSettings;
    }

    /* JADX WARN: Type inference failed for: r4v1, types: [com.android.systemui.util.ViewController, com.android.systemui.qp.SubscreenQSControllerContract$BaseViewController] */
    public final void init() {
        ViewController viewController;
        SubroomQuickSettingsBaseViewFactory subroomQuickSettingsBaseViewFactory = this.mBaseViewFactory;
        subroomQuickSettingsBaseViewFactory.getClass();
        if (QpRune.QUICK_PANEL_SUBSCREEN) {
            viewController = new SubscreenQsPanelControllerBase((SubroomQuickSettingsQSPanelBaseView) mSubRoomQuickSettings.mMainView.findViewById(R.id.subscreen_quick_settings_baseview), SubscreenQsPanelController.this.mHost);
        } else if (QpRune.QUICK_SETTINGS_SUBSCREEN) {
            viewController = new SubscreenBaseViewControllerBase((SubroomQuickSettingsBaseView) mSubRoomQuickSettings.mMainView.findViewById(R.id.subscreen_quick_settings_baseview));
        } else {
            viewController = null;
        }
        this.mBaseViewController = viewController;
        Log.d("SubscreenQsPanelController", "init() controller: " + this.mBaseViewController);
        ?? r4 = this.mBaseViewController;
        if (r4 != 0) {
            r4.initView();
        }
    }
}
