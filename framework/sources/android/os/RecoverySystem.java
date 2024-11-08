package android.os;

import android.Manifest;
import android.annotation.SystemApi;
import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.audio.Enums;
import android.os.IRecoverySystemProgressListener;
import android.os.IVold;
import android.provider.Settings;
import android.sec.enterprise.EnterpriseDeviceManager;
import android.sec.enterprise.RestrictionPolicy;
import android.sec.enterprise.auditlog.AuditEvents;
import android.sec.enterprise.auditlog.AuditLog;
import android.security.AndroidKeyStoreMaintenance;
import android.security.KeyStoreException;
import android.system.ErrnoException;
import android.system.Os;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.euicc.EuiccManager;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import sun.security.pkcs.PKCS7;
import sun.security.pkcs.SignerInfo;

/* loaded from: classes3.dex */
public class RecoverySystem {
    private static final String ACTION_EUICC_FACTORY_RESET = "com.android.internal.action.EUICC_FACTORY_RESET";
    private static final String ACTION_EUICC_REMOVE_INVISIBLE_SUBSCRIPTIONS = "com.android.internal.action.EUICC_REMOVE_INVISIBLE_SUBSCRIPTIONS";
    public static final File BLOCK_BACKUP_FILE;
    public static final File BLOCK_MAP_FILE;
    private static File COMMAND_FILE = null;
    private static final long DEFAULT_EUICC_FACTORY_RESET_TIMEOUT_MILLIS = 30000;
    private static final long DEFAULT_EUICC_REMOVING_INVISIBLE_PROFILES_TIMEOUT_MILLIS = 45000;
    private static final File DEFAULT_KEYSTORE = new File("/system/etc/security/otacerts.zip");
    private static final String LAST_CACHE_SUDDEN_RESET_LOG_PATH = "/data/log/recovery_last_sudden_reset.log";
    private static final String LAST_INSTALL_PATH = "last_install";
    private static final String LAST_PREFIX = "last_";
    private static final String LAST_RECOVERY_MODE = "dev.lastrecoverymode";
    private static final File LOG_FILE;
    private static final int LOG_FILE_MAX_LENGTH = 65536;
    private static final long MAX_EUICC_FACTORY_RESET_TIMEOUT_MILLIS = 60000;
    private static final long MAX_EUICC_REMOVING_INVISIBLE_PROFILES_TIMEOUT_MILLIS = 90000;
    private static final long MIN_EUICC_FACTORY_RESET_TIMEOUT_MILLIS = 5000;
    private static final long MIN_EUICC_REMOVING_INVISIBLE_PROFILES_TIMEOUT_MILLIS = 15000;
    private static final String PACKAGE_NAME_EUICC_DATA_MANAGEMENT_CALLBACK = "android";
    private static final long PUBLISH_PROGRESS_INTERVAL_MS = 500;
    private static final File RECOVERY_DIR;
    public static final File RECOVERY_RESCUEPARTY_FILE;
    private static final String RECOVERY_WIPE_DATA_COMMAND = "--wipe_data";
    private static final int RESCUEPARTY_LOG_MAX_LENGTH = 524288;

    @SystemApi
    public static final int RESUME_ON_REBOOT_REBOOT_ERROR_INVALID_PACKAGE_NAME = 2000;

    @SystemApi
    public static final int RESUME_ON_REBOOT_REBOOT_ERROR_LSKF_NOT_CAPTURED = 3000;
    public static final int RESUME_ON_REBOOT_REBOOT_ERROR_NONE = 0;

    @SystemApi
    public static final int RESUME_ON_REBOOT_REBOOT_ERROR_PROVIDER_PREPARATION_FAILURE = 5000;

    @SystemApi
    public static final int RESUME_ON_REBOOT_REBOOT_ERROR_SLOT_MISMATCH = 4000;

    @SystemApi
    public static final int RESUME_ON_REBOOT_REBOOT_ERROR_UNSPECIFIED = 1000;
    private static final String SUDDEN_RESET_LAST_KMSG_NAME = "recovery_sudden_reset_last_kmsg.log";
    private static final String TAG = "RecoverySystem";
    private static final String TMP_RECOVERY_LOG_PATH = "/efs/recovery/tmp_recovery.log";
    public static final File UNCRYPT_PACKAGE_FILE;
    public static final File UNCRYPT_STATUS_FILE;
    private static Boolean mShutdownIsInProgress;
    private static final Object mShutdownIsInProgressLock;
    private static final Object sRequestLock;
    private final IRecoverySystem mService;

    /* loaded from: classes3.dex */
    public interface ProgressListener {
        void onProgress(int i);
    }

    /* loaded from: classes3.dex */
    public @interface ResumeOnRebootRebootErrorCode {
    }

    static {
        File file = new File("/cache/recovery");
        RECOVERY_DIR = file;
        LOG_FILE = new File(file, "log");
        COMMAND_FILE = new File(file, "command");
        BLOCK_BACKUP_FILE = new File(file, "corrupted_blocks");
        mShutdownIsInProgressLock = new Object();
        mShutdownIsInProgress = false;
        RECOVERY_RESCUEPARTY_FILE = new File(file, "rescueparty_log");
        BLOCK_MAP_FILE = new File(file, "block.map");
        UNCRYPT_PACKAGE_FILE = new File(file, "uncrypt_file");
        UNCRYPT_STATUS_FILE = new File(file, "uncrypt_status");
        sRequestLock = new Object();
    }

    private static HashSet<X509Certificate> getTrustedCerts(File keystore) throws IOException, GeneralSecurityException {
        HashSet<X509Certificate> trusted = new HashSet<>();
        if (keystore == null) {
            keystore = DEFAULT_KEYSTORE;
        }
        ZipFile zip = new ZipFile(keystore);
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            Enumeration<? extends ZipEntry> entries = zip.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                InputStream is = zip.getInputStream(entry);
                try {
                    trusted.add((X509Certificate) cf.generateCertificate(is));
                    is.close();
                } finally {
                }
            }
            return trusted;
        } finally {
            zip.close();
        }
    }

    private static long parseSuperUsedSize(File packageFile) throws IOException {
        try {
            ZipFile zip = new ZipFile(packageFile);
            try {
                ZipEntry entry = zip.getEntry("super_used_size.txt");
                if (entry == null) {
                    Log.e(TAG, "!@RecoverySystem failed to get zipEntry");
                } else {
                    InputStream inputStream = zip.getInputStream(entry);
                    if (inputStream == null) {
                        Log.e(TAG, "!@RecoverySystem failed to get inputStream");
                    } else {
                        byte[] bytes = new byte[8];
                        int len = inputStream.read(bytes);
                        if (len <= 0) {
                            Log.e(TAG, "!@RecoverySystem failed to read super_used_size");
                            inputStream.close();
                        } else {
                            String str = new String(bytes, StandardCharsets.UTF_8);
                            Log.i(TAG, "!@RecoverySystem super_used_size: " + str);
                            long parseLong = Long.parseLong(str);
                            zip.close();
                            return parseLong;
                        }
                    }
                }
                zip.close();
                zip.close();
                return 0L;
            } finally {
            }
        } catch (Exception e) {
            Log.e(TAG, "!@RecoverySystem IOException when reading package files", e);
            return 0L;
        }
    }

    public static void verifyPackage(File packageFile, ProgressListener listener, File deviceCertsZipFile) throws IOException, GeneralSecurityException {
        byte[] eocd;
        long fileLen = packageFile.length();
        RandomAccessFile raf = new RandomAccessFile(packageFile, "r");
        try {
            long startTimeMillis = System.currentTimeMillis();
            if (listener != null) {
                listener.onProgress(0);
            }
            raf.seek(fileLen - 6);
            byte[] footer = new byte[6];
            raf.readFully(footer);
            if (footer[2] != -1 || footer[3] != -1) {
                throw new SignatureException("no signature in file (no footer)");
            }
            int commentSize = (footer[4] & 255) | ((footer[5] & 255) << 8);
            int signatureStart = (footer[0] & 255) | ((footer[1] & 255) << 8);
            byte[] eocd2 = new byte[commentSize + 22];
            raf.seek(fileLen - (commentSize + 22));
            raf.readFully(eocd2);
            byte b = 80;
            if (eocd2[0] != 80 || eocd2[1] != 75 || eocd2[2] != 5 || eocd2[3] != 6) {
                throw new SignatureException("no signature in file (bad footer)");
            }
            int i = 4;
            while (i < eocd2.length - 3) {
                if (eocd2[i] == b && eocd2[i + 1] == 75 && eocd2[i + 2] == 5) {
                    if (eocd2[i + 3] == 6) {
                        throw new SignatureException("EOCD marker found after start of EOCD");
                    }
                }
                i++;
                b = 80;
            }
            PKCS7 block = new PKCS7(new ByteArrayInputStream(eocd2, (commentSize + 22) - signatureStart, signatureStart));
            X509Certificate[] certificates = block.getCertificates();
            if (certificates == null || certificates.length == 0) {
                throw new SignatureException("signature contains no certificates");
            }
            X509Certificate cert = certificates[0];
            PublicKey signatureKey = cert.getPublicKey();
            SignerInfo[] signerInfos = block.getSignerInfos();
            if (signerInfos == null || signerInfos.length == 0) {
                throw new SignatureException("signature contains no signedData");
            }
            SignerInfo signerInfo = signerInfos[0];
            boolean verified = false;
            HashSet<X509Certificate> trusted = getTrustedCerts(deviceCertsZipFile == null ? DEFAULT_KEYSTORE : deviceCertsZipFile);
            Iterator<X509Certificate> it = trusted.iterator();
            while (true) {
                if (!it.hasNext()) {
                    eocd = eocd2;
                    break;
                }
                X509Certificate c = it.next();
                eocd = eocd2;
                if (c.getPublicKey().equals(signatureKey)) {
                    verified = true;
                    break;
                }
                eocd2 = eocd;
            }
            if (!verified) {
                throw new SignatureException("signature doesn't match any trusted key");
            }
            raf.seek(0L);
            SignerInfo verifyResult = block.verify(signerInfo, new InputStream(fileLen, commentSize, startTimeMillis, raf, listener) { // from class: android.os.RecoverySystem.1
                long lastPublishTime;
                long toRead;
                final /* synthetic */ int val$commentSize;
                final /* synthetic */ long val$fileLen;
                final /* synthetic */ ProgressListener val$listenerForInner;
                final /* synthetic */ RandomAccessFile val$raf;
                final /* synthetic */ long val$startTimeMillis;
                long soFar = 0;
                int lastPercent = 0;

                AnonymousClass1(long fileLen2, int commentSize2, long startTimeMillis2, RandomAccessFile raf2, ProgressListener listener2) {
                    this.val$fileLen = fileLen2;
                    this.val$commentSize = commentSize2;
                    this.val$startTimeMillis = startTimeMillis2;
                    this.val$raf = raf2;
                    this.val$listenerForInner = listener2;
                    this.toRead = (fileLen2 - commentSize2) - 2;
                    this.lastPublishTime = startTimeMillis2;
                }

                @Override // java.io.InputStream
                public int read() throws IOException {
                    throw new UnsupportedOperationException();
                }

                @Override // java.io.InputStream
                public int read(byte[] b2, int off, int len) throws IOException {
                    if (this.soFar >= this.toRead || Thread.currentThread().isInterrupted()) {
                        return -1;
                    }
                    int size = len;
                    long j = this.soFar;
                    long j2 = size + j;
                    long j3 = this.toRead;
                    if (j2 > j3) {
                        size = (int) (j3 - j);
                    }
                    int read = this.val$raf.read(b2, off, size);
                    this.soFar += read;
                    if (this.val$listenerForInner != null) {
                        long now = System.currentTimeMillis();
                        int p = (int) ((this.soFar * 100) / this.toRead);
                        if (p > this.lastPercent && now - this.lastPublishTime > RecoverySystem.PUBLISH_PROGRESS_INTERVAL_MS) {
                            this.lastPercent = p;
                            this.lastPublishTime = now;
                            this.val$listenerForInner.onProgress(p);
                        }
                    }
                    return read;
                }
            });
            boolean interrupted = Thread.interrupted();
            if (listener2 != null) {
                listener2.onProgress(100);
            }
            if (interrupted) {
                throw new SignatureException("verification was interrupted");
            }
            if (verifyResult == null) {
                throw new SignatureException("signature digest verification failed");
            }
            raf2.close();
            if (!readAndVerifyPackageCompatibilityEntry(packageFile)) {
                throw new SignatureException("package compatibility verification failed");
            }
        } catch (Throwable th) {
            raf2.close();
            throw th;
        }
    }

    /* renamed from: android.os.RecoverySystem$1 */
    /* loaded from: classes3.dex */
    class AnonymousClass1 extends InputStream {
        long lastPublishTime;
        long toRead;
        final /* synthetic */ int val$commentSize;
        final /* synthetic */ long val$fileLen;
        final /* synthetic */ ProgressListener val$listenerForInner;
        final /* synthetic */ RandomAccessFile val$raf;
        final /* synthetic */ long val$startTimeMillis;
        long soFar = 0;
        int lastPercent = 0;

        AnonymousClass1(long fileLen2, int commentSize2, long startTimeMillis2, RandomAccessFile raf2, ProgressListener listener2) {
            this.val$fileLen = fileLen2;
            this.val$commentSize = commentSize2;
            this.val$startTimeMillis = startTimeMillis2;
            this.val$raf = raf2;
            this.val$listenerForInner = listener2;
            this.toRead = (fileLen2 - commentSize2) - 2;
            this.lastPublishTime = startTimeMillis2;
        }

        @Override // java.io.InputStream
        public int read() throws IOException {
            throw new UnsupportedOperationException();
        }

        @Override // java.io.InputStream
        public int read(byte[] b2, int off, int len) throws IOException {
            if (this.soFar >= this.toRead || Thread.currentThread().isInterrupted()) {
                return -1;
            }
            int size = len;
            long j = this.soFar;
            long j2 = size + j;
            long j3 = this.toRead;
            if (j2 > j3) {
                size = (int) (j3 - j);
            }
            int read = this.val$raf.read(b2, off, size);
            this.soFar += read;
            if (this.val$listenerForInner != null) {
                long now = System.currentTimeMillis();
                int p = (int) ((this.soFar * 100) / this.toRead);
                if (p > this.lastPercent && now - this.lastPublishTime > RecoverySystem.PUBLISH_PROGRESS_INTERVAL_MS) {
                    this.lastPercent = p;
                    this.lastPublishTime = now;
                    this.val$listenerForInner.onProgress(p);
                }
            }
            return read;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0052, code lost:
    
        throw new java.io.IOException("invalid entry size (" + r4 + ") in the compatibility file");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static boolean verifyPackageCompatibility(java.io.InputStream r8) throws java.io.IOException {
        /*
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            java.util.zip.ZipInputStream r1 = new java.util.zip.ZipInputStream
            r1.<init>(r8)
        La:
            java.util.zip.ZipEntry r2 = r1.getNextEntry()
            r3 = r2
            if (r2 == 0) goto L53
            long r4 = r3.getSize()
            r6 = 2147483647(0x7fffffff, double:1.060997895E-314)
            int r2 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r2 > 0) goto L33
            r6 = 0
            int r2 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r2 < 0) goto L33
            int r2 = (int) r4
            byte[] r2 = new byte[r2]
            libcore.io.Streams.readFully(r1, r2)
            java.lang.String r6 = new java.lang.String
            java.nio.charset.Charset r7 = java.nio.charset.StandardCharsets.UTF_8
            r6.<init>(r2, r7)
            r0.add(r6)
            goto La
        L33:
            java.io.IOException r2 = new java.io.IOException
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "invalid entry size ("
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.StringBuilder r6 = r6.append(r4)
            java.lang.String r7 = ") in the compatibility file"
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.String r6 = r6.toString()
            r2.<init>(r6)
            throw r2
        L53:
            boolean r2 = r0.isEmpty()
            if (r2 != 0) goto L6f
            int r2 = r0.size()
            java.lang.String[] r2 = new java.lang.String[r2]
            java.lang.Object[] r2 = r0.toArray(r2)
            java.lang.String[] r2 = (java.lang.String[]) r2
            int r2 = android.os.VintfObject.verify(r2)
            if (r2 != 0) goto L6d
            r2 = 1
            goto L6e
        L6d:
            r2 = 0
        L6e:
            return r2
        L6f:
            java.io.IOException r2 = new java.io.IOException
            java.lang.String r4 = "no entries found in the compatibility file"
            r2.<init>(r4)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.os.RecoverySystem.verifyPackageCompatibility(java.io.InputStream):boolean");
    }

    private static boolean readAndVerifyPackageCompatibilityEntry(File packageFile) throws IOException {
        ZipFile zip = new ZipFile(packageFile);
        try {
            ZipEntry entry = zip.getEntry("compatibility.zip");
            if (entry != null) {
                InputStream inputStream = zip.getInputStream(entry);
                boolean verifyPackageCompatibility = verifyPackageCompatibility(inputStream);
                zip.close();
                return verifyPackageCompatibility;
            }
            zip.close();
            return true;
        } catch (Throwable th) {
            try {
                zip.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    @SystemApi
    public static boolean verifyPackageCompatibility(File compatibilityFile) throws IOException {
        InputStream inputStream = new FileInputStream(compatibilityFile);
        try {
            boolean verifyPackageCompatibility = verifyPackageCompatibility(inputStream);
            inputStream.close();
            return verifyPackageCompatibility;
        } catch (Throwable th) {
            try {
                inputStream.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    @SystemApi
    public static void processPackage(Context context, File packageFile, ProgressListener listener, Handler handler) throws IOException {
        Handler progressHandler;
        String filename = packageFile.getCanonicalPath();
        if (!filename.startsWith("/data/")) {
            return;
        }
        RecoverySystem rs = (RecoverySystem) context.getSystemService("recovery");
        IRecoverySystemProgressListener progressListener = null;
        if (listener != null) {
            if (handler != null) {
                progressHandler = handler;
            } else {
                progressHandler = new Handler(context.getMainLooper());
            }
            progressListener = new AnonymousClass2(progressHandler, listener);
        }
        if (!rs.uncrypt(filename, progressListener)) {
            throw new IOException("process package failed");
        }
    }

    /* renamed from: android.os.RecoverySystem$2 */
    /* loaded from: classes3.dex */
    public class AnonymousClass2 extends IRecoverySystemProgressListener.Stub {
        int lastProgress = 0;
        long lastPublishTime = System.currentTimeMillis();
        final /* synthetic */ ProgressListener val$listener;
        final /* synthetic */ Handler val$progressHandler;

        AnonymousClass2(Handler handler, ProgressListener progressListener) {
            this.val$progressHandler = handler;
            this.val$listener = progressListener;
        }

        /* renamed from: android.os.RecoverySystem$2$1 */
        /* loaded from: classes3.dex */
        class AnonymousClass1 implements Runnable {
            final /* synthetic */ long val$now;
            final /* synthetic */ int val$progress;

            AnonymousClass1(int i, long j) {
                progress = i;
                now = j;
            }

            @Override // java.lang.Runnable
            public void run() {
                if (progress > AnonymousClass2.this.lastProgress && now - AnonymousClass2.this.lastPublishTime > RecoverySystem.PUBLISH_PROGRESS_INTERVAL_MS) {
                    AnonymousClass2.this.lastProgress = progress;
                    AnonymousClass2.this.lastPublishTime = now;
                    AnonymousClass2.this.val$listener.onProgress(progress);
                }
            }
        }

        @Override // android.os.IRecoverySystemProgressListener
        public void onProgress(int progress) {
            long now = System.currentTimeMillis();
            this.val$progressHandler.post(new Runnable() { // from class: android.os.RecoverySystem.2.1
                final /* synthetic */ long val$now;
                final /* synthetic */ int val$progress;

                AnonymousClass1(int progress2, long now2) {
                    progress = progress2;
                    now = now2;
                }

                @Override // java.lang.Runnable
                public void run() {
                    if (progress > AnonymousClass2.this.lastProgress && now - AnonymousClass2.this.lastPublishTime > RecoverySystem.PUBLISH_PROGRESS_INTERVAL_MS) {
                        AnonymousClass2.this.lastProgress = progress;
                        AnonymousClass2.this.lastPublishTime = now;
                        AnonymousClass2.this.val$listener.onProgress(progress);
                    }
                }
            });
        }
    }

    @SystemApi
    public static void processPackage(Context context, File packageFile, ProgressListener listener) throws IOException {
        processPackage(context, packageFile, listener, null);
    }

    public static void installPackage(Context context, File packageFile) throws IOException {
        installPackage(context, packageFile, false);
    }

    /* JADX WARN: Removed duplicated region for block: B:70:0x0290 A[Catch: all -> 0x0303, TryCatch #3 {, blocks: (B:4:0x0005, B:6:0x0060, B:8:0x006e, B:9:0x0079, B:10:0x0087, B:15:0x008e, B:19:0x0097, B:20:0x00a5, B:21:0x00a6, B:24:0x00c1, B:26:0x00cb, B:28:0x00e9, B:29:0x00d1, B:32:0x00f3, B:33:0x00f7, B:34:0x00f8, B:36:0x014a, B:37:0x015e, B:39:0x016a, B:41:0x01b6, B:42:0x01ca, B:45:0x01d1, B:49:0x01f4, B:51:0x01fa, B:100:0x0202, B:57:0x0224, B:59:0x022d, B:61:0x0234, B:66:0x0252, B:68:0x0278, B:70:0x0290, B:72:0x02a4, B:73:0x02b8, B:74:0x02c2, B:78:0x0271, B:91:0x026b, B:90:0x0268, B:97:0x02c3, B:98:0x02d3, B:53:0x020a, B:112:0x02ee, B:111:0x02eb, B:115:0x02f4, B:116:0x0302, B:121:0x017f, B:123:0x018b, B:124:0x01a0, B:23:0x00ab), top: B:3:0x0005, inners: #1 }] */
    @android.annotation.SystemApi
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void installPackage(android.content.Context r18, java.io.File r19, boolean r20) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 774
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.os.RecoverySystem.installPackage(android.content.Context, java.io.File, boolean):void");
    }

    @SystemApi
    public static void prepareForUnattendedUpdate(Context context, String updateToken, IntentSender intentSender) throws IOException {
        if (updateToken == null) {
            throw new NullPointerException("updateToken == null");
        }
        KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(KeyguardManager.class);
        if (keyguardManager == null || !keyguardManager.isDeviceSecure()) {
            throw new IOException("Failed to request LSKF because the device doesn't have a lock screen. ");
        }
        RecoverySystem rs = (RecoverySystem) context.getSystemService("recovery");
        if (!rs.requestLskf(context.getPackageName(), intentSender)) {
            throw new IOException("preparation for update failed");
        }
    }

    @SystemApi
    public static void clearPrepareForUnattendedUpdate(Context context) throws IOException {
        RecoverySystem rs = (RecoverySystem) context.getSystemService("recovery");
        if (!rs.clearLskf(context.getPackageName())) {
            throw new IOException("could not reset unattended update state");
        }
    }

    @SystemApi
    public static void rebootAndApply(Context context, String updateToken, String reason) throws IOException {
        if (updateToken == null) {
            throw new NullPointerException("updateToken == null");
        }
        RecoverySystem rs = (RecoverySystem) context.getSystemService("recovery");
        if (rs.rebootWithLskfAssumeSlotSwitch(context.getPackageName(), reason) != 0) {
            throw new IOException("system not prepared to apply update");
        }
    }

    @SystemApi
    public static boolean isPreparedForUnattendedUpdate(Context context) throws IOException {
        RecoverySystem rs = (RecoverySystem) context.getSystemService(RecoverySystem.class);
        return rs.isLskfCaptured(context.getPackageName());
    }

    @SystemApi
    public static int rebootAndApply(Context context, String reason, boolean slotSwitch) throws IOException {
        RecoverySystem rs = (RecoverySystem) context.getSystemService(RecoverySystem.class);
        return rs.rebootWithLskf(context.getPackageName(), reason, slotSwitch);
    }

    @SystemApi
    public static void scheduleUpdateOnBoot(Context context, File packageFile) throws IOException {
        String filename = packageFile.getCanonicalPath();
        boolean securityUpdate = filename.endsWith("_s.zip");
        if (filename.startsWith("/data/")) {
            filename = "@/cache/recovery/block.map";
        }
        String filenameArg = "--update_package=" + filename + "\n";
        String localeArg = "--locale=" + Locale.getDefault().toLanguageTag() + "\n";
        String command = filenameArg + localeArg;
        if (securityUpdate) {
            command = command + "--security\n";
        }
        RecoverySystem rs = (RecoverySystem) context.getSystemService("recovery");
        if (!rs.setupBcb(command)) {
            throw new IOException("schedule update on boot failed");
        }
    }

    @SystemApi
    public static void cancelScheduledUpdate(Context context) throws IOException {
        RecoverySystem rs = (RecoverySystem) context.getSystemService("recovery");
        if (!rs.clearBcb()) {
            throw new IOException("cancel scheduled update failed");
        }
    }

    public static void rebootWipeUserData(Context context) throws IOException {
        rebootWipeUserData(context, false, context.getPackageName(), false, false);
    }

    public static void rebootWipeUserData(Context context, String reason) throws IOException {
        rebootWipeUserData(context, false, reason, false, false);
    }

    public static void rebootWipeUserData(Context context, boolean shutdown) throws IOException {
        rebootWipeUserData(context, shutdown, context.getPackageName(), false, false);
    }

    public static void rebootWipeUserData(Context context, boolean shutdown, String reason, boolean force) throws IOException {
        rebootWipeUserData(context, shutdown, reason, force, false);
    }

    public static void rebootWipeUserData(Context context, boolean shutdown, String reason, boolean force, boolean wipeEuicc) throws IOException {
        rebootWipeUserData(context, shutdown, reason, force, wipeEuicc, null);
    }

    public static void rebootWipeUserData(Context context, boolean shutdown, String reason, boolean force, boolean wipeEuicc, String extraCmd) throws IOException {
        String shutdownArg;
        String timeStamp;
        String extraCmdArg;
        RestrictionPolicy restrPol;
        Log.i(TAG, "rebootWipeUserData++");
        if (force || (restrPol = EnterpriseDeviceManager.getInstance().getRestrictionPolicy()) == null || restrPol.isFactoryResetAllowed()) {
            UserManager um = (UserManager) context.getSystemService("user");
            if (!force && um.hasUserRestriction(UserManager.DISALLOW_FACTORY_RESET)) {
                AuditLog.log(5, 1, false, Process.myPid(), TAG, AuditEvents.AUDIT_WIPING_DATA_IS_NOT_ALLOWED_FOR_THIS_USER);
                throw new SecurityException("Wiping data is not allowed for this user.");
            }
            ConditionVariable condition = new ConditionVariable();
            HandlerThread hthread = new HandlerThread(TAG);
            Log.i(TAG, "rebootWipeUserData: run handler " + hthread);
            hthread.start();
            Log.i(TAG, "rebootWipeUserData: sendOrderedBroadcastAsUser");
            Intent intent = new Intent(Intent.ACTION_MASTER_CLEAR_NOTIFICATION);
            intent.addFlags(285212672);
            context.sendOrderedBroadcastAsUser(intent, UserHandle.SYSTEM, Manifest.permission.MASTER_CLEAR, new BroadcastReceiver() { // from class: android.os.RecoverySystem.3
                AnonymousClass3() {
                }

                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context2, Intent intent2) {
                    Log.i(RecoverySystem.TAG, "rebootWipeUserData: onReceive");
                    ConditionVariable.this.open();
                }
            }, new Handler(hthread.getLooper()), 0, null, null);
            Log.i(TAG, "rebootWipeUserData: wait intent to complete");
            condition.block();
            Log.i(TAG, "rebootWipeUserData: continue..");
            hthread.quitSafely();
            EuiccManager euiccManager = (EuiccManager) context.getSystemService(EuiccManager.class);
            if (wipeEuicc) {
                wipeEuiccData(context, "android");
            } else {
                removeEuiccInvisibleSubs(context, euiccManager);
            }
            if (!shutdown) {
                shutdownArg = null;
            } else {
                shutdownArg = "--shutdown_after";
            }
            if (TextUtils.isEmpty(reason)) {
                timeStamp = null;
            } else {
                String timeStamp2 = DateFormat.format("yyyy-MM-ddTHH:mm:ssZ", System.currentTimeMillis()).toString();
                String reasonArg = "--reason=" + sanitizeArg(reason + "," + timeStamp2);
                timeStamp = reasonArg;
            }
            String localeArg = "--locale=" + Locale.getDefault().toLanguageTag();
            if (TextUtils.isEmpty(extraCmd)) {
                extraCmdArg = "";
            } else {
                String extraCmdArg2 = "--" + sanitizeArg(extraCmd);
                extraCmdArg = extraCmdArg2;
            }
            try {
                AuditLog.log(5, 1, true, Process.myPid(), TAG, AuditEvents.AUDIT_STARTING_USER_DATA_WIPE);
                Log.d(TAG, "!@[RecoverySystem] rebootWipeUserData: wipeDataArg:[" + RECOVERY_WIPE_DATA_COMMAND + "], extraCmdArg:[" + extraCmdArg + NavigationBarInflaterView.SIZE_MOD_END);
                bootCommand(context, shutdownArg, RECOVERY_WIPE_DATA_COMMAND, extraCmdArg, timeStamp, localeArg);
                return;
            } catch (IOException ioE) {
                AuditLog.log(5, 1, false, Process.myPid(), TAG, String.format(AuditEvents.AUDIT_FAILED_TO_WIPE_USER_DATA, ioE.getMessage()));
                throw ioE;
            }
        }
        AuditLog.log(5, 1, false, Process.myPid(), TAG, AuditEvents.AUDIT_WIPING_DATA_IS_NOT_ALLOWED_FOR_THIS_USER);
        throw new SecurityException("Wiping data is not allowed due to restriction policy.");
    }

    /* renamed from: android.os.RecoverySystem$3 */
    /* loaded from: classes3.dex */
    public class AnonymousClass3 extends BroadcastReceiver {
        AnonymousClass3() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context2, Intent intent2) {
            Log.i(RecoverySystem.TAG, "rebootWipeUserData: onReceive");
            ConditionVariable.this.open();
        }
    }

    public static boolean wipeEuiccData(Context context, String packageName) {
        long waitingTimeMillis;
        ContentResolver cr = context.getContentResolver();
        if (Settings.Global.getInt(cr, Settings.Global.EUICC_PROVISIONED, 0) == 0) {
            Log.d(TAG, "Skipping eUICC wipe/retain as it is not provisioned");
            return true;
        }
        EuiccManager euiccManager = (EuiccManager) context.getSystemService(Context.EUICC_SERVICE);
        if (euiccManager == null || !euiccManager.isEnabled()) {
            return false;
        }
        CountDownLatch euiccFactoryResetLatch = new CountDownLatch(1);
        AtomicBoolean wipingSucceeded = new AtomicBoolean(false);
        BroadcastReceiver euiccWipeFinishReceiver = new BroadcastReceiver() { // from class: android.os.RecoverySystem.4
            final /* synthetic */ CountDownLatch val$euiccFactoryResetLatch;
            final /* synthetic */ AtomicBoolean val$wipingSucceeded;

            AnonymousClass4(AtomicBoolean wipingSucceeded2, CountDownLatch euiccFactoryResetLatch2) {
                wipingSucceeded = wipingSucceeded2;
                euiccFactoryResetLatch = euiccFactoryResetLatch2;
            }

            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                if (RecoverySystem.ACTION_EUICC_FACTORY_RESET.equals(intent.getAction())) {
                    if (getResultCode() != 0) {
                        int detailedCode = intent.getIntExtra(EuiccManager.EXTRA_EMBEDDED_SUBSCRIPTION_DETAILED_CODE, 0);
                        Log.e(RecoverySystem.TAG, "Error wiping euicc data, Detailed code = " + detailedCode);
                    } else {
                        Log.d(RecoverySystem.TAG, "Successfully wiped euicc data.");
                        wipingSucceeded.set(true);
                    }
                    euiccFactoryResetLatch.countDown();
                }
            }
        };
        Intent intent = new Intent(ACTION_EUICC_FACTORY_RESET);
        intent.setPackage(packageName);
        PendingIntent callbackIntent = PendingIntent.getBroadcastAsUser(context, 0, intent, Enums.AUDIO_FORMAT_DTS_HD, UserHandle.SYSTEM);
        IntentFilter filterConsent = new IntentFilter();
        filterConsent.addAction(ACTION_EUICC_FACTORY_RESET);
        HandlerThread euiccHandlerThread = new HandlerThread("euiccWipeFinishReceiverThread");
        euiccHandlerThread.start();
        Handler euiccHandler = new Handler(euiccHandlerThread.getLooper());
        context.getApplicationContext().registerReceiver(euiccWipeFinishReceiver, filterConsent, null, euiccHandler);
        euiccManager.eraseSubscriptions(callbackIntent);
        try {
        } catch (InterruptedException e) {
            e = e;
        } catch (Throwable th) {
            e = th;
        }
        try {
            waitingTimeMillis = Settings.Global.getLong(context.getContentResolver(), Settings.Global.EUICC_FACTORY_RESET_TIMEOUT_MILLIS, 30000L);
            if (waitingTimeMillis < 5000) {
                waitingTimeMillis = 5000;
            } else if (waitingTimeMillis > 60000) {
                waitingTimeMillis = 60000;
            }
        } catch (InterruptedException e2) {
            e = e2;
        } catch (Throwable th2) {
            e = th2;
            context.getApplicationContext().unregisterReceiver(euiccWipeFinishReceiver);
            throw e;
        }
        try {
            try {
                if (euiccFactoryResetLatch2.await(waitingTimeMillis, TimeUnit.MILLISECONDS)) {
                    context.getApplicationContext().unregisterReceiver(euiccWipeFinishReceiver);
                    return wipingSucceeded2.get();
                }
                Log.e(TAG, "Timeout wiping eUICC data.");
                context.getApplicationContext().unregisterReceiver(euiccWipeFinishReceiver);
                return false;
            } catch (Throwable th3) {
                e = th3;
                context.getApplicationContext().unregisterReceiver(euiccWipeFinishReceiver);
                throw e;
            }
        } catch (InterruptedException e3) {
            e = e3;
            Thread.currentThread().interrupt();
            Log.e(TAG, "Wiping eUICC data interrupted", e);
            context.getApplicationContext().unregisterReceiver(euiccWipeFinishReceiver);
            return false;
        }
    }

    /* renamed from: android.os.RecoverySystem$4 */
    /* loaded from: classes3.dex */
    public class AnonymousClass4 extends BroadcastReceiver {
        final /* synthetic */ CountDownLatch val$euiccFactoryResetLatch;
        final /* synthetic */ AtomicBoolean val$wipingSucceeded;

        AnonymousClass4(AtomicBoolean wipingSucceeded2, CountDownLatch euiccFactoryResetLatch2) {
            wipingSucceeded = wipingSucceeded2;
            euiccFactoryResetLatch = euiccFactoryResetLatch2;
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context2, Intent intent) {
            if (RecoverySystem.ACTION_EUICC_FACTORY_RESET.equals(intent.getAction())) {
                if (getResultCode() != 0) {
                    int detailedCode = intent.getIntExtra(EuiccManager.EXTRA_EMBEDDED_SUBSCRIPTION_DETAILED_CODE, 0);
                    Log.e(RecoverySystem.TAG, "Error wiping euicc data, Detailed code = " + detailedCode);
                } else {
                    Log.d(RecoverySystem.TAG, "Successfully wiped euicc data.");
                    wipingSucceeded.set(true);
                }
                euiccFactoryResetLatch.countDown();
            }
        }
    }

    private static void removeEuiccInvisibleSubs(Context context, EuiccManager euiccManager) {
        ContentResolver cr = context.getContentResolver();
        if (Settings.Global.getInt(cr, Settings.Global.EUICC_PROVISIONED, 0) == 0) {
            Log.i(TAG, "Skip removing eUICC invisible profiles as it is not provisioned.");
            return;
        }
        if (euiccManager == null || !euiccManager.isEnabled()) {
            Log.i(TAG, "Skip removing eUICC invisible profiles as eUICC manager is not available.");
            return;
        }
        SubscriptionManager subscriptionManager = (SubscriptionManager) context.getSystemService(SubscriptionManager.class);
        List<SubscriptionInfo> availableSubs = subscriptionManager.getAvailableSubscriptionInfoList();
        if (availableSubs == null || availableSubs.isEmpty()) {
            Log.i(TAG, "Skip removing eUICC invisible profiles as no available profiles found.");
            return;
        }
        List<SubscriptionInfo> invisibleSubs = new ArrayList<>();
        for (SubscriptionInfo sub : availableSubs) {
            if (sub.isEmbedded() && sub.getGroupUuid() != null && sub.isOpportunistic()) {
                invisibleSubs.add(sub);
            }
        }
        removeEuiccInvisibleSubs(context, invisibleSubs, euiccManager);
    }

    private static boolean removeEuiccInvisibleSubs(Context context, List<SubscriptionInfo> subscriptionInfos, EuiccManager euiccManager) {
        if (subscriptionInfos != null && !subscriptionInfos.isEmpty()) {
            CountDownLatch removeSubsLatch = new CountDownLatch(subscriptionInfos.size());
            AtomicInteger removedSubsCount = new AtomicInteger(0);
            BroadcastReceiver removeEuiccSubsReceiver = new BroadcastReceiver() { // from class: android.os.RecoverySystem.5
                final /* synthetic */ CountDownLatch val$removeSubsLatch;
                final /* synthetic */ AtomicInteger val$removedSubsCount;

                AnonymousClass5(AtomicInteger removedSubsCount2, CountDownLatch removeSubsLatch2) {
                    removedSubsCount = removedSubsCount2;
                    removeSubsLatch = removeSubsLatch2;
                }

                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context2, Intent intent) {
                    if (RecoverySystem.ACTION_EUICC_REMOVE_INVISIBLE_SUBSCRIPTIONS.equals(intent.getAction())) {
                        if (getResultCode() != 0) {
                            int detailedCode = intent.getIntExtra(EuiccManager.EXTRA_EMBEDDED_SUBSCRIPTION_DETAILED_CODE, 0);
                            Log.e(RecoverySystem.TAG, "Error removing euicc opportunistic profile, Detailed code = " + detailedCode);
                        } else {
                            Log.e(RecoverySystem.TAG, "Successfully remove euicc opportunistic profile.");
                            removedSubsCount.incrementAndGet();
                        }
                        removeSubsLatch.countDown();
                    }
                }
            };
            Intent intent = new Intent(ACTION_EUICC_REMOVE_INVISIBLE_SUBSCRIPTIONS);
            intent.setPackage("android");
            PendingIntent callbackIntent = PendingIntent.getBroadcastAsUser(context, 0, intent, Enums.AUDIO_FORMAT_DTS_HD, UserHandle.SYSTEM);
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(ACTION_EUICC_REMOVE_INVISIBLE_SUBSCRIPTIONS);
            HandlerThread euiccHandlerThread = new HandlerThread("euiccRemovingSubsReceiverThread");
            euiccHandlerThread.start();
            Handler euiccHandler = new Handler(euiccHandlerThread.getLooper());
            context.getApplicationContext().registerReceiver(removeEuiccSubsReceiver, intentFilter, null, euiccHandler);
            for (SubscriptionInfo subscriptionInfo : subscriptionInfos) {
                Log.i(TAG, "Remove invisible subscription " + subscriptionInfo.getSubscriptionId() + " from card " + subscriptionInfo.getCardId());
                euiccManager.createForCardId(subscriptionInfo.getCardId()).deleteSubscription(subscriptionInfo.getSubscriptionId(), callbackIntent);
            }
            try {
                long waitingTimeMillis = Settings.Global.getLong(context.getContentResolver(), Settings.Global.EUICC_REMOVING_INVISIBLE_PROFILES_TIMEOUT_MILLIS, DEFAULT_EUICC_REMOVING_INVISIBLE_PROFILES_TIMEOUT_MILLIS);
                if (waitingTimeMillis < MIN_EUICC_REMOVING_INVISIBLE_PROFILES_TIMEOUT_MILLIS) {
                    waitingTimeMillis = MIN_EUICC_REMOVING_INVISIBLE_PROFILES_TIMEOUT_MILLIS;
                } else if (waitingTimeMillis > MAX_EUICC_REMOVING_INVISIBLE_PROFILES_TIMEOUT_MILLIS) {
                    waitingTimeMillis = MAX_EUICC_REMOVING_INVISIBLE_PROFILES_TIMEOUT_MILLIS;
                }
                if (!removeSubsLatch2.await(waitingTimeMillis, TimeUnit.MILLISECONDS)) {
                    Log.e(TAG, "Timeout removing invisible euicc profiles.");
                    return false;
                }
                context.getApplicationContext().unregisterReceiver(removeEuiccSubsReceiver);
                euiccHandlerThread.quit();
                return removedSubsCount2.get() == subscriptionInfos.size();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                Log.e(TAG, "Removing invisible euicc profiles interrupted", e);
                return false;
            } finally {
                context.getApplicationContext().unregisterReceiver(removeEuiccSubsReceiver);
                euiccHandlerThread.quit();
            }
        }
        Log.i(TAG, "There are no eUICC invisible profiles needed to be removed.");
        return true;
    }

    /* renamed from: android.os.RecoverySystem$5 */
    /* loaded from: classes3.dex */
    public class AnonymousClass5 extends BroadcastReceiver {
        final /* synthetic */ CountDownLatch val$removeSubsLatch;
        final /* synthetic */ AtomicInteger val$removedSubsCount;

        AnonymousClass5(AtomicInteger removedSubsCount2, CountDownLatch removeSubsLatch2) {
            removedSubsCount = removedSubsCount2;
            removeSubsLatch = removeSubsLatch2;
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context2, Intent intent) {
            if (RecoverySystem.ACTION_EUICC_REMOVE_INVISIBLE_SUBSCRIPTIONS.equals(intent.getAction())) {
                if (getResultCode() != 0) {
                    int detailedCode = intent.getIntExtra(EuiccManager.EXTRA_EMBEDDED_SUBSCRIPTION_DETAILED_CODE, 0);
                    Log.e(RecoverySystem.TAG, "Error removing euicc opportunistic profile, Detailed code = " + detailedCode);
                } else {
                    Log.e(RecoverySystem.TAG, "Successfully remove euicc opportunistic profile.");
                    removedSubsCount.incrementAndGet();
                }
                removeSubsLatch.countDown();
            }
        }
    }

    public static void rebootPromptAndWipeUserData(Context context, String reason) throws IOException {
        boolean checkpointing = false;
        IVold vold = null;
        try {
            vold = IVold.Stub.asInterface(ServiceManager.checkService("vold"));
            if (vold != null) {
                checkpointing = vold.needsCheckpoint();
            } else {
                Log.w(TAG, "Failed to get vold");
            }
        } catch (Exception e) {
            Log.w(TAG, "Failed to check for checkpointing");
        }
        if (checkpointing) {
            try {
                vold.abortChanges("rescueparty", false);
                Log.i(TAG, "Rescue Party requested wipe. Aborting update");
                return;
            } catch (Exception e2) {
                Log.i(TAG, "Rescue Party requested wipe. Rebooting instead.");
                PowerManager pm = (PowerManager) context.getSystemService("power");
                pm.reboot("rescueparty");
                return;
            }
        }
        String reasonArg = null;
        if (!TextUtils.isEmpty(reason)) {
            reasonArg = "--reason=" + sanitizeArg(reason);
        }
        String localeArg = "--locale=" + Locale.getDefault().toString();
        bootCommand(context, null, "--prompt_and_wipe_data", reasonArg, localeArg);
    }

    public static void rebootPromptAndWipeAppData(Context context, String reason) throws IOException {
        boolean checkpointing = false;
        IVold vold = null;
        try {
            vold = IVold.Stub.asInterface(ServiceManager.checkService("vold"));
            if (vold != null) {
                checkpointing = vold.needsCheckpoint();
            } else {
                Log.w(TAG, "Failed to get vold");
            }
        } catch (Exception e) {
            Log.w(TAG, "Failed to check for checkpointing");
        }
        if (checkpointing) {
            try {
                vold.abortChanges("rescueparty", false);
                Log.i(TAG, "Rescue Party requested wipe. Aborting update");
                return;
            } catch (Exception e2) {
                Log.i(TAG, "Rescue Party requested wipe. Rebooting instead.");
                PowerManager pm = (PowerManager) context.getSystemService("power");
                pm.reboot("rescueparty");
                return;
            }
        }
        String reasonArg = null;
        if (!TextUtils.isEmpty(reason)) {
            reasonArg = "--reason=" + sanitizeArg(reason);
        }
        String localeArg = "--locale=" + Locale.getDefault().toString();
        bootCommand(context, null, "--prompt_and_wipe_app_data", reasonArg, localeArg);
    }

    public static void rebootWipeCache(Context context) throws IOException {
        rebootWipeCache(context, context.getPackageName());
    }

    public static void rebootWipeCache(Context context, String reason) throws IOException {
        String reasonArg = null;
        if (!TextUtils.isEmpty(reason)) {
            reasonArg = "--reason=" + sanitizeArg(reason);
        }
        String localeArg = "--locale=" + Locale.getDefault().toLanguageTag();
        bootCommand(context, "--wipe_cache", reasonArg, localeArg);
    }

    @SystemApi
    public static void rebootWipeAb(Context context, File packageFile, String reason) throws IOException {
        String reasonArg = null;
        if (!TextUtils.isEmpty(reason)) {
            reasonArg = "--reason=" + sanitizeArg(reason);
        }
        String filename = packageFile.getCanonicalPath();
        String filenameArg = "--wipe_package=" + filename;
        String localeArg = "--locale=" + Locale.getDefault().toLanguageTag();
        bootCommand(context, "--wipe_ab", filenameArg, reasonArg, localeArg);
    }

    public static void rebootWipeCustomerPartition(Context context, String arg, String reason) throws IOException {
        String reasonArg = null;
        if (!TextUtils.isEmpty(reason)) {
            reasonArg = "--reason=" + sanitizeArg(reason);
        }
        bootCommand(context, arg, reasonArg);
    }

    private static String getRecoveryReason(String arg) {
        int idx = arg.indexOf("=");
        try {
            String reason = arg.substring(idx + 1);
            return reason;
        } catch (StringIndexOutOfBoundsException e) {
            Log.e(TAG, "StringIndexOutOfBoundsException when splitting recovery cause:", e);
            return null;
        }
    }

    private static void bootCommand(Context context, String... args) throws IOException {
        FileOutputStream fos;
        synchronized (mShutdownIsInProgressLock) {
            if (mShutdownIsInProgress.booleanValue()) {
                return;
            }
            mShutdownIsInProgress = true;
            Log.i(TAG, "!@[RecoverySystem] bootCommand: " + Arrays.toString(args));
            boolean isForcedWipe = Arrays.toString(args) != null && Arrays.toString(args).contains(RECOVERY_WIPE_DATA_COMMAND);
            synchronized (sRequestLock) {
                StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
                StringBuilder sb = new StringBuilder();
                sb.append("!@[RecoverySystem] ");
                for (StackTraceElement st : stackTraceElements) {
                    sb.append(st.toString() + "\n");
                }
                Log.i(TAG, sb.toString());
                RECOVERY_DIR.mkdirs();
                COMMAND_FILE.delete();
                String recovery_cause = null;
                LOG_FILE.delete();
                int retryCount = 3;
                while (true) {
                    RandomAccessFile command = new RandomAccessFile(COMMAND_FILE, "rwd");
                    try {
                        for (String arg : args) {
                            if (!TextUtils.isEmpty(arg)) {
                                command.writeBytes(arg);
                                command.writeBytes("\n");
                                if (arg.startsWith("--reason=")) {
                                    recovery_cause = getRecoveryReason(arg);
                                }
                            }
                        }
                        Log.i(TAG, "!@[RecoverySystem] bootCommand: before fsync syscall!!");
                        command.getFD().sync();
                        Log.i(TAG, "!@[RecoverySystem] bootCommand: after fsync syscall!!");
                        command.close();
                        retryCount--;
                        if (COMMAND_FILE.exists()) {
                            Log.i(TAG, "COMMAND_FILE is created!!");
                            break;
                        } else {
                            Log.i(TAG, "retryCount : " + retryCount);
                            if (retryCount == 0) {
                                break;
                            }
                        }
                    } catch (Throwable th) {
                        command.close();
                        throw th;
                    }
                }
                if (!COMMAND_FILE.exists()) {
                    Log.i(TAG, "!@[RecoverySystem] bootCommand: command file absent, throw exception");
                    throw new IOException("Reboot failed (unable to create command file)");
                }
                if (isForcedWipe) {
                    deleteSecrets();
                }
                PowerManager pm = (PowerManager) context.getSystemService("power");
                String reason = SystemProperties.get("persist.sys.reboot.reason");
                if ("nvrecovery".equals(reason)) {
                    Log.i(TAG, "FactoryTest ->nvrecovery ");
                    pm.reboot("nvrecovery");
                } else if (Context.DOWNLOAD_SERVICE.equals(reason)) {
                    Log.i(TAG, "FactoryTest ->download ");
                    pm.reboot(Context.DOWNLOAD_SERVICE);
                } else {
                    Log.d(TAG, "calling pm.reboot");
                    if (recovery_cause == null) {
                        recovery_cause = "bootCommand()";
                    }
                    Log.d(TAG, "!@[RecoverySystem] bootCommand: [reset tracking] write to recovery_cause : " + recovery_cause);
                    try {
                        fos = new FileOutputStream("/sys/class/sec/sec_debug/recovery_cause");
                    } catch (IOException e) {
                        Log.e(TAG, "IOException when writing /sys/class/sec/sec_debug/recovery_cause:", e);
                    }
                    try {
                        String content = "RecoverySystem " + recovery_cause;
                        fos.write(content.getBytes(StandardCharsets.UTF_8));
                        fos.close();
                        pm.reboot("recovery");
                    } catch (Throwable th2) {
                        try {
                            fos.close();
                        } catch (Throwable th3) {
                            th2.addSuppressed(th3);
                        }
                        throw th2;
                    }
                }
                throw new IOException("Reboot failed (no permissions?)");
            }
        }
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:118:0x008e -> B:26:0x00b5). Please report as a decompilation issue!!! */
    public static String handleAftermath(Context context) {
        RandomAccessFile raf;
        synchronized (mShutdownIsInProgressLock) {
            if (mShutdownIsInProgress.booleanValue()) {
                Log.i(TAG, "!@[RecoverySystem] handleAftermath: disabled, as shutdown in progress");
                return null;
            }
            Log.i(TAG, "!@[RecoverySystem] handleAftermath");
            String log = null;
            try {
                log = FileUtils.readTextFile(LOG_FILE, -65536, "...\n");
            } catch (FileNotFoundException e) {
                Log.i(TAG, "No recovery log file");
            } catch (IOException e2) {
                Log.e(TAG, "Error reading recovery log", e2);
            }
            FileInputStream reFis = null;
            try {
            } catch (IOException e3) {
                Log.e(TAG, "IOException when close last_recovery_mode file:", e3);
            }
            try {
                try {
                    File reFile = new File("/cache/recovery/last_recovery_mode");
                    reFis = new FileInputStream(reFile);
                    byte[] mode = new byte[21];
                    int bytes = reFis.read(mode);
                    if (bytes > 0) {
                        String lastRecoveryMode = new String(mode, 0, bytes, StandardCharsets.UTF_8);
                        Log.i(TAG, "last_recovery_mode : " + lastRecoveryMode);
                        SystemProperties.set(LAST_RECOVERY_MODE, lastRecoveryMode);
                    }
                    if (!reFile.delete()) {
                        Log.i(TAG, "Failed to delete /cache/recovery/last_recovery_mode");
                    }
                    reFis.close();
                } catch (FileNotFoundException e4) {
                    Log.e(TAG, "FileNotFoundException when open /cache/recovery/last_recovery_mode:", e4);
                    if (reFis != null) {
                        reFis.close();
                    }
                } catch (IOException e5) {
                    Log.e(TAG, "IOException when read /cache/recovery/last_recovery_mode:", e5);
                    if (reFis != null) {
                        reFis.close();
                    }
                }
                File file = RECOVERY_DIR;
                copyFile(new File(file, "last_history"), new File("/data/log/recovery_history.log"));
                copyFile(new File(file, "last_extra_history"), new File("/data/log/recovery_extra_history.log"));
                copyFile(new File(file, "last_recovery"), new File("/data/log/recovery.log"));
                File file2 = RECOVERY_RESCUEPARTY_FILE;
                if (file2.exists()) {
                    try {
                        raf = new RandomAccessFile(file2, "rw");
                    } catch (IOException e6) {
                        Log.e(TAG, "IOException with rescueparty_log :", e6);
                    }
                    try {
                        if (raf.length() > 524288) {
                            raf.setLength(524288L);
                        }
                        raf.close();
                        raf.close();
                        copyFile(new File(RECOVERY_DIR, "rescueparty_log"), new File("/data/log/rescueparty_log"));
                    } finally {
                    }
                }
                boolean reservePackage = BLOCK_MAP_FILE.exists();
                if (!reservePackage) {
                    File file3 = UNCRYPT_PACKAGE_FILE;
                    if (file3.exists()) {
                        String filename = null;
                        try {
                            filename = FileUtils.readTextFile(file3, 0, null);
                        } catch (IOException e7) {
                            Log.e(TAG, "Error reading uncrypt file", e7);
                        }
                        if (filename != null && filename.startsWith("/data")) {
                            if (UNCRYPT_PACKAGE_FILE.delete()) {
                                Log.i(TAG, "Deleted: " + filename);
                            } else {
                                Log.e(TAG, "Can't delete: " + filename);
                            }
                        }
                    }
                }
                File file4 = BLOCK_BACKUP_FILE;
                if (file4.exists()) {
                    copyFile(file4, new File("/data/log/corrupted_blocks"));
                }
                Log.i(TAG, "copy sudden_reset_log to /data/log/");
                File file5 = RECOVERY_DIR;
                File tmpSuddenResetLastKmsg = new File(file5, SUDDEN_RESET_LAST_KMSG_NAME);
                if (tmpSuddenResetLastKmsg.exists()) {
                    copyFile(tmpSuddenResetLastKmsg, new File("/data/log", SUDDEN_RESET_LAST_KMSG_NAME));
                }
                File tmpRecoveryLogFile = new File(TMP_RECOVERY_LOG_PATH);
                if (tmpRecoveryLogFile.exists()) {
                    copyFile(tmpRecoveryLogFile, new File(LAST_CACHE_SUDDEN_RESET_LOG_PATH));
                    copyFile(new File("/proc/last_kmsg"), new File("/data/log", SUDDEN_RESET_LAST_KMSG_NAME));
                    if (tmpRecoveryLogFile.delete()) {
                        Log.i(TAG, "Deleted: /efs/recovery/tmp_recovery.log");
                    } else {
                        Log.e(TAG, "Can't delete: /efs/recovery/tmp_recovery.log");
                    }
                }
                String[] names = file5.list();
                for (int i = 0; names != null && i < names.length; i++) {
                    if (!names[i].startsWith(LAST_PREFIX) && !names[i].equals(LAST_INSTALL_PATH) && ((!reservePackage || !names[i].equals(BLOCK_MAP_FILE.getName())) && ((!reservePackage || !names[i].equals(UNCRYPT_PACKAGE_FILE.getName())) && !names[i].equals(RECOVERY_RESCUEPARTY_FILE.getName()) && !names[i].equals(COMMAND_FILE.getName())))) {
                        recursiveDelete(new File(RECOVERY_DIR, names[i]));
                    }
                }
                return log;
            } catch (Throwable th) {
                if (reFis != null) {
                    try {
                        reFis.close();
                    } catch (IOException e8) {
                        Log.e(TAG, "IOException when close last_recovery_mode file:", e8);
                    }
                }
                throw th;
            }
        }
    }

    private static void deleteSecrets() {
        Log.w(TAG, "deleteSecrets");
        try {
            AndroidKeyStoreMaintenance.deleteAllKeys();
        } catch (KeyStoreException e) {
            Log.wtf(TAG, "Failed to delete all keys from keystore.", e);
        }
    }

    private static void recursiveDelete(File name) {
        if (name.isDirectory()) {
            String[] files = name.list();
            for (int i = 0; files != null && i < files.length; i++) {
                File f = new File(name, files[i]);
                recursiveDelete(f);
            }
        }
        if (!name.delete()) {
            Log.e(TAG, "Can't delete: " + name);
        } else {
            Log.i(TAG, "Deleted: " + name);
        }
    }

    private boolean uncrypt(String packageFile, IRecoverySystemProgressListener listener) {
        try {
            return this.mService.uncrypt(packageFile, listener);
        } catch (RemoteException e) {
            return false;
        }
    }

    private boolean setupBcb(String command) {
        try {
            return this.mService.setupBcb(command);
        } catch (RemoteException e) {
            return false;
        }
    }

    private boolean allocateSpaceForUpdate(File packageFile) throws RemoteException {
        return this.mService.allocateSpaceForUpdate(packageFile.getAbsolutePath());
    }

    private boolean clearBcb() {
        try {
            return this.mService.clearBcb();
        } catch (RemoteException e) {
            return false;
        }
    }

    private void rebootRecoveryWithCommand(String command) {
        try {
            this.mService.rebootRecoveryWithCommand(command);
        } catch (RemoteException e) {
        }
    }

    private boolean requestLskf(String packageName, IntentSender sender) throws IOException {
        try {
            return this.mService.requestLskf(packageName, sender);
        } catch (RemoteException | SecurityException e) {
            throw new IOException("could not request LSKF capture", e);
        }
    }

    private boolean clearLskf(String packageName) throws IOException {
        try {
            return this.mService.clearLskf(packageName);
        } catch (RemoteException | SecurityException e) {
            throw new IOException("could not clear LSKF", e);
        }
    }

    private boolean isLskfCaptured(String packageName) throws IOException {
        try {
            return this.mService.isLskfCaptured(packageName);
        } catch (RemoteException | SecurityException e) {
            throw new IOException("could not get LSKF capture state", e);
        }
    }

    private int rebootWithLskf(String packageName, String reason, boolean slotSwitch) throws IOException {
        try {
            return this.mService.rebootWithLskf(packageName, reason, slotSwitch);
        } catch (RemoteException | SecurityException e) {
            throw new IOException("could not reboot for update", e);
        }
    }

    private int rebootWithLskfAssumeSlotSwitch(String packageName, String reason) throws IOException {
        try {
            return this.mService.rebootWithLskfAssumeSlotSwitch(packageName, reason);
        } catch (RemoteException | RuntimeException e) {
            throw new IOException("could not reboot for update", e);
        }
    }

    private static String sanitizeArg(String arg) {
        return arg.replace((char) 0, '?').replace('\n', '?');
    }

    public RecoverySystem() {
        this.mService = null;
    }

    public RecoverySystem(IRecoverySystem service) {
        this.mService = service;
    }

    private static void copyFile(File source, File dest) {
        String str = "copyFile: Error close FileChannel ";
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            try {
                try {
                    try {
                        inputChannel = new FileInputStream(source).getChannel();
                        outputChannel = new FileOutputStream(dest).getChannel();
                        outputChannel.transferFrom(inputChannel, 0L, inputChannel.size());
                        Os.chmod(dest.getPath(), 416);
                        Os.chown(dest.getPath(), 1000, 1007);
                        if (inputChannel != null) {
                            inputChannel.close();
                        }
                        if (outputChannel != null) {
                            outputChannel.close();
                        }
                    } catch (ErrnoException e) {
                        Log.e(TAG, "copyFile: Error chmod recovery logs", e);
                        if (inputChannel != null) {
                            inputChannel.close();
                        }
                        if (outputChannel != null) {
                            outputChannel.close();
                        }
                    }
                } catch (IOException e2) {
                    Log.e(TAG, "copyFile: Error copy recovery logs", e2);
                    if (inputChannel != null) {
                        inputChannel.close();
                    }
                    if (outputChannel != null) {
                        outputChannel.close();
                    }
                }
            } catch (IOException e3) {
                Log.e(TAG, "copyFile: Error close FileChannel ", e3);
            }
            str = "copyFile: " + source + " -> " + dest;
            Log.i(TAG, str);
        } catch (Throwable th) {
            if (inputChannel != null) {
                try {
                    inputChannel.close();
                } catch (IOException e4) {
                    Log.e(TAG, str, e4);
                    throw th;
                }
            }
            if (outputChannel != null) {
                outputChannel.close();
            }
            throw th;
        }
    }
}
