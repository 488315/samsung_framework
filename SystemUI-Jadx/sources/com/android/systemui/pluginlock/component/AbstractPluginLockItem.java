package com.android.systemui.pluginlock.component;

import android.content.Context;
import android.provider.Settings;
import com.android.systemui.pluginlock.PluginLockInstanceData;
import com.android.systemui.pluginlock.PluginLockInstanceState;
import com.android.systemui.util.SettingsHelper;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public abstract class AbstractPluginLockItem {
    public final Context mContext;
    public PluginLockInstanceState mInstanceState;
    public final SettingsHelper mSettingsHelper;
    public int mCallbackValue = -1;
    public long mCallbackRegisterTime = 0;

    public AbstractPluginLockItem(Context context, PluginLockInstanceState pluginLockInstanceState, SettingsHelper settingsHelper) {
        this.mContext = context;
        context.getContentResolver();
        this.mInstanceState = pluginLockInstanceState;
        this.mSettingsHelper = settingsHelper;
    }

    public final int getClockState() {
        PluginLockInstanceData.Data.RecoverData recoverData;
        PluginLockInstanceState pluginLockInstanceState = this.mInstanceState;
        if (pluginLockInstanceState == null || (recoverData = pluginLockInstanceState.getRecoverData()) == null) {
            return -1;
        }
        return recoverData.getClockState().intValue();
    }

    public final int getNotificationState() {
        PluginLockInstanceData.Data.RecoverData recoverData;
        PluginLockInstanceState pluginLockInstanceState = this.mInstanceState;
        if (pluginLockInstanceState == null || (recoverData = pluginLockInstanceState.getRecoverData()) == null) {
            return -1;
        }
        return recoverData.getNotificationState().intValue();
    }

    public final int getSettingsInt(int i, String str) {
        SettingsHelper.ItemMap itemMap = this.mSettingsHelper.mItemLists;
        if (itemMap.mMap.containsKey(str)) {
            return itemMap.get(str).getIntValue();
        }
        return i;
    }

    public final void putSettingsSecure(int i, String str) {
        SettingsHelper settingsHelper = this.mSettingsHelper;
        SettingsHelper.ItemMap itemMap = settingsHelper.mItemLists;
        if (itemMap.mMap.containsKey(str)) {
            itemMap.get(str).mIntValue = i;
        }
        Settings.Secure.putInt(settingsHelper.mContext.getContentResolver(), str, i);
    }

    public final void putSettingsSystem(int i, String str) {
        SettingsHelper settingsHelper = this.mSettingsHelper;
        SettingsHelper.ItemMap itemMap = settingsHelper.mItemLists;
        if (itemMap.mMap.containsKey(str)) {
            itemMap.get(str).mIntValue = i;
        }
        Settings.System.putInt(settingsHelper.mContext.getContentResolver(), str, i);
    }

    public final void setClockBackupValue(int i) {
        PluginLockInstanceData.Data.RecoverData recoverData;
        PluginLockInstanceState pluginLockInstanceState = this.mInstanceState;
        if (pluginLockInstanceState != null && (recoverData = pluginLockInstanceState.getRecoverData()) != null) {
            recoverData.setClock(i);
            if (i >= 0) {
                recoverData.setClockState(-3);
            } else {
                recoverData.setClockState(-1);
            }
            this.mInstanceState.updateDb();
        }
    }

    public final void setNotificationBackup(int i, int i2) {
        PluginLockInstanceData.Data.RecoverData recoverData;
        PluginLockInstanceState pluginLockInstanceState = this.mInstanceState;
        if (pluginLockInstanceState != null && (recoverData = pluginLockInstanceState.getRecoverData()) != null) {
            recoverData.setNotificationBackupType(Integer.valueOf(i));
            recoverData.setNotificationBackupVisibility(Integer.valueOf(i2));
            if (i >= 0 && i2 >= 0) {
                recoverData.setNotificationState(-3);
            } else {
                recoverData.setNotificationState(-1);
            }
            this.mInstanceState.updateDb();
        }
    }

    public final void setWallpaperBackupValue() {
        PluginLockInstanceData.Data.RecoverData recoverData;
        PluginLockInstanceState pluginLockInstanceState = this.mInstanceState;
        if (pluginLockInstanceState != null && (recoverData = pluginLockInstanceState.getRecoverData()) != null) {
            recoverData.setWallpaperDynamic(-2);
            recoverData.setWallpaperSource();
            recoverData.setWallpaperType();
            this.mInstanceState.updateDb();
        }
    }

    public final void setWallpaperSourceBackupValue(int i, int i2) {
        PluginLockInstanceData.Data.RecoverData recoverData;
        PluginLockInstanceState pluginLockInstanceState = this.mInstanceState;
        if (pluginLockInstanceState != null && (recoverData = pluginLockInstanceState.getRecoverData()) != null && recoverData.getWallpaperSource(i).intValue() != i2) {
            recoverData.setWallpaperSource(i, i2);
            this.mInstanceState.updateDb();
        }
    }

    public final void setWallpaperTypeBackupValue(int i, int i2) {
        PluginLockInstanceData.Data.RecoverData recoverData;
        PluginLockInstanceState pluginLockInstanceState = this.mInstanceState;
        if (pluginLockInstanceState != null && (recoverData = pluginLockInstanceState.getRecoverData()) != null && recoverData.getWallpaperType(i).intValue() != i2) {
            recoverData.setWallpaperType(i, i2);
            this.mInstanceState.updateDb();
        }
    }
}
