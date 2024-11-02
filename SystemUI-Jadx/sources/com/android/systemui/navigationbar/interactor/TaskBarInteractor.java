package com.android.systemui.navigationbar.interactor;

import android.app.ActivityManager;
import android.app.role.RoleManager;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.UserManager;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.app.SemRoleManager;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class TaskBarInteractor {
    public final Handler bgHandler;
    public final BroadcastDispatcher broadcastDispatcher;
    public TaskBarInteractor$addCallback$2 broadcastReceiver;
    public TaskBarInteractor$addCallback$5 callback;
    public final Context context;
    public final IntentFilter intentFilter;
    public boolean isDefaultHome;
    public final LogWrapper logWrapper;
    public TaskBarInteractor$addCallback$7 roleCallback;
    public final RoleManager roleManager;
    public final SettingsHelper settingsHelper;
    public boolean userUnlocked;

    public TaskBarInteractor(Context context, BroadcastDispatcher broadcastDispatcher, Handler handler, SettingsHelper settingsHelper, LogWrapper logWrapper) {
        this.context = context;
        this.broadcastDispatcher = broadcastDispatcher;
        this.bgHandler = handler;
        this.settingsHelper = settingsHelper;
        this.logWrapper = logWrapper;
        IntentFilter intentFilter = new IntentFilter();
        this.intentFilter = intentFilter;
        this.userUnlocked = ((UserManager) context.getSystemService(UserManager.class)).isUserUnlocked(ActivityManager.getCurrentUser());
        this.roleManager = (RoleManager) context.getSystemService(RoleManager.class);
        this.isDefaultHome = updateHomeStatus();
        intentFilter.addAction("android.intent.action.USER_UNLOCKED");
        intentFilter.addAction("android.intent.action.USER_SWITCHED");
        logWrapper.d("TaskBarInteractor", "init userUnlocked=" + this.userUnlocked + " for userid=" + ActivityManager.getCurrentUser());
    }

    public final boolean updateHomeStatus() {
        List roleHolders = new SemRoleManager(this.context).getRoleHolders("android.app.role.HOME");
        if (!roleHolders.isEmpty() && !Intrinsics.areEqual("com.sec.android.app.launcher", roleHolders.get(0))) {
            return false;
        }
        return true;
    }
}
