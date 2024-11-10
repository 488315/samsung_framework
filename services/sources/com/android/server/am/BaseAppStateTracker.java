package com.android.server.am;

import android.app.ActivityManagerInternal;
import android.app.AppOpsManager;
import android.app.role.RoleManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManagerInternal;
import android.media.session.MediaSessionManager;
import android.os.BatteryManagerInternal;
import android.os.BatteryStatsInternal;
import android.os.Handler;
import android.os.ServiceManager;
import android.permission.PermissionManager;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;
import com.android.internal.app.IAppOpsService;
import com.android.server.DeviceIdleInternal;
import com.android.server.LocalServices;
import com.android.server.notification.NotificationManagerInternal;
import com.android.server.pm.UserManagerInternal;
import com.android.server.pm.permission.PermissionManagerServiceInternal;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.util.ArrayList;

/* loaded from: classes.dex */
public abstract class BaseAppStateTracker {
    public final AppRestrictionController mAppRestrictionController;
    public final Handler mBgHandler;
    public final Context mContext;
    public final Injector mInjector;
    public final Object mLock;
    public final ArrayList mStateListeners = new ArrayList();

    /* loaded from: classes.dex */
    public interface StateListener {
        void onStateChange(int i, String str, boolean z, long j, int i2);
    }

    public static int stateIndexToType(int i) {
        return 1 << i;
    }

    public void dumpAsProto(ProtoOutputStream protoOutputStream, int i) {
    }

    public byte[] getTrackerInfoForStatsd(int i) {
        return null;
    }

    public abstract int getType();

    public void onBackgroundRestrictionChanged(int i, String str, boolean z) {
    }

    public void onLockedBootCompleted() {
    }

    public void onUidAdded(int i) {
    }

    public void onUidGone(int i) {
    }

    public void onUidProcStateChanged(int i, int i2) {
    }

    public void onUidRemoved(int i) {
    }

    public void onUserAdded(int i) {
    }

    public void onUserInteractionStarted(String str, int i) {
    }

    public void onUserRemoved(int i) {
    }

    public void onUserStarted(int i) {
    }

    public void onUserStopped(int i) {
    }

    public BaseAppStateTracker(Context context, AppRestrictionController appRestrictionController, Constructor constructor, Object obj) {
        Injector injector;
        this.mContext = context;
        this.mAppRestrictionController = appRestrictionController;
        this.mBgHandler = appRestrictionController.getBackgroundHandler();
        this.mLock = appRestrictionController.getLock();
        if (constructor == null) {
            this.mInjector = new Injector();
            return;
        }
        try {
            injector = (Injector) constructor.newInstance(obj);
        } catch (Exception e) {
            Slog.w("ActivityManager", "Unable to instantiate " + constructor, e);
            injector = null;
        }
        this.mInjector = injector == null ? new Injector() : injector;
    }

    public static int stateTypeToIndex(int i) {
        return Integer.numberOfTrailingZeros(i);
    }

    public static String stateTypesToString(int i) {
        StringBuilder sb = new StringBuilder("[");
        int highestOneBit = Integer.highestOneBit(i);
        boolean z = false;
        while (highestOneBit != 0) {
            if (z) {
                sb.append('|');
            }
            z = true;
            if (highestOneBit == 1) {
                sb.append("MEDIA_SESSION");
            } else if (highestOneBit == 2) {
                sb.append("FGS_MEDIA_PLAYBACK");
            } else if (highestOneBit == 4) {
                sb.append("FGS_LOCATION");
            } else if (highestOneBit == 8) {
                sb.append("FGS_NOTIFICATION");
            } else if (highestOneBit == 16) {
                sb.append("PERMISSION");
            } else {
                return "[UNKNOWN(" + Integer.toHexString(i) + ")]";
            }
            i &= ~highestOneBit;
            highestOneBit = Integer.highestOneBit(i);
        }
        sb.append("]");
        return sb.toString();
    }

    public void registerStateListener(StateListener stateListener) {
        synchronized (this.mLock) {
            this.mStateListeners.add(stateListener);
        }
    }

    public void notifyListenersOnStateChange(int i, String str, boolean z, long j, int i2) {
        synchronized (this.mLock) {
            int size = this.mStateListeners.size();
            for (int i3 = 0; i3 < size; i3++) {
                ((StateListener) this.mStateListeners.get(i3)).onStateChange(i, str, z, j, i2);
            }
        }
    }

    public BaseAppStatePolicy getPolicy() {
        return this.mInjector.getPolicy();
    }

    public void onSystemReady() {
        this.mInjector.onSystemReady();
    }

    public void onPropertiesChanged(String str) {
        getPolicy().onPropertiesChanged(str);
    }

    public void dump(PrintWriter printWriter, String str) {
        this.mInjector.getPolicy().dump(printWriter, "  " + str);
    }

    /* loaded from: classes.dex */
    public class Injector {
        public ActivityManagerInternal mActivityManagerInternal;
        public AppOpsManager mAppOpsManager;
        public BaseAppStatePolicy mAppStatePolicy;
        public BatteryManagerInternal mBatteryManagerInternal;
        public BatteryStatsInternal mBatteryStatsInternal;
        public DeviceIdleInternal mDeviceIdleInternal;
        public IAppOpsService mIAppOpsService;
        public MediaSessionManager mMediaSessionManager;
        public NotificationManagerInternal mNotificationManagerInternal;
        public PackageManager mPackageManager;
        public PackageManagerInternal mPackageManagerInternal;
        public PermissionManager mPermissionManager;
        public PermissionManagerServiceInternal mPermissionManagerServiceInternal;
        public RoleManager mRoleManager;
        public UserManagerInternal mUserManagerInternal;

        public void setPolicy(BaseAppStatePolicy baseAppStatePolicy) {
            this.mAppStatePolicy = baseAppStatePolicy;
        }

        public void onSystemReady() {
            this.mActivityManagerInternal = (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
            this.mBatteryManagerInternal = (BatteryManagerInternal) LocalServices.getService(BatteryManagerInternal.class);
            this.mBatteryStatsInternal = (BatteryStatsInternal) LocalServices.getService(BatteryStatsInternal.class);
            this.mDeviceIdleInternal = (DeviceIdleInternal) LocalServices.getService(DeviceIdleInternal.class);
            this.mUserManagerInternal = (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
            this.mPackageManagerInternal = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
            this.mPermissionManagerServiceInternal = (PermissionManagerServiceInternal) LocalServices.getService(PermissionManagerServiceInternal.class);
            Context context = this.mAppStatePolicy.mTracker.mContext;
            this.mPackageManager = context.getPackageManager();
            this.mAppOpsManager = (AppOpsManager) context.getSystemService(AppOpsManager.class);
            this.mMediaSessionManager = (MediaSessionManager) context.getSystemService(MediaSessionManager.class);
            this.mPermissionManager = (PermissionManager) context.getSystemService(PermissionManager.class);
            this.mRoleManager = (RoleManager) context.getSystemService(RoleManager.class);
            this.mNotificationManagerInternal = (NotificationManagerInternal) LocalServices.getService(NotificationManagerInternal.class);
            this.mIAppOpsService = IAppOpsService.Stub.asInterface(ServiceManager.getService("appops"));
            getPolicy().onSystemReady();
        }

        public ActivityManagerInternal getActivityManagerInternal() {
            return this.mActivityManagerInternal;
        }

        public BatteryManagerInternal getBatteryManagerInternal() {
            return this.mBatteryManagerInternal;
        }

        public BatteryStatsInternal getBatteryStatsInternal() {
            return this.mBatteryStatsInternal;
        }

        public BaseAppStatePolicy getPolicy() {
            return this.mAppStatePolicy;
        }

        public UserManagerInternal getUserManagerInternal() {
            return this.mUserManagerInternal;
        }

        public long currentTimeMillis() {
            return System.currentTimeMillis();
        }

        public PackageManager getPackageManager() {
            return this.mPackageManager;
        }

        public PackageManagerInternal getPackageManagerInternal() {
            return this.mPackageManagerInternal;
        }

        public PermissionManager getPermissionManager() {
            return this.mPermissionManager;
        }

        public PermissionManagerServiceInternal getPermissionManagerServiceInternal() {
            return this.mPermissionManagerServiceInternal;
        }

        public MediaSessionManager getMediaSessionManager() {
            return this.mMediaSessionManager;
        }

        public long getServiceStartForegroundTimeout() {
            return this.mActivityManagerInternal.getServiceStartForegroundTimeout();
        }

        public IAppOpsService getIAppOpsService() {
            return this.mIAppOpsService;
        }
    }
}
