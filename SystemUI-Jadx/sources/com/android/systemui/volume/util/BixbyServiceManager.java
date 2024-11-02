package com.android.systemui.volume.util;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import com.android.systemui.basic.util.LogWrapper;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class BixbyServiceManager {
    public static final Uri VOICE_ENABLE_URI;
    public final ActivityManagerWrapper activityManagerWrapper;
    public final Context context;
    public boolean isBixbyServiceChecked;
    public boolean isBixbyServiceOn;
    public final ReentrantLock lock = new ReentrantLock();
    public final LogWrapper logWrapper;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
        VOICE_ENABLE_URI = Uri.parse("content://com.samsung.android.bixby.agent.settings/bixby_voice_isenable");
    }

    public BixbyServiceManager(Context context, LogWrapper logWrapper, ActivityManagerWrapper activityManagerWrapper) {
        this.context = context;
        this.logWrapper = logWrapper;
        this.activityManagerWrapper = activityManagerWrapper;
    }

    public final boolean checkBixbyServiceEnabled() {
        LogWrapper logWrapper = this.logWrapper;
        ReentrantLock reentrantLock = this.lock;
        try {
            if (reentrantLock.tryLock(3L, TimeUnit.SECONDS)) {
                boolean z = true;
                this.isBixbyServiceChecked = true;
                Cursor query = this.context.getContentResolver().query(VOICE_ENABLE_URI, null, null, null, null);
                if (query != null) {
                    query.moveToFirst();
                    int i = query.getInt(query.getColumnIndex("bixby_voice_isenable"));
                    query.close();
                    if (i == 1) {
                        reentrantLock.unlock();
                        return z;
                    }
                }
                z = false;
                reentrantLock.unlock();
                return z;
            }
            logWrapper.e("BixbyServiceManager", "isBixbyServiceOn() : the waiting time elapsed before the lock was acquired!!");
            this.isBixbyServiceChecked = false;
            return false;
        } catch (Exception e) {
            logWrapper.e("BixbyServiceManager", "isBixbyServiceOn() : exception = " + e);
            return false;
        }
    }

    public final boolean isBixbyServiceForeground() {
        String str;
        ActivityManagerWrapper activityManagerWrapper = this.activityManagerWrapper;
        activityManagerWrapper.getClass();
        SystemServiceExtension.INSTANCE.getClass();
        ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) CollectionsKt___CollectionsKt.firstOrNull((List) ((ActivityManager) activityManagerWrapper.context.getSystemService(ActivityManager.class)).getRunningTasks(1));
        if (runningTaskInfo != null) {
            ComponentName componentName = runningTaskInfo.topActivity;
            if (componentName != null) {
                str = componentName.getPackageName();
            } else {
                str = null;
            }
            return Intrinsics.areEqual("com.samsung.android.bixby.agent", str);
        }
        return false;
    }
}
