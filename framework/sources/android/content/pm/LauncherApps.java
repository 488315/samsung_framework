package android.content.pm;

import android.Manifest;
import android.annotation.SystemApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetProviderInfo;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.LocusId;
import android.content.pm.ILauncherApps;
import android.content.pm.IOnAppsChangedListener;
import android.content.pm.IPinItemRequest;
import android.content.pm.IShortcutChangeCallback;
import android.content.pm.LauncherApps;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.multiuser.Flags;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Pair;
import android.window.IDumpCallback;
import com.android.internal.infra.AndroidFuture;
import com.android.internal.util.function.QuadConsumer;
import com.android.internal.util.function.pooled.PooledLambda;
import com.samsung.android.knox.KnoxHelper;
import com.samsung.android.knox.SemPersonaManager;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.function.Function;

/* loaded from: classes.dex */
public class LauncherApps {
    public static final String ACTION_CONFIRM_PIN_APPWIDGET = "android.content.pm.action.CONFIRM_PIN_APPWIDGET";
    public static final String ACTION_CONFIRM_PIN_SHORTCUT = "android.content.pm.action.CONFIRM_PIN_SHORTCUT";
    static final boolean DEBUG = false;
    public static final String EXTRA_PIN_ITEM_REQUEST = "android.content.pm.extra.PIN_ITEM_REQUEST";
    public static final int FLAG_CACHE_BUBBLE_SHORTCUTS = 1;
    public static final int FLAG_CACHE_NOTIFICATION_SHORTCUTS = 0;
    public static final int FLAG_CACHE_PEOPLE_TILE_SHORTCUTS = 2;
    static final String TAG = "LauncherApps";
    private final IOnAppsChangedListener.Stub mAppsChangedListener;
    private final List<CallbackMessageHandler> mCallbacks;
    private final Context mContext;
    private final List<PackageInstaller.SessionCallbackDelegate> mDelegates;
    private final PackageManager mPm;
    private final ILauncherApps mService;
    private final Map<ShortcutChangeCallback, Pair<Executor, IShortcutChangeCallback>> mShortcutChangeCallbacks;
    private final SemPersonaManager mSpm;
    private final UserManager mUserManager;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ShortcutCacheFlags {
    }

    public static abstract class Callback {
        public abstract void onPackageAdded(String str, UserHandle userHandle);

        public abstract void onPackageChanged(String str, UserHandle userHandle);

        public abstract void onPackageRemoved(String str, UserHandle userHandle);

        public abstract void onPackagesAvailable(String[] strArr, UserHandle userHandle, boolean z);

        public abstract void onPackagesUnavailable(String[] strArr, UserHandle userHandle, boolean z);

        public void onPackagesSuspended(String[] packageNames, UserHandle user) {
        }

        @Deprecated
        public void onPackagesSuspended(String[] packageNames, UserHandle user, Bundle launcherExtras) {
            onPackagesSuspended(packageNames, user);
        }

        public void onPackagesUnsuspended(String[] packageNames, UserHandle user) {
        }

        public void onShortcutsChanged(String packageName, List<ShortcutInfo> shortcuts, UserHandle user) {
        }

        public void onPackageLoadingProgressChanged(String packageName, UserHandle user, float progress) {
        }
    }

    public static class ShortcutQuery {

        @Deprecated
        public static final int FLAG_GET_ALL_KINDS = 27;

        @Deprecated
        public static final int FLAG_GET_DYNAMIC = 1;
        public static final int FLAG_GET_KEY_FIELDS_ONLY = 4;

        @Deprecated
        public static final int FLAG_GET_MANIFEST = 8;

        @SystemApi
        public static final int FLAG_GET_PERSISTED_DATA = 4096;

        @SystemApi
        public static final int FLAG_GET_PERSONS_DATA = 2048;

        @Deprecated
        public static final int FLAG_GET_PINNED = 2;
        public static final int FLAG_MATCH_ALL_KINDS = 27;
        public static final int FLAG_MATCH_ALL_KINDS_WITH_ALL_PINNED = 1051;
        public static final int FLAG_MATCH_CACHED = 16;
        public static final int FLAG_MATCH_DYNAMIC = 1;
        public static final int FLAG_MATCH_MANIFEST = 8;
        public static final int FLAG_MATCH_PINNED = 2;
        public static final int FLAG_MATCH_PINNED_BY_ANY_LAUNCHER = 1024;
        ComponentName mActivity;
        long mChangedSince;
        List<LocusId> mLocusIds;
        String mPackage;
        int mQueryFlags;
        List<String> mShortcutIds;

        @Retention(RetentionPolicy.SOURCE)
        public @interface QueryFlags {
        }

        public ShortcutQuery setChangedSince(long changedSince) {
            this.mChangedSince = changedSince;
            return this;
        }

        public ShortcutQuery setPackage(String packageName) {
            this.mPackage = packageName;
            return this;
        }

        public ShortcutQuery setShortcutIds(List<String> shortcutIds) {
            this.mShortcutIds = shortcutIds;
            return this;
        }

        public ShortcutQuery setLocusIds(List<LocusId> locusIds) {
            this.mLocusIds = locusIds;
            return this;
        }

        public ShortcutQuery setActivity(ComponentName activity) {
            this.mActivity = activity;
            return this;
        }

        public ShortcutQuery setQueryFlags(int queryFlags) {
            this.mQueryFlags = queryFlags;
            return this;
        }
    }

    public interface ShortcutChangeCallback {
        default void onShortcutsAddedOrUpdated(String packageName, List<ShortcutInfo> shortcuts, UserHandle user) {
        }

        default void onShortcutsRemoved(String packageName, List<ShortcutInfo> shortcuts, UserHandle user) {
        }
    }

    private static class ShortcutChangeCallbackProxy extends IShortcutChangeCallback.Stub {
        private final WeakReference<Pair<Executor, ShortcutChangeCallback>> mRemoteReferences;

        ShortcutChangeCallbackProxy(Executor executor, ShortcutChangeCallback callback) {
            this.mRemoteReferences = new WeakReference<>(new Pair(executor, callback));
        }

        @Override // android.content.pm.IShortcutChangeCallback
        public void onShortcutsAddedOrUpdated(String packageName, List<ShortcutInfo> shortcuts, UserHandle user) {
            Pair<Executor, ShortcutChangeCallback> remoteReferences = this.mRemoteReferences.get();
            if (remoteReferences == null) {
                return;
            }
            Executor executor = remoteReferences.first;
            ShortcutChangeCallback callback = remoteReferences.second;
            executor.execute(PooledLambda.obtainRunnable(new QuadConsumer() { // from class: android.content.pm.LauncherApps$ShortcutChangeCallbackProxy$$ExternalSyntheticLambda1
                @Override // com.android.internal.util.function.QuadConsumer
                public final void accept(Object obj, Object obj2, Object obj3, Object obj4) {
                    ((LauncherApps.ShortcutChangeCallback) obj).onShortcutsAddedOrUpdated((String) obj2, (List) obj3, (UserHandle) obj4);
                }
            }, callback, packageName, shortcuts, user).recycleOnUse());
        }

        @Override // android.content.pm.IShortcutChangeCallback
        public void onShortcutsRemoved(String packageName, List<ShortcutInfo> shortcuts, UserHandle user) {
            Pair<Executor, ShortcutChangeCallback> remoteReferences = this.mRemoteReferences.get();
            if (remoteReferences == null) {
                return;
            }
            Executor executor = remoteReferences.first;
            ShortcutChangeCallback callback = remoteReferences.second;
            executor.execute(PooledLambda.obtainRunnable(new QuadConsumer() { // from class: android.content.pm.LauncherApps$ShortcutChangeCallbackProxy$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.function.QuadConsumer
                public final void accept(Object obj, Object obj2, Object obj3, Object obj4) {
                    ((LauncherApps.ShortcutChangeCallback) obj).onShortcutsRemoved((String) obj2, (List) obj3, (UserHandle) obj4);
                }
            }, callback, packageName, shortcuts, user).recycleOnUse());
        }
    }

    public LauncherApps(Context context, ILauncherApps service) {
        this.mCallbacks = new ArrayList();
        this.mDelegates = new ArrayList();
        this.mShortcutChangeCallbacks = new HashMap();
        this.mAppsChangedListener = new IOnAppsChangedListener.Stub() { // from class: android.content.pm.LauncherApps.1
            @Override // android.content.pm.IOnAppsChangedListener
            public void onPackageRemoved(UserHandle user, String packageName) throws RemoteException {
                Log.d(LauncherApps.TAG, "onPackageRemoved " + user.getIdentifier() + "," + packageName);
                synchronized (LauncherApps.this) {
                    for (CallbackMessageHandler callback : LauncherApps.this.mCallbacks) {
                        callback.postOnPackageRemoved(packageName, user);
                    }
                }
            }

            @Override // android.content.pm.IOnAppsChangedListener
            public void onPackageChanged(UserHandle user, String packageName) throws RemoteException {
                Log.d(LauncherApps.TAG, "onPackageChanged " + user.getIdentifier() + "," + packageName);
                synchronized (LauncherApps.this) {
                    for (CallbackMessageHandler callback : LauncherApps.this.mCallbacks) {
                        callback.postOnPackageChanged(packageName, user);
                    }
                }
            }

            @Override // android.content.pm.IOnAppsChangedListener
            public void onPackageAdded(UserHandle user, String packageName) throws RemoteException {
                Log.d(LauncherApps.TAG, "onPackageAdded " + user.getIdentifier() + "," + packageName);
                synchronized (LauncherApps.this) {
                    for (CallbackMessageHandler callback : LauncherApps.this.mCallbacks) {
                        callback.postOnPackageAdded(packageName, user);
                    }
                }
            }

            @Override // android.content.pm.IOnAppsChangedListener
            public void onPackagesAvailable(UserHandle user, String[] packageNames, boolean replacing) throws RemoteException {
                synchronized (LauncherApps.this) {
                    for (CallbackMessageHandler callback : LauncherApps.this.mCallbacks) {
                        callback.postOnPackagesAvailable(packageNames, user, replacing);
                    }
                }
            }

            @Override // android.content.pm.IOnAppsChangedListener
            public void onPackagesUnavailable(UserHandle user, String[] packageNames, boolean replacing) throws RemoteException {
                synchronized (LauncherApps.this) {
                    for (CallbackMessageHandler callback : LauncherApps.this.mCallbacks) {
                        callback.postOnPackagesUnavailable(packageNames, user, replacing);
                    }
                }
            }

            @Override // android.content.pm.IOnAppsChangedListener
            public void onPackagesSuspended(UserHandle user, String[] packageNames, Bundle launcherExtras) throws RemoteException {
                synchronized (LauncherApps.this) {
                    for (CallbackMessageHandler callback : LauncherApps.this.mCallbacks) {
                        callback.postOnPackagesSuspended(packageNames, launcherExtras, user);
                    }
                }
            }

            @Override // android.content.pm.IOnAppsChangedListener
            public void onPackagesUnsuspended(UserHandle user, String[] packageNames) throws RemoteException {
                synchronized (LauncherApps.this) {
                    for (CallbackMessageHandler callback : LauncherApps.this.mCallbacks) {
                        callback.postOnPackagesUnsuspended(packageNames, user);
                    }
                }
            }

            @Override // android.content.pm.IOnAppsChangedListener
            public void onShortcutChanged(UserHandle user, String packageName, ParceledListSlice shortcuts) {
                List<ShortcutInfo> list = shortcuts.getList();
                synchronized (LauncherApps.this) {
                    for (CallbackMessageHandler callback : LauncherApps.this.mCallbacks) {
                        callback.postOnShortcutChanged(packageName, user, list);
                    }
                }
            }

            @Override // android.content.pm.IOnAppsChangedListener
            public void onPackageLoadingProgressChanged(UserHandle user, String packageName, float progress) {
                synchronized (LauncherApps.this) {
                    for (CallbackMessageHandler callback : LauncherApps.this.mCallbacks) {
                        callback.postOnPackageLoadingProgressChanged(user, packageName, progress);
                    }
                }
            }
        };
        this.mContext = context;
        this.mService = service;
        this.mPm = context.getPackageManager();
        this.mSpm = (SemPersonaManager) context.getSystemService(SemPersonaManager.class);
        this.mUserManager = (UserManager) context.getSystemService(UserManager.class);
    }

    public LauncherApps(Context context) {
        this(context, ILauncherApps.Stub.asInterface(ServiceManager.getService(Context.LAUNCHER_APPS_SERVICE)));
    }

    private void logErrorForInvalidProfileAccess(UserHandle target) {
        if (UserHandle.myUserId() != target.getIdentifier() && this.mUserManager.isManagedProfile() && this.mContext.checkSelfPermission(Manifest.permission.INTERACT_ACROSS_USERS_FULL) != 0) {
            Log.w(TAG, "Accessing other profiles/users from managed profile is no longer allowed.");
        }
    }

    public List<UserHandle> getProfiles() {
        if (this.mUserManager.isManagedProfile() || (Flags.enableLauncherAppsHiddenProfileChecks() && com.android.internal.hidden_from_bootclasspath.android.os.Flags.allowPrivateProfile() && Flags.enablePrivateSpaceFeatures() && this.mUserManager.isPrivateProfile())) {
            List result = new ArrayList(1);
            result.add(Process.myUserHandle());
            return result;
        }
        if (Flags.enableLauncherAppsHiddenProfileChecks()) {
            try {
                return this.mService.getUserProfiles();
            } catch (RemoteException re) {
                throw re.rethrowFromSystemServer();
            }
        }
        return this.mUserManager.getUserProfiles();
    }

    public List<LauncherActivityInfo> getActivityList(String packageName, UserHandle user) {
        List<LauncherActivityInfo> knoxActivityInfoList;
        logErrorForInvalidProfileAccess(user);
        if (this.mContext.getUserId() != user.getIdentifier() && (knoxActivityInfoList = KnoxHelper.getActivityList(this.mContext, this.mService, packageName, user)) != null) {
            if (isAppSeparationPresent(user.getIdentifier())) {
                return updateLauncherInfoListWithAppSeparation(knoxActivityInfoList);
            }
            return knoxActivityInfoList;
        }
        try {
            return convertToActivityList(this.mService.getLauncherActivities(this.mContext.getPackageName(), packageName, user), user);
        } catch (RemoteException re) {
            throw re.rethrowFromSystemServer();
        }
    }

    public static LauncherActivityInfo getLauncherActivityInfo(Context context, UserHandle user, LauncherActivityInfoInternal internal) {
        return new LauncherActivityInfo(context, internal);
    }

    public PendingIntent getMainActivityLaunchIntent(ComponentName component, Bundle startActivityOptions, UserHandle user) {
        logErrorForInvalidProfileAccess(user);
        try {
            return this.mService.getActivityLaunchIntent(this.mContext.getPackageName(), component, user);
        } catch (RemoteException re) {
            throw re.rethrowFromSystemServer();
        }
    }

    public final LauncherUserInfo getLauncherUserInfo(UserHandle userHandle) {
        try {
            return this.mService.getLauncherUserInfo(userHandle);
        } catch (RemoteException re) {
            throw re.rethrowFromSystemServer();
        }
    }

    public IntentSender getAppMarketActivityIntent(String packageName, UserHandle user) {
        try {
            return this.mService.getAppMarketActivityIntent(this.mContext.getPackageName(), packageName, user);
        } catch (RemoteException re) {
            throw re.rethrowFromSystemServer();
        }
    }

    public List<String> getPreInstalledSystemPackages(UserHandle userHandle) {
        try {
            return this.mService.getPreInstalledSystemPackages(userHandle);
        } catch (RemoteException re) {
            throw re.rethrowFromSystemServer();
        }
    }

    public IntentSender getPrivateSpaceSettingsIntent() {
        try {
            return this.mService.getPrivateSpaceSettingsIntent();
        } catch (RemoteException re) {
            throw re.rethrowFromSystemServer();
        }
    }

    public LauncherActivityInfo resolveActivity(Intent intent, UserHandle user) {
        logErrorForInvalidProfileAccess(user);
        try {
            LauncherActivityInfoInternal ai = this.mService.resolveLauncherActivityInternal(this.mContext.getPackageName(), intent.getComponent(), user);
            if (ai == null) {
                return null;
            }
            return new LauncherActivityInfo(this.mContext, ai);
        } catch (RemoteException re) {
            throw re.rethrowFromSystemServer();
        }
    }

    public Map<String, LauncherActivityInfo> getActivityOverrides() {
        Map<String, LauncherActivityInfo> activityOverrides = new ArrayMap<>();
        try {
            Map<String, LauncherActivityInfoInternal> activityOverridesInternal = this.mService.getActivityOverrides(this.mContext.getPackageName(), this.mContext.getUserId());
            for (Map.Entry<String, LauncherActivityInfoInternal> packageToOverride : activityOverridesInternal.entrySet()) {
                activityOverrides.put(packageToOverride.getKey(), new LauncherActivityInfo(this.mContext, packageToOverride.getValue()));
            }
            return activityOverrides;
        } catch (RemoteException re) {
            throw re.rethrowFromSystemServer();
        }
    }

    public void startMainActivity(ComponentName component, UserHandle user, Rect sourceBounds, Bundle opts) {
        logErrorForInvalidProfileAccess(user);
        try {
            this.mService.startActivityAsUser(this.mContext.getIApplicationThread(), this.mContext.getPackageName(), this.mContext.getAttributionTag(), component, sourceBounds, opts, user);
        } catch (RemoteException re) {
            throw re.rethrowFromSystemServer();
        }
    }

    public void startPackageInstallerSessionDetailsActivity(PackageInstaller.SessionInfo sessionInfo, Rect sourceBounds, Bundle opts) {
        try {
            this.mService.startSessionDetailsActivityAsUser(this.mContext.getIApplicationThread(), this.mContext.getPackageName(), this.mContext.getAttributionTag(), sessionInfo, sourceBounds, opts, sessionInfo.getUser());
        } catch (RemoteException re) {
            throw re.rethrowFromSystemServer();
        }
    }

    public void startAppDetailsActivity(ComponentName component, UserHandle user, Rect sourceBounds, Bundle opts) {
        logErrorForInvalidProfileAccess(user);
        try {
            this.mService.showAppDetailsAsUser(this.mContext.getIApplicationThread(), this.mContext.getPackageName(), this.mContext.getAttributionTag(), component, sourceBounds, opts, user);
        } catch (RemoteException re) {
            throw re.rethrowFromSystemServer();
        }
    }

    public PendingIntent getShortcutIntent(String packageName, String shortcutId, Bundle opts, UserHandle user) {
        logErrorForInvalidProfileAccess(user);
        try {
            return this.mService.getShortcutIntent(this.mContext.getPackageName(), packageName, shortcutId, null, user);
        } catch (RemoteException re) {
            throw re.rethrowFromSystemServer();
        }
    }

    public List<LauncherActivityInfo> getShortcutConfigActivityList(String packageName, UserHandle user) {
        logErrorForInvalidProfileAccess(user);
        try {
            return convertToActivityList(this.mService.getShortcutConfigActivities(this.mContext.getPackageName(), packageName, user), user);
        } catch (RemoteException re) {
            throw re.rethrowFromSystemServer();
        }
    }

    private List<LauncherActivityInfo> convertToActivityList(ParceledListSlice<LauncherActivityInfoInternal> internals, UserHandle user) {
        if (internals == null || internals.getList().isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        ArrayList<LauncherActivityInfo> lais = new ArrayList<>();
        for (LauncherActivityInfoInternal internal : internals.getList()) {
            LauncherActivityInfo lai = new LauncherActivityInfo(this.mContext, internal);
            lais.add(lai);
        }
        if (isAppSeparationPresent(user.getIdentifier())) {
            return updateLauncherInfoListWithAppSeparation(lais);
        }
        return lais;
    }

    public IntentSender getShortcutConfigActivityIntent(LauncherActivityInfo info) {
        try {
            return this.mService.getShortcutConfigActivityIntent(this.mContext.getPackageName(), info.getComponentName(), info.getUser());
        } catch (RemoteException re) {
            throw re.rethrowFromSystemServer();
        }
    }

    private boolean isAppSeparationPresent(int userId) {
        if (SemPersonaManager.isDoEnabled(userId) && this.mSpm != null && this.mSpm.isAppSeparationPresent()) {
            return true;
        }
        return false;
    }

    private List<LauncherActivityInfo> updateLauncherInfoListWithAppSeparation(List<LauncherActivityInfo> launcherActivityInfoList) {
        Set<String> separatedAppsList = new HashSet<>(this.mSpm.getSeparatedAppsList());
        List<LauncherActivityInfo> launcherInfoListWithAppSeparation = new ArrayList<>();
        for (LauncherActivityInfo info : launcherActivityInfoList) {
            if (!separatedAppsList.contains(info.getActivityInfo().packageName)) {
                launcherInfoListWithAppSeparation.add(info);
            }
        }
        return launcherInfoListWithAppSeparation;
    }

    public boolean isPackageEnabled(String packageName, UserHandle user) {
        logErrorForInvalidProfileAccess(user);
        try {
            return this.mService.isPackageEnabled(this.mContext.getPackageName(), packageName, user);
        } catch (RemoteException re) {
            throw re.rethrowFromSystemServer();
        }
    }

    public Bundle getSuspendedPackageLauncherExtras(String packageName, UserHandle user) {
        logErrorForInvalidProfileAccess(user);
        try {
            return this.mService.getSuspendedPackageLauncherExtras(packageName, user);
        } catch (RemoteException re) {
            throw re.rethrowFromSystemServer();
        }
    }

    public boolean shouldHideFromSuggestions(String packageName, UserHandle user) {
        Objects.requireNonNull(packageName, "packageName");
        Objects.requireNonNull(user, "user");
        try {
            return this.mService.shouldHideFromSuggestions(packageName, user);
        } catch (RemoteException re) {
            throw re.rethrowFromSystemServer();
        }
    }

    public ApplicationInfo getApplicationInfo(String packageName, int flags, UserHandle user) throws PackageManager.NameNotFoundException {
        Objects.requireNonNull(packageName, "packageName");
        Objects.requireNonNull(user, "user");
        logErrorForInvalidProfileAccess(user);
        try {
            ApplicationInfo ai = this.mService.getApplicationInfo(this.mContext.getPackageName(), packageName, flags, user);
            if (ai == null) {
                throw new PackageManager.NameNotFoundException("Package " + packageName + " not found for user " + user.getIdentifier());
            }
            return ai;
        } catch (RemoteException re) {
            throw re.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public AppUsageLimit getAppUsageLimit(String packageName, UserHandle user) {
        try {
            return this.mService.getAppUsageLimit(this.mContext.getPackageName(), packageName, user);
        } catch (RemoteException re) {
            throw re.rethrowFromSystemServer();
        }
    }

    public boolean isActivityEnabled(ComponentName component, UserHandle user) {
        logErrorForInvalidProfileAccess(user);
        try {
            if (isAppSeparationPresent(user.getIdentifier()) && this.mSpm.isInSeparatedAppsOnly(component.getPackageName())) {
                return false;
            }
            return this.mService.isActivityEnabled(this.mContext.getPackageName(), component, user);
        } catch (RemoteException re) {
            throw re.rethrowFromSystemServer();
        }
    }

    public boolean hasShortcutHostPermission() {
        try {
            return this.mService.hasShortcutHostPermission(this.mContext.getPackageName());
        } catch (RemoteException re) {
            throw re.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<ShortcutInfo> maybeUpdateDisabledMessage(List<ShortcutInfo> shortcuts) {
        if (shortcuts == null) {
            return null;
        }
        for (int i = shortcuts.size() - 1; i >= 0; i--) {
            ShortcutInfo si = shortcuts.get(i);
            String message = ShortcutInfo.getDisabledReasonForRestoreIssue(this.mContext, si.getDisabledReason());
            if (message != null) {
                si.setDisabledMessage(message);
            }
        }
        return shortcuts;
    }

    public void registerDumpCallback(IDumpCallback cb) {
        try {
            this.mService.registerDumpCallback(cb);
        } catch (RemoteException e) {
            e.rethrowAsRuntimeException();
        }
    }

    public void saveViewCaptureData() {
        try {
            this.mService.saveViewCaptureData();
        } catch (RemoteException e) {
            e.rethrowAsRuntimeException();
        }
    }

    public void unRegisterDumpCallback(IDumpCallback cb) {
        try {
            this.mService.unRegisterDumpCallback(cb);
        } catch (RemoteException e) {
            e.rethrowAsRuntimeException();
        }
    }

    public List<ShortcutInfo> getShortcuts(ShortcutQuery query, UserHandle user) {
        logErrorForInvalidProfileAccess(user);
        try {
            if ((query.mQueryFlags & 4096) != 0) {
                return getShortcutsBlocked(query, user);
            }
            return maybeUpdateDisabledMessage(this.mService.getShortcuts(this.mContext.getPackageName(), new ShortcutQueryWrapper(query), user).getList());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private List<ShortcutInfo> getShortcutsBlocked(ShortcutQuery query, UserHandle user) {
        logErrorForInvalidProfileAccess(user);
        AndroidFuture<List<ShortcutInfo>> future = new AndroidFuture<>();
        future.thenApply((Function<? super List<ShortcutInfo>, ? extends U>) new Function() { // from class: android.content.pm.LauncherApps$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                List maybeUpdateDisabledMessage;
                maybeUpdateDisabledMessage = LauncherApps.this.maybeUpdateDisabledMessage((List) obj);
                return maybeUpdateDisabledMessage;
            }
        });
        try {
            this.mService.getShortcutsAsync(this.mContext.getPackageName(), new ShortcutQueryWrapper(query), user, future);
            return future.get();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (InterruptedException | ExecutionException e2) {
            throw new RuntimeException(e2);
        }
    }

    @Deprecated
    public List<ShortcutInfo> getShortcutInfo(String packageName, List<String> ids, UserHandle user) {
        ShortcutQuery q = new ShortcutQuery();
        q.setPackage(packageName);
        q.setShortcutIds(ids);
        q.setQueryFlags(27);
        return getShortcuts(q, user);
    }

    public void pinShortcuts(String packageName, List<String> shortcutIds, UserHandle user) {
        logErrorForInvalidProfileAccess(user);
        try {
            this.mService.pinShortcuts(this.mContext.getPackageName(), packageName, shortcutIds, user);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void cacheShortcuts(String packageName, List<String> shortcutIds, UserHandle user, int cacheFlags) {
        logErrorForInvalidProfileAccess(user);
        try {
            this.mService.cacheShortcuts(this.mContext.getPackageName(), packageName, shortcutIds, user, cacheFlags);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void uncacheShortcuts(String packageName, List<String> shortcutIds, UserHandle user, int cacheFlags) {
        logErrorForInvalidProfileAccess(user);
        try {
            this.mService.uncacheShortcuts(this.mContext.getPackageName(), packageName, shortcutIds, user, cacheFlags);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public int getShortcutIconResId(ShortcutInfo shortcut) {
        return shortcut.getIconResourceId();
    }

    @Deprecated
    public int getShortcutIconResId(String packageName, String shortcutId, UserHandle user) {
        ShortcutQuery q = new ShortcutQuery();
        q.setPackage(packageName);
        q.setShortcutIds(Arrays.asList(shortcutId));
        q.setQueryFlags(27);
        List<ShortcutInfo> shortcuts = getShortcuts(q, user);
        if (shortcuts.size() > 0) {
            return shortcuts.get(0).getIconResourceId();
        }
        return 0;
    }

    public ParcelFileDescriptor getShortcutIconFd(ShortcutInfo shortcut) {
        return getShortcutIconFd(shortcut.getPackage(), shortcut.getId(), shortcut.getUserId());
    }

    public ParcelFileDescriptor getShortcutIconFd(String packageName, String shortcutId, UserHandle user) {
        return getShortcutIconFd(packageName, shortcutId, user.getIdentifier());
    }

    private ParcelFileDescriptor getShortcutIconFd(String packageName, String shortcutId, int userId) {
        try {
            return this.mService.getShortcutIconFd(this.mContext.getPackageName(), packageName, shortcutId, userId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public ParcelFileDescriptor getUriShortcutIconFd(ShortcutInfo shortcut) {
        return getUriShortcutIconFd(shortcut.getPackage(), shortcut.getId(), shortcut.getUserId());
    }

    private ParcelFileDescriptor getUriShortcutIconFd(String packageName, String shortcutId, int userId) {
        String uri = getShortcutIconUri(packageName, shortcutId, userId);
        if (uri == null) {
            return null;
        }
        try {
            return this.mContext.getContentResolver().openFileDescriptor(Uri.parse(uri), "r");
        } catch (Exception e) {
            Log.e(TAG, "Failed to open icon file: " + uri, e);
            return null;
        }
    }

    private String getShortcutIconUri(String packageName, String shortcutId, int userId) {
        try {
            String uri = this.mService.getShortcutIconUri(this.mContext.getPackageName(), packageName, shortcutId, userId);
            return uri;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Drawable getShortcutIconDrawable(ShortcutInfo shortcut, int density) {
        if (shortcut.hasIconFile()) {
            ParcelFileDescriptor pfd = getShortcutIconFd(shortcut);
            return loadDrawableFromFileDescriptor(pfd, shortcut.hasAdaptiveBitmap());
        }
        if (shortcut.hasIconUri()) {
            ParcelFileDescriptor pfd2 = getUriShortcutIconFd(shortcut);
            return loadDrawableFromFileDescriptor(pfd2, shortcut.hasAdaptiveBitmap());
        }
        if (shortcut.hasIconResource()) {
            return loadDrawableResourceFromPackage(shortcut.getPackage(), shortcut.getIconResourceId(), shortcut.getUserHandle(), density);
        }
        if (shortcut.getIcon() == null) {
            return null;
        }
        Icon icon = shortcut.getIcon();
        switch (icon.getType()) {
            case 1:
            case 5:
                return icon.loadDrawable(this.mContext);
            case 2:
                return loadDrawableResourceFromPackage(shortcut.getPackage(), icon.getResId(), shortcut.getUserHandle(), density);
            case 3:
            case 4:
            default:
                return null;
        }
    }

    private Drawable loadDrawableFromFileDescriptor(ParcelFileDescriptor pfd, boolean adaptive) {
        if (pfd == null) {
            return null;
        }
        try {
            Bitmap bmp = BitmapFactory.decodeFileDescriptor(pfd.getFileDescriptor());
            if (bmp == null) {
                try {
                    pfd.close();
                } catch (IOException e) {
                }
                return null;
            }
            BitmapDrawable dr = new BitmapDrawable(this.mContext.getResources(), bmp);
            if (adaptive) {
                return new AdaptiveIconDrawable((Drawable) null, dr);
            }
            try {
                pfd.close();
            } catch (IOException e2) {
            }
            return dr;
        } finally {
            try {
                pfd.close();
            } catch (IOException e3) {
            }
        }
    }

    public Icon getShortcutIcon(ShortcutInfo shortcut) {
        if (!shortcut.hasIconFile()) {
            if (!shortcut.hasIconUri()) {
                return shortcut.hasIconResource() ? Icon.createWithResource(shortcut.getPackage(), shortcut.getIconResourceId()) : shortcut.getIcon();
            }
            String uri = getShortcutIconUri(shortcut.getPackage(), shortcut.getId(), shortcut.getUserId());
            if (uri == null) {
                return null;
            }
            return shortcut.hasAdaptiveBitmap() ? Icon.createWithAdaptiveBitmapContentUri(uri) : Icon.createWithContentUri(uri);
        }
        ParcelFileDescriptor pfd = getShortcutIconFd(shortcut);
        if (pfd == null) {
            return null;
        }
        try {
            Bitmap bmp = BitmapFactory.decodeFileDescriptor(pfd.getFileDescriptor());
            if (bmp == null) {
                try {
                    pfd.close();
                } catch (IOException e) {
                }
                return null;
            }
            if (shortcut.hasAdaptiveBitmap()) {
                return Icon.createWithAdaptiveBitmap(bmp);
            }
            Icon createWithBitmap = Icon.createWithBitmap(bmp);
            try {
                pfd.close();
            } catch (IOException e2) {
            }
            return createWithBitmap;
        } finally {
            try {
                pfd.close();
            } catch (IOException e3) {
            }
        }
    }

    private Drawable loadDrawableResourceFromPackage(String packageName, int resId, UserHandle user, int density) {
        if (resId == 0) {
            return null;
        }
        try {
            ApplicationInfo ai = getApplicationInfo(packageName, 0, user);
            Resources res = this.mContext.getPackageManager().getResourcesForApplication(ai);
            return res.getDrawableForDensity(resId, density);
        } catch (PackageManager.NameNotFoundException | Resources.NotFoundException e) {
            return null;
        }
    }

    public Drawable getShortcutBadgedIconDrawable(ShortcutInfo shortcut, int density) {
        Drawable originalIcon = getShortcutIconDrawable(shortcut, density);
        if (originalIcon == null) {
            return null;
        }
        return this.mContext.getPackageManager().getUserBadgedIcon(originalIcon, shortcut.getUserHandle());
    }

    public void startShortcut(String packageName, String shortcutId, Rect sourceBounds, Bundle startActivityOptions, UserHandle user) {
        logErrorForInvalidProfileAccess(user);
        startShortcut(packageName, shortcutId, sourceBounds, startActivityOptions, user.getIdentifier());
    }

    public void startShortcut(ShortcutInfo shortcut, Rect sourceBounds, Bundle startActivityOptions) {
        startShortcut(shortcut.getPackage(), shortcut.getId(), sourceBounds, startActivityOptions, shortcut.getUserId());
    }

    private void startShortcut(String packageName, String shortcutId, Rect sourceBounds, Bundle startActivityOptions, int userId) {
        try {
            boolean success = this.mService.startShortcut(this.mContext.getPackageName(), packageName, null, shortcutId, sourceBounds, startActivityOptions, userId);
            if (!success) {
                throw new ActivityNotFoundException("Shortcut could not be started");
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void registerCallback(Callback callback) {
        registerCallback(callback, null);
    }

    public void registerCallback(Callback callback, Handler handler) {
        synchronized (this) {
            if (callback != null) {
                if (findCallbackLocked(callback) < 0) {
                    boolean addedFirstCallback = this.mCallbacks.size() == 0;
                    addCallbackLocked(callback, handler);
                    if (addedFirstCallback) {
                        try {
                            this.mService.addOnAppsChangedListener(this.mContext.getPackageName(), this.mAppsChangedListener);
                        } catch (RemoteException re) {
                            throw re.rethrowFromSystemServer();
                        }
                    }
                }
            }
        }
    }

    public void unregisterCallback(Callback callback) {
        synchronized (this) {
            removeCallbackLocked(callback);
            if (this.mCallbacks.size() == 0) {
                try {
                    this.mService.removeOnAppsChangedListener(this.mAppsChangedListener);
                } catch (RemoteException re) {
                    throw re.rethrowFromSystemServer();
                }
            }
        }
    }

    public void setArchiveCompatibility(ArchiveCompatibilityParams params) {
        try {
            this.mService.setArchiveCompatibilityOptions(params.isEnableIconOverlay(), params.isEnableUnarchivalConfirmation());
        } catch (RemoteException re) {
            throw re.rethrowFromSystemServer();
        }
    }

    private int findCallbackLocked(Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("Callback cannot be null");
        }
        int size = this.mCallbacks.size();
        for (int i = 0; i < size; i++) {
            if (this.mCallbacks.get(i).mCallback == callback) {
                return i;
            }
        }
        return -1;
    }

    private void removeCallbackLocked(Callback callback) {
        int pos = findCallbackLocked(callback);
        if (pos >= 0) {
            this.mCallbacks.remove(pos);
        }
    }

    private void addCallbackLocked(Callback callback, Handler handler) {
        removeCallbackLocked(callback);
        if (handler == null) {
            handler = new Handler();
        }
        CallbackMessageHandler toAdd = new CallbackMessageHandler(handler.getLooper(), callback);
        this.mCallbacks.add(toAdd);
    }

    public static class ArchiveCompatibilityParams {
        private boolean mEnableIconOverlay = true;
        private boolean mEnableUnarchivalConfirmation = true;

        public boolean isEnableIconOverlay() {
            return this.mEnableIconOverlay;
        }

        public boolean isEnableUnarchivalConfirmation() {
            return this.mEnableUnarchivalConfirmation;
        }

        public void setEnableIconOverlay(boolean enableIconOverlay) {
            this.mEnableIconOverlay = enableIconOverlay;
        }

        public void setEnableUnarchivalConfirmation(boolean enableUnarchivalConfirmation) {
            this.mEnableUnarchivalConfirmation = enableUnarchivalConfirmation;
        }
    }

    private static class CallbackMessageHandler extends Handler {
        private static final int MSG_ADDED = 1;
        private static final int MSG_AVAILABLE = 4;
        private static final int MSG_CHANGED = 3;
        private static final int MSG_LOADING_PROGRESS_CHANGED = 9;
        private static final int MSG_REMOVED = 2;
        private static final int MSG_SHORTCUT_CHANGED = 8;
        private static final int MSG_SUSPENDED = 6;
        private static final int MSG_UNAVAILABLE = 5;
        private static final int MSG_UNSUSPENDED = 7;
        private final Callback mCallback;

        private static class CallbackInfo {
            Bundle launcherExtras;
            float mLoadingProgress;
            String packageName;
            String[] packageNames;
            boolean replacing;
            List<ShortcutInfo> shortcuts;
            UserHandle user;

            private CallbackInfo() {
            }
        }

        public CallbackMessageHandler(Looper looper, Callback callback) {
            super(looper, null, true);
            this.mCallback = callback;
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            if (this.mCallback == null || !(msg.obj instanceof CallbackInfo)) {
                return;
            }
            CallbackInfo info = (CallbackInfo) msg.obj;
            switch (msg.what) {
                case 1:
                    this.mCallback.onPackageAdded(info.packageName, info.user);
                    break;
                case 2:
                    this.mCallback.onPackageRemoved(info.packageName, info.user);
                    break;
                case 3:
                    this.mCallback.onPackageChanged(info.packageName, info.user);
                    break;
                case 4:
                    this.mCallback.onPackagesAvailable(info.packageNames, info.user, info.replacing);
                    break;
                case 5:
                    this.mCallback.onPackagesUnavailable(info.packageNames, info.user, info.replacing);
                    break;
                case 6:
                    this.mCallback.onPackagesSuspended(info.packageNames, info.user, info.launcherExtras);
                    break;
                case 7:
                    this.mCallback.onPackagesUnsuspended(info.packageNames, info.user);
                    break;
                case 8:
                    this.mCallback.onShortcutsChanged(info.packageName, info.shortcuts, info.user);
                    break;
                case 9:
                    this.mCallback.onPackageLoadingProgressChanged(info.packageName, info.user, info.mLoadingProgress);
                    break;
            }
        }

        public void postOnPackageAdded(String packageName, UserHandle user) {
            CallbackInfo info = new CallbackInfo();
            info.packageName = packageName;
            info.user = user;
            obtainMessage(1, info).sendToTarget();
        }

        public void postOnPackageRemoved(String packageName, UserHandle user) {
            CallbackInfo info = new CallbackInfo();
            info.packageName = packageName;
            info.user = user;
            obtainMessage(2, info).sendToTarget();
        }

        public void postOnPackageChanged(String packageName, UserHandle user) {
            CallbackInfo info = new CallbackInfo();
            info.packageName = packageName;
            info.user = user;
            obtainMessage(3, info).sendToTarget();
        }

        public void postOnPackagesAvailable(String[] packageNames, UserHandle user, boolean replacing) {
            CallbackInfo info = new CallbackInfo();
            info.packageNames = packageNames;
            info.replacing = replacing;
            info.user = user;
            obtainMessage(4, info).sendToTarget();
        }

        public void postOnPackagesUnavailable(String[] packageNames, UserHandle user, boolean replacing) {
            CallbackInfo info = new CallbackInfo();
            info.packageNames = packageNames;
            info.replacing = replacing;
            info.user = user;
            obtainMessage(5, info).sendToTarget();
        }

        public void postOnPackagesSuspended(String[] packageNames, Bundle launcherExtras, UserHandle user) {
            CallbackInfo info = new CallbackInfo();
            info.packageNames = packageNames;
            info.user = user;
            info.launcherExtras = launcherExtras;
            obtainMessage(6, info).sendToTarget();
        }

        public void postOnPackagesUnsuspended(String[] packageNames, UserHandle user) {
            CallbackInfo info = new CallbackInfo();
            info.packageNames = packageNames;
            info.user = user;
            obtainMessage(7, info).sendToTarget();
        }

        public void postOnShortcutChanged(String packageName, UserHandle user, List<ShortcutInfo> shortcuts) {
            CallbackInfo info = new CallbackInfo();
            info.packageName = packageName;
            info.user = user;
            info.shortcuts = shortcuts;
            obtainMessage(8, info).sendToTarget();
        }

        public void postOnPackageLoadingProgressChanged(UserHandle user, String packageName, float progress) {
            CallbackInfo info = new CallbackInfo();
            info.packageName = packageName;
            info.user = user;
            info.mLoadingProgress = progress;
            obtainMessage(9, info).sendToTarget();
        }
    }

    public void registerPackageInstallerSessionCallback(Executor executor, PackageInstaller.SessionCallback callback) {
        if (executor == null) {
            throw new NullPointerException("Executor must not be null");
        }
        synchronized (this.mDelegates) {
            PackageInstaller.SessionCallbackDelegate delegate = new PackageInstaller.SessionCallbackDelegate(callback, executor);
            try {
                this.mService.registerPackageInstallerCallback(this.mContext.getPackageName(), delegate);
                this.mDelegates.add(delegate);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void unregisterPackageInstallerSessionCallback(PackageInstaller.SessionCallback callback) {
        synchronized (this.mDelegates) {
            Iterator<PackageInstaller.SessionCallbackDelegate> i = this.mDelegates.iterator();
            while (i.hasNext()) {
                PackageInstaller.SessionCallbackDelegate delegate = i.next();
                if (delegate.mCallback == callback) {
                    this.mPm.getPackageInstaller().unregisterSessionCallback(delegate.mCallback);
                    i.remove();
                }
            }
        }
    }

    public List<PackageInstaller.SessionInfo> getAllPackageInstallerSessions() {
        try {
            return this.mService.getAllSessions(this.mContext.getPackageName()).getList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void registerShortcutChangeCallback(ShortcutChangeCallback callback, ShortcutQuery query, Executor executor) {
        Objects.requireNonNull(callback, "Callback cannot be null");
        Objects.requireNonNull(query, "Query cannot be null");
        Objects.requireNonNull(executor, "Executor cannot be null");
        synchronized (this.mShortcutChangeCallbacks) {
            IShortcutChangeCallback proxy = new ShortcutChangeCallbackProxy(executor, callback);
            this.mShortcutChangeCallbacks.put(callback, new Pair<>(executor, proxy));
            try {
                this.mService.registerShortcutChangeCallback(this.mContext.getPackageName(), new ShortcutQueryWrapper(query), proxy);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void unregisterShortcutChangeCallback(ShortcutChangeCallback callback) {
        Objects.requireNonNull(callback, "Callback cannot be null");
        synchronized (this.mShortcutChangeCallbacks) {
            if (this.mShortcutChangeCallbacks.containsKey(callback)) {
                IShortcutChangeCallback proxy = this.mShortcutChangeCallbacks.remove(callback).second;
                try {
                    this.mService.unregisterShortcutChangeCallback(this.mContext.getPackageName(), proxy);
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }
    }

    public PinItemRequest getPinItemRequest(Intent intent) {
        return (PinItemRequest) intent.getParcelableExtra(EXTRA_PIN_ITEM_REQUEST, PinItemRequest.class);
    }

    public static final class PinItemRequest implements Parcelable {
        public static final Parcelable.Creator<PinItemRequest> CREATOR = new Parcelable.Creator<PinItemRequest>() { // from class: android.content.pm.LauncherApps.PinItemRequest.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public PinItemRequest createFromParcel(Parcel source) {
                return new PinItemRequest(source);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public PinItemRequest[] newArray(int size) {
                return new PinItemRequest[size];
            }
        };
        public static final int REQUEST_TYPE_APPWIDGET = 2;
        public static final int REQUEST_TYPE_SHORTCUT = 1;
        private final IPinItemRequest mInner;
        private final int mRequestType;

        @Retention(RetentionPolicy.SOURCE)
        public @interface RequestType {
        }

        public PinItemRequest(IPinItemRequest inner, int type) {
            this.mInner = inner;
            this.mRequestType = type;
        }

        public int getRequestType() {
            return this.mRequestType;
        }

        public ShortcutInfo getShortcutInfo() {
            try {
                return this.mInner.getShortcutInfo();
            } catch (RemoteException e) {
                throw e.rethrowAsRuntimeException();
            }
        }

        public AppWidgetProviderInfo getAppWidgetProviderInfo(Context context) {
            try {
                AppWidgetProviderInfo info = this.mInner.getAppWidgetProviderInfo();
                if (info == null) {
                    return null;
                }
                info.updateDimensions(context.getResources().getDisplayMetrics());
                return info;
            } catch (RemoteException e) {
                throw e.rethrowAsRuntimeException();
            }
        }

        public Bundle getExtras() {
            try {
                return this.mInner.getExtras();
            } catch (RemoteException e) {
                throw e.rethrowAsRuntimeException();
            }
        }

        public boolean isValid() {
            try {
                return this.mInner.isValid();
            } catch (RemoteException e) {
                return false;
            }
        }

        public boolean accept(Bundle options) {
            try {
                return this.mInner.accept(options);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public boolean accept() {
            return accept(null);
        }

        private PinItemRequest(Parcel source) {
            getClass().getClassLoader();
            this.mRequestType = source.readInt();
            this.mInner = IPinItemRequest.Stub.asInterface(source.readStrongBinder());
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.mRequestType);
            dest.writeStrongBinder(this.mInner.asBinder());
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }
    }

    @SystemApi
    public static final class AppUsageLimit implements Parcelable {
        public static final Parcelable.Creator<AppUsageLimit> CREATOR = new Parcelable.Creator<AppUsageLimit>() { // from class: android.content.pm.LauncherApps.AppUsageLimit.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public AppUsageLimit createFromParcel(Parcel source) {
                return new AppUsageLimit(source);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public AppUsageLimit[] newArray(int size) {
                return new AppUsageLimit[size];
            }
        };
        private final long mTotalUsageLimit;
        private final long mUsageRemaining;

        public AppUsageLimit(long totalUsageLimit, long usageRemaining) {
            this.mTotalUsageLimit = totalUsageLimit;
            this.mUsageRemaining = usageRemaining;
        }

        public long getTotalUsageLimit() {
            return this.mTotalUsageLimit;
        }

        public long getUsageRemaining() {
            return this.mUsageRemaining;
        }

        private AppUsageLimit(Parcel source) {
            this.mTotalUsageLimit = source.readLong();
            this.mUsageRemaining = source.readLong();
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeLong(this.mTotalUsageLimit);
            dest.writeLong(this.mUsageRemaining);
        }
    }
}
