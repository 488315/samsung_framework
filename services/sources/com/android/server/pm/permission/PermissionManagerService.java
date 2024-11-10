package com.android.server.pm.permission;

import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.IActivityManager;
import android.content.AttributionSource;
import android.content.AttributionSourceState;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManagerInternal;
import android.content.pm.ParceledListSlice;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.health.connect.HealthConnectManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.permission.IOnPermissionsChangeListener;
import android.permission.IPermissionChecker;
import android.permission.IPermissionManager;
import android.permission.PermissionManager;
import android.permission.PermissionManagerInternal;
import android.service.voice.VoiceInteractionManagerInternal;
import android.util.ArrayMap;
import android.util.Slog;
import android.util.SparseArray;
import com.android.internal.util.Preconditions;
import com.android.internal.util.function.TriFunction;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.LocalServices;
import com.android.server.appprelauncher.AppPrelaunchManagerService;
import com.android.server.pm.UserManagerInternal;
import com.android.server.pm.UserManagerService;
import com.android.server.pm.permission.PermissionManagerService;
import com.android.server.pm.permission.PermissionManagerServiceInternal;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.PackageState;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public class PermissionManagerService extends IPermissionManager.Stub {
    public static final String LOG_TAG = PermissionManagerService.class.getSimpleName();
    public static final ConcurrentHashMap sRunningAttributionSources = new ConcurrentHashMap();
    public final AppOpsManager mAppOpsManager;
    public final AttributionSourceRegistry mAttributionSourceRegistry;
    public CheckPermissionDelegate mCheckPermissionDelegate;
    public final Context mContext;
    public PermissionManagerServiceInternal.HotwordDetectionServiceProvider mHotwordDetectionServiceProvider;
    public final Object mLock = new Object();
    public final SparseArray mOneTimePermissionUserManagers = new SparseArray();
    public final PackageManagerInternal mPackageManagerInt;
    public final PermissionManagerServiceInterface mPermissionManagerServiceImpl;
    public final UserManagerInternal mUserManagerInt;

    /* loaded from: classes3.dex */
    public interface CheckPermissionDelegate {
        int checkPermission(String str, String str2, int i, TriFunction triFunction);

        int checkUidPermission(int i, String str, BiFunction biFunction);

        List getDelegatedPermissionNames();

        int getDelegatedUid();
    }

    public PermissionManagerService(Context context, ArrayMap arrayMap) {
        PackageManager.invalidatePackageInfoCache();
        PermissionManager.disablePackageNamePermissionCache();
        this.mContext = context;
        this.mPackageManagerInt = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
        this.mUserManagerInt = (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
        this.mAppOpsManager = (AppOpsManager) context.getSystemService(AppOpsManager.class);
        this.mAttributionSourceRegistry = new AttributionSourceRegistry(context);
        PermissionManagerServiceInternalImpl permissionManagerServiceInternalImpl = new PermissionManagerServiceInternalImpl();
        LocalServices.addService(PermissionManagerServiceInternal.class, permissionManagerServiceInternalImpl);
        LocalServices.addService(PermissionManagerInternal.class, permissionManagerServiceInternalImpl);
        this.mPermissionManagerServiceImpl = new PermissionManagerServiceImpl(context, arrayMap);
    }

    public static PermissionManagerServiceInternal create(Context context, ArrayMap arrayMap) {
        PermissionManagerServiceInternal permissionManagerServiceInternal = (PermissionManagerServiceInternal) LocalServices.getService(PermissionManagerServiceInternal.class);
        if (permissionManagerServiceInternal != null) {
            return permissionManagerServiceInternal;
        }
        if (((PermissionManagerService) ServiceManager.getService("permissionmgr")) == null) {
            ServiceManager.addService("permissionmgr", new PermissionManagerService(context, arrayMap));
            ServiceManager.addService("permission_checker", new PermissionCheckerService(context));
        }
        return (PermissionManagerServiceInternal) LocalServices.getService(PermissionManagerServiceInternal.class);
    }

    public static void killUid(int i, int i2, String str) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            IActivityManager service = ActivityManager.getService();
            if (service != null) {
                try {
                    service.killUidForPermissionChange(i, i2, str);
                } catch (RemoteException unused) {
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final int checkPermission(String str, String str2, int i) {
        CheckPermissionDelegate checkPermissionDelegate;
        if (str == null || str2 == null) {
            return -1;
        }
        synchronized (this.mLock) {
            checkPermissionDelegate = this.mCheckPermissionDelegate;
        }
        if (checkPermissionDelegate == null) {
            return this.mPermissionManagerServiceImpl.checkPermission(str, str2, i);
        }
        final PermissionManagerServiceInterface permissionManagerServiceInterface = this.mPermissionManagerServiceImpl;
        Objects.requireNonNull(permissionManagerServiceInterface);
        return checkPermissionDelegate.checkPermission(str, str2, i, new TriFunction() { // from class: com.android.server.pm.permission.PermissionManagerService$$ExternalSyntheticLambda1
            public final Object apply(Object obj, Object obj2, Object obj3) {
                return Integer.valueOf(PermissionManagerServiceInterface.this.checkPermission((String) obj, (String) obj2, ((Integer) obj3).intValue()));
            }
        });
    }

    public final int checkUidPermission(int i, String str) {
        CheckPermissionDelegate checkPermissionDelegate;
        if (str == null) {
            return -1;
        }
        synchronized (this.mLock) {
            checkPermissionDelegate = this.mCheckPermissionDelegate;
        }
        if (checkPermissionDelegate == null) {
            return this.mPermissionManagerServiceImpl.checkUidPermission(i, str);
        }
        final PermissionManagerServiceInterface permissionManagerServiceInterface = this.mPermissionManagerServiceImpl;
        Objects.requireNonNull(permissionManagerServiceInterface);
        return checkPermissionDelegate.checkUidPermission(i, str, new BiFunction() { // from class: com.android.server.pm.permission.PermissionManagerService$$ExternalSyntheticLambda2
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return Integer.valueOf(PermissionManagerServiceInterface.this.checkUidPermission(((Integer) obj).intValue(), (String) obj2));
            }
        });
    }

    public boolean setAutoRevokeExempted(String str, boolean z, int i) {
        Objects.requireNonNull(str);
        AndroidPackage androidPackage = this.mPackageManagerInt.getPackage(str);
        if (checkAutoRevokeAccess(androidPackage, Binder.getCallingUid())) {
            return setAutoRevokeExemptedInternal(androidPackage, z, i);
        }
        return false;
    }

    public final boolean setAutoRevokeExemptedInternal(AndroidPackage androidPackage, boolean z, int i) {
        int uid = UserHandle.getUid(i, androidPackage.getUid());
        if (this.mAppOpsManager.checkOpNoThrow(98, uid, androidPackage.getPackageName()) != 0) {
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mAppOpsManager.setMode(97, uid, androidPackage.getPackageName(), z ? 1 : 0);
            return true;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean checkAutoRevokeAccess(AndroidPackage androidPackage, int i) {
        boolean z = this.mContext.checkCallingOrSelfPermission("android.permission.WHITELIST_AUTO_REVOKE_PERMISSIONS") == 0;
        boolean isCallerInstallerOfRecord = this.mPackageManagerInt.isCallerInstallerOfRecord(androidPackage, i);
        if (z || isCallerInstallerOfRecord) {
            return (androidPackage == null || this.mPackageManagerInt.filterAppAccess(androidPackage, i, UserHandle.getUserId(i))) ? false : true;
        }
        throw new SecurityException("Caller must either hold android.permission.WHITELIST_AUTO_REVOKE_PERMISSIONS or be the installer on record");
    }

    public boolean isAutoRevokeExempted(String str, int i) {
        Objects.requireNonNull(str);
        AndroidPackage androidPackage = this.mPackageManagerInt.getPackage(str);
        if (!checkAutoRevokeAccess(androidPackage, Binder.getCallingUid())) {
            return false;
        }
        int uid = UserHandle.getUid(i, androidPackage.getUid());
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return this.mAppOpsManager.checkOpNoThrow(97, uid, str) == 1;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void startShellPermissionIdentityDelegationInternal(int i, String str, List list) {
        synchronized (this.mLock) {
            CheckPermissionDelegate checkPermissionDelegate = this.mCheckPermissionDelegate;
            if (checkPermissionDelegate != null && checkPermissionDelegate.getDelegatedUid() != i) {
                throw new SecurityException("Shell can delegate permissions only to one UID at a time");
            }
            setCheckPermissionDelegateLocked(new ShellDelegate(i, str, list));
        }
    }

    public final void stopShellPermissionIdentityDelegationInternal() {
        synchronized (this.mLock) {
            setCheckPermissionDelegateLocked(null);
        }
    }

    public final List getDelegatedShellPermissionsInternal() {
        synchronized (this.mLock) {
            CheckPermissionDelegate checkPermissionDelegate = this.mCheckPermissionDelegate;
            if (checkPermissionDelegate == null) {
                return Collections.EMPTY_LIST;
            }
            return checkPermissionDelegate.getDelegatedPermissionNames();
        }
    }

    public final void setCheckPermissionDelegateLocked(CheckPermissionDelegate checkPermissionDelegate) {
        if (checkPermissionDelegate != null || this.mCheckPermissionDelegate != null) {
            PackageManager.invalidatePackageInfoCache();
        }
        this.mCheckPermissionDelegate = checkPermissionDelegate;
    }

    public final OneTimePermissionUserManager getOneTimePermissionUserManager(int i) {
        synchronized (this.mLock) {
            OneTimePermissionUserManager oneTimePermissionUserManager = (OneTimePermissionUserManager) this.mOneTimePermissionUserManagers.get(i);
            if (oneTimePermissionUserManager != null) {
                return oneTimePermissionUserManager;
            }
            OneTimePermissionUserManager oneTimePermissionUserManager2 = new OneTimePermissionUserManager(this.mContext.createContextAsUser(UserHandle.of(i), 0));
            synchronized (this.mLock) {
                OneTimePermissionUserManager oneTimePermissionUserManager3 = (OneTimePermissionUserManager) this.mOneTimePermissionUserManagers.get(i);
                if (oneTimePermissionUserManager3 != null) {
                    return oneTimePermissionUserManager3;
                }
                this.mOneTimePermissionUserManagers.put(i, oneTimePermissionUserManager2);
                oneTimePermissionUserManager2.registerUninstallListener();
                return oneTimePermissionUserManager2;
            }
        }
    }

    public void startOneTimePermissionSession(String str, int i, long j, long j2) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_ONE_TIME_PERMISSION_SESSIONS", "Must hold android.permission.MANAGE_ONE_TIME_PERMISSION_SESSIONS to register permissions as one time.");
        Objects.requireNonNull(str);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            getOneTimePermissionUserManager(i).startPackageOneTimeSession(str, j, j2);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void stopOneTimePermissionSession(String str, int i) {
        super.stopOneTimePermissionSession_enforcePermission();
        Objects.requireNonNull(str);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            getOneTimePermissionUserManager(i).stopPackageOneTimeSession(str);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public IBinder registerAttributionSource(AttributionSourceState attributionSourceState) {
        Binder binder = new Binder();
        this.mAttributionSourceRegistry.registerAttributionSource(new AttributionSource(attributionSourceState).withToken(binder));
        return binder;
    }

    public boolean isRegisteredAttributionSource(AttributionSourceState attributionSourceState) {
        return this.mAttributionSourceRegistry.isRegisteredAttributionSource(new AttributionSource(attributionSourceState));
    }

    public List getAutoRevokeExemptionRequestedPackages(int i) {
        return getPackagesWithAutoRevokePolicy(1, i);
    }

    public List getAutoRevokeExemptionGrantedPackages(int i) {
        return getPackagesWithAutoRevokePolicy(2, i);
    }

    public final List getPackagesWithAutoRevokePolicy(final int i, int i2) {
        this.mContext.enforceCallingPermission("android.permission.ADJUST_RUNTIME_PERMISSIONS_POLICY", "Must hold android.permission.ADJUST_RUNTIME_PERMISSIONS_POLICY");
        final ArrayList arrayList = new ArrayList();
        this.mPackageManagerInt.forEachInstalledPackage(new Consumer() { // from class: com.android.server.pm.permission.PermissionManagerService$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                PermissionManagerService.lambda$getPackagesWithAutoRevokePolicy$0(i, arrayList, (AndroidPackage) obj);
            }
        }, i2);
        return arrayList;
    }

    public static /* synthetic */ void lambda$getPackagesWithAutoRevokePolicy$0(int i, List list, AndroidPackage androidPackage) {
        if (androidPackage.getAutoRevokePermissions() == i) {
            list.add(androidPackage.getPackageName());
        }
    }

    public ParceledListSlice getAllPermissionGroups(int i) {
        return new ParceledListSlice(this.mPermissionManagerServiceImpl.getAllPermissionGroups(i));
    }

    public PermissionGroupInfo getPermissionGroupInfo(String str, int i) {
        return this.mPermissionManagerServiceImpl.getPermissionGroupInfo(str, i);
    }

    public PermissionInfo getPermissionInfo(String str, String str2, int i) {
        return this.mPermissionManagerServiceImpl.getPermissionInfo(str, i, str2);
    }

    public ParceledListSlice queryPermissionsByGroup(String str, int i) {
        List queryPermissionsByGroup = this.mPermissionManagerServiceImpl.queryPermissionsByGroup(str, i);
        if (queryPermissionsByGroup == null) {
            return null;
        }
        return new ParceledListSlice(queryPermissionsByGroup);
    }

    public boolean addPermission(PermissionInfo permissionInfo, boolean z) {
        return this.mPermissionManagerServiceImpl.addPermission(permissionInfo, z);
    }

    public void removePermission(String str) {
        this.mPermissionManagerServiceImpl.removePermission(str);
    }

    public int getPermissionFlags(String str, String str2, int i) {
        return this.mPermissionManagerServiceImpl.getPermissionFlags(str, str2, i);
    }

    public void updatePermissionFlags(String str, String str2, int i, int i2, boolean z, int i3) {
        this.mPermissionManagerServiceImpl.updatePermissionFlags(str, str2, i, i2, z, i3);
    }

    public void updatePermissionFlagsForAllApps(int i, int i2, int i3) {
        this.mPermissionManagerServiceImpl.updatePermissionFlagsForAllApps(i, i2, i3);
    }

    public void addOnPermissionsChangeListener(IOnPermissionsChangeListener iOnPermissionsChangeListener) {
        this.mPermissionManagerServiceImpl.addOnPermissionsChangeListener(iOnPermissionsChangeListener);
    }

    public void removeOnPermissionsChangeListener(IOnPermissionsChangeListener iOnPermissionsChangeListener) {
        this.mPermissionManagerServiceImpl.removeOnPermissionsChangeListener(iOnPermissionsChangeListener);
    }

    public List getAllowlistedRestrictedPermissions(String str, int i, int i2) {
        return this.mPermissionManagerServiceImpl.getAllowlistedRestrictedPermissions(str, i, i2);
    }

    public boolean addAllowlistedRestrictedPermission(String str, String str2, int i, int i2) {
        return this.mPermissionManagerServiceImpl.addAllowlistedRestrictedPermission(str, str2, i, i2);
    }

    public boolean removeAllowlistedRestrictedPermission(String str, String str2, int i, int i2) {
        return this.mPermissionManagerServiceImpl.removeAllowlistedRestrictedPermission(str, str2, i, i2);
    }

    public void grantRuntimePermission(String str, String str2, int i) {
        this.mPermissionManagerServiceImpl.grantRuntimePermission(str, str2, i);
    }

    public void revokeRuntimePermission(String str, String str2, int i, String str3) {
        this.mPermissionManagerServiceImpl.revokeRuntimePermission(str, str2, i, str3);
    }

    public void revokePostNotificationPermissionWithoutKillForTest(String str, int i) {
        this.mPermissionManagerServiceImpl.revokePostNotificationPermissionWithoutKillForTest(str, i);
    }

    public boolean shouldShowRequestPermissionRationale(String str, String str2, int i) {
        return this.mPermissionManagerServiceImpl.shouldShowRequestPermissionRationale(str, str2, i);
    }

    public boolean isPermissionRevokedByPolicy(String str, String str2, int i) {
        return this.mPermissionManagerServiceImpl.isPermissionRevokedByPolicy(str, str2, i);
    }

    public List getSplitPermissions() {
        return this.mPermissionManagerServiceImpl.getSplitPermissions();
    }

    /* loaded from: classes3.dex */
    public class PermissionManagerServiceInternalImpl implements PermissionManagerServiceInternal {
        public /* synthetic */ PermissionManagerServiceInternalImpl(PermissionManagerService permissionManagerService, PermissionManagerServiceInternalImplIA permissionManagerServiceInternalImplIA) {
            this();
        }

        public PermissionManagerServiceInternalImpl() {
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceInternal
        public int checkPermission(String str, String str2, int i) {
            return PermissionManagerService.this.checkPermission(str, str2, i);
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceInternal
        public int checkUidPermission(int i, String str) {
            return PermissionManagerService.this.checkUidPermission(i, str);
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceInternal
        public int checkPostNotificationsPermissionGrantedOrLegacyAccess(int i) {
            int checkUidPermission = PermissionManagerService.this.checkUidPermission(i, "android.permission.POST_NOTIFICATIONS");
            AndroidPackage androidPackage = PermissionManagerService.this.mPackageManagerInt.getPackage(i);
            if (androidPackage == null) {
                Slog.e(PermissionManagerService.LOG_TAG, "No package for uid " + i);
                return checkUidPermission;
            }
            if (checkUidPermission != 0 && androidPackage.getTargetSdkVersion() >= 23) {
                int permissionFlags = PermissionManagerService.this.getPermissionFlags(androidPackage.getPackageName(), "android.permission.POST_NOTIFICATIONS", UserHandle.getUserId(i));
                if ((permissionFlags & 64) != 0 && (permissionFlags & 1) == 0) {
                    return 0;
                }
            }
            return checkUidPermission;
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceInternal
        public void startShellPermissionIdentityDelegation(int i, String str, List list) {
            Objects.requireNonNull(str, "packageName");
            PermissionManagerService.this.startShellPermissionIdentityDelegationInternal(i, str, list);
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceInternal
        public void stopShellPermissionIdentityDelegation() {
            PermissionManagerService.this.stopShellPermissionIdentityDelegationInternal();
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceInternal
        public List getDelegatedShellPermissions() {
            return PermissionManagerService.this.getDelegatedShellPermissionsInternal();
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceInternal
        public void setHotwordDetectionServiceProvider(PermissionManagerServiceInternal.HotwordDetectionServiceProvider hotwordDetectionServiceProvider) {
            PermissionManagerService.this.mHotwordDetectionServiceProvider = hotwordDetectionServiceProvider;
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceInternal
        public PermissionManagerServiceInternal.HotwordDetectionServiceProvider getHotwordDetectionServiceProvider() {
            return PermissionManagerService.this.mHotwordDetectionServiceProvider;
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceInternal
        public void revokeInstallPermission(String str, String str2) {
            PermissionManagerService.this.mPermissionManagerServiceImpl.revokeInstallPermission(str, str2);
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceInternal
        public void grantInstallPermission(String str, String str2) {
            PermissionManagerService.this.mPermissionManagerServiceImpl.grantInstallPermission(str, str2);
        }

        @Override // com.android.server.pm.permission.LegacyPermissionDataProvider
        public int[] getGidsForUid(int i) {
            return PermissionManagerService.this.mPermissionManagerServiceImpl.getGidsForUid(i);
        }

        @Override // com.android.server.pm.permission.LegacyPermissionDataProvider
        public Map getAllAppOpPermissionPackages() {
            return PermissionManagerService.this.mPermissionManagerServiceImpl.getAllAppOpPermissionPackages();
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceInternal
        public void onUserCreated(int i) {
            PermissionManagerService.this.mPermissionManagerServiceImpl.onUserCreated(i);
        }

        @Override // com.android.server.pm.permission.LegacyPermissionDataProvider
        public List getLegacyPermissions() {
            return PermissionManagerService.this.mPermissionManagerServiceImpl.getLegacyPermissions();
        }

        @Override // com.android.server.pm.permission.LegacyPermissionDataProvider
        public LegacyPermissionState getLegacyPermissionState(int i) {
            return PermissionManagerService.this.mPermissionManagerServiceImpl.getLegacyPermissionState(i);
        }

        public byte[] backupRuntimePermissions(int i) {
            return PermissionManagerService.this.mPermissionManagerServiceImpl.backupRuntimePermissions(i);
        }

        public void restoreRuntimePermissions(byte[] bArr, int i) {
            PermissionManagerService.this.mPermissionManagerServiceImpl.restoreRuntimePermissions(bArr, i);
        }

        public void restoreDelayedRuntimePermissions(String str, int i) {
            PermissionManagerService.this.mPermissionManagerServiceImpl.restoreDelayedRuntimePermissions(str, i);
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceInternal
        public List getRequestedRuntimePermissionsForMDM(String str) {
            return PermissionManagerService.this.mPermissionManagerServiceImpl.getRequestedRuntimePermissionsForMDM(str);
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceInternal
        public boolean applyRuntimePermissionsForMDM(String str, List list, int i, int i2) {
            return PermissionManagerService.this.mPermissionManagerServiceImpl.applyRuntimePermissionsForMDM(str, list, i, i2);
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceInternal
        public boolean applyRuntimePermissionsForAllApplicationsForMDM(int i, int i2) {
            return PermissionManagerService.this.mPermissionManagerServiceImpl.applyRuntimePermissionsForAllApplicationsForMDM(i, i2);
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceInternal
        public int setLicensePermissionsForMDM(String str, Set set) {
            return PermissionManagerService.this.mPermissionManagerServiceImpl.setLicensePermissionsForMDM(str, set);
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceInternal
        public List getPackageGrantedPermissionsForMDM(String str) {
            return PermissionManagerService.this.mPermissionManagerServiceImpl.getPackageGrantedPermissionsForMDM(str);
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceInternal
        public void removePackageGrantedPermissionsForMDM(String str) {
            PermissionManagerService.this.mPermissionManagerServiceImpl.removePackageGrantedPermissionsForMDM(str);
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceInternal
        public void readLegacyPermissionsTEMP(LegacyPermissionSettings legacyPermissionSettings) {
            PermissionManagerService.this.mPermissionManagerServiceImpl.readLegacyPermissionsTEMP(legacyPermissionSettings);
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceInternal
        public void writeLegacyPermissionsTEMP(LegacyPermissionSettings legacyPermissionSettings) {
            PermissionManagerService.this.mPermissionManagerServiceImpl.writeLegacyPermissionsTEMP(legacyPermissionSettings);
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceInternal
        public void onPackageAdded(PackageState packageState, boolean z, AndroidPackage androidPackage) {
            PermissionManagerService.this.mPermissionManagerServiceImpl.onPackageAdded(packageState, z, androidPackage);
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceInternal
        public void onPackageInstalled(AndroidPackage androidPackage, int i, PermissionManagerServiceInternal.PackageInstalledParams packageInstalledParams, int i2) {
            int[] iArr;
            Objects.requireNonNull(androidPackage, "pkg");
            Objects.requireNonNull(packageInstalledParams, "params");
            Preconditions.checkArgument(i2 >= 0 || i2 == -1, "userId");
            PermissionManagerService.this.mPermissionManagerServiceImpl.onPackageInstalled(androidPackage, i, packageInstalledParams, i2);
            if (i2 == -1) {
                iArr = PermissionManagerService.this.getAllUserIds();
            } else {
                iArr = new int[]{i2};
            }
            for (int i3 : iArr) {
                int autoRevokePermissionsMode = packageInstalledParams.getAutoRevokePermissionsMode();
                if (autoRevokePermissionsMode == 0 || autoRevokePermissionsMode == 1) {
                    PermissionManagerService.this.setAutoRevokeExemptedInternal(androidPackage, autoRevokePermissionsMode == 1, i3);
                }
            }
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceInternal
        public void onPackageRemoved(AndroidPackage androidPackage) {
            PermissionManagerService.this.mPermissionManagerServiceImpl.onPackageRemoved(androidPackage);
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceInternal
        public void onPackageUninstalled(String str, int i, PackageState packageState, AndroidPackage androidPackage, List list, int i2) {
            PermissionManagerService.this.mPermissionManagerServiceImpl.onPackageUninstalled(str, i, packageState, androidPackage, list, i2);
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceInternal
        public void addOnRuntimePermissionStateChangedListener(PermissionManagerServiceInternal.OnRuntimePermissionStateChangedListener onRuntimePermissionStateChangedListener) {
            PermissionManagerService.this.mPermissionManagerServiceImpl.addOnRuntimePermissionStateChangedListener(onRuntimePermissionStateChangedListener);
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceInternal
        public void onSystemReady() {
            PermissionManagerService.this.mPermissionManagerServiceImpl.onSystemReady();
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceInternal
        public boolean isPermissionsReviewRequired(String str, int i) {
            return PermissionManagerService.this.mPermissionManagerServiceImpl.isPermissionsReviewRequired(str, i);
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceInternal
        public void readLegacyPermissionStateTEMP() {
            PermissionManagerService.this.mPermissionManagerServiceImpl.readLegacyPermissionStateTEMP();
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceInternal, com.android.server.pm.permission.LegacyPermissionDataProvider
        public void writeLegacyPermissionStateTEMP() {
            PermissionManagerService.this.mPermissionManagerServiceImpl.writeLegacyPermissionStateTEMP();
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceInternal
        public void onUserRemoved(int i) {
            PermissionManagerService.this.mPermissionManagerServiceImpl.onUserRemoved(i);
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceInternal
        public Set getInstalledPermissions(String str) {
            return PermissionManagerService.this.mPermissionManagerServiceImpl.getInstalledPermissions(str);
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceInternal
        public Set getGrantedPermissions(String str, int i) {
            return PermissionManagerService.this.mPermissionManagerServiceImpl.getGrantedPermissions(str, i);
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceInternal
        public int[] getPermissionGids(String str, int i) {
            return PermissionManagerService.this.mPermissionManagerServiceImpl.getPermissionGids(str, i);
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceInternal
        public String[] getAppOpPermissionPackages(String str) {
            return PermissionManagerService.this.mPermissionManagerServiceImpl.getAppOpPermissionPackages(str);
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceInternal
        public void onStorageVolumeMounted(String str, boolean z) {
            PermissionManagerService.this.mPermissionManagerServiceImpl.onStorageVolumeMounted(str, z);
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceInternal
        public void resetRuntimePermissions(AndroidPackage androidPackage, int i) {
            PermissionManagerService.this.mPermissionManagerServiceImpl.resetRuntimePermissions(androidPackage, i);
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceInternal
        public void resetRuntimePermissionsForUser(int i) {
            PermissionManagerService.this.mPermissionManagerServiceImpl.resetRuntimePermissionsForUser(i);
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceInternal
        public Permission getPermissionTEMP(String str) {
            return PermissionManagerService.this.mPermissionManagerServiceImpl.getPermissionTEMP(str);
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceInternal
        public List getAllPermissionsWithProtection(int i) {
            return PermissionManagerService.this.mPermissionManagerServiceImpl.getAllPermissionsWithProtection(i);
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceInternal
        public List getAllPermissionsWithProtectionFlags(int i) {
            return PermissionManagerService.this.mPermissionManagerServiceImpl.getAllPermissionsWithProtectionFlags(i);
        }
    }

    public final int[] getAllUserIds() {
        return UserManagerService.getInstance().getUserIdsIncludingPreCreated();
    }

    /* loaded from: classes3.dex */
    public class ShellDelegate implements CheckPermissionDelegate {
        public final String mDelegatedPackageName;
        public final List mDelegatedPermissionNames;
        public final int mDelegatedUid;

        public ShellDelegate(int i, String str, List list) {
            this.mDelegatedUid = i;
            this.mDelegatedPackageName = str;
            this.mDelegatedPermissionNames = list;
        }

        @Override // com.android.server.pm.permission.PermissionManagerService.CheckPermissionDelegate
        public int getDelegatedUid() {
            return this.mDelegatedUid;
        }

        @Override // com.android.server.pm.permission.PermissionManagerService.CheckPermissionDelegate
        public int checkPermission(String str, String str2, int i, TriFunction triFunction) {
            if (this.mDelegatedPackageName.equals(str) && isDelegatedPermission(str2)) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    return ((Integer) triFunction.apply("com.android.shell", str2, Integer.valueOf(i))).intValue();
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
            return ((Integer) triFunction.apply(str, str2, Integer.valueOf(i))).intValue();
        }

        @Override // com.android.server.pm.permission.PermissionManagerService.CheckPermissionDelegate
        public int checkUidPermission(int i, String str, BiFunction biFunction) {
            if (i == this.mDelegatedUid && isDelegatedPermission(str)) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    return ((Integer) biFunction.apply(2000, str)).intValue();
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
            return ((Integer) biFunction.apply(Integer.valueOf(i), str)).intValue();
        }

        @Override // com.android.server.pm.permission.PermissionManagerService.CheckPermissionDelegate
        public List getDelegatedPermissionNames() {
            if (this.mDelegatedPermissionNames == null) {
                return null;
            }
            return new ArrayList(this.mDelegatedPermissionNames);
        }

        public final boolean isDelegatedPermission(String str) {
            List list = this.mDelegatedPermissionNames;
            return list == null || list.contains(str);
        }
    }

    /* loaded from: classes3.dex */
    public final class AttributionSourceRegistry {
        public final Context mContext;
        public final Object mLock = new Object();
        public final WeakHashMap mAttributions = new WeakHashMap();

        public AttributionSourceRegistry(Context context) {
            this.mContext = context;
        }

        public void registerAttributionSource(AttributionSource attributionSource) {
            int resolveUid = resolveUid(Binder.getCallingUid());
            int resolveUid2 = resolveUid(attributionSource.getUid());
            if (resolveUid2 != resolveUid && this.mContext.checkPermission("android.permission.UPDATE_APP_OPS_STATS", -1, resolveUid) != 0) {
                throw new SecurityException("Cannot register attribution source for uid:" + resolveUid2 + " from uid:" + resolveUid);
            }
            if (((PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class)).getPackageUid(attributionSource.getPackageName(), 0L, UserHandle.getUserId(resolveUid == 1000 ? resolveUid2 : resolveUid)) != resolveUid2) {
                throw new SecurityException("Cannot register attribution source for package:" + attributionSource.getPackageName() + " from uid:" + resolveUid);
            }
            AttributionSource next = attributionSource.getNext();
            if (next != null && next.getNext() != null && !isRegisteredAttributionSource(next)) {
                throw new SecurityException("Cannot register forged attribution source:" + attributionSource);
            }
            synchronized (this.mLock) {
                this.mAttributions.put(attributionSource.getToken(), attributionSource.withDefaultToken());
            }
        }

        public boolean isRegisteredAttributionSource(AttributionSource attributionSource) {
            synchronized (this.mLock) {
                AttributionSource attributionSource2 = (AttributionSource) this.mAttributions.get(attributionSource.getToken());
                if (attributionSource2 == null) {
                    return false;
                }
                return attributionSource2.equalsExceptToken(attributionSource);
            }
        }

        public final int resolveUid(int i) {
            VoiceInteractionManagerInternal.HotwordDetectionServiceIdentity hotwordDetectionServiceIdentity;
            VoiceInteractionManagerInternal voiceInteractionManagerInternal = (VoiceInteractionManagerInternal) LocalServices.getService(VoiceInteractionManagerInternal.class);
            return (voiceInteractionManagerInternal == null || (hotwordDetectionServiceIdentity = voiceInteractionManagerInternal.getHotwordDetectionServiceIdentity()) == null || i != hotwordDetectionServiceIdentity.getIsolatedUid()) ? i : hotwordDetectionServiceIdentity.getOwnerUid();
        }
    }

    /* loaded from: classes3.dex */
    public final class PermissionCheckerService extends IPermissionChecker.Stub {
        public final AppOpsManager mAppOpsManager;
        public final Context mContext;
        public final PermissionManagerServiceInternal mPermissionManagerServiceInternal = (PermissionManagerServiceInternal) LocalServices.getService(PermissionManagerServiceInternal.class);
        public static final ConcurrentHashMap sPlatformPermissions = new ConcurrentHashMap();
        public static final AtomicInteger sAttributionChainIds = new AtomicInteger(0);

        public PermissionCheckerService(Context context) {
            this.mContext = context;
            this.mAppOpsManager = (AppOpsManager) context.getSystemService(AppOpsManager.class);
        }

        public int checkPermission(String str, AttributionSourceState attributionSourceState, String str2, boolean z, boolean z2, boolean z3, int i) {
            Objects.requireNonNull(str);
            Objects.requireNonNull(attributionSourceState);
            AttributionSource attributionSource = new AttributionSource(attributionSourceState);
            int checkPermission = checkPermission(this.mContext, this.mPermissionManagerServiceInternal, str, attributionSource, str2, z, z2, z3, i);
            if (z2 && checkPermission != 0 && checkPermission != 1) {
                if (i == -1) {
                    finishDataDelivery(AppOpsManager.permissionToOpCode(str), attributionSource.asState(), z3);
                } else {
                    finishDataDelivery(i, attributionSource.asState(), z3);
                }
            }
            return checkPermission;
        }

        public void finishDataDelivery(int i, AttributionSourceState attributionSourceState, boolean z) {
            finishDataDelivery(this.mContext, i, attributionSourceState, z);
        }

        /* JADX WARN: Code restructure failed: missing block: B:37:0x009f, code lost:
        
            if (r8 == null) goto L124;
         */
        /* JADX WARN: Code restructure failed: missing block: B:38:0x00a1, code lost:
        
            r9 = (com.android.server.pm.permission.PermissionManagerService.RegisteredAttribution) com.android.server.pm.permission.PermissionManagerService.sRunningAttributionSources.remove(r8.getToken());
         */
        /* JADX WARN: Code restructure failed: missing block: B:39:0x00af, code lost:
        
            if (r9 == null) goto L125;
         */
        /* JADX WARN: Code restructure failed: missing block: B:40:0x00b1, code lost:
        
            r9.unregister();
         */
        /* JADX WARN: Code restructure failed: missing block: B:41:0x00b4, code lost:
        
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:42:?, code lost:
        
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:43:?, code lost:
        
            return;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public static void finishDataDelivery(android.content.Context r9, int r10, android.content.AttributionSourceState r11, boolean r12) {
            /*
                java.util.Objects.requireNonNull(r11)
                java.lang.Class<android.app.AppOpsManager> r0 = android.app.AppOpsManager.class
                java.lang.Object r0 = r9.getSystemService(r0)
                android.app.AppOpsManager r0 = (android.app.AppOpsManager) r0
                r1 = -1
                if (r10 != r1) goto Lf
                return
            Lf:
                android.content.AttributionSource r1 = new android.content.AttributionSource
                r1.<init>(r11)
                r2 = 0
                r7 = r1
            L16:
                r1 = 0
                r3 = 1
                if (r12 != 0) goto L1f
                if (r2 == 0) goto L1d
                goto L1f
            L1d:
                r2 = r1
                goto L20
            L1f:
                r2 = r3
            L20:
                android.content.AttributionSource r8 = r7.getNext()
                if (r12 == 0) goto L2c
                android.content.AttributionSourceState r4 = r7.asState()
                if (r4 == r11) goto L35
            L2c:
                if (r8 == 0) goto L35
                boolean r4 = r7.isTrusted(r9)
                if (r4 != 0) goto L35
                return
            L35:
                if (r12 == 0) goto L47
                android.content.AttributionSourceState r4 = r7.asState()
                if (r4 != r11) goto L47
                if (r8 == 0) goto L47
                android.content.AttributionSource r4 = r8.getNext()
                if (r4 != 0) goto L47
                r4 = r3
                goto L48
            L47:
                r4 = r1
            L48:
                if (r4 != 0) goto L4c
                if (r8 != 0) goto L4d
            L4c:
                r1 = r3
            L4d:
                if (r4 != 0) goto L51
                r3 = r7
                goto L52
            L51:
                r3 = r8
            L52:
                if (r1 == 0) goto L6b
                java.lang.String r5 = resolvePackageName(r9, r3)
                if (r5 != 0) goto L5b
                return
            L5b:
                android.os.IBinder r2 = r11.token
                int r4 = r3.getUid()
                java.lang.String r6 = r3.getAttributionTag()
                r1 = r0
                r3 = r10
                r1.finishOp(r2, r3, r4, r5, r6)
                goto L7f
            L6b:
                android.content.AttributionSource r1 = resolveAttributionSource(r9, r3)
                java.lang.String r3 = r1.getPackageName()
                if (r3 != 0) goto L76
                return
            L76:
                android.os.IBinder r3 = r11.token
                java.lang.String r4 = android.app.AppOpsManager.opToPublicName(r10)
                r0.finishProxyOp(r3, r4, r1, r2)
            L7f:
                java.util.concurrent.ConcurrentHashMap r1 = com.android.server.pm.permission.PermissionManagerService.m9856$$Nest$sfgetsRunningAttributionSources()
                android.os.IBinder r2 = r7.getToken()
                java.lang.Object r1 = r1.remove(r2)
                com.android.server.pm.permission.PermissionManagerService$RegisteredAttribution r1 = (com.android.server.pm.permission.PermissionManagerService.RegisteredAttribution) r1
                if (r1 == 0) goto L92
                r1.unregister()
            L92:
                if (r8 == 0) goto L9f
                android.content.AttributionSource r1 = r8.getNext()
                if (r1 != 0) goto L9b
                goto L9f
            L9b:
                r2 = r8
                r7 = r2
                goto L16
            L9f:
                if (r8 == 0) goto Lb4
                java.util.concurrent.ConcurrentHashMap r9 = com.android.server.pm.permission.PermissionManagerService.m9856$$Nest$sfgetsRunningAttributionSources()
                android.os.IBinder r10 = r8.getToken()
                java.lang.Object r9 = r9.remove(r10)
                com.android.server.pm.permission.PermissionManagerService$RegisteredAttribution r9 = (com.android.server.pm.permission.PermissionManagerService.RegisteredAttribution) r9
                if (r9 == 0) goto Lb4
                r9.unregister()
            Lb4:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.permission.PermissionManagerService.PermissionCheckerService.finishDataDelivery(android.content.Context, int, android.content.AttributionSourceState, boolean):void");
        }

        public int checkOp(int i, AttributionSourceState attributionSourceState, String str, boolean z, boolean z2) {
            int checkOp = checkOp(this.mContext, i, this.mPermissionManagerServiceInternal, new AttributionSource(attributionSourceState), str, z, z2);
            if (checkOp != 0 && z2) {
                finishDataDelivery(i, attributionSourceState, false);
            }
            return checkOp;
        }

        public static int checkPermission(Context context, PermissionManagerServiceInternal permissionManagerServiceInternal, String str, AttributionSource attributionSource, String str2, boolean z, boolean z2, boolean z3, int i) {
            ConcurrentHashMap concurrentHashMap = sPlatformPermissions;
            PermissionInfo permissionInfo = (PermissionInfo) concurrentHashMap.get(str);
            if (permissionInfo == null) {
                try {
                    permissionInfo = context.getPackageManager().getPermissionInfo(str, 0);
                    if ("android".equals(permissionInfo.packageName) || HealthConnectManager.isHealthPermission(context, str)) {
                        concurrentHashMap.put(str, permissionInfo);
                    }
                } catch (PackageManager.NameNotFoundException unused) {
                    return 2;
                }
            }
            if (permissionInfo.isAppOp()) {
                return checkAppOpPermission(context, permissionManagerServiceInternal, str, attributionSource, str2, z, z3);
            }
            if (permissionInfo.isRuntime()) {
                return checkRuntimePermission(context, permissionManagerServiceInternal, str, attributionSource, str2, z, z2, z3, i);
            }
            if (!z3 && !checkPermission(context, permissionManagerServiceInternal, str, attributionSource.getUid(), attributionSource.getRenouncedPermissions())) {
                return 2;
            }
            if (attributionSource.getNext() != null) {
                return checkPermission(context, permissionManagerServiceInternal, str, attributionSource.getNext(), str2, z, z2, false, i);
            }
            return 0;
        }

        /* JADX WARN: Code restructure failed: missing block: B:37:0x00d5, code lost:
        
            return 0;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public static int checkAppOpPermission(android.content.Context r24, com.android.server.pm.permission.PermissionManagerServiceInternal r25, java.lang.String r26, android.content.AttributionSource r27, java.lang.String r28, boolean r29, boolean r30) {
            /*
                Method dump skipped, instructions count: 216
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.permission.PermissionManagerService.PermissionCheckerService.checkAppOpPermission(android.content.Context, com.android.server.pm.permission.PermissionManagerServiceInternal, java.lang.String, android.content.AttributionSource, java.lang.String, boolean, boolean):int");
        }

        /* JADX WARN: Code restructure failed: missing block: B:83:0x0197, code lost:
        
            return 0;
         */
        /* JADX WARN: Multi-variable type inference failed */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public static int checkRuntimePermission(android.content.Context r27, com.android.server.pm.permission.PermissionManagerServiceInternal r28, java.lang.String r29, android.content.AttributionSource r30, java.lang.String r31, boolean r32, boolean r33, boolean r34, int r35) {
            /*
                Method dump skipped, instructions count: 411
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.permission.PermissionManagerService.PermissionCheckerService.checkRuntimePermission(android.content.Context, com.android.server.pm.permission.PermissionManagerServiceInternal, java.lang.String, android.content.AttributionSource, java.lang.String, boolean, boolean, boolean, int):int");
        }

        public static boolean checkPermission(Context context, PermissionManagerServiceInternal permissionManagerServiceInternal, String str, int i, Set set) {
            AppPrelaunchManagerService appPrelaunchManagerService;
            boolean z = context.checkPermission(str, -1, i) == 0;
            if (!z && Process.isIsolated(i) && (str.equals("android.permission.RECORD_AUDIO") || str.equals("android.permission.CAPTURE_AUDIO_HOTWORD") || str.equals("android.permission.CAPTURE_AUDIO_OUTPUT") || str.equals("android.permission.CAMERA"))) {
                PermissionManagerServiceInternal.HotwordDetectionServiceProvider hotwordDetectionServiceProvider = permissionManagerServiceInternal.getHotwordDetectionServiceProvider();
                z = hotwordDetectionServiceProvider != null && i == hotwordDetectionServiceProvider.getUid();
            }
            if (z && set.contains(str) && context.checkPermission("android.permission.RENOUNCE_PERMISSIONS", -1, i) == 0) {
                return false;
            }
            if (!CoreRune.SYSFW_APP_PREL || !z || !str.startsWith("android.permission.BLUETOOTH") || (appPrelaunchManagerService = (AppPrelaunchManagerService) LocalServices.getService(AppPrelaunchManagerService.class)) == null || !appPrelaunchManagerService.isAppPrelaunched(i)) {
                return z;
            }
            appPrelaunchManagerService.stopPrelaunch(i, "App tries to use BT");
            return false;
        }

        public static int resolveProxyAttributionFlags(AttributionSource attributionSource, AttributionSource attributionSource2, boolean z, boolean z2, boolean z3, boolean z4) {
            return resolveAttributionFlags(attributionSource, attributionSource2, z, z2, z3, z4, true);
        }

        public static int resolveProxiedAttributionFlags(AttributionSource attributionSource, AttributionSource attributionSource2, boolean z, boolean z2, boolean z3, boolean z4) {
            return resolveAttributionFlags(attributionSource, attributionSource2, z, z2, z3, z4, false);
        }

        public static int resolveAttributionFlags(AttributionSource attributionSource, AttributionSource attributionSource2, boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
            if (attributionSource2 == null || !z2) {
                return 0;
            }
            int i = z4 ? 8 : 0;
            if (z5) {
                if (z3) {
                    return i | 1;
                }
                if (!z && attributionSource2.equals(attributionSource)) {
                    return i | 1;
                }
            } else {
                if (z3) {
                    return i | 4;
                }
                if (z && attributionSource2.equals(attributionSource.getNext())) {
                    return i | 1;
                }
                if (attributionSource2.getNext() == null) {
                    return i | 4;
                }
            }
            if (z && attributionSource2.equals(attributionSource)) {
                return 0;
            }
            return i | 2;
        }

        /* JADX WARN: Code restructure failed: missing block: B:46:0x00db, code lost:
        
            return 0;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public static int checkOp(android.content.Context r24, int r25, com.android.server.pm.permission.PermissionManagerServiceInternal r26, android.content.AttributionSource r27, java.lang.String r28, boolean r29, boolean r30) {
            /*
                Method dump skipped, instructions count: 224
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.permission.PermissionManagerService.PermissionCheckerService.checkOp(android.content.Context, int, com.android.server.pm.permission.PermissionManagerServiceInternal, android.content.AttributionSource, java.lang.String, boolean, boolean):int");
        }

        public static int performOpTransaction(Context context, IBinder iBinder, int i, AttributionSource attributionSource, String str, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, int i2, int i3, int i4, int i5) {
            AppOpsManager appOpsManager;
            int i6;
            int i7;
            int noteProxyOpNoThrow;
            String str2;
            int i8;
            int i9;
            int i10;
            String str3;
            String str4;
            String str5;
            String str6;
            AppOpsManager appOpsManager2;
            int startProxyOpNoThrow;
            int uid;
            String packageName;
            String attributionTag;
            AppOpsManager appOpsManager3 = (AppOpsManager) context.getSystemService(AppOpsManager.class);
            AttributionSource next = !z5 ? attributionSource : attributionSource.getNext();
            if (!z) {
                String resolvePackageName = resolvePackageName(context, next);
                if (resolvePackageName == null) {
                    return 2;
                }
                int unsafeCheckOpRawNoThrow = appOpsManager3.unsafeCheckOpRawNoThrow(i, next.getUid(), resolvePackageName);
                AttributionSource next2 = next.getNext();
                if (z4 || unsafeCheckOpRawNoThrow != 0 || next2 == null) {
                    return unsafeCheckOpRawNoThrow;
                }
                String resolvePackageName2 = resolvePackageName(context, next2);
                if (resolvePackageName2 == null) {
                    return 2;
                }
                return appOpsManager3.unsafeCheckOpRawNoThrow(i, next2.getUid(), resolvePackageName2);
            }
            if (z2) {
                AttributionSource resolveAttributionSource = resolveAttributionSource(context, next);
                if (resolveAttributionSource.getPackageName() == null) {
                    return 2;
                }
                if (i2 == -1 || i2 == i) {
                    str2 = XmlUtils.STRING_ARRAY_SEPARATOR;
                    i8 = i;
                    i9 = 0;
                } else {
                    int uid2 = resolveAttributionSource.getUid();
                    str2 = XmlUtils.STRING_ARRAY_SEPARATOR;
                    i9 = appOpsManager3.checkOpNoThrow(i, uid2, resolveAttributionSource.getPackageName());
                    if (i9 == 2) {
                        return i9;
                    }
                    i8 = i2;
                }
                if (z4) {
                    try {
                        uid = resolveAttributionSource.getUid();
                        packageName = resolveAttributionSource.getPackageName();
                        attributionTag = resolveAttributionSource.getAttributionTag();
                        i10 = i9;
                        str3 = "Datasource ";
                        str4 = " protecting data with platform defined runtime permission ";
                        str5 = " while not having ";
                        str6 = "android.permission.UPDATE_APP_OPS_STATS";
                        appOpsManager2 = appOpsManager3;
                    } catch (SecurityException unused) {
                        i10 = i9;
                        str3 = "Datasource ";
                        str4 = " protecting data with platform defined runtime permission ";
                        str5 = " while not having ";
                        str6 = "android.permission.UPDATE_APP_OPS_STATS";
                        appOpsManager2 = appOpsManager3;
                    }
                    try {
                        startProxyOpNoThrow = appOpsManager3.startOpNoThrow(iBinder, i8, uid, packageName, false, attributionTag, str, i3, i5);
                    } catch (SecurityException unused2) {
                        Slog.w(PermissionManagerService.LOG_TAG, str3 + attributionSource + str4 + AppOpsManager.opToPermission(i) + str5 + str6);
                        startProxyOpNoThrow = appOpsManager2.startProxyOpNoThrow(iBinder, i2, attributionSource, str, z3, i3, i4, i5);
                        return Math.max(i10, startProxyOpNoThrow);
                    }
                } else {
                    i10 = i9;
                    int i11 = i8;
                    String str7 = str2;
                    try {
                        startProxyOpNoThrow = appOpsManager3.startProxyOpNoThrow(iBinder, i11, resolveAttributionSource, str, z3, i3, i4, i5);
                    } catch (SecurityException e) {
                        String str8 = "Security exception for op " + i11 + " with source " + attributionSource.getUid() + str7 + attributionSource.getPackageName() + ", " + attributionSource.getNextUid() + str7 + attributionSource.getNextPackageName();
                        if (attributionSource.getNext() != null) {
                            AttributionSource next3 = attributionSource.getNext();
                            str8 = str8 + ", " + next3.getNextPackageName() + str7 + next3.getNextUid();
                        }
                        throw new SecurityException(str8 + str7 + e.getMessage());
                    }
                }
                return Math.max(i10, startProxyOpNoThrow);
            }
            AttributionSource resolveAttributionSource2 = resolveAttributionSource(context, next);
            if (resolveAttributionSource2.getPackageName() == null) {
                return 2;
            }
            if (i2 == -1 || i2 == i) {
                appOpsManager = appOpsManager3;
                i6 = i;
                i7 = 0;
            } else {
                appOpsManager = appOpsManager3;
                i7 = appOpsManager.checkOpNoThrow(i, resolveAttributionSource2.getUid(), resolveAttributionSource2.getPackageName());
                if (i7 == 2) {
                    return i7;
                }
                i6 = i2;
            }
            if (z4) {
                try {
                    noteProxyOpNoThrow = appOpsManager.noteOpNoThrow(i6, resolveAttributionSource2.getUid(), resolveAttributionSource2.getPackageName(), resolveAttributionSource2.getAttributionTag(), str);
                } catch (SecurityException unused3) {
                    Slog.w(PermissionManagerService.LOG_TAG, "Datasource " + attributionSource + " protecting data with platform defined runtime permission " + AppOpsManager.opToPermission(i) + " while not having android.permission.UPDATE_APP_OPS_STATS");
                    noteProxyOpNoThrow = appOpsManager.noteProxyOpNoThrow(i6, attributionSource, str, z3);
                }
            } else {
                try {
                    noteProxyOpNoThrow = appOpsManager.noteProxyOpNoThrow(i6, resolveAttributionSource2, str, z3);
                } catch (SecurityException e2) {
                    String str9 = "Security exception for op " + i6 + " with source " + attributionSource.getUid() + XmlUtils.STRING_ARRAY_SEPARATOR + attributionSource.getPackageName() + ", " + attributionSource.getNextUid() + XmlUtils.STRING_ARRAY_SEPARATOR + attributionSource.getNextPackageName();
                    if (attributionSource.getNext() != null) {
                        AttributionSource next4 = attributionSource.getNext();
                        str9 = str9 + ", " + next4.getNextPackageName() + XmlUtils.STRING_ARRAY_SEPARATOR + next4.getNextUid();
                    }
                    throw new SecurityException(str9 + XmlUtils.STRING_ARRAY_SEPARATOR + e2.getMessage());
                }
            }
            return Math.max(i7, noteProxyOpNoThrow);
        }

        public static int getAttributionChainId(boolean z, AttributionSource attributionSource) {
            if (attributionSource == null || attributionSource.getNext() == null || !z) {
                return -1;
            }
            AtomicInteger atomicInteger = sAttributionChainIds;
            int incrementAndGet = atomicInteger.incrementAndGet();
            if (incrementAndGet >= 0) {
                return incrementAndGet;
            }
            atomicInteger.set(0);
            return 0;
        }

        public static String resolvePackageName(Context context, AttributionSource attributionSource) {
            if (attributionSource.getPackageName() != null) {
                return attributionSource.getPackageName();
            }
            String[] packagesForUid = context.getPackageManager().getPackagesForUid(attributionSource.getUid());
            if (packagesForUid != null) {
                return packagesForUid[0];
            }
            return AppOpsManager.resolvePackageName(attributionSource.getUid(), attributionSource.getPackageName());
        }

        public static AttributionSource resolveAttributionSource(Context context, AttributionSource attributionSource) {
            return attributionSource.getPackageName() != null ? attributionSource : attributionSource.withPackageName(resolvePackageName(context, attributionSource));
        }
    }

    /* loaded from: classes3.dex */
    public final class RegisteredAttribution {
        public final IBinder.DeathRecipient mDeathRecipient;
        public final AtomicBoolean mFinished = new AtomicBoolean(false);
        public final IBinder mToken;

        public RegisteredAttribution(final Context context, final int i, final AttributionSource attributionSource, final boolean z) {
            IBinder.DeathRecipient deathRecipient = new IBinder.DeathRecipient() { // from class: com.android.server.pm.permission.PermissionManagerService$RegisteredAttribution$$ExternalSyntheticLambda0
                @Override // android.os.IBinder.DeathRecipient
                public final void binderDied() {
                    PermissionManagerService.RegisteredAttribution.this.lambda$new$0(context, i, attributionSource, z);
                }
            };
            this.mDeathRecipient = deathRecipient;
            IBinder token = attributionSource.getToken();
            this.mToken = token;
            if (token != null) {
                try {
                    token.linkToDeath(deathRecipient, 0);
                } catch (RemoteException unused) {
                    this.mDeathRecipient.binderDied();
                }
            }
        }

        public /* synthetic */ void lambda$new$0(Context context, int i, AttributionSource attributionSource, boolean z) {
            if (unregister()) {
                PermissionCheckerService.finishDataDelivery(context, i, attributionSource.asState(), z);
            }
        }

        public boolean unregister() {
            if (!this.mFinished.compareAndSet(false, true)) {
                return false;
            }
            try {
                IBinder iBinder = this.mToken;
                if (iBinder != null) {
                    iBinder.unlinkToDeath(this.mDeathRecipient, 0);
                }
            } catch (NoSuchElementException unused) {
            }
            return true;
        }
    }
}
