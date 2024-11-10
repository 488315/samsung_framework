package com.android.server.backup;

import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.AlarmManager;
import android.app.AppGlobals;
import android.app.IActivityManager;
import android.app.IBackupAgent;
import android.app.PendingIntent;
import android.app.backup.BackupAgent;
import android.app.backup.IBackupManager;
import android.app.backup.IBackupManagerMonitor;
import android.app.backup.IBackupObserver;
import android.app.backup.IFullBackupRestoreObserver;
import android.app.backup.IMemorySaverBackupRestoreObserver;
import android.app.backup.ISelectBackupTransportCallback;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManagerInternal;
import android.database.ContentObserver;
import android.net.Uri;
import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.IInstalld;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.SELinux;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.WorkSource;
import android.provider.Settings;
import android.sec.enterprise.EnterpriseDeviceManager;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.AtomicFile;
import android.util.EventLog;
import android.util.FeatureFlagUtils;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import com.android.internal.util.Preconditions;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.AppWidgetBackupBridge;
import com.android.server.LocalServices;
import com.android.server.backup.UserBackupManagerService;
import com.android.server.backup.fullbackup.FullBackupEntry;
import com.android.server.backup.fullbackup.PerformFullTransportBackupTask;
import com.android.server.backup.internal.BackupHandler;
import com.android.server.backup.internal.ClearDataObserver;
import com.android.server.backup.internal.LifecycleOperationStorage;
import com.android.server.backup.internal.OnTaskFinishedListener;
import com.android.server.backup.internal.PerformInitializeTask;
import com.android.server.backup.internal.RunInitializeReceiver;
import com.android.server.backup.internal.SetupObserver;
import com.android.server.backup.keyvalue.BackupRequest;
import com.android.server.backup.params.AdbBackupParams;
import com.android.server.backup.params.AdbParams;
import com.android.server.backup.params.AdbRestoreParams;
import com.android.server.backup.params.BackupParams;
import com.android.server.backup.params.ClearParams;
import com.android.server.backup.params.ClearRetryParams;
import com.android.server.backup.params.RestoreParams;
import com.android.server.backup.restore.ActiveRestoreSession;
import com.android.server.backup.transport.OnTransportRegisteredListener;
import com.android.server.backup.transport.TransportConnection;
import com.android.server.backup.transport.TransportNotAvailableException;
import com.android.server.backup.transport.TransportNotRegisteredException;
import com.android.server.backup.utils.BackupEligibilityRules;
import com.android.server.backup.utils.BackupManagerMonitorUtils;
import com.android.server.backup.utils.BackupObserverUtils;
import com.android.server.backup.utils.SparseArrayUtils;
import com.android.server.clipboard.ClipboardService;
import com.android.server.pm.PackageManagerShellCommandDataLoader;
import com.google.android.collect.Sets;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.knox.analytics.activation.ActivationMonitor;
import dalvik.annotation.optimization.NeverCompile;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.security.SecureRandom;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

/* loaded from: classes.dex */
public class UserBackupManagerService {
    public static BackupManagerYuva mBackupManagerYuva;
    public static Boolean mSplitBackupFlag;
    public static Boolean mSplitRestoreFlag;
    public static final String valueCscYuva = SemCscFeature.getInstance().getString("CscFeature_Common_ConfigYuva");
    public final boolean DEBUG_BACKUP;
    public ActiveRestoreSession mActiveRestoreSession;
    public final IActivityManager mActivityManager;
    public final ActivityManagerInternal mActivityManagerInternal;
    public AdbBackupParams mAdbBackupParams;
    public final SparseArray mAdbBackupRestoreConfirmations;
    public AdbRestoreParams mAdbRestoreParams;
    public final Object mAgentConnectLock;
    public final BackupAgentTimeoutParameters mAgentTimeoutParameters;
    public final AlarmManager mAlarmManager;
    public volatile long mAncestralBackupDestination;
    public Set mAncestralPackages;
    public File mAncestralSerialNumberFile;
    public long mAncestralToken;
    public boolean mAutoRestore;
    public final BackupHandler mBackupHandler;
    public final IBackupManager mBackupManagerBinder;
    public final SparseArray mBackupParticipants;
    public final BackupPasswordManager mBackupPasswordManager;
    public final UserBackupPreferences mBackupPreferences;
    public Object mBackupRestoreLock;
    public volatile boolean mBackupRunning;
    public final File mBaseStateDir;
    public final Object mClearDataLock;
    public volatile boolean mClearingData;
    public IBackupAgent mConnectedAgent;
    public volatile boolean mConnecting;
    public final BackupManagerConstants mConstants;
    public final Context mContext;
    public long mCurrentToken;
    public final File mDataDir;
    public boolean mDisableDataExtractionRule;
    public boolean mEnabled;
    public String mEncPassword;
    public int mExtraFlag;
    public ArrayList mFullBackupQueue;
    public final File mFullBackupScheduleFile;
    public Runnable mFullBackupScheduleWriter;
    public boolean mIsRestoreInProgress;
    public DataChangedJournal mJournal;
    public final File mJournalDir;
    public volatile long mLastBackupPass;
    public final AtomicInteger mNextToken;
    public final LifecycleOperationStorage mOperationStorage;
    public final PackageManager mPackageManager;
    public final IPackageManager mPackageManagerBinder;
    public BroadcastReceiver mPackageTrackingReceiver;
    public boolean mPassword;
    public final HashMap mPendingBackups;
    public final ArraySet mPendingInits;
    public final Queue mPendingRestores;
    public PowerManager mPowerManager;
    public String[] mPrivilegePkgName;
    public ProcessedPackagesJournal mProcessedPackagesJournal;
    public final Object mQueueLock;
    public final long mRegisterTransportsRequestedTime;
    public final SecureRandom mRng;
    public final PendingIntent mRunInitIntent;
    public final BroadcastReceiver mRunInitReceiver;
    public PerformFullTransportBackupTask mRunningFullBackupTask;
    public final BackupEligibilityRules mScheduledBackupEligibility;
    public boolean mSepTimeOut;
    public int mSepTimeoutMin;
    public boolean mSepWakeLock;
    public boolean mSetupComplete;
    public final ContentObserver mSetupObserver;
    public String[] mSmartSwitchBackupPath;
    public File mTokenFile;
    public final Random mTokenGenerator;
    public int mTransportFlagsForAdbBackup;
    public final TransportManager mTransportManager;
    public final int mUserId;
    public final BackupWakeLock mWakelock;

    public static /* synthetic */ void lambda$cancelBackups$2(int i) {
    }

    /* loaded from: classes.dex */
    public class BackupWakeLock {
        public boolean mHasQuit = false;
        public final PowerManager.WakeLock mPowerManagerWakeLock;
        public int mUserId;

        public BackupWakeLock(PowerManager.WakeLock wakeLock, int i) {
            this.mPowerManagerWakeLock = wakeLock;
            this.mUserId = i;
        }

        public synchronized void acquire() {
            if (this.mHasQuit) {
                Slog.v("BackupManagerService", UserBackupManagerService.addUserIdToLogMessage(this.mUserId, "Ignore wakelock acquire after quit: " + this.mPowerManagerWakeLock.getTag()));
                return;
            }
            this.mPowerManagerWakeLock.acquire();
            Slog.i("BackupManagerService", UserBackupManagerService.addUserIdToLogMessage(this.mUserId, "Acquired wakelock:" + this.mPowerManagerWakeLock.getTag()));
        }

        public synchronized void release() {
            if (this.mHasQuit) {
                Slog.v("BackupManagerService", UserBackupManagerService.addUserIdToLogMessage(this.mUserId, "Ignore wakelock release after quit: " + this.mPowerManagerWakeLock.getTag()));
                return;
            }
            this.mPowerManagerWakeLock.release();
            Slog.i("BackupManagerService", UserBackupManagerService.addUserIdToLogMessage(this.mUserId, "Released wakelock:" + this.mPowerManagerWakeLock.getTag()));
        }

        public synchronized boolean isHeld() {
            return this.mPowerManagerWakeLock.isHeld();
        }

        public synchronized void quit() {
            while (this.mPowerManagerWakeLock.isHeld()) {
                Slog.v("BackupManagerService", UserBackupManagerService.addUserIdToLogMessage(this.mUserId, "Releasing wakelock: " + this.mPowerManagerWakeLock.getTag()));
                this.mPowerManagerWakeLock.release();
            }
            this.mHasQuit = true;
        }
    }

    static {
        Boolean bool = Boolean.FALSE;
        mSplitBackupFlag = bool;
        mSplitRestoreFlag = bool;
    }

    public static UserBackupManagerService createAndInitializeService(int i, Context context, BackupManagerService backupManagerService, Set set) {
        String stringForUser = Settings.Secure.getStringForUser(context.getContentResolver(), "backup_transport", i);
        if (TextUtils.isEmpty(stringForUser)) {
            stringForUser = null;
        }
        Slog.v("BackupManagerService", addUserIdToLogMessage(i, "Starting with transport " + stringForUser));
        TransportManager transportManager = new TransportManager(i, context, set, stringForUser);
        File baseStateDir = UserBackupManagerFiles.getBaseStateDir(i);
        File dataDir = UserBackupManagerFiles.getDataDir(i);
        HandlerThread handlerThread = new HandlerThread("backup-" + i, 10);
        handlerThread.start();
        Slog.d("BackupManagerService", addUserIdToLogMessage(i, "Started thread " + handlerThread.getName()));
        return createAndInitializeService(i, context, backupManagerService, handlerThread, baseStateDir, dataDir, transportManager);
    }

    public static UserBackupManagerService createAndInitializeService(int i, Context context, BackupManagerService backupManagerService, HandlerThread handlerThread, File file, File file2, TransportManager transportManager) {
        return new UserBackupManagerService(i, context, backupManagerService, handlerThread, file, file2, transportManager);
    }

    public static boolean getSetupCompleteSettingForUser(Context context, int i) {
        return Settings.Secure.getIntForUser(context.getContentResolver(), "user_setup_complete", 0, i) != 0;
    }

    public UserBackupManagerService(Context context, PackageManager packageManager, LifecycleOperationStorage lifecycleOperationStorage, TransportManager transportManager) {
        this.mPendingInits = new ArraySet();
        this.mBackupParticipants = new SparseArray();
        this.mPendingBackups = new HashMap();
        this.mQueueLock = new Object();
        this.mAgentConnectLock = new Object();
        this.mClearDataLock = new Object();
        this.mBackupRestoreLock = new Object();
        this.mEncPassword = "";
        this.mExtraFlag = 0;
        this.mSepWakeLock = false;
        this.mSepTimeOut = false;
        this.mSepTimeoutMin = 30;
        this.mTransportFlagsForAdbBackup = 0;
        this.mPrivilegePkgName = new String[]{"com.sec.android.easyMover", "com.sec.android.Kies", "com.samsung.android.da.daagent", "com.samsung.memorysaver", "com.samsung.knox.bnr", "com.samsung.knox.securefolder", "com.samsung.android.se.unit", "com.samsung.android.game.gamehome"};
        this.DEBUG_BACKUP = false;
        this.mAdbBackupRestoreConfirmations = new SparseArray();
        this.mRng = new SecureRandom();
        this.mPendingRestores = new ArrayDeque();
        this.mTokenGenerator = new Random();
        this.mNextToken = new AtomicInteger();
        this.mAncestralPackages = null;
        this.mAncestralToken = 0L;
        this.mCurrentToken = 0L;
        this.mPassword = true;
        this.mDisableDataExtractionRule = false;
        this.mFullBackupScheduleWriter = new Runnable() { // from class: com.android.server.backup.UserBackupManagerService.1
            @Override // java.lang.Runnable
            public void run() {
                synchronized (UserBackupManagerService.this.mQueueLock) {
                    try {
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(IInstalld.FLAG_USE_QUOTA);
                        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
                        dataOutputStream.writeInt(1);
                        int size = UserBackupManagerService.this.mFullBackupQueue.size();
                        dataOutputStream.writeInt(size);
                        for (int i = 0; i < size; i++) {
                            FullBackupEntry fullBackupEntry = (FullBackupEntry) UserBackupManagerService.this.mFullBackupQueue.get(i);
                            dataOutputStream.writeUTF(fullBackupEntry.packageName);
                            dataOutputStream.writeLong(fullBackupEntry.lastBackup);
                        }
                        dataOutputStream.flush();
                        AtomicFile atomicFile = new AtomicFile(UserBackupManagerService.this.mFullBackupScheduleFile);
                        FileOutputStream startWrite = atomicFile.startWrite();
                        startWrite.write(byteArrayOutputStream.toByteArray());
                        atomicFile.finishWrite(startWrite);
                    } catch (Exception e) {
                        Slog.e("BackupManagerService", UserBackupManagerService.addUserIdToLogMessage(UserBackupManagerService.this.mUserId, "Unable to write backup schedule!"), e);
                    }
                }
            }
        };
        this.mPackageTrackingReceiver = new AnonymousClass2();
        this.mContext = context;
        this.mUserId = 0;
        this.mRegisterTransportsRequestedTime = 0L;
        this.mPackageManager = packageManager;
        this.mOperationStorage = lifecycleOperationStorage;
        this.mTransportManager = transportManager;
        this.mBaseStateDir = null;
        this.mDataDir = null;
        this.mJournalDir = null;
        this.mFullBackupScheduleFile = null;
        this.mSetupObserver = null;
        this.mRunInitReceiver = null;
        this.mRunInitIntent = null;
        this.mAgentTimeoutParameters = null;
        this.mActivityManagerInternal = null;
        this.mAlarmManager = null;
        this.mConstants = null;
        this.mWakelock = null;
        this.mBackupHandler = null;
        this.mBackupPreferences = null;
        this.mBackupPasswordManager = null;
        this.mPackageManagerBinder = null;
        this.mActivityManager = null;
        this.mBackupManagerBinder = null;
        this.mScheduledBackupEligibility = null;
    }

    public UserBackupManagerService(int i, Context context, BackupManagerService backupManagerService, HandlerThread handlerThread, File file, File file2, final TransportManager transportManager) {
        this.mPendingInits = new ArraySet();
        SparseArray sparseArray = new SparseArray();
        this.mBackupParticipants = sparseArray;
        this.mPendingBackups = new HashMap();
        this.mQueueLock = new Object();
        this.mAgentConnectLock = new Object();
        this.mClearDataLock = new Object();
        this.mBackupRestoreLock = new Object();
        this.mEncPassword = "";
        this.mExtraFlag = 0;
        this.mSepWakeLock = false;
        this.mSepTimeOut = false;
        this.mSepTimeoutMin = 30;
        this.mTransportFlagsForAdbBackup = 0;
        this.mPrivilegePkgName = new String[]{"com.sec.android.easyMover", "com.sec.android.Kies", "com.samsung.android.da.daagent", "com.samsung.memorysaver", "com.samsung.knox.bnr", "com.samsung.knox.securefolder", "com.samsung.android.se.unit", "com.samsung.android.game.gamehome"};
        this.DEBUG_BACKUP = false;
        this.mAdbBackupRestoreConfirmations = new SparseArray();
        SecureRandom secureRandom = new SecureRandom();
        this.mRng = secureRandom;
        this.mPendingRestores = new ArrayDeque();
        this.mTokenGenerator = new Random();
        this.mNextToken = new AtomicInteger();
        this.mAncestralPackages = null;
        this.mAncestralToken = 0L;
        this.mCurrentToken = 0L;
        this.mPassword = true;
        this.mDisableDataExtractionRule = false;
        this.mFullBackupScheduleWriter = new Runnable() { // from class: com.android.server.backup.UserBackupManagerService.1
            @Override // java.lang.Runnable
            public void run() {
                synchronized (UserBackupManagerService.this.mQueueLock) {
                    try {
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(IInstalld.FLAG_USE_QUOTA);
                        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
                        dataOutputStream.writeInt(1);
                        int size = UserBackupManagerService.this.mFullBackupQueue.size();
                        dataOutputStream.writeInt(size);
                        for (int i2 = 0; i2 < size; i2++) {
                            FullBackupEntry fullBackupEntry = (FullBackupEntry) UserBackupManagerService.this.mFullBackupQueue.get(i2);
                            dataOutputStream.writeUTF(fullBackupEntry.packageName);
                            dataOutputStream.writeLong(fullBackupEntry.lastBackup);
                        }
                        dataOutputStream.flush();
                        AtomicFile atomicFile = new AtomicFile(UserBackupManagerService.this.mFullBackupScheduleFile);
                        FileOutputStream startWrite = atomicFile.startWrite();
                        startWrite.write(byteArrayOutputStream.toByteArray());
                        atomicFile.finishWrite(startWrite);
                    } catch (Exception e) {
                        Slog.e("BackupManagerService", UserBackupManagerService.addUserIdToLogMessage(UserBackupManagerService.this.mUserId, "Unable to write backup schedule!"), e);
                    }
                }
            }
        };
        this.mPackageTrackingReceiver = new AnonymousClass2();
        this.mUserId = i;
        Objects.requireNonNull(context, "context cannot be null");
        this.mContext = context;
        PackageManager packageManager = context.getPackageManager();
        this.mPackageManager = packageManager;
        this.mPackageManagerBinder = AppGlobals.getPackageManager();
        this.mActivityManager = ActivityManager.getService();
        this.mActivityManagerInternal = (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
        this.mScheduledBackupEligibility = getEligibilityRules(packageManager, i, context, 0);
        this.mAlarmManager = (AlarmManager) context.getSystemService("alarm");
        this.mPowerManager = (PowerManager) context.getSystemService("power");
        Objects.requireNonNull(backupManagerService, "parent cannot be null");
        this.mBackupManagerBinder = IBackupManager.Stub.asInterface(backupManagerService.asBinder());
        BackupAgentTimeoutParameters backupAgentTimeoutParameters = new BackupAgentTimeoutParameters(Handler.getMain(), context.getContentResolver());
        this.mAgentTimeoutParameters = backupAgentTimeoutParameters;
        backupAgentTimeoutParameters.start();
        LifecycleOperationStorage lifecycleOperationStorage = new LifecycleOperationStorage(i);
        this.mOperationStorage = lifecycleOperationStorage;
        Objects.requireNonNull(handlerThread, "userBackupThread cannot be null");
        BackupHandler backupHandler = new BackupHandler(this, lifecycleOperationStorage, handlerThread);
        this.mBackupHandler = backupHandler;
        ContentResolver contentResolver = context.getContentResolver();
        this.mSetupComplete = getSetupCompleteSettingForUser(context, i);
        this.mAutoRestore = Settings.Secure.getIntForUser(contentResolver, "backup_auto_restore", 1, i) != 0;
        SetupObserver setupObserver = new SetupObserver(this, backupHandler);
        this.mSetupObserver = setupObserver;
        contentResolver.registerContentObserver(Settings.Secure.getUriFor("user_setup_complete"), false, setupObserver, i);
        Objects.requireNonNull(file, "baseStateDir cannot be null");
        this.mBaseStateDir = file;
        if (i == 0) {
            file.mkdirs();
            if (!SELinux.restorecon(file)) {
                Slog.w("BackupManagerService", addUserIdToLogMessage(i, "SELinux restorecon failed on " + file));
            }
        }
        Objects.requireNonNull(file2, "dataDir cannot be null");
        this.mDataDir = file2;
        this.mBackupPasswordManager = new BackupPasswordManager(context, file, secureRandom);
        RunInitializeReceiver runInitializeReceiver = new RunInitializeReceiver(this);
        this.mRunInitReceiver = runInitializeReceiver;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.app.backup.intent.INIT");
        context.registerReceiverAsUser(runInitializeReceiver, UserHandle.of(i), intentFilter, "android.permission.BACKUP", null);
        Intent intent = new Intent("android.app.backup.intent.INIT");
        intent.addFlags(1073741824);
        this.mRunInitIntent = PendingIntent.getBroadcastAsUser(context, 0, intent, 67108864, UserHandle.of(i));
        File file3 = new File(file, "pending");
        this.mJournalDir = file3;
        file3.mkdirs();
        this.mJournal = null;
        BackupManagerConstants backupManagerConstants = new BackupManagerConstants(backupHandler, context.getContentResolver());
        this.mConstants = backupManagerConstants;
        backupManagerConstants.start();
        synchronized (sparseArray) {
            addPackageParticipantsLocked(null);
        }
        Objects.requireNonNull(transportManager, "transportManager cannot be null");
        this.mTransportManager = transportManager;
        transportManager.setOnTransportRegisteredListener(new OnTransportRegisteredListener() { // from class: com.android.server.backup.UserBackupManagerService$$ExternalSyntheticLambda1
            @Override // com.android.server.backup.transport.OnTransportRegisteredListener
            public final void onTransportRegistered(String str, String str2) {
                UserBackupManagerService.this.onTransportRegistered(str, str2);
            }
        });
        this.mRegisterTransportsRequestedTime = SystemClock.elapsedRealtime();
        Objects.requireNonNull(transportManager);
        backupHandler.postDelayed(new Runnable() { // from class: com.android.server.backup.UserBackupManagerService$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                TransportManager.this.registerTransports();
            }
        }, 3000L);
        backupHandler.postDelayed(new Runnable() { // from class: com.android.server.backup.UserBackupManagerService$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                UserBackupManagerService.this.parseLeftoverJournals();
            }
        }, 3000L);
        this.mBackupPreferences = new UserBackupPreferences(context, file);
        this.mWakelock = new BackupWakeLock(this.mPowerManager.newWakeLock(1, "*backup*-" + i + PackageManagerShellCommandDataLoader.STDIN_PATH + handlerThread.getThreadId()), i);
        if (isYuvaSupported()) {
            Slog.d("BackupManagerService", "Backup Manager Yuva is Supported");
            mBackupManagerYuva = BackupManagerYuva.getInstanceBackupYuva();
        }
        this.mFullBackupScheduleFile = new File(file, "fb-schedule");
        initPackageTracking();
    }

    public static boolean isYuvaSupported() {
        String str = valueCscYuva;
        if (str == null || !str.contains("MemorySaver")) {
            return false;
        }
        Slog.d("BackupManagerService", "Memory Saver is there");
        return true;
    }

    public static void setSplitBackupFlagVal(boolean z) {
        mSplitBackupFlag = Boolean.valueOf(z);
    }

    public static void setSplitRestoreFlagVal(boolean z) {
        mSplitRestoreFlag = Boolean.valueOf(z);
    }

    public void initializeBackupEnableState() {
        setBackupEnabled(readEnabledState(), false);
    }

    public void tearDownService() {
        this.mAgentTimeoutParameters.stop();
        this.mConstants.stop();
        this.mContext.getContentResolver().unregisterContentObserver(this.mSetupObserver);
        this.mContext.unregisterReceiver(this.mRunInitReceiver);
        this.mContext.unregisterReceiver(this.mPackageTrackingReceiver);
        this.mBackupHandler.stop();
    }

    public int getUserId() {
        return this.mUserId;
    }

    public BackupManagerConstants getConstants() {
        return this.mConstants;
    }

    public BackupAgentTimeoutParameters getAgentTimeoutParameters() {
        return this.mAgentTimeoutParameters;
    }

    public Context getContext() {
        return this.mContext;
    }

    public PackageManager getPackageManager() {
        return this.mPackageManager;
    }

    public IPackageManager getPackageManagerBinder() {
        return this.mPackageManagerBinder;
    }

    public IActivityManager getActivityManager() {
        return this.mActivityManager;
    }

    public AlarmManager getAlarmManager() {
        return this.mAlarmManager;
    }

    public void setPowerManager(PowerManager powerManager) {
        this.mPowerManager = powerManager;
    }

    public TransportManager getTransportManager() {
        return this.mTransportManager;
    }

    public boolean isEnabled() {
        return this.mEnabled;
    }

    public boolean isSetupComplete() {
        return this.mSetupComplete;
    }

    public void setSetupComplete(boolean z) {
        this.mSetupComplete = z;
    }

    public BackupWakeLock getWakelock() {
        return this.mWakelock;
    }

    public void setSepWakeLock(boolean z) {
        this.mSepWakeLock = z;
    }

    public boolean getSepWakeLock() {
        return this.mSepWakeLock;
    }

    public void setWorkSource(WorkSource workSource) {
        this.mWakelock.mPowerManagerWakeLock.setWorkSource(workSource);
    }

    public Handler getBackupHandler() {
        return this.mBackupHandler;
    }

    public PendingIntent getRunInitIntent() {
        return this.mRunInitIntent;
    }

    public HashMap getPendingBackups() {
        return this.mPendingBackups;
    }

    public Object getQueueLock() {
        return this.mQueueLock;
    }

    public boolean isBackupRunning() {
        return this.mBackupRunning;
    }

    public void setBackupRunning(boolean z) {
        this.mBackupRunning = z;
    }

    public void setLastBackupPass(long j) {
        this.mLastBackupPass = j;
    }

    public Object getClearDataLock() {
        return this.mClearDataLock;
    }

    public void setClearingData(boolean z) {
        this.mClearingData = z;
    }

    public boolean isRestoreInProgress() {
        return this.mIsRestoreInProgress;
    }

    public void setRestoreInProgress(boolean z) {
        this.mIsRestoreInProgress = z;
    }

    public Queue getPendingRestores() {
        return this.mPendingRestores;
    }

    public ActiveRestoreSession getActiveRestoreSession() {
        return this.mActiveRestoreSession;
    }

    public SparseArray getAdbBackupRestoreConfirmations() {
        return this.mAdbBackupRestoreConfirmations;
    }

    public File getBaseStateDir() {
        return this.mBaseStateDir;
    }

    public File getDataDir() {
        return this.mDataDir;
    }

    public BroadcastReceiver getPackageTrackingReceiver() {
        return this.mPackageTrackingReceiver;
    }

    public DataChangedJournal getJournal() {
        return this.mJournal;
    }

    public void setJournal(DataChangedJournal dataChangedJournal) {
        this.mJournal = dataChangedJournal;
    }

    public SecureRandom getRng() {
        return this.mRng;
    }

    public void setAncestralPackages(Set set) {
        this.mAncestralPackages = set;
    }

    public void setAncestralToken(long j) {
        this.mAncestralToken = j;
    }

    public void setAncestralBackupDestination(int i) {
        this.mAncestralBackupDestination = i;
    }

    public long getCurrentToken() {
        return this.mCurrentToken;
    }

    public void setCurrentToken(long j) {
        this.mCurrentToken = j;
    }

    public ArraySet getPendingInits() {
        return this.mPendingInits;
    }

    public void clearPendingInits() {
        this.mPendingInits.clear();
    }

    public void setRunningFullBackupTask(PerformFullTransportBackupTask performFullTransportBackupTask) {
        this.mRunningFullBackupTask = performFullTransportBackupTask;
    }

    public int generateRandomIntegerToken() {
        int nextInt = this.mTokenGenerator.nextInt();
        if (nextInt < 0) {
            nextInt = -nextInt;
        }
        return (this.mNextToken.incrementAndGet() & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT) | (nextInt & (-256));
    }

    public BackupAgent makeMetadataAgentWithEligibilityRules(BackupEligibilityRules backupEligibilityRules) {
        PackageManagerBackupAgent packageManagerBackupAgent = new PackageManagerBackupAgent(this.mPackageManager, this.mUserId, backupEligibilityRules);
        packageManagerBackupAgent.attach(this.mContext);
        packageManagerBackupAgent.onCreate(UserHandle.of(this.mUserId));
        return packageManagerBackupAgent;
    }

    public PackageManagerBackupAgent makeMetadataAgent(List list) {
        PackageManagerBackupAgent packageManagerBackupAgent = new PackageManagerBackupAgent(this.mPackageManager, list, this.mUserId);
        packageManagerBackupAgent.attach(this.mContext);
        packageManagerBackupAgent.onCreate(UserHandle.of(this.mUserId));
        return packageManagerBackupAgent;
    }

    public final void initPackageTracking() {
        this.mTokenFile = new File(this.mBaseStateDir, "ancestral");
        try {
            DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(this.mTokenFile)));
            try {
                if (dataInputStream.readInt() == 1) {
                    this.mAncestralToken = dataInputStream.readLong();
                    this.mCurrentToken = dataInputStream.readLong();
                    int readInt = dataInputStream.readInt();
                    if (readInt >= 0) {
                        this.mAncestralPackages = new HashSet();
                        for (int i = 0; i < readInt; i++) {
                            this.mAncestralPackages.add(dataInputStream.readUTF());
                        }
                    }
                }
                dataInputStream.close();
            } finally {
            }
        } catch (FileNotFoundException unused) {
            Slog.v("BackupManagerService", addUserIdToLogMessage(this.mUserId, "No ancestral data"));
        } catch (IOException e) {
            Slog.w("BackupManagerService", addUserIdToLogMessage(this.mUserId, "Unable to read token file"), e);
        }
        ProcessedPackagesJournal processedPackagesJournal = new ProcessedPackagesJournal(this.mBaseStateDir);
        this.mProcessedPackagesJournal = processedPackagesJournal;
        processedPackagesJournal.init();
        synchronized (this.mQueueLock) {
            this.mFullBackupQueue = readFullBackupSchedule();
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
        intentFilter.addDataScheme("package");
        this.mContext.registerReceiverAsUser(this.mPackageTrackingReceiver, UserHandle.of(this.mUserId), intentFilter, null, null);
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE");
        intentFilter2.addAction("android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE");
        this.mContext.registerReceiverAsUser(this.mPackageTrackingReceiver, UserHandle.of(this.mUserId), intentFilter2, null, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x01ad  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x01aa  */
    /* JADX WARN: Removed duplicated region for block: B:5:0x016d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.ArrayList readFullBackupSchedule() {
        /*
            Method dump skipped, instructions count: 433
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.backup.UserBackupManagerService.readFullBackupSchedule():java.util.ArrayList");
    }

    public final void writeFullBackupScheduleAsync() {
        this.mBackupHandler.removeCallbacks(this.mFullBackupScheduleWriter);
        this.mBackupHandler.post(this.mFullBackupScheduleWriter);
    }

    public final void parseLeftoverJournals() {
        ArrayList listJournals = DataChangedJournal.listJournals(this.mJournalDir);
        listJournals.removeAll(Collections.singletonList(this.mJournal));
        if (!listJournals.isEmpty()) {
            Slog.i("BackupManagerService", addUserIdToLogMessage(this.mUserId, "Found " + listJournals.size() + " stale backup journal(s), scheduling."));
        }
        final LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator it = listJournals.iterator();
        while (it.hasNext()) {
            DataChangedJournal dataChangedJournal = (DataChangedJournal) it.next();
            try {
                dataChangedJournal.forEach(new Consumer() { // from class: com.android.server.backup.UserBackupManagerService$$ExternalSyntheticLambda13
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        UserBackupManagerService.this.lambda$parseLeftoverJournals$0(linkedHashSet, (String) obj);
                    }
                });
            } catch (IOException e) {
                Slog.e("BackupManagerService", addUserIdToLogMessage(this.mUserId, "Can't read " + dataChangedJournal), e);
            }
        }
        if (linkedHashSet.isEmpty()) {
            return;
        }
        Slog.i("BackupManagerService", addUserIdToLogMessage(this.mUserId, "Stale backup journals: Scheduled " + linkedHashSet.size() + " package(s) total"));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$parseLeftoverJournals$0(Set set, String str) {
        if (set.add(str)) {
            dataChangedImpl(str);
        }
    }

    public Set getExcludedRestoreKeys(String str) {
        return this.mBackupPreferences.getExcludedRestoreKeysForPackage(str);
    }

    public byte[] randomBytes(int i) {
        byte[] bArr = new byte[i / 8];
        this.mRng.nextBytes(bArr);
        return bArr;
    }

    public boolean setBackupPassword(String str, String str2) {
        return this.mBackupPasswordManager.setBackupPassword(str, str2);
    }

    public boolean hasBackupPassword() {
        return this.mBackupPasswordManager.hasBackupPassword();
    }

    public boolean backupPasswordMatches(String str) {
        return this.mBackupPasswordManager.backupPasswordMatches(str);
    }

    public void recordInitPending(boolean z, String str, String str2) {
        synchronized (this.mQueueLock) {
            File file = new File(new File(this.mBaseStateDir, str2), "_need_init_");
            if (z) {
                this.mPendingInits.add(str);
                try {
                    new FileOutputStream(file).close();
                } catch (IOException unused) {
                }
            } else {
                file.delete();
                this.mPendingInits.remove(str);
            }
        }
    }

    public void resetBackupState(File file) {
        int i;
        synchronized (this.mQueueLock) {
            this.mProcessedPackagesJournal.reset();
            this.mCurrentToken = 0L;
            writeRestoreTokens();
            for (File file2 : file.listFiles()) {
                if (!file2.getName().equals("_need_init_")) {
                    file2.delete();
                }
            }
        }
        synchronized (this.mBackupParticipants) {
            int size = this.mBackupParticipants.size();
            for (i = 0; i < size; i++) {
                HashSet hashSet = (HashSet) this.mBackupParticipants.valueAt(i);
                if (hashSet != null) {
                    Iterator it = hashSet.iterator();
                    while (it.hasNext()) {
                        dataChangedImpl((String) it.next());
                    }
                }
            }
        }
    }

    public final void onTransportRegistered(String str, String str2) {
        long elapsedRealtime = SystemClock.elapsedRealtime() - this.mRegisterTransportsRequestedTime;
        Slog.d("BackupManagerService", addUserIdToLogMessage(this.mUserId, "Transport " + str + " registered " + elapsedRealtime + "ms after first request (delay = 3000ms)"));
        File file = new File(this.mBaseStateDir, str2);
        file.mkdirs();
        if (new File(file, "_need_init_").exists()) {
            synchronized (this.mQueueLock) {
                this.mPendingInits.add(str);
                this.mAlarmManager.set(0, System.currentTimeMillis() + 60000, this.mRunInitIntent);
            }
        }
    }

    /* renamed from: com.android.server.backup.UserBackupManagerService$2, reason: invalid class name */
    /* loaded from: classes.dex */
    public class AnonymousClass2 extends BroadcastReceiver {
        public AnonymousClass2() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String[] strArr;
            boolean equals;
            boolean z;
            String action = intent.getAction();
            Bundle extras = intent.getExtras();
            if ("android.intent.action.PACKAGE_ADDED".equals(action) || "android.intent.action.PACKAGE_REMOVED".equals(action) || "android.intent.action.PACKAGE_CHANGED".equals(action)) {
                Uri data = intent.getData();
                if (data == null) {
                    return;
                }
                final String schemeSpecificPart = data.getSchemeSpecificPart();
                strArr = schemeSpecificPart != null ? new String[]{schemeSpecificPart} : null;
                if ("android.intent.action.PACKAGE_CHANGED".equals(action)) {
                    final String[] stringArrayExtra = intent.getStringArrayExtra("android.intent.extra.changed_component_name_list");
                    UserBackupManagerService.this.mBackupHandler.post(new Runnable() { // from class: com.android.server.backup.UserBackupManagerService$2$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            UserBackupManagerService.AnonymousClass2.this.lambda$onReceive$0(schemeSpecificPart, stringArrayExtra);
                        }
                    });
                    return;
                } else {
                    equals = "android.intent.action.PACKAGE_ADDED".equals(action);
                    z = extras.getBoolean("android.intent.extra.REPLACING", false);
                }
            } else if ("android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE".equals(action)) {
                strArr = intent.getStringArrayExtra("android.intent.extra.changed_package_list");
                equals = true;
                z = false;
            } else {
                strArr = "android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE".equals(action) ? intent.getStringArrayExtra("android.intent.extra.changed_package_list") : null;
                equals = false;
                z = false;
            }
            if (strArr == null || strArr.length == 0) {
                return;
            }
            int i = extras.getInt("android.intent.extra.UID");
            if (equals) {
                synchronized (UserBackupManagerService.this.mBackupParticipants) {
                    if (z) {
                        UserBackupManagerService.this.removePackageParticipantsLocked(strArr, i);
                    }
                    UserBackupManagerService.this.addPackageParticipantsLocked(strArr);
                }
                long currentTimeMillis = System.currentTimeMillis();
                for (final String str : strArr) {
                    try {
                        PackageInfo packageInfoAsUser = UserBackupManagerService.this.mPackageManager.getPackageInfoAsUser(str, 0, UserBackupManagerService.this.mUserId);
                        if (UserBackupManagerService.this.mScheduledBackupEligibility.appGetsFullBackup(packageInfoAsUser) && UserBackupManagerService.this.mScheduledBackupEligibility.appIsEligibleForBackup(packageInfoAsUser.applicationInfo)) {
                            UserBackupManagerService.this.enqueueFullBackup(str, currentTimeMillis);
                            UserBackupManagerService.this.scheduleNextFullBackupJob(0L);
                        } else {
                            synchronized (UserBackupManagerService.this.mQueueLock) {
                                UserBackupManagerService.this.dequeueFullBackupLocked(str);
                            }
                            UserBackupManagerService.this.writeFullBackupScheduleAsync();
                        }
                        UserBackupManagerService.this.mBackupHandler.post(new Runnable() { // from class: com.android.server.backup.UserBackupManagerService$2$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                UserBackupManagerService.AnonymousClass2.this.lambda$onReceive$1(str);
                            }
                        });
                    } catch (PackageManager.NameNotFoundException unused) {
                        Slog.w("BackupManagerService", UserBackupManagerService.addUserIdToLogMessage(UserBackupManagerService.this.mUserId, "Can't resolve new app " + str));
                    }
                }
                UserBackupManagerService.this.dataChangedImpl("@pm@");
                return;
            }
            if (!z) {
                synchronized (UserBackupManagerService.this.mBackupParticipants) {
                    UserBackupManagerService.this.removePackageParticipantsLocked(strArr, i);
                }
            }
            for (final String str2 : strArr) {
                UserBackupManagerService.this.mBackupHandler.post(new Runnable() { // from class: com.android.server.backup.UserBackupManagerService$2$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        UserBackupManagerService.AnonymousClass2.this.lambda$onReceive$2(str2);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onReceive$0(String str, String[] strArr) {
            UserBackupManagerService.this.mTransportManager.onPackageChanged(str, strArr);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onReceive$1(String str) {
            UserBackupManagerService.this.mTransportManager.onPackageAdded(str);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onReceive$2(String str) {
            UserBackupManagerService.this.mTransportManager.onPackageRemoved(str);
        }
    }

    public final void addPackageParticipantsLocked(String[] strArr) {
        List allAgentPackages = allAgentPackages();
        if (strArr != null) {
            for (String str : strArr) {
                addPackageParticipantsLockedInner(str, allAgentPackages);
            }
            return;
        }
        addPackageParticipantsLockedInner(null, allAgentPackages);
    }

    public final void addPackageParticipantsLockedInner(String str, List list) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            PackageInfo packageInfo = (PackageInfo) it.next();
            if (str == null || packageInfo.packageName.equals(str)) {
                int i = packageInfo.applicationInfo.uid;
                HashSet hashSet = (HashSet) this.mBackupParticipants.get(i);
                if (hashSet == null) {
                    hashSet = new HashSet();
                    this.mBackupParticipants.put(i, hashSet);
                }
                hashSet.add(packageInfo.packageName);
                this.mBackupHandler.sendMessage(this.mBackupHandler.obtainMessage(16, packageInfo.packageName));
            }
        }
    }

    public final void removePackageParticipantsLocked(String[] strArr, int i) {
        if (strArr == null) {
            Slog.w("BackupManagerService", addUserIdToLogMessage(this.mUserId, "removePackageParticipants with null list"));
            return;
        }
        for (String str : strArr) {
            HashSet hashSet = (HashSet) this.mBackupParticipants.get(i);
            if (hashSet != null && hashSet.contains(str)) {
                removePackageFromSetLocked(hashSet, str);
                if (hashSet.isEmpty()) {
                    this.mBackupParticipants.remove(i);
                }
            }
        }
    }

    public final void removePackageFromSetLocked(HashSet hashSet, String str) {
        if (hashSet.contains(str)) {
            hashSet.remove(str);
            this.mPendingBackups.remove(str);
        }
    }

    public final List allAgentPackages() {
        ApplicationInfo applicationInfo;
        int i;
        List installedPackagesAsUser = this.mPackageManager.getInstalledPackagesAsUser(134217728, this.mUserId);
        for (int size = installedPackagesAsUser.size() - 1; size >= 0; size--) {
            PackageInfo packageInfo = (PackageInfo) installedPackagesAsUser.get(size);
            try {
                applicationInfo = packageInfo.applicationInfo;
                i = applicationInfo.flags;
            } catch (PackageManager.NameNotFoundException unused) {
                installedPackagesAsUser.remove(size);
            }
            if ((32768 & i) != 0 && applicationInfo.backupAgentName != null && (67108864 & i) == 0) {
                ApplicationInfo applicationInfoAsUser = this.mPackageManager.getApplicationInfoAsUser(packageInfo.packageName, 1024, this.mUserId);
                ApplicationInfo applicationInfo2 = packageInfo.applicationInfo;
                applicationInfo2.sharedLibraryFiles = applicationInfoAsUser.sharedLibraryFiles;
                applicationInfo2.sharedLibraryInfos = applicationInfoAsUser.sharedLibraryInfos;
            }
            installedPackagesAsUser.remove(size);
        }
        return installedPackagesAsUser;
    }

    public void logBackupComplete(String str) {
        if (str.equals("@pm@")) {
            return;
        }
        for (String str2 : this.mConstants.getBackupFinishedNotificationReceivers()) {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.BACKUP_FINISHED");
            intent.setPackage(str2);
            intent.addFlags(268435488);
            intent.putExtra("packageName", str);
            this.mContext.sendBroadcastAsUser(intent, UserHandle.of(this.mUserId));
        }
        this.mProcessedPackagesJournal.addPackage(str);
    }

    public void writeRestoreTokens() {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(this.mTokenFile, "rwd");
            try {
                randomAccessFile.writeInt(1);
                randomAccessFile.writeLong(this.mAncestralToken);
                randomAccessFile.writeLong(this.mCurrentToken);
                Set set = this.mAncestralPackages;
                if (set == null) {
                    randomAccessFile.writeInt(-1);
                } else {
                    randomAccessFile.writeInt(set.size());
                    Slog.v("BackupManagerService", addUserIdToLogMessage(this.mUserId, "Ancestral packages:  " + this.mAncestralPackages.size()));
                    Iterator it = this.mAncestralPackages.iterator();
                    while (it.hasNext()) {
                        randomAccessFile.writeUTF((String) it.next());
                    }
                }
                randomAccessFile.close();
            } finally {
            }
        } catch (IOException e) {
            Slog.w("BackupManagerService", addUserIdToLogMessage(this.mUserId, "Unable to write token file:"), e);
        }
    }

    public IBackupAgent bindToAgentSynchronous(ApplicationInfo applicationInfo, int i, int i2) {
        IBackupAgent iBackupAgent;
        synchronized (this.mAgentConnectLock) {
            this.mConnecting = true;
            iBackupAgent = null;
            this.mConnectedAgent = null;
            try {
                if (this.mActivityManager.bindBackupAgent(applicationInfo.packageName, i, this.mUserId, i2)) {
                    Slog.d("BackupManagerService", addUserIdToLogMessage(this.mUserId, "awaiting agent for " + applicationInfo));
                    long currentTimeMillis = System.currentTimeMillis() + 10000;
                    while (this.mConnecting && this.mConnectedAgent == null && System.currentTimeMillis() < currentTimeMillis) {
                        try {
                            this.mAgentConnectLock.wait(5000L);
                        } catch (InterruptedException e) {
                            Slog.w("BackupManagerService", addUserIdToLogMessage(this.mUserId, "Interrupted: " + e));
                            this.mConnecting = false;
                            this.mConnectedAgent = null;
                        }
                    }
                    if (this.mConnecting) {
                        Slog.w("BackupManagerService", addUserIdToLogMessage(this.mUserId, "Timeout waiting for agent " + applicationInfo));
                        this.mConnectedAgent = null;
                    }
                    Slog.i("BackupManagerService", addUserIdToLogMessage(this.mUserId, "got agent " + this.mConnectedAgent));
                    iBackupAgent = this.mConnectedAgent;
                }
            } catch (RemoteException unused) {
            }
        }
        if (iBackupAgent == null) {
            this.mActivityManagerInternal.clearPendingBackup(this.mUserId);
        }
        return iBackupAgent;
    }

    public void unbindAgent(ApplicationInfo applicationInfo) {
        try {
            this.mActivityManager.unbindBackupAgent(applicationInfo);
        } catch (RemoteException unused) {
        }
    }

    public void clearApplicationDataAfterRestoreFailure(String str) {
        clearApplicationDataSynchronous(str, true, false);
    }

    public void clearApplicationDataBeforeRestore(String str) {
        clearApplicationDataSynchronous(str, false, true);
    }

    public final void clearApplicationDataSynchronous(String str, boolean z, boolean z2) {
        try {
            ApplicationInfo applicationInfo = this.mPackageManager.getPackageInfoAsUser(str, 0, this.mUserId).applicationInfo;
            if (!z || applicationInfo.targetSdkVersion < 29 ? (applicationInfo.flags & 64) != 0 : (applicationInfo.privateFlags & 67108864) != 0) {
                ClearDataObserver clearDataObserver = new ClearDataObserver(this);
                synchronized (this.mClearDataLock) {
                    this.mClearingData = true;
                    try {
                        this.mActivityManager.clearApplicationUserData(str, z2, clearDataObserver, this.mUserId);
                    } catch (RemoteException unused) {
                    }
                    long currentTimeMillis = System.currentTimeMillis() + 30000;
                    while (this.mClearingData && System.currentTimeMillis() < currentTimeMillis) {
                        try {
                            this.mClearDataLock.wait(5000L);
                        } catch (InterruptedException e) {
                            this.mClearingData = false;
                            Slog.w("BackupManagerService", addUserIdToLogMessage(this.mUserId, "Interrupted while waiting for " + str + " data to be cleared"), e);
                        }
                    }
                    if (this.mClearingData) {
                        Slog.w("BackupManagerService", addUserIdToLogMessage(this.mUserId, "Clearing app data for " + str + " timed out"));
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException unused2) {
            Slog.w("BackupManagerService", addUserIdToLogMessage(this.mUserId, "Tried to clear data for " + str + " but not found"));
        }
    }

    public final BackupEligibilityRules getEligibilityRulesForRestoreAtInstall(long j) {
        if (this.mAncestralBackupDestination == 1 && j == this.mAncestralToken) {
            return getEligibilityRulesForOperation(1);
        }
        return this.mScheduledBackupEligibility;
    }

    public long getAvailableRestoreToken(String str) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.BACKUP", "getAvailableRestoreToken");
        long j = this.mAncestralToken;
        synchronized (this.mQueueLock) {
            if (this.mCurrentToken != 0 && this.mProcessedPackagesJournal.hasBeenProcessed(str)) {
                j = this.mCurrentToken;
            }
        }
        return j;
    }

    public int requestBackup(String[] strArr, IBackupObserver iBackupObserver, IBackupManagerMonitor iBackupManagerMonitor, int i) {
        this.mContext.enforceCallingPermission("android.permission.BACKUP", "requestBackup");
        if (strArr == null || strArr.length < 1) {
            Slog.e("BackupManagerService", addUserIdToLogMessage(this.mUserId, "No packages named for backup request"));
            BackupObserverUtils.sendBackupFinished(iBackupObserver, -1000);
            BackupManagerMonitorUtils.monitorEvent(iBackupManagerMonitor, 49, null, 1, null);
            throw new IllegalArgumentException("No packages are provided for backup");
        }
        if (!this.mEnabled || !this.mSetupComplete) {
            Slog.i("BackupManagerService", addUserIdToLogMessage(this.mUserId, "Backup requested but enabled=" + this.mEnabled + " setupComplete=" + this.mSetupComplete));
            BackupObserverUtils.sendBackupFinished(iBackupObserver, -2001);
            BackupManagerMonitorUtils.monitorEvent(iBackupManagerMonitor, this.mSetupComplete ? 13 : 14, null, 3, null);
            return -2001;
        }
        try {
            TransportManager transportManager = this.mTransportManager;
            String transportDirName = transportManager.getTransportDirName(transportManager.getCurrentTransportName());
            final TransportConnection currentTransportClientOrThrow = this.mTransportManager.getCurrentTransportClientOrThrow("BMS.requestBackup()");
            int backupDestinationFromTransport = getBackupDestinationFromTransport(currentTransportClientOrThrow);
            OnTaskFinishedListener onTaskFinishedListener = new OnTaskFinishedListener() { // from class: com.android.server.backup.UserBackupManagerService$$ExternalSyntheticLambda9
                @Override // com.android.server.backup.internal.OnTaskFinishedListener
                public final void onFinished(String str) {
                    UserBackupManagerService.this.lambda$requestBackup$1(currentTransportClientOrThrow, str);
                }
            };
            BackupEligibilityRules eligibilityRulesForOperation = getEligibilityRulesForOperation(backupDestinationFromTransport);
            Message obtainMessage = this.mBackupHandler.obtainMessage(15);
            obtainMessage.obj = getRequestBackupParams(strArr, iBackupObserver, iBackupManagerMonitor, i, eligibilityRulesForOperation, currentTransportClientOrThrow, transportDirName, onTaskFinishedListener);
            this.mBackupHandler.sendMessage(obtainMessage);
            return 0;
        } catch (RemoteException | TransportNotAvailableException | TransportNotRegisteredException unused) {
            BackupObserverUtils.sendBackupFinished(iBackupObserver, -1000);
            BackupManagerMonitorUtils.monitorEvent(iBackupManagerMonitor, 50, null, 1, null);
            return -1000;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestBackup$1(TransportConnection transportConnection, String str) {
        this.mTransportManager.disposeOfTransportClient(transportConnection, str);
    }

    public BackupParams getRequestBackupParams(String[] strArr, IBackupObserver iBackupObserver, IBackupManagerMonitor iBackupManagerMonitor, int i, BackupEligibilityRules backupEligibilityRules, TransportConnection transportConnection, String str, OnTaskFinishedListener onTaskFinishedListener) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (String str2 : strArr) {
            if ("@pm@".equals(str2)) {
                arrayList2.add(str2);
            } else {
                try {
                    PackageInfo packageInfoAsUser = this.mPackageManager.getPackageInfoAsUser(str2, 134217728, this.mUserId);
                    if (!backupEligibilityRules.appIsEligibleForBackup(packageInfoAsUser.applicationInfo)) {
                        BackupObserverUtils.sendBackupOnPackageResult(iBackupObserver, str2, -2001);
                    } else if (backupEligibilityRules.appGetsFullBackup(packageInfoAsUser)) {
                        arrayList.add(packageInfoAsUser.packageName);
                    } else {
                        arrayList2.add(packageInfoAsUser.packageName);
                    }
                } catch (PackageManager.NameNotFoundException unused) {
                    BackupObserverUtils.sendBackupOnPackageResult(iBackupObserver, str2, -2002);
                }
            }
        }
        EventLog.writeEvent(2828, Integer.valueOf(strArr.length), Integer.valueOf(arrayList2.size()), Integer.valueOf(arrayList.size()));
        return new BackupParams(transportConnection, str, arrayList2, arrayList, iBackupObserver, iBackupManagerMonitor, onTaskFinishedListener, true, (i & 1) != 0, backupEligibilityRules);
    }

    public void cancelBackups() {
        this.mContext.enforceCallingPermission("android.permission.BACKUP", "cancelBackups");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Iterator it = this.mOperationStorage.operationTokensForOpType(2).iterator();
            while (it.hasNext()) {
                this.mOperationStorage.cancelOperation(((Integer) it.next()).intValue(), true, new IntConsumer() { // from class: com.android.server.backup.UserBackupManagerService$$ExternalSyntheticLambda8
                    @Override // java.util.function.IntConsumer
                    public final void accept(int i) {
                        UserBackupManagerService.lambda$cancelBackups$2(i);
                    }
                });
            }
            KeyValueBackupJob.schedule(this.mUserId, this.mContext, ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS, this);
            FullBackupJob.schedule(this.mUserId, this.mContext, 7200000L, this);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void prepareOperationTimeout(int i, long j, BackupRestoreTask backupRestoreTask, int i2) {
        if (this.mSepTimeOut) {
            j = this.mSepTimeoutMin * 60 * 1000;
        }
        if (i2 != 0 && i2 != 1) {
            Slog.wtf("BackupManagerService", addUserIdToLogMessage(this.mUserId, "prepareOperationTimeout() doesn't support operation " + Integer.toHexString(i) + " of type " + i2));
            return;
        }
        this.mOperationStorage.registerOperation(i, 0, backupRestoreTask, i2);
        this.mBackupHandler.sendMessageDelayed(this.mBackupHandler.obtainMessage(getMessageIdForOperationType(i2), i, 0, backupRestoreTask), j);
    }

    public final int getMessageIdForOperationType(int i) {
        if (i == 0) {
            return 17;
        }
        if (i == 1) {
            return 18;
        }
        Slog.wtf("BackupManagerService", addUserIdToLogMessage(this.mUserId, "getMessageIdForOperationType called on invalid operation type: " + i));
        return -1;
    }

    public boolean waitUntilOperationComplete(int i) {
        return this.mOperationStorage.waitUntilOperationComplete(i, new IntConsumer() { // from class: com.android.server.backup.UserBackupManagerService$$ExternalSyntheticLambda15
            @Override // java.util.function.IntConsumer
            public final void accept(int i2) {
                UserBackupManagerService.this.lambda$waitUntilOperationComplete$3(i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$waitUntilOperationComplete$3(int i) {
        this.mBackupHandler.removeMessages(getMessageIdForOperationType(i));
    }

    public void handleCancel(int i, boolean z) {
        this.mOperationStorage.cancelOperation(i, z, new IntConsumer() { // from class: com.android.server.backup.UserBackupManagerService$$ExternalSyntheticLambda14
            @Override // java.util.function.IntConsumer
            public final void accept(int i2) {
                UserBackupManagerService.this.lambda$handleCancel$4(i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleCancel$4(int i) {
        if (i == 0 || i == 1) {
            this.mBackupHandler.removeMessages(getMessageIdForOperationType(i));
        }
    }

    public boolean isBackupOperationInProgress() {
        return this.mOperationStorage.isBackupOperationInProgress();
    }

    public void tearDownAgentAndKill(ApplicationInfo applicationInfo) {
        if (applicationInfo == null) {
            return;
        }
        try {
            this.mActivityManager.unbindBackupAgent(applicationInfo);
            if (UserHandle.isCore(applicationInfo.uid) || applicationInfo.packageName.equals("com.android.backupconfirm")) {
                return;
            }
            this.mActivityManager.killApplicationProcess(applicationInfo.processName, applicationInfo.uid);
        } catch (RemoteException unused) {
            Slog.d("BackupManagerService", addUserIdToLogMessage(this.mUserId, "Lost app trying to shut down"));
        }
    }

    public void scheduleNextFullBackupJob(long j) {
        synchronized (this.mQueueLock) {
            if (this.mFullBackupQueue.size() > 0) {
                long currentTimeMillis = System.currentTimeMillis() - ((FullBackupEntry) this.mFullBackupQueue.get(0)).lastBackup;
                long fullBackupIntervalMilliseconds = this.mConstants.getFullBackupIntervalMilliseconds();
                FullBackupJob.schedule(this.mUserId, this.mContext, Math.max(j, currentTimeMillis < fullBackupIntervalMilliseconds ? fullBackupIntervalMilliseconds - currentTimeMillis : 0L), this);
            } else {
                Slog.i("BackupManagerService", addUserIdToLogMessage(this.mUserId, "Full backup queue empty; not scheduling"));
            }
        }
    }

    public final void dequeueFullBackupLocked(String str) {
        for (int size = this.mFullBackupQueue.size() - 1; size >= 0; size--) {
            if (str.equals(((FullBackupEntry) this.mFullBackupQueue.get(size)).packageName)) {
                this.mFullBackupQueue.remove(size);
            }
        }
    }

    public void enqueueFullBackup(String str, long j) {
        int i;
        FullBackupEntry fullBackupEntry = new FullBackupEntry(str, j);
        synchronized (this.mQueueLock) {
            dequeueFullBackupLocked(str);
            if (j > 0) {
                i = this.mFullBackupQueue.size() - 1;
                while (true) {
                    if (i < 0) {
                        break;
                    }
                    if (((FullBackupEntry) this.mFullBackupQueue.get(i)).lastBackup <= j) {
                        this.mFullBackupQueue.add(i + 1, fullBackupEntry);
                        break;
                    }
                    i--;
                }
            } else {
                i = -1;
            }
            if (i < 0) {
                this.mFullBackupQueue.add(0, fullBackupEntry);
            }
        }
        writeFullBackupScheduleAsync();
    }

    public final boolean fullBackupAllowable(String str) {
        if (!this.mTransportManager.isTransportRegistered(str)) {
            Slog.w("BackupManagerService", addUserIdToLogMessage(this.mUserId, "Transport not registered; full data backup not performed"));
            return false;
        }
        try {
            if (new File(new File(this.mBaseStateDir, this.mTransportManager.getTransportDirName(str)), "@pm@").length() > 0) {
                return true;
            }
            Slog.i("BackupManagerService", addUserIdToLogMessage(this.mUserId, "Full backup requested but dataset not yet initialized"));
            return false;
        } catch (Exception e) {
            Slog.w("BackupManagerService", addUserIdToLogMessage(this.mUserId, "Unable to get transport name: " + e.getMessage()));
            return false;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:47:0x01e7 A[LOOP:0: B:24:0x0067->B:47:0x01e7, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x015d A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:59:0x01a8 A[Catch: all -> 0x01f0, TryCatch #0 {, blocks: (B:18:0x0051, B:20:0x0055, B:21:0x0062, B:24:0x0067, B:105:0x006f, B:52:0x0161, B:54:0x016e, B:57:0x018b, B:59:0x01a8, B:60:0x01cd, B:62:0x01d0, B:63:0x01e5, B:67:0x0199, B:26:0x007e, B:30:0x008e, B:36:0x00a7, B:94:0x00b9, B:38:0x00cc, B:41:0x00d4, B:73:0x00e3, B:76:0x00f3, B:79:0x0107, B:82:0x0148), top: B:17:0x0051 }] */
    /* JADX WARN: Removed duplicated region for block: B:62:0x01d0 A[Catch: all -> 0x01f0, TryCatch #0 {, blocks: (B:18:0x0051, B:20:0x0055, B:21:0x0062, B:24:0x0067, B:105:0x006f, B:52:0x0161, B:54:0x016e, B:57:0x018b, B:59:0x01a8, B:60:0x01cd, B:62:0x01d0, B:63:0x01e5, B:67:0x0199, B:26:0x007e, B:30:0x008e, B:36:0x00a7, B:94:0x00b9, B:38:0x00cc, B:41:0x00d4, B:73:0x00e3, B:76:0x00f3, B:79:0x0107, B:82:0x0148), top: B:17:0x0051 }] */
    /* JADX WARN: Removed duplicated region for block: B:84:0x0151  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x0153  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean beginFullBackup(com.android.server.backup.FullBackupJob r25) {
        /*
            Method dump skipped, instructions count: 504
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.backup.UserBackupManagerService.beginFullBackup(com.android.server.backup.FullBackupJob):boolean");
    }

    public void endFullBackup() {
        new Thread(new Runnable() { // from class: com.android.server.backup.UserBackupManagerService.3
            @Override // java.lang.Runnable
            public void run() {
                PerformFullTransportBackupTask performFullTransportBackupTask;
                synchronized (UserBackupManagerService.this.mQueueLock) {
                    performFullTransportBackupTask = UserBackupManagerService.this.mRunningFullBackupTask != null ? UserBackupManagerService.this.mRunningFullBackupTask : null;
                }
                if (performFullTransportBackupTask != null) {
                    Slog.i("BackupManagerService", UserBackupManagerService.addUserIdToLogMessage(UserBackupManagerService.this.mUserId, "Telling running backup to stop"));
                    performFullTransportBackupTask.handleCancel(true);
                }
            }
        }, "end-full-backup").start();
    }

    public void restoreWidgetData(String str, byte[] bArr) {
        AppWidgetBackupBridge.restoreWidgetState(str, bArr, this.mUserId);
    }

    public void dataChangedImpl(String str) {
        dataChangedImpl(str, dataChangedTargets(str));
    }

    public final void dataChangedImpl(String str, HashSet hashSet) {
        if (hashSet == null) {
            Slog.w("BackupManagerService", addUserIdToLogMessage(this.mUserId, "dataChanged but no participant pkg='" + str + "' uid=" + Binder.getCallingUid()));
            return;
        }
        synchronized (this.mQueueLock) {
            if (hashSet.contains(str)) {
                if (this.mPendingBackups.put(str, new BackupRequest(str)) == null) {
                    writeToJournalLocked(str);
                }
            }
        }
        KeyValueBackupJob.schedule(this.mUserId, this.mContext, this);
    }

    public final HashSet dataChangedTargets(String str) {
        HashSet union;
        HashSet hashSet;
        if (this.mContext.checkPermission("android.permission.BACKUP", Binder.getCallingPid(), Binder.getCallingUid()) == -1) {
            synchronized (this.mBackupParticipants) {
                hashSet = (HashSet) this.mBackupParticipants.get(Binder.getCallingUid());
            }
            return hashSet;
        }
        if ("@pm@".equals(str)) {
            return Sets.newHashSet(new String[]{"@pm@"});
        }
        synchronized (this.mBackupParticipants) {
            union = SparseArrayUtils.union(this.mBackupParticipants);
        }
        return union;
    }

    public final void writeToJournalLocked(String str) {
        try {
            if (this.mJournal == null) {
                this.mJournal = DataChangedJournal.newJournal(this.mJournalDir);
            }
            this.mJournal.addPackage(str);
        } catch (IOException e) {
            Slog.e("BackupManagerService", addUserIdToLogMessage(this.mUserId, "Can't write " + str + " to backup journal"), e);
            this.mJournal = null;
        }
    }

    public void dataChanged(final String str) {
        final HashSet dataChangedTargets = dataChangedTargets(str);
        if (dataChangedTargets == null) {
            Slog.w("BackupManagerService", addUserIdToLogMessage(this.mUserId, "dataChanged but no participant pkg='" + str + "' uid=" + Binder.getCallingUid()));
            return;
        }
        this.mBackupHandler.post(new Runnable() { // from class: com.android.server.backup.UserBackupManagerService.4
            @Override // java.lang.Runnable
            public void run() {
                UserBackupManagerService.this.dataChangedImpl(str, dataChangedTargets);
            }
        });
    }

    public void initializeTransports(String[] strArr, IBackupObserver iBackupObserver) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.BACKUP", "initializeTransport");
        Slog.v("BackupManagerService", addUserIdToLogMessage(this.mUserId, "initializeTransport(): " + Arrays.asList(strArr)));
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mWakelock.acquire();
            this.mBackupHandler.post(new PerformInitializeTask(this, strArr, iBackupObserver, new OnTaskFinishedListener() { // from class: com.android.server.backup.UserBackupManagerService$$ExternalSyntheticLambda5
                @Override // com.android.server.backup.internal.OnTaskFinishedListener
                public final void onFinished(String str) {
                    UserBackupManagerService.this.lambda$initializeTransports$5(str);
                }
            }));
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initializeTransports$5(String str) {
        this.mWakelock.release();
    }

    public void setAncestralSerialNumber(long j) {
        this.mContext.enforceCallingPermission("android.permission.BACKUP", "setAncestralSerialNumber");
        Slog.v("BackupManagerService", addUserIdToLogMessage(this.mUserId, "Setting ancestral work profile id to " + j));
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(getAncestralSerialNumberFile(), "rwd");
            try {
                randomAccessFile.writeLong(j);
                randomAccessFile.close();
            } finally {
            }
        } catch (IOException e) {
            Slog.w("BackupManagerService", addUserIdToLogMessage(this.mUserId, "Unable to write to work profile serial mapping file:"), e);
        }
    }

    public long getAncestralSerialNumber() {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(getAncestralSerialNumberFile(), "r");
            try {
                long readLong = randomAccessFile.readLong();
                randomAccessFile.close();
                return readLong;
            } catch (Throwable th) {
                try {
                    randomAccessFile.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (FileNotFoundException unused) {
            return -1L;
        } catch (IOException e) {
            Slog.w("BackupManagerService", addUserIdToLogMessage(this.mUserId, "Unable to read work profile serial number file:"), e);
            return -1L;
        }
    }

    public final File getAncestralSerialNumberFile() {
        if (this.mAncestralSerialNumberFile == null) {
            this.mAncestralSerialNumberFile = new File(UserBackupManagerFiles.getBaseStateDir(getUserId()), "serial_id");
        }
        return this.mAncestralSerialNumberFile;
    }

    public void setAncestralSerialNumberFile(File file) {
        this.mAncestralSerialNumberFile = file;
    }

    public void clearBackupData(String str, String str2) {
        Set packagesCopy;
        Slog.v("BackupManagerService", addUserIdToLogMessage(this.mUserId, "clearBackupData() of " + str2 + " on " + str));
        try {
            PackageInfo packageInfoAsUser = this.mPackageManager.getPackageInfoAsUser(str2, 134217728, this.mUserId);
            if (this.mContext.checkPermission("android.permission.BACKUP", Binder.getCallingPid(), Binder.getCallingUid()) == -1) {
                packagesCopy = (Set) this.mBackupParticipants.get(Binder.getCallingUid());
            } else {
                packagesCopy = this.mProcessedPackagesJournal.getPackagesCopy();
            }
            if (packagesCopy.contains(str2)) {
                this.mBackupHandler.removeMessages(12);
                synchronized (this.mQueueLock) {
                    final TransportConnection transportClient = this.mTransportManager.getTransportClient(str, "BMS.clearBackupData()");
                    if (transportClient == null) {
                        this.mBackupHandler.sendMessageDelayed(this.mBackupHandler.obtainMessage(12, new ClearRetryParams(str, str2)), ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS);
                        return;
                    }
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        OnTaskFinishedListener onTaskFinishedListener = new OnTaskFinishedListener() { // from class: com.android.server.backup.UserBackupManagerService$$ExternalSyntheticLambda10
                            @Override // com.android.server.backup.internal.OnTaskFinishedListener
                            public final void onFinished(String str3) {
                                UserBackupManagerService.this.lambda$clearBackupData$6(transportClient, str3);
                            }
                        };
                        this.mWakelock.acquire();
                        this.mBackupHandler.sendMessage(this.mBackupHandler.obtainMessage(4, new ClearParams(transportClient, packageInfoAsUser, onTaskFinishedListener)));
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException unused) {
            Slog.d("BackupManagerService", addUserIdToLogMessage(this.mUserId, "No such package '" + str2 + "' - not clearing backup data"));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$clearBackupData$6(TransportConnection transportConnection, String str) {
        this.mTransportManager.disposeOfTransportClient(transportConnection, str);
    }

    public void backupNow() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.BACKUP", "backupNow");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (!this.mPowerManager.getPowerSaveState(5).batterySaverEnabled) {
                Slog.v("BackupManagerService", addUserIdToLogMessage(this.mUserId, "Scheduling immediate backup pass"));
                synchronized (getQueueLock()) {
                    if (getPendingInits().size() > 0) {
                        try {
                            getAlarmManager().cancel(this.mRunInitIntent);
                            this.mRunInitIntent.send();
                        } catch (PendingIntent.CanceledException unused) {
                            Slog.w("BackupManagerService", addUserIdToLogMessage(this.mUserId, "Run init intent cancelled"));
                        }
                        return;
                    }
                    if (isEnabled() && isSetupComplete()) {
                        this.mBackupHandler.sendMessage(this.mBackupHandler.obtainMessage(1));
                        KeyValueBackupJob.cancel(this.mUserId, this.mContext);
                    }
                    Slog.w("BackupManagerService", addUserIdToLogMessage(this.mUserId, "Backup pass but enabled=" + isEnabled() + " setupComplete=" + isSetupComplete()));
                    return;
                }
            }
            Slog.v("BackupManagerService", addUserIdToLogMessage(this.mUserId, "Not running backup while in battery save mode"));
            KeyValueBackupJob.schedule(this.mUserId, this.mContext, this);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public Map semBackupPackage(ParcelFileDescriptor parcelFileDescriptor, String[] strArr, String str, int i) {
        if (strArr == null || strArr.length <= 0 || parcelFileDescriptor == null) {
            throw new IllegalArgumentException("packageName is not available");
        }
        this.mSepTimeOut = true;
        synchronized (this.mBackupRestoreLock) {
            boolean z = (i & 1) != 0;
            boolean z2 = (i & 2) != 0;
            boolean z3 = (i & 4) != 0;
            boolean z4 = (i & 8) != 0;
            boolean z5 = (i & 16) != 0;
            boolean z6 = (i & 32) != 0;
            boolean z7 = (i & 64) != 0;
            boolean z8 = (i & 128) != 0;
            boolean z9 = (i & 256) != 0;
            boolean z10 = (i & IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES) != 0;
            if (z9) {
                return null;
            }
            this.mEncPassword = str;
            this.mExtraFlag = i;
            adbBackup(parcelFileDescriptor, z, z8 ? true : z2, z3, z4, z5, z6, z7, z10, strArr, false);
            return null;
        }
    }

    public Map semBackupPackagePath(ParcelFileDescriptor parcelFileDescriptor, String[] strArr, String str, int i, String[] strArr2) {
        if (strArr == null || strArr.length <= 0 || parcelFileDescriptor == null) {
            throw new IllegalArgumentException("packageName is not available");
        }
        this.mSepTimeOut = true;
        boolean equalsIgnoreCase = "CHINA".equalsIgnoreCase(SystemProperties.get(ActivationMonitor.COUNTRY_CODE_PROPERTY));
        if (strArr2 != null && equalsIgnoreCase) {
            this.mSmartSwitchBackupPath = strArr2;
            for (String str2 : strArr2) {
                Log.i("BackupManagerService", "mSmartSwitchBackupPath = " + str2);
            }
        }
        synchronized (this.mBackupRestoreLock) {
            boolean z = (i & 1) != 0;
            boolean z2 = (i & 2) != 0;
            boolean z3 = (i & 4) != 0;
            boolean z4 = (i & 8) != 0;
            boolean z5 = (i & 16) != 0;
            boolean z6 = (i & 32) != 0;
            boolean z7 = (i & 64) != 0;
            boolean z8 = (i & 128) != 0;
            boolean z9 = (i & 256) != 0;
            boolean z10 = (i & IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES) != 0;
            if (z9) {
                return null;
            }
            this.mEncPassword = str;
            this.mExtraFlag = i;
            adbBackup(parcelFileDescriptor, z, z8 ? true : z2, z3, z4, z5, z6, z7, z10, strArr, false);
            return null;
        }
    }

    public void fullBackupCustomized(String str, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, String[] strArr, boolean z8, String str2, boolean z9, IMemorySaverBackupRestoreObserver iMemorySaverBackupRestoreObserver) {
        ParcelFileDescriptor open = ParcelFileDescriptor.open(new File(str), 805306368);
        this.mPassword = z8;
        this.mExtraFlag = 512;
        BackupManagerYuva backupManagerYuva = mBackupManagerYuva;
        if (backupManagerYuva != null) {
            backupManagerYuva.setMemorySaverObserver(iMemorySaverBackupRestoreObserver);
        }
        this.mEncPassword = str2;
        setSplitBackupFlagVal(true);
        adbBackup(open, z, z2, z3, z4, z5, z6, z7, z9, strArr);
        setSplitBackupFlagVal(false);
    }

    public void semRestorePackage(ParcelFileDescriptor parcelFileDescriptor, String str) {
        if (parcelFileDescriptor == null) {
            throw new IllegalArgumentException("fd is null");
        }
        this.mSepTimeOut = true;
        synchronized (this.mBackupRestoreLock) {
            this.mEncPassword = str;
            adbRestore(parcelFileDescriptor, false);
        }
    }

    public void fullRestoreCustomized(String str, boolean z, String str2, IMemorySaverBackupRestoreObserver iMemorySaverBackupRestoreObserver) {
        ParcelFileDescriptor open = ParcelFileDescriptor.open(new File(str), 805306368);
        this.mPassword = z;
        BackupManagerYuva backupManagerYuva = mBackupManagerYuva;
        if (backupManagerYuva != null) {
            backupManagerYuva.setMemorySaverObserver(iMemorySaverBackupRestoreObserver);
        }
        String[] split = str.split("/");
        String substring = split[split.length - 1].substring(0, split[split.length - 1].length() - 3);
        BackupManagerYuva backupManagerYuva2 = mBackupManagerYuva;
        if (backupManagerYuva2 != null) {
            backupManagerYuva2.sendStartRestoreCallback(substring);
        }
        this.mEncPassword = str2;
        setSplitRestoreFlagVal(true);
        adbRestore(open);
        setSplitRestoreFlagVal(false);
    }

    public final boolean isPrivilegeBackupApp(int i) {
        String nameForUid = this.mPackageManager.getNameForUid(i);
        boolean z = false;
        try {
        } catch (Exception unused) {
            Slog.d("BackupManagerService", "isPrivilegeBackupApp() error");
        }
        if ((!nameForUid.equals("android.uid.system:1000") || this.mPackageManager.checkSignatures(1000, i) != 0) && (!nameForUid.equals("android.uid.samsungcloud:5009") || this.mPackageManager.checkSignatures(5009, i) != 0)) {
            for (String str : this.mPrivilegePkgName) {
                if (!str.equals(nameForUid) || this.mPackageManager.checkSignatures("android", nameForUid) != 0) {
                }
            }
            Slog.d("BackupManagerService", "isPrivilegeBackupApp() pkg name is " + nameForUid + " value : " + z);
            return z;
        }
        z = true;
        Slog.d("BackupManagerService", "isPrivilegeBackupApp() pkg name is " + nameForUid + " value : " + z);
        return z;
    }

    public boolean semIsBackupEnabled() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.BACKUP", "semIsBackupEnabled");
        return this.mEnabled;
    }

    public void semSetBackupEnabled(boolean z) {
        EnterpriseDeviceManager enterpriseDeviceManager = EnterpriseDeviceManager.getInstance();
        if (z && !enterpriseDeviceManager.getRestrictionPolicy().isBackupAllowed(true)) {
            Slog.w("BackupManagerService", "Backup is not allowed by MDM");
            return;
        }
        this.mContext.enforceCallingOrSelfPermission("android.permission.BACKUP", "setBackupEnabled");
        Slog.i("BackupManagerService", addUserIdToLogMessage(this.mUserId, "Backup enabled => " + z));
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            boolean z2 = this.mEnabled;
            synchronized (this) {
                UserBackupManagerFilePersistedSettings.writeBackupEnableState(this.mUserId, z);
                this.mEnabled = z;
            }
            synchronized (this.mQueueLock) {
                if (z && !z2) {
                    if (this.mSetupComplete) {
                        KeyValueBackupJob.schedule(this.mUserId, this.mContext, this);
                        scheduleNextFullBackupJob(0L);
                    }
                }
                if (!z) {
                    KeyValueBackupJob.cancel(this.mUserId, this.mContext);
                    if (z2 && this.mSetupComplete) {
                        final ArrayList arrayList = new ArrayList();
                        final ArrayList arrayList2 = new ArrayList();
                        this.mTransportManager.forEachRegisteredTransport(new Consumer() { // from class: com.android.server.backup.UserBackupManagerService$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                UserBackupManagerService.this.lambda$semSetBackupEnabled$7(arrayList, arrayList2, (String) obj);
                            }
                        });
                        for (int i = 0; i < arrayList.size(); i++) {
                            recordInitPending(true, (String) arrayList.get(i), (String) arrayList2.get(i));
                        }
                        this.mAlarmManager.set(0, System.currentTimeMillis(), this.mRunInitIntent);
                    }
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$semSetBackupEnabled$7(List list, List list2, String str) {
        try {
            String transportDirName = this.mTransportManager.getTransportDirName(str);
            list.add(str);
            list2.add(transportDirName);
        } catch (TransportNotRegisteredException e) {
            Slog.e("BackupManagerService", addUserIdToLogMessage(this.mUserId, "Unexpected unregistered transport"), e);
        }
    }

    public void semSetAutoRestoreEnabled(boolean z) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.BACKUP", "setAutoRestoreEnabled");
        Slog.i("BackupManagerService", addUserIdToLogMessage(this.mUserId, "Auto restore => " + z));
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this) {
                Settings.Secure.putIntForUser(this.mContext.getContentResolver(), "backup_auto_restore", z ? 1 : 0, this.mUserId);
                this.mAutoRestore = z;
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void semCancelBackupAndRestore() {
        AdbBackupParams adbBackupParams = this.mAdbBackupParams;
        if (adbBackupParams == null && this.mAdbRestoreParams == null) {
            Slog.w("BackupManagerService", "all of adbParams null");
            return;
        }
        if (adbBackupParams != null) {
            Slog.i("BackupManagerService", "BackupParams latch");
            synchronized (this.mAdbBackupParams.latch) {
                this.mAdbBackupParams.latch.set(true);
                this.mAdbBackupParams.latch.notifyAll();
            }
        }
        if (this.mAdbRestoreParams != null) {
            Slog.i("BackupManagerService", "RestoreParams latch");
            synchronized (this.mAdbRestoreParams.latch) {
                this.mAdbRestoreParams.latch.set(true);
                this.mAdbRestoreParams.latch.notifyAll();
            }
        }
    }

    public boolean semSetTimeoutBackupAndRestore(int i) {
        boolean z;
        if (i < 0 || i > 300) {
            z = false;
        } else {
            this.mSepTimeoutMin = i;
            z = true;
        }
        Slog.i("BackupManagerService", "semSetTimeoutBackupAndRestore, timeout(min) : " + this.mSepTimeoutMin + ", " + z);
        return z;
    }

    public void semSetTransportFlagsForAdbBackup(int i) {
        this.mTransportFlagsForAdbBackup = i;
        Slog.i("BackupManagerService", "semSetTransportFlagsForAdbBackup, set flags : " + this.mTransportFlagsForAdbBackup);
    }

    public boolean semDisableDataExtractionRule(boolean z) {
        if ("CHINA".equalsIgnoreCase(SystemProperties.get(ActivationMonitor.COUNTRY_CODE_PROPERTY))) {
            this.mDisableDataExtractionRule = z;
            return true;
        }
        this.mDisableDataExtractionRule = false;
        return false;
    }

    public boolean getDataExtractionRuleStatus() {
        return this.mDisableDataExtractionRule;
    }

    public void adbBackup(ParcelFileDescriptor parcelFileDescriptor, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, String[] strArr) {
        adbBackup(parcelFileDescriptor, z, z2, z3, z4, z5, z6, z7, z8, strArr, true);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r22v0, types: [com.android.server.backup.UserBackupManagerService] */
    /* JADX WARN: Type inference failed for: r5v0 */
    /* JADX WARN: Type inference failed for: r5v1 */
    /* JADX WARN: Type inference failed for: r5v10, types: [android.util.SparseArray] */
    /* JADX WARN: Type inference failed for: r5v6, types: [boolean] */
    /* JADX WARN: Type inference failed for: r5v7 */
    /* JADX WARN: Type inference failed for: r5v8 */
    /* JADX WARN: Type inference failed for: r6v0 */
    /* JADX WARN: Type inference failed for: r6v1 */
    /* JADX WARN: Type inference failed for: r6v6, types: [boolean] */
    /* JADX WARN: Type inference failed for: r6v7 */
    public void adbBackup(ParcelFileDescriptor parcelFileDescriptor, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, String[] strArr, boolean z9) {
        boolean z10;
        ?? r5;
        ?? r6;
        Throwable th;
        int i;
        String[] strArr2;
        AdbBackupParams adbBackupParams;
        String[] strArr3;
        int i2;
        this.mContext.enforceCallingPermission("android.permission.BACKUP", "adbBackup");
        if (UserHandle.getCallingUserId() != 0) {
            throw new IllegalStateException("Backup supported only for the device owner");
        }
        if (!z5 && !z3 && (strArr == null || strArr.length == 0)) {
            throw new IllegalArgumentException("Backup requested but neither shared nor any apps named");
        }
        int callingUid = Binder.getCallingUid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                if (!this.mSetupComplete) {
                    Slog.i("BackupManagerService", addUserIdToLogMessage(this.mUserId, "Backup not supported before setup"));
                    try {
                        parcelFileDescriptor.close();
                    } catch (IOException e) {
                        Slog.e("BackupManagerService", addUserIdToLogMessage(this.mUserId, "IO error closing output for adb backup: " + e.getMessage()));
                    }
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    Slog.d("BackupManagerService", addUserIdToLogMessage(this.mUserId, "Adb backup processing complete."));
                    this.mSmartSwitchBackupPath = null;
                    this.mSepTimeoutMin = 30;
                    this.mSepTimeOut = false;
                    return;
                }
                Slog.v("BackupManagerService", addUserIdToLogMessage(this.mUserId, "Requesting backup: apks=" + z + " obb=" + z2 + " shared=" + z3 + " all=" + z5 + " system=" + z6 + " includekeyvalue=" + z8 + " pkgs=" + Arrays.toString(strArr)));
                Slog.i("BackupManagerService", addUserIdToLogMessage(this.mUserId, "Beginning adb backup..."));
                r5 = z2;
                r6 = z3;
                try {
                    adbBackupParams = new AdbBackupParams(parcelFileDescriptor, z, r5, r6, z4, z5, z6, z7, z8, strArr, z9 ? getEligibilityRulesForOperation(2) : getEligibilityRulesForOperation(1), this.mExtraFlag, this.mSmartSwitchBackupPath);
                    adbBackupParams.transportFlags = this.mTransportFlagsForAdbBackup;
                    this.mAdbBackupParams = adbBackupParams;
                    z10 = false;
                } catch (Throwable th2) {
                    th = th2;
                    z10 = false;
                }
                try {
                    this.mTransportFlagsForAdbBackup = 0;
                    adbBackupParams.privilegeApp = isPrivilegeBackupApp(callingUid);
                    int generateRandomIntegerToken = generateRandomIntegerToken();
                    synchronized (this.mAdbBackupRestoreConfirmations) {
                        try {
                            try {
                                r5 = this.mAdbBackupRestoreConfirmations;
                                r5.put(generateRandomIntegerToken, adbBackupParams);
                            } catch (Throwable th3) {
                                th = th3;
                                th = th;
                                strArr2 = r5;
                                i = r6;
                                try {
                                    parcelFileDescriptor.close();
                                } catch (IOException e2) {
                                    Slog.e("BackupManagerService", addUserIdToLogMessage(this.mUserId, "IO error closing output for adb backup: " + e2.getMessage()));
                                }
                                Binder.restoreCallingIdentity(clearCallingIdentity);
                                Slog.d("BackupManagerService", addUserIdToLogMessage(this.mUserId, "Adb backup processing complete."));
                                this.mSmartSwitchBackupPath = strArr2;
                                this.mSepTimeoutMin = i;
                                this.mSepTimeOut = z10;
                                throw th;
                            }
                        } finally {
                            th = th;
                            while (true) {
                                try {
                                    break;
                                } catch (Throwable th4) {
                                    th = th4;
                                }
                            }
                        }
                    }
                    if (adbBackupParams.privilegeApp) {
                        try {
                            acknowledgeAdbBackupOrRestore(generateRandomIntegerToken, true, "", this.mEncPassword, null);
                        } catch (Throwable th5) {
                            th = th5;
                            parcelFileDescriptor.close();
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            Slog.d("BackupManagerService", addUserIdToLogMessage(this.mUserId, "Adb backup processing complete."));
                            this.mSmartSwitchBackupPath = strArr2;
                            this.mSepTimeoutMin = i;
                            this.mSepTimeOut = z10;
                            throw th;
                        }
                    } else {
                        Slog.d("BackupManagerService", addUserIdToLogMessage(this.mUserId, "Starting backup confirmation UI, token=" + generateRandomIntegerToken));
                        if (!startConfirmationUi(generateRandomIntegerToken, "fullback")) {
                            Slog.e("BackupManagerService", addUserIdToLogMessage(this.mUserId, "Unable to launch backup confirmation UI"));
                            this.mAdbBackupRestoreConfirmations.delete(generateRandomIntegerToken);
                            try {
                                parcelFileDescriptor.close();
                            } catch (IOException e3) {
                                Slog.e("BackupManagerService", addUserIdToLogMessage(this.mUserId, "IO error closing output for adb backup: " + e3.getMessage()));
                            }
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            Slog.d("BackupManagerService", addUserIdToLogMessage(this.mUserId, "Adb backup processing complete."));
                            this.mSmartSwitchBackupPath = null;
                            this.mSepTimeoutMin = 30;
                            this.mSepTimeOut = false;
                            return;
                        }
                        strArr3 = null;
                        i2 = 30;
                        this.mPowerManager.userActivity(SystemClock.uptimeMillis(), 0, 0);
                        startConfirmationTimeout(generateRandomIntegerToken, adbBackupParams);
                    }
                    Slog.d("BackupManagerService", addUserIdToLogMessage(this.mUserId, "Waiting for backup completion..."));
                    startSepTimeout(adbBackupParams);
                    waitForCompletion(adbBackupParams);
                    this.mBackupHandler.removeMessages(101, adbBackupParams);
                    try {
                        parcelFileDescriptor.close();
                    } catch (IOException e4) {
                        Slog.e("BackupManagerService", addUserIdToLogMessage(this.mUserId, "IO error closing output for adb backup: " + e4.getMessage()));
                    }
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    Slog.d("BackupManagerService", addUserIdToLogMessage(this.mUserId, "Adb backup processing complete."));
                    this.mSmartSwitchBackupPath = strArr3;
                    this.mSepTimeoutMin = i2;
                    this.mSepTimeOut = false;
                } catch (Throwable th6) {
                    th = th6;
                    r5 = 0;
                    r6 = 30;
                    th = th;
                    strArr2 = r5;
                    i = r6;
                    parcelFileDescriptor.close();
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    Slog.d("BackupManagerService", addUserIdToLogMessage(this.mUserId, "Adb backup processing complete."));
                    this.mSmartSwitchBackupPath = strArr2;
                    this.mSepTimeoutMin = i;
                    this.mSepTimeOut = z10;
                    throw th;
                }
            } catch (Throwable th7) {
                th = th7;
                z10 = false;
                strArr2 = null;
                i = 30;
            }
        } catch (Throwable th8) {
            th = th8;
            z10 = false;
            r5 = 0;
            r6 = 30;
        }
    }

    public void fullTransportBackup(String[] strArr) {
        this.mContext.enforceCallingPermission("android.permission.BACKUP", "fullTransportBackup");
        if (UserHandle.getCallingUserId() != 0) {
            throw new IllegalStateException("Restore supported only for the device owner");
        }
        if (!fullBackupAllowable(this.mTransportManager.getCurrentTransportName())) {
            Slog.i("BackupManagerService", addUserIdToLogMessage(this.mUserId, "Full backup not currently possible -- key/value backup not yet run?"));
        } else {
            Slog.d("BackupManagerService", addUserIdToLogMessage(this.mUserId, "fullTransportBackup()"));
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                CountDownLatch countDownLatch = new CountDownLatch(1);
                PerformFullTransportBackupTask newWithCurrentTransport = PerformFullTransportBackupTask.newWithCurrentTransport(this, this.mOperationStorage, null, strArr, false, null, countDownLatch, null, null, false, "BMS.fullTransportBackup()", getEligibilityRulesForOperation(0));
                this.mWakelock.acquire();
                new Thread(newWithCurrentTransport, "full-transport-master").start();
                while (true) {
                    try {
                        countDownLatch.await();
                        break;
                    } catch (InterruptedException unused) {
                    }
                }
                long currentTimeMillis = System.currentTimeMillis();
                for (String str : strArr) {
                    enqueueFullBackup(str, currentTimeMillis);
                }
            } catch (IllegalStateException e) {
                Slog.w("BackupManagerService", "Failed to start backup: ", e);
                return;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        Slog.d("BackupManagerService", addUserIdToLogMessage(this.mUserId, "Done with full transport backup."));
    }

    public void adbRestore(ParcelFileDescriptor parcelFileDescriptor) {
        adbRestore(parcelFileDescriptor, true);
    }

    public void adbRestore(ParcelFileDescriptor parcelFileDescriptor, boolean z) {
        this.mContext.enforceCallingPermission("android.permission.BACKUP", "adbRestore");
        if (UserHandle.getCallingUserId() != 0) {
            throw new IllegalStateException("Restore supported only for the device owner");
        }
        int callingUid = Binder.getCallingUid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (!this.mSetupComplete) {
                Slog.i("BackupManagerService", addUserIdToLogMessage(this.mUserId, "Full restore not permitted before setup"));
                return;
            }
            Slog.i("BackupManagerService", addUserIdToLogMessage(this.mUserId, "Beginning restore..."));
            AdbRestoreParams adbRestoreParams = new AdbRestoreParams(parcelFileDescriptor);
            this.mAdbRestoreParams = adbRestoreParams;
            adbRestoreParams.privilegeApp = isPrivilegeBackupApp(callingUid);
            if (z) {
                adbRestoreParams.typeMigration = false;
            } else {
                adbRestoreParams.typeMigration = true;
            }
            Slog.i("BackupManagerService", addUserIdToLogMessage(this.mUserId, "params.typeMigration : " + adbRestoreParams.typeMigration));
            int generateRandomIntegerToken = generateRandomIntegerToken();
            synchronized (this.mAdbBackupRestoreConfirmations) {
                this.mAdbBackupRestoreConfirmations.put(generateRandomIntegerToken, adbRestoreParams);
            }
            if (adbRestoreParams.privilegeApp) {
                acknowledgeAdbBackupOrRestore(generateRandomIntegerToken, true, "", this.mEncPassword, null);
            } else {
                Slog.d("BackupManagerService", addUserIdToLogMessage(this.mUserId, "Starting restore confirmation UI, token=" + generateRandomIntegerToken));
                if (!startConfirmationUi(generateRandomIntegerToken, "fullrest")) {
                    Slog.e("BackupManagerService", addUserIdToLogMessage(this.mUserId, "Unable to launch restore confirmation"));
                    this.mAdbBackupRestoreConfirmations.delete(generateRandomIntegerToken);
                    try {
                        parcelFileDescriptor.close();
                    } catch (IOException e) {
                        Slog.w("BackupManagerService", addUserIdToLogMessage(this.mUserId, "Error trying to close fd after adb restore: " + e));
                    }
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    Slog.i("BackupManagerService", addUserIdToLogMessage(this.mUserId, "adb restore processing complete."));
                    return;
                }
                this.mPowerManager.userActivity(SystemClock.uptimeMillis(), 0, 0);
                startConfirmationTimeout(generateRandomIntegerToken, adbRestoreParams);
            }
            Slog.d("BackupManagerService", addUserIdToLogMessage(this.mUserId, "Waiting for restore completion..."));
            startSepTimeout(adbRestoreParams);
            waitForCompletion(adbRestoreParams);
            this.mBackupHandler.removeMessages(101, adbRestoreParams);
            try {
                parcelFileDescriptor.close();
            } catch (IOException e2) {
                Slog.w("BackupManagerService", addUserIdToLogMessage(this.mUserId, "Error trying to close fd after adb restore: " + e2));
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            Slog.i("BackupManagerService", addUserIdToLogMessage(this.mUserId, "adb restore processing complete."));
            this.mSepTimeoutMin = 30;
            this.mSepTimeOut = false;
        } finally {
            try {
                parcelFileDescriptor.close();
            } catch (IOException e3) {
                Slog.w("BackupManagerService", addUserIdToLogMessage(this.mUserId, "Error trying to close fd after adb restore: " + e3));
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            Slog.i("BackupManagerService", addUserIdToLogMessage(this.mUserId, "adb restore processing complete."));
        }
    }

    public void excludeKeysFromRestore(String str, List list) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.BACKUP", "excludeKeysFromRestore");
        this.mBackupPreferences.addExcludedKeys(str, list);
    }

    public void reportDelayedRestoreResult(String str, List list) {
        String currentTransportName = this.mTransportManager.getCurrentTransportName();
        if (currentTransportName == null) {
            Slog.w("BackupManagerService", "Failed to send delayed restore logs as no transport selected");
            return;
        }
        TransportConnection transportConnection = null;
        try {
            try {
                PackageInfo packageInfoAsUser = getPackageManager().getPackageInfoAsUser(str, PackageManager.PackageInfoFlags.of(0L), getUserId());
                transportConnection = this.mTransportManager.getTransportClientOrThrow(currentTransportName, "BMS.reportDelayedRestoreResult");
                BackupManagerMonitorUtils.sendAgentLoggingResults(transportConnection.connectOrThrow("BMS.reportDelayedRestoreResult").getBackupManagerMonitor(), packageInfoAsUser, list, 1);
            } catch (PackageManager.NameNotFoundException | RemoteException | TransportNotAvailableException | TransportNotRegisteredException e) {
                Slog.w("BackupManagerService", "Failed to send delayed restore logs: " + e);
                if (transportConnection == null) {
                    return;
                }
            }
            this.mTransportManager.disposeOfTransportClient(transportConnection, "BMS.reportDelayedRestoreResult");
        } catch (Throwable th) {
            if (transportConnection != null) {
                this.mTransportManager.disposeOfTransportClient(transportConnection, "BMS.reportDelayedRestoreResult");
            }
            throw th;
        }
    }

    public final boolean startConfirmationUi(int i, String str) {
        try {
            Intent intent = new Intent(str);
            intent.setClassName("com.android.backupconfirm", "com.android.backupconfirm.BackupRestoreConfirmation");
            intent.putExtra("conftoken", i);
            intent.addFlags(536870912);
            this.mContext.startActivityAsUser(intent, UserHandle.SYSTEM);
            return true;
        } catch (ActivityNotFoundException unused) {
            return false;
        }
    }

    public final void startConfirmationTimeout(int i, AdbParams adbParams) {
        this.mBackupHandler.sendMessageDelayed(this.mBackupHandler.obtainMessage(9, i, 0, adbParams), 60000L);
    }

    public void startSepTimeout(AdbParams adbParams) {
        int i = this.mSepTimeoutMin * 60 * 1000;
        Slog.d("BackupManagerService", "Posting MSG_SEP_TIMEOUT msg, " + i);
        this.mBackupHandler.sendMessageDelayed(this.mBackupHandler.obtainMessage(101, adbParams), (long) i);
    }

    public final void waitForCompletion(AdbParams adbParams) {
        synchronized (adbParams.latch) {
            while (!adbParams.latch.get()) {
                try {
                    adbParams.latch.wait();
                } catch (InterruptedException unused) {
                }
            }
        }
    }

    public void signalAdbBackupRestoreCompletion(AdbParams adbParams) {
        synchronized (adbParams.latch) {
            adbParams.latch.set(true);
            adbParams.latch.notifyAll();
        }
    }

    public void acknowledgeAdbBackupOrRestore(int i, boolean z, String str, String str2, IFullBackupRestoreObserver iFullBackupRestoreObserver) {
        AdbParams adbParams;
        Slog.d("BackupManagerService", addUserIdToLogMessage(this.mUserId, "acknowledgeAdbBackupOrRestore : token=" + i + " allow=" + z));
        synchronized (this.mAdbBackupRestoreConfirmations) {
            adbParams = (AdbParams) this.mAdbBackupRestoreConfirmations.get(i);
        }
        if (adbParams != null && adbParams.privilegeApp) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.BACKUP", "acknowledgeAdbBackupOrRestore");
        } else {
            this.mContext.enforceCallingPermission("android.permission.BACKUP", "acknowledgeAdbBackupOrRestore");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this.mAdbBackupRestoreConfirmations) {
                AdbParams adbParams2 = (AdbParams) this.mAdbBackupRestoreConfirmations.get(i);
                if (adbParams2 != null) {
                    this.mBackupHandler.removeMessages(9, adbParams2);
                    this.mAdbBackupRestoreConfirmations.delete(i);
                    if (z) {
                        int i2 = adbParams2 instanceof AdbBackupParams ? 2 : 10;
                        adbParams2.observer = iFullBackupRestoreObserver;
                        adbParams2.curPassword = str;
                        adbParams2.encryptPassword = str2;
                        if (this.mWakelock.isHeld() && this.mSepWakeLock) {
                            this.mWakelock.release();
                        }
                        this.mSepWakeLock = true;
                        this.mWakelock.acquire();
                        this.mBackupHandler.sendMessage(this.mBackupHandler.obtainMessage(i2, adbParams2));
                    } else {
                        Slog.w("BackupManagerService", addUserIdToLogMessage(this.mUserId, "User rejected full backup/restore operation"));
                        signalAdbBackupRestoreCompletion(adbParams2);
                    }
                } else {
                    Slog.w("BackupManagerService", addUserIdToLogMessage(this.mUserId, "Attempted to ack full backup/restore with invalid token"));
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void setBackupEnabled(boolean z) {
        setBackupEnabled(z, true);
    }

    public final void setBackupEnabled(boolean z, boolean z2) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.BACKUP", "setBackupEnabled");
        Slog.i("BackupManagerService", addUserIdToLogMessage(this.mUserId, "Backup enabled => " + z));
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            boolean z3 = this.mEnabled;
            synchronized (this) {
                if (z2) {
                    writeEnabledState(z);
                }
                this.mEnabled = z;
            }
            updateStateOnBackupEnabled(z3, z);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public synchronized void setFrameworkSchedulingEnabled(boolean z) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.BACKUP", "setFrameworkSchedulingEnabled");
        if (isFrameworkSchedulingEnabled() == z) {
            return;
        }
        int i = this.mUserId;
        StringBuilder sb = new StringBuilder();
        sb.append(z ? "Enabling" : "Disabling");
        sb.append(" backup scheduling");
        Slog.i("BackupManagerService", addUserIdToLogMessage(i, sb.toString()));
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Settings.Secure.putIntForUser(this.mContext.getContentResolver(), "backup_scheduling_enabled", z ? 1 : 0, this.mUserId);
            if (!z) {
                KeyValueBackupJob.cancel(this.mUserId, this.mContext);
                FullBackupJob.cancel(this.mUserId, this.mContext);
            } else {
                KeyValueBackupJob.schedule(this.mUserId, this.mContext, this);
                scheduleNextFullBackupJob(0L);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public synchronized boolean isFrameworkSchedulingEnabled() {
        return Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "backup_scheduling_enabled", 1, this.mUserId) == 1;
    }

    public void updateStateOnBackupEnabled(boolean z, boolean z2) {
        synchronized (this.mQueueLock) {
            if (z2 && !z) {
                if (this.mSetupComplete) {
                    KeyValueBackupJob.schedule(this.mUserId, this.mContext, this);
                    scheduleNextFullBackupJob(0L);
                }
            }
            if (!z2) {
                KeyValueBackupJob.cancel(this.mUserId, this.mContext);
                if (z && this.mSetupComplete) {
                    final ArrayList arrayList = new ArrayList();
                    final ArrayList arrayList2 = new ArrayList();
                    this.mTransportManager.forEachRegisteredTransport(new Consumer() { // from class: com.android.server.backup.UserBackupManagerService$$ExternalSyntheticLambda12
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            UserBackupManagerService.this.lambda$updateStateOnBackupEnabled$8(arrayList, arrayList2, (String) obj);
                        }
                    });
                    for (int i = 0; i < arrayList.size(); i++) {
                        recordInitPending(true, (String) arrayList.get(i), (String) arrayList2.get(i));
                    }
                    this.mAlarmManager.set(0, System.currentTimeMillis(), this.mRunInitIntent);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateStateOnBackupEnabled$8(List list, List list2, String str) {
        try {
            String transportDirName = this.mTransportManager.getTransportDirName(str);
            list.add(str);
            list2.add(transportDirName);
        } catch (TransportNotRegisteredException e) {
            Slog.e("BackupManagerService", addUserIdToLogMessage(this.mUserId, "Unexpected unregistered transport"), e);
        }
    }

    public void writeEnabledState(boolean z) {
        UserBackupManagerFilePersistedSettings.writeBackupEnableState(this.mUserId, z);
    }

    public boolean readEnabledState() {
        return UserBackupManagerFilePersistedSettings.readBackupEnableState(this.mUserId);
    }

    public void setAutoRestore(boolean z) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.BACKUP", "setAutoRestore");
        Slog.i("BackupManagerService", addUserIdToLogMessage(this.mUserId, "Auto restore => " + z));
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this) {
                Settings.Secure.putIntForUser(this.mContext.getContentResolver(), "backup_auto_restore", z ? 1 : 0, this.mUserId);
                this.mAutoRestore = z;
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean isBackupEnabled() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.BACKUP", "isBackupEnabled");
        return this.mEnabled;
    }

    public String getCurrentTransport() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.BACKUP", "getCurrentTransport");
        return this.mTransportManager.getCurrentTransportName();
    }

    public ComponentName getCurrentTransportComponent() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.BACKUP", "getCurrentTransportComponent");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            ComponentName currentTransportComponent = this.mTransportManager.getCurrentTransportComponent();
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return currentTransportComponent;
        } catch (TransportNotRegisteredException unused) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return null;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public String[] listAllTransports() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.BACKUP", "listAllTransports");
        return this.mTransportManager.getRegisteredTransportNames();
    }

    public ComponentName[] listAllTransportComponents() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.BACKUP", "listAllTransportComponents");
        return this.mTransportManager.getRegisteredTransportComponents();
    }

    public void updateTransportAttributes(ComponentName componentName, String str, Intent intent, String str2, Intent intent2, CharSequence charSequence) {
        updateTransportAttributes(Binder.getCallingUid(), componentName, str, intent, str2, intent2, charSequence);
    }

    public void updateTransportAttributes(int i, ComponentName componentName, String str, Intent intent, String str2, Intent intent2, CharSequence charSequence) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.BACKUP", "updateTransportAttributes");
        Objects.requireNonNull(componentName, "transportComponent can't be null");
        Objects.requireNonNull(str, "name can't be null");
        Objects.requireNonNull(str2, "currentDestinationString can't be null");
        Preconditions.checkArgument((intent2 == null) == (charSequence == null), "dataManagementLabel should be null iff dataManagementIntent is null");
        try {
            if (i != this.mContext.getPackageManager().getPackageUidAsUser(componentName.getPackageName(), 0, this.mUserId)) {
                throw new SecurityException("Only the transport can change its description");
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mTransportManager.updateTransportAttributes(componentName, str, intent, str2, intent2, charSequence);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        } catch (PackageManager.NameNotFoundException e) {
            throw new SecurityException("Transport package not found", e);
        }
    }

    public String selectBackupTransport(String str) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.BACKUP", "selectBackupTransport");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (!this.mTransportManager.isTransportRegistered(str)) {
                Slog.v("BackupManagerService", addUserIdToLogMessage(this.mUserId, "Could not select transport " + str + ", as the transport is not registered."));
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return null;
            }
            String selectTransport = this.mTransportManager.selectTransport(str);
            updateStateForTransport(str);
            Slog.v("BackupManagerService", addUserIdToLogMessage(this.mUserId, "selectBackupTransport(transport = " + str + "): previous transport = " + selectTransport));
            return selectTransport;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void selectBackupTransportAsync(final ComponentName componentName, final ISelectBackupTransportCallback iSelectBackupTransportCallback) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.BACKUP", "selectBackupTransportAsync");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            String flattenToShortString = componentName.flattenToShortString();
            Slog.v("BackupManagerService", addUserIdToLogMessage(this.mUserId, "selectBackupTransportAsync(transport = " + flattenToShortString + ")"));
            this.mBackupHandler.post(new Runnable() { // from class: com.android.server.backup.UserBackupManagerService$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    UserBackupManagerService.this.lambda$selectBackupTransportAsync$9(componentName, iSelectBackupTransportCallback);
                }
            });
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$selectBackupTransportAsync$9(ComponentName componentName, ISelectBackupTransportCallback iSelectBackupTransportCallback) {
        int registerAndSelectTransport = this.mTransportManager.registerAndSelectTransport(componentName);
        String str = null;
        if (registerAndSelectTransport == 0) {
            try {
                str = this.mTransportManager.getTransportName(componentName);
                updateStateForTransport(str);
            } catch (TransportNotRegisteredException unused) {
                Slog.e("BackupManagerService", addUserIdToLogMessage(this.mUserId, "Transport got unregistered"));
                registerAndSelectTransport = -1;
            }
        }
        try {
            if (str != null) {
                iSelectBackupTransportCallback.onSuccess(str);
            } else {
                iSelectBackupTransportCallback.onFailure(registerAndSelectTransport);
            }
        } catch (RemoteException unused2) {
            Slog.e("BackupManagerService", addUserIdToLogMessage(this.mUserId, "ISelectBackupTransportCallback listener not available"));
        }
    }

    public List filterUserFacingPackages(List list) {
        if (!shouldSkipUserFacingData()) {
            return list;
        }
        ArrayList arrayList = new ArrayList(list.size());
        Iterator it = list.iterator();
        while (it.hasNext()) {
            PackageInfo packageInfo = (PackageInfo) it.next();
            if (!shouldSkipPackage(packageInfo.packageName)) {
                arrayList.add(packageInfo);
            } else {
                Slog.i("BackupManagerService", "Will skip backup/restore for " + packageInfo.packageName);
            }
        }
        return arrayList;
    }

    public boolean shouldSkipUserFacingData() {
        return Settings.Secure.getInt(this.mContext.getContentResolver(), "backup_skip_user_facing_packages", 0) != 0;
    }

    public boolean shouldSkipPackage(String str) {
        return "com.android.wallpaperbackup".equals(str);
    }

    public final void updateStateForTransport(String str) {
        Settings.Secure.putStringForUser(this.mContext.getContentResolver(), "backup_transport", str, this.mUserId);
        TransportConnection transportClient = this.mTransportManager.getTransportClient(str, "BMS.updateStateForTransport()");
        if (transportClient != null) {
            try {
                this.mCurrentToken = transportClient.connectOrThrow("BMS.updateStateForTransport()").getCurrentRestoreSet();
            } catch (Exception unused) {
                this.mCurrentToken = 0L;
                Slog.w("BackupManagerService", addUserIdToLogMessage(this.mUserId, "Transport " + str + " not available: current token = 0"));
            }
            this.mTransportManager.disposeOfTransportClient(transportClient, "BMS.updateStateForTransport()");
            return;
        }
        Slog.w("BackupManagerService", addUserIdToLogMessage(this.mUserId, "Transport " + str + " not registered: current token = 0"));
        this.mCurrentToken = 0L;
    }

    public Intent getConfigurationIntent(String str) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.BACKUP", "getConfigurationIntent");
        try {
            return this.mTransportManager.getTransportConfigurationIntent(str);
        } catch (TransportNotRegisteredException e) {
            Slog.e("BackupManagerService", addUserIdToLogMessage(this.mUserId, "Unable to get configuration intent from transport: " + e.getMessage()));
            return null;
        }
    }

    public String getDestinationString(String str) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.BACKUP", "getDestinationString");
        try {
            return this.mTransportManager.getTransportCurrentDestinationString(str);
        } catch (TransportNotRegisteredException e) {
            Slog.e("BackupManagerService", addUserIdToLogMessage(this.mUserId, "Unable to get destination string from transport: " + e.getMessage()));
            return null;
        }
    }

    public Intent getDataManagementIntent(String str) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.BACKUP", "getDataManagementIntent");
        try {
            return this.mTransportManager.getTransportDataManagementIntent(str);
        } catch (TransportNotRegisteredException e) {
            Slog.e("BackupManagerService", addUserIdToLogMessage(this.mUserId, "Unable to get management intent from transport: " + e.getMessage()));
            return null;
        }
    }

    public CharSequence getDataManagementLabel(String str) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.BACKUP", "getDataManagementLabel");
        try {
            return this.mTransportManager.getTransportDataManagementLabel(str);
        } catch (TransportNotRegisteredException e) {
            Slog.e("BackupManagerService", addUserIdToLogMessage(this.mUserId, "Unable to get management label from transport: " + e.getMessage()));
            return null;
        }
    }

    public void agentConnected(String str, IBinder iBinder) {
        synchronized (this.mAgentConnectLock) {
            if (Binder.getCallingUid() == 1000) {
                Slog.d("BackupManagerService", addUserIdToLogMessage(this.mUserId, "agentConnected pkg=" + str + " agent=" + iBinder));
                this.mConnectedAgent = IBackupAgent.Stub.asInterface(iBinder);
                this.mConnecting = false;
            } else {
                Slog.w("BackupManagerService", addUserIdToLogMessage(this.mUserId, "Non-system process uid=" + Binder.getCallingUid() + " claiming agent connected"));
            }
            this.mAgentConnectLock.notifyAll();
        }
    }

    public void agentDisconnected(final String str) {
        synchronized (this.mAgentConnectLock) {
            if (Binder.getCallingUid() == 1000) {
                this.mConnectedAgent = null;
                this.mConnecting = false;
            } else {
                Slog.w("BackupManagerService", addUserIdToLogMessage(this.mUserId, "Non-system process uid=" + Binder.getCallingUid() + " claiming agent disconnected"));
            }
            Slog.w("BackupManagerService", "agentDisconnected: the backup agent for " + str + " died: cancel current operations");
            getThreadForAsyncOperation("agent-disconnected", new Runnable() { // from class: com.android.server.backup.UserBackupManagerService$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    UserBackupManagerService.this.lambda$agentDisconnected$10(str);
                }
            }).start();
            this.mAgentConnectLock.notifyAll();
        }
        AdbBackupParams adbBackupParams = this.mAdbBackupParams;
        if (adbBackupParams != null) {
            this.mBackupHandler.removeMessages(101, adbBackupParams);
            this.mBackupHandler.sendMessage(this.mBackupHandler.obtainMessage(101, this.mAdbBackupParams));
        } else {
            AdbRestoreParams adbRestoreParams = this.mAdbRestoreParams;
            if (adbRestoreParams != null) {
                this.mBackupHandler.removeMessages(101, adbRestoreParams);
                this.mBackupHandler.sendMessage(this.mBackupHandler.obtainMessage(101, this.mAdbRestoreParams));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$agentDisconnected$10(String str) {
        Iterator it = this.mOperationStorage.operationTokensForPackage(str).iterator();
        while (it.hasNext()) {
            handleCancel(((Integer) it.next()).intValue(), true);
        }
    }

    public Thread getThreadForAsyncOperation(String str, Runnable runnable) {
        return new Thread(runnable, str);
    }

    public void restoreAtInstall(String str, int i) {
        boolean z;
        if (Binder.getCallingUid() != 1000) {
            Slog.w("BackupManagerService", addUserIdToLogMessage(this.mUserId, "Non-system process uid=" + Binder.getCallingUid() + " attemping install-time restore"));
            return;
        }
        long availableRestoreToken = getAvailableRestoreToken(str);
        Slog.i("BackupManagerService", addUserIdToLogMessage(this.mUserId, "restoreAtInstall pkg=" + str + " token=" + Integer.toHexString(i) + " restoreSet=" + Long.toHexString(availableRestoreToken)));
        boolean z2 = availableRestoreToken == 0;
        final TransportConnection currentTransportClient = this.mTransportManager.getCurrentTransportClient("BMS.restoreAtInstall()");
        if (currentTransportClient == null) {
            Slog.w("BackupManagerService", addUserIdToLogMessage(this.mUserId, "No transport client"));
            z2 = true;
        }
        if (!this.mAutoRestore) {
            Slog.w("BackupManagerService", addUserIdToLogMessage(this.mUserId, "Non-restorable state: auto=" + this.mAutoRestore));
            z2 = true;
        }
        if (!z2) {
            try {
                this.mWakelock.acquire();
                OnTaskFinishedListener onTaskFinishedListener = new OnTaskFinishedListener() { // from class: com.android.server.backup.UserBackupManagerService$$ExternalSyntheticLambda11
                    @Override // com.android.server.backup.internal.OnTaskFinishedListener
                    public final void onFinished(String str2) {
                        UserBackupManagerService.this.lambda$restoreAtInstall$11(currentTransportClient, str2);
                    }
                };
                Message obtainMessage = this.mBackupHandler.obtainMessage(3);
                obtainMessage.obj = RestoreParams.createForRestoreAtInstall(currentTransportClient, null, null, availableRestoreToken, str, i, onTaskFinishedListener, getEligibilityRulesForRestoreAtInstall(availableRestoreToken));
                this.mBackupHandler.sendMessage(obtainMessage);
            } catch (Exception e) {
                Slog.e("BackupManagerService", addUserIdToLogMessage(this.mUserId, "Unable to contact transport: " + e.getMessage()));
                z = true;
            }
        }
        z = z2;
        if (z) {
            if (currentTransportClient != null) {
                this.mTransportManager.disposeOfTransportClient(currentTransportClient, "BMS.restoreAtInstall()");
            }
            Slog.v("BackupManagerService", addUserIdToLogMessage(this.mUserId, "Finishing install immediately"));
            try {
                this.mPackageManagerBinder.finishPackageInstall(i, false);
            } catch (RemoteException unused) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$restoreAtInstall$11(TransportConnection transportConnection, String str) {
        this.mTransportManager.disposeOfTransportClient(transportConnection, str);
        this.mWakelock.release();
    }

    /* JADX WARN: Removed duplicated region for block: B:52:0x011c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.app.backup.IRestoreSession beginRestoreSession(java.lang.String r6, java.lang.String r7) {
        /*
            Method dump skipped, instructions count: 292
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.backup.UserBackupManagerService.beginRestoreSession(java.lang.String, java.lang.String):android.app.backup.IRestoreSession");
    }

    public void clearRestoreSession(ActiveRestoreSession activeRestoreSession) {
        synchronized (this) {
            if (activeRestoreSession != this.mActiveRestoreSession) {
                Slog.e("BackupManagerService", addUserIdToLogMessage(this.mUserId, "ending non-current restore session"));
            } else {
                Slog.v("BackupManagerService", addUserIdToLogMessage(this.mUserId, "Clearing restore session and halting timeout"));
                this.mActiveRestoreSession = null;
                this.mBackupHandler.removeMessages(8);
            }
        }
    }

    public void opComplete(int i, final long j) {
        this.mOperationStorage.onOperationComplete(i, j, new Consumer() { // from class: com.android.server.backup.UserBackupManagerService$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                UserBackupManagerService.this.lambda$opComplete$12(j, (BackupRestoreTask) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$opComplete$12(long j, BackupRestoreTask backupRestoreTask) {
        this.mBackupHandler.sendMessage(this.mBackupHandler.obtainMessage(21, Pair.create(backupRestoreTask, Long.valueOf(j))));
    }

    public boolean isAppEligibleForBackup(String str) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.BACKUP", "isAppEligibleForBackup");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            TransportConnection currentTransportClient = this.mTransportManager.getCurrentTransportClient("BMS.isAppEligibleForBackup");
            boolean appIsRunningAndEligibleForBackupWithTransport = this.mScheduledBackupEligibility.appIsRunningAndEligibleForBackupWithTransport(currentTransportClient, str);
            if (currentTransportClient != null) {
                this.mTransportManager.disposeOfTransportClient(currentTransportClient, "BMS.isAppEligibleForBackup");
            }
            return appIsRunningAndEligibleForBackupWithTransport;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public String[] filterAppsEligibleForBackup(String[] strArr) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.BACKUP", "filterAppsEligibleForBackup");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            TransportConnection currentTransportClient = this.mTransportManager.getCurrentTransportClient("BMS.filterAppsEligibleForBackup");
            ArrayList arrayList = new ArrayList();
            for (String str : strArr) {
                if (this.mScheduledBackupEligibility.appIsRunningAndEligibleForBackupWithTransport(currentTransportClient, str)) {
                    arrayList.add(str);
                }
            }
            if (currentTransportClient != null) {
                this.mTransportManager.disposeOfTransportClient(currentTransportClient, "BMS.filterAppsEligibleForBackup");
            }
            return (String[]) arrayList.toArray(new String[0]);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public BackupEligibilityRules getEligibilityRulesForOperation(int i) {
        return getEligibilityRules(this.mPackageManager, this.mUserId, this.mContext, i);
    }

    public static BackupEligibilityRules getEligibilityRules(PackageManager packageManager, int i, Context context, int i2) {
        return new BackupEligibilityRules(packageManager, (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class), i, context, i2);
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        if (strArr != null) {
            try {
                for (String str : strArr) {
                    if ("agents".startsWith(str)) {
                        dumpAgents(printWriter);
                        return;
                    } else if ("transportclients".equals(str.toLowerCase())) {
                        this.mTransportManager.dumpTransportClients(printWriter);
                        return;
                    } else {
                        if ("transportstats".equals(str.toLowerCase())) {
                            this.mTransportManager.dumpTransportStats(printWriter);
                            return;
                        }
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        dumpInternal(printWriter);
    }

    public final void dumpAgents(PrintWriter printWriter) {
        List<PackageInfo> allAgentPackages = allAgentPackages();
        printWriter.println("Defined backup agents:");
        for (PackageInfo packageInfo : allAgentPackages) {
            printWriter.print("  ");
            printWriter.print(packageInfo.packageName);
            printWriter.println(':');
            printWriter.print("      ");
            printWriter.println(packageInfo.applicationInfo.backupAgentName);
        }
    }

    @NeverCompile
    public final void dumpInternal(PrintWriter printWriter) {
        String str = this.mUserId == 0 ? "" : "User " + this.mUserId + XmlUtils.STRING_ARRAY_SEPARATOR;
        synchronized (this.mQueueLock) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append("Backup Manager is ");
            sb.append(this.mEnabled ? "enabled" : "disabled");
            sb.append(" / ");
            sb.append(!this.mSetupComplete ? "not " : "");
            sb.append("setup complete / ");
            sb.append(this.mPendingInits.size() == 0 ? "not " : "");
            sb.append("pending init");
            printWriter.println(sb.toString());
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Auto-restore is ");
            sb2.append(this.mAutoRestore ? "enabled" : "disabled");
            printWriter.println(sb2.toString());
            if (this.mBackupRunning) {
                printWriter.println("Backup currently running");
            }
            printWriter.println(isBackupOperationInProgress() ? "Backup in progress" : "No backups running");
            StringBuilder sb3 = new StringBuilder();
            sb3.append("Framework scheduling is ");
            sb3.append(isFrameworkSchedulingEnabled() ? "enabled" : "disabled");
            printWriter.println(sb3.toString());
            printWriter.println("Last backup pass started: " + this.mLastBackupPass + " (now = " + System.currentTimeMillis() + ')');
            StringBuilder sb4 = new StringBuilder();
            sb4.append("  next scheduled: ");
            sb4.append(KeyValueBackupJob.nextScheduled(this.mUserId));
            printWriter.println(sb4.toString());
            printWriter.println(str + "Transport whitelist:");
            for (ComponentName componentName : this.mTransportManager.getTransportWhitelist()) {
                printWriter.print("    ");
                printWriter.println(componentName.flattenToShortString());
            }
            printWriter.println(str + "Available transports:");
            String[] listAllTransports = listAllTransports();
            if (listAllTransports != null) {
                for (String str2 : listAllTransports) {
                    StringBuilder sb5 = new StringBuilder();
                    sb5.append(str2.equals(this.mTransportManager.getCurrentTransportName()) ? "  * " : "    ");
                    sb5.append(str2);
                    printWriter.println(sb5.toString());
                    try {
                        File file = new File(this.mBaseStateDir, this.mTransportManager.getTransportDirName(str2));
                        printWriter.println("       destination: " + this.mTransportManager.getTransportCurrentDestinationString(str2));
                        printWriter.println("       intent: " + this.mTransportManager.getTransportConfigurationIntent(str2));
                        File[] listFiles = file.listFiles();
                        int length = listFiles.length;
                        for (int i = 0; i < length; i++) {
                            File file2 = listFiles[i];
                            printWriter.println("       " + file2.getName() + " - " + file2.length() + " state bytes");
                        }
                    } catch (Exception e) {
                        Slog.e("BackupManagerService", addUserIdToLogMessage(this.mUserId, "Error in transport"), e);
                        printWriter.println("        Error: " + e);
                    }
                }
            }
            this.mTransportManager.dumpTransportClients(printWriter);
            printWriter.println(str + "Pending init: " + this.mPendingInits.size());
            Iterator it = this.mPendingInits.iterator();
            while (it.hasNext()) {
                printWriter.println("    " + ((String) it.next()));
            }
            printWriter.print(str + "Ancestral: ");
            printWriter.println(Long.toHexString(this.mAncestralToken));
            printWriter.print(str + "Current:   ");
            printWriter.println(Long.toHexString(this.mCurrentToken));
            int size = this.mBackupParticipants.size();
            printWriter.println(str + "Participants:");
            for (int i2 = 0; i2 < size; i2++) {
                int keyAt = this.mBackupParticipants.keyAt(i2);
                printWriter.print("  uid: ");
                printWriter.println(keyAt);
                Iterator it2 = ((HashSet) this.mBackupParticipants.valueAt(i2)).iterator();
                while (it2.hasNext()) {
                    printWriter.println("    " + ((String) it2.next()));
                }
            }
            StringBuilder sb6 = new StringBuilder();
            sb6.append(str);
            sb6.append("Ancestral packages: ");
            Set set = this.mAncestralPackages;
            sb6.append(set == null ? "none" : Integer.valueOf(set.size()));
            printWriter.println(sb6.toString());
            Set set2 = this.mAncestralPackages;
            if (set2 != null) {
                Iterator it3 = set2.iterator();
                while (it3.hasNext()) {
                    printWriter.println("    " + ((String) it3.next()));
                }
            }
            Set packagesCopy = this.mProcessedPackagesJournal.getPackagesCopy();
            printWriter.println(str + "Ever backed up: " + packagesCopy.size());
            Iterator it4 = packagesCopy.iterator();
            while (it4.hasNext()) {
                printWriter.println("    " + ((String) it4.next()));
            }
            printWriter.println(str + "Pending key/value backup: " + this.mPendingBackups.size());
            Iterator it5 = this.mPendingBackups.values().iterator();
            while (it5.hasNext()) {
                printWriter.println("    " + ((BackupRequest) it5.next()));
            }
            printWriter.println(str + "Full backup queue:" + this.mFullBackupQueue.size());
            Iterator it6 = this.mFullBackupQueue.iterator();
            while (it6.hasNext()) {
                FullBackupEntry fullBackupEntry = (FullBackupEntry) it6.next();
                printWriter.print("    ");
                printWriter.print(fullBackupEntry.lastBackup);
                printWriter.print(" : ");
                printWriter.println(fullBackupEntry.packageName);
            }
            printWriter.println(str + "Agent timeouts:");
            printWriter.println("    KvBackupAgentTimeoutMillis: " + this.mAgentTimeoutParameters.getKvBackupAgentTimeoutMillis());
            printWriter.println("    FullBackupAgentTimeoutMillis: " + this.mAgentTimeoutParameters.getFullBackupAgentTimeoutMillis());
            printWriter.println("    SharedBackupAgentTimeoutMillis: " + this.mAgentTimeoutParameters.getSharedBackupAgentTimeoutMillis());
            printWriter.println("    RestoreAgentTimeoutMillis (system): " + this.mAgentTimeoutParameters.getRestoreAgentTimeoutMillis(9999));
            printWriter.println("    RestoreAgentTimeoutMillis: " + this.mAgentTimeoutParameters.getRestoreAgentTimeoutMillis(10000));
            printWriter.println("    RestoreAgentFinishedTimeoutMillis: " + this.mAgentTimeoutParameters.getRestoreAgentFinishedTimeoutMillis());
            printWriter.println("    QuotaExceededTimeoutMillis: " + this.mAgentTimeoutParameters.getQuotaExceededTimeoutMillis());
        }
    }

    public int getBackupDestinationFromTransport(TransportConnection transportConnection) {
        if (!shouldUseNewBackupEligibilityRules()) {
            return 0;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if ((transportConnection.connectOrThrow("BMS.getBackupDestinationFromTransport").getTransportFlags() & 2) == 0) {
                return 0;
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return 1;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean shouldUseNewBackupEligibilityRules() {
        return FeatureFlagUtils.isEnabled(this.mContext, "settings_use_new_backup_eligibility_rules");
    }

    public static String addUserIdToLogMessage(int i, String str) {
        return "[UserID:" + i + "] " + str;
    }

    public IBackupManager getBackupManagerBinder() {
        return this.mBackupManagerBinder;
    }
}
