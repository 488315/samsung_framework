package com.android.systemui.qs.customize.setting;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowInsets;
import androidx.activity.ComponentActivity;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.Rune;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.qs.QSTileHost;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.customize.SecQSCustomizerTileAdapter;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.multisim.MultiSIMController;
import com.android.systemui.tuner.TunerService;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import java.util.concurrent.Executor;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class SecQSSettingEditResources {
    public static final int LARGE_POS;
    public static final int REMOVE_ICON_ID;
    public final Executor bgExecutor;
    public final SharedPreferences.Editor editor;
    public boolean isAnotherActivityOverMain;
    public boolean isCurrentTopEdit;
    public boolean isMainRelaunchedByConfigChanged;
    public final ActivityStarter mActivityStarter;
    public final Context mContext;
    public final SecQSPanelResourcePicker mResourcePicker;
    public final Executor mainExecutor;
    public final MultiSIMController multiSIMController;
    public SecQSCustomizerTileAdapter tileFullAdapter;
    public final QSTileHost tileHost;
    public SecQSCustomizerTileAdapter tileTopAdapter;
    public final TunerService tunerService;
    public final UserTracker userTracker;
    public final String TAG = "SecQSSettingEdit";
    public final int MIN_DEFAULT_DPI = IKnoxCustomManager.Stub.TRANSACTION_addAutoCallNumber;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public abstract class POPUPSELECTED {
        public static final /* synthetic */ POPUPSELECTED[] $VALUES = {new BRIGHTNESS("BRIGHTNESS", 0), new DEVICEMEDIA("DEVICEMEDIA", 1), new MULTISIM("MULTISIM", 2), new HIDE_SMART_VIEW_LARGE_TILE("HIDE_SMART_VIEW_LARGE_TILE", 3)};
        public static final Companion Companion = new Companion(null);
        public Context mContext;
        public MultiSIMController multiSIMController;
        public TunerService tunerService;
        public Function2 updateSALog;

        /* JADX INFO: Fake field, exist only in values array */
        POPUPSELECTED EF5;

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes2.dex */
        public static final class BRIGHTNESS extends POPUPSELECTED {
            public BRIGHTNESS(String str, int i) {
                super(str, i, null);
            }

            @Override // com.android.systemui.qs.customize.setting.SecQSSettingEditResources.POPUPSELECTED
            public final int getSelectedIdx() {
                if (getTunerService().getValue(1, "brightness_on_top") == 0) {
                    return 1;
                }
                return 0;
            }

            @Override // com.android.systemui.qs.customize.setting.SecQSSettingEditResources.POPUPSELECTED
            public final boolean isAvailable() {
                return true;
            }

            @Override // com.android.systemui.qs.customize.setting.SecQSSettingEditResources.POPUPSELECTED
            public final void updateValue(boolean z) {
                getTunerService().setValue(z ? 1 : 0, "brightness_on_top");
                Function2 function2 = this.updateSALog;
                if (function2 == null) {
                    function2 = null;
                }
                function2.invoke("QPPS1023", Boolean.valueOf(z));
            }
        }

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes2.dex */
        public final class Companion {
            private Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }
        }

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes2.dex */
        public static final class DEVICEMEDIA extends POPUPSELECTED {
            public DEVICEMEDIA(String str, int i) {
                super(str, i, null);
            }

            @Override // com.android.systemui.qs.customize.setting.SecQSSettingEditResources.POPUPSELECTED
            public final int getSelectedIdx() {
                boolean z;
                boolean z2;
                if (getTunerService().getValue(1, "qspanel_media_quickcontrol_bar_available") != 0) {
                    z = true;
                } else {
                    z = false;
                }
                if (!z) {
                    return 1;
                }
                if (getTunerService().getValue(-1, "qspanel_media_quickcontrol_bar_available_on_top") != 0) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                if (!z2) {
                    return 1;
                }
                return 0;
            }

            @Override // com.android.systemui.qs.customize.setting.SecQSSettingEditResources.POPUPSELECTED
            public final boolean isAvailable() {
                return true;
            }

            @Override // com.android.systemui.qs.customize.setting.SecQSSettingEditResources.POPUPSELECTED
            public final void updateValue(boolean z) {
                getTunerService().setValue(z ? 1 : 0, "qspanel_media_quickcontrol_bar_available");
                getTunerService().getValue(-1, "qspanel_media_quickcontrol_bar_available_on_top");
                getTunerService().setValue(z ? 1 : 0, "qspanel_media_quickcontrol_bar_available_on_top");
                Function2 function2 = this.updateSALog;
                if (function2 == null) {
                    function2 = null;
                }
                function2.invoke("QPPS1024", Boolean.valueOf(z));
            }
        }

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes2.dex */
        public static final class HIDE_SMART_VIEW_LARGE_TILE extends POPUPSELECTED {
            public HIDE_SMART_VIEW_LARGE_TILE(String str, int i) {
                super(str, i, null);
            }

            @Override // com.android.systemui.qs.customize.setting.SecQSSettingEditResources.POPUPSELECTED
            public final int getSelectedIdx() {
                return getTunerService().getValue(0, "hide_smart_view_large_tile_on_panel");
            }

            @Override // com.android.systemui.qs.customize.setting.SecQSSettingEditResources.POPUPSELECTED
            public final boolean isAvailable() {
                return QpRune.QUICK_HIDE_TILE_FROM_BAR;
            }

            @Override // com.android.systemui.qs.customize.setting.SecQSSettingEditResources.POPUPSELECTED
            public final void updateValue(boolean z) {
                getTunerService().setValue(!z ? 1 : 0, "hide_smart_view_large_tile_on_panel");
            }
        }

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes2.dex */
        public static final class MULTISIM extends POPUPSELECTED {
            public MULTISIM(String str, int i) {
                super(str, i, null);
            }

            @Override // com.android.systemui.qs.customize.setting.SecQSSettingEditResources.POPUPSELECTED
            public final int getSelectedIdx() {
                if (getTunerService().getValue(1, "multi_sim_bar_show_on_qspanel") == 0) {
                    return 1;
                }
                return 0;
            }

            @Override // com.android.systemui.qs.customize.setting.SecQSSettingEditResources.POPUPSELECTED
            public final boolean isAvailable() {
                MultiSIMController multiSIMController;
                if (!QpRune.QUICK_BAR_MULTISIM || !Rune.SYSUI_MULTI_SIM) {
                    return false;
                }
                MultiSIMController multiSIMController2 = this.multiSIMController;
                if (multiSIMController2 != null) {
                    multiSIMController = multiSIMController2;
                } else {
                    multiSIMController = null;
                }
                if (multiSIMController == null) {
                    return false;
                }
                if (multiSIMController2 == null) {
                    multiSIMController2 = null;
                }
                if (!multiSIMController2.isMultiSimAvailable() || getTunerService().getValue(0, "multi_sim_bar_hide_by_knox_restrictions") != 0) {
                    return false;
                }
                return true;
            }

            @Override // com.android.systemui.qs.customize.setting.SecQSSettingEditResources.POPUPSELECTED
            public final void updateValue(boolean z) {
                getTunerService().setValue(z ? 1 : 0, "multi_sim_bar_show_on_qspanel");
                Function2 function2 = this.updateSALog;
                if (function2 == null) {
                    function2 = null;
                }
                function2.invoke("QPPS1025", Boolean.valueOf(z));
            }
        }

        public /* synthetic */ POPUPSELECTED(String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, i);
        }

        public static POPUPSELECTED valueOf(String str) {
            return (POPUPSELECTED) Enum.valueOf(POPUPSELECTED.class, str);
        }

        public static POPUPSELECTED[] values() {
            return (POPUPSELECTED[]) $VALUES.clone();
        }

        public abstract int getSelectedIdx();

        public final TunerService getTunerService() {
            TunerService tunerService = this.tunerService;
            if (tunerService != null) {
                return tunerService;
            }
            return null;
        }

        public abstract boolean isAvailable();

        public abstract void updateValue(boolean z);

        private POPUPSELECTED(String str, int i) {
        }
    }

    static {
        new Companion(null);
        REMOVE_ICON_ID = View.generateViewId();
        LARGE_POS = 9999;
    }

    public SecQSSettingEditResources(TunerService tunerService, Context context, MultiSIMController multiSIMController, ActivityStarter activityStarter, SecQSPanelResourcePicker secQSPanelResourcePicker, QSTileHost qSTileHost, UserTracker userTracker, Executor executor, Executor executor2) {
        this.tunerService = tunerService;
        this.mContext = context;
        this.multiSIMController = multiSIMController;
        this.mActivityStarter = activityStarter;
        this.mResourcePicker = secQSPanelResourcePicker;
        this.tileHost = qSTileHost;
        this.userTracker = userTracker;
        this.mainExecutor = executor;
        this.bgExecutor = executor2;
        this.editor = context.getSharedPreferences("quick_pref", 0).edit();
    }

    public static int getBottomPadding(ComponentActivity componentActivity, WindowInsets windowInsets) {
        int stableInsetBottom = windowInsets.getStableInsetBottom();
        boolean z = QpRune.QUICK_TABLET;
        if (z) {
            stableInsetBottom = componentActivity.getResources().getDimensionPixelSize(R.dimen.navigation_bar_height);
        }
        int i = componentActivity.getResources().getDisplayMetrics().heightPixels;
        int dimensionPixelOffset = componentActivity.getResources().getDimensionPixelOffset(R.dimen.qs_edit_tablet_height);
        int dimensionPixelSize = i - componentActivity.getResources().getDimensionPixelSize(R.dimen.status_bar_height);
        if (z && dimensionPixelSize - stableInsetBottom > dimensionPixelOffset) {
            return dimensionPixelSize - dimensionPixelOffset;
        }
        return stableInsetBottom;
    }

    public static int getSidePadding(ComponentActivity componentActivity) {
        int i;
        int i2 = componentActivity.getResources().getDisplayMetrics().widthPixels;
        if (QpRune.QUICK_TABLET) {
            i = componentActivity.getResources().getDimensionPixelOffset(R.dimen.qqs_panel_width_tablet);
        } else {
            i = i2;
        }
        return (i2 - i) / 2;
    }

    public final boolean isPhoneLandscape() {
        if ((QpRune.QUICK_TABLET && DisplayMetrics.DENSITY_DEVICE_STABLE > this.MIN_DEFAULT_DPI) || this.mContext.getResources().getConfiguration().orientation != 2) {
            return false;
        }
        return true;
    }
}
