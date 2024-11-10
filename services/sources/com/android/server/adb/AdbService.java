package com.android.server.adb;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.debug.AdbManagerInternal;
import android.debug.FingerprintAndPairDevice;
import android.debug.IAdbCallback;
import android.debug.IAdbManager;
import android.debug.IAdbTransport;
import android.debug.PairDevice;
import android.net.Uri;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.sysprop.AdbProperties;
import android.util.ArrayMap;
import android.util.sysfwutil.Slog;
import com.android.internal.util.Preconditions;
import com.android.internal.util.function.TriConsumer;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.server.FgThread;
import com.android.server.LocalServices;
import com.android.server.SystemService;
import com.android.server.adb.AdbDebuggingManager;
import java.io.File;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public class AdbService extends IAdbManager.Stub {
    public final RemoteCallbackList mCallbacks;
    public AtomicInteger mConnectionPort;
    public AdbDebuggingManager.AdbConnectionPortPoller mConnectionPortPoller;
    public final ContentResolver mContentResolver;
    public final Context mContext;
    public AdbDebuggingManager mDebuggingManager;
    public boolean mIsAdbUsbEnabled;
    public boolean mIsAdbWifiEnabled;
    public ContentObserver mObserver;
    public final AdbConnectionPortListener mPortListener;
    public final ArrayMap mTransports;

    public /* synthetic */ AdbService(Context context, AdbServiceIA adbServiceIA) {
        this(context);
    }

    /* loaded from: classes.dex */
    public class Lifecycle extends SystemService {
        public AdbService mAdbService;

        public Lifecycle(Context context) {
            super(context);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v0, types: [com.android.server.adb.AdbService, android.os.IBinder] */
        @Override // com.android.server.SystemService
        public void onStart() {
            ?? adbService = new AdbService(getContext());
            this.mAdbService = adbService;
            publishBinderService("adb", adbService);
        }

        @Override // com.android.server.SystemService
        public void onBootPhase(int i) {
            if (i == 550) {
                this.mAdbService.systemReady();
            } else if (i == 1000) {
                FgThread.getHandler().sendMessage(PooledLambda.obtainMessage(new Consumer() { // from class: com.android.server.adb.AdbService$Lifecycle$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((AdbService) obj).bootCompleted();
                    }
                }, this.mAdbService));
            }
        }
    }

    /* loaded from: classes.dex */
    public class AdbManagerInternalImpl extends AdbManagerInternal {
        public /* synthetic */ AdbManagerInternalImpl(AdbService adbService, AdbManagerInternalImplIA adbManagerInternalImplIA) {
            this();
        }

        public AdbManagerInternalImpl() {
        }

        public void registerTransport(IAdbTransport iAdbTransport) {
            AdbService.this.mTransports.put(iAdbTransport.asBinder(), iAdbTransport);
        }

        public void unregisterTransport(IAdbTransport iAdbTransport) {
            AdbService.this.mTransports.remove(iAdbTransport.asBinder());
        }

        public boolean isAdbEnabled(byte b) {
            if (b == 0) {
                return AdbService.this.mIsAdbUsbEnabled;
            }
            if (b == 1) {
                return AdbService.this.mIsAdbWifiEnabled;
            }
            throw new IllegalArgumentException("isAdbEnabled called with unimplemented transport type=" + ((int) b));
        }

        public File getAdbKeysFile() {
            if (AdbService.this.mDebuggingManager == null) {
                return null;
            }
            return AdbService.this.mDebuggingManager.getUserKeyFile();
        }

        public File getAdbTempKeysFile() {
            if (AdbService.this.mDebuggingManager == null) {
                return null;
            }
            return AdbService.this.mDebuggingManager.getAdbTempKeysFile();
        }

        public void notifyKeyFilesUpdated() {
            if (AdbService.this.mDebuggingManager == null) {
                return;
            }
            AdbService.this.mDebuggingManager.notifyKeyFilesUpdated();
        }

        public void startAdbdForTransport(byte b) {
            FgThread.getHandler().sendMessage(PooledLambda.obtainMessage(new AdbService$AdbManagerInternalImpl$$ExternalSyntheticLambda0(), AdbService.this, Boolean.TRUE, Byte.valueOf(b)));
        }

        public void stopAdbdForTransport(byte b) {
            FgThread.getHandler().sendMessage(PooledLambda.obtainMessage(new AdbService$AdbManagerInternalImpl$$ExternalSyntheticLambda0(), AdbService.this, Boolean.FALSE, Byte.valueOf(b)));
        }
    }

    public final void registerContentObservers() {
        try {
            this.mObserver = new AdbSettingsObserver();
            this.mContentResolver.registerContentObserver(Settings.Global.getUriFor("adb_enabled"), false, this.mObserver);
            this.mContentResolver.registerContentObserver(Settings.Global.getUriFor("adb_wifi_enabled"), false, this.mObserver);
        } catch (Exception e) {
            Slog.e("AdbService", "Error in registerContentObservers", e);
        }
    }

    public static boolean containsFunction(String str, String str2) {
        int indexOf = str.indexOf(str2);
        if (indexOf < 0) {
            return false;
        }
        if (indexOf > 0 && str.charAt(indexOf - 1) != ',') {
            return false;
        }
        int length = indexOf + str2.length();
        return length >= str.length() || str.charAt(length) == ',';
    }

    /* loaded from: classes.dex */
    public class AdbSettingsObserver extends ContentObserver {
        public final Uri mAdbUsbUri;
        public final Uri mAdbWifiUri;

        public AdbSettingsObserver() {
            super(null);
            this.mAdbUsbUri = Settings.Global.getUriFor("adb_enabled");
            this.mAdbWifiUri = Settings.Global.getUriFor("adb_wifi_enabled");
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri, int i) {
            if (Settings.Secure.getInt(AdbService.this.mContext.getContentResolver(), "rampart_blocked_adb_cmd", 0) == 1) {
                Slog.d("AdbService", "onChange : ADB is blocked by Auto Blocker");
                Handler handler = FgThread.getHandler();
                TriConsumer triConsumer = new TriConsumer() { // from class: com.android.server.adb.AdbService$AdbSettingsObserver$$ExternalSyntheticLambda0
                    public final void accept(Object obj, Object obj2, Object obj3) {
                        ((AdbService) obj).setAdbEnabled(((Boolean) obj2).booleanValue(), ((Byte) obj3).byteValue());
                    }
                };
                AdbService adbService = AdbService.this;
                Boolean bool = Boolean.FALSE;
                handler.sendMessage(PooledLambda.obtainMessage(triConsumer, adbService, bool, (byte) 1));
                FgThread.getHandler().sendMessage(PooledLambda.obtainMessage(new TriConsumer() { // from class: com.android.server.adb.AdbService$AdbSettingsObserver$$ExternalSyntheticLambda0
                    public final void accept(Object obj, Object obj2, Object obj3) {
                        ((AdbService) obj).setAdbEnabled(((Boolean) obj2).booleanValue(), ((Byte) obj3).byteValue());
                    }
                }, AdbService.this, bool, (byte) 0));
                return;
            }
            if (this.mAdbUsbUri.equals(uri)) {
                FgThread.getHandler().sendMessage(PooledLambda.obtainMessage(new TriConsumer() { // from class: com.android.server.adb.AdbService$AdbSettingsObserver$$ExternalSyntheticLambda0
                    public final void accept(Object obj, Object obj2, Object obj3) {
                        ((AdbService) obj).setAdbEnabled(((Boolean) obj2).booleanValue(), ((Byte) obj3).byteValue());
                    }
                }, AdbService.this, Boolean.valueOf(Settings.Global.getInt(AdbService.this.mContentResolver, "adb_enabled", 0) > 0), (byte) 0));
            } else if (this.mAdbWifiUri.equals(uri)) {
                FgThread.getHandler().sendMessage(PooledLambda.obtainMessage(new TriConsumer() { // from class: com.android.server.adb.AdbService$AdbSettingsObserver$$ExternalSyntheticLambda0
                    public final void accept(Object obj, Object obj2, Object obj3) {
                        ((AdbService) obj).setAdbEnabled(((Boolean) obj2).booleanValue(), ((Byte) obj3).byteValue());
                    }
                }, AdbService.this, Boolean.valueOf(Settings.Global.getInt(AdbService.this.mContentResolver, "adb_wifi_enabled", 0) > 0), (byte) 1));
            }
        }
    }

    public AdbService(Context context) {
        this.mConnectionPort = new AtomicInteger(-1);
        this.mPortListener = new AdbConnectionPortListener();
        this.mCallbacks = new RemoteCallbackList();
        this.mTransports = new ArrayMap();
        this.mContext = context;
        this.mContentResolver = context.getContentResolver();
        this.mDebuggingManager = new AdbDebuggingManager(context);
        registerContentObservers();
        LocalServices.addService(AdbManagerInternal.class, new AdbManagerInternalImpl());
    }

    public void systemReady() {
        Slog.d("AdbService", "systemReady");
        boolean containsFunction = containsFunction(SystemProperties.get("persist.sys.usb.config", ""), "adb");
        this.mIsAdbUsbEnabled = containsFunction;
        int i = 1;
        boolean z = containsFunction || SystemProperties.getBoolean("persist.sys.test_harness", false);
        this.mIsAdbWifiEnabled = "1".equals(SystemProperties.get("persist.adb.tls_server.enable", "0"));
        try {
            Settings.Global.putInt(this.mContentResolver, "adb_enabled", z ? 1 : 0);
            ContentResolver contentResolver = this.mContentResolver;
            if (!this.mIsAdbWifiEnabled) {
                i = 0;
            }
            Settings.Global.putInt(contentResolver, "adb_wifi_enabled", i);
        } catch (SecurityException unused) {
            Slog.d("AdbService", "ADB_ENABLED is restricted.");
        }
    }

    public void bootCompleted() {
        Slog.d("AdbService", "boot completed");
        AdbDebuggingManager adbDebuggingManager = this.mDebuggingManager;
        if (adbDebuggingManager != null) {
            adbDebuggingManager.setAdbEnabled(this.mIsAdbUsbEnabled, (byte) 0);
            this.mDebuggingManager.setAdbEnabled(this.mIsAdbWifiEnabled, (byte) 1);
        }
    }

    public void allowDebugging(boolean z, String str) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_DEBUGGING", null);
        Preconditions.checkStringNotEmpty(str);
        AdbDebuggingManager adbDebuggingManager = this.mDebuggingManager;
        if (adbDebuggingManager != null) {
            adbDebuggingManager.allowDebugging(z, str);
        }
    }

    public void denyDebugging() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_DEBUGGING", null);
        AdbDebuggingManager adbDebuggingManager = this.mDebuggingManager;
        if (adbDebuggingManager != null) {
            adbDebuggingManager.denyDebugging();
        }
    }

    public void clearDebuggingKeys() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_DEBUGGING", null);
        AdbDebuggingManager adbDebuggingManager = this.mDebuggingManager;
        if (adbDebuggingManager != null) {
            adbDebuggingManager.clearDebuggingKeys();
            return;
        }
        throw new RuntimeException("Cannot clear ADB debugging keys, AdbDebuggingManager not enabled");
    }

    public boolean isAdbWifiSupported() {
        this.mContext.enforceCallingPermission("android.permission.MANAGE_DEBUGGING", "AdbService");
        return this.mContext.getPackageManager().hasSystemFeature("android.hardware.wifi") || this.mContext.getPackageManager().hasSystemFeature("android.hardware.ethernet");
    }

    public boolean isAdbWifiQrSupported() {
        this.mContext.enforceCallingPermission("android.permission.MANAGE_DEBUGGING", "AdbService");
        return isAdbWifiSupported() && this.mContext.getPackageManager().hasSystemFeature("android.hardware.camera.any");
    }

    public void allowWirelessDebugging(boolean z, String str) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_DEBUGGING", null);
        Preconditions.checkStringNotEmpty(str);
        AdbDebuggingManager adbDebuggingManager = this.mDebuggingManager;
        if (adbDebuggingManager != null) {
            adbDebuggingManager.allowWirelessDebugging(z, str);
        }
    }

    public void denyWirelessDebugging() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_DEBUGGING", null);
        AdbDebuggingManager adbDebuggingManager = this.mDebuggingManager;
        if (adbDebuggingManager != null) {
            adbDebuggingManager.denyWirelessDebugging();
        }
    }

    public FingerprintAndPairDevice[] getPairedDevices() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_DEBUGGING", null);
        AdbDebuggingManager adbDebuggingManager = this.mDebuggingManager;
        if (adbDebuggingManager == null) {
            return null;
        }
        Map pairedDevices = adbDebuggingManager.getPairedDevices();
        FingerprintAndPairDevice[] fingerprintAndPairDeviceArr = new FingerprintAndPairDevice[pairedDevices.size()];
        int i = 0;
        for (Map.Entry entry : pairedDevices.entrySet()) {
            FingerprintAndPairDevice fingerprintAndPairDevice = new FingerprintAndPairDevice();
            fingerprintAndPairDeviceArr[i] = fingerprintAndPairDevice;
            fingerprintAndPairDevice.keyFingerprint = (String) entry.getKey();
            fingerprintAndPairDeviceArr[i].device = (PairDevice) entry.getValue();
            i++;
        }
        return fingerprintAndPairDeviceArr;
    }

    public void unpairDevice(String str) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_DEBUGGING", null);
        Preconditions.checkStringNotEmpty(str);
        AdbDebuggingManager adbDebuggingManager = this.mDebuggingManager;
        if (adbDebuggingManager != null) {
            adbDebuggingManager.unpairDevice(str);
        }
    }

    public void enablePairingByPairingCode() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_DEBUGGING", null);
        AdbDebuggingManager adbDebuggingManager = this.mDebuggingManager;
        if (adbDebuggingManager != null) {
            adbDebuggingManager.enablePairingByPairingCode();
        }
    }

    public void enablePairingByQrCode(String str, String str2) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_DEBUGGING", null);
        Preconditions.checkStringNotEmpty(str);
        Preconditions.checkStringNotEmpty(str2);
        AdbDebuggingManager adbDebuggingManager = this.mDebuggingManager;
        if (adbDebuggingManager != null) {
            adbDebuggingManager.enablePairingByQrCode(str, str2);
        }
    }

    public void disablePairing() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_DEBUGGING", null);
        AdbDebuggingManager adbDebuggingManager = this.mDebuggingManager;
        if (adbDebuggingManager != null) {
            adbDebuggingManager.disablePairing();
        }
    }

    public int getAdbWirelessPort() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_DEBUGGING", null);
        AdbDebuggingManager adbDebuggingManager = this.mDebuggingManager;
        if (adbDebuggingManager != null) {
            return adbDebuggingManager.getAdbWirelessPort();
        }
        return this.mConnectionPort.get();
    }

    public void registerCallback(IAdbCallback iAdbCallback) {
        Slog.d("AdbService", "Registering callback " + iAdbCallback);
        this.mCallbacks.register(iAdbCallback);
    }

    public void unregisterCallback(IAdbCallback iAdbCallback) {
        Slog.d("AdbService", "Unregistering callback " + iAdbCallback);
        this.mCallbacks.unregister(iAdbCallback);
    }

    /* loaded from: classes.dex */
    public class AdbConnectionPortListener implements AdbDebuggingManager.AdbConnectionPortListener {
        public AdbConnectionPortListener() {
        }

        @Override // com.android.server.adb.AdbDebuggingManager.AdbConnectionPortListener
        public void onPortReceived(int i) {
            if (i > 0 && i <= 65535) {
                AdbService.this.mConnectionPort.set(i);
            } else {
                AdbService.this.mConnectionPort.set(-1);
                try {
                    Settings.Global.putInt(AdbService.this.mContentResolver, "adb_wifi_enabled", 0);
                } catch (SecurityException unused) {
                    Slog.d("AdbService", "ADB_ENABLED is restricted.");
                }
            }
            AdbService adbService = AdbService.this;
            adbService.broadcastPortInfo(adbService.mConnectionPort.get());
        }
    }

    public final void broadcastPortInfo(int i) {
        Intent intent = new Intent("com.android.server.adb.WIRELESS_DEBUG_STATUS");
        intent.putExtra("status", i >= 0 ? 4 : 5);
        intent.putExtra("adb_port", i);
        AdbDebuggingManager.sendBroadcastWithDebugPermission(this.mContext, intent, UserHandle.ALL);
        Slog.i("AdbService", "sent port broadcast port=" + i);
    }

    public final void startAdbd() {
        SystemProperties.set("ctl.start", "adbd");
    }

    public final void stopAdbd() {
        if (this.mIsAdbUsbEnabled || this.mIsAdbWifiEnabled) {
            return;
        }
        SystemProperties.set("ctl.stop", "adbd");
    }

    public final void setAdbdEnabledForTransport(boolean z, byte b) {
        if (b == 0) {
            this.mIsAdbUsbEnabled = z;
        } else if (b == 1) {
            this.mIsAdbWifiEnabled = z;
        }
        if (z) {
            startAdbd();
        } else {
            stopAdbd();
        }
    }

    public final void setAdbEnabled(final boolean z, final byte b) {
        Slog.d("AdbService", "setAdbEnabled(" + z + "), mIsAdbUsbEnabled=" + this.mIsAdbUsbEnabled + ", mIsAdbWifiEnabled=" + this.mIsAdbWifiEnabled + ", transportType=" + ((int) b));
        if (b == 0 && z != this.mIsAdbUsbEnabled) {
            this.mIsAdbUsbEnabled = z;
        } else {
            if (b != 1 || z == this.mIsAdbWifiEnabled) {
                return;
            }
            this.mIsAdbWifiEnabled = z;
            if (z) {
                if (!((Boolean) AdbProperties.secure().orElse(Boolean.FALSE)).booleanValue() && this.mDebuggingManager == null) {
                    SystemProperties.set("persist.adb.tls_server.enable", "1");
                    AdbDebuggingManager.AdbConnectionPortPoller adbConnectionPortPoller = new AdbDebuggingManager.AdbConnectionPortPoller(this.mPortListener);
                    this.mConnectionPortPoller = adbConnectionPortPoller;
                    adbConnectionPortPoller.start();
                }
            } else {
                SystemProperties.set("persist.adb.tls_server.enable", "0");
                AdbDebuggingManager.AdbConnectionPortPoller adbConnectionPortPoller2 = this.mConnectionPortPoller;
                if (adbConnectionPortPoller2 != null) {
                    adbConnectionPortPoller2.cancelAndWait();
                    this.mConnectionPortPoller = null;
                }
            }
        }
        if (z) {
            startAdbd();
        } else {
            stopAdbd();
        }
        for (IAdbTransport iAdbTransport : this.mTransports.values()) {
            try {
                iAdbTransport.onAdbEnabled(z, b);
            } catch (RemoteException unused) {
                Slog.w("AdbService", "Unable to send onAdbEnabled to transport " + iAdbTransport.toString());
            }
        }
        AdbDebuggingManager adbDebuggingManager = this.mDebuggingManager;
        if (adbDebuggingManager != null) {
            adbDebuggingManager.setAdbEnabled(z, b);
        }
        Slog.d("AdbService", "Broadcasting enable = " + z + ", type = " + ((int) b));
        this.mCallbacks.broadcast(new Consumer() { // from class: com.android.server.adb.AdbService$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                AdbService.lambda$setAdbEnabled$0(z, b, (IAdbCallback) obj);
            }
        });
    }

    public static /* synthetic */ void lambda$setAdbEnabled$0(boolean z, byte b, IAdbCallback iAdbCallback) {
        Slog.d("AdbService", "Sending enable = " + z + ", type = " + ((int) b) + " to " + iAdbCallback);
        try {
            iAdbCallback.onDebuggingChanged(z, b);
        } catch (RemoteException e) {
            Slog.d("AdbService", "Unable to send onDebuggingChanged:", e);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int handleShellCommand(ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2, ParcelFileDescriptor parcelFileDescriptor3, String[] strArr) {
        return new AdbShellCommand(this).exec(this, parcelFileDescriptor.getFileDescriptor(), parcelFileDescriptor2.getFileDescriptor(), parcelFileDescriptor3.getFileDescriptor(), strArr);
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0042, code lost:
    
        r7 = new com.android.internal.util.dump.DualDumpOutputStream(new android.util.proto.ProtoOutputStream(r6));
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void dump(java.io.FileDescriptor r6, java.io.PrintWriter r7, java.lang.String[] r8) {
        /*
            r5 = this;
            android.content.Context r0 = r5.mContext
            java.lang.String r1 = "AdbService"
            boolean r0 = com.android.internal.util.DumpUtils.checkDumpPermission(r0, r1, r7)
            if (r0 != 0) goto Lb
            return
        Lb:
            com.android.internal.util.IndentingPrintWriter r0 = new com.android.internal.util.IndentingPrintWriter
            java.lang.String r1 = "  "
            r0.<init>(r7, r1)
            long r2 = android.os.Binder.clearCallingIdentity()
            android.util.ArraySet r7 = new android.util.ArraySet     // Catch: java.lang.Throwable -> L71
            r7.<init>()     // Catch: java.lang.Throwable -> L71
            java.util.Collections.addAll(r7, r8)     // Catch: java.lang.Throwable -> L71
            java.lang.String r8 = "--proto"
            boolean r8 = r7.contains(r8)     // Catch: java.lang.Throwable -> L71
            int r4 = r7.size()     // Catch: java.lang.Throwable -> L71
            if (r4 == 0) goto L40
            java.lang.String r4 = "-a"
            boolean r7 = r7.contains(r4)     // Catch: java.lang.Throwable -> L71
            if (r7 != 0) goto L40
            if (r8 == 0) goto L35
            goto L40
        L35:
            java.lang.String r5 = "Dump current ADB state"
            r0.println(r5)     // Catch: java.lang.Throwable -> L71
            java.lang.String r5 = "  No commands available"
            r0.println(r5)     // Catch: java.lang.Throwable -> L71
            goto L6d
        L40:
            if (r8 == 0) goto L4d
            com.android.internal.util.dump.DualDumpOutputStream r7 = new com.android.internal.util.dump.DualDumpOutputStream     // Catch: java.lang.Throwable -> L71
            android.util.proto.ProtoOutputStream r8 = new android.util.proto.ProtoOutputStream     // Catch: java.lang.Throwable -> L71
            r8.<init>(r6)     // Catch: java.lang.Throwable -> L71
            r7.<init>(r8)     // Catch: java.lang.Throwable -> L71
            goto L5c
        L4d:
            java.lang.String r6 = "ADB MANAGER STATE (dumpsys adb):"
            r0.println(r6)     // Catch: java.lang.Throwable -> L71
            com.android.internal.util.dump.DualDumpOutputStream r7 = new com.android.internal.util.dump.DualDumpOutputStream     // Catch: java.lang.Throwable -> L71
            com.android.internal.util.IndentingPrintWriter r6 = new com.android.internal.util.IndentingPrintWriter     // Catch: java.lang.Throwable -> L71
            r6.<init>(r0, r1)     // Catch: java.lang.Throwable -> L71
            r7.<init>(r6)     // Catch: java.lang.Throwable -> L71
        L5c:
            com.android.server.adb.AdbDebuggingManager r5 = r5.mDebuggingManager     // Catch: java.lang.Throwable -> L71
            if (r5 == 0) goto L6a
            java.lang.String r6 = "debugging_manager"
            r0 = 1146756268033(0x10b00000001, double:5.66572876188E-312)
            r5.dump(r7, r6, r0)     // Catch: java.lang.Throwable -> L71
        L6a:
            r7.flush()     // Catch: java.lang.Throwable -> L71
        L6d:
            android.os.Binder.restoreCallingIdentity(r2)
            return
        L71:
            r5 = move-exception
            android.os.Binder.restoreCallingIdentity(r2)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.adb.AdbService.dump(java.io.FileDescriptor, java.io.PrintWriter, java.lang.String[]):void");
    }
}
