package com.android.server.backup.restore;

import android.app.IActivityManager;
import android.app.IBackupAgent;
import android.app.backup.BackupDataInput;
import android.app.backup.BackupDataOutput;
import android.app.backup.IBackupManagerMonitor;
import android.app.backup.IRestoreObserver;
import android.app.backup.RestoreDescription;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManagerInternal;
import android.os.Bundle;
import android.os.IInstalld;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserHandle;
import android.util.EventLog;
import android.util.Slog;
import com.android.server.AppWidgetBackupBridge;
import com.android.server.LocalServices;
import com.android.server.backup.BackupAgentTimeoutParameters;
import com.android.server.backup.BackupAndRestoreFeatureFlags;
import com.android.server.backup.BackupRestoreTask;
import com.android.server.backup.BackupUtils;
import com.android.server.backup.OperationStorage;
import com.android.server.backup.PackageManagerBackupAgent;
import com.android.server.backup.TransportManager;
import com.android.server.backup.UserBackupManagerService;
import com.android.server.backup.internal.OnTaskFinishedListener;
import com.android.server.backup.keyvalue.KeyValueBackupTask;
import com.android.server.backup.transport.BackupTransportClient;
import com.android.server.backup.transport.TransportConnection;
import com.android.server.backup.utils.BackupEligibilityRules;
import com.android.server.backup.utils.BackupManagerMonitorUtils;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import libcore.io.IoUtils;

/* loaded from: classes.dex */
public class PerformUnifiedRestoreTask implements BackupRestoreTask {
    public UserBackupManagerService backupManagerService;
    public List mAcceptSet;
    public IBackupAgent mAgent;
    public final BackupAgentTimeoutParameters mAgentTimeoutParameters;
    public ParcelFileDescriptor mBackupData;
    public File mBackupDataName;
    public final BackupEligibilityRules mBackupEligibilityRules;
    public int mCount;
    public PackageInfo mCurrentPackage;
    public boolean mDidLaunch;
    public final int mEphemeralOpToken;
    public boolean mFinished;
    public boolean mIsSystemRestore;
    public final OnTaskFinishedListener mListener;
    public IBackupManagerMonitor mMonitor;
    public ParcelFileDescriptor mNewState;
    public File mNewStateName;
    public IRestoreObserver mObserver;
    public final OperationStorage mOperationStorage;
    public PackageManagerBackupAgent mPmAgent;
    public int mPmToken;
    public RestoreDescription mRestoreDescription;
    public File mStageName;
    public long mStartRealtime;
    public UnifiedRestoreState mState;
    public File mStateDir;
    public int mStatus;
    public PackageInfo mTargetPackage;
    public long mToken;
    public final TransportConnection mTransportConnection;
    public final TransportManager mTransportManager;
    public final int mUserId;
    public byte[] mWidgetData;

    public PerformUnifiedRestoreTask(UserBackupManagerService userBackupManagerService, TransportConnection transportConnection) {
        this.mListener = null;
        this.mAgentTimeoutParameters = null;
        this.mOperationStorage = null;
        this.mTransportConnection = transportConnection;
        this.mTransportManager = null;
        this.mEphemeralOpToken = 0;
        this.mUserId = 0;
        this.mBackupEligibilityRules = null;
        this.backupManagerService = userBackupManagerService;
    }

    public PerformUnifiedRestoreTask(UserBackupManagerService userBackupManagerService, OperationStorage operationStorage, TransportConnection transportConnection, IRestoreObserver iRestoreObserver, IBackupManagerMonitor iBackupManagerMonitor, long j, PackageInfo packageInfo, int i, boolean z, String[] strArr, OnTaskFinishedListener onTaskFinishedListener, BackupEligibilityRules backupEligibilityRules) {
        this.backupManagerService = userBackupManagerService;
        this.mOperationStorage = operationStorage;
        int userId = userBackupManagerService.getUserId();
        this.mUserId = userId;
        this.mTransportManager = userBackupManagerService.getTransportManager();
        this.mEphemeralOpToken = userBackupManagerService.generateRandomIntegerToken();
        this.mState = UnifiedRestoreState.INITIAL;
        this.mStartRealtime = SystemClock.elapsedRealtime();
        this.mTransportConnection = transportConnection;
        this.mObserver = iRestoreObserver;
        this.mMonitor = iBackupManagerMonitor;
        this.mToken = j;
        this.mPmToken = i;
        this.mTargetPackage = packageInfo;
        this.mIsSystemRestore = z;
        this.mFinished = false;
        this.mDidLaunch = false;
        this.mListener = onTaskFinishedListener;
        BackupAgentTimeoutParameters agentTimeoutParameters = userBackupManagerService.getAgentTimeoutParameters();
        Objects.requireNonNull(agentTimeoutParameters, "Timeout parameters cannot be null");
        this.mAgentTimeoutParameters = agentTimeoutParameters;
        this.mBackupEligibilityRules = backupEligibilityRules;
        if (packageInfo != null) {
            ArrayList arrayList = new ArrayList();
            this.mAcceptSet = arrayList;
            arrayList.add(packageInfo);
        } else {
            if (strArr == null) {
                strArr = packagesToNames(PackageManagerBackupAgent.getStorableApplications(userBackupManagerService.getPackageManager(), userId, backupEligibilityRules));
                Slog.i("BackupManagerService", "Full restore; asking about " + strArr.length + " apps");
            }
            this.mAcceptSet = new ArrayList(strArr.length);
            boolean z2 = false;
            boolean z3 = false;
            for (String str : strArr) {
                try {
                    PackageInfo packageInfoAsUser = userBackupManagerService.getPackageManager().getPackageInfoAsUser(str, 0, this.mUserId);
                    if ("android".equals(packageInfoAsUser.packageName)) {
                        z2 = true;
                    } else if ("com.android.providers.settings".equals(packageInfoAsUser.packageName)) {
                        z3 = true;
                    } else if (backupEligibilityRules.appIsEligibleForBackup(packageInfoAsUser.applicationInfo)) {
                        this.mAcceptSet.add(packageInfoAsUser);
                    }
                } catch (PackageManager.NameNotFoundException unused) {
                }
            }
            if (z2) {
                try {
                    this.mAcceptSet.add(0, userBackupManagerService.getPackageManager().getPackageInfoAsUser("android", 0, this.mUserId));
                } catch (PackageManager.NameNotFoundException unused2) {
                }
            }
            if (z3) {
                try {
                    this.mAcceptSet.add(userBackupManagerService.getPackageManager().getPackageInfoAsUser("com.android.providers.settings", 0, this.mUserId));
                } catch (PackageManager.NameNotFoundException unused3) {
                }
            }
        }
        this.mAcceptSet = userBackupManagerService.filterUserFacingPackages(this.mAcceptSet);
    }

    public final String[] packagesToNames(List list) {
        int size = list.size();
        String[] strArr = new String[size];
        for (int i = 0; i < size; i++) {
            strArr[i] = ((PackageInfo) list.get(i)).packageName;
        }
        return strArr;
    }

    /* renamed from: com.android.server.backup.restore.PerformUnifiedRestoreTask$1, reason: invalid class name */
    /* loaded from: classes.dex */
    public abstract /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$android$server$backup$restore$UnifiedRestoreState;

        static {
            int[] iArr = new int[UnifiedRestoreState.values().length];
            $SwitchMap$com$android$server$backup$restore$UnifiedRestoreState = iArr;
            try {
                iArr[UnifiedRestoreState.INITIAL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$android$server$backup$restore$UnifiedRestoreState[UnifiedRestoreState.RUNNING_QUEUE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$android$server$backup$restore$UnifiedRestoreState[UnifiedRestoreState.RESTORE_KEYVALUE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$android$server$backup$restore$UnifiedRestoreState[UnifiedRestoreState.RESTORE_FULL.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$android$server$backup$restore$UnifiedRestoreState[UnifiedRestoreState.RESTORE_FINISHED.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$android$server$backup$restore$UnifiedRestoreState[UnifiedRestoreState.FINAL.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    @Override // com.android.server.backup.BackupRestoreTask
    public void execute() {
        switch (AnonymousClass1.$SwitchMap$com$android$server$backup$restore$UnifiedRestoreState[this.mState.ordinal()]) {
            case 1:
                startRestore();
                return;
            case 2:
                dispatchNextRestore();
                return;
            case 3:
                restoreKeyValue();
                return;
            case 4:
                restoreFull();
                return;
            case 5:
                restoreFinished();
                return;
            case 6:
                if (!this.mFinished) {
                    finalizeRestore();
                } else {
                    Slog.e("BackupManagerService", "Duplicate finish");
                }
                this.mFinished = true;
                return;
            default:
                return;
        }
    }

    public final void startRestore() {
        sendStartRestore(this.mAcceptSet.size());
        if (this.mIsSystemRestore) {
            AppWidgetBackupBridge.systemRestoreStarting(this.mUserId);
        }
        try {
            this.mStateDir = new File(this.backupManagerService.getBaseStateDir(), this.mTransportManager.getTransportDirName(this.mTransportConnection.getTransportComponent()));
            PackageInfo packageInfo = new PackageInfo();
            packageInfo.packageName = "@pm@";
            this.mAcceptSet.add(0, packageInfo);
            PackageInfo[] packageInfoArr = (PackageInfo[]) this.mAcceptSet.toArray(new PackageInfo[0]);
            BackupTransportClient connectOrThrow = this.mTransportConnection.connectOrThrow("PerformUnifiedRestoreTask.startRestore()");
            if (this.mMonitor == null) {
                this.mMonitor = connectOrThrow.getBackupManagerMonitor();
            }
            int startRestore = connectOrThrow.startRestore(this.mToken, packageInfoArr);
            this.mStatus = startRestore;
            if (startRestore != 0) {
                Slog.e("BackupManagerService", "Transport error " + this.mStatus + "; no restore possible");
                this.mStatus = -1000;
                executeNextState(UnifiedRestoreState.FINAL);
                return;
            }
            RestoreDescription nextRestorePackage = connectOrThrow.nextRestorePackage();
            if (nextRestorePackage == null) {
                Slog.e("BackupManagerService", "No restore metadata available; halting");
                this.mMonitor = BackupManagerMonitorUtils.monitorEvent(this.mMonitor, 22, this.mCurrentPackage, 3, null);
                this.mStatus = -1000;
                executeNextState(UnifiedRestoreState.FINAL);
                return;
            }
            if (!"@pm@".equals(nextRestorePackage.getPackageName())) {
                Slog.e("BackupManagerService", "Required package metadata but got " + nextRestorePackage.getPackageName());
                this.mMonitor = BackupManagerMonitorUtils.monitorEvent(this.mMonitor, 23, this.mCurrentPackage, 3, null);
                this.mStatus = -1000;
                executeNextState(UnifiedRestoreState.FINAL);
                return;
            }
            PackageInfo packageInfo2 = new PackageInfo();
            this.mCurrentPackage = packageInfo2;
            packageInfo2.packageName = "@pm@";
            packageInfo2.applicationInfo = new ApplicationInfo();
            this.mCurrentPackage.applicationInfo.uid = 1000;
            PackageManagerBackupAgent makeMetadataAgent = this.backupManagerService.makeMetadataAgent(null);
            this.mPmAgent = makeMetadataAgent;
            this.mAgent = IBackupAgent.Stub.asInterface(makeMetadataAgent.onBind());
            initiateOneRestore(this.mCurrentPackage, 0L);
            this.backupManagerService.getBackupHandler().removeMessages(18);
            if (this.mPmAgent.hasMetadata()) {
                return;
            }
            Slog.e("BackupManagerService", "PM agent has no metadata, so not restoring");
            this.mMonitor = BackupManagerMonitorUtils.monitorEvent(this.mMonitor, 24, this.mCurrentPackage, 3, null);
            EventLog.writeEvent(2832, "@pm@", "Package manager restore metadata missing");
            this.mStatus = -1000;
            this.backupManagerService.getBackupHandler().removeMessages(20, this);
            executeNextState(UnifiedRestoreState.FINAL);
        } catch (Exception e) {
            Slog.e("BackupManagerService", "Unable to contact transport for restore: " + e.getMessage());
            this.mMonitor = BackupManagerMonitorUtils.monitorEvent(this.mMonitor, 25, null, 1, null);
            this.mStatus = -1000;
            this.backupManagerService.getBackupHandler().removeMessages(20, this);
            executeNextState(UnifiedRestoreState.FINAL);
        }
    }

    public final void dispatchNextRestore() {
        UnifiedRestoreState unifiedRestoreState;
        UnifiedRestoreState unifiedRestoreState2 = UnifiedRestoreState.FINAL;
        try {
            RestoreDescription nextRestorePackage = this.mTransportConnection.connectOrThrow("PerformUnifiedRestoreTask.dispatchNextRestore()").nextRestorePackage();
            this.mRestoreDescription = nextRestorePackage;
            String packageName = nextRestorePackage != null ? nextRestorePackage.getPackageName() : null;
            if (packageName == null) {
                Slog.e("BackupManagerService", "Failure getting next package name");
                EventLog.writeEvent(2831, new Object[0]);
                return;
            }
            if (this.mRestoreDescription == RestoreDescription.NO_MORE_PACKAGES) {
                Slog.v("BackupManagerService", "No more packages; finishing restore");
                EventLog.writeEvent(2834, Integer.valueOf(this.mCount), Integer.valueOf((int) (SystemClock.elapsedRealtime() - this.mStartRealtime)));
                return;
            }
            Slog.i("BackupManagerService", "Next restore package: " + this.mRestoreDescription);
            sendOnRestorePackage(packageName);
            PackageManagerBackupAgent.Metadata restoredMetadata = this.mPmAgent.getRestoredMetadata(packageName);
            if (restoredMetadata == null) {
                Slog.e("BackupManagerService", "No metadata for " + packageName);
                EventLog.writeEvent(2832, packageName, "Package metadata missing");
                executeNextState(UnifiedRestoreState.RUNNING_QUEUE);
                return;
            }
            try {
                PackageInfo packageInfoAsUser = this.backupManagerService.getPackageManager().getPackageInfoAsUser(packageName, 134217728, this.mUserId);
                this.mCurrentPackage = packageInfoAsUser;
                if (restoredMetadata.versionCode > packageInfoAsUser.getLongVersionCode()) {
                    if ((this.mCurrentPackage.applicationInfo.flags & IInstalld.FLAG_CLEAR_APP_DATA_KEEP_ART_PROFILES) == 0) {
                        String str = "Source version " + restoredMetadata.versionCode + " > installed version " + this.mCurrentPackage.getLongVersionCode();
                        Slog.w("BackupManagerService", "Package " + packageName + ": " + str);
                        this.mMonitor = BackupManagerMonitorUtils.monitorEvent(this.mMonitor, 27, this.mCurrentPackage, 3, BackupManagerMonitorUtils.putMonitoringExtra(BackupManagerMonitorUtils.putMonitoringExtra((Bundle) null, "android.app.backup.extra.LOG_RESTORE_VERSION", restoredMetadata.versionCode), "android.app.backup.extra.LOG_RESTORE_ANYWAY", false));
                        EventLog.writeEvent(2832, packageName, str);
                        executeNextState(UnifiedRestoreState.RUNNING_QUEUE);
                        return;
                    }
                    Slog.v("BackupManagerService", "Source version " + restoredMetadata.versionCode + " > installed version " + this.mCurrentPackage.getLongVersionCode() + " but restoreAnyVersion");
                    this.mMonitor = BackupManagerMonitorUtils.monitorEvent(this.mMonitor, 27, this.mCurrentPackage, 3, BackupManagerMonitorUtils.putMonitoringExtra(BackupManagerMonitorUtils.putMonitoringExtra((Bundle) null, "android.app.backup.extra.LOG_RESTORE_VERSION", restoredMetadata.versionCode), "android.app.backup.extra.LOG_RESTORE_ANYWAY", true));
                }
                this.mWidgetData = null;
                int dataType = this.mRestoreDescription.getDataType();
                if (dataType == 1) {
                    unifiedRestoreState = UnifiedRestoreState.RESTORE_KEYVALUE;
                } else {
                    if (dataType != 2) {
                        Slog.e("BackupManagerService", "Unrecognized restore type " + dataType);
                        executeNextState(UnifiedRestoreState.RUNNING_QUEUE);
                        return;
                    }
                    unifiedRestoreState = UnifiedRestoreState.RESTORE_FULL;
                }
                executeNextState(unifiedRestoreState);
            } catch (PackageManager.NameNotFoundException unused) {
                Slog.e("BackupManagerService", "Package not present: " + packageName);
                this.mMonitor = BackupManagerMonitorUtils.monitorEvent(this.mMonitor, 26, this.mCurrentPackage, 3, null);
                EventLog.writeEvent(2832, packageName, "Package missing on device");
                executeNextState(UnifiedRestoreState.RUNNING_QUEUE);
            }
        } catch (Exception e) {
            Slog.e("BackupManagerService", "Can't get next restore target from transport; halting: " + e.getMessage());
            EventLog.writeEvent(2831, new Object[0]);
            unifiedRestoreState2 = UnifiedRestoreState.FINAL;
        } finally {
            executeNextState(unifiedRestoreState2);
        }
    }

    public final void restoreKeyValue() {
        PackageInfo packageInfo = this.mCurrentPackage;
        String str = packageInfo.packageName;
        String str2 = packageInfo.applicationInfo.backupAgentName;
        if (str2 == null || "".equals(str2)) {
            this.mMonitor = BackupManagerMonitorUtils.monitorEvent(this.mMonitor, 28, this.mCurrentPackage, 2, null);
            EventLog.writeEvent(2832, str, "Package has no agent");
            executeNextState(UnifiedRestoreState.RUNNING_QUEUE);
            return;
        }
        PackageManagerBackupAgent.Metadata restoredMetadata = this.mPmAgent.getRestoredMetadata(str);
        if (!BackupUtils.signaturesMatch(restoredMetadata.sigHashes, this.mCurrentPackage, (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class))) {
            Slog.w("BackupManagerService", "Signature mismatch restoring " + str);
            this.mMonitor = BackupManagerMonitorUtils.monitorEvent(this.mMonitor, 29, this.mCurrentPackage, 3, null);
            EventLog.writeEvent(2832, str, "Signature mismatch");
            executeNextState(UnifiedRestoreState.RUNNING_QUEUE);
            return;
        }
        IBackupAgent bindToAgentSynchronous = this.backupManagerService.bindToAgentSynchronous(this.mCurrentPackage.applicationInfo, 2, this.mBackupEligibilityRules.getBackupDestination());
        this.mAgent = bindToAgentSynchronous;
        if (bindToAgentSynchronous == null) {
            Slog.w("BackupManagerService", "Can't find backup agent for " + str);
            this.mMonitor = BackupManagerMonitorUtils.monitorEvent(this.mMonitor, 30, this.mCurrentPackage, 3, null);
            EventLog.writeEvent(2832, str, "Restore agent missing");
            executeNextState(UnifiedRestoreState.RUNNING_QUEUE);
            return;
        }
        this.mDidLaunch = true;
        try {
            initiateOneRestore(this.mCurrentPackage, restoredMetadata.versionCode);
            this.mCount++;
        } catch (Exception e) {
            Slog.e("BackupManagerService", "Error when attempting restore: " + e.toString());
            keyValueAgentErrorCleanup(false);
            executeNextState(UnifiedRestoreState.RUNNING_QUEUE);
        }
    }

    public final void initiateOneRestore(PackageInfo packageInfo, long j) {
        UnifiedRestoreState unifiedRestoreState;
        String str = packageInfo.packageName;
        Slog.d("BackupManagerService", "initiateOneRestore packageName=" + str);
        this.mBackupDataName = new File(this.backupManagerService.getDataDir(), str + ".restore");
        this.mStageName = new File(this.backupManagerService.getDataDir(), str + ".stage");
        this.mNewStateName = new File(this.mStateDir, str + KeyValueBackupTask.NEW_STATE_FILE_SUFFIX);
        boolean shouldStageBackupData = shouldStageBackupData(str);
        File file = shouldStageBackupData ? this.mStageName : this.mBackupDataName;
        try {
            BackupTransportClient connectOrThrow = this.mTransportConnection.connectOrThrow("PerformUnifiedRestoreTask.initiateOneRestore()");
            ParcelFileDescriptor open = ParcelFileDescriptor.open(file, 1006632960);
            if (connectOrThrow.getRestoreData(open) != 0) {
                Slog.e("BackupManagerService", "Error getting restore data for " + str);
                EventLog.writeEvent(2831, new Object[0]);
                open.close();
                file.delete();
                if (BackupAndRestoreFeatureFlags.getUnifiedRestoreContinueAfterTransportFailureInKvRestore()) {
                    unifiedRestoreState = UnifiedRestoreState.RUNNING_QUEUE;
                } else {
                    unifiedRestoreState = UnifiedRestoreState.FINAL;
                }
                executeNextState(unifiedRestoreState);
                return;
            }
            if (shouldStageBackupData) {
                open.close();
                open = ParcelFileDescriptor.open(file, 268435456);
                this.mBackupData = ParcelFileDescriptor.open(this.mBackupDataName, 1006632960);
                filterExcludedKeys(str, new BackupDataInput(open.getFileDescriptor()), new BackupDataOutput(this.mBackupData.getFileDescriptor()));
                this.mBackupData.close();
            }
            open.close();
            this.mBackupData = ParcelFileDescriptor.open(this.mBackupDataName, 268435456);
            this.mNewState = ParcelFileDescriptor.open(this.mNewStateName, 1006632960);
            this.backupManagerService.prepareOperationTimeout(this.mEphemeralOpToken, this.mAgentTimeoutParameters.getRestoreAgentTimeoutMillis(packageInfo.applicationInfo.uid), this, 1);
            this.mAgent.doRestoreWithExcludedKeys(this.mBackupData, j, this.mNewState, this.mEphemeralOpToken, this.backupManagerService.getBackupManagerBinder(), new ArrayList(getExcludedKeysForPackage(str)));
        } catch (Exception e) {
            Slog.e("BackupManagerService", "Unable to call app for restore: " + str, e);
            EventLog.writeEvent(2832, str, e.toString());
            keyValueAgentErrorCleanup(false);
            executeNextState(UnifiedRestoreState.RUNNING_QUEUE);
        }
    }

    public boolean shouldStageBackupData(String str) {
        return (str.equals("android") && getExcludedKeysForPackage("android").isEmpty()) ? false : true;
    }

    public Set getExcludedKeysForPackage(String str) {
        return this.backupManagerService.getExcludedRestoreKeys(str);
    }

    public void filterExcludedKeys(String str, BackupDataInput backupDataInput, BackupDataOutput backupDataOutput) {
        Set excludedKeysForPackage = getExcludedKeysForPackage(str);
        byte[] bArr = new byte[IInstalld.FLAG_FORCE];
        while (backupDataInput.readNextHeader()) {
            String key = backupDataInput.getKey();
            int dataSize = backupDataInput.getDataSize();
            if (excludedKeysForPackage != null && excludedKeysForPackage.contains(key)) {
                Slog.i("BackupManagerService", "Skipping blocked key " + key);
                backupDataInput.skipEntityData();
            } else if (key.equals("￭￭widget")) {
                Slog.i("BackupManagerService", "Restoring widget state for " + str);
                byte[] bArr2 = new byte[dataSize];
                this.mWidgetData = bArr2;
                backupDataInput.readEntityData(bArr2, 0, dataSize);
            } else {
                if (dataSize > bArr.length) {
                    bArr = new byte[dataSize];
                }
                backupDataInput.readEntityData(bArr, 0, dataSize);
                backupDataOutput.writeEntityHeader(key, dataSize);
                backupDataOutput.writeEntityData(bArr, dataSize);
            }
        }
    }

    public final void restoreFull() {
        try {
            new Thread(new StreamFeederThread(), "unified-stream-feeder").start();
        } catch (IOException unused) {
            Slog.e("BackupManagerService", "Unable to construct pipes for stream restore!");
            executeNextState(UnifiedRestoreState.RUNNING_QUEUE);
        }
    }

    public final void restoreFinished() {
        Slog.d("BackupManagerService", "restoreFinished packageName=" + this.mCurrentPackage.packageName);
        try {
            this.backupManagerService.prepareOperationTimeout(this.mEphemeralOpToken, this.mAgentTimeoutParameters.getRestoreAgentFinishedTimeoutMillis(), this, 1);
            this.mAgent.doRestoreFinished(this.mEphemeralOpToken, this.backupManagerService.getBackupManagerBinder());
        } catch (Exception e) {
            String str = this.mCurrentPackage.packageName;
            Slog.e("BackupManagerService", "Unable to finalize restore of " + str);
            EventLog.writeEvent(2832, str, e.toString());
            keyValueAgentErrorCleanup(true);
            executeNextState(UnifiedRestoreState.RUNNING_QUEUE);
        }
    }

    /* loaded from: classes.dex */
    public class StreamFeederThread extends RestoreEngine implements Runnable, BackupRestoreTask {
        public FullRestoreEngine mEngine;
        public FullRestoreEngineThread mEngineThread;
        public final int mEphemeralOpToken;
        public final String TAG = "StreamFeederThread";
        public ParcelFileDescriptor[] mTransportPipes = ParcelFileDescriptor.createPipe();
        public ParcelFileDescriptor[] mEnginePipes = ParcelFileDescriptor.createPipe();

        @Override // com.android.server.backup.BackupRestoreTask
        public void execute() {
        }

        @Override // com.android.server.backup.BackupRestoreTask
        public void operationComplete(long j) {
        }

        public StreamFeederThread() {
            this.mEphemeralOpToken = PerformUnifiedRestoreTask.this.backupManagerService.generateRandomIntegerToken();
            setRunning(true);
        }

        /* JADX WARN: Code restructure failed: missing block: B:35:0x015f, code lost:
        
            if (r11 == (-1000)) goto L33;
         */
        /* JADX WARN: Code restructure failed: missing block: B:37:0x0164, code lost:
        
            r0 = com.android.server.backup.restore.UnifiedRestoreState.RUNNING_QUEUE;
         */
        /* JADX WARN: Code restructure failed: missing block: B:53:0x0288, code lost:
        
            if (r0 != 64536) goto L34;
         */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:77:0x02bc  */
        /* JADX WARN: Removed duplicated region for block: B:80:0x0304  */
        /* JADX WARN: Removed duplicated region for block: B:83:0x02c2 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Type inference failed for: r11v16 */
        /* JADX WARN: Type inference failed for: r11v5 */
        /* JADX WARN: Type inference failed for: r11v6 */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void run() {
            /*
                Method dump skipped, instructions count: 805
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.backup.restore.PerformUnifiedRestoreTask.StreamFeederThread.run():void");
        }

        @Override // com.android.server.backup.BackupRestoreTask
        public void handleCancel(boolean z) {
            PerformUnifiedRestoreTask.this.mOperationStorage.removeOperation(this.mEphemeralOpToken);
            Slog.w("StreamFeederThread", "Full-data restore target timed out; shutting down");
            PerformUnifiedRestoreTask performUnifiedRestoreTask = PerformUnifiedRestoreTask.this;
            performUnifiedRestoreTask.mMonitor = BackupManagerMonitorUtils.monitorEvent(performUnifiedRestoreTask.mMonitor, 45, PerformUnifiedRestoreTask.this.mCurrentPackage, 2, null);
            this.mEngineThread.handleTimeout();
            IoUtils.closeQuietly(this.mEnginePipes[1]);
            ParcelFileDescriptor[] parcelFileDescriptorArr = this.mEnginePipes;
            parcelFileDescriptorArr[1] = null;
            IoUtils.closeQuietly(parcelFileDescriptorArr[0]);
            this.mEnginePipes[0] = null;
        }
    }

    public final void finalizeRestore() {
        PackageManagerBackupAgent packageManagerBackupAgent;
        try {
            this.mTransportConnection.connectOrThrow("PerformUnifiedRestoreTask.finalizeRestore()").finishRestore();
        } catch (Exception e) {
            Slog.e("BackupManagerService", "Error finishing restore", e);
        }
        IRestoreObserver iRestoreObserver = this.mObserver;
        if (iRestoreObserver != null) {
            try {
                iRestoreObserver.restoreFinished(this.mStatus);
            } catch (RemoteException unused) {
                Slog.d("BackupManagerService", "Restore observer died at restoreFinished");
            }
        }
        this.backupManagerService.getBackupHandler().removeMessages(8);
        if (this.mPmToken > 0) {
            try {
                this.backupManagerService.getPackageManagerBinder().finishPackageInstall(this.mPmToken, this.mDidLaunch);
            } catch (RemoteException unused2) {
            }
        } else {
            this.backupManagerService.getBackupHandler().sendEmptyMessageDelayed(8, this.mAgentTimeoutParameters.getRestoreSessionTimeoutMillis());
        }
        if (this.mIsSystemRestore) {
            AppWidgetBackupBridge.systemRestoreFinished(this.mUserId);
        }
        if (this.mIsSystemRestore && (packageManagerBackupAgent = this.mPmAgent) != null) {
            this.backupManagerService.setAncestralPackages(packageManagerBackupAgent.getRestoredPackages());
            this.backupManagerService.setAncestralToken(this.mToken);
            this.backupManagerService.setAncestralBackupDestination(this.mBackupEligibilityRules.getBackupDestination());
            this.backupManagerService.writeRestoreTokens();
        }
        synchronized (this.backupManagerService.getPendingRestores()) {
            if (this.backupManagerService.getPendingRestores().size() > 0) {
                Slog.d("BackupManagerService", "Starting next pending restore.");
                this.backupManagerService.getBackupHandler().sendMessage(this.backupManagerService.getBackupHandler().obtainMessage(20, (PerformUnifiedRestoreTask) this.backupManagerService.getPendingRestores().remove()));
            } else {
                this.backupManagerService.setRestoreInProgress(false);
            }
        }
        Slog.i("BackupManagerService", "Restore complete.");
        this.mListener.onFinished("PerformUnifiedRestoreTask.finalizeRestore()");
    }

    public void keyValueAgentErrorCleanup(boolean z) {
        if (z) {
            this.backupManagerService.clearApplicationDataAfterRestoreFailure(this.mCurrentPackage.packageName);
        }
        keyValueAgentCleanup();
    }

    public void keyValueAgentCleanup() {
        this.mBackupDataName.delete();
        this.mStageName.delete();
        try {
            ParcelFileDescriptor parcelFileDescriptor = this.mBackupData;
            if (parcelFileDescriptor != null) {
                parcelFileDescriptor.close();
            }
        } catch (IOException unused) {
        }
        try {
            ParcelFileDescriptor parcelFileDescriptor2 = this.mNewState;
            if (parcelFileDescriptor2 != null) {
                parcelFileDescriptor2.close();
            }
        } catch (IOException unused2) {
        }
        this.mNewState = null;
        this.mBackupData = null;
        this.mNewStateName.delete();
        if (this.mCurrentPackage.applicationInfo != null) {
            try {
                this.backupManagerService.getActivityManager().unbindBackupAgent(this.mCurrentPackage.applicationInfo);
                ApplicationInfo applicationInfo = this.mCurrentPackage.applicationInfo;
                boolean z = !UserHandle.isCore(applicationInfo.uid) && (this.mRestoreDescription.getDataType() == 2 || (65536 & applicationInfo.flags) != 0);
                if (this.mTargetPackage == null && z) {
                    Slog.d("BackupManagerService", "Restore complete, killing host process of " + this.mCurrentPackage.applicationInfo.processName);
                    IActivityManager activityManager = this.backupManagerService.getActivityManager();
                    ApplicationInfo applicationInfo2 = this.mCurrentPackage.applicationInfo;
                    activityManager.killApplicationProcess(applicationInfo2.processName, applicationInfo2.uid);
                }
            } catch (RemoteException unused3) {
            }
        }
        this.backupManagerService.getBackupHandler().removeMessages(18, this);
    }

    @Override // com.android.server.backup.BackupRestoreTask
    public void operationComplete(long j) {
        UnifiedRestoreState unifiedRestoreState;
        this.mOperationStorage.removeOperation(this.mEphemeralOpToken);
        int i = AnonymousClass1.$SwitchMap$com$android$server$backup$restore$UnifiedRestoreState[this.mState.ordinal()];
        if (i == 1) {
            unifiedRestoreState = UnifiedRestoreState.RUNNING_QUEUE;
        } else if (i == 3 || i == 4) {
            unifiedRestoreState = UnifiedRestoreState.RESTORE_FINISHED;
        } else if (i == 5) {
            EventLog.writeEvent(2833, this.mCurrentPackage.packageName, Integer.valueOf((int) this.mBackupDataName.length()));
            BackupManagerMonitorUtils.monitorAgentLoggingResults(this.mMonitor, this.mCurrentPackage, this.mAgent);
            keyValueAgentCleanup();
            byte[] bArr = this.mWidgetData;
            if (bArr != null) {
                this.backupManagerService.restoreWidgetData(this.mCurrentPackage.packageName, bArr);
            }
            unifiedRestoreState = UnifiedRestoreState.RUNNING_QUEUE;
        } else {
            Slog.e("BackupManagerService", "Unexpected restore callback into state " + this.mState);
            keyValueAgentErrorCleanup(true);
            unifiedRestoreState = UnifiedRestoreState.FINAL;
        }
        executeNextState(unifiedRestoreState);
    }

    @Override // com.android.server.backup.BackupRestoreTask
    public void handleCancel(boolean z) {
        this.mOperationStorage.removeOperation(this.mEphemeralOpToken);
        Slog.e("BackupManagerService", "Timeout restoring application " + this.mCurrentPackage.packageName);
        this.mMonitor = BackupManagerMonitorUtils.monitorEvent(this.mMonitor, 31, this.mCurrentPackage, 2, null);
        EventLog.writeEvent(2832, this.mCurrentPackage.packageName, "restore timeout");
        keyValueAgentErrorCleanup(true);
        executeNextState(UnifiedRestoreState.RUNNING_QUEUE);
    }

    public void executeNextState(UnifiedRestoreState unifiedRestoreState) {
        this.mState = unifiedRestoreState;
        this.backupManagerService.getBackupHandler().sendMessage(this.backupManagerService.getBackupHandler().obtainMessage(20, this));
    }

    public UnifiedRestoreState getCurrentUnifiedRestoreStateForTesting() {
        return this.mState;
    }

    public void setCurrentUnifiedRestoreStateForTesting(UnifiedRestoreState unifiedRestoreState) {
        this.mState = unifiedRestoreState;
    }

    public void setStateDirForTesting(File file) {
        this.mStateDir = file;
    }

    public void initiateOneRestoreForTesting(PackageInfo packageInfo, long j) {
        initiateOneRestore(packageInfo, j);
    }

    public void sendStartRestore(int i) {
        IRestoreObserver iRestoreObserver = this.mObserver;
        if (iRestoreObserver != null) {
            try {
                iRestoreObserver.restoreStarting(i);
            } catch (RemoteException unused) {
                Slog.w("BackupManagerService", "Restore observer went away: startRestore");
                this.mObserver = null;
            }
        }
    }

    public void sendOnRestorePackage(String str) {
        IRestoreObserver iRestoreObserver = this.mObserver;
        if (iRestoreObserver != null) {
            try {
                iRestoreObserver.onUpdate(this.mCount, str);
            } catch (RemoteException unused) {
                Slog.d("BackupManagerService", "Restore observer died in onUpdate");
                this.mObserver = null;
            }
        }
    }
}
