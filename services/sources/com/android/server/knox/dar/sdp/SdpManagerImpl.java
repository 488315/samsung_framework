package com.android.server.knox.dar.sdp;

import android.annotation.SystemApi;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.UserInfo;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.ContainerStateReceiver;
import android.os.FileUtils;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.os.storage.StorageManager;
import android.text.TextUtils;
import android.util.AtomicFile;
import android.util.Log;
import android.util.SparseArray;
import com.android.internal.widget.ICheckCredentialProgressCallback;
import com.android.internal.widget.ILockSettings;
import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.LockSettingsInternal;
import com.android.internal.widget.LockscreenCredential;
import com.android.internal.widget.VerifyCredentialResponse;
import com.android.server.LocalServices;
import com.android.server.ServiceKeeper;
import com.android.server.knox.dar.DarManagerService;
import com.android.server.knox.dar.DarUtil;
import com.android.server.knox.dar.EnterprisePartitionManager;
import com.android.server.knox.dar.FileUtil;
import com.android.server.knox.dar.KeyProtector;
import com.android.server.knox.dar.SecureUtil;
import com.android.server.knox.dar.sdp.SdpManagerImpl;
import com.android.server.knox.dar.sdp.engine.SdpServiceKeeper;
import com.android.server.knox.dar.sdp.security.BytesUtil;
import com.android.server.pm.PackageManagerService;
import com.android.server.pm.UserManagerInternal;
import com.android.server.wm.ActivityTaskManagerService;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.custom.LauncherConfigurationInternal;
import com.samsung.android.knox.dar.sdp.ISdpListener;
import com.samsung.android.knox.sdp.SdpFileSystem;
import com.samsung.android.knox.sdp.SdpUtil;
import com.samsung.android.knox.sdp.core.SdpCreationParam;
import com.samsung.android.knox.sdp.core.SdpDomain;
import com.samsung.android.knox.sdp.core.SdpEngineInfo;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

/* loaded from: classes2.dex */
public class SdpManagerImpl {
    public static final UserInfo NULL_USER = new UserInfo(-10000, (String) null, (String) null, 0);
    public static boolean mSystemReady = false;
    public Context mContext;
    public DevicePolicyManager mDevicePolicyManager;
    public KeyProtector mKeyProtector;
    public LockPatternUtils mLockPatternUtils;
    public LockSettingsInternal mLockSettingsInternal;
    public ILockSettings mLockSettingsService;
    public PackageManagerService.IPackageManagerImpl mPackageManagerImpl;
    public final SdpDatabaseCache mSdpDatabaseCache;
    public SdpEngineDatabase mSdpEngineDb;
    public SdpManagerInternal mSdpManagerInternal;
    public SdpManagerProxy mSdpManagerProxy;
    public SecureFileSystemManager mSecureFileSystemManager;
    public SdpServiceKeeper mServiceKeeper;
    public final UserManager mUserManager;
    public UserManagerInternal mUserManagerInternal;
    public final Object mSdpEngineDbLock = new Object();
    public final SparseArray mSdpEngineMap = new SparseArray();
    public SdpHandler mSdpHandler = null;
    public int mNeedToSetSdpPolicyForUser = -10000;
    public HandlerThread handlerThread = null;
    public final Map mManagedCredentialMap = new HashMap();
    public final Map mManagedTokenMap = new HashMap();
    public BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() { // from class: com.android.server.knox.dar.sdp.SdpManagerImpl.1
        public AnonymousClass1() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d("SdpManagerImpl.receiver", "onReceive - " + action);
            if ("android.intent.action.USER_ADDED".equals(action)) {
                int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -10000);
                Log.d("SdpManagerImpl.receiver", String.format("On %s : UserId = %d", action, Integer.valueOf(intExtra)));
                SdpManagerImpl.this.quickMessage(5, intExtra);
            } else if ("android.intent.action.USER_REMOVED".equals(action)) {
                int intExtra2 = intent.getIntExtra("android.intent.extra.user_handle", -10000);
                Log.d("SdpManagerImpl.receiver", String.format("On %s : UserId = %d", action, Integer.valueOf(intExtra2)));
                SdpManagerImpl.this.quickMessage(6, intExtra2);
            } else if ("android.intent.action.MANAGED_PROFILE_UNAVAILABLE".equals(action)) {
                int intExtra3 = intent.getIntExtra("android.intent.extra.user_handle", -10000);
                Log.d("SdpManagerImpl.receiver", String.format("On %s : UserId = %d", action, Integer.valueOf(intExtra3)));
                SdpManagerImpl.this.onManagedProfileUnavailable(intExtra3);
            }
        }
    };
    public BroadcastReceiver mPackageEventReceiver = new BroadcastReceiver() { // from class: com.android.server.knox.dar.sdp.SdpManagerImpl.2
        public AnonymousClass2() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d("SdpManagerImpl.receiver", "onReceive - " + action);
            if ("android.intent.action.PACKAGE_REMOVED".equals(action)) {
                int intExtra = intent.getIntExtra("android.intent.extra.UID", -1);
                int userId = intExtra >= 0 ? UserHandle.getUserId(intExtra) : -1;
                boolean z = false;
                if (intent.getBooleanExtra("android.intent.extra.REPLACING", false) && !intent.getBooleanExtra("android.intent.extra.DATA_REMOVED", false)) {
                    z = true;
                }
                Log.d("SdpManagerImpl.receiver", String.format("On %s : DATA = %s, UID = %d, UserId = %d, Is replacing? %s", action, intent.getData(), Integer.valueOf(intExtra), Integer.valueOf(userId), Boolean.valueOf(z)));
                if (z) {
                    Log.d("SdpManagerImpl.receiver", String.format("On %s : Skipped!", action));
                    return;
                }
                Uri data = intent.getData();
                if (data != null) {
                    String schemeSpecificPart = data.getSchemeSpecificPart();
                    Log.d("SdpManagerImpl.receiver", "ACTION_PACKAGE_REMOVED packageName:: " + schemeSpecificPart);
                    Bundle bundle = new Bundle();
                    bundle.putInt("userId", userId);
                    bundle.putString("pkgName", schemeSpecificPart);
                    SdpManagerImpl.this.quickMessage(4, bundle);
                }
            }
        }
    };
    public ContainerStateReceiver mContainerStateReceiver = new ContainerStateReceiver() { // from class: com.android.server.knox.dar.sdp.SdpManagerImpl.3
        public AnonymousClass3() {
        }

        public void onContainerCreated(Context context, int i, Bundle bundle) {
            Log.d("SdpManagerImpl", "onContainerCreated :: user: " + i);
            SDPLog.i(String.format("Workspace for user %d has been created", Integer.valueOf(i)));
            if (SemPersonaManager.isDarDualEncryptionEnabled(i)) {
                return;
            }
            synchronized (SdpManagerImpl.this.mSdpEngineDbLock) {
                SdpEngineInfo engineInfoLocked = SdpManagerImpl.this.getEngineInfoLocked(i);
                if (engineInfoLocked == null) {
                    SDPLog.e(new Exception("Unexpected condition while find engine info with id " + i));
                    return;
                }
                if (DarUtil.isLegacyContainerUser(SdpManagerImpl.this.getUserInfo(i))) {
                    SDPLog.d(String.format("On created - User %d workspace identified as old-fashioned", Integer.valueOf(i)));
                    if (SecureUtil.isEmpty(bundle.getString("EXTRA_RESET_TOKEN", null))) {
                        synchronized (SdpManagerImpl.this.mSdpEngineDbLock) {
                            engineInfoLocked.setFlag(engineInfoLocked.getFlag() | 1);
                            SdpManagerImpl.this.mSdpEngineMap.put(engineInfoLocked.getId(), engineInfoLocked);
                            SdpManagerImpl.this.mSdpEngineDb.storeEngineInfoLocked(engineInfoLocked);
                        }
                    }
                }
            }
        }

        public void onContainerShutdown(Context context, int i, Bundle bundle) {
            Log.d("SdpManagerImpl", "onContainerShutdown :: user: " + i);
            if (!SemPersonaManager.isKnoxId(i) || SemPersonaManager.isDarDualEncryptionEnabled(i)) {
                return;
            }
            SDPLog.d("Container has been shut down for user " + i);
            SdpManagerImpl.this.lockSdpIfRequired(i);
        }

        public void onContainerLocked(Context context, int i, Bundle bundle) {
            Log.d("SdpManagerImpl", "onContainerLocked :: user: " + i);
            if (!SemPersonaManager.isKnoxId(i) || SemPersonaManager.isDarDualEncryptionEnabled(i)) {
                return;
            }
            Log.d("SdpManagerImpl", "Immediately lock sdp for user " + i);
            SdpManagerImpl.this.lockSdpIfRequired(i);
        }

        public void onContainerUnlocked(Context context, int i, Bundle bundle) {
            Log.d("SdpManagerImpl", "onContainerUnlocked :: user: " + i);
        }
    };
    public final Map mListenerMap = new HashMap();
    public final Map mBinderListeners = new HashMap();
    public boolean mIsHandlerReady = false;
    public VirtualLockClient mVirtualLock = new VirtualLockClient(this);

    public static native int nativeOnBoot(int i, int i2);

    public static native int nativeOnChangePassword(int i, byte[] bArr, byte[] bArr2);

    public static native int nativeOnDeviceLocked(int i, int i2);

    public static native int nativeOnDeviceUnlocked(int i, byte[] bArr);

    public static native int nativeOnMigration(int i, int i2, int i3, int i4, byte[] bArr);

    public static native int nativeOnUserAdded(int i, int i2, byte[] bArr);

    public static native int nativeOnUserRemoved(int i, int i2);

    public SdpManagerImpl(DarManagerService.Injector injector) {
        this.mSdpEngineDb = null;
        this.mContext = injector.getContext();
        this.mUserManager = injector.getUserManager();
        this.mDevicePolicyManager = injector.getDevicePolicyManager();
        this.mSdpEngineDb = new SdpEngineDatabase();
        this.mSecureFileSystemManager = new SecureFileSystemManager(this.mContext);
        this.mSdpDatabaseCache = new SdpDatabaseCache(this.mContext);
        this.mLockPatternUtils = injector.getLockPatternUtils();
        this.mServiceKeeper = new SdpServiceKeeper(injector);
        this.mKeyProtector = injector.getKeyProtector();
        LocalServices.addService(SdpManagerInternal.class, new SdpLocalService());
        readEngineList();
        sdpServiceReady();
    }

    public boolean isDefaultPathUser(int i) {
        return !SemPersonaManager.isDoEnabled(i);
    }

    public final boolean isSupportedDevice() {
        Log.e("SdpManagerImpl", "SDP not supported");
        return false;
    }

    public int isLicensed() {
        return (DarUtil.isEnterpriseUser(getUserInfo(UserHandle.getUserId(Binder.getCallingUid()))) || this.mServiceKeeper.isLicensed(this.mContext, Binder.getCallingPid(), Binder.getCallingUid())) ? 0 : -9;
    }

    public double getSupportedSDKVersion() {
        return isSupportedDevice() ? 1.3d : 0.0d;
    }

    public final boolean isSystemComponent(SdpEngineInfo sdpEngineInfo) {
        return this.mServiceKeeper.isSystemComponent(this.mContext, Binder.getCallingPid(), Binder.getCallingUid(), sdpEngineInfo);
    }

    public final boolean isEngineOwner(SdpEngineInfo sdpEngineInfo) {
        return this.mServiceKeeper.isEngineOwner(this.mContext, Binder.getCallingPid(), Binder.getCallingUid(), sdpEngineInfo);
    }

    public final boolean isPrivileged(SdpEngineInfo sdpEngineInfo) {
        return this.mServiceKeeper.isPrivileged(this.mContext, Binder.getCallingPid(), Binder.getCallingUid(), sdpEngineInfo);
    }

    public final void readEngineList() {
        if (isSupportedDevice()) {
            synchronized (this.mSdpEngineDbLock) {
                SparseArray engineListLocked = this.mSdpEngineDb.getEngineListLocked();
                if (engineListLocked != null) {
                    for (int i = 0; i < engineListLocked.size(); i++) {
                        int keyAt = engineListLocked.keyAt(i);
                        String str = (String) engineListLocked.get(keyAt);
                        Log.e("SdpManagerImpl", String.format("read engine - [%s, %d] found in engine list", str, Integer.valueOf(keyAt)));
                        SdpEngineInfo engineInfoLocked = this.mSdpEngineDb.getEngineInfoLocked(keyAt);
                        if (engineInfoLocked != null) {
                            Log.d("SdpManagerImpl", "read engine - Put " + engineInfoLocked.toString());
                            this.mSdpEngineMap.put(keyAt, engineInfoLocked);
                            this.mServiceKeeper.loadPolicy(engineInfoLocked);
                        } else {
                            Log.e("SdpManagerImpl", String.format("read engine - Can't find engine info with [%s, %d]", str, Integer.valueOf(keyAt)));
                        }
                    }
                } else {
                    Log.e("SdpManagerImpl", "read engine - No any engine found");
                }
            }
        }
    }

    public final boolean isVirtualUserId(int i) {
        return UserManager.isVirtualUserId(i);
    }

    public final void sdpServiceReady() {
        Log.d("SdpManagerImpl", "SdpManagerImpl ready");
        checkCallerPermissionFor("systemReady");
        HandlerThread handlerThread = new HandlerThread("SdpManagerImpl", 10);
        this.handlerThread = handlerThread;
        handlerThread.start();
        this.mSdpHandler = new SdpHandler(this.handlerThread.getLooper());
        this.mIsHandlerReady = true;
        Log.d("SdpManagerImpl", "Sending message MSG_SYSTEM_READY to handler");
        quickMessage(1);
        Log.d("SdpManagerImpl", "systemReady done.");
    }

    public final void onSystemReady() {
        runSdpCryptoDaemon();
        registerReceiver();
        updateDeviceOwnerStatus();
        this.mSecureFileSystemManager.secureFileSystemManagerReady();
        int size = this.mSdpEngineMap.size();
        for (int i = 0; i < size; i++) {
            Log.d("SdpManagerImpl", "SdpEngine boot = " + boot(((SdpEngineInfo) this.mSdpEngineMap.valueAt(i)).getId()));
        }
        try {
            removeInvalidEngines();
        } catch (Exception e) {
            SDPLog.e("Failed to remove Invalid Engines!", e);
            e.printStackTrace();
        }
        mSystemReady = true;
    }

    public final void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.USER_ADDED");
        intentFilter.addAction("android.intent.action.USER_REMOVED");
        intentFilter.addAction("android.intent.action.MANAGED_PROFILE_UNAVAILABLE");
        this.mContext.registerReceiver(this.mBroadcastReceiver, intentFilter);
        Log.d("SdpManagerImpl.receiver", "Broadcast receiver has been registered");
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter2.addDataScheme("package");
        this.mContext.registerReceiver(this.mPackageEventReceiver, intentFilter2);
        Log.d("SdpManagerImpl.receiver", "Package event receiver has been registered");
        ContainerStateReceiver.register(this.mContext, this.mContainerStateReceiver);
        Log.d("SdpManagerImpl.receiver", "Container state receiver has been registered");
    }

    /* renamed from: com.android.server.knox.dar.sdp.SdpManagerImpl$1 */
    /* loaded from: classes2.dex */
    public class AnonymousClass1 extends BroadcastReceiver {
        public AnonymousClass1() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d("SdpManagerImpl.receiver", "onReceive - " + action);
            if ("android.intent.action.USER_ADDED".equals(action)) {
                int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -10000);
                Log.d("SdpManagerImpl.receiver", String.format("On %s : UserId = %d", action, Integer.valueOf(intExtra)));
                SdpManagerImpl.this.quickMessage(5, intExtra);
            } else if ("android.intent.action.USER_REMOVED".equals(action)) {
                int intExtra2 = intent.getIntExtra("android.intent.extra.user_handle", -10000);
                Log.d("SdpManagerImpl.receiver", String.format("On %s : UserId = %d", action, Integer.valueOf(intExtra2)));
                SdpManagerImpl.this.quickMessage(6, intExtra2);
            } else if ("android.intent.action.MANAGED_PROFILE_UNAVAILABLE".equals(action)) {
                int intExtra3 = intent.getIntExtra("android.intent.extra.user_handle", -10000);
                Log.d("SdpManagerImpl.receiver", String.format("On %s : UserId = %d", action, Integer.valueOf(intExtra3)));
                SdpManagerImpl.this.onManagedProfileUnavailable(intExtra3);
            }
        }
    }

    public final void runSdpCryptoDaemon() {
        if (DarUtil.isDaemonRunning("persist.sys.knox.sdp_cryptod")) {
            Log.d("SdpManagerImpl", "SDP daemon is already running!");
        } else if (SdpFileSystem.testSdpIoctl()) {
            SDPLog.d("SdpManagerImpl", "Start SDP daemon!");
            DarUtil.setSystemPropertyBoolean("persist.sys.knox.sdp_cryptod", true);
        } else {
            SDPLog.d("SdpManagerImpl", "Failed in fs ping test...");
        }
    }

    public final void onManagedProfileUnavailable(int i) {
        Log.d("SdpManagerImpl", "Managed profile user " + i + " got unavailable");
        ActivityTaskManagerService activityTaskManagerService = (ActivityTaskManagerService) ServiceManager.getService("activity_task");
        if (activityTaskManagerService == null || !SemPersonaManager.isKnoxId(i)) {
            return;
        }
        activityTaskManagerService.mExt.removeTaskByCmpName("com.android.settings/.password.ChooseLockGeneric$InternalActivity", 0, "Managed profile unavailable");
    }

    /* renamed from: com.android.server.knox.dar.sdp.SdpManagerImpl$2 */
    /* loaded from: classes2.dex */
    public class AnonymousClass2 extends BroadcastReceiver {
        public AnonymousClass2() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d("SdpManagerImpl.receiver", "onReceive - " + action);
            if ("android.intent.action.PACKAGE_REMOVED".equals(action)) {
                int intExtra = intent.getIntExtra("android.intent.extra.UID", -1);
                int userId = intExtra >= 0 ? UserHandle.getUserId(intExtra) : -1;
                boolean z = false;
                if (intent.getBooleanExtra("android.intent.extra.REPLACING", false) && !intent.getBooleanExtra("android.intent.extra.DATA_REMOVED", false)) {
                    z = true;
                }
                Log.d("SdpManagerImpl.receiver", String.format("On %s : DATA = %s, UID = %d, UserId = %d, Is replacing? %s", action, intent.getData(), Integer.valueOf(intExtra), Integer.valueOf(userId), Boolean.valueOf(z)));
                if (z) {
                    Log.d("SdpManagerImpl.receiver", String.format("On %s : Skipped!", action));
                    return;
                }
                Uri data = intent.getData();
                if (data != null) {
                    String schemeSpecificPart = data.getSchemeSpecificPart();
                    Log.d("SdpManagerImpl.receiver", "ACTION_PACKAGE_REMOVED packageName:: " + schemeSpecificPart);
                    Bundle bundle = new Bundle();
                    bundle.putInt("userId", userId);
                    bundle.putString("pkgName", schemeSpecificPart);
                    SdpManagerImpl.this.quickMessage(4, bundle);
                }
            }
        }
    }

    /* renamed from: com.android.server.knox.dar.sdp.SdpManagerImpl$3 */
    /* loaded from: classes2.dex */
    public class AnonymousClass3 extends ContainerStateReceiver {
        public AnonymousClass3() {
        }

        public void onContainerCreated(Context context, int i, Bundle bundle) {
            Log.d("SdpManagerImpl", "onContainerCreated :: user: " + i);
            SDPLog.i(String.format("Workspace for user %d has been created", Integer.valueOf(i)));
            if (SemPersonaManager.isDarDualEncryptionEnabled(i)) {
                return;
            }
            synchronized (SdpManagerImpl.this.mSdpEngineDbLock) {
                SdpEngineInfo engineInfoLocked = SdpManagerImpl.this.getEngineInfoLocked(i);
                if (engineInfoLocked == null) {
                    SDPLog.e(new Exception("Unexpected condition while find engine info with id " + i));
                    return;
                }
                if (DarUtil.isLegacyContainerUser(SdpManagerImpl.this.getUserInfo(i))) {
                    SDPLog.d(String.format("On created - User %d workspace identified as old-fashioned", Integer.valueOf(i)));
                    if (SecureUtil.isEmpty(bundle.getString("EXTRA_RESET_TOKEN", null))) {
                        synchronized (SdpManagerImpl.this.mSdpEngineDbLock) {
                            engineInfoLocked.setFlag(engineInfoLocked.getFlag() | 1);
                            SdpManagerImpl.this.mSdpEngineMap.put(engineInfoLocked.getId(), engineInfoLocked);
                            SdpManagerImpl.this.mSdpEngineDb.storeEngineInfoLocked(engineInfoLocked);
                        }
                    }
                }
            }
        }

        public void onContainerShutdown(Context context, int i, Bundle bundle) {
            Log.d("SdpManagerImpl", "onContainerShutdown :: user: " + i);
            if (!SemPersonaManager.isKnoxId(i) || SemPersonaManager.isDarDualEncryptionEnabled(i)) {
                return;
            }
            SDPLog.d("Container has been shut down for user " + i);
            SdpManagerImpl.this.lockSdpIfRequired(i);
        }

        public void onContainerLocked(Context context, int i, Bundle bundle) {
            Log.d("SdpManagerImpl", "onContainerLocked :: user: " + i);
            if (!SemPersonaManager.isKnoxId(i) || SemPersonaManager.isDarDualEncryptionEnabled(i)) {
                return;
            }
            Log.d("SdpManagerImpl", "Immediately lock sdp for user " + i);
            SdpManagerImpl.this.lockSdpIfRequired(i);
        }

        public void onContainerUnlocked(Context context, int i, Bundle bundle) {
            Log.d("SdpManagerImpl", "onContainerUnlocked :: user: " + i);
        }
    }

    public final VerifyCredentialResponse verifyToken(final byte[] bArr, final long j, final int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return (VerifyCredentialResponse) getLockSettings().map(new Function() { // from class: com.android.server.knox.dar.sdp.SdpManagerImpl$$ExternalSyntheticLambda4
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    VerifyCredentialResponse lambda$verifyToken$0;
                    lambda$verifyToken$0 = SdpManagerImpl.lambda$verifyToken$0(bArr, j, i, (ILockSettings) obj);
                    return lambda$verifyToken$0;
                }
            }).orElse(VerifyCredentialResponse.ERROR);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public static /* synthetic */ VerifyCredentialResponse lambda$verifyToken$0(byte[] bArr, long j, int i, ILockSettings iLockSettings) {
        try {
            return iLockSettings.verifyToken(bArr, j, i);
        } catch (RemoteException e) {
            e.printStackTrace();
            return VerifyCredentialResponse.ERROR;
        }
    }

    public final VerifyCredentialResponse checkCredential(final String str, final int i, final int i2) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return (VerifyCredentialResponse) getLockSettings().map(new Function() { // from class: com.android.server.knox.dar.sdp.SdpManagerImpl$$ExternalSyntheticLambda5
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    VerifyCredentialResponse lambda$checkCredential$2;
                    lambda$checkCredential$2 = SdpManagerImpl.lambda$checkCredential$2(i, str, i2, (ILockSettings) obj);
                    return lambda$checkCredential$2;
                }
            }).orElse(VerifyCredentialResponse.ERROR);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public static /* synthetic */ VerifyCredentialResponse lambda$checkCredential$2(int i, String str, int i2, ILockSettings iLockSettings) {
        LockscreenCredential createPassword;
        VerifyCredentialResponse verifyCredentialResponse = VerifyCredentialResponse.ERROR;
        try {
            if (i == 4) {
                createPassword = LockscreenCredential.createPassword(str);
            } else {
                Log.e("SdpManagerImpl", "Unknown credential type");
                createPassword = null;
            }
            return createPassword != null ? iLockSettings.checkCredential(createPassword, i2, (ICheckCredentialProgressCallback) null) : verifyCredentialResponse;
        } catch (RemoteException e) {
            e.printStackTrace();
            return verifyCredentialResponse;
        }
    }

    public final void updateDeviceOwnerStatus() {
        DarUtil.updateDeviceOwnerStatus(this.mLockPatternUtils.isDeviceOwner(0));
        Log.d("SdpManagerImpl", String.format("Device owner status updated! [ Enabled : %b ]", Boolean.valueOf(DarUtil.isDoEnabled())));
        if (this.mSdpDatabaseCache.getBoolean(0, "do_cleared", false)) {
            quickMessage(10);
        }
    }

    public final void checkCallerPermissionFor(String str) {
        if (ServiceKeeper.isAuthorized(this.mContext, Binder.getCallingPid(), Binder.getCallingUid(), "SdpManagerImpl", str) == 0) {
            return;
        }
        throw new SecurityException("Security Exception Occurred while pid[" + Binder.getCallingPid() + "] with uid[" + Binder.getCallingUid() + "] trying to access methodName [" + str + "] in [SdpManagerImpl] service");
    }

    public final void checkSystemPermission() {
        if (Binder.getCallingUid() == 1000) {
            return;
        }
        Log.e("SdpManagerImpl", "Require system permission.");
        throw new SecurityException("Security Exception Occurred in pid[" + Binder.getCallingPid() + "] with uid[" + Binder.getCallingUid() + "]");
    }

    public final void quickMessageDelayed(int i, Object obj, long j) {
        boolean z;
        if (this.mIsHandlerReady) {
            if (this.mSdpHandler.hasMessages(i, obj)) {
                this.mSdpHandler.removeMessages(i, obj);
            }
            Message obtainMessage = this.mSdpHandler.obtainMessage(i, obj);
            if (j <= 0) {
                z = this.mSdpHandler.sendMessage(obtainMessage);
            } else {
                z = this.mSdpHandler.sendMessageDelayed(obtainMessage, j);
            }
        } else {
            z = false;
        }
        if (z) {
            return;
        }
        Log.e("SdpManagerImpl", "Failed to send a message delayed : " + i);
    }

    public final void quickMessage(int i) {
        if (this.mIsHandlerReady) {
            this.mSdpHandler.obtainMessage(i).sendToTarget();
            return;
        }
        Log.e("SdpManagerImpl", "Failed to send a message : " + i);
    }

    public final void quickMessage(int i, int i2) {
        if (this.mIsHandlerReady) {
            this.mSdpHandler.obtainMessage(i, i2, 0).sendToTarget();
            return;
        }
        Log.e("SdpManagerImpl", "Failed to send a message : " + i);
    }

    public final void quickMessage(int i, Bundle bundle) {
        if (this.mIsHandlerReady && bundle != null) {
            Message obtainMessage = this.mSdpHandler.obtainMessage(i);
            obtainMessage.setData(bundle);
            obtainMessage.sendToTarget();
        } else {
            Log.e("SdpManagerImpl", "Failed to send a message : " + i);
        }
    }

    public final void sendBroadcastForStateChange(int i, int i2, int i3) {
        if (this.mIsHandlerReady) {
            Bundle bundle = new Bundle();
            bundle.putInt("userId", i);
            bundle.putInt("engineId", i2);
            bundle.putInt(LauncherConfigurationInternal.KEY_STATE_BOOLEAN, i3);
            quickMessage(15, bundle);
            SDPLog.d("change state for user " + i);
        }
    }

    public final void handleSendBroadcastForStateChange(int i, int i2, int i3) {
        Intent intent = new Intent("com.sec.sdp.SDP_STATE_CHANGED");
        Intent intent2 = new Intent("com.samsung.android.knox.intent.action.SDP_STATE_CHANGED");
        Log.e("SdpManagerImpl", "sendBroadcastAsUser(INTENT_SDP_STATE_CHANGED, state:" + i3 + ")");
        intent.putExtra("id", i2);
        intent.putExtra(LauncherConfigurationInternal.KEY_STATE_BOOLEAN, i3);
        intent.addFlags(32);
        intent.addFlags(67108864);
        intent.addFlags(268435456);
        this.mContext.sendBroadcastAsUser(intent, new UserHandle(i));
        Log.e("SdpManagerImpl", "sendBroadcastAsUser(ACTION_SDP_STATE_CHANGED, state:" + i3 + ")");
        intent2.putExtra("com.samsung.android.knox.intent.extra.SDP_ENGINE_ID", i2);
        intent2.putExtra("com.samsung.android.knox.intent.extra.SDP_ENGINE_STATE", i3);
        intent2.addFlags(32);
        intent2.addFlags(67108864);
        intent2.addFlags(268435456);
        this.mContext.sendBroadcastAsUser(intent2, new UserHandle(i));
    }

    public final void recordException(String str, Exception exc, String str2) {
        SDPLog.d(str, str2);
        recordException(str, exc);
    }

    public final void recordException(String str, Exception exc) {
        if (str == null || exc == null) {
            return;
        }
        SDPLog.d(str, "Leave a trace of the exception...!");
        SDPLog.d(str, exc.toString());
        try {
            for (StackTraceElement stackTraceElement : exc.getStackTrace()) {
                SDPLog.d(str, stackTraceElement.toString());
            }
        } catch (Exception unused) {
        }
    }

    public int createEncPkgDir(final int i, final String str) {
        if (isSupportedDevice()) {
            return SecureUtil.isFailed(getPackageManagerImpl().map(new Function() { // from class: com.android.server.knox.dar.sdp.SdpManagerImpl$$ExternalSyntheticLambda3
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    Boolean lambda$createEncPkgDir$3;
                    lambda$createEncPkgDir$3 = SdpManagerImpl.lambda$createEncPkgDir$3(str, i, (PackageManagerService.IPackageManagerImpl) obj);
                    return lambda$createEncPkgDir$3;
                }
            }).orElse(Boolean.FALSE)) ? -11 : 0;
        }
        return -10;
    }

    public static /* synthetic */ Boolean lambda$createEncPkgDir$3(String str, int i, PackageManagerService.IPackageManagerImpl iPackageManagerImpl) {
        return Boolean.valueOf(iPackageManagerImpl.createEncAppData(str, i));
    }

    public final Optional getStorageManager() {
        return Optional.ofNullable((StorageManager) this.mContext.getSystemService(StorageManager.class));
    }

    public final Optional getSdpManagerInternal() {
        if (this.mSdpManagerInternal == null) {
            this.mSdpManagerInternal = (SdpManagerInternal) LocalServices.getService(SdpManagerInternal.class);
        }
        return Optional.ofNullable(this.mSdpManagerInternal);
    }

    public final Optional getLockSettings() {
        if (this.mLockSettingsService == null) {
            this.mLockSettingsService = ILockSettings.Stub.asInterface(ServiceManager.getService("lock_settings"));
        }
        return Optional.ofNullable(this.mLockSettingsService);
    }

    public final Optional getLockSettingsInternal() {
        if (this.mLockSettingsInternal == null) {
            this.mLockSettingsInternal = (LockSettingsInternal) LocalServices.getService(LockSettingsInternal.class);
        }
        return Optional.ofNullable(this.mLockSettingsInternal);
    }

    public final Optional getPackageManagerImpl() {
        if (this.mPackageManagerImpl == null) {
            this.mPackageManagerImpl = (PackageManagerService.IPackageManagerImpl) ServiceManager.getService("package");
        }
        return Optional.ofNullable(this.mPackageManagerImpl);
    }

    public final Optional getUserManagerInternal() {
        if (this.mUserManagerInternal == null) {
            this.mUserManagerInternal = (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
        }
        return Optional.ofNullable(this.mUserManagerInternal);
    }

    public final UserInfo getUserInfo(int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        UserManager userManager = this.mUserManager;
        UserInfo userInfo = userManager != null ? userManager.getUserInfo(i) : null;
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return userInfo != null ? userInfo : NULL_USER;
    }

    public SdpEngineInfo getEngineInfo(String str) {
        SdpEngineInfo engineInfoLocked;
        if (!isSupportedDevice()) {
            return null;
        }
        synchronized (this.mSdpEngineDbLock) {
            engineInfoLocked = getEngineInfoLocked(str);
        }
        return engineInfoLocked;
    }

    public final SdpEngineInfo getEngineInfoLocked(int i) {
        SdpEngineInfo sdpEngineInfo = (SdpEngineInfo) this.mSdpEngineMap.get(i);
        if (sdpEngineInfo == null) {
            Log.e("SdpManagerImpl", "get - engine info not found in map  for " + i);
        }
        return sdpEngineInfo;
    }

    public final SdpEngineInfo getEngineInfoLocked(String str) {
        if (str == null || str.isEmpty()) {
            int userId = UserHandle.getUserId(Binder.getCallingUid());
            if (!SdpUtil.isAndroidDefaultUser(userId) || getUserInfo(userId).isBMode()) {
                return null;
            }
            str = SdpUtil.getAndroidDefaultAlias(userId);
        }
        if (str != null) {
            int size = this.mSdpEngineMap.size();
            for (int i = 0; i < size; i++) {
                SdpEngineInfo sdpEngineInfo = (SdpEngineInfo) this.mSdpEngineMap.valueAt(i);
                if (sdpEngineInfo.getAlias().equals(str)) {
                    return sdpEngineInfo;
                }
            }
        }
        return null;
    }

    public int exists(String str) {
        SdpEngineInfo engineInfoLocked;
        synchronized (this.mSdpEngineDbLock) {
            engineInfoLocked = getEngineInfoLocked(str);
        }
        return engineInfoLocked != null ? -4 : -5;
    }

    public int allow(String str, String str2) {
        SdpEngineInfo engineInfoLocked;
        synchronized (this.mSdpEngineDbLock) {
            engineInfoLocked = getEngineInfoLocked(str);
        }
        if (engineInfoLocked == null) {
            return -5;
        }
        return this.mServiceKeeper.addPrivilegedApp(this.mContext, Binder.getCallingPid(), Binder.getCallingUid(), engineInfoLocked, new SdpDomain(str, str2));
    }

    public int disallow(String str, String str2) {
        SdpEngineInfo engineInfoLocked;
        synchronized (this.mSdpEngineDbLock) {
            engineInfoLocked = getEngineInfoLocked(str);
        }
        if (engineInfoLocked == null) {
            return -5;
        }
        return this.mServiceKeeper.removePrivilegedApp(this.mContext, Binder.getCallingPid(), Binder.getCallingUid(), engineInfoLocked, new SdpDomain(str, str2));
    }

    public final int setEngineStateLocked(SdpEngineInfo sdpEngineInfo, int i) {
        if (sdpEngineInfo == null) {
            return -5;
        }
        sdpEngineInfo.setState(i);
        onStateChange(sdpEngineInfo, i);
        onStateChange(sdpEngineInfo.getId(), i);
        int userId = sdpEngineInfo.getUserId();
        int id = sdpEngineInfo.getId();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        sendBroadcastForStateChange(userId, id, i);
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return 0;
    }

    public final void onStateChange(SdpEngineInfo sdpEngineInfo, int i) {
        synchronized (this.mBinderListeners) {
            ArrayList arrayList = (ArrayList) this.mBinderListeners.get(sdpEngineInfo.getAlias());
            if (arrayList == null) {
                return;
            }
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                try {
                    ((SdpManagerImplBinderListener) arrayList.get(size)).mListener.onStateChange(i);
                } catch (RemoteException unused) {
                    Log.e("SdpManagerImpl", "Listener dead");
                    arrayList.remove(size);
                } catch (Exception e) {
                    Log.e("SdpManagerImpl", "Listener failed", e);
                }
            }
        }
    }

    public boolean setSensitive(int i, String str) {
        if (isSupportedDevice()) {
            return getProxy().setSensitive(i, str);
        }
        return false;
    }

    public boolean isSensitive(String str) {
        if (isSupportedDevice()) {
            return getProxy().isSensitive(str);
        }
        return false;
    }

    public final int boot(int i) {
        if (!isSupportedDevice()) {
            return -10;
        }
        SdpEngineInfo engineInfoLocked = getEngineInfoLocked(i);
        if (engineInfoLocked == null) {
            Log.e("SdpManagerImpl", "boot - Engine info not found in map with id " + i);
            synchronized (this.mSdpEngineDbLock) {
                engineInfoLocked = this.mSdpEngineDb.getEngineInfoLocked(i);
                if (engineInfoLocked != null) {
                    this.mSdpEngineMap.put(i, engineInfoLocked);
                    this.mSdpEngineDb.storeEngineInfoLocked(engineInfoLocked);
                    this.mSdpEngineDb.updateEngineListLocked();
                }
            }
        }
        if (engineInfoLocked == null) {
            Log.e("SdpManagerImpl", "boot - Failed to find engine info with id " + i);
            return -5;
        }
        return bootInternal(engineInfoLocked);
    }

    public final int bootInternal(SdpEngineInfo sdpEngineInfo) {
        int id = sdpEngineInfo.getId();
        Log.d("SdpManagerImpl", "boot - " + id);
        int bootNative = bootNative(sdpEngineInfo);
        Log.d("SdpManagerImpl", String.format("boot - [ Detected version : %d, Latest version : %d ]", Integer.valueOf(sdpEngineInfo.getVersion()), 6));
        if (sdpEngineInfo.getVersion() != 6) {
            if (onMigrationInternal(sdpEngineInfo) != 0) {
                SDPLog.d("SdpManagerImpl", "boot - Migration failed");
            }
            synchronized (this.mSdpEngineDbLock) {
                this.mSdpEngineMap.put(id, sdpEngineInfo);
            }
        }
        synchronized (this.mSdpEngineDbLock) {
            setEngineStateLocked(sdpEngineInfo, 1);
        }
        SDPLog.d(String.format("Boot - Prepare session key for engine %d [ res : %s ]", Integer.valueOf(id), Boolean.valueOf(generateAndSaveSessionKey(id))));
        SDPLog.d(String.format("Boot - Engine %d boot completed! [ rc : %d ]", Integer.valueOf(id), Integer.valueOf(bootNative)));
        loadInternalEngineInfo(id);
        return bootNative;
    }

    public final int bootNative(SdpEngineInfo sdpEngineInfo) {
        if (!SecureUtil.isFailed(Integer.valueOf(nativeOnBoot(sdpEngineInfo.getId(), sdpEngineInfo.getUserId())))) {
            return 0;
        }
        Log.e("SdpManagerImpl", "bootNative - Failed with id " + sdpEngineInfo.getId());
        return -11;
    }

    public int lock(String str) {
        if (!isSupportedDevice()) {
            return -10;
        }
        synchronized (this.mSdpEngineDbLock) {
            SdpEngineInfo engineInfoLocked = getEngineInfoLocked(str);
            if (engineInfoLocked == null) {
                Log.e("SdpManagerImpl", "lock :: Can't find engine info " + str);
                return -5;
            }
            if (engineInfoLocked.isAndroidDefaultEngine()) {
                Log.e("SdpManagerImpl", "lock :: Not supported anymore to " + str);
                return -99;
            }
            if (!isEngineOwner(engineInfoLocked) && !isPrivileged(engineInfoLocked) && !isSystemComponent(engineInfoLocked)) {
                Log.e("SdpManagerImpl", "lock :: Permission denied to invoke engine control API");
                return -7;
            }
            return lockInternal(engineInfoLocked);
        }
    }

    public final int lockInternal(SdpEngineInfo sdpEngineInfo) {
        int id = sdpEngineInfo.getId();
        Log.d("SdpManagerImpl", "lock :: Lock engine for user " + id);
        int lockFinal = lockFinal(sdpEngineInfo);
        if (lockFinal == 0) {
            Log.d("SdpManagerImpl", String.format("lock :: Successfully done for user %d", Integer.valueOf(id)));
            clearManagedCredential(id);
        } else {
            Log.d("SdpManagerImpl", String.format("lock :: Failed to lock for user %d... [ rc : %d ]", Integer.valueOf(id), Integer.valueOf(lockFinal)));
        }
        return lockFinal;
    }

    public int unlock(String str, String str2) {
        SdpEngineInfo engineInfoLocked;
        if (!isSupportedDevice()) {
            return -10;
        }
        synchronized (this.mSdpEngineDbLock) {
            engineInfoLocked = getEngineInfoLocked(str);
        }
        if (engineInfoLocked == null) {
            Log.e("SdpManagerImpl", "unlock :: Can't find engine info for " + str);
            return -5;
        }
        if (engineInfoLocked.isAndroidDefaultEngine()) {
            Log.e("SdpManagerImpl", "unlock :: Not supported anymore to " + str);
            return -99;
        }
        if (!isEngineOwner(engineInfoLocked) && !isPrivileged(engineInfoLocked) && !isSystemComponent(engineInfoLocked)) {
            Log.e("SdpManagerImpl", "unlock :: Permission denied to invoke engine control API");
            return -7;
        }
        return unlockInternal(engineInfoLocked, str2);
    }

    public final int unlockInternal(SdpEngineInfo sdpEngineInfo, String str) {
        int id = sdpEngineInfo.getId();
        VerifyCredentialResponse verifyCredentialResponse = VerifyCredentialResponse.ERROR;
        int i = -1;
        if (!SecureUtil.isEmpty(str)) {
            try {
                verifyCredentialResponse = this.mVirtualLock.checkPassword(str, id);
            } catch (Exception e) {
                Log.e("SdpManagerImpl", "unlock :: Failed to check password user " + id, e);
                return -1;
            }
        }
        Log.d("SdpManagerImpl", String.format("unlock :: Result of virtual user %d verification : %s", Integer.valueOf(id), verifyCredentialResponse.toString()));
        int responseCode = verifyCredentialResponse.getResponseCode();
        if (responseCode != -1) {
            i = -99;
            if (responseCode == 0) {
                byte[] secret = verifyCredentialResponse.getSecret();
                if (SecureUtil.isFailed(Integer.valueOf(unlockNative(id, secret)))) {
                    Log.e("SdpManagerImpl", "unlock :: Failed in native unlock with user " + id);
                } else {
                    i = 0;
                }
                SecureUtil.clear(secret);
            } else if (responseCode == 1) {
                i = verifyCredentialResponse.getTimeout();
                Log.e("SdpManagerImpl", String.format("unlock :: User %d throttled! Please try %d ms later...", Integer.valueOf(id), Integer.valueOf(i)));
            }
        }
        if (i == 0) {
            synchronized (this.mSdpEngineDbLock) {
                setEngineStateLocked(sdpEngineInfo, 2);
            }
            cacheManagedCredential(str.getBytes(Charset.forName("UTF-8")), id);
        }
        return i;
    }

    public final int unlockNative(int i, byte[] bArr) {
        if (bArr == null || bArr.length != 32) {
            return -3;
        }
        if (nativeOnDeviceUnlocked(i, bArr) == 0) {
            return 0;
        }
        Log.e("SdpManagerImpl", "unlockNative :: failed. " + i);
        return -11;
    }

    public int setPassword(String str, String str2) {
        if (!isSupportedDevice()) {
            return -10;
        }
        synchronized (this.mSdpEngineDbLock) {
            SdpEngineInfo engineInfoLocked = getEngineInfoLocked(str);
            if (engineInfoLocked == null) {
                Log.e("SdpManagerImpl", "set password :: Can't find engine info " + str);
                return -5;
            }
            if (engineInfoLocked.isAndroidDefaultEngine()) {
                Log.e("SdpManagerImpl", "set password :: Not supported anymore to " + str);
                return -99;
            }
            if (!isEngineOwner(engineInfoLocked) && !isPrivileged(engineInfoLocked) && !isSystemComponent(engineInfoLocked)) {
                Log.e("SdpManagerImpl", "set password :: Permission denied to invoke engine control API");
                return -7;
            }
            return setPasswordInternal(engineInfoLocked, str2);
        }
    }

    public final int setPasswordInternal(SdpEngineInfo sdpEngineInfo, String str) {
        if (sdpEngineInfo == null) {
            return -5;
        }
        int id = sdpEngineInfo.getId();
        if (SecureUtil.isEmpty(str)) {
            return -1;
        }
        if (sdpEngineInfo.getState() != 2) {
            return -6;
        }
        byte[] managedCredential = getManagedCredential(id);
        if (SecureUtil.isEmpty(managedCredential)) {
            Log.e("SdpManagerImpl", "set password :: Unexpected condition while derive managed creential for user " + id);
            return -6;
        }
        String str2 = new String(managedCredential, StandardCharsets.UTF_8);
        VerifyCredentialResponse changePassword = this.mVirtualLock.changePassword(str, str2, id);
        int i = changePassword.isMatched() ? 0 : -99;
        if (i == 0) {
            Log.d("SdpManagerImpl", String.format("set password :: Successfully done for user %d", Integer.valueOf(id)));
            cacheManagedCredential(str.getBytes(Charset.forName("UTF-8")), id);
        } else {
            Log.d("SdpManagerImpl", String.format("set password :: Failed to set password for user %d... [ rc : %d ]", Integer.valueOf(id), Integer.valueOf(i)));
        }
        changePassword.destroy();
        SecureUtil.clearAll(managedCredential, str2);
        return i;
    }

    public int resetPassword(String str, String str2, String str3) {
        if (!isSupportedDevice()) {
            return -10;
        }
        synchronized (this.mSdpEngineDbLock) {
            SdpEngineInfo engineInfoLocked = getEngineInfoLocked(str);
            if (engineInfoLocked == null) {
                Log.e("SdpManagerImpl", "reset :: Can't find engine info " + str);
                return -5;
            }
            if (engineInfoLocked.isAndroidDefaultEngine()) {
                Log.e("SdpManagerImpl", "reset :: Not supported anymore to " + str);
                return -99;
            }
            if (!isEngineOwner(engineInfoLocked) && !isPrivileged(engineInfoLocked) && !isSystemComponent(engineInfoLocked)) {
                Log.e("SdpManagerImpl", "reset :: Permission denied to invoke engine control API");
                return -7;
            }
            return resetPasswordInternal(engineInfoLocked, str2, str3);
        }
    }

    public final int resetPasswordInternal(SdpEngineInfo sdpEngineInfo, String str, String str2) {
        byte[] bytes;
        int id = sdpEngineInfo.getId();
        Log.d("SdpManagerImpl", "Reset password for user " + id);
        if (sdpEngineInfo.isMinor()) {
            bytes = getResetTokenViaProtector(id);
        } else {
            bytes = str != null ? str.getBytes(Charset.forName("UTF-8")) : null;
        }
        byte[] bArr = bytes;
        if (SecureUtil.isEmpty(bArr)) {
            return -2;
        }
        if (SecureUtil.isEmpty(str2)) {
            return -1;
        }
        return this.mVirtualLock.setPasswordWithToken(str2, getTokenHandleViaProtector(id), bArr, id) ? 0 : -99;
    }

    public boolean isSDPEnabled(int i) {
        return isSupportedDevice() && getEngineInfoLocked(i) != null;
    }

    public int migrate(String str) {
        return !isSupportedDevice() ? -10 : -1;
    }

    public final int assignEngineId(String str) {
        int i;
        int extractAndroidDefaultUserId = SdpUtil.extractAndroidDefaultUserId(str);
        if (extractAndroidDefaultUserId >= 0) {
            return extractAndroidDefaultUserId;
        }
        Log.d("SdpManagerImpl", "custom engine. assign custom engine id");
        synchronized (this.mSdpEngineDbLock) {
            i = 1000;
            while (this.mSdpEngineMap.get(i) != null) {
                i++;
            }
        }
        Log.d("SdpManagerImpl", "custom engine : " + str + ", id assigned. [" + i + "]");
        return i;
    }

    public int addEngine(SdpCreationParam sdpCreationParam, String str, String str2) {
        if (!isSupportedDevice()) {
            return -10;
        }
        int callingPid = Binder.getCallingPid();
        int callingUid = Binder.getCallingUid();
        int userId = UserHandle.getUserId(Binder.getCallingUid());
        Log.e("SdpManagerImpl", String.format("add engine :: calling by the process %d %d", Integer.valueOf(callingPid), Integer.valueOf(callingUid)));
        if (sdpCreationParam == null || sdpCreationParam.getAlias() == null || sdpCreationParam.getAlias().isEmpty() || sdpCreationParam.getPrivilegedApps() == null) {
            Log.e("SdpManagerImpl", "add engine :: failed to create engine due to invalid parameters");
            return -3;
        }
        Log.e("SdpManagerImpl", "add engine :: " + sdpCreationParam);
        String alias = sdpCreationParam.getAlias();
        String packageName = getPackageName(this.mContext, callingUid);
        int assignEngineId = assignEngineId(alias);
        int flags = sdpCreationParam.getFlags();
        Log.d("SdpManagerImpl", String.format("add engine :: alias : %s, id : %d", alias, Integer.valueOf(assignEngineId)));
        SdpEngineInfo sdpEngineInfo = new SdpEngineInfo(alias, assignEngineId, userId, 1, flags, 6, false);
        sdpEngineInfo.setPackageName(packageName);
        if (sdpEngineInfo.isAndroidDefaultEngine()) {
            Log.e("SdpManagerImpl", "add engine :: not supported anymore to " + alias);
            return -3;
        }
        if (SecureUtil.isEmpty(str)) {
            return -1;
        }
        if (sdpEngineInfo.isMdfpp() && (SecureUtil.isEmpty(str2) || str2.length() < 32)) {
            return -2;
        }
        synchronized (this.mSdpEngineDbLock) {
            if (getEngineInfoLocked(alias) == null && getEngineInfoLocked(assignEngineId) == null) {
                return addEngineInternal(sdpCreationParam, sdpEngineInfo, callingPid, callingUid, str, str2);
            }
            Log.e("SdpManagerImpl", "add engine :: failed to create engine due to pre-existing engine");
            return -4;
        }
    }

    public final int addEngineInternal(SdpCreationParam sdpCreationParam, SdpEngineInfo sdpEngineInfo, int i, int i2, String str, String str2) {
        byte[] generateRandomBytes;
        int i3;
        if (sdpEngineInfo.isMdfpp()) {
            generateRandomBytes = str2.getBytes(Charset.forName("UTF-8"));
        } else {
            generateRandomBytes = SecureUtil.generateRandomBytes(32);
        }
        byte[] bArr = generateRandomBytes;
        byte[] generateRandomBytes2 = SecureUtil.generateRandomBytes(32);
        int id = sdpEngineInfo.getId();
        Log.d("SdpManagerImpl", "try to add engine internal for virtual user : " + id);
        this.mVirtualLock.clean(id);
        this.mVirtualLock.clearLock(id);
        long establish = this.mVirtualLock.establish(str, bArr, id);
        byte[] secret = this.mVirtualLock.checkPassword(str, id).getSecret();
        if (SecureUtil.isEmpty(secret) || SecureUtil.isFailed(Boolean.valueOf(saveTokenHandleViaProtector(establish, id)))) {
            i3 = -14;
        } else {
            i3 = addEngineNative(sdpEngineInfo, secret);
            if (!SecureUtil.isFailed(Integer.valueOf(i3))) {
                i3 = this.mServiceKeeper.addPolicy(this.mContext, i, i2, sdpEngineInfo, sdpCreationParam.getPrivilegedApps());
                SecureUtil.isFailed(Integer.valueOf(i3));
            }
        }
        if (i3 != 0) {
            Log.e("SdpManagerImpl", "add engine :: Failed with error code " + i3);
            this.mVirtualLock.clean(id);
            this.mVirtualLock.clearLock(id);
            this.mServiceKeeper.removePolicy(this.mContext, i, i2, sdpEngineInfo);
        } else {
            Log.d("SdpManagerImpl", "add engine - Sucessfully done with " + sdpEngineInfo.toString());
            sdpEngineInfo.setState(1);
            synchronized (this.mSdpEngineDbLock) {
                this.mSdpEngineMap.put(id, sdpEngineInfo);
                this.mSdpEngineDb.storeEngineInfoLocked(sdpEngineInfo);
                this.mSdpEngineDb.updateEngineListLocked();
            }
            this.mKeyProtector.protect(generateRandomBytes2, "SdpSessionKey", id);
            this.mKeyProtector.protect(null, "SdpTokenHandle", id);
            if (sdpEngineInfo.isMinor()) {
                this.mKeyProtector.protect(bArr, "SdpResetToken", id);
            }
        }
        SecureUtil.clearAll(secret, generateRandomBytes2, bArr);
        return i3;
    }

    public final int addEngineNative(SdpEngineInfo sdpEngineInfo, byte[] bArr) {
        if (SecureUtil.isAnyoneEmptyHere(sdpEngineInfo, bArr)) {
            return -99;
        }
        if (!SecureUtil.isFailed(Integer.valueOf(nativeOnUserAdded(sdpEngineInfo.getId(), sdpEngineInfo.getUserId(), bArr)))) {
            return 0;
        }
        Log.e("SdpManagerImpl", "add engine - failed to create engine due to native error " + sdpEngineInfo.getId());
        return -11;
    }

    public int removeEngine(String str) {
        SdpEngineInfo engineInfoLocked;
        if (!isSupportedDevice()) {
            return -10;
        }
        synchronized (this.mSdpEngineDbLock) {
            engineInfoLocked = getEngineInfoLocked(str);
        }
        if (engineInfoLocked == null) {
            Log.e("SdpManagerImpl", "removeEngine :: no engine found");
            return -5;
        }
        if (engineInfoLocked.isAndroidDefaultEngine()) {
            Log.e("SdpManagerImpl", "remove :: Not supported anymore to " + str);
            return -99;
        }
        if (!isEngineOwner(engineInfoLocked) && !isSystemComponent(engineInfoLocked)) {
            Log.e("SdpManagerImpl", "remove :: Permission denied to invoke engine control API");
            return -7;
        }
        return removeEngineInternal(engineInfoLocked);
    }

    public final int removeEngineInternal(SdpEngineInfo sdpEngineInfo) {
        int id = sdpEngineInfo.getId();
        this.mKeyProtector.delete("SdpEphemeralKey", id);
        this.mKeyProtector.delete("SdpSessionKey", id);
        this.mVirtualLock.clearLock(id);
        this.mVirtualLock.clean(id);
        int removePolicy = this.mServiceKeeper.removePolicy(this.mContext, Binder.getCallingPid(), Binder.getCallingUid(), sdpEngineInfo);
        if (removePolicy == 0) {
            synchronized (this.mSdpEngineDbLock) {
                this.mSdpEngineMap.remove(sdpEngineInfo.getId());
                this.mSdpEngineDb.removeEngineInfoLocked(sdpEngineInfo);
                this.mSdpEngineDb.updateEngineListLocked();
                onEngineRemoved(sdpEngineInfo);
            }
            int removeEngineNative = removeEngineNative(sdpEngineInfo.getId(), sdpEngineInfo.getUserId());
            Log.d("SdpManagerImpl", "remove :: successfully engine removed! " + sdpEngineInfo.toString());
            return removeEngineNative;
        }
        Log.e("SdpManagerImpl", "remove :: failed [" + removePolicy + "]");
        return removePolicy;
    }

    public final int removeEngineNative(int i, int i2) {
        if (nativeOnUserRemoved(i, i2) == 0) {
            return 0;
        }
        Log.e("SdpManagerImpl", "removeEngineNative :: failed " + i + "/" + i2);
        return -11;
    }

    public final void handleEmptyListenerRoll(int i) {
        ListenerRoll listenerRoll = (ListenerRoll) this.mListenerMap.get(Integer.valueOf(i));
        if (listenerRoll == null || !listenerRoll.isEmpty()) {
            return;
        }
        this.mListenerMap.remove(Integer.valueOf(i));
    }

    /* loaded from: classes2.dex */
    public final class ListenerRoll {
        public final ArrayList mStateListeners;

        public /* synthetic */ ListenerRoll(ListenerRollIA listenerRollIA) {
            this();
        }

        public ListenerRoll() {
            this.mStateListeners = new ArrayList();
        }

        public boolean enroll(StateListener stateListener) {
            return this.mStateListeners.add(stateListener);
        }

        public boolean disenroll(StateListener stateListener) {
            return this.mStateListeners.remove(stateListener);
        }

        public boolean isEmpty() {
            return this.mStateListeners.isEmpty();
        }

        public int size() {
            return this.mStateListeners.size();
        }

        public ArrayList get() {
            return this.mStateListeners;
        }
    }

    /* loaded from: classes2.dex */
    public final class StateListener implements IBinder.DeathRecipient {
        public int mEngineId;
        public boolean mIsValid = false;
        public WeakReference mWeakListener;

        public StateListener(int i, ISdpListener iSdpListener) {
            this.mEngineId = i;
            this.mWeakListener = new WeakReference(iSdpListener);
            Optional.ofNullable(asBinder()).ifPresent(new Consumer() { // from class: com.android.server.knox.dar.sdp.SdpManagerImpl$StateListener$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    SdpManagerImpl.StateListener.this.lambda$new$0((IBinder) obj);
                }
            });
        }

        public /* synthetic */ void lambda$new$0(IBinder iBinder) {
            try {
                iBinder.linkToDeath(this, 0);
                this.mIsValid = true;
            } catch (Exception e) {
                Log.e("SdpManagerImpl", "Failed to link death listener...");
                e.printStackTrace();
                releaseReferences();
            }
        }

        public ISdpListener get() {
            return (ISdpListener) this.mWeakListener.get();
        }

        public IBinder asBinder() {
            ISdpListener iSdpListener = get();
            if (iSdpListener == null) {
                return null;
            }
            return iSdpListener.asBinder();
        }

        public ListenerRoll getParent() {
            return (ListenerRoll) SdpManagerImpl.this.mListenerMap.get(Integer.valueOf(this.mEngineId));
        }

        public boolean isValid() {
            return this.mIsValid;
        }

        public void releaseReferences() {
            this.mWeakListener.clear();
            this.mIsValid = false;
        }

        public void dispose() {
            if (this.mIsValid) {
                if (getParent() != null) {
                    getParent().disenroll(this);
                }
                Optional.ofNullable(asBinder()).ifPresent(new Consumer() { // from class: com.android.server.knox.dar.sdp.SdpManagerImpl$StateListener$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        SdpManagerImpl.StateListener.this.lambda$dispose$1((IBinder) obj);
                    }
                });
                releaseReferences();
            }
        }

        public /* synthetic */ void lambda$dispose$1(IBinder iBinder) {
            try {
                iBinder.unlinkToDeath(this, 0);
            } catch (NullPointerException unused) {
                Log.e("SdpManagerImpl", "Listener might be already finalized...");
            } catch (Exception e) {
                Log.e("SdpManagerImpl", "Failed to unlink death listener");
                e.printStackTrace();
            }
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            synchronized (SdpManagerImpl.this.mListenerMap) {
                dispose();
                SdpManagerImpl.this.handleEmptyListenerRoll(this.mEngineId);
            }
        }
    }

    public void registerClient(int i, ISdpListener iSdpListener) {
        if (!isSupportedDevice() || iSdpListener == null || getEngineInfoLocked(i) == null) {
            return;
        }
        synchronized (this.mListenerMap) {
            ListenerRoll listenerRoll = (ListenerRoll) this.mListenerMap.get(Integer.valueOf(i));
            if (listenerRoll == null) {
                listenerRoll = new ListenerRoll();
                this.mListenerMap.put(Integer.valueOf(i), listenerRoll);
            }
            StateListener stateListener = new StateListener(i, iSdpListener);
            Log.d("SdpManagerImpl", String.format("registerClient :: Engine Id = %d, Roll Size = %d, Result = %s", Integer.valueOf(i), Integer.valueOf(listenerRoll.size()), Boolean.valueOf(stateListener.isValid() ? listenerRoll.enroll(stateListener) : false)));
        }
    }

    public void unregisterClient(int i, ISdpListener iSdpListener) {
        int i2;
        if (!isSupportedDevice() || iSdpListener == null || getEngineInfoLocked(i) == null) {
            return;
        }
        synchronized (this.mListenerMap) {
            ListenerRoll listenerRoll = (ListenerRoll) this.mListenerMap.get(Integer.valueOf(i));
            if (listenerRoll == null) {
                Log.d("SdpManagerImpl", "unregisterClient :: Already cleared...");
                i2 = 0;
            } else {
                Iterator it = listenerRoll.get().iterator();
                i2 = 0;
                while (it.hasNext()) {
                    StateListener stateListener = (StateListener) it.next();
                    if (iSdpListener.asBinder().equals(stateListener.asBinder())) {
                        stateListener.dispose();
                        i2++;
                    }
                }
                handleEmptyListenerRoll(i);
            }
            Log.d("SdpManagerImpl", String.format("unregisterClient :: Engine Id = %d, Roll Size = %d, Result = %d", Integer.valueOf(i), Integer.valueOf(this.mListenerMap.get(Integer.valueOf(i)) == null ? 0 : ((ListenerRoll) this.mListenerMap.get(Integer.valueOf(i))).size()), Integer.valueOf(i2)));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0045 A[Catch: all -> 0x0090, TryCatch #0 {, blocks: (B:4:0x0003, B:6:0x0013, B:8:0x001e, B:10:0x0024, B:20:0x002f, B:15:0x0045, B:17:0x0048, B:25:0x0037, B:23:0x003c, B:32:0x004b, B:33:0x004e, B:36:0x006c, B:37:0x008e, B:41:0x005c), top: B:3:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0048 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onStateChange(int r10, int r11) {
        /*
            r9 = this;
            java.util.Map r0 = r9.mListenerMap
            monitor-enter(r0)
            java.util.Map r1 = r9.mListenerMap     // Catch: java.lang.Throwable -> L90
            java.lang.Integer r2 = java.lang.Integer.valueOf(r10)     // Catch: java.lang.Throwable -> L90
            java.lang.Object r1 = r1.get(r2)     // Catch: java.lang.Throwable -> L90
            com.android.server.knox.dar.sdp.SdpManagerImpl$ListenerRoll r1 = (com.android.server.knox.dar.sdp.SdpManagerImpl.ListenerRoll) r1     // Catch: java.lang.Throwable -> L90
            r2 = 1
            r3 = 0
            if (r1 == 0) goto L4e
            java.util.ArrayList r1 = r1.get()     // Catch: java.lang.Throwable -> L90
            int r4 = r1.size()     // Catch: java.lang.Throwable -> L90
            int r4 = r4 - r2
        L1c:
            if (r4 < 0) goto L4b
            java.lang.Object r5 = r1.get(r4)     // Catch: java.lang.Throwable -> L90
            com.android.server.knox.dar.sdp.SdpManagerImpl$StateListener r5 = (com.android.server.knox.dar.sdp.SdpManagerImpl.StateListener) r5     // Catch: java.lang.Throwable -> L90
            com.samsung.android.knox.dar.sdp.ISdpListener r6 = r5.get()     // Catch: java.lang.Exception -> L35 android.os.RemoteException -> L3b java.lang.Throwable -> L90
            if (r6 == 0) goto L2c
            r7 = r2
            goto L2d
        L2c:
            r7 = r3
        L2d:
            if (r7 == 0) goto L43
            r6.onStateChange(r11)     // Catch: java.lang.Exception -> L33 android.os.RemoteException -> L3c java.lang.Throwable -> L90
            goto L43
        L33:
            r6 = move-exception
            goto L37
        L35:
            r6 = move-exception
            r7 = r3
        L37:
            r6.printStackTrace()     // Catch: java.lang.Throwable -> L90
            goto L43
        L3b:
            r7 = r3
        L3c:
            java.lang.String r6 = "SdpManagerImpl"
            java.lang.String r8 = "Listener might be dead..."
            android.util.Log.e(r6, r8)     // Catch: java.lang.Throwable -> L90
        L43:
            if (r7 != 0) goto L48
            r5.dispose()     // Catch: java.lang.Throwable -> L90
        L48:
            int r4 = r4 + (-1)
            goto L1c
        L4b:
            r9.handleEmptyListenerRoll(r10)     // Catch: java.lang.Throwable -> L90
        L4e:
            java.util.Map r1 = r9.mListenerMap     // Catch: java.lang.Throwable -> L90
            java.lang.Integer r4 = java.lang.Integer.valueOf(r10)     // Catch: java.lang.Throwable -> L90
            java.lang.Object r1 = r1.get(r4)     // Catch: java.lang.Throwable -> L90
            if (r1 != 0) goto L5c
            r9 = r3
            goto L6c
        L5c:
            java.util.Map r9 = r9.mListenerMap     // Catch: java.lang.Throwable -> L90
            java.lang.Integer r1 = java.lang.Integer.valueOf(r10)     // Catch: java.lang.Throwable -> L90
            java.lang.Object r9 = r9.get(r1)     // Catch: java.lang.Throwable -> L90
            com.android.server.knox.dar.sdp.SdpManagerImpl$ListenerRoll r9 = (com.android.server.knox.dar.sdp.SdpManagerImpl.ListenerRoll) r9     // Catch: java.lang.Throwable -> L90
            int r9 = r9.size()     // Catch: java.lang.Throwable -> L90
        L6c:
            java.lang.String r1 = "SdpManagerImpl"
            java.lang.String r4 = "onStateChange :: Engine Id : %d, State : %d, Roll Size : %d"
            r5 = 3
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch: java.lang.Throwable -> L90
            java.lang.Integer r10 = java.lang.Integer.valueOf(r10)     // Catch: java.lang.Throwable -> L90
            r5[r3] = r10     // Catch: java.lang.Throwable -> L90
            java.lang.Integer r10 = java.lang.Integer.valueOf(r11)     // Catch: java.lang.Throwable -> L90
            r5[r2] = r10     // Catch: java.lang.Throwable -> L90
            java.lang.Integer r9 = java.lang.Integer.valueOf(r9)     // Catch: java.lang.Throwable -> L90
            r10 = 2
            r5[r10] = r9     // Catch: java.lang.Throwable -> L90
            java.lang.String r9 = java.lang.String.format(r4, r5)     // Catch: java.lang.Throwable -> L90
            android.util.Log.d(r1, r9)     // Catch: java.lang.Throwable -> L90
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L90
            return
        L90:
            r9 = move-exception
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L90
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.knox.dar.sdp.SdpManagerImpl.onStateChange(int, int):void");
    }

    /* loaded from: classes2.dex */
    public final class SdpManagerImplBinderListener implements IBinder.DeathRecipient {
        public int caller = 0;
        public String mAlias;
        public final ISdpListener mListener;

        public SdpManagerImplBinderListener(String str, ISdpListener iSdpListener) {
            this.mListener = iSdpListener;
            this.mAlias = str;
        }

        public void setCaller(int i) {
            this.caller = i;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            Log.d("SdpManagerImpl", "An ISdpListener has died!");
            synchronized (SdpManagerImpl.this.mBinderListeners) {
                ArrayList arrayList = (ArrayList) SdpManagerImpl.this.mBinderListeners.get(this.mAlias);
                arrayList.remove(this);
                this.mListener.asBinder().unlinkToDeath(this, 0);
                if (arrayList.size() == 0) {
                    SdpManagerImpl.this.mBinderListeners.remove(this.mAlias);
                }
            }
        }
    }

    public int registerListener(String str, ISdpListener iSdpListener) {
        if (!isSupportedDevice()) {
            return -10;
        }
        Log.e("SdpManagerImpl", "registerListener");
        synchronized (this.mSdpEngineDbLock) {
            SdpEngineInfo engineInfoLocked = getEngineInfoLocked(str);
            if (engineInfoLocked == null) {
                return -5;
            }
            return registerListenerInternal(engineInfoLocked, iSdpListener);
        }
    }

    public int unregisterListener(String str, ISdpListener iSdpListener) {
        if (!isSupportedDevice()) {
            return -10;
        }
        Log.e("SdpManagerImpl", "unregisterListener");
        synchronized (this.mSdpEngineDbLock) {
            SdpEngineInfo engineInfoLocked = getEngineInfoLocked(str);
            if (engineInfoLocked == null) {
                return -5;
            }
            return unregisterListenerInternal(engineInfoLocked, iSdpListener);
        }
    }

    public final int registerListenerInternal(SdpEngineInfo sdpEngineInfo, ISdpListener iSdpListener) {
        if (sdpEngineInfo == null) {
            return -5;
        }
        Log.e("SdpManagerImpl", "registerListener from pid = " + Binder.getCallingPid() + ", uid = " + Binder.getCallingUid());
        ArrayList arrayList = (ArrayList) this.mBinderListeners.get(sdpEngineInfo.getAlias());
        if (arrayList == null) {
            arrayList = new ArrayList();
            this.mBinderListeners.put(sdpEngineInfo.getAlias(), arrayList);
        }
        synchronized (this.mBinderListeners) {
            SdpManagerImplBinderListener sdpManagerImplBinderListener = new SdpManagerImplBinderListener(sdpEngineInfo.getAlias(), iSdpListener);
            try {
                sdpManagerImplBinderListener.setCaller(Binder.getCallingPid());
                iSdpListener.asBinder().linkToDeath(sdpManagerImplBinderListener, 0);
                arrayList.add(sdpManagerImplBinderListener);
            } catch (RemoteException unused) {
                Log.e("SdpManagerImpl", "Failed to link to listener death");
            }
        }
        return 0;
    }

    public final int unregisterListenerInternal(SdpEngineInfo sdpEngineInfo, ISdpListener iSdpListener) {
        if (sdpEngineInfo == null) {
            return -5;
        }
        synchronized (this.mBinderListeners) {
            ArrayList arrayList = (ArrayList) this.mBinderListeners.get(sdpEngineInfo.getAlias());
            if (arrayList == null) {
                return 0;
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                SdpManagerImplBinderListener sdpManagerImplBinderListener = (SdpManagerImplBinderListener) it.next();
                if (sdpManagerImplBinderListener.mListener.asBinder() == iSdpListener.asBinder()) {
                    arrayList.remove(arrayList.indexOf(sdpManagerImplBinderListener));
                    iSdpListener.asBinder().unlinkToDeath(sdpManagerImplBinderListener, 0);
                }
            }
            if (arrayList.size() == 0) {
                this.mBinderListeners.remove(sdpEngineInfo.getAlias());
            }
            return 0;
        }
    }

    public final void onEngineRemoved(SdpEngineInfo sdpEngineInfo) {
        clearInternalEngineInfo(sdpEngineInfo.getId());
        synchronized (this.mBinderListeners) {
            ArrayList arrayList = (ArrayList) this.mBinderListeners.get(sdpEngineInfo.getAlias());
            if (arrayList == null) {
                return;
            }
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                try {
                    ((SdpManagerImplBinderListener) arrayList.get(size)).mListener.onEngineRemoved();
                } catch (RemoteException unused) {
                    Log.e("SdpManagerImpl", "Listener dead");
                    arrayList.remove(size);
                } catch (Exception e) {
                    Log.e("SdpManagerImpl", "Listener failed", e);
                }
            }
        }
    }

    public final boolean establish(byte[] bArr, int i) {
        SDPLog.d("Establish new engine for user " + i);
        boolean z = false;
        if (isVirtualUserId(i) || SecureUtil.isEmpty(bArr)) {
            return false;
        }
        SdpEngineInfo sdpEngineInfo = new SdpEngineInfo(SdpUtil.getAndroidDefaultAlias(i), i, i, 1, 0, 6, false);
        ArrayList arrayList = new ArrayList();
        if (SecureUtil.isFailed(Integer.valueOf(addEngineNative(sdpEngineInfo, bArr)))) {
            SDPLog.d("establish - Unexpected failure while native setup");
        } else if (SecureUtil.isFailed(Integer.valueOf(this.mServiceKeeper.addPolicy(this.mContext, Binder.getCallingPid(), Binder.getCallingUid(), sdpEngineInfo, arrayList)))) {
            SDPLog.d("establish - Unexpected failure while policy setup");
        } else {
            initInternalEngineInfo(sdpEngineInfo.getId());
            synchronized (this.mSdpEngineDbLock) {
                this.mSdpEngineMap.put(sdpEngineInfo.getId(), sdpEngineInfo);
                this.mSdpEngineDb.storeEngineInfoLocked(sdpEngineInfo);
                this.mSdpEngineDb.updateEngineListLocked();
            }
            z = true;
        }
        SDPLog.d(String.format("Result of engine establishment for user %d : %s", Integer.valueOf(i), Boolean.valueOf(z)));
        return z;
    }

    public final void initInternalEngineInfo(int i) {
        SDPLog.d("Engine info initialized for engine " + i);
        this.mSdpDatabaseCache.destroy(i);
        setMasterKeyVersion(1, i);
    }

    public final void loadInternalEngineInfo(int i) {
        SDPLog.d("Engine info loaded for engine " + i);
        this.mSdpDatabaseCache.preload(i);
    }

    public final void clearInternalEngineInfo(int i) {
        SDPLog.d("Engine info cleared for engine " + i);
        this.mSdpDatabaseCache.destroy(i);
    }

    public final int unlockFinal(byte[] bArr, SdpEngineInfo sdpEngineInfo) {
        if (SecureUtil.isEmpty(bArr)) {
            return -1;
        }
        if (SecureUtil.isEmpty(sdpEngineInfo)) {
            return -3;
        }
        if (SecureUtil.isFailed(Integer.valueOf(nativeOnDeviceUnlocked(sdpEngineInfo.getId(), bArr)))) {
            return -11;
        }
        synchronized (this.mSdpEngineDbLock) {
            setEngineStateLocked(sdpEngineInfo, 2);
        }
        return 0;
    }

    public final int lockFinal(SdpEngineInfo sdpEngineInfo) {
        if (SecureUtil.isEmpty(sdpEngineInfo)) {
            return -3;
        }
        if (SecureUtil.isFailed(Integer.valueOf(nativeOnDeviceLocked(sdpEngineInfo.getId(), sdpEngineInfo.getUserId())))) {
            return -11;
        }
        synchronized (this.mSdpEngineDbLock) {
            setEngineStateLocked(sdpEngineInfo, 1);
        }
        return 0;
    }

    public final int onMigrationInternal(SdpEngineInfo sdpEngineInfo) {
        if (sdpEngineInfo == null) {
            return -3;
        }
        SDPLog.d("SdpManagerImpl", "onMigrationInternal :: " + sdpEngineInfo.toString());
        int version = sdpEngineInfo.getVersion();
        if (version == 1) {
            version++;
        }
        if (version == 2) {
            version++;
        }
        if (version == 3) {
            version++;
        }
        if (version == 4) {
            version++;
        }
        if (version == 5) {
            SDPLog.d("SdpManagerImpl", "onMigrationInternal :: version 5 -> 6)");
            synchronized (this.mSdpEngineDbLock) {
                sdpEngineInfo.setVersion(version + 1);
                this.mSdpEngineDb.storeEngineInfoLocked(sdpEngineInfo);
            }
        }
        SDPLog.d("SdpManagerImpl", "onMigrationInternal :: DONE");
        return 0;
    }

    @SystemApi
    public void initializeMasterKeyIfRequired(byte[] bArr, final int i) {
        boolean z;
        if (isSupportedDevice() && DarUtil.isEnterpriseUser(getUserInfo(i))) {
            SDPLog.i("sdp essential key initialized for user " + i);
            SDPLog.p("masterKey", bArr, "userId", Integer.valueOf(i));
            byte[] ephemeralKeyViaProtector = getEphemeralKeyViaProtector(i);
            if (SecureUtil.isEmpty(ephemeralKeyViaProtector)) {
                SDPLog.d("Failed to get ephemeral key");
            } else if (SecureUtil.isFailed(Boolean.valueOf(reWrapSdpKeys(bArr, ephemeralKeyViaProtector, i)))) {
                SDPLog.d("Failed to rewrap sdp essential key");
            } else if (SecureUtil.isFailed(Boolean.valueOf(removeEphemeralKeyViaProtector(i)))) {
                SDPLog.d("Failed to delete ephemeral key");
            } else {
                z = true;
                if (z && ((Integer) getLockSettingsInternal().map(new Function() { // from class: com.android.server.knox.dar.sdp.SdpManagerImpl$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        Integer lambda$initializeMasterKeyIfRequired$4;
                        lambda$initializeMasterKeyIfRequired$4 = SdpManagerImpl.lambda$initializeMasterKeyIfRequired$4(i, (LockSettingsInternal) obj);
                        return lambda$initializeMasterKeyIfRequired$4;
                    }
                }).orElse(-1)).intValue() == -1) {
                    SDPLog.d(String.format("User %d has none type credential", Integer.valueOf(i)));
                    saveEphemeralKeyViaProtector(bArr, i);
                }
                SecureUtil.clear(bArr);
                SDPLog.d("Result of key adjustment : " + z);
            }
            z = false;
            if (z) {
                SDPLog.d(String.format("User %d has none type credential", Integer.valueOf(i)));
                saveEphemeralKeyViaProtector(bArr, i);
            }
            SecureUtil.clear(bArr);
            SDPLog.d("Result of key adjustment : " + z);
        }
    }

    public static /* synthetic */ Integer lambda$initializeMasterKeyIfRequired$4(int i, LockSettingsInternal lockSettingsInternal) {
        return Integer.valueOf(lockSettingsInternal.getCredentialType(i));
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x008a  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0095  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x008d  */
    @android.annotation.SystemApi
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void unlockSdpOnCredentialVerified(byte[] r4, int r5) {
        /*
            r3 = this;
            boolean r0 = r3.isSupportedDevice()
            if (r0 != 0) goto L7
            return
        L7:
            android.content.pm.UserInfo r0 = r3.getUserInfo(r5)
            boolean r0 = com.android.server.knox.dar.DarUtil.isEnterpriseUser(r0)
            if (r0 != 0) goto L12
            return
        L12:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "sdp essential key acquired for user "
            r0.append(r1)
            r0.append(r5)
            java.lang.String r0 = r0.toString()
            com.android.server.knox.dar.sdp.SDPLog.i(r0)
            java.lang.String r0 = "masterKey"
            java.lang.String r1 = "userId"
            java.lang.Integer r2 = java.lang.Integer.valueOf(r5)
            java.lang.Object[] r0 = new java.lang.Object[]{r0, r4, r1, r2}
            com.android.server.knox.dar.sdp.SDPLog.p(r0)
            java.lang.Object r0 = r3.mSdpEngineDbLock
            monitor-enter(r0)
            com.samsung.android.knox.sdp.core.SdpEngineInfo r1 = r3.getEngineInfoLocked(r5)     // Catch: java.lang.Throwable -> Lb1
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Lb1
            boolean r0 = com.android.server.knox.dar.SecureUtil.isEmpty(r1)
            if (r0 == 0) goto L5b
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "Couldn't find engine info for user "
            r0.append(r1)
            r0.append(r5)
            java.lang.String r0 = r0.toString()
            com.android.server.knox.dar.sdp.SDPLog.d(r0)
            goto L66
        L5b:
            boolean r0 = com.android.server.knox.dar.SecureUtil.isEmpty(r4)
            if (r0 == 0) goto L69
            java.lang.String r0 = "Failed to unlock due to invalid key"
            com.android.server.knox.dar.sdp.SDPLog.d(r0)
        L66:
            r0 = -99
            goto L88
        L69:
            int r0 = r1.getState()
            r2 = 2
            if (r0 != r2) goto L84
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r2 = "Engine already unlocked for user "
            r0.append(r2)
            r0.append(r5)
            java.lang.String r0 = r0.toString()
            com.android.server.knox.dar.sdp.SDPLog.d(r0)
        L84:
            int r0 = r3.unlockFinal(r4, r1)
        L88:
            if (r0 != 0) goto L8d
            com.android.internal.widget.VerifyCredentialResponse r1 = com.android.internal.widget.VerifyCredentialResponse.OK
            goto L8f
        L8d:
            com.android.internal.widget.VerifyCredentialResponse r1 = com.android.internal.widget.VerifyCredentialResponse.ERROR
        L8f:
            boolean r2 = r1.isMatched()
            if (r2 == 0) goto L98
            r3.cacheManagedCredential(r4, r5)
        L98:
            com.android.server.knox.dar.SecureUtil.clear(r4)
            java.lang.String r3 = "Result of sdp unlock : %s [ rc : %d ]"
            java.lang.String r4 = r1.toString()
            java.lang.Integer r5 = java.lang.Integer.valueOf(r0)
            java.lang.Object[] r4 = new java.lang.Object[]{r4, r5}
            java.lang.String r3 = java.lang.String.format(r3, r4)
            com.android.server.knox.dar.sdp.SDPLog.d(r3)
            return
        Lb1:
            r3 = move-exception
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Lb1
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.knox.dar.sdp.SdpManagerImpl.unlockSdpOnCredentialVerified(byte[], int):void");
    }

    @SystemApi
    public void lockSdpIfRequired(int i) {
        SdpEngineInfo engineInfoLocked;
        int lockFinal;
        if (isSupportedDevice() && DarUtil.isEnterpriseUser(getUserInfo(i))) {
            SDPLog.i("sdp essential key eviction required for user " + i);
            SDPLog.p("userId" + i);
            synchronized (this.mSdpEngineDbLock) {
                engineInfoLocked = getEngineInfoLocked(i);
            }
            if (SecureUtil.isEmpty(engineInfoLocked)) {
                SDPLog.d("Couldn't find engine info for user " + i);
                lockFinal = -99;
            } else if (engineInfoLocked.getState() == 1) {
                SDPLog.d("Engine already locked for user " + i);
                lockFinal = 0;
            } else {
                lockFinal = lockFinal(engineInfoLocked);
            }
            VerifyCredentialResponse verifyCredentialResponse = lockFinal == 0 ? VerifyCredentialResponse.OK : VerifyCredentialResponse.ERROR;
            if (verifyCredentialResponse.isMatched()) {
                clearManageCredentialIfRequired(i);
            }
            SDPLog.d(String.format("Result of sdp lock : %s [ rc : %d ]", verifyCredentialResponse.toString(), Integer.valueOf(lockFinal)));
        }
    }

    public final void clearManageCredentialIfRequired(int i) {
        if (isVirtualUserId(i) || hasBiometricTypeTraced(i) || hasNoSecurity(i)) {
            return;
        }
        clearManagedCredential(i);
    }

    @SystemApi
    public void unlockSdpIfUnsecuredOrBiometricAuthenticated(int i, int i2) {
        SdpEngineInfo engineInfoLocked;
        byte[] masterKey;
        if (isSupportedDevice() && DarUtil.isEnterpriseUser(getUserInfo(i))) {
            SDPLog.i("sdp essential key derivation required for user " + i);
            SDPLog.d("Issued order is identified as " + i2);
            synchronized (this.mSdpEngineDbLock) {
                engineInfoLocked = getEngineInfoLocked(i);
            }
            int i3 = -99;
            if (SecureUtil.isEmpty(engineInfoLocked)) {
                SDPLog.d("Couldn't find engine info for user " + i);
                masterKey = null;
            } else {
                masterKey = getMasterKey(i, i2);
                if (SecureUtil.isEmpty(masterKey)) {
                    SDPLog.d("Failed to unlock due to invalid key");
                } else {
                    if (engineInfoLocked.getState() == 2) {
                        SDPLog.d("Engine already unlocked for user " + i);
                    }
                    i3 = unlockFinal(masterKey, engineInfoLocked);
                }
            }
            VerifyCredentialResponse verifyCredentialResponse = i3 == 0 ? VerifyCredentialResponse.OK : VerifyCredentialResponse.ERROR;
            if (verifyCredentialResponse.isMatched()) {
                cacheManagedCredential(masterKey, i);
            }
            SecureUtil.clear(masterKey);
            SDPLog.d(String.format("Result of sdp unlock : %s [ rc : %d ]", verifyCredentialResponse.toString(), Integer.valueOf(i3)));
        }
    }

    public final byte[] getMasterKey(int i, int i2) {
        byte[] ephemeralKeyViaProtector;
        if (i2 == 4) {
            ephemeralKeyViaProtector = getEphemeralKeyViaProtector(i);
            if (SecureUtil.isFailed(ephemeralKeyViaProtector)) {
                SDPLog.d("Ephemeral key not found for user " + i);
            }
        } else if (i2 == 8 || i2 == 16) {
            ephemeralKeyViaProtector = getManagedCredential(i);
            if (SecureUtil.isFailed(ephemeralKeyViaProtector)) {
                SDPLog.d("Managed credential not found for user " + i);
            }
        } else {
            SDPLog.d("Unexpected condition while check order " + i2);
            ephemeralKeyViaProtector = null;
        }
        SDPLog.p("masterKey", ephemeralKeyViaProtector, "userId", Integer.valueOf(i), "order", Integer.valueOf(i2));
        return ephemeralKeyViaProtector;
    }

    @SystemApi
    public void saveMasterKeyIfUnsecured(byte[] bArr, int i) {
        if (isSupportedDevice() && DarUtil.isEnterpriseUser(getUserInfo(i))) {
            SDPLog.i("sdp essential key deserted with user " + i);
            SDPLog.p("masterKey", bArr, "userId", Integer.valueOf(i));
            SDPLog.d(String.format("User %d has none type credential", Integer.valueOf(i)));
            saveEphemeralKeyViaProtector(bArr, i);
        }
    }

    @SystemApi
    public void onCredentialChanged(int i, int i2) {
        SdpEngineInfo engineInfoLocked;
        if (isSupportedDevice() && DarUtil.isEnterpriseUser(getUserInfo(i2))) {
            SDPLog.i("Password has been changed for user " + i2);
            if (i == -1) {
                synchronized (this.mSdpEngineDbLock) {
                    engineInfoLocked = getEngineInfoLocked(i2);
                }
                if (SecureUtil.isEmpty(engineInfoLocked) || engineInfoLocked.getState() != 1) {
                    return;
                }
                SDPLog.d("Engine is locked after changing to none type.");
                unlockSdpIfUnsecuredOrBiometricAuthenticated(i2, 4);
                return;
            }
            if (doesEphemeralKeyExist(i2)) {
                SDPLog.d(String.format("According as user %d password changed, remove ephemeral key", Integer.valueOf(i2)));
                removeEphemeralKeyViaProtector(i2);
            }
        }
    }

    public void onBiometricsAuthenticated(int i) {
        if (isSupportedDevice()) {
            Log.d("SdpManagerImpl", String.format("User %d has been authenticated with biometrics", Integer.valueOf(i)));
            if (DarUtil.isEnterpriseUser(getUserInfo(i)) && !SemPersonaManager.isDarDualEncryptionEnabled(i) && hasBiometricTypeTraced(i)) {
                unlockSdpIfUnsecuredOrBiometricAuthenticated(i, 8);
            }
        }
    }

    public void onDeviceOwnerLocked(int i) {
        if (isSupportedDevice()) {
            Log.d("SdpManagerImpl", String.format("User %d has been locked", Integer.valueOf(i)));
            if (!DarUtil.isDeviceOwnerUser(i) || hasNoSecurity(i)) {
                return;
            }
            lockSdpIfRequired(i);
        }
    }

    public final String getPackageName(Context context, int i) {
        String[] packagesForUid = context.getPackageManager().getPackagesForUid(i);
        String str = "";
        if (packagesForUid == null) {
            Log.e("SdpManagerImpl", "getPackage :: Not found with caller " + i);
        } else {
            for (String str2 : packagesForUid) {
                Log.d("SdpManagerImpl", "getPackage :: found name for caller " + i);
                if (str2 != null && str.isEmpty()) {
                    str = str2;
                }
            }
        }
        return str;
    }

    @SystemApi
    public boolean isSdpPackage(int i, String str) {
        if (this.mSdpEngineMap.size() == 0) {
            readEngineList();
        }
        int size = this.mSdpEngineMap.size();
        boolean z = false;
        for (int i2 = 0; i2 < size; i2++) {
            SdpEngineInfo sdpEngineInfo = (SdpEngineInfo) this.mSdpEngineMap.valueAt(i2);
            if (sdpEngineInfo != null && sdpEngineInfo.getPackageName().equals(str) && sdpEngineInfo.getUserId() == i) {
                z = true;
            }
        }
        return z;
    }

    public final boolean isDeviceProvisioned() {
        DevicePolicyManager devicePolicyManager = this.mDevicePolicyManager;
        return devicePolicyManager != null && devicePolicyManager.isDeviceProvisioned();
    }

    public final void handlePackageRemoved(String str, int i) {
        if (!isSupportedDevice() || i < 0 || SecureUtil.isEmpty(str)) {
            return;
        }
        Log.d("SdpManagerImpl", String.format("handlePackageRemoved - PackageName : %s, UserId : %d", str, Integer.valueOf(i)));
        if (!isDeviceProvisioned()) {
            Log.d("SdpManagerImpl", "handlePackageRemoved - Device is not provisioned yet...");
            return;
        }
        if (this.mSdpEngineMap.size() == 0) {
            readEngineList();
        }
        ArrayList arrayList = new ArrayList();
        synchronized (this.mSdpEngineDbLock) {
            for (int size = this.mSdpEngineMap.size() - 1; size >= 0; size--) {
                SdpEngineInfo sdpEngineInfo = (SdpEngineInfo) this.mSdpEngineMap.valueAt(size);
                if (sdpEngineInfo != null && sdpEngineInfo.getPackageName().equals(str) && sdpEngineInfo.getUserId() == i) {
                    Log.d("SdpManagerImpl", "handlePackageRemoved - Add package engine to Removing Engine " + sdpEngineInfo.toString());
                    arrayList.add(sdpEngineInfo);
                }
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            SdpEngineInfo sdpEngineInfo2 = (SdpEngineInfo) it.next();
            Log.d("SdpManagerImpl", "handlePackageRemoved - Removing Sdp Engine " + sdpEngineInfo2.toString());
            removeEngineInternal(sdpEngineInfo2);
        }
        File file = new File(FileUtil.getEncUserDir(i), str);
        if (file.exists()) {
            Log.d("SdpManagerImpl", String.format("handlePackageRemoved - Remove secure package dir : %s [ res : %b ]", file.getAbsolutePath(), Boolean.valueOf(this.mSecureFileSystemManager.removePkgDir(i, str))));
        }
    }

    public final void handleUserAdded(int i) {
        SdpEngineInfo engineInfoLocked;
        if (isSupportedDevice()) {
            Log.d("SdpManagerImpl", String.format("User %d added", Integer.valueOf(i)));
            UserInfo userInfo = getUserInfo(i);
            Log.d("SdpManagerImpl", String.format("user added - Is user %d Bmode? %s", Integer.valueOf(i), Boolean.valueOf(userInfo.isBMode())));
            Log.d("SdpManagerImpl", String.format("user added - Is user %d managed profile? %s", Integer.valueOf(i), Boolean.valueOf(userInfo.isManagedProfile())));
            synchronized (this.mSdpEngineDbLock) {
                engineInfoLocked = getEngineInfoLocked(i);
            }
            Object[] objArr = new Object[2];
            objArr[0] = Integer.valueOf(i);
            objArr[1] = Boolean.valueOf(engineInfoLocked != null);
            Log.d("SdpManagerImpl", String.format("user added - Has user %d sdp engine? %s", objArr));
        }
    }

    public final void removeInvalidEngines() {
        ArrayList arrayList = new ArrayList();
        synchronized (this.mSdpEngineDbLock) {
            for (int size = this.mSdpEngineMap.size() - 1; size >= 0; size--) {
                SdpEngineInfo sdpEngineInfo = (SdpEngineInfo) this.mSdpEngineMap.valueAt(size);
                if (sdpEngineInfo != null && sdpEngineInfo.getUserId() != 0 && getUserInfo(sdpEngineInfo.getUserId()) == NULL_USER && sdpEngineInfo.isCustomEngine()) {
                    Log.d("SdpManagerImpl", "Invalid user - Add Custom engine to Removing Engine " + sdpEngineInfo.toString());
                    arrayList.add(sdpEngineInfo);
                }
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            SdpEngineInfo sdpEngineInfo2 = (SdpEngineInfo) it.next();
            SDPLog.d("SdpManagerImpl", "Invalid user - Removing Custom engine " + sdpEngineInfo2.toString());
            removeEngineInternal(sdpEngineInfo2);
        }
    }

    public final void removeCustomEngines(int i) {
        ArrayList arrayList = new ArrayList();
        synchronized (this.mSdpEngineDbLock) {
            for (int size = this.mSdpEngineMap.size() - 1; size >= 0; size--) {
                SdpEngineInfo sdpEngineInfo = (SdpEngineInfo) this.mSdpEngineMap.valueAt(size);
                if (sdpEngineInfo != null && sdpEngineInfo.getUserId() == i && sdpEngineInfo.isCustomEngine()) {
                    Log.d("SdpManagerImpl", "Remove user - Add Custom engine to Removing Engine " + sdpEngineInfo.toString());
                    arrayList.add(sdpEngineInfo);
                }
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            SdpEngineInfo sdpEngineInfo2 = (SdpEngineInfo) it.next();
            SDPLog.d("SdpManagerImpl", "Remove user - Removing Custom engine " + sdpEngineInfo2.toString());
            removeEngineInternal(sdpEngineInfo2);
        }
    }

    public final void handleUserRemoved(final int i) {
        SdpEngineInfo engineInfoLocked;
        if (isSupportedDevice()) {
            Log.d("SdpManagerImpl", "Removing user " + i);
            removeInvalidEngines();
            removeCustomEngines(i);
            synchronized (this.mSdpEngineDbLock) {
                engineInfoLocked = getEngineInfoLocked(i);
            }
            if (engineInfoLocked == null) {
                Log.e("SdpManagerImpl", "Remove user - Engine not found with id " + i);
                return;
            }
            int id = engineInfoLocked.getId();
            Log.d("SdpManagerImpl", String.format("Remove user - Policy removal with id %d successfully done? %s", Integer.valueOf(id), Integer.valueOf(this.mServiceKeeper.removePolicy(this.mContext, Binder.getCallingPid(), Binder.getCallingUid(), engineInfoLocked))));
            synchronized (this.mSdpEngineDbLock) {
                this.mSdpEngineMap.remove(engineInfoLocked.getId());
                this.mSdpEngineDb.removeEngineInfoLocked(engineInfoLocked);
                this.mSdpEngineDb.updateEngineListLocked();
                onEngineRemoved(engineInfoLocked);
            }
            Log.d("SdpManagerImpl", String.format("Remove user - Native removal with id %d successfully done? %s", Integer.valueOf(id), Integer.valueOf(removeEngineNative(id, engineInfoLocked.getUserId()))));
            this.mKeyProtector.delete("SdpEphemeralKey", id);
            this.mKeyProtector.delete("SdpTokenHandle", id);
            this.mKeyProtector.delete("SdpResetToken", id);
            this.mKeyProtector.delete("SdpSessionKey", id);
            SDPLog.d("SdpManagerImpl", "Remove user - Engine remove! " + engineInfoLocked.toString());
            if (FileUtil.getEncUserDir(i).exists()) {
                SDPLog.d("Removing Enc user " + i + " directory.");
                if (SecureUtil.isFailed(getPackageManagerImpl().map(new Function() { // from class: com.android.server.knox.dar.sdp.SdpManagerImpl$$ExternalSyntheticLambda6
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        Boolean lambda$handleUserRemoved$5;
                        lambda$handleUserRemoved$5 = SdpManagerImpl.lambda$handleUserRemoved$5(i, (PackageManagerService.IPackageManagerImpl) obj);
                        return lambda$handleUserRemoved$5;
                    }
                }).orElse(Boolean.FALSE))) {
                    SDPLog.d("Failed to clean enc user directory");
                    return;
                }
                SDPLog.d("Removed Enc user " + i + " directory.");
            }
        }
    }

    public static /* synthetic */ Boolean lambda$handleUserRemoved$5(int i, PackageManagerService.IPackageManagerImpl iPackageManagerImpl) {
        return Boolean.valueOf(iPackageManagerImpl.removeEncUserDir(i));
    }

    public final void handleDeviceOwnerCleared() {
        SdpEngineInfo engineInfoLocked;
        SDPLog.d("Device Owner has been cleared!");
        synchronized (this.mSdpEngineDbLock) {
            engineInfoLocked = getEngineInfoLocked(0);
        }
        if (engineInfoLocked == null) {
            SDPLog.d("Device Owner engine already cleared");
        } else {
            removeEngineInternal(engineInfoLocked);
        }
        this.mSdpDatabaseCache.putBoolean(0, "do_cleared", false);
    }

    @SystemApi
    public void onDeviceLocked(int i) {
        if (isSupportedDevice()) {
            Log.d("SdpManagerImpl", "onDeviceLocked : User " + i);
            if (!DarUtil.isEnterpriseUser(getUserInfo(i)) || DarUtil.isDeviceOwnerUser(i) || SemPersonaManager.isDarDualEncryptionEnabled(i)) {
                return;
            }
            lockSdpIfRequired(i);
        }
    }

    @SystemApi
    public void onDeviceUnlocked(int i) {
        if (isSupportedDevice()) {
            Log.d("SdpManagerImpl", "onDeviceUnLocked " + i);
            if (!DarUtil.isEnterpriseUser(getUserInfo(i)) || SemPersonaManager.isDarDualEncryptionEnabled(i)) {
                return;
            }
            if (hasBiometricTypeTraced(i)) {
                unlockSdpIfUnsecuredOrBiometricAuthenticated(i, 8);
            } else if (this.mLockPatternUtils.isLockScreenDisabled(i)) {
                Log.d("SdpManagerImpl", "User has no lock");
                unlockSdpIfUnsecuredOrBiometricAuthenticated(i, 4);
            }
        }
    }

    @SystemApi
    public void welcomeNewUser(int i) {
        boolean z;
        if (isSupportedDevice()) {
            SDPLog.i("Welcome new user " + i);
            byte[] generateRandomBytes = SecureUtil.generateRandomBytes(32);
            if (SecureUtil.isFailed(Boolean.valueOf(establish(generateRandomBytes, i)))) {
                SDPLog.d("Welcome - Failed to create new engine");
            } else if (SecureUtil.isFailed(Boolean.valueOf(saveEphemeralKeyViaProtector(generateRandomBytes, i)))) {
                SDPLog.d("Welcome - Failed to safekeep sdp ephemeral key");
            } else {
                if (SecureUtil.isFailed(Boolean.valueOf(generateAndSaveSessionKey(i)))) {
                    SDPLog.d("Welcome - Failed to prepare session key");
                }
                this.mNeedToSetSdpPolicyForUser = i;
                z = true;
                SecureUtil.clear(generateRandomBytes);
                SDPLog.d(String.format("Result of welcoming new user %d : %s", Integer.valueOf(i), Boolean.valueOf(z)));
            }
            z = false;
            SecureUtil.clear(generateRandomBytes);
            SDPLog.d(String.format("Result of welcoming new user %d : %s", Integer.valueOf(i), Boolean.valueOf(z)));
        }
    }

    public final boolean reWrapSdpKeys(byte[] bArr, byte[] bArr2, int i) {
        boolean z;
        if (SecureUtil.isAnyoneEmptyHere(bArr, bArr2)) {
            SDPLog.d("reWrap - Failed to reWrap sdp keys due to invalid input");
        } else if (SecureUtil.isFailed(Integer.valueOf(nativeOnChangePassword(i, bArr2, bArr)))) {
            SDPLog.d("reWrap - Failed to change password");
        } else {
            z = true;
            SDPLog.d("Result of reWrapping sdp keys : " + z);
            return z;
        }
        z = false;
        SDPLog.d("Result of reWrapping sdp keys : " + z);
        return z;
    }

    public final boolean hasBiometricTypeTraced(int i) {
        int biometricType = this.mLockPatternUtils.getBiometricType(i);
        boolean z = biometricType != 0;
        if (z) {
            SDPLog.d(String.format("Biometrics detected for user %d [ Type : %d ]", Integer.valueOf(i), Integer.valueOf(biometricType)));
        } else {
            SDPLog.d("Biometrics not detected for user " + i);
        }
        return z;
    }

    public final boolean hasNoSecurity(int i) {
        int activePasswordQuality = this.mLockPatternUtils.getActivePasswordQuality(i);
        boolean isLockScreenDisabled = this.mLockPatternUtils.isLockScreenDisabled(i);
        Log.d("SdpManagerImpl", String.format("Check security - [ User ID : %d, Quality : %d, None type? %s ]", Integer.valueOf(i), Integer.valueOf(activePasswordQuality), Boolean.valueOf(isLockScreenDisabled)));
        return isLockScreenDisabled || activePasswordQuality == 0;
    }

    public final byte[] getEphemeralKeyViaProtector(int i) {
        return this.mKeyProtector.release("SdpEphemeralKey", i);
    }

    public final boolean saveEphemeralKeyViaProtector(byte[] bArr, int i) {
        return SecureUtil.record(this.mKeyProtector.protect(bArr, "SdpEphemeralKey", i));
    }

    public final boolean removeEphemeralKeyViaProtector(int i) {
        return SecureUtil.record(this.mKeyProtector.delete("SdpEphemeralKey", i));
    }

    public final boolean doesEphemeralKeyExist(int i) {
        return this.mKeyProtector.exists("SdpEphemeralKey", i);
    }

    public final byte[] getSessionKeyViaProtector(int i) {
        return this.mKeyProtector.release("SdpSessionKey", i);
    }

    public final boolean saveSessionKeyViaProtector(byte[] bArr, int i) {
        return SecureUtil.record(this.mKeyProtector.protect(bArr, "SdpSessionKey", i));
    }

    public final boolean generateAndSaveSessionKey(int i) {
        byte[] generateRandomBytes = SecureUtil.generateRandomBytes(32);
        try {
            return SecureUtil.record(saveSessionKeyViaProtector(generateRandomBytes, i));
        } finally {
            SecureUtil.clear(generateRandomBytes);
        }
    }

    @SystemApi
    public byte[] getResetToken(int i) {
        checkSystemPermission();
        return getResetTokenViaProtector(i);
    }

    @SystemApi
    public byte[] getResetTokenSafe(int i) {
        checkSystemPermission();
        return getManagedToken(i);
    }

    public final byte[] getResetTokenViaProtector(int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return this.mKeyProtector.release("SdpResetToken", i);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @SystemApi
    public void saveResetTokenSafe(byte[] bArr, int i) {
        cacheManagedToken(bArr, i);
    }

    @SystemApi
    public long getTokenHandle(int i) {
        checkSystemPermission();
        return getTokenHandleViaProtector(i);
    }

    public final long getTokenHandleViaProtector(int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            byte[] release = this.mKeyProtector.release("SdpTokenHandle", i);
            return release != null ? BytesUtil.bytesToLong(release) : 0L;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean saveTokenHandleViaProtector(long j, int i) {
        return SecureUtil.record(this.mKeyProtector.protect(BytesUtil.longToBytes(j), "SdpTokenHandle", i));
    }

    public final byte[] getSpecificKeyViaProtector(String str, int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return this.mKeyProtector.release(str, i);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean saveSpecificKeyViaProtector(byte[] bArr, String str, int i) {
        boolean z;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (!SecureUtil.isAnyoneEmptyHere(bArr, str)) {
                if (SecureUtil.record(this.mKeyProtector.protect(bArr, str, i))) {
                    z = true;
                    return z;
                }
            }
            z = false;
            return z;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean removeSpecificKeyViaProtector(String str, int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return SecureUtil.record(this.mKeyProtector.delete(str, i));
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void cacheSafe(Map map, String str, byte[] bArr, int i) {
        byte[] sessionKeyViaProtector;
        if (map == null || bArr == null) {
            return;
        }
        synchronized (map) {
            sessionKeyViaProtector = getSessionKeyViaProtector(i);
            if (SecureUtil.isEmpty(sessionKeyViaProtector)) {
                SDPLog.d("cache - Session key not found for user " + i);
            } else {
                byte[] encryptFast = this.mKeyProtector.encryptFast(sessionKeyViaProtector, bArr);
                if (SecureUtil.isEmpty(encryptFast)) {
                    SDPLog.d("cache - Fast encryption failed with user " + i);
                } else {
                    map.put(Integer.valueOf(i), encryptFast);
                    SDPLog.d(String.format("cache - Now %s is under secure management for user %d", str, Integer.valueOf(i)));
                }
            }
        }
        SecureUtil.clear(sessionKeyViaProtector);
    }

    public final void clearCached(Map map, String str, int i) {
        if (map == null) {
            return;
        }
        synchronized (map) {
            if (map.containsKey(Integer.valueOf(i))) {
                SecureUtil.clear((byte[]) map.get(Integer.valueOf(i)));
                map.remove(Integer.valueOf(i));
                SDPLog.d(String.format("clear - Managed %s removed for user %d", str, Integer.valueOf(i)));
            } else {
                SDPLog.d(String.format("clear - Managed %s not found for user %d", str, Integer.valueOf(i)));
            }
        }
    }

    public final byte[] getCached(Map map, String str, int i) {
        byte[] bArr;
        byte[] bArr2;
        synchronized (map) {
            bArr = null;
            if (!map.containsKey(Integer.valueOf(i))) {
                SDPLog.d(String.format("get - Managed %s not found for user %d", str, Integer.valueOf(i)));
                bArr2 = null;
            } else {
                byte[] sessionKeyViaProtector = getSessionKeyViaProtector(i);
                if (SecureUtil.isEmpty(sessionKeyViaProtector)) {
                    SDPLog.d("get - Session key not found for user " + i);
                } else {
                    byte[] bArr3 = (byte[]) map.get(Integer.valueOf(i));
                    if (SecureUtil.isEmpty(bArr3)) {
                        SDPLog.d(String.format("get - Empty managed %s found for user %d", str, Integer.valueOf(i)));
                    } else {
                        bArr = this.mKeyProtector.decryptFast(sessionKeyViaProtector, bArr3);
                        if (SecureUtil.isEmpty(bArr)) {
                            SDPLog.d("get - Fast decryption failed with user " + i);
                        } else {
                            SDPLog.d(String.format("get - Managed %s given for user %d", str, Integer.valueOf(i)));
                        }
                    }
                }
                bArr2 = bArr;
                bArr = sessionKeyViaProtector;
            }
        }
        SecureUtil.clear(bArr);
        return bArr2;
    }

    public final void cacheManagedCredential(byte[] bArr, int i) {
        cacheSafe(this.mManagedCredentialMap, "credential", bArr, i);
    }

    public final void clearManagedCredential(int i) {
        clearCached(this.mManagedCredentialMap, "credential", i);
    }

    public final byte[] getManagedCredential(int i) {
        return getCached(this.mManagedCredentialMap, "credential", i);
    }

    public final void cacheManagedToken(byte[] bArr, int i) {
        cacheSafe(this.mManagedTokenMap, KnoxCustomManagerService.SPCM_KEY_TOKEN, bArr, i);
    }

    public final void clearManagedToken(int i) {
        clearCached(this.mManagedTokenMap, KnoxCustomManagerService.SPCM_KEY_TOKEN, i);
    }

    public final byte[] getManagedToken(int i) {
        return getCached(this.mManagedTokenMap, KnoxCustomManagerService.SPCM_KEY_TOKEN, i);
    }

    /* loaded from: classes2.dex */
    public final class VirtualLockClient {
        public SdpManagerImpl mSdpManager;

        public VirtualLockClient(SdpManagerImpl sdpManagerImpl) {
            this.mSdpManager = sdpManagerImpl;
        }

        public boolean prepare(int i) {
            if (!SdpManagerImpl.this.isVirtualUserId(i)) {
                return false;
            }
            File userSystemDir = FileUtil.getUserSystemDir(i);
            if (!userSystemDir.exists() && !userSystemDir.mkdir()) {
                Log.e("SdpManagerImpl.VirtualLock", "prepare - failed to create sp state path for user " + i);
                return false;
            }
            FileUtils.setPermissions(userSystemDir.getPath(), 505, -1, -1);
            SdpManagerImpl.this.initInternalEngineInfo(i);
            return true;
        }

        public void clean(int i) {
            if (SdpManagerImpl.this.isVirtualUserId(i)) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                File userSystemDir = FileUtil.getUserSystemDir(i);
                if (userSystemDir.exists()) {
                    Log.d("SdpManagerImpl.VirtualLock", "clean - sp state path found with user " + i);
                    SdpManagerImpl.this.removeDirectoryRecursive(userSystemDir);
                } else {
                    Log.e("SdpManagerImpl.VirtualLock", "clean - sp state path not found with user " + i);
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public long establish(String str, byte[] bArr, int i) {
            if (!SdpManagerImpl.this.isVirtualUserId(i)) {
                return 0L;
            }
            clearLock(i);
            if (prepare(i)) {
                long resetPasswordToken = setResetPasswordToken(bArr, i);
                if (setPasswordWithToken(str, resetPasswordToken, bArr, i)) {
                    Log.d("SdpManagerImpl.VirtualLock", String.format("establish - sp based credential established for user %d with %d", Integer.valueOf(i), Long.valueOf(resetPasswordToken)));
                    return resetPasswordToken;
                }
            }
            return 0L;
        }

        public VerifyCredentialResponse checkPassword(String str, int i) {
            Log.d("SdpManagerImpl.VirtualLock", "Check password for user " + i);
            if (!SdpManagerImpl.this.isVirtualUserId(i)) {
                return VerifyCredentialResponse.ERROR;
            }
            return this.mSdpManager.checkCredential(str, 4, i);
        }

        public void setPassword(final String str, final String str2, final int i) {
            Log.d("SdpManagerImpl.VirtualLock", "Set password for user " + i);
            if (SdpManagerImpl.this.isVirtualUserId(i)) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    SdpManagerImpl.this.getLockSettings().ifPresent(new Consumer() { // from class: com.android.server.knox.dar.sdp.SdpManagerImpl$VirtualLockClient$$ExternalSyntheticLambda2
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            SdpManagerImpl.VirtualLockClient.lambda$setPassword$0(str, str2, i, (ILockSettings) obj);
                        }
                    });
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        public static /* synthetic */ void lambda$setPassword$0(String str, String str2, int i, ILockSettings iLockSettings) {
            try {
                iLockSettings.setLockCredential(LockscreenCredential.createPassword(str), LockscreenCredential.createPassword(str2), i);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        public VerifyCredentialResponse changePassword(String str, String str2, int i) {
            Log.d("SdpManagerImpl.VirtualLock", "Change password for user " + i);
            if (!SdpManagerImpl.this.isVirtualUserId(i)) {
                return VerifyCredentialResponse.ERROR;
            }
            setPassword(str, str2, i);
            return checkPassword(str, i);
        }

        public void clearLock(final int i) {
            Log.d("SdpManagerImpl.VirtualLock", "Clear lock for user " + i);
            if (SdpManagerImpl.this.isVirtualUserId(i)) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    SdpManagerImpl.this.getLockSettings().ifPresent(new Consumer() { // from class: com.android.server.knox.dar.sdp.SdpManagerImpl$VirtualLockClient$$ExternalSyntheticLambda1
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            SdpManagerImpl.VirtualLockClient.lambda$clearLock$1(i, (ILockSettings) obj);
                        }
                    });
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        public static /* synthetic */ void lambda$clearLock$1(int i, ILockSettings iLockSettings) {
            try {
                iLockSettings.setLockCredential(LockscreenCredential.createNone(), LockscreenCredential.createNone(), i);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        public long setResetPasswordToken(byte[] bArr, int i) {
            Log.d("SdpManagerImpl.VirtualLock", "Set reset password token for user " + i);
            if (!SdpManagerImpl.this.isVirtualUserId(i)) {
                return 0L;
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return SdpManagerImpl.this.mLockPatternUtils.addEscrowToken(bArr, i, (LockPatternUtils.EscrowTokenStateChangeCallback) null);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public boolean setPasswordWithToken(final String str, final long j, final byte[] bArr, final int i) {
            Log.d("SdpManagerImpl.VirtualLock", "Set password with token for user " + i);
            if (!SdpManagerImpl.this.isVirtualUserId(i)) {
                return false;
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return ((Boolean) SdpManagerImpl.this.getLockSettingsInternal().map(new Function() { // from class: com.android.server.knox.dar.sdp.SdpManagerImpl$VirtualLockClient$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        Boolean lambda$setPasswordWithToken$2;
                        lambda$setPasswordWithToken$2 = SdpManagerImpl.VirtualLockClient.lambda$setPasswordWithToken$2(str, j, bArr, i, (LockSettingsInternal) obj);
                        return lambda$setPasswordWithToken$2;
                    }
                }).orElse(Boolean.FALSE)).booleanValue();
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public static /* synthetic */ Boolean lambda$setPasswordWithToken$2(String str, long j, byte[] bArr, int i, LockSettingsInternal lockSettingsInternal) {
            return Boolean.valueOf(lockSettingsInternal.setLockCredentialWithToken(LockscreenCredential.createPassword(str), j, bArr, i));
        }

        public VerifyCredentialResponse verifyToken(byte[] bArr, long j, int i) {
            Log.d("SdpManagerImpl.VirtualLock", "Verify token for user " + i);
            if (!SdpManagerImpl.this.isVirtualUserId(i)) {
                return VerifyCredentialResponse.ERROR;
            }
            return this.mSdpManager.verifyToken(bArr, j, i);
        }
    }

    /* loaded from: classes2.dex */
    public class SdpEngineDatabase {
        public EngineInfoXmlHandler mEngineInfoXmlHandler;
        public EngineListHandler mEngineListXMLHandler;

        public /* synthetic */ SdpEngineDatabase(SdpManagerImpl sdpManagerImpl, SdpEngineDatabaseIA sdpEngineDatabaseIA) {
            this();
        }

        public SdpEngineDatabase() {
            this.mEngineListXMLHandler = null;
            this.mEngineInfoXmlHandler = null;
            this.mEngineListXMLHandler = new EngineListHandler();
            this.mEngineInfoXmlHandler = new EngineInfoXmlHandler();
        }

        public final int updateEngineListLocked() {
            return this.mEngineListXMLHandler.updateEngineListLocked();
        }

        public final int storeEngineInfoLocked(SdpEngineInfo sdpEngineInfo) {
            return this.mEngineInfoXmlHandler.updateEngineInfoLocked(sdpEngineInfo);
        }

        public final void removeEngineInfoLocked(SdpEngineInfo sdpEngineInfo) {
            this.mEngineInfoXmlHandler.removeEngineInfoLocked(sdpEngineInfo);
        }

        public final SparseArray getEngineListLocked() {
            SparseArray engineListLocked = this.mEngineListXMLHandler.getEngineListLocked();
            if (engineListLocked == null) {
                return null;
            }
            if (engineListLocked.size() != 0) {
                return engineListLocked;
            }
            SDPLog.d("SdpManagerImpl", "getEngineListLocked :: no engine found");
            return null;
        }

        public final SdpEngineInfo getEngineInfoLocked(int i) {
            return this.mEngineInfoXmlHandler.getEngineInfoLocked(i);
        }

        /* loaded from: classes2.dex */
        public class EngineListHandler {
            public /* synthetic */ EngineListHandler(SdpEngineDatabase sdpEngineDatabase, EngineListHandlerIA engineListHandlerIA) {
                this();
            }

            public EngineListHandler() {
            }

            public final AtomicFile getEngineListXmlFile() {
                return getEngineListXmlFile(0);
            }

            public final AtomicFile getEngineListXmlFile(int i) {
                if (i != 1) {
                    i = 0;
                }
                File file = new File("/data/system/users/sdp_engine_list.xml");
                if (!file.exists() && i == 0) {
                    Log.e("SdpManagerImpl", "Failed to get engine list due to non-existence...");
                    throw new IOException();
                }
                return new AtomicFile(file);
            }

            /* JADX WARN: Removed duplicated region for block: B:25:0x00b5  */
            /* JADX WARN: Removed duplicated region for block: B:27:? A[RETURN, SYNTHETIC] */
            /* JADX WARN: Removed duplicated region for block: B:46:0x00bb A[EXC_TOP_SPLITTER, SYNTHETIC] */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final int updateEngineListLocked() {
                /*
                    r15 = this;
                    java.lang.String r0 = "engine"
                    java.lang.String r1 = "engine_list"
                    r2 = -99
                    r3 = 1
                    android.util.AtomicFile r4 = r15.getEngineListXmlFile(r3)     // Catch: java.io.IOException -> Lbf
                    com.android.server.knox.dar.sdp.SdpManagerImpl$SdpEngineDatabase r5 = com.android.server.knox.dar.sdp.SdpManagerImpl.SdpEngineDatabase.this
                    com.android.server.knox.dar.sdp.SdpManagerImpl r5 = com.android.server.knox.dar.sdp.SdpManagerImpl.this
                    android.util.SparseArray r5 = com.android.server.knox.dar.sdp.SdpManagerImpl.m7644$$Nest$fgetmSdpEngineMap(r5)
                    int r5 = r5.size()
                    r6 = 0
                    if (r5 != 0) goto L1e
                    r4.delete()
                    return r6
                L1e:
                    r5 = 0
                    java.io.FileOutputStream r7 = r4.startWrite()     // Catch: java.lang.Throwable -> L96 java.lang.Exception -> L98
                    java.io.BufferedOutputStream r8 = new java.io.BufferedOutputStream     // Catch: java.lang.Exception -> L92 java.lang.Throwable -> L96
                    r8.<init>(r7)     // Catch: java.lang.Exception -> L92 java.lang.Throwable -> L96
                    com.android.internal.util.FastXmlSerializer r9 = new com.android.internal.util.FastXmlSerializer     // Catch: java.lang.Exception -> L90 java.lang.Throwable -> Lb7
                    r9.<init>()     // Catch: java.lang.Exception -> L90 java.lang.Throwable -> Lb7
                    java.lang.String r10 = "UTF-8"
                    r9.setOutput(r8, r10)     // Catch: java.lang.Exception -> L90 java.lang.Throwable -> Lb7
                    java.lang.Boolean r10 = java.lang.Boolean.TRUE     // Catch: java.lang.Exception -> L90 java.lang.Throwable -> Lb7
                    r9.startDocument(r5, r10)     // Catch: java.lang.Exception -> L90 java.lang.Throwable -> Lb7
                    java.lang.String r10 = "http://xmlpull.org/v1/doc/features.html#indent-output"
                    r9.setFeature(r10, r3)     // Catch: java.lang.Exception -> L90 java.lang.Throwable -> Lb7
                    r9.startTag(r5, r1)     // Catch: java.lang.Exception -> L90 java.lang.Throwable -> Lb7
                    com.android.server.knox.dar.sdp.SdpManagerImpl$SdpEngineDatabase r10 = com.android.server.knox.dar.sdp.SdpManagerImpl.SdpEngineDatabase.this     // Catch: java.lang.Exception -> L90 java.lang.Throwable -> Lb7
                    com.android.server.knox.dar.sdp.SdpManagerImpl r10 = com.android.server.knox.dar.sdp.SdpManagerImpl.this     // Catch: java.lang.Exception -> L90 java.lang.Throwable -> Lb7
                    android.util.SparseArray r10 = com.android.server.knox.dar.sdp.SdpManagerImpl.m7644$$Nest$fgetmSdpEngineMap(r10)     // Catch: java.lang.Exception -> L90 java.lang.Throwable -> Lb7
                    int r10 = r10.size()     // Catch: java.lang.Exception -> L90 java.lang.Throwable -> Lb7
                    r11 = r6
                L4c:
                    if (r11 >= r10) goto L83
                    com.android.server.knox.dar.sdp.SdpManagerImpl$SdpEngineDatabase r12 = com.android.server.knox.dar.sdp.SdpManagerImpl.SdpEngineDatabase.this     // Catch: java.lang.Exception -> L90 java.lang.Throwable -> Lb7
                    com.android.server.knox.dar.sdp.SdpManagerImpl r12 = com.android.server.knox.dar.sdp.SdpManagerImpl.this     // Catch: java.lang.Exception -> L90 java.lang.Throwable -> Lb7
                    android.util.SparseArray r12 = com.android.server.knox.dar.sdp.SdpManagerImpl.m7644$$Nest$fgetmSdpEngineMap(r12)     // Catch: java.lang.Exception -> L90 java.lang.Throwable -> Lb7
                    int r12 = r12.keyAt(r11)     // Catch: java.lang.Exception -> L90 java.lang.Throwable -> Lb7
                    com.android.server.knox.dar.sdp.SdpManagerImpl$SdpEngineDatabase r13 = com.android.server.knox.dar.sdp.SdpManagerImpl.SdpEngineDatabase.this     // Catch: java.lang.Exception -> L90 java.lang.Throwable -> Lb7
                    com.android.server.knox.dar.sdp.SdpManagerImpl r13 = com.android.server.knox.dar.sdp.SdpManagerImpl.this     // Catch: java.lang.Exception -> L90 java.lang.Throwable -> Lb7
                    android.util.SparseArray r13 = com.android.server.knox.dar.sdp.SdpManagerImpl.m7644$$Nest$fgetmSdpEngineMap(r13)     // Catch: java.lang.Exception -> L90 java.lang.Throwable -> Lb7
                    java.lang.Object r13 = r13.valueAt(r11)     // Catch: java.lang.Exception -> L90 java.lang.Throwable -> Lb7
                    com.samsung.android.knox.sdp.core.SdpEngineInfo r13 = (com.samsung.android.knox.sdp.core.SdpEngineInfo) r13     // Catch: java.lang.Exception -> L90 java.lang.Throwable -> Lb7
                    r9.startTag(r5, r0)     // Catch: java.lang.Exception -> L90 java.lang.Throwable -> Lb7
                    java.lang.String r14 = "alias"
                    java.lang.String r13 = r13.getAlias()     // Catch: java.lang.Exception -> L90 java.lang.Throwable -> Lb7
                    r9.attribute(r5, r14, r13)     // Catch: java.lang.Exception -> L90 java.lang.Throwable -> Lb7
                    java.lang.String r13 = "id"
                    java.lang.String r12 = java.lang.Integer.toString(r12)     // Catch: java.lang.Exception -> L90 java.lang.Throwable -> Lb7
                    r9.attribute(r5, r13, r12)     // Catch: java.lang.Exception -> L90 java.lang.Throwable -> Lb7
                    r9.endTag(r5, r0)     // Catch: java.lang.Exception -> L90 java.lang.Throwable -> Lb7
                    int r11 = r11 + 1
                    goto L4c
                L83:
                    r9.endTag(r5, r1)     // Catch: java.lang.Exception -> L90 java.lang.Throwable -> Lb7
                    r9.endDocument()     // Catch: java.lang.Exception -> L90 java.lang.Throwable -> Lb7
                    r4.finishWrite(r7)     // Catch: java.lang.Exception -> L90 java.lang.Throwable -> Lb7
                    r8.close()     // Catch: java.io.IOException -> Lb3
                    goto Lb3
                L90:
                    r0 = move-exception
                    goto L94
                L92:
                    r0 = move-exception
                    r8 = r5
                L94:
                    r5 = r7
                    goto L9a
                L96:
                    r15 = move-exception
                    goto Lb9
                L98:
                    r0 = move-exception
                    r8 = r5
                L9a:
                    com.android.server.knox.dar.sdp.SdpManagerImpl$SdpEngineDatabase r15 = com.android.server.knox.dar.sdp.SdpManagerImpl.SdpEngineDatabase.this     // Catch: java.lang.Throwable -> Lb7
                    com.android.server.knox.dar.sdp.SdpManagerImpl r15 = com.android.server.knox.dar.sdp.SdpManagerImpl.this     // Catch: java.lang.Throwable -> Lb7
                    java.lang.String r1 = "SdpManagerImpl"
                    java.lang.String r3 = "Failed to update engine list..."
                    com.android.server.knox.dar.sdp.SdpManagerImpl.m7671$$Nest$mrecordException(r15, r1, r0, r3)     // Catch: java.lang.Throwable -> Lb7
                    r0.printStackTrace()     // Catch: java.lang.Throwable -> Lb7
                    if (r5 == 0) goto Lad
                    r4.failWrite(r5)     // Catch: java.lang.Throwable -> Lb7
                Lad:
                    if (r8 == 0) goto Lb2
                    r8.close()     // Catch: java.io.IOException -> Lb2
                Lb2:
                    r3 = r6
                Lb3:
                    if (r3 == 0) goto Lb6
                    r2 = r6
                Lb6:
                    return r2
                Lb7:
                    r15 = move-exception
                    r5 = r8
                Lb9:
                    if (r5 == 0) goto Lbe
                    r5.close()     // Catch: java.io.IOException -> Lbe
                Lbe:
                    throw r15
                Lbf:
                    r15 = move-exception
                    r15.printStackTrace()
                    return r2
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.server.knox.dar.sdp.SdpManagerImpl.SdpEngineDatabase.EngineListHandler.updateEngineListLocked():int");
            }

            public final SparseArray getEngineListLocked() {
                int i;
                SparseArray sparseArray = new SparseArray();
                int i2 = 3;
                FileInputStream fileInputStream = null;
                while (true) {
                    int i3 = i2 - 1;
                    if (i2 > 0) {
                        sparseArray.clear();
                        try {
                            fileInputStream = getEngineListXmlFile().openRead();
                            XmlPullParser newPullParser = XmlPullParserFactory.newInstance().newPullParser();
                            newPullParser.setInput(fileInputStream, "UTF-8");
                            String str = "";
                            boolean z = true;
                            for (int eventType = newPullParser.getEventType(); eventType != 1; eventType = newPullParser.next()) {
                                if (eventType == 2 && "engine".equalsIgnoreCase(newPullParser.getName())) {
                                    boolean z2 = false;
                                    if ("alias".equals(newPullParser.getAttributeName(0)) && "id".equals(newPullParser.getAttributeName(1))) {
                                        str = newPullParser.getAttributeValue(0);
                                        i = Integer.valueOf(newPullParser.getAttributeValue(1)).intValue();
                                        if (i >= 0 && !str.isEmpty()) {
                                            z2 = true;
                                        }
                                        z = z2;
                                    } else {
                                        i = -1;
                                    }
                                    if (z) {
                                        sparseArray.append(i, str);
                                        str = "";
                                    } else {
                                        throw new Exception("Suspicious of damaged file...");
                                        break;
                                    }
                                }
                            }
                            if (fileInputStream != null) {
                                try {
                                    fileInputStream.close();
                                } catch (IOException unused) {
                                }
                            }
                            return sparseArray;
                        } catch (Exception e) {
                            try {
                                if (e.getMessage() != null) {
                                    Log.e("SdpManagerImpl", e.getMessage());
                                }
                                if (fileInputStream != null) {
                                    try {
                                        fileInputStream.close();
                                    } catch (IOException unused2) {
                                    }
                                }
                                i2 = i3;
                            } catch (Throwable th) {
                                if (fileInputStream != null) {
                                    try {
                                        fileInputStream.close();
                                    } catch (IOException unused3) {
                                    }
                                }
                                throw th;
                            }
                        }
                    } else {
                        Log.d("SdpManagerImpl", "getEngineList :: No engine found");
                        return null;
                    }
                }
            }
        }

        /* loaded from: classes2.dex */
        public class EngineInfoXmlHandler {
            public /* synthetic */ EngineInfoXmlHandler(SdpEngineDatabase sdpEngineDatabase, EngineInfoXmlHandlerIA engineInfoXmlHandlerIA) {
                this();
            }

            public EngineInfoXmlHandler() {
            }

            public final AtomicFile getEngineInfoXmlFile(int i) {
                String str = "SdpUser" + i + ".xml";
                File file = new File("/data/system/users/" + i + "/");
                if (!file.exists() && !file.mkdir()) {
                    SDPLog.d("SdpManagerImpl", "Can't make directory - " + file.getAbsolutePath());
                    throw new IOException();
                }
                return new AtomicFile(new File("/data/system/users/" + i + "/" + str));
            }

            /* JADX WARN: Not initialized variable reg: 6, insn: 0x00bb: MOVE (r4 I:??[OBJECT, ARRAY]) = (r6 I:??[OBJECT, ARRAY]), block:B:47:0x00bb */
            /* JADX WARN: Removed duplicated region for block: B:17:0x00b8  */
            /* JADX WARN: Removed duplicated region for block: B:20:? A[RETURN, SYNTHETIC] */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final int updateEngineInfoLocked(com.samsung.android.knox.sdp.core.SdpEngineInfo r12) {
                /*
                    r11 = this;
                    java.lang.String r0 = "user"
                    int r1 = r12.getId()
                    r2 = -99
                    android.util.AtomicFile r1 = r11.getEngineInfoXmlFile(r1)     // Catch: java.io.IOException -> Lc2
                    r3 = 0
                    r4 = 0
                    java.io.FileOutputStream r5 = r1.startWrite()     // Catch: java.lang.Throwable -> L99 java.lang.Exception -> L9b
                    java.io.BufferedOutputStream r6 = new java.io.BufferedOutputStream     // Catch: java.lang.Exception -> L95 java.lang.Throwable -> L99
                    r6.<init>(r5)     // Catch: java.lang.Exception -> L95 java.lang.Throwable -> L99
                    com.android.internal.util.FastXmlSerializer r7 = new com.android.internal.util.FastXmlSerializer     // Catch: java.lang.Exception -> L93 java.lang.Throwable -> Lba
                    r7.<init>()     // Catch: java.lang.Exception -> L93 java.lang.Throwable -> Lba
                    java.lang.String r8 = "UTF-8"
                    r7.setOutput(r6, r8)     // Catch: java.lang.Exception -> L93 java.lang.Throwable -> Lba
                    java.lang.Boolean r8 = java.lang.Boolean.TRUE     // Catch: java.lang.Exception -> L93 java.lang.Throwable -> Lba
                    r7.startDocument(r4, r8)     // Catch: java.lang.Exception -> L93 java.lang.Throwable -> Lba
                    java.lang.String r8 = "http://xmlpull.org/v1/doc/features.html#indent-output"
                    r9 = 1
                    r7.setFeature(r8, r9)     // Catch: java.lang.Exception -> L93 java.lang.Throwable -> Lba
                    r7.startTag(r4, r0)     // Catch: java.lang.Exception -> L93 java.lang.Throwable -> Lba
                    java.lang.String r8 = "alias"
                    java.lang.String r10 = r12.getAlias()     // Catch: java.lang.Exception -> L93 java.lang.Throwable -> Lba
                    r7.attribute(r4, r8, r10)     // Catch: java.lang.Exception -> L93 java.lang.Throwable -> Lba
                    java.lang.String r8 = "pkgName"
                    java.lang.String r10 = r12.getPackageName()     // Catch: java.lang.Exception -> L93 java.lang.Throwable -> Lba
                    r7.attribute(r4, r8, r10)     // Catch: java.lang.Exception -> L93 java.lang.Throwable -> Lba
                    java.lang.String r8 = "id"
                    int r10 = r12.getId()     // Catch: java.lang.Exception -> L93 java.lang.Throwable -> Lba
                    java.lang.String r10 = java.lang.Integer.toString(r10)     // Catch: java.lang.Exception -> L93 java.lang.Throwable -> Lba
                    r7.attribute(r4, r8, r10)     // Catch: java.lang.Exception -> L93 java.lang.Throwable -> Lba
                    java.lang.String r8 = "userid"
                    int r10 = r12.getUserId()     // Catch: java.lang.Exception -> L93 java.lang.Throwable -> Lba
                    java.lang.String r10 = java.lang.Integer.toString(r10)     // Catch: java.lang.Exception -> L93 java.lang.Throwable -> Lba
                    r7.attribute(r4, r8, r10)     // Catch: java.lang.Exception -> L93 java.lang.Throwable -> Lba
                    java.lang.String r8 = "flags"
                    int r10 = r12.getFlag()     // Catch: java.lang.Exception -> L93 java.lang.Throwable -> Lba
                    java.lang.String r10 = java.lang.Integer.toString(r10)     // Catch: java.lang.Exception -> L93 java.lang.Throwable -> Lba
                    r7.attribute(r4, r8, r10)     // Catch: java.lang.Exception -> L93 java.lang.Throwable -> Lba
                    java.lang.String r8 = "version"
                    int r10 = r12.getVersion()     // Catch: java.lang.Exception -> L93 java.lang.Throwable -> Lba
                    java.lang.String r10 = java.lang.Integer.toString(r10)     // Catch: java.lang.Exception -> L93 java.lang.Throwable -> Lba
                    r7.attribute(r4, r8, r10)     // Catch: java.lang.Exception -> L93 java.lang.Throwable -> Lba
                    java.lang.String r8 = "isMigrating"
                    boolean r12 = r12.isMigrating()     // Catch: java.lang.Exception -> L93 java.lang.Throwable -> Lba
                    java.lang.String r12 = java.lang.Boolean.toString(r12)     // Catch: java.lang.Exception -> L93 java.lang.Throwable -> Lba
                    r7.attribute(r4, r8, r12)     // Catch: java.lang.Exception -> L93 java.lang.Throwable -> Lba
                    r7.endTag(r4, r0)     // Catch: java.lang.Exception -> L93 java.lang.Throwable -> Lba
                    r7.endDocument()     // Catch: java.lang.Exception -> L93 java.lang.Throwable -> Lba
                    r1.finishWrite(r5)     // Catch: java.lang.Exception -> L93 java.lang.Throwable -> Lba
                    r6.close()     // Catch: java.io.IOException -> Lb6
                    goto Lb6
                L93:
                    r12 = move-exception
                    goto L97
                L95:
                    r12 = move-exception
                    r6 = r4
                L97:
                    r4 = r5
                    goto L9d
                L99:
                    r11 = move-exception
                    goto Lbc
                L9b:
                    r12 = move-exception
                    r6 = r4
                L9d:
                    com.android.server.knox.dar.sdp.SdpManagerImpl$SdpEngineDatabase r11 = com.android.server.knox.dar.sdp.SdpManagerImpl.SdpEngineDatabase.this     // Catch: java.lang.Throwable -> Lba
                    com.android.server.knox.dar.sdp.SdpManagerImpl r11 = com.android.server.knox.dar.sdp.SdpManagerImpl.this     // Catch: java.lang.Throwable -> Lba
                    java.lang.String r0 = "SdpManagerImpl"
                    java.lang.String r5 = "Failed to update engine info..."
                    com.android.server.knox.dar.sdp.SdpManagerImpl.m7671$$Nest$mrecordException(r11, r0, r12, r5)     // Catch: java.lang.Throwable -> Lba
                    r12.printStackTrace()     // Catch: java.lang.Throwable -> Lba
                    if (r4 == 0) goto Lb0
                    r1.failWrite(r4)     // Catch: java.lang.Throwable -> Lba
                Lb0:
                    if (r6 == 0) goto Lb5
                    r6.close()     // Catch: java.io.IOException -> Lb5
                Lb5:
                    r9 = r3
                Lb6:
                    if (r9 == 0) goto Lb9
                    r2 = r3
                Lb9:
                    return r2
                Lba:
                    r11 = move-exception
                    r4 = r6
                Lbc:
                    if (r4 == 0) goto Lc1
                    r4.close()     // Catch: java.io.IOException -> Lc1
                Lc1:
                    throw r11
                Lc2:
                    r11 = move-exception
                    r11.printStackTrace()
                    return r2
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.server.knox.dar.sdp.SdpManagerImpl.SdpEngineDatabase.EngineInfoXmlHandler.updateEngineInfoLocked(com.samsung.android.knox.sdp.core.SdpEngineInfo):int");
            }

            /* JADX WARN: Removed duplicated region for block: B:19:0x0158  */
            /* JADX WARN: Removed duplicated region for block: B:21:0x015b A[EXC_TOP_SPLITTER, SYNTHETIC] */
            /* JADX WARN: Removed duplicated region for block: B:27:0x015e A[SYNTHETIC] */
            /* JADX WARN: Removed duplicated region for block: B:59:0x0102 A[Catch: Exception -> 0x012e, all -> 0x0136, TRY_LEAVE, TryCatch #0 {Exception -> 0x012e, blocks: (B:13:0x0127, B:50:0x00b6, B:59:0x0102, B:67:0x011f, B:68:0x0126), top: B:12:0x0127 }] */
            /* JADX WARN: Removed duplicated region for block: B:66:0x011f A[SYNTHETIC] */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final com.samsung.android.knox.sdp.core.SdpEngineInfo getEngineInfoLocked(int r26) {
                /*
                    Method dump skipped, instructions count: 361
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.server.knox.dar.sdp.SdpManagerImpl.SdpEngineDatabase.EngineInfoXmlHandler.getEngineInfoLocked(int):com.samsung.android.knox.sdp.core.SdpEngineInfo");
            }

            public final void removeEngineInfoLocked(SdpEngineInfo sdpEngineInfo) {
                try {
                    getEngineInfoXmlFile(sdpEngineInfo.getId()).delete();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("SdpManagerImpl", "can't remove engine info file " + sdpEngineInfo.getAlias());
                }
            }
        }
    }

    /* loaded from: classes2.dex */
    public class SecureFileSystemManager {
        public Context mContext;

        public /* synthetic */ SecureFileSystemManager(SdpManagerImpl sdpManagerImpl, Context context, SecureFileSystemManagerIA secureFileSystemManagerIA) {
            this(context);
        }

        public SecureFileSystemManager(Context context) {
            this.mContext = context;
        }

        public final boolean removePkgDir(final int i, final String str) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            boolean z = !SecureUtil.isFailed(SdpManagerImpl.this.getPackageManagerImpl().map(new Function() { // from class: com.android.server.knox.dar.sdp.SdpManagerImpl$SecureFileSystemManager$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    Boolean lambda$removePkgDir$1;
                    lambda$removePkgDir$1 = SdpManagerImpl.SecureFileSystemManager.lambda$removePkgDir$1(i, str, (PackageManagerService.IPackageManagerImpl) obj);
                    return lambda$removePkgDir$1;
                }
            }).orElse(Boolean.FALSE));
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return z;
        }

        public static /* synthetic */ Boolean lambda$removePkgDir$1(int i, String str, PackageManagerService.IPackageManagerImpl iPackageManagerImpl) {
            return Boolean.valueOf(iPackageManagerImpl.removeEncPkgDir(i, str));
        }

        public final boolean setBaseDataUserDir(final int i) {
            boolean z;
            final File file = new File("/data/enc_user", Integer.toString(i));
            if (file.exists()) {
                z = true;
            } else {
                z = file.mkdir();
                if (!z) {
                    Log.e("SecureFS.SDP", "setBaseDataUserDir :: Error - user dir creation failed... " + i);
                    z = false;
                } else {
                    FileUtils.setPermissions(file.getPath(), 505, -1, -1);
                    if (((Boolean) SdpManagerImpl.this.getSdpManagerInternal().map(new Function() { // from class: com.android.server.knox.dar.sdp.SdpManagerImpl$SecureFileSystemManager$$ExternalSyntheticLambda1
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            Boolean lambda$setBaseDataUserDir$2;
                            lambda$setBaseDataUserDir$2 = SdpManagerImpl.SecureFileSystemManager.lambda$setBaseDataUserDir$2(i, file, (SdpManagerInternal) obj);
                            return lambda$setBaseDataUserDir$2;
                        }
                    }).orElse(Boolean.FALSE)).booleanValue()) {
                        SDPLog.d("Successfully set sdp policy to " + file.getPath());
                    } else {
                        SDPLog.d("Failed to set sdp policy to " + file.getPath());
                    }
                }
            }
            Log.e("SecureFS.SDP", "setBaseDataUserDir :: User " + i + ", result : " + z);
            return z;
        }

        public static /* synthetic */ Boolean lambda$setBaseDataUserDir$2(int i, File file, SdpManagerInternal sdpManagerInternal) {
            return Boolean.valueOf(sdpManagerInternal.setSdpPolicyToPath(i, file.getPath()));
        }

        public final void secureFileSystemManagerReady() {
            Iterator it = UserManager.get(this.mContext).getUsers().iterator();
            while (it.hasNext()) {
                secureFileSystemManagerReady(((UserInfo) it.next()).id);
            }
        }

        public final void secureFileSystemManagerReady(int i) {
            if (i != 0 || setBaseDataUserDir(i)) {
                return;
            }
            Log.e("SecureFS.SDP", "systemReady :: Error - Failed to set up base directory for user " + i);
        }
    }

    /* loaded from: classes2.dex */
    public class SdpHandler extends Handler {
        public SdpHandler(Looper looper) {
            super(looper);
            SdpManagerImpl.this.checkCallerPermissionFor("SdpHandler");
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            SdpManagerImpl.this.checkCallerPermissionFor("SdpHandler");
            switch (message.what) {
                case 1:
                    if (SdpManagerImpl.this.isSupportedDevice()) {
                        Log.d("SdpManagerImpl.Handler", "received MSG_SYSTEM_READY. ");
                        SdpManagerImpl.this.onSystemReady();
                        return;
                    }
                    return;
                case 2:
                    SdpManagerImpl.this.lockSdpIfRequired(message.arg1);
                    return;
                case 3:
                    SdpManagerImpl.this.unlockSdpIfUnsecuredOrBiometricAuthenticated(message.arg1, message.arg2);
                    return;
                case 4:
                    Bundle data = message.getData();
                    if (data != null) {
                        int i = data.getInt("userId", -1);
                        SdpManagerImpl.this.handlePackageRemoved(data.getString("pkgName", ""), i);
                        return;
                    }
                    return;
                case 5:
                    SdpManagerImpl.this.handleUserAdded(message.arg1);
                    return;
                case 6:
                    SdpManagerImpl.this.handleUserRemoved(message.arg1);
                    return;
                case 7:
                    SdpManagerImpl.this.handleStartUser(message.arg1);
                    return;
                case 8:
                case 11:
                default:
                    return;
                case 9:
                    SdpManagerImpl.this.handleCleanupUser(message.arg1);
                    return;
                case 10:
                    SdpManagerImpl.this.handleDeviceOwnerCleared();
                    return;
                case 12:
                    Object obj = message.obj;
                    if (obj == null || !(obj instanceof Integer)) {
                        return;
                    }
                    int intValue = ((Integer) obj).intValue();
                    SdpManagerImpl.this.clearLegacyResetStatus(intValue);
                    ActivityTaskManagerService activityTaskManagerService = (ActivityTaskManagerService) ServiceManager.getService("activity_task");
                    if (activityTaskManagerService == null || !SemPersonaManager.isKnoxId(intValue)) {
                        return;
                    }
                    activityTaskManagerService.mExt.removeTaskByCmpName("com.android.settings/.password.ChooseLockGeneric$InternalActivity", 0, "Legacy reset timeout");
                    return;
                case 13:
                    Object obj2 = message.obj;
                    if (obj2 instanceof Integer) {
                        int intValue2 = ((Integer) obj2).intValue();
                        ActivityTaskManagerService activityTaskManagerService2 = (ActivityTaskManagerService) ServiceManager.getService("activity_task");
                        if (activityTaskManagerService2 == null || !SemPersonaManager.isKnoxId(intValue2)) {
                            return;
                        }
                        activityTaskManagerService2.mExt.removeTaskByCmpName("com.android.settings/.password.ChooseLockGeneric$InternalActivity", intValue2, "Legacy reset password");
                        return;
                    }
                    return;
                case 14:
                    Object obj3 = message.obj;
                    if (obj3 instanceof Integer) {
                        int intValue3 = ((Integer) obj3).intValue();
                        ActivityTaskManagerService activityTaskManagerService3 = (ActivityTaskManagerService) ServiceManager.getService("activity_task");
                        if (activityTaskManagerService3 == null || !SemPersonaManager.isKnoxId(intValue3)) {
                            return;
                        }
                        activityTaskManagerService3.mExt.removeTaskByCmpName("com.android.settings/.password.ChooseLockGeneric$InternalActivity", intValue3, "Enforce change password");
                        return;
                    }
                    return;
                case 15:
                    Bundle data2 = message.getData();
                    if (data2 != null) {
                        SdpManagerImpl.this.handleSendBroadcastForStateChange(data2.getInt("userId", -1), data2.getInt("engineId", -1), data2.getInt(LauncherConfigurationInternal.KEY_STATE_BOOLEAN, -1));
                        return;
                    }
                    return;
            }
        }
    }

    public final void removeDirectoryRecursive(File file) {
        String[] list;
        if (file.isDirectory() && (list = file.list()) != null) {
            for (String str : list) {
                removeDirectoryRecursive(new File(file, str));
            }
        }
        if (file.delete()) {
            return;
        }
        Log.w("SdpManagerImpl", String.format("Failed to delete file: %s", file));
    }

    public int saveTokenIntoTrusted(String str, String str2) {
        checkCallerPermissionFor("saveTokenIntoTrusted");
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return -3;
        }
        int userId = UserHandle.getUserId(Binder.getCallingUid());
        String str3 = str + userId;
        byte[] bytes = str2.getBytes(Charset.forName("UTF-8"));
        int i = saveSpecificKeyViaProtector(bytes, str3, userId) ? 0 : -99;
        SecureUtil.clear(bytes);
        return i;
    }

    public int deleteToeknFromTrusted(String str) {
        checkCallerPermissionFor("deleteToeknFromTrusted");
        if (TextUtils.isEmpty(str)) {
            return -3;
        }
        int userId = UserHandle.getUserId(Binder.getCallingUid());
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(userId);
        return deleteTokenInternal(userId, sb.toString()) ? 0 : -99;
    }

    public final boolean deleteTokenInternal(int i, String str) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean removeSpecificKeyViaProtector = this.mKeyProtector.exists(str, i) ? removeSpecificKeyViaProtector(str, i) : false;
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return removeSpecificKeyViaProtector;
    }

    public int unlockViaTrusted(String str, String str2) {
        if (!isSupportedDevice()) {
            return -10;
        }
        checkCallerPermissionFor("unlockViaTrusted");
        synchronized (this.mSdpEngineDbLock) {
            SdpEngineInfo engineInfoLocked = getEngineInfoLocked(str2);
            if (engineInfoLocked == null) {
                Log.e("SdpManagerImpl", "unlockViaTrusted :: Can't find engine info with " + str2);
                return -5;
            }
            if (engineInfoLocked.isAndroidDefaultEngine()) {
                return -7;
            }
            if (!isEngineOwner(engineInfoLocked) && !isPrivileged(engineInfoLocked) && !isSystemComponent(engineInfoLocked)) {
                Log.e("SdpManagerImpl", "unlockViaTrusted :: Permission denied to invoke engine control API");
                return -7;
            }
            int userId = UserHandle.getUserId(Binder.getCallingUid());
            return unlockViaTrustedInternal(str + userId, userId, engineInfoLocked);
        }
    }

    public final int unlockViaTrustedInternal(String str, int i, SdpEngineInfo sdpEngineInfo) {
        VerifyCredentialResponse verifyToken;
        int i2;
        if (sdpEngineInfo == null) {
            return -5;
        }
        int id = sdpEngineInfo.getId();
        byte[] specificKeyViaProtector = getSpecificKeyViaProtector(str, i);
        if (SecureUtil.isEmpty(specificKeyViaProtector)) {
            Log.e("SdpManagerImpl", "unlockViaTrusted :: Failed to get token for user " + id);
            return -2;
        }
        long tokenHandleViaProtector = getTokenHandleViaProtector(id);
        if (tokenHandleViaProtector == 0) {
            Log.e("SdpManagerImpl", "unlockViaTrusted :: Failed to get token handle for user" + id);
            verifyToken = VerifyCredentialResponse.ERROR;
        } else {
            verifyToken = this.mVirtualLock.verifyToken(specificKeyViaProtector, tokenHandleViaProtector, id);
        }
        if (verifyToken.isMatched()) {
            byte[] secret = verifyToken.getSecret();
            if (SecureUtil.isFailed(Integer.valueOf(unlockNative(id, secret)))) {
                Log.e("SdpManagerImpl", "unlockViaTrusted :: Failed in native unlock with user " + id);
            }
            synchronized (this.mSdpEngineDbLock) {
                setEngineStateLocked(sdpEngineInfo, 2);
            }
            SecureUtil.clear(secret);
            i2 = 0;
        } else {
            i2 = -99;
        }
        Log.d("SdpManagerImpl", String.format("unlockViaTrusted :: Result of virtual user %d verification : %s", Integer.valueOf(id), verifyToken.toString()));
        return i2;
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.DUMP") != 0) {
            printWriter.println("Permission Denial: Can't dump SDP from pid=" + Binder.getCallingPid() + ", uid=" + Binder.getCallingUid() + " without permission android.permission.DUMP");
            return;
        }
        if (isSupportedDevice()) {
            synchronized (this.mSdpEngineDbLock) {
                printWriter.println("SDP Engine List :");
                if (this.mSdpEngineMap.size() <= 0) {
                    printWriter.println("EMPTY");
                } else {
                    int size = this.mSdpEngineMap.size();
                    for (int i = 0; i < size; i++) {
                        SdpEngineInfo sdpEngineInfo = (SdpEngineInfo) this.mSdpEngineMap.valueAt(i);
                        if (sdpEngineInfo != null) {
                            Object[] objArr = new Object[7];
                            objArr[0] = Integer.valueOf(sdpEngineInfo.getId());
                            objArr[1] = Integer.valueOf(sdpEngineInfo.getUserId());
                            objArr[2] = Integer.valueOf(sdpEngineInfo.getVersion());
                            objArr[3] = sdpEngineInfo.getState() == 2 ? "UNLOCKED" : "LOCKED";
                            objArr[4] = sdpEngineInfo.isMinor() ? "SDP_MINOR" : "SDP_MDFPP";
                            objArr[5] = Integer.valueOf(sdpEngineInfo.getFlag());
                            objArr[6] = sdpEngineInfo.getAlias();
                            printWriter.print(String.format("Engine Id : %5d   User ID : %5d   Version : %d   State : %s   Flag : %10s ( %d )   Alias : %s", objArr));
                            printWriter.println();
                        }
                    }
                    printWriter.println();
                }
            }
        } else {
            printWriter.println("Not Supported...");
        }
        printWriter.flush();
    }

    public final void handleStartUser(int i) {
        if (DarUtil.isEnterpriseUser(getUserInfo(i))) {
            Log.d("SdpManagerImpl", "Start user : " + i);
        }
    }

    public final void handleCleanupUser(int i) {
        cancelLegacyResetTimeout(i);
        clearLegacyResetStatus(i);
    }

    public void onStartUser(int i) {
        Log.d("SdpManagerImpl", "Starting user " + i);
        checkCallerPermissionFor("onStartUser");
        Log.d("SdpManagerImpl", String.format("Mark the beginning of sdp service! [Version : %s]", getDeviceVersion()));
        quickMessage(7, i);
    }

    public void onCleanupUser(int i) {
        Log.d("SdpManagerImpl", "Cleaning up user - " + i);
        checkCallerPermissionFor("onCleanupUser");
        quickMessage(9, i);
    }

    public void onLegacyResetCredentialRequested(byte[] bArr, final int i, int i2) {
        SDPLog.d("Legacy reset credential policy requested for user " + i + ", timeoutMins:" + i2);
        if (SecureUtil.isEmpty(bArr)) {
            SDPLog.d("Failed due to invalid token");
            return;
        }
        cacheManagedToken(bArr, i);
        if (!((Boolean) getUserManagerInternal().map(new Function() { // from class: com.android.server.knox.dar.sdp.SdpManagerImpl$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Boolean lambda$onLegacyResetCredentialRequested$8;
                lambda$onLegacyResetCredentialRequested$8 = SdpManagerImpl.lambda$onLegacyResetCredentialRequested$8(i, (UserManagerInternal) obj);
                return lambda$onLegacyResetCredentialRequested$8;
            }
        }).orElse(Boolean.FALSE)).booleanValue()) {
            SDPLog.d("Unexpected failure while set volatiles");
        } else {
            quickMessageDelayed(12, Integer.valueOf(i), i2 <= 0 ? 900000L : i2 * 60 * 1000);
            SDPLog.d("Ready to reset credential!");
        }
    }

    public static /* synthetic */ Boolean lambda$onLegacyResetCredentialRequested$8(int i, UserManagerInternal userManagerInternal) {
        return Boolean.valueOf(userManagerInternal.setVolatiles(i, 1));
    }

    public void clearLegacyResetStatus(final int i) {
        SDPLog.d("Clear legacy reset status for user " + i);
        if (!((Boolean) getUserManagerInternal().map(new Function() { // from class: com.android.server.knox.dar.sdp.SdpManagerImpl$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Boolean lambda$clearLegacyResetStatus$9;
                lambda$clearLegacyResetStatus$9 = SdpManagerImpl.lambda$clearLegacyResetStatus$9(i, (UserManagerInternal) obj);
                return lambda$clearLegacyResetStatus$9;
            }
        }).orElse(Boolean.FALSE)).booleanValue()) {
            SDPLog.d("Unexpected failure while clear volatiles");
        }
        clearManagedToken(i);
    }

    public static /* synthetic */ Boolean lambda$clearLegacyResetStatus$9(int i, UserManagerInternal userManagerInternal) {
        return Boolean.valueOf(userManagerInternal.clearVolatiles(i, 1));
    }

    public void cancelLegacyResetTimeout(int i) {
        if (this.mIsHandlerReady) {
            Integer valueOf = Integer.valueOf(i);
            if (this.mSdpHandler.hasMessages(12, valueOf)) {
                this.mSdpHandler.removeMessages(12, valueOf);
                SDPLog.d("Legacy reset timout canceled for user " + i);
            }
        }
    }

    public void handleLegacyResetPassword(int i) {
        if (this.mIsHandlerReady) {
            quickMessageDelayed(13, Integer.valueOf(i), 0L);
            SDPLog.d("Legacy force Reset Password for user " + i);
        }
    }

    public void handleEnforcePwdChange(int i) {
        if (this.mIsHandlerReady) {
            quickMessageDelayed(14, Integer.valueOf(i), 0L);
            SDPLog.d("Enforce change password for user " + i);
        }
    }

    public final String getDeviceVersion() {
        String str = SystemProperties.get("ro.build.PDA", "Unknown");
        return (str == null || str.indexOf(95) == -1) ? str : str.substring(0, str.indexOf(95));
    }

    public final void setMasterKeyVersion(int i, int i2) {
        this.mSdpDatabaseCache.putInt(i2, "smk_ver", i);
    }

    public final int getMasterKeyVersion(int i) {
        return this.mSdpDatabaseCache.getInt(i, "smk_ver", 0);
    }

    public final SdpManagerProxy getProxy() {
        if (this.mSdpManagerProxy == null) {
            this.mSdpManagerProxy = new SdpManagerProxy();
        }
        return this.mSdpManagerProxy;
    }

    /* loaded from: classes2.dex */
    public class SdpManagerProxy {
        public /* synthetic */ SdpManagerProxy(SdpManagerImpl sdpManagerImpl, SdpManagerProxyIA sdpManagerProxyIA) {
            this();
        }

        public SdpManagerProxy() {
        }

        public boolean setSensitive(final int i, final String str) {
            return ((Boolean) SdpManagerImpl.this.getStorageManager().map(new Function() { // from class: com.android.server.knox.dar.sdp.SdpManagerImpl$SdpManagerProxy$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    Boolean lambda$setSensitive$0;
                    lambda$setSensitive$0 = SdpManagerImpl.SdpManagerProxy.lambda$setSensitive$0(i, str, (StorageManager) obj);
                    return lambda$setSensitive$0;
                }
            }).orElse(Boolean.FALSE)).booleanValue();
        }

        public static /* synthetic */ Boolean lambda$setSensitive$0(int i, String str, StorageManager storageManager) {
            return Boolean.valueOf(storageManager.setSensitive(i, str));
        }

        public boolean isSensitive(final String str) {
            return ((Boolean) SdpManagerImpl.this.getStorageManager().map(new Function() { // from class: com.android.server.knox.dar.sdp.SdpManagerImpl$SdpManagerProxy$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    Boolean lambda$isSensitive$1;
                    lambda$isSensitive$1 = SdpManagerImpl.SdpManagerProxy.lambda$isSensitive$1(str, (StorageManager) obj);
                    return lambda$isSensitive$1;
                }
            }).orElse(Boolean.FALSE)).booleanValue();
        }

        public static /* synthetic */ Boolean lambda$isSensitive$1(String str, StorageManager storageManager) {
            return Boolean.valueOf(storageManager.isSensitive(str));
        }
    }

    /* loaded from: classes2.dex */
    public final class SdpLocalService extends SdpManagerInternal {
        public /* synthetic */ SdpLocalService(SdpManagerImpl sdpManagerImpl, SdpLocalServiceIA sdpLocalServiceIA) {
            this();
        }

        public SdpLocalService() {
        }

        @Override // com.android.server.knox.dar.sdp.SdpManagerInternal
        public boolean setSdpPolicyToPath(int i, String str) {
            if (EnterprisePartitionManager.getInstance(SdpManagerImpl.this.mContext).setSdpPolicyToPath(i, str)) {
                return true;
            }
            Log.e("SdpManagerImpl", "setSdpPolicyToPath failed!");
            return false;
        }

        @Override // com.android.server.knox.dar.sdp.SdpManagerInternal
        public int getMasterKeyVersion(int i) {
            return SdpManagerImpl.this.getMasterKeyVersion(i);
        }

        @Override // com.android.server.knox.dar.sdp.SdpManagerInternal
        public void setMasterKeyVersion(int i, int i2) {
            SdpManagerImpl.this.setMasterKeyVersion(i, i2);
        }

        @Override // com.android.server.knox.dar.sdp.SdpManagerInternal
        public boolean updateMasterKey(byte[] bArr, byte[] bArr2, int i) {
            return SdpManagerImpl.this.reWrapSdpKeys(bArr, bArr2, i);
        }
    }
}
