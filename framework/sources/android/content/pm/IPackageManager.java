package android.content.pm;

import android.Manifest;
import android.app.ActivityThread;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.IDexModuleRegisterCallback;
import android.content.pm.IMemorySaverPackageMoveObserver;
import android.content.pm.IOnChecksumsReadyListener;
import android.content.pm.IPackageDataObserver;
import android.content.pm.IPackageDeleteObserver;
import android.content.pm.IPackageDeleteObserver2;
import android.content.pm.IPackageInstaller;
import android.content.pm.IPackageManager;
import android.content.pm.IPackageMoveObserver;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageManager;
import android.content.pm.dex.IArtManager;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.IRemoteCallback;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.PermissionEnforcer;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.os.UserHandle;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

/* loaded from: classes.dex */
public interface IPackageManager extends IInterface {
    boolean activitySupportsIntentAsUser(ComponentName componentName, Intent intent, String str, int i) throws RemoteException;

    void addCrossProfileIntentFilter(IntentFilter intentFilter, String str, int i, int i2, int i3) throws RemoteException;

    boolean addPermission(PermissionInfo permissionInfo) throws RemoteException;

    boolean addPermissionAsync(PermissionInfo permissionInfo) throws RemoteException;

    void addPersistentPreferredActivity(IntentFilter intentFilter, ComponentName componentName, int i) throws RemoteException;

    void addPreferredActivity(IntentFilter intentFilter, int i, ComponentName[] componentNameArr, ComponentName componentName, int i2, boolean z) throws RemoteException;

    boolean applyRuntimePermissionsForAllApplicationsForMDM(int i, int i2) throws RemoteException;

    boolean applyRuntimePermissionsForMDM(String str, List<String> list, int i, int i2) throws RemoteException;

    boolean canForwardTo(Intent intent, String str, int i, int i2) throws RemoteException;

    boolean[] canPackageQuery(String str, String[] strArr, int i) throws RemoteException;

    boolean canRequestPackageInstalls(String str, int i) throws RemoteException;

    String[] canonicalToCurrentPackageNames(String[] strArr) throws RemoteException;

    void changeMonetizationBadgeState(String str, String str2) throws RemoteException;

    void checkPackageStartable(String str, int i) throws RemoteException;

    int checkPermission(String str, String str2, int i) throws RemoteException;

    int checkSignatures(String str, String str2, int i) throws RemoteException;

    int checkUidPermission(String str, int i) throws RemoteException;

    int checkUidSignatures(int i, int i2) throws RemoteException;

    void clearAppCategoryHintUser(String str) throws RemoteException;

    void clearApplicationProfileData(String str) throws RemoteException;

    void clearApplicationUserData(String str, IPackageDataObserver iPackageDataObserver, int i) throws RemoteException;

    void clearCrossProfileIntentFilters(int i, String str) throws RemoteException;

    void clearPackagePersistentPreferredActivities(String str, int i) throws RemoteException;

    void clearPackagePreferredActivities(String str) throws RemoteException;

    void clearPackagePreferredActivitiesAsUserForMDM(String str, int i) throws RemoteException;

    void clearPersistentPreferredActivity(IntentFilter intentFilter, int i) throws RemoteException;

    boolean createEncAppData(String str, int i) throws RemoteException;

    String[] currentToCanonicalPackageNames(String[] strArr) throws RemoteException;

    void deleteApplicationCacheFiles(String str, IPackageDataObserver iPackageDataObserver) throws RemoteException;

    void deleteApplicationCacheFilesAsUser(String str, int i, IPackageDataObserver iPackageDataObserver) throws RemoteException;

    void deleteExistingPackageAsUser(VersionedPackage versionedPackage, IPackageDeleteObserver2 iPackageDeleteObserver2, int i) throws RemoteException;

    @Deprecated
    void deletePackageAsUser(String str, int i, IPackageDeleteObserver iPackageDeleteObserver, int i2, int i3) throws RemoteException;

    void deletePackageVersioned(VersionedPackage versionedPackage, IPackageDeleteObserver2 iPackageDeleteObserver2, int i, int i2) throws RemoteException;

    void deletePreloadsFileCache() throws RemoteException;

    void enterSafeMode() throws RemoteException;

    void extendVerificationTimeout(int i, int i2, long j) throws RemoteException;

    ResolveInfo findPersistentPreferredActivity(Intent intent, int i) throws RemoteException;

    void finishPackageInstall(int i, boolean z) throws RemoteException;

    void flushPackageRestrictionsAsUser(int i) throws RemoteException;

    void freeStorage(String str, long j, int i, IntentSender intentSender) throws RemoteException;

    void freeStorageAndNotify(String str, long j, int i, IPackageDataObserver iPackageDataObserver) throws RemoteException;

    ActivityInfo getActivityInfo(ComponentName componentName, long j, int i) throws RemoteException;

    ParceledListSlice getAllIntentFilters(String str) throws RemoteException;

    List<String> getAllPackages() throws RemoteException;

    Map<String, String> getAppCategoryHintUserMap() throws RemoteException;

    Map<String, String[]> getAppCategoryInfos(String str) throws RemoteException;

    ParcelFileDescriptor getAppMetadataFd(String str, int i) throws RemoteException;

    int getAppMetadataSource(String str, int i) throws RemoteException;

    String[] getAppOpPermissionPackages(String str, int i) throws RemoteException;

    String getAppPredictionServicePackageName() throws RemoteException;

    int getApplicationEnabledSetting(String str, int i) throws RemoteException;

    boolean getApplicationHiddenSettingAsUser(String str, int i) throws RemoteException;

    ApplicationInfo getApplicationInfo(String str, long j, int i) throws RemoteException;

    Bitmap getArchivedAppIcon(String str, UserHandle userHandle, String str2) throws RemoteException;

    ArchivedPackageParcel getArchivedPackage(String str, int i) throws RemoteException;

    IArtManager getArtManager() throws RemoteException;

    String getAttentionServicePackageName() throws RemoteException;

    boolean getBlockUninstallForUser(String str, int i) throws RemoteException;

    ChangedPackages getChangedPackages(int i, int i2) throws RemoteException;

    int getComponentEnabledSetting(ComponentName componentName, int i) throws RemoteException;

    ParceledListSlice getDeclaredSharedLibraries(String str, long j, int i) throws RemoteException;

    byte[] getDefaultAppsBackup(int i) throws RemoteException;

    String getDefaultTextClassifierPackageName() throws RemoteException;

    ComponentName getDomainVerificationAgent(int i) throws RemoteException;

    byte[] getDomainVerificationBackup(int i) throws RemoteException;

    int getFlagsForUid(int i) throws RemoteException;

    List<String> getGrantedPermissionsForMDM(String str) throws RemoteException;

    CharSequence getHarmfulAppWarning(String str, int i) throws RemoteException;

    IBinder getHoldLockToken() throws RemoteException;

    ComponentName getHomeActivities(List<ResolveInfo> list) throws RemoteException;

    String getIncidentReportApproverPackageName() throws RemoteException;

    List<String> getInitialNonStoppedSystemPackages() throws RemoteException;

    int getInstallLocation() throws RemoteException;

    int getInstallReason(String str, int i) throws RemoteException;

    InstallSourceInfo getInstallSourceInfo(String str, int i) throws RemoteException;

    ParceledListSlice getInstalledApplications(long j, int i) throws RemoteException;

    List<ModuleInfo> getInstalledModules(int i) throws RemoteException;

    ParceledListSlice getInstalledPackages(long j, int i) throws RemoteException;

    String getInstallerPackageName(String str) throws RemoteException;

    String getInstantAppAndroidId(String str, int i) throws RemoteException;

    byte[] getInstantAppCookie(String str, int i) throws RemoteException;

    Bitmap getInstantAppIcon(String str, int i) throws RemoteException;

    ComponentName getInstantAppInstallerComponent() throws RemoteException;

    ComponentName getInstantAppResolverComponent() throws RemoteException;

    ComponentName getInstantAppResolverSettingsComponent() throws RemoteException;

    ParceledListSlice getInstantApps(int i) throws RemoteException;

    InstrumentationInfo getInstrumentationInfoAsUser(ComponentName componentName, int i, int i2) throws RemoteException;

    @Deprecated
    ParceledListSlice getIntentFilterVerifications(String str) throws RemoteException;

    @Deprecated
    int getIntentVerificationStatus(String str, int i) throws RemoteException;

    KeySet getKeySetByAlias(String str, String str2) throws RemoteException;

    ResolveInfo getLastChosenActivity(Intent intent, String str, int i) throws RemoteException;

    IntentSender getLaunchIntentSenderForPackage(String str, String str2, String str3, int i) throws RemoteException;

    boolean getMetadataForIconTray(String str, String str2, int i, List<String> list) throws RemoteException;

    List<String> getMimeGroup(String str, String str2) throws RemoteException;

    ModuleInfo getModuleInfo(String str, int i) throws RemoteException;

    int getMoveStatus(int i) throws RemoteException;

    String getNameForUid(int i) throws RemoteException;

    String[] getNamesForUids(int[] iArr) throws RemoteException;

    int[] getPackageGids(String str, long j, int i) throws RemoteException;

    List<String> getPackageGrantedPermissionsForMDM(String str) throws RemoteException;

    PackageInfo getPackageInfo(String str, long j, int i) throws RemoteException;

    PackageInfo getPackageInfoVersioned(VersionedPackage versionedPackage, long j, int i) throws RemoteException;

    IPackageInstaller getPackageInstaller() throws RemoteException;

    List<String> getPackageListForDualDarPolicy(String str) throws RemoteException;

    void getPackageSizeInfo(String str, int i, IPackageStatsObserver iPackageStatsObserver) throws RemoteException;

    int getPackageUid(String str, long j, int i) throws RemoteException;

    String[] getPackagesForUid(int i) throws RemoteException;

    ParceledListSlice getPackagesHoldingPermissions(String[] strArr, long j, int i) throws RemoteException;

    String getPermissionControllerPackageName() throws RemoteException;

    PermissionGroupInfo getPermissionGroupInfo(String str, int i) throws RemoteException;

    ParceledListSlice getPersistentApplications(int i) throws RemoteException;

    int getPreferredActivities(List<IntentFilter> list, List<ComponentName> list2, String str) throws RemoteException;

    byte[] getPreferredActivityBackup(int i) throws RemoteException;

    int getPrivateFlagsForUid(int i) throws RemoteException;

    PackageManager.Property getPropertyAsUser(String str, String str2, String str3, int i) throws RemoteException;

    ProviderInfo getProviderInfo(ComponentName componentName, long j, int i) throws RemoteException;

    ActivityInfo getReceiverInfo(ComponentName componentName, long j, int i) throws RemoteException;

    List<String> getRequestedRuntimePermissionsForMDM(String str) throws RemoteException;

    String getRotationResolverPackageName() throws RemoteException;

    int getRuntimePermissionsVersion(int i) throws RemoteException;

    String getSdkSandboxPackageName() throws RemoteException;

    ServiceInfo getServiceInfo(ComponentName componentName, long j, int i) throws RemoteException;

    String getServicesSystemSharedLibraryPackageName() throws RemoteException;

    String getSetupWizardPackageName() throws RemoteException;

    ParceledListSlice getSharedLibraries(String str, long j, int i) throws RemoteException;

    String getSharedSystemSharedLibraryPackageName() throws RemoteException;

    KeySet getSigningKeySet(String str) throws RemoteException;

    String getSplashScreenTheme(String str, int i) throws RemoteException;

    Bundle getSuspendedPackageAppExtras(String str, int i) throws RemoteException;

    String getSuspendingPackage(String str, int i) throws RemoteException;

    ParceledListSlice getSystemAvailableFeatures() throws RemoteException;

    String getSystemCaptionsServicePackageName() throws RemoteException;

    @Deprecated
    String[] getSystemSharedLibraryNames() throws RemoteException;

    Map<String, String> getSystemSharedLibraryNamesAndPaths() throws RemoteException;

    String getSystemTextClassifierPackageName() throws RemoteException;

    int getTargetSdkVersion(String str) throws RemoteException;

    int getUidForSharedUser(String str) throws RemoteException;

    ParceledListSlice getUnknownSourcePackagesAsUser(long j, int i) throws RemoteException;

    String[] getUnsuspendablePackagesForUser(String[] strArr, int i) throws RemoteException;

    int getUserMinAspectRatio(String str, int i) throws RemoteException;

    VerifierDeviceIdentity getVerifierDeviceIdentity() throws RemoteException;

    String getWellbeingPackageName() throws RemoteException;

    void grantRuntimePermission(String str, String str2, int i) throws RemoteException;

    boolean hasSigningCertificate(String str, byte[] bArr, int i) throws RemoteException;

    boolean hasSystemFeature(String str, int i) throws RemoteException;

    boolean hasSystemUidErrors() throws RemoteException;

    boolean hasUidSigningCertificate(int i, byte[] bArr, int i2) throws RemoteException;

    void holdLock(IBinder iBinder, int i) throws RemoteException;

    int installExistingPackageAsUser(String str, int i, int i2, int i3, List<String> list) throws RemoteException;

    boolean isAppArchivable(String str, UserHandle userHandle) throws RemoteException;

    boolean isAutoRevokeWhitelisted(String str) throws RemoteException;

    boolean isDeviceUpgrading() throws RemoteException;

    boolean isFirstBoot() throws RemoteException;

    boolean isInstantApp(String str, int i) throws RemoteException;

    boolean isPackageAutoDisabled(String str, int i) throws RemoteException;

    boolean isPackageAvailable(String str, int i) throws RemoteException;

    boolean isPackageDeviceAdminOnAnyUser(String str) throws RemoteException;

    boolean isPackageQuarantinedForUser(String str, int i) throws RemoteException;

    boolean isPackageSignedByKeySet(String str, KeySet keySet) throws RemoteException;

    boolean isPackageSignedByKeySetExactly(String str, KeySet keySet) throws RemoteException;

    boolean isPackageStateProtected(String str, int i) throws RemoteException;

    boolean isPackageStoppedForUser(String str, int i) throws RemoteException;

    boolean isPackageSuspendedForUser(String str, int i) throws RemoteException;

    boolean isProtectedBroadcast(String str) throws RemoteException;

    boolean isSafeMode() throws RemoteException;

    boolean isStorageLow() throws RemoteException;

    boolean isSystemCompressedPackage(String str, int i) throws RemoteException;

    boolean isUidPrivileged(int i) throws RemoteException;

    boolean isUnknownSourcePackage(String str) throws RemoteException;

    void logAppProcessStartIfNeeded(String str, String str2, int i, String str3, String str4, int i2) throws RemoteException;

    void makeProviderVisible(int i, String str) throws RemoteException;

    void makeUidVisible(int i, int i2) throws RemoteException;

    int movePackage(String str, String str2) throws RemoteException;

    int movePackageToSd(String str, String str2, IMemorySaverPackageMoveObserver iMemorySaverPackageMoveObserver) throws RemoteException;

    int movePrimaryStorage(String str) throws RemoteException;

    void notifyDexLoad(String str, Map<String, String> map, String str2) throws RemoteException;

    void notifyPackageUse(String str, int i) throws RemoteException;

    void notifyPackagesReplacedReceived(String[] strArr) throws RemoteException;

    void overrideLabelAndIcon(ComponentName componentName, String str, int i, int i2) throws RemoteException;

    int performDexOptForADCP(String str, boolean z) throws RemoteException;

    boolean performDexOptMode(String str, boolean z, String str2, boolean z2, boolean z3, String str3) throws RemoteException;

    boolean performDexOptSecondary(String str, String str2, boolean z) throws RemoteException;

    ParceledListSlice queryContentProviders(String str, int i, long j, String str2) throws RemoteException;

    ParceledListSlice queryInstrumentationAsUser(String str, int i, int i2) throws RemoteException;

    ParceledListSlice queryIntentActivities(Intent intent, String str, long j, int i) throws RemoteException;

    ParceledListSlice queryIntentActivityOptions(ComponentName componentName, Intent[] intentArr, String[] strArr, Intent intent, String str, long j, int i) throws RemoteException;

    ParceledListSlice queryIntentContentProviders(Intent intent, String str, long j, int i) throws RemoteException;

    ParceledListSlice queryIntentReceivers(Intent intent, String str, long j, int i) throws RemoteException;

    ParceledListSlice queryIntentServices(Intent intent, String str, long j, int i) throws RemoteException;

    ParceledListSlice queryProperty(String str, int i) throws RemoteException;

    void querySyncProviders(List<String> list, List<ProviderInfo> list2) throws RemoteException;

    void registerDexModule(String str, String str2, boolean z, IDexModuleRegisterCallback iDexModuleRegisterCallback) throws RemoteException;

    void registerMoveCallback(IPackageMoveObserver iPackageMoveObserver) throws RemoteException;

    void registerPackageMonitorCallback(IRemoteCallback iRemoteCallback, int i) throws RemoteException;

    void relinquishUpdateOwnership(String str) throws RemoteException;

    boolean removeCrossProfileIntentFilter(IntentFilter intentFilter, String str, int i, int i2, int i3) throws RemoteException;

    boolean removeEncPkgDir(int i, String str) throws RemoteException;

    boolean removeEncUserDir(int i) throws RemoteException;

    void removePermission(String str) throws RemoteException;

    void replacePreferredActivity(IntentFilter intentFilter, int i, ComponentName[] componentNameArr, ComponentName componentName, int i2) throws RemoteException;

    void requestPackageChecksums(String str, boolean z, int i, int i2, List list, IOnChecksumsReadyListener iOnChecksumsReadyListener, int i3) throws RemoteException;

    void resetApplicationPreferences(int i) throws RemoteException;

    ProviderInfo resolveContentProvider(String str, long j, int i) throws RemoteException;

    ResolveInfo resolveIntent(Intent intent, String str, long j, int i) throws RemoteException;

    ResolveInfo resolveService(Intent intent, String str, long j, int i) throws RemoteException;

    void restoreDefaultApps(byte[] bArr, int i) throws RemoteException;

    void restoreDomainVerification(byte[] bArr, int i) throws RemoteException;

    void restoreLabelAndIcon(ComponentName componentName, int i) throws RemoteException;

    void restorePreferredActivities(byte[] bArr, int i) throws RemoteException;

    boolean semIsPermissionRevokedByUserFixed(String str, String str2, int i) throws RemoteException;

    void sendDeviceCustomizationReadyBroadcast() throws RemoteException;

    void setAppCategoryHintUser(String str, int i) throws RemoteException;

    void setApplicationCategoryHint(String str, int i, String str2) throws RemoteException;

    void setApplicationEnabledSetting(String str, int i, int i2, int i3, String str2) throws RemoteException;

    boolean setApplicationHiddenSettingAsUser(String str, boolean z, int i) throws RemoteException;

    boolean setBlockUninstallForUser(String str, boolean z, int i) throws RemoteException;

    void setComponentEnabledSetting(ComponentName componentName, int i, int i2, int i3, String str) throws RemoteException;

    void setComponentEnabledSettings(List<PackageManager.ComponentEnabledSetting> list, int i, String str) throws RemoteException;

    String[] setDistractingPackageRestrictionsAsUser(String[] strArr, int i, int i2) throws RemoteException;

    void setHarmfulAppWarning(String str, CharSequence charSequence, int i) throws RemoteException;

    void setHomeActivity(ComponentName componentName, int i) throws RemoteException;

    boolean setInstallLocation(int i) throws RemoteException;

    void setInstallerPackageName(String str, String str2) throws RemoteException;

    boolean setInstantAppCookie(String str, byte[] bArr, int i) throws RemoteException;

    void setKeepUninstalledPackages(List<String> list) throws RemoteException;

    void setLastChosenActivity(Intent intent, String str, int i, IntentFilter intentFilter, int i2, ComponentName componentName) throws RemoteException;

    int setLicensePermissionsForMDM(String str) throws RemoteException;

    void setMimeGroup(String str, String str2, List<String> list) throws RemoteException;

    void setPackageStoppedState(String str, boolean z, int i) throws RemoteException;

    String[] setPackagesSuspendedAsUser(String[] strArr, boolean z, PersistableBundle persistableBundle, PersistableBundle persistableBundle2, SuspendDialogInfo suspendDialogInfo, int i, String str, int i2, int i3) throws RemoteException;

    boolean setRequiredForSystemUser(String str, boolean z) throws RemoteException;

    void setRuntimePermissionsVersion(int i, int i2) throws RemoteException;

    void setSplashScreenTheme(String str, String str2, int i) throws RemoteException;

    void setSystemAppHiddenUntilInstalled(String str, boolean z) throws RemoteException;

    boolean setSystemAppInstallState(String str, boolean z, int i) throws RemoteException;

    void setUpdateAvailable(String str, boolean z) throws RemoteException;

    void setUserMinAspectRatio(String str, int i, int i2) throws RemoteException;

    boolean shouldAppSupportBadgeIcon(String str) throws RemoteException;

    void unregisterMoveCallback(IPackageMoveObserver iPackageMoveObserver) throws RemoteException;

    void unregisterPackageMonitorCallback(IRemoteCallback iRemoteCallback) throws RemoteException;

    @Deprecated
    boolean updateIntentVerificationStatus(String str, int i, int i2) throws RemoteException;

    @Deprecated
    void verifyIntentFilter(int i, int i2, List<String> list) throws RemoteException;

    void verifyPendingInstall(int i, int i2) throws RemoteException;

    boolean waitForHandler(long j, boolean z) throws RemoteException;

    public static class Default implements IPackageManager {
        @Override // android.content.pm.IPackageManager
        public void checkPackageStartable(String packageName, int userId) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public boolean isPackageAvailable(String packageName, int userId) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public PackageInfo getPackageInfo(String packageName, long flags, int userId) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public PackageInfo getPackageInfoVersioned(VersionedPackage versionedPackage, long flags, int userId) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public int getPackageUid(String packageName, long flags, int userId) throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManager
        public int[] getPackageGids(String packageName, long flags, int userId) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public String[] currentToCanonicalPackageNames(String[] names) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public String[] canonicalToCurrentPackageNames(String[] names) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public ApplicationInfo getApplicationInfo(String packageName, long flags, int userId) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public int getTargetSdkVersion(String packageName) throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManager
        public ActivityInfo getActivityInfo(ComponentName className, long flags, int userId) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public boolean activitySupportsIntentAsUser(ComponentName className, Intent intent, String resolvedType, int userId) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public ActivityInfo getReceiverInfo(ComponentName className, long flags, int userId) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public ServiceInfo getServiceInfo(ComponentName className, long flags, int userId) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public ProviderInfo getProviderInfo(ComponentName className, long flags, int userId) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public boolean isProtectedBroadcast(String actionName) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public int checkSignatures(String pkg1, String pkg2, int userId) throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManager
        public int checkUidSignatures(int uid1, int uid2) throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManager
        public List<String> getAllPackages() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public String[] getPackagesForUid(int uid) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public String getNameForUid(int uid) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public String[] getNamesForUids(int[] uids) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public int getUidForSharedUser(String sharedUserName) throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManager
        public int getFlagsForUid(int uid) throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManager
        public int getPrivateFlagsForUid(int uid) throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManager
        public boolean isUidPrivileged(int uid) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public ResolveInfo resolveIntent(Intent intent, String resolvedType, long flags, int userId) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public ResolveInfo findPersistentPreferredActivity(Intent intent, int userId) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public boolean canForwardTo(Intent intent, String resolvedType, int sourceUserId, int targetUserId) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public ParceledListSlice queryIntentActivities(Intent intent, String resolvedType, long flags, int userId) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public ParceledListSlice queryIntentActivityOptions(ComponentName caller, Intent[] specifics, String[] specificTypes, Intent intent, String resolvedType, long flags, int userId) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public ParceledListSlice queryIntentReceivers(Intent intent, String resolvedType, long flags, int userId) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public ResolveInfo resolveService(Intent intent, String resolvedType, long flags, int userId) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public ParceledListSlice queryIntentServices(Intent intent, String resolvedType, long flags, int userId) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public ParceledListSlice queryIntentContentProviders(Intent intent, String resolvedType, long flags, int userId) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public ParceledListSlice getInstalledPackages(long flags, int userId) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public ParcelFileDescriptor getAppMetadataFd(String packageName, int userId) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public ParceledListSlice getPackagesHoldingPermissions(String[] permissions, long flags, int userId) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public ParceledListSlice getInstalledApplications(long flags, int userId) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public ParceledListSlice getPersistentApplications(int flags) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public ProviderInfo resolveContentProvider(String name, long flags, int userId) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public void querySyncProviders(List<String> outNames, List<ProviderInfo> outInfo) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public ParceledListSlice queryContentProviders(String processName, int uid, long flags, String metaDataKey) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public InstrumentationInfo getInstrumentationInfoAsUser(ComponentName className, int flags, int userId) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public ParceledListSlice queryInstrumentationAsUser(String targetPackage, int flags, int userId) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public void finishPackageInstall(int token, boolean didLaunch) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void setInstallerPackageName(String targetPackage, String installerPackageName) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void relinquishUpdateOwnership(String targetPackage) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void setApplicationCategoryHint(String packageName, int categoryHint, String callerPackageName) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void deletePackageAsUser(String packageName, int versionCode, IPackageDeleteObserver observer, int userId, int flags) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void deletePackageVersioned(VersionedPackage versionedPackage, IPackageDeleteObserver2 observer, int userId, int flags) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void deleteExistingPackageAsUser(VersionedPackage versionedPackage, IPackageDeleteObserver2 observer, int userId) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public String getInstallerPackageName(String packageName) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public InstallSourceInfo getInstallSourceInfo(String packageName, int userId) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public void resetApplicationPreferences(int userId) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public ResolveInfo getLastChosenActivity(Intent intent, String resolvedType, int flags) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public void setLastChosenActivity(Intent intent, String resolvedType, int flags, IntentFilter filter, int match, ComponentName activity) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void addPreferredActivity(IntentFilter filter, int match, ComponentName[] set, ComponentName activity, int userId, boolean removeExisting) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void replacePreferredActivity(IntentFilter filter, int match, ComponentName[] set, ComponentName activity, int userId) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void clearPackagePreferredActivities(String packageName) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public int getPreferredActivities(List<IntentFilter> outFilters, List<ComponentName> outActivities, String packageName) throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManager
        public void addPersistentPreferredActivity(IntentFilter filter, ComponentName activity, int userId) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void clearPackagePersistentPreferredActivities(String packageName, int userId) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void clearPersistentPreferredActivity(IntentFilter filter, int userId) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void addCrossProfileIntentFilter(IntentFilter intentFilter, String ownerPackage, int sourceUserId, int targetUserId, int flags) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public boolean removeCrossProfileIntentFilter(IntentFilter intentFilter, String ownerPackage, int sourceUserId, int targetUserId, int flags) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public void clearCrossProfileIntentFilters(int sourceUserId, String ownerPackage) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public String[] setDistractingPackageRestrictionsAsUser(String[] packageNames, int restrictionFlags, int userId) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public String[] setPackagesSuspendedAsUser(String[] packageNames, boolean suspended, PersistableBundle appExtras, PersistableBundle launcherExtras, SuspendDialogInfo dialogInfo, int flags, String suspendingPackage, int suspendingUserId, int targetUserId) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public String[] getUnsuspendablePackagesForUser(String[] packageNames, int userId) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public boolean isPackageSuspendedForUser(String packageName, int userId) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public boolean isPackageQuarantinedForUser(String packageName, int userId) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public boolean isPackageStoppedForUser(String packageName, int userId) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public Bundle getSuspendedPackageAppExtras(String packageName, int userId) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public String getSuspendingPackage(String packageName, int userId) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public byte[] getPreferredActivityBackup(int userId) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public void restorePreferredActivities(byte[] backup, int userId) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public byte[] getDefaultAppsBackup(int userId) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public void restoreDefaultApps(byte[] backup, int userId) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public byte[] getDomainVerificationBackup(int userId) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public void restoreDomainVerification(byte[] backup, int userId) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public ComponentName getHomeActivities(List<ResolveInfo> outHomeCandidates) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public void setHomeActivity(ComponentName className, int userId) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void overrideLabelAndIcon(ComponentName componentName, String nonLocalizedLabel, int icon, int userId) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void restoreLabelAndIcon(ComponentName componentName, int userId) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void setComponentEnabledSetting(ComponentName componentName, int newState, int flags, int userId, String callingPackage) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void setComponentEnabledSettings(List<PackageManager.ComponentEnabledSetting> settings, int userId, String callingPackage) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public int getComponentEnabledSetting(ComponentName componentName, int userId) throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManager
        public void setApplicationEnabledSetting(String packageName, int newState, int flags, int userId, String callingPackage) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public int getApplicationEnabledSetting(String packageName, int userId) throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManager
        public void logAppProcessStartIfNeeded(String packageName, String processName, int uid, String seinfo, String apkFile, int pid) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void flushPackageRestrictionsAsUser(int userId) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void setPackageStoppedState(String packageName, boolean stopped, int userId) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void freeStorageAndNotify(String volumeUuid, long freeStorageSize, int storageFlags, IPackageDataObserver observer) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void freeStorage(String volumeUuid, long freeStorageSize, int storageFlags, IntentSender pi) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void deleteApplicationCacheFiles(String packageName, IPackageDataObserver observer) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void deleteApplicationCacheFilesAsUser(String packageName, int userId, IPackageDataObserver observer) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void clearApplicationUserData(String packageName, IPackageDataObserver observer, int userId) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void clearApplicationProfileData(String packageName) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void getPackageSizeInfo(String packageName, int userHandle, IPackageStatsObserver observer) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public String[] getSystemSharedLibraryNames() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public Map<String, String> getSystemSharedLibraryNamesAndPaths() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public ParceledListSlice getSystemAvailableFeatures() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public boolean hasSystemFeature(String name, int version) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public List<String> getInitialNonStoppedSystemPackages() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public void enterSafeMode() throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public boolean isSafeMode() throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public boolean hasSystemUidErrors() throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public void notifyPackageUse(String packageName, int reason) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void notifyDexLoad(String loadingPackageName, Map<String, String> classLoaderContextMap, String loaderIsa) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void registerDexModule(String packageName, String dexModulePath, boolean isSharedModule, IDexModuleRegisterCallback callback) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public boolean performDexOptMode(String packageName, boolean checkProfiles, String targetCompilerFilter, boolean force, boolean bootComplete, String splitName) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public boolean performDexOptSecondary(String packageName, String targetCompilerFilter, boolean force) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public int performDexOptForADCP(String packageName, boolean force) throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManager
        public int getMoveStatus(int moveId) throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManager
        public void registerMoveCallback(IPackageMoveObserver callback) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void unregisterMoveCallback(IPackageMoveObserver callback) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public int movePackage(String packageName, String volumeUuid) throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManager
        public int movePackageToSd(String packageName, String volumeUuid, IMemorySaverPackageMoveObserver observer) throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManager
        public int movePrimaryStorage(String volumeUuid) throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManager
        public boolean setInstallLocation(int loc) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public int getInstallLocation() throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManager
        public int installExistingPackageAsUser(String packageName, int userId, int installFlags, int installReason, List<String> whiteListedPermissions) throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManager
        public void verifyPendingInstall(int id, int verificationCode) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void extendVerificationTimeout(int id, int verificationCodeAtTimeout, long millisecondsToDelay) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void verifyIntentFilter(int id, int verificationCode, List<String> failedDomains) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public int getIntentVerificationStatus(String packageName, int userId) throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManager
        public boolean updateIntentVerificationStatus(String packageName, int status, int userId) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public ParceledListSlice getIntentFilterVerifications(String packageName) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public ParceledListSlice getAllIntentFilters(String packageName) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public VerifierDeviceIdentity getVerifierDeviceIdentity() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public boolean isFirstBoot() throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public boolean isDeviceUpgrading() throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public boolean isStorageLow() throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public boolean setApplicationHiddenSettingAsUser(String packageName, boolean hidden, int userId) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public boolean getApplicationHiddenSettingAsUser(String packageName, int userId) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public void setSystemAppHiddenUntilInstalled(String packageName, boolean hidden) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public boolean setSystemAppInstallState(String packageName, boolean installed, int userId) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public IPackageInstaller getPackageInstaller() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public boolean setBlockUninstallForUser(String packageName, boolean blockUninstall, int userId) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public boolean getBlockUninstallForUser(String packageName, int userId) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public KeySet getKeySetByAlias(String packageName, String alias) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public KeySet getSigningKeySet(String packageName) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public boolean isPackageSignedByKeySet(String packageName, KeySet ks) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public boolean isPackageSignedByKeySetExactly(String packageName, KeySet ks) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public String getPermissionControllerPackageName() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public String getSdkSandboxPackageName() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public ParceledListSlice getInstantApps(int userId) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public byte[] getInstantAppCookie(String packageName, int userId) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public boolean setInstantAppCookie(String packageName, byte[] cookie, int userId) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public Bitmap getInstantAppIcon(String packageName, int userId) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public boolean isInstantApp(String packageName, int userId) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public boolean setRequiredForSystemUser(String packageName, boolean systemUserApp) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public void setUpdateAvailable(String packageName, boolean updateAvaialble) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public String getServicesSystemSharedLibraryPackageName() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public String getSharedSystemSharedLibraryPackageName() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public ChangedPackages getChangedPackages(int sequenceNumber, int userId) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public boolean isPackageDeviceAdminOnAnyUser(String packageName) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public int getInstallReason(String packageName, int userId) throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManager
        public ParceledListSlice getSharedLibraries(String packageName, long flags, int userId) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public ParceledListSlice getDeclaredSharedLibraries(String packageName, long flags, int userId) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public boolean canRequestPackageInstalls(String packageName, int userId) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public void deletePreloadsFileCache() throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public ComponentName getInstantAppResolverComponent() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public ComponentName getInstantAppResolverSettingsComponent() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public ComponentName getInstantAppInstallerComponent() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public String getInstantAppAndroidId(String packageName, int userId) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public IArtManager getArtManager() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public void setHarmfulAppWarning(String packageName, CharSequence warning, int userId) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public CharSequence getHarmfulAppWarning(String packageName, int userId) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public boolean hasSigningCertificate(String packageName, byte[] signingCertificate, int flags) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public boolean hasUidSigningCertificate(int uid, byte[] signingCertificate, int flags) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public String getDefaultTextClassifierPackageName() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public String getSystemTextClassifierPackageName() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public String getAttentionServicePackageName() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public String getRotationResolverPackageName() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public String getWellbeingPackageName() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public String getAppPredictionServicePackageName() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public String getSystemCaptionsServicePackageName() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public String getSetupWizardPackageName() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public String getIncidentReportApproverPackageName() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public boolean isPackageStateProtected(String packageName, int userId) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public void sendDeviceCustomizationReadyBroadcast() throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public List<ModuleInfo> getInstalledModules(int flags) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public ModuleInfo getModuleInfo(String packageName, int flags) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public int getRuntimePermissionsVersion(int userId) throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManager
        public void setRuntimePermissionsVersion(int version, int userId) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void notifyPackagesReplacedReceived(String[] packages) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void requestPackageChecksums(String packageName, boolean includeSplits, int optional, int required, List trustedInstallers, IOnChecksumsReadyListener onChecksumsReadyListener, int userId) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public IntentSender getLaunchIntentSenderForPackage(String packageName, String callingPackage, String featureId, int userId) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public String[] getAppOpPermissionPackages(String permissionName, int userId) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public PermissionGroupInfo getPermissionGroupInfo(String name, int flags) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public boolean addPermission(PermissionInfo info) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public boolean addPermissionAsync(PermissionInfo info) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public void removePermission(String name) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public int checkPermission(String permName, String pkgName, int userId) throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManager
        public void grantRuntimePermission(String packageName, String permissionName, int userId) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public int checkUidPermission(String permName, int uid) throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManager
        public void setMimeGroup(String packageName, String group, List<String> mimeTypes) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public String getSplashScreenTheme(String packageName, int userId) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public void setSplashScreenTheme(String packageName, String themeName, int userId) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public int getUserMinAspectRatio(String packageName, int userId) throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManager
        public void setUserMinAspectRatio(String packageName, int userId, int aspectRatio) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public List<String> getMimeGroup(String packageName, String group) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public boolean isAutoRevokeWhitelisted(String packageName) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public void makeProviderVisible(int recipientAppId, String visibleAuthority) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void makeUidVisible(int recipientAppId, int visibleUid) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public IBinder getHoldLockToken() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public void holdLock(IBinder token, int durationMs) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public PackageManager.Property getPropertyAsUser(String propertyName, String packageName, String className, int userId) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public ParceledListSlice queryProperty(String propertyName, int componentType) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public void setKeepUninstalledPackages(List<String> packageList) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public int setLicensePermissionsForMDM(String packageName) throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManager
        public List<String> getPackageGrantedPermissionsForMDM(String packageName) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public List<String> getGrantedPermissionsForMDM(String pkgName) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public void clearPackagePreferredActivitiesAsUserForMDM(String packageName, int userId) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public boolean applyRuntimePermissionsForMDM(String pkgName, List<String> permissions, int permState, int userId) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public boolean applyRuntimePermissionsForAllApplicationsForMDM(int permState, int userId) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public List<String> getRequestedRuntimePermissionsForMDM(String pkgName) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public boolean[] canPackageQuery(String sourcePackageName, String[] targetPackageNames, int userId) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public boolean waitForHandler(long timeoutMillis, boolean forBackgroundHandler) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public void registerPackageMonitorCallback(IRemoteCallback callback, int userId) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void unregisterPackageMonitorCallback(IRemoteCallback callback) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public ArchivedPackageParcel getArchivedPackage(String packageName, int userId) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public Bitmap getArchivedAppIcon(String packageName, UserHandle user, String callingPackageName) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public boolean isAppArchivable(String packageName, UserHandle user) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public int getAppMetadataSource(String packageName, int userId) throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.IPackageManager
        public ComponentName getDomainVerificationAgent(int userId) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public List<String> getPackageListForDualDarPolicy(String packageType) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public boolean createEncAppData(String packageName, int userId) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public boolean removeEncUserDir(int userId) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public boolean removeEncPkgDir(int userId, String pkgName) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public boolean getMetadataForIconTray(String packageName, String metadata, int userId, List<String> feature) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public boolean semIsPermissionRevokedByUserFixed(String permName, String packageName, int userId) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public boolean isUnknownSourcePackage(String packageName) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public ParceledListSlice getUnknownSourcePackagesAsUser(long flags, int userId) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public boolean isPackageAutoDisabled(String packageName, int uid) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public boolean isSystemCompressedPackage(String packageName, int userId) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public void changeMonetizationBadgeState(String value, String packageName) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public boolean shouldAppSupportBadgeIcon(String packageName) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IPackageManager
        public void setAppCategoryHintUser(String pkgName, int category) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public void clearAppCategoryHintUser(String pkgName) throws RemoteException {
        }

        @Override // android.content.pm.IPackageManager
        public Map<String, String> getAppCategoryHintUserMap() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IPackageManager
        public Map<String, String[]> getAppCategoryInfos(String pkgName) throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IPackageManager {
        public static final String DESCRIPTOR = "android.content.pm.IPackageManager";
        static final int TRANSACTION_activitySupportsIntentAsUser = 12;
        static final int TRANSACTION_addCrossProfileIntentFilter = 65;
        static final int TRANSACTION_addPermission = 193;
        static final int TRANSACTION_addPermissionAsync = 194;
        static final int TRANSACTION_addPersistentPreferredActivity = 62;
        static final int TRANSACTION_addPreferredActivity = 58;
        static final int TRANSACTION_applyRuntimePermissionsForAllApplicationsForMDM = 218;
        static final int TRANSACTION_applyRuntimePermissionsForMDM = 217;
        static final int TRANSACTION_canForwardTo = 29;
        static final int TRANSACTION_canPackageQuery = 220;
        static final int TRANSACTION_canRequestPackageInstalls = 162;
        static final int TRANSACTION_canonicalToCurrentPackageNames = 8;
        static final int TRANSACTION_changeMonetizationBadgeState = 239;
        static final int TRANSACTION_checkPackageStartable = 1;
        static final int TRANSACTION_checkPermission = 196;
        static final int TRANSACTION_checkSignatures = 17;
        static final int TRANSACTION_checkUidPermission = 198;
        static final int TRANSACTION_checkUidSignatures = 18;
        static final int TRANSACTION_clearAppCategoryHintUser = 242;
        static final int TRANSACTION_clearApplicationProfileData = 99;
        static final int TRANSACTION_clearApplicationUserData = 98;
        static final int TRANSACTION_clearCrossProfileIntentFilters = 67;
        static final int TRANSACTION_clearPackagePersistentPreferredActivities = 63;
        static final int TRANSACTION_clearPackagePreferredActivities = 60;
        static final int TRANSACTION_clearPackagePreferredActivitiesAsUserForMDM = 216;
        static final int TRANSACTION_clearPersistentPreferredActivity = 64;
        static final int TRANSACTION_createEncAppData = 230;
        static final int TRANSACTION_currentToCanonicalPackageNames = 7;
        static final int TRANSACTION_deleteApplicationCacheFiles = 96;
        static final int TRANSACTION_deleteApplicationCacheFilesAsUser = 97;
        static final int TRANSACTION_deleteExistingPackageAsUser = 52;
        static final int TRANSACTION_deletePackageAsUser = 50;
        static final int TRANSACTION_deletePackageVersioned = 51;
        static final int TRANSACTION_deletePreloadsFileCache = 163;
        static final int TRANSACTION_enterSafeMode = 106;
        static final int TRANSACTION_extendVerificationTimeout = 125;
        static final int TRANSACTION_findPersistentPreferredActivity = 28;
        static final int TRANSACTION_finishPackageInstall = 46;
        static final int TRANSACTION_flushPackageRestrictionsAsUser = 92;
        static final int TRANSACTION_freeStorage = 95;
        static final int TRANSACTION_freeStorageAndNotify = 94;
        static final int TRANSACTION_getActivityInfo = 11;
        static final int TRANSACTION_getAllIntentFilters = 130;
        static final int TRANSACTION_getAllPackages = 19;
        static final int TRANSACTION_getAppCategoryHintUserMap = 243;
        static final int TRANSACTION_getAppCategoryInfos = 244;
        static final int TRANSACTION_getAppMetadataFd = 37;
        static final int TRANSACTION_getAppMetadataSource = 227;
        static final int TRANSACTION_getAppOpPermissionPackages = 191;
        static final int TRANSACTION_getAppPredictionServicePackageName = 178;
        static final int TRANSACTION_getApplicationEnabledSetting = 90;
        static final int TRANSACTION_getApplicationHiddenSettingAsUser = 136;
        static final int TRANSACTION_getApplicationInfo = 9;
        static final int TRANSACTION_getArchivedAppIcon = 225;
        static final int TRANSACTION_getArchivedPackage = 224;
        static final int TRANSACTION_getArtManager = 168;
        static final int TRANSACTION_getAttentionServicePackageName = 175;
        static final int TRANSACTION_getBlockUninstallForUser = 141;
        static final int TRANSACTION_getChangedPackages = 157;
        static final int TRANSACTION_getComponentEnabledSetting = 88;
        static final int TRANSACTION_getDeclaredSharedLibraries = 161;
        static final int TRANSACTION_getDefaultAppsBackup = 78;
        static final int TRANSACTION_getDefaultTextClassifierPackageName = 173;
        static final int TRANSACTION_getDomainVerificationAgent = 228;
        static final int TRANSACTION_getDomainVerificationBackup = 80;
        static final int TRANSACTION_getFlagsForUid = 24;
        static final int TRANSACTION_getGrantedPermissionsForMDM = 215;
        static final int TRANSACTION_getHarmfulAppWarning = 170;
        static final int TRANSACTION_getHoldLockToken = 208;
        static final int TRANSACTION_getHomeActivities = 82;
        static final int TRANSACTION_getIncidentReportApproverPackageName = 181;
        static final int TRANSACTION_getInitialNonStoppedSystemPackages = 105;
        static final int TRANSACTION_getInstallLocation = 122;
        static final int TRANSACTION_getInstallReason = 159;
        static final int TRANSACTION_getInstallSourceInfo = 54;
        static final int TRANSACTION_getInstalledApplications = 39;
        static final int TRANSACTION_getInstalledModules = 184;
        static final int TRANSACTION_getInstalledPackages = 36;
        static final int TRANSACTION_getInstallerPackageName = 53;
        static final int TRANSACTION_getInstantAppAndroidId = 167;
        static final int TRANSACTION_getInstantAppCookie = 149;
        static final int TRANSACTION_getInstantAppIcon = 151;
        static final int TRANSACTION_getInstantAppInstallerComponent = 166;
        static final int TRANSACTION_getInstantAppResolverComponent = 164;
        static final int TRANSACTION_getInstantAppResolverSettingsComponent = 165;
        static final int TRANSACTION_getInstantApps = 148;
        static final int TRANSACTION_getInstrumentationInfoAsUser = 44;
        static final int TRANSACTION_getIntentFilterVerifications = 129;
        static final int TRANSACTION_getIntentVerificationStatus = 127;
        static final int TRANSACTION_getKeySetByAlias = 142;
        static final int TRANSACTION_getLastChosenActivity = 56;
        static final int TRANSACTION_getLaunchIntentSenderForPackage = 190;
        static final int TRANSACTION_getMetadataForIconTray = 233;
        static final int TRANSACTION_getMimeGroup = 204;
        static final int TRANSACTION_getModuleInfo = 185;
        static final int TRANSACTION_getMoveStatus = 115;
        static final int TRANSACTION_getNameForUid = 21;
        static final int TRANSACTION_getNamesForUids = 22;
        static final int TRANSACTION_getPackageGids = 6;
        static final int TRANSACTION_getPackageGrantedPermissionsForMDM = 214;
        static final int TRANSACTION_getPackageInfo = 3;
        static final int TRANSACTION_getPackageInfoVersioned = 4;
        static final int TRANSACTION_getPackageInstaller = 139;
        static final int TRANSACTION_getPackageListForDualDarPolicy = 229;
        static final int TRANSACTION_getPackageSizeInfo = 100;
        static final int TRANSACTION_getPackageUid = 5;
        static final int TRANSACTION_getPackagesForUid = 20;
        static final int TRANSACTION_getPackagesHoldingPermissions = 38;
        static final int TRANSACTION_getPermissionControllerPackageName = 146;
        static final int TRANSACTION_getPermissionGroupInfo = 192;
        static final int TRANSACTION_getPersistentApplications = 40;
        static final int TRANSACTION_getPreferredActivities = 61;
        static final int TRANSACTION_getPreferredActivityBackup = 76;
        static final int TRANSACTION_getPrivateFlagsForUid = 25;
        static final int TRANSACTION_getPropertyAsUser = 210;
        static final int TRANSACTION_getProviderInfo = 15;
        static final int TRANSACTION_getReceiverInfo = 13;
        static final int TRANSACTION_getRequestedRuntimePermissionsForMDM = 219;
        static final int TRANSACTION_getRotationResolverPackageName = 176;
        static final int TRANSACTION_getRuntimePermissionsVersion = 186;
        static final int TRANSACTION_getSdkSandboxPackageName = 147;
        static final int TRANSACTION_getServiceInfo = 14;
        static final int TRANSACTION_getServicesSystemSharedLibraryPackageName = 155;
        static final int TRANSACTION_getSetupWizardPackageName = 180;
        static final int TRANSACTION_getSharedLibraries = 160;
        static final int TRANSACTION_getSharedSystemSharedLibraryPackageName = 156;
        static final int TRANSACTION_getSigningKeySet = 143;
        static final int TRANSACTION_getSplashScreenTheme = 200;
        static final int TRANSACTION_getSuspendedPackageAppExtras = 74;
        static final int TRANSACTION_getSuspendingPackage = 75;
        static final int TRANSACTION_getSystemAvailableFeatures = 103;
        static final int TRANSACTION_getSystemCaptionsServicePackageName = 179;
        static final int TRANSACTION_getSystemSharedLibraryNames = 101;
        static final int TRANSACTION_getSystemSharedLibraryNamesAndPaths = 102;
        static final int TRANSACTION_getSystemTextClassifierPackageName = 174;
        static final int TRANSACTION_getTargetSdkVersion = 10;
        static final int TRANSACTION_getUidForSharedUser = 23;
        static final int TRANSACTION_getUnknownSourcePackagesAsUser = 236;
        static final int TRANSACTION_getUnsuspendablePackagesForUser = 70;
        static final int TRANSACTION_getUserMinAspectRatio = 202;
        static final int TRANSACTION_getVerifierDeviceIdentity = 131;
        static final int TRANSACTION_getWellbeingPackageName = 177;
        static final int TRANSACTION_grantRuntimePermission = 197;
        static final int TRANSACTION_hasSigningCertificate = 171;
        static final int TRANSACTION_hasSystemFeature = 104;
        static final int TRANSACTION_hasSystemUidErrors = 108;
        static final int TRANSACTION_hasUidSigningCertificate = 172;
        static final int TRANSACTION_holdLock = 209;
        static final int TRANSACTION_installExistingPackageAsUser = 123;
        static final int TRANSACTION_isAppArchivable = 226;
        static final int TRANSACTION_isAutoRevokeWhitelisted = 205;
        static final int TRANSACTION_isDeviceUpgrading = 133;
        static final int TRANSACTION_isFirstBoot = 132;
        static final int TRANSACTION_isInstantApp = 152;
        static final int TRANSACTION_isPackageAutoDisabled = 237;
        static final int TRANSACTION_isPackageAvailable = 2;
        static final int TRANSACTION_isPackageDeviceAdminOnAnyUser = 158;
        static final int TRANSACTION_isPackageQuarantinedForUser = 72;
        static final int TRANSACTION_isPackageSignedByKeySet = 144;
        static final int TRANSACTION_isPackageSignedByKeySetExactly = 145;
        static final int TRANSACTION_isPackageStateProtected = 182;
        static final int TRANSACTION_isPackageStoppedForUser = 73;
        static final int TRANSACTION_isPackageSuspendedForUser = 71;
        static final int TRANSACTION_isProtectedBroadcast = 16;
        static final int TRANSACTION_isSafeMode = 107;
        static final int TRANSACTION_isStorageLow = 134;
        static final int TRANSACTION_isSystemCompressedPackage = 238;
        static final int TRANSACTION_isUidPrivileged = 26;
        static final int TRANSACTION_isUnknownSourcePackage = 235;
        static final int TRANSACTION_logAppProcessStartIfNeeded = 91;
        static final int TRANSACTION_makeProviderVisible = 206;
        static final int TRANSACTION_makeUidVisible = 207;
        static final int TRANSACTION_movePackage = 118;
        static final int TRANSACTION_movePackageToSd = 119;
        static final int TRANSACTION_movePrimaryStorage = 120;
        static final int TRANSACTION_notifyDexLoad = 110;
        static final int TRANSACTION_notifyPackageUse = 109;
        static final int TRANSACTION_notifyPackagesReplacedReceived = 188;
        static final int TRANSACTION_overrideLabelAndIcon = 84;
        static final int TRANSACTION_performDexOptForADCP = 114;
        static final int TRANSACTION_performDexOptMode = 112;
        static final int TRANSACTION_performDexOptSecondary = 113;
        static final int TRANSACTION_queryContentProviders = 43;
        static final int TRANSACTION_queryInstrumentationAsUser = 45;
        static final int TRANSACTION_queryIntentActivities = 30;
        static final int TRANSACTION_queryIntentActivityOptions = 31;
        static final int TRANSACTION_queryIntentContentProviders = 35;
        static final int TRANSACTION_queryIntentReceivers = 32;
        static final int TRANSACTION_queryIntentServices = 34;
        static final int TRANSACTION_queryProperty = 211;
        static final int TRANSACTION_querySyncProviders = 42;
        static final int TRANSACTION_registerDexModule = 111;
        static final int TRANSACTION_registerMoveCallback = 116;
        static final int TRANSACTION_registerPackageMonitorCallback = 222;
        static final int TRANSACTION_relinquishUpdateOwnership = 48;
        static final int TRANSACTION_removeCrossProfileIntentFilter = 66;
        static final int TRANSACTION_removeEncPkgDir = 232;
        static final int TRANSACTION_removeEncUserDir = 231;
        static final int TRANSACTION_removePermission = 195;
        static final int TRANSACTION_replacePreferredActivity = 59;
        static final int TRANSACTION_requestPackageChecksums = 189;
        static final int TRANSACTION_resetApplicationPreferences = 55;
        static final int TRANSACTION_resolveContentProvider = 41;
        static final int TRANSACTION_resolveIntent = 27;
        static final int TRANSACTION_resolveService = 33;
        static final int TRANSACTION_restoreDefaultApps = 79;
        static final int TRANSACTION_restoreDomainVerification = 81;
        static final int TRANSACTION_restoreLabelAndIcon = 85;
        static final int TRANSACTION_restorePreferredActivities = 77;
        static final int TRANSACTION_semIsPermissionRevokedByUserFixed = 234;
        static final int TRANSACTION_sendDeviceCustomizationReadyBroadcast = 183;
        static final int TRANSACTION_setAppCategoryHintUser = 241;
        static final int TRANSACTION_setApplicationCategoryHint = 49;
        static final int TRANSACTION_setApplicationEnabledSetting = 89;
        static final int TRANSACTION_setApplicationHiddenSettingAsUser = 135;
        static final int TRANSACTION_setBlockUninstallForUser = 140;
        static final int TRANSACTION_setComponentEnabledSetting = 86;
        static final int TRANSACTION_setComponentEnabledSettings = 87;
        static final int TRANSACTION_setDistractingPackageRestrictionsAsUser = 68;
        static final int TRANSACTION_setHarmfulAppWarning = 169;
        static final int TRANSACTION_setHomeActivity = 83;
        static final int TRANSACTION_setInstallLocation = 121;
        static final int TRANSACTION_setInstallerPackageName = 47;
        static final int TRANSACTION_setInstantAppCookie = 150;
        static final int TRANSACTION_setKeepUninstalledPackages = 212;
        static final int TRANSACTION_setLastChosenActivity = 57;
        static final int TRANSACTION_setLicensePermissionsForMDM = 213;
        static final int TRANSACTION_setMimeGroup = 199;
        static final int TRANSACTION_setPackageStoppedState = 93;
        static final int TRANSACTION_setPackagesSuspendedAsUser = 69;
        static final int TRANSACTION_setRequiredForSystemUser = 153;
        static final int TRANSACTION_setRuntimePermissionsVersion = 187;
        static final int TRANSACTION_setSplashScreenTheme = 201;
        static final int TRANSACTION_setSystemAppHiddenUntilInstalled = 137;
        static final int TRANSACTION_setSystemAppInstallState = 138;
        static final int TRANSACTION_setUpdateAvailable = 154;
        static final int TRANSACTION_setUserMinAspectRatio = 203;
        static final int TRANSACTION_shouldAppSupportBadgeIcon = 240;
        static final int TRANSACTION_unregisterMoveCallback = 117;
        static final int TRANSACTION_unregisterPackageMonitorCallback = 223;
        static final int TRANSACTION_updateIntentVerificationStatus = 128;
        static final int TRANSACTION_verifyIntentFilter = 126;
        static final int TRANSACTION_verifyPendingInstall = 124;
        static final int TRANSACTION_waitForHandler = 221;
        private final PermissionEnforcer mEnforcer;

        public Stub(PermissionEnforcer enforcer) {
            attachInterface(this, DESCRIPTOR);
            if (enforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = enforcer;
        }

        @Deprecated
        public Stub() {
            this(PermissionEnforcer.fromContext(ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static IPackageManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IPackageManager)) {
                return (IPackageManager) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "checkPackageStartable";
                case 2:
                    return "isPackageAvailable";
                case 3:
                    return "getPackageInfo";
                case 4:
                    return "getPackageInfoVersioned";
                case 5:
                    return "getPackageUid";
                case 6:
                    return "getPackageGids";
                case 7:
                    return "currentToCanonicalPackageNames";
                case 8:
                    return "canonicalToCurrentPackageNames";
                case 9:
                    return "getApplicationInfo";
                case 10:
                    return "getTargetSdkVersion";
                case 11:
                    return "getActivityInfo";
                case 12:
                    return "activitySupportsIntentAsUser";
                case 13:
                    return "getReceiverInfo";
                case 14:
                    return "getServiceInfo";
                case 15:
                    return "getProviderInfo";
                case 16:
                    return "isProtectedBroadcast";
                case 17:
                    return "checkSignatures";
                case 18:
                    return "checkUidSignatures";
                case 19:
                    return "getAllPackages";
                case 20:
                    return "getPackagesForUid";
                case 21:
                    return "getNameForUid";
                case 22:
                    return "getNamesForUids";
                case 23:
                    return "getUidForSharedUser";
                case 24:
                    return "getFlagsForUid";
                case 25:
                    return "getPrivateFlagsForUid";
                case 26:
                    return "isUidPrivileged";
                case 27:
                    return "resolveIntent";
                case 28:
                    return "findPersistentPreferredActivity";
                case 29:
                    return "canForwardTo";
                case 30:
                    return "queryIntentActivities";
                case 31:
                    return "queryIntentActivityOptions";
                case 32:
                    return "queryIntentReceivers";
                case 33:
                    return "resolveService";
                case 34:
                    return "queryIntentServices";
                case 35:
                    return "queryIntentContentProviders";
                case 36:
                    return "getInstalledPackages";
                case 37:
                    return "getAppMetadataFd";
                case 38:
                    return "getPackagesHoldingPermissions";
                case 39:
                    return "getInstalledApplications";
                case 40:
                    return "getPersistentApplications";
                case 41:
                    return "resolveContentProvider";
                case 42:
                    return "querySyncProviders";
                case 43:
                    return "queryContentProviders";
                case 44:
                    return "getInstrumentationInfoAsUser";
                case 45:
                    return "queryInstrumentationAsUser";
                case 46:
                    return "finishPackageInstall";
                case 47:
                    return "setInstallerPackageName";
                case 48:
                    return "relinquishUpdateOwnership";
                case 49:
                    return "setApplicationCategoryHint";
                case 50:
                    return "deletePackageAsUser";
                case 51:
                    return "deletePackageVersioned";
                case 52:
                    return "deleteExistingPackageAsUser";
                case 53:
                    return "getInstallerPackageName";
                case 54:
                    return "getInstallSourceInfo";
                case 55:
                    return "resetApplicationPreferences";
                case 56:
                    return "getLastChosenActivity";
                case 57:
                    return "setLastChosenActivity";
                case 58:
                    return "addPreferredActivity";
                case 59:
                    return "replacePreferredActivity";
                case 60:
                    return "clearPackagePreferredActivities";
                case 61:
                    return "getPreferredActivities";
                case 62:
                    return "addPersistentPreferredActivity";
                case 63:
                    return "clearPackagePersistentPreferredActivities";
                case 64:
                    return "clearPersistentPreferredActivity";
                case 65:
                    return "addCrossProfileIntentFilter";
                case 66:
                    return "removeCrossProfileIntentFilter";
                case 67:
                    return "clearCrossProfileIntentFilters";
                case 68:
                    return "setDistractingPackageRestrictionsAsUser";
                case 69:
                    return "setPackagesSuspendedAsUser";
                case 70:
                    return "getUnsuspendablePackagesForUser";
                case 71:
                    return "isPackageSuspendedForUser";
                case 72:
                    return "isPackageQuarantinedForUser";
                case 73:
                    return "isPackageStoppedForUser";
                case 74:
                    return "getSuspendedPackageAppExtras";
                case 75:
                    return "getSuspendingPackage";
                case 76:
                    return "getPreferredActivityBackup";
                case 77:
                    return "restorePreferredActivities";
                case 78:
                    return "getDefaultAppsBackup";
                case 79:
                    return "restoreDefaultApps";
                case 80:
                    return "getDomainVerificationBackup";
                case 81:
                    return "restoreDomainVerification";
                case 82:
                    return "getHomeActivities";
                case 83:
                    return "setHomeActivity";
                case 84:
                    return "overrideLabelAndIcon";
                case 85:
                    return "restoreLabelAndIcon";
                case 86:
                    return "setComponentEnabledSetting";
                case 87:
                    return "setComponentEnabledSettings";
                case 88:
                    return "getComponentEnabledSetting";
                case 89:
                    return "setApplicationEnabledSetting";
                case 90:
                    return "getApplicationEnabledSetting";
                case 91:
                    return "logAppProcessStartIfNeeded";
                case 92:
                    return "flushPackageRestrictionsAsUser";
                case 93:
                    return "setPackageStoppedState";
                case 94:
                    return "freeStorageAndNotify";
                case 95:
                    return "freeStorage";
                case 96:
                    return "deleteApplicationCacheFiles";
                case 97:
                    return "deleteApplicationCacheFilesAsUser";
                case 98:
                    return "clearApplicationUserData";
                case 99:
                    return "clearApplicationProfileData";
                case 100:
                    return "getPackageSizeInfo";
                case 101:
                    return "getSystemSharedLibraryNames";
                case 102:
                    return "getSystemSharedLibraryNamesAndPaths";
                case 103:
                    return "getSystemAvailableFeatures";
                case 104:
                    return "hasSystemFeature";
                case 105:
                    return "getInitialNonStoppedSystemPackages";
                case 106:
                    return "enterSafeMode";
                case 107:
                    return "isSafeMode";
                case 108:
                    return "hasSystemUidErrors";
                case 109:
                    return "notifyPackageUse";
                case 110:
                    return "notifyDexLoad";
                case 111:
                    return "registerDexModule";
                case 112:
                    return "performDexOptMode";
                case 113:
                    return "performDexOptSecondary";
                case 114:
                    return "performDexOptForADCP";
                case 115:
                    return "getMoveStatus";
                case 116:
                    return "registerMoveCallback";
                case 117:
                    return "unregisterMoveCallback";
                case 118:
                    return "movePackage";
                case 119:
                    return "movePackageToSd";
                case 120:
                    return "movePrimaryStorage";
                case 121:
                    return "setInstallLocation";
                case 122:
                    return "getInstallLocation";
                case 123:
                    return "installExistingPackageAsUser";
                case 124:
                    return "verifyPendingInstall";
                case 125:
                    return "extendVerificationTimeout";
                case 126:
                    return "verifyIntentFilter";
                case 127:
                    return "getIntentVerificationStatus";
                case 128:
                    return "updateIntentVerificationStatus";
                case 129:
                    return "getIntentFilterVerifications";
                case 130:
                    return "getAllIntentFilters";
                case 131:
                    return "getVerifierDeviceIdentity";
                case 132:
                    return "isFirstBoot";
                case 133:
                    return "isDeviceUpgrading";
                case 134:
                    return "isStorageLow";
                case 135:
                    return "setApplicationHiddenSettingAsUser";
                case 136:
                    return "getApplicationHiddenSettingAsUser";
                case 137:
                    return "setSystemAppHiddenUntilInstalled";
                case 138:
                    return "setSystemAppInstallState";
                case 139:
                    return "getPackageInstaller";
                case 140:
                    return "setBlockUninstallForUser";
                case 141:
                    return "getBlockUninstallForUser";
                case 142:
                    return "getKeySetByAlias";
                case 143:
                    return "getSigningKeySet";
                case 144:
                    return "isPackageSignedByKeySet";
                case 145:
                    return "isPackageSignedByKeySetExactly";
                case 146:
                    return "getPermissionControllerPackageName";
                case 147:
                    return "getSdkSandboxPackageName";
                case 148:
                    return "getInstantApps";
                case 149:
                    return "getInstantAppCookie";
                case 150:
                    return "setInstantAppCookie";
                case 151:
                    return "getInstantAppIcon";
                case 152:
                    return "isInstantApp";
                case 153:
                    return "setRequiredForSystemUser";
                case 154:
                    return "setUpdateAvailable";
                case 155:
                    return "getServicesSystemSharedLibraryPackageName";
                case 156:
                    return "getSharedSystemSharedLibraryPackageName";
                case 157:
                    return "getChangedPackages";
                case 158:
                    return "isPackageDeviceAdminOnAnyUser";
                case 159:
                    return "getInstallReason";
                case 160:
                    return "getSharedLibraries";
                case 161:
                    return "getDeclaredSharedLibraries";
                case 162:
                    return "canRequestPackageInstalls";
                case 163:
                    return "deletePreloadsFileCache";
                case 164:
                    return "getInstantAppResolverComponent";
                case 165:
                    return "getInstantAppResolverSettingsComponent";
                case 166:
                    return "getInstantAppInstallerComponent";
                case 167:
                    return "getInstantAppAndroidId";
                case 168:
                    return "getArtManager";
                case 169:
                    return "setHarmfulAppWarning";
                case 170:
                    return "getHarmfulAppWarning";
                case 171:
                    return "hasSigningCertificate";
                case 172:
                    return "hasUidSigningCertificate";
                case 173:
                    return "getDefaultTextClassifierPackageName";
                case 174:
                    return "getSystemTextClassifierPackageName";
                case 175:
                    return "getAttentionServicePackageName";
                case 176:
                    return "getRotationResolverPackageName";
                case 177:
                    return "getWellbeingPackageName";
                case 178:
                    return "getAppPredictionServicePackageName";
                case 179:
                    return "getSystemCaptionsServicePackageName";
                case 180:
                    return "getSetupWizardPackageName";
                case 181:
                    return "getIncidentReportApproverPackageName";
                case 182:
                    return "isPackageStateProtected";
                case 183:
                    return "sendDeviceCustomizationReadyBroadcast";
                case 184:
                    return "getInstalledModules";
                case 185:
                    return "getModuleInfo";
                case 186:
                    return "getRuntimePermissionsVersion";
                case 187:
                    return "setRuntimePermissionsVersion";
                case 188:
                    return "notifyPackagesReplacedReceived";
                case 189:
                    return "requestPackageChecksums";
                case 190:
                    return "getLaunchIntentSenderForPackage";
                case 191:
                    return "getAppOpPermissionPackages";
                case 192:
                    return "getPermissionGroupInfo";
                case 193:
                    return "addPermission";
                case 194:
                    return "addPermissionAsync";
                case 195:
                    return "removePermission";
                case 196:
                    return "checkPermission";
                case 197:
                    return "grantRuntimePermission";
                case 198:
                    return "checkUidPermission";
                case 199:
                    return "setMimeGroup";
                case 200:
                    return "getSplashScreenTheme";
                case 201:
                    return "setSplashScreenTheme";
                case 202:
                    return "getUserMinAspectRatio";
                case 203:
                    return "setUserMinAspectRatio";
                case 204:
                    return "getMimeGroup";
                case 205:
                    return "isAutoRevokeWhitelisted";
                case 206:
                    return "makeProviderVisible";
                case 207:
                    return "makeUidVisible";
                case 208:
                    return "getHoldLockToken";
                case 209:
                    return "holdLock";
                case 210:
                    return "getPropertyAsUser";
                case 211:
                    return "queryProperty";
                case 212:
                    return "setKeepUninstalledPackages";
                case 213:
                    return "setLicensePermissionsForMDM";
                case 214:
                    return "getPackageGrantedPermissionsForMDM";
                case 215:
                    return "getGrantedPermissionsForMDM";
                case 216:
                    return "clearPackagePreferredActivitiesAsUserForMDM";
                case 217:
                    return "applyRuntimePermissionsForMDM";
                case 218:
                    return "applyRuntimePermissionsForAllApplicationsForMDM";
                case 219:
                    return "getRequestedRuntimePermissionsForMDM";
                case 220:
                    return "canPackageQuery";
                case 221:
                    return "waitForHandler";
                case 222:
                    return "registerPackageMonitorCallback";
                case 223:
                    return "unregisterPackageMonitorCallback";
                case 224:
                    return "getArchivedPackage";
                case 225:
                    return "getArchivedAppIcon";
                case 226:
                    return "isAppArchivable";
                case 227:
                    return "getAppMetadataSource";
                case 228:
                    return "getDomainVerificationAgent";
                case 229:
                    return "getPackageListForDualDarPolicy";
                case 230:
                    return "createEncAppData";
                case 231:
                    return "removeEncUserDir";
                case 232:
                    return "removeEncPkgDir";
                case 233:
                    return "getMetadataForIconTray";
                case 234:
                    return "semIsPermissionRevokedByUserFixed";
                case 235:
                    return "isUnknownSourcePackage";
                case 236:
                    return "getUnknownSourcePackagesAsUser";
                case 237:
                    return "isPackageAutoDisabled";
                case 238:
                    return "isSystemCompressedPackage";
                case 239:
                    return "changeMonetizationBadgeState";
                case 240:
                    return "shouldAppSupportBadgeIcon";
                case 241:
                    return "setAppCategoryHintUser";
                case 242:
                    return "clearAppCategoryHintUser";
                case 243:
                    return "getAppCategoryHintUserMap";
                case 244:
                    return "getAppCategoryInfos";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, final Parcel data, final Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    String _arg0 = data.readString();
                    int _arg1 = data.readInt();
                    data.enforceNoDataAvail();
                    checkPackageStartable(_arg0, _arg1);
                    reply.writeNoException();
                    return true;
                case 2:
                    String _arg02 = data.readString();
                    int _arg12 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result = isPackageAvailable(_arg02, _arg12);
                    reply.writeNoException();
                    reply.writeBoolean(_result);
                    return true;
                case 3:
                    String _arg03 = data.readString();
                    long _arg13 = data.readLong();
                    int _arg2 = data.readInt();
                    data.enforceNoDataAvail();
                    PackageInfo _result2 = getPackageInfo(_arg03, _arg13, _arg2);
                    reply.writeNoException();
                    reply.writeTypedObject(_result2, 1);
                    return true;
                case 4:
                    VersionedPackage _arg04 = (VersionedPackage) data.readTypedObject(VersionedPackage.CREATOR);
                    long _arg14 = data.readLong();
                    int _arg22 = data.readInt();
                    data.enforceNoDataAvail();
                    PackageInfo _result3 = getPackageInfoVersioned(_arg04, _arg14, _arg22);
                    reply.writeNoException();
                    reply.writeTypedObject(_result3, 1);
                    return true;
                case 5:
                    String _arg05 = data.readString();
                    long _arg15 = data.readLong();
                    int _arg23 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result4 = getPackageUid(_arg05, _arg15, _arg23);
                    reply.writeNoException();
                    reply.writeInt(_result4);
                    return true;
                case 6:
                    String _arg06 = data.readString();
                    long _arg16 = data.readLong();
                    int _arg24 = data.readInt();
                    data.enforceNoDataAvail();
                    int[] _result5 = getPackageGids(_arg06, _arg16, _arg24);
                    reply.writeNoException();
                    reply.writeIntArray(_result5);
                    return true;
                case 7:
                    String[] _arg07 = data.createStringArray();
                    data.enforceNoDataAvail();
                    String[] _result6 = currentToCanonicalPackageNames(_arg07);
                    reply.writeNoException();
                    reply.writeStringArray(_result6);
                    return true;
                case 8:
                    String[] _arg08 = data.createStringArray();
                    data.enforceNoDataAvail();
                    String[] _result7 = canonicalToCurrentPackageNames(_arg08);
                    reply.writeNoException();
                    reply.writeStringArray(_result7);
                    return true;
                case 9:
                    String _arg09 = data.readString();
                    long _arg17 = data.readLong();
                    int _arg25 = data.readInt();
                    data.enforceNoDataAvail();
                    ApplicationInfo _result8 = getApplicationInfo(_arg09, _arg17, _arg25);
                    reply.writeNoException();
                    reply.writeTypedObject(_result8, 1);
                    return true;
                case 10:
                    String _arg010 = data.readString();
                    data.enforceNoDataAvail();
                    int _result9 = getTargetSdkVersion(_arg010);
                    reply.writeNoException();
                    reply.writeInt(_result9);
                    return true;
                case 11:
                    ComponentName _arg011 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    long _arg18 = data.readLong();
                    int _arg26 = data.readInt();
                    data.enforceNoDataAvail();
                    ActivityInfo _result10 = getActivityInfo(_arg011, _arg18, _arg26);
                    reply.writeNoException();
                    reply.writeTypedObject(_result10, 1);
                    return true;
                case 12:
                    ComponentName _arg012 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    Intent _arg19 = (Intent) data.readTypedObject(Intent.CREATOR);
                    String _arg27 = data.readString();
                    int _arg3 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result11 = activitySupportsIntentAsUser(_arg012, _arg19, _arg27, _arg3);
                    reply.writeNoException();
                    reply.writeBoolean(_result11);
                    return true;
                case 13:
                    ComponentName _arg013 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    long _arg110 = data.readLong();
                    int _arg28 = data.readInt();
                    data.enforceNoDataAvail();
                    ActivityInfo _result12 = getReceiverInfo(_arg013, _arg110, _arg28);
                    reply.writeNoException();
                    reply.writeTypedObject(_result12, 1);
                    return true;
                case 14:
                    ComponentName _arg014 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    long _arg111 = data.readLong();
                    int _arg29 = data.readInt();
                    data.enforceNoDataAvail();
                    ServiceInfo _result13 = getServiceInfo(_arg014, _arg111, _arg29);
                    reply.writeNoException();
                    reply.writeTypedObject(_result13, 1);
                    return true;
                case 15:
                    ComponentName _arg015 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    long _arg112 = data.readLong();
                    int _arg210 = data.readInt();
                    data.enforceNoDataAvail();
                    ProviderInfo _result14 = getProviderInfo(_arg015, _arg112, _arg210);
                    reply.writeNoException();
                    reply.writeTypedObject(_result14, 1);
                    return true;
                case 16:
                    String _arg016 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result15 = isProtectedBroadcast(_arg016);
                    reply.writeNoException();
                    reply.writeBoolean(_result15);
                    return true;
                case 17:
                    String _arg017 = data.readString();
                    String _arg113 = data.readString();
                    int _arg211 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result16 = checkSignatures(_arg017, _arg113, _arg211);
                    reply.writeNoException();
                    reply.writeInt(_result16);
                    return true;
                case 18:
                    int _arg018 = data.readInt();
                    int _arg114 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result17 = checkUidSignatures(_arg018, _arg114);
                    reply.writeNoException();
                    reply.writeInt(_result17);
                    return true;
                case 19:
                    List<String> _result18 = getAllPackages();
                    reply.writeNoException();
                    reply.writeStringList(_result18);
                    return true;
                case 20:
                    int _arg019 = data.readInt();
                    data.enforceNoDataAvail();
                    String[] _result19 = getPackagesForUid(_arg019);
                    reply.writeNoException();
                    reply.writeStringArray(_result19);
                    return true;
                case 21:
                    int _arg020 = data.readInt();
                    data.enforceNoDataAvail();
                    String _result20 = getNameForUid(_arg020);
                    reply.writeNoException();
                    reply.writeString(_result20);
                    return true;
                case 22:
                    int[] _arg021 = data.createIntArray();
                    data.enforceNoDataAvail();
                    String[] _result21 = getNamesForUids(_arg021);
                    reply.writeNoException();
                    reply.writeStringArray(_result21);
                    return true;
                case 23:
                    String _arg022 = data.readString();
                    data.enforceNoDataAvail();
                    int _result22 = getUidForSharedUser(_arg022);
                    reply.writeNoException();
                    reply.writeInt(_result22);
                    return true;
                case 24:
                    int _arg023 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result23 = getFlagsForUid(_arg023);
                    reply.writeNoException();
                    reply.writeInt(_result23);
                    return true;
                case 25:
                    int _arg024 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result24 = getPrivateFlagsForUid(_arg024);
                    reply.writeNoException();
                    reply.writeInt(_result24);
                    return true;
                case 26:
                    int _arg025 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result25 = isUidPrivileged(_arg025);
                    reply.writeNoException();
                    reply.writeBoolean(_result25);
                    return true;
                case 27:
                    Intent _arg026 = (Intent) data.readTypedObject(Intent.CREATOR);
                    String _arg115 = data.readString();
                    long _arg212 = data.readLong();
                    int _arg32 = data.readInt();
                    data.enforceNoDataAvail();
                    ResolveInfo _result26 = resolveIntent(_arg026, _arg115, _arg212, _arg32);
                    reply.writeNoException();
                    reply.writeTypedObject(_result26, 1);
                    return true;
                case 28:
                    Intent _arg027 = (Intent) data.readTypedObject(Intent.CREATOR);
                    int _arg116 = data.readInt();
                    data.enforceNoDataAvail();
                    ResolveInfo _result27 = findPersistentPreferredActivity(_arg027, _arg116);
                    reply.writeNoException();
                    reply.writeTypedObject(_result27, 1);
                    return true;
                case 29:
                    Intent _arg028 = (Intent) data.readTypedObject(Intent.CREATOR);
                    String _arg117 = data.readString();
                    int _arg213 = data.readInt();
                    int _arg33 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result28 = canForwardTo(_arg028, _arg117, _arg213, _arg33);
                    reply.writeNoException();
                    reply.writeBoolean(_result28);
                    return true;
                case 30:
                    Intent _arg029 = (Intent) data.readTypedObject(Intent.CREATOR);
                    String _arg118 = data.readString();
                    long _arg214 = data.readLong();
                    int _arg34 = data.readInt();
                    data.enforceNoDataAvail();
                    ParceledListSlice _result29 = queryIntentActivities(_arg029, _arg118, _arg214, _arg34);
                    reply.writeNoException();
                    reply.writeTypedObject(_result29, 1);
                    return true;
                case 31:
                    ComponentName _arg030 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    Intent[] _arg119 = (Intent[]) data.createTypedArray(Intent.CREATOR);
                    String[] _arg215 = data.createStringArray();
                    Intent _arg35 = (Intent) data.readTypedObject(Intent.CREATOR);
                    String _arg4 = data.readString();
                    long _arg5 = data.readLong();
                    int _arg6 = data.readInt();
                    data.enforceNoDataAvail();
                    ParceledListSlice _result30 = queryIntentActivityOptions(_arg030, _arg119, _arg215, _arg35, _arg4, _arg5, _arg6);
                    reply.writeNoException();
                    reply.writeTypedObject(_result30, 1);
                    return true;
                case 32:
                    Intent _arg031 = (Intent) data.readTypedObject(Intent.CREATOR);
                    String _arg120 = data.readString();
                    long _arg216 = data.readLong();
                    int _arg36 = data.readInt();
                    data.enforceNoDataAvail();
                    ParceledListSlice _result31 = queryIntentReceivers(_arg031, _arg120, _arg216, _arg36);
                    reply.writeNoException();
                    reply.writeTypedObject(_result31, 1);
                    return true;
                case 33:
                    Intent _arg032 = (Intent) data.readTypedObject(Intent.CREATOR);
                    String _arg121 = data.readString();
                    long _arg217 = data.readLong();
                    int _arg37 = data.readInt();
                    data.enforceNoDataAvail();
                    ResolveInfo _result32 = resolveService(_arg032, _arg121, _arg217, _arg37);
                    reply.writeNoException();
                    reply.writeTypedObject(_result32, 1);
                    return true;
                case 34:
                    Intent _arg033 = (Intent) data.readTypedObject(Intent.CREATOR);
                    String _arg122 = data.readString();
                    long _arg218 = data.readLong();
                    int _arg38 = data.readInt();
                    data.enforceNoDataAvail();
                    ParceledListSlice _result33 = queryIntentServices(_arg033, _arg122, _arg218, _arg38);
                    reply.writeNoException();
                    reply.writeTypedObject(_result33, 1);
                    return true;
                case 35:
                    Intent _arg034 = (Intent) data.readTypedObject(Intent.CREATOR);
                    String _arg123 = data.readString();
                    long _arg219 = data.readLong();
                    int _arg39 = data.readInt();
                    data.enforceNoDataAvail();
                    ParceledListSlice _result34 = queryIntentContentProviders(_arg034, _arg123, _arg219, _arg39);
                    reply.writeNoException();
                    reply.writeTypedObject(_result34, 1);
                    return true;
                case 36:
                    long _arg035 = data.readLong();
                    int _arg124 = data.readInt();
                    data.enforceNoDataAvail();
                    ParceledListSlice _result35 = getInstalledPackages(_arg035, _arg124);
                    reply.writeNoException();
                    reply.writeTypedObject(_result35, 1);
                    return true;
                case 37:
                    String _arg036 = data.readString();
                    int _arg125 = data.readInt();
                    data.enforceNoDataAvail();
                    ParcelFileDescriptor _result36 = getAppMetadataFd(_arg036, _arg125);
                    reply.writeNoException();
                    reply.writeTypedObject(_result36, 1);
                    return true;
                case 38:
                    String[] _arg037 = data.createStringArray();
                    long _arg126 = data.readLong();
                    int _arg220 = data.readInt();
                    data.enforceNoDataAvail();
                    ParceledListSlice _result37 = getPackagesHoldingPermissions(_arg037, _arg126, _arg220);
                    reply.writeNoException();
                    reply.writeTypedObject(_result37, 1);
                    return true;
                case 39:
                    long _arg038 = data.readLong();
                    int _arg127 = data.readInt();
                    data.enforceNoDataAvail();
                    ParceledListSlice _result38 = getInstalledApplications(_arg038, _arg127);
                    reply.writeNoException();
                    reply.writeTypedObject(_result38, 1);
                    return true;
                case 40:
                    int _arg039 = data.readInt();
                    data.enforceNoDataAvail();
                    ParceledListSlice _result39 = getPersistentApplications(_arg039);
                    reply.writeNoException();
                    reply.writeTypedObject(_result39, 1);
                    return true;
                case 41:
                    String _arg040 = data.readString();
                    long _arg128 = data.readLong();
                    int _arg221 = data.readInt();
                    data.enforceNoDataAvail();
                    ProviderInfo _result40 = resolveContentProvider(_arg040, _arg128, _arg221);
                    reply.writeNoException();
                    reply.writeTypedObject(_result40, 1);
                    return true;
                case 42:
                    List<String> _arg041 = data.createStringArrayList();
                    ArrayList createTypedArrayList = data.createTypedArrayList(ProviderInfo.CREATOR);
                    data.enforceNoDataAvail();
                    querySyncProviders(_arg041, createTypedArrayList);
                    reply.writeNoException();
                    reply.writeStringList(_arg041);
                    reply.writeTypedList(createTypedArrayList, 1);
                    return true;
                case 43:
                    String _arg042 = data.readString();
                    int _arg129 = data.readInt();
                    long _arg222 = data.readLong();
                    String _arg310 = data.readString();
                    data.enforceNoDataAvail();
                    ParceledListSlice _result41 = queryContentProviders(_arg042, _arg129, _arg222, _arg310);
                    reply.writeNoException();
                    reply.writeTypedObject(_result41, 1);
                    return true;
                case 44:
                    ComponentName _arg043 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    int _arg130 = data.readInt();
                    int _arg223 = data.readInt();
                    data.enforceNoDataAvail();
                    InstrumentationInfo _result42 = getInstrumentationInfoAsUser(_arg043, _arg130, _arg223);
                    reply.writeNoException();
                    reply.writeTypedObject(_result42, 1);
                    return true;
                case 45:
                    String _arg044 = data.readString();
                    int _arg131 = data.readInt();
                    int _arg224 = data.readInt();
                    data.enforceNoDataAvail();
                    ParceledListSlice _result43 = queryInstrumentationAsUser(_arg044, _arg131, _arg224);
                    reply.writeNoException();
                    reply.writeTypedObject(_result43, 1);
                    return true;
                case 46:
                    int _arg045 = data.readInt();
                    boolean _arg132 = data.readBoolean();
                    data.enforceNoDataAvail();
                    finishPackageInstall(_arg045, _arg132);
                    reply.writeNoException();
                    return true;
                case 47:
                    String _arg046 = data.readString();
                    String _arg133 = data.readString();
                    data.enforceNoDataAvail();
                    setInstallerPackageName(_arg046, _arg133);
                    reply.writeNoException();
                    return true;
                case 48:
                    String _arg047 = data.readString();
                    data.enforceNoDataAvail();
                    relinquishUpdateOwnership(_arg047);
                    reply.writeNoException();
                    return true;
                case 49:
                    String _arg048 = data.readString();
                    int _arg134 = data.readInt();
                    String _arg225 = data.readString();
                    data.enforceNoDataAvail();
                    setApplicationCategoryHint(_arg048, _arg134, _arg225);
                    reply.writeNoException();
                    return true;
                case 50:
                    String _arg049 = data.readString();
                    int _arg135 = data.readInt();
                    IPackageDeleteObserver _arg226 = IPackageDeleteObserver.Stub.asInterface(data.readStrongBinder());
                    int _arg311 = data.readInt();
                    int _arg42 = data.readInt();
                    data.enforceNoDataAvail();
                    deletePackageAsUser(_arg049, _arg135, _arg226, _arg311, _arg42);
                    reply.writeNoException();
                    return true;
                case 51:
                    VersionedPackage _arg050 = (VersionedPackage) data.readTypedObject(VersionedPackage.CREATOR);
                    IPackageDeleteObserver2 _arg136 = IPackageDeleteObserver2.Stub.asInterface(data.readStrongBinder());
                    int _arg227 = data.readInt();
                    int _arg312 = data.readInt();
                    data.enforceNoDataAvail();
                    deletePackageVersioned(_arg050, _arg136, _arg227, _arg312);
                    reply.writeNoException();
                    return true;
                case 52:
                    VersionedPackage _arg051 = (VersionedPackage) data.readTypedObject(VersionedPackage.CREATOR);
                    IPackageDeleteObserver2 _arg137 = IPackageDeleteObserver2.Stub.asInterface(data.readStrongBinder());
                    int _arg228 = data.readInt();
                    data.enforceNoDataAvail();
                    deleteExistingPackageAsUser(_arg051, _arg137, _arg228);
                    reply.writeNoException();
                    return true;
                case 53:
                    String _arg052 = data.readString();
                    data.enforceNoDataAvail();
                    String _result44 = getInstallerPackageName(_arg052);
                    reply.writeNoException();
                    reply.writeString(_result44);
                    return true;
                case 54:
                    String _arg053 = data.readString();
                    int _arg138 = data.readInt();
                    data.enforceNoDataAvail();
                    InstallSourceInfo _result45 = getInstallSourceInfo(_arg053, _arg138);
                    reply.writeNoException();
                    reply.writeTypedObject(_result45, 1);
                    return true;
                case 55:
                    int _arg054 = data.readInt();
                    data.enforceNoDataAvail();
                    resetApplicationPreferences(_arg054);
                    reply.writeNoException();
                    return true;
                case 56:
                    Intent _arg055 = (Intent) data.readTypedObject(Intent.CREATOR);
                    String _arg139 = data.readString();
                    int _arg229 = data.readInt();
                    data.enforceNoDataAvail();
                    ResolveInfo _result46 = getLastChosenActivity(_arg055, _arg139, _arg229);
                    reply.writeNoException();
                    reply.writeTypedObject(_result46, 1);
                    return true;
                case 57:
                    Intent _arg056 = (Intent) data.readTypedObject(Intent.CREATOR);
                    String _arg140 = data.readString();
                    int _arg230 = data.readInt();
                    IntentFilter _arg313 = (IntentFilter) data.readTypedObject(IntentFilter.CREATOR);
                    int _arg43 = data.readInt();
                    ComponentName _arg52 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    data.enforceNoDataAvail();
                    setLastChosenActivity(_arg056, _arg140, _arg230, _arg313, _arg43, _arg52);
                    reply.writeNoException();
                    return true;
                case 58:
                    IntentFilter _arg057 = (IntentFilter) data.readTypedObject(IntentFilter.CREATOR);
                    int _arg141 = data.readInt();
                    ComponentName[] _arg231 = (ComponentName[]) data.createTypedArray(ComponentName.CREATOR);
                    ComponentName _arg314 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    int _arg44 = data.readInt();
                    boolean _arg53 = data.readBoolean();
                    data.enforceNoDataAvail();
                    addPreferredActivity(_arg057, _arg141, _arg231, _arg314, _arg44, _arg53);
                    reply.writeNoException();
                    return true;
                case 59:
                    IntentFilter _arg058 = (IntentFilter) data.readTypedObject(IntentFilter.CREATOR);
                    int _arg142 = data.readInt();
                    ComponentName[] _arg232 = (ComponentName[]) data.createTypedArray(ComponentName.CREATOR);
                    ComponentName _arg315 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    int _arg45 = data.readInt();
                    data.enforceNoDataAvail();
                    replacePreferredActivity(_arg058, _arg142, _arg232, _arg315, _arg45);
                    reply.writeNoException();
                    return true;
                case 60:
                    String _arg059 = data.readString();
                    data.enforceNoDataAvail();
                    clearPackagePreferredActivities(_arg059);
                    reply.writeNoException();
                    return true;
                case 61:
                    ArrayList arrayList = new ArrayList();
                    ArrayList arrayList2 = new ArrayList();
                    String _arg233 = data.readString();
                    data.enforceNoDataAvail();
                    int _result47 = getPreferredActivities(arrayList, arrayList2, _arg233);
                    reply.writeNoException();
                    reply.writeInt(_result47);
                    reply.writeTypedList(arrayList, 1);
                    reply.writeTypedList(arrayList2, 1);
                    return true;
                case 62:
                    IntentFilter _arg060 = (IntentFilter) data.readTypedObject(IntentFilter.CREATOR);
                    ComponentName _arg143 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    int _arg234 = data.readInt();
                    data.enforceNoDataAvail();
                    addPersistentPreferredActivity(_arg060, _arg143, _arg234);
                    reply.writeNoException();
                    return true;
                case 63:
                    String _arg061 = data.readString();
                    int _arg144 = data.readInt();
                    data.enforceNoDataAvail();
                    clearPackagePersistentPreferredActivities(_arg061, _arg144);
                    reply.writeNoException();
                    return true;
                case 64:
                    IntentFilter _arg062 = (IntentFilter) data.readTypedObject(IntentFilter.CREATOR);
                    int _arg145 = data.readInt();
                    data.enforceNoDataAvail();
                    clearPersistentPreferredActivity(_arg062, _arg145);
                    reply.writeNoException();
                    return true;
                case 65:
                    IntentFilter _arg063 = (IntentFilter) data.readTypedObject(IntentFilter.CREATOR);
                    String _arg146 = data.readString();
                    int _arg235 = data.readInt();
                    int _arg316 = data.readInt();
                    int _arg46 = data.readInt();
                    data.enforceNoDataAvail();
                    addCrossProfileIntentFilter(_arg063, _arg146, _arg235, _arg316, _arg46);
                    reply.writeNoException();
                    return true;
                case 66:
                    IntentFilter _arg064 = (IntentFilter) data.readTypedObject(IntentFilter.CREATOR);
                    String _arg147 = data.readString();
                    int _arg236 = data.readInt();
                    int _arg317 = data.readInt();
                    int _arg47 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result48 = removeCrossProfileIntentFilter(_arg064, _arg147, _arg236, _arg317, _arg47);
                    reply.writeNoException();
                    reply.writeBoolean(_result48);
                    return true;
                case 67:
                    int _arg065 = data.readInt();
                    String _arg148 = data.readString();
                    data.enforceNoDataAvail();
                    clearCrossProfileIntentFilters(_arg065, _arg148);
                    reply.writeNoException();
                    return true;
                case 68:
                    String[] _arg066 = data.createStringArray();
                    int _arg149 = data.readInt();
                    int _arg237 = data.readInt();
                    data.enforceNoDataAvail();
                    String[] _result49 = setDistractingPackageRestrictionsAsUser(_arg066, _arg149, _arg237);
                    reply.writeNoException();
                    reply.writeStringArray(_result49);
                    return true;
                case 69:
                    String[] _arg067 = data.createStringArray();
                    boolean _arg150 = data.readBoolean();
                    PersistableBundle _arg238 = (PersistableBundle) data.readTypedObject(PersistableBundle.CREATOR);
                    PersistableBundle _arg318 = (PersistableBundle) data.readTypedObject(PersistableBundle.CREATOR);
                    SuspendDialogInfo _arg48 = (SuspendDialogInfo) data.readTypedObject(SuspendDialogInfo.CREATOR);
                    int _arg54 = data.readInt();
                    String _arg62 = data.readString();
                    int _arg7 = data.readInt();
                    int _arg8 = data.readInt();
                    data.enforceNoDataAvail();
                    String[] _result50 = setPackagesSuspendedAsUser(_arg067, _arg150, _arg238, _arg318, _arg48, _arg54, _arg62, _arg7, _arg8);
                    reply.writeNoException();
                    reply.writeStringArray(_result50);
                    return true;
                case 70:
                    String[] _arg068 = data.createStringArray();
                    int _arg151 = data.readInt();
                    data.enforceNoDataAvail();
                    String[] _result51 = getUnsuspendablePackagesForUser(_arg068, _arg151);
                    reply.writeNoException();
                    reply.writeStringArray(_result51);
                    return true;
                case 71:
                    String _arg069 = data.readString();
                    int _arg152 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result52 = isPackageSuspendedForUser(_arg069, _arg152);
                    reply.writeNoException();
                    reply.writeBoolean(_result52);
                    return true;
                case 72:
                    String _arg070 = data.readString();
                    int _arg153 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result53 = isPackageQuarantinedForUser(_arg070, _arg153);
                    reply.writeNoException();
                    reply.writeBoolean(_result53);
                    return true;
                case 73:
                    String _arg071 = data.readString();
                    int _arg154 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result54 = isPackageStoppedForUser(_arg071, _arg154);
                    reply.writeNoException();
                    reply.writeBoolean(_result54);
                    return true;
                case 74:
                    String _arg072 = data.readString();
                    int _arg155 = data.readInt();
                    data.enforceNoDataAvail();
                    Bundle _result55 = getSuspendedPackageAppExtras(_arg072, _arg155);
                    reply.writeNoException();
                    reply.writeTypedObject(_result55, 1);
                    return true;
                case 75:
                    String _arg073 = data.readString();
                    int _arg156 = data.readInt();
                    data.enforceNoDataAvail();
                    String _result56 = getSuspendingPackage(_arg073, _arg156);
                    reply.writeNoException();
                    reply.writeString(_result56);
                    return true;
                case 76:
                    int _arg074 = data.readInt();
                    data.enforceNoDataAvail();
                    byte[] _result57 = getPreferredActivityBackup(_arg074);
                    reply.writeNoException();
                    reply.writeByteArray(_result57);
                    return true;
                case 77:
                    byte[] _arg075 = data.createByteArray();
                    int _arg157 = data.readInt();
                    data.enforceNoDataAvail();
                    restorePreferredActivities(_arg075, _arg157);
                    reply.writeNoException();
                    return true;
                case 78:
                    int _arg076 = data.readInt();
                    data.enforceNoDataAvail();
                    byte[] _result58 = getDefaultAppsBackup(_arg076);
                    reply.writeNoException();
                    reply.writeByteArray(_result58);
                    return true;
                case 79:
                    byte[] _arg077 = data.createByteArray();
                    int _arg158 = data.readInt();
                    data.enforceNoDataAvail();
                    restoreDefaultApps(_arg077, _arg158);
                    reply.writeNoException();
                    return true;
                case 80:
                    int _arg078 = data.readInt();
                    data.enforceNoDataAvail();
                    byte[] _result59 = getDomainVerificationBackup(_arg078);
                    reply.writeNoException();
                    reply.writeByteArray(_result59);
                    return true;
                case 81:
                    byte[] _arg079 = data.createByteArray();
                    int _arg159 = data.readInt();
                    data.enforceNoDataAvail();
                    restoreDomainVerification(_arg079, _arg159);
                    reply.writeNoException();
                    return true;
                case 82:
                    ArrayList arrayList3 = new ArrayList();
                    data.enforceNoDataAvail();
                    ComponentName _result60 = getHomeActivities(arrayList3);
                    reply.writeNoException();
                    reply.writeTypedObject(_result60, 1);
                    reply.writeTypedList(arrayList3, 1);
                    return true;
                case 83:
                    ComponentName _arg080 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    int _arg160 = data.readInt();
                    data.enforceNoDataAvail();
                    setHomeActivity(_arg080, _arg160);
                    reply.writeNoException();
                    return true;
                case 84:
                    ComponentName _arg081 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    String _arg161 = data.readString();
                    int _arg239 = data.readInt();
                    int _arg319 = data.readInt();
                    data.enforceNoDataAvail();
                    overrideLabelAndIcon(_arg081, _arg161, _arg239, _arg319);
                    reply.writeNoException();
                    return true;
                case 85:
                    ComponentName _arg082 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    int _arg162 = data.readInt();
                    data.enforceNoDataAvail();
                    restoreLabelAndIcon(_arg082, _arg162);
                    reply.writeNoException();
                    return true;
                case 86:
                    ComponentName _arg083 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    int _arg163 = data.readInt();
                    int _arg240 = data.readInt();
                    int _arg320 = data.readInt();
                    String _arg49 = data.readString();
                    data.enforceNoDataAvail();
                    setComponentEnabledSetting(_arg083, _arg163, _arg240, _arg320, _arg49);
                    reply.writeNoException();
                    return true;
                case 87:
                    List<PackageManager.ComponentEnabledSetting> _arg084 = data.createTypedArrayList(PackageManager.ComponentEnabledSetting.CREATOR);
                    int _arg164 = data.readInt();
                    String _arg241 = data.readString();
                    data.enforceNoDataAvail();
                    setComponentEnabledSettings(_arg084, _arg164, _arg241);
                    reply.writeNoException();
                    return true;
                case 88:
                    ComponentName _arg085 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    int _arg165 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result61 = getComponentEnabledSetting(_arg085, _arg165);
                    reply.writeNoException();
                    reply.writeInt(_result61);
                    return true;
                case 89:
                    String _arg086 = data.readString();
                    int _arg166 = data.readInt();
                    int _arg242 = data.readInt();
                    int _arg321 = data.readInt();
                    String _arg410 = data.readString();
                    data.enforceNoDataAvail();
                    setApplicationEnabledSetting(_arg086, _arg166, _arg242, _arg321, _arg410);
                    reply.writeNoException();
                    return true;
                case 90:
                    String _arg087 = data.readString();
                    int _arg167 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result62 = getApplicationEnabledSetting(_arg087, _arg167);
                    reply.writeNoException();
                    reply.writeInt(_result62);
                    return true;
                case 91:
                    String _arg088 = data.readString();
                    String _arg168 = data.readString();
                    int _arg243 = data.readInt();
                    String _arg322 = data.readString();
                    String _arg411 = data.readString();
                    int _arg55 = data.readInt();
                    data.enforceNoDataAvail();
                    logAppProcessStartIfNeeded(_arg088, _arg168, _arg243, _arg322, _arg411, _arg55);
                    reply.writeNoException();
                    return true;
                case 92:
                    int _arg089 = data.readInt();
                    data.enforceNoDataAvail();
                    flushPackageRestrictionsAsUser(_arg089);
                    reply.writeNoException();
                    return true;
                case 93:
                    String _arg090 = data.readString();
                    boolean _arg169 = data.readBoolean();
                    int _arg244 = data.readInt();
                    data.enforceNoDataAvail();
                    setPackageStoppedState(_arg090, _arg169, _arg244);
                    reply.writeNoException();
                    return true;
                case 94:
                    String _arg091 = data.readString();
                    long _arg170 = data.readLong();
                    int _arg245 = data.readInt();
                    IPackageDataObserver _arg323 = IPackageDataObserver.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    freeStorageAndNotify(_arg091, _arg170, _arg245, _arg323);
                    reply.writeNoException();
                    return true;
                case 95:
                    String _arg092 = data.readString();
                    long _arg171 = data.readLong();
                    int _arg246 = data.readInt();
                    IntentSender _arg324 = (IntentSender) data.readTypedObject(IntentSender.CREATOR);
                    data.enforceNoDataAvail();
                    freeStorage(_arg092, _arg171, _arg246, _arg324);
                    reply.writeNoException();
                    return true;
                case 96:
                    String _arg093 = data.readString();
                    IPackageDataObserver _arg172 = IPackageDataObserver.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    deleteApplicationCacheFiles(_arg093, _arg172);
                    reply.writeNoException();
                    return true;
                case 97:
                    String _arg094 = data.readString();
                    int _arg173 = data.readInt();
                    IPackageDataObserver _arg247 = IPackageDataObserver.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    deleteApplicationCacheFilesAsUser(_arg094, _arg173, _arg247);
                    reply.writeNoException();
                    return true;
                case 98:
                    String _arg095 = data.readString();
                    IPackageDataObserver _arg174 = IPackageDataObserver.Stub.asInterface(data.readStrongBinder());
                    int _arg248 = data.readInt();
                    data.enforceNoDataAvail();
                    clearApplicationUserData(_arg095, _arg174, _arg248);
                    reply.writeNoException();
                    return true;
                case 99:
                    String _arg096 = data.readString();
                    data.enforceNoDataAvail();
                    clearApplicationProfileData(_arg096);
                    reply.writeNoException();
                    return true;
                case 100:
                    String _arg097 = data.readString();
                    int _arg175 = data.readInt();
                    IPackageStatsObserver _arg249 = IPackageStatsObserver.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    getPackageSizeInfo(_arg097, _arg175, _arg249);
                    reply.writeNoException();
                    return true;
                case 101:
                    String[] _result63 = getSystemSharedLibraryNames();
                    reply.writeNoException();
                    reply.writeStringArray(_result63);
                    return true;
                case 102:
                    Map<String, String> _result64 = getSystemSharedLibraryNamesAndPaths();
                    reply.writeNoException();
                    if (_result64 == null) {
                        reply.writeInt(-1);
                    } else {
                        reply.writeInt(_result64.size());
                        _result64.forEach(new BiConsumer() { // from class: android.content.pm.IPackageManager$Stub$$ExternalSyntheticLambda0
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                IPackageManager.Stub.lambda$onTransact$0(Parcel.this, (String) obj, (String) obj2);
                            }
                        });
                    }
                    return true;
                case 103:
                    ParceledListSlice _result65 = getSystemAvailableFeatures();
                    reply.writeNoException();
                    reply.writeTypedObject(_result65, 1);
                    return true;
                case 104:
                    String _arg098 = data.readString();
                    int _arg176 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result66 = hasSystemFeature(_arg098, _arg176);
                    reply.writeNoException();
                    reply.writeBoolean(_result66);
                    return true;
                case 105:
                    List<String> _result67 = getInitialNonStoppedSystemPackages();
                    reply.writeNoException();
                    reply.writeStringList(_result67);
                    return true;
                case 106:
                    enterSafeMode();
                    reply.writeNoException();
                    return true;
                case 107:
                    boolean _result68 = isSafeMode();
                    reply.writeNoException();
                    reply.writeBoolean(_result68);
                    return true;
                case 108:
                    boolean _result69 = hasSystemUidErrors();
                    reply.writeNoException();
                    reply.writeBoolean(_result69);
                    return true;
                case 109:
                    String _arg099 = data.readString();
                    int _arg177 = data.readInt();
                    data.enforceNoDataAvail();
                    notifyPackageUse(_arg099, _arg177);
                    return true;
                case 110:
                    String _arg0100 = data.readString();
                    int N = data.readInt();
                    final Map<String, String> _arg178 = N < 0 ? null : new HashMap<>();
                    IntStream.range(0, N).forEach(new IntConsumer() { // from class: android.content.pm.IPackageManager$Stub$$ExternalSyntheticLambda1
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i) {
                            IPackageManager.Stub.lambda$onTransact$1(Parcel.this, _arg178, i);
                        }
                    });
                    String _arg250 = data.readString();
                    data.enforceNoDataAvail();
                    notifyDexLoad(_arg0100, _arg178, _arg250);
                    return true;
                case 111:
                    String _arg0101 = data.readString();
                    String _arg179 = data.readString();
                    boolean _arg251 = data.readBoolean();
                    IDexModuleRegisterCallback _arg325 = IDexModuleRegisterCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    registerDexModule(_arg0101, _arg179, _arg251, _arg325);
                    return true;
                case 112:
                    String _arg0102 = data.readString();
                    boolean _arg180 = data.readBoolean();
                    String _arg252 = data.readString();
                    boolean _arg326 = data.readBoolean();
                    boolean _arg412 = data.readBoolean();
                    String _arg56 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result70 = performDexOptMode(_arg0102, _arg180, _arg252, _arg326, _arg412, _arg56);
                    reply.writeNoException();
                    reply.writeBoolean(_result70);
                    return true;
                case 113:
                    String _arg0103 = data.readString();
                    String _arg181 = data.readString();
                    boolean _arg253 = data.readBoolean();
                    data.enforceNoDataAvail();
                    boolean _result71 = performDexOptSecondary(_arg0103, _arg181, _arg253);
                    reply.writeNoException();
                    reply.writeBoolean(_result71);
                    return true;
                case 114:
                    String _arg0104 = data.readString();
                    boolean _arg182 = data.readBoolean();
                    data.enforceNoDataAvail();
                    int _result72 = performDexOptForADCP(_arg0104, _arg182);
                    reply.writeNoException();
                    reply.writeInt(_result72);
                    return true;
                case 115:
                    int _arg0105 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result73 = getMoveStatus(_arg0105);
                    reply.writeNoException();
                    reply.writeInt(_result73);
                    return true;
                case 116:
                    IPackageMoveObserver _arg0106 = IPackageMoveObserver.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    registerMoveCallback(_arg0106);
                    reply.writeNoException();
                    return true;
                case 117:
                    IPackageMoveObserver _arg0107 = IPackageMoveObserver.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    unregisterMoveCallback(_arg0107);
                    reply.writeNoException();
                    return true;
                case 118:
                    String _arg0108 = data.readString();
                    String _arg183 = data.readString();
                    data.enforceNoDataAvail();
                    int _result74 = movePackage(_arg0108, _arg183);
                    reply.writeNoException();
                    reply.writeInt(_result74);
                    return true;
                case 119:
                    String _arg0109 = data.readString();
                    String _arg184 = data.readString();
                    IMemorySaverPackageMoveObserver _arg254 = IMemorySaverPackageMoveObserver.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    int _result75 = movePackageToSd(_arg0109, _arg184, _arg254);
                    reply.writeNoException();
                    reply.writeInt(_result75);
                    return true;
                case 120:
                    String _arg0110 = data.readString();
                    data.enforceNoDataAvail();
                    int _result76 = movePrimaryStorage(_arg0110);
                    reply.writeNoException();
                    reply.writeInt(_result76);
                    return true;
                case 121:
                    int _arg0111 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result77 = setInstallLocation(_arg0111);
                    reply.writeNoException();
                    reply.writeBoolean(_result77);
                    return true;
                case 122:
                    int _result78 = getInstallLocation();
                    reply.writeNoException();
                    reply.writeInt(_result78);
                    return true;
                case 123:
                    String _arg0112 = data.readString();
                    int _arg185 = data.readInt();
                    int _arg255 = data.readInt();
                    int _arg327 = data.readInt();
                    List<String> _arg413 = data.createStringArrayList();
                    data.enforceNoDataAvail();
                    int _result79 = installExistingPackageAsUser(_arg0112, _arg185, _arg255, _arg327, _arg413);
                    reply.writeNoException();
                    reply.writeInt(_result79);
                    return true;
                case 124:
                    int _arg0113 = data.readInt();
                    int _arg186 = data.readInt();
                    data.enforceNoDataAvail();
                    verifyPendingInstall(_arg0113, _arg186);
                    reply.writeNoException();
                    return true;
                case 125:
                    int _arg0114 = data.readInt();
                    int _arg187 = data.readInt();
                    long _arg256 = data.readLong();
                    data.enforceNoDataAvail();
                    extendVerificationTimeout(_arg0114, _arg187, _arg256);
                    reply.writeNoException();
                    return true;
                case 126:
                    int _arg0115 = data.readInt();
                    int _arg188 = data.readInt();
                    List<String> _arg257 = data.createStringArrayList();
                    data.enforceNoDataAvail();
                    verifyIntentFilter(_arg0115, _arg188, _arg257);
                    reply.writeNoException();
                    return true;
                case 127:
                    String _arg0116 = data.readString();
                    int _arg189 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result80 = getIntentVerificationStatus(_arg0116, _arg189);
                    reply.writeNoException();
                    reply.writeInt(_result80);
                    return true;
                case 128:
                    String _arg0117 = data.readString();
                    int _arg190 = data.readInt();
                    int _arg258 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result81 = updateIntentVerificationStatus(_arg0117, _arg190, _arg258);
                    reply.writeNoException();
                    reply.writeBoolean(_result81);
                    return true;
                case 129:
                    String _arg0118 = data.readString();
                    data.enforceNoDataAvail();
                    ParceledListSlice _result82 = getIntentFilterVerifications(_arg0118);
                    reply.writeNoException();
                    reply.writeTypedObject(_result82, 1);
                    return true;
                case 130:
                    String _arg0119 = data.readString();
                    data.enforceNoDataAvail();
                    ParceledListSlice _result83 = getAllIntentFilters(_arg0119);
                    reply.writeNoException();
                    reply.writeTypedObject(_result83, 1);
                    return true;
                case 131:
                    VerifierDeviceIdentity _result84 = getVerifierDeviceIdentity();
                    reply.writeNoException();
                    reply.writeTypedObject(_result84, 1);
                    return true;
                case 132:
                    boolean _result85 = isFirstBoot();
                    reply.writeNoException();
                    reply.writeBoolean(_result85);
                    return true;
                case 133:
                    boolean _result86 = isDeviceUpgrading();
                    reply.writeNoException();
                    reply.writeBoolean(_result86);
                    return true;
                case 134:
                    boolean _result87 = isStorageLow();
                    reply.writeNoException();
                    reply.writeBoolean(_result87);
                    return true;
                case 135:
                    String _arg0120 = data.readString();
                    boolean _arg191 = data.readBoolean();
                    int _arg259 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result88 = setApplicationHiddenSettingAsUser(_arg0120, _arg191, _arg259);
                    reply.writeNoException();
                    reply.writeBoolean(_result88);
                    return true;
                case 136:
                    String _arg0121 = data.readString();
                    int _arg192 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result89 = getApplicationHiddenSettingAsUser(_arg0121, _arg192);
                    reply.writeNoException();
                    reply.writeBoolean(_result89);
                    return true;
                case 137:
                    String _arg0122 = data.readString();
                    boolean _arg193 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setSystemAppHiddenUntilInstalled(_arg0122, _arg193);
                    reply.writeNoException();
                    return true;
                case 138:
                    String _arg0123 = data.readString();
                    boolean _arg194 = data.readBoolean();
                    int _arg260 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result90 = setSystemAppInstallState(_arg0123, _arg194, _arg260);
                    reply.writeNoException();
                    reply.writeBoolean(_result90);
                    return true;
                case 139:
                    IPackageInstaller _result91 = getPackageInstaller();
                    reply.writeNoException();
                    reply.writeStrongInterface(_result91);
                    return true;
                case 140:
                    String _arg0124 = data.readString();
                    boolean _arg195 = data.readBoolean();
                    int _arg261 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result92 = setBlockUninstallForUser(_arg0124, _arg195, _arg261);
                    reply.writeNoException();
                    reply.writeBoolean(_result92);
                    return true;
                case 141:
                    String _arg0125 = data.readString();
                    int _arg196 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result93 = getBlockUninstallForUser(_arg0125, _arg196);
                    reply.writeNoException();
                    reply.writeBoolean(_result93);
                    return true;
                case 142:
                    String _arg0126 = data.readString();
                    String _arg197 = data.readString();
                    data.enforceNoDataAvail();
                    KeySet _result94 = getKeySetByAlias(_arg0126, _arg197);
                    reply.writeNoException();
                    reply.writeTypedObject(_result94, 1);
                    return true;
                case 143:
                    String _arg0127 = data.readString();
                    data.enforceNoDataAvail();
                    KeySet _result95 = getSigningKeySet(_arg0127);
                    reply.writeNoException();
                    reply.writeTypedObject(_result95, 1);
                    return true;
                case 144:
                    String _arg0128 = data.readString();
                    KeySet _arg198 = (KeySet) data.readTypedObject(KeySet.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result96 = isPackageSignedByKeySet(_arg0128, _arg198);
                    reply.writeNoException();
                    reply.writeBoolean(_result96);
                    return true;
                case 145:
                    String _arg0129 = data.readString();
                    KeySet _arg199 = (KeySet) data.readTypedObject(KeySet.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result97 = isPackageSignedByKeySetExactly(_arg0129, _arg199);
                    reply.writeNoException();
                    reply.writeBoolean(_result97);
                    return true;
                case 146:
                    String _result98 = getPermissionControllerPackageName();
                    reply.writeNoException();
                    reply.writeString(_result98);
                    return true;
                case 147:
                    String _result99 = getSdkSandboxPackageName();
                    reply.writeNoException();
                    reply.writeString(_result99);
                    return true;
                case 148:
                    int _arg0130 = data.readInt();
                    data.enforceNoDataAvail();
                    ParceledListSlice _result100 = getInstantApps(_arg0130);
                    reply.writeNoException();
                    reply.writeTypedObject(_result100, 1);
                    return true;
                case 149:
                    String _arg0131 = data.readString();
                    int _arg1100 = data.readInt();
                    data.enforceNoDataAvail();
                    byte[] _result101 = getInstantAppCookie(_arg0131, _arg1100);
                    reply.writeNoException();
                    reply.writeByteArray(_result101);
                    return true;
                case 150:
                    String _arg0132 = data.readString();
                    byte[] _arg1101 = data.createByteArray();
                    int _arg262 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result102 = setInstantAppCookie(_arg0132, _arg1101, _arg262);
                    reply.writeNoException();
                    reply.writeBoolean(_result102);
                    return true;
                case 151:
                    String _arg0133 = data.readString();
                    int _arg1102 = data.readInt();
                    data.enforceNoDataAvail();
                    Bitmap _result103 = getInstantAppIcon(_arg0133, _arg1102);
                    reply.writeNoException();
                    reply.writeTypedObject(_result103, 1);
                    return true;
                case 152:
                    String _arg0134 = data.readString();
                    int _arg1103 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result104 = isInstantApp(_arg0134, _arg1103);
                    reply.writeNoException();
                    reply.writeBoolean(_result104);
                    return true;
                case 153:
                    String _arg0135 = data.readString();
                    boolean _arg1104 = data.readBoolean();
                    data.enforceNoDataAvail();
                    boolean _result105 = setRequiredForSystemUser(_arg0135, _arg1104);
                    reply.writeNoException();
                    reply.writeBoolean(_result105);
                    return true;
                case 154:
                    String _arg0136 = data.readString();
                    boolean _arg1105 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setUpdateAvailable(_arg0136, _arg1105);
                    reply.writeNoException();
                    return true;
                case 155:
                    String _result106 = getServicesSystemSharedLibraryPackageName();
                    reply.writeNoException();
                    reply.writeString(_result106);
                    return true;
                case 156:
                    String _result107 = getSharedSystemSharedLibraryPackageName();
                    reply.writeNoException();
                    reply.writeString(_result107);
                    return true;
                case 157:
                    int _arg0137 = data.readInt();
                    int _arg1106 = data.readInt();
                    data.enforceNoDataAvail();
                    ChangedPackages _result108 = getChangedPackages(_arg0137, _arg1106);
                    reply.writeNoException();
                    reply.writeTypedObject(_result108, 1);
                    return true;
                case 158:
                    String _arg0138 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result109 = isPackageDeviceAdminOnAnyUser(_arg0138);
                    reply.writeNoException();
                    reply.writeBoolean(_result109);
                    return true;
                case 159:
                    String _arg0139 = data.readString();
                    int _arg1107 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result110 = getInstallReason(_arg0139, _arg1107);
                    reply.writeNoException();
                    reply.writeInt(_result110);
                    return true;
                case 160:
                    String _arg0140 = data.readString();
                    long _arg1108 = data.readLong();
                    int _arg263 = data.readInt();
                    data.enforceNoDataAvail();
                    ParceledListSlice _result111 = getSharedLibraries(_arg0140, _arg1108, _arg263);
                    reply.writeNoException();
                    reply.writeTypedObject(_result111, 1);
                    return true;
                case 161:
                    String _arg0141 = data.readString();
                    long _arg1109 = data.readLong();
                    int _arg264 = data.readInt();
                    data.enforceNoDataAvail();
                    ParceledListSlice _result112 = getDeclaredSharedLibraries(_arg0141, _arg1109, _arg264);
                    reply.writeNoException();
                    reply.writeTypedObject(_result112, 1);
                    return true;
                case 162:
                    String _arg0142 = data.readString();
                    int _arg1110 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result113 = canRequestPackageInstalls(_arg0142, _arg1110);
                    reply.writeNoException();
                    reply.writeBoolean(_result113);
                    return true;
                case 163:
                    deletePreloadsFileCache();
                    reply.writeNoException();
                    return true;
                case 164:
                    ComponentName _result114 = getInstantAppResolverComponent();
                    reply.writeNoException();
                    reply.writeTypedObject(_result114, 1);
                    return true;
                case 165:
                    ComponentName _result115 = getInstantAppResolverSettingsComponent();
                    reply.writeNoException();
                    reply.writeTypedObject(_result115, 1);
                    return true;
                case 166:
                    ComponentName _result116 = getInstantAppInstallerComponent();
                    reply.writeNoException();
                    reply.writeTypedObject(_result116, 1);
                    return true;
                case 167:
                    String _arg0143 = data.readString();
                    int _arg1111 = data.readInt();
                    data.enforceNoDataAvail();
                    String _result117 = getInstantAppAndroidId(_arg0143, _arg1111);
                    reply.writeNoException();
                    reply.writeString(_result117);
                    return true;
                case 168:
                    IArtManager _result118 = getArtManager();
                    reply.writeNoException();
                    reply.writeStrongInterface(_result118);
                    return true;
                case 169:
                    String _arg0144 = data.readString();
                    CharSequence _arg1112 = (CharSequence) data.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    int _arg265 = data.readInt();
                    data.enforceNoDataAvail();
                    setHarmfulAppWarning(_arg0144, _arg1112, _arg265);
                    reply.writeNoException();
                    return true;
                case 170:
                    String _arg0145 = data.readString();
                    int _arg1113 = data.readInt();
                    data.enforceNoDataAvail();
                    CharSequence _result119 = getHarmfulAppWarning(_arg0145, _arg1113);
                    reply.writeNoException();
                    if (_result119 != null) {
                        reply.writeInt(1);
                        TextUtils.writeToParcel(_result119, reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 171:
                    String _arg0146 = data.readString();
                    byte[] _arg1114 = data.createByteArray();
                    int _arg266 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result120 = hasSigningCertificate(_arg0146, _arg1114, _arg266);
                    reply.writeNoException();
                    reply.writeBoolean(_result120);
                    return true;
                case 172:
                    int _arg0147 = data.readInt();
                    byte[] _arg1115 = data.createByteArray();
                    int _arg267 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result121 = hasUidSigningCertificate(_arg0147, _arg1115, _arg267);
                    reply.writeNoException();
                    reply.writeBoolean(_result121);
                    return true;
                case 173:
                    String _result122 = getDefaultTextClassifierPackageName();
                    reply.writeNoException();
                    reply.writeString(_result122);
                    return true;
                case 174:
                    String _result123 = getSystemTextClassifierPackageName();
                    reply.writeNoException();
                    reply.writeString(_result123);
                    return true;
                case 175:
                    String _result124 = getAttentionServicePackageName();
                    reply.writeNoException();
                    reply.writeString(_result124);
                    return true;
                case 176:
                    String _result125 = getRotationResolverPackageName();
                    reply.writeNoException();
                    reply.writeString(_result125);
                    return true;
                case 177:
                    String _result126 = getWellbeingPackageName();
                    reply.writeNoException();
                    reply.writeString(_result126);
                    return true;
                case 178:
                    String _result127 = getAppPredictionServicePackageName();
                    reply.writeNoException();
                    reply.writeString(_result127);
                    return true;
                case 179:
                    String _result128 = getSystemCaptionsServicePackageName();
                    reply.writeNoException();
                    reply.writeString(_result128);
                    return true;
                case 180:
                    String _result129 = getSetupWizardPackageName();
                    reply.writeNoException();
                    reply.writeString(_result129);
                    return true;
                case 181:
                    String _result130 = getIncidentReportApproverPackageName();
                    reply.writeNoException();
                    reply.writeString(_result130);
                    return true;
                case 182:
                    String _arg0148 = data.readString();
                    int _arg1116 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result131 = isPackageStateProtected(_arg0148, _arg1116);
                    reply.writeNoException();
                    reply.writeBoolean(_result131);
                    return true;
                case 183:
                    sendDeviceCustomizationReadyBroadcast();
                    reply.writeNoException();
                    return true;
                case 184:
                    int _arg0149 = data.readInt();
                    data.enforceNoDataAvail();
                    List<ModuleInfo> _result132 = getInstalledModules(_arg0149);
                    reply.writeNoException();
                    reply.writeTypedList(_result132, 1);
                    return true;
                case 185:
                    String _arg0150 = data.readString();
                    int _arg1117 = data.readInt();
                    data.enforceNoDataAvail();
                    ModuleInfo _result133 = getModuleInfo(_arg0150, _arg1117);
                    reply.writeNoException();
                    reply.writeTypedObject(_result133, 1);
                    return true;
                case 186:
                    int _arg0151 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result134 = getRuntimePermissionsVersion(_arg0151);
                    reply.writeNoException();
                    reply.writeInt(_result134);
                    return true;
                case 187:
                    int _arg0152 = data.readInt();
                    int _arg1118 = data.readInt();
                    data.enforceNoDataAvail();
                    setRuntimePermissionsVersion(_arg0152, _arg1118);
                    reply.writeNoException();
                    return true;
                case 188:
                    String[] _arg0153 = data.createStringArray();
                    data.enforceNoDataAvail();
                    notifyPackagesReplacedReceived(_arg0153);
                    reply.writeNoException();
                    return true;
                case 189:
                    String _arg0154 = data.readString();
                    boolean _arg1119 = data.readBoolean();
                    int _arg268 = data.readInt();
                    int _arg328 = data.readInt();
                    ClassLoader cl = getClass().getClassLoader();
                    List _arg414 = data.readArrayList(cl);
                    IOnChecksumsReadyListener _arg57 = IOnChecksumsReadyListener.Stub.asInterface(data.readStrongBinder());
                    int _arg63 = data.readInt();
                    data.enforceNoDataAvail();
                    requestPackageChecksums(_arg0154, _arg1119, _arg268, _arg328, _arg414, _arg57, _arg63);
                    reply.writeNoException();
                    return true;
                case 190:
                    String _arg0155 = data.readString();
                    String _arg1120 = data.readString();
                    String _arg269 = data.readString();
                    int _arg329 = data.readInt();
                    data.enforceNoDataAvail();
                    IntentSender _result135 = getLaunchIntentSenderForPackage(_arg0155, _arg1120, _arg269, _arg329);
                    reply.writeNoException();
                    reply.writeTypedObject(_result135, 1);
                    return true;
                case 191:
                    String _arg0156 = data.readString();
                    int _arg1121 = data.readInt();
                    data.enforceNoDataAvail();
                    String[] _result136 = getAppOpPermissionPackages(_arg0156, _arg1121);
                    reply.writeNoException();
                    reply.writeStringArray(_result136);
                    return true;
                case 192:
                    String _arg0157 = data.readString();
                    int _arg1122 = data.readInt();
                    data.enforceNoDataAvail();
                    PermissionGroupInfo _result137 = getPermissionGroupInfo(_arg0157, _arg1122);
                    reply.writeNoException();
                    reply.writeTypedObject(_result137, 1);
                    return true;
                case 193:
                    PermissionInfo _arg0158 = (PermissionInfo) data.readTypedObject(PermissionInfo.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result138 = addPermission(_arg0158);
                    reply.writeNoException();
                    reply.writeBoolean(_result138);
                    return true;
                case 194:
                    PermissionInfo _arg0159 = (PermissionInfo) data.readTypedObject(PermissionInfo.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result139 = addPermissionAsync(_arg0159);
                    reply.writeNoException();
                    reply.writeBoolean(_result139);
                    return true;
                case 195:
                    String _arg0160 = data.readString();
                    data.enforceNoDataAvail();
                    removePermission(_arg0160);
                    reply.writeNoException();
                    return true;
                case 196:
                    String _arg0161 = data.readString();
                    String _arg1123 = data.readString();
                    int _arg270 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result140 = checkPermission(_arg0161, _arg1123, _arg270);
                    reply.writeNoException();
                    reply.writeInt(_result140);
                    return true;
                case 197:
                    String _arg0162 = data.readString();
                    String _arg1124 = data.readString();
                    int _arg271 = data.readInt();
                    data.enforceNoDataAvail();
                    grantRuntimePermission(_arg0162, _arg1124, _arg271);
                    reply.writeNoException();
                    return true;
                case 198:
                    String _arg0163 = data.readString();
                    int _arg1125 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result141 = checkUidPermission(_arg0163, _arg1125);
                    reply.writeNoException();
                    reply.writeInt(_result141);
                    return true;
                case 199:
                    String _arg0164 = data.readString();
                    String _arg1126 = data.readString();
                    List<String> _arg272 = data.createStringArrayList();
                    data.enforceNoDataAvail();
                    setMimeGroup(_arg0164, _arg1126, _arg272);
                    reply.writeNoException();
                    return true;
                case 200:
                    String _arg0165 = data.readString();
                    int _arg1127 = data.readInt();
                    data.enforceNoDataAvail();
                    String _result142 = getSplashScreenTheme(_arg0165, _arg1127);
                    reply.writeNoException();
                    reply.writeString(_result142);
                    return true;
                case 201:
                    String _arg0166 = data.readString();
                    String _arg1128 = data.readString();
                    int _arg273 = data.readInt();
                    data.enforceNoDataAvail();
                    setSplashScreenTheme(_arg0166, _arg1128, _arg273);
                    reply.writeNoException();
                    return true;
                case 202:
                    String _arg0167 = data.readString();
                    int _arg1129 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result143 = getUserMinAspectRatio(_arg0167, _arg1129);
                    reply.writeNoException();
                    reply.writeInt(_result143);
                    return true;
                case 203:
                    String _arg0168 = data.readString();
                    int _arg1130 = data.readInt();
                    int _arg274 = data.readInt();
                    data.enforceNoDataAvail();
                    setUserMinAspectRatio(_arg0168, _arg1130, _arg274);
                    reply.writeNoException();
                    return true;
                case 204:
                    String _arg0169 = data.readString();
                    String _arg1131 = data.readString();
                    data.enforceNoDataAvail();
                    List<String> _result144 = getMimeGroup(_arg0169, _arg1131);
                    reply.writeNoException();
                    reply.writeStringList(_result144);
                    return true;
                case 205:
                    String _arg0170 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result145 = isAutoRevokeWhitelisted(_arg0170);
                    reply.writeNoException();
                    reply.writeBoolean(_result145);
                    return true;
                case 206:
                    int _arg0171 = data.readInt();
                    String _arg1132 = data.readString();
                    data.enforceNoDataAvail();
                    makeProviderVisible(_arg0171, _arg1132);
                    reply.writeNoException();
                    return true;
                case 207:
                    int _arg0172 = data.readInt();
                    int _arg1133 = data.readInt();
                    data.enforceNoDataAvail();
                    makeUidVisible(_arg0172, _arg1133);
                    reply.writeNoException();
                    return true;
                case 208:
                    IBinder _result146 = getHoldLockToken();
                    reply.writeNoException();
                    reply.writeStrongBinder(_result146);
                    return true;
                case 209:
                    IBinder _arg0173 = data.readStrongBinder();
                    int _arg1134 = data.readInt();
                    data.enforceNoDataAvail();
                    holdLock(_arg0173, _arg1134);
                    reply.writeNoException();
                    return true;
                case 210:
                    String _arg0174 = data.readString();
                    String _arg1135 = data.readString();
                    String _arg275 = data.readString();
                    int _arg330 = data.readInt();
                    data.enforceNoDataAvail();
                    PackageManager.Property _result147 = getPropertyAsUser(_arg0174, _arg1135, _arg275, _arg330);
                    reply.writeNoException();
                    reply.writeTypedObject(_result147, 1);
                    return true;
                case 211:
                    String _arg0175 = data.readString();
                    int _arg1136 = data.readInt();
                    data.enforceNoDataAvail();
                    ParceledListSlice _result148 = queryProperty(_arg0175, _arg1136);
                    reply.writeNoException();
                    reply.writeTypedObject(_result148, 1);
                    return true;
                case 212:
                    List<String> _arg0176 = data.createStringArrayList();
                    data.enforceNoDataAvail();
                    setKeepUninstalledPackages(_arg0176);
                    reply.writeNoException();
                    return true;
                case 213:
                    String _arg0177 = data.readString();
                    data.enforceNoDataAvail();
                    int _result149 = setLicensePermissionsForMDM(_arg0177);
                    reply.writeNoException();
                    reply.writeInt(_result149);
                    return true;
                case 214:
                    String _arg0178 = data.readString();
                    data.enforceNoDataAvail();
                    List<String> _result150 = getPackageGrantedPermissionsForMDM(_arg0178);
                    reply.writeNoException();
                    reply.writeStringList(_result150);
                    return true;
                case 215:
                    String _arg0179 = data.readString();
                    data.enforceNoDataAvail();
                    List<String> _result151 = getGrantedPermissionsForMDM(_arg0179);
                    reply.writeNoException();
                    reply.writeStringList(_result151);
                    return true;
                case 216:
                    String _arg0180 = data.readString();
                    int _arg1137 = data.readInt();
                    data.enforceNoDataAvail();
                    clearPackagePreferredActivitiesAsUserForMDM(_arg0180, _arg1137);
                    reply.writeNoException();
                    return true;
                case 217:
                    String _arg0181 = data.readString();
                    List<String> _arg1138 = data.createStringArrayList();
                    int _arg276 = data.readInt();
                    int _arg331 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result152 = applyRuntimePermissionsForMDM(_arg0181, _arg1138, _arg276, _arg331);
                    reply.writeNoException();
                    reply.writeBoolean(_result152);
                    return true;
                case 218:
                    int _arg0182 = data.readInt();
                    int _arg1139 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result153 = applyRuntimePermissionsForAllApplicationsForMDM(_arg0182, _arg1139);
                    reply.writeNoException();
                    reply.writeBoolean(_result153);
                    return true;
                case 219:
                    String _arg0183 = data.readString();
                    data.enforceNoDataAvail();
                    List<String> _result154 = getRequestedRuntimePermissionsForMDM(_arg0183);
                    reply.writeNoException();
                    reply.writeStringList(_result154);
                    return true;
                case 220:
                    String _arg0184 = data.readString();
                    String[] _arg1140 = data.createStringArray();
                    int _arg277 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean[] _result155 = canPackageQuery(_arg0184, _arg1140, _arg277);
                    reply.writeNoException();
                    reply.writeBooleanArray(_result155);
                    return true;
                case 221:
                    long _arg0185 = data.readLong();
                    boolean _arg1141 = data.readBoolean();
                    data.enforceNoDataAvail();
                    boolean _result156 = waitForHandler(_arg0185, _arg1141);
                    reply.writeNoException();
                    reply.writeBoolean(_result156);
                    return true;
                case 222:
                    IRemoteCallback _arg0186 = IRemoteCallback.Stub.asInterface(data.readStrongBinder());
                    int _arg1142 = data.readInt();
                    data.enforceNoDataAvail();
                    registerPackageMonitorCallback(_arg0186, _arg1142);
                    reply.writeNoException();
                    return true;
                case 223:
                    IRemoteCallback _arg0187 = IRemoteCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    unregisterPackageMonitorCallback(_arg0187);
                    reply.writeNoException();
                    return true;
                case 224:
                    String _arg0188 = data.readString();
                    int _arg1143 = data.readInt();
                    data.enforceNoDataAvail();
                    ArchivedPackageParcel _result157 = getArchivedPackage(_arg0188, _arg1143);
                    reply.writeNoException();
                    reply.writeTypedObject(_result157, 1);
                    return true;
                case 225:
                    String _arg0189 = data.readString();
                    UserHandle _arg1144 = (UserHandle) data.readTypedObject(UserHandle.CREATOR);
                    String _arg278 = data.readString();
                    data.enforceNoDataAvail();
                    Bitmap _result158 = getArchivedAppIcon(_arg0189, _arg1144, _arg278);
                    reply.writeNoException();
                    reply.writeTypedObject(_result158, 1);
                    return true;
                case 226:
                    String _arg0190 = data.readString();
                    UserHandle _arg1145 = (UserHandle) data.readTypedObject(UserHandle.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result159 = isAppArchivable(_arg0190, _arg1145);
                    reply.writeNoException();
                    reply.writeBoolean(_result159);
                    return true;
                case 227:
                    String _arg0191 = data.readString();
                    int _arg1146 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result160 = getAppMetadataSource(_arg0191, _arg1146);
                    reply.writeNoException();
                    reply.writeInt(_result160);
                    return true;
                case 228:
                    int _arg0192 = data.readInt();
                    data.enforceNoDataAvail();
                    ComponentName _result161 = getDomainVerificationAgent(_arg0192);
                    reply.writeNoException();
                    reply.writeTypedObject(_result161, 1);
                    return true;
                case 229:
                    String _arg0193 = data.readString();
                    data.enforceNoDataAvail();
                    List<String> _result162 = getPackageListForDualDarPolicy(_arg0193);
                    reply.writeNoException();
                    reply.writeStringList(_result162);
                    return true;
                case 230:
                    String _arg0194 = data.readString();
                    int _arg1147 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result163 = createEncAppData(_arg0194, _arg1147);
                    reply.writeNoException();
                    reply.writeBoolean(_result163);
                    return true;
                case 231:
                    int _arg0195 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result164 = removeEncUserDir(_arg0195);
                    reply.writeNoException();
                    reply.writeBoolean(_result164);
                    return true;
                case 232:
                    int _arg0196 = data.readInt();
                    String _arg1148 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result165 = removeEncPkgDir(_arg0196, _arg1148);
                    reply.writeNoException();
                    reply.writeBoolean(_result165);
                    return true;
                case 233:
                    String _arg0197 = data.readString();
                    String _arg1149 = data.readString();
                    int _arg279 = data.readInt();
                    List<String> _arg332 = new ArrayList<>();
                    data.enforceNoDataAvail();
                    boolean _result166 = getMetadataForIconTray(_arg0197, _arg1149, _arg279, _arg332);
                    reply.writeNoException();
                    reply.writeBoolean(_result166);
                    reply.writeStringList(_arg332);
                    return true;
                case 234:
                    String _arg0198 = data.readString();
                    String _arg1150 = data.readString();
                    int _arg280 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result167 = semIsPermissionRevokedByUserFixed(_arg0198, _arg1150, _arg280);
                    reply.writeNoException();
                    reply.writeBoolean(_result167);
                    return true;
                case 235:
                    String _arg0199 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result168 = isUnknownSourcePackage(_arg0199);
                    reply.writeNoException();
                    reply.writeBoolean(_result168);
                    return true;
                case 236:
                    long _arg0200 = data.readLong();
                    int _arg1151 = data.readInt();
                    data.enforceNoDataAvail();
                    ParceledListSlice _result169 = getUnknownSourcePackagesAsUser(_arg0200, _arg1151);
                    reply.writeNoException();
                    reply.writeTypedObject(_result169, 1);
                    return true;
                case 237:
                    String _arg0201 = data.readString();
                    int _arg1152 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result170 = isPackageAutoDisabled(_arg0201, _arg1152);
                    reply.writeNoException();
                    reply.writeBoolean(_result170);
                    return true;
                case 238:
                    String _arg0202 = data.readString();
                    int _arg1153 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result171 = isSystemCompressedPackage(_arg0202, _arg1153);
                    reply.writeNoException();
                    reply.writeBoolean(_result171);
                    return true;
                case 239:
                    String _arg0203 = data.readString();
                    String _arg1154 = data.readString();
                    data.enforceNoDataAvail();
                    changeMonetizationBadgeState(_arg0203, _arg1154);
                    reply.writeNoException();
                    return true;
                case 240:
                    String _arg0204 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result172 = shouldAppSupportBadgeIcon(_arg0204);
                    reply.writeNoException();
                    reply.writeBoolean(_result172);
                    return true;
                case 241:
                    String _arg0205 = data.readString();
                    int _arg1155 = data.readInt();
                    data.enforceNoDataAvail();
                    setAppCategoryHintUser(_arg0205, _arg1155);
                    reply.writeNoException();
                    return true;
                case 242:
                    String _arg0206 = data.readString();
                    data.enforceNoDataAvail();
                    clearAppCategoryHintUser(_arg0206);
                    reply.writeNoException();
                    return true;
                case 243:
                    Map<String, String> _result173 = getAppCategoryHintUserMap();
                    reply.writeNoException();
                    if (_result173 == null) {
                        reply.writeInt(-1);
                    } else {
                        reply.writeInt(_result173.size());
                        _result173.forEach(new BiConsumer() { // from class: android.content.pm.IPackageManager$Stub$$ExternalSyntheticLambda2
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                IPackageManager.Stub.lambda$onTransact$2(Parcel.this, (String) obj, (String) obj2);
                            }
                        });
                    }
                    return true;
                case 244:
                    String _arg0207 = data.readString();
                    data.enforceNoDataAvail();
                    Map<String, String[]> _result174 = getAppCategoryInfos(_arg0207);
                    reply.writeNoException();
                    if (_result174 == null) {
                        reply.writeInt(-1);
                    } else {
                        reply.writeInt(_result174.size());
                        _result174.forEach(new BiConsumer() { // from class: android.content.pm.IPackageManager$Stub$$ExternalSyntheticLambda3
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                IPackageManager.Stub.lambda$onTransact$3(Parcel.this, (String) obj, (String[]) obj2);
                            }
                        });
                    }
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        static /* synthetic */ void lambda$onTransact$0(Parcel reply, String k, String v) {
            reply.writeString(k);
            reply.writeString(v);
        }

        static /* synthetic */ void lambda$onTransact$1(Parcel data, Map _arg1, int i) {
            String k = data.readString();
            String v = data.readString();
            _arg1.put(k, v);
        }

        static /* synthetic */ void lambda$onTransact$2(Parcel reply, String k, String v) {
            reply.writeString(k);
            reply.writeString(v);
        }

        static /* synthetic */ void lambda$onTransact$3(Parcel reply, String k, String[] v) {
            reply.writeString(k);
            reply.writeStringArray(v);
        }

        /* JADX INFO: Access modifiers changed from: private */
        static class Proxy implements IPackageManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.content.pm.IPackageManager
            public void checkPackageStartable(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isPackageAvailable(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public PackageInfo getPackageInfo(String packageName, long flags, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeLong(flags);
                    _data.writeInt(userId);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    PackageInfo _result = (PackageInfo) _reply.readTypedObject(PackageInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public PackageInfo getPackageInfoVersioned(VersionedPackage versionedPackage, long flags, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(versionedPackage, 0);
                    _data.writeLong(flags);
                    _data.writeInt(userId);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    PackageInfo _result = (PackageInfo) _reply.readTypedObject(PackageInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int getPackageUid(String packageName, long flags, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeLong(flags);
                    _data.writeInt(userId);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int[] getPackageGids(String packageName, long flags, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeLong(flags);
                    _data.writeInt(userId);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String[] currentToCanonicalPackageNames(String[] names) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringArray(names);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String[] canonicalToCurrentPackageNames(String[] names) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringArray(names);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ApplicationInfo getApplicationInfo(String packageName, long flags, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeLong(flags);
                    _data.writeInt(userId);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    ApplicationInfo _result = (ApplicationInfo) _reply.readTypedObject(ApplicationInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int getTargetSdkVersion(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ActivityInfo getActivityInfo(ComponentName className, long flags, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(className, 0);
                    _data.writeLong(flags);
                    _data.writeInt(userId);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                    ActivityInfo _result = (ActivityInfo) _reply.readTypedObject(ActivityInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean activitySupportsIntentAsUser(ComponentName className, Intent intent, String resolvedType, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(className, 0);
                    _data.writeTypedObject(intent, 0);
                    _data.writeString(resolvedType);
                    _data.writeInt(userId);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ActivityInfo getReceiverInfo(ComponentName className, long flags, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(className, 0);
                    _data.writeLong(flags);
                    _data.writeInt(userId);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                    ActivityInfo _result = (ActivityInfo) _reply.readTypedObject(ActivityInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ServiceInfo getServiceInfo(ComponentName className, long flags, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(className, 0);
                    _data.writeLong(flags);
                    _data.writeInt(userId);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                    ServiceInfo _result = (ServiceInfo) _reply.readTypedObject(ServiceInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ProviderInfo getProviderInfo(ComponentName className, long flags, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(className, 0);
                    _data.writeLong(flags);
                    _data.writeInt(userId);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                    ProviderInfo _result = (ProviderInfo) _reply.readTypedObject(ProviderInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isProtectedBroadcast(String actionName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(actionName);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int checkSignatures(String pkg1, String pkg2, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkg1);
                    _data.writeString(pkg2);
                    _data.writeInt(userId);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int checkUidSignatures(int uid1, int uid2) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid1);
                    _data.writeInt(uid2);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public List<String> getAllPackages() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(19, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String[] getPackagesForUid(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    this.mRemote.transact(20, _data, _reply, 0);
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String getNameForUid(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    this.mRemote.transact(21, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String[] getNamesForUids(int[] uids) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeIntArray(uids);
                    this.mRemote.transact(22, _data, _reply, 0);
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int getUidForSharedUser(String sharedUserName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(sharedUserName);
                    this.mRemote.transact(23, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int getFlagsForUid(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    this.mRemote.transact(24, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int getPrivateFlagsForUid(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    this.mRemote.transact(25, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isUidPrivileged(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    this.mRemote.transact(26, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ResolveInfo resolveIntent(Intent intent, String resolvedType, long flags, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(intent, 0);
                    _data.writeString(resolvedType);
                    _data.writeLong(flags);
                    _data.writeInt(userId);
                    this.mRemote.transact(27, _data, _reply, 0);
                    _reply.readException();
                    ResolveInfo _result = (ResolveInfo) _reply.readTypedObject(ResolveInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ResolveInfo findPersistentPreferredActivity(Intent intent, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(intent, 0);
                    _data.writeInt(userId);
                    this.mRemote.transact(28, _data, _reply, 0);
                    _reply.readException();
                    ResolveInfo _result = (ResolveInfo) _reply.readTypedObject(ResolveInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean canForwardTo(Intent intent, String resolvedType, int sourceUserId, int targetUserId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(intent, 0);
                    _data.writeString(resolvedType);
                    _data.writeInt(sourceUserId);
                    _data.writeInt(targetUserId);
                    this.mRemote.transact(29, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ParceledListSlice queryIntentActivities(Intent intent, String resolvedType, long flags, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(intent, 0);
                    _data.writeString(resolvedType);
                    _data.writeLong(flags);
                    _data.writeInt(userId);
                    this.mRemote.transact(30, _data, _reply, 0);
                    _reply.readException();
                    ParceledListSlice _result = (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ParceledListSlice queryIntentActivityOptions(ComponentName caller, Intent[] specifics, String[] specificTypes, Intent intent, String resolvedType, long flags, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(caller, 0);
                    _data.writeTypedArray(specifics, 0);
                    _data.writeStringArray(specificTypes);
                    _data.writeTypedObject(intent, 0);
                    _data.writeString(resolvedType);
                    _data.writeLong(flags);
                    _data.writeInt(userId);
                    this.mRemote.transact(31, _data, _reply, 0);
                    _reply.readException();
                    ParceledListSlice _result = (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ParceledListSlice queryIntentReceivers(Intent intent, String resolvedType, long flags, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(intent, 0);
                    _data.writeString(resolvedType);
                    _data.writeLong(flags);
                    _data.writeInt(userId);
                    this.mRemote.transact(32, _data, _reply, 0);
                    _reply.readException();
                    ParceledListSlice _result = (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ResolveInfo resolveService(Intent intent, String resolvedType, long flags, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(intent, 0);
                    _data.writeString(resolvedType);
                    _data.writeLong(flags);
                    _data.writeInt(userId);
                    this.mRemote.transact(33, _data, _reply, 0);
                    _reply.readException();
                    ResolveInfo _result = (ResolveInfo) _reply.readTypedObject(ResolveInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ParceledListSlice queryIntentServices(Intent intent, String resolvedType, long flags, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(intent, 0);
                    _data.writeString(resolvedType);
                    _data.writeLong(flags);
                    _data.writeInt(userId);
                    this.mRemote.transact(34, _data, _reply, 0);
                    _reply.readException();
                    ParceledListSlice _result = (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ParceledListSlice queryIntentContentProviders(Intent intent, String resolvedType, long flags, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(intent, 0);
                    _data.writeString(resolvedType);
                    _data.writeLong(flags);
                    _data.writeInt(userId);
                    this.mRemote.transact(35, _data, _reply, 0);
                    _reply.readException();
                    ParceledListSlice _result = (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ParceledListSlice getInstalledPackages(long flags, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(flags);
                    _data.writeInt(userId);
                    this.mRemote.transact(36, _data, _reply, 0);
                    _reply.readException();
                    ParceledListSlice _result = (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ParcelFileDescriptor getAppMetadataFd(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(37, _data, _reply, 0);
                    _reply.readException();
                    ParcelFileDescriptor _result = (ParcelFileDescriptor) _reply.readTypedObject(ParcelFileDescriptor.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ParceledListSlice getPackagesHoldingPermissions(String[] permissions, long flags, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringArray(permissions);
                    _data.writeLong(flags);
                    _data.writeInt(userId);
                    this.mRemote.transact(38, _data, _reply, 0);
                    _reply.readException();
                    ParceledListSlice _result = (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ParceledListSlice getInstalledApplications(long flags, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(flags);
                    _data.writeInt(userId);
                    this.mRemote.transact(39, _data, _reply, 0);
                    _reply.readException();
                    ParceledListSlice _result = (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ParceledListSlice getPersistentApplications(int flags) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(flags);
                    this.mRemote.transact(40, _data, _reply, 0);
                    _reply.readException();
                    ParceledListSlice _result = (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ProviderInfo resolveContentProvider(String name, long flags, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeLong(flags);
                    _data.writeInt(userId);
                    this.mRemote.transact(41, _data, _reply, 0);
                    _reply.readException();
                    ProviderInfo _result = (ProviderInfo) _reply.readTypedObject(ProviderInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void querySyncProviders(List<String> outNames, List<ProviderInfo> outInfo) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringList(outNames);
                    _data.writeTypedList(outInfo, 0);
                    this.mRemote.transact(42, _data, _reply, 0);
                    _reply.readException();
                    _reply.readStringList(outNames);
                    _reply.readTypedList(outInfo, ProviderInfo.CREATOR);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ParceledListSlice queryContentProviders(String processName, int uid, long flags, String metaDataKey) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(processName);
                    _data.writeInt(uid);
                    _data.writeLong(flags);
                    _data.writeString(metaDataKey);
                    this.mRemote.transact(43, _data, _reply, 0);
                    _reply.readException();
                    ParceledListSlice _result = (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public InstrumentationInfo getInstrumentationInfoAsUser(ComponentName className, int flags, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(className, 0);
                    _data.writeInt(flags);
                    _data.writeInt(userId);
                    this.mRemote.transact(44, _data, _reply, 0);
                    _reply.readException();
                    InstrumentationInfo _result = (InstrumentationInfo) _reply.readTypedObject(InstrumentationInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ParceledListSlice queryInstrumentationAsUser(String targetPackage, int flags, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(targetPackage);
                    _data.writeInt(flags);
                    _data.writeInt(userId);
                    this.mRemote.transact(45, _data, _reply, 0);
                    _reply.readException();
                    ParceledListSlice _result = (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void finishPackageInstall(int token, boolean didLaunch) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(token);
                    _data.writeBoolean(didLaunch);
                    this.mRemote.transact(46, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void setInstallerPackageName(String targetPackage, String installerPackageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(targetPackage);
                    _data.writeString(installerPackageName);
                    this.mRemote.transact(47, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void relinquishUpdateOwnership(String targetPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(targetPackage);
                    this.mRemote.transact(48, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void setApplicationCategoryHint(String packageName, int categoryHint, String callerPackageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(categoryHint);
                    _data.writeString(callerPackageName);
                    this.mRemote.transact(49, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void deletePackageAsUser(String packageName, int versionCode, IPackageDeleteObserver observer, int userId, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(versionCode);
                    _data.writeStrongInterface(observer);
                    _data.writeInt(userId);
                    _data.writeInt(flags);
                    this.mRemote.transact(50, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void deletePackageVersioned(VersionedPackage versionedPackage, IPackageDeleteObserver2 observer, int userId, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(versionedPackage, 0);
                    _data.writeStrongInterface(observer);
                    _data.writeInt(userId);
                    _data.writeInt(flags);
                    this.mRemote.transact(51, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void deleteExistingPackageAsUser(VersionedPackage versionedPackage, IPackageDeleteObserver2 observer, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(versionedPackage, 0);
                    _data.writeStrongInterface(observer);
                    _data.writeInt(userId);
                    this.mRemote.transact(52, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String getInstallerPackageName(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    this.mRemote.transact(53, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public InstallSourceInfo getInstallSourceInfo(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(54, _data, _reply, 0);
                    _reply.readException();
                    InstallSourceInfo _result = (InstallSourceInfo) _reply.readTypedObject(InstallSourceInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void resetApplicationPreferences(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(55, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ResolveInfo getLastChosenActivity(Intent intent, String resolvedType, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(intent, 0);
                    _data.writeString(resolvedType);
                    _data.writeInt(flags);
                    this.mRemote.transact(56, _data, _reply, 0);
                    _reply.readException();
                    ResolveInfo _result = (ResolveInfo) _reply.readTypedObject(ResolveInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void setLastChosenActivity(Intent intent, String resolvedType, int flags, IntentFilter filter, int match, ComponentName activity) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(intent, 0);
                    _data.writeString(resolvedType);
                    _data.writeInt(flags);
                    _data.writeTypedObject(filter, 0);
                    _data.writeInt(match);
                    _data.writeTypedObject(activity, 0);
                    this.mRemote.transact(57, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void addPreferredActivity(IntentFilter filter, int match, ComponentName[] set, ComponentName activity, int userId, boolean removeExisting) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(filter, 0);
                    _data.writeInt(match);
                    _data.writeTypedArray(set, 0);
                    _data.writeTypedObject(activity, 0);
                    _data.writeInt(userId);
                    _data.writeBoolean(removeExisting);
                    this.mRemote.transact(58, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void replacePreferredActivity(IntentFilter filter, int match, ComponentName[] set, ComponentName activity, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(filter, 0);
                    _data.writeInt(match);
                    _data.writeTypedArray(set, 0);
                    _data.writeTypedObject(activity, 0);
                    _data.writeInt(userId);
                    this.mRemote.transact(59, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void clearPackagePreferredActivities(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    this.mRemote.transact(60, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int getPreferredActivities(List<IntentFilter> outFilters, List<ComponentName> outActivities, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    this.mRemote.transact(61, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.readTypedList(outFilters, IntentFilter.CREATOR);
                    _reply.readTypedList(outActivities, ComponentName.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void addPersistentPreferredActivity(IntentFilter filter, ComponentName activity, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(filter, 0);
                    _data.writeTypedObject(activity, 0);
                    _data.writeInt(userId);
                    this.mRemote.transact(62, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void clearPackagePersistentPreferredActivities(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(63, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void clearPersistentPreferredActivity(IntentFilter filter, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(filter, 0);
                    _data.writeInt(userId);
                    this.mRemote.transact(64, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void addCrossProfileIntentFilter(IntentFilter intentFilter, String ownerPackage, int sourceUserId, int targetUserId, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(intentFilter, 0);
                    _data.writeString(ownerPackage);
                    _data.writeInt(sourceUserId);
                    _data.writeInt(targetUserId);
                    _data.writeInt(flags);
                    this.mRemote.transact(65, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean removeCrossProfileIntentFilter(IntentFilter intentFilter, String ownerPackage, int sourceUserId, int targetUserId, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(intentFilter, 0);
                    _data.writeString(ownerPackage);
                    _data.writeInt(sourceUserId);
                    _data.writeInt(targetUserId);
                    _data.writeInt(flags);
                    this.mRemote.transact(66, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void clearCrossProfileIntentFilters(int sourceUserId, String ownerPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sourceUserId);
                    _data.writeString(ownerPackage);
                    this.mRemote.transact(67, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String[] setDistractingPackageRestrictionsAsUser(String[] packageNames, int restrictionFlags, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringArray(packageNames);
                    _data.writeInt(restrictionFlags);
                    _data.writeInt(userId);
                    this.mRemote.transact(68, _data, _reply, 0);
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String[] setPackagesSuspendedAsUser(String[] packageNames, boolean suspended, PersistableBundle appExtras, PersistableBundle launcherExtras, SuspendDialogInfo dialogInfo, int flags, String suspendingPackage, int suspendingUserId, int targetUserId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringArray(packageNames);
                    _data.writeBoolean(suspended);
                    _data.writeTypedObject(appExtras, 0);
                    _data.writeTypedObject(launcherExtras, 0);
                    _data.writeTypedObject(dialogInfo, 0);
                    _data.writeInt(flags);
                    _data.writeString(suspendingPackage);
                    _data.writeInt(suspendingUserId);
                    _data.writeInt(targetUserId);
                    this.mRemote.transact(69, _data, _reply, 0);
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String[] getUnsuspendablePackagesForUser(String[] packageNames, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringArray(packageNames);
                    _data.writeInt(userId);
                    this.mRemote.transact(70, _data, _reply, 0);
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isPackageSuspendedForUser(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(71, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isPackageQuarantinedForUser(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(72, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isPackageStoppedForUser(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(73, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public Bundle getSuspendedPackageAppExtras(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(74, _data, _reply, 0);
                    _reply.readException();
                    Bundle _result = (Bundle) _reply.readTypedObject(Bundle.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String getSuspendingPackage(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(75, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public byte[] getPreferredActivityBackup(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(76, _data, _reply, 0);
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void restorePreferredActivities(byte[] backup, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByteArray(backup);
                    _data.writeInt(userId);
                    this.mRemote.transact(77, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public byte[] getDefaultAppsBackup(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(78, _data, _reply, 0);
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void restoreDefaultApps(byte[] backup, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByteArray(backup);
                    _data.writeInt(userId);
                    this.mRemote.transact(79, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public byte[] getDomainVerificationBackup(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(80, _data, _reply, 0);
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void restoreDomainVerification(byte[] backup, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByteArray(backup);
                    _data.writeInt(userId);
                    this.mRemote.transact(81, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ComponentName getHomeActivities(List<ResolveInfo> outHomeCandidates) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(82, _data, _reply, 0);
                    _reply.readException();
                    ComponentName _result = (ComponentName) _reply.readTypedObject(ComponentName.CREATOR);
                    _reply.readTypedList(outHomeCandidates, ResolveInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void setHomeActivity(ComponentName className, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(className, 0);
                    _data.writeInt(userId);
                    this.mRemote.transact(83, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void overrideLabelAndIcon(ComponentName componentName, String nonLocalizedLabel, int icon, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(componentName, 0);
                    _data.writeString(nonLocalizedLabel);
                    _data.writeInt(icon);
                    _data.writeInt(userId);
                    this.mRemote.transact(84, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void restoreLabelAndIcon(ComponentName componentName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(componentName, 0);
                    _data.writeInt(userId);
                    this.mRemote.transact(85, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void setComponentEnabledSetting(ComponentName componentName, int newState, int flags, int userId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(componentName, 0);
                    _data.writeInt(newState);
                    _data.writeInt(flags);
                    _data.writeInt(userId);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(86, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void setComponentEnabledSettings(List<PackageManager.ComponentEnabledSetting> settings, int userId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedList(settings, 0);
                    _data.writeInt(userId);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(87, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int getComponentEnabledSetting(ComponentName componentName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(componentName, 0);
                    _data.writeInt(userId);
                    this.mRemote.transact(88, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void setApplicationEnabledSetting(String packageName, int newState, int flags, int userId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(newState);
                    _data.writeInt(flags);
                    _data.writeInt(userId);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(89, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int getApplicationEnabledSetting(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(90, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void logAppProcessStartIfNeeded(String packageName, String processName, int uid, String seinfo, String apkFile, int pid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(processName);
                    _data.writeInt(uid);
                    _data.writeString(seinfo);
                    _data.writeString(apkFile);
                    _data.writeInt(pid);
                    this.mRemote.transact(91, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void flushPackageRestrictionsAsUser(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(92, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void setPackageStoppedState(String packageName, boolean stopped, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeBoolean(stopped);
                    _data.writeInt(userId);
                    this.mRemote.transact(93, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void freeStorageAndNotify(String volumeUuid, long freeStorageSize, int storageFlags, IPackageDataObserver observer) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(volumeUuid);
                    _data.writeLong(freeStorageSize);
                    _data.writeInt(storageFlags);
                    _data.writeStrongInterface(observer);
                    this.mRemote.transact(94, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void freeStorage(String volumeUuid, long freeStorageSize, int storageFlags, IntentSender pi) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(volumeUuid);
                    _data.writeLong(freeStorageSize);
                    _data.writeInt(storageFlags);
                    _data.writeTypedObject(pi, 0);
                    this.mRemote.transact(95, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void deleteApplicationCacheFiles(String packageName, IPackageDataObserver observer) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeStrongInterface(observer);
                    this.mRemote.transact(96, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void deleteApplicationCacheFilesAsUser(String packageName, int userId, IPackageDataObserver observer) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    _data.writeStrongInterface(observer);
                    this.mRemote.transact(97, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void clearApplicationUserData(String packageName, IPackageDataObserver observer, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeStrongInterface(observer);
                    _data.writeInt(userId);
                    this.mRemote.transact(98, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void clearApplicationProfileData(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    this.mRemote.transact(99, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void getPackageSizeInfo(String packageName, int userHandle, IPackageStatsObserver observer) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userHandle);
                    _data.writeStrongInterface(observer);
                    this.mRemote.transact(100, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String[] getSystemSharedLibraryNames() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(101, _data, _reply, 0);
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public Map<String, String> getSystemSharedLibraryNamesAndPaths() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                final Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(102, _data, _reply, 0);
                    _reply.readException();
                    int N = _reply.readInt();
                    final Map<String, String> _result = N < 0 ? null : new HashMap<>();
                    IntStream.range(0, N).forEach(new IntConsumer() { // from class: android.content.pm.IPackageManager$Stub$Proxy$$ExternalSyntheticLambda0
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i) {
                            IPackageManager.Stub.Proxy.lambda$getSystemSharedLibraryNamesAndPaths$0(Parcel.this, _result, i);
                        }
                    });
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            static /* synthetic */ void lambda$getSystemSharedLibraryNamesAndPaths$0(Parcel _reply, Map _result, int i) {
                String k = _reply.readString();
                String v = _reply.readString();
                _result.put(k, v);
            }

            @Override // android.content.pm.IPackageManager
            public ParceledListSlice getSystemAvailableFeatures() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(103, _data, _reply, 0);
                    _reply.readException();
                    ParceledListSlice _result = (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean hasSystemFeature(String name, int version) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeInt(version);
                    this.mRemote.transact(104, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public List<String> getInitialNonStoppedSystemPackages() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(105, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void enterSafeMode() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(106, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isSafeMode() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(107, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean hasSystemUidErrors() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(108, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void notifyPackageUse(String packageName, int reason) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(reason);
                    this.mRemote.transact(109, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void notifyDexLoad(String loadingPackageName, Map<String, String> classLoaderContextMap, String loaderIsa) throws RemoteException {
                final Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(loadingPackageName);
                    if (classLoaderContextMap == null) {
                        _data.writeInt(-1);
                    } else {
                        _data.writeInt(classLoaderContextMap.size());
                        classLoaderContextMap.forEach(new BiConsumer() { // from class: android.content.pm.IPackageManager$Stub$Proxy$$ExternalSyntheticLambda3
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                IPackageManager.Stub.Proxy.lambda$notifyDexLoad$1(Parcel.this, (String) obj, (String) obj2);
                            }
                        });
                    }
                    _data.writeString(loaderIsa);
                    this.mRemote.transact(110, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            static /* synthetic */ void lambda$notifyDexLoad$1(Parcel _data, String k, String v) {
                _data.writeString(k);
                _data.writeString(v);
            }

            @Override // android.content.pm.IPackageManager
            public void registerDexModule(String packageName, String dexModulePath, boolean isSharedModule, IDexModuleRegisterCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(dexModulePath);
                    _data.writeBoolean(isSharedModule);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(111, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean performDexOptMode(String packageName, boolean checkProfiles, String targetCompilerFilter, boolean force, boolean bootComplete, String splitName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeBoolean(checkProfiles);
                    _data.writeString(targetCompilerFilter);
                    _data.writeBoolean(force);
                    _data.writeBoolean(bootComplete);
                    _data.writeString(splitName);
                    this.mRemote.transact(112, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean performDexOptSecondary(String packageName, String targetCompilerFilter, boolean force) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(targetCompilerFilter);
                    _data.writeBoolean(force);
                    this.mRemote.transact(113, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int performDexOptForADCP(String packageName, boolean force) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeBoolean(force);
                    this.mRemote.transact(114, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int getMoveStatus(int moveId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(moveId);
                    this.mRemote.transact(115, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void registerMoveCallback(IPackageMoveObserver callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(116, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void unregisterMoveCallback(IPackageMoveObserver callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(117, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int movePackage(String packageName, String volumeUuid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(volumeUuid);
                    this.mRemote.transact(118, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int movePackageToSd(String packageName, String volumeUuid, IMemorySaverPackageMoveObserver observer) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(volumeUuid);
                    _data.writeStrongInterface(observer);
                    this.mRemote.transact(119, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int movePrimaryStorage(String volumeUuid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(volumeUuid);
                    this.mRemote.transact(120, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean setInstallLocation(int loc) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(loc);
                    this.mRemote.transact(121, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int getInstallLocation() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(122, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int installExistingPackageAsUser(String packageName, int userId, int installFlags, int installReason, List<String> whiteListedPermissions) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    _data.writeInt(installFlags);
                    _data.writeInt(installReason);
                    _data.writeStringList(whiteListedPermissions);
                    this.mRemote.transact(123, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void verifyPendingInstall(int id, int verificationCode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(id);
                    _data.writeInt(verificationCode);
                    this.mRemote.transact(124, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void extendVerificationTimeout(int id, int verificationCodeAtTimeout, long millisecondsToDelay) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(id);
                    _data.writeInt(verificationCodeAtTimeout);
                    _data.writeLong(millisecondsToDelay);
                    this.mRemote.transact(125, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void verifyIntentFilter(int id, int verificationCode, List<String> failedDomains) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(id);
                    _data.writeInt(verificationCode);
                    _data.writeStringList(failedDomains);
                    this.mRemote.transact(126, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int getIntentVerificationStatus(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(127, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean updateIntentVerificationStatus(String packageName, int status, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(status);
                    _data.writeInt(userId);
                    this.mRemote.transact(128, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ParceledListSlice getIntentFilterVerifications(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    this.mRemote.transact(129, _data, _reply, 0);
                    _reply.readException();
                    ParceledListSlice _result = (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ParceledListSlice getAllIntentFilters(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    this.mRemote.transact(130, _data, _reply, 0);
                    _reply.readException();
                    ParceledListSlice _result = (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public VerifierDeviceIdentity getVerifierDeviceIdentity() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(131, _data, _reply, 0);
                    _reply.readException();
                    VerifierDeviceIdentity _result = (VerifierDeviceIdentity) _reply.readTypedObject(VerifierDeviceIdentity.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isFirstBoot() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(132, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isDeviceUpgrading() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(133, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isStorageLow() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(134, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean setApplicationHiddenSettingAsUser(String packageName, boolean hidden, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeBoolean(hidden);
                    _data.writeInt(userId);
                    this.mRemote.transact(135, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean getApplicationHiddenSettingAsUser(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(136, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void setSystemAppHiddenUntilInstalled(String packageName, boolean hidden) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeBoolean(hidden);
                    this.mRemote.transact(137, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean setSystemAppInstallState(String packageName, boolean installed, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeBoolean(installed);
                    _data.writeInt(userId);
                    this.mRemote.transact(138, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public IPackageInstaller getPackageInstaller() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(139, _data, _reply, 0);
                    _reply.readException();
                    IPackageInstaller _result = IPackageInstaller.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean setBlockUninstallForUser(String packageName, boolean blockUninstall, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeBoolean(blockUninstall);
                    _data.writeInt(userId);
                    this.mRemote.transact(140, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean getBlockUninstallForUser(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(141, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public KeySet getKeySetByAlias(String packageName, String alias) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(alias);
                    this.mRemote.transact(142, _data, _reply, 0);
                    _reply.readException();
                    KeySet _result = (KeySet) _reply.readTypedObject(KeySet.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public KeySet getSigningKeySet(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    this.mRemote.transact(143, _data, _reply, 0);
                    _reply.readException();
                    KeySet _result = (KeySet) _reply.readTypedObject(KeySet.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isPackageSignedByKeySet(String packageName, KeySet ks) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeTypedObject(ks, 0);
                    this.mRemote.transact(144, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isPackageSignedByKeySetExactly(String packageName, KeySet ks) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeTypedObject(ks, 0);
                    this.mRemote.transact(145, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String getPermissionControllerPackageName() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(146, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String getSdkSandboxPackageName() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(147, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ParceledListSlice getInstantApps(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(148, _data, _reply, 0);
                    _reply.readException();
                    ParceledListSlice _result = (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public byte[] getInstantAppCookie(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(149, _data, _reply, 0);
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean setInstantAppCookie(String packageName, byte[] cookie, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeByteArray(cookie);
                    _data.writeInt(userId);
                    this.mRemote.transact(150, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public Bitmap getInstantAppIcon(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(151, _data, _reply, 0);
                    _reply.readException();
                    Bitmap _result = (Bitmap) _reply.readTypedObject(Bitmap.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isInstantApp(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(152, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean setRequiredForSystemUser(String packageName, boolean systemUserApp) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeBoolean(systemUserApp);
                    this.mRemote.transact(153, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void setUpdateAvailable(String packageName, boolean updateAvaialble) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeBoolean(updateAvaialble);
                    this.mRemote.transact(154, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String getServicesSystemSharedLibraryPackageName() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(155, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String getSharedSystemSharedLibraryPackageName() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(156, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ChangedPackages getChangedPackages(int sequenceNumber, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sequenceNumber);
                    _data.writeInt(userId);
                    this.mRemote.transact(157, _data, _reply, 0);
                    _reply.readException();
                    ChangedPackages _result = (ChangedPackages) _reply.readTypedObject(ChangedPackages.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isPackageDeviceAdminOnAnyUser(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    this.mRemote.transact(158, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int getInstallReason(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(159, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ParceledListSlice getSharedLibraries(String packageName, long flags, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeLong(flags);
                    _data.writeInt(userId);
                    this.mRemote.transact(160, _data, _reply, 0);
                    _reply.readException();
                    ParceledListSlice _result = (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ParceledListSlice getDeclaredSharedLibraries(String packageName, long flags, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeLong(flags);
                    _data.writeInt(userId);
                    this.mRemote.transact(161, _data, _reply, 0);
                    _reply.readException();
                    ParceledListSlice _result = (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean canRequestPackageInstalls(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(162, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void deletePreloadsFileCache() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(163, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ComponentName getInstantAppResolverComponent() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(164, _data, _reply, 0);
                    _reply.readException();
                    ComponentName _result = (ComponentName) _reply.readTypedObject(ComponentName.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ComponentName getInstantAppResolverSettingsComponent() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(165, _data, _reply, 0);
                    _reply.readException();
                    ComponentName _result = (ComponentName) _reply.readTypedObject(ComponentName.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ComponentName getInstantAppInstallerComponent() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(166, _data, _reply, 0);
                    _reply.readException();
                    ComponentName _result = (ComponentName) _reply.readTypedObject(ComponentName.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String getInstantAppAndroidId(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(167, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public IArtManager getArtManager() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(168, _data, _reply, 0);
                    _reply.readException();
                    IArtManager _result = IArtManager.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void setHarmfulAppWarning(String packageName, CharSequence warning, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    if (warning != null) {
                        _data.writeInt(1);
                        TextUtils.writeToParcel(warning, _data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userId);
                    this.mRemote.transact(169, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public CharSequence getHarmfulAppWarning(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(170, _data, _reply, 0);
                    _reply.readException();
                    CharSequence _result = (CharSequence) _reply.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean hasSigningCertificate(String packageName, byte[] signingCertificate, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeByteArray(signingCertificate);
                    _data.writeInt(flags);
                    this.mRemote.transact(171, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean hasUidSigningCertificate(int uid, byte[] signingCertificate, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeByteArray(signingCertificate);
                    _data.writeInt(flags);
                    this.mRemote.transact(172, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String getDefaultTextClassifierPackageName() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(173, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String getSystemTextClassifierPackageName() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(174, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String getAttentionServicePackageName() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(175, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String getRotationResolverPackageName() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(176, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String getWellbeingPackageName() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(177, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String getAppPredictionServicePackageName() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(178, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String getSystemCaptionsServicePackageName() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(179, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String getSetupWizardPackageName() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(180, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String getIncidentReportApproverPackageName() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(181, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isPackageStateProtected(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(182, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void sendDeviceCustomizationReadyBroadcast() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(183, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public List<ModuleInfo> getInstalledModules(int flags) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(flags);
                    this.mRemote.transact(184, _data, _reply, 0);
                    _reply.readException();
                    List<ModuleInfo> _result = _reply.createTypedArrayList(ModuleInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ModuleInfo getModuleInfo(String packageName, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(flags);
                    this.mRemote.transact(185, _data, _reply, 0);
                    _reply.readException();
                    ModuleInfo _result = (ModuleInfo) _reply.readTypedObject(ModuleInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int getRuntimePermissionsVersion(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(186, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void setRuntimePermissionsVersion(int version, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(version);
                    _data.writeInt(userId);
                    this.mRemote.transact(187, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void notifyPackagesReplacedReceived(String[] packages) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringArray(packages);
                    this.mRemote.transact(188, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void requestPackageChecksums(String packageName, boolean includeSplits, int optional, int required, List trustedInstallers, IOnChecksumsReadyListener onChecksumsReadyListener, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeBoolean(includeSplits);
                    _data.writeInt(optional);
                    _data.writeInt(required);
                    _data.writeList(trustedInstallers);
                    _data.writeStrongInterface(onChecksumsReadyListener);
                    _data.writeInt(userId);
                    this.mRemote.transact(189, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public IntentSender getLaunchIntentSenderForPackage(String packageName, String callingPackage, String featureId, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(callingPackage);
                    _data.writeString(featureId);
                    _data.writeInt(userId);
                    this.mRemote.transact(190, _data, _reply, 0);
                    _reply.readException();
                    IntentSender _result = (IntentSender) _reply.readTypedObject(IntentSender.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String[] getAppOpPermissionPackages(String permissionName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(permissionName);
                    _data.writeInt(userId);
                    this.mRemote.transact(191, _data, _reply, 0);
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public PermissionGroupInfo getPermissionGroupInfo(String name, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeInt(flags);
                    this.mRemote.transact(192, _data, _reply, 0);
                    _reply.readException();
                    PermissionGroupInfo _result = (PermissionGroupInfo) _reply.readTypedObject(PermissionGroupInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean addPermission(PermissionInfo info) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    this.mRemote.transact(193, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean addPermissionAsync(PermissionInfo info) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    this.mRemote.transact(194, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void removePermission(String name) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    this.mRemote.transact(195, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int checkPermission(String permName, String pkgName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(permName);
                    _data.writeString(pkgName);
                    _data.writeInt(userId);
                    this.mRemote.transact(196, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void grantRuntimePermission(String packageName, String permissionName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(permissionName);
                    _data.writeInt(userId);
                    this.mRemote.transact(197, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int checkUidPermission(String permName, int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(permName);
                    _data.writeInt(uid);
                    this.mRemote.transact(198, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void setMimeGroup(String packageName, String group, List<String> mimeTypes) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(group);
                    _data.writeStringList(mimeTypes);
                    this.mRemote.transact(199, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public String getSplashScreenTheme(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(200, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void setSplashScreenTheme(String packageName, String themeName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(themeName);
                    _data.writeInt(userId);
                    this.mRemote.transact(201, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int getUserMinAspectRatio(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(202, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void setUserMinAspectRatio(String packageName, int userId, int aspectRatio) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    _data.writeInt(aspectRatio);
                    this.mRemote.transact(203, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public List<String> getMimeGroup(String packageName, String group) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(group);
                    this.mRemote.transact(204, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isAutoRevokeWhitelisted(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    this.mRemote.transact(205, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void makeProviderVisible(int recipientAppId, String visibleAuthority) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(recipientAppId);
                    _data.writeString(visibleAuthority);
                    this.mRemote.transact(206, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void makeUidVisible(int recipientAppId, int visibleUid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(recipientAppId);
                    _data.writeInt(visibleUid);
                    this.mRemote.transact(207, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public IBinder getHoldLockToken() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(208, _data, _reply, 0);
                    _reply.readException();
                    IBinder _result = _reply.readStrongBinder();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void holdLock(IBinder token, int durationMs) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeInt(durationMs);
                    this.mRemote.transact(209, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public PackageManager.Property getPropertyAsUser(String propertyName, String packageName, String className, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(propertyName);
                    _data.writeString(packageName);
                    _data.writeString(className);
                    _data.writeInt(userId);
                    this.mRemote.transact(210, _data, _reply, 0);
                    _reply.readException();
                    PackageManager.Property _result = (PackageManager.Property) _reply.readTypedObject(PackageManager.Property.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ParceledListSlice queryProperty(String propertyName, int componentType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(propertyName);
                    _data.writeInt(componentType);
                    this.mRemote.transact(211, _data, _reply, 0);
                    _reply.readException();
                    ParceledListSlice _result = (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void setKeepUninstalledPackages(List<String> packageList) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringList(packageList);
                    this.mRemote.transact(212, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int setLicensePermissionsForMDM(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    this.mRemote.transact(213, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public List<String> getPackageGrantedPermissionsForMDM(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    this.mRemote.transact(214, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public List<String> getGrantedPermissionsForMDM(String pkgName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkgName);
                    this.mRemote.transact(215, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void clearPackagePreferredActivitiesAsUserForMDM(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(216, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean applyRuntimePermissionsForMDM(String pkgName, List<String> permissions, int permState, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkgName);
                    _data.writeStringList(permissions);
                    _data.writeInt(permState);
                    _data.writeInt(userId);
                    this.mRemote.transact(217, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean applyRuntimePermissionsForAllApplicationsForMDM(int permState, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(permState);
                    _data.writeInt(userId);
                    this.mRemote.transact(218, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public List<String> getRequestedRuntimePermissionsForMDM(String pkgName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkgName);
                    this.mRemote.transact(219, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean[] canPackageQuery(String sourcePackageName, String[] targetPackageNames, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(sourcePackageName);
                    _data.writeStringArray(targetPackageNames);
                    _data.writeInt(userId);
                    this.mRemote.transact(220, _data, _reply, 0);
                    _reply.readException();
                    boolean[] _result = _reply.createBooleanArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean waitForHandler(long timeoutMillis, boolean forBackgroundHandler) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(timeoutMillis);
                    _data.writeBoolean(forBackgroundHandler);
                    this.mRemote.transact(221, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void registerPackageMonitorCallback(IRemoteCallback callback, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    _data.writeInt(userId);
                    this.mRemote.transact(222, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void unregisterPackageMonitorCallback(IRemoteCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(223, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ArchivedPackageParcel getArchivedPackage(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(224, _data, _reply, 0);
                    _reply.readException();
                    ArchivedPackageParcel _result = (ArchivedPackageParcel) _reply.readTypedObject(ArchivedPackageParcel.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public Bitmap getArchivedAppIcon(String packageName, UserHandle user, String callingPackageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeTypedObject(user, 0);
                    _data.writeString(callingPackageName);
                    this.mRemote.transact(225, _data, _reply, 0);
                    _reply.readException();
                    Bitmap _result = (Bitmap) _reply.readTypedObject(Bitmap.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isAppArchivable(String packageName, UserHandle user) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeTypedObject(user, 0);
                    this.mRemote.transact(226, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public int getAppMetadataSource(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(227, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ComponentName getDomainVerificationAgent(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(228, _data, _reply, 0);
                    _reply.readException();
                    ComponentName _result = (ComponentName) _reply.readTypedObject(ComponentName.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public List<String> getPackageListForDualDarPolicy(String packageType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageType);
                    this.mRemote.transact(229, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean createEncAppData(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(230, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean removeEncUserDir(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(231, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean removeEncPkgDir(int userId, String pkgName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeString(pkgName);
                    this.mRemote.transact(232, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean getMetadataForIconTray(String packageName, String metadata, int userId, List<String> feature) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(metadata);
                    _data.writeInt(userId);
                    this.mRemote.transact(233, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    _reply.readStringList(feature);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean semIsPermissionRevokedByUserFixed(String permName, String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(permName);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(234, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isUnknownSourcePackage(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    this.mRemote.transact(235, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public ParceledListSlice getUnknownSourcePackagesAsUser(long flags, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(flags);
                    _data.writeInt(userId);
                    this.mRemote.transact(236, _data, _reply, 0);
                    _reply.readException();
                    ParceledListSlice _result = (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isPackageAutoDisabled(String packageName, int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(uid);
                    this.mRemote.transact(237, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean isSystemCompressedPackage(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(238, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void changeMonetizationBadgeState(String value, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(value);
                    _data.writeString(packageName);
                    this.mRemote.transact(239, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public boolean shouldAppSupportBadgeIcon(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    this.mRemote.transact(240, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void setAppCategoryHintUser(String pkgName, int category) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkgName);
                    _data.writeInt(category);
                    this.mRemote.transact(241, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public void clearAppCategoryHintUser(String pkgName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkgName);
                    this.mRemote.transact(242, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IPackageManager
            public Map<String, String> getAppCategoryHintUserMap() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                final Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(243, _data, _reply, 0);
                    _reply.readException();
                    int N = _reply.readInt();
                    final Map<String, String> _result = N < 0 ? null : new HashMap<>();
                    IntStream.range(0, N).forEach(new IntConsumer() { // from class: android.content.pm.IPackageManager$Stub$Proxy$$ExternalSyntheticLambda1
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i) {
                            IPackageManager.Stub.Proxy.lambda$getAppCategoryHintUserMap$2(Parcel.this, _result, i);
                        }
                    });
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            static /* synthetic */ void lambda$getAppCategoryHintUserMap$2(Parcel _reply, Map _result, int i) {
                String k = _reply.readString();
                String v = _reply.readString();
                _result.put(k, v);
            }

            @Override // android.content.pm.IPackageManager
            public Map<String, String[]> getAppCategoryInfos(String pkgName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                final Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkgName);
                    this.mRemote.transact(244, _data, _reply, 0);
                    _reply.readException();
                    int N = _reply.readInt();
                    final Map<String, String[]> _result = N < 0 ? null : new HashMap<>();
                    IntStream.range(0, N).forEach(new IntConsumer() { // from class: android.content.pm.IPackageManager$Stub$Proxy$$ExternalSyntheticLambda2
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i) {
                            IPackageManager.Stub.Proxy.lambda$getAppCategoryInfos$3(Parcel.this, _result, i);
                        }
                    });
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            static /* synthetic */ void lambda$getAppCategoryInfos$3(Parcel _reply, Map _result, int i) {
                String k = _reply.readString();
                String[] v = _reply.createStringArray();
                _result.put(k, v);
            }
        }

        protected void getAppMetadataFd_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.GET_APP_METADATA, getCallingPid(), getCallingUid());
        }

        protected void removeCrossProfileIntentFilter_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.INTERACT_ACROSS_USERS_FULL, getCallingPid(), getCallingUid());
        }

        protected void clearCrossProfileIntentFilters_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.INTERACT_ACROSS_USERS_FULL, getCallingPid(), getCallingUid());
        }

        protected void freeStorageAndNotify_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CLEAR_APP_CACHE, getCallingPid(), getCallingUid());
        }

        protected void freeStorage_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CLEAR_APP_CACHE, getCallingPid(), getCallingUid());
        }

        protected void clearApplicationUserData_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CLEAR_APP_USER_DATA, getCallingPid(), getCallingUid());
        }

        protected void getMoveStatus_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS, getCallingPid(), getCallingUid());
        }

        protected void registerMoveCallback_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS, getCallingPid(), getCallingUid());
        }

        protected void unregisterMoveCallback_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS, getCallingPid(), getCallingUid());
        }

        protected void movePackage_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MOVE_PACKAGE, getCallingPid(), getCallingUid());
        }

        protected void movePackageToSd_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MOVE_PACKAGE, getCallingPid(), getCallingUid());
        }

        protected void movePrimaryStorage_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MOVE_PACKAGE, getCallingPid(), getCallingUid());
        }

        protected void setInstallLocation_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.WRITE_SECURE_SETTINGS, getCallingPid(), getCallingUid());
        }

        protected void getVerifierDeviceIdentity_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.PACKAGE_VERIFICATION_AGENT, getCallingPid(), getCallingUid());
        }

        protected void setApplicationHiddenSettingAsUser_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_USERS, getCallingPid(), getCallingUid());
        }

        protected void setBlockUninstallForUser_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.DELETE_PACKAGES, getCallingPid(), getCallingUid());
        }

        protected void setUpdateAvailable_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.INSTALL_PACKAGES, getCallingPid(), getCallingUid());
        }

        protected void getInstantAppAndroidId_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ACCESS_INSTANT_APPS, getCallingPid(), getCallingUid());
        }

        protected void setUserMinAspectRatio_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.INSTALL_PACKAGES, getCallingPid(), getCallingUid());
        }

        protected void makeUidVisible_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MAKE_UID_VISIBLE, getCallingPid(), getCallingUid());
        }

        protected void getAppMetadataSource_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.GET_APP_METADATA, getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 243;
        }
    }
}
