package com.android.server.pm;

import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.BroadcastOptions;
import android.app.admin.DevicePolicyManagerInternal;
import android.content.pm.PackageInfo;
import android.content.pm.SuspendDialogInfo;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.os.UserHandle;
import android.provider.DeviceConfig;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.IntArray;
import android.util.Slog;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.CollectionUtils;
import com.android.server.LocalServices;
import com.android.server.pm.parsing.pkg.AndroidPackageInternal;
import com.android.server.pm.pkg.PackageStateInternal;
import com.android.server.pm.pkg.PackageUserStateInternal;
import com.android.server.pm.pkg.SuspendParams;
import com.android.server.pm.pkg.mutate.PackageStateMutator;
import com.android.server.pm.pkg.mutate.PackageUserStateWrite;
import com.android.server.utils.WatchedArrayMap;
import com.samsung.android.server.pm.PmLog;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public final class SuspendPackageHelper {
    public final BroadcastHelper mBroadcastHelper;
    public final PackageManagerServiceInjector mInjector;
    public final PackageManagerService mPm;
    public final ProtectedPackages mProtectedPackages;
    public final UserManagerService mUserManager;

    public SuspendPackageHelper(PackageManagerService packageManagerService, PackageManagerServiceInjector packageManagerServiceInjector, UserManagerService userManagerService, BroadcastHelper broadcastHelper, ProtectedPackages protectedPackages) {
        this.mPm = packageManagerService;
        this.mUserManager = userManagerService;
        this.mInjector = packageManagerServiceInjector;
        this.mBroadcastHelper = broadcastHelper;
        this.mProtectedPackages = protectedPackages;
    }

    public String[] setPackagesSuspended(Computer computer, String[] strArr, final boolean z, PersistableBundle persistableBundle, PersistableBundle persistableBundle2, SuspendDialogInfo suspendDialogInfo, final String str, final int i, int i2, boolean z2) {
        int i3;
        IntArray intArray;
        SuspendParams suspendParams;
        boolean[] zArr;
        IntArray intArray2;
        Computer computer2 = computer;
        String[] strArr2 = strArr;
        int i4 = i2;
        if (ArrayUtils.isEmpty(strArr)) {
            return strArr2;
        }
        if (z && !z2 && !isSuspendAllowedForUser(computer2, i, i4)) {
            Slog.w("PackageManager", "Cannot suspend due to restrictions on user " + i);
            return strArr2;
        }
        final SuspendParams suspendParams2 = new SuspendParams(suspendDialogInfo, persistableBundle, persistableBundle2);
        ArrayList arrayList = new ArrayList(strArr2.length);
        ArrayList arrayList2 = new ArrayList(strArr2.length);
        IntArray intArray3 = new IntArray(strArr2.length);
        final ArraySet arraySet = new ArraySet(strArr2.length);
        IntArray intArray4 = new IntArray(strArr2.length);
        boolean[] canSuspendPackageForUser = (!z || z2) ? null : canSuspendPackageForUser(computer2, strArr2, i, i4);
        int i5 = 0;
        while (i5 < strArr2.length) {
            String str2 = strArr2[i5];
            if (str.equals(str2)) {
                StringBuilder sb = new StringBuilder();
                intArray = intArray4;
                sb.append("Calling package: ");
                sb.append(str);
                sb.append(" trying to ");
                sb.append(z ? "" : "un");
                sb.append("suspend itself. Ignoring");
                Slog.w("PackageManager", sb.toString());
                arrayList.add(str2);
            } else {
                intArray = intArray4;
                PackageStateInternal packageStateInternal = computer2.getPackageStateInternal(str2);
                if (packageStateInternal == null || !packageStateInternal.getUserStateOrDefault(i).isInstalled() || computer2.shouldFilterApplication(packageStateInternal, i4, i)) {
                    suspendParams = suspendParams2;
                    zArr = canSuspendPackageForUser;
                    intArray2 = intArray;
                    Slog.w("PackageManager", "Could not find package setting for package: " + str2 + ". Skipping suspending/un-suspending.");
                    arrayList.add(str2);
                } else if (canSuspendPackageForUser != null && !canSuspendPackageForUser[i5]) {
                    arrayList.add(str2);
                } else {
                    WatchedArrayMap suspendParams3 = packageStateInternal.getUserStateOrDefault(i).getSuspendParams();
                    zArr = canSuspendPackageForUser;
                    boolean z3 = !Objects.equals(suspendParams3 == null ? null : (SuspendParams) suspendParams3.get(str2), suspendParams2);
                    if (z && !z3) {
                        arrayList2.add(str2);
                        intArray3.add(UserHandle.getUid(i, packageStateInternal.getAppId()));
                        intArray2 = intArray;
                        suspendParams = suspendParams2;
                    } else {
                        suspendParams = suspendParams2;
                        boolean z4 = !z && CollectionUtils.size(suspendParams3) == 1 && suspendParams3.containsKey(str);
                        if (z || z4) {
                            arrayList2.add(str2);
                            intArray3.add(UserHandle.getUid(i, packageStateInternal.getAppId()));
                        }
                        if (z3) {
                            arraySet.add(str2);
                            intArray2 = intArray;
                            intArray2.add(UserHandle.getUid(i, packageStateInternal.getAppId()));
                        } else {
                            intArray2 = intArray;
                            Slog.w("PackageManager", "No change is needed for package: " + str2 + ". Skipping suspending/un-suspending.");
                        }
                    }
                }
                i5++;
                computer2 = computer;
                strArr2 = strArr;
                i4 = i2;
                intArray4 = intArray2;
                suspendParams2 = suspendParams;
                canSuspendPackageForUser = zArr;
            }
            suspendParams = suspendParams2;
            zArr = canSuspendPackageForUser;
            intArray2 = intArray;
            i5++;
            computer2 = computer;
            strArr2 = strArr;
            i4 = i2;
            intArray4 = intArray2;
            suspendParams2 = suspendParams;
            canSuspendPackageForUser = zArr;
        }
        IntArray intArray5 = intArray4;
        this.mPm.commitPackageStateMutation(null, new Consumer() { // from class: com.android.server.pm.SuspendPackageHelper$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                SuspendPackageHelper.lambda$setPackagesSuspended$0(arraySet, i, z, str, suspendParams2, (PackageStateMutator) obj);
            }
        });
        if (!arrayList2.isEmpty()) {
            String[] strArr3 = (String[]) arrayList2.toArray(new String[0]);
            sendPackagesSuspendedForUser(z ? "android.intent.action.PACKAGES_SUSPENDED" : "android.intent.action.PACKAGES_UNSUSPENDED", strArr3, intArray3.toArray(), i);
            sendMyPackageSuspendedOrUnsuspended(strArr3, z, i);
            this.mPm.scheduleWritePackageRestrictions(i);
        }
        if (arraySet.isEmpty()) {
            i3 = 0;
        } else {
            i3 = 0;
            sendPackagesSuspendedForUser("android.intent.action.PACKAGES_SUSPENSION_CHANGED", (String[]) arraySet.toArray(new String[0]), intArray5.toArray(), i);
        }
        return (String[]) arrayList.toArray(new String[i3]);
    }

    public static /* synthetic */ void lambda$setPackagesSuspended$0(ArraySet arraySet, int i, boolean z, String str, SuspendParams suspendParams, PackageStateMutator packageStateMutator) {
        int size = arraySet.size();
        for (int i2 = 0; i2 < size; i2++) {
            PackageUserStateWrite userState = packageStateMutator.forPackage((String) arraySet.valueAt(i2)).userState(i);
            if (z) {
                userState.putSuspendParams(str, suspendParams);
            } else {
                userState.removeSuspension(str);
            }
        }
    }

    public String[] getUnsuspendablePackagesForUser(Computer computer, String[] strArr, int i, int i2) {
        if (!isSuspendAllowedForUser(computer, i, i2)) {
            Slog.w("PackageManager", "Cannot suspend due to restrictions on user " + i);
            return strArr;
        }
        ArraySet arraySet = new ArraySet();
        boolean[] canSuspendPackageForUser = canSuspendPackageForUser(computer, strArr, i, i2);
        for (int i3 = 0; i3 < strArr.length; i3++) {
            if (!canSuspendPackageForUser[i3]) {
                arraySet.add(strArr[i3]);
            } else if (computer.getPackageStateForInstalledAndFiltered(strArr[i3], i2, i) == null) {
                Slog.w("PackageManager", "Could not find package setting for package: " + strArr[i3]);
                arraySet.add(strArr[i3]);
            }
        }
        return (String[]) arraySet.toArray(new String[arraySet.size()]);
    }

    public Bundle getSuspendedPackageAppExtras(Computer computer, String str, int i, int i2) {
        PackageStateInternal packageStateInternal = computer.getPackageStateInternal(str, i2);
        if (packageStateInternal == null) {
            return null;
        }
        PackageUserStateInternal userStateOrDefault = packageStateInternal.getUserStateOrDefault(i);
        Bundle bundle = new Bundle();
        if (userStateOrDefault.isSuspended()) {
            for (int i3 = 0; i3 < userStateOrDefault.getSuspendParams().size(); i3++) {
                SuspendParams suspendParams = (SuspendParams) userStateOrDefault.getSuspendParams().valueAt(i3);
                if (suspendParams != null && suspendParams.getAppExtras() != null) {
                    bundle.putAll(suspendParams.getAppExtras());
                }
            }
        }
        if (bundle.size() > 0) {
            return bundle;
        }
        return null;
    }

    public void removeSuspensionsBySuspendingPackage(Computer computer, String[] strArr, Predicate predicate, final int i) {
        ArraySet arraySet;
        String[] strArr2 = strArr;
        ArrayList arrayList = new ArrayList();
        IntArray intArray = new IntArray();
        final ArrayMap arrayMap = new ArrayMap();
        int length = strArr2.length;
        int i2 = 0;
        while (true) {
            if (i2 >= length) {
                break;
            }
            String str = strArr2[i2];
            PackageStateInternal packageStateInternal = computer.getPackageStateInternal(str);
            PackageUserStateInternal userStateOrDefault = packageStateInternal != null ? packageStateInternal.getUserStateOrDefault(i) : null;
            if (userStateOrDefault != null && userStateOrDefault.isSuspended()) {
                WatchedArrayMap suspendParams = userStateOrDefault.getSuspendParams();
                int i3 = 0;
                for (int i4 = 0; i4 < suspendParams.size(); i4++) {
                    String str2 = (String) suspendParams.keyAt(i4);
                    if (predicate.test(str2)) {
                        ArraySet arraySet2 = (ArraySet) arrayMap.get(str);
                        if (arraySet2 == null) {
                            arraySet = new ArraySet();
                            arrayMap.put(str, arraySet);
                        } else {
                            arraySet = arraySet2;
                        }
                        arraySet.add(str2);
                        i3++;
                    }
                }
                if (i3 == suspendParams.size()) {
                    arrayList.add(packageStateInternal.getPackageName());
                    intArray.add(UserHandle.getUid(i, packageStateInternal.getAppId()));
                }
            }
            i2++;
            strArr2 = strArr;
        }
        this.mPm.commitPackageStateMutation(null, new Consumer() { // from class: com.android.server.pm.SuspendPackageHelper$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                SuspendPackageHelper.lambda$removeSuspensionsBySuspendingPackage$1(arrayMap, i, (PackageStateMutator) obj);
            }
        });
        this.mPm.scheduleWritePackageRestrictions(i);
        if (arrayList.isEmpty()) {
            return;
        }
        String[] strArr3 = (String[]) arrayList.toArray(new String[arrayList.size()]);
        sendMyPackageSuspendedOrUnsuspended(strArr3, false, i);
        sendPackagesSuspendedForUser("android.intent.action.PACKAGES_UNSUSPENDED", strArr3, intArray.toArray(), i);
    }

    public static /* synthetic */ void lambda$removeSuspensionsBySuspendingPackage$1(ArrayMap arrayMap, int i, PackageStateMutator packageStateMutator) {
        for (int i2 = 0; i2 < arrayMap.size(); i2++) {
            String str = (String) arrayMap.keyAt(i2);
            ArraySet arraySet = (ArraySet) arrayMap.valueAt(i2);
            PackageUserStateWrite userState = packageStateMutator.forPackage(str).userState(i);
            for (int i3 = 0; i3 < arraySet.size(); i3++) {
                userState.removeSuspension((String) arraySet.valueAt(i3));
            }
        }
    }

    public Bundle getSuspendedPackageLauncherExtras(Computer computer, String str, int i, int i2) {
        PackageStateInternal packageStateInternal = computer.getPackageStateInternal(str, i2);
        if (packageStateInternal == null) {
            return null;
        }
        Bundle bundle = new Bundle();
        PackageUserStateInternal userStateOrDefault = packageStateInternal.getUserStateOrDefault(i);
        if (userStateOrDefault.isSuspended()) {
            for (int i3 = 0; i3 < userStateOrDefault.getSuspendParams().size(); i3++) {
                SuspendParams suspendParams = (SuspendParams) userStateOrDefault.getSuspendParams().valueAt(i3);
                if (suspendParams != null && suspendParams.getLauncherExtras() != null) {
                    bundle.putAll(suspendParams.getLauncherExtras());
                }
            }
        }
        if (bundle.size() > 0) {
            return bundle;
        }
        return null;
    }

    public boolean isPackageSuspended(Computer computer, String str, int i, int i2) {
        PackageStateInternal packageStateInternal = computer.getPackageStateInternal(str, i2);
        return packageStateInternal != null && packageStateInternal.getUserStateOrDefault(i).isSuspended();
    }

    public String getSuspendingPackage(Computer computer, String str, int i, int i2) {
        PackageStateInternal packageStateInternal = computer.getPackageStateInternal(str, i2);
        String str2 = null;
        if (packageStateInternal == null) {
            return null;
        }
        PackageUserStateInternal userStateOrDefault = packageStateInternal.getUserStateOrDefault(i);
        if (!userStateOrDefault.isSuspended()) {
            return null;
        }
        for (int i3 = 0; i3 < userStateOrDefault.getSuspendParams().size(); i3++) {
            str2 = (String) userStateOrDefault.getSuspendParams().keyAt(i3);
            if ("android".equals(str2)) {
                return str2;
            }
        }
        return str2;
    }

    public SuspendDialogInfo getSuspendedDialogInfo(Computer computer, String str, String str2, int i, int i2) {
        WatchedArrayMap suspendParams;
        SuspendParams suspendParams2;
        PackageStateInternal packageStateInternal = computer.getPackageStateInternal(str, i2);
        if (packageStateInternal == null) {
            return null;
        }
        PackageUserStateInternal userStateOrDefault = packageStateInternal.getUserStateOrDefault(i);
        if (!userStateOrDefault.isSuspended() || (suspendParams = userStateOrDefault.getSuspendParams()) == null || (suspendParams2 = (SuspendParams) suspendParams.get(str2)) == null) {
            return null;
        }
        return suspendParams2.getDialogInfo();
    }

    public boolean isSuspendAllowedForUser(Computer computer, int i, int i2) {
        UserManagerService userManagerService = this.mInjector.getUserManagerService();
        return isCallerDeviceOrProfileOwner(computer, i, i2) || !(userManagerService.hasUserRestriction("no_control_apps", i) || userManagerService.hasUserRestriction("no_uninstall_apps", i));
    }

    public boolean[] canSuspendPackageForUser(Computer computer, String[] strArr, int i, int i2) {
        long j;
        String defaultHome;
        String defaultDialer;
        String knownPackageName;
        String knownPackageName2;
        String knownPackageName3;
        String knownPackageName4;
        AppOpsManager appOpsManager;
        SuspendPackageHelper suspendPackageHelper = this;
        String[] strArr2 = strArr;
        boolean[] zArr = new boolean[strArr2.length];
        boolean isCallerDeviceOrProfileOwner = suspendPackageHelper.isCallerDeviceOrProfileOwner(computer, i, i2);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            DefaultAppProvider defaultAppProvider = suspendPackageHelper.mInjector.getDefaultAppProvider();
            defaultHome = defaultAppProvider.getDefaultHome(i);
            defaultDialer = defaultAppProvider.getDefaultDialer(i);
            knownPackageName = suspendPackageHelper.getKnownPackageName(computer, 2, i);
            knownPackageName2 = suspendPackageHelper.getKnownPackageName(computer, 3, i);
            knownPackageName3 = suspendPackageHelper.getKnownPackageName(computer, 4, i);
            knownPackageName4 = suspendPackageHelper.getKnownPackageName(computer, 7, i);
            appOpsManager = (AppOpsManager) suspendPackageHelper.mInjector.getSystemService(AppOpsManager.class);
            j = clearCallingIdentity;
        } catch (Throwable th) {
            th = th;
            j = clearCallingIdentity;
        }
        try {
            boolean z = DeviceConfig.getBoolean("package_manager_service", "system_exempt_from_suspension", true);
            int i3 = 0;
            while (i3 < strArr2.length) {
                zArr[i3] = false;
                String str = strArr2[i3];
                boolean[] zArr2 = zArr;
                int i4 = i3;
                if (suspendPackageHelper.mPm.isPackageDeviceAdmin(str, i)) {
                    Slog.w("PackageManager", "Cannot suspend package \"" + str + "\": has an active device admin");
                } else if (str.equals(defaultHome)) {
                    Slog.w("PackageManager", "Cannot suspend package \"" + str + "\": contains the active launcher");
                } else if (str.equals(knownPackageName)) {
                    Slog.w("PackageManager", "Cannot suspend package \"" + str + "\": required for package installation");
                } else if (str.equals(knownPackageName2)) {
                    Slog.w("PackageManager", "Cannot suspend package \"" + str + "\": required for package uninstallation");
                } else if (str.equals(knownPackageName3)) {
                    Slog.w("PackageManager", "Cannot suspend package \"" + str + "\": required for package verification");
                } else if (str.equals(defaultDialer)) {
                    Slog.w("PackageManager", "Cannot suspend package \"" + str + "\": is the default dialer");
                } else if (str.equals(knownPackageName4)) {
                    Slog.w("PackageManager", "Cannot suspend package \"" + str + "\": required for permissions management");
                } else if (suspendPackageHelper.mProtectedPackages.isPackageStateProtected(i, str)) {
                    Slog.w("PackageManager", "Cannot suspend package \"" + str + "\": protected package");
                } else if (!isCallerDeviceOrProfileOwner && computer.getBlockUninstall(i, str)) {
                    Slog.w("PackageManager", "Cannot suspend package \"" + str + "\": blocked by admin");
                } else if (suspendPackageHelper.mPm.isRequiredSystemPackage(computer, str, i)) {
                    PmLog.logDebugInfoAndLogcat("Cannot suspend package \"" + str + "\": required system package");
                } else {
                    PackageStateInternal packageStateInternal = computer.getPackageStateInternal(str);
                    AndroidPackageInternal pkg = packageStateInternal == null ? null : packageStateInternal.getPkg();
                    if (pkg != null) {
                        int uid = UserHandle.getUid(i, packageStateInternal.getAppId());
                        if (pkg.isSdkLibrary()) {
                            Slog.w("PackageManager", "Cannot suspend package: " + str + " providing SDK library: " + pkg.getSdkLibraryName());
                        } else if (pkg.isStaticSharedLibrary()) {
                            Slog.w("PackageManager", "Cannot suspend package: " + str + " providing static shared library: " + pkg.getStaticSharedLibraryName());
                        } else if (z && appOpsManager.checkOpNoThrow(124, uid, str) == 0) {
                            Slog.w("PackageManager", "Cannot suspend package \"" + str + "\": has OP_SYSTEM_EXEMPT_FROM_SUSPENSION set");
                        }
                    }
                    if ("android".equals(str)) {
                        Slog.w("PackageManager", "Cannot suspend the platform package: " + str);
                    } else {
                        zArr2[i4] = true;
                        i3 = i4 + 1;
                        suspendPackageHelper = this;
                        strArr2 = strArr;
                        zArr = zArr2;
                    }
                }
                i3 = i4 + 1;
                suspendPackageHelper = this;
                strArr2 = strArr;
                zArr = zArr2;
            }
            boolean[] zArr3 = zArr;
            Binder.restoreCallingIdentity(j);
            return zArr3;
        } catch (Throwable th2) {
            th = th2;
            Binder.restoreCallingIdentity(j);
            throw th;
        }
    }

    public void sendPackagesSuspendedForUser(final String str, String[] strArr, int[] iArr, final int i) {
        Handler handler = this.mInjector.getHandler();
        final Bundle bundle = new Bundle(3);
        bundle.putStringArray("android.intent.extra.changed_package_list", strArr);
        bundle.putIntArray("android.intent.extra.changed_uid_list", iArr);
        final Bundle bundle2 = new BroadcastOptions().setDeferralPolicy(2).toBundle();
        handler.post(new Runnable() { // from class: com.android.server.pm.SuspendPackageHelper$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                SuspendPackageHelper.this.lambda$sendPackagesSuspendedForUser$3(str, bundle, i, bundle2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendPackagesSuspendedForUser$3(String str, Bundle bundle, int i, Bundle bundle2) {
        this.mBroadcastHelper.sendPackageBroadcast(str, null, bundle, 1342177280, null, null, new int[]{i}, null, null, new BiFunction() { // from class: com.android.server.pm.SuspendPackageHelper$$ExternalSyntheticLambda3
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                Bundle lambda$sendPackagesSuspendedForUser$2;
                lambda$sendPackagesSuspendedForUser$2 = SuspendPackageHelper.this.lambda$sendPackagesSuspendedForUser$2((Integer) obj, (Bundle) obj2);
                return lambda$sendPackagesSuspendedForUser$2;
            }
        }, bundle2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Bundle lambda$sendPackagesSuspendedForUser$2(Integer num, Bundle bundle) {
        return BroadcastHelper.filterExtrasChangedPackageList(this.mPm.snapshotComputer(), num.intValue(), bundle);
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0052  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String[] setPackagesSuspendedByAdmin(com.android.server.pm.Computer r14, int r15, java.lang.String[] r16, boolean r17) {
        /*
            r13 = this;
            r0 = r13
            r8 = r15
            android.util.ArraySet r1 = new android.util.ArraySet
            r2 = r16
            r1.<init>(r2)
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            com.android.server.pm.UserManagerService r3 = r0.mUserManager
            boolean r3 = r3.isQuietModeEnabled(r15)
            if (r3 == 0) goto L4a
            java.util.Set r3 = r13.packagesToSuspendInQuietMode(r14, r15)
            r3.retainAll(r1)
            boolean r4 = r3.isEmpty()
            if (r4 != 0) goto L42
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "Ignoring quiet packages: "
            r4.append(r5)
            java.lang.String r5 = ", "
            java.lang.String r5 = java.lang.String.join(r5, r3)
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            java.lang.String r5 = "PackageManager"
            android.util.Slog.i(r5, r4)
            r1.removeAll(r3)
        L42:
            if (r17 == 0) goto L4a
            r4 = r14
            java.util.List r2 = r13.getUnsuspendablePackages(r14, r15, r3)
            goto L4b
        L4a:
            r4 = r14
        L4b:
            r11 = r2
            boolean r2 = r1.isEmpty()
            if (r2 != 0) goto L7a
            r2 = 0
            java.lang.String[] r2 = new java.lang.String[r2]
            java.lang.Object[] r1 = r1.toArray(r2)
            r2 = r1
            java.lang.String[] r2 = (java.lang.String[]) r2
            r5 = 0
            r6 = 0
            r7 = 0
            java.lang.String r9 = "android"
            r10 = 1000(0x3e8, float:1.401E-42)
            r12 = 0
            r0 = r13
            r1 = r14
            r3 = r17
            r4 = r5
            r5 = r6
            r6 = r7
            r7 = r9
            r8 = r15
            r9 = r10
            r10 = r12
            java.lang.String[] r0 = r0.setPackagesSuspended(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10)
            java.util.List r0 = java.util.Arrays.asList(r0)
            r11.addAll(r0)
        L7a:
            com.android.server.pm.SuspendPackageHelper$$ExternalSyntheticLambda5 r0 = new com.android.server.pm.SuspendPackageHelper$$ExternalSyntheticLambda5
            r0.<init>()
            java.lang.Object[] r0 = r11.toArray(r0)
            java.lang.String[] r0 = (java.lang.String[]) r0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.SuspendPackageHelper.setPackagesSuspendedByAdmin(com.android.server.pm.Computer, int, java.lang.String[], boolean):java.lang.String[]");
    }

    public static /* synthetic */ String[] lambda$setPackagesSuspendedByAdmin$4(int i) {
        return new String[i];
    }

    public static /* synthetic */ String[] lambda$getUnsuspendablePackages$5(int i) {
        return new String[i];
    }

    public final List getUnsuspendablePackages(Computer computer, int i, Set set) {
        String[] strArr = (String[]) set.toArray(new IntFunction() { // from class: com.android.server.pm.SuspendPackageHelper$$ExternalSyntheticLambda6
            @Override // java.util.function.IntFunction
            public final Object apply(int i2) {
                String[] lambda$getUnsuspendablePackages$5;
                lambda$getUnsuspendablePackages$5 = SuspendPackageHelper.lambda$getUnsuspendablePackages$5(i2);
                return lambda$getUnsuspendablePackages$5;
            }
        });
        boolean[] canSuspendPackageForUser = canSuspendPackageForUser(computer, strArr, i, 1000);
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < canSuspendPackageForUser.length; i2++) {
            if (!canSuspendPackageForUser[i2]) {
                arrayList.add(strArr[i2]);
            }
        }
        return arrayList;
    }

    public void setPackagesSuspendedForQuietMode(Computer computer, int i, boolean z) {
        Set packagesToSuspendInQuietMode = packagesToSuspendInQuietMode(computer, i);
        if (!z) {
            DevicePolicyManagerInternal devicePolicyManagerInternal = (DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class);
            if (devicePolicyManagerInternal != null) {
                packagesToSuspendInQuietMode.removeAll(devicePolicyManagerInternal.getPackagesSuspendedByAdmin(i));
            } else {
                Slog.wtf("PackageManager", "DevicePolicyManager unavailable while suspending apps for quiet mode");
            }
        }
        if (packagesToSuspendInQuietMode.isEmpty()) {
            return;
        }
        setPackagesSuspended(computer, (String[]) packagesToSuspendInQuietMode.toArray(new String[0]), z, null, null, null, "android", i, 1000, true);
    }

    public final Set packagesToSuspendInQuietMode(Computer computer, int i) {
        List list = computer.getInstalledPackages(786432L, i).getList();
        ArraySet arraySet = new ArraySet();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arraySet.add(((PackageInfo) it.next()).packageName);
        }
        arraySet.remove(this.mPm.getDevicePolicyManagementRoleHolderPackageName(i));
        return arraySet;
    }

    public final String getKnownPackageName(Computer computer, int i, int i2) {
        String[] knownPackageNamesInternal = this.mPm.getKnownPackageNamesInternal(computer, i, i2);
        if (knownPackageNamesInternal.length > 0) {
            return knownPackageNamesInternal[0];
        }
        return null;
    }

    public final boolean isCallerDeviceOrProfileOwner(Computer computer, int i, int i2) {
        if (i2 == 1000) {
            return true;
        }
        String deviceOwnerOrProfileOwnerPackage = this.mProtectedPackages.getDeviceOwnerOrProfileOwnerPackage(i);
        return deviceOwnerOrProfileOwnerPackage != null && i2 == computer.getPackageUidInternal(deviceOwnerOrProfileOwnerPackage, 0L, i, i2);
    }

    public final void sendMyPackageSuspendedOrUnsuspended(final String[] strArr, final boolean z, final int i) {
        Handler handler = this.mInjector.getHandler();
        final String str = z ? "android.intent.action.MY_PACKAGE_SUSPENDED" : "android.intent.action.MY_PACKAGE_UNSUSPENDED";
        handler.post(new Runnable() { // from class: com.android.server.pm.SuspendPackageHelper$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                SuspendPackageHelper.this.lambda$sendMyPackageSuspendedOrUnsuspended$6(z, i, strArr, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendMyPackageSuspendedOrUnsuspended$6(boolean z, int i, String[] strArr, String str) {
        if (ActivityManager.getService() == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("IActivityManager null. Cannot send MY_PACKAGE_ ");
            sb.append(z ? "" : "UN");
            sb.append("SUSPENDED broadcasts");
            Slog.wtf("PackageManager", sb.toString());
            return;
        }
        int[] iArr = {i};
        Computer snapshotComputer = this.mPm.snapshotComputer();
        int i2 = 0;
        for (int length = strArr.length; i2 < length; length = length) {
            String str2 = strArr[i2];
            Bundle bundle = null;
            Bundle suspendedPackageAppExtras = z ? getSuspendedPackageAppExtras(snapshotComputer, str2, i, 1000) : null;
            if (suspendedPackageAppExtras != null) {
                bundle = new Bundle(1);
                bundle.putBundle("android.intent.extra.SUSPENDED_PACKAGE_EXTRAS", suspendedPackageAppExtras);
            }
            this.mBroadcastHelper.doSendBroadcast(str, null, bundle, 16777216, str2, null, iArr, false, null, null, null);
            i2++;
        }
    }
}
