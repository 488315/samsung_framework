package com.android.server.pm;

import android.app.AppOpsManager;
import android.app.BroadcastOptions;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.admin.DevicePolicyEventLogger;
import android.app.admin.DevicePolicyManager;
import android.app.admin.DevicePolicyManagerInternal;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.ApplicationInfo;
import android.content.pm.Checksum;
import android.content.pm.DataLoaderManager;
import android.content.pm.DataLoaderParams;
import android.content.pm.DataLoaderParamsParcel;
import android.content.pm.FileSystemControlParcel;
import android.content.pm.IDataLoader;
import android.content.pm.IDataLoaderStatusListener;
import android.content.pm.IOnChecksumsReadyListener;
import android.content.pm.IPackageInstallObserver2;
import android.content.pm.IPackageInstallerSession;
import android.content.pm.IPackageInstallerSessionFileSystemConnector;
import android.content.pm.IPackageLoadingProgressCallback;
import android.content.pm.InstallSourceInfo;
import android.content.pm.InstallationFile;
import android.content.pm.InstallationFileParcel;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.content.pm.PackageManagerInternal;
import android.content.pm.PackageParser;
import android.content.pm.Signature;
import android.content.pm.SigningDetails;
import android.content.pm.dex.DexMetadataHelper;
import android.content.pm.parsing.ApkLite;
import android.content.pm.parsing.ApkLiteParseUtils;
import android.content.pm.parsing.PackageLite;
import android.content.pm.parsing.result.ParseResult;
import android.content.pm.parsing.result.ParseTypeImpl;
import android.content.res.ApkAssets;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.icu.util.ULocale;
import android.os.Binder;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileBridge;
import android.os.FileUtils;
import android.os.Handler;
import android.os.IInstalld;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.ParcelableException;
import android.os.RemoteException;
import android.os.RevocableFileDescriptor;
import android.os.SELinux;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.incremental.IStorageHealthListener;
import android.os.incremental.IncrementalFileStorages;
import android.os.incremental.IncrementalManager;
import android.os.incremental.PerUidReadTimeouts;
import android.os.incremental.StorageHealthCheckParams;
import android.os.storage.StorageManager;
import android.provider.DeviceConfig;
import android.system.ErrnoException;
import android.system.Int64Ref;
import android.system.Os;
import android.system.OsConstants;
import android.system.StructStat;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.ExceptionUtils;
import android.util.IntArray;
import android.util.Log;
import android.util.MathUtils;
import android.util.Slog;
import android.util.SparseArray;
import android.util.apk.ApkSignatureVerifier;
import com.android.internal.compat.IPlatformCompat;
import com.android.internal.content.InstallLocationUtils;
import com.android.internal.content.NativeLibraryHelper;
import com.android.internal.os.SomeArgs;
import com.android.internal.security.VerityUtils;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.IndentingPrintWriter;
import com.android.internal.util.Preconditions;
import com.android.internal.util.jobs.XmlUtils;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.LocalServices;
import com.android.server.SystemServerInitThreadPool$$ExternalSyntheticLambda1;
import com.android.server.display.DisplayPowerController2;
import com.android.server.om.SemSamsungThemeUtils;
import com.android.server.pm.Installer;
import com.android.server.pm.PackageInstallerService;
import com.android.server.pm.PackageInstallerSession;
import com.android.server.pm.PackageSessionVerifier;
import com.android.server.pm.StagingManager;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.PackageStateInternal;
import com.samsung.android.core.pm.containerservice.PackageHelperExt;
import com.samsung.android.rune.PMRune;
import com.samsung.android.server.pm.install.UnknownSourceAppManager;
import com.samsung.android.server.pm.mm.MaintenanceModeManager;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import libcore.io.IoUtils;
import libcore.util.EmptyArray;

/* loaded from: classes3.dex */
public class PackageInstallerSession extends IPackageInstallerSession.Stub {
    public static final int[] EMPTY_CHILD_SESSION_ARRAY = EmptyArray.INT;
    public static final InstallationFile[] EMPTY_INSTALLATION_FILE_ARRAY = new InstallationFile[0];
    public static final FileFilter sAddedApkFilter = new FileFilter() { // from class: com.android.server.pm.PackageInstallerSession.1
        @Override // java.io.FileFilter
        public boolean accept(File file) {
            return (file.isDirectory() || file.getName().endsWith(".removed") || PackageInstallerSession.isAppMetadata(file) || DexMetadataHelper.isDexMetadataFile(file) || VerityUtils.isFsveritySignatureFile(file) || ApkChecksums.isDigestOrDigestSignatureFile(file)) ? false : true;
        }
    };
    public static final FileFilter sAddedFilter = new FileFilter() { // from class: com.android.server.pm.PackageInstallerSession.2
        @Override // java.io.FileFilter
        public boolean accept(File file) {
            return (file.isDirectory() || file.getName().endsWith(".removed")) ? false : true;
        }
    };
    public static final FileFilter sRemovedFilter = new FileFilter() { // from class: com.android.server.pm.PackageInstallerSession.3
        @Override // java.io.FileFilter
        public boolean accept(File file) {
            return !file.isDirectory() && file.getName().endsWith(".removed");
        }
    };
    public long committedMillis;
    public final long createdMillis;
    public final int defaultContainerGid;
    public final PackageInstallerService.InternalCallback mCallback;
    public final Context mContext;
    public volatile boolean mDestroyed;
    public String mFinalMessage;
    public int mFinalStatus;
    public final Handler mHandler;
    public final Handler.Callback mHandlerCallback;
    public boolean mHasDeviceAdminReceiver;
    public IncrementalFileStorages mIncrementalFileStorages;
    public File mInheritedFilesBase;
    public InstallSource mInstallSource;
    public final Installer mInstaller;
    public volatile int mInstallerUid;
    public final String mOriginalInstallerPackageName;
    public final int mOriginalInstallerUid;
    public PackageLite mPackageLite;
    public String mPackageName;
    public int mParentSessionId;
    public Runnable mPendingAbandonCallback;
    public final PackageManagerService mPm;
    public PackageInstaller.PreapprovalDetails mPreapprovalDetails;
    public boolean mPrepared;
    public IntentSender mRemoteStatusReceiver;
    public File mResolvedBaseFile;
    public File mResolvedStageDir;
    public boolean mSessionApplied;
    public int mSessionErrorCode;
    public String mSessionErrorMessage;
    public boolean mSessionFailed;
    public final PackageSessionProvider mSessionProvider;
    public boolean mSessionReady;
    public boolean mShouldBeSealed;
    public SigningDetails mSigningDetails;
    public final SilentUpdatePolicy mSilentUpdatePolicy;
    public final StagedSession mStagedSession;
    public final StagingManager mStagingManager;
    public Boolean mUserActionRequired;
    public int mUserActionRequirement;
    public String mUserActionRequirementMsg;
    public boolean mVerityFoundForApks;
    public long mVersionCode;
    public final PackageInstaller.SessionParams params;
    public final int sessionId;
    public final String stageCid;
    public final File stageDir;
    public long updatedMillis;
    public final int userId;
    public final AtomicInteger mActiveCount = new AtomicInteger();
    public final Object mLock = new Object();
    public final AtomicBoolean mTransactionLock = new AtomicBoolean(false);
    public final AtomicBoolean mUnknownSourceInstallAccepted = new AtomicBoolean(false);
    public final Object mProgressLock = new Object();
    public float mClientProgress = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
    public float mInternalProgress = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
    public float mProgress = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
    public float mReportedProgress = -1.0f;
    public float mIncrementalProgress = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
    public boolean mSealed = false;
    public final AtomicBoolean mPreapprovalRequested = new AtomicBoolean(false);
    public final AtomicBoolean mCommitted = new AtomicBoolean(false);
    public boolean mStageDirInUse = false;
    public boolean mVerificationInProgress = false;
    public boolean mPermissionsManuallyAccepted = false;
    public final ArrayList mFds = new ArrayList();
    public final ArrayList mBridges = new ArrayList();
    public SparseArray mChildSessions = new SparseArray();
    public ArraySet mFiles = new ArraySet();
    public ArrayMap mChecksums = new ArrayMap();
    public final List mResolvedStagedFiles = new ArrayList();
    public final List mResolvedInheritedFiles = new ArrayList();
    public final List mResolvedInstructionSets = new ArrayList();
    public final List mResolvedNativeLibPaths = new ArrayList();
    public volatile boolean mDataLoaderFinished = false;
    public int mValidatedTargetSdk = Integer.MAX_VALUE;

    public static boolean isStagedSessionStateValid(boolean z, boolean z2, boolean z3) {
        return ((z || z2 || z3) && (!z || z2 || z3) && ((z || !z2 || z3) && (z || z2 || !z3))) ? false : true;
    }

    public static int userActionRequirementToReason(int i) {
        return i != 3 ? 0 : 2;
    }

    /* loaded from: classes3.dex */
    public class FileEntry {
        public final InstallationFile mFile;
        public final int mIndex;

        public FileEntry(int i, InstallationFile installationFile) {
            this.mIndex = i;
            this.mFile = installationFile;
        }

        public int getIndex() {
            return this.mIndex;
        }

        public InstallationFile getFile() {
            return this.mFile;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof FileEntry)) {
                return false;
            }
            FileEntry fileEntry = (FileEntry) obj;
            return this.mFile.getLocation() == fileEntry.mFile.getLocation() && TextUtils.equals(this.mFile.getName(), fileEntry.mFile.getName());
        }

        public int hashCode() {
            return Objects.hash(Integer.valueOf(this.mFile.getLocation()), this.mFile.getName());
        }
    }

    /* loaded from: classes3.dex */
    public class PerFileChecksum {
        public final Checksum[] mChecksums;
        public final byte[] mSignature;

        public PerFileChecksum(Checksum[] checksumArr, byte[] bArr) {
            this.mChecksums = checksumArr;
            this.mSignature = bArr;
        }

        public Checksum[] getChecksums() {
            return this.mChecksums;
        }

        public byte[] getSignature() {
            return this.mSignature;
        }
    }

    /* loaded from: classes3.dex */
    public class StagedSession implements StagingManager.StagedSession {
        public StagedSession() {
        }

        @Override // com.android.server.pm.StagingManager.StagedSession
        public List getChildSessions() {
            ArrayList arrayList;
            PackageInstallerSession packageInstallerSession = PackageInstallerSession.this;
            if (!packageInstallerSession.params.isMultiPackage) {
                return Collections.EMPTY_LIST;
            }
            synchronized (packageInstallerSession.mLock) {
                int size = PackageInstallerSession.this.mChildSessions.size();
                arrayList = new ArrayList(size);
                for (int i = 0; i < size; i++) {
                    arrayList.add(((PackageInstallerSession) PackageInstallerSession.this.mChildSessions.valueAt(i)).mStagedSession);
                }
            }
            return arrayList;
        }

        @Override // com.android.server.pm.StagingManager.StagedSession
        public PackageInstaller.SessionParams sessionParams() {
            return PackageInstallerSession.this.params;
        }

        @Override // com.android.server.pm.StagingManager.StagedSession
        public boolean isMultiPackage() {
            return PackageInstallerSession.this.params.isMultiPackage;
        }

        @Override // com.android.server.pm.StagingManager.StagedSession
        public boolean isApexSession() {
            return (PackageInstallerSession.this.params.installFlags & IInstalld.FLAG_CLEAR_APP_DATA_KEEP_ART_PROFILES) != 0;
        }

        @Override // com.android.server.pm.StagingManager.StagedSession
        public int sessionId() {
            return PackageInstallerSession.this.sessionId;
        }

        @Override // com.android.server.pm.StagingManager.StagedSession
        public boolean containsApexSession() {
            return sessionContains(new Predicate() { // from class: com.android.server.pm.PackageInstallerSession$StagedSession$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean isApexSession;
                    isApexSession = ((StagingManager.StagedSession) obj).isApexSession();
                    return isApexSession;
                }
            });
        }

        @Override // com.android.server.pm.StagingManager.StagedSession
        public String getPackageName() {
            return PackageInstallerSession.this.getPackageName();
        }

        @Override // com.android.server.pm.StagingManager.StagedSession
        public void setSessionReady() {
            PackageInstallerSession.this.setSessionReady();
        }

        @Override // com.android.server.pm.StagingManager.StagedSession
        public void setSessionFailed(int i, String str) {
            PackageInstallerSession.this.setSessionFailed(i, str);
        }

        @Override // com.android.server.pm.StagingManager.StagedSession
        public CompletableFuture installSession() {
            PackageInstallerSession.this.assertCallerIsOwnerOrRootOrSystem();
            PackageInstallerSession.this.assertNotChild("StagedSession#installSession");
            Preconditions.checkArgument(isCommitted() && isSessionReady());
            return PackageInstallerSession.this.install();
        }

        @Override // com.android.server.pm.StagingManager.StagedSession
        public boolean hasParentSessionId() {
            return PackageInstallerSession.this.hasParentSessionId();
        }

        @Override // com.android.server.pm.StagingManager.StagedSession
        public int getParentSessionId() {
            return PackageInstallerSession.this.getParentSessionId();
        }

        @Override // com.android.server.pm.StagingManager.StagedSession
        public boolean isCommitted() {
            return PackageInstallerSession.this.isCommitted();
        }

        @Override // com.android.server.pm.StagingManager.StagedSession
        public boolean isInTerminalState() {
            return PackageInstallerSession.this.isInTerminalState();
        }

        @Override // com.android.server.pm.StagingManager.StagedSession
        public boolean isDestroyed() {
            return PackageInstallerSession.this.isDestroyed();
        }

        @Override // com.android.server.pm.StagingManager.StagedSession
        public long getCommittedMillis() {
            return PackageInstallerSession.this.getCommittedMillis();
        }

        public static /* synthetic */ boolean lambda$sessionContains$1(Predicate predicate, PackageInstallerSession packageInstallerSession) {
            return predicate.test(packageInstallerSession.mStagedSession);
        }

        @Override // com.android.server.pm.StagingManager.StagedSession
        public boolean sessionContains(final Predicate predicate) {
            return PackageInstallerSession.this.sessionContains(new Predicate() { // from class: com.android.server.pm.PackageInstallerSession$StagedSession$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean lambda$sessionContains$1;
                    lambda$sessionContains$1 = PackageInstallerSession.StagedSession.lambda$sessionContains$1(predicate, (PackageInstallerSession) obj);
                    return lambda$sessionContains$1;
                }
            });
        }

        @Override // com.android.server.pm.StagingManager.StagedSession
        public boolean isSessionReady() {
            return PackageInstallerSession.this.isSessionReady();
        }

        @Override // com.android.server.pm.StagingManager.StagedSession
        public boolean isSessionFailed() {
            return PackageInstallerSession.this.isSessionFailed();
        }

        @Override // com.android.server.pm.StagingManager.StagedSession
        public void abandon() {
            PackageInstallerSession.this.abandon();
        }

        @Override // com.android.server.pm.StagingManager.StagedSession
        public void verifySession() {
            PackageInstallerSession.this.assertCallerIsOwnerOrRootOrSystem();
            Preconditions.checkArgument(isCommitted());
            Preconditions.checkArgument(!isInTerminalState());
            PackageInstallerSession.this.verify();
        }
    }

    public static boolean isDataLoaderInstallation(PackageInstaller.SessionParams sessionParams) {
        return sessionParams.dataLoaderParams != null;
    }

    public static boolean isSystemDataLoaderInstallation(PackageInstaller.SessionParams sessionParams) {
        if (isDataLoaderInstallation(sessionParams)) {
            return "android".equals(sessionParams.dataLoaderParams.getComponentName().getPackageName());
        }
        return false;
    }

    public final boolean isDataLoaderInstallation() {
        return isDataLoaderInstallation(this.params);
    }

    public final boolean isStreamingInstallation() {
        return isDataLoaderInstallation() && this.params.dataLoaderParams.getType() == 1;
    }

    public final boolean isIncrementalInstallation() {
        return isDataLoaderInstallation() && this.params.dataLoaderParams.getType() == 2;
    }

    public final boolean isSystemDataLoaderInstallation() {
        return isSystemDataLoaderInstallation(this.params);
    }

    public final boolean isInstallerDeviceOwnerOrAffiliatedProfileOwner() {
        DevicePolicyManagerInternal devicePolicyManagerInternal;
        assertNotLocked("isInstallerDeviceOwnerOrAffiliatedProfileOwner");
        return this.userId == UserHandle.getUserId(getInstallerUid()) && (devicePolicyManagerInternal = (DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class)) != null && devicePolicyManagerInternal.canSilentlyInstallPackage(getInstallSource().mInstallerPackageName, this.mInstallerUid);
    }

    public final int computeUserActionRequirement() {
        PackageInstaller.PreapprovalDetails preapprovalDetails;
        synchronized (this.mLock) {
            if (this.mPermissionsManuallyAccepted) {
                return 0;
            }
            String str = this.mPackageName;
            if (str == null) {
                str = (!this.mPreapprovalRequested.get() || (preapprovalDetails = this.mPreapprovalDetails) == null) ? null : preapprovalDetails.getPackageName();
            }
            boolean z = this.mHasDeviceAdminReceiver;
            PackageInstaller.SessionParams sessionParams = this.params;
            int i = ((sessionParams.installFlags & 1024) != 0 || sessionParams.requireUserAction == 1) ? 1 : 0;
            Computer snapshotComputer = this.mPm.snapshotComputer();
            boolean z2 = snapshotComputer.checkUidPermission("android.permission.INSTALL_PACKAGES", this.mInstallerUid) == 0;
            boolean z3 = snapshotComputer.checkUidPermission("android.permission.INSTALL_SELF_UPDATES", this.mInstallerUid) == 0;
            boolean z4 = snapshotComputer.checkUidPermission("android.permission.INSTALL_PACKAGE_UPDATES", this.mInstallerUid) == 0;
            boolean z5 = snapshotComputer.checkUidPermission("android.permission.UPDATE_PACKAGES_WITHOUT_USER_ACTION", this.mInstallerUid) == 0;
            boolean z6 = snapshotComputer.checkUidPermission("android.permission.INSTALL_DPC_PACKAGES", this.mInstallerUid) == 0;
            int packageUid = snapshotComputer.getPackageUid(str, 0L, this.userId);
            boolean z7 = packageUid != -1 || isApexSession();
            InstallSourceInfo installSourceInfo = z7 ? snapshotComputer.getInstallSourceInfo(str, this.userId) : null;
            String installingPackageName = installSourceInfo != null ? installSourceInfo.getInstallingPackageName() : null;
            String updateOwnerPackageName = installSourceInfo != null ? installSourceInfo.getUpdateOwnerPackageName() : null;
            boolean z8 = z7 && Objects.equals(installingPackageName, getInstallerPackageName());
            boolean equals = TextUtils.equals(updateOwnerPackageName, getInstallerPackageName());
            boolean z9 = packageUid == this.mInstallerUid;
            boolean z10 = z2 || (z4 && z7) || ((z3 && z9) || (z6 && z));
            boolean z11 = this.mInstallerUid == 0;
            boolean z12 = this.mInstallerUid == 1000;
            boolean z13 = this.mInstallerUid == 2000;
            boolean z14 = (this.params.installFlags & 67108864) != 0;
            boolean z15 = PackageManagerService.isUpdateOwnershipEnforcementAvailable() && updateOwnerPackageName != null;
            if (z11 || z12 || isInstallerDeviceOwnerOrAffiliatedProfileOwner()) {
                return i;
            }
            if (z15 && !isApexSession() && !equals && !z13 && !z14 && !this.mPm.mInjector.getUpdateOwnershipHelper().isSamsungApp(str)) {
                this.mUserActionRequirementMsg = getInstallerPackageName() + " is not UpdateOwner for " + str + ", UpdateOwner: " + updateOwnerPackageName;
                return 3;
            }
            if (z10) {
                return i;
            }
            if (snapshotComputer.isInstallDisabledForPackage(getInstallerPackageName(), this.mInstallerUid, this.userId) || this.params.requireUserAction != 2 || !z5) {
                return 1;
            }
            if (!z15 ? !z8 : !equals) {
                if (!z9) {
                    return 1;
                }
            }
            return 2;
        }
    }

    public final void updateUserActionRequirement(int i) {
        synchronized (this.mLock) {
            this.mUserActionRequirement = i;
        }
    }

    public PackageInstallerSession(PackageInstallerService.InternalCallback internalCallback, Context context, PackageManagerService packageManagerService, PackageSessionProvider packageSessionProvider, SilentUpdatePolicy silentUpdatePolicy, Looper looper, StagingManager stagingManager, int i, int i2, int i3, InstallSource installSource, PackageInstaller.SessionParams sessionParams, long j, long j2, File file, String str, InstallationFile[] installationFileArr, ArrayMap arrayMap, boolean z, boolean z2, boolean z3, boolean z4, int[] iArr, int i4, boolean z5, boolean z6, boolean z7, int i5, String str2) {
        this.mPrepared = false;
        this.mShouldBeSealed = false;
        this.mSessionErrorCode = 0;
        this.mDestroyed = false;
        Handler.Callback callback = new Handler.Callback() { // from class: com.android.server.pm.PackageInstallerSession.4
            /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
            /* JADX WARN: Code restructure failed: missing block: B:13:0x0065, code lost:
            
                return true;
             */
            @Override // android.os.Handler.Callback
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public boolean handleMessage(android.os.Message r13) {
                /*
                    r12 = this;
                    int r0 = r13.what
                    r1 = 1
                    switch(r0) {
                        case 1: goto L60;
                        case 2: goto L5a;
                        case 3: goto L54;
                        case 4: goto L19;
                        case 5: goto Ld;
                        case 6: goto L7;
                        default: goto L6;
                    }
                L6:
                    goto L65
                L7:
                    com.android.server.pm.PackageInstallerSession r12 = com.android.server.pm.PackageInstallerSession.this
                    com.android.server.pm.PackageInstallerSession.m9438$$Nest$mhandlePreapprovalRequest(r12)
                    goto L65
                Ld:
                    int r0 = r13.arg1
                    java.lang.Object r13 = r13.obj
                    java.lang.String r13 = (java.lang.String) r13
                    com.android.server.pm.PackageInstallerSession r12 = com.android.server.pm.PackageInstallerSession.this
                    com.android.server.pm.PackageInstallerSession.m9444$$Nest$monSessionValidationFailure(r12, r0, r13)
                    goto L65
                L19:
                    java.lang.Object r13 = r13.obj
                    com.android.internal.os.SomeArgs r13 = (com.android.internal.os.SomeArgs) r13
                    java.lang.Object r0 = r13.arg1
                    r7 = r0
                    java.lang.String r7 = (java.lang.String) r7
                    java.lang.Object r0 = r13.arg2
                    r10 = r0
                    java.lang.String r10 = (java.lang.String) r10
                    java.lang.Object r0 = r13.arg3
                    r11 = r0
                    android.os.Bundle r11 = (android.os.Bundle) r11
                    java.lang.Object r0 = r13.arg4
                    r3 = r0
                    android.content.IntentSender r3 = (android.content.IntentSender) r3
                    int r8 = r13.argi1
                    int r0 = r13.argi2
                    if (r0 != r1) goto L39
                    r9 = r1
                    goto L3b
                L39:
                    r0 = 0
                    r9 = r0
                L3b:
                    r13.recycle()
                    com.android.server.pm.PackageInstallerSession r13 = com.android.server.pm.PackageInstallerSession.this
                    android.content.Context r2 = com.android.server.pm.PackageInstallerSession.m9421$$Nest$fgetmContext(r13)
                    com.android.server.pm.PackageInstallerSession r13 = com.android.server.pm.PackageInstallerSession.this
                    int r4 = r13.sessionId
                    boolean r5 = com.android.server.pm.PackageInstallerSession.m9443$$Nest$misInstallerDeviceOwnerOrAffiliatedProfileOwner(r13)
                    com.android.server.pm.PackageInstallerSession r12 = com.android.server.pm.PackageInstallerSession.this
                    int r6 = r12.userId
                    com.android.server.pm.PackageInstallerSession.m9448$$Nest$smsendOnPackageInstalled(r2, r3, r4, r5, r6, r7, r8, r9, r10, r11)
                    goto L65
                L54:
                    com.android.server.pm.PackageInstallerSession r12 = com.android.server.pm.PackageInstallerSession.this
                    com.android.server.pm.PackageInstallerSession.m9437$$Nest$mhandleInstall(r12)
                    goto L65
                L5a:
                    com.android.server.pm.PackageInstallerSession r12 = com.android.server.pm.PackageInstallerSession.this
                    com.android.server.pm.PackageInstallerSession.m9440$$Nest$mhandleStreamValidateAndCommit(r12)
                    goto L65
                L60:
                    com.android.server.pm.PackageInstallerSession r12 = com.android.server.pm.PackageInstallerSession.this
                    com.android.server.pm.PackageInstallerSession.m9439$$Nest$mhandleSessionSealed(r12)
                L65:
                    return r1
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.PackageInstallerSession.AnonymousClass4.handleMessage(android.os.Message):boolean");
            }
        };
        this.mHandlerCallback = callback;
        this.mCallback = internalCallback;
        this.mContext = context;
        this.mPm = packageManagerService;
        this.mInstaller = packageManagerService != null ? packageManagerService.mInstaller : null;
        this.mSessionProvider = packageSessionProvider;
        this.mSilentUpdatePolicy = silentUpdatePolicy;
        this.mHandler = new Handler(looper, callback);
        this.mStagingManager = stagingManager;
        this.sessionId = i;
        this.userId = i2;
        this.mOriginalInstallerUid = i3;
        this.mInstallerUid = i3;
        Objects.requireNonNull(installSource);
        this.mInstallSource = installSource;
        this.mOriginalInstallerPackageName = installSource.mInstallerPackageName;
        this.params = sessionParams;
        this.createdMillis = j;
        this.updatedMillis = j;
        this.committedMillis = j2;
        this.stageDir = file;
        this.stageCid = str;
        this.mShouldBeSealed = z4;
        if (iArr != null) {
            for (int i6 : iArr) {
                this.mChildSessions.put(i6, null);
            }
        }
        this.mParentSessionId = i4;
        if (installationFileArr != null) {
            this.mFiles.ensureCapacity(installationFileArr.length);
            int length = installationFileArr.length;
            for (int i7 = 0; i7 < length; i7++) {
                if (!this.mFiles.add(new FileEntry(i7, installationFileArr[i7]))) {
                    throw new IllegalArgumentException("Trying to add a duplicate installation file");
                }
            }
        }
        if (arrayMap != null) {
            this.mChecksums.putAll(arrayMap);
        }
        if (!sessionParams.isMultiPackage) {
            if ((file == null) == (str == null)) {
                throw new IllegalArgumentException("Exactly one of stageDir or stageCid stage must be set");
            }
        }
        this.mPrepared = z;
        this.mCommitted.set(z2);
        this.mDestroyed = z3;
        this.mSessionReady = z5;
        this.mSessionApplied = z7;
        this.mSessionFailed = z6;
        this.mSessionErrorCode = i5;
        this.mSessionErrorMessage = str2 != null ? str2 : "";
        this.mStagedSession = sessionParams.isStaged ? new StagedSession() : null;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.defaultContainerGid = UserHandle.getSharedAppGid(this.mPm.snapshotComputer().getPackageUid("com.samsung.android.container", 1048576L, 0));
            Binder.restoreCallingIdentity(clearCallingIdentity);
            if (isDataLoaderInstallation()) {
                if (isApexSession()) {
                    throw new IllegalArgumentException("DataLoader installation of APEX modules is not allowed.");
                }
                if (isSystemDataLoaderInstallation() && this.mContext.checkCallingOrSelfPermission("com.android.permission.USE_SYSTEM_DATA_LOADERS") != 0) {
                    throw new SecurityException("You need the com.android.permission.USE_SYSTEM_DATA_LOADERS permission to use system data loaders");
                }
            }
            if (isIncrementalInstallation() && !IncrementalManager.isAllowed()) {
                throw new IllegalArgumentException("Incremental installation not allowed.");
            }
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final boolean shouldScrubData(int i) {
        return i >= 10000 && getInstallerUid() != i;
    }

    public PackageInstaller.SessionInfo generateInfoForCaller(boolean z, int i) {
        return generateInfoInternal(z, shouldScrubData(i));
    }

    public PackageInstaller.SessionInfo generateInfoScrubbed(boolean z) {
        return generateInfoInternal(z, true);
    }

    public final PackageInstaller.SessionInfo generateInfoInternal(boolean z, boolean z2) {
        float f;
        String str;
        PackageInstaller.SessionInfo sessionInfo = new PackageInstaller.SessionInfo();
        synchronized (this.mProgressLock) {
            f = this.mProgress;
        }
        synchronized (this.mLock) {
            sessionInfo.sessionId = this.sessionId;
            sessionInfo.userId = this.userId;
            InstallSource installSource = this.mInstallSource;
            sessionInfo.installerPackageName = installSource.mInstallerPackageName;
            sessionInfo.installerAttributionTag = installSource.mInstallerAttributionTag;
            sessionInfo.resolvedBaseCodePath = null;
            if (this.mContext.checkCallingOrSelfPermission("android.permission.READ_INSTALLED_SESSION_PATHS") == 0) {
                File file = this.mResolvedBaseFile;
                if (file == null) {
                    List addedApksLocked = getAddedApksLocked();
                    if (addedApksLocked.size() > 0) {
                        file = (File) addedApksLocked.get(0);
                    }
                }
                if (file != null) {
                    sessionInfo.resolvedBaseCodePath = file.getAbsolutePath();
                }
            }
            sessionInfo.progress = f;
            sessionInfo.sealed = this.mSealed;
            sessionInfo.isCommitted = isCommitted();
            sessionInfo.isPreapprovalRequested = isPreapprovalRequested();
            sessionInfo.active = this.mActiveCount.get() > 0;
            PackageInstaller.SessionParams sessionParams = this.params;
            sessionInfo.mode = sessionParams.mode;
            sessionInfo.installReason = sessionParams.installReason;
            sessionInfo.installScenario = sessionParams.installScenario;
            sessionInfo.sizeBytes = sessionParams.sizeBytes;
            PackageInstaller.PreapprovalDetails preapprovalDetails = this.mPreapprovalDetails;
            if (preapprovalDetails != null) {
                str = preapprovalDetails.getPackageName();
            } else {
                String str2 = this.mPackageName;
                str = str2 != null ? str2 : sessionParams.appPackageName;
            }
            sessionInfo.appPackageName = str;
            if (z) {
                PackageInstaller.PreapprovalDetails preapprovalDetails2 = this.mPreapprovalDetails;
                sessionInfo.appIcon = (preapprovalDetails2 == null || preapprovalDetails2.getIcon() == null) ? this.params.appIcon : this.mPreapprovalDetails.getIcon();
            }
            PackageInstaller.PreapprovalDetails preapprovalDetails3 = this.mPreapprovalDetails;
            sessionInfo.appLabel = preapprovalDetails3 != null ? preapprovalDetails3.getLabel() : this.params.appLabel;
            PackageInstaller.SessionParams sessionParams2 = this.params;
            sessionInfo.installLocation = sessionParams2.installLocation;
            if (!z2) {
                sessionInfo.originatingUri = sessionParams2.originatingUri;
            }
            sessionInfo.originatingUid = sessionParams2.originatingUid;
            if (!z2) {
                sessionInfo.referrerUri = sessionParams2.referrerUri;
            }
            sessionInfo.grantedRuntimePermissions = sessionParams2.getLegacyGrantedRuntimePermissions();
            PackageInstaller.SessionParams sessionParams3 = this.params;
            sessionInfo.whitelistedRestrictedPermissions = sessionParams3.whitelistedRestrictedPermissions;
            sessionInfo.autoRevokePermissionsMode = sessionParams3.autoRevokePermissionsMode;
            sessionInfo.installFlags = sessionParams3.installFlags;
            sessionInfo.isMultiPackage = sessionParams3.isMultiPackage;
            sessionInfo.isStaged = sessionParams3.isStaged;
            sessionInfo.rollbackDataPolicy = sessionParams3.rollbackDataPolicy;
            sessionInfo.parentSessionId = this.mParentSessionId;
            sessionInfo.childSessionIds = getChildSessionIdsLocked();
            sessionInfo.isSessionApplied = this.mSessionApplied;
            sessionInfo.isSessionReady = this.mSessionReady;
            sessionInfo.isSessionFailed = this.mSessionFailed;
            sessionInfo.setSessionErrorCode(this.mSessionErrorCode, this.mSessionErrorMessage);
            sessionInfo.createdMillis = this.createdMillis;
            sessionInfo.updatedMillis = this.updatedMillis;
            sessionInfo.requireUserAction = this.params.requireUserAction;
            sessionInfo.installerUid = this.mInstallerUid;
            PackageInstaller.SessionParams sessionParams4 = this.params;
            sessionInfo.packageSource = sessionParams4.packageSource;
            sessionInfo.applicationEnabledSettingPersistent = sessionParams4.applicationEnabledSettingPersistent;
            sessionInfo.pendingUserActionReason = userActionRequirementToReason(this.mUserActionRequirement);
        }
        return sessionInfo;
    }

    public boolean isSealed() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mSealed;
        }
        return z;
    }

    public boolean isPreapprovalRequested() {
        return this.mPreapprovalRequested.get();
    }

    public boolean isCommitted() {
        return this.mCommitted.get();
    }

    public boolean isDestroyed() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mDestroyed;
        }
        return z;
    }

    public final boolean isInTerminalState() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mSessionApplied || this.mSessionFailed;
        }
        return z;
    }

    public boolean isStagedAndInTerminalState() {
        return this.params.isStaged && isInTerminalState();
    }

    public final void assertNotLocked(String str) {
        if (Thread.holdsLock(this.mLock)) {
            throw new IllegalStateException(str + " is holding mLock");
        }
    }

    public final void assertSealed(String str) {
        if (isSealed()) {
            return;
        }
        throw new IllegalStateException(str + " before sealing");
    }

    public final void assertPreparedAndNotPreapprovalRequestedLocked(String str) {
        assertPreparedAndNotSealedLocked(str);
        if (isPreapprovalRequested()) {
            throw new IllegalStateException(str + " not allowed after requesting");
        }
    }

    public final void assertPreparedAndNotSealedLocked(String str) {
        assertPreparedAndNotCommittedOrDestroyedLocked(str);
        if (this.mSealed) {
            throw new SecurityException(str + " not allowed after sealing");
        }
    }

    public final void assertPreparedAndNotCommittedOrDestroyedLocked(String str) {
        assertPreparedAndNotDestroyedLocked(str);
        if (isCommitted()) {
            throw new SecurityException(str + " not allowed after commit");
        }
    }

    public final void assertPreparedAndNotDestroyedLocked(String str) {
        if (!this.mPrepared) {
            throw new IllegalStateException(str + " before prepared");
        }
        if (this.mDestroyed) {
            throw new SecurityException(str + " not allowed after destruction");
        }
    }

    public final File resolveStageDirLocked() {
        if (this.mResolvedStageDir == null) {
            File file = this.stageDir;
            if (file != null) {
                this.mResolvedStageDir = file;
            } else if (this.stageCid != null) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    String sdDir = PackageHelperExt.getSdDir(this.stageCid);
                    if (sdDir != null) {
                        this.mResolvedStageDir = new File(sdDir);
                    } else {
                        throw new IOException("Failed to resolve path to container " + this.stageCid);
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }
        return this.mResolvedStageDir;
    }

    public final void setClientProgressLocked(float f) {
        boolean z = this.mClientProgress == DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
        this.mClientProgress = f;
        computeProgressLocked(z);
    }

    public void setClientProgress(float f) {
        assertCallerIsOwnerOrRoot();
        synchronized (this.mProgressLock) {
            setClientProgressLocked(f);
        }
    }

    public void addClientProgress(float f) {
        assertCallerIsOwnerOrRoot();
        synchronized (this.mProgressLock) {
            setClientProgressLocked(this.mClientProgress + f);
        }
    }

    public final void computeProgressLocked(boolean z) {
        if (!isIncrementalInstallation() || !isCommitted()) {
            this.mProgress = MathUtils.constrain(this.mClientProgress * 0.8f, DisplayPowerController2.RATE_FROM_DOZE_TO_ON, 0.8f) + MathUtils.constrain(this.mInternalProgress * 0.2f, DisplayPowerController2.RATE_FROM_DOZE_TO_ON, 0.2f);
        } else {
            float f = this.mIncrementalProgress;
            if (f - this.mProgress >= 0.01d) {
                this.mProgress = f;
            }
        }
        if (z || this.mProgress - this.mReportedProgress >= 0.01d) {
            float f2 = this.mProgress;
            this.mReportedProgress = f2;
            this.mCallback.onSessionProgressChanged(this, f2);
        }
    }

    public String[] getNames() {
        assertCallerIsOwnerRootOrVerifier();
        synchronized (this.mLock) {
            assertPreparedAndNotDestroyedLocked("getNames");
            if (!isCommitted()) {
                return getNamesLocked();
            }
            return getStageDirContentsLocked();
        }
    }

    public final String[] getStageDirContentsLocked() {
        File file = this.stageDir;
        if (file == null) {
            return EmptyArray.STRING;
        }
        String[] list = file.list();
        return list == null ? EmptyArray.STRING : list;
    }

    public final String[] getNamesLocked() {
        if (!isDataLoaderInstallation()) {
            if (PMRune.PM_INSTALL_TO_SDCARD) {
                try {
                    File resolveStageDirLocked = resolveStageDirLocked();
                    if (resolveStageDirLocked == null) {
                        return EmptyArray.STRING;
                    }
                    String[] list = resolveStageDirLocked.list();
                    return list == null ? EmptyArray.STRING : list;
                } catch (IOException e) {
                    throw ExceptionUtils.wrap(e);
                }
            }
            return getStageDirContentsLocked();
        }
        InstallationFile[] installationFilesLocked = getInstallationFilesLocked();
        String[] strArr = new String[installationFilesLocked.length];
        int length = installationFilesLocked.length;
        for (int i = 0; i < length; i++) {
            strArr[i] = installationFilesLocked[i].getName();
        }
        return strArr;
    }

    public final InstallationFile[] getInstallationFilesLocked() {
        InstallationFile[] installationFileArr = new InstallationFile[this.mFiles.size()];
        Iterator it = this.mFiles.iterator();
        while (it.hasNext()) {
            FileEntry fileEntry = (FileEntry) it.next();
            installationFileArr[fileEntry.getIndex()] = fileEntry.getFile();
        }
        return installationFileArr;
    }

    public static ArrayList filterFiles(File file, String[] strArr, FileFilter fileFilter) {
        ArrayList arrayList = new ArrayList(strArr.length);
        for (String str : strArr) {
            File file2 = new File(file, str);
            if (fileFilter.accept(file2)) {
                arrayList.add(file2);
            }
        }
        return arrayList;
    }

    public final List getAddedApksLocked() {
        return filterFiles(PMRune.PM_INSTALL_TO_SDCARD ? this.mResolvedStageDir : this.stageDir, getNamesLocked(), sAddedApkFilter);
    }

    public final List getRemovedFilesLocked() {
        return filterFiles(PMRune.PM_INSTALL_TO_SDCARD ? this.mResolvedStageDir : this.stageDir, getNamesLocked(), sRemovedFilter);
    }

    public void setChecksums(String str, Checksum[] checksumArr, byte[] bArr) {
        if (checksumArr.length == 0) {
            return;
        }
        String str2 = getInstallSource().mInitiatingPackageName;
        if (PackageManagerServiceUtils.isInstalledByAdb(str2)) {
            str2 = getInstallSource().mInstallerPackageName;
        }
        if (TextUtils.isEmpty(str2)) {
            throw new IllegalStateException("Installer package is empty.");
        }
        ((AppOpsManager) this.mContext.getSystemService(AppOpsManager.class)).checkPackage(Binder.getCallingUid(), str2);
        if (((PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class)).getPackage(str2) == null) {
            throw new IllegalStateException("Can't obtain calling installer's package.");
        }
        if (bArr != null && bArr.length != 0) {
            try {
                ApkChecksums.verifySignature(checksumArr, bArr);
            } catch (IOException | NoSuchAlgorithmException | SignatureException e) {
                throw new IllegalArgumentException("Can't verify signature", e);
            }
        }
        assertCallerIsOwnerOrRoot();
        synchronized (this.mLock) {
            assertPreparedAndNotCommittedOrDestroyedLocked("addChecksums");
            if (this.mChecksums.containsKey(str)) {
                throw new IllegalStateException("Duplicate checksums.");
            }
            this.mChecksums.put(str, new PerFileChecksum(checksumArr, bArr));
        }
    }

    public void requestChecksums(String str, int i, int i2, List list, IOnChecksumsReadyListener iOnChecksumsReadyListener) {
        String str2;
        assertCallerIsOwnerRootOrVerifier();
        File file = new File(this.stageDir, str);
        if (PackageManagerServiceUtils.isInstalledByAdb(getInstallSource().mInitiatingPackageName)) {
            str2 = getInstallSource().mInstallerPackageName;
        } else {
            str2 = getInstallSource().mInitiatingPackageName;
        }
        try {
            this.mPm.requestFileChecksums(file, str2, i, i2, list, iOnChecksumsReadyListener);
        } catch (FileNotFoundException e) {
            throw new ParcelableException(e);
        }
    }

    public void removeSplit(String str) {
        if (isDataLoaderInstallation()) {
            throw new IllegalStateException("Cannot remove splits in a data loader installation session.");
        }
        if (TextUtils.isEmpty(this.params.appPackageName)) {
            throw new IllegalStateException("Must specify package name to remove a split");
        }
        assertCallerIsOwnerOrRoot();
        synchronized (this.mLock) {
            assertPreparedAndNotCommittedOrDestroyedLocked("removeSplit");
            try {
                createRemoveSplitMarkerLocked(str);
            } catch (IOException e) {
                throw ExceptionUtils.wrap(e);
            }
        }
    }

    public static String getRemoveMarkerName(String str) {
        String str2 = str + ".removed";
        if (FileUtils.isValidExtFilename(str2)) {
            return str2;
        }
        throw new IllegalArgumentException("Invalid marker: " + str2);
    }

    public final void createRemoveSplitMarkerLocked(String str) {
        try {
            File file = new File(PMRune.PM_INSTALL_TO_SDCARD ? resolveStageDirLocked() : this.stageDir, getRemoveMarkerName(str));
            file.createNewFile();
            Os.chmod(file.getAbsolutePath(), 0);
        } catch (ErrnoException e) {
            throw e.rethrowAsIOException();
        }
    }

    public final void assertShellOrSystemCalling(String str) {
        int callingUid = Binder.getCallingUid();
        if (callingUid == 0 || callingUid == 1000 || callingUid == 2000) {
            return;
        }
        throw new SecurityException(str + " only supported from shell or system");
    }

    public final void assertCanWrite(boolean z) {
        if (isDataLoaderInstallation()) {
            throw new IllegalStateException("Cannot write regular files in a data loader installation session.");
        }
        assertCallerIsOwnerOrRoot();
        synchronized (this.mLock) {
            assertPreparedAndNotSealedLocked("assertCanWrite");
        }
        if (z) {
            assertShellOrSystemCalling("Reverse mode");
        }
    }

    public final File getTmpAppMetadataFile() {
        return new File(Environment.getDataAppDirectory(this.params.volumeUuid), this.sessionId + PackageManagerShellCommandDataLoader.STDIN_PATH + "app.metadata");
    }

    public final File getStagedAppMetadataFile() {
        File file = new File(this.stageDir, "app.metadata");
        if (file.exists()) {
            return file;
        }
        return null;
    }

    public static boolean isAppMetadata(String str) {
        return str.endsWith("app.metadata");
    }

    public static boolean isAppMetadata(File file) {
        return isAppMetadata(file.getName());
    }

    public ParcelFileDescriptor getAppMetadataFd() {
        assertCallerIsOwnerOrRoot();
        synchronized (this.mLock) {
            assertPreparedAndNotCommittedOrDestroyedLocked("getAppMetadataFd");
            if (getStagedAppMetadataFile() == null) {
                return null;
            }
            try {
                return openReadInternalLocked("app.metadata");
            } catch (IOException e) {
                throw ExceptionUtils.wrap(e);
            }
        }
    }

    public void removeAppMetadata() {
        File stagedAppMetadataFile = getStagedAppMetadataFile();
        if (stagedAppMetadataFile != null) {
            stagedAppMetadataFile.delete();
        }
    }

    public static long getAppMetadataSizeLimit() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return DeviceConfig.getLong("package_manager_service", "app_metadata_byte_size_limit", 32000L);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public ParcelFileDescriptor openWriteAppMetadata() {
        assertCallerIsOwnerOrRoot();
        synchronized (this.mLock) {
            assertPreparedAndNotSealedLocked("openWriteAppMetadata");
        }
        try {
            return doWriteInternal("app.metadata", 0L, -1L, null);
        } catch (IOException e) {
            throw ExceptionUtils.wrap(e);
        }
    }

    public ParcelFileDescriptor openWrite(String str, long j, long j2) {
        assertCanWrite(false);
        try {
            return doWriteInternal(str, j, j2, null);
        } catch (IOException e) {
            throw ExceptionUtils.wrap(e);
        }
    }

    public void write(String str, long j, long j2, ParcelFileDescriptor parcelFileDescriptor) {
        assertCanWrite(parcelFileDescriptor != null);
        try {
            doWriteInternal(str, j, j2, parcelFileDescriptor);
        } catch (IOException e) {
            throw ExceptionUtils.wrap(e);
        }
    }

    public void stageViaHardLink(String str) {
        if (Binder.getCallingUid() != 1000) {
            throw new SecurityException("link() can only be run by the system");
        }
        try {
            File resolveStageDirLocked = resolveStageDirLocked();
            File file = new File(str);
            if (!PMRune.PM_INSTALL_TO_SDCARD) {
                resolveStageDirLocked = this.stageDir;
            }
            File file2 = new File(resolveStageDirLocked, file.getName());
            String absolutePath = file2.getAbsolutePath();
            try {
                try {
                    Os.link(str, absolutePath);
                    Os.chmod(absolutePath, FrameworkStatsLog.VBMETA_DIGEST_REPORTED);
                } catch (IOException e) {
                    try {
                        Os.unlink(absolutePath);
                    } catch (Exception unused) {
                        Slog.d("PackageInstallerSession", "Failed to unlink session file: " + absolutePath);
                    }
                    throw ExceptionUtils.wrap(e);
                }
            } catch (ErrnoException e2) {
                e2.rethrowAsIOException();
            }
            if (SELinux.restorecon(file2)) {
                return;
            }
            throw new IOException("Can't relabel file: " + file2);
        } catch (IOException e3) {
            throw ExceptionUtils.wrap(e3);
        }
    }

    public final ParcelFileDescriptor openTargetInternal(String str, int i, int i2) {
        return new ParcelFileDescriptor(Os.open(str, i, i2));
    }

    public final ParcelFileDescriptor createRevocableFdInternal(RevocableFileDescriptor revocableFileDescriptor, ParcelFileDescriptor parcelFileDescriptor) {
        int detachFd = parcelFileDescriptor.detachFd();
        FileDescriptor fileDescriptor = new FileDescriptor();
        fileDescriptor.setInt$(detachFd);
        revocableFileDescriptor.init(this.mContext, fileDescriptor);
        return revocableFileDescriptor.getRevocableFileDescriptor();
    }

    public final ParcelFileDescriptor doWriteInternal(String str, long j, long j2, ParcelFileDescriptor parcelFileDescriptor) {
        FileBridge fileBridge;
        RevocableFileDescriptor revocableFileDescriptor;
        File file;
        synchronized (this.mLock) {
            if (PackageInstaller.ENABLE_REVOCABLE_FD) {
                RevocableFileDescriptor revocableFileDescriptor2 = new RevocableFileDescriptor();
                this.mFds.add(revocableFileDescriptor2);
                revocableFileDescriptor = revocableFileDescriptor2;
                fileBridge = null;
            } else {
                FileBridge fileBridge2 = new FileBridge();
                this.mBridges.add(fileBridge2);
                fileBridge = fileBridge2;
                revocableFileDescriptor = null;
            }
        }
        try {
            if (!FileUtils.isValidExtFilename(str)) {
                throw new IllegalArgumentException("Invalid name: " + str);
            }
            if (PMRune.PM_INSTALL_TO_SDCARD) {
                file = new File(resolveStageDirLocked(), str);
            } else {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    File file2 = new File(this.stageDir, str);
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    file = file2;
                } catch (Throwable th) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            }
            int i = str.equals("app.metadata") ? FrameworkStatsLog.DISPLAY_HBM_STATE_CHANGED : FrameworkStatsLog.VBMETA_DIGEST_REPORTED;
            ParcelFileDescriptor openTargetInternal = openTargetInternal(file.getAbsolutePath(), OsConstants.O_CREAT | OsConstants.O_WRONLY, i);
            Os.chmod(file.getAbsolutePath(), i);
            if (this.stageDir != null && j2 > 0) {
                ((StorageManager) this.mContext.getSystemService(StorageManager.class)).allocateBytes(openTargetInternal.getFileDescriptor(), j2, InstallLocationUtils.translateAllocateFlags(this.params.installFlags));
            }
            if (j > 0) {
                Os.lseek(openTargetInternal.getFileDescriptor(), j, OsConstants.SEEK_SET);
            }
            if (parcelFileDescriptor != null) {
                try {
                    final Int64Ref int64Ref = new Int64Ref(0L);
                    FileUtils.copy(parcelFileDescriptor.getFileDescriptor(), openTargetInternal.getFileDescriptor(), j2, null, new SystemServerInitThreadPool$$ExternalSyntheticLambda1(), new FileUtils.ProgressListener() { // from class: com.android.server.pm.PackageInstallerSession$$ExternalSyntheticLambda2
                        @Override // android.os.FileUtils.ProgressListener
                        public final void onProgress(long j3) {
                            PackageInstallerSession.this.lambda$doWriteInternal$0(int64Ref, j3);
                        }
                    });
                    IoUtils.closeQuietly(openTargetInternal);
                    IoUtils.closeQuietly(parcelFileDescriptor);
                    synchronized (this.mLock) {
                        if (PackageInstaller.ENABLE_REVOCABLE_FD) {
                            this.mFds.remove(revocableFileDescriptor);
                        } else {
                            fileBridge.forceClose();
                            this.mBridges.remove(fileBridge);
                        }
                    }
                    return null;
                } catch (Throwable th2) {
                    IoUtils.closeQuietly(openTargetInternal);
                    IoUtils.closeQuietly(parcelFileDescriptor);
                    synchronized (this.mLock) {
                        if (PackageInstaller.ENABLE_REVOCABLE_FD) {
                            this.mFds.remove(revocableFileDescriptor);
                        } else {
                            fileBridge.forceClose();
                            this.mBridges.remove(fileBridge);
                        }
                        throw th2;
                    }
                }
            }
            if (PackageInstaller.ENABLE_REVOCABLE_FD) {
                return createRevocableFdInternal(revocableFileDescriptor, openTargetInternal);
            }
            fileBridge.setTargetFile(openTargetInternal);
            fileBridge.start();
            return fileBridge.getClientSocket();
        } catch (ErrnoException e) {
            throw e.rethrowAsIOException();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$doWriteInternal$0(Int64Ref int64Ref, long j) {
        if (this.params.sizeBytes > 0) {
            long j2 = j - int64Ref.value;
            int64Ref.value = j;
            synchronized (this.mProgressLock) {
                setClientProgressLocked(this.mClientProgress + (((float) j2) / ((float) this.params.sizeBytes)));
            }
        }
    }

    public ParcelFileDescriptor openRead(String str) {
        ParcelFileDescriptor openReadInternalLocked;
        if (isDataLoaderInstallation()) {
            throw new IllegalStateException("Cannot read regular files in a data loader installation session.");
        }
        assertCallerIsOwnerOrRoot();
        synchronized (this.mLock) {
            assertPreparedAndNotCommittedOrDestroyedLocked("openRead");
            try {
                openReadInternalLocked = openReadInternalLocked(str);
            } catch (IOException e) {
                throw ExceptionUtils.wrap(e);
            }
        }
        return openReadInternalLocked;
    }

    public final ParcelFileDescriptor openReadInternalLocked(String str) {
        try {
            if (!FileUtils.isValidExtFilename(str)) {
                throw new IllegalArgumentException("Invalid name: " + str);
            }
            return new ParcelFileDescriptor(Os.open(new File(PMRune.PM_INSTALL_TO_SDCARD ? resolveStageDirLocked() : this.stageDir, str).getAbsolutePath(), OsConstants.O_RDONLY, 0));
        } catch (ErrnoException e) {
            throw e.rethrowAsIOException();
        }
    }

    public final void assertCallerIsOwnerRootOrVerifier() {
        int callingUid = Binder.getCallingUid();
        if (callingUid == 0 || callingUid == this.mInstallerUid) {
            return;
        }
        if (isSealed() && this.mContext.checkCallingOrSelfPermission("android.permission.PACKAGE_VERIFICATION_AGENT") == 0) {
            return;
        }
        throw new SecurityException("Session does not belong to uid " + callingUid);
    }

    public final void assertCallerIsOwnerOrRoot() {
        int callingUid = Binder.getCallingUid();
        if (callingUid == 0 || callingUid == this.mInstallerUid) {
            return;
        }
        throw new SecurityException("Session does not belong to uid " + callingUid);
    }

    public final void assertCallerIsOwnerOrRootOrSystem() {
        int callingUid = Binder.getCallingUid();
        if (callingUid == 0 || callingUid == this.mInstallerUid || callingUid == 1000) {
            return;
        }
        throw new SecurityException("Session does not belong to uid " + callingUid);
    }

    public final void assertNoWriteFileTransfersOpenLocked() {
        Iterator it = this.mFds.iterator();
        while (it.hasNext()) {
            if (!((RevocableFileDescriptor) it.next()).isRevoked()) {
                throw new SecurityException("Files still open");
            }
        }
        Iterator it2 = this.mBridges.iterator();
        while (it2.hasNext()) {
            if (!((FileBridge) it2.next()).isClosed()) {
                throw new SecurityException("Files still open");
            }
        }
    }

    public void commit(IntentSender intentSender, boolean z) {
        assertNotChild("commit");
        Slog.i("PackageInstallerSession", "START COMMIT SESSION: id{" + this.sessionId + "}");
        if (markAsSealed(intentSender, z)) {
            if (isMultiPackage()) {
                synchronized (this.mLock) {
                    boolean z2 = false;
                    for (int size = this.mChildSessions.size() - 1; size >= 0; size--) {
                        if (!((PackageInstallerSession) this.mChildSessions.valueAt(size)).markAsSealed(null, z)) {
                            z2 = true;
                        }
                    }
                    if (z2) {
                        return;
                    }
                }
            }
            File stagedAppMetadataFile = getStagedAppMetadataFile();
            if (stagedAppMetadataFile != null) {
                long appMetadataSizeLimit = getAppMetadataSizeLimit();
                if (stagedAppMetadataFile.length() > appMetadataSizeLimit) {
                    stagedAppMetadataFile.delete();
                    throw new IllegalArgumentException("App metadata size exceeds the maximum allowed limit of " + appMetadataSizeLimit);
                }
                if (isIncrementalInstallation()) {
                    stagedAppMetadataFile.renameTo(getTmpAppMetadataFile());
                }
            }
            dispatchSessionSealed();
        }
    }

    public void seal() {
        assertNotChild("seal");
        assertCallerIsOwnerOrRoot();
        try {
            sealInternal();
            Iterator it = getChildSessions().iterator();
            while (it.hasNext()) {
                ((PackageInstallerSession) it.next()).sealInternal();
            }
        } catch (PackageManagerException e) {
            throw new IllegalStateException("Package is not valid", e);
        }
    }

    public final void sealInternal() {
        synchronized (this.mLock) {
            sealLocked();
        }
    }

    public List fetchPackageNames() {
        assertNotChild("fetchPackageNames");
        assertCallerIsOwnerOrRoot();
        List selfOrChildSessions = getSelfOrChildSessions();
        ArrayList arrayList = new ArrayList(selfOrChildSessions.size());
        Iterator it = selfOrChildSessions.iterator();
        while (it.hasNext()) {
            arrayList.add(((PackageInstallerSession) it.next()).fetchPackageName());
        }
        return arrayList;
    }

    public final String fetchPackageName() {
        String packageName;
        assertSealed("fetchPackageName");
        synchronized (this.mLock) {
            ParseTypeImpl forDefaultParsing = ParseTypeImpl.forDefaultParsing();
            Iterator it = getAddedApksLocked().iterator();
            while (it.hasNext()) {
                ParseResult parseApkLite = ApkLiteParseUtils.parseApkLite(forDefaultParsing.reset(), (File) it.next(), 0);
                if (parseApkLite.isError()) {
                    throw new IllegalStateException("Can't parse package for session=" + this.sessionId, parseApkLite.getException());
                }
                packageName = ((ApkLite) parseApkLite.getResult()).getPackageName();
                if (packageName != null) {
                }
            }
            throw new IllegalStateException("Can't fetch package name for session=" + this.sessionId);
        }
        return packageName;
    }

    public final void dispatchSessionSealed() {
        this.mHandler.obtainMessage(1).sendToTarget();
    }

    public final void handleSessionSealed() {
        assertSealed("dispatchSessionSealed");
        this.mCallback.onSessionSealedBlocking(this);
        dispatchStreamValidateAndCommit();
    }

    public final void dispatchStreamValidateAndCommit() {
        this.mHandler.obtainMessage(2).sendToTarget();
    }

    public final void handleStreamValidateAndCommit() {
        try {
            Iterator it = getChildSessions().iterator();
            boolean z = true;
            while (it.hasNext()) {
                z &= ((PackageInstallerSession) it.next()).streamValidateAndCommit();
            }
            if (z && streamValidateAndCommit()) {
                this.mHandler.obtainMessage(3).sendToTarget();
            }
        } catch (PackageManagerException e) {
            destroy();
            String completeMessage = ExceptionUtils.getCompleteMessage(e);
            dispatchSessionFinished(e.error, completeMessage, null);
            maybeFinishChildSessions(e.error, completeMessage);
        }
    }

    public final void handlePreapprovalRequest() {
        if (sendPendingUserActionIntentIfNeeded()) {
            return;
        }
        dispatchSessionPreappoved();
    }

    /* loaded from: classes3.dex */
    public final class FileSystemConnector extends IPackageInstallerSessionFileSystemConnector.Stub {
        public final Set mAddedFiles = new ArraySet();

        public FileSystemConnector(List list) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                this.mAddedFiles.add(((InstallationFileParcel) it.next()).name);
            }
        }

        public void writeData(String str, long j, long j2, ParcelFileDescriptor parcelFileDescriptor) {
            if (parcelFileDescriptor == null) {
                throw new IllegalArgumentException("incomingFd can't be null");
            }
            if (!this.mAddedFiles.contains(str)) {
                throw new SecurityException("File name is not in the list of added files.");
            }
            try {
                PackageInstallerSession.this.doWriteInternal(str, j, j2, parcelFileDescriptor);
            } catch (IOException e) {
                throw ExceptionUtils.wrap(e);
            }
        }
    }

    public final void resizeContainerForSd(String str, long j) {
        try {
            if (PackageHelperExt.isContainerMounted(str)) {
                PackageHelperExt.unMountSdDir(str, true);
            }
            PackageHelperExt.resizeSdDir(j, str, AsecInstallHelper.getEncryptKey());
            PackageHelperExt.mountSdDir(str, AsecInstallHelper.getEncryptKey(), 1000, false);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PackageManagerException(-18, "Failed to find mounted " + str);
        }
    }

    public static boolean isSecureFrpInstallAllowed(Context context, int i) {
        PackageManagerInternal packageManagerInternal = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
        String[] knownPackageNames = packageManagerInternal.getKnownPackageNames(2, 0);
        AndroidPackage androidPackage = packageManagerInternal.getPackage(i);
        return (androidPackage == null || !ArrayUtils.contains(knownPackageNames, androidPackage.getPackageName())) && context.checkCallingOrSelfPermission("android.permission.INSTALL_PACKAGES") == 0;
    }

    public static boolean isIncrementalInstallationAllowed(String str) {
        PackageStateInternal packageStateInternal = ((PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class)).getPackageStateInternal(str);
        if (packageStateInternal == null || packageStateInternal.getPkg() == null) {
            return true;
        }
        return (packageStateInternal.isSystem() || packageStateInternal.isUpdatedSystemApp()) ? false : true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x005e, code lost:
    
        r5.mContext.enforceCallingOrSelfPermission("android.permission.INSTALL_PACKAGES", null);
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x006a, code lost:
    
        if (r5.mInstallerUid == r5.mOriginalInstallerUid) goto L25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0074, code lost:
    
        throw new java.lang.IllegalArgumentException("Session has not been transferred");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean markAsSealed(android.content.IntentSender r6, boolean r7) {
        /*
            r5 = this;
            r0 = 0
            r1 = 1
            if (r6 != 0) goto Ld
            boolean r2 = r5.hasParentSessionId()
            if (r2 == 0) goto Lb
            goto Ld
        Lb:
            r2 = r0
            goto Le
        Ld:
            r2 = r1
        Le:
            java.lang.String r3 = "statusReceiver can't be null for the root session"
            com.android.internal.util.Preconditions.checkState(r2, r3)
            r5.assertCallerIsOwnerOrRoot()
            java.lang.Object r2 = r5.mLock
            monitor-enter(r2)
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L93
            r3.<init>()     // Catch: java.lang.Throwable -> L93
            java.lang.String r4 = "commit of session "
            r3.append(r4)     // Catch: java.lang.Throwable -> L93
            int r4 = r5.sessionId     // Catch: java.lang.Throwable -> L93
            r3.append(r4)     // Catch: java.lang.Throwable -> L93
            java.lang.String r3 = r3.toString()     // Catch: java.lang.Throwable -> L93
            r5.assertPreparedAndNotDestroyedLocked(r3)     // Catch: java.lang.Throwable -> L93
            r5.assertNoWriteFileTransfersOpenLocked()     // Catch: java.lang.Throwable -> L93
            android.content.Context r3 = r5.mContext     // Catch: java.lang.Throwable -> L93
            android.content.ContentResolver r3 = r3.getContentResolver()     // Catch: java.lang.Throwable -> L93
            java.lang.String r4 = "secure_frp_mode"
            int r3 = android.provider.Settings.Global.getInt(r3, r4, r0)     // Catch: java.lang.Throwable -> L93
            if (r3 != r1) goto L44
            r3 = r1
            goto L45
        L44:
            r3 = r0
        L45:
            if (r3 == 0) goto L5c
            android.content.Context r3 = r5.mContext     // Catch: java.lang.Throwable -> L93
            int r4 = android.os.Binder.getCallingUid()     // Catch: java.lang.Throwable -> L93
            boolean r3 = isSecureFrpInstallAllowed(r3, r4)     // Catch: java.lang.Throwable -> L93
            if (r3 == 0) goto L54
            goto L5c
        L54:
            java.lang.SecurityException r5 = new java.lang.SecurityException     // Catch: java.lang.Throwable -> L93
            java.lang.String r6 = "Can't install packages while in secure FRP"
            r5.<init>(r6)     // Catch: java.lang.Throwable -> L93
            throw r5     // Catch: java.lang.Throwable -> L93
        L5c:
            if (r7 == 0) goto L75
            android.content.Context r7 = r5.mContext     // Catch: java.lang.Throwable -> L93
            java.lang.String r3 = "android.permission.INSTALL_PACKAGES"
            r4 = 0
            r7.enforceCallingOrSelfPermission(r3, r4)     // Catch: java.lang.Throwable -> L93
            int r7 = r5.mInstallerUid     // Catch: java.lang.Throwable -> L93
            int r3 = r5.mOriginalInstallerUid     // Catch: java.lang.Throwable -> L93
            if (r7 == r3) goto L6d
            goto L7b
        L6d:
            java.lang.IllegalArgumentException r5 = new java.lang.IllegalArgumentException     // Catch: java.lang.Throwable -> L93
            java.lang.String r6 = "Session has not been transferred"
            r5.<init>(r6)     // Catch: java.lang.Throwable -> L93
            throw r5     // Catch: java.lang.Throwable -> L93
        L75:
            int r7 = r5.mInstallerUid     // Catch: java.lang.Throwable -> L93
            int r3 = r5.mOriginalInstallerUid     // Catch: java.lang.Throwable -> L93
            if (r7 != r3) goto L8b
        L7b:
            r5.setRemoteStatusReceiver(r6)     // Catch: java.lang.Throwable -> L93
            boolean r6 = r5.mSealed     // Catch: java.lang.Throwable -> L93
            if (r6 == 0) goto L84
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L93
            return r1
        L84:
            r5.sealLocked()     // Catch: com.android.server.pm.PackageManagerException -> L89 java.lang.Throwable -> L93
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L93
            return r1
        L89:
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L93
            return r0
        L8b:
            java.lang.IllegalArgumentException r5 = new java.lang.IllegalArgumentException     // Catch: java.lang.Throwable -> L93
            java.lang.String r6 = "Session has been transferred"
            r5.<init>(r6)     // Catch: java.lang.Throwable -> L93
            throw r5     // Catch: java.lang.Throwable -> L93
        L93:
            r5 = move-exception
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L93
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.PackageInstallerSession.markAsSealed(android.content.IntentSender, boolean):boolean");
    }

    public final boolean streamValidateAndCommit() {
        try {
            synchronized (this.mLock) {
                if (isCommitted()) {
                    return true;
                }
                if (!this.params.isMultiPackage) {
                    if (PMRune.PM_INSTALL_TO_SDCARD) {
                        resolveStageDirLocked();
                    }
                    if (!prepareDataLoaderLocked()) {
                        return false;
                    }
                    if (isApexSession()) {
                        validateApexInstallLocked();
                    } else {
                        validateApkInstallLocked();
                    }
                }
                if (this.mDestroyed) {
                    throw new PackageManagerException(-110, "Session destroyed");
                }
                if (!isIncrementalInstallation()) {
                    synchronized (this.mProgressLock) {
                        this.mClientProgress = 1.0f;
                        computeProgressLocked(true);
                    }
                }
                this.mActiveCount.incrementAndGet();
                if (!this.mCommitted.compareAndSet(false, true)) {
                    throw new PackageManagerException(-110, TextUtils.formatSimple("The mCommitted of session %d should be false originally", new Object[]{Integer.valueOf(this.sessionId)}));
                }
                this.committedMillis = System.currentTimeMillis();
                return true;
            }
        } catch (PackageManagerException e) {
            throw e;
        } catch (Throwable th) {
            throw new PackageManagerException(th);
        }
    }

    public final List getChildSessionsLocked() {
        List list = Collections.EMPTY_LIST;
        if (!isMultiPackage()) {
            return list;
        }
        int size = this.mChildSessions.size();
        ArrayList arrayList = new ArrayList(size);
        for (int i = 0; i < size; i++) {
            arrayList.add((PackageInstallerSession) this.mChildSessions.valueAt(i));
        }
        return arrayList;
    }

    public List getChildSessions() {
        List childSessionsLocked;
        synchronized (this.mLock) {
            childSessionsLocked = getChildSessionsLocked();
        }
        return childSessionsLocked;
    }

    public final List getSelfOrChildSessions() {
        return isMultiPackage() ? getChildSessions() : Collections.singletonList(this);
    }

    public final void sealLocked() {
        try {
            assertNoWriteFileTransfersOpenLocked();
            assertPreparedAndNotDestroyedLocked("sealing of session " + this.sessionId);
            this.mSealed = true;
        } catch (Throwable th) {
            throw onSessionValidationFailure(new PackageManagerException(th));
        }
    }

    public final PackageManagerException onSessionValidationFailure(PackageManagerException packageManagerException) {
        onSessionValidationFailure(packageManagerException.error, ExceptionUtils.getCompleteMessage(packageManagerException));
        return packageManagerException;
    }

    public final void onSessionValidationFailure(int i, String str) {
        destroyInternal();
        dispatchSessionFinished(i, str, null);
    }

    public final void onSessionVerificationFailure(int i, String str) {
        Slog.e("PackageInstallerSession", "Failed to verify session " + this.sessionId);
        dispatchSessionFinished(i, str, null);
        maybeFinishChildSessions(i, str);
    }

    public final void onSystemDataLoaderUnrecoverable() {
        final DeletePackageHelper deletePackageHelper = new DeletePackageHelper(this.mPm);
        final String packageName = getPackageName();
        if (TextUtils.isEmpty(packageName)) {
            return;
        }
        this.mHandler.post(new Runnable() { // from class: com.android.server.pm.PackageInstallerSession$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                PackageInstallerSession.lambda$onSystemDataLoaderUnrecoverable$1(DeletePackageHelper.this, packageName);
            }
        });
    }

    public static /* synthetic */ void lambda$onSystemDataLoaderUnrecoverable$1(DeletePackageHelper deletePackageHelper, String str) {
        if (deletePackageHelper.deletePackageX(str, -1L, 0, 2, true) != 1) {
            Slog.e("PackageInstallerSession", "Failed to uninstall package with failed dataloader: " + str);
        }
    }

    public void onAfterSessionRead(SparseArray sparseArray) {
        synchronized (this.mLock) {
            for (int size = this.mChildSessions.size() - 1; size >= 0; size--) {
                int keyAt = this.mChildSessions.keyAt(size);
                PackageInstallerSession packageInstallerSession = (PackageInstallerSession) sparseArray.get(keyAt);
                if (packageInstallerSession != null) {
                    this.mChildSessions.setValueAt(size, packageInstallerSession);
                } else {
                    Slog.e("PackageInstallerSession", "Child session not existed: " + keyAt);
                    this.mChildSessions.removeAt(size);
                }
            }
            if (!this.mShouldBeSealed || isStagedAndInTerminalState()) {
                return;
            }
            try {
                sealLocked();
            } catch (PackageManagerException e) {
                Slog.e("PackageInstallerSession", "Package not valid", e);
            }
            if (!isMultiPackage() && isStaged() && isCommitted()) {
                PackageInstallerSession packageInstallerSession2 = hasParentSessionId() ? (PackageInstallerSession) sparseArray.get(getParentSessionId()) : this;
                if (packageInstallerSession2 != null && !packageInstallerSession2.isStagedAndInTerminalState()) {
                    if (isApexSession()) {
                        validateApexInstallLocked();
                    } else {
                        validateApkInstallLocked();
                    }
                }
            }
        }
    }

    public void markUpdated() {
        synchronized (this.mLock) {
            this.updatedMillis = System.currentTimeMillis();
        }
    }

    public void transfer(String str) {
        Preconditions.checkArgument(!TextUtils.isEmpty(str));
        Computer snapshotComputer = this.mPm.snapshotComputer();
        ApplicationInfo applicationInfo = snapshotComputer.getApplicationInfo(str, 0L, this.userId);
        if (applicationInfo == null) {
            throw new ParcelableException(new PackageManager.NameNotFoundException(str));
        }
        if (snapshotComputer.checkUidPermission("android.permission.INSTALL_PACKAGES", applicationInfo.uid) != 0) {
            throw new SecurityException("Destination package " + str + " does not have the android.permission.INSTALL_PACKAGES permission");
        }
        if (!this.params.areHiddenOptionsSet()) {
            throw new SecurityException("Can only transfer sessions that use public options");
        }
        synchronized (this.mLock) {
            assertCallerIsOwnerOrRoot();
            assertPreparedAndNotSealedLocked("transfer");
            try {
                sealLocked();
                this.mInstallerUid = applicationInfo.uid;
                this.mInstallSource = InstallSource.create(str, null, str, this.mInstallerUid, str, null, this.params.packageSource);
            } catch (PackageManagerException e) {
                throw new IllegalStateException("Package is not valid", e);
            }
        }
    }

    public static boolean checkUserActionRequirement(PackageInstallerSession packageInstallerSession, IntentSender intentSender) {
        if (packageInstallerSession.isMultiPackage()) {
            return false;
        }
        int computeUserActionRequirement = packageInstallerSession.computeUserActionRequirement();
        packageInstallerSession.updateUserActionRequirement(computeUserActionRequirement);
        if (computeUserActionRequirement == 1 || computeUserActionRequirement == 3) {
            packageInstallerSession.sendPendingUserActionIntent(intentSender);
            return true;
        }
        if (!packageInstallerSession.isApexSession() && computeUserActionRequirement == 2) {
            if (!isTargetSdkConditionSatisfied(packageInstallerSession)) {
                packageInstallerSession.sendPendingUserActionIntent(intentSender);
                return true;
            }
            if (packageInstallerSession.params.requireUserAction == 2) {
                if (!packageInstallerSession.mSilentUpdatePolicy.isSilentUpdateAllowed(packageInstallerSession.getInstallerPackageName(), packageInstallerSession.getPackageName())) {
                    packageInstallerSession.sendPendingUserActionIntent(intentSender);
                    return true;
                }
                packageInstallerSession.mSilentUpdatePolicy.track(packageInstallerSession.getInstallerPackageName(), packageInstallerSession.getPackageName());
            }
        }
        return false;
    }

    public static boolean isTargetSdkConditionSatisfied(PackageInstallerSession packageInstallerSession) {
        int i;
        String str;
        synchronized (packageInstallerSession.mLock) {
            i = packageInstallerSession.mValidatedTargetSdk;
            str = packageInstallerSession.mPackageName;
        }
        ApplicationInfo applicationInfo = new ApplicationInfo();
        applicationInfo.packageName = str;
        applicationInfo.targetSdkVersion = i;
        IPlatformCompat asInterface = IPlatformCompat.Stub.asInterface(ServiceManager.getService("platform_compat"));
        if (i == Integer.MAX_VALUE) {
            return false;
        }
        try {
            return asInterface.isChangeEnabled(265131695L, applicationInfo);
        } catch (RemoteException e) {
            Log.e("PackageInstallerSession", "Failed to get a response from PLATFORM_COMPAT_SERVICE", e);
            return false;
        }
    }

    public final boolean sendPendingUserActionIntentIfNeeded() {
        if (isCommitted()) {
            assertNotChild("PackageInstallerSession#sendPendingUserActionIntentIfNeeded");
        }
        final IntentSender remoteStatusReceiver = getRemoteStatusReceiver();
        return sessionContains(new Predicate() { // from class: com.android.server.pm.PackageInstallerSession$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$sendPendingUserActionIntentIfNeeded$2;
                lambda$sendPendingUserActionIntentIfNeeded$2 = PackageInstallerSession.lambda$sendPendingUserActionIntentIfNeeded$2(remoteStatusReceiver, (PackageInstallerSession) obj);
                return lambda$sendPendingUserActionIntentIfNeeded$2;
            }
        });
    }

    public static /* synthetic */ boolean lambda$sendPendingUserActionIntentIfNeeded$2(IntentSender intentSender, PackageInstallerSession packageInstallerSession) {
        return checkUserActionRequirement(packageInstallerSession, intentSender);
    }

    public void setUnknownSourceConfirmResult(boolean z) {
        Slog.d("PackageInstallerSession", "setUnknownSourceConfirmResult, sessionid: " + this.sessionId + ", accepted: " + z);
        if (!isSealed()) {
            throw new SecurityException("Must be sealed to accept permissions");
        }
        if (z) {
            synchronized (this.mLock) {
                this.mUnknownSourceInstallAccepted.compareAndSet(false, true);
                this.mHandler.obtainMessage(3).sendToTarget();
            }
            return;
        }
        destroyInternal();
        dispatchSessionFinished(-115, "User rejected installing unknown source package", null);
    }

    public final void handleInstall() {
        if (SemSamsungThemeUtils.isThemeCenter(getInstallerPackageName()) && getInstallerUid() != 1000) {
            destroyInternal();
            dispatchSessionFinished(-110, "Install failed with internal error", null);
            Slog.e("PackageInstallerSession", "Install failed with internal error");
        }
        if (isInstallerDeviceOwnerOrAffiliatedProfileOwner()) {
            DevicePolicyEventLogger.createEvent(112).setAdmin(getInstallSource().mInstallerPackageName).write();
        }
        boolean sendPendingUserActionIntentIfNeeded = sendPendingUserActionIntentIfNeeded();
        if (this.mUserActionRequired == null) {
            this.mUserActionRequired = Boolean.valueOf(sendPendingUserActionIntentIfNeeded);
        }
        if (sendPendingUserActionIntentIfNeeded) {
            deactivate();
            return;
        }
        if (this.mUserActionRequired.booleanValue()) {
            activate();
        }
        if (this.mVerificationInProgress) {
            Slog.w("PackageInstallerSession", "Verification is already in progress for session " + this.sessionId);
            return;
        }
        this.mVerificationInProgress = true;
        if (this.params.isStaged) {
            this.mStagedSession.verifySession();
        } else {
            verify();
        }
    }

    public final void verify() {
        try {
            List<PackageInstallerSession> childSessions = getChildSessions();
            if (isMultiPackage()) {
                for (PackageInstallerSession packageInstallerSession : childSessions) {
                    packageInstallerSession.prepareInheritedFiles();
                    packageInstallerSession.parseApkAndExtractNativeLibraries();
                }
            } else {
                prepareInheritedFiles();
                parseApkAndExtractNativeLibraries();
            }
            verifyNonStaged();
        } catch (PackageManagerException e) {
            String installStatusToString = PackageManager.installStatusToString(e.error, ExceptionUtils.getCompleteMessage(e));
            setSessionFailed(e.error, installStatusToString);
            onSessionVerificationFailure(e.error, installStatusToString);
        }
    }

    public final IntentSender getRemoteStatusReceiver() {
        IntentSender intentSender;
        synchronized (this.mLock) {
            intentSender = this.mRemoteStatusReceiver;
        }
        return intentSender;
    }

    public final void setRemoteStatusReceiver(IntentSender intentSender) {
        synchronized (this.mLock) {
            this.mRemoteStatusReceiver = intentSender;
        }
    }

    public final void prepareInheritedFiles() {
        if (isApexSession() || this.params.mode != 2) {
            return;
        }
        synchronized (this.mLock) {
            if (this.mStageDirInUse) {
                throw new PackageManagerException(-110, "Session files in use");
            }
            if (this.mDestroyed) {
                throw new PackageManagerException(-110, "Session destroyed");
            }
            if (!this.mSealed) {
                throw new PackageManagerException(-110, "Session not sealed");
            }
            if (PMRune.PM_INSTALL_TO_SDCARD && this.stageCid != null) {
                long calculateInstalledSize = calculateInstalledSize();
                Slog.w("PackageInstallerSession", "Final Size " + calculateInstalledSize);
                resizeContainerForSd(this.stageCid, calculateInstalledSize);
            }
            if (PMRune.PM_INSTALL_TO_SDCARD) {
                try {
                    resolveStageDirLocked();
                } catch (IOException e) {
                    throw new PackageManagerException(-18, "Failed to resolve stage location", e);
                }
            }
            try {
                List list = this.mResolvedInheritedFiles;
                File file = PMRune.PM_INSTALL_TO_SDCARD ? this.mResolvedStageDir : this.stageDir;
                String name = file.getName();
                Slog.d("PackageInstallerSession", "Inherited files: " + this.mResolvedInheritedFiles);
                if (!this.mResolvedInheritedFiles.isEmpty() && this.mInheritedFilesBase == null) {
                    throw new IllegalStateException("mInheritedFilesBase == null");
                }
                if (isLinkPossible(list, file)) {
                    if (!this.mResolvedInstructionSets.isEmpty()) {
                        createOatDirs(name, this.mResolvedInstructionSets, new File(file, "oat"));
                    }
                    if (!this.mResolvedNativeLibPaths.isEmpty()) {
                        for (String str : this.mResolvedNativeLibPaths) {
                            int lastIndexOf = str.lastIndexOf(47);
                            if (lastIndexOf >= 0 && lastIndexOf < str.length() - 1) {
                                File file2 = new File(file, str.substring(1, lastIndexOf));
                                if (!file2.exists()) {
                                    NativeLibraryHelper.createNativeLibrarySubdir(file2);
                                }
                                NativeLibraryHelper.createNativeLibrarySubdir(new File(file2, str.substring(lastIndexOf + 1)));
                            }
                            Slog.e("PackageInstallerSession", "Skipping native library creation for linking due to invalid path: " + str);
                        }
                    }
                    linkFiles(name, list, file, this.mInheritedFilesBase);
                } else {
                    copyFiles(list, file);
                }
            } catch (IOException e2) {
                throw new PackageManagerException(-4, "Failed to inherit existing install", e2);
            }
        }
    }

    public final void markStageDirInUseLocked() {
        if (this.mDestroyed) {
            throw new PackageManagerException(-110, "Session destroyed");
        }
        this.mStageDirInUse = true;
    }

    public final void parseApkAndExtractNativeLibraries() {
        PackageLite orParsePackageLiteLocked;
        synchronized (this.mLock) {
            if (this.mStageDirInUse) {
                throw new PackageManagerException(-110, "Session files in use");
            }
            if (this.mDestroyed) {
                throw new PackageManagerException(-110, "Session destroyed");
            }
            if (!this.mSealed) {
                throw new PackageManagerException(-110, "Session not sealed");
            }
            if (this.mPackageName == null) {
                throw new PackageManagerException(-110, "Session no package name");
            }
            if (this.mSigningDetails == null) {
                throw new PackageManagerException(-110, "Session no signing data");
            }
            if (this.mResolvedBaseFile == null) {
                throw new PackageManagerException(-110, "Session no resolved base file");
            }
            if (!isApexSession()) {
                if (PMRune.PM_INSTALL_TO_SDCARD && this.stageCid != null && this.params.mode != 2) {
                    long calculateInstalledSize = calculateInstalledSize();
                    Slog.w("PackageInstallerSession", "Final Size " + calculateInstalledSize);
                    resizeContainerForSd(this.stageCid, calculateInstalledSize);
                }
                if (PMRune.PM_INSTALL_TO_SDCARD) {
                    try {
                        resolveStageDirLocked();
                    } catch (IOException e) {
                        throw new PackageManagerException(-18, "Failed to resolve stage location", e);
                    }
                }
                orParsePackageLiteLocked = getOrParsePackageLiteLocked(PMRune.PM_INSTALL_TO_SDCARD ? this.mResolvedStageDir : this.stageDir, 0);
            } else {
                orParsePackageLiteLocked = getOrParsePackageLiteLocked(this.mResolvedBaseFile, 0);
            }
            if (orParsePackageLiteLocked != null) {
                this.mPackageLite = orParsePackageLiteLocked;
                if (!isApexSession()) {
                    synchronized (this.mProgressLock) {
                        this.mInternalProgress = 0.5f;
                        computeProgressLocked(true);
                    }
                    if (PMRune.PM_INSTALL_TO_SDCARD) {
                        try {
                            resolveStageDirLocked();
                        } catch (IOException e2) {
                            throw new PackageManagerException(-18, "Failed to resolve stage location", e2);
                        }
                    }
                    extractNativeLibraries(this.mPackageLite, PMRune.PM_INSTALL_TO_SDCARD ? this.mResolvedStageDir : this.stageDir, this.params.abiOverride, mayInheritNativeLibs());
                    String str = this.stageCid;
                    if (str != null) {
                        finalizeAndFixContainer(str);
                    }
                }
            }
        }
    }

    public final void verifyNonStaged() {
        UnknownSourceAppManager unknownSourceAppManager = this.mPm.mCustomInjector.getUnknownSourceAppManager();
        if (!this.mUnknownSourceInstallAccepted.get() ? unknownSourceAppManager.needUnknownSourceConfirmForStore(this.mInstallSource.mInitiatingPackageName, this.mOriginalInstallerUid) : false) {
            SigningDetails signingDetails = this.mSigningDetails;
            Signature[] signatures = (signingDetails == null || signingDetails.getSignatures() == null) ? null : this.mSigningDetails.getSignatures();
            File file = this.mResolvedBaseFile;
            PackageInstaller.SessionParams sessionParams = this.params;
            InstallSource installSource = this.mInstallSource;
            int checkUnknownSourcePackage = unknownSourceAppManager.checkUnknownSourcePackage(file, sessionParams, installSource.mInitiatingPackageName, installSource.mOriginatingPackageName, installSource.mInstallerPackageName, signatures, UserHandle.getUserId(this.mInstallerUid));
            if (checkUnknownSourcePackage == 130) {
                this.mVerificationInProgress = false;
                destroyInternal();
                dispatchSessionFinished(-115, "Self update is blocked by unknown source package", null);
                return;
            } else if (checkUnknownSourcePackage != 0) {
                unknownSourceAppManager.startUnknownSourceConfirmDialog(this.mContext, this.sessionId, checkUnknownSourcePackage, this.userId);
                this.mVerificationInProgress = false;
                return;
            }
        }
        synchronized (this.mLock) {
            markStageDirInUseLocked();
        }
        this.mSessionProvider.getSessionVerifier().verify(this, new PackageSessionVerifier.Callback() { // from class: com.android.server.pm.PackageInstallerSession$$ExternalSyntheticLambda5
            @Override // com.android.server.pm.PackageSessionVerifier.Callback
            public final void onResult(int i, String str) {
                PackageInstallerSession.this.lambda$verifyNonStaged$4(i, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$verifyNonStaged$4(final int i, final String str) {
        this.mHandler.post(new Runnable() { // from class: com.android.server.pm.PackageInstallerSession$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                PackageInstallerSession.this.lambda$verifyNonStaged$3(i, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$verifyNonStaged$3(int i, String str) {
        if (dispatchPendingAbandonCallback()) {
            return;
        }
        if (i == 1) {
            onVerificationComplete();
        } else {
            onSessionVerificationFailure(i, str);
        }
    }

    /* loaded from: classes3.dex */
    public class InstallResult {
        public final Bundle extras;
        public final PackageInstallerSession session;

        public InstallResult(PackageInstallerSession packageInstallerSession, Bundle bundle) {
            this.session = packageInstallerSession;
            this.extras = bundle;
        }
    }

    public final CompletableFuture install() {
        final List installNonStaged = installNonStaged();
        return CompletableFuture.allOf((CompletableFuture[]) installNonStaged.toArray(new CompletableFuture[installNonStaged.size()])).whenComplete(new BiConsumer() { // from class: com.android.server.pm.PackageInstallerSession$$ExternalSyntheticLambda10
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                PackageInstallerSession.this.lambda$install$5(installNonStaged, (Void) obj, (Throwable) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$install$5(List list, Void r3, Throwable th) {
        if (th == null) {
            setSessionApplied();
            Iterator it = list.iterator();
            while (it.hasNext()) {
                InstallResult installResult = (InstallResult) ((CompletableFuture) it.next()).join();
                installResult.session.dispatchSessionFinished(1, "Session installed", installResult.extras);
            }
            return;
        }
        PackageManagerException packageManagerException = (PackageManagerException) th.getCause();
        int i = packageManagerException.error;
        setSessionFailed(i, PackageManager.installStatusToString(i, packageManagerException.getMessage()));
        dispatchSessionFinished(packageManagerException.error, packageManagerException.getMessage(), null);
        maybeFinishChildSessions(packageManagerException.error, packageManagerException.getMessage());
    }

    public final List installNonStaged() {
        try {
            ArrayList arrayList = new ArrayList();
            CompletableFuture completableFuture = new CompletableFuture();
            arrayList.add(completableFuture);
            InstallingSession createInstallingSession = createInstallingSession(completableFuture);
            if (isMultiPackage()) {
                List childSessions = getChildSessions();
                ArrayList arrayList2 = new ArrayList(childSessions.size());
                for (int i = 0; i < childSessions.size(); i++) {
                    PackageInstallerSession packageInstallerSession = (PackageInstallerSession) childSessions.get(i);
                    if (packageInstallerSession != null && isAttemptSamsungThemeForgery(packageInstallerSession.getPackageName())) {
                        throw new PackageManagerException(-110, "Install failed with internal error");
                    }
                    CompletableFuture completableFuture2 = new CompletableFuture();
                    arrayList.add(completableFuture2);
                    InstallingSession createInstallingSession2 = packageInstallerSession.createInstallingSession(completableFuture2);
                    if (createInstallingSession2 != null) {
                        arrayList2.add(createInstallingSession2);
                    }
                }
                if (!arrayList2.isEmpty()) {
                    Objects.requireNonNull(createInstallingSession);
                    createInstallingSession.installStage(arrayList2);
                }
            } else if (createInstallingSession != null) {
                PackageLite packageLite = createInstallingSession.mPackageLite;
                if (packageLite != null && isAttemptSamsungThemeForgery(packageLite.getPackageName())) {
                    throw new PackageManagerException(-110, "Install failed with internal error");
                }
                createInstallingSession.installStage();
            }
            return arrayList;
        } catch (PackageManagerException e) {
            ArrayList arrayList3 = new ArrayList();
            arrayList3.add(CompletableFuture.failedFuture(e));
            return arrayList3;
        }
    }

    public final void sendPendingUserActionIntent(IntentSender intentSender) {
        String str;
        Intent intent = new Intent(isPreapprovalRequested() && !isCommitted() ? "android.content.pm.action.CONFIRM_PRE_APPROVAL" : "android.content.pm.action.CONFIRM_INSTALL");
        intent.setPackage(this.mPm.getPackageInstallerPackageName());
        intent.putExtra("android.content.pm.extra.SESSION_ID", this.sessionId);
        if (TextUtils.isEmpty(this.mUserActionRequirementMsg)) {
            str = "";
        } else {
            str = " [" + this.mUserActionRequirementMsg + "]";
        }
        Slog.i("PackageInstallerSession", "status of session: pending{" + this.sessionId + "}, User action required" + str);
        this.mUserActionRequirementMsg = null;
        sendOnUserActionRequired(this.mContext, intentSender, this.sessionId, intent);
    }

    public final void onVerificationComplete() {
        if (isStaged()) {
            this.mStagingManager.commitSession(this.mStagedSession);
            sendUpdateToRemoteStatusReceiver(1, "Session staged", null);
        } else {
            install();
        }
    }

    public final InstallingSession createInstallingSession(final CompletableFuture completableFuture) {
        boolean z;
        UserHandle userHandle;
        InstallingSession installingSession;
        synchronized (this.mLock) {
            if (!this.mSealed) {
                throw new PackageManagerException(-110, "Session not sealed");
            }
            markStageDirInUseLocked();
        }
        if (isMultiPackage()) {
            completableFuture.complete(new InstallResult(this, null));
        } else if (isApexSession() && this.params.isStaged) {
            completableFuture.complete(new InstallResult(this, null));
            return null;
        }
        IPackageInstallObserver2.Stub stub = new IPackageInstallObserver2.Stub() { // from class: com.android.server.pm.PackageInstallerSession.5
            public void onUserActionRequired(Intent intent) {
                throw new IllegalStateException();
            }

            public void onPackageInstalled(String str, int i, String str2, Bundle bundle) {
                if (i == 1) {
                    completableFuture.complete(new InstallResult(PackageInstallerSession.this, bundle));
                } else {
                    completableFuture.completeExceptionally(new PackageManagerException(i, str2));
                }
            }
        };
        if (this.mInstallerUid != 2000 || !MaintenanceModeManager.isInMaintenanceModeFromProperty() || MaintenanceModeManager.isPlatformSigned(this.mPm.getPlatformPackage().getSigningDetails(), this.mSigningDetails)) {
            z = false;
        } else {
            if ((this.params.installFlags & 64) == 0 && this.userId != 77) {
                throw new PackageManagerException(-110, "Not allowed to install apps on user " + this.userId);
            }
            Slog.i("PackageInstallerSession", "It will be installed only on Maintenance mode user");
            this.params.installFlags &= -65;
            z = true;
        }
        if ((this.params.installFlags & 64) != 0) {
            userHandle = UserHandle.ALL;
        } else if (z) {
            userHandle = new UserHandle(77);
        } else {
            userHandle = new UserHandle(this.userId);
        }
        UserHandle userHandle2 = userHandle;
        PackageInstaller.SessionParams sessionParams = this.params;
        if (sessionParams.isStaged) {
            sessionParams.installFlags |= 2097152;
        }
        if (!isMultiPackage() && !isApexSession()) {
            synchronized (this.mLock) {
                if (this.mPackageLite == null) {
                    Slog.wtf("PackageInstallerSession", "Session: " + this.sessionId + ". Don't have a valid PackageLite.");
                }
                if (PMRune.PM_INSTALL_TO_SDCARD) {
                    try {
                        resolveStageDirLocked();
                    } catch (IOException e) {
                        throw new PackageManagerException(-18, "Failed to resolve stage location", e);
                    }
                }
                this.mPackageLite = getOrParsePackageLiteLocked(PMRune.PM_INSTALL_TO_SDCARD ? this.mResolvedStageDir : this.stageDir, 0);
            }
        }
        synchronized (this.mLock) {
            installingSession = new InstallingSession(this.sessionId, this.stageDir, this.stageCid, (IPackageInstallObserver2) stub, this.params, this.mInstallSource, userHandle2, this.mSigningDetails, this.mInstallerUid, this.mPackageLite, this.mPm);
            if (this.stageCid != null) {
                installingSession.mIsMoveRequest = true;
            }
            if (PMRune.PM_INSTALL_TO_SDCARD) {
                installingSession.mPreferredInstallLocation = this.params.installLocation;
            }
        }
        return installingSession;
    }

    public final PackageLite getOrParsePackageLiteLocked(File file, int i) {
        PackageLite packageLite = this.mPackageLite;
        if (packageLite != null) {
            return packageLite;
        }
        ParseResult parsePackageLite = ApkLiteParseUtils.parsePackageLite(ParseTypeImpl.forDefaultParsing(), file, i);
        if (parsePackageLite.isError()) {
            throw new PackageManagerException(-110, parsePackageLite.getErrorMessage(), parsePackageLite.getException());
        }
        return (PackageLite) parsePackageLite.getResult();
    }

    public static void maybeRenameFile(File file, File file2) {
        if (file.equals(file2) || file.renameTo(file2)) {
            return;
        }
        throw new PackageManagerException(-110, "Could not rename file " + file + " to " + file2);
    }

    public final void logDataLoaderInstallationSession(int i) {
        String packageName = getPackageName();
        String str = (this.params.installFlags & 32) == 0 ? packageName : "";
        long currentTimeMillis = System.currentTimeMillis();
        FrameworkStatsLog.write(263, isIncrementalInstallation(), str, currentTimeMillis - this.createdMillis, i, getApksSize(packageName), i != 1 ? -1 : this.mPm.snapshotComputer().getPackageUid(packageName, 0L, this.userId));
    }

    public final long getApksSize(String str) {
        File path;
        PackageStateInternal packageStateInternal = ((PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class)).getPackageStateInternal(str);
        long j = 0;
        if (packageStateInternal == null || (path = packageStateInternal.getPath()) == null) {
            return 0L;
        }
        if (path.isFile() && path.getName().toLowerCase().endsWith(".apk")) {
            return path.length();
        }
        if (!path.isDirectory()) {
            return 0L;
        }
        File[] listFiles = path.listFiles();
        for (int i = 0; i < listFiles.length; i++) {
            if (listFiles[i].getName().toLowerCase().endsWith(".apk")) {
                j += listFiles[i].length();
            }
        }
        return j;
    }

    public final boolean mayInheritNativeLibs() {
        if (SystemProperties.getBoolean("pi.inherit_native_on_dont_kill", true)) {
            PackageInstaller.SessionParams sessionParams = this.params;
            if (sessionParams.mode == 2 && (sessionParams.installFlags & 1) != 0) {
                return true;
            }
        }
        return false;
    }

    public boolean isApexSession() {
        return (this.params.installFlags & IInstalld.FLAG_CLEAR_APP_DATA_KEEP_ART_PROFILES) != 0;
    }

    public boolean sessionContains(Predicate predicate) {
        List childSessionsLocked;
        if (!isMultiPackage()) {
            return predicate.test(this);
        }
        synchronized (this.mLock) {
            childSessionsLocked = getChildSessionsLocked();
        }
        Iterator it = childSessionsLocked.iterator();
        while (it.hasNext()) {
            if (predicate.test((PackageInstallerSession) it.next())) {
                return true;
            }
        }
        return false;
    }

    public static /* synthetic */ boolean lambda$containsApkSession$6(PackageInstallerSession packageInstallerSession) {
        return !packageInstallerSession.isApexSession();
    }

    public boolean containsApkSession() {
        return sessionContains(new Predicate() { // from class: com.android.server.pm.PackageInstallerSession$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$containsApkSession$6;
                lambda$containsApkSession$6 = PackageInstallerSession.lambda$containsApkSession$6((PackageInstallerSession) obj);
                return lambda$containsApkSession$6;
            }
        });
    }

    public final void validateApexInstallLocked() {
        List addedApksLocked = getAddedApksLocked();
        if (addedApksLocked.isEmpty()) {
            throw new PackageManagerException(-2, TextUtils.formatSimple("Session: %d. No packages staged in %s", new Object[]{Integer.valueOf(this.sessionId), this.stageDir.getAbsolutePath()}));
        }
        if (ArrayUtils.size(addedApksLocked) > 1) {
            throw new PackageManagerException(-2, "Too many files for apex install");
        }
        File file = (File) addedApksLocked.get(0);
        String name = file.getName();
        if (!name.endsWith(".apex")) {
            name = name + ".apex";
        }
        if (!FileUtils.isValidExtFilename(name)) {
            throw new PackageManagerException(-2, "Invalid filename: " + name);
        }
        File file2 = new File(this.stageDir, name);
        resolveAndStageFileLocked(file, file2, null);
        this.mResolvedBaseFile = file2;
        this.mPackageName = null;
        ParseResult parseApkLite = ApkLiteParseUtils.parseApkLite(ParseTypeImpl.forDefaultParsing().reset(), this.mResolvedBaseFile, 32);
        if (parseApkLite.isError()) {
            throw new PackageManagerException(parseApkLite.getErrorCode(), parseApkLite.getErrorMessage(), parseApkLite.getException());
        }
        ApkLite apkLite = (ApkLite) parseApkLite.getResult();
        if (this.mPackageName == null) {
            this.mPackageName = apkLite.getPackageName();
            this.mVersionCode = apkLite.getLongVersionCode();
        }
        this.mSigningDetails = apkLite.getSigningDetails();
        this.mHasDeviceAdminReceiver = apkLite.isHasDeviceAdminReceiver();
    }

    /* JADX WARN: Removed duplicated region for block: B:202:0x0428  */
    /* JADX WARN: Removed duplicated region for block: B:222:0x0493  */
    /* JADX WARN: Removed duplicated region for block: B:242:0x04e9  */
    /* JADX WARN: Removed duplicated region for block: B:288:0x0589  */
    /* JADX WARN: Removed duplicated region for block: B:307:0x05d5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.content.pm.parsing.PackageLite validateApkInstallLocked() {
        /*
            Method dump skipped, instructions count: 1676
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.PackageInstallerSession.validateApkInstallLocked():android.content.pm.parsing.PackageLite");
    }

    public final void stageFileLocked(File file, File file2) {
        this.mResolvedStagedFiles.add(file2);
        maybeRenameFile(file, file2);
    }

    public final void maybeStageFsveritySignatureLocked(File file, File file2, boolean z) {
        File file3 = new File(VerityUtils.getFsveritySignatureFilePath(file.getPath()));
        if (file3.exists()) {
            stageFileLocked(file3, new File(VerityUtils.getFsveritySignatureFilePath(file2.getPath())));
        } else if (z) {
            throw new PackageManagerException(-118, "Missing corresponding fs-verity signature to " + file);
        }
    }

    public final void maybeStageDexMetadataLocked(File file, File file2) {
        File findDexMetadataForFile = DexMetadataHelper.findDexMetadataForFile(file);
        if (findDexMetadataForFile == null) {
            return;
        }
        if (!FileUtils.isValidExtFilename(findDexMetadataForFile.getName())) {
            throw new PackageManagerException(-2, "Invalid filename: " + findDexMetadataForFile);
        }
        if (PMRune.PM_INSTALL_TO_SDCARD) {
            try {
                resolveStageDirLocked();
            } catch (IOException e) {
                throw new PackageManagerException(-18, "Failed to resolve stage location", e);
            }
        }
        File file3 = new File(PMRune.PM_INSTALL_TO_SDCARD ? this.mResolvedStageDir : this.stageDir, DexMetadataHelper.buildDexMetadataPathForApk(file2.getName()));
        stageFileLocked(findDexMetadataForFile, file3);
        maybeStageFsveritySignatureLocked(findDexMetadataForFile, file3, DexMetadataHelper.isFsVerityRequired());
    }

    public final IncrementalFileStorages getIncrementalFileStorages() {
        IncrementalFileStorages incrementalFileStorages;
        synchronized (this.mLock) {
            incrementalFileStorages = this.mIncrementalFileStorages;
        }
        return incrementalFileStorages;
    }

    public final void storeBytesToInstallationFile(String str, String str2, byte[] bArr) {
        IncrementalFileStorages incrementalFileStorages = getIncrementalFileStorages();
        if (!isIncrementalInstallation() || incrementalFileStorages == null) {
            FileUtils.bytesToFile(str2, bArr);
        } else {
            incrementalFileStorages.makeFile(str, bArr, 511);
        }
    }

    public final void maybeStageDigestsLocked(File file, File file2, String str) {
        PerFileChecksum perFileChecksum = (PerFileChecksum) this.mChecksums.get(file.getName());
        if (perFileChecksum == null) {
            return;
        }
        this.mChecksums.remove(file.getName());
        Checksum[] checksums = perFileChecksum.getChecksums();
        if (checksums.length == 0) {
            return;
        }
        if (PMRune.PM_INSTALL_TO_SDCARD) {
            try {
                resolveStageDirLocked();
            } catch (IOException e) {
                throw new PackageManagerException(-18, "Failed to resolve stage location", e);
            }
        }
        String buildDigestsPathForApk = ApkChecksums.buildDigestsPathForApk(file2.getName());
        File file3 = new File(PMRune.PM_INSTALL_TO_SDCARD ? this.mResolvedStageDir : this.stageDir, buildDigestsPathForApk);
        try {
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                try {
                    ApkChecksums.writeChecksums(byteArrayOutputStream, checksums);
                    byte[] signature = perFileChecksum.getSignature();
                    if (signature != null && signature.length > 0) {
                        ApkChecksums.verifySignature(checksums, signature);
                    }
                    storeBytesToInstallationFile(buildDigestsPathForApk, file3.getAbsolutePath(), byteArrayOutputStream.toByteArray());
                    stageFileLocked(file3, file3);
                    if (signature != null && signature.length != 0) {
                        String buildSignaturePathForDigests = ApkChecksums.buildSignaturePathForDigests(buildDigestsPathForApk);
                        File file4 = new File(PMRune.PM_INSTALL_TO_SDCARD ? this.mResolvedStageDir : this.stageDir, buildSignaturePathForDigests);
                        storeBytesToInstallationFile(buildSignaturePathForDigests, file4.getAbsolutePath(), signature);
                        stageFileLocked(file4, file4);
                        byteArrayOutputStream.close();
                        return;
                    }
                    byteArrayOutputStream.close();
                } catch (Throwable th) {
                    try {
                        byteArrayOutputStream.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            } catch (IOException e2) {
                throw new PackageManagerException(-4, "Failed to store digests for " + this.mPackageName, e2);
            }
        } catch (NoSuchAlgorithmException | SignatureException e3) {
            throw new PackageManagerException(-103, "Failed to verify digests' signature for " + this.mPackageName, e3);
        }
    }

    public final boolean isFsVerityRequiredForApk(File file, File file2) {
        if (this.mVerityFoundForApks) {
            return true;
        }
        if (!new File(VerityUtils.getFsveritySignatureFilePath(file.getPath())).exists()) {
            return false;
        }
        this.mVerityFoundForApks = true;
        for (File file3 : this.mResolvedStagedFiles) {
            if (file3.getName().endsWith(".apk") && !file2.getName().equals(file3.getName())) {
                throw new PackageManagerException(-118, "Previously staged apk is missing fs-verity signature");
            }
        }
        return true;
    }

    public final void resolveAndStageFileLocked(File file, File file2, String str) {
        stageFileLocked(file, file2);
        maybeStageFsveritySignatureLocked(file, file2, isFsVerityRequiredForApk(file, file2));
        maybeStageDexMetadataLocked(file, file2);
        maybeStageDigestsLocked(file, file2, str);
    }

    public final void maybeInheritFsveritySignatureLocked(File file) {
        File file2 = new File(VerityUtils.getFsveritySignatureFilePath(file.getPath()));
        if (file2.exists()) {
            this.mResolvedInheritedFiles.add(file2);
        }
    }

    public final void inheritFileLocked(File file) {
        this.mResolvedInheritedFiles.add(file);
        maybeInheritFsveritySignatureLocked(file);
        File findDexMetadataForFile = DexMetadataHelper.findDexMetadataForFile(file);
        if (findDexMetadataForFile != null) {
            this.mResolvedInheritedFiles.add(findDexMetadataForFile);
            maybeInheritFsveritySignatureLocked(findDexMetadataForFile);
        }
        File findDigestsForFile = ApkChecksums.findDigestsForFile(file);
        if (findDigestsForFile != null) {
            this.mResolvedInheritedFiles.add(findDigestsForFile);
            File findSignatureForDigests = ApkChecksums.findSignatureForDigests(findDigestsForFile);
            if (findSignatureForDigests != null) {
                this.mResolvedInheritedFiles.add(findSignatureForDigests);
            }
        }
    }

    public final void assertApkConsistentLocked(String str, ApkLite apkLite) {
        assertPackageConsistentLocked(str, apkLite.getPackageName(), apkLite.getLongVersionCode());
        if (this.mSigningDetails.signaturesMatchExactly(apkLite.getSigningDetails())) {
            return;
        }
        throw new PackageManagerException(-2, str + " signatures are inconsistent");
    }

    public final void assertPackageConsistentLocked(String str, String str2, long j) {
        if (!this.mPackageName.equals(str2)) {
            throw new PackageManagerException(-2, str + " package " + str2 + " inconsistent with " + this.mPackageName);
        }
        String str3 = this.params.appPackageName;
        if (str3 != null && !str3.equals(str2)) {
            throw new PackageManagerException(-2, str + " specified package " + this.params.appPackageName + " inconsistent with " + str2);
        }
        if (this.mVersionCode == j) {
            return;
        }
        throw new PackageManagerException(-2, str + " version code " + j + " inconsistent with " + this.mVersionCode);
    }

    public final void assertPreapprovalDetailsConsistentIfNeededLocked(PackageLite packageLite, PackageInfo packageInfo) {
        if (this.mPreapprovalDetails == null || !isPreapprovalRequested()) {
            return;
        }
        if (!TextUtils.equals(this.mPackageName, this.mPreapprovalDetails.getPackageName())) {
            throw new PackageManagerException(-110, this.mPreapprovalDetails + " inconsistent with " + this.mPackageName);
        }
        PackageManager packageManager = this.mContext.getPackageManager();
        if (packageInfo == null) {
            packageInfo = this.mPm.snapshotComputer().getPackageInfo(this.mPackageName, 0L, this.userId);
        }
        CharSequence label = this.mPreapprovalDetails.getLabel();
        if (packageInfo == null || !TextUtils.equals(label, packageManager.getApplicationLabel(packageInfo.applicationInfo))) {
            PackageInfo packageArchiveInfo = packageManager.getPackageArchiveInfo(packageLite.getPath(), PackageManager.PackageInfoFlags.of(0L));
            if (packageArchiveInfo == null) {
                throw new PackageManagerException(-2, "Failure to obtain package info from APK files.");
            }
            List allApkPaths = packageLite.getAllApkPaths();
            ULocale locale = this.mPreapprovalDetails.getLocale();
            ApplicationInfo applicationInfo = packageArchiveInfo.applicationInfo;
            boolean z = false;
            for (int size = allApkPaths.size() - 1; size >= 0 && !z; size--) {
                z |= TextUtils.equals(getAppLabel((String) allApkPaths.get(size), locale, applicationInfo), label);
            }
            if (z) {
                return;
            }
            throw new PackageManagerException(-110, this.mPreapprovalDetails + " inconsistent with app label");
        }
    }

    public final CharSequence getAppLabel(String str, ULocale uLocale, ApplicationInfo applicationInfo) {
        Resources resources = this.mContext.getResources();
        AssetManager assetManager = new AssetManager();
        Configuration configuration = new Configuration(resources.getConfiguration());
        try {
            assetManager.setApkAssets(new ApkAssets[]{ApkAssets.loadFromPath(str)}, false);
            configuration.setLocale(uLocale.toLocale());
            return TextUtils.trimToSize(tryLoadingAppLabel(new Resources(assetManager, resources.getDisplayMetrics(), configuration), applicationInfo), 1000);
        } catch (IOException unused) {
            throw new PackageManagerException(-2, "Failure to get resources from package archive " + str);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:5:0x0014  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.CharSequence tryLoadingAppLabel(android.content.res.Resources r1, android.content.pm.ApplicationInfo r2) {
        /*
            r0 = this;
            int r0 = r2.labelRes
            if (r0 == 0) goto L11
            java.lang.CharSequence r0 = r1.getText(r0)     // Catch: android.content.res.Resources.NotFoundException -> L11
            java.lang.String r0 = r0.toString()     // Catch: android.content.res.Resources.NotFoundException -> L11
            java.lang.String r0 = r0.trim()     // Catch: android.content.res.Resources.NotFoundException -> L11
            goto L12
        L11:
            r0 = 0
        L12:
            if (r0 != 0) goto L1b
            java.lang.CharSequence r0 = r2.nonLocalizedLabel
            if (r0 == 0) goto L19
            goto L1b
        L19:
            java.lang.String r0 = r2.packageName
        L1b:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.PackageInstallerSession.tryLoadingAppLabel(android.content.res.Resources, android.content.pm.ApplicationInfo):java.lang.CharSequence");
    }

    public final SigningDetails unsafeGetCertsWithoutVerification(String str) {
        ParseResult unsafeGetCertsWithoutVerification = ApkSignatureVerifier.unsafeGetCertsWithoutVerification(ParseTypeImpl.forDefaultParsing(), str, 1);
        if (unsafeGetCertsWithoutVerification.isError()) {
            throw new PackageManagerException(-2, "Couldn't obtain signatures from APK : " + str);
        }
        return (SigningDetails) unsafeGetCertsWithoutVerification.getResult();
    }

    public final long calculateInstalledSize() {
        Preconditions.checkNotNull(this.mResolvedBaseFile);
        ParseResult parseApkLite = ApkLiteParseUtils.parseApkLite(ParseTypeImpl.forDefaultParsing().reset(), this.mResolvedBaseFile, 0);
        if (parseApkLite.isError()) {
            throw new PackageManagerException(parseApkLite.getErrorCode(), parseApkLite.getErrorMessage(), parseApkLite.getException());
        }
        ApkLite apkLite = (ApkLite) parseApkLite.getResult();
        ArrayList arrayList = new ArrayList();
        long j = 0;
        for (File file : this.mResolvedStagedFiles) {
            if (!this.mResolvedBaseFile.equals(file)) {
                if (PackageParser.isApkFile(file)) {
                    arrayList.add(file.getAbsolutePath());
                } else {
                    j += file.length();
                }
            }
        }
        long j2 = j;
        for (File file2 : this.mResolvedInheritedFiles) {
            if (!this.mResolvedBaseFile.equals(file2)) {
                if (PackageParser.isApkFile(file2)) {
                    arrayList.add(file2.getAbsolutePath());
                } else {
                    j2 += file2.length();
                }
            }
        }
        long j3 = j2;
        PackageLite packageLite = new PackageLite((String) null, apkLite.getPath(), apkLite, (String[]) null, (boolean[]) null, (String[]) null, (String[]) null, (String[]) arrayList.toArray(new String[arrayList.size()]), (int[]) null, apkLite.getTargetSdkVersion(), (Set[]) null, (Set[]) null);
        try {
            long calculateInstalledSize = InstallLocationUtils.calculateInstalledSize(packageLite, this.params.abiOverride) + j3;
            return this.mPm.getAsecInstallHelper().shouldAddDexOptOnAsec() ? (PackageHelperExt.calculateApksSize(packageLite) * 2) + calculateInstalledSize : calculateInstalledSize;
        } catch (IOException e) {
            throw new PackageManagerException(-2, "Failed to calculate install size", e);
        }
    }

    public static boolean isLinkPossible(List list, File file) {
        try {
            StructStat stat = Os.stat(file.getAbsolutePath());
            Iterator it = list.iterator();
            while (it.hasNext()) {
                if (Os.stat(((File) it.next()).getAbsolutePath()).st_dev != stat.st_dev) {
                    return false;
                }
            }
            return true;
        } catch (ErrnoException e) {
            Slog.w("PackageInstallerSession", "Failed to detect if linking possible: " + e);
            return false;
        }
    }

    public int getInstallerUid() {
        int i;
        synchronized (this.mLock) {
            i = this.mInstallerUid;
        }
        return i;
    }

    public String getPackageName() {
        String str;
        synchronized (this.mLock) {
            str = this.mPackageName;
        }
        return str;
    }

    public long getUpdatedMillis() {
        long j;
        synchronized (this.mLock) {
            j = this.updatedMillis;
        }
        return j;
    }

    public long getCommittedMillis() {
        long j;
        synchronized (this.mLock) {
            j = this.committedMillis;
        }
        return j;
    }

    public String getInstallerPackageName() {
        return getInstallSource().mInstallerPackageName;
    }

    public InstallSource getInstallSource() {
        InstallSource installSource;
        synchronized (this.mLock) {
            installSource = this.mInstallSource;
        }
        return installSource;
    }

    public SigningDetails getSigningDetails() {
        SigningDetails signingDetails;
        synchronized (this.mLock) {
            signingDetails = this.mSigningDetails;
        }
        return signingDetails;
    }

    public PackageLite getPackageLite() {
        PackageLite packageLite;
        synchronized (this.mLock) {
            packageLite = this.mPackageLite;
        }
        return packageLite;
    }

    public boolean getUserActionRequired() {
        Boolean bool = this.mUserActionRequired;
        if (bool != null) {
            return bool.booleanValue();
        }
        Slog.wtf("PackageInstallerSession", "mUserActionRequired should not be null.");
        return false;
    }

    public static String getRelativePath(File file, File file2) {
        String absolutePath = file.getAbsolutePath();
        String absolutePath2 = file2.getAbsolutePath();
        if (absolutePath.contains("/.")) {
            throw new IOException("Invalid path (was relative) : " + absolutePath);
        }
        if (absolutePath.startsWith(absolutePath2)) {
            return absolutePath.substring(absolutePath2.length());
        }
        throw new IOException("File: " + absolutePath + " outside base: " + absolutePath2);
    }

    public final void createOatDirs(String str, List list, File file) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            try {
                this.mInstaller.createOatDir(str, file.getAbsolutePath(), (String) it.next());
            } catch (Installer.InstallerException e) {
                throw PackageManagerException.from(e);
            }
        }
    }

    public final void linkFile(String str, String str2, String str3, String str4) {
        try {
            IncrementalFileStorages incrementalFileStorages = getIncrementalFileStorages();
            if (incrementalFileStorages == null || !incrementalFileStorages.makeLink(str2, str3, str4)) {
                this.mInstaller.linkFile(str, str2, str3, str4);
            }
        } catch (Installer.InstallerException | IOException e) {
            throw new IOException("failed linkOrCreateDir(" + str2 + ", " + str3 + ", " + str4 + ")", e);
        }
    }

    public final void linkFiles(String str, List list, File file, File file2) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            linkFile(str, getRelativePath((File) it.next(), file2), file2.getAbsolutePath(), file.getAbsolutePath());
        }
        Slog.d("PackageInstallerSession", "Linked " + list.size() + " files into " + file);
    }

    public static void copyFiles(List list, File file) {
        for (File file2 : file.listFiles()) {
            if (file2.getName().endsWith(".tmp")) {
                file2.delete();
            }
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            File file3 = (File) it.next();
            File createTempFile = File.createTempFile("inherit", ".tmp", file);
            Slog.d("PackageInstallerSession", "Copying " + file3 + " to " + createTempFile);
            if (!FileUtils.copyFile(file3, createTempFile)) {
                throw new IOException("Failed to copy " + file3 + " to " + createTempFile);
            }
            try {
                Os.chmod(createTempFile.getAbsolutePath(), FrameworkStatsLog.VBMETA_DIGEST_REPORTED);
                File file4 = new File(file, file3.getName());
                Slog.d("PackageInstallerSession", "Renaming " + createTempFile + " to " + file4);
                if (!createTempFile.renameTo(file4)) {
                    throw new IOException("Failed to rename " + createTempFile + " to " + file4);
                }
            } catch (ErrnoException unused) {
                throw new IOException("Failed to chmod " + createTempFile);
            }
        }
        Slog.d("PackageInstallerSession", "Copied " + list.size() + " files into " + file);
    }

    public final void extractNativeLibraries(PackageLite packageLite, File file, String str, boolean z) {
        Objects.requireNonNull(packageLite);
        File file2 = new File(file, "lib");
        if (!z) {
            NativeLibraryHelper.removeNativeBinariesFromDirLI(file2, true);
        }
        NativeLibraryHelper.Handle handle = null;
        try {
            try {
                handle = NativeLibraryHelper.Handle.create(packageLite);
                int copyNativeBinariesWithOverride = NativeLibraryHelper.copyNativeBinariesWithOverride(handle, file2, str, isIncrementalInstallation());
                if (copyNativeBinariesWithOverride == 1) {
                    return;
                }
                throw new PackageManagerException(copyNativeBinariesWithOverride, "Failed to extract native libraries, res=" + copyNativeBinariesWithOverride);
            } catch (IOException e) {
                throw new PackageManagerException(-110, "Failed to extract native libraries", e);
            }
        } finally {
            IoUtils.closeQuietly(handle);
        }
    }

    public final void finalizeAndFixContainer(String str) {
        if (!PackageHelperExt.finalizeSdDir(str)) {
            throw new PackageManagerException(-18, "Failed to finalize container " + str);
        }
        if (PackageHelperExt.fixSdPermissions(str, this.defaultContainerGid, (String) null)) {
            return;
        }
        throw new PackageManagerException(-18, "Failed to fix permissions on container " + str);
    }

    public void setPermissionsResult(boolean z) {
        if (!isSealed() && !isPreapprovalRequested()) {
            throw new SecurityException("Must be sealed to accept permissions");
        }
        PackageInstallerSession session = (hasParentSessionId() && isCommitted()) ? this.mSessionProvider.getSession(getParentSessionId()) : this;
        if (z) {
            Slog.i("PackageInstallerSession", "status of session: accepted{" + this.sessionId + "}, User has confirmed");
            synchronized (this.mLock) {
                this.mPermissionsManuallyAccepted = true;
            }
            session.mHandler.obtainMessage(isCommitted() ? 3 : 6).sendToTarget();
            return;
        }
        session.destroy();
        session.dispatchSessionFinished(-115, "User rejected permissions", null);
        session.maybeFinishChildSessions(-115, "User rejected permissions");
    }

    public void open() {
        boolean z;
        activate();
        synchronized (this.mLock) {
            z = this.mPrepared;
            if (!z) {
                File file = this.stageDir;
                if (file != null) {
                    PackageInstallerService.prepareStageDir(file);
                } else if (!this.params.isMultiPackage) {
                    if (PMRune.PM_INSTALL_TO_SDCARD && this.stageCid != null) {
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            PackageInstallerService.prepareExternalStageCid(this.stageCid, this.params.sizeBytes + 5242880);
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            this.mInternalProgress = 0.25f;
                            computeProgressLocked(true);
                        } catch (Throwable th) {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            throw th;
                        }
                    } else {
                        throw new IllegalArgumentException("Exactly one of stageDir or stageCid stage must be set");
                    }
                }
                this.mPrepared = true;
            }
        }
        if (z) {
            return;
        }
        this.mCallback.onSessionPrepared(this);
    }

    public final void activate() {
        if (this.mActiveCount.getAndIncrement() == 0) {
            this.mCallback.onSessionActiveChanged(this, true);
        }
    }

    public void openQuick(String str) {
        activate();
        synchronized (this.mLock) {
            if (!this.mPrepared) {
                File file = this.stageDir;
                if (file != null) {
                    PackageInstallerService.prepareStageDirQuick(file, str);
                } else if (!this.params.isMultiPackage) {
                    throw new IllegalArgumentException("stageDir must be set");
                }
                this.mPrepared = true;
            }
        }
    }

    public void close() {
        closeInternal(true);
    }

    public final void closeInternal(boolean z) {
        synchronized (this.mLock) {
            if (z) {
                assertCallerIsOwnerOrRoot();
            }
        }
        deactivate();
    }

    public final void deactivate() {
        int decrementAndGet;
        synchronized (this.mLock) {
            decrementAndGet = this.mActiveCount.decrementAndGet();
        }
        if (decrementAndGet == 0) {
            this.mCallback.onSessionActiveChanged(this, false);
        }
    }

    public final void maybeFinishChildSessions(int i, String str) {
        Iterator it = getChildSessions().iterator();
        while (it.hasNext()) {
            ((PackageInstallerSession) it.next()).dispatchSessionFinished(i, str, null);
        }
    }

    public final void assertNotChild(String str) {
        if (hasParentSessionId()) {
            throw new IllegalStateException(str + " can't be called on a child session, id=" + this.sessionId + " parentId=" + getParentSessionId());
        }
    }

    public final boolean dispatchPendingAbandonCallback() {
        synchronized (this.mLock) {
            if (!this.mStageDirInUse) {
                return false;
            }
            this.mStageDirInUse = false;
            Runnable runnable = this.mPendingAbandonCallback;
            this.mPendingAbandonCallback = null;
            if (runnable == null) {
                return false;
            }
            runnable.run();
            return true;
        }
    }

    public void abandon() {
        synchronized (this.mLock) {
            assertNotChild("abandon");
            assertCallerIsOwnerOrRootOrSystem();
            if (isInTerminalState()) {
                return;
            }
            this.mDestroyed = true;
            Runnable runnable = new Runnable() { // from class: com.android.server.pm.PackageInstallerSession$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    PackageInstallerSession.this.lambda$abandon$7();
                }
            };
            if (this.mStageDirInUse) {
                this.mPendingAbandonCallback = runnable;
                this.mCallback.onSessionChanged(this);
            } else {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    runnable.run();
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$abandon$7() {
        assertNotLocked("abandonStaged");
        if (isStaged() && isCommitted()) {
            this.mStagingManager.abortCommittedSession(this.mStagedSession);
        }
        destroy();
        dispatchSessionFinished(-115, "Session was abandoned", null);
        maybeFinishChildSessions(-115, "Session was abandoned because the parent session is abandoned");
    }

    public boolean isMultiPackage() {
        return this.params.isMultiPackage;
    }

    public boolean isStaged() {
        return this.params.isStaged;
    }

    public int getInstallFlags() {
        return this.params.installFlags;
    }

    public DataLoaderParamsParcel getDataLoaderParams() {
        this.mContext.enforceCallingOrSelfPermission("com.android.permission.USE_INSTALLER_V2", null);
        DataLoaderParams dataLoaderParams = this.params.dataLoaderParams;
        if (dataLoaderParams != null) {
            return dataLoaderParams.getData();
        }
        return null;
    }

    public void addFile(int i, String str, long j, byte[] bArr, byte[] bArr2) {
        this.mContext.enforceCallingOrSelfPermission("com.android.permission.USE_INSTALLER_V2", null);
        if (!isDataLoaderInstallation()) {
            throw new IllegalStateException("Cannot add files to non-data loader installation session.");
        }
        if (isStreamingInstallation() && i != 0) {
            throw new IllegalArgumentException("Non-incremental installation only supports /data/app placement: " + str);
        }
        if (bArr == null) {
            throw new IllegalArgumentException("DataLoader installation requires valid metadata: " + str);
        }
        if (!FileUtils.isValidExtFilename(str)) {
            throw new IllegalArgumentException("Invalid name: " + str);
        }
        synchronized (this.mLock) {
            assertCallerIsOwnerOrRoot();
            assertPreparedAndNotSealedLocked("addFile");
            ArraySet arraySet = this.mFiles;
            if (!arraySet.add(new FileEntry(arraySet.size(), new InstallationFile(i, str, j, bArr, bArr2)))) {
                throw new IllegalArgumentException("File already added: " + str);
            }
        }
    }

    public void removeFile(int i, String str) {
        this.mContext.enforceCallingOrSelfPermission("com.android.permission.USE_INSTALLER_V2", null);
        if (!isDataLoaderInstallation()) {
            throw new IllegalStateException("Cannot add files to non-data loader installation session.");
        }
        if (TextUtils.isEmpty(this.params.appPackageName)) {
            throw new IllegalStateException("Must specify package name to remove a split");
        }
        synchronized (this.mLock) {
            assertCallerIsOwnerOrRoot();
            assertPreparedAndNotSealedLocked("removeFile");
            ArraySet arraySet = this.mFiles;
            if (!arraySet.add(new FileEntry(arraySet.size(), new InstallationFile(i, getRemoveMarkerName(str), -1L, (byte[]) null, (byte[]) null)))) {
                throw new IllegalArgumentException("File already removed: " + str);
            }
        }
    }

    public final boolean prepareDataLoaderLocked() {
        if (!isDataLoaderInstallation() || this.mDataLoaderFinished) {
            return true;
        }
        final ArrayList arrayList = new ArrayList();
        final ArrayList arrayList2 = new ArrayList();
        for (InstallationFile installationFile : getInstallationFilesLocked()) {
            if (sAddedFilter.accept(new File(this.stageDir, installationFile.getName()))) {
                arrayList.add(installationFile.getData());
            } else if (sRemovedFilter.accept(new File(this.stageDir, installationFile.getName()))) {
                arrayList2.add(installationFile.getName().substring(0, installationFile.getName().length() - 8));
            }
        }
        final DataLoaderParams dataLoaderParams = this.params.dataLoaderParams;
        final boolean isIncrementalInstallation = true ^ isIncrementalInstallation();
        final boolean isSystemDataLoaderInstallation = isSystemDataLoaderInstallation();
        IDataLoaderStatusListener.Stub stub = new IDataLoaderStatusListener.Stub() { // from class: com.android.server.pm.PackageInstallerSession.6
            public void onStatusChanged(int i, int i2) {
                if (i2 == 0 || i2 == 1 || i2 == 5) {
                    return;
                }
                if (PackageInstallerSession.this.mDestroyed || PackageInstallerSession.this.mDataLoaderFinished) {
                    if (i2 == 9 && isSystemDataLoaderInstallation) {
                        PackageInstallerSession.this.onSystemDataLoaderUnrecoverable();
                        return;
                    }
                    return;
                }
                try {
                    switch (i2) {
                        case 2:
                            if (isIncrementalInstallation) {
                                FileSystemControlParcel fileSystemControlParcel = new FileSystemControlParcel();
                                fileSystemControlParcel.callback = new FileSystemConnector(arrayList);
                                PackageInstallerSession.this.getDataLoader(i).create(i, dataLoaderParams.getData(), fileSystemControlParcel, this);
                                return;
                            }
                            return;
                        case 3:
                            if (isIncrementalInstallation) {
                                PackageInstallerSession.this.getDataLoader(i).start(i);
                                return;
                            }
                            return;
                        case 4:
                            IDataLoader dataLoader = PackageInstallerSession.this.getDataLoader(i);
                            List list = arrayList;
                            InstallationFileParcel[] installationFileParcelArr = (InstallationFileParcel[]) list.toArray(new InstallationFileParcel[list.size()]);
                            List list2 = arrayList2;
                            dataLoader.prepareImage(i, installationFileParcelArr, (String[]) list2.toArray(new String[list2.size()]));
                            return;
                        case 5:
                        default:
                            return;
                        case 6:
                            PackageInstallerSession.this.mDataLoaderFinished = true;
                            if (PackageInstallerSession.this.hasParentSessionId()) {
                                PackageInstallerSession.this.mSessionProvider.getSession(PackageInstallerSession.this.getParentSessionId()).dispatchSessionSealed();
                            } else {
                                PackageInstallerSession.this.dispatchSessionSealed();
                            }
                            if (isIncrementalInstallation) {
                                PackageInstallerSession.this.getDataLoader(i).destroy(i);
                                return;
                            }
                            return;
                        case 7:
                            PackageInstallerSession.this.mDataLoaderFinished = true;
                            PackageInstallerSession.this.dispatchSessionValidationFailure(-20, "Failed to prepare image.");
                            if (isIncrementalInstallation) {
                                PackageInstallerSession.this.getDataLoader(i).destroy(i);
                                return;
                            }
                            return;
                        case 8:
                            PackageInstallerSession.sendPendingStreaming(PackageInstallerSession.this.mContext, PackageInstallerSession.this.getRemoteStatusReceiver(), PackageInstallerSession.this.sessionId, "DataLoader unavailable");
                            return;
                        case 9:
                            throw new PackageManagerException(-20, "DataLoader reported unrecoverable failure.");
                    }
                } catch (RemoteException e) {
                    PackageInstallerSession.sendPendingStreaming(PackageInstallerSession.this.mContext, PackageInstallerSession.this.getRemoteStatusReceiver(), PackageInstallerSession.this.sessionId, e.getMessage());
                } catch (PackageManagerException e2) {
                    PackageInstallerSession.this.mDataLoaderFinished = true;
                    PackageInstallerSession.this.dispatchSessionValidationFailure(e2.error, ExceptionUtils.getCompleteMessage(e2));
                }
            }
        };
        if (!isIncrementalInstallation) {
            PackageManagerService packageManagerService = this.mPm;
            PerUidReadTimeouts[] perUidReadTimeouts = packageManagerService.getPerUidReadTimeouts(packageManagerService.snapshotComputer());
            StorageHealthCheckParams storageHealthCheckParams = new StorageHealthCheckParams();
            storageHealthCheckParams.blockedTimeoutMs = 2000;
            storageHealthCheckParams.unhealthyTimeoutMs = 7000;
            storageHealthCheckParams.unhealthyMonitoringMs = 60000;
            IStorageHealthListener.Stub stub2 = new IStorageHealthListener.Stub() { // from class: com.android.server.pm.PackageInstallerSession.7
                public void onHealthStatus(int i, int i2) {
                    if (PackageInstallerSession.this.mDestroyed || PackageInstallerSession.this.mDataLoaderFinished) {
                        return;
                    }
                    if (i2 == 1 || i2 == 2) {
                        if (isSystemDataLoaderInstallation) {
                            return;
                        }
                    } else if (i2 != 3) {
                        return;
                    }
                    PackageInstallerSession.this.mDataLoaderFinished = true;
                    PackageInstallerSession.this.dispatchSessionValidationFailure(-20, "Image is missing pages required for installation.");
                }
            };
            try {
                PackageInfo packageInfo = this.mPm.snapshotComputer().getPackageInfo(this.params.appPackageName, 0L, this.userId);
                File parentFile = (packageInfo == null || packageInfo.applicationInfo == null) ? null : new File(packageInfo.applicationInfo.getCodePath()).getParentFile();
                IncrementalFileStorages incrementalFileStorages = this.mIncrementalFileStorages;
                if (incrementalFileStorages == null) {
                    this.mIncrementalFileStorages = IncrementalFileStorages.initialize(this.mContext, PMRune.PM_INSTALL_TO_SDCARD ? resolveStageDirLocked() : this.stageDir, parentFile, dataLoaderParams, stub, storageHealthCheckParams, stub2, arrayList, perUidReadTimeouts, new IPackageLoadingProgressCallback.Stub() { // from class: com.android.server.pm.PackageInstallerSession.8
                        public void onPackageLoadingProgressChanged(float f) {
                            synchronized (PackageInstallerSession.this.mProgressLock) {
                                PackageInstallerSession.this.mIncrementalProgress = f;
                                PackageInstallerSession.this.computeProgressLocked(true);
                            }
                        }
                    });
                } else {
                    incrementalFileStorages.startLoading(dataLoaderParams, stub, storageHealthCheckParams, stub2, perUidReadTimeouts);
                }
                return false;
            } catch (IOException e) {
                throw new PackageManagerException(-20, e.getMessage(), e.getCause());
            }
        }
        if (getDataLoaderManager().bindToDataLoader(this.sessionId, dataLoaderParams.getData(), 0L, stub)) {
            return false;
        }
        throw new PackageManagerException(-20, "Failed to initialize data loader");
    }

    public final DataLoaderManager getDataLoaderManager() {
        DataLoaderManager dataLoaderManager = (DataLoaderManager) this.mContext.getSystemService(DataLoaderManager.class);
        if (dataLoaderManager != null) {
            return dataLoaderManager;
        }
        throw new PackageManagerException(-20, "Failed to find data loader manager service");
    }

    public final IDataLoader getDataLoader(int i) {
        IDataLoader dataLoader = getDataLoaderManager().getDataLoader(i);
        if (dataLoader != null) {
            return dataLoader;
        }
        throw new PackageManagerException(-20, "Failure to obtain data loader");
    }

    public final void dispatchSessionValidationFailure(int i, String str) {
        this.mHandler.obtainMessage(5, i, -1, str).sendToTarget();
    }

    public final int[] getChildSessionIdsLocked() {
        int size = this.mChildSessions.size();
        if (size == 0) {
            return EMPTY_CHILD_SESSION_ARRAY;
        }
        int[] iArr = new int[size];
        for (int i = 0; i < size; i++) {
            iArr[i] = this.mChildSessions.keyAt(i);
        }
        return iArr;
    }

    public int[] getChildSessionIds() {
        int[] childSessionIdsLocked;
        synchronized (this.mLock) {
            childSessionIdsLocked = getChildSessionIdsLocked();
        }
        return childSessionIdsLocked;
    }

    public final boolean canBeAddedAsChild(int i) {
        boolean z;
        synchronized (this.mLock) {
            z = ((hasParentSessionId() && this.mParentSessionId != i) || isCommitted() || this.mDestroyed) ? false : true;
        }
        return z;
    }

    public final void acquireTransactionLock() {
        if (!this.mTransactionLock.compareAndSet(false, true)) {
            throw new UnsupportedOperationException("Concurrent access not supported");
        }
    }

    public final void releaseTransactionLock() {
        this.mTransactionLock.compareAndSet(true, false);
    }

    public void addChildSessionId(int i) {
        if (!this.params.isMultiPackage) {
            throw new IllegalStateException("Single-session " + this.sessionId + " can't have child.");
        }
        PackageInstallerSession session = this.mSessionProvider.getSession(i);
        if (session == null) {
            throw new IllegalStateException("Unable to add child session " + i + " as it does not exist.");
        }
        PackageInstaller.SessionParams sessionParams = session.params;
        if (sessionParams.isMultiPackage) {
            throw new IllegalStateException("Multi-session " + i + " can't be a child.");
        }
        PackageInstaller.SessionParams sessionParams2 = this.params;
        if (sessionParams2.isStaged != sessionParams.isStaged) {
            throw new IllegalStateException("Multipackage Inconsistency: session " + session.sessionId + " and session " + this.sessionId + " have inconsistent staged settings");
        }
        if (sessionParams2.getEnableRollback() != session.params.getEnableRollback()) {
            throw new IllegalStateException("Multipackage Inconsistency: session " + session.sessionId + " and session " + this.sessionId + " have inconsistent rollback settings");
        }
        boolean z = containsApkSession() || !session.isApexSession();
        boolean z2 = sessionContains(new Predicate() { // from class: com.android.server.pm.PackageInstallerSession$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean isApexSession;
                isApexSession = ((PackageInstallerSession) obj).isApexSession();
                return isApexSession;
            }
        }) || session.isApexSession();
        if (!this.params.isStaged && z && z2) {
            throw new IllegalStateException("Mix of APK and APEX is not supported for non-staged multi-package session");
        }
        try {
            acquireTransactionLock();
            session.acquireTransactionLock();
            if (!session.canBeAddedAsChild(this.sessionId)) {
                throw new IllegalStateException("Unable to add child session " + i + " as it is in an invalid state.");
            }
            synchronized (this.mLock) {
                assertCallerIsOwnerOrRoot();
                assertPreparedAndNotSealedLocked("addChildSessionId");
                if (this.mChildSessions.indexOfKey(i) >= 0) {
                    return;
                }
                session.setParentSessionId(this.sessionId);
                this.mChildSessions.put(i, session);
            }
        } finally {
            releaseTransactionLock();
            session.releaseTransactionLock();
        }
    }

    public void removeChildSessionId(int i) {
        synchronized (this.mLock) {
            assertCallerIsOwnerOrRoot();
            assertPreparedAndNotSealedLocked("removeChildSessionId");
            int indexOfKey = this.mChildSessions.indexOfKey(i);
            if (indexOfKey < 0) {
                return;
            }
            PackageInstallerSession packageInstallerSession = (PackageInstallerSession) this.mChildSessions.valueAt(indexOfKey);
            try {
                acquireTransactionLock();
                packageInstallerSession.acquireTransactionLock();
                packageInstallerSession.setParentSessionId(-1);
                this.mChildSessions.removeAt(indexOfKey);
            } finally {
                releaseTransactionLock();
                packageInstallerSession.releaseTransactionLock();
            }
        }
    }

    public void setParentSessionId(int i) {
        synchronized (this.mLock) {
            if (i != -1) {
                if (this.mParentSessionId != -1) {
                    throw new IllegalStateException("The parent of " + this.sessionId + " is alreadyset to " + this.mParentSessionId);
                }
            }
            this.mParentSessionId = i;
        }
    }

    public boolean hasParentSessionId() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mParentSessionId != -1;
        }
        return z;
    }

    public int getParentSessionId() {
        int i;
        synchronized (this.mLock) {
            i = this.mParentSessionId;
        }
        return i;
    }

    public final void dispatchSessionFinished(int i, String str, Bundle bundle) {
        sendUpdateToRemoteStatusReceiver(i, str, bundle);
        synchronized (this.mLock) {
            this.mFinalStatus = i;
            this.mFinalMessage = str;
        }
        boolean z = i == 1;
        boolean z2 = bundle == null || !bundle.getBoolean("android.intent.extra.REPLACING");
        if (z && z2 && this.mPm.mInstallerService.okToSendBroadcasts()) {
            this.mPm.sendSessionCommitBroadcast(generateInfoScrubbed(true), this.userId);
        }
        if (this.mUnknownSourceInstallAccepted.get()) {
            this.mPm.mCustomInjector.getUnknownSourceAppManager().onUnknownSourceSessionFinished(z, this.mPackageName);
        }
        this.mCallback.onSessionFinished(this, z);
        if (isDataLoaderInstallation()) {
            logDataLoaderInstallationSession(i);
        }
    }

    public final void sendUpdateToRemoteStatusReceiver(int i, String str, Bundle bundle) {
        IntentSender remoteStatusReceiver = getRemoteStatusReceiver();
        if (remoteStatusReceiver != null) {
            SomeArgs obtain = SomeArgs.obtain();
            obtain.arg1 = getPackageName();
            obtain.arg2 = str;
            obtain.arg3 = bundle;
            obtain.arg4 = remoteStatusReceiver;
            obtain.argi1 = i;
            obtain.argi2 = (!isPreapprovalRequested() || isCommitted()) ? 0 : 1;
            this.mHandler.obtainMessage(4, obtain).sendToTarget();
        }
    }

    public final void dispatchSessionPreappoved() {
        IntentSender remoteStatusReceiver = getRemoteStatusReceiver();
        Intent intent = new Intent();
        intent.putExtra("android.content.pm.extra.SESSION_ID", this.sessionId);
        intent.putExtra("android.content.pm.extra.STATUS", 0);
        intent.putExtra("android.content.pm.extra.PRE_APPROVAL", true);
        try {
            BroadcastOptions makeBasic = BroadcastOptions.makeBasic();
            makeBasic.setPendingIntentBackgroundActivityLaunchAllowed(false);
            remoteStatusReceiver.sendIntent(this.mContext, 0, intent, null, null, null, makeBasic.toBundle());
        } catch (IntentSender.SendIntentException unused) {
        }
    }

    public void requestUserPreapproval(PackageInstaller.PreapprovalDetails preapprovalDetails, IntentSender intentSender) {
        validatePreapprovalRequest(preapprovalDetails, intentSender);
        Slog.i("PackageInstallerSession", "START PREAPPROVAL SESSION: id{" + this.sessionId + "}");
        if (!PackageManagerService.isPreapprovalRequestAvailable()) {
            sendUpdateToRemoteStatusReceiver(-129, "Request user pre-approval is currently not available.", null);
        } else {
            dispatchPreapprovalRequest();
        }
    }

    public final void validatePreapprovalRequest(PackageInstaller.PreapprovalDetails preapprovalDetails, IntentSender intentSender) {
        assertCallerIsOwnerOrRoot();
        if (isMultiPackage()) {
            throw new IllegalStateException("Session " + this.sessionId + " is a parent of multi-package session and requestUserPreapproval on the parent session isn't supported.");
        }
        synchronized (this.mLock) {
            assertPreparedAndNotSealedLocked("request of session " + this.sessionId);
            this.mPreapprovalDetails = preapprovalDetails;
            setRemoteStatusReceiver(intentSender);
        }
    }

    public final void dispatchPreapprovalRequest() {
        synchronized (this.mLock) {
            assertPreparedAndNotPreapprovalRequestedLocked("dispatchPreapprovalRequest");
        }
        markAsPreapprovalRequested();
        this.mHandler.obtainMessage(6).sendToTarget();
    }

    public final void markAsPreapprovalRequested() {
        this.mPreapprovalRequested.set(true);
    }

    public boolean isApplicationEnabledSettingPersistent() {
        return this.params.applicationEnabledSettingPersistent;
    }

    public boolean isRequestUpdateOwnership() {
        return (this.params.installFlags & 33554432) != 0;
    }

    public void setSessionReady() {
        synchronized (this.mLock) {
            if (!this.mDestroyed && !this.mSessionFailed) {
                this.mSessionReady = true;
                this.mSessionApplied = false;
                this.mSessionFailed = false;
                this.mSessionErrorCode = 0;
                this.mSessionErrorMessage = "";
                this.mCallback.onSessionChanged(this);
            }
        }
    }

    public void setSessionFailed(int i, String str) {
        synchronized (this.mLock) {
            if (!this.mDestroyed && !this.mSessionFailed) {
                this.mSessionReady = false;
                this.mSessionApplied = false;
                this.mSessionFailed = true;
                this.mSessionErrorCode = i;
                this.mSessionErrorMessage = str;
                Slog.d("PackageInstallerSession", "Marking session " + this.sessionId + " as failed: " + str);
                destroy();
                this.mCallback.onSessionChanged(this);
            }
        }
    }

    public final void setSessionApplied() {
        synchronized (this.mLock) {
            if (!this.mDestroyed && !this.mSessionFailed) {
                this.mSessionReady = false;
                this.mSessionApplied = true;
                this.mSessionFailed = false;
                this.mSessionErrorCode = 1;
                this.mSessionErrorMessage = "";
                Slog.d("PackageInstallerSession", "Marking session " + this.sessionId + " as applied");
                destroy();
                this.mCallback.onSessionChanged(this);
            }
        }
    }

    public boolean isSessionReady() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mSessionReady;
        }
        return z;
    }

    public boolean isSessionFailed() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mSessionFailed;
        }
        return z;
    }

    public final void destroy() {
        destroyInternal();
        Iterator it = getChildSessions().iterator();
        while (it.hasNext()) {
            ((PackageInstallerSession) it.next()).destroyInternal();
        }
    }

    public final void destroyInternal() {
        IncrementalFileStorages incrementalFileStorages;
        synchronized (this.mLock) {
            this.mSealed = true;
            if (!this.params.isStaged) {
                this.mDestroyed = true;
            }
            Iterator it = this.mFds.iterator();
            while (it.hasNext()) {
                ((RevocableFileDescriptor) it.next()).revoke();
            }
            Iterator it2 = this.mBridges.iterator();
            while (it2.hasNext()) {
                ((FileBridge) it2.next()).forceClose();
            }
            incrementalFileStorages = this.mIncrementalFileStorages;
            this.mIncrementalFileStorages = null;
        }
        if (incrementalFileStorages != null) {
            try {
                incrementalFileStorages.cleanUpAndMarkComplete();
            } catch (Installer.InstallerException unused) {
            }
        }
        File file = this.stageDir;
        if (file != null) {
            this.mInstaller.rmPackageDir(file.getName(), this.stageDir.getAbsolutePath());
        }
        if (!PMRune.PM_INSTALL_TO_SDCARD || this.stageCid == null) {
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            PackageHelperExt.destroySdDir(this.stageCid);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void dump(IndentingPrintWriter indentingPrintWriter) {
        synchronized (this.mLock) {
            dumpLocked(indentingPrintWriter);
        }
    }

    public final void dumpLocked(IndentingPrintWriter indentingPrintWriter) {
        float f;
        float f2;
        indentingPrintWriter.println("Session " + this.sessionId + XmlUtils.STRING_ARRAY_SEPARATOR);
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.printPair("userId", Integer.valueOf(this.userId));
        indentingPrintWriter.printPair("mOriginalInstallerUid", Integer.valueOf(this.mOriginalInstallerUid));
        indentingPrintWriter.printPair("mOriginalInstallerPackageName", this.mOriginalInstallerPackageName);
        indentingPrintWriter.printPair("installerPackageName", this.mInstallSource.mInstallerPackageName);
        indentingPrintWriter.printPair("installInitiatingPackageName", this.mInstallSource.mInitiatingPackageName);
        indentingPrintWriter.printPair("installOriginatingPackageName", this.mInstallSource.mOriginatingPackageName);
        indentingPrintWriter.printPair("mInstallerUid", Integer.valueOf(this.mInstallerUid));
        indentingPrintWriter.printPair("createdMillis", Long.valueOf(this.createdMillis));
        indentingPrintWriter.printPair("updatedMillis", Long.valueOf(this.updatedMillis));
        indentingPrintWriter.printPair("committedMillis", Long.valueOf(this.committedMillis));
        indentingPrintWriter.printPair("stageDir", this.stageDir);
        indentingPrintWriter.printPair("stageCid", this.stageCid);
        indentingPrintWriter.println();
        this.params.dump(indentingPrintWriter);
        synchronized (this.mProgressLock) {
            f = this.mClientProgress;
            f2 = this.mProgress;
        }
        indentingPrintWriter.printPair("mClientProgress", Float.valueOf(f));
        indentingPrintWriter.printPair("mProgress", Float.valueOf(f2));
        indentingPrintWriter.printPair("mCommitted", this.mCommitted);
        indentingPrintWriter.printPair("mPreapprovalRequested", this.mPreapprovalRequested);
        indentingPrintWriter.printPair("mSealed", Boolean.valueOf(this.mSealed));
        indentingPrintWriter.printPair("mPermissionsManuallyAccepted", Boolean.valueOf(this.mPermissionsManuallyAccepted));
        indentingPrintWriter.printPair("mStageDirInUse", Boolean.valueOf(this.mStageDirInUse));
        indentingPrintWriter.printPair("mDestroyed", Boolean.valueOf(this.mDestroyed));
        indentingPrintWriter.printPair("mFds", Integer.valueOf(this.mFds.size()));
        indentingPrintWriter.printPair("mBridges", Integer.valueOf(this.mBridges.size()));
        indentingPrintWriter.printPair("mFinalStatus", Integer.valueOf(this.mFinalStatus));
        indentingPrintWriter.printPair("mFinalMessage", this.mFinalMessage);
        indentingPrintWriter.printPair("params.isMultiPackage", Boolean.valueOf(this.params.isMultiPackage));
        indentingPrintWriter.printPair("params.isStaged", Boolean.valueOf(this.params.isStaged));
        indentingPrintWriter.printPair("mParentSessionId", Integer.valueOf(this.mParentSessionId));
        indentingPrintWriter.printPair("mChildSessionIds", getChildSessionIdsLocked());
        indentingPrintWriter.printPair("mSessionApplied", Boolean.valueOf(this.mSessionApplied));
        indentingPrintWriter.printPair("mSessionFailed", Boolean.valueOf(this.mSessionFailed));
        indentingPrintWriter.printPair("mSessionReady", Boolean.valueOf(this.mSessionReady));
        indentingPrintWriter.printPair("mSessionErrorCode", Integer.valueOf(this.mSessionErrorCode));
        indentingPrintWriter.printPair("mSessionErrorMessage", this.mSessionErrorMessage);
        indentingPrintWriter.printPair("mPreapprovalDetails", this.mPreapprovalDetails);
        indentingPrintWriter.println();
        indentingPrintWriter.decreaseIndent();
    }

    public static void sendOnUserActionRequired(Context context, IntentSender intentSender, int i, Intent intent) {
        Intent intent2 = new Intent();
        intent2.putExtra("android.content.pm.extra.SESSION_ID", i);
        intent2.putExtra("android.content.pm.extra.STATUS", -1);
        intent2.putExtra("android.content.pm.extra.PRE_APPROVAL", "android.content.pm.action.CONFIRM_PRE_APPROVAL".equals(intent.getAction()));
        intent2.putExtra("android.intent.extra.INTENT", intent);
        try {
            BroadcastOptions makeBasic = BroadcastOptions.makeBasic();
            makeBasic.setPendingIntentBackgroundActivityLaunchAllowed(false);
            intentSender.sendIntent(context, 0, intent2, null, null, null, makeBasic.toBundle());
        } catch (IntentSender.SendIntentException unused) {
        }
    }

    public static void sendOnPackageInstalled(Context context, IntentSender intentSender, int i, boolean z, int i2, String str, int i3, boolean z2, String str2, Bundle bundle) {
        if (1 == i3 && z) {
            Notification buildSuccessNotification = PackageInstallerService.buildSuccessNotification(context, getDeviceOwnerInstalledPackageMsg(context, bundle != null && bundle.getBoolean("android.intent.extra.REPLACING")), str, i2);
            if (buildSuccessNotification != null) {
                ((NotificationManager) context.getSystemService("notification")).notify(str, 21, buildSuccessNotification);
            }
        }
        Intent intent = new Intent();
        intent.putExtra("android.content.pm.extra.PACKAGE_NAME", str);
        intent.putExtra("android.content.pm.extra.SESSION_ID", i);
        intent.putExtra("android.content.pm.extra.STATUS", PackageManager.installStatusToPublicStatus(i3));
        intent.putExtra("android.content.pm.extra.STATUS_MESSAGE", PackageManager.installStatusToString(i3, str2));
        intent.putExtra("android.content.pm.extra.LEGACY_STATUS", i3);
        intent.putExtra("android.content.pm.extra.PRE_APPROVAL", z2);
        if (bundle != null) {
            String string = bundle.getString("android.content.pm.extra.FAILURE_EXISTING_PACKAGE");
            if (!TextUtils.isEmpty(string)) {
                intent.putExtra("android.content.pm.extra.OTHER_PACKAGE_NAME", string);
            }
        }
        Slog.i("PackageInstallerSession", "result of session: " + i3 + "{" + i + "}, " + str2);
        try {
            BroadcastOptions makeBasic = BroadcastOptions.makeBasic();
            makeBasic.setPendingIntentBackgroundActivityLaunchAllowed(false);
            intentSender.sendIntent(context, 0, intent, null, null, null, makeBasic.toBundle());
        } catch (IntentSender.SendIntentException unused) {
        }
    }

    public static String getDeviceOwnerInstalledPackageMsg(final Context context, boolean z) {
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) context.getSystemService(DevicePolicyManager.class);
        if (z) {
            return devicePolicyManager.getResources().getString("Core.PACKAGE_UPDATED_BY_DO", new Supplier() { // from class: com.android.server.pm.PackageInstallerSession$$ExternalSyntheticLambda7
                @Override // java.util.function.Supplier
                public final Object get() {
                    String string;
                    string = context.getString(17041618);
                    return string;
                }
            });
        }
        return devicePolicyManager.getResources().getString("Core.PACKAGE_INSTALLED_BY_DO", new Supplier() { // from class: com.android.server.pm.PackageInstallerSession$$ExternalSyntheticLambda8
            @Override // java.util.function.Supplier
            public final Object get() {
                String string;
                string = context.getString(17041613);
                return string;
            }
        });
    }

    public static void sendPendingStreaming(Context context, IntentSender intentSender, int i, String str) {
        if (intentSender == null) {
            Slog.e("PackageInstallerSession", "Missing receiver for pending streaming status.");
            return;
        }
        Intent intent = new Intent();
        intent.putExtra("android.content.pm.extra.SESSION_ID", i);
        intent.putExtra("android.content.pm.extra.STATUS", -2);
        if (!TextUtils.isEmpty(str)) {
            intent.putExtra("android.content.pm.extra.STATUS_MESSAGE", "Staging Image Not Ready [" + str + "]");
        } else {
            intent.putExtra("android.content.pm.extra.STATUS_MESSAGE", "Staging Image Not Ready");
        }
        try {
            BroadcastOptions makeBasic = BroadcastOptions.makeBasic();
            makeBasic.setPendingIntentBackgroundActivityLaunchAllowed(false);
            intentSender.sendIntent(context, 0, intent, null, null, null, makeBasic.toBundle());
        } catch (IntentSender.SendIntentException unused) {
        }
    }

    public static void writePermissionsLocked(TypedXmlSerializer typedXmlSerializer, PackageInstaller.SessionParams sessionParams) {
        ArrayMap permissionStates = sessionParams.getPermissionStates();
        for (int i = 0; i < permissionStates.size(); i++) {
            String str = (String) permissionStates.keyAt(i);
            String str2 = ((Integer) permissionStates.valueAt(i)).intValue() == 1 ? "grant-permission" : "deny-permission";
            typedXmlSerializer.startTag((String) null, str2);
            com.android.internal.util.XmlUtils.writeStringAttribute(typedXmlSerializer, "name", str);
            typedXmlSerializer.endTag((String) null, str2);
        }
    }

    public static void writeWhitelistedRestrictedPermissionsLocked(TypedXmlSerializer typedXmlSerializer, List list) {
        if (list != null) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                typedXmlSerializer.startTag((String) null, "whitelisted-restricted-permission");
                com.android.internal.util.XmlUtils.writeStringAttribute(typedXmlSerializer, "name", (CharSequence) list.get(i));
                typedXmlSerializer.endTag((String) null, "whitelisted-restricted-permission");
            }
        }
    }

    public static void writeAutoRevokePermissionsMode(TypedXmlSerializer typedXmlSerializer, int i) {
        typedXmlSerializer.startTag((String) null, "auto-revoke-permissions-mode");
        typedXmlSerializer.attributeInt((String) null, "mode", i);
        typedXmlSerializer.endTag((String) null, "auto-revoke-permissions-mode");
    }

    public static File buildAppIconFile(int i, File file) {
        return new File(file, "app_icon." + i + ".png");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0273 A[Catch: all -> 0x0363, LOOP:0: B:29:0x0271->B:30:0x0273, LOOP_END, TryCatch #1 {, blocks: (B:4:0x0003, B:6:0x0007, B:8:0x000d, B:11:0x000f, B:13:0x007e, B:14:0x0088, B:16:0x008c, B:17:0x0092, B:20:0x018a, B:22:0x0191, B:23:0x01cd, B:25:0x01ec, B:27:0x01f2, B:28:0x026b, B:30:0x0273, B:32:0x0288, B:34:0x0290, B:36:0x02d1, B:38:0x02da, B:40:0x02f2, B:42:0x031b, B:44:0x031e, B:46:0x0326, B:48:0x033c, B:53:0x0340, B:52:0x0358, B:57:0x035b, B:60:0x01f7, B:62:0x01fd, B:65:0x0209, B:70:0x022f, B:71:0x025c, B:80:0x0267, B:81:0x026a), top: B:3:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0290 A[Catch: all -> 0x0363, LOOP:1: B:33:0x028e->B:34:0x0290, LOOP_END, TryCatch #1 {, blocks: (B:4:0x0003, B:6:0x0007, B:8:0x000d, B:11:0x000f, B:13:0x007e, B:14:0x0088, B:16:0x008c, B:17:0x0092, B:20:0x018a, B:22:0x0191, B:23:0x01cd, B:25:0x01ec, B:27:0x01f2, B:28:0x026b, B:30:0x0273, B:32:0x0288, B:34:0x0290, B:36:0x02d1, B:38:0x02da, B:40:0x02f2, B:42:0x031b, B:44:0x031e, B:46:0x0326, B:48:0x033c, B:53:0x0340, B:52:0x0358, B:57:0x035b, B:60:0x01f7, B:62:0x01fd, B:65:0x0209, B:70:0x022f, B:71:0x025c, B:80:0x0267, B:81:0x026a), top: B:3:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x02da A[Catch: all -> 0x0363, TryCatch #1 {, blocks: (B:4:0x0003, B:6:0x0007, B:8:0x000d, B:11:0x000f, B:13:0x007e, B:14:0x0088, B:16:0x008c, B:17:0x0092, B:20:0x018a, B:22:0x0191, B:23:0x01cd, B:25:0x01ec, B:27:0x01f2, B:28:0x026b, B:30:0x0273, B:32:0x0288, B:34:0x0290, B:36:0x02d1, B:38:0x02da, B:40:0x02f2, B:42:0x031b, B:44:0x031e, B:46:0x0326, B:48:0x033c, B:53:0x0340, B:52:0x0358, B:57:0x035b, B:60:0x01f7, B:62:0x01fd, B:65:0x0209, B:70:0x022f, B:71:0x025c, B:80:0x0267, B:81:0x026a), top: B:3:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0326 A[Catch: all -> 0x0363, TryCatch #1 {, blocks: (B:4:0x0003, B:6:0x0007, B:8:0x000d, B:11:0x000f, B:13:0x007e, B:14:0x0088, B:16:0x008c, B:17:0x0092, B:20:0x018a, B:22:0x0191, B:23:0x01cd, B:25:0x01ec, B:27:0x01f2, B:28:0x026b, B:30:0x0273, B:32:0x0288, B:34:0x0290, B:36:0x02d1, B:38:0x02da, B:40:0x02f2, B:42:0x031b, B:44:0x031e, B:46:0x0326, B:48:0x033c, B:53:0x0340, B:52:0x0358, B:57:0x035b, B:60:0x01f7, B:62:0x01fd, B:65:0x0209, B:70:0x022f, B:71:0x025c, B:80:0x0267, B:81:0x026a), top: B:3:0x0003 }] */
    /* JADX WARN: Type inference failed for: r1v58, types: [int] */
    /* JADX WARN: Type inference failed for: r1v59 */
    /* JADX WARN: Type inference failed for: r1v63, types: [java.lang.AutoCloseable] */
    /* JADX WARN: Type inference failed for: r1v85 */
    /* JADX WARN: Type inference failed for: r1v86 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void write(com.android.modules.utils.TypedXmlSerializer r12, java.io.File r13) {
        /*
            Method dump skipped, instructions count: 870
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.PackageInstallerSession.write(com.android.modules.utils.TypedXmlSerializer, java.io.File):void");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to find 'out' block for switch in B:55:0x0286. Please report as an issue. */
    public static PackageInstallerSession readFromXml(TypedXmlPullParser typedXmlPullParser, PackageInstallerService.InternalCallback internalCallback, Context context, PackageManagerService packageManagerService, Looper looper, StagingManager stagingManager, File file, PackageSessionProvider packageSessionProvider, SilentUpdatePolicy silentUpdatePolicy) {
        int i;
        String str;
        int[] iArr;
        ArrayMap arrayMap;
        int i2;
        ArrayMap arrayMap2;
        String str2;
        IntArray intArray;
        int i3;
        int attributeInt = typedXmlPullParser.getAttributeInt((String) null, "sessionId");
        int attributeInt2 = typedXmlPullParser.getAttributeInt((String) null, "userId");
        String readStringAttribute = com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, "installerPackageName");
        int attributeInt3 = typedXmlPullParser.getAttributeInt((String) null, "installerPackageUid", -1);
        String readStringAttribute2 = com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, "updateOwnererPackageName");
        String readStringAttribute3 = com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, "installerAttributionTag");
        int attributeInt4 = typedXmlPullParser.getAttributeInt((String) null, "installerUid", packageManagerService.snapshotComputer().getPackageUid(readStringAttribute, 8192L, attributeInt2));
        String readStringAttribute4 = com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, "installInitiatingPackageName");
        String readStringAttribute5 = com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, "installOriginatingPackageName");
        long attributeLong = typedXmlPullParser.getAttributeLong((String) null, "createdMillis");
        typedXmlPullParser.getAttributeLong((String) null, "updatedMillis");
        long attributeLong2 = typedXmlPullParser.getAttributeLong((String) null, "committedMillis", 0L);
        String readStringAttribute6 = com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, "sessionStageDir");
        File file2 = readStringAttribute6 != null ? new File(readStringAttribute6) : null;
        String readStringAttribute7 = com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, "sessionStageCid");
        boolean attributeBoolean = typedXmlPullParser.getAttributeBoolean((String) null, "prepared", true);
        boolean attributeBoolean2 = typedXmlPullParser.getAttributeBoolean((String) null, "committed", false);
        boolean attributeBoolean3 = typedXmlPullParser.getAttributeBoolean((String) null, "destroyed", false);
        boolean attributeBoolean4 = typedXmlPullParser.getAttributeBoolean((String) null, "sealed", false);
        int attributeInt5 = typedXmlPullParser.getAttributeInt((String) null, "parentSessionId", -1);
        PackageInstaller.SessionParams sessionParams = new PackageInstaller.SessionParams(-1);
        sessionParams.isMultiPackage = typedXmlPullParser.getAttributeBoolean((String) null, "multiPackage", false);
        sessionParams.isStaged = typedXmlPullParser.getAttributeBoolean((String) null, "stagedSession", false);
        sessionParams.sessionFlags = typedXmlPullParser.getAttributeInt((String) null, "sessionFlags", 0);
        String str3 = "mode";
        sessionParams.mode = typedXmlPullParser.getAttributeInt((String) null, "mode");
        sessionParams.installFlags = typedXmlPullParser.getAttributeInt((String) null, "installFlags");
        sessionParams.installLocation = typedXmlPullParser.getAttributeInt((String) null, "installLocation");
        sessionParams.sizeBytes = typedXmlPullParser.getAttributeLong((String) null, "sizeBytes");
        sessionParams.appPackageName = com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, "appPackageName");
        sessionParams.appIcon = com.android.internal.util.XmlUtils.readBitmapAttribute(typedXmlPullParser, "appIcon");
        sessionParams.appLabel = com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, "appLabel");
        sessionParams.originatingUri = com.android.internal.util.XmlUtils.readUriAttribute(typedXmlPullParser, "originatingUri");
        sessionParams.originatingUid = typedXmlPullParser.getAttributeInt((String) null, "originatingUid", -1);
        sessionParams.referrerUri = com.android.internal.util.XmlUtils.readUriAttribute(typedXmlPullParser, "referrerUri");
        sessionParams.abiOverride = com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, "abiOverride");
        sessionParams.volumeUuid = com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, "volumeUuid");
        sessionParams.installReason = typedXmlPullParser.getAttributeInt((String) null, "installRason");
        sessionParams.packageSource = typedXmlPullParser.getAttributeInt((String) null, "packageSource");
        sessionParams.applicationEnabledSettingPersistent = typedXmlPullParser.getAttributeBoolean((String) null, "applicationEnabledSettingPersistent", false);
        if (typedXmlPullParser.getAttributeBoolean((String) null, "isDataLoader", false)) {
            i = attributeInt4;
            sessionParams.dataLoaderParams = new DataLoaderParams(typedXmlPullParser.getAttributeInt((String) null, "dataLoaderType"), new ComponentName(com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, "dataLoaderPackageName"), com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, "dataLoaderClassName")), com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, "dataLoaderArguments"));
        } else {
            i = attributeInt4;
        }
        File buildAppIconFile = buildAppIconFile(attributeInt, file);
        if (buildAppIconFile.exists()) {
            sessionParams.appIcon = BitmapFactory.decodeFile(buildAppIconFile.getAbsolutePath());
            sessionParams.appIconLastModified = buildAppIconFile.lastModified();
        }
        boolean attributeBoolean5 = typedXmlPullParser.getAttributeBoolean((String) null, "isReady", false);
        boolean attributeBoolean6 = typedXmlPullParser.getAttributeBoolean((String) null, "isFailed", false);
        boolean attributeBoolean7 = typedXmlPullParser.getAttributeBoolean((String) null, "isApplied", false);
        int attributeInt6 = typedXmlPullParser.getAttributeInt((String) null, "errorCode", 0);
        String readStringAttribute8 = com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, "errorMessage");
        if (!isStagedSessionStateValid(attributeBoolean5, attributeBoolean7, attributeBoolean6)) {
            throw new IllegalArgumentException("Can't restore staged session with invalid state.");
        }
        ArrayList arrayList = new ArrayList();
        ArraySet arraySet = new ArraySet();
        ArraySet arraySet2 = new ArraySet();
        ArrayList arrayList2 = new ArrayList();
        IntArray intArray2 = new IntArray();
        ArrayList arrayList3 = new ArrayList();
        ArrayMap arrayMap3 = new ArrayMap();
        ArrayMap arrayMap4 = new ArrayMap();
        int depth = typedXmlPullParser.getDepth();
        int i4 = 3;
        int i5 = 3;
        while (true) {
            int next = typedXmlPullParser.next();
            str = readStringAttribute4;
            if (next != 1 && (next != i4 || typedXmlPullParser.getDepth() > depth)) {
                if (next == i4 || next == 4) {
                    i2 = depth;
                    arrayMap2 = arrayMap3;
                    str2 = str3;
                    intArray = intArray2;
                } else {
                    String name = typedXmlPullParser.getName();
                    name.hashCode();
                    switch (name.hashCode()) {
                        case -1558680102:
                            if (name.equals("childSession")) {
                                i3 = 0;
                                break;
                            }
                            break;
                        case -1361644609:
                            if (name.equals("sessionChecksumSignature")) {
                                i3 = 1;
                                break;
                            }
                            break;
                        case -606495946:
                            if (name.equals("granted-runtime-permission")) {
                                i3 = 2;
                                break;
                            }
                            break;
                        case -22892238:
                            if (name.equals("sessionFile")) {
                                i3 = i4;
                                break;
                            }
                            break;
                        case 158177050:
                            if (name.equals("whitelisted-restricted-permission")) {
                                i3 = 4;
                                break;
                            }
                            break;
                        case 641551609:
                            if (name.equals("sessionChecksum")) {
                                i3 = 5;
                                break;
                            }
                            break;
                        case 1529564053:
                            if (name.equals("auto-revoke-permissions-mode")) {
                                i3 = 6;
                                break;
                            }
                            break;
                        case 1620305696:
                            if (name.equals("grant-permission")) {
                                i3 = 7;
                                break;
                            }
                            break;
                        case 1658008624:
                            if (name.equals("deny-permission")) {
                                i3 = 8;
                                break;
                            }
                            break;
                    }
                    i3 = -1;
                    switch (i3) {
                        case 0:
                            i2 = depth;
                            arrayMap2 = arrayMap3;
                            str2 = str3;
                            intArray = intArray2;
                            intArray.add(typedXmlPullParser.getAttributeInt((String) null, "sessionId", -1));
                            break;
                        case 1:
                            i2 = depth;
                            arrayMap2 = arrayMap3;
                            str2 = str3;
                            intArray = intArray2;
                            arrayMap4.put(com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, "name"), com.android.internal.util.XmlUtils.readByteArrayAttribute(typedXmlPullParser, "signature"));
                            break;
                        case 2:
                            i2 = depth;
                            arrayMap2 = arrayMap3;
                            str2 = str3;
                            intArray = intArray2;
                            arrayList.add(com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, "name"));
                            break;
                        case 3:
                            i2 = depth;
                            str2 = str3;
                            arrayMap2 = arrayMap3;
                            intArray = intArray2;
                            arrayList3.add(new InstallationFile(typedXmlPullParser.getAttributeInt((String) null, "location", 0), com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, "name"), typedXmlPullParser.getAttributeLong((String) null, "lengthBytes", -1L), com.android.internal.util.XmlUtils.readByteArrayAttribute(typedXmlPullParser, "metadata"), com.android.internal.util.XmlUtils.readByteArrayAttribute(typedXmlPullParser, "signature")));
                            break;
                        case 4:
                            i2 = depth;
                            str2 = str3;
                            arrayList2.add(com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, "name"));
                            arrayMap2 = arrayMap3;
                            intArray = intArray2;
                            break;
                        case 5:
                            String readStringAttribute9 = com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, "name");
                            i2 = depth;
                            str2 = str3;
                            Checksum checksum = new Checksum(typedXmlPullParser.getAttributeInt((String) null, "checksumKind", 0), com.android.internal.util.XmlUtils.readByteArrayAttribute(typedXmlPullParser, "checksumValue"));
                            List list = (List) arrayMap3.get(readStringAttribute9);
                            if (list == null) {
                                list = new ArrayList();
                                arrayMap3.put(readStringAttribute9, list);
                            }
                            list.add(checksum);
                            arrayMap2 = arrayMap3;
                            intArray = intArray2;
                            break;
                        case 6:
                            i5 = typedXmlPullParser.getAttributeInt((String) null, str3);
                            i2 = depth;
                            arrayMap2 = arrayMap3;
                            str2 = str3;
                            intArray = intArray2;
                            break;
                        case 7:
                            arraySet.add(com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, "name"));
                            i2 = depth;
                            arrayMap2 = arrayMap3;
                            str2 = str3;
                            intArray = intArray2;
                            break;
                        case 8:
                            arraySet2.add(com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, "name"));
                            i2 = depth;
                            arrayMap2 = arrayMap3;
                            str2 = str3;
                            intArray = intArray2;
                            break;
                        default:
                            i2 = depth;
                            arrayMap2 = arrayMap3;
                            str2 = str3;
                            intArray = intArray2;
                            break;
                    }
                    intArray2 = intArray;
                    arrayMap3 = arrayMap2;
                    readStringAttribute4 = str;
                    depth = i2;
                    str3 = str2;
                    i4 = 3;
                }
                intArray2 = intArray;
                arrayMap3 = arrayMap2;
                readStringAttribute4 = str;
                depth = i2;
                str3 = str2;
                i4 = 3;
            }
        }
        ArrayMap arrayMap5 = arrayMap3;
        IntArray intArray3 = intArray2;
        if (arrayList.size() > 0) {
            sessionParams.setPermissionStates(arrayList, Collections.emptyList());
        } else {
            sessionParams.setPermissionStates(arraySet, arraySet2);
        }
        if (arrayList2.size() > 0) {
            sessionParams.whitelistedRestrictedPermissions = arrayList2;
        }
        sessionParams.autoRevokePermissionsMode = i5;
        if (intArray3.size() > 0) {
            iArr = new int[intArray3.size()];
            int size = intArray3.size();
            for (int i6 = 0; i6 < size; i6++) {
                iArr[i6] = intArray3.get(i6);
            }
        } else {
            iArr = EMPTY_CHILD_SESSION_ARRAY;
        }
        int[] iArr2 = iArr;
        InstallationFile[] installationFileArr = !arrayList3.isEmpty() ? (InstallationFile[]) arrayList3.toArray(EMPTY_INSTALLATION_FILE_ARRAY) : null;
        if (arrayMap5.isEmpty()) {
            arrayMap = null;
        } else {
            ArrayMap arrayMap6 = new ArrayMap(arrayMap5.size());
            int size2 = arrayMap5.size();
            for (int i7 = 0; i7 < size2; i7++) {
                String str4 = (String) arrayMap5.keyAt(i7);
                List list2 = (List) arrayMap5.valueAt(i7);
                arrayMap6.put(str4, new PerFileChecksum((Checksum[]) list2.toArray(new Checksum[list2.size()]), (byte[]) arrayMap4.get(str4)));
            }
            arrayMap = arrayMap6;
        }
        return new PackageInstallerSession(internalCallback, context, packageManagerService, packageSessionProvider, silentUpdatePolicy, looper, stagingManager, attributeInt, attributeInt2, i, InstallSource.create(str, readStringAttribute5, readStringAttribute, attributeInt3, readStringAttribute2, readStringAttribute3, sessionParams.packageSource), sessionParams, attributeLong, attributeLong2, file2, readStringAttribute7, installationFileArr, arrayMap, attributeBoolean, attributeBoolean2, attributeBoolean3, attributeBoolean4, iArr2, attributeInt5, attributeBoolean5, attributeBoolean6, attributeBoolean7, attributeInt6, readStringAttribute8);
    }

    public final boolean isAttemptSamsungThemeForgery(String str) {
        if (!SemSamsungThemeUtils.isThemeCenter(str) || this.mPm.snapshotComputer().getPackageInfo(str, 0L, 0) == null) {
            return this.mInstallerUid == 2000 && SemSamsungThemeUtils.hasThemeParkPrefix(str);
        }
        return true;
    }
}
