package com.android.server.backup.utils;

import android.app.backup.IBackupManagerMonitor;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Slog;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.backup.FileMetadata;
import com.android.server.backup.UserBackupManagerService;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes.dex */
public class TarBackupReader {
    public final BytesReadListener mBytesReadListener;
    public final InputStream mInputStream;
    public IBackupManagerMonitor mMonitor;
    public byte[] mWidgetData = null;

    public TarBackupReader(InputStream inputStream, BytesReadListener bytesReadListener, IBackupManagerMonitor iBackupManagerMonitor) {
        this.mInputStream = inputStream;
        this.mBytesReadListener = bytesReadListener;
        this.mMonitor = iBackupManagerMonitor;
    }

    public FileMetadata readTarHeaders() {
        byte[] bArr = new byte[512];
        if (!readTarHeader(bArr)) {
            return null;
        }
        try {
            FileMetadata fileMetadata = new FileMetadata();
            fileMetadata.size = extractRadix(bArr, 124, 12, 8);
            fileMetadata.mtime = extractRadix(bArr, 136, 12, 8);
            fileMetadata.mode = extractRadix(bArr, 100, 8, 8);
            fileMetadata.path = extractString(bArr, FrameworkStatsLog.MAGNIFICATION_USAGE_REPORTED, FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__RESOLVER_CROSS_PROFILE_TARGET_OPENED);
            String extractString = extractString(bArr, 0, 100);
            if (extractString.length() > 0) {
                if (fileMetadata.path.length() > 0) {
                    fileMetadata.path += '/';
                }
                fileMetadata.path += extractString;
            }
            byte b = bArr[156];
            if (b == 120) {
                boolean readPaxExtendedHeader = readPaxExtendedHeader(fileMetadata);
                if (readPaxExtendedHeader) {
                    readPaxExtendedHeader = readTarHeader(bArr);
                }
                if (!readPaxExtendedHeader) {
                    throw new IOException("Bad or missing pax header");
                }
                b = bArr[156];
            }
            if (b == 0) {
                return null;
            }
            if (b == 48) {
                fileMetadata.type = 1;
            } else if (b == 53) {
                fileMetadata.type = 2;
                if (fileMetadata.size != 0) {
                    Slog.w("BackupManagerService", "Directory entry with nonzero size in header");
                    fileMetadata.size = 0L;
                }
            } else {
                Slog.e("BackupManagerService", "Unknown tar entity type: " + ((int) b));
                throw new IOException("Unknown entity type " + ((int) b));
            }
            if ("shared/".regionMatches(0, fileMetadata.path, 0, 7)) {
                fileMetadata.path = fileMetadata.path.substring(7);
                fileMetadata.packageName = "com.android.sharedstoragebackup";
                fileMetadata.domain = "shared";
                Slog.i("BackupManagerService", "File in shared storage: " + fileMetadata.path);
            } else if ("apps/".regionMatches(0, fileMetadata.path, 0, 5)) {
                String substring = fileMetadata.path.substring(5);
                fileMetadata.path = substring;
                int indexOf = substring.indexOf(47);
                if (indexOf < 0) {
                    throw new IOException("Illegal semantic path in " + fileMetadata.path);
                }
                fileMetadata.packageName = fileMetadata.path.substring(0, indexOf);
                String substring2 = fileMetadata.path.substring(indexOf + 1);
                fileMetadata.path = substring2;
                if (!substring2.equals("_manifest") && !fileMetadata.path.equals("_meta")) {
                    int indexOf2 = fileMetadata.path.indexOf(47);
                    if (indexOf2 < 0) {
                        throw new IOException("Illegal semantic path in non-manifest " + fileMetadata.path);
                    }
                    fileMetadata.domain = fileMetadata.path.substring(0, indexOf2);
                    fileMetadata.path = fileMetadata.path.substring(indexOf2 + 1);
                }
            }
            return fileMetadata;
        } catch (IOException e) {
            Slog.e("BackupManagerService", "Parse error in header: " + e.getMessage());
            throw e;
        }
    }

    public static int readExactly(InputStream inputStream, byte[] bArr, int i, int i2) {
        if (i2 <= 0) {
            throw new IllegalArgumentException("size must be > 0");
        }
        int i3 = 0;
        while (i3 < i2) {
            int read = inputStream.read(bArr, i + i3, i2 - i3);
            if (read <= 0) {
                break;
            }
            i3 += read;
        }
        return i3;
    }

    public Signature[] readAppManifestAndReturnSignatures(FileMetadata fileMetadata) {
        long j = fileMetadata.size;
        if (j > 65536) {
            throw new IOException("Restore manifest too big; corrupt? size=" + fileMetadata.size);
        }
        byte[] bArr = new byte[(int) j];
        long readExactly = readExactly(this.mInputStream, bArr, 0, (int) j);
        long j2 = fileMetadata.size;
        if (readExactly == j2) {
            this.mBytesReadListener.onBytesRead(j2);
            String[] strArr = new String[1];
            try {
                int extractLine = extractLine(bArr, 0, strArr);
                int parseInt = Integer.parseInt(strArr[0]);
                if (parseInt == 1) {
                    int extractLine2 = extractLine(bArr, extractLine, strArr);
                    String str = strArr[0];
                    if (str.equals(fileMetadata.packageName)) {
                        int extractLine3 = extractLine(bArr, extractLine2, strArr);
                        fileMetadata.version = Integer.parseInt(strArr[0]);
                        int extractLine4 = extractLine(bArr, extractLine3, strArr);
                        Integer.parseInt(strArr[0]);
                        int extractLine5 = extractLine(bArr, extractLine4, strArr);
                        fileMetadata.installerPackageName = strArr[0].length() > 0 ? strArr[0] : null;
                        int extractLine6 = extractLine(bArr, extractLine5, strArr);
                        fileMetadata.hasApk = strArr[0].equals("1");
                        if (UserBackupManagerService.mSplitRestoreFlag.booleanValue()) {
                            extractLine6 = extractLine(bArr, extractLine6, strArr);
                            fileMetadata.splitCount = Integer.parseInt(strArr[0]);
                        }
                        int extractLine7 = extractLine(bArr, extractLine6, strArr);
                        int parseInt2 = Integer.parseInt(strArr[0]);
                        if (parseInt2 > 0) {
                            Signature[] signatureArr = new Signature[parseInt2];
                            for (int i = 0; i < parseInt2; i++) {
                                extractLine7 = extractLine(bArr, extractLine7, strArr);
                                signatureArr[i] = new Signature(strArr[0]);
                            }
                            return signatureArr;
                        }
                        Slog.i("BackupManagerService", "Missing signature on backed-up package " + fileMetadata.packageName);
                        this.mMonitor = BackupManagerMonitorUtils.monitorEvent(this.mMonitor, 42, null, 3, BackupManagerMonitorUtils.putMonitoringExtra((Bundle) null, "android.app.backup.extra.LOG_EVENT_PACKAGE_NAME", fileMetadata.packageName));
                    } else {
                        Slog.i("BackupManagerService", "Expected package " + fileMetadata.packageName + " but restore manifest claims " + str);
                        this.mMonitor = BackupManagerMonitorUtils.monitorEvent(this.mMonitor, 43, null, 3, BackupManagerMonitorUtils.putMonitoringExtra(BackupManagerMonitorUtils.putMonitoringExtra((Bundle) null, "android.app.backup.extra.LOG_EVENT_PACKAGE_NAME", fileMetadata.packageName), "android.app.backup.extra.LOG_MANIFEST_PACKAGE_NAME", str));
                    }
                } else {
                    Slog.i("BackupManagerService", "Unknown restore manifest version " + parseInt + " for package " + fileMetadata.packageName);
                    this.mMonitor = BackupManagerMonitorUtils.monitorEvent(this.mMonitor, 44, null, 3, BackupManagerMonitorUtils.putMonitoringExtra(BackupManagerMonitorUtils.putMonitoringExtra((Bundle) null, "android.app.backup.extra.LOG_EVENT_PACKAGE_NAME", fileMetadata.packageName), "android.app.backup.extra.LOG_EVENT_PACKAGE_VERSION", (long) parseInt));
                }
            } catch (NumberFormatException unused) {
                Slog.w("BackupManagerService", "Corrupt restore manifest for package " + fileMetadata.packageName);
                this.mMonitor = BackupManagerMonitorUtils.monitorEvent(this.mMonitor, 46, null, 3, BackupManagerMonitorUtils.putMonitoringExtra((Bundle) null, "android.app.backup.extra.LOG_EVENT_PACKAGE_NAME", fileMetadata.packageName));
            } catch (IllegalArgumentException e) {
                Slog.w("BackupManagerService", e.getMessage());
            }
            return null;
        }
        throw new IOException("Unexpected EOF in manifest");
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0178  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.android.server.backup.restore.RestorePolicy chooseRestorePolicy(android.content.pm.PackageManager r8, boolean r9, com.android.server.backup.FileMetadata r10, android.content.pm.Signature[] r11, android.content.pm.PackageManagerInternal r12, int r13, com.android.server.backup.utils.BackupEligibilityRules r14, boolean r15) {
        /*
            Method dump skipped, instructions count: 424
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.backup.utils.TarBackupReader.chooseRestorePolicy(android.content.pm.PackageManager, boolean, com.android.server.backup.FileMetadata, android.content.pm.Signature[], android.content.pm.PackageManagerInternal, int, com.android.server.backup.utils.BackupEligibilityRules, boolean):com.android.server.backup.restore.RestorePolicy");
    }

    public void skipTarPadding(long j) {
        long j2 = (j + 512) % 512;
        if (j2 > 0) {
            int i = 512 - ((int) j2);
            if (readExactly(this.mInputStream, new byte[i], 0, i) == i) {
                this.mBytesReadListener.onBytesRead(i);
                return;
            }
            throw new IOException("Unexpected EOF in padding");
        }
    }

    public void readMetadata(FileMetadata fileMetadata) {
        long j = fileMetadata.size;
        if (j > 65536) {
            throw new IOException("Metadata too big; corrupt? size=" + fileMetadata.size);
        }
        int i = (int) j;
        byte[] bArr = new byte[i];
        long readExactly = readExactly(this.mInputStream, bArr, 0, (int) j);
        long j2 = fileMetadata.size;
        if (readExactly == j2) {
            this.mBytesReadListener.onBytesRead(j2);
            String[] strArr = new String[1];
            int extractLine = extractLine(bArr, 0, strArr);
            int parseInt = Integer.parseInt(strArr[0]);
            if (parseInt == 1) {
                int extractLine2 = extractLine(bArr, extractLine, strArr);
                String str = strArr[0];
                if (fileMetadata.packageName.equals(str)) {
                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr, extractLine2, i - extractLine2);
                    DataInputStream dataInputStream = new DataInputStream(byteArrayInputStream);
                    while (byteArrayInputStream.available() > 0) {
                        int readInt = dataInputStream.readInt();
                        int readInt2 = dataInputStream.readInt();
                        if (readInt2 > 65536) {
                            throw new IOException("Datum " + Integer.toHexString(readInt) + " too big; corrupt? size=" + fileMetadata.size);
                        }
                        if (readInt == 33549569) {
                            byte[] bArr2 = new byte[readInt2];
                            this.mWidgetData = bArr2;
                            dataInputStream.read(bArr2);
                        } else {
                            Slog.i("BackupManagerService", "Ignoring metadata blob " + Integer.toHexString(readInt) + " for " + fileMetadata.packageName);
                            dataInputStream.skipBytes(readInt2);
                        }
                    }
                    return;
                }
                Slog.w("BackupManagerService", "Metadata mismatch: package " + fileMetadata.packageName + " but widget data for " + str);
                this.mMonitor = BackupManagerMonitorUtils.monitorEvent(this.mMonitor, 47, null, 3, BackupManagerMonitorUtils.putMonitoringExtra(BackupManagerMonitorUtils.putMonitoringExtra((Bundle) null, "android.app.backup.extra.LOG_EVENT_PACKAGE_NAME", fileMetadata.packageName), "android.app.backup.extra.LOG_WIDGET_PACKAGE_NAME", str));
                return;
            }
            Slog.w("BackupManagerService", "Unsupported metadata version " + parseInt);
            this.mMonitor = BackupManagerMonitorUtils.monitorEvent(this.mMonitor, 48, null, 3, BackupManagerMonitorUtils.putMonitoringExtra(BackupManagerMonitorUtils.putMonitoringExtra((Bundle) null, "android.app.backup.extra.LOG_EVENT_PACKAGE_NAME", fileMetadata.packageName), "android.app.backup.extra.LOG_EVENT_PACKAGE_VERSION", (long) parseInt));
            return;
        }
        throw new IOException("Unexpected EOF in widget data");
    }

    public static int extractLine(byte[] bArr, int i, String[] strArr) {
        int length = bArr.length;
        if (i >= length) {
            throw new IOException("Incomplete data");
        }
        int i2 = i;
        while (i2 < length && bArr[i2] != 10) {
            i2++;
        }
        strArr[0] = new String(bArr, i, i2 - i);
        return i2 + 1;
    }

    public final boolean readTarHeader(byte[] bArr) {
        int readExactly = readExactly(this.mInputStream, bArr, 0, 512);
        if (readExactly == 0) {
            return false;
        }
        if (readExactly < 512) {
            throw new IOException("Unable to read full block header");
        }
        this.mBytesReadListener.onBytesRead(512L);
        return true;
    }

    public final boolean readPaxExtendedHeader(FileMetadata fileMetadata) {
        long j = fileMetadata.size;
        if (j > 32768) {
            Slog.w("BackupManagerService", "Suspiciously large pax header size " + fileMetadata.size + " - aborting");
            throw new IOException("Sanity failure: pax header size " + fileMetadata.size);
        }
        int i = ((int) ((j + 511) >> 9)) * 512;
        byte[] bArr = new byte[i];
        int i2 = 0;
        if (readExactly(this.mInputStream, bArr, 0, i) < i) {
            throw new IOException("Unable to read full pax header");
        }
        this.mBytesReadListener.onBytesRead(i);
        int i3 = (int) fileMetadata.size;
        do {
            int i4 = i2 + 1;
            while (i4 < i3 && bArr[i4] != 32) {
                i4++;
            }
            if (i4 >= i3) {
                throw new IOException("Invalid pax data");
            }
            int extractRadix = (int) extractRadix(bArr, i2, i4 - i2, 10);
            int i5 = i4 + 1;
            i2 += extractRadix;
            int i6 = i2 - 1;
            int i7 = i5 + 1;
            while (bArr[i7] != 61 && i7 <= i6) {
                i7++;
            }
            if (i7 > i6) {
                throw new IOException("Invalid pax declaration");
            }
            String str = new String(bArr, i5, i7 - i5, "UTF-8");
            String str2 = new String(bArr, i7 + 1, (i6 - i7) - 1, "UTF-8");
            if ("path".equals(str)) {
                fileMetadata.path = str2;
            } else if ("size".equals(str)) {
                fileMetadata.size = Long.parseLong(str2);
            } else {
                Slog.i("BackupManagerService", "Unhandled pax key: " + i5);
            }
        } while (i2 < i3);
        return true;
    }

    public static long extractRadix(byte[] bArr, int i, int i2, int i3) {
        int i4 = i2 + i;
        long j = 0;
        while (i < i4) {
            int i5 = bArr[i];
            if (i5 == 0 || i5 == 32) {
                break;
            }
            if (i5 < 48 || i5 > (i3 + 48) - 1) {
                throw new IOException("Invalid number in header: '" + ((char) i5) + "' for radix " + i3);
            }
            j = (i5 - 48) + (i3 * j);
            i++;
        }
        return j;
    }

    public static String extractString(byte[] bArr, int i, int i2) {
        int i3 = i2 + i;
        int i4 = i;
        while (i4 < i3 && bArr[i4] != 0) {
            i4++;
        }
        return new String(bArr, i, i4 - i, "US-ASCII");
    }

    public IBackupManagerMonitor getMonitor() {
        return this.mMonitor;
    }

    public byte[] getWidgetData() {
        return this.mWidgetData;
    }
}
