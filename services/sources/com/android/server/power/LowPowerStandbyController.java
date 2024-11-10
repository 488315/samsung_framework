package com.android.server.power;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.AlarmManager;
import android.app.IActivityManager;
import android.app.IForegroundServiceObserver;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.PowerManagerInternal;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.AtomicFile;
import android.util.IndentingPrintWriter;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.util.Xml;
import android.util.proto.ProtoOutputStream;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.LocalServices;
import com.android.server.PowerAllowlistInternal;
import com.android.server.net.NetworkPolicyManagerInternal;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.Supplier;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes3.dex */
public class LowPowerStandbyController {
    static final PowerManager.LowPowerStandbyPolicy DEFAULT_POLICY = new PowerManager.LowPowerStandbyPolicy("DEFAULT_POLICY", Collections.emptySet(), 1, Collections.emptySet());
    public boolean mActiveDuringMaintenance;
    public final Supplier mActivityManager;
    public ActivityManagerInternal mActivityManagerInternal;
    public AlarmManager mAlarmManager;
    public final BroadcastReceiver mBroadcastReceiver;
    public final Clock mClock;
    public final Context mContext;
    public final DeviceConfigWrapper mDeviceConfig;
    public boolean mEnableCustomPolicy;
    public boolean mEnableStandbyPorts;
    public boolean mEnabledByDefaultConfig;
    public boolean mForceActive;
    public final Handler mHandler;
    public boolean mIdleSinceNonInteractive;
    public boolean mIsActive;
    public boolean mIsDeviceIdle;
    public boolean mIsEnabled;
    public boolean mIsInteractive;
    public long mLastInteractiveTimeElapsed;
    public final LowPowerStandbyControllerInternal mLocalService;
    public final Object mLock;
    public final AlarmManager.OnAlarmListener mOnStandbyTimeoutExpired;
    public final BroadcastReceiver mPackageBroadcastReceiver;
    public final PhoneCallServiceTracker mPhoneCallServiceTracker;
    public PowerManager.LowPowerStandbyPolicy mPolicy;
    public final File mPolicyFile;
    public PowerManager mPowerManager;
    public final SettingsObserver mSettingsObserver;
    public final List mStandbyPortLocks;
    public int mStandbyTimeoutConfig;
    public boolean mSupportedConfig;
    public final TempAllowlistChangeListener mTempAllowlistChangeListener;
    public final SparseIntArray mUidAllowedReasons;
    public final BroadcastReceiver mUserReceiver;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public interface Clock {
        long elapsedRealtime();
    }

    /* loaded from: classes3.dex */
    public final class StandbyPortsLock implements IBinder.DeathRecipient {
        public final List mPorts;
        public final IBinder mToken;
        public final int mUid;

        public StandbyPortsLock(IBinder iBinder, int i, List list) {
            this.mToken = iBinder;
            this.mUid = i;
            this.mPorts = list;
        }

        public boolean linkToDeath() {
            try {
                this.mToken.linkToDeath(this, 0);
                return true;
            } catch (RemoteException unused) {
                android.util.Slog.i("LowPowerStandbyController", "StandbyPorts token already died");
                return false;
            }
        }

        public void unlinkToDeath() {
            this.mToken.unlinkToDeath(this, 0);
        }

        public IBinder getToken() {
            return this.mToken;
        }

        public int getUid() {
            return this.mUid;
        }

        public List getPorts() {
            return this.mPorts;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            LowPowerStandbyController.this.releaseStandbyPorts(this.mToken);
        }
    }

    public LowPowerStandbyController(Context context, Looper looper) {
        this(context, looper, new Clock() { // from class: com.android.server.power.LowPowerStandbyController$$ExternalSyntheticLambda3
            @Override // com.android.server.power.LowPowerStandbyController.Clock
            public final long elapsedRealtime() {
                return SystemClock.elapsedRealtime();
            }
        }, new DeviceConfigWrapper(), new Supplier() { // from class: com.android.server.power.LowPowerStandbyController$$ExternalSyntheticLambda4
            @Override // java.util.function.Supplier
            public final Object get() {
                IActivityManager lambda$new$0;
                lambda$new$0 = LowPowerStandbyController.lambda$new$0();
                return lambda$new$0;
            }
        }, new File(Environment.getDataSystemDirectory(), "low_power_standby_policy.xml"));
    }

    public static /* synthetic */ IActivityManager lambda$new$0() {
        return ActivityManager.getService();
    }

    public LowPowerStandbyController(Context context, Looper looper, Clock clock, DeviceConfigWrapper deviceConfigWrapper, Supplier supplier, File file) {
        this.mLock = new Object();
        this.mOnStandbyTimeoutExpired = new AlarmManager.OnAlarmListener() { // from class: com.android.server.power.LowPowerStandbyController$$ExternalSyntheticLambda0
            @Override // android.app.AlarmManager.OnAlarmListener
            public final void onAlarm() {
                LowPowerStandbyController.this.onStandbyTimeoutExpired();
            }
        };
        this.mLocalService = new LocalService();
        this.mUidAllowedReasons = new SparseIntArray();
        this.mStandbyPortLocks = new ArrayList();
        this.mBroadcastReceiver = new BroadcastReceiver() { // from class: com.android.server.power.LowPowerStandbyController.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                action.hashCode();
                char c = 65535;
                switch (action.hashCode()) {
                    case -2128145023:
                        if (action.equals("android.intent.action.SCREEN_OFF")) {
                            c = 0;
                            break;
                        }
                        break;
                    case -1454123155:
                        if (action.equals("android.intent.action.SCREEN_ON")) {
                            c = 1;
                            break;
                        }
                        break;
                    case 870701415:
                        if (action.equals("android.os.action.DEVICE_IDLE_MODE_CHANGED")) {
                            c = 2;
                            break;
                        }
                        break;
                }
                switch (c) {
                    case 0:
                        LowPowerStandbyController.this.onNonInteractive();
                        return;
                    case 1:
                        LowPowerStandbyController.this.onInteractive();
                        return;
                    case 2:
                        LowPowerStandbyController.this.onDeviceIdleModeChanged();
                        return;
                    default:
                        return;
                }
            }
        };
        this.mTempAllowlistChangeListener = new TempAllowlistChangeListener();
        this.mPhoneCallServiceTracker = new PhoneCallServiceTracker();
        this.mPackageBroadcastReceiver = new BroadcastReceiver() { // from class: com.android.server.power.LowPowerStandbyController.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                if (intent.getBooleanExtra("android.intent.extra.REPLACING", false)) {
                    return;
                }
                Uri data = intent.getData();
                String schemeSpecificPart = data != null ? data.getSchemeSpecificPart() : null;
                synchronized (LowPowerStandbyController.this.mLock) {
                    if (LowPowerStandbyController.this.getPolicy().getExemptPackages().contains(schemeSpecificPart)) {
                        LowPowerStandbyController.this.enqueueNotifyAllowlistChangedLocked();
                    }
                }
            }
        };
        this.mUserReceiver = new BroadcastReceiver() { // from class: com.android.server.power.LowPowerStandbyController.3
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                synchronized (LowPowerStandbyController.this.mLock) {
                    LowPowerStandbyController.this.enqueueNotifyAllowlistChangedLocked();
                }
            }
        };
        this.mContext = context;
        LowPowerStandbyHandler lowPowerStandbyHandler = new LowPowerStandbyHandler(looper);
        this.mHandler = lowPowerStandbyHandler;
        this.mClock = clock;
        this.mSettingsObserver = new SettingsObserver(lowPowerStandbyHandler);
        this.mDeviceConfig = deviceConfigWrapper;
        this.mActivityManager = supplier;
        this.mPolicyFile = file;
    }

    public void systemReady() {
        Resources resources = this.mContext.getResources();
        synchronized (this.mLock) {
            boolean z = resources.getBoolean(17891761);
            this.mSupportedConfig = z;
            if (z) {
                this.mAlarmManager = (AlarmManager) this.mContext.getSystemService(AlarmManager.class);
                this.mPowerManager = (PowerManager) this.mContext.getSystemService(PowerManager.class);
                this.mActivityManagerInternal = (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
                this.mStandbyTimeoutConfig = resources.getInteger(R.integer.config_valid_wappush_index);
                this.mEnabledByDefaultConfig = resources.getBoolean(17891760);
                this.mIsInteractive = this.mPowerManager.isInteractive();
                this.mContext.getContentResolver().registerContentObserver(Settings.Global.getUriFor("low_power_standby_enabled"), false, this.mSettingsObserver, -1);
                this.mContext.getContentResolver().registerContentObserver(Settings.Global.getUriFor("low_power_standby_active_during_maintenance"), false, this.mSettingsObserver, -1);
                this.mDeviceConfig.registerPropertyUpdateListener(this.mContext.getMainExecutor(), new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.server.power.LowPowerStandbyController$$ExternalSyntheticLambda1
                    public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                        LowPowerStandbyController.this.lambda$systemReady$1(properties);
                    }
                });
                this.mEnableCustomPolicy = this.mDeviceConfig.enableCustomPolicy();
                this.mEnableStandbyPorts = this.mDeviceConfig.enableStandbyPorts();
                if (this.mEnableCustomPolicy) {
                    this.mPolicy = loadPolicy();
                } else {
                    this.mPolicy = DEFAULT_POLICY;
                }
                initSettingsLocked();
                updateSettingsLocked();
                if (this.mIsEnabled) {
                    registerListeners();
                }
                LocalServices.addService(LowPowerStandbyControllerInternal.class, this.mLocalService);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$systemReady$1(DeviceConfig.Properties properties) {
        onDeviceConfigFlagsChanged();
    }

    public final void onDeviceConfigFlagsChanged() {
        synchronized (this.mLock) {
            boolean enableCustomPolicy = this.mDeviceConfig.enableCustomPolicy();
            if (this.mEnableCustomPolicy != enableCustomPolicy) {
                enqueueNotifyPolicyChangedLocked();
                enqueueNotifyAllowlistChangedLocked();
                this.mEnableCustomPolicy = enableCustomPolicy;
            }
            this.mEnableStandbyPorts = this.mDeviceConfig.enableStandbyPorts();
        }
    }

    public final void initSettingsLocked() {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        if (this.mSupportedConfig && Settings.Global.getInt(contentResolver, "low_power_standby_enabled", -1) == -1) {
            Settings.Global.putInt(contentResolver, "low_power_standby_enabled", this.mEnabledByDefaultConfig ? 1 : 0);
        }
    }

    public final void updateSettingsLocked() {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        this.mIsEnabled = this.mSupportedConfig && Settings.Global.getInt(contentResolver, "low_power_standby_enabled", this.mEnabledByDefaultConfig ? 1 : 0) != 0;
        this.mActiveDuringMaintenance = Settings.Global.getInt(contentResolver, "low_power_standby_active_during_maintenance", 0) != 0;
        updateActiveLocked();
    }

    public final PowerManager.LowPowerStandbyPolicy loadPolicy() {
        char c;
        AtomicFile policyFile = getPolicyFile();
        if (!policyFile.exists()) {
            return null;
        }
        try {
            try {
                FileInputStream openRead = policyFile.openRead();
                try {
                    ArraySet arraySet = new ArraySet();
                    ArraySet arraySet2 = new ArraySet();
                    TypedXmlPullParser resolvePullParser = Xml.resolvePullParser(openRead);
                    String str = null;
                    int i = 0;
                    while (true) {
                        int next = resolvePullParser.next();
                        if (next == 1) {
                            PowerManager.LowPowerStandbyPolicy lowPowerStandbyPolicy = new PowerManager.LowPowerStandbyPolicy(str, arraySet, i, arraySet2);
                            if (openRead != null) {
                                openRead.close();
                            }
                            return lowPowerStandbyPolicy;
                        }
                        if (next == 2) {
                            int depth = resolvePullParser.getDepth();
                            String name = resolvePullParser.getName();
                            if (depth != 1) {
                                switch (name.hashCode()) {
                                    case -1618432855:
                                        if (name.equals("identifier")) {
                                            c = 0;
                                            break;
                                        }
                                        break;
                                    case -764820798:
                                        if (name.equals("allowed-features")) {
                                            c = 3;
                                            break;
                                        }
                                        break;
                                    case 1342665610:
                                        if (name.equals("allowed-reasons")) {
                                            c = 2;
                                            break;
                                        }
                                        break;
                                    case 2046809176:
                                        if (name.equals("exempt-package")) {
                                            c = 1;
                                            break;
                                        }
                                        break;
                                }
                                c = 65535;
                                if (c == 0) {
                                    str = resolvePullParser.getAttributeValue((String) null, "value");
                                } else if (c == 1) {
                                    arraySet.add(resolvePullParser.getAttributeValue((String) null, "value"));
                                } else if (c == 2) {
                                    i = resolvePullParser.getAttributeInt((String) null, "value");
                                } else if (c == 3) {
                                    arraySet2.add(resolvePullParser.getAttributeValue((String) null, "value"));
                                } else {
                                    android.util.Slog.e("LowPowerStandbyController", "Invalid tag: " + name);
                                }
                            } else if (!"low-power-standby-policy".equals(name)) {
                                android.util.Slog.e("LowPowerStandbyController", "Invalid root tag: " + name);
                                if (openRead != null) {
                                    openRead.close();
                                }
                                return null;
                            }
                        }
                    }
                } catch (Throwable th) {
                    if (openRead != null) {
                        try {
                            openRead.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            } catch (FileNotFoundException unused) {
                return null;
            }
        } catch (IOException | IllegalArgumentException | NullPointerException | XmlPullParserException e) {
            android.util.Slog.e("LowPowerStandbyController", "Failed to read policy file " + policyFile.getBaseFile(), e);
            return null;
        }
    }

    public static void writeTagValue(TypedXmlSerializer typedXmlSerializer, String str, String str2) {
        if (TextUtils.isEmpty(str2)) {
            return;
        }
        typedXmlSerializer.startTag((String) null, str);
        typedXmlSerializer.attribute((String) null, "value", str2);
        typedXmlSerializer.endTag((String) null, str);
    }

    public static void writeTagValue(TypedXmlSerializer typedXmlSerializer, String str, int i) {
        typedXmlSerializer.startTag((String) null, str);
        typedXmlSerializer.attributeInt((String) null, "value", i);
        typedXmlSerializer.endTag((String) null, str);
    }

    /* renamed from: savePolicy, reason: merged with bridge method [inline-methods] */
    public final void lambda$enqueueSavePolicy$2(PowerManager.LowPowerStandbyPolicy lowPowerStandbyPolicy) {
        AtomicFile policyFile = getPolicyFile();
        if (lowPowerStandbyPolicy == null) {
            policyFile.delete();
            return;
        }
        FileOutputStream fileOutputStream = null;
        try {
            policyFile.getBaseFile().mkdirs();
            FileOutputStream startWrite = policyFile.startWrite();
            try {
                TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(startWrite);
                resolveSerializer.startDocument((String) null, Boolean.TRUE);
                resolveSerializer.startTag((String) null, "low-power-standby-policy");
                writeTagValue(resolveSerializer, "identifier", lowPowerStandbyPolicy.getIdentifier());
                Iterator it = lowPowerStandbyPolicy.getExemptPackages().iterator();
                while (it.hasNext()) {
                    writeTagValue(resolveSerializer, "exempt-package", (String) it.next());
                }
                writeTagValue(resolveSerializer, "allowed-reasons", lowPowerStandbyPolicy.getAllowedReasons());
                Iterator it2 = lowPowerStandbyPolicy.getAllowedFeatures().iterator();
                while (it2.hasNext()) {
                    writeTagValue(resolveSerializer, "allowed-features", (String) it2.next());
                }
                resolveSerializer.endTag((String) null, "low-power-standby-policy");
                resolveSerializer.endDocument();
                policyFile.finishWrite(startWrite);
            } catch (IOException e) {
                e = e;
                fileOutputStream = startWrite;
                android.util.Slog.e("LowPowerStandbyController", "Failed to write policy to file " + policyFile.getBaseFile(), e);
                policyFile.failWrite(fileOutputStream);
            }
        } catch (IOException e2) {
            e = e2;
        }
    }

    public final void enqueueSavePolicy(final PowerManager.LowPowerStandbyPolicy lowPowerStandbyPolicy) {
        this.mHandler.post(new Runnable() { // from class: com.android.server.power.LowPowerStandbyController$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                LowPowerStandbyController.this.lambda$enqueueSavePolicy$2(lowPowerStandbyPolicy);
            }
        });
    }

    public final AtomicFile getPolicyFile() {
        return new AtomicFile(this.mPolicyFile);
    }

    public final void updateActiveLocked() {
        boolean z = true;
        boolean z2 = this.mClock.elapsedRealtime() - this.mLastInteractiveTimeElapsed >= ((long) this.mStandbyTimeoutConfig);
        boolean z3 = this.mIdleSinceNonInteractive && !this.mIsDeviceIdle;
        if (!this.mForceActive && (!this.mIsEnabled || this.mIsInteractive || !z2 || (z3 && !this.mActiveDuringMaintenance))) {
            z = false;
        }
        if (this.mIsActive != z) {
            this.mIsActive = z;
            enqueueNotifyActiveChangedLocked();
        }
    }

    public final void onNonInteractive() {
        long elapsedRealtime = this.mClock.elapsedRealtime();
        synchronized (this.mLock) {
            this.mIsInteractive = false;
            this.mIsDeviceIdle = false;
            this.mLastInteractiveTimeElapsed = elapsedRealtime;
            if (this.mStandbyTimeoutConfig > 0) {
                scheduleStandbyTimeoutAlarmLocked();
            }
            updateActiveLocked();
        }
    }

    public final void onInteractive() {
        synchronized (this.mLock) {
            cancelStandbyTimeoutAlarmLocked();
            this.mIsInteractive = true;
            this.mIsDeviceIdle = false;
            this.mIdleSinceNonInteractive = false;
            updateActiveLocked();
        }
    }

    public final void scheduleStandbyTimeoutAlarmLocked() {
        this.mAlarmManager.setExact(2, SystemClock.elapsedRealtime() + this.mStandbyTimeoutConfig, "LowPowerStandbyController.StandbyTimeout", this.mOnStandbyTimeoutExpired, this.mHandler);
    }

    public final void cancelStandbyTimeoutAlarmLocked() {
        this.mAlarmManager.cancel(this.mOnStandbyTimeoutExpired);
    }

    public final void onDeviceIdleModeChanged() {
        boolean z;
        synchronized (this.mLock) {
            boolean isDeviceIdleMode = this.mPowerManager.isDeviceIdleMode();
            this.mIsDeviceIdle = isDeviceIdleMode;
            if (!this.mIdleSinceNonInteractive && !isDeviceIdleMode) {
                z = false;
                this.mIdleSinceNonInteractive = z;
                updateActiveLocked();
            }
            z = true;
            this.mIdleSinceNonInteractive = z;
            updateActiveLocked();
        }
    }

    public final void onEnabledLocked() {
        if (this.mPowerManager.isInteractive()) {
            onInteractive();
        } else {
            onNonInteractive();
        }
        registerListeners();
    }

    public final void onDisabledLocked() {
        cancelStandbyTimeoutAlarmLocked();
        unregisterListeners();
        updateActiveLocked();
    }

    public void onSettingsChanged() {
        synchronized (this.mLock) {
            boolean z = this.mIsEnabled;
            updateSettingsLocked();
            boolean z2 = this.mIsEnabled;
            if (z2 != z) {
                if (z2) {
                    onEnabledLocked();
                } else {
                    onDisabledLocked();
                }
                notifyEnabledChangedLocked();
            }
        }
    }

    public final void registerListeners() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.os.action.DEVICE_IDLE_MODE_CHANGED");
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        this.mContext.registerReceiver(this.mBroadcastReceiver, intentFilter);
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addDataScheme("package");
        intentFilter2.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter2.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter2.setPriority(1000);
        this.mContext.registerReceiver(this.mPackageBroadcastReceiver, intentFilter2);
        IntentFilter intentFilter3 = new IntentFilter();
        intentFilter3.addAction("android.intent.action.USER_ADDED");
        intentFilter3.addAction("android.intent.action.USER_REMOVED");
        this.mContext.registerReceiver(this.mUserReceiver, intentFilter3, null, this.mHandler);
        ((PowerAllowlistInternal) LocalServices.getService(PowerAllowlistInternal.class)).registerTempAllowlistChangeListener(this.mTempAllowlistChangeListener);
        this.mPhoneCallServiceTracker.register();
    }

    public final void unregisterListeners() {
        this.mContext.unregisterReceiver(this.mBroadcastReceiver);
        this.mContext.unregisterReceiver(this.mPackageBroadcastReceiver);
        this.mContext.unregisterReceiver(this.mUserReceiver);
        ((PowerAllowlistInternal) LocalServices.getService(PowerAllowlistInternal.class)).unregisterTempAllowlistChangeListener(this.mTempAllowlistChangeListener);
    }

    public final void notifyEnabledChangedLocked() {
        Intent intent = new Intent("android.os.action.LOW_POWER_STANDBY_ENABLED_CHANGED");
        intent.addFlags(1342177280);
        this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
    }

    public final void enqueueNotifyPolicyChangedLocked() {
        long elapsedRealtime = this.mClock.elapsedRealtime();
        this.mHandler.sendMessageAtTime(this.mHandler.obtainMessage(3, getPolicy()), elapsedRealtime);
    }

    public final void notifyPolicyChanged(PowerManager.LowPowerStandbyPolicy lowPowerStandbyPolicy) {
        Intent intent = new Intent("android.os.action.LOW_POWER_STANDBY_POLICY_CHANGED");
        intent.addFlags(1342177280);
        this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
    }

    public final void onStandbyTimeoutExpired() {
        synchronized (this.mLock) {
            updateActiveLocked();
        }
    }

    public final void enqueueNotifyActiveChangedLocked() {
        long elapsedRealtime = this.mClock.elapsedRealtime();
        this.mHandler.sendMessageAtTime(this.mHandler.obtainMessage(1, Boolean.valueOf(this.mIsActive)), elapsedRealtime);
    }

    public final void notifyActiveChanged(boolean z) {
        PowerManagerInternal powerManagerInternal = (PowerManagerInternal) LocalServices.getService(PowerManagerInternal.class);
        NetworkPolicyManagerInternal networkPolicyManagerInternal = (NetworkPolicyManagerInternal) LocalServices.getService(NetworkPolicyManagerInternal.class);
        powerManagerInternal.setLowPowerStandbyActive(z);
        networkPolicyManagerInternal.setLowPowerStandbyActive(z);
    }

    public boolean isActive() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mIsActive;
        }
        return z;
    }

    public boolean isSupported() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mSupportedConfig;
        }
        return z;
    }

    public boolean isEnabled() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mSupportedConfig && this.mIsEnabled;
        }
        return z;
    }

    public void setEnabled(boolean z) {
        synchronized (this.mLock) {
            if (!this.mSupportedConfig) {
                android.util.Slog.w("LowPowerStandbyController", "Low Power Standby cannot be enabled because it is not supported on this device");
            } else {
                Settings.Global.putInt(this.mContext.getContentResolver(), "low_power_standby_enabled", z ? 1 : 0);
                onSettingsChanged();
            }
        }
    }

    public void setActiveDuringMaintenance(boolean z) {
        synchronized (this.mLock) {
            if (!this.mSupportedConfig) {
                android.util.Slog.w("LowPowerStandbyController", "Low Power Standby settings cannot be changed because it is not supported on this device");
            } else {
                Settings.Global.putInt(this.mContext.getContentResolver(), "low_power_standby_active_during_maintenance", z ? 1 : 0);
                onSettingsChanged();
            }
        }
    }

    public void forceActive(boolean z) {
        synchronized (this.mLock) {
            this.mForceActive = z;
            updateActiveLocked();
        }
    }

    public void setPolicy(PowerManager.LowPowerStandbyPolicy lowPowerStandbyPolicy) {
        synchronized (this.mLock) {
            if (!this.mSupportedConfig) {
                android.util.Slog.w("LowPowerStandbyController", "Low Power Standby policy cannot be changed because it is not supported on this device");
                return;
            }
            if (!this.mEnableCustomPolicy) {
                android.util.Slog.d("LowPowerStandbyController", "Custom policies are not enabled.");
                return;
            }
            if (Objects.equals(this.mPolicy, lowPowerStandbyPolicy)) {
                return;
            }
            boolean policyChangeAffectsAllowlistLocked = policyChangeAffectsAllowlistLocked(this.mPolicy, lowPowerStandbyPolicy);
            this.mPolicy = lowPowerStandbyPolicy;
            enqueueSavePolicy(lowPowerStandbyPolicy);
            if (policyChangeAffectsAllowlistLocked) {
                enqueueNotifyAllowlistChangedLocked();
            }
            enqueueNotifyPolicyChangedLocked();
        }
    }

    public PowerManager.LowPowerStandbyPolicy getPolicy() {
        synchronized (this.mLock) {
            if (!this.mSupportedConfig) {
                return null;
            }
            if (this.mEnableCustomPolicy) {
                return policyOrDefault(this.mPolicy);
            }
            return DEFAULT_POLICY;
        }
    }

    public final PowerManager.LowPowerStandbyPolicy policyOrDefault(PowerManager.LowPowerStandbyPolicy lowPowerStandbyPolicy) {
        return lowPowerStandbyPolicy == null ? DEFAULT_POLICY : lowPowerStandbyPolicy;
    }

    public boolean isPackageExempt(int i) {
        synchronized (this.mLock) {
            if (!isEnabled()) {
                return true;
            }
            return getExemptPackageAppIdsLocked().contains(Integer.valueOf(UserHandle.getAppId(i)));
        }
    }

    public boolean isAllowed(int i) {
        synchronized (this.mLock) {
            boolean z = true;
            if (!isEnabled()) {
                return true;
            }
            if ((getPolicy().getAllowedReasons() & i) == 0) {
                z = false;
            }
            return z;
        }
    }

    public boolean isAllowed(String str) {
        synchronized (this.mLock) {
            boolean z = true;
            if (!this.mSupportedConfig) {
                return true;
            }
            if (isEnabled() && !getPolicy().getAllowedFeatures().contains(str)) {
                z = false;
            }
            return z;
        }
    }

    public final int findIndexOfStandbyPorts(IBinder iBinder) {
        for (int i = 0; i < this.mStandbyPortLocks.size(); i++) {
            if (((StandbyPortsLock) this.mStandbyPortLocks.get(i)).getToken() == iBinder) {
                return i;
            }
        }
        return -1;
    }

    public void acquireStandbyPorts(IBinder iBinder, int i, List list) {
        validatePorts(list);
        StandbyPortsLock standbyPortsLock = new StandbyPortsLock(iBinder, i, list);
        synchronized (this.mLock) {
            if (findIndexOfStandbyPorts(iBinder) != -1) {
                return;
            }
            if (standbyPortsLock.linkToDeath()) {
                this.mStandbyPortLocks.add(standbyPortsLock);
                if (this.mEnableStandbyPorts && isEnabled() && isPackageExempt(i)) {
                    enqueueNotifyStandbyPortsChangedLocked();
                }
            }
        }
    }

    public void validatePorts(List list) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            int portNumber = ((PowerManager.LowPowerStandbyPortDescription) it.next()).getPortNumber();
            if (portNumber < 0 || portNumber > 65535) {
                throw new IllegalArgumentException("port out of range:" + portNumber);
            }
        }
    }

    public void releaseStandbyPorts(IBinder iBinder) {
        synchronized (this.mLock) {
            int findIndexOfStandbyPorts = findIndexOfStandbyPorts(iBinder);
            if (findIndexOfStandbyPorts == -1) {
                return;
            }
            StandbyPortsLock standbyPortsLock = (StandbyPortsLock) this.mStandbyPortLocks.remove(findIndexOfStandbyPorts);
            standbyPortsLock.unlinkToDeath();
            if (this.mEnableStandbyPorts && isEnabled() && isPackageExempt(standbyPortsLock.getUid())) {
                enqueueNotifyStandbyPortsChangedLocked();
            }
        }
    }

    public List getActiveStandbyPorts() {
        ArrayList arrayList = new ArrayList();
        synchronized (this.mLock) {
            if (isEnabled() && this.mEnableStandbyPorts) {
                List exemptPackageAppIdsLocked = getExemptPackageAppIdsLocked();
                for (StandbyPortsLock standbyPortsLock : this.mStandbyPortLocks) {
                    if (exemptPackageAppIdsLocked.contains(Integer.valueOf(UserHandle.getAppId(standbyPortsLock.getUid())))) {
                        arrayList.addAll(standbyPortsLock.getPorts());
                    }
                }
                return arrayList;
            }
            return arrayList;
        }
    }

    public final boolean policyChangeAffectsAllowlistLocked(PowerManager.LowPowerStandbyPolicy lowPowerStandbyPolicy, PowerManager.LowPowerStandbyPolicy lowPowerStandbyPolicy2) {
        PowerManager.LowPowerStandbyPolicy policyOrDefault = policyOrDefault(lowPowerStandbyPolicy);
        PowerManager.LowPowerStandbyPolicy policyOrDefault2 = policyOrDefault(lowPowerStandbyPolicy2);
        int i = 0;
        for (int i2 = 0; i2 < this.mUidAllowedReasons.size(); i2++) {
            i |= this.mUidAllowedReasons.valueAt(i2);
        }
        return ((policyOrDefault.getAllowedReasons() ^ policyOrDefault2.getAllowedReasons()) & i) != 0 || (policyOrDefault.getExemptPackages().equals(policyOrDefault2.getExemptPackages()) ^ true);
    }

    public void dump(PrintWriter printWriter) {
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
        indentingPrintWriter.println();
        indentingPrintWriter.println("Low Power Standby Controller:");
        indentingPrintWriter.increaseIndent();
        synchronized (this.mLock) {
            indentingPrintWriter.print("mIsActive=");
            indentingPrintWriter.println(this.mIsActive);
            indentingPrintWriter.print("mIsEnabled=");
            indentingPrintWriter.println(this.mIsEnabled);
            indentingPrintWriter.print("mSupportedConfig=");
            indentingPrintWriter.println(this.mSupportedConfig);
            indentingPrintWriter.print("mEnabledByDefaultConfig=");
            indentingPrintWriter.println(this.mEnabledByDefaultConfig);
            indentingPrintWriter.print("mStandbyTimeoutConfig=");
            indentingPrintWriter.println(this.mStandbyTimeoutConfig);
            indentingPrintWriter.print("mEnableCustomPolicy=");
            indentingPrintWriter.println(this.mEnableCustomPolicy);
            if (this.mIsActive || this.mIsEnabled) {
                indentingPrintWriter.print("mIsInteractive=");
                indentingPrintWriter.println(this.mIsInteractive);
                indentingPrintWriter.print("mLastInteractiveTime=");
                indentingPrintWriter.println(this.mLastInteractiveTimeElapsed);
                indentingPrintWriter.print("mIdleSinceNonInteractive=");
                indentingPrintWriter.println(this.mIdleSinceNonInteractive);
                indentingPrintWriter.print("mIsDeviceIdle=");
                indentingPrintWriter.println(this.mIsDeviceIdle);
            }
            int[] allowlistUidsLocked = getAllowlistUidsLocked();
            indentingPrintWriter.print("Allowed UIDs=");
            indentingPrintWriter.println(Arrays.toString(allowlistUidsLocked));
            PowerManager.LowPowerStandbyPolicy policy = getPolicy();
            if (policy != null) {
                indentingPrintWriter.println();
                indentingPrintWriter.println("mPolicy:");
                indentingPrintWriter.increaseIndent();
                indentingPrintWriter.print("mIdentifier=");
                indentingPrintWriter.println(policy.getIdentifier());
                indentingPrintWriter.print("mExemptPackages=");
                indentingPrintWriter.println(String.join(",", policy.getExemptPackages()));
                indentingPrintWriter.print("mAllowedReasons=");
                indentingPrintWriter.println(PowerManager.lowPowerStandbyAllowedReasonsToString(policy.getAllowedReasons()));
                indentingPrintWriter.print("mAllowedFeatures=");
                indentingPrintWriter.println(String.join(",", policy.getAllowedFeatures()));
                indentingPrintWriter.decreaseIndent();
            }
            indentingPrintWriter.println();
            indentingPrintWriter.println("UID allowed reasons:");
            indentingPrintWriter.increaseIndent();
            for (int i = 0; i < this.mUidAllowedReasons.size(); i++) {
                if (this.mUidAllowedReasons.valueAt(i) > 0) {
                    indentingPrintWriter.print(this.mUidAllowedReasons.keyAt(i));
                    indentingPrintWriter.print(": ");
                    indentingPrintWriter.println(PowerManager.lowPowerStandbyAllowedReasonsToString(this.mUidAllowedReasons.valueAt(i)));
                }
            }
            indentingPrintWriter.decreaseIndent();
            List activeStandbyPorts = getActiveStandbyPorts();
            if (!activeStandbyPorts.isEmpty()) {
                indentingPrintWriter.println();
                indentingPrintWriter.println("Active standby ports locks:");
                indentingPrintWriter.increaseIndent();
                Iterator it = activeStandbyPorts.iterator();
                while (it.hasNext()) {
                    indentingPrintWriter.print(((PowerManager.LowPowerStandbyPortDescription) it.next()).toString());
                }
                indentingPrintWriter.decreaseIndent();
            }
        }
        indentingPrintWriter.decreaseIndent();
    }

    public void dumpProto(ProtoOutputStream protoOutputStream, long j) {
        synchronized (this.mLock) {
            long start = protoOutputStream.start(j);
            protoOutputStream.write(1133871366145L, this.mIsActive);
            protoOutputStream.write(1133871366146L, this.mIsEnabled);
            protoOutputStream.write(1133871366147L, this.mSupportedConfig);
            protoOutputStream.write(1133871366148L, this.mEnabledByDefaultConfig);
            protoOutputStream.write(1133871366149L, this.mIsInteractive);
            protoOutputStream.write(1112396529670L, this.mLastInteractiveTimeElapsed);
            protoOutputStream.write(1120986464263L, this.mStandbyTimeoutConfig);
            protoOutputStream.write(1133871366152L, this.mIdleSinceNonInteractive);
            protoOutputStream.write(1133871366153L, this.mIsDeviceIdle);
            for (int i : getAllowlistUidsLocked()) {
                protoOutputStream.write(2220498092042L, i);
            }
            PowerManager.LowPowerStandbyPolicy policy = getPolicy();
            if (policy != null) {
                long start2 = protoOutputStream.start(1146756268043L);
                protoOutputStream.write(1138166333441L, policy.getIdentifier());
                Iterator it = policy.getExemptPackages().iterator();
                while (it.hasNext()) {
                    protoOutputStream.write(2237677961218L, (String) it.next());
                }
                protoOutputStream.write(1120986464259L, policy.getAllowedReasons());
                Iterator it2 = policy.getAllowedFeatures().iterator();
                while (it2.hasNext()) {
                    protoOutputStream.write(2237677961220L, (String) it2.next());
                }
                protoOutputStream.end(start2);
            }
            protoOutputStream.end(start);
        }
    }

    /* loaded from: classes3.dex */
    public class LowPowerStandbyHandler extends Handler {
        public LowPowerStandbyHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 0) {
                LowPowerStandbyController.this.onStandbyTimeoutExpired();
                return;
            }
            if (i == 1) {
                LowPowerStandbyController.this.notifyActiveChanged(((Boolean) message.obj).booleanValue());
                return;
            }
            if (i == 2) {
                LowPowerStandbyController.this.notifyAllowlistChanged((int[]) message.obj);
            } else {
                if (i == 3) {
                    LowPowerStandbyController.this.notifyPolicyChanged((PowerManager.LowPowerStandbyPolicy) message.obj);
                    return;
                }
                if (i == 4) {
                    LowPowerStandbyController.this.mPhoneCallServiceTracker.foregroundServiceStateChanged(message.arg1);
                } else {
                    if (i != 5) {
                        return;
                    }
                    LowPowerStandbyController.this.notifyStandbyPortsChanged();
                }
            }
        }
    }

    public final boolean hasAllowedReasonLocked(int i, int i2) {
        return (this.mUidAllowedReasons.get(i) & i2) != 0;
    }

    public final boolean addAllowedReasonLocked(int i, int i2) {
        int i3 = this.mUidAllowedReasons.get(i);
        int i4 = i2 | i3;
        this.mUidAllowedReasons.put(i, i4);
        return i3 != i4;
    }

    public final boolean removeAllowedReasonLocked(int i, int i2) {
        int i3 = this.mUidAllowedReasons.get(i);
        if (i3 == 0) {
            return false;
        }
        int i4 = (~i2) & i3;
        if (i4 == 0) {
            SparseIntArray sparseIntArray = this.mUidAllowedReasons;
            sparseIntArray.removeAt(sparseIntArray.indexOfKey(i));
        } else {
            this.mUidAllowedReasons.put(i, i4);
        }
        return i3 != i4;
    }

    public final void addToAllowlistInternal(int i, int i2) {
        synchronized (this.mLock) {
            if (this.mSupportedConfig) {
                if (i2 != 0 && !hasAllowedReasonLocked(i, i2)) {
                    addAllowedReasonLocked(i, i2);
                    if ((getPolicy().getAllowedReasons() & i2) != 0) {
                        enqueueNotifyAllowlistChangedLocked();
                    }
                }
            }
        }
    }

    public final void removeFromAllowlistInternal(int i, int i2) {
        synchronized (this.mLock) {
            if (this.mSupportedConfig) {
                if (i2 != 0 && hasAllowedReasonLocked(i, i2)) {
                    removeAllowedReasonLocked(i, i2);
                    if ((getPolicy().getAllowedReasons() & i2) != 0) {
                        enqueueNotifyAllowlistChangedLocked();
                    }
                }
            }
        }
    }

    public final List getExemptPackageAppIdsLocked() {
        PackageManager packageManager = this.mContext.getPackageManager();
        PowerManager.LowPowerStandbyPolicy policy = getPolicy();
        ArrayList arrayList = new ArrayList();
        if (policy == null) {
            return arrayList;
        }
        Iterator it = policy.getExemptPackages().iterator();
        while (it.hasNext()) {
            try {
                arrayList.add(Integer.valueOf(UserHandle.getAppId(packageManager.getPackageUid((String) it.next(), PackageManager.PackageInfoFlags.of(0L)))));
            } catch (PackageManager.NameNotFoundException unused) {
            }
        }
        return arrayList;
    }

    public final int[] getAllowlistUidsLocked() {
        List userHandles = ((UserManager) this.mContext.getSystemService(UserManager.class)).getUserHandles(true);
        ArraySet arraySet = new ArraySet(this.mUidAllowedReasons.size());
        PowerManager.LowPowerStandbyPolicy policy = getPolicy();
        if (policy == null) {
            return new int[0];
        }
        int allowedReasons = policy.getAllowedReasons();
        for (int i = 0; i < this.mUidAllowedReasons.size(); i++) {
            Integer valueOf = Integer.valueOf(this.mUidAllowedReasons.keyAt(i));
            if ((this.mUidAllowedReasons.valueAt(i) & allowedReasons) != 0) {
                arraySet.add(valueOf);
            }
        }
        Iterator it = getExemptPackageAppIdsLocked().iterator();
        while (it.hasNext()) {
            for (int i2 : uidsForAppId(((Integer) it.next()).intValue(), userHandles)) {
                arraySet.add(Integer.valueOf(i2));
            }
        }
        int[] iArr = new int[arraySet.size()];
        for (int i3 = 0; i3 < arraySet.size(); i3++) {
            iArr[i3] = ((Integer) arraySet.valueAt(i3)).intValue();
        }
        Arrays.sort(iArr);
        return iArr;
    }

    public final int[] uidsForAppId(int i, List list) {
        int appId = UserHandle.getAppId(i);
        int[] iArr = new int[list.size()];
        for (int i2 = 0; i2 < list.size(); i2++) {
            iArr[i2] = ((UserHandle) list.get(i2)).getUid(appId);
        }
        return iArr;
    }

    public final void enqueueNotifyAllowlistChangedLocked() {
        long elapsedRealtime = this.mClock.elapsedRealtime();
        this.mHandler.sendMessageAtTime(this.mHandler.obtainMessage(2, getAllowlistUidsLocked()), elapsedRealtime);
    }

    public final void notifyAllowlistChanged(int[] iArr) {
        PowerManagerInternal powerManagerInternal = (PowerManagerInternal) LocalServices.getService(PowerManagerInternal.class);
        NetworkPolicyManagerInternal networkPolicyManagerInternal = (NetworkPolicyManagerInternal) LocalServices.getService(NetworkPolicyManagerInternal.class);
        powerManagerInternal.setLowPowerStandbyAllowlist(iArr);
        networkPolicyManagerInternal.setLowPowerStandbyAllowlist(iArr);
    }

    public final void enqueueNotifyStandbyPortsChangedLocked() {
        long elapsedRealtime = this.mClock.elapsedRealtime();
        this.mHandler.sendMessageAtTime(this.mHandler.obtainMessage(5), elapsedRealtime);
    }

    public final void notifyStandbyPortsChanged() {
        Intent intent = new Intent("android.os.action.LOW_POWER_STANDBY_PORTS_CHANGED");
        intent.addFlags(1342177280);
        this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL, "android.permission.MANAGE_LOW_POWER_STANDBY");
    }

    /* loaded from: classes3.dex */
    public class DeviceConfigWrapper {
        public boolean enableCustomPolicy() {
            return DeviceConfig.getBoolean("low_power_standby", "enable_policy", false);
        }

        public boolean enableStandbyPorts() {
            return DeviceConfig.getBoolean("low_power_standby", "enable_standby_ports", false);
        }

        public void registerPropertyUpdateListener(Executor executor, DeviceConfig.OnPropertiesChangedListener onPropertiesChangedListener) {
            DeviceConfig.addOnPropertiesChangedListener("low_power_standby", executor, onPropertiesChangedListener);
        }
    }

    /* loaded from: classes3.dex */
    public final class LocalService extends LowPowerStandbyControllerInternal {
        public LocalService() {
        }

        @Override // com.android.server.power.LowPowerStandbyControllerInternal
        public void addToAllowlist(int i, int i2) {
            LowPowerStandbyController.this.addToAllowlistInternal(i, i2);
        }

        @Override // com.android.server.power.LowPowerStandbyControllerInternal
        public void removeFromAllowlist(int i, int i2) {
            LowPowerStandbyController.this.removeFromAllowlistInternal(i, i2);
        }
    }

    /* loaded from: classes3.dex */
    public final class SettingsObserver extends ContentObserver {
        public SettingsObserver(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri) {
            LowPowerStandbyController.this.onSettingsChanged();
        }
    }

    /* loaded from: classes3.dex */
    public final class TempAllowlistChangeListener implements PowerAllowlistInternal.TempAllowlistChangeListener {
        public TempAllowlistChangeListener() {
        }

        public void onAppAdded(int i) {
            LowPowerStandbyController.this.addToAllowlistInternal(i, 2);
        }

        public void onAppRemoved(int i) {
            LowPowerStandbyController.this.removeFromAllowlistInternal(i, 2);
        }
    }

    /* loaded from: classes3.dex */
    public final class PhoneCallServiceTracker extends IForegroundServiceObserver.Stub {
        public boolean mRegistered = false;
        public final SparseBooleanArray mUidsWithPhoneCallService = new SparseBooleanArray();

        public PhoneCallServiceTracker() {
        }

        public void register() {
            if (this.mRegistered) {
                return;
            }
            try {
                ((IActivityManager) LowPowerStandbyController.this.mActivityManager.get()).registerForegroundServiceObserver(this);
                this.mRegistered = true;
            } catch (RemoteException unused) {
            }
        }

        public void onForegroundStateChanged(IBinder iBinder, String str, int i, boolean z) {
            try {
                long elapsedRealtime = LowPowerStandbyController.this.mClock.elapsedRealtime();
                LowPowerStandbyController.this.mHandler.sendMessageAtTime(LowPowerStandbyController.this.mHandler.obtainMessage(4, LowPowerStandbyController.this.mContext.getPackageManager().getPackageUidAsUser(str, i), 0), elapsedRealtime);
            } catch (PackageManager.NameNotFoundException unused) {
            }
        }

        public void foregroundServiceStateChanged(int i) {
            boolean z = this.mUidsWithPhoneCallService.get(i);
            boolean hasRunningForegroundService = LowPowerStandbyController.this.mActivityManagerInternal.hasRunningForegroundService(i, 4);
            if (hasRunningForegroundService == z) {
                return;
            }
            if (hasRunningForegroundService) {
                this.mUidsWithPhoneCallService.append(i, true);
                uidStartedPhoneCallService(i);
            } else {
                this.mUidsWithPhoneCallService.delete(i);
                uidStoppedPhoneCallService(i);
            }
        }

        public final void uidStartedPhoneCallService(int i) {
            LowPowerStandbyController.this.addToAllowlistInternal(i, 4);
        }

        public final void uidStoppedPhoneCallService(int i) {
            LowPowerStandbyController.this.removeFromAllowlistInternal(i, 4);
        }
    }
}
