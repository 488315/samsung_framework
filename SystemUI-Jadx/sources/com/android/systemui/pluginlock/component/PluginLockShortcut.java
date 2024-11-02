package com.android.systemui.pluginlock.component;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.systemui.pluginlock.PluginLockInstanceData;
import com.android.systemui.pluginlock.PluginLockInstanceState;
import com.android.systemui.pluginlock.PluginLockMediator;
import com.android.systemui.pluginlock.PluginLockMediatorImpl;
import com.android.systemui.pluginlock.model.DynamicLockData;
import com.android.systemui.util.SettingsHelper;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class PluginLockShortcut extends AbstractPluginLockItem {
    public final PluginLockShortcut$$ExternalSyntheticLambda0 mCallback;
    public boolean mIsDlsData;
    public final PluginLockMediator mMediator;
    public int mShortcutVisibility;

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.pluginlock.component.PluginLockShortcut$$ExternalSyntheticLambda0] */
    public PluginLockShortcut(Context context, PluginLockInstanceState pluginLockInstanceState, SettingsHelper settingsHelper, PluginLockMediator pluginLockMediator) {
        super(context, pluginLockInstanceState, settingsHelper);
        this.mIsDlsData = true;
        this.mShortcutVisibility = -1;
        this.mCallback = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.pluginlock.component.PluginLockShortcut$$ExternalSyntheticLambda0
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                PluginLockInstanceData.Data.RecoverData recoverData;
                PluginLockShortcut pluginLockShortcut = PluginLockShortcut.this;
                pluginLockShortcut.getClass();
                if (uri != null) {
                    Log.d("PluginLockShortcut", "onChange() uri: " + uri);
                    if (pluginLockShortcut.mCallbackRegisterTime != 0 && pluginLockShortcut.mCallbackValue != -1) {
                        if (System.currentTimeMillis() - pluginLockShortcut.mCallbackRegisterTime < 8000 && pluginLockShortcut.mCallbackValue == pluginLockShortcut.getSettingsInt(1, "lockscreen_show_shortcut")) {
                            Log.d("PluginLockShortcut", "onChange() ignored");
                            return;
                        }
                        if (!uri.equals(Settings.System.getUriFor("lockscreen_show_shortcut"))) {
                            Log.w("PluginLockShortcut", "updateLockStarStoredData, uri is null or shortcut isn't enabled");
                        } else {
                            Bundle bundle = new Bundle();
                            bundle.putString("action", "update_lockstar_data");
                            Bundle bundle2 = new Bundle();
                            bundle2.putString("update_lockstar_data_item", "shortcut_visibility");
                            bundle2.putInt("shortcut_visibility", pluginLockShortcut.getSettingsInt(1, "lockscreen_show_shortcut"));
                            bundle.putBundle("extras", bundle2);
                            Log.d("PluginLockShortcut", "updateLockStarStoredData() bundle" + bundle.toString());
                            ((PluginLockMediatorImpl) pluginLockShortcut.mMediator).onEventReceived(bundle);
                        }
                        Log.d("PluginLockShortcut", "recover()");
                        pluginLockShortcut.mShortcutVisibility = -1;
                        pluginLockShortcut.setShortcutBackup(-1);
                        PluginLockInstanceState pluginLockInstanceState2 = pluginLockShortcut.mInstanceState;
                        if (pluginLockInstanceState2 != null && (recoverData = pluginLockInstanceState2.getRecoverData()) != null) {
                            recoverData.setShortcutState(-2);
                            pluginLockShortcut.mInstanceState.updateDb();
                            return;
                        }
                        return;
                    }
                    Log.w("PluginLockShortcut", "onChange() wrong state");
                }
            }
        };
        this.mMediator = pluginLockMediator;
    }

    public final void apply(DynamicLockData dynamicLockData, DynamicLockData dynamicLockData2) {
        loadData(dynamicLockData2);
        int shortcutState = getShortcutState();
        ListPopupWindow$$ExternalSyntheticOutline0.m("apply() state:", shortcutState, "PluginLockShortcut");
        if (shortcutState == -2) {
            Log.d("PluginLockShortcut", "apply() skip!");
            return;
        }
        if (shortcutState == -3) {
            update(dynamicLockData, dynamicLockData2);
            return;
        }
        if (this.mShortcutVisibility != -1) {
            int i = 1;
            int settingsInt = getSettingsInt(1, "lockscreen_show_shortcut");
            Log.d("PluginLockShortcut", "apply() curValue: " + settingsInt);
            setShortcutBackup(settingsInt);
            if (this.mShortcutVisibility != 0) {
                i = 0;
            }
            Log.d("PluginLockShortcut", "apply() dlsVisible visibility: " + i);
            setShortcutVisibility(i);
            registerCallback(i);
        }
    }

    public final int getShortcutState() {
        PluginLockInstanceData.Data.RecoverData recoverData;
        PluginLockInstanceState pluginLockInstanceState = this.mInstanceState;
        if (pluginLockInstanceState == null || (recoverData = pluginLockInstanceState.getRecoverData()) == null) {
            return -1;
        }
        return recoverData.getShortcutState().intValue();
    }

    public final void loadData(DynamicLockData dynamicLockData) {
        this.mIsDlsData = dynamicLockData.isDlsData();
        this.mShortcutVisibility = dynamicLockData.getShortcutData().getVisibility().intValue();
        RecyclerView$$ExternalSyntheticOutline0.m(new StringBuilder("loadData() mShortcutVisibility: "), this.mShortcutVisibility, "PluginLockShortcut");
    }

    public final void registerCallback(int i) {
        ListPopupWindow$$ExternalSyntheticOutline0.m("registerCallback() value: ", i, "PluginLockShortcut");
        this.mCallbackValue = i;
        this.mCallbackRegisterTime = System.currentTimeMillis();
        this.mSettingsHelper.registerCallback(this.mCallback, Settings.System.getUriFor("lockscreen_show_shortcut"));
    }

    public final void setShortcutBackup(int i) {
        PluginLockInstanceData.Data.RecoverData recoverData;
        PluginLockInstanceState pluginLockInstanceState = this.mInstanceState;
        if (pluginLockInstanceState != null && (recoverData = pluginLockInstanceState.getRecoverData()) != null) {
            recoverData.setShortcutBackup(i);
            if (i >= 0) {
                recoverData.setShortcutState(-3);
            } else {
                recoverData.setShortcutState(-1);
            }
            this.mInstanceState.updateDb();
        }
    }

    public final void setShortcutVisibility(int i) {
        Log.d("PluginLockShortcut", "setShortcutVisibility : " + i);
        putSettingsSystem(i, "lockscreen_show_shortcut");
    }

    public final void update(DynamicLockData dynamicLockData, DynamicLockData dynamicLockData2) {
        int i;
        PluginLockInstanceData.Data.RecoverData recoverData;
        int i2;
        loadData(dynamicLockData2);
        int shortcutState = getShortcutState();
        ListPopupWindow$$ExternalSyntheticOutline0.m("update() state: ", shortcutState, "PluginLockShortcut");
        if (shortcutState == -2 && this.mIsDlsData) {
            Log.d("PluginLockShortcut", "update() skip!");
            return;
        }
        if (shortcutState == -1) {
            apply(dynamicLockData, dynamicLockData2);
            return;
        }
        Log.d("PluginLockShortcut", "unregisterCallback() ");
        this.mCallbackValue = -1;
        this.mCallbackRegisterTime = 0L;
        this.mSettingsHelper.unregisterCallback(this.mCallback);
        int intValue = dynamicLockData2.getShortcutData().getVisibility().intValue();
        ListPopupWindow$$ExternalSyntheticOutline0.m("update() visibility: ", intValue, "PluginLockShortcut");
        if (intValue != -1) {
            if (intValue == 0) {
                i2 = 1;
            } else {
                i2 = 0;
            }
            setShortcutVisibility(i2);
            registerCallback(i2);
            return;
        }
        PluginLockInstanceState pluginLockInstanceState = this.mInstanceState;
        if (pluginLockInstanceState != null && (recoverData = pluginLockInstanceState.getRecoverData()) != null) {
            i = recoverData.getShortcutBackupValue().intValue();
        } else {
            i = -1;
        }
        setShortcutVisibility(i);
        setShortcutBackup(-1);
    }
}
